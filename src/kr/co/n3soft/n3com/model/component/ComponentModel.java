package kr.co.n3soft.n3com.model.component;

import java.io.File;

import kr.co.n3soft.n3com.model.comm.BorderContainlModel;
import kr.co.n3soft.n3com.model.comm.ClassifierModel;
import kr.co.n3soft.n3com.model.comm.ElementLabelModel;
import kr.co.n3soft.n3com.model.comm.LineModel;
import kr.co.n3soft.n3com.model.comm.NodeContainerModel;
import kr.co.n3soft.n3com.model.comm.UMLDataModel;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.node.NodeItemModel;
import kr.co.n3soft.n3com.model.umlclass.CompartmentModel;
import kr.co.n3soft.n3com.project.browser.UMLTreeModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

public class ComponentModel extends ClassifierModel {
    static final long serialVersionUID = 1;
    public static int count = 0;
    ElementLabelModel name = null;
    ElementLabelModel streotype = null;
    NodeContainerModel actionModel = null;
    CompartmentModel attrModel = null;
    java.util.List temp_childs = null;
    UMLModel cm = null;
    Integer type = new Integer(-1);
    BorderContainlModel borderContainlModel = null;
    private UMLModel callBeavior = null;
    public int default_width = 100;
    public int default_height = 50;
    NodeItemModel nodeItemModel = null;
    
	public boolean isInstance = false; 
	public String tmp_core_id = "";
	
	public UMLTreeModel instanceUMLTreeModel = null;
	
	
	public File file = null;
	public File dir = null;
	public File fsmFile = null;
	public File dllFile = null;
	public String fsmFileName = "";
	
	public UMLTreeModel getInstanceUMLTreeModel() {
		instanceUMLTreeModel = (UMLTreeModel)this.getUMLDataModel().getElementProperty("instanceUMLTreeModel1");
			return instanceUMLTreeModel;
		}
		public void setInstanceUMLTreeModel(UMLTreeModel instanceUMLTreeModel) {
			this.instanceUMLTreeModel = instanceUMLTreeModel;
			this.getUMLDataModel().setElementProperty("instanceUMLTreeModel1", instanceUMLTreeModel);
			if(instanceUMLTreeModel!=null)
			this.getUMLDataModel().setProperty("INSTANCE_NAME", instanceUMLTreeModel.getName());
		}
		
		public boolean isInstance() {
			String in = this.getUMLDataModel().getProperty("IS_INSTANCE");
			if(in!=null && !in.trim().equals("")){
				isInstance = 	Boolean.valueOf(in);
			}
			return isInstance;
		}
		
		public void setInstance(boolean isInstance) {
			this.isInstance = isInstance;
			this.getUMLDataModel().setProperty("IS_INSTANCE", String.valueOf(isInstance));
		}
   
    
	public NodeItemModel getNodeItemModel() {
		nodeItemModel = (NodeItemModel)this.getClassModel().getUMLDataModel().getElementProperty("NodeItemModel");
		return nodeItemModel;
	}

	public void setNodeItemModel(NodeItemModel _nodeItemModel) {
		
		this.nodeItemModel = _nodeItemModel;
		if(_nodeItemModel!=null){
			this.setNodeItemModelId(nodeItemModel.getUMLDataModel().getId());
			this.getClassModel().getUMLDataModel().setElementProperty("NodeItemModel", nodeItemModel);
			
			ProjectManager.getInstance().mapNode(nodeItemModel.getUMLDataModel().getId(), this);
		}
		else{
			this.setNodeItemModelId("");
			this.getClassModel().getUMLDataModel().setElementProperty("NodeItemModel", null);
		}
//		this.uMLDataModel.setElementProperty("NodeItemModel", nodeItemModel);
	}
	
	public String getNodeItemModelId(){
//		if(uMLDataModel.getProperty(this.ID_IP)==null){
//			this.setVersion("1.0");
//		}
		if(this.getClassModel().getUMLDataModel().getProperty("NodeItemModel_id")==null){
			this.setNode("");
		}
		return this.getClassModel().getUMLDataModel().getProperty("NodeItemModel_id");
	}
	
	public void setNodeItemModelId(String p){
		
		this.getClassModel().getUMLDataModel().setProperty("NodeItemModel_id", p);
		this.getUMLDataModel().setProperty("NodeItemModel_id", p);

	}
	
	
	
	public String getNode(){
		NodeItemModel nodeItemModel = (NodeItemModel)this.getClassModel().getUMLDataModel().getElementProperty("NodeItemModel");
		if(nodeItemModel!=null){
			UMLTreeModel utm = nodeItemModel.getUMLTreeModel().getParent();
			return utm.getName()+"."+nodeItemModel.getNode();
		}
		return "";
	}
	
//	public void setNode(String p){
//		
//		uMLDataModel.setProperty(this.ID_NODE, p);
//
//	}

    public ComponentModel() {
        super(150, 100);
        //		default_width = 80;
        //		default_height = 50;
        this.setAttrIsBorder(false);
        this.setOperIsBorder(false);
        this.getClassModel().setComponent(true);
        //		this.setStereotype("interface");
    }
    public ComponentModel(UMLDataModel p,boolean isLoad) {
        super(p,isLoad);
//        this.setUMLDataModel(p);
    }

    //	public FinalActiionModel(){
    ////		attrModel = new CompartmentModel();
    //		
    //		actionModel = new NodeContainerModel();
    //		actionModel.setParentModel(this);
    //		actionModel.setLayout(BorderLayout.CENTER);
    //		actionModel.setParentLayout(1);
    //		this.addChild(actionModel);
    //		streotype= new ElementLabelModel(PositionConstants.CENTER);
    //		size.width = 80;
    //		size.height= 50;
    //		name = new ElementLabelModel(PositionConstants.CENTER);
    //		name.setType("NAME");
    //		borderContainlModel = new BorderContainlModel();
    //		borderContainlModel.setParentModel(this);
    //		borderContainlModel.setLayout(BorderLayout.TOP);
    //		borderContainlModel.setParentLayout(1);
    //		borderContainlModel.setCenterModel(name);
    //		this.addChild(borderContainlModel);
    //		this.uMLDataModel.setElementProperty(String.valueOf(BorderLayout.CENTER), name);
    //	
    //		
    //		
    //
    //	}
    public ComponentModel(UMLDataModel p) {
        super(p);
        //		this.callBeavior = (UMLModel)this.uMLDataModel.getElementProperty("callBeavior");
        this.setAttrIsBorder(false);
        this.setOperIsBorder(false);
        
        
        
        this.setUMLDataModel(p);
        if("<<atomic>>".equals(this.getStereotype())){
        	this.getClassModel().setComponent(false);
        }
        else{
        	this.getClassModel().setComponent(true);
        }
    }
    
public File getFile() {
		
		
			String file_str = this.getUMLDataModel().getProperty("file_str");
			if(file_str!=null){
				File f = new File(file_str);
				if(f.exists())
					return f;
			}
			else{
				if(this.getUMLDataModel().getElementProperty("file")!=null){
					return (File)this.getUMLDataModel().getElementProperty("file");
			}
			
			
		}
		return null;
		
	}
	public void setFile(File file) {
		this.file = file;
		this.getUMLDataModel().setElementProperty("file", file);
		if(file!=null){
			String df = file.toString();
			System.out.println("df:"+df);
			this.getUMLDataModel().setProperty("file_str", df);
		}
	}
	public File getDir() {
//		return (File)this.getUMLDataModel().getElementProperty("dir");
		
		
//		else {
			String file_str = this.getUMLDataModel().getProperty("dir_str");
			if(file_str!=null){
				File f = new File(file_str);
				if(f.exists())
					return f;
			}
			else{
				if(this.getUMLDataModel().getElementProperty("dir")!=null){
					return (File)this.getUMLDataModel().getElementProperty("dir");
				}
			}
			
//		}
		return null;
	}
	public void setDir(File dir) {
		this.dir = dir;
		this.getUMLDataModel().setElementProperty("dir", dir);
		if(dir!=null){
			
			this.getUMLDataModel().setProperty("dir_str", dir.toString());
		}
	}
	public File getFsmFile() {
		return (File)this.getUMLDataModel().getElementProperty("fsmFile");
	}
	public void setFsmFile(File fsmFile) {
		this.fsmFile = fsmFile;
		this.getUMLDataModel().setElementProperty("fsmFile", fsmFile);
		if(fsmFile!=null){
			String df = fsmFile.toString();
			System.out.println("df:"+df);
			this.getUMLDataModel().setProperty("fsmFile_str", df);
		}
	}
	public File getDllFile() {
//		return (File)this.getUMLDataModel().getElementProperty("dllFile");
//		if(this.getUMLDataModel().getElementProperty("dllFile")!=null){
//			return (File)this.getUMLDataModel().getElementProperty("dllFile");
//		}
//		else {
			String file_str = this.getUMLDataModel().getProperty("dllFile_str");
			if(file_str!=null){
				File f = new File(file_str);
				if(f.exists())
					return f;
			}
			else{
				if(this.getUMLDataModel().getElementProperty("dllFile")!=null){
					return (File)this.getUMLDataModel().getElementProperty("dllFile");
				}
			}
			
//		}
		return null;
	}
	public void setDllFile(File dllFile) {
		this.dllFile = dllFile;
		this.getUMLDataModel().setElementProperty("dllFile", dllFile);
		if(dllFile!=null){
			String df = dllFile.toString();
			System.out.println("df:"+df);
			this.getUMLDataModel().setProperty("dllFile_str", df);
		}
		
	}
	public String getFsmFileName() {
		return (String)this.getUMLDataModel().getElementProperty("fsmFileName");
	}
	
	public String getDllFileName() {
		return (String)this.getUMLDataModel().getElementProperty("dllFileName");
	}
	
	public void setStrDllFile(String dllFile){
		this.getUMLDataModel().setProperty("dllFile_str", dllFile);
	}
	
	public void setStrFile(String file){
		this.getUMLDataModel().setProperty("file_str", file);
	}
	
	public void setStrFsmFile(String file){
		this.getUMLDataModel().setProperty("fsmFile_str", file);
	}
	
	public void setStrDirFile(String file){
		this.getUMLDataModel().setProperty("dir_str", file);
	}
	
	
	
	
	public void setFsmFileName(String fsmFileName) {
		this.fsmFileName = fsmFileName;
		this.getUMLDataModel().setElementProperty("fsmFileName", fsmFileName);
	}
	
	public void setDllFileName(String dllFileName) {
		
		this.getUMLDataModel().setElementProperty("dllFileName", dllFileName);
	}

    public Object clone() {
        ComponentModel clone = new ComponentModel((UMLDataModel)this.getUMLDataModel().clone());
        //		clone.temp_childs = this.activityModel.getChildren();
//        for (int i = 0; i < this.boundaryModel.getChildren().size(); i++) {
//            UMLModel um = (UMLModel)this.boundaryModel.getChildren().get(i);
//            clone.boundaryModel.addChild((UMLElementModel)um.clone());
//        }
        clone.setPortManager(this.getPortManager().clone(clone));
        clone.setSize(this.getSize());
        clone.setBackGroundColor(this.getBackGroundColor());
        clone.setLocation(this.getLocation());
        clone.sourceModel = this;
        ProjectManager.getInstance().setTempCopyMap(clone.sourceModel.getID(), clone);
        for (int i1 = 0; i1 < this.getSourceConnections().size(); i1++) {
            LineModel li = (LineModel)this.getSourceConnections().get(i1);
            LineModel liCopy = (LineModel)li.clone();
            ProjectManager.getInstance().addSelectLineModel(liCopy);
        }
        return clone;
    }
}
