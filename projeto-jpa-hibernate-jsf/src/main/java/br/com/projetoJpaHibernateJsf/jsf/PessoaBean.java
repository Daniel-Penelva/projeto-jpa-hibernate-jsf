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

	/* Chamando o método salvar do DaoGeneric */
	public String salvar() {
		
		daoGeneric.salvar(pessoa);
		
		/* instanciando uma pessoa para que possa salvar vários pessoas sem precisar
		 * atualizar a primeirapagina.xhtml. No caso, você irá reparar que vai limpar
		 * até os campos de texos. */
		pessoa = new Pessoa();
		
		/* Vai retornar o valor na mesma página, no caso, primeirapagina.xhtml*/
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
