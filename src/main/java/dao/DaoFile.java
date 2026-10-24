package dao;

import org.springframework.stereotype.Component;

/** Simule l'import d'un releve transmis dans un fichier local. */
@Component("daoFile")
public class DaoFile implements IDao {
    @Override
    public double getValue() {
        System.out.println("[RELEVE] Fichier local : 180 kWh");
        return 180.0;
    }
}
