package com.yelpoauth.app.android.models;

import java.util.List;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

@Table(name = "Queries")
public class Query extends Model {
	@Column(name = "Query")
	String query;

	public Query(){
		super();
	}
	
	public Query(String queryString){
		super();
		this.query = queryString;
	}
	 
	 public String getQuery(){
		 return query;
	 }
	 	 
}
