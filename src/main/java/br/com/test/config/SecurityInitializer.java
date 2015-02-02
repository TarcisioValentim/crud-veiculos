package br.com.test.config;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;


/**
 * @author Adriano S. Bonfiglio
 *
 */
public class SecurityInitializer extends AbstractSecurityWebApplicationInitializer {

	/**
     * 
     */
	public SecurityInitializer() {
		super(SecurityConfig.class);
	}
	
}
