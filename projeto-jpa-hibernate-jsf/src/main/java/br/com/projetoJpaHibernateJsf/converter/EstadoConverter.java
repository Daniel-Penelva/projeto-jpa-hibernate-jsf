package br.com.projetoJpaHibernateJsf.converter;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.projetoJpaHibernateJsf.entidade.Estados;
import br.com.projetoJpaHibernateJsf.jpaUtil.JPAUtil;

@FacesConverter(forClass = Estados.class)
public class EstadoConverter implements Converter, Serializable{

	private static final long serialVersionUID = 1L;

	/* Retorna objeto inteiro 
	 * Aqui vai vir o código do estado da tela */
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String codigoEstado) {
		
		EntityManager entityManager = JPAUtil.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		
		Estados estados = (Estados) entityManager.find(Estados.class, Long.parseLong(codigoEstado));
		
		return estados;
	}

	
	/* Retorna apenas o código em String */
	@Override
	public String getAsString(FacesContext context, UIComponent component, Object estado) {
		
		/* Retorna apenas o id do Estado!!! 
		 * Repare que foi feito um casting para reconhecer que não é uma classe generica, e 
		 * sim uma classe Estados.
		 * */
		return ((Estados) estado).getId().toString();
	}

}
