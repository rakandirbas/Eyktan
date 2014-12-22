package com.rakblog.model;

import java.util.Date;

public class Tweet {
	
	private long id;
	public String name;
	public String screenName;
	public String text;
	private Date creationTime;
	private String imageURL;
	private double latitude;
	private double longitude;
	private String hashTags;
	private String country;
	private String placeName;
	private long retweetCount;
	private String URLs;
	private String mentionedUsers;
	private long elapsedTime;
	private String elapsedTimeType;
	private Date indexingTime;
	
	public Tweet() {
		
	}
	
	
	
	public long getRetweetCount() {
		return retweetCount;
	}



	public void setRetweetCount(long retweetCount) {
		this.retweetCount = retweetCount;
	}



	public String getMentionedUsers() {
		return mentionedUsers;
	}



	public void setMentionedUsers(String mentionedUsers) {
		this.mentionedUsers = mentionedUsers;
	}



	public Date getIndexingTime() {
		return indexingTime;
	}



	public void setIndexingTime(Date indexingTime) {
		this.indexingTime = indexingTime;
	}



	public void setupTime() {
		long past = getCreationTime().getTime();
		long now = new Date().getTime();
		long diff = now - past;
		long seconds;
		long minutes;
		long hours;
		long days;
		long weeks;
		long months;
		long years;
		
		seconds = diff / 1000;
		minutes = seconds / 60;
		hours = minutes / 60;
		days = hours / 24;
		weeks = days / 7;
		months = weeks / 4;
		years = months / 12;
		
		switch( determineType( diff ) ) {
			case SECONDS:
				setElapsedTimeType( "Seconds" );
				setElapsedTime( seconds );
				break;
				
			case MINUTES:
				setElapsedTimeType( "Minutes" );
				setElapsedTime( minutes );
				break;
				
			case HOURS:
				setElapsedTimeType( "Hours" );
				setElapsedTime( hours );
				break;
				
			case DAYS:
				setElapsedTimeType( "Days" );
				setElapsedTime( days );
				break;
				
			case WEEKS:
				setElapsedTimeType( "Weeks" );
				setElapsedTime( weeks );
				break;
				
			case MONTHS:
				setElapsedTimeType( "Months" );
				setElapsedTime( months );
				break;
				
			case YEARS:
				setElapsedTimeType( "Years" );
				setElapsedTime( years );
				break;
		}
		
	}
	
	private enum DateType { SECONDS, MINUTES, HOURS, DAYS, WEEKS, MONTHS, YEARS };
	
	public DateType determineType( long x ) {
		long seconds;
		long minutes;
		long hours;
		long days;
		long weeks;
		long months;
		long years;
		
		seconds = x / 1000;
		minutes = seconds / 60;
		hours = minutes / 60;
		days = hours / 24;
		weeks = days / 7;
		months = weeks / 4;
		years = months / 12;
		
		if ( years != 0 )
			return DateType.YEARS;
		else if ( months != 0 )
			return DateType.MONTHS;
		else if ( weeks != 0 )
			return DateType.WEEKS;
		else if ( days != 0 )
			return DateType.DAYS;
		else if ( hours != 0 )
			return DateType.HOURS;
		else if ( minutes != 0 )
			return DateType.MINUTES;
		else 
			return DateType.SECONDS;
		
	}
	
	public long getElapsedTime() {
		return elapsedTime;
	}



	public void setElapsedTime(long elapsedTime) {
		this.elapsedTime = elapsedTime;
	}



	public String getElapsedTimeType() {
		return elapsedTimeType;
	}



	public void setElapsedTimeType(String elapsedTimeType) {
		this.elapsedTimeType = elapsedTimeType;
	}



	public String getImageURL() {
		return imageURL;
	}



	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}



	public Date getCreationTime() {
		return creationTime;
	}



	public void setCreationTime(Date dateOfCreation) {
		this.creationTime = dateOfCreation;
	}



	public double getLatitude() {
		return latitude;
	}



	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}



	public double getLongitude() {
		return longitude;
	}



	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}



	public String getHashTags() {
		return hashTags;
	}



	public void setHashTags(String hashTags) {
		this.hashTags = hashTags;
	}



	public String getCountry() {
		return country;
	}



	public void setCountry(String country) {
		this.country = country;
	}



	public String getPlaceName() {
		return placeName;
	}



	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}



	public String getURLs() {
		return URLs;
	}



	public void setURLs(String uRLs) {
		URLs = uRLs;
	}



	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getScreenName() {
		return screenName;
	}
	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	

}
