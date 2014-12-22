package com.rakblog.utils;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.index.CorruptIndexException;

import twitter4j.FilterQuery;
import twitter4j.RateLimitStatusEvent;
import twitter4j.RateLimitStatusListener;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

public class IndexWorker {

	private TwitterStream twitterStream;
	private Indexer indexer;
	private boolean running;
	public String[] trackTerms;
	public boolean indexLinks;
	public int statsCounter;
	public boolean apiError;
	public boolean isIndexClosed;
	
	public void indexTweets( Status status ) {

		if ( getIndexer().count % 10 == 0 ) {
			commit();
		}

		if ( indexLinks ) {
			if ( status.getURLEntities().length > 0 ) {
				getIndexer().indexTweet(status);
				getIndexer().count++;
				statsCounter++;
			}
		} 
		else {
			getIndexer().indexTweet(status);
			getIndexer().count++;
			statsCounter++;
		}

	}

	public void startStreaming( File file ) {
		setRunning(true);
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		.setOAuthConsumerKey("")
		.setOAuthConsumerSecret("")
		.setOAuthAccessToken("-")
		.setOAuthAccessTokenSecret("");
		setTwitterStream( new TwitterStreamFactory( cb.build() ).getInstance() );
		getTwitterStream().addListener(getStatusListener());
		getTwitterStream().addRateLimitStatusListener(getLimitListener());

		indexer = new Indexer( file );
		if ( trackTerms.length > 0 ) {
			getTwitterStream().filter( new FilterQuery().track( trackTerms ) );
		} 
		else {
			getTwitterStream().sample();
		}
		isIndexClosed = false;

	}

	public void stopStreaming() {
		setRunning(false);
		System.out.println("Preparing to close the stream...");
		if ( getTwitterStream() != null ) {
			getTwitterStream().shutdown();
		}
		System.out.println("Stream was closed and commited to index.");
		try {
			System.out.println( "Preparing to stop indexing..." );
			commit();
			if ( !isIndexClosed ) {
				getIndexer().getWriter().close();
				isIndexClosed = true;
			}
			System.out.println( "The index was closed." );
		}
		catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void optimize( File file ) {
		try {
			System.out.println( "===!=====!======!====Optimizing the Index!=====!====!===!===" );
			Indexer indexer = new Indexer( file );
			indexer.getWriter().optimize();
			System.out.println( "===!=====!======!====Finished optimizing the Index!=====!====!===!===" );
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public TwitterStream getTwitterStream() {
		return twitterStream;
	}

	public void setTwitterStream(TwitterStream twitterStream) {
		this.twitterStream = twitterStream;
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean proceed) {
		this.running = proceed;
	}

	public Indexer getIndexer() {
		return indexer;
	}

	public void commit() {
		if ( getIndexer().count > 0 && getIndexer() != null ) {
			try {
				getIndexer().getWriter().commit();
			} catch (CorruptIndexException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


	public StatusListener getStatusListener() {
		StatusListener listener = new StatusListener() {
			public void onStatus(Status status) {
				indexTweets( status );

			}

			public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
			}

			public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
			}

			public void onScrubGeo(long userId, long upToStatusId) {
			}

			public void onException(Exception ex) {
				ex.printStackTrace();
			}

			@SuppressWarnings("unused")
			public void onScrubGeo(int arg0, long arg1) {

			}
		};

		return listener;
	}

	public RateLimitStatusListener getLimitListener() {
		RateLimitStatusListener listener = new RateLimitStatusListener() {

			@Override
			public void onRateLimitReached(RateLimitStatusEvent event) {
				System.err.println( event.toString() );
				apiError = true;
			}

			@Override
			public void onRateLimitStatus(RateLimitStatusEvent event) {
				System.err.println( event );
				apiError = true;
			}

		};
		return listener;
	}

	public String[] getTrackTerms() {
		return trackTerms;
	}

	public void setTrackTerms(String[] trackTerms) {
		this.trackTerms = trackTerms;
	}

}








