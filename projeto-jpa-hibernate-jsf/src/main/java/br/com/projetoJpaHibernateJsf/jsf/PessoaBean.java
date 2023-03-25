package br.com.projetoJpaHibernateJsf.jsf;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.projetoJpaHibernateJsf.dao.DaoGeneric;
import br.com.projetoJpaHibernateJsf.entidade.Pessoa;

@ViewScoped
@ManagedBean(name = "pessoaBean")
public class PessoaBean {

	private Pessoa pessoa = new Pessoa();
	private DaoGeneric<Pessoa> daoGeneric = new DaoGeneric<Pessoa>();

	/* Chamando o método merge do DaoGeneric */
	public String salvar() {
		
		pessoa = daoGeneric.merge(pessoa);
		
		/* Vai retornar o valor na mesma página, no caso, primeirapagina.xhtml*/
		return "";
	}
	
	/* Método para redirecionar na primeirapágina.xhtml e instanciar um novo usuário */
	public String novo() {
		pessoa = new Pessoa();
		return "";
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

}
