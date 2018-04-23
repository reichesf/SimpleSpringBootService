package com.github.reichesf.itemservice;

import io.swagger.annotations.Api;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

// Enable swagger only for when running under the "local" or
// "swagger" profile. Otherwise, not enabled.
//@Profile({
//    "local",
//    "swagger"})
@Configuration
@EnableSwagger2
class SwaggerConfiguration extends WebMvcConfigurerAdapter
{

    @Bean
    public Docket customImplementation()
    {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .build()
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false);
    }

    private ApiInfo apiInfo()
    {
        final String title = "Simple Item Service API";
        final String description = "This API accesses items";
        final String version = "1.0";
        final String termsOfServiceUrl = "";
        final Contact contact = new Contact("The A-Team", "", "");
        final String license = "";
        final String licenseUrl = "";
        final ArrayList<VendorExtension> vendorExtensions = new ArrayList<>();

        ApiInfo apiInfo = new ApiInfo(title,
                                      description,
                                      version,
                                      termsOfServiceUrl,
                                      contact,
                                      licenseUrl,
                                      license,
                                      vendorExtensions);

        return apiInfo;
    }
}
