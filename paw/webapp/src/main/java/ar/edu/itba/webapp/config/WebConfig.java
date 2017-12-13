package ar.edu.itba.webapp.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
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

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Properties;


@EnableWebMvc
@ComponentScan( {"ar.edu.itba.webapp.config", "ar.edu.itba.webapp.config.auth", "ar.edu.itba.webapp.config.auth.hmac", "ar.edu.itba.webapp.controller", "ar.edu.itba.services", "ar.edu.itba.persistence"})
@Configuration
@EnableAsync
@EnableScheduling
@EnableTransactionManagement
@PropertySource(value = "file:application.properties", ignoreResourceNotFound = true)
@PropertySource(value = "file:${user.home}/.scrumlr.properties", ignoreResourceNotFound = true)
@PropertySource(value = "file:/scrumlr.properties", ignoreResourceNotFound = true)
@PropertySource(value = "classpath:application.properties", ignoreResourceNotFound = true)
public class WebConfig extends WebMvcConfigurerAdapter {
    private final static Logger logger = LoggerFactory.getLogger(WebConfig.class);

    @Value("classpath:schema.sql")
    private Resource schemaSql;

    @Bean
    @Autowired
    public DataSource dataSource(final Environment env) {
        final SimpleDriverDataSource ds = new SimpleDriverDataSource();
        ds.setDriverClass(org.postgresql.Driver.class);
        String dbUrl = System.getenv("DB_URL");
        String dbUser = System.getenv("DB_USER");
        String dbPass = System.getenv("DB_PASS");

        ds.setUrl(env.getProperty("configuration.postgresUrl", dbUrl != null ? dbUrl : "jdbc:postgresql://10.16.1.110/grupo2"));
        ds.setUsername(env.getProperty("configuration.postgresUser",  dbUser != null ? dbUser : "grupo2"));
        ds.setPassword(env.getProperty("configuration.postgresPass",  dbPass != null ? dbPass : "shiufi7T"));

        logger.info("Connecting to database at {} with user {}", ds.getUrl(), ds.getUsername());
        return ds;
    }

    @Bean
    public PlatformTransactionManager transactionManager(final EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

    @Bean
    public LocaleResolver localeResolver() {
        final CookieLocaleResolver resolver = new CookieLocaleResolver();
        resolver.setDefaultLocale(Locale.ENGLISH);
        resolver.setCookieName("app-locale");
        resolver.setCookieMaxAge(4800);
        return resolver;
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        final LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName("language");
        registry.addInterceptor(interceptor);
    }

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("/scrumlr/dist/");
    }

    @Bean
    @Autowired
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(final Environment env) {
        final LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setPackagesToScan("ar.edu.itba.models");
        factoryBean.setDataSource(dataSource(env));

        final JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        factoryBean.setJpaVendorAdapter(vendorAdapter);

        final Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "update");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL92Dialect");

        properties.setProperty("hibernate.show_sql", env.getProperty("configuration.showSql", "false"));
        properties.setProperty("format_sql", env.getProperty("configuration.showSql", "false"));

        factoryBean.setJpaProperties(properties);

        return factoryBean;
    }
}
