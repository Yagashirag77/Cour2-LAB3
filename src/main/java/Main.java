import config.AppConfig;
import metier.IMetier;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/** Lance un exemple complet avec la source choisie dans app.properties. */
public class Main {
    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext context =
                     new AnnotationConfigApplicationContext(AppConfig.class)) {
            IMetier metier = context.getBean(IMetier.class);
            System.out.printf("Facture mensuelle : %.2f DH%n", metier.calcul());
        }
    }
}
