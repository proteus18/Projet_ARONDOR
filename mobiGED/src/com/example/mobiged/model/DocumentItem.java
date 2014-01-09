package com.example.mobiged.model;

import java.util.Date;

public class DocumentItem {
	
	private int id;
	private String titre;
	private String author;
	private Date date;
	private String type;
	
	
	public DocumentItem(){
		this.id = 1;
		this.author = "tot";
		this.titre = "toto";
		this.type = "word";
		this.date = new Date();
	}
	
	public DocumentItem(int id, String titre, String author, Date date, String type) {		
		this.id = id;
		this.titre = titre;
		this.author = author;
		this.date = date;
		this.type = type;
	}
	
	
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitre() {
		return titre;
	}
	public void setTitre(String titre) {
		this.titre = titre;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String description) {
		this.author = description;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	

}
