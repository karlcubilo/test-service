package com.te.testservice.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.naming.NamingException;
import javax.sql.DataSource;

@Configuration
@Profile("mock")
@EnableTransactionManagement
@EnableJpaRepositories(
        transactionManagerRef = "fakeTransactionManager",
        entityManagerFactoryRef = "fakeEntityManagerFactory",
        basePackages = "com.te.testservice.repository"
)
public class FakeDataConfig {

    public static final String DATA_SOURCE = "h2DataSource";
    private static final String[] PACKAGE_SCAN = new String[] {"com.te.testservice.repository"};

    @Bean
    public PlatformTransactionManager fakeTransactionManager() throws NamingException {
        return new JpaTransactionManager(fakeEntityManagerFactory());
    }

    @Bean(name = "fakeEntityManager")
    public EntityManager fakeEntityManager() throws NamingException {
        return fakeEntityManagerFactory().createEntityManager();
    }

    @Bean
    public EntityManagerFactory fakeEntityManagerFactory() {
        LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
        bean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        bean.setPackagesToScan(PACKAGE_SCAN);
        bean.setDataSource(dataSource());
        bean.setPersistenceUnitName(DATA_SOURCE);
        bean.afterPropertiesSet();
        return  bean.getObject();
    }

    @Bean(name ="h2DataSource")
    @Primary
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).build();
    }

    @Bean
    public DataSourceInitializer mock(DataSource h2DataSource) {

        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
        resourceDatabasePopulator.addScript(new ClassPathResource("/test-import.sql"));
        resourceDatabasePopulator.setCommentPrefix("//");

        DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
        dataSourceInitializer.setDataSource(h2DataSource);
        dataSourceInitializer.setDatabasePopulator(resourceDatabasePopulator);

        return dataSourceInitializer;
    }
}
