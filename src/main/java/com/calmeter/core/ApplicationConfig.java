package com.calmeter.core;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.calmeter.core")
@EntityScan("com.calmeter.core")
@EnableAutoConfiguration
public class ApplicationConfig {

}
