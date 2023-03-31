package br.com.projetoJpaHibernateJsf.jsf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.projetoJpaHibernateJsf.dao.DaoGeneric;
import br.com.projetoJpaHibernateJsf.entidade.Pessoa;

@ViewScoped
@ManagedBean(name = "pessoaBean")
public class PessoaBean implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	private Pessoa pessoa = new Pessoa();
	private DaoGeneric<Pessoa> daoGeneric = new DaoGeneric<Pessoa>();

	private List<Pessoa> pessoas = new ArrayList<Pessoa>();

	/* Chamando o método merge do DaoGeneric */
	public String salvar() {

		pessoa = daoGeneric.merge(pessoa);
		carregarPessoas();

		/* Vai retornar o valor na mesma página, no caso, primeirapagina.xhtml */
		return "";
	}

	/*
	 * Método para redirecionar na primeirapágina.xhtml e instanciar um novo usuário
	 */
	public String novo() {
		pessoa = new Pessoa();
		return "";
	}

	/* Método para remover uma pessoa por id */
	public String remove() {

		daoGeneric.deletarPorId(pessoa);
		pessoa = new Pessoa();
		carregarPessoas();

		/* Depois de deletar, vamos instanciar uma Pessoa para limpar a tela */
		pessoa = new Pessoa();

		return "";
	}

	/* Método para carregar uma lista de Pessoas */
	@PostConstruct
	public void carregarPessoas() {
		pessoas = daoGeneric.getListEntity(Pessoa.class);
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public DaoGeneric<Pessoa> getDaoGeneric() {
		return daoGeneric;
	}

	public void setDaoGeneric(DaoGeneric<Pessoa> daoGeneric) {
		this.daoGeneric = daoGeneric;
	}

	public List<Pessoa> getPessoas() {
		return pessoas;
	}

	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}

}
