package config;

import dao.IDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Transforme le nom configure en source de releve principale.
 * Une cible inconnue provoque volontairement une erreur explicite au demarrage.
 */
@Configuration
public class DynamicDaoConfig {
    @Bean
    @Primary
    public IDao dynamicDao(
            ApplicationContext context,
            @Value("${DAO_TARGET:${dao.target:dao}}") String daoTarget) {
        if (!context.containsBean(daoTarget)) {
            throw new IllegalArgumentException("Cible DAO inconnue : " + daoTarget);
        }

        Object candidate = context.getBean(daoTarget);
        if (!(candidate instanceof IDao)) {
            throw new IllegalArgumentException(
                    "Le bean '" + daoTarget + "' n'implemente pas IDao");
        }
        return (IDao) candidate;
    }
}
