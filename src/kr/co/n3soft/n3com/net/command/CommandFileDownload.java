package kr.co.n3soft.n3com.net.command;

import java.io.File;

import kr.co.n3soft.n3com.net.HeaderSendData;
import kr.co.n3soft.n3com.project.browser.SendFile;
import kr.co.n3soft.n3com.projectmanager.CompAssemManager;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

public class CommandFileDownload implements ICommand{
	File file = null;
	
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public void run(){
//		java.util.ArrayList hrds = new java.util.ArrayList();
//		if(this.file!=null && this.file.isDirectory()){
//			CompAssemManager.getInstance().initCompFiles();
//			CompAssemManager.getInstance().getComponentFile(file);
//			java.util.ArrayList list = CompAssemManager.getInstance().getCompFiles();
//			if(list !=null &&list.size()>0){
//
//				for(int i=0;i<list.size();i++){
//					SendFile sf = (SendFile)list.get(i);
//					if(sf.value!=null && sf.value.exists()){
//						HeaderSendData hrd = new HeaderSendData();
//						hrd.target = "deploy";
//						hrd.cmd = "file.download";
//						hrd.payloadSize = String.valueOf(sf.value.length());
//						hrd.file_name = sf.key;
//						hrd.payload = sf.value;
//						hrds.add(hrd);
//					}
//
//				}
//				this.connectNetwork();
////				this.runFileDownLoad(hrds);
//				FileDownLoadThread fd = new FileDownLoadThread(hrds);
//				ProjectManager.getInstance().window.getShell().getDisplay().asyncExec(fd);
//				
//				
//				
//
//				
//
//			}
//		}
	}

}
