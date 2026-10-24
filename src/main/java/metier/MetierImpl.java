package metier;

import dao.IDao;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Calcule une facture progressive a partir du releve fourni par IDao.
 * Les tarifs sont appliques par tranche afin de ne pas surfacturer les premiers kWh.
 */
@Service
public class MetierImpl implements IMetier {
    private static final double ABONNEMENT = 25.0;
    private static final double LIMITE_TRANCHE_1 = 100.0;
    private static final double LIMITE_TRANCHE_2 = 300.0;
    private static final double TARIF_TRANCHE_1 = 0.90;
    private static final double TARIF_TRANCHE_2 = 1.10;
    private static final double TARIF_TRANCHE_3 = 1.40;
    private static final double LIMITE_REDUCTION = 80.0;
    private static final double TAUX_REDUCTION = 0.05;
    private static final double LIMITE_MAJORATION = 500.0;
    private static final double TAUX_MAJORATION = 0.08;

    private final IDao dao;

    public MetierImpl(IDao dao) {
        this.dao = dao;
        System.out.println("[TRACE] Source de releve injectee : "
                + dao.getClass().getSimpleName());
    }

    @Override
    public double calcul() {
        double consommation = dao.getValue();
        verifierConsommation(consommation);

        double montant = ABONNEMENT + calculerCoutProgressif(consommation);
        if (consommation <= LIMITE_REDUCTION) {
            montant *= 1.0 - TAUX_REDUCTION;
        } else if (consommation > LIMITE_MAJORATION) {
            montant *= 1.0 + TAUX_MAJORATION;
        }

        return BigDecimal.valueOf(montant)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }

    private double calculerCoutProgressif(double consommation) {
        double premiereTranche = Math.min(consommation, LIMITE_TRANCHE_1);
        double deuxiemeTranche = Math.min(
                Math.max(consommation - LIMITE_TRANCHE_1, 0.0),
                LIMITE_TRANCHE_2 - LIMITE_TRANCHE_1);
        double troisiemeTranche = Math.max(consommation - LIMITE_TRANCHE_2, 0.0);

        return premiereTranche * TARIF_TRANCHE_1
                + deuxiemeTranche * TARIF_TRANCHE_2
                + troisiemeTranche * TARIF_TRANCHE_3;
    }

    private void verifierConsommation(double consommation) {
        if (!Double.isFinite(consommation) || consommation < 0.0) {
            throw new IllegalArgumentException(
                    "La consommation doit etre un nombre positif ou nul");
        }
    }
}
