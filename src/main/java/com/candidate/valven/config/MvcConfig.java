package com.candidate.valven.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    // This class is marked as a configuration class, indicating that it contains
    // configuration for Spring.

    public void addViewControllers(ViewControllerRegistry registry) {
        // This method is used to configure simple automated controllers that render
        // views.
        registry.addViewController("/developer").setViewName("developer");
        registry.addViewController("/").setViewName("developer");
        registry.addViewController("/commitList").setViewName("commitList");
        registry.addViewController("/commitDetails").setViewName("commitDetails");
    }
}
