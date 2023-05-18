package br.com.projetoJpaHibernateJsf.repository;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;

import br.com.projetoJpaHibernateJsf.entidade.Estados;
import br.com.projetoJpaHibernateJsf.entidade.Lancamento;
import br.com.projetoJpaHibernateJsf.entidade.Pessoa;

@Named
public class IDaoPessoaImpl implements IDaoPessoa, Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager entityManager;
	
	@Override
	public Pessoa consultarUsuario(String login, String senha) {

		Pessoa pessoa = null;

		/* Abrir uma transação com o BD */
		EntityTransaction entityTransaction = entityManager.getTransaction();

		/* iniciar a Transação */
		entityTransaction.begin();

		try {
			pessoa = (Pessoa) entityManager.createQuery("SELECT p FROM Pessoa p WHERE p.login = '"+login+"' and p.senha = '"+senha+"'").getSingleResult();

		} catch (javax.persistence.NoResultException e) { // Tratamento se não encontrar o usuário com login e senha
			e.printStackTrace();
		}
		
		/* Vai fazer um commit da transação */
		entityTransaction.commit();

		return pessoa;
	}

	
	@Override
	public List<SelectItem> listaEstados() {
		
		/* Declarando uma lista de SelectItem */
		List<SelectItem> selectItems = new ArrayList<SelectItem>();
		
		/* Acessando a lista de estados (acessa a Classe Estados) */
		List<Estados> estados = entityManager.createQuery("from Estados").getResultList();
		
		for (Estados estado : estados) {
			
			/* No caso agora precisa passar o objeto inteiro para o 'Converter' que irá converte-lo 
			 * para conseguir enviar para a camada de modelo. */
			selectItems.add(new SelectItem(estado, estado.getNome()));
		}
				
		return selectItems;
	}


	@Override
	public List<Pessoa> relatorioPessoa(String nome, Date dataInicial, Date dataFinal) {
		
		List<Pessoa> pessoas = new ArrayList<Pessoa>();
		
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT p FROM Pessoa p ");
		
		//OBS. Tem que montar os sql de acordo com as condições
		//Criando consulta por número da nota - OBS. !nome.isEmpty() - diferente de vazio
		if(dataInicial == null && dataFinal == null && nome != null && !nome.isEmpty()) {
			
			// nome é uma string, logo tem que usar uma aspa simples
			//o método trim() é para remover espaço
			sql.append("WHERE UPPER(p.nome) LIKE '%").append(nome.trim().toUpperCase()).append("%'");
			
		  // Se existir apenas a data inicial
		}else if(nome == null || (nome != null && nome.isEmpty()) && dataInicial != null && dataFinal == null) {
			
			String dataIniString = new SimpleDateFormat("yyyy-MM-dd").format(dataInicial);
			
			sql.append("WHERE p.dataNascimento >= '").append(dataIniString).append("'");
			
		  // Se existir apenas a data final	
		} else if(nome == null || (nome != null && nome.isEmpty()) && dataInicial == null && dataFinal != null) {
			
			String dataFimString = new SimpleDateFormat("yyyy-MM-dd").format(dataFinal);
			
			sql.append("WHERE p.dataNascimento <= '").append(dataFimString).append("'");
			
		 // Se as duas datas forem informadas
		}else if(nome == null || (nome != null && nome.isEmpty()) && dataInicial != null && dataFinal != null) {
			
			String dataIniString = new SimpleDateFormat("yyyy-MM-dd").format(dataInicial);
			String dataFimString = new SimpleDateFormat("yyyy-MM-dd").format(dataFinal);
			
			sql.append("WHERE p.dataNascimento >= '").append(dataIniString).append("' ");
			sql.append(" AND p.dataNascimento <= '").append(dataFimString).append("' ");
			
		  // Se todos os campos forem informados
		} else if(nome != null && !nome.isEmpty() && dataInicial != null && dataFinal != null) {
			
			String dataIniString = new SimpleDateFormat("yyyy-MM-dd").format(dataInicial);
			String dataFimString = new SimpleDateFormat("yyyy-MM-dd").format(dataFinal);
			
			sql.append("WHERE p.dataNascimento >= '").append(dataIniString).append("' ");
			sql.append(" AND p.dataNascimento <= '").append(dataFimString).append("' ");
			sql.append(" AND UPPER(p.nome) LIKE '%").append(nome.trim().toUpperCase()).append("%'");
			
		}
		
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		
		pessoas = entityManager.createQuery(sql.toString()).getResultList();
		
		transaction.commit();
		
		return pessoas;
	}

}
