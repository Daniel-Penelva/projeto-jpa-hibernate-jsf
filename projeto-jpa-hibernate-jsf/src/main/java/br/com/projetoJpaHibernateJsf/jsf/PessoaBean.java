package br.com.projetoJpaHibernateJsf.jsf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import br.com.projetoJpaHibernateJsf.dao.DaoGeneric;
import br.com.projetoJpaHibernateJsf.entidade.Pessoa;
import br.com.projetoJpaHibernateJsf.repository.IDaoPessoa;
import br.com.projetoJpaHibernateJsf.repository.IDaoPessoaImpl;

@ViewScoped
@ManagedBean(name = "pessoaBean")
public class PessoaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Pessoa pessoa = new Pessoa();
	private DaoGeneric<Pessoa> daoGeneric = new DaoGeneric<Pessoa>();

	private List<Pessoa> pessoas = new ArrayList<Pessoa>();

	private IDaoPessoa iDaoPessoa = new IDaoPessoaImpl();

	/* Chamando o método merge do DaoGeneric */
	public String salvar() {

		pessoa = daoGeneric.merge(pessoa);
		carregarPessoas();
		
		mostrarMsg("Cadastrado com Sucesso!");

		/* Vai retornar o valor na mesma página, no caso, primeirapagina.xhtml */
		return "";
	}

	private void mostrarMsg(String msg) {
		FacesContext context = FacesContext.getCurrentInstance();
		
		FacesMessage message = new FacesMessage(msg);
		
		context.addMessage(null, message);
	}

	/*
	 * Método para redirecionar na primeirapágina.xhtml e instanciar um novo usuário.
	 * Executa algum processo antes do novo.
	 */
	public String novo() {
		pessoa = new Pessoa();
		return "";
	}
	
	/* Executa algum processo antes de limpar */
	public String limpar() {
		pessoa = new Pessoa();
		return "";
	}

	/* Método para remover uma pessoa por id */
	public String remove() {

		daoGeneric.deletarPorId(pessoa);
		pessoa = new Pessoa();
		carregarPessoas();
		mostrarMsg("Removido com Sucesso!");

		/* Depois de deletar, vamos instanciar uma Pessoa para limpar a tela */
		pessoa = new Pessoa();

		return "";
	}

	/* Método para carregar uma lista de Pessoas */
	@PostConstruct
	public void carregarPessoas() {
		pessoas = daoGeneric.getListEntity(Pessoa.class);
	}

	/* Método para logar o usuário */
	public String logar() {

		Pessoa pessoaUser = iDaoPessoa.consultarUsuario(pessoa.getLogin(), pessoa.getSenha());

		/* Achou o usuário */
		if (pessoaUser != null) {

			FacesContext context = FacesContext.getCurrentInstance();
			ExternalContext externalContext = context.getExternalContext();
			
			HttpServletRequest req = (HttpServletRequest) externalContext.getRequest();
			HttpSession session = req.getSession();
			
			/* Adicionar o usuário na sessão usuarioLogado (vai cair no filtro de autenticação) */
			session.setAttribute("usuarioLogado", pessoaUser);

			return "primeirapagina.jsf";
			
		}else {
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage("Usuário não encontrado!"));
		}

		/* Se não logar com sucesso vai redirecionar para a página index.jsf */
		return "index.jsf";
	}
	
	/* Mostrando e ocultando de acordo com o perfil do usuário */
	public boolean permiteAcesso(String acesso) {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		Pessoa pessoa = (Pessoa) externalContext.getSessionMap().get("usuarioLogado");
		
		return pessoa.getPerfilUser().equals(acesso);
	}
	
	/* Método de pesquisa de CEP */
	public void pesquisaCep(AjaxBehaviorEvent event) {
		
		System.out.println("Método pesquisa CEP chamado: " + pessoa.getCep());
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
