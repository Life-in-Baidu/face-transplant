package com.ruoyi.biz.api.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.biz.api.context.ApiContext;
import com.ruoyi.biz.api.service.TokenApiService;
import com.ruoyi.biz.api.util.IpUtil;
import com.ruoyi.biz.domain.User;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.domain.ApiResult;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName : JwtTokenInterceptor
 * @Description :token拦截器
 * @Author : Fly
 * @Date: 2021-01-26 17:46
 */
@Component
@Slf4j
public class ApiTokenInterceptor implements HandlerInterceptor {
    private final UrlPathHelper urlPathHelper = new UrlPathHelper();

    private static final String TOKEN_HEADER_KEY = "X-YAuth-Token";


    @Value("${api.token.excludes}")
    private String excludes;

    public List<String> excludeUrls = new ArrayList<>();

    @Autowired
    private TokenApiService tokenApiService;
    /**
     * 当接口调用超过3秒，则需要出告警日志
     */
    private static final int WARNING_COST_TIME = 1000 * 3;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ip = IpUtil.getIpAddr(request);
        String jwt = null;
        User user;
        String path = urlPathHelper.getLookupPathForRequest(request);
        try {
            long startTime = System.currentTimeMillis();
            ApiContext.setRequestStartTime(startTime);
            jwt = request.getHeader(TOKEN_HEADER_KEY);
            log.info("请求拦截:{}",jwt);
            if (StringUtils.isBlank(jwt)) {
                if (isNoAuthUrl(path)) {
                    return true;
                }
                // 请求头不包含token，则直接重新登入
                log.error("AuthHeader is empty,ip:{},URI:{}", ip, request.getRequestURI());
                responseErrorResult(response, ApiResult.error(HttpStatus.UNAUTHORIZED,"token认证失败"));
                return false;
            }

            // Token不存在
            user = tokenApiService.getLoginUser(jwt);
            if (user == null) {
                if (isNoAuthUrl(path)) {
                    return true;
                }
                log.error("Token not exists,ip:{},URI:{}", ip, request.getRequestURI());
                responseErrorResult(response, ApiResult.error(HttpStatus.UNAUTHORIZED,"token认证失败,token不存在"));
                return false;
            }

            //看看是否要刷新
            tokenApiService.verifyToken(user);
            ApiContext.setUser(user);
            return true;

        } catch (ExpiredJwtException e) {
            if (isNoAuthUrl(path)) {
                return true;
            }

            log.error("token认证失败,ip:{},URI:{}", ip, request.getRequestURI());
            tokenApiService.delLoginUser(jwt);
            responseErrorResult(response, ApiResult.error(HttpStatus.UNAUTHORIZED,"token认证失败"));
            return false;
        } catch (Exception e) {
            if (isNoAuthUrl(path)) {
                return true;
            }
            log.error("Process token error,token:{},ip:{},URI:{}", jwt, ip, request.getRequestURI(), e);
            responseErrorResult(response, ApiResult.error(HttpStatus.UNAUTHORIZED,"token认证失败"));
            return false;
        }
    }

    private boolean isNoAuthUrl(String path) {
        if (excludeUrls.isEmpty()){
            List<String> data = new ArrayList<String>();
            String[] url = excludes.split(",");
            for (int i = 0; url != null && i < url.length; i++)
            {
                data.add(url[i]);
            }
            excludeUrls = data;
        }
        // 判断请求的URL是否是在过滤的集合中，如果在过滤集合中，则无需认证
        for (String pattern : excludeUrls)
        {
            Pattern p = Pattern.compile("^" + pattern);
            Matcher m = p.matcher(path);
            if (m.find())
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        ApiContext.removeUser();
        Long startTime = ApiContext.getRequestStartTime();
        if (startTime != null) {
            long cost = System.currentTimeMillis() - startTime;
            if (cost > WARNING_COST_TIME) {
                log.warn("Cost time more than:{} ms,cost:{} ms,URI:{},method:{}", WARNING_COST_TIME, cost,
                        request.getRequestURI(), request.getMethod());
            } else {
                log.info("Cost time:{} ms,URI:{},method:{}", cost, request.getRequestURI(), request.getMethod());
            }
        }
    }

    private void  responseErrorResult(HttpServletResponse response, ApiResult result) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().println(JSONObject.toJSONString(result));

    }
}
