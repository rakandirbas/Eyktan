package com.rakblog.utils;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class Information {
	
	private Directory dir;
	private IndexReader reader;
	private NumberFormat formatter;
	public static final String INDEX_LOCATION = "/root/index";
	
	public Information() throws IOException {
		
		formatter = new DecimalFormat("#,####,###,###,###,###");
		
		File indexLocation = new File( INDEX_LOCATION );
		
		if ( indexLocation.exists() & indexLocation.canRead() & indexLocation.isDirectory() ) {
			dir = FSDirectory.open( indexLocation );
			System.out.println( "Opened the Index." );
		}
		else {
			System.err.println( "Index can't be opened!" );
		}
		
		reader = IndexReader.open( dir );
		System.out.println( "Opened the index reader." );
	}
	
	public int getIndexDocumentsNumber() {
		int x = getReader().numDocs();
		close();
		return x;
	}
	
	public void close() {
		try {
			getReader().close();
			getDir().close();
			System.out.println( "Closed the index reader." );
			System.out.println( "Closed the directory." );
		} catch (IOException e) {
			System.err.println(  "Can't close the index reader or the directory."  );
			e.printStackTrace();
		}
	}

	public Directory getDir() {
	
		return dir;
	}

	public IndexReader getReader() {
		return reader;
	}

}
