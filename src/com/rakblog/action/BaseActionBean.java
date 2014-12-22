package com.rakblog.action;

import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;

import com.rakblog.model.User;

public class BaseActionBean implements ActionBean {
	
	private ActionBeanContext actionBeanContext;
	
	@Override
	public ActionBeanContext getContext() {
		return this.actionBeanContext;
	}

	@Override
	public void setContext(ActionBeanContext ctx) {
		this.actionBeanContext = ctx;
	}
	
	public Object getSessionAttribute( String key ) {
		Object value = 
			getContext().getRequest().getSession().getAttribute( key );
		return value;
		
	}
	
	public void setSessionAttribute( String key, Object value ) {
		getContext().getRequest().getSession().setAttribute( key, value );
	}
	
	public User getLoggedUser() {
		return (User) getSessionAttribute( "LoggedUser" );
	}
	
	public void setLoggedUser( User user ) {
		setSessionAttribute("LoggedUser", user);
	}

}
