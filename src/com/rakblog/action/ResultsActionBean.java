package com.rakblog.action;

import java.io.IOException;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;

import org.apache.lucene.queryParser.ParseException;

import com.rakblog.utils.Searcher;

public class ResultsActionBean extends BaseActionBean {
	
	private final String VIEW = "/WEB-INF/jsp/results.jsp";
	private String query;
	private Searcher searcher;
	private Searcher.SearchResult searchResult;
	private int pageNumber = 1;
	
	@DefaultHandler
	public Resolution showResults() {
		try {
			searcher = new Searcher();
			
			if ( getQuery() == null )
				setQuery( "" );
			//System.out.println( "HAHHAHA********************#" + getQuery() );
			searchResult = searcher.search( getQuery(), getPageNumber() );
			searcher.close();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ForwardResolution( VIEW );
	}
	
	public Resolution search() {
		
		return new RedirectResolution( ResultsActionBean.class ).addParameter("query", getQuery());
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		
		this.query = query;
	}
	
	public Searcher.SearchResult getSearchResult() {
		return searchResult;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber > 0 ? pageNumber : 1;
	}
	
	public int getPageRange1() {
		int range = 0;
		
		if ( getPagesNumber() == -1 ) 
			return 0;
		
		return range;
	}
	


}
