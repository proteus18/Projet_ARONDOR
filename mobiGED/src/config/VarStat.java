package config;

/**
 * 
 */

public class VarStat {
	private static String messageToBox;
	private static Boolean isGoogleConnected;

	public static String getMessageToBox() {
		return messageToBox;
	}

	public static void setMessageToBox(String messageToBox) {
		VarStat.messageToBox = messageToBox;
	}

	public static Boolean getIsGoogleConnected() {
		return isGoogleConnected;
	}

	public static void setIsGoogleConnected(Boolean isGoogleConnected) {
		VarStat.isGoogleConnected = isGoogleConnected;
	}
	
	
}
