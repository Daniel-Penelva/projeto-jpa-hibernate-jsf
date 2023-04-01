package br.com.projetoJpaHibernateJsf.filter;

import java.io.IOException;
import java.io.Serializable;

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
public class FilterAutenticacao implements Filter, Serializable{

	
	private static final long serialVersionUID = 1L;
	
	
	private JPAUtil jpaUtil;
	
	@Override
	public void destroy() {
		
	}

	/* Utilizado em todas as requisições */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();
		
		Pessoa usuarioLogado = (Pessoa) session.getAttribute("usuarioLogado");
		
		String url = req.getServletPath();
		
		/* A url tem que ser diferente de index.jsf e o usuario não está logado e irá autenticar */
		if(!url.equalsIgnoreCase("index.jsf") && usuarioLogado == null) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsf");
			dispatcher.forward(request, response);
			return;
		}else {
			/* Executa as ações do request e do response */
		chain.doFilter(request, response);
		}
	}
	
	/* Utilizado quando está subindo o servidor */
	public void init(FilterConfig arg0) throws ServletException{
		
		/* Para criar o EntityManager */
		jpaUtil.getEntityManager();
	}
	

}
