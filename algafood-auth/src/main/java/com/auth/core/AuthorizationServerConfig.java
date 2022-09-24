package com.auth.core;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients
                .inMemory()
                .withClient("algafood-web")
                .secret(passwordEncoder.encode("web123"))
                .authorizedGrantTypes("password", "refresh_token")
                .scopes("write", "read")
                .accessTokenValiditySeconds(6 * 24 * 60 * 60)  //1 dia
                .refreshTokenValiditySeconds(4 * 24 * 60 * 60) //2 dia

//                .and()
//                   .withClient("foodanalytics")
//                   .secret(passwordEncoder.encode("analitics123"))
//                   .authorizedGrantTypes("authorization_code")
//                   .scopes("write", "read")
//                  .redirectUris("http://www.foodanalytics.local:6060")
//
//                .and()
//                .withClient("webadmin")
//                .authorizedGrantTypes("implicit")
//                .scopes("write", "read")
//                .redirectUris("http://aplicacao-cliente")


                .and()
                .withClient("faturamento")
                .secret(passwordEncoder.encode("faturamento123"))
                .authorizedGrantTypes("client_credentials")
                .scopes("write", "read")

                .and()
                .withClient("checktoken")
                .secret(passwordEncoder.encode("chech123"));
        // .accessTokenValiditySeconds(60 * 60 * 6)//define o tempo do token de acordo o que voce optar

    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
//        security.checkTokenAccess("isAuthenticated()");
        security.checkTokenAccess("permitAll()");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
                //.tokenStore(redisTokenStore())
                .reuseRefreshTokens(false)
                .accessTokenConverter(jwtAccessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setSigningKey("56464tqgv9hja932570dfudsfw9e888uwffhfh89wef888888f883f233ncnd3jnxviu9e9895fiiihfe9f587285rbmncvcxvlsdoihdsdsff090323r903r3r390r323893972823ncjk");

        return jwtAccessTokenConverter;
    }

    private TokenStore redisTokenStore(){
        return new RedisTokenStore(redisConnectionFactory);
    }
}

