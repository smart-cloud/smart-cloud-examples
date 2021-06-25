package org.smartframework.cloud.examples.api.ac.core.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.smartframework.cloud.api.core.annotation.SmartApiAcess;
import org.smartframework.cloud.api.core.annotation.SmartRequiresDataSecurity;
import org.smartframework.cloud.api.core.annotation.SmartRequiresRepeatSubmitCheck;
import org.smartframework.cloud.api.core.annotation.auth.SmartRequiresPermissions;
import org.smartframework.cloud.api.core.annotation.auth.SmartRequiresRoles;
import org.smartframework.cloud.api.core.annotation.auth.SmartRequiresUser;
import org.smartframework.cloud.examples.api.ac.core.vo.ApiMetaFetchRespVO;
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

        Map<String, ApiMetaFetchRespVO.ApiAccess> apiAccessMap = new HashMap<>();
        for (Method method : allMappingSet) {
            Class<?> declaringClass = method.getDeclaringClass();
            // 过滤掉rpc接口
            if (declaringClass.isInterface() && (declaringClass.isAnnotationPresent(SmartFeignClient.class) || declaringClass.isAnnotationPresent(FeignClient.class))) {
                continue;
            }

            SmartRequiresDataSecurity smartRequiresDataSecurity = method.getAnnotation(SmartRequiresDataSecurity.class);
            SmartRequiresRepeatSubmitCheck smartRequiresRepeatSubmitCheck = method.getAnnotation(SmartRequiresRepeatSubmitCheck.class);
            // auth
            SmartRequiresPermissions smartRequiresPermissions = method.getAnnotation(SmartRequiresPermissions.class);
            SmartRequiresRoles smartRequiresRoles = method.getAnnotation(SmartRequiresRoles.class);
            SmartRequiresUser smartRequiresUser = method.getAnnotation(SmartRequiresUser.class);

            String urlHeader = getUrlUnderClass(declaringClass);
            String[] urlTails = getUrlTails(method);
            for (String urlTail : urlTails) {
                String urlCode = getUrlCode(urlHeader, urlTail);
                ApiMetaFetchRespVO.ApiAccess apiAccess = null;
                if (smartRequiresDataSecurity != null) {
                    apiAccess = buildApiAccess(smartApiAcess);
                }
                apiAccessMap.put(urlCode, apiAccess);
            }
        }

        return new ApiMetaFetchRespVO(apiAccessMap);
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
        if (!urlHeader.startsWith("/")) {
            urlHeader = "/" + urlHeader;
        }
        String urlCode;
        if (!urlHeader.endsWith("/") && !urlTail.startsWith("/")) {
            urlCode = urlHeader + "/" + urlTail;
        } else if (urlHeader.endsWith("/") && urlTail.startsWith("/")) {
            urlCode = urlHeader + urlTail.substring(1);
        } else {
            urlCode = urlHeader + urlTail;
        }
        return urlCode;
    }

    private ApiMetaFetchRespVO.ApiAccess buildApiAccess(SmartApiAcess smartApiAcess) {
        return ApiMetaFetchRespVO.ApiAccess.builder().tokenCheck(smartApiAcess.tokenCheck())
                .sign(smartApiAcess.sign().getType())
                .decrypt(smartApiAcess.decrypt())
                .encrypt(smartApiAcess.encrypt())
                .auth(smartApiAcess.auth())
                .repeatSubmitCheck(smartApiAcess.repeatSubmitCheck())
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