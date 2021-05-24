package loja.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

//Tem como objetivo isolar a criação do Entity Manager
public class JPAUtil {

    // Criando conexão no padrão connection factory similar ao JDBC
    // Recebe o persistenceUnitName criado no arquivo persistence.xml
    private static final EntityManagerFactory FACTORY = Persistence
            .createEntityManagerFactory("loja");

    public static EntityManager getEntityManager() {
        return FACTORY.createEntityManager();
    }
}
