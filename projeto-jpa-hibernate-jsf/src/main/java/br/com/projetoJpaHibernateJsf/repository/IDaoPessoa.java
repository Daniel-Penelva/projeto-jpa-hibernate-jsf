package br.com.projetoJpaHibernateJsf.repository;

import br.com.projetoJpaHibernateJsf.entidade.Pessoa;

public interface IDaoPessoa{
	
	Pessoa consultarUsuario(String login, String senha);

}
