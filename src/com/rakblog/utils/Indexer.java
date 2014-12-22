package com.rakblog.utils;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.lucene.analysis.WhitespaceAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import twitter4j.HashtagEntity;
import twitter4j.Status;
import twitter4j.URLEntity;
import twitter4j.UserMentionEntity;

public class Indexer {

	private Directory directory;
	private IndexWriter writer;
	public int count = 0;
	
	public Indexer( File file ) {
		
		try {
			directory = FSDirectory.open( file );
			writer = getIndexWriter();
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void indexTweet( Status status ) {
		
		Document doc = new Document();
		
		doc.add( new Field( "indexingTime",  new Long(  new Date().getTime() ).toString(),
				Field.Store.YES,
				Field.Index.NOT_ANALYZED) );
		
		doc.add( new Field( "id", new Long( status.getId() ).toString(),
				Field.Store.YES,
				Field.Index.NOT_ANALYZED) );
		
		doc.add( new Field( "name", status.getUser().getName(),
				Field.Store.YES,
				Field.Index.NOT_ANALYZED) );
		
		doc.add( new Field( "screenName", status.getUser().getScreenName(),
				Field.Store.YES,
				Field.Index.NOT_ANALYZED) );
		
		doc.add( new Field( "text", status.getText(),
				Field.Store.YES,
				Field.Index.ANALYZED ) );
		
		doc.add( new Field( "creationTime",  new Long( status.getCreatedAt().getTime() ).toString(),
				Field.Store.YES,
				Field.Index.NOT_ANALYZED) );
		
		doc.add( new Field( "imageURL", status.getUser().getProfileImageURL().toString(),
				Field.Store.YES,
				Field.Index.NOT_ANALYZED) );
		
		if ( status.getGeoLocation() != null ) {
			doc.add( new Field( "latitude",  new Double( status.getGeoLocation().getLatitude() ).toString(),
					Field.Store.YES,
					Field.Index.NOT_ANALYZED) );
			doc.add( new Field( "longitude",  new Double( status.getGeoLocation().getLongitude() ).toString(),
					Field.Store.YES,
					Field.Index.NOT_ANALYZED) );
		}
		else {
			doc.add( new Field( "latitude",  "0",
					Field.Store.YES,
					Field.Index.NOT_ANALYZED) );
			doc.add( new Field( "longitude",  "0",
					Field.Store.YES,
					Field.Index.NOT_ANALYZED) );
		}
		
		if ( status.getHashtagEntities() != null ) {
			StringBuilder tags = new StringBuilder();
			
			for ( HashtagEntity t : status.getHashtagEntities() ) {
				tags.append( t.getText() + " " );
			}
			
			doc.add( new Field( "hashtTags",  tags.toString(),
					Field.Store.YES,
					Field.Index.NOT_ANALYZED) );
		}
		else {
			doc.add( new Field( "hashtTags",  "",
					Field.Store.YES,
					Field.Index.NOT_ANALYZED) );
		}
		
		if ( status.getPlace() != null ) {
			doc.add( new Field( "country",  status.getPlace().getCountry(),
					Field.Store.YES,
					Field.Index.NOT_ANALYZED) );
			doc.add( new Field( "placeName",  status.getPlace().getName(),
					Field.Store.YES,
					Field.Index.NOT_ANALYZED) );
		}
		else {
			
			doc.add( new Field( "country",  "",
					Field.Store.YES,
					Field.Index.NOT_ANALYZED) );
			doc.add( new Field( "placeName",  "",
					Field.Store.YES,
					Field.Index.NOT_ANALYZED) );
			
		}
		
		
		doc.add( new Field( "retweetCount",  new Long( status.getRetweetCount() ).toString(),
				Field.Store.YES,
				Field.Index.NOT_ANALYZED) );
		
		if ( status.getURLEntities() != null ) {
			StringBuilder tags = new StringBuilder();
			
			for ( URLEntity t : status.getURLEntities() ) {
				tags.append( t.getExpandedURL() + " " );
			}
			
			doc.add( new Field( "URLs",  tags.toString(),
					Field.Store.YES,
					Field.Index.NOT_ANALYZED) );
		}
		else {
			doc.add( new Field( "URLs",  "",
					Field.Store.YES,
					Field.Index.NOT_ANALYZED) );
		}
		
		if ( status.getUserMentionEntities() != null ) {
			StringBuilder tags = new StringBuilder();
			
			for ( UserMentionEntity t : status.getUserMentionEntities() ) {
				tags.append( t.getScreenName()  + " " );
			}
			
			doc.add( new Field( "mentionedUsers",  tags.toString(),
					Field.Store.YES,
					Field.Index.NOT_ANALYZED) );
		}
		else {
			doc.add( new Field( "mentionedUsers",  "",
					Field.Store.YES,
					Field.Index.NOT_ANALYZED) );
		}
		
		
		try {
			writer.addDocument(doc);
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private IndexWriter getIndexWriter() throws IOException{
		
		return new IndexWriter(directory, new WhitespaceAnalyzer(), 
				IndexWriter.MaxFieldLength.UNLIMITED);
		
	}

	public IndexWriter getWriter() {
		return writer;
	}

	public void setWriter(IndexWriter writer) {
		this.writer = writer;
	}

	
	
}
