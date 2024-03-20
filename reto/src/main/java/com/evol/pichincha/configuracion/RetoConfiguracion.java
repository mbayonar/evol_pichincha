package com.evol.pichincha.configuracion;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableTransactionManagement
@EntityScan(basePackages = {"com.evol.pichincha.entidad"})
@EnableAutoConfiguration
public class RetoConfiguracion {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private Environment environment;

    @Bean
    public LocalSessionFactoryBean sessionFactory() throws Exception {
        LocalSessionFactoryBean bean = new LocalSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setPackagesToScan("com.evol.pichincha.entidad");
        bean.setHibernateProperties(getHibernateProperties());
        return bean;
    }

    @Bean
    Properties getHibernateProperties() {
        Properties props = new Properties();
        props.setProperty("hibernate.dialect", environment.getProperty("spring.jpa.properties.hibernate.dialect"));
        props.setProperty("hibernate.show_sql", environment.getProperty("spring.jpa.properties.hibernate.show_sql"));
        props.setProperty("hibernate.format_sql", environment.getProperty("spring.jpa.properties.hibernate.format_sql"));
        props.setProperty("hibernate.id.new_generator_mappings", environment.getProperty("spring.jpa.properties.hibernate.id.new_generator_mappings"));
        return props;
    }

    @Bean
    public HibernateTransactionManager transactionManager(SessionFactory s) {
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(s);
        return txManager;
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("http://localhost:8091")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                                .maxAge(3600);
            }

        };
    }
}