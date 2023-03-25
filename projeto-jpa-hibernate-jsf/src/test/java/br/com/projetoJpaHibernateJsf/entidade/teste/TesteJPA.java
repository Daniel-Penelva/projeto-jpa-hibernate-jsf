package br.com.projetoJpaHibernateJsf.entidade.teste;

import javax.persistence.Persistence;

public class TesteJPA {

	public static void main(String[] args) {
		
		Persistence.createEntityManagerFactory("projeto-jpa-hibernate-jsf");
	}

}
