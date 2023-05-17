package br.com.projetoJpaHibernateJsf.repository;

import java.util.Date;
import java.util.List;

import br.com.projetoJpaHibernateJsf.entidade.Lancamento;

public interface IDaoLancamento {
	
	/* Método que vai receber o código do usuário que está logado */
	List<Lancamento> consultar(Long codUser);

	List<Lancamento> consultarLimit10(Long codUser);
	
	List<Lancamento> relatorioLancamento(String numeroNota, Date dataInicial, Date dataFinal);

}
