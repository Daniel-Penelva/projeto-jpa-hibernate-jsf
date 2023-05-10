package br.com.projetoJpaHibernateJsf.jpaUtil;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@ApplicationScoped
public class JPAUtil {

	private EntityManagerFactory factory = null;
	
	/* Se não existir um EntityManagerFactory vai ser criado */
	public JPAUtil() {
		if (factory == null) {
			factory = Persistence.createEntityManagerFactory("projeto-jpa-hibernate-jsf");
		}
	}

	@Produces
	@RequestScoped
	public EntityManager getEntityManager() {
		return factory.createEntityManager();
	}
	
	/* O método getIdentifier retorna o id da Pessoa */
	public Object getPrimaryKet(Object entidade) {
	   return factory.getPersistenceUnitUtil().getIdentifier(entidade);
	}

}
