package hubble.backend.api.configurations;


import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import static springfox.documentation.builders.PathSelectors.regex;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket applicationsApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Applications")
                .globalOperationParameters(this.getParameters())
                .apiInfo(apiInfo())
                .select().apis(RequestHandlerSelectors.basePackage("hubble.backend.api.controllers"))
                .paths(regex("/applications.*"))
                .build();
    }

    @Bean
    public Docket configurationApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Configuration")
                .globalOperationParameters(this.getParameters())
                .apiInfo(apiInfo())
                .select().apis(RequestHandlerSelectors.basePackage("hubble.backend.api.controllers"))
                .paths(regex("/configuration.*"))
                .build();
    }

    @Bean
    public Docket usersApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Users")
                .globalOperationParameters(this.getParameters())
                .apiInfo(apiInfo())
                .select().apis(RequestHandlerSelectors.basePackage("hubble.backend.api.controllers"))
                .paths(regex("/users.*"))
                .build();
    }

    @Bean
    public Docket providersApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Providers")
                .globalOperationParameters(this.getParameters())
                .apiInfo(apiInfo())
                .select().apis(RequestHandlerSelectors.basePackage("hubble.backend.api.controllers"))
                .paths(regex("/providers.*"))
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Hubble REST API v0.0.2")
                .description("Cloud Integration")
                .termsOfServiceUrl("http://tsfotlatam.com")
                .contact("contact@tsoftlatam.com")
                .license("Copyright Hubble Tsoft Latam")
                .licenseUrl("http://tsoftlatam.com")
                .version("0.0.2")
                .build();
    }
    

    private List<Parameter> getParameters() {
        //Adding Header
        ParameterBuilder aParameterBuilder = new ParameterBuilder();
        aParameterBuilder.name("access-token").defaultValue("access-token").modelRef(new ModelRef("string")).parameterType("header").required(true).build();
        List<Parameter> aParameters = new ArrayList<>();
        aParameters.add(aParameterBuilder.build());
        return aParameters;
    }
}
