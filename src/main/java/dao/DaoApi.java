package dao;

import org.springframework.stereotype.Component;

/** Simule un releve recu depuis l'API distante d'un compteur intelligent. */
@Component("daoApi")
public class DaoApi implements IDao {
    @Override
    public double getValue() {
        System.out.println("[RELEVE] API du compteur intelligent : 220 kWh");
        return 220.0;
    }
}
