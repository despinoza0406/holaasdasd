package hubble.backend.api.configurations;

import hubble.backend.api.interceptors.CommonAPIInterceptor;
import hubble.backend.storage.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@ComponentScan(basePackages = {"hubble.backend.business.services", "hubble.backend.providers", "hubble.backend.storage", "hubble.backend.api"})
public class BasicConfiguration extends WebMvcConfigurerAdapter {

     @Autowired
    private UsersRepository users;

    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new CommonAPIInterceptor(users));
    }
}
