package br.com.projetoJpaHibernateJsf.repository;

import java.util.List;

import br.com.projetoJpaHibernateJsf.entidade.Lancamento;

public interface IDaoLancamento {
	
	/* Método que vai receber o código do usuário que está logado */
	List<Lancamento> consultar(Long codUser);

}
