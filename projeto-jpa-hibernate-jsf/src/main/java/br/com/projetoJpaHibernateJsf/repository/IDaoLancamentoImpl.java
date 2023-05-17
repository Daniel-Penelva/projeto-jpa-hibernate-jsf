package br.com.projetoJpaHibernateJsf.repository;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.projetoJpaHibernateJsf.entidade.Lancamento;

@Named
public class IDaoLancamentoImpl implements IDaoLancamento, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager entityManager;

	@Override
	public List<Lancamento> consultar(Long codUser) {
		
		List<Lancamento> lista = null;
		
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		
		lista = entityManager.createQuery("FROM Lancamento WHERE usuario.id= " + codUser).getResultList();
		
		transaction.commit();
		
		return lista;
	}
	
	// Limitando carregamento na tela 
	@SuppressWarnings("unchecked")
	@Override
	public List<Lancamento> consultarLimit10(Long codUser) {
		
		List<Lancamento> lista = null;
		
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		
		lista = entityManager.createQuery("FROM Lancamento WHERE usuario.id= " + codUser + " ORDER BY id DESC").setMaxResults(10).getResultList();
		
		transaction.commit();
		
		return lista;
	}

	@Override
	public List<Lancamento> relatorioLancamento(String numeroNota, Date dataInicial, Date dataFinal) {
		System.out.println(numeroNota + " --- " + dataInicial + " --- " + dataFinal);
		return null;
	}

}
