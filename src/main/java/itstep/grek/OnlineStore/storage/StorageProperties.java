package itstep.grek.OnlineStore.storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("storage")
public class StorageProperties {


	/**
	 * Folder location for storing files
	 */
	private String location = "/home/grek/upload-dir";
	//private String location = "upload-dir";

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}
