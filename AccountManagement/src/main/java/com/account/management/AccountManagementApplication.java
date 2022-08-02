package com.account.management;

import java.util.Arrays;
import javax.faces.webapp.FacesServlet;
import javax.servlet.ServletContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class AccountManagementApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(AccountManagementApplication.class, args);
	}
                
        @Bean
        ServletRegistrationBean jsfServletRegistration (ServletContext servletContext) {
            servletContext.setInitParameter("com.sun.faces.forceLoadConfiguration", Boolean.TRUE.toString());

            ServletRegistrationBean srb = new ServletRegistrationBean();
            srb.setServlet(new FacesServlet());
            srb.setUrlMappings(Arrays.asList("*.xhtml"));
            srb.setLoadOnStartup(1);
            return srb;
        }

}
