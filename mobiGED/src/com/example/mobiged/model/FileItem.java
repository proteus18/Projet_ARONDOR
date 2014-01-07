package com.example.mobiged.model;

import java.util.Date;

public class FileItem implements Comparable<FileItem>{
	
	private String name;
	private String nameAuthor;
	private String type;
	private String path;
	private int size;
	private Date lastAccess;
	private Date lastModif;
	private Date dateCreate;
	private boolean isFolder;
	
	 
	
	/**
	 * @param name
	 * @param nameAuthor
	 * @param type
	 * @param size
	 * @param lastAccess
	 * @param lastModif
	 * @param dateCreate
	 * @param idFolder
	 * @param path 
	 */
	public FileItem(String name, String nameAuthor, String type, int size,
			Date lastAccess, Date lastModif, Date dateCreate, boolean idFolder, String path) {
		super();
		this.name = name;
		this.nameAuthor = nameAuthor;
		this.type = type;
		this.size = size;
		this.lastAccess = lastAccess;
		this.lastModif = lastModif;
		this.dateCreate = dateCreate;
		this.isFolder = idFolder;
		this.path = path;
	}
	
	public FileItem(String name, String type, boolean isFolder, String path){
		this.name = name;
		this.type = type;
		this.isFolder = isFolder;
		this.path = path;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNameAuthor() {
		return nameAuthor;
	}
	public void setNameAuthor(String nameAuthor) {
		this.nameAuthor = nameAuthor;
	}
	public String getType() {
		return type;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public Date getLastAccess() {
		return lastAccess;
	}
	public void setLastAccess(Date lastAccess) {
		this.lastAccess = lastAccess;
	}
	public Date getLastModif() {
		return lastModif;
	}
	public void setLastModif(Date lastModif) {
		this.lastModif = lastModif;
	}
	public Date getDateCreate() {
		return dateCreate;
	}
	public void setDateCreate(Date dateCreate) {
		this.dateCreate = dateCreate;
	}
	public boolean isFolder() {
		return isFolder;
	}
	public void setIsFolder(boolean idFolder) {
		this.isFolder = idFolder;
	}

	@Override
	public int compareTo(FileItem another) {
		if(this.name != null)
			return this.name.toLowerCase().compareTo(another.getName().toLowerCase());
		else
			throw new IllegalArgumentException();
	}
	
	
	
	
	

}
