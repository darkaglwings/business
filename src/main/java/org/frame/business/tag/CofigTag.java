package org.frame.business.tag;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

import org.frame.business.system.Config;
import org.frame.common.constant.ISymbolConstant;

public class CofigTag extends org.frame.web.tag.CofigTag {

	private static final long serialVersionUID = 7383509425320223502L;
	
	@Override
	public int doStartTag() throws JspException {
		Config config = new Config(this.getSource());
		
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		request.setAttribute(this.getInfo(), config.get(this.getKey()));
		
		return SKIP_BODY;
	}
	
	@Override
	public int doEndTag() throws JspException {
		if (ISymbolConstant.FLAG_TRUE.equals(this.getScript())) {
			Config config = new Config(this.getSource());
			
			StringBuffer sbufBody = new StringBuffer();
			sbufBody.append("<script type=\"text/javascript\">\n");
			sbufBody.append("var ").append(this.getInfo()).append(" = '").append(config.get(this.getKey())).append("';\n");
			sbufBody.append("</script>\n");

			try {
				pageContext.getOut().print(sbufBody.toString());
			} catch (IOException e) {
				throw new JspTagException("ConfigTag: " + e.getMessage());
			}
		}

		return EVAL_PAGE;
	}

}
