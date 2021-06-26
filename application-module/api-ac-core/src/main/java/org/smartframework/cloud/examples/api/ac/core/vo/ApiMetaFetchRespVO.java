package org.smartframework.cloud.examples.api.ac.core.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.smartframework.cloud.common.pojo.Base;

import java.util.Map;

/**
 * Api访问控制元数据信息
 *
 * @author liyulin
 * @date 2020-09-10
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiMetaFetchRespVO extends Base {

    private static final long serialVersionUID = 1L;

    /**
     * Api访问控制信息<url编码（由url+http method组成）, Api访问控制信息>
     */
    private Map<String, ApiAccessMetaRespVO> apiAccessMap;

}