package dao;

import org.springframework.stereotype.Component;

/** Simule un releve de developpement utilise pour verifier la configuration. */
@Component("dao2")
public class DaoImpl2 implements IDao {
    @Override
    public double getValue() {
        System.out.println("[RELEVE] Base de developpement : 150 kWh");
        return 150.0;
    }
}
