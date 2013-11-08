package kr.co.n3soft.n3com.model.component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import kr.co.n3soft.n3com.OPRoSUtil;
import kr.co.n3soft.n3com.model.diagram.UMLDiagramModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.swt.SWT;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

public class OPRoSDataTypesElementModel extends UMLDiagramModel{
	java.util.ArrayList dataTypeReference = new java.util.ArrayList();
	private Document dataTypeDoc; //20110707-¼­µ¿¹Î
	
	public OPRoSDataTypesElementModel(){
		super();
		initializeData();
	}
	public void initializeData(){
         
          LOGIC_ICON	= ProjectManager.getInstance().getImage(10115);
	}
	
	public String toString() {
		return "Data Types";
	}
	
    public void wirteProfile(String path){
    	XMLOutputter opt = new XMLOutputter();
		Format form = opt.getFormat();
		form.setEncoding("euc-kr");
		form.setLineSeparator("\r\n");
		form.setIndent("	");
		form.setTextMode(Format.TextMode.TRIM);
		opt.setFormat(form);
		
    	try{
    		for(int i=0;i<this.getChildren().size();i++){
    			Object obj = this.getChildren().get(i);
    			if(obj instanceof OPRoSDataTypeElementModel){
    				
    				OPRoSDataTypeElementModel ops = (OPRoSDataTypeElementModel)obj;	
    				File  f = new File(path+File.separator+"profile"+File.separator+ops.getDataTypeFileName());
    				ops.doSave(opt,f);
    			}
    		}
    		}
    		catch(Exception e){
    			e.printStackTrace();
    		}
    		
    	
    	
    }
	
    //20110707 - ¼­µ¿¹Î
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
    
    //20110707 - ¼­µ¿¹Î
    public void doLoad(){
//		Element parentEle = new 
		if(!this.getDataTypeFileName().isEmpty()){
			String filePath = this.getCmpFolder()+File.separator+this.getDataTypeFileName();
			
			FileInputStream dataTypeInputStream = null;
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
//					addChild(model);
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
    
	public String writeModel(){
		StringBuffer sb = new StringBuffer();
	
//		int i = this.getChildren().size();
		for(int i=0;i<this.getChildren().size();i++){
			Object obj = this.getChildren().get(i);
			if(obj instanceof OPRoSDataTypeElementModel){
				OPRoSDataTypeElementModel ops = (OPRoSDataTypeElementModel)obj;	
				sb.append(ops.writeModel());
			}
		}
		
		
		
//		sb.append("property OPRoSServiceTypeElementModel="+this.getServiceTypeFileName()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
		return sb.toString();
	}
	
	public void addDataTypeReference(String dataTypeFile){
		if(dataTypeReference.contains(dataTypeFile))
			return;
		dataTypeReference.add(dataTypeFile);
	}
	
	public java.util.ArrayList getataTypeReference(){
		
		return dataTypeReference;
	}
	
	public void writeXMLFile(Document sb,File f) throws Exception{
		XMLOutputter opt = new XMLOutputter();
		Format form = opt.getFormat();
		form.setEncoding("euc-kr");
		form.setLineSeparator("\r\n");
		form.setIndent("	");
		form.setTextMode(Format.TextMode.TRIM);
		opt.setFormat(form);
		FileOutputStream outStream = new FileOutputStream(f);
		opt.output(sb,outStream);
		outStream.close();
	}

}
