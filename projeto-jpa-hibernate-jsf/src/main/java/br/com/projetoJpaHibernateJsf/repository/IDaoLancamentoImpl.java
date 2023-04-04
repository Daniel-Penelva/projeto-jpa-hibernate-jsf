package br.com.projetoJpaHibernateJsf.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.projetoJpaHibernateJsf.entidade.Lancamento;
import br.com.projetoJpaHibernateJsf.jpaUtil.JPAUtil;

public class IDaoLancamentoImpl implements IDaoLancamento {

	@Override
	public List<Lancamento> consultar(Long codUser) {
		
		List<Lancamento> lista = null;
		
		EntityManager entityManager = JPAUtil.getEntityManager();
		
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		
		lista = entityManager.createQuery("FROM Lancamento WHERE usuario.id= " + codUser).getResultList();
		
		transaction.commit();
		entityManager.close();
		
		return lista;
	}

}
