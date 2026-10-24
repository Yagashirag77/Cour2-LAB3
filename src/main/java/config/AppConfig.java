package config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

/** Point central qui active les composants et charge la configuration externe. */
@Configuration
@ComponentScan(basePackages = {"dao", "metier"})
@PropertySource("classpath:app.properties")
@Import(DynamicDaoConfig.class)
public class AppConfig {
}
