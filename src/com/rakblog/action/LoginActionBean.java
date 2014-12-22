package com.rakblog.action;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.validation.SimpleError;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidationErrors;
import net.sourceforge.stripes.validation.ValidationMethod;

import com.rakblog.model.User;

public class LoginActionBean extends BaseActionBean {
	
	@Validate(required = true)
	private String username;
	@Validate(required = true)
	private String password;
	
	@DefaultHandler
	@DontValidate
	public Resolution view() {
		
		if ( getLoggedUser() != null ) {
			return new RedirectResolution( IndexActionBean.class );
		}
		else {
			return new ForwardResolution( "/WEB-INF/jsp/login.jsp" );
		}
		
	}
	
	public Resolution go() {
		com.rakblog.model.User u = new User();
		u.setUsername(getUsername());
		u.setPassword(getPassword());
		setLoggedUser( u );
		return new RedirectResolution( IndexActionBean.class );
}
	
	@DontValidate
	public Resolution cancel() {
		return new RedirectResolution( getClass() );
	}
	
	@ValidationMethod
	public void validateUsernameAndPassword( ValidationErrors errors ) {
		if ( !getUsername().equals("rockyrock") || 
				!getPassword().equals("1111") ) {
			errors.addGlobalError( new SimpleError( "Username or Password aren't correct" ) );
		}
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
