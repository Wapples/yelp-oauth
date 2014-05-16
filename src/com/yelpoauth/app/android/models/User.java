package com.yelpoauth.app.android.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "Users")
public class User extends Model {
	@Column(name = "Username", index = true)
    public String username;

	@Column(name = "Password")
	public String password;
	
	 public User(){
         super();
	 }
	 
	 public User(String username, String password){
         super();
         this.username = username;
         this.password = password;
         
	 }
	 
	 
}
