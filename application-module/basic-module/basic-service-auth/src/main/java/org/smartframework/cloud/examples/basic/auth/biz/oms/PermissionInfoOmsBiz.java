package org.smartframework.cloud.examples.basic.auth.biz.oms;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.smartframework.cloud.common.pojo.BasePageResponse;
import org.smartframework.cloud.examples.app.auth.core.UserContext;
import org.smartframework.cloud.examples.basic.auth.entity.base.PermissionInfoEntity;
import org.smartframework.cloud.examples.basic.auth.mapper.base.PermissionInfoBaseMapper;
import org.smartframework.cloud.examples.basic.rpc.auth.request.oms.permisson.PagePermissionReqVO;
import org.smartframework.cloud.examples.basic.rpc.auth.request.oms.permisson.PermissionCreateReqVO;
import org.smartframework.cloud.examples.basic.rpc.auth.request.oms.permisson.PermissionUpdateReqVO;
import org.smartframework.cloud.examples.basic.rpc.auth.response.base.PermissionInfoBaseRespVO;
import org.smartframework.cloud.examples.common.config.constants.DataSourceName;
import org.smartframework.cloud.starter.mybatis.common.biz.BaseBiz;
import org.smartframework.cloud.starter.mybatis.common.mapper.constants.DelState;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@DS(DataSourceName.BASIC_AUTH)
public class PermissionInfoOmsBiz extends BaseBiz<PermissionInfoBaseMapper, PermissionInfoEntity> {

    /**
     * 判断权限编码是否已存在
     *
     * @param id   权限主键id
     * @param code
     * @return
     */
    public boolean exist(Long id, String code) {
        LambdaQueryWrapper<PermissionInfoEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(PermissionInfoEntity::getId)
                .eq(PermissionInfoEntity::getCode, code)
                .eq(PermissionInfoEntity::getDelState, DelState.NORMAL);
        if (id != null) {
            queryWrapper.ne(PermissionInfoEntity::getId, id);
        }
        return super.getOne(queryWrapper) != null;
    }

    /**
     * 添加角色
     *
     * @param req
     * @return
     */
    public Boolean create(PermissionCreateReqVO req) {
        PermissionInfoEntity entity = create();
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
    public Boolean update(PermissionUpdateReqVO req) {
        PermissionInfoEntity entity = new PermissionInfoEntity();
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
    public BasePageResponse<PermissionInfoBaseRespVO> page(PagePermissionReqVO req) {
        LambdaQueryWrapper<PermissionInfoEntity> wrapper = new LambdaQueryWrapper<>();
        String code = req.getCode();
        if (StringUtils.isNotBlank(code)) {
            wrapper.likeRight(PermissionInfoEntity::getCode, code);
        }
        String desc = req.getDesc();
        if (StringUtils.isNotBlank(desc)) {
            wrapper.like(PermissionInfoEntity::getDescription, desc);
        }
        wrapper.eq(PermissionInfoEntity::getDelState, DelState.NORMAL);
        wrapper.orderByDesc(PermissionInfoEntity::getInsertTime);

        return super.page(req, wrapper, PermissionInfoBaseRespVO.class);
    }

    /**
     * 查询权限编码
     *
     * @param permissionIds
     * @return
     */
    public Set<String> listCode(Set<Long> permissionIds) {
        List<PermissionInfoEntity> permissionInfoEntities = super.list(new LambdaQueryWrapper<PermissionInfoEntity>()
                .select(PermissionInfoEntity::getCode)
                .in(PermissionInfoEntity::getId, permissionIds));
        return permissionInfoEntities.stream().map(PermissionInfoEntity::getCode).collect(Collectors.toSet());
    }

}