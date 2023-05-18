package br.com.projetoJpaHibernateJsf.repository;

import java.io.Serializable;
import java.text.SimpleDateFormat;
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
			
		  // Se existir apenas a data inicial
		}else if(numeroNota == null || (numeroNota != null && numeroNota.isEmpty()) && dataInicial != null && dataFinal == null) {
			
			String dataIniString = new SimpleDateFormat("yyyy-MM-dd").format(dataInicial);
			
			sql.append("WHERE l.dataInicial >= '").append(dataIniString).append("'");
			
		  // Se existir apenas a data final	
		} else if(numeroNota == null || (numeroNota != null && numeroNota.isEmpty()) && dataInicial == null && dataFinal != null) {
			
			String dataFimString = new SimpleDateFormat("yyyy-MM-dd").format(dataFinal);
			
			sql.append("WHERE l.dataFinal <= '").append(dataFimString).append("'");
			
		 // Se as duas datas forem informadas
		}else if(numeroNota == null || (numeroNota != null && numeroNota.isEmpty()) && dataInicial != null && dataFinal != null) {
			
			String dataIniString = new SimpleDateFormat("yyyy-MM-dd").format(dataInicial);
			String dataFimString = new SimpleDateFormat("yyyy-MM-dd").format(dataFinal);
			
			sql.append("WHERE l.dataInicial >= '").append(dataIniString).append("' ");
			sql.append(" AND l.dataFinal <= '").append(dataFimString).append("' ");
			
		  // Se todos os campos forem informados
		} else if(numeroNota != null && !numeroNota.isEmpty() && dataInicial != null && dataFinal != null) {
			
			String dataIniString = new SimpleDateFormat("yyyy-MM-dd").format(dataInicial);
			String dataFimString = new SimpleDateFormat("yyyy-MM-dd").format(dataFinal);
			
			sql.append("WHERE l.dataInicial >= '").append(dataIniString).append("' ");
			sql.append(" AND l.dataFinal <= '").append(dataFimString).append("' ");
			sql.append(" AND l.numeroNotaFiscal = '").append(numeroNota.trim()).append("'");
			
		}
		
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		
		lancamentos = entityManager.createQuery(sql.toString()).getResultList();
		
		transaction.commit();
		
		return lancamentos;
	}

}
