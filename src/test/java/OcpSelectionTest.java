import config.AppConfig;
import dao.DaoApi;
import dao.DaoFile;
import dao.DaoImpl;
import metier.IMetier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.core.env.MapPropertySource;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Verifie la selection Spring et les valeurs de releve demandees. */
class OcpSelectionTest {
    @Test
    @DisplayName("Chaque source selectionnee produit sa facture attendue")
    void selectionneChaqueDaoParProprieteExterne() {
        verifierFacture("dao", 115.0);
        verifierFacture("dao2", 170.0);
        verifierFacture("daoFile", 203.0);
        verifierFacture("daoApi", 247.0);
    }

    @Test
    @DisplayName("Les releves simules sont stables, hors source de developpement dao2")
    void verifieLesValeursDesDaoDemandes() {
        assertAll(
                () -> assertEquals(100.0, new DaoImpl().getValue()),
                () -> assertEquals(180.0, new DaoFile().getValue()),
                () -> assertEquals(220.0, new DaoApi().getValue()));
    }

    @Test
    @DisplayName("Une cible DAO inconnue bloque le demarrage avec un message clair")
    void refuseUneCibleDaoInconnue() {
        try (AnnotationConfigApplicationContext context = creerContexte("daoInconnu")) {
            BeanCreationException erreur = assertThrows(
                    BeanCreationException.class, context::refresh);

            Throwable cause = NestedExceptionUtils.getMostSpecificCause(erreur);
            assertTrue(cause.getMessage().contains("Cible DAO inconnue : daoInconnu"));
        }
    }

    private void verifierFacture(String cible, double montantAttendu) {
        try (AnnotationConfigApplicationContext context = creerContexte(cible)) {
            context.refresh();
            IMetier metier = context.getBean(IMetier.class);
            assertEquals(montantAttendu, metier.calcul(), 0.001);
        }
    }

    private AnnotationConfigApplicationContext creerContexte(String cible) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext();
        context.getEnvironment().getPropertySources().addFirst(
                new MapPropertySource(
                        "test",
                        Collections.singletonMap("DAO_TARGET", cible)));
        context.register(AppConfig.class);
        return context;
    }
}
