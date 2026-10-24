package metier;

/** Service metier charge de produire le montant de la facture mensuelle. */
public interface IMetier {
    /**
     * @return le montant TTC de la facture en dirhams
     */
    double calcul();
}
