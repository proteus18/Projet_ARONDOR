package config;

import java.util.HashMap;

public class SaveSettings {
	private HashMap<String, String> comptes;
	
	public HashMap<String, String> getComptes() {
		return comptes;
	}
	public void setComptes(HashMap<String, String> comptes) {
		this.comptes = comptes;
	}
	public void addCompteToHash(String nomCompte, String token) {
		this.comptes.put(nomCompte, token);
	}
	
}
