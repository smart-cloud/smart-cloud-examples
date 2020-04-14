package org.smartframework.cloud.examples.basic.user.service.api;

import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.smartframework.cloud.common.pojo.vo.RespVO;
import org.smartframework.cloud.examples.basic.rpc.enums.user.UserStateEnum;
import org.smartframework.cloud.examples.basic.rpc.user.request.api.login.CacheDesKeyReqVO;
import org.smartframework.cloud.examples.basic.rpc.user.request.api.login.LoginReqVO;
import org.smartframework.cloud.examples.basic.rpc.user.response.api.login.GetRsaKeyRespVO;
import org.smartframework.cloud.examples.basic.rpc.user.response.api.login.LoginRespVO;
import org.smartframework.cloud.examples.basic.user.biz.api.LoginInfoApiBiz;
import org.smartframework.cloud.examples.basic.user.bo.login.LoginInfoInsertBizBO;
import org.smartframework.cloud.examples.basic.user.bo.login.LoginInfoInsertServiceBO;
import org.smartframework.cloud.examples.basic.user.config.UserParamValidateMessage;
import org.smartframework.cloud.examples.basic.user.config.UserRedisConfig;
import org.smartframework.cloud.examples.basic.user.entity.base.LoginInfoEntity;
import org.smartframework.cloud.examples.basic.user.enums.UserReturnCodeEnum;
import org.smartframework.cloud.starter.core.business.LoginCache;
import org.smartframework.cloud.starter.core.business.ReqContextHolder;
import org.smartframework.cloud.starter.core.business.exception.ParamValidateException;
import org.smartframework.cloud.starter.core.business.exception.ServerException;
import org.smartframework.cloud.starter.core.business.security.LoginRedisConfig;
import org.smartframework.cloud.starter.core.business.security.util.ReqHttpHeadersUtil;
import org.smartframework.cloud.starter.core.business.util.PasswordUtil;
import org.smartframework.cloud.starter.core.business.util.RespUtil;
import org.smartframework.cloud.starter.mybatis.common.mapper.enums.DelStateEnum;
import org.smartframework.cloud.starter.redis.component.RedisComponent;
import org.smartframework.cloud.utility.security.RsaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LoginInfoApiService {

	@Autowired
	private LoginInfoApiBiz loginInfoApiBiz;
	@Autowired
	private RedisComponent redisComponent;

	/**
	 * 生成rsa公钥、私钥、token
	 * 
	 * @return
	 */
	public GetRsaKeyRespVO generateRsaKey() {
		// 客户端公钥（校验签名）、服务端私钥（签名）
		KeyPair clientPubServerPriKeyPair = null;
		// 客户端私钥（签名）、服务端公钥（校验签名）
		KeyPair clientPriServerPubkeyPair = null;
		try {
			clientPubServerPriKeyPair = RsaUtil.generateKeyPair();
			clientPriServerPubkeyPair = RsaUtil.generateKeyPair();
		} catch (NoSuchAlgorithmException e) {
			log.error(e.getMessage(), e);
			throw new ServerException(UserReturnCodeEnum.GENERATE_RSAKEY_FAIL);
		}

		GetRsaKeyRespVO getRsaKeyRespVO = new GetRsaKeyRespVO();
		getRsaKeyRespVO.setCheckSignModulus(RsaUtil.getModulus(clientPubServerPriKeyPair));
		getRsaKeyRespVO.setCheckSignKey(RsaUtil.getPublicExponent(clientPubServerPriKeyPair));
		
		getRsaKeyRespVO.setSignModules(RsaUtil.getModulus(clientPriServerPubkeyPair));
		getRsaKeyRespVO.setSignKey(RsaUtil.getPrivateExponent(clientPriServerPubkeyPair));
		
		String token = ReqHttpHeadersUtil.generateToken();
		getRsaKeyRespVO.setToken(token);

		cacheRsaKey(token, clientPubServerPriKeyPair, clientPriServerPubkeyPair);
		return getRsaKeyRespVO;
	}

	/**
	 * 缓存rsa key相关信息
	 * 
	 * @param token
	 * @param clientPubServerPriKeyPair
	 * @param clientPriServerPubkeyPair
	 */
	private void cacheRsaKey(String token, KeyPair clientPubServerPriKeyPair, KeyPair clientPriServerPubkeyPair) {
		String tokenRedisKey = LoginRedisConfig.getTokenRedisKey(token);

		LoginCache loginCache = new LoginCache();
		loginCache.setToken(token);
		loginCache.setSignModules(RsaUtil.getModulus(clientPubServerPriKeyPair));
		loginCache.setSignKey(RsaUtil.getPrivateExponent(clientPubServerPriKeyPair));
		loginCache.setCheckSignModulus(RsaUtil.getModulus(clientPriServerPubkeyPair));
		loginCache.setCheckSignKey(RsaUtil.getPublicExponent(clientPriServerPubkeyPair));
		
		redisComponent.setObject(tokenRedisKey, loginCache, UserRedisConfig.NON_LOGIN_TOKEN_EXPIRE_MILLIS);
	}

	public void cacheDesKey(CacheDesKeyReqVO req) {
		LoginCache loginCache = ReqContextHolder.getLoginCache();
		loginCache.setAesKey(req.getKey());

		String token = loginCache.getToken();
		String tokenRedisKey = LoginRedisConfig.getTokenRedisKey(token);
		redisComponent.setObject(tokenRedisKey, loginCache, UserRedisConfig.NON_LOGIN_TOKEN_EXPIRE_MILLIS);
	}

	/**
	 * 登陆校验
	 * 
	 * @param req
	 * @return
	 */
	public RespVO<LoginRespVO> login(LoginReqVO req) {
		LoginInfoEntity entity = loginInfoApiBiz.queryByUsername(req.getUsername());
		if (Objects.isNull(entity)) {
			return RespUtil.error(UserReturnCodeEnum.ACCOUNT_NOT_EXIST);
		}
		// 校验密码
		String salt = entity.getSalt();
		String securePassword = PasswordUtil.secure(req.getPassword(), salt);
		if (!Objects.equals(securePassword, entity.getPassword())) {
			return RespUtil.error(UserReturnCodeEnum.USERNAME_OR_PASSWORD_ERROR);
		}

		if (Objects.equals(entity.getUserState(), UserStateEnum.UNENABLE.getValue())) {
			return RespUtil.error(UserReturnCodeEnum.USER_UNENABLE);
		}
		if (Objects.equals(entity.getDelState(), DelStateEnum.DELETED.getDelState())) {
			return RespUtil.error(UserReturnCodeEnum.USER_DELETED);
		}

		Long userId = entity.getId();
		cacheLoginAfterLoginSuccess(userId);

		return RespUtil.success(new LoginRespVO(userId));
	}
	
	public void cacheLoginAfterLoginSuccess(Long userId) {
		// 1、更新当前登陆缓存
		LoginCache loginCache = ReqContextHolder.getLoginCache();
		loginCache.setUserId(userId);

		String token = loginCache.getToken();
		String tokenRedisKey = LoginRedisConfig.getTokenRedisKey(token);
		redisComponent.setObject(tokenRedisKey, loginCache, UserRedisConfig.APP_LOGINED_TOKEN_EXPIRE_MILLIS);
		
		// 2、删除上次登陆的缓存
		String userIdRedisKey = LoginRedisConfig.getUserIdRedisKey(userId);
		// 上一次登陆成功保存的token
		String oldToken = redisComponent.getString(userIdRedisKey);
		if(StringUtils.isNotBlank(oldToken)) {
			String oldTokenRedisKey = LoginRedisConfig.getTokenRedisKey(oldToken);
			redisComponent.delete(oldTokenRedisKey);
		}
		
		// 3、保存当前登陆的“userId: token”对，以便于下次登陆成功后删除上一次的
		redisComponent.setString(userIdRedisKey, token, UserRedisConfig.APP_LOGINED_TOKEN_EXPIRE_MILLIS);
	}
	
	/**
	 * 插入登陆信息
	 * 
	 * @param dto
	 * @return
	 */
	public LoginInfoEntity insert(LoginInfoInsertServiceBO bo) {
		// 判断该用户名是否已存在
		boolean existUsername = loginInfoApiBiz.existByUsername(bo.getUsername());
		if(existUsername) {
			throw new ParamValidateException(UserParamValidateMessage.REGISTER_USERNAME_EXSITED);
		}
		
		String salt = generateRandomSalt();
		String securePassword = PasswordUtil.secure(bo.getPassword(), salt);
		
		LoginInfoInsertBizBO loginInfoInsertDto = LoginInfoInsertBizBO.builder()
				.userId(bo.getUserId())
				.username(bo.getUsername())
				.password(securePassword)
				.pwdState(bo.getPwdState())
				.salt(salt)
				.build();
		return loginInfoApiBiz.insert(loginInfoInsertDto);
	}
	
	
	/**
	 * 生成随机盐值
	 * 
	 * @return
	 */
	private String generateRandomSalt() {
		String salt = null;
		try {
			salt = PasswordUtil.generateRandomSalt();
		} catch (NoSuchAlgorithmException e) {
			log.error(e.getMessage(), e);
			throw new ServerException(UserReturnCodeEnum.GENERATE_SALT_FAIL);
		}
		return salt;
	}

}