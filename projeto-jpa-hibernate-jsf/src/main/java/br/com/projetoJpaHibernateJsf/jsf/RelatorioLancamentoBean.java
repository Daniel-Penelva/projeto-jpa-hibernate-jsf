package br.com.projetoJpaHibernateJsf.jsf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.projetoJpaHibernateJsf.entidade.Lancamento;
import br.com.projetoJpaHibernateJsf.repository.IDaoLancamento;

@ViewScoped
@Named(value = "relatorioLancamentoBean")
public class RelatorioLancamentoBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private List<Lancamento> lancamentos = new ArrayList<Lancamento>();
	
	@Inject
	private IDaoLancamento daoLancamento;
	
	
	// Métodos setters e getters
	public void setLancamentos(List<Lancamento> lancamentos) {
		this.lancamentos = lancamentos;
	}
	
	public List<Lancamento> getLancamentos() {
		return lancamentos;
	}

}
