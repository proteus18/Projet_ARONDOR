/**
 * 
 */
package config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.thoughtworks.xstream.XStream;


public class WriteFileConfig {

	private FileOutputStream fop = null;
	private File file;
	private String content = "This is the text content";
	
	public void Write() {
		XStream xstream = new XStream();
	}
}
