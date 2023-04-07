package br.com.projetoJpaHibernateJsf.repository;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.projetoJpaHibernateJsf.entidade.Estados;
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

	
	@Override
	public List<SelectItem> listaEstados() {
		
		/* Declarando uma lista de SelectItem */
		List<SelectItem> selectItems = new ArrayList<SelectItem>();
		
		/* Criando um entityManager */
		EntityManager entityManager = JPAUtil.getEntityManager();
		
		/* Abrir uma transação com o BD */
		EntityTransaction entityTransaction = entityManager.getTransaction();
		
		/* Acessando a lista de estados (acessa a Classe Estados) */
		List<Estados> estados = entityManager.createQuery("from Estados").getResultList();
		
		for (Estados estado : estados) {
			
			/* No selectItem (combo) vai ser passado o id do objeto e o label por parâmetro. Essa linha de código
			 * que permite capturar o valor do objeto, no caso, capturar o id do objeto, por exemplo, inspecione (F12) o 
			 * código primeirapagina.xhtml e você verá no selected na  'optionValue' que estamos capturando pelo 
			 * id (estado.getId()). Portanto, vamos recuperar este código. */
			selectItems.add(new SelectItem(estado.getId(), estado.getNome()));
		}
				
				
		return selectItems;
	}

}
