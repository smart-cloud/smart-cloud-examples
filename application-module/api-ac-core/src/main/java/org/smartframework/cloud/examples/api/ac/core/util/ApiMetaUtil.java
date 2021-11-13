package org.smartframework.cloud.examples.api.ac.core.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.smartframework.cloud.api.core.annotation.RequireDataSecurity;
import org.smartframework.cloud.api.core.annotation.RequireRepeatSubmitCheck;
import org.smartframework.cloud.api.core.annotation.RequireTimestamp;
import org.smartframework.cloud.api.core.annotation.auth.RequirePermissions;
import org.smartframework.cloud.api.core.annotation.auth.RequireRoles;
import org.smartframework.cloud.api.core.annotation.auth.RequireUser;
import org.smartframework.cloud.api.core.annotation.enums.SignType;
import org.smartframework.cloud.constants.SymbolConstant;
import org.smartframework.cloud.examples.api.ac.core.vo.*;
import org.smartframework.cloud.starter.core.constants.PackageConfig;
import org.smartframework.cloud.starter.rpc.feign.annotation.SmartFeignClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author liyulin
 * @date 2020-09-18
 */
@Slf4j
@UtilityClass
public class ApiMetaUtil {

    /**
     * 收集上传接口元数据
     *
     * @return
     */
    public ApiMetaFetchRespVO collectApiMetas() {
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

        Set<Method> allMappingSet = getAllApiMethods(reflections);
        if (CollectionUtils.isEmpty(allMappingSet)) {
            log.debug("methodSet is empty!");
            return null;
        }

        Map<String, ApiAccessMetaRespVO> apiAccessMap = new HashMap<>();
        for (Method method : allMappingSet) {
            Class<?> declaringClass = method.getDeclaringClass();
            // 过滤掉rpc接口
            if (isRpc(declaringClass)) {
                continue;
            }

            String urlHeader = getUrlUnderClass(declaringClass);
            String[] urlTails = getUrlTails(method);
            for (String urlTail : urlTails) {
                String urlCode = getUrlCode(urlHeader, urlTail);

                ApiAccessMetaRespVO apiAccessMeta = new ApiAccessMetaRespVO();
                apiAccessMeta.setAuthMeta(buildAuthMeta(method));
                apiAccessMeta.setDataSecurityMeta(buildDataSecurityMeta(method));
                apiAccessMeta.setRepeatSubmitCheckMeta(buildRepeatSubmitCheckMeta(method));
                apiAccessMeta.setRequestValidMillis(getRequestValidMillis(method));
                apiAccessMap.put(urlCode, apiAccessMeta);
            }
        }

        return new ApiMetaFetchRespVO(apiAccessMap);
    }

    private boolean isRpc(Class<?> declaringClass) {
        return declaringClass.isInterface() && (declaringClass.isAnnotationPresent(SmartFeignClient.class) || declaringClass.isAnnotationPresent(FeignClient.class));
    }

    /**
     * 重复提交校验meta
     *
     * @param method
     * @return
     */
    private RepeatSubmitCheckMetaRespVO buildRepeatSubmitCheckMeta(Method method) {
        RequireRepeatSubmitCheck requireRepeatSubmitCheck = method.getAnnotation(RequireRepeatSubmitCheck.class);

        RepeatSubmitCheckMetaRespVO repeatSubmitCheckMeta = new RepeatSubmitCheckMetaRespVO();
        boolean check = requireRepeatSubmitCheck != null;
        repeatSubmitCheckMeta.setCheck(check);
        repeatSubmitCheckMeta.setExpireMillis(check ? requireRepeatSubmitCheck.expireMillis() : 0L);
        return repeatSubmitCheckMeta;
    }

    /**
     * 请求有效间隔meta
     *
     * @param method
     * @return
     */
    private Long getRequestValidMillis(Method method) {
        RequireTimestamp requireTimestamp = method.getAnnotation(RequireTimestamp.class);
        return requireTimestamp == null ? null : requireTimestamp.validMillis();
    }

    /**
     * 接口安全处理meta
     *
     * @param method
     * @return
     */
    private DataSecurityMetaRespVO buildDataSecurityMeta(Method method) {
        RequireDataSecurity requireDataSecurity = method.getAnnotation(RequireDataSecurity.class);
        DataSecurityMetaRespVO dataSecurityMeta = new DataSecurityMetaRespVO();
        if (requireDataSecurity == null) {
            dataSecurityMeta.setRequestDecrypt(false);
            dataSecurityMeta.setResponseEncrypt(false);
            dataSecurityMeta.setSign(SignType.NONE.getType());
        } else {
            dataSecurityMeta.setRequestDecrypt(requireDataSecurity.requestDecrypt());
            dataSecurityMeta.setResponseEncrypt(requireDataSecurity.responseEncrypt());
            dataSecurityMeta.setSign(requireDataSecurity.sign().getType());
        }
        return dataSecurityMeta;
    }

    /**
     * 接口鉴权meta
     *
     * @param method
     * @return
     */
    private AuthMetaRespVO buildAuthMeta(Method method) {
        RequirePermissions requirePermissions = method.getAnnotation(RequirePermissions.class);
        RequireRoles requireRoles = method.getAnnotation(RequireRoles.class);
        RequireUser requireUser = method.getAnnotation(RequireUser.class);

        AuthMetaRespVO authMeta = new AuthMetaRespVO();
        authMeta.setRequireUser(requireUser != null);
        authMeta.setRequireRoles((requireRoles != null) ? requireRoles.value() : new String[0]);
        authMeta.setRequirePermissions((requirePermissions != null) ? requirePermissions.value() : new String[0]);
        return authMeta;
    }

    private Set<Method> getAllApiMethods(Reflections reflections) {
        Set<Method> allMappingSet = new HashSet<>();
        allMappingSet.addAll(reflections.getMethodsAnnotatedWith(RequestMapping.class));
        allMappingSet.addAll(reflections.getMethodsAnnotatedWith(PostMapping.class));
        allMappingSet.addAll(reflections.getMethodsAnnotatedWith(GetMapping.class));
        allMappingSet.addAll(reflections.getMethodsAnnotatedWith(DeleteMapping.class));
        allMappingSet.addAll(reflections.getMethodsAnnotatedWith(PutMapping.class));
        allMappingSet.addAll(reflections.getMethodsAnnotatedWith(PatchMapping.class));

        return allMappingSet;
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
        if (!urlHeader.startsWith(SymbolConstant.DIAGONAL_BAR)) {
            urlHeader = SymbolConstant.DIAGONAL_BAR + urlHeader;
        }
        String urlCode;
        if (!urlHeader.endsWith(SymbolConstant.DIAGONAL_BAR) && !urlTail.startsWith(SymbolConstant.DIAGONAL_BAR)) {
            urlCode = urlHeader + SymbolConstant.DIAGONAL_BAR + urlTail;
        } else if (urlHeader.endsWith(SymbolConstant.DIAGONAL_BAR) && urlTail.startsWith(SymbolConstant.DIAGONAL_BAR)) {
            urlCode = urlHeader + urlTail.substring(1);
        } else {
            urlCode = urlHeader + urlTail;
        }
        return urlCode;
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

        throw new UnsupportedOperationException(String.format("%s|%s:%s", method.getDeclaringClass().getName(), method.getName(), "http method is not supprted!"));
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