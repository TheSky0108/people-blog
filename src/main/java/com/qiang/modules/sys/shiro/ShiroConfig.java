package com.qiang.modules.sys.shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.LinkedHashMap;

/**
 * @Author: qiang
 * @ProjectName: adminsystem
 * @Package: com.qiang.shiro
 * @Description: shiro配置文件
 * @Date: 2019/6/20 0020 13:24
 **/
@Configuration
public class ShiroConfig {

    @Value("${qiang.shiro.redis.cacheLive}")
    private long cacheLive;

    @Value("${qiang.shiro.redis.cachePrefix}")
    private String cachePrefix;


    /**
     * 自定义shiro cache管理
     *
     * @return
     */
    @Bean(name = "redisCacheManager")
    public RedisCacheManager redisCacheManager(@Qualifier("redisTemplate") RedisTemplate redisTemplate) {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setCacheLive(cacheLive);
        redisCacheManager.setCacheKeyPrefix(cachePrefix);
        redisCacheManager.setRedisTemplate(redisTemplate);
        return redisCacheManager;
    }

    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(@Qualifier("securityManager") SecurityManager manager) {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(manager);
        bean.setLoginUrl("login");
        bean.setSuccessUrl("/");
        bean.setUnauthorizedUrl("/");
        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/", "anon");    // authc --    认证(登录)才能使用
        filterChainDefinitionMap.put("/user", "authc");
        filterChainDefinitionMap.put("/admin", "roles[admin]"); // 角色为admin的可以访问admin页面
//        filterChainDefinitionMap.put("/edit", "perms[edit]");  // 权限为edit的可以执行edit的相关功能
        filterChainDefinitionMap.put("/druid/**", "anon");  // anon -- 匿名访问
        filterChainDefinitionMap.put("/**", "anon");
//        filterChainDefinitionMap.put("/**", "user"); // user --  验证是否登录
        bean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return bean;
    }

    @Bean("securityManager")
    public SecurityManager securityManager(@Qualifier("authRealm") AuthRealm authRealm, @Qualifier("redisCacheManager") RedisCacheManager redisCacheManager
            , @Qualifier("sessionManager") SessionManager sessionManager) {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(authRealm);
        // 自定义缓存实现 使用redis
        manager.setCacheManager(redisCacheManager);
        manager.setSessionManager(sessionManager);
        return manager;
    }

    @Bean("sessionManager")
    public SessionManager sessionManager(){
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionValidationSchedulerEnabled(true);
        sessionManager.setSessionIdCookieEnabled(true);
        return sessionManager;
    }

    @Bean("authRealm")
    public AuthRealm authRealm(@Qualifier("hashedCredentialsMatcher") HashedCredentialsMatcher matcher) {
        AuthRealm authRealm = new AuthRealm();
        authRealm.setCachingEnabled(true);
        authRealm.setCredentialsMatcher(matcher);
        return authRealm;
    }

    /**
     * Shiro控制ThymeLeaf界面按钮级权限控制
     * @return
     */
    @Bean(name = "shiroDialect")
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }



        /**
         * 密码校验规则HashedCredentialsMatcher
         * 这个类是为了对密码进行编码的 ,
         * 防止密码在数据库里明码保存 , 当然在登陆认证的时候 ,
         * 这个类也负责对form里输入的密码进行编码
         * 处理认证匹配处理器：如果自定义需要实现继承HashedCredentialsMatcher
         */
    @Bean("hashedCredentialsMatcher")
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        //指定加密方式为MD5
        credentialsMatcher.setHashAlgorithmName("MD5");
        //加密次数
        credentialsMatcher.setHashIterations(1024);
        credentialsMatcher.setStoredCredentialsHexEncoded(true);
        return credentialsMatcher;
    }


    /**
     * 配置shiro跟spring的关联
     *
     * @param
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager") SecurityManager manager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(manager);
        return advisor;
    }


    /**
     * Spring的一个bean , 由Advisor决定对哪些类的方法进行AOP代理 .
     *
     * @return
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }

}
