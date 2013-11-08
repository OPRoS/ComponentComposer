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
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

public class OPRoSDataTypeElementModel  extends UMLDiagramModel{
	public static final String PROPERTY_DATA_TYPE_FILENAME = "Data Type File Name";
//	private String dataTypeFileName;
	private Document dataTypeDoc;
//	public String rn = "\r\n";
//	
//	private String[] basicType = {"int","short","long","bool","float","double"};
//	private String unsigned ="unsigned";
	
	public OPRoSDataTypeElementModel(){
		super();
		initializeData();
	}
	public void initializeData(){
//		super.initializeData();
		this.setDataTypeFileName(OPRoSStrings.getString("DataTypeEleFileNameDefault"));
		dataTypeDoc = null;
		LOGIC_ICON	= ProjectManager.getInstance().getImage(10116);
	}
//	public String getDataTypeFileName() {
//		return dataTypeFileName;
//	}
//	public void setDataTypeFileName(String dataTypeFileName) {
//		String oldValue = this.getDataTypeFileName();
//		this.dataTypeFileName = dataTypeFileName;
////		getListeners().firePropertyChange(PROPERTY_DATA_TYPE_FILENAME, oldValue, dataTypeFileName);
////		if(getPropertySource()!=null){
////			getPropertySource().getPropertyValue(OPRoSDataTypeElementModel.PROPERTY_DATA_TYPE_FILENAME);
////		}
//	}
	public Document getDataTypeDoc() {
		if(dataTypeDoc==null){
			this.doLoad();
			return dataTypeDoc;
		}
		else
		return dataTypeDoc;
	}
	public void setDataTypeDoc(Document dataTypeDoc) {
		this.dataTypeDoc = dataTypeDoc;
	}
	public void doSave(XMLOutputter opt,File f){
//		String outFileName;
//		String referPath=OPRoSUtil.getOPRoSFilesPath();
		try {
			opt.output(getDataTypeDoc(), new FileOutputStream(f));
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
	
	public void doLoad(){
//		List<Element> list = parentEle.getChildren();
//		if(list!=null){
//			Iterator<Element> it = list.iterator();
			String filePath=this.getCmpFolder()+File.separator+this.getDataTypeFileName();
			Document dataTypeDoc=null;
			FileInputStream dataTypeInputStream=null;
//			while(it.hasNext()){
//				OPRoSDataTypeElementModel model = new OPRoSDataTypeElementModel();
//				model.setDataTypeFileName(it.next().getText());
				if(!this.getDataTypeFileName().isEmpty()){
//					filePath = profileDirPath+"/"+model.getDataTypeFileName();
					try {
						dataTypeInputStream = new FileInputStream(filePath);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
						String path = this.getTempPath();
						 filePath =path+File.separator+this.getDataTypeFileName();
						 try {
						 dataTypeInputStream = new FileInputStream(filePath);
						 } catch (FileNotFoundException e1) {
							 
						 }
					}
					SAXBuilder builder = new SAXBuilder();
					try {
						dataTypeDoc = builder.build( dataTypeInputStream );
					} catch (JDOMException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					if(dataTypeDoc!=null){
						this.setDataTypeDoc(dataTypeDoc);
					}else{
						OPRoSUtil.openMessageBox("Data Doc is NULL", SWT.OK);
					}
					if(dataTypeInputStream!=null)
						try {
							dataTypeInputStream.close();
//							addChild(model);
						} catch (IOException e) {
							e.printStackTrace();
						}
				}
//			}
//		}
	}
	
	//20110810¼­µ¿¹Î
	public void doLoad(String filePath){
//		List<Element> list = parentEle.getChildren();
//		if(list!=null){
//			Iterator<Element> it = list.iterator();
			Document dataTypeDoc=new Document();
			FileInputStream dataTypeInputStream=null;
//			while(it.hasNext()){
//				OPRoSDataTypeElementModel model = new OPRoSDataTypeElementModel();
//				model.setDataTypeFileName(it.next().getText());
				if(!this.getDataTypeFileName().isEmpty()){
//					filePath = profileDirPath+"/"+model.getDataTypeFileName();
					try {
						dataTypeInputStream = new FileInputStream(filePath);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
						String path = this.getTempPath();
						 filePath =path+File.separator+this.getDataTypeFileName();
						 try {
						 dataTypeInputStream = new FileInputStream(filePath);
						 } catch (FileNotFoundException e1) {
							 
						 }
					}
					SAXBuilder builder = new SAXBuilder();
					try {
						dataTypeDoc = builder.build( dataTypeInputStream );
					} catch (JDOMException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					if(dataTypeDoc!=null){
						this.setDataTypeDoc(dataTypeDoc);
					}else{
						OPRoSUtil.openMessageBox("Data Doc is NULL", SWT.OK);
					}
					if(dataTypeInputStream!=null)
						try {
							dataTypeInputStream.close();
//							addChild(model);
						} catch (IOException e) {
							e.printStackTrace();
						}
				}
//			}
//		}
	}
	
	public String toString() {
		return this.getDataTypeFileName();
	}
	
	public String writeModel(){
		StringBuffer sb = new StringBuffer();
		sb.append("property OPRoSDataTypeElementModel="+this.getDataTypeFileName()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
		return sb.toString();
	}
	
//	public String wirteSourceHeader(){
//		StringBuffer sb = new StringBuffer();
//		String dataType1 = this.getDataType();
//		if(dataType1!=null){
//			String dataType = dataType1.trim();
//		if(dataType.startsWith("std::")){
//			int i = dataType.lastIndexOf("<");
//			String includeName ="";
//			if(i>0){
//				includeName = dataType.substring(5,i);
//			}
//			else{
//				includeName = dataType.substring(5);
//			}
//			sb.append("#include <"+name+">"+rn);
//		}
//		else{
//			for(int i=0;i<basicType.length;i++){
//				int j = dataType.indexOf("unsigned ");
//				if(dataType.equals(basicType[i]) || j>=0){
//					
//				}
//				else{
//					sb.append("#include <"+name+">.h"+rn);
//				}
//			}
//			
//		}
//		}
//		return sb.toString();
//		
//		
//	}

}
