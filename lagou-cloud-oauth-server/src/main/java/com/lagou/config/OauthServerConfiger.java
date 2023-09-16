package com.lagou.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.jwt.crypto.sign.MacSigner;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import javax.sql.DataSource;

/**
 * 〈〉
 *
 * @author 商玉
 * @create 2022/1/24
 * @since 1.0.0
 */
@Configuration
@EnableAuthorizationServer
public class OauthServerConfiger extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private LagouAccessTokenConvertor lagouAccessTokenConvertor;

    /**
     * 认证服务器最终是以api接口的方式对外提供服务（校验合法性并生成令牌、校验令牌等）
     * 那么，以api接口方式对外的话，就涉及到接口的访问权限，我们需要在这里进行必要的配置
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        super.configure(security);
        //允许客户端表单验证
        security.allowFormAuthenticationForClients()
                //开启端口、oauth/token_key的访问权限（允许）
                .tokenKeyAccess("permitAll()")
                //开启端口/oauth/check_token的访问权限（允许）
                .checkTokenAccess("permitAll()");
    }

    /**
     * 客户端详情配置
     * 比如client_id ,secret
     * 当前这个服务就如同qq平台
     * 颁发client_id等必要参数，表明客户端是谁
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        super.configure(clients);
        clients.withClientDetails(createJdbcClientDetailsService());

        //clients.inMemory()
        //        //添加一个client配置，指定其client——id
        //        .withClient("client_lagou")
        //        //指定客户端的密码/安全码
        //        .secret("abcxyz")
        //        //指定客户端所能访问资源id清单，此处的资源id是需要在具体的资源服务器上也配置一样
        //        //认证类型/令牌办法模式，可以配置多个在这里，但是不一定都用，具体使用哪中方式办法token，
        //        // 需要客户端调用的时候传递参数指定
        //        .resourceIds("autodeliver")
        //        .authorizedGrantTypes("password","refresh_token")
        //        //客户端的权限范围
        //        .scopes("all");

    }


    /**
     * jdbc
     * @return
     */
    private JdbcClientDetailsService createJdbcClientDetailsService() {
        JdbcClientDetailsService jdbcClientDetailsService = new JdbcClientDetailsService(dataSource);
        return jdbcClientDetailsService;
    }


    /**
     * 认证服务器是玩儿转token的，那么这里配置token令牌管理相关（token此时就是一个字符串，当下的token需要在服务器端存储，
     * 那么存储在那里？都是在这里配置）
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        super.configure(endpoints);
        //指定token的存储方法
        endpoints.tokenStore(tokenStore())
                //token服务的一个描述，可以认为是token生成细节的描述，比如有效时间多少等
                .tokenServices(authorizationServerTokenServices())
                //指定认证管理器，随后注入一个到当前类使用即可
                .authenticationManager(authenticationManager)
                .allowedTokenEndpointRequestMethods(HttpMethod.GET,HttpMethod.POST);
    }

    /**
     *  该方法用户获取一个token服务对象（该对象描述了token有效期等信息）
     * @return
     */
    private AuthorizationServerTokenServices authorizationServerTokenServices() {
        //使用默认实现
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        //是否开启令牌刷新
        defaultTokenServices.setSupportRefreshToken(true);
        defaultTokenServices.setTokenStore(tokenStore());

        //针对jwt令牌的增加
        defaultTokenServices.setTokenEnhancer(jwtAccessTokenConverter());


        //设置令牌有效事件（一般设置为2个小时）
        defaultTokenServices.setAccessTokenValiditySeconds(20);
        //设置刷新令牌的有效时间 3天
        defaultTokenServices.setRefreshTokenValiditySeconds(259200);
        return defaultTokenServices;
    }

    /**
     * 该方法用于创建tokenStore对象（令牌存储对象）
     * @return
     */
    private TokenStore tokenStore() {
        //return new InMemoryTokenStore();
        return  new JwtTokenStore(jwtAccessTokenConverter());
    }

    private String sign_key="lagou123";

    /**
     * 返回jwt令牌转换器（帮助我们生成jwt令牌的）
     * 再这里，我们可以把签名密钥传递进去给转换器对象
     * @return
     */
    private JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setSigningKey(sign_key);
        jwtAccessTokenConverter.setVerifier(new MacSigner(sign_key));
        jwtAccessTokenConverter.setAccessTokenConverter(lagouAccessTokenConvertor);
        return jwtAccessTokenConverter;
    }

    public static void main(String[] args) {

    }

}
