package br.com.projetoJpaHibernateJsf.converter;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.projetoJpaHibernateJsf.entidade.Cidades;
import br.com.projetoJpaHibernateJsf.entidade.Estados;
import br.com.projetoJpaHibernateJsf.jpaUtil.JPAUtil;

/* Tem que dizer qual é a classe e o valor que vai ser uma referência para a tela jsf */
@FacesConverter(forClass = Cidades.class, value = "cidadeConverter")
public class CidadeConverter implements Converter, Serializable{

	private static final long serialVersionUID = 1L;

	/* Retorna objeto inteiro */
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String codigoCidade) {
		
		EntityManager entityManager = JPAUtil.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		
		Cidades cidades = (Cidades) entityManager.find(Cidades.class, Long.parseLong(codigoCidade));
		
		return cidades;
	}

	
	/* Retorna apenas o código em String */
	@Override
	public String getAsString(FacesContext context, UIComponent component, Object cidade) {
		
		if(cidade == null) {
			return null;
		}if(cidade instanceof Cidades) {
			return ((Cidades) cidade).getId().toString();
		}else {
			return cidade.toString();
		}
	}

}
