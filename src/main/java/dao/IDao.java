package dao;

/**
 * Contrat commun aux sources capables de fournir un releve mensuel.
 * Le metier reste ainsi independant de l'endroit ou la consommation est lue.
 */
public interface IDao {
    /**
     * @return la consommation mensuelle du client, exprimee en kWh
     */
    double getValue();
}
