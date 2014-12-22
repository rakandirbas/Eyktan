package com.rakblog.action;

import java.io.File;

import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;

import com.rakblog.utils.IndexWorker;
import com.rakblog.utils.Information;

public class AdminActionBean extends BaseActionBean {
	
	private static final IndexWorker worker = getWorker();
	private static boolean isWorking = false;
	private static String terms;
	@DefaultHandler
	public Resolution show() {
		
		if ( getLoggedUser() != null ) {
			return new ForwardResolution("/WEB-INF/jsp/admin.jsp");
		}
		else {
			return new RedirectResolution( LoginActionBean.class );
		}
		
	}
	
	public Resolution startIndexing() {
		if ( terms == null ) 
			worker.trackTerms = new String[0];
		else
			worker.trackTerms = terms.split( "," );
		
		worker.startStreaming( new File(Information.INDEX_LOCATION) );
		isWorking = true;
		return new RedirectResolution(getClass());
	}
	
	public Resolution stopIndexing() {
		worker.stopStreaming();
		isWorking = false;
		return new RedirectResolution(getClass());
	}
	
	public Resolution optimize() {
		worker.optimize( new File(Information.INDEX_LOCATION) );
		return new RedirectResolution(getClass());
	}
	
	public boolean getIsWorking() {
		return isWorking;
	}
	public boolean setIsWorking() {
		return isWorking;
	}
	public static IndexWorker getWorker() {
		return new IndexWorker();
	}

	public String getTerms() {
		return terms;
	}

	public void setTerms(String terms) {
		this.terms = terms;
	}
	
	
}
