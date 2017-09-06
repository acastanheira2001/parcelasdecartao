package com.mycompany.parcelasdecartao.model;

import com.google.firebase.database.*;


	/**
	 * Created by Belal on 2/23/2016.
	 */
	//@JsonIgnoreProperties
	@IgnoreExtraProperties 
	 public class Person {
		
		 //name and address string 	
		private String name;
		private String address;
	//	@Exclude
	//	private String uid;

		public Person(String name, String address){
			setName(name);
			setAddress(address);
		}
	
	
		public Person() {
			/*Blank default constructor essential for Firebase*/
		}
	
		
		
		
		//Getters and setters
	/*	public String getUid(){
			return uid;
		}
		
		public void setUid(String uId) {
			this.uid = uId;
		}*/
		
		
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}
	
}
