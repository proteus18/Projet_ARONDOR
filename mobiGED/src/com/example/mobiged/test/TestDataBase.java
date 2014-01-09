package com.example.mobiged.test;

import java.util.ArrayList;
import java.util.Date;

import com.example.mobiged.model.DocumentItem;

public class TestDataBase {

	private ArrayList<DocumentItem> docItems;

	public TestDataBase() {

		
		docItems = new ArrayList<DocumentItem>(); 
		
		this.docItems.add(new DocumentItem(1,"Anaconda, le prédateur","Luis Llosa",new Date(),"word"));
		this.docItems.add(new DocumentItem(2,"Anacondas : À la poursuite de l'orchidée de sang","Dwight H. Little",new Date(),"rar"));
		this.docItems.add(new DocumentItem(3,"Anaconda 3: l'héritier","Don E. FauntLeRoy",new Date(),"word"));
		this.docItems.add(new DocumentItem(4,"Anacondas 4 : Sur la piste de sang","Don E. FauntLeRoy",new Date(),"pdf"));
		this.docItems.add(new DocumentItem(5,"Constrictor","Chaninton Muangsuwan",new Date(),"rar"));
		this.docItems.add(new DocumentItem(6,"Boa vs. Python","David Flores",new Date(),"rar"));
		this.docItems.add(new DocumentItem(7,"Calamity of Snakes","William Chang",new Date(),"pdf"));
		this.docItems.add(new DocumentItem(8,"Cobra Girl","Sun Chung",new Date(),"rar"));
		this.docItems.add(new DocumentItem(9,"Copperhead","Leland Payton",new Date(),"ppt"));
		this.docItems.add(new DocumentItem(10,"Copperhead","Todor Chapkanov",new Date(),"rar"));
		this.docItems.add(new DocumentItem(11,"Des serpents dans l'avion","David R. Ellis",new Date(),"ppt"));
		this.docItems.add(new DocumentItem(12,"Face aux serpents","Fred Olen Ray",new Date(),"ppt"));
		this.docItems.add(new DocumentItem(13,"Fair Game","Mario Orfini",new Date(),"pdf"));
		this.docItems.add(new DocumentItem(14,"Horrible carnage","Brice Mack",new Date(),"pdf"));
		this.docItems.add(new DocumentItem(15,"Jaws of Satan","Bob Claver",new Date(),"pdf"));
		this.docItems.add(new DocumentItem(16,"The Killer Snakes","Kwei Chih-Hung",new Date(),"img"));
		this.docItems.add(new DocumentItem(17,"King Cobra","David et Scott Hillenbrand",new Date(),"img"));
		this.docItems.add(new DocumentItem(18,"Komodo vs Cobra","Jim Wynorski",new Date(),"img"));
		this.docItems.add(new DocumentItem(19,"Mega Snake","Tibor Takács",new Date(),"img"));
		this.docItems.add(new DocumentItem(20,"Morsures","Tony Randel",new Date(),"img"));
		this.docItems.add(new DocumentItem(21,"Morsures mortelles","Noel Nosseck",new Date(),"excel"));
		this.docItems.add(new DocumentItem(22,"New Alcatraz","Phillip J. Roth",new Date(),"excel"));
		this.docItems.add(new DocumentItem(23,"Opération serpent","Russ Mayberry",new Date(),"excel"));
		this.docItems.add(new DocumentItem(24,"Python","Richard Clabaugh",new Date(),"word"));
		this.docItems.add(new DocumentItem(25,"Python 2 : Le parfait prédateur","L.A. McConnell",new Date(),"img"));
		this.docItems.add(new DocumentItem(26,"Les Serpents attaquent","John McCauley",new Date(),"word"));
		this.docItems.add(new DocumentItem(27,"Snake Island","Wayne Crawford",new Date(),"excel"));
		this.docItems.add(new DocumentItem(28,"Snakeman","Allan A. Goldstein",new Date(),"word"));
		this.docItems.add(new DocumentItem(29,"Snakes","Art Names",new Date(),"excel"));
		this.docItems.add(new DocumentItem(30,"Snakes on a Train","Peter Mervis",new Date(),"pdf"));
		this.docItems.add(new DocumentItem(31,"Spasmes","William Fruet",new Date(),"pdf"));
		this.docItems.add(new DocumentItem(32,"SSSSSSS","Bernard L. Kowalski",new Date(),"excel"));
		this.docItems.add(new DocumentItem(33,"Stanley","William Grefe",new Date(),"word"));
		this.docItems.add(new DocumentItem(34,"Thunder of Gigantic Serpent","Godfrey Ho",new Date(),"oth"));
		this.docItems.add(new DocumentItem(35,"Vengeance","Preaw Sirisuwan",new Date(),"img"));
		this.docItems.add(new DocumentItem(36,"Venin","Piers Haggard",new Date(),"word"));
		this.docItems.add(new DocumentItem(37,"Venins","Max Reid",new Date(),"pdf"));
		this.docItems.add(new DocumentItem(38,"Venom","Max Reid",new Date(),"oth"));     
	}

	public ArrayList<DocumentItem> getDocItems() {
		return docItems;
	}

	public void setDocItems(ArrayList<DocumentItem> docItems) {
		this.docItems = docItems;
	}

}
