package br.com.projetoJpaHibernateJsf.jsf;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
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

import com.google.gson.Gson;

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
		
		try {
			/* Capturar a URL do cep lá no servidor para fazer o consumo */
			URL url = new URL("https://viacep.com.br/ws/"+pessoa.getCep()+"/json/");
			
			/* Abrir uma conexão dessa url do cep */
			URLConnection connection = url.openConnection();
			
			/* Obter o retorno dessa url, ou seja, dos dados da url */
			InputStream is = connection.getInputStream();
			
			/* Esse retorno é jogado para dentro do Buffer que são classes para fazer leitura de fluxo de dados */
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			
			/* Jogar esse retorno para dentro de uma String */
			String cep = "";
			StringBuilder jsonCep = new StringBuilder();
			
			/*Como ele vem na forma de uma string é preciso varrer todas as linhas dela.
			 * No caso, vão vir o cep, logradouro, complemento, numero, ... */
			while((cep = br.readLine()) != null){
				jsonCep.append(cep);
			}
			
			/* Iniciou um novo objeto gson, e os valores que virão das linhas serão jogados nos atributos
			 * do cep, logradouro, complemento, bairro, localidade, numero, uf, unidade, ibge e gia. Vale 
			 * ressaltar que os nomes dos atributos precisam ser iguais aos valores das linhas. 
			 * Objetivo transformar o resultado para dentro de um objeto para auxiliar a colocar os dados em tela. */
			Pessoa gsonAux = new Gson().fromJson(jsonCep.toString(), Pessoa.class);
			
			/* Setar (atribuir) os valores que estão vindo para capturá-los e gerar na tela */
			pessoa.setCep(gsonAux.getCep());
			pessoa.setLogradouro(gsonAux.getLogradouro());
			pessoa.setComplemento(gsonAux.getComplemento());
			pessoa.setBairro(gsonAux.getBairro());
			pessoa.setLocalidade(gsonAux.getLocalidade());
			pessoa.setUf(gsonAux.getUf());
			pessoa.setUnidade(gsonAux.getUnidade());
			pessoa.setIbge(gsonAux.getIbge());
			pessoa.setGia(gsonAux.getGia());
			
			
			System.out.println(gsonAux);
			
		} catch (Exception e) {
			e.printStackTrace();
			mostrarMsg("Erro ao consultar o cep");
		}
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
