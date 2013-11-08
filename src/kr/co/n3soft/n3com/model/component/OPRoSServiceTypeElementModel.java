package kr.co.n3soft.n3com.model.component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import kr.co.n3soft.n3com.OPRoSStrings;
import kr.co.n3soft.n3com.OPRoSUtil;
import kr.co.n3soft.n3com.model.diagram.UMLDiagramModel;
import kr.co.n3soft.n3com.parser.IN3ModelTokenType;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.swt.SWT;
import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

public class OPRoSServiceTypeElementModel extends UMLDiagramModel{
	public static final String PROPERTY_SERVICE_TYPE_FILENAME = "Service Type File Name";
//	private String serviceTypeFileName;
	private Document serviceTypeDoc;
//	public String rn = "\r\n";
	public OPRoSServiceTypeElementModel(){
		super();
		initializeData();
	}
	public void initializeData(){
//		super.initializeData();
		this.setServiceTypeFileName(OPRoSStrings.getString("ServiceTypeEleFileNameDefault"));
		serviceTypeDoc = null;
		LOGIC_ICON	= ProjectManager.getInstance().getImage(10118);
	}
//	public String getServiceTypeFileName() {
//		return serviceTypeFileName;
//	}
//	public void setServiceTypeFileName(String serviceTypeFileName) {
//		String oldValue = this.getServiceTypeFileName();
//		this.serviceTypeFileName = serviceTypeFileName;
////		getListeners().firePropertyChange(PROPERTY_SERVICE_TYPE_FILENAME, oldValue, serviceTypeFileName);
//	}
	public Document getServiceTypeDoc() {
		if(serviceTypeDoc==null){
			this.doLoad();
			return serviceTypeDoc;
		}
		else
			return serviceTypeDoc;
	}
	
	public void doLoad(){
//		Element parentEle = new 
		if(!this.getServiceTypeFileName().isEmpty()){
			String filePath = this.getCmpFolder()+File.separator+this.getServiceTypeFileName();
			
			FileInputStream serviceTypeInputStream = null;
			try {
				serviceTypeInputStream = new FileInputStream(filePath);
			} catch (FileNotFoundException e) {

				e.printStackTrace();
				String path = this.getTempPath();
				 filePath =path+File.separator+this.getServiceTypeFileName();
				 try {
					 serviceTypeInputStream = new FileInputStream(filePath);
				 } catch (FileNotFoundException e1) {
					 
				 }
			
				
				
			}
			SAXBuilder builder = new SAXBuilder();
			try {
				serviceTypeDoc = builder.build( serviceTypeInputStream );
			} catch (JDOMException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(serviceTypeDoc!=null){
				this.setServiceTypeDoc(serviceTypeDoc);
			}else{
				OPRoSUtil.openMessageBox("Data Doc is NULL", SWT.OK);
			}
			if(serviceTypeInputStream!=null)
				try {
					serviceTypeInputStream.close();
//					addChild(model);
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
	
	public void doLoad(String filePath){
//		Element parentEle = new 
		if(!this.getServiceTypeFileName().isEmpty()){
//			String filePath = this.getCmpFolder()+File.separator+this.getServiceTypeFileName();
			FileInputStream serviceTypeInputStream = null;
			try {
				serviceTypeInputStream = new FileInputStream(filePath);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			SAXBuilder builder = new SAXBuilder();
			try {
				serviceTypeDoc = builder.build( serviceTypeInputStream );
			} catch (JDOMException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(serviceTypeDoc!=null){
				this.setServiceTypeDoc(serviceTypeDoc);
			}else{
				OPRoSUtil.openMessageBox("Data Doc is NULL", SWT.OK);
			}
			if(serviceTypeInputStream!=null)
				try {
					serviceTypeInputStream.close();
//					addChild(model);
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
	
	
	
	
	public void setServiceTypeDoc(Document serviceTypeDoc) {
		this.serviceTypeDoc = serviceTypeDoc;
	}
	public void doSave(XMLOutputter opt,File f){
//		String outFileName;
//		String referPath=OPRoSUtil.getOPRoSFilesPath();
		try {
			opt.output(getServiceTypeDoc(), new FileOutputStream(f));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		int index = getDataTypeFileName().lastIndexOf(".");
//		outFileName = getDataTypeFileName().substring(0, index);
//		String[] params = {
//				"-param","filename","'"+profileDirPath+"/"+outFileName+"'",
//				"-param","outpath","'"+profileDirPath.substring(0,profileDirPath.lastIndexOf("/"))+"/'",
//				referPath+"/OPRoSFiles/GenerateProfiles.xsl",referPath+"/OPRoSFiles/GenerateProfiles.xsl"};
//		Stylesheet.main(params);

	}
//	public void initializeData(){
//        
//        LOGIC_ICON	= ProjectManager.getInstance().getImage(10118);
//	}
	
	public String toString() {
		return this.getServiceTypeFileName();
	}
	
	public String writeModel(){
		StringBuffer sb = new StringBuffer();
		sb.append("property OPRoSServiceTypeElementModel="+this.getServiceTypeFileName()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
		return sb.toString();
	}
	
//	public String wirteSourceHeader(){
//		StringBuffer sb = new StringBuffer();
//		String dataType1 = this.get
//		if(dataType1!=null){
//			String dataType = dataType1.trim();
//			sb.append("#include <"+name+">"+rn);
//			this.
//
//		}
//		return sb.toString();
//		
//		
//	}

}
