package com.rakblog.action;

import java.io.IOException;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;

import com.rakblog.utils.Information;

public class IndexActionBean extends BaseActionBean {
	
	public static final String VIEW = "/WEB-INF/jsp/index.jsp";
	private Information info;
	private String query;
	
	@DefaultHandler
	public Resolution show() {
		
		if ( getLoggedUser() != null ) {
			try {
				info = new Information();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return new ForwardResolution(VIEW);
		}
		else {
			return new RedirectResolution( LoginActionBean.class );
		}
		
	}

	public Information getInfo() {
		return info;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
		
	}
	
}
