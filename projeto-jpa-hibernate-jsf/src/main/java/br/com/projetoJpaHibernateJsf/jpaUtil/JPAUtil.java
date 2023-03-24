package br.com.projetoJpaHibernateJsf.jpaUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {

	private static EntityManagerFactory factory = null;

	/* Se não existir um EntityManagerFactory vai ser criado */
	static {
		if (factory == null) {
			factory = Persistence.createEntityManagerFactory("projeto-jpa-hibernate-jsf");
		}
	}
	
	public static EntityManager getEntityManager() {
		return factory.createEntityManager();
	}

}
