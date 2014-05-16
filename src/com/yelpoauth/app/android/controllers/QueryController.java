package com.yelpoauth.app.android.controllers;

import java.util.List;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.yelpoauth.app.android.models.Query;
import com.yelpoauth.app.android.models.User;

public class QueryController {
	
	
	 	public static void storeQuery(String queryString) {
	 		Query query = new Query(queryString);
			query.save();
		 }
		 
		 public static List<Query> getQueryList(int limit) {
		      return new Select().from(Query.class).orderBy("id DESC").limit("" + limit).execute();
		 }
		 
		 public static List<Query> getAllQueries(){
			 return new Select().from(Query.class).orderBy("id DESC").execute();
		 }
		 
		 public static List<Query> recentUsers() {
		      return new Select().from(Query.class).orderBy("id DESC").limit("300").execute();
		 }
		 
		 public static Query getLastStoredItem(){
			 return new Select().from(Query.class).orderBy("id DESC").executeSingle();
		 }
		 
		 public static void populateQueryList(int queryCount){
			 for (int i = 0; i < queryCount; i++){
				 storeQuery(i + "query");
			 }
		 }
		 
		 public static void deleteAll(){
			 new Delete().from(Query.class).execute();
		 }
}

