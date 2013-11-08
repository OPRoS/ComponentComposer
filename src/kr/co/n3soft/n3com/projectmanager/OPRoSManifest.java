package kr.co.n3soft.n3com.projectmanager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
//ijs monitoring->exports
public class OPRoSManifest {
	
	public String Manifest_version= "OPRoS_1.0";
	public String Archive_type= "OPRoS_Application";
	public String Archive_name= "";
	public String Archive_description= "";
	public String Archive_Element = "";
	public String rn = "\r\n";
//	String Archive_Element= "GreetingService.xml";
	public java.util.ArrayList Archive_Elements = new java.util.ArrayList();
	public File f = null;
	
	//111027 SDM S - 메니퍼스트 파일 읽기
	public boolean readOPRoSManifest() throws IOException{
		FileInputStream fis = null;
		BufferedReader br = null;
		File fManifest = new File(f.getPath()+"OPRoS.mf");
		
		try {
			fis = new FileInputStream(fManifest.getPath());
			
			br = new BufferedReader(new InputStreamReader(fis));
			String line = null;
			
			while((line = br.readLine()) != null){
				int nElement = line.indexOf("Archive-Element:");
				
				if(nElement > -1){
					line = line.replace("Archive-Element:", "");
					line = line.trim();
					
					String[] strFileList = f.list();
					
					for(int i=0; strFileList.length > i; i++){
						if(strFileList[i].equals(line))
							return true;
					}
				}
			}
			
			fis.close();
			br.close();
			 
			return false;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			try{
			 fis.close();
			 br.close();
			}
			catch(Exception e1){
				e1.printStackTrace();
			}
		}
		return false;	
	}
	//111027 SDM E - 메니퍼스트 파일 읽기
	
	public void writeOPRoSManifest(){
		try{
			java.util.HashMap temp = new java.util.HashMap();
			
			
		if(f!=null){
				StringBuffer sb = new StringBuffer();
				sb.append("Manifest-version: "+Manifest_version+rn);
				sb.append("Archive-type: "+Archive_type+rn);
				sb.append("Archive-name: "+Archive_name+rn);
				sb.append("Archive-description: "+Archive_description+rn);
				sb.append("Archive-Element: "+Archive_Element+rn);
				for(int i=0;i<Archive_Elements.size();i++){
					String str = (String)Archive_Elements.get(i);
					if(temp.get(str)==null){
						sb.append("Element: "+str+"/"+rn);
						temp.put(str, str);
					}
				}
				this.writeFile(sb, f);
				
				
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//ijs monitoring->exports
	public void writeFile(StringBuffer sb,File f) throws Exception{
			
			if(!f.exists())
				f.createNewFile();
			FileWriter fw = new FileWriter(f);
		
			BufferedWriter bw=new BufferedWriter(fw);

			bw.write(sb.toString());
			bw.close();
		}


}
