package com.lagou.config;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 〈〉
 *
 * @author 商玉
 * @create 2022/1/24
 * @since 1.0.0
 */
@Component
public class LagouAccessTokenConvertor extends DefaultAccessTokenConverter {

    @Override
    public Map<String, ?> convertAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.getRequestAttributes())).getRequest();
        String remoteAddr = request.getRemoteAddr();

        Map<String, String> stringMap = (Map<String, String>) super.convertAccessToken(token, authentication);
        stringMap.put("clientIp", remoteAddr);
        return stringMap;
    }

    //@Override
    //public OAuth2Authentication extractAuthentication(Map<String, ?> map) {
    //    OAuth2Authentication oAuth2Authentication = super.extractAuthentication(map);
    //    //将map放入认证对象中，认证对象再controller中可以拿到
    //    oAuth2Authentication.setDetails(map);
    //    return oAuth2Authentication;
    //}
}
