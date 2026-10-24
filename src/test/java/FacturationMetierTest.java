import dao.IDao;
import metier.MetierImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/** Teste le calcul metier sans demarrer Spring, grace a une source IDao controlee. */
class FacturationMetierTest {
    @Test
    @DisplayName("Une faible consommation recoit une reduction de 5 pour cent")
    void appliqueLaReductionBasseConsommation() {
        assertEquals(75.05, creerMetier(60.0).calcul(), 0.001);
    }

    @Test
    @DisplayName("Une forte consommation recoit une majoration de 8 pour cent")
    void appliqueLaMajorationForteConsommation() {
        assertEquals(815.40, creerMetier(600.0).calcul(), 0.001);
    }

    @Test
    @DisplayName("Les limites des tranches sont calculees progressivement")
    void respecteLesLimitesDesTranches() {
        assertEquals(115.0, creerMetier(100.0).calcul(), 0.001);
        assertEquals(335.0, creerMetier(300.0).calcul(), 0.001);
    }

    @Test
    @DisplayName("Les consommations invalides sont refusees")
    void refuseLesConsommationsInvalides() {
        assertThrows(IllegalArgumentException.class, () -> creerMetier(-1.0).calcul());
        assertThrows(IllegalArgumentException.class, () -> creerMetier(Double.NaN).calcul());
        assertThrows(IllegalArgumentException.class,
                () -> creerMetier(Double.POSITIVE_INFINITY).calcul());
    }

    private MetierImpl creerMetier(double consommation) {
        IDao releveControle = () -> consommation;
        return new MetierImpl(releveControle);
    }
}
