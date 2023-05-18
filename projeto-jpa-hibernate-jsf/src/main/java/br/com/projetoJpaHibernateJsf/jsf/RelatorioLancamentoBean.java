package br.com.projetoJpaHibernateJsf.jsf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.projetoJpaHibernateJsf.dao.DaoGeneric;
import br.com.projetoJpaHibernateJsf.entidade.Lancamento;
import br.com.projetoJpaHibernateJsf.repository.IDaoLancamento;

@ViewScoped
@Named(value = "relatorioLancamentoBean")
public class RelatorioLancamentoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Date dataInicial;
	private Date dataFinal;
	private String numeroNota;

	private List<Lancamento> lancamentos = new ArrayList<Lancamento>();

	@Inject
	private IDaoLancamento daoLancamento;

	@Inject
	private DaoGeneric<Lancamento> daoGeneric;

	// Ação do botão - método que vai buscar os dados para imprimir os resultados do lançamento
	public void buscarlancamento() {
		// System.out.println("Chamou o botão buscar");
		
		//se todos os valores do inputText estiver vazio vai carregar todos os lançamentos
		if(dataInicial == null && dataFinal == null && numeroNota == null) {
			lancamentos = daoGeneric.getListEntity(Lancamento.class);
		}else { // se estiver algum valor informado
			lancamentos = daoLancamento.relatorioLancamento(numeroNota, dataInicial, dataFinal);
		}
	}

	// Métodos setters e getters
	public void setLancamentos(List<Lancamento> lancamentos) {
		this.lancamentos = lancamentos;
	}

	public List<Lancamento> getLancamentos() {
		return lancamentos;
	}

	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public String getNumeroNota() {
		return numeroNota;
	}

	public void setNumeroNota(String numeroNota) {
		this.numeroNota = numeroNota;
	}

}
