package com.rakblog.utils;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.lucene.analysis.WhitespaceAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.util.Version;

import com.rakblog.model.Tweet;

public class Searcher {
	
	private Information info;
	private IndexSearcher indexSearcher;
	private QueryParser queryParser;
	private static final int NUMBER_OF_RESULTS = 10; //Represents the number of results per page needed.
	
	public Searcher() throws IOException {
		info = new Information();
		setIndexSearcher( new IndexSearcher( info.getReader() ) );
		setQueryParser( new QueryParser( Version.LUCENE_30, 
				"text", new WhitespaceAnalyzer() ) );
	}
	
	public SearchResult search( String query, int pageNumber ) 
			throws ParseException, IOException {
		
		//Watch if there's a bug when pageNumber == 0
		//SortField sortField = new SortField( "indexingTime", SortField.LONG, true );
		Query q = queryParser.parse( query );
		long oldTime = System.currentTimeMillis();
		TopDocs docs = indexSearcher.search( q, null,pageNumber * 
				NUMBER_OF_RESULTS );
		long searchTime = ( System.currentTimeMillis() - oldTime );
		return this.new SearchResult( docs, pageNumber, indexSearcher, searchTime );
		
	}
	
	public void close() throws IOException {
		getIndexSearcher().close();
		getInfo().close();
	}

	
	private void setInfo(Information info) {
		this.info = info;
	}
	private void setIndexSearcher(IndexSearcher indexSearcher) {
		this.indexSearcher = indexSearcher;
	}
	public Information getInfo() {
		return info;
	}
	public IndexSearcher getIndexSearcher() {
		return indexSearcher;
	}
	
	private void setQueryParser(QueryParser queryParser) {
		this.queryParser = queryParser;
	}

	public QueryParser getQueryParser() {
		return queryParser;
	}

	
	
	/*
	 * This class represents the whole search results that's returned to the results page.
	 * In the results page, the SearchResult object returned will be processed to 
	 * display the info it contains.
	 */
	public class SearchResult {
		private int totalHits;
		public ArrayList<Tweet> tweets;
		private int pageNumber;
		private IndexSearcher indexSearcher;
		private TopDocs docs;
		private long searchTime;
		private String formattedSearchTime;
		private final int RESULTS_NUMBER;
		
		public SearchResult( TopDocs docs, int pageNumber, 
				IndexSearcher indexSearcher, long searchTime ) throws CorruptIndexException, IOException {

			this.docs = docs;
			
			if ( NUMBER_OF_RESULTS > docs.scoreDocs.length )
				RESULTS_NUMBER = docs.scoreDocs.length;
			else
				RESULTS_NUMBER = NUMBER_OF_RESULTS;
			
			this.pageNumber = pageNumber;
			this.indexSearcher = indexSearcher;
			setTotalHits( this.docs.totalHits );
			setTweets( new ArrayList< Tweet >() );
			setSearchTime( searchTime );
			
		}
		

		public long getSearchTime() {
			return this.searchTime;
		}

		public String getFormattedSearchTime() {
			NumberFormat formatter = new DecimalFormat( "#,####,###.##" );
			return formatter.format( (double) getSearchTime()/1000 );
		}
		
		public String getFormattedTotalHits() {
			NumberFormat formatter = new DecimalFormat( "#,####,###" );
			return formatter.format( getTotalHits() );
		}

		public void setSearchTime(long searchTime) {
			this.searchTime = searchTime;
		}



		public int getTotalHits() {
			return totalHits;
		}

		public void setTotalHits(int totalHits) {
			this.totalHits = totalHits;
		}

		public ArrayList<Tweet> getTweets() {
			return tweets;
		}

		public void setTweets(ArrayList<Tweet> tweets) throws CorruptIndexException, IOException {
			this.tweets = tweets;
			int rng1 = ( pageNumber-1 ) * 10;
			int rng2 = pageNumber * 10;
			ArrayList<Document> docsArray = new ArrayList<Document>();
			
			for ( int i = rng1; i < rng2 & i < docs.scoreDocs.length; i++ ) {
				docsArray.add( indexSearcher.doc( docs.scoreDocs[ i ].doc ) );
			}
			
			for ( Document doc : docsArray ) {
				
				getTweets().add( setupTweet( doc ) );
				
			}

		}
		
		public Tweet setupTweet( Document doc ) {
			Tweet tweet = new Tweet();
			tweet.setIndexingTime( new Date( Long.parseLong( doc.get( "indexingTime" ) ) ) );
			tweet.setId( Long.parseLong( doc.get( "id" ) ) );
			tweet.setName( doc.get("name") );
			tweet.setScreenName( doc.get( "screenName" ) );
			tweet.setText( doc.get( "text" ) );
			tweet.setText( doc.get( "text" ) );
			tweet.setCreationTime( new Date( Long.parseLong( doc.get( "creationTime" ) ) ) );
			tweet.setImageURL( doc.get( "imageURL" ) );
			tweet.setLatitude( Double.parseDouble( doc.get( "latitude" ) ) );
			tweet.setLongitude( Double.parseDouble( doc.get( "longitude" ) ) );
			tweet.setHashTags( doc.get( "hashtTags" ) );
			tweet.setCountry( doc.get( "country" ) );
			tweet.setPlaceName( doc.get( "placeName" ) );
			tweet.setRetweetCount( Long.parseLong( doc.get( "retweetCount" ) ) );
			tweet.setURLs( doc.get( "URLs" ) );
			tweet.setMentionedUsers( doc.get( "mentionedUsers" ) );

			tweet.setupTime();
			
			return tweet;
		}
		
	}//End of SearchResult
	
}
