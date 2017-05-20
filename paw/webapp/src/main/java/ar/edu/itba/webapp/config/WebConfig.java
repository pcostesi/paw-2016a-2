package ar.edu.itba.webapp.config;

import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

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
import org.springframework.format.FormatterRegistry;
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
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import ar.edu.itba.models.Priority;
import ar.edu.itba.models.Score;
import ar.edu.itba.models.Status;
import ar.edu.itba.webapp.i18n.PriorityEnumFormatter;
import ar.edu.itba.webapp.i18n.ScoreEnumFormatter;
import ar.edu.itba.webapp.i18n.StatusEnumFormatter;


@EnableWebMvc
@ComponentScan({ "ar.edu.itba.webapp.config", "ar.edu.itba.webapp.config.auth", "ar.edu.itba.webapp.config.auth.hmac", "ar.edu.itba.webapp.controller", "ar.edu.itba.services", "ar.edu.itba.persistence", "ar.edu.itba.webapp.i18n" })
@Configuration
@EnableAsync
@EnableScheduling
@EnableTransactionManagement
@PropertySource(value = "file:application.properties", ignoreResourceNotFound = true)
@PropertySource(value = "classpath:application.properties", ignoreResourceNotFound = true)
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
    @Autowired
    public DataSource dataSource(final Environment env) {
        final SimpleDriverDataSource ds = new SimpleDriverDataSource();
        ds.setDriverClass(org.postgresql.Driver.class);

        ds.setUrl(env.getProperty("configuration.postgresUrl", "jdbc:postgresql://10.16.1.110/grupo2"));
        ds.setUsername(env.getProperty("configuration.postgresUser", "grupo2"));
        ds.setPassword(env.getProperty("configuration.postgresPass", "shiufi7T"));
        
        logger.info("Connecting to database at {} with user {}", ds.getUrl(), ds.getUsername());
        return ds;
    }

    @Bean
    public PlatformTransactionManager transactionManager(final EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

    @Override
    public void addFormatters(final FormatterRegistry formatterRegistry) {
        super.addFormatters(formatterRegistry);
        final MessageSource messageSource = messageSource();
        final PriorityEnumFormatter priority = new PriorityEnumFormatter(messageSource);
        final ScoreEnumFormatter score = new ScoreEnumFormatter(messageSource);
        final StatusEnumFormatter status = new StatusEnumFormatter(messageSource);
        formatterRegistry.addFormatterForFieldType(Priority.class, priority);
        formatterRegistry.addFormatterForFieldType(Score.class, score);
        formatterRegistry.addFormatterForFieldType(Status.class, status);
    }

    @Bean
    public MessageSource messageSource() {
        final ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:/i18n/messages");
        messageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());
        return messageSource;
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

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowCredentials(true)
            ;
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
