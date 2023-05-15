package br.com.projetoJpaHibernateJsf.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.projetoJpaHibernateJsf.jpaUtil.JPAUtil;

@Named
public class DaoGeneric<E> implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager entityManager;
	
	@Inject
	private JPAUtil jpaUtil;

	public void salvar(E entidade) {

		/* Abrir uma transação com o BD */
		EntityTransaction entityTransaction = entityManager.getTransaction();

		/* iniciar a Transação */
		entityTransaction.begin();

		/* Vai capturar o EntityManager e salvar no BD as propriedades da entidade */
		entityManager.persist(entidade);

		/* Vai fazer um commit da transação */
		entityTransaction.commit();
	}

	public E merge(E entidade) {

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

		return retornoEntidade;
	}

	public void deletar(E entidade) {

		/* Abrir uma transação com o BD */
		EntityTransaction entityTransaction = entityManager.getTransaction();

		/* iniciar a Transação */
		entityTransaction.begin();

		/* Vai capturar o EntityManager e remover no BD as propriedades da entidade */
		entityManager.remove(entidade);

		/* Vai fazer um commit da transação */
		entityTransaction.commit();
	}

	public void deletarPorId(E entidade) {
		
		/* Abrir uma transação com o BD */
		EntityTransaction entityTransaction = entityManager.getTransaction();

		/* iniciar a Transação */
		entityTransaction.begin();

		/* Chama o método getPrimaryKey da classe JPAUtil */
		Object id = jpaUtil.getPrimaryKet(entidade);

		/*
		 * Vai capturar o EntityManager e remover no BD as propriedades da entidade.
		 * Para isso vai ser criado uma query sql. Como é um object, logo a classe é
		 * generica e faremos retornar uma pessoa.
		 */
		entityManager.createQuery("delete from " + entidade.getClass().getCanonicalName() + " where id = " + id)
				.executeUpdate();

		/* Vai fazer um commit da transação */
		entityTransaction.commit();
	}

	@SuppressWarnings("unchecked")
	public List<E> getListEntity(Class<E> entidade) {

		/* Abrir uma transação com o BD */
		EntityTransaction entityTransaction = entityManager.getTransaction();

		/* iniciar a Transação */
		entityTransaction.begin();
		
		List<E> retorno = entityManager.createQuery("from " + entidade.getName()).getResultList();
		
		/* Vai fazer um commit da transação */
		entityTransaction.commit();
		
		return retorno;
	}
	
	// Limitar carregamento na tela
	@SuppressWarnings("unchecked")
	public List<E> getListEntityLimit10(Class<E> entidade) {

		/* Abrir uma transação com o BD */
		EntityTransaction entityTransaction = entityManager.getTransaction();

		/* iniciar a Transação */
		entityTransaction.begin();
		
		List<E> retorno = entityManager.createQuery("from " + entidade.getName() + " ORDER BY id DESC").setMaxResults(10).getResultList();
		
		/* Vai fazer um commit da transação */
		entityTransaction.commit();
		
		return retorno;
	}
	
	//Consultar o objeto pessoa para que possa ser executado o download do arquivo imagem.
	public E consultar(Class<E> entidade, String primaryKey) {
		
		EntityTransaction entityTransaction = entityManager.getTransaction();

		entityTransaction.begin();
		
		// Lembrando que temos que passar o id com o tipo Long
		E objeto = (E) entityManager.find(entidade, Long.parseLong(primaryKey));
		
		entityTransaction.commit();
		
		return objeto;
	}
}
