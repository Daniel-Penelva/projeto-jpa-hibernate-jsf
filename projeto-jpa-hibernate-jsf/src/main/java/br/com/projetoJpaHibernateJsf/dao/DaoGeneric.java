package br.com.projetoJpaHibernateJsf.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.projetoJpaHibernateJsf.jpaUtil.JPAUtil;

public class DaoGeneric<E> {

	public void salvar(E entidade) {
		
		/* Criando um entityManager */
		EntityManager entityManager = JPAUtil.getEntityManager();
		
		/* Abrir uma transação com o BD */
		EntityTransaction entityTransaction = entityManager.getTransaction();
		
		/* iniciar a Transação */
		entityTransaction.begin();
		
		/* Vai capturar o EntityManager e salvar no BD as propriedades da entidade */
		entityManager.persist(entidade);
		
		/* Vai fazer um commit da transação */
		entityTransaction.commit();
		
		/* Fecha o entityManager */
		entityManager.close();
	}
}
