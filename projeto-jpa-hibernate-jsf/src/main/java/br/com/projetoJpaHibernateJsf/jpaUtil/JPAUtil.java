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
	
	/* O método getIdentifier retorna o id da Pessoa */
	public static Object getPrimaryKet(Object entidade) {
	   return factory.getPersistenceUnitUtil().getIdentifier(entidade);
	}

}
