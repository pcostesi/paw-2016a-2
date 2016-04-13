package ar.edu.itba.webapp.config;

import javax.sql.DataSource;

import org.hsqldb.jdbc.JDBCDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@EnableWebMvc
@ComponentScan({ "ar.edu.itba.webapp.controller", "ar.edu.itba.services", "ar.edu.itba.persistence"})
@Configuration

public class WebConfig extends WebMvcConfigurerAdapter {

    @Bean
    public ViewResolver viewResolver() {
        final InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/jsp/");
        viewResolver.setSuffix(".jsp");
       
        return viewResolver;
    }
    
    @Bean
    public DataSource dataSurce() {
            final SimpleDriverDataSource ds = new SimpleDriverDataSource();
            ds.setDriverClass(JDBCDriver.class);
            ds.setUrl("jdbc:hsqldb:mem:paw");
            ds.setUsername("hq");
            ds.setPassword("");
            
            return ds;
    }
	
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	registry.addResourceHandler("/**")
    		.addResourceLocations("/resources/");
    }
}
