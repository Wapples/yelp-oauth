package com.yelpoauth.app.android.controllers;

import java.util.List;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.yelpoauth.app.android.models.User;

public class UserController {
	
	
	 	public static void storeUser(String username, String password) {
				User query = new User(username, password);
				query.save();
		 }
		 
		 public static List<User> getUserList(int limit) {
		      return new Select().from(User.class).orderBy("id DESC").limit("" + limit).execute();
		 }
		 
		 public static List<User> getAllUsers(){
			 return new Select().from(User.class).orderBy("id DESC").execute();
		 }
		 
		 public static List<User> recentUsers() {
		      return new Select().from(User.class).orderBy("id DESC").limit("300").execute();
		 }
		 
		 public static User getLastStoredItem(){
			 return new Select().from(User.class).orderBy("id DESC").executeSingle();
		 }
		 
		 public static void populateUserList(int queryCount){
			 for (int i = 0; i < queryCount; i++){
				 storeUser(i + "username", i + "password");
			 }
		 }
		 
		 public static void deleteAll(){
			 new Delete().from(User.class).execute();
		 }
}

