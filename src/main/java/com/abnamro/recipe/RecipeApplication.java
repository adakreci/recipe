package com.abnamro.recipe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
public class RecipeApplication {

    public static void main( String[] args ) {
        SpringApplication.run( RecipeApplication.class, args );
    }

}
