package org.smartframework.cloud.examples.basic.auth.biz.oms;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.smartframework.cloud.common.pojo.BasePageResponse;
import org.smartframework.cloud.examples.app.auth.core.UserContext;
import org.smartframework.cloud.examples.basic.auth.entity.base.RoleInfoEntity;
import org.smartframework.cloud.examples.basic.auth.mapper.base.RoleInfoBaseMapper;
import org.smartframework.cloud.examples.basic.rpc.auth.request.oms.role.PageRoleReqVO;
import org.smartframework.cloud.examples.basic.rpc.auth.request.oms.role.RoleCreateReqVO;
import org.smartframework.cloud.examples.basic.rpc.auth.request.oms.role.RoleUpdateReqVO;
import org.smartframework.cloud.examples.basic.rpc.auth.response.base.RoleInfoBaseRespVO;
import org.smartframework.cloud.examples.common.config.constants.DataSourceName;
import org.smartframework.cloud.starter.mybatis.plus.common.biz.BaseBiz;
import org.smartframework.cloud.starter.mybatis.plus.common.mapper.constants.DelState;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@DS(DataSourceName.BASIC_AUTH)
public class RoleInfoOmsBiz extends BaseBiz<RoleInfoBaseMapper, RoleInfoEntity> {

    /**
     * 判断角色编码是否已存在
     *
     * @param code
     * @return
     */
    public boolean exist(String code) {
        return super.getOne(new LambdaQueryWrapper<RoleInfoEntity>()
                .select(RoleInfoEntity::getId)
                .eq(RoleInfoEntity::getCode, code)
                .eq(RoleInfoEntity::getDelState, DelState.NORMAL)) != null;
    }

    /**
     * 添加角色
     *
     * @param req
     * @return
     */
    public Boolean create(RoleCreateReqVO req) {
        RoleInfoEntity entity = super.buildEntity();
        entity.setCode(req.getCode());
        entity.setDescription(req.getDesc());
        entity.setInsertUser(UserContext.getUserId());

        return super.save(entity);
    }

    /**
     * 修改角色信息
     *
     * @param req
     * @return
     */
    public Boolean update(RoleUpdateReqVO req) {
        RoleInfoEntity entity = new RoleInfoEntity();
        entity.setId(req.getId());
        entity.setCode(req.getCode());
        entity.setDescription(req.getDesc());
        entity.setUpdTime(new Date());
        entity.setInsertUser(UserContext.getUserId());
        entity.setUpdUser(UserContext.getUserId());

        return super.updateById(entity);
    }

    /**
     * 分页查询角色信息
     *
     * @param req
     * @return
     */
    public BasePageResponse<RoleInfoBaseRespVO> page(PageRoleReqVO req) {
        LambdaQueryWrapper<RoleInfoEntity> wrapper = new LambdaQueryWrapper<>();
        String code = req.getCode();
        if (StringUtils.isNotBlank(code)) {
            wrapper.likeRight(RoleInfoEntity::getCode, code);
        }
        String desc = req.getDesc();
        if (StringUtils.isNotBlank(desc)) {
            wrapper.like(RoleInfoEntity::getDescription, desc);
        }
        wrapper.eq(RoleInfoEntity::getDelState, DelState.NORMAL);
        wrapper.orderByDesc(RoleInfoEntity::getInsertTime);

        return super.page(req, wrapper, RoleInfoBaseRespVO.class);
    }

    /**
     * 查询角色编码
     *
     * @param roleIds
     * @return
     */
    public Set<String> listCode(Set<Long> roleIds) {
        List<RoleInfoEntity> roleInfoEntities = super.list(new LambdaQueryWrapper<RoleInfoEntity>()
                .select(RoleInfoEntity::getCode)
                .in(RoleInfoEntity::getId, roleIds));
        return roleInfoEntities.stream().map(RoleInfoEntity::getCode).collect(Collectors.toSet());
    }

}