package br.com.projetoJpaHibernateJsf.dao;

import java.util.List;

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

	public E merge(E entidade) {

		/* Criando um entityManager */
		EntityManager entityManager = JPAUtil.getEntityManager();

		/* Abrir uma transação com o BD */
		EntityTransaction entityTransaction = entityManager.getTransaction();

		/* iniciar a Transação */
		entityTransaction.begin();

		/*
		 * Vai capturar o EntityManager e salvar/atualizar no BD as propriedades da
		 * entidade
		 */
		E retornoEntidade = entityManager.merge(entidade);

		/* Vai fazer um commit da transação */
		entityTransaction.commit();

		/* Fecha o entityManager */
		entityManager.close();

		return retornoEntidade;
	}

	public void deletar(E entidade) {

		/* Criando um entityManager */
		EntityManager entityManager = JPAUtil.getEntityManager();

		/* Abrir uma transação com o BD */
		EntityTransaction entityTransaction = entityManager.getTransaction();

		/* iniciar a Transação */
		entityTransaction.begin();

		/* Vai capturar o EntityManager e remover no BD as propriedades da entidade */
		entityManager.remove(entidade);

		/* Vai fazer um commit da transação */
		entityTransaction.commit();

		/* Fecha o entityManager */
		entityManager.close();
	}

	public void deletarPorId(E entidade) {

		/* Criando um entityManager */
		EntityManager entityManager = JPAUtil.getEntityManager();

		/* Abrir uma transação com o BD */
		EntityTransaction entityTransaction = entityManager.getTransaction();

		/* iniciar a Transação */
		entityTransaction.begin();

		/* Chama o método getPrimaryKey da classe JPAUtil */
		Object id = JPAUtil.getPrimaryKet(entidade);

		/*
		 * Vai capturar o EntityManager e remover no BD as propriedades da entidade.
		 * Para isso vai ser criado uma query sql. Como é um object, logo a classe é
		 * generica e faremos retornar uma pessoa.
		 */
		entityManager.createQuery("delete from " + entidade.getClass().getCanonicalName() + " where id = " + id)
				.executeUpdate();

		/* Vai fazer um commit da transação */
		entityTransaction.commit();

		/* Fecha o entityManager */
		entityManager.close();
	}

	@SuppressWarnings("unchecked")
	public List<E> getListEntity(Class<E> entidade) {
		/* Criando um entityManager */
		EntityManager entityManager = JPAUtil.getEntityManager();

		/* Abrir uma transação com o BD */
		EntityTransaction entityTransaction = entityManager.getTransaction();

		/* iniciar a Transação */
		entityTransaction.begin();
		
	
		List<E> retorno = entityManager.createQuery("from " + entidade.getName()).getResultList();
		
		/* Vai fazer um commit da transação */
		entityTransaction.commit();

		/* Fecha o entityManager */
		entityManager.close();
		
		return retorno;
	}
}
