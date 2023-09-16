package com.lagou.config;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 〈资源服务器取出jwt令牌扩展信息〉
 *
 * @author 商玉
 * @create 2022/1/25
 * @since 1.0.0
 */
@Component
public class LagouAccessTokenConvertor extends DefaultAccessTokenConverter {

    @Override
    public OAuth2Authentication extractAuthentication(Map<String, ?> map) {
        OAuth2Authentication oAuth2Authentication = super.extractAuthentication(map);
        //将map放入认证对象中，认证对象再controller中可以拿到
        oAuth2Authentication.setDetails(map);
        return oAuth2Authentication;
    }
}
