package org.smartframework.cloud.examples.api.ac.core.listener;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.smartframework.cloud.api.core.annotation.SmartApiAC;
import org.smartframework.cloud.common.pojo.Base;
import org.smartframework.cloud.common.pojo.vo.RespVO;
import org.smartframework.cloud.examples.api.ac.core.properties.ApiAcProperties;
import org.smartframework.cloud.examples.support.rpc.gateway.ApiMetaRpc;
import org.smartframework.cloud.examples.support.rpc.gateway.request.rpc.ApiMetaUploadReqVO;
import org.smartframework.cloud.examples.support.rpc.gateway.request.rpc.ApiMetaUploadReqVO.ApiAC;
import org.smartframework.cloud.starter.core.business.exception.ServerException;
import org.smartframework.cloud.starter.core.business.util.RespUtil;
import org.smartframework.cloud.starter.core.constants.PackageConfig;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @desc 上传接口信息给网关
 * @author liyulin
 * @date 2020/04/14
 */
@Slf4j
@AllArgsConstructor
public class UploadApiMetaListener implements ApplicationListener<ApplicationStartedEvent> {

	private ApiMetaRpc apiMetaRpc;
	private ApiAcProperties apiAcProperties;

	@Override
	public void onApplicationEvent(ApplicationStartedEvent event) {
		if (!apiAcProperties.isUploadApiMeta()) {
			log.info("upload api meta is ignored!");
			return;
		}
		
		log.info("upload api meta to gateway start!");
		RespVO<Base> result = uploadApiMetaToGateWay();
		log.info("upload api meta to gateway finish!");
		
		if (!RespUtil.isSuccess(result)) {
			throw new ServerException(RespUtil.getFailMsg(result));
		}
	}

	/**
	 * 上传接口元数据给网关
	 * @return
	 */
	public RespVO<Base> uploadApiMetaToGateWay() {
		String[] basePackages = PackageConfig.getBasePackages();
		if (ArrayUtils.isEmpty(basePackages)) {
			log.debug("basePackages is empty!");
			return null;
		}

		Reflections reflections = null;
		for (String basePackage : basePackages) {
			if (reflections == null) {
				reflections = new Reflections(basePackage, new MethodAnnotationsScanner());
			} else {
				reflections.merge(new Reflections(basePackage, new MethodAnnotationsScanner()));
			}
		}
		Set<Method> methodSet = reflections.getMethodsAnnotatedWith(SmartApiAC.class);
		if(CollectionUtils.isEmpty(methodSet)) {
			log.debug("methodSet is empty!");
			return null;
		}

		Map<String, ApiAC> apiACs = new HashMap<>();
		for (Method method : methodSet) {
			ApiAC apiAC = buildApiAC(method.getAnnotation(SmartApiAC.class));

			Class<?> declaringClass = method.getDeclaringClass();
			String urlHeader = getUrlUnderClass(declaringClass);
			String[] urlTails = getUrlTails(method);
			for (String urlTail : urlTails) {
				String urlCode = getUrlCode(urlHeader, urlTail);
				apiACs.put(urlCode, apiAC);
			}
		}

		return apiMetaRpc.upload(new ApiMetaUploadReqVO(apiACs));
	}
	
	/**
	 * 获取url code
	 * 
	 * @param urlHeader
	 * @param urlTail
	 * @return
	 */
	private String getUrlCode(String urlHeader, String urlTail) {
		urlHeader = (urlHeader == null) ? "" : urlHeader;
		if(!urlHeader.startsWith("/")) {
			urlHeader = "/"+urlHeader;
		}
		String urlCode;
		if(!urlHeader.endsWith("/") && !urlTail.startsWith("/")) {
			urlCode = urlHeader + "/"+urlTail;
		} else if(urlHeader.endsWith("/") && urlTail.startsWith("/")) {
			urlCode = urlHeader + urlTail.substring(1);
		} else {
			urlCode = urlHeader +urlTail;
		}
		return urlCode;
	}
	
	private ApiAC buildApiAC(SmartApiAC smartApiAC) {
		return ApiAC.builder().tokenCheck(smartApiAC.tokenCheck())
				.sign(smartApiAC.sign().getType())
				.decrypt(smartApiAC.decrypt())
				.encrypt(smartApiAC.encrypt())
				.auth(smartApiAC.auth())
				.repeatSubmitCheck(smartApiAC.repeatSubmitCheck())
				.build();
	}

	/**
	 * 获取url尾部（由url“后缀+httpmethod”组成）
	 * 
	 * @param method
	 * @return
	 */
	private String[] getUrlTails(Method method) {
		if (method.isAnnotationPresent(GetMapping.class)) {
			GetMapping mapping = method.getAnnotation(GetMapping.class);
			String[] paths = ArrayUtils.isEmpty(mapping.value()) ? mapping.path() : mapping.value();
			for (int i = 0; i < paths.length; i++) {
				paths[i] = paths[i] + RequestMethod.GET;
			}
			return paths;
		}
		if (method.isAnnotationPresent(PostMapping.class)) {
			PostMapping mapping = method.getAnnotation(PostMapping.class);
			String[] paths = ArrayUtils.isEmpty(mapping.value()) ? mapping.path() : mapping.value();
			for (int i = 0; i < paths.length; i++) {
				paths[i] = paths[i] + RequestMethod.POST;
			}
			return paths;
		}
		if (method.isAnnotationPresent(PutMapping.class)) {
			PutMapping mapping = method.getAnnotation(PutMapping.class);
			String[] paths = ArrayUtils.isEmpty(mapping.value()) ? mapping.path() : mapping.value();
			for (int i = 0; i < paths.length; i++) {
				paths[i] = paths[i] + RequestMethod.PUT;
			}
			return paths;
		}
		if (method.isAnnotationPresent(DeleteMapping.class)) {
			DeleteMapping mapping = method.getAnnotation(DeleteMapping.class);
			String[] paths = ArrayUtils.isEmpty(mapping.value()) ? mapping.path() : mapping.value();
			for (int i = 0; i < paths.length; i++) {
				paths[i] = paths[i] + RequestMethod.DELETE;
			}
			return paths;
		}
		if (method.isAnnotationPresent(PatchMapping.class)) {
			PatchMapping mapping = method.getAnnotation(PatchMapping.class);
			String[] paths = ArrayUtils.isEmpty(mapping.value()) ? mapping.path() : mapping.value();
			for (int i = 0; i < paths.length; i++) {
				paths[i] = paths[i] + RequestMethod.PATCH;
			}
			return paths;
		}
		if (method.isAnnotationPresent(RequestMapping.class)) {
			RequestMapping mapping = method.getAnnotation(RequestMapping.class);
			String[] paths = ArrayUtils.isEmpty(mapping.value()) ? mapping.path() : mapping.value();
			RequestMethod[] requestMethods = mapping.method();
			String[] urlTails = new String[paths.length * requestMethods.length];
			for (int i = 0, j = 0; i < paths.length; i++) {
				for (RequestMethod requestMethod : requestMethods) {
					urlTails[j++] = paths[i] + requestMethod.name();
				}
			}
			return urlTails;
		}

		throw new UnsupportedOperationException(
				method.getDeclaringClass().getName() + "|" + method.getName() + ":" + "http method is not supprted!");
	}

	private String getUrlUnderClass(Class<?> c) {
		// 是否有RequestMapping注解标注
		if (c.isAnnotationPresent(RequestMapping.class)) {
			RequestMapping requestMapping = c.getAnnotation(RequestMapping.class);
			String[] path = ArrayUtils.isEmpty(requestMapping.value()) ? requestMapping.path() : requestMapping.value();
			return path[0];
		} else {
			return null;
		}
	}

}