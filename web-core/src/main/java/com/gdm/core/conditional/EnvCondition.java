/*
 * 
 *
 * 
 */
package com.gdm.core.conditional;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import com.gdm.core.constants.PROFILES;

/**
 * 依赖注入条件判断.
 * 
 * @author YHY
 * @version 2015-01-16
 * @----------------------------------------------------------------------------------------
 * @updated 修改描述.
 * @updated by YHY
 * @updated at 2015-01-16
 */
public class EnvCondition implements Condition {

	/**
	 * 根据bms|web|webapp|apis环境的不同注入不同的bean
	 * 
	 * @see org.springframework.context.annotation.Condition#matches(org.springframework.context.annotation.ConditionContext, org.springframework.core.type.AnnotatedTypeMetadata)
	 */
	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		boolean isBmsEnv = metadata.isAnnotated("com.gdm.core.annotation.BmsEnv");
		boolean isWebEnv = metadata.isAnnotated("com.gdm.core.annotation.WebEnv");
		boolean isWebappEnv = metadata.isAnnotated("com.gdm.core.annotation.WebappEnv");
		boolean isApisEnv = metadata.isAnnotated("com.gdm.core.annotation.ApisEnv");
		// 如果bean没有注解@BmsEnv或@WebEnv或@WebappEnv或@ApisEnv，返回true，表示创建Bean
		if (!isBmsEnv && !isWebEnv && !isWebappEnv && !isApisEnv) {
			return true;
		}

		// 如果profile=apis 且 bean注解了@ApisEnv，则返回true 表示创建bean
		boolean isApisProfile = context.getEnvironment().acceptsProfiles(PROFILES.APIS);
		if (isApisProfile) {
			if (isApisEnv) {
				return isApisEnv;
			}
		}

		// 如果profile=bms 且 bean注解了@BmsEnv，则返回true 表示创建bean
		boolean isBmsProfile = context.getEnvironment().acceptsProfiles(PROFILES.BMS);
		if (isBmsProfile) {
			return isBmsEnv;
		}

		// 如果profile=web 且 bean注解了@WebEnv，则返回true 表示创建bean
		boolean isWebProfile = context.getEnvironment().acceptsProfiles(PROFILES.WEB);
		if (isWebProfile) {
			return isWebEnv;
		}

		// 如果profile=webapp 且 bean注解了@WebEnv，则返回true 表示创建bean
		boolean isWebappProfile = context.getEnvironment().acceptsProfiles(PROFILES.WEBAPP);
		if (isWebappProfile) {
			return isWebappEnv;
		}

		// 否则默认返回注解了@ApisEnv或没有注解@ApisEnv的Bean
		return isApisEnv;
	}

}
