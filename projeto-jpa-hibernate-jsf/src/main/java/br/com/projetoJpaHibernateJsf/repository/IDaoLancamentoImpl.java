package br.com.projetoJpaHibernateJsf.repository;

import java.io.Serializable;
import java.util.ArrayList;
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
		//System.out.println(numeroNota + " --- " + dataInicial + " --- " + dataFinal);
		
		List<Lancamento> lancamentos = new ArrayList<Lancamento>();
		
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT l FROM Lancamento l ");
		
		//OBS. Tem que montar os sql de acordo com as condições
		//Criando consulta por número da nota - OBS. !numeroNota.isEmpty() - diferente de vazio
		if(dataInicial == null && dataFinal == null && numeroNota != null && !numeroNota.isEmpty()) {
			
			// numeroNota é uma string, logo tem que usar uma aspa simples
			//o método trim() é para remover espaço
			sql.append("WHERE l.numeroNotaFiscal = '").append(numeroNota.trim()).append("'");
		}
		
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		
		lancamentos = entityManager.createQuery(sql.toString()).getResultList();
		
		transaction.commit();
		
		return lancamentos;
	}

}
