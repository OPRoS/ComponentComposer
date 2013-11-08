package kr.co.n3soft.n3com.model.component;

import java.io.File;
import java.io.FileOutputStream;

import kr.co.n3soft.n3com.model.diagram.UMLDiagramModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

public class OPRoSServiceTypesElementModel extends UMLDiagramModel{
	public OPRoSServiceTypesElementModel(){
		super();
		initializeData();
	}
	public void initializeData(){
         
          LOGIC_ICON	= ProjectManager.getInstance().getImage(10117);
	}
	
	public String toString() {
		return "Service Types";
	}
	
	public void wirteProfile(String path){
    	XMLOutputter opt = new XMLOutputter();
		Format form = opt.getFormat();
		form.setEncoding("euc-kr");
		form.setLineSeparator("\r\n");
		form.setIndent("	");
		form.setTextMode(Format.TextMode.TRIM);
		opt.setFormat(form);
//		Element ele = new Element("defined_data_types");
		
    	
    		try{
    			for(int i=0;i<this.getChildren().size();i++){
    				Object obj = this.getChildren().get(i);
    				if(obj instanceof OPRoSServiceTypeElementModel){
    					OPRoSServiceTypeElementModel ops = (OPRoSServiceTypeElementModel)obj;	
//    					WJH 100817 S 내용없는 리스트 제거
    					if(ops.getServiceTypeFileName() != null && !ops.getServiceTypeFileName().equals("")){
	    					File  f = new File(path+File.separator+"profile"+File.separator+ops.getServiceTypeFileName());
    						ops.doSave(opt,f);
    					}
//    					WJH 100817 E 내용없는 리스트 제거
    				}
//    				else if(obj instanceof OPRoSDataTypeElementModel){
//    					OPRoSServiceTypeElementModel ops = (OPRoSServiceTypeElementModel)obj;	
//    					File  f = new File(this.getCmpFolder()+File.separator+"profile"+File.separator+ops.getServiceTypeFileName());
//    					ops.doSave(opt,f);
//    				}
    			}
    			}
    			catch(Exception e){
    				e.printStackTrace();
    			}
    		
    		
    		
    	
    }
	
	public String writeModel(){
		Element ele = new Element("defined_service_types");
		XMLOutputter opt = new XMLOutputter();
		Format form = opt.getFormat();
		form.setEncoding("euc-kr");
		form.setLineSeparator("\r\n");
		form.setIndent("	");
		form.setTextMode(Format.TextMode.TRIM);
		opt.setFormat(form);
		StringBuffer sb = new StringBuffer();
//		int i = this.getChildren().size();
		for(int i=0;i<this.getChildren().size();i++){
			Object obj = this.getChildren().get(i);
			if(obj instanceof OPRoSServiceTypeElementModel){
				OPRoSServiceTypeElementModel ops = (OPRoSServiceTypeElementModel)obj;	
				sb.append(ops.writeModel());
			}
		}
		
//		sb.append("property OPRoSServiceTypeElementModel="+this.getServiceTypeFileName()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
		return sb.toString();
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