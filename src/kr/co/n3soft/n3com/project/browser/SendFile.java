package kr.co.n3soft.n3com.project.browser;

import java.io.File;

public class SendFile {
	public String key = "";
	public String getKey() {
		return key;
	}
	public void setKey(String filePath) {
		int index = filePath.indexOf(path);
		key = filePath.substring(this.path.length()+1);
		System.out.println("key:"+key);
//		this.key = key;
	}
	public File value = null;
	public String path = "";
	
	public String toString(){
		return key;
	}
		

}
