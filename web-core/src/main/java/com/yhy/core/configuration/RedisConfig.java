package com.yhy.core.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import com.yhy.core.constants.PROFILES;

/**
 * Redis配置.
 * 
 * @author YHY
 * @version 2016-03-10
 * @----------------------------------------------------------------------------------------
 * @updated 修改描述.
 * @updated by YHY
 * @updated at 2016-03-10
 */
@Configuration
@EnableRedisHttpSession
@Profile(value = { PROFILES.APIS, PROFILES.BMS, PROFILES.WEB, PROFILES.WEBAPP })
public class RedisConfig {

}
