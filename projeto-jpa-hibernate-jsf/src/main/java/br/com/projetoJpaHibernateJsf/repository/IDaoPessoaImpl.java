package br.com.projetoJpaHibernateJsf.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;

import br.com.projetoJpaHibernateJsf.entidade.Estados;
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

}
