package com.luizalabs.marketplace.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.luizalabs.marketplace.resource.ProductResource;

@Configuration
@EnableWebMvc
@ComponentScan(basePackageClasses = { ProductResource.class})
public class WebConfig {

}
