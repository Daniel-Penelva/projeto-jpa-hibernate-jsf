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
import br.com.projetoJpaHibernateJsf.entidade.Pessoa;
import br.com.projetoJpaHibernateJsf.repository.IDaoLancamento;
import br.com.projetoJpaHibernateJsf.repository.IDaoPessoa;

@ViewScoped
@Named(value = "relatorioUsuarioBean")
public class RelatorioUsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Date dataInicial;
	private Date dataFinal;
	private String nome;

	private List<Pessoa> pessoas = new ArrayList<Pessoa>();

	@Inject
	private IDaoPessoa daoPessoa;
	
	@Inject
	private DaoGeneric<Pessoa> daoGeneric;

	// Ação do botão - Método que vai buscar os dados para imprimir os resultados do
	// usuário
	public void buscarUsuario() {

		// se todos os valores do inputText estiver vazio vai carregar todos os
		// lançamentos
		if (dataInicial == null && dataFinal == null && nome == null) {
			pessoas = daoGeneric.getListEntity(Pessoa.class);
		} else { // se estiver algum valor informado
			pessoas = daoPessoa.relatorioPessoa(nome, dataInicial, dataFinal);
		}
	}

	// Métodos setters e getters

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

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}

	public List<Pessoa> getPessoas() {
		return pessoas;
	}

}
