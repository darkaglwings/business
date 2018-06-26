package org.frame.business.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.frame.business.constant.IBusinessConstant;
import org.frame.business.constant.IMessageConstant;
import org.frame.business.model.system.access.Account;
import org.frame.common.constant.ISymbolConstant;
import org.springframework.web.filter.OncePerRequestFilter;


public class LoginFilter extends OncePerRequestFilter{

	private String notFilterUrls;

	public String getNotFilterUrls() {
		return notFilterUrls;
	}

	public void setNotFilterUrls(String notFilterUrls) {
		this.notFilterUrls = notFilterUrls;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
		Account account = (Account) request.getSession().getAttribute(IBusinessConstant.SYSTEM_ACCOUNT);
        String currentURI = request.getRequestURI();
        String compareUrl = currentURI.substring(currentURI.lastIndexOf("/") + 1, currentURI.length());
        
        if (account == null && (!getNotFilterUrls().contains(compareUrl) || "".equals(compareUrl))) {
        	request.setAttribute(ISymbolConstant.FLAG_WARNING, IMessageConstant.LOGIN_NO_ACCOUNT);
        	response.sendRedirect(request.getContextPath() + "/index.jsp");
        } else {
        	chain.doFilter(request, response);
        }
	}

}
