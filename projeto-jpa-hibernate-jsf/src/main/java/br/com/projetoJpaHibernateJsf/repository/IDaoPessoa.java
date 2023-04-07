package br.com.projetoJpaHibernateJsf.repository;

import java.util.List;

import javax.faces.model.SelectItem;

import br.com.projetoJpaHibernateJsf.entidade.Pessoa;

public interface IDaoPessoa{
	
	Pessoa consultarUsuario(String login, String senha);
	
	/*Vamos usar um combo na tela, logo vamos usar a Classe SelectItem */
	List<SelectItem> listaEstados();

}
