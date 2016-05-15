package ar.edu.itba.webapp.config;

import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Properties;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@EnableWebMvc
@ComponentScan({ "ar.edu.itba.webapp.config", "ar.edu.itba.webapp.controller", "ar.edu.itba.services", "ar.edu.itba.persistence" })
@Configuration
@EnableTransactionManagement
public class WebConfig extends WebMvcConfigurerAdapter {
    private final static Logger logger = LoggerFactory.getLogger(WebConfig.class);

    @Value("classpath:schema.sql")
    private Resource schemaSql;
	
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
		ds.setDriverClass(org.postgresql.Driver.class);
        //ds.setUrl("jdbc:postgresql://10.16.1.110/grupo2");
        //ds.setUsername("grupo2");
        //ds.setPassword("shiufi7T");
        ds.setUrl("jdbc:postgresql://localhost/paw");
        ds.setUsername("test");
        ds.setPassword("test");
		return ds;
	}
	
    @Bean
    public PlatformTransactionManager transactionManager(final EntityManagerFactory emf) {
         return new JpaTransactionManager(emf);
    }

	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:/i18n/messages");
		messageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());
		return messageSource;
	}

	@Bean
	public LocaleResolver localeResolver() {
		CookieLocaleResolver resolver = new CookieLocaleResolver();
		resolver.setDefaultLocale(Locale.ENGLISH);
		resolver.setCookieName("app-locale");
		resolver.setCookieMaxAge(4800);
		return resolver;
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
	    LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
	    interceptor.setParamName("language");
	    registry.addInterceptor(interceptor);
	} 

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**").addResourceLocations("/resources/");
	}
	
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        final LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setPackagesToScan("ar.edu.itba.paw.model");
        factoryBean.setDataSource(dataSource());

        final JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        factoryBean.setJpaVendorAdapter(vendorAdapter);

        final Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "update");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL92Dialect");

        // Si ponen esto en prod, hay tabla!!!
        hibernateProperties.setProperty("hibernate.show_sql", "true");
        hibernateProperties.setProperty("format_sql", "true");

        factoryBean.setJpaProperties(properties);

        return factoryBean;
    }
}
