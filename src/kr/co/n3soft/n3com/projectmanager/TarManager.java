package kr.co.n3soft.n3com.projectmanager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.StringTokenizer;


import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.ui.internal.wizards.datatransfer.TarEntry;
import org.eclipse.ui.internal.wizards.datatransfer.TarFile;
import org.eclipse.ui.internal.wizards.datatransfer.TarOutputStream;
public class TarManager {
//	public String manifest_version = "OPRoS_1.0";
//	public String manifest_version = "OPRoS_1.0";
	
	private static TarManager instance;
	public static TarManager getInstance() {
		if (instance == null) {
			instance = new TarManager();
			return instance;
		}
		else {
			return instance;
		}
	}
	
	//111102 SDM S - ��ũ�����̽� ��� ����
	public String getTempDir(){
		String temp = ResourcesPlugin.getWorkspace().getRoot().getLocation().toString() 
					+ File.separator + ".opros_cc" + File.separator + "templib_";
		
		return temp;
	}	
	//111102 SDM E - ��ũ�����̽� ��� ����
	
	
	public void makeTempLib(String templibPath) throws Exception{
		if(templibPath==null){
			templibPath = getTempDir();		//111102 SDM - TEMP���� ��ũ�����̽� ������ �̵�
		}
		String libPath = ProjectManager.getInstance().getSourceFolder();
//		File flibPath = new File(libPath);
		File templibPathf = new File(templibPath);
		// 110905 S SDM - TEMP ���� ����ϱ��� TEMP������ �����ִ� ���� ����
		if(!templibPathf.exists()){
			templibPathf.mkdir();
		}
		else{
			boolean chkDeleteFile = deleteFile(templibPath);  // ���� �޼ҵ�
			templibPathf.mkdir();
		}
		// 110905 E SDM - TEMP ���� ����ϱ��� TEMP������ �����ִ� ���� ����
		makeTempLibFile(libPath,templibPath);	//110906 SDM - ���ʿ��� ���� ���� ����
		
	}
	
	// 110905 S SDM - ���� �޼ҵ�
	public static boolean deleteFile(String delTarget) {
		  File delDir = new File(delTarget);
		  
		  if(delDir.isDirectory()) {
		   File[] allFiles = delDir.listFiles();
		   
		   for(File delAllDir : allFiles) {
		    deleteFile(delAllDir.getAbsolutePath());
		   }
		  }
		  
		  return delDir.delete();
	}
	// 110905 E SDM - ���� �޼ҵ�
	
	// 111111 SDM S - ���̺귯�� TEMP�� ����� ������ ����
	public void makeTempLibFile( String currentPath, String backupPath )
	throws IOException
	{
		File [] subList = new File ( currentPath ).listFiles ( );
        
		for ( int i = 0; i < subList.length; i++ )
		{
			if ( !subList[i].isDirectory ( ) ){

				try{
					if(subList [i].getName().lastIndexOf(".tar")>0){
						extractTar(subList[i],new File(backupPath));
					}
					else{
						String name = subList[i].getName();
						CompAssemManager.getInstance().copyFile(subList[i],new File(backupPath+File.separator+subList[i].getName()));
					}
				}
				catch(Exception e){
					e.printStackTrace();
				}
				
				
			} else if ( subList[i].isDirectory ( ) ) {
				File backupDir = new File ( backupPath + File.separator + subList [i].getName ( ) );
				if ( ! backupDir.isDirectory ( ) ){
					backupDir.mkdir ( );
					this.makeTempLibFile(currentPath + File.separator + subList [i].getName ( ), backupPath + File.separator + subList [i].getName ( ));
				}
				else{
					this.makeTempLibFile(currentPath + File.separator + subList [i].getName ( ), backupPath + File.separator + subList [i].getName ( ));
				}


			}
		}
	}
	
	/*
	public void makeTempLibFile( String currentPath, String backupPath, String strMkdir )	//110906 SDM - ���ʿ��� ���� ���� ����
	throws IOException
	{
		File [] subList = new File ( currentPath ).listFiles ( );
		
		//110906 S SDM - ���ʿ��� ���� ���� ���� 
		boolean bNewLibFile = false;
		
		for(int j = 0; j<subList.length; j++){
			if((subList[j].getName().lastIndexOf(".tar")>0) || (subList[j].getName().lastIndexOf(".xml")>0)){
				bNewLibFile = true;
				break;
			}
		}
		
		if(strMkdir!=null && bNewLibFile){
			backupPath = backupPath + File.separator + strMkdir;
			File fMkdir = new File (backupPath);
			fMkdir.mkdir();
		}
		//110906 E SDM - ���ʿ��� ���� ���� ���� 
		
        
		for ( int i = 0; i < subList.length; i++ )
		{
			if ( !subList[i].isDirectory ( ) ){

				try{
					if(subList [i].getName().lastIndexOf(".tar")>0){
						extractTar(subList[i],new File(backupPath));
					}
					else if((subList [i].getName().lastIndexOf(".xml")>0) || (subList [i].getName().lastIndexOf(".dll")>0)){	//110906 S SDM - XML�̳� DLL���ϸ� �����ϵ��� IF�� �߰�
						String name = subList[i].getName();
						CompAssemManager.getInstance().copyFile(subList[i],new File(backupPath+File.separator+subList[i].getName()));
					}
				}
				catch(Exception e){
					e.printStackTrace();
				}
				
				
			} else if ( subList[i].isDirectory ( ) ) {
				File backupDir = new File ( backupPath + File.separator + subList [i].getName ( ) );
				//110906 S SDM - ���ʿ��� ���� ���� ����
				if ( ! backupDir.isDirectory ( ) ){
					//backupDir.mkdir ( );
					this.makeTempLibFile(currentPath + File.separator + subList [i].getName ( ), backupPath, subList [i].getName ( ));
				}
				else{
					this.makeTempLibFile(currentPath + File.separator + subList [i].getName ( ), backupPath + File.separator + subList [i].getName ( ), null);
				}
				//110906 E SDM - ���ʿ��� ���� ���� ����

			}
		}
	}*/
	// 111111 SDM E - ���̺귯�� TEMP�� ����� ������ ����
	
	
//	getBytesFromFile
	
	
	public boolean makeTar(File folder,File target)throws Exception {

		 TarEntry tarEntry = null;
		 OutputStream outputStream = null;
		 File tarFile = null;
		 if(!folder.exists())
			 return false;
		 if(!folder.isDirectory())
			 return false;
		 

//			StringBuffer sb = new StringBuffer();
//			sb.append(folder.getName()+"/");
			String path = folder.getName()+"/";
				 if(target.isDirectory()){
					  tarFile = new File(target.getPath()+File.separator+folder.getName()+".tar");
					 
				 }
				 else{
					 tarFile = target;
				 }
				 if(tarFile.exists()){
					 tarFile.delete();
					 tarFile.createNewFile();
				 }
				 else{
					 tarFile.createNewFile();
				 }
				 outputStream = new FileOutputStream(tarFile);
				 
			 
//		 }
		 TarOutputStream tarOutputStream = new TarOutputStream(outputStream);
		
		 File[] files = folder.listFiles();
		 for(int i=0;i<files.length;i++)

		 {
			 File file = (File)files[i];
			 if(file.isDirectory()){
//				 sb.append(file.getName()+"/");
				 String tPaht = path + file.getName()+"/";
				 subcompressFiles(file,tarOutputStream,tPaht);
			 }
			 else{
				 FileInputStream fis = new FileInputStream(file);
				 tarEntry = new TarEntry(path+file.getName());
				 System.out.println("path=====>"+path);
				 tarEntry.setSize(file.length());
				 tarEntry.setFileType(0);
				 tarOutputStream.putNextEntry(tarEntry);
				 tarOutputStream.write(getBytesFromFile(file));
				 tarOutputStream.closeEntry();
//				 fis.close();
			 }
			
		
		 }
//		 tarOutputStream.closeEntry();
		 tarOutputStream.close();
		 outputStream.close();
		
	
		 return true;
	}
	
	public  void subcompressFiles(File dir,TarOutputStream tarOutputStream ,String path)throws Exception{
		 TarEntry tarEntry = null;
		if(dir.isDirectory()){
			File[] files = dir.listFiles();
			 for(int i=0;i<files.length;i++)

			 {
				 File file = (File)files[i];
				 if(file.isDirectory()){
//					 String tPaht = path;
					 String tPaht = path + file.getName()+"/";
					 subcompressFiles(file, tarOutputStream,tPaht);
				 }
				 else{
					 FileInputStream fis = new FileInputStream(file);
					 tarEntry = new TarEntry(path+file.getName());
					 System.out.println("path=====>"+path);
					 tarEntry.setSize(file.length());
					 tarEntry.setFileType(0);
					 tarOutputStream.putNextEntry(tarEntry);
					 tarOutputStream.write(getBytesFromFile(file));
					 tarOutputStream.closeEntry();
				 }
				 
			
			 }
		}
		return;
		
	}
	
//	makeTar
	
	private  byte[] getBytesFromFile(File file) throws Exception {

		 InputStream is = new FileInputStream(file);
	
		 // Get the size of the file
	
		 long length = file.length();
	
		 if (length > Integer.MAX_VALUE) {
	
		 // File is too large
	
		 }
	
		 // Create the byte array to hold the data
		
		 byte[] bytes = new byte[(int)length];
		
		 // Read in the bytes
	
		 int offset = 0;
	
		 int numRead = 0;
	
		 while (offset < bytes.length
	
		 &&  (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
		
		 offset += numRead;
		
		 }
	
		 // Ensure all the bytes have been read in
	
		 if (offset < bytes.length){
		
		 throw new Exception("Could not completely read the file "+file.getName());
	
		 }
	
		 // Close the input stream and return bytes
		
		 is.close();
	
		 return bytes;
	
		 }
	
	public void extractTar(File tar, File dest){
		try{
			TarFile tarfile = new TarFile(tar);
//			for(int i=0;i<tarfile.entries().hasMoreElements();i++){
//				
//			}
			if(!dest.exists())
			dest.mkdir();
			while(tarfile.entries().hasMoreElements()){
				TarEntry tarEntry = (TarEntry)tarfile.entries().nextElement();
				InputStream in = tarfile.getInputStream(tarEntry);
				StringTokenizer stk = new StringTokenizer(tarEntry.getName(),"/");
				File parent = dest;
				String name = "";
				while(stk.hasMoreElements()){
					
					String token = stk.nextToken();
					if(stk.hasMoreElements()){
						File desFile1 = new File(parent.getPath()+File.separator+token);
						if(!desFile1.exists())
							desFile1.mkdir();
						parent = desFile1;
					}
					else{
						name = token;
					}

				}

				File desFile = new File(parent.getPath()+File.separator+name);
				OutputStream out = new FileOutputStream(desFile);
				byte buf[] = new byte[1024];

				for (int cnt;(cnt = in.read(buf))!=-1;)
					out.write(buf, 0, cnt);
				in.close();
				out.close();
			}
			


		 }
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
//	public void backup( String currentPath, String backupPath )
//	throws IOException
//	{
//		File [] subList = new File ( currentPath ).listFiles ( );
//
//		for ( int i = 0; i < subList.length; i++ )
//		{
//			
//			
//			if ( subList [i].isFile ( ) ){
//				InputStream in = new FileInputStream ( currentPath + File.separator + subList [i].getName ( ) );
//				OutputStream out = new FileOutputStream ( backupPath + File.separator + subList [i].getName ( ) );
//
//				int count = 0;
//				for ( int b; ( b = in.read ( ) ) != -1; ){
//					++count;
//					out.write(b);
//				}
//				in.close ( );
//				out.close ( );
////				if(monitor!=null){
////					monitor.subTask(subList[i].getPath());
////					monitor.worked(INCREMENT);
////				}
//			} else if ( subList[i].isDirectory ( ) ) {
//				File backupDir = new File ( backupPath + File.separator + subList [i].getName ( ) );
//				if ( ! backupDir.isDirectory ( ) ){
//					backupDir.mkdir ( );
//					this.backup(currentPath + File.separator + subList [i].getName ( ), backupPath + File.separator + subList [i].getName ( ));
//				}
//
//
//			}
//		}
//	}
	
	
}
