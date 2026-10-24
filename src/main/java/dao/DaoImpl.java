package dao;

import org.springframework.stereotype.Component;

/** Simule le releve valide dans la base de production du fournisseur. */
@Component("dao")
public class DaoImpl implements IDao {
    @Override
    public double getValue() {
        System.out.println("[RELEVE] Base de production : 100 kWh");
        return 100.0;
    }
}
