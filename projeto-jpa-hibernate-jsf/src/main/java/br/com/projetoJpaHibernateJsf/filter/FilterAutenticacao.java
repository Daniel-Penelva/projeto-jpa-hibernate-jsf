package br.com.projetoJpaHibernateJsf.filter;

import java.io.IOException;
import java.io.Serializable;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import br.com.projetoJpaHibernateJsf.entidade.Pessoa;
import br.com.projetoJpaHibernateJsf.jpaUtil.JPAUtil;

@WebFilter(urlPatterns = {"/*"})
public class FilterAutenticacao implements Filter{
	
	@Inject
	private JPAUtil jpaUtil;
	
	@Override
	public void destroy() {
		
	}

	/* Utilizado em todas as requisições */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		/* Fazer a requisição */
		HttpServletRequest req = (HttpServletRequest) request;
		
		/* Abrir uma sessão única carregando o atributo do usuário logado */
		HttpSession session = req.getSession();
		
		Pessoa usuarioLogado = (Pessoa) session.getAttribute("usuarioLogado");
		
		/* getServletPath() é o endereço do sistema da url, para saber o que ele está acessando */
		String url = req.getServletPath();
		
		/* A url tem que ser diferente de index.jsf(página de login) e o usuario não está logado e irá autenticar */
		if(!url.equalsIgnoreCase("index.jsf") && usuarioLogado == null) {
			
			/* Vai redirecionar para o index.jsf */
			RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsf");
			dispatcher.forward(request, response);
			return;
			
		}else {
			/* Executa as ações do request e do response, caso esteja logado */
		chain.doFilter(request, response);
		}
	}
	
	/* Utilizado quando está subindo o servidor */
	public void init(FilterConfig arg0) throws ServletException{
		
		/* Para criar o EntityManager */
		jpaUtil.getEntityManager();
	}

}
