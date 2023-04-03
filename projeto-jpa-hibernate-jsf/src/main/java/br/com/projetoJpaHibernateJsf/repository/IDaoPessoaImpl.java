package br.com.projetoJpaHibernateJsf.repository;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.projetoJpaHibernateJsf.entidade.Pessoa;
import br.com.projetoJpaHibernateJsf.jpaUtil.JPAUtil;

public class IDaoPessoaImpl implements IDaoPessoa{

	@Override
	public Pessoa consultarUsuario(String login, String senha) {

		Pessoa pessoa = null;

		/* Criando um entityManager */
		EntityManager entityManager = JPAUtil.getEntityManager();

		/* Abrir uma transação com o BD */
		EntityTransaction entityTransaction = entityManager.getTransaction();

		/* iniciar a Transação */
		entityTransaction.begin();

		pessoa = (Pessoa) entityManager.createQuery("SELECT p FROM Pessoa p WHERE p.login = '"+login+"' and p.senha = '"+senha+"'").getSingleResult();

		/* Vai fazer um commit da transação */
		entityTransaction.commit();

		/* Fecha o entityManager */
		entityManager.close();

		return pessoa;
	}

}
