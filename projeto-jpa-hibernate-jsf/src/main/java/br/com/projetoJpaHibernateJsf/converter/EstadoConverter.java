package br.com.projetoJpaHibernateJsf.converter;

import java.io.Serializable;

import javax.enterprise.inject.spi.CDI;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;

import br.com.projetoJpaHibernateJsf.entidade.Estados;

/* Tem que dizer qual é a classe e o valor que vai ser uma referência para a tela jsf */
@FacesConverter(forClass = Estados.class, value = "estadoConverter")
public class EstadoConverter implements Converter, Serializable{

	private static final long serialVersionUID = 1L;

	/* Retorna objeto inteiro 
	 * Aqui vai vir o código do estado da tela */
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String codigoEstado) {
		
		EntityManager entityManager = CDI.current().select(EntityManager.class).get();
		
		Estados estados = (Estados) entityManager.find(Estados.class, Long.parseLong(codigoEstado));
		
		return estados;
	}

	
	/* Retorna apenas o código em String */
	@Override
	public String getAsString(FacesContext context, UIComponent component, Object estado) {
		
		if(estado == null) {
			return null;
			
		}if(estado instanceof Estados) {
			/* Aqui pode cair uma instancia do objeto estado. Ou seja, vai testar se o objeto é uma instância 
			 * de um tipo específico de uma class, no caso, a Classe Estados. Logo vai ser comparado se 
			 * o estado é do tipo Estados. E se vier em forma de estado tem que converter ele com um casting de Estados 
			 * para pegar o id e retornar em forma de String. */
			return ((Estados) estado).getId().toString();
			
		}else {
			/* Uma string do codigo da primary key. Se vier em formato de id vai cair aqui */
			return estado.toString();
		}
		
	}

}
