package kr.co.n3soft.n3com.model.component;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import kr.co.n3soft.n3com.OPRoSProjectInfo;
import kr.co.n3soft.n3com.OPRoSUtil;
import kr.co.n3soft.n3com.model.comm.DataInputPortModel;
import kr.co.n3soft.n3com.model.comm.DataOutputPortModel;
import kr.co.n3soft.n3com.model.comm.EventInputPortModel;
import kr.co.n3soft.n3com.model.comm.EventOutputPortModel;
import kr.co.n3soft.n3com.model.comm.MethodInputPortModel;
import kr.co.n3soft.n3com.model.comm.MethodOutputPortModel;
import kr.co.n3soft.n3com.model.comm.PortModel;
import kr.co.n3soft.n3com.model.comm.TypeRefModel;
import kr.co.n3soft.n3com.model.comm.UMLDataModel;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.diagram.N3EditorDiagramModel;
import kr.co.n3soft.n3com.project.browser.UMLTreeModel;
import kr.co.n3soft.n3com.project.browser.UMLTreeParentModel;
import kr.co.n3soft.n3com.project.dialog.DetailPropertyTableItem;
import kr.co.n3soft.n3com.projectmanager.ComponentProfile;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

public class AtomicComponentModel extends ComponentModel{
	
	public String rn = "\r\n";
	
	public AtomicComponentModel coreModel = null;

	OPRoSProjectInfo info = null;
	
	boolean isExport = false;
	
//	WJH 100909 S 블럭 컴포넌트 추가
	boolean blockComponent = false;
	
	
	
	
	//20110822 SDM S 컴포저에서 포트 변경 및 삭제 정보를 담을 List객체
	private List ltReName = new ArrayList();	//20110822 SDM
	private List ltDelete = new ArrayList();	//20110822 SDM
	
	public List getReNameList(){
		return ltReName;
	}
	
	public List getDeleteList(){
		return ltDelete;
	}
	
	public void setReNameList(String re){
		ltReName.add(re);
	}
	
	public void setReNameList(String oldName, String newName){
		int index = ltReName.indexOf(oldName);
		ltReName.set(index, newName);
	}
	
	public void setDeleteList(String del){
		ltReName.add(del);
	}
	
	public boolean isBlockComponent() {
		System.out.println("this.id : "+this.getId());		
		return blockComponent;
	}

	public void setBlockComponent(boolean blockComponent) {
		System.out.println("this.id : "+this.getId());
		this.blockComponent = blockComponent;
	}
	
	
//	public AtomicComponentModel getCoreModel() {
//		return coreModel;
//	}
//
//
//	public void setCoreModel(AtomicComponentModel coreModel) {
//		this.coreModel = coreModel;
//	}
	public AtomicComponentModel getCoreDiagramCmpModel() {
		UMLTreeParentModel ut  = (UMLTreeParentModel)this.getUMLDataModel().getElementProperty("CoreUMLTreeModel");
		if(ut!=null){
		for(int i=0;i<ut.getChildren().length;i++){
				UMLTreeModel obj = (UMLTreeModel)ut.getChildren()[i];
				
				if(obj.getRefModel() instanceof N3EditorDiagramModel){
					N3EditorDiagramModel uc = (N3EditorDiagramModel)obj.getRefModel();
	
	
					for(int i1=0;i1<uc.getChildren().size();i1++){
						Object obj1 = uc.getChildren().get(i1);
						if(obj1 instanceof AtomicComponentModel){
							return (AtomicComponentModel)obj1;
						}
					}
					
				}
			}
//		this.getCmpFolder()
		}
		else{
			 ut  = (UMLTreeParentModel)this.getUMLTreeModel();
			 for(int i=0;i<ut.getChildren().length;i++){
					UMLTreeModel obj = (UMLTreeModel)ut.getChildren()[i];
					
					if(obj.getRefModel() instanceof N3EditorDiagramModel){
						N3EditorDiagramModel uc = (N3EditorDiagramModel)obj.getRefModel();
		
		
						for(int i1=0;i1<uc.getChildren().size();i1++){
							Object obj1 = uc.getChildren().get(i1);
							if(obj1 instanceof AtomicComponentModel){
								return (AtomicComponentModel)obj1;
							}
						}
						
					}
				}
		}
		return null;
	}
	
	//20110721서동민>>코어수정
	public void setCoreDiagramCmpModel(AtomicComponentModel newAtCmp) {
		UMLTreeModel nobj = new UMLTreeModel(null);
		N3EditorDiagramModel nuc = new N3EditorDiagramModel();
		UMLTreeParentModel oldut = new UMLTreeParentModel(null);

		
		UMLTreeParentModel ut  = (UMLTreeParentModel)this.getUMLDataModel().getElementProperty("CoreUMLTreeModel");
		oldut = ut;
		
		if(ut!=null){
		for(int i=0;i<ut.getChildren().length;i++){
				UMLTreeModel obj = (UMLTreeModel)ut.getChildren()[i];
				nobj = obj;
				
				if(obj.getRefModel() instanceof N3EditorDiagramModel){
					N3EditorDiagramModel uc = (N3EditorDiagramModel)obj.getRefModel();
					nuc = uc;
	
					for(int i1=0;i1<uc.getChildren().size();i1++){
						Object obj1 = uc.getChildren().get(i1);
						if(obj1 instanceof AtomicComponentModel){
							
							nuc.modChildrenItem(i1, newAtCmp);
							
							
							
							
							
						}
					}
					
					nobj.setRefModel(nuc);
					this.setCoreUMLTreeModel(nobj);
					
					
				}
				ut.modChildrenItem(obj, nobj);
			}

		this.getUMLDataModel().setElementProperty("CoreUMLTreeModel", ut);
//		ProjectManager.getInstance().modTypeModel(oldut, ut);
		
//		this.getCmpFolder()
		}
		else{
			 ut  = (UMLTreeParentModel)this.getUMLTreeModel();
			 for(int i=0;i<ut.getChildren().length;i++){
					UMLTreeModel obj = (UMLTreeModel)ut.getChildren()[i];
					
					if(obj.getRefModel() instanceof N3EditorDiagramModel){
						N3EditorDiagramModel uc = (N3EditorDiagramModel)obj.getRefModel();
		
		
						for(int i1=0;i1<uc.getChildren().size();i1++){
							Object obj1 = uc.getChildren().get(i1);
							if(obj1 instanceof AtomicComponentModel){
							}
						}
						
					}
				}
		}
	}
	
//	public void removeAllModel(){
////		UMLTreeModel ut = this.getCoreUMLTreeModel();
////		if(ut==null){
//		
////		AtomicComponentModel atm = (AtomicComponentModel)ut.getRefModel();
//		 ProjectManager.getInstance().getModelBrowser().searchComponentDiagramIdModel(this.getUMLDataModel().getId());
// 		java.util.ArrayList al = ProjectManager.getInstance().getSearchModel();
// 		System.out.println("dddd");
// 		for(int i = 0; i<al.size();i++){
// 			UMLModel um1 = (UMLModel)al.get(i);
// 			if(um1!=this){
//     				if(um1 instanceof AtomicComponentModel){
//     					 deleteConnections(um1);
//     		            detachFromGuides(um1);
//     		            index = parent.getChildren().indexOf(child);
//     		            try{
//     		            parent.removeChild(child);
//     		            }
//     		            catch(Exception e){
//     		            	e.printStackTrace();
//     		            }
//     				}
//     					
//     				
//
// 				
// 			}
// 		}
////		}
//	}
	
	
	
	public void removePortTreeModel(PortModel pm){
		UMLTreeModel treeObject =this.getCorePortUMLTreeModel(pm);
		if(treeObject!=null){
			  ProjectManager.getInstance().deleteUMLModel(treeObject);

				ProjectManager.getInstance().removeUMLNode(treeObject.getParent(), treeObject);
		}
	}
	
	public UMLTreeModel getCorePortUMLTreeModel(PortModel pm) {
		UMLTreeParentModel ut =  (UMLTreeParentModel)this.getUMLDataModel().getElementProperty("CoreUMLTreeModel");
		if(ut!=null){
		for(int i=0;i<ut.getChildren().length;i++){
			UMLTreeModel ch = (UMLTreeModel)ut.getChildren()[i];
			if(ch.getRefModel() instanceof PortModel){
				PortModel pm1 = (PortModel)ch.getRefModel();
				if(pm1.getId().equals(pm.getId())){
					return ch;
				}
			}
		}
		}
		else{
			 ut =  (UMLTreeParentModel)this.getUMLTreeModel();
			for(int i=0;i<ut.getChildren().length;i++){
				UMLTreeModel ch = (UMLTreeModel)ut.getChildren()[i];
				if(ch.getRefModel() instanceof PortModel){
					PortModel pm1 = (PortModel)ch.getRefModel();
					if(pm1.getId().equals(pm.getId())){
						return ch;
					}
				}
			}
		}
		return null;
//			return instanceUMLTreeModel;
		}
	
	public UMLTreeModel getCoreUMLTreeModel() {
		return (UMLTreeModel)this.getUMLDataModel().getElementProperty("CoreUMLTreeModel");
//			return instanceUMLTreeModel;
		}
		public void setCoreUMLTreeModel(UMLTreeModel instanceUMLTreeModel) {
//			this.instanceUMLTreeModel = instanceUMLTreeModel;
			this.getUMLDataModel().setElementProperty("CoreUMLTreeModel", instanceUMLTreeModel);
//			if(instanceUMLTreeModel!=null)
//			this.getUMLDataModel().setProperty("INSTANCE_NAME", instanceUMLTreeModel.getName());
		}


	public AtomicComponentModel(UMLDataModel p,boolean ist) {
		super(p,ist);
		OPRoSExeSemanticsElementModel sm = (OPRoSExeSemanticsElementModel)this.getClassModel().getUMLDataModel().getElementProperty("OPRoSExeSemanticsElementModel");
		OPRoSExeEnvironmentElementModel am = (OPRoSExeEnvironmentElementModel)this.getClassModel().getUMLDataModel().getElementProperty("OPRoSExeEnvironmentElementModel");
		OPRoSMonitorVariablesElementModel mvs = (OPRoSMonitorVariablesElementModel)this.getClassModel().getUMLDataModel().getElementProperty("OPRoSMonitorVariablesElementModel");
		OPRoSDataTypesElementModel dmts = (OPRoSDataTypesElementModel)this.getClassModel().getUMLDataModel().getElementProperty("OPRoSDataTypesElementModel");
		OPRoSServiceTypesElementModel smts = (OPRoSServiceTypesElementModel)this.getClassModel().getUMLDataModel().getElementProperty("OPRoSServiceTypesElementModel");
//		OPRoSMonitorVariablesElementModel mvs = (OPRoSMonitorVariablesElementModel)this.getClassModel().getUMLDataModel().getElementProperty("OPRoSMonitorVariablesElementModel");
		if(am!=null && sm!=null && smts!=null && dmts!=null){
			this.addChild(sm);
			this.addChild(am);
			
			this.addChild(mvs);
			this.addChild(dmts);
			this.addChild(smts);
		}
		else{
			OPRoSExeSemanticsElementModel sm1 = new OPRoSExeSemanticsElementModel();
			OPRoSExeEnvironmentElementModel am1 = new OPRoSExeEnvironmentElementModel();
			OPRoSMonitorVariablesElementModel mvs1 = new OPRoSMonitorVariablesElementModel();
			OPRoSPropertiesElementModel pms1 = new OPRoSPropertiesElementModel();
			OPRoSDataTypesElementModel dmts1 = new OPRoSDataTypesElementModel();
			OPRoSServiceTypesElementModel smts1 = new OPRoSServiceTypesElementModel();
			Object obj = p.getElementProperty("OPRoSDataTypesElementModel");
			if(obj instanceof java.util.ArrayList){
				java.util.ArrayList list = (java.util.ArrayList)obj;
				for(int i=0;i<list.size();i++){
					UMLModel  obj1 = (UMLModel)list.get(i);
					if(obj1 instanceof OPRoSDataTypeElementModel){
						String str = this.getCmpFolder();
						obj1.setCmpFolder(str+File.separator+"profile");
					}
//					obj1.setCmpFolder(this.getCmpFolder()+File.separator+"profile");
					dmts1.addChild(obj1);
				}
			}
			obj = p.getElementProperty("OPRoSExeEnvironmentElementModel");
			if(obj instanceof java.util.ArrayList){
				java.util.ArrayList list = (java.util.ArrayList)obj;
				for(int i=0;i<list.size();i++){
					UMLModel  obj1 = (UMLModel)list.get(i);
					
					am1.addChild(obj1);
				}
			}
			obj = p.getElementProperty("OPRoSServiceTypesElementModel");
			if(obj instanceof java.util.ArrayList){
				java.util.ArrayList list = (java.util.ArrayList)obj;
				for(int i=0;i<list.size();i++){
					UMLModel  obj1 = (UMLModel)list.get(i);
//					obj1.setCmpFolder(this.getCmpFolder()+File.separator+"profile");
					if(obj1 instanceof OPRoSServiceTypeElementModel){
						String str = this.getCmpFolder();
						obj1.setCmpFolder(str+File.separator+"profile");
					}
					smts1.addChild(obj1);
				}
			}
			obj = p.getElementProperty("OPRoSMonitorVariablesElementModel");
			if(obj instanceof java.util.ArrayList){
				java.util.ArrayList list = (java.util.ArrayList)obj;
				for(int i=0;i<list.size();i++){
					UMLModel  obj1 = (UMLModel)list.get(i);
					
					mvs1.addChild(obj1);
				}
			}
			
			this.addChild(sm1);
			this.addChild(am1);
			this.addChild(pms1);
			this.addChild(dmts1);
			this.addChild(smts1);
			this.addChild(mvs1);
			this.getClassModel().getUMLDataModel().setElementProperty("OPRoSExeSemanticsElementModel", sm1);
			this.getClassModel().getUMLDataModel().setElementProperty("OPRoSExeEnvironmentElementModel", am1);
			this.getClassModel().getUMLDataModel().setElementProperty("OPRoSPropertiesElementModel", pms1);
			this.getClassModel().getUMLDataModel().setElementProperty("OPRoSDataTypesElementModel", dmts1);
			this.getClassModel().getUMLDataModel().setElementProperty("OPRoSMonitorVariablesElementModel", mvs1);
			this.getClassModel().getUMLDataModel().setElementProperty("OPRoSServiceTypesElementModel", smts1);
			
			
		}
		
		
		
	}
	
	
	public void addOPRoS(TypeRefModel p,String path){
		OPRoSExeSemanticsElementModel sm = (OPRoSExeSemanticsElementModel)p.getUMLDataModel().getElementProperty("OPRoSExeSemanticsElementModel");
		OPRoSExeEnvironmentElementModel am = (OPRoSExeEnvironmentElementModel)p.getUMLDataModel().getElementProperty("OPRoSExeEnvironmentElementModel");
		OPRoSMonitorVariablesElementModel mvs = (OPRoSMonitorVariablesElementModel)p.getUMLDataModel().getElementProperty("OPRoSMonitorVariablesElementModel");
		OPRoSDataTypesElementModel dmts = (OPRoSDataTypesElementModel)p.getUMLDataModel().getElementProperty("OPRoSDataTypesElementModel");
		OPRoSServiceTypesElementModel smts = (OPRoSServiceTypesElementModel)p.getUMLDataModel().getElementProperty("OPRoSServiceTypesElementModel");
		if(am!=null && sm!=null &&  smts!=null && dmts!=null){
			this.addChild(sm);
			this.addChild(am);
			
			this.addChild(mvs);
			this.addChild(dmts);
			this.addChild(smts);
//			Object obj = p.getElementProperty("OPRoSServiceTypesElementModel");
//			if(obj instanceof java.util.ArrayList){
//				java.util.ArrayList list = (java.util.ArrayList)obj;
				for(int i=0;i<smts.getChildren().size();i++){
					UMLModel  obj1 = (UMLModel)smts.getChildren().get(i);
					if(obj1 instanceof OPRoSServiceTypeElementModel){
						String str = path;
						obj1.setCmpFolder(path+File.separator+"profile");
					}
//					smts1.addChild(obj1);
				}
				for(int i=0;i<dmts.getChildren().size();i++){
					UMLModel  obj1 = (UMLModel)dmts.getChildren().get(i);
					if(obj1 instanceof OPRoSDataTypeElementModel){
						String str = path;
						obj1.setCmpFolder(path+File.separator+"profile");
					}
//					smts1.addChild(obj1);
				}
//			}
			this.getClassModel().getUMLDataModel().setElementProperty("OPRoSExeSemanticsElementModel", sm);
			this.getClassModel().getUMLDataModel().setElementProperty("OPRoSExeEnvironmentElementModel", am);
			this.getClassModel().getUMLDataModel().setElementProperty("OPRoSMonitorVariablesElementModel", mvs);
			this.getClassModel().getUMLDataModel().setElementProperty("OPRoSDataTypesElementModel", dmts);
			this.getClassModel().getUMLDataModel().setElementProperty("OPRoSServiceTypesElementModel", smts);
		}
	}
	
	
	public void addOPRoS(String path,java.util.ArrayList mv){
		OPRoSExeSemanticsElementModel sm = (OPRoSExeSemanticsElementModel)this.getClassModel().getUMLDataModel().getElementProperty("OPRoSExeSemanticsElementModel");
		OPRoSExeEnvironmentElementModel am = (OPRoSExeEnvironmentElementModel)this.getClassModel().getUMLDataModel().getElementProperty("OPRoSExeEnvironmentElementModel");
		OPRoSMonitorVariablesElementModel mvs = (OPRoSMonitorVariablesElementModel)this.getUMLDataModel().getElementProperty("OPRoSMonitorVariablesElementModel");
		OPRoSDataTypesElementModel dmts = (OPRoSDataTypesElementModel)this.getClassModel().getUMLDataModel().getElementProperty("OPRoSDataTypesElementModel");
		OPRoSServiceTypesElementModel smts = (OPRoSServiceTypesElementModel)this.getClassModel().getUMLDataModel().getElementProperty("OPRoSServiceTypesElementModel");
		if(am!=null && sm!=null &&  smts!=null && dmts!=null){
//			this.addChild(sm);
//			this.addChild(am);
//			
////			this.addChild(pms);
//			this.addChild(dmts);
//			this.addChild(smts);
//			Object obj = p.getElementProperty("OPRoSServiceTypesElementModel");
//			if(obj instanceof java.util.ArrayList){
//				java.util.ArrayList list = (java.util.ArrayList)obj;
				for(int i=0;i<smts.getChildren().size();i++){
					UMLModel  obj1 = (UMLModel)smts.getChildren().get(i);
					if(obj1 instanceof OPRoSServiceTypeElementModel){
						String str = path;
						obj1.setCmpFolder(path+File.separator+"profile");
					}
//					smts1.addChild(obj1);
				}
				for(int i=0;i<dmts.getChildren().size();i++){
					UMLModel  obj1 = (UMLModel)dmts.getChildren().get(i);
					if(obj1 instanceof OPRoSDataTypeElementModel){
						String str = path;
						obj1.setCmpFolder(path+File.separator+"profile");
					}
//					smts1.addChild(obj1);
				}
				this.setMonitorVariables3(mv);
//			}
			/*this.getClassModel().getUMLDataModel().setElementProperty("OPRoSExeSemanticsElementModel", sm);
			this.getClassModel().getUMLDataModel().setElementProperty("OPRoSExeEnvironmentElementModel", am);
//			this.getClassModel().getUMLDataModel().setElementProperty("OPRoSPropertiesElementModel", pms);
			this.getClassModel().getUMLDataModel().setElementProperty("OPRoSDataTypesElementModel", dmts);
			this.getClassModel().getUMLDataModel().setElementProperty("OPRoSServiceTypesElementModel", smts);*/
		}
	}
	
	
	
	
	public AtomicComponentModel(UMLDataModel p,int isClone) {
		super(p);
		
//		OPRoSExeSemanticsElementModel sm = (OPRoSExeSemanticsElementModel)this.getClassModel().getUMLDataModel().getElementProperty("OPRoSExeSemanticsElementModel");
//		OPRoSExeEnvironmentElementModel am = (OPRoSExeEnvironmentElementModel)this.getClassModel().getUMLDataModel().getElementProperty("OPRoSExeEnvironmentElementModel");
		OPRoSPropertiesElementModel pms1 = new OPRoSPropertiesElementModel();
//		Object obj = p.getElementProperty("OPRoSPropertiesElementModel");
//		if(obj instanceof java.util.ArrayList){
//			java.util.ArrayList list = (java.util.ArrayList)obj;
//			for(int i=0;i<list.size();i++){
//				UMLModel  obj1 = (UMLModel)list.get(i);
//				
//				pms1.addChild(obj1);
//			}
//		}
		this.getClassModel().getUMLDataModel().setElementProperty("OPRoSPropertiesElementModel", pms1);
		
		//		OPRoSDataTypesElementModel dmts = (OPRoSDataTypesElementModel)this.getClassModel().getUMLDataModel().getElementProperty("OPRoSDataTypesElementModel");
//		OPRoSServiceTypesElementModel smts = (OPRoSServiceTypesElementModel)this.getClassModel().getUMLDataModel().getElementProperty("OPRoSServiceTypesElementModel");
//		if(am!=null && sm!=null && pms!=null && smts!=null && dmts!=null){
//			this.addChild(sm);
//			this.addChild(am);
//			this.addChild(pms);
//			this.addChild(dmts);
//			this.addChild(smts);
//		}
//		else{
//			OPRoSExeSemanticsElementModel sm1 = new OPRoSExeSemanticsElementModel();
//			OPRoSExeEnvironmentElementModel am1 = new OPRoSExeEnvironmentElementModel();
//			
//			OPRoSPropertiesElementModel pms1 = new OPRoSPropertiesElementModel();
//			OPRoSDataTypesElementModel dmts1 = new OPRoSDataTypesElementModel();
//			OPRoSServiceTypesElementModel smts1 = new OPRoSServiceTypesElementModel();
//			
//			this.addChild(sm1);
//			this.addChild(am1);
//			this.addChild(pms1);
//			this.addChild(dmts1);
//			this.addChild(smts1);
//			this.getClassModel().getUMLDataModel().setElementProperty("OPRoSExeSemanticsElementModel", sm1);
//			this.getClassModel().getUMLDataModel().setElementProperty("OPRoSExeEnvironmentElementModel", am1);
//			this.getClassModel().getUMLDataModel().setElementProperty("OPRoSPropertiesElementModel", pms1);
//			this.getClassModel().getUMLDataModel().setElementProperty("OPRoSDataTypesElementModel", dmts1);
//			this.getClassModel().getUMLDataModel().setElementProperty("OPRoSServiceTypesElementModel", smts1);
//		}
//		AtomicComponentModel t = this.getCoreDiagramCmpModel();
//		if(t!=null){
//			OPRoSExeSemanticsElementModel sm1 = (OPRoSExeSemanticsElementModel)t.getClassModel().getUMLDataModel().getElementProperty("OPRoSExeSemanticsElementModel");
//			OPRoSExeEnvironmentElementModel am1 = (OPRoSExeEnvironmentElementModel)t.getClassModel().getUMLDataModel().getElementProperty("OPRoSExeEnvironmentElementModel");
//			OPRoSPropertiesElementModel pms1 = (OPRoSPropertiesElementModel)t.getClassModel().getUMLDataModel().getElementProperty("OPRoSPropertiesElementModel");
//			OPRoSDataTypesElementModel dmts1 = (OPRoSDataTypesElementModel)t.getClassModel().getUMLDataModel().getElementProperty("OPRoSDataTypesElementModel");
//			OPRoSServiceTypesElementModel smts1 = (OPRoSServiceTypesElementModel)t.getClassModel().getUMLDataModel().getElementProperty("OPRoSServiceTypesElementModel");
//			
//			this.getClassModel().getUMLDataModel().setElementProperty("OPRoSExeSemanticsElementModel", sm1);
//			this.getClassModel().getUMLDataModel().setElementProperty("OPRoSExeEnvironmentElementModel", am1);
//			this.getClassModel().getUMLDataModel().setElementProperty("OPRoSPropertiesElementModel", pms1);
//			this.getClassModel().getUMLDataModel().setElementProperty("OPRoSDataTypesElementModel", dmts1);
//			this.getClassModel().getUMLDataModel().setElementProperty("OPRoSServiceTypesElementModel", smts1);
//			if(am1!=null && sm1!=null && pms1!=null && smts1!=null && dmts1!=null){
//				this.addChild(sm1);
//				this.addChild(am1);
//				this.addChild(pms1);
//				this.addChild(dmts1);
//				this.addChild(smts1);
//			}
//			else{
//				
//			}
//		}
		
		
		
	}
	
	
	public AtomicComponentModel(UMLDataModel p) {
		super(p);
		OPRoSExeSemanticsElementModel sm = (OPRoSExeSemanticsElementModel)this.getClassModel().getUMLDataModel().getElementProperty("OPRoSExeSemanticsElementModel");
		OPRoSExeEnvironmentElementModel am = (OPRoSExeEnvironmentElementModel)this.getClassModel().getUMLDataModel().getElementProperty("OPRoSExeEnvironmentElementModel");
		
		OPRoSMonitorVariablesElementModel mvs = (OPRoSMonitorVariablesElementModel)this.getClassModel().getUMLDataModel().getElementProperty("OPRoSMonitorVariablesElementModel");
		
		
		OPRoSPropertiesElementModel pms = (OPRoSPropertiesElementModel)this.getClassModel().getUMLDataModel().getElementProperty("OPRoSPropertiesElementModel");
		OPRoSPropertiesElementModel pmsc = null;
		if(pms!=null)
		 pmsc = pms.clone();
		this.getClassModel().getUMLDataModel().setElementProperty("OPRoSPropertiesElementModel",pmsc);
		
		OPRoSDataTypesElementModel dmts = (OPRoSDataTypesElementModel)this.getClassModel().getUMLDataModel().getElementProperty("OPRoSDataTypesElementModel");
		OPRoSServiceTypesElementModel smts = (OPRoSServiceTypesElementModel)this.getClassModel().getUMLDataModel().getElementProperty("OPRoSServiceTypesElementModel");
		if(am!=null && sm!=null && smts!=null && dmts!=null && mvs!=null){
			this.addChild(sm);
			this.addChild(am);
			if(pmsc!=null)
			this.addChild(pmsc);
			this.addChild(dmts);
			this.addChild(smts);
			this.addChild(mvs);
			if(am.getChildren().size()==0){
				OPRoSExeEnvironmentOSElementModel os = new OPRoSExeEnvironmentOSElementModel();
				os.setOs("");
				os.setOsVersion("");
				OPRoSExeEnvironmentCPUElementModel cpu = new OPRoSExeEnvironmentCPUElementModel();
				cpu.setCpu("");
				am.addChild(os);
				am.addChild(cpu);
			}
		}
		else{
			OPRoSExeSemanticsElementModel sm1 = new OPRoSExeSemanticsElementModel();
			OPRoSExeEnvironmentElementModel am1 = new OPRoSExeEnvironmentElementModel();
			OPRoSMonitorVariablesElementModel mvs1 = new OPRoSMonitorVariablesElementModel();
			OPRoSPropertiesElementModel pms1 = new OPRoSPropertiesElementModel();
			OPRoSDataTypesElementModel dmts1 = new OPRoSDataTypesElementModel();
			OPRoSServiceTypesElementModel smts1 = new OPRoSServiceTypesElementModel();
//			OPRoSExeEnvironmentOSElementModel os = new OPRoSExeEnvironmentOSElementModel();
//			os.setOs("NoNameOS");
//			os.setOsVersion("NoVersion");
//			OPRoSExeEnvironmentCPUElementModel cpu = new OPRoSExeEnvironmentCPUElementModel();
//			cpu.setCpu("NoCPU");
//			am1.addChild(os);
//			am1.addChild(cpu);
			this.addChild(sm1);
			this.addChild(am1);
			this.addChild(pms1);
			this.addChild(dmts1);
			this.addChild(smts1);
			this.addChild(mvs1);
			this.getClassModel().getUMLDataModel().setElementProperty("OPRoSExeSemanticsElementModel", sm1);
			this.getClassModel().getUMLDataModel().setElementProperty("OPRoSExeEnvironmentElementModel", am1);
			this.getClassModel().getUMLDataModel().setElementProperty("OPRoSPropertiesElementModel", pms1);
			this.getClassModel().getUMLDataModel().setElementProperty("OPRoSDataTypesElementModel", dmts1);
			this.getClassModel().getUMLDataModel().setElementProperty("OPRoSServiceTypesElementModel", smts1);
			this.getClassModel().getUMLDataModel().setElementProperty("OPRoSMonitorVariablesElementModel", mvs1);
		}
		
		
	}
	
	public AtomicComponentModel() {
		super();
		OPRoSExeSemanticsElementModel sm1 = new OPRoSExeSemanticsElementModel();
		OPRoSExeEnvironmentElementModel am1 = new OPRoSExeEnvironmentElementModel();
		
		OPRoSPropertiesElementModel pms1 = new OPRoSPropertiesElementModel();
		OPRoSDataTypesElementModel dmts1 = new OPRoSDataTypesElementModel();
		OPRoSServiceTypesElementModel smts1 = new OPRoSServiceTypesElementModel();
		OPRoSMonitorVariablesElementModel mvs = new OPRoSMonitorVariablesElementModel();
		
		this.addChild(sm1);
		this.addChild(am1);
		this.addChild(pms1);
		this.addChild(dmts1);
		this.addChild(smts1);
		this.addChild(mvs);
		this.getClassModel().getUMLDataModel().setElementProperty("OPRoSExeSemanticsElementModel", sm1);
		this.getClassModel().getUMLDataModel().setElementProperty("OPRoSExeEnvironmentElementModel", am1);
		this.getClassModel().getUMLDataModel().setElementProperty("OPRoSPropertiesElementModel", pms1);
		this.getClassModel().getUMLDataModel().setElementProperty("OPRoSDataTypesElementModel", dmts1);
		this.getClassModel().getUMLDataModel().setElementProperty("OPRoSServiceTypesElementModel", smts1);
		this.getClassModel().getUMLDataModel().setElementProperty("OPRoSMonitorVariablesElementModel", mvs);
		
	}
	
	public void setOPRoSData(UMLDataModel p){
		
	}
	
	public List getServiceTypesModel(){
		OPRoSServiceTypesElementModel a = (OPRoSServiceTypesElementModel)this.getClassModel().getUMLDataModel().getElementProperty("OPRoSServiceTypesElementModel");
//		a.getChildren().
//		a.getChildren()
		return a.getChildren();
		
	}
	
	public void writeSourceCPP(){
		StringBuffer sb = new StringBuffer();
		
		sb.append("/*"+rn);
		sb.append(" *  Generated sources by OPRoS Component Generator (OCG V2)"+rn);
		sb.append(" *  "+rn);
		sb.append("*/"+rn);
		
		sb.append("#include <Component.h>"+rn);
		sb.append("#include <InputDataPort.h>"+rn);
		sb.append("#include <OutputDataPort.h>"+rn);
		sb.append("#include <InputEventPort.h>"+rn);
		sb.append("#include <OutputEventPort.h>"+rn);
		sb.append("#include <OPRoSTypes.h>"+rn);
		sb.append("#include <EventData.h>"+rn+rn);
		
		for(int i=0;i<this.getPortManager().getPorts().size();i++){
			PortModel pm = (PortModel)this.getPortManager().getPorts().get(i);
			if((pm instanceof MethodInputPortModel) || (pm instanceof MethodOutputPortModel)){
				sb.append(pm.wirteServicePortSourceHeader());
				sb.append(rn);
			}
		}
		
//		 WJH 100614 S 추가 작업
		sb.append(rn);
		sb.append("#include \""+ this.getName()+".h\""+rn);
		
		if(isBlockComponent()){//				WJH 100909 S 블럭 컴포넌트 추가
//			WJH 100729 S 소스코드 수정된 부분
			sb.append("//////////////By ERC ///////////////////////////////////"+rn);
			sb.append("#ifdef THIS_IS_CALL"+rn);
			sb.append("#undef THIS_IS_CALL"+rn);
			sb.append("#endif"+rn);
			sb.append("#include \"call_Val.h\""+rn);
			sb.append("/*"+rn);
			sb.append("* External Functions"+rn);
			sb.append("* All generated code (ex:model.c) has these functions"+rn);
			sb.append("*/"+rn);
			sb.append("#ifdef __cplusplus"+rn);
			sb.append("//extern \"C\" {"+rn);
			sb.append("#endif"+rn);
			sb.append("void Initialize( );"+rn);
			sb.append("void SetInitialState( );"+rn);
			sb.append("void EvalOutput( );"+rn);
			sb.append("void UpdateOutput( );"+rn);
			sb.append("void StateUpdate( );"+rn);
			sb.append("void Terminate();"+rn);
			sb.append("/*"+rn);
			sb.append("* External functions for data logging"+rn);
			sb.append("*/"+rn);
			sb.append("void StopLog();"+rn);
			sb.append("void DestroyLogVar();"+rn);
			sb.append("/*"+rn);
			sb.append("* External function for ODE solver."+rn);
			sb.append("* We provide 4 different ODE Solver (Euler, rk3, rk4, Adams)"+rn);
			sb.append("*/"+rn);
			sb.append("void UpdateContinuousState();"+rn);
			sb.append("/*"+rn);
			sb.append("* Global Variables"+rn);
			sb.append("*"+rn);
			sb.append("*/"+rn);
			sb.append("double start, end, step, t, scale;"+rn);
			sb.append("int numofcstate, numofscope, numofoutblock;"+rn);
			sb.append("char sys_name[128]; //"+rn);
			sb.append("int IsMajorTimeStep = 1;"+rn);
			sb.append("int PulseBlk_Bset1=0;"+rn);
			sb.append("int PulseBlk_Bset2=0;"+rn);
			sb.append("#ifdef __cplusplus"+rn);
			sb.append("//}"+rn);
			sb.append("#endif"+rn);
			sb.append("///////////////////////////////////////////////////////////"+rn);
//			WJH 100729 E 소스코드 수정된 부분
			}
		
		sb.append("//"+rn);
		sb.append("// constructor declaration"+rn);
		sb.append("//"+rn);
		
		sb.append(this.getName()+"::"+this.getName()+"()"+rn);
		sb.append(writeConstructor());
		
		sb.append("//"+rn);
		sb.append("// constructor declaration (with name)"+rn);
		sb.append("//"+rn);
		
		sb.append(this.getName()+"::"+this.getName()+"(const std::string &name):Component(name)"+rn);
		sb.append(writeConstructor2());
		
		sb.append("//"+rn);
		sb.append("// destructor declaration"+rn);
		sb.append("//"+rn);
		sb.append(this.getName()+"::~"+this.getName()+"() {"+rn);
		sb.append("}"+rn+rn);

		if(isBlockComponent()){//				WJH 100909 S 블럭 컴포넌트 추가
//		WJH 100729 S 소스코드 수정된 부분
			sb.append("void "+this.getName()+"::print(double num)"+rn);
			sb.append("{"+rn);
			sb.append("\t//user code hear"+rn);
			sb.append("}"+rn);
//		WJH 100729 E 소스코드 수정된 부분
		}
		sb.append("void "+this.getName()+"::portSetup() {"+rn);
		int count = 1 ;
		sb.append(rn+"\t// provided service port setup"+rn+rn);
		for(int i=0;i<this.getPortManager().getPorts().size();i++){
			PortModel pm = (PortModel)this.getPortManager().getPorts().get(i);
			if(pm instanceof MethodOutputPortModel){
				sb.append("\tProvidedServicePort *pa"+count+";"+rn);
				sb.append("\tpa"+count+"=new "+pm.getType()+"Provided(this);"+rn);
				sb.append("\taddPort(\""+pm.getName()+"\",pa"+count+");"+rn);
				count++;
			}			
		}
		sb.append(rn);
		sb.append("\t// required service port setup"+rn);
		for(int i=0;i<this.getPortManager().getPorts().size();i++){
			PortModel pm = (PortModel)this.getPortManager().getPorts().get(i);
			if(pm instanceof MethodInputPortModel){
				sb.append("\tptr"+pm.getName()+"=new "+pm.getType()+"Required();"+rn);
				sb.append("\taddPort(\""+pm.getName()+"\",ptr"+pm.getName()+");"+rn);
				count++;
			}			
		}
		sb.append(rn);
		sb.append("\t// data port setup"+rn);
		for(int i=0;i<this.getPortManager().getPorts().size();i++){
			PortModel pm = (PortModel)this.getPortManager().getPorts().get(i);
			if((pm instanceof DataInputPortModel) || (pm instanceof DataOutputPortModel)){				
				sb.append(pm.wirteDataPortPortSetup());
				count++;
			}
		}
		sb.append(rn);
		
		sb.append("\t// event port setup"+rn);
		for(int i=0;i<this.getPortManager().getPorts().size();i++){
			PortModel pm = (PortModel)this.getPortManager().getPorts().get(i);
			if((pm instanceof EventInputPortModel) || (pm instanceof EventOutputPortModel)){
				sb.append(pm.wirteEventPortSourcePortSetup());
				sb.append(rn);
			}
		}
		sb.append("}"+rn);
		
		sb.append("// Call back Declaration"+rn);
		
		sb.append("ReturnType "+this.getName()+"::onInitialize()"+rn);
		sb.append("{"+rn);
		sb.append("\t// user code here"+rn);
		
		if(isBlockComponent()){//				WJH 100909 S 블럭 컴포넌트 추가
//			WJH 100729 S 소스코드 수정된 부분
			sb.append("\t//// Output ///////////////////////////////////////////"+rn);
			sb.append("\tInitialize();"+rn);
			sb.append("\tSetInitialState();"+rn);
			sb.append("\t///////////////////////////////////////////////////////"+rn);
//			WJH
		}
		sb.append("\treturn OPROS_SUCCESS;"+rn);
		sb.append("}"+rn+rn);
		
		sb.append("ReturnType "+this.getName()+"::onStart()"+rn);
		sb.append("{"+rn);
		sb.append("\t// user code here"+rn);
		sb.append("\treturn OPROS_SUCCESS;"+rn);
		sb.append("}"+rn+rn);
		
		sb.append("ReturnType "+this.getName()+"::onStop()"+rn);
		sb.append("{"+rn);
		sb.append("\t// user code here"+rn);
		sb.append("\treturn OPROS_SUCCESS;"+rn);
		sb.append("}"+rn+rn);
		
		sb.append("ReturnType "+this.getName()+"::onReset()"+rn);
		sb.append("{"+rn);
		sb.append("\t// user code here"+rn);
		sb.append("\treturn OPROS_SUCCESS;"+rn);
		sb.append("}"+rn+rn);
		
		sb.append("ReturnType "+this.getName()+"::onError()"+rn);
		sb.append("{"+rn);
		sb.append("\t// user code here"+rn);
		sb.append("\treturn OPROS_SUCCESS;"+rn);
		sb.append("}"+rn+rn);
		
		sb.append("ReturnType "+this.getName()+"::onRecover()"+rn);
		sb.append("{"+rn);
		sb.append("\t// user code here"+rn);
		sb.append("\treturn OPROS_SUCCESS;"+rn);
		sb.append("}"+rn+rn);
		
		sb.append("ReturnType "+this.getName()+"::onDestroy()"+rn);
		sb.append("{"+rn);
		sb.append("\t// user code here"+rn);
		
		if(isBlockComponent()){//				WJH 100909 S 블럭 컴포넌트 추가
//			WJH 100729 S 소스코드 수정된 부분
			sb.append("\t//// Output ///////////////////////////////////////////"+rn);
			sb.append("\tTerminate();"+rn);
			sb.append("\t// Start Data Logging"+rn);
			sb.append("\tStopLog();"+rn);
			sb.append("\tDestroyLogVar();"+rn);
			sb.append("\t///////////////////////////////////////////////////////"+rn);
//			WJH 100729 E 소스코드 수정된 부분
			}
		
		sb.append("\treturn OPROS_SUCCESS;"+rn);
		sb.append("}"+rn+rn);
		
		sb.append("ReturnType "+this.getName()+"::onEvent(Event *evt)"+rn);
		sb.append("{"+rn);
		sb.append("\t// user code here"+rn);
		
		if(isBlockComponent()){//				WJH 100909 S 블럭 컴포넌트 추가
//			WJH 100729 S 소스코드 수정된 부분
			sb.append("\t////// Input port ////////////////////////////"+rn);
			sb.append("\tstd::string msgid = evt->getId();"+rn);
			sb.append("\tif (msgid.compare(\"print_this\") == 0)"+rn);
			sb.append("\t{"+rn);
			sb.append("\t\ttypedef EventData<double> MyEventData;"+rn);
			sb.append("\t\tMyEventData *pdata = (MyEventData *) (evt);"+rn);
			sb.append("\t\tdouble *data = pdata->getContentData();"+rn);
			sb.append("\t}"+rn);
			sb.append("\t///////////////////////////////////////////////////////"+rn);
//			WJH 100729 E 소스코드 수정된 부분
			}
		
		sb.append("\treturn OPROS_SUCCESS;"+rn);
		sb.append("}"+rn+rn);
		
		sb.append("ReturnType "+this.getName()+"::onExecute()"+rn);
		sb.append("{"+rn);
		sb.append("\t// user code here"+rn);
		
		if(isBlockComponent()){//				WJH 100909 S 블럭 컴포넌트 추가
//			WJH 100729 S 소스코드 수정된 부분
			sb.append("\t////// Input port ////////////////////////////"+rn);
			sb.append("\t// using data port"+rn);
			sb.append("\tboost::any *pdata = ");
			String dataInPort = "";
			String dataOutPort = "";
			String eventOutPort = "";
			String serviceType = "";
			for(int i=0;i<this.getPortManager().getPorts().size();i++){
				PortModel pm = (PortModel)this.getPortManager().getPorts().get(i);			
				if(pm instanceof DataInputPortModel){
					dataInPort = pm.getName();
//					sb.append(pm.wirteDataPortPortSetup());
//					count++;
				}
				else if(pm instanceof DataOutputPortModel){
					dataOutPort = pm.getName();
				}
				else if(pm instanceof EventOutputPortModel){
					eventOutPort = pm.getName();
				}
				else if(pm instanceof MethodInputPortModel){
					serviceType = pm.getType();
				}
			}
			sb.append(dataInPort+".pop();"+rn);
			sb.append("\tdouble instr;"+rn);
			sb.append("\tif (pdata != NULL)"+rn);
			sb.append("{"+rn);
			sb.append("\t\tinstr = "+dataInPort+".getContent(*pdata); // input data"+rn);
			sb.append("\t\tdelete pdata;"+rn);
			sb.append("\t\t//////////////////////////////////////////////"+rn);
			sb.append("\t\t//// Output ///////////////////////////////////////////"+rn);
			sb.append("\t\tdouble * out ;"+rn);
			sb.append("\t\tint size;"+rn);

			sb.append("\t\tdouble * in = &instr;"+rn);

			sb.append("\t\tsave_inVal(in);"+rn);

			sb.append("\t\tdouble tnext_temp1, tnext_temp2;"+rn);

			sb.append("\t\tEvalOutput();"+rn);
			sb.append("\t\tUpdateOutput();"+rn);
			sb.append("\t\tStateUpdate();"+rn);

			sb.append("\t\tif (numofcstate > 0)"+rn);
			sb.append("\t\t\tUpdateContinuousState();"+rn);

			sb.append("\t\t/* Next Sample Hit를 계산하는 Routine */"+rn);
			sb.append("\t\ttnext_temp1 = ceil( (t/step) + 1);"+rn);
			sb.append("\t\ttnext_temp2 = (t/step) + 1;"+rn);

			sb.append("\t\tif ((tnext_temp1 - tnext_temp2) > 0.500)"+rn);
			sb.append("\t\t\tt = (tnext_temp1 - 1) * step;"+rn);
			sb.append("\t\telse"+rn);
			sb.append("\t\t\tt = tnext_temp1 * step;"+rn);

			sb.append("\t\tout= get_outVal();"+rn);
			sb.append("\t\tsize= get_outValSize();"+rn);
			sb.append("\t\t//"+rn);
			sb.append("\t\t// using data port"+rn);
			sb.append("\t\tdouble num = *out;"+rn);
			sb.append("\t\tfree(out);"+rn);

			sb.append("\t\t// num = instr;"+rn);
			sb.append("\t\t"+dataOutPort+".push(num);"+rn);

			sb.append("\t\t// using event port"+rn);
			sb.append("\t\tEventData<double> evt_data;"+rn);
			sb.append("\t\tstd::string msg_id = \"print_this\";"+rn);
			sb.append("\t\tevt_data.setId(msg_id);"+rn);
			sb.append("\t\tevt_data.setContentData(num);"+rn);

			sb.append("\t\t"+eventOutPort+".push(&evt_data);"+rn);
			sb.append("\t\t// using required method port"+rn);
			sb.append("\t\tptr"+serviceType+"->print(num);"+rn);
			sb.append("\t}"+rn);
			sb.append("\t///////////////////////////////////////////////////////"+rn);
//			WJH 100729 E 소스코드 수정된 부분		
			}
		
		sb.append("\treturn OPROS_SUCCESS;"+rn);
		sb.append("}"+rn+rn);
		
		sb.append("ReturnType "+this.getName()+"::onUpdated()"+rn);
		sb.append("{"+rn);
		sb.append("\t// user code here"+rn);
		sb.append("\treturn OPROS_SUCCESS;"+rn);
		sb.append("}"+rn+rn);
		
		sb.append("ReturnType "+this.getName()+"::onPeriodChanged()"+rn);
		sb.append("{"+rn);
		sb.append("\t// user code here"+rn);
		sb.append("\treturn OPROS_SUCCESS;"+rn);
		sb.append("}"+rn+rn);
		
		for(int i=0;i<this.getPortManager().getPorts().size();i++){
			PortModel pm = (PortModel)this.getPortManager().getPorts().get(i);
			if(pm instanceof MethodOutputPortModel){
				
				String typeRef = pm.getTypeRef();
				java.util.ArrayList list = pm.getTypeRefMethodModel(this.getCmpFolder()+File.separator+"profile"+File.separator+typeRef);
				for(int j=0;j<list.size();j++){
					MethodModel mm = (MethodModel)list.get(j);
					sb.append(mm.returnType+" "+this.getName()+"::"+mm.name+"(");
					ArrayList list2 = (ArrayList)mm.getParams();
					if(list2.size()>0){
						for(int k=0; k<list2.size(); k++){
							ParamModel para = (ParamModel)list2.get(k);
							if(k==0)
								sb.append(para.type+" "+para.name);
							else
								sb.append(","+para.type+" "+para.name);
						}
					}
//					else
//						sb.append(")"+rn);
					
					sb.append(")"+rn+"{"+rn);
					sb.append("\t// user code here"+rn);
					sb.append("}"+rn);
				}				
			}			
		}
		sb.append(rn+rn+rn);
		
		sb.append("#ifdef MAKE_COMPONENT_DLL"+rn);
		sb.append("#ifdef WIN32"+rn+rn);
		sb.append("extern \"C\""+rn);
		sb.append("{"+rn);
		sb.append("__declspec(dllexport) Component *getComponent();"+rn);
		sb.append("__declspec(dllexport) void releaseComponent(Component *pcom);"+rn);
		sb.append("}"+rn+rn);
		sb.append("Component *getComponent()"+rn);
		sb.append("{");
		sb.append("\treturn new "+this.getName()+"();"+rn);
		sb.append("}"+rn+rn);
		
		sb.append("void releaseComponent(Component *com)"+rn);
		sb.append("{"+rn);
		sb.append("\tdelete com;"+rn);
		sb.append("}"+rn+rn);
		sb.append("#else"+rn);
		sb.append("extern \"C\""+rn);
		sb.append("{"+rn);
		sb.append("\tComponent *getComponent();"+rn);
		sb.append("\tvoid releaseComponent(Component *com);"+rn);
		sb.append("}"+rn);
		sb.append("Component *getComponent()"+rn);
		sb.append("{"+rn);		
		sb.append("\treturn new "+this.getName()+"();"+rn);
		sb.append("}"+rn+rn);
		sb.append("void releaseComponent(Component *com)"+rn);
		sb.append("{"+rn);
		sb.append("\tdelete com;"+rn);
		sb.append("}"+rn);
		sb.append("#endif"+rn);
		sb.append("#endif"+rn);
		
		try{
			File  f = new File(this.getCmpFolder()+File.separator+this.getName()+".cpp");
			this.writeFile( sb,f);
		}
		catch(Exception e){
			e.printStackTrace();
		}
//		 WJH 100614 E 추가 작업
	}
	
	public void wirteSourceHeader(){
		String path = this.getCmpFolder();
		
		StringBuffer sb = new StringBuffer();
		sb.append("#ifndef _"+this.getName()+"_COMPONENT_H"+rn);
		sb.append("#define _"+this.getName()+"_COMPONENT_H"+rn);
		sb.append("/*"+rn);
		sb.append(" *  Generated sources by OPRoS Component Generator (OCG V2)"+rn);
		sb.append(" *   "+rn);
		sb.append(" */"+rn);
		sb.append("#include <Component.h>"+rn);
		sb.append("#include <InputDataPort.h>"+rn);
		sb.append("#include <OutputDataPort.h>"+rn);
		sb.append("#include <InputEventPort.h>"+rn);
		sb.append("#include <OutputEventPort.h>"+rn);
		sb.append("#include <Event.h>"+rn);
		sb.append("#include <OPRoSTypes.h>"+rn);
		
		
//		sb.append("{"+rn);
		for(int i=0;i<this.getPortManager().getPorts().size();i++){
			PortModel pm = (PortModel)this.getPortManager().getPorts().get(i);
			if((pm instanceof DataInputPortModel) || (pm instanceof DataOutputPortModel)){
				sb.append(pm.wirteDataTypeSourceHeader());
				sb.append(rn);
			}
		}
		sb.append(rn);
//		WJH 100721 S 소스 수정
		for(int i=0;i<this.getPortManager().getPorts().size();i++){
			PortModel pm = (PortModel)this.getPortManager().getPorts().get(i);
			if(pm instanceof EventOutputPortModel){
				sb.append(pm.wirteServicePortSourceBody2());
				sb.append(rn);
			}
		}
		sb.append(rn);
//		WJH 100721 E 소스 수정
		for(int i=0;i<this.getPortManager().getPorts().size();i++){
			PortModel pm = (PortModel)this.getPortManager().getPorts().get(i);
			if((pm instanceof MethodInputPortModel) || (pm instanceof MethodOutputPortModel)){
				sb.append(pm.wirteServicePortSourceHeader());
				sb.append(rn);
			}
		}
		sb.append(rn);
		
		
		sb.append("class "+this.getName());
		sb.append(": public Component"+rn);
		for(int i=0;i<this.getPortManager().getPorts().size();i++){
			PortModel pm = (PortModel)this.getPortManager().getPorts().get(i);
			if(pm instanceof MethodOutputPortModel){
				sb.append("\t,public I"+pm.getType());
			}
		}
		sb.append(rn);
		sb.append("{"+rn);
		sb.append("protected:"+rn);
		sb.append("// data"+rn+rn);
		for(int i=0;i<this.getPortManager().getPorts().size();i++){
			PortModel pm = (PortModel)this.getPortManager().getPorts().get(i);
			if((pm instanceof DataInputPortModel) || (pm instanceof DataOutputPortModel)){
				sb.append(pm.wirteDataPortSourceHeaderBody());
				sb.append(rn);
			}
		}
		sb.append(rn);
		sb.append("//event"+rn);
		for(int i=0;i<this.getPortManager().getPorts().size();i++){
			PortModel pm = (PortModel)this.getPortManager().getPorts().get(i);
			if((pm instanceof EventInputPortModel) || (pm instanceof EventOutputPortModel)){
				sb.append(pm.wirteEventPortSourceHeaderBody());
				sb.append(rn);
			}
		}
		sb.append(rn);
		sb.append("// method for provider"+rn);
		for(int i=0;i<this.getPortManager().getPorts().size();i++){
			PortModel pm = (PortModel)this.getPortManager().getPorts().get(i);
			if(pm instanceof MethodOutputPortModel){
				sb.append(pm.wirteServiceSourceHeaderBody());
				sb.append(rn);
			}
		}
		
		
		sb.append("// method for required"+rn);
		for(int i=0;i<this.getPortManager().getPorts().size();i++){
			PortModel pm = (PortModel)this.getPortManager().getPorts().get(i);
			if(pm instanceof MethodInputPortModel ){
				sb.append(pm.wirteServiceSourceHeaderBody());
				sb.append(rn);
			}
		}
		sb.append(rn);
		sb.append(rn);
		sb.append(rn);
		sb.append(rn);
		sb.append("public:"+rn);
		sb.append("\t"+this.getName()+"();"+rn);
		sb.append("\t"+this.getName()+"(const std::string &compId);"+rn);
		sb.append("\tvirtual ~"+this.getName()+"();"+rn);
		sb.append("\tvirtual void portSetup();"+rn);
		sb.append(rn);
		sb.append("protected:"+rn);
		sb.append("\tvirtual ReturnType onInitialize();"+rn);
		sb.append("\tvirtual ReturnType onStart();"+rn);
		sb.append("\tvirtual ReturnType onStop();"+rn);
		sb.append("\tvirtual ReturnType onReset();"+rn);
		sb.append("\tvirtual ReturnType onError();"+rn);
		sb.append("\tvirtual ReturnType onRecover();"+rn);
		sb.append("\tvirtual ReturnType onDestroy();"+rn);
		sb.append(rn);
		sb.append("public:"+rn);
		sb.append("\tvirtual ReturnType onEvent(Event *evt);"+rn);
		sb.append("\tvirtual ReturnType onExecute();"+rn);
		sb.append("\tvirtual ReturnType onUpdated();"+rn);
		sb.append("\tvirtual ReturnType onPeriodChanged();"+rn);
		
		for(int i=0;i<this.getPortManager().getPorts().size();i++){
			PortModel pm = (PortModel)this.getPortManager().getPorts().get(i);
			if((pm instanceof MethodOutputPortModel)){
				sb.append(pm.wirteServiceSourceHeaderBody2());
				sb.append(rn);
			}
		}
		sb.append("};"+rn);
		
		sb.append("#endif"+rn);
		try{
//			IWorkspace workspace = ResourcesPlugin.getWorkspace();//워크스페이스 핸들
//			IWorkspaceRoot root = workspace.getRoot();//워크스페이스 루트핸들
//			final IProject newProjectHandle = root.getProject(this.getName()); 
//		
//			IFolder ifolder =  newProjectHandle.getFolder(this.getName());
//			IPath ip = ifolder.getLocation();
//			String path1 = ip.toFile().getAbsolutePath();
			
			String path33 = this.getCmpFolder();
			File  f = new File(this.getCmpFolder()+File.separator+this.getName()+".h");
			this.writeFile( sb,f);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
	
	public void writeFile(StringBuffer sb,File f) throws Exception{
		
		if(!f.exists())
			f.createNewFile();
		FileWriter fw = new FileWriter(f);
	
		BufferedWriter bw=new BufferedWriter(fw);

		bw.write(sb.toString());
		bw.close();
	}
	
	
	public void writeXMLFile(Document sb,File f) throws Exception{
		if(!f.exists())
			f.createNewFile();
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
	
	public String writeConstructor2(){
		StringBuffer sb = new StringBuffer();
		int count = 0;
		for(int i=0;i<this.getPortManager().getPorts().size();i++){
			PortModel pm = (PortModel)this.getPortManager().getPorts().get(i);			
			if((pm instanceof DataInputPortModel)){
//				if(count==0){
//					sb.append(":");
//				}
//				else{
					sb.append("\t\t,");
//				}
				sb.append(pm.wirteDataPortSourceBody());
				count++;
			}
		}
		sb.append(rn);
		
		sb.append("{"+rn);
		
		for(int i=0;i<this.getPortManager().getPorts().size();i++){
			PortModel pm = (PortModel)this.getPortManager().getPorts().get(i);
			if(pm instanceof MethodInputPortModel){
				sb.append(pm.wirteServicePortSourceBody());				
			}
		}
		sb.append(rn);
		sb.append("\tportSetup();"+rn);
		sb.append("}"+rn);
		return sb.toString();
	}
	
//	 WJH 100614 S 추가 작업
	public String writeConstructor(){
		StringBuffer sb = new StringBuffer();
		int count = 0;
		for(int i=0;i<this.getPortManager().getPorts().size();i++){
			PortModel pm = (PortModel)this.getPortManager().getPorts().get(i);			
			if((pm instanceof DataInputPortModel)){
				if(count==0){
					sb.append("\t\t:");
				}
				else{
					sb.append("\t\t,");
				}
				sb.append(pm.wirteDataPortSourceBody());
				count++;
			}
		}
		sb.append(rn);
		
		sb.append("{"+rn);
		
		for(int i=0;i<this.getPortManager().getPorts().size();i++){
			PortModel pm = (PortModel)this.getPortManager().getPorts().get(i);
			if(pm instanceof MethodInputPortModel){
				sb.append(pm.wirteServicePortSourceBody());				
			}
		}
		sb.append(rn);
		sb.append("\tportSetup();"+rn);
		sb.append("}"+rn);
		return sb.toString();
	}
	public void writeDataPortpHeader(DataModel dm){
		StringBuffer sb = new StringBuffer();
//		for(int i=0;i<this.getPortManager().getPorts().size();i++){
//			PortModel pm = (PortModel)this.getPortManager().getPorts().get(i);
//			if((pm instanceof DataInputPortModel) || (pm instanceof DataOutputPortModel)){
				sb.append("#ifndef _"+dm.name+"_DATATYPE_H"+rn);
				sb.append("#define _"+dm.name+"_DATATYPE_H"+rn);
				sb.append("#include <boost/shared_ptr.hpp>"+rn);
				sb.append("#include <boost/serialization/split_member.hpp>"+rn);
				sb.append("#include <boost/serialization/shared_ptr.hpp>"+rn+rn);
				
				sb.append("/*"+rn);
				sb.append("** If you want to serialize binary, unmark following statements"+rn);
				sb.append("** typedef boost::shared_ptr&lt;char&gt; BytePtr;"+rn);
				sb.append("*/"+rn);
				sb.append("class "+dm.name+rn);
				sb.append("{"+rn);
				sb.append("protected:"+rn);
				sb.append("\tfriend class boost::serialization::access;"+rn);
//				String typeRef = pm.getTypeRef();
				java.util.ArrayList list = dm.items;
				if(list.size()>0){
					for(int j=0; j<list.size(); j++){
						ItemModel item = (ItemModel)list.get(j);	
						sb.append("\t"+item.type+" m_"+item.name+";"+rn);
//						if(method.getParams().size()>0){
//							for(int k=0; k<method.getParams().size(); k++){
//								ParamModel para = (ParamModel)method.getParams().get(k);
//								sb.append("\t"+para.type+" m_"+para.name+";"+rn);
//							}
//						}						
					}
				}
				sb.append("\t/** for using binary... "+rn);
				sb.append("\tBytePtr m_image;"+rn);
				sb.append("\t**/"+rn+rn);
				sb.append("\ttemplate <class Archive>"+rn);
				sb.append("\tvoid save(Archive &ar, const unsigned int) const"+rn);
				sb.append("\t{"+rn);
				if(list.size()>0){
					for(int j=0; j<list.size(); j++){
						ItemModel item = (ItemModel)list.get(j);	
//						sb.append("\t"+item.type+" m_"+item.name+";"+rn);
						sb.append("\t\tar & m_"+item.name+";"+rn);
//						if(method.getParams().size()>0){
//							for(int k=0; k<method.getParams().size(); k++){
//								ParamModel para = (ParamModel)method.getParams().get(k);
//								sb.append("\t"+para.type+" m_"+para.name+";"+rn);
//							}
//						}						
					}
				}
//				if(list.size()>0){
//					for(int j=0; j<list.size(); j++){
//						MethodModel method = (MethodModel)list.get(j);
//						if(method.getParams().size()>0){
//							for(int k=0; k<method.getParams().size(); k++){
//								ParamModel para = (ParamModel)method.getParams().get(k);
//								sb.append("\t\tar & m_"+para.name+";"+rn);
//							}
//						}
//					}
//				}
				sb.append("\t\t/* To serialize binary "+rn);
				sb.append("\t\t** m_size is size of binary data to be saved"+rn);
				sb.append("\t\tar.save_binary( m_image.get(), m_size);"+rn);
				sb.append("\t\t*/"+rn);
				sb.append("\t}"+rn+rn);
				sb.append("\ttemplate <class Archive>"+rn);
				sb.append("\tvoid load(Archive &ar, const unsigned int)"+rn);
				sb.append("\t{"+rn);
				if(list.size()>0){
					for(int j=0; j<list.size(); j++){
						ItemModel item = (ItemModel)list.get(j);	
//						sb.append("\t"+item.type+" m_"+item.name+";"+rn);
						sb.append("\t\tar & m_"+item.name+";"+rn);
//						if(method.getParams().size()>0){
//							for(int k=0; k<method.getParams().size(); k++){
//								ParamModel para = (ParamModel)method.getParams().get(k);
//								sb.append("\t"+para.type+" m_"+para.name+";"+rn);
//							}
//						}						
					}
				}
//				if(list.size()>0){
//					for(int j=0; j<list.size(); j++){
//						MethodModel method = (MethodModel)list.get(j);						
//						if(method.getParams().size()>0){
//							for(int k=0; k<method.getParams().size(); k++){
//								ParamModel para = (ParamModel)method.getParams().get(k);
//								sb.append("\t\tar & m_"+para.name+";"+rn);
//							}
//						}						
//					}
//				}
				sb.append("\t\t/* To serialize binary "+rn);
				sb.append("\t\t** m_size is size of binary data to be saved"+rn);
				sb.append("\t\tm_image.reset(new char [m_size]);"+rn);
				sb.append("\t\tchar *img = m_image.get();"+rn);
				sb.append("\t\tar.load_binary( img, m_size);"+rn);
				sb.append("\t\t**/"+rn);
				sb.append("\t}"+rn);
				sb.append("\tBOOST_SERIALIZATION_SPLIT_MEMBER()"+rn);
				sb.append("public:"+rn);
				sb.append("\t"+rn);
				sb.append("\t"+dm.name+"()"+rn);
				sb.append("\t{"+rn);
				sb.append("\t}"+rn+rn);
				if(list.size()>0){
					sb.append("\t"+dm.name+"(");
//					for(int j=0; j<list.size(); j++){
						
//						if(list.size()>0){
							for(int j1=0; j1<list.size(); j1++){
								ItemModel item = (ItemModel)list.get(j1);	
								if(j1==0){
									sb.append(item.type+"& in_"+item.name);
								}
								else{
									sb.append(", "+item.type+"& in_"+item.name);
								}
//								sb.append("\t"+item.type+" m_"+item.name+";"+rn);
//								sb.append("\t\tar & m_"+item.name+";"+rn);
//								if(method.getParams().size()>0){
//									for(int k=0; k<method.getParams().size(); k++){
//										ParamModel para = (ParamModel)method.getParams().get(k);
//										sb.append("\t"+para.type+" m_"+para.name+";"+rn);
//									}
//								}						
							}
//						}
						
						
//						MethodModel method = (MethodModel)list.get(j);						
//						ArrayList list2 = (ArrayList)method.getParams();
//						if(list2.size()>0){
//							for(int k=0; k<list2.size(); k++){
//								ParamModel para = (ParamModel)list2.get(k);
//								if(k==0)
//									sb.append(para.type+"& in_"+para.name);
//								else
//									sb.append(", "+para.type+"& in_"+para.name);
//							}
//						}						
//					}
					sb.append(")"+rn);
					sb.append("\t{"+rn);
					for(int j=0; j<list.size(); j++){
						ItemModel item = (ItemModel)list.get(j);					
						sb.append("\t\tm_"+item.name+" = in_"+item.name+";"+rn);						
					}
					sb.append("\t}"+rn);
					
					for(int j=0; j<list.size(); j++){
						ItemModel item = (ItemModel)list.get(j);							
//						if(method.getParams().size()>0){
//							for(int k=0; k<method.getParams().size(); k++){
//								ParamModel para = (ParamModel)method.getParams().get(k);
								String pname = item.name;
								String ch = pname.substring(0, 1).toUpperCase();
								pname = ch+pname.substring(1, item.name.length());
								sb.append("\t"+item.type+"& get"+pname+"()"+rn);
								sb.append("\t{"+rn);
								sb.append("\t\treturn m_"+item.name+";"+rn);
								sb.append("\t}"+rn+rn);
								sb.append("\tvoid set"+pname+"("+item.type+"& in_"+item.name+")"+rn);
								sb.append("\t{"+rn);
								sb.append("\t\tm_"+item.name+" = in_"+item.name+";"+rn);
								sb.append("\t}"+rn);
//							}
//						}						
					}					
				}
				
				sb.append("};"+rn);
				sb.append("#endif"+rn);
				
//			}
			try{
				if(!sb.toString().isEmpty()){
				File  f = new File(this.getCmpFolder()+File.separator+dm.name+".h");
				this.writeFile( sb,f);
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
//	}
	}
	
	public void writeDataPortHeader(){
		OPRoSDataTypesElementModel dmts = (OPRoSDataTypesElementModel)this.getClassModel().getUMLDataModel().getElementProperty("OPRoSDataTypesElementModel");
		Iterator it = dmts.getChildren().iterator();
//		File  f = new File(this.getCmpFolder()+File.separator+"profile"+File.separator+this.getName()+".xml");
		String path = this.getCmpFolder()+File.separator+"profile";
		while(it.hasNext()){
			Object model = it.next();
			if(model instanceof OPRoSDataTypeElementModel){
				OPRoSDataTypeElementModel opsd = (OPRoSDataTypeElementModel)model;
				java.util.ArrayList list = this.getTypeDataModelAll(path+File.separator+opsd.getDataTypeFileName());
				for(int i=0;i<list.size();i++){
					DataModel sm = (DataModel)list.get(i);
					this.writeDataPortpHeader(sm);
					
				}
			}
		}

	}
	
	public void writeServicePortRequiredHeader(ServiceModel sm){
		StringBuffer sb = new StringBuffer();
//		for(int i=0;i<this.getPortManager().getPorts().size();i++){
//			PortModel pm = (PortModel)this.getPortManager().getPorts().get(i);
//			if(pm instanceof MethodInputPortModel){
				sb.append("#ifndef _"+sm.typeName+"_REQUIRED_PORT_H"+rn);
				sb.append("#define _"+sm.typeName+"_REQUIRED_PORT_H"+rn+rn);
				sb.append("#include <OPRoSTypes.h>"+rn);
				sb.append("#include <InputDataPort.h>"+rn);
				sb.append("#include <OutputDataPort.h>"+rn);
				sb.append("#include <InputEventPort.h>"+rn);
				sb.append("#include <OutputEventPort.h>"+rn);
				sb.append("#include <ProvidedServicePort.h>"+rn);
				sb.append("#include <RequiredServicePort.h>"+rn);
				sb.append("#include <ProvidedMethod.h>"+rn);
				sb.append("#include <RequiredMethod.h>"+rn+rn);
//				String typeRef = pm.getTypeRef();
				java.util.ArrayList list = sm.methods;
				if(list.size()>0){
					for(int j=0; j<list.size(); j++){
						MethodModel method = (MethodModel)list.get(j);
						
						ArrayList list2 = (ArrayList)method.getParams();
						if(list2.size()>0){
							for(int k=0; k<list2.size(); k++){
								ParamModel para = (ParamModel)list2.get(k);
								if(para.type.startsWith("std::")){
									String sub = para.type.substring(5, para.type.length());
//									WJH 100721 S 코드 수정
									int index = sub.lastIndexOf("<");
									if(sub.lastIndexOf("<")>0){
										sub = sub.substring(0, index);										
									}
//									WJH 100721 E 코드 수정
									sb.append("#include < "+sub+" >"+rn+rn);
								}
								else if(para.type.startsWith("boost::")){
									sb.append("#include \""+para.type+".h\""+rn+rn);
								}
								else{
									
								}
							}
						}
					}
				}
				sb.append("/*"+rn);
				sb.append(" *"+rn);
				sb.append(" * "+sm.typeName+" : public RequiredServicePort"+rn);
				sb.append(" *"+rn);
				sb.append(" */"+rn);
				sb.append("class "+sm.typeName+"Required"+rn);
				sb.append("   :public RequiredServicePort"+rn);
				sb.append("{"+rn);
				sb.append("protected:"+rn+rn);
				if(list.size()>0){
					for(int j=0; j<list.size(); j++){
						MethodModel method = (MethodModel)list.get(j);
						sb.append("\ttypedef RequiredMethod<"+method.returnType+"> "+method.name+"FuncType;"+rn);//	WJH 100721 S 코드 수정
						sb.append("\t"+method.name+"FuncType *"+method.name+"Func;"+rn+rn);
					}
				}
				sb.append("public:"+rn);
				sb.append("\t//"+rn);
				sb.append("\t// Constructor"+rn);
				sb.append("\t//"+rn);
				sb.append("\t"+sm.typeName+"Required()"+rn);
				sb.append("\t{"+rn);
				if(list.size()>0){
					for(int j=0; j<list.size(); j++){
						MethodModel method = (MethodModel)list.get(j);
						sb.append("\t\t"+method.name+"Func = NULL;"+rn);						
					}
					sb.append("\t\tsetup();"+rn);
				
					sb.append("\t}"+rn+rn);
					sb.append("\t// method implementation for required method"+rn);
					for(int j=0; j<list.size(); j++){
						MethodModel method = (MethodModel)list.get(j);
						sb.append("\t"+method.returnType+" "+method.name+"(");//	WJH 100721 S 코드 수정
						ArrayList list2 = (ArrayList)method.getParams();
						if(list2.size()>0){
							for(int k=0; k<list2.size(); k++){
								ParamModel para = (ParamModel)list2.get(k);
								if(k==0)
									sb.append(para.type+" "+para.name);
								else
									sb.append(","+para.type+" "+para.name);
							}
						}
						sb.append(")"+rn);
						sb.append("\t{"+rn);
						sb.append("\t\tassert("+method.name+"Func != NULL);"+rn+rn);
//						WJH 100721 S 코드 수정
						if(method.returnType.equals("void")){
							sb.append("\t\t");
						}
						else{
							sb.append("\t\treturn ");
						}
//						WJH 100721 E 코드 수정
						sb.append("(*"+method.name+"Func)(");
						if(list2.size()>0){
							for(int k=0; k<list2.size(); k++){
								ParamModel para = (ParamModel)list2.get(k);
								if(k==0)
									sb.append(para.name);
								else
									sb.append(","+para.name);
							}
						}
						sb.append(");"+rn);
						sb.append("\t}"+rn+rn);					
					}					
				}

				sb.append("\t// generated setup function"+rn);
				sb.append("\tvirtual void setup()"+rn);
				sb.append("\t{"+rn);
				sb.append("\t\tMethod *ptr_method;"+rn+rn);
				if(list.size()>0){
					for(int j=0; j<list.size(); j++){
						MethodModel method = (MethodModel)list.get(j);
						sb.append("\t\tptr_method = makeRequiredMethod(&"+sm.typeName+"Required::"+method.name+",\""+method.name+"\");"+rn);
						sb.append("\t\tassert(ptr_method != NULL);"+rn);
						sb.append("\t\taddMethod(\""+method.name+"\",ptr_method);"+rn);
						sb.append("\t\t"+method.name+"Func = reinterpret_cast<"+method.name+"FuncType *>(ptr_method);"+rn);
						sb.append("\t\tptr_method = NULL;"+rn+rn);
					}
				}
				sb.append("\t}"+rn);
				sb.append("};"+rn);
				sb.append("#endif"+rn);
//			}
			try{
				if(!sb.toString().isEmpty()){
				File  f = new File(this.getCmpFolder()+File.separator+sm.typeName+"Required"+".h");
				this.writeFile( sb,f);
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
//		}
		
		
	}
	
	public void writeServicePortHeader(){
		OPRoSServiceTypesElementModel smts1 = (OPRoSServiceTypesElementModel)this.getClassModel().getUMLDataModel().getElementProperty("OPRoSServiceTypesElementModel");
		Iterator it = smts1.getChildren().iterator();
//		File  f = new File(this.getCmpFolder()+File.separator+"profile"+File.separator+this.getName()+".xml");
		String path = this.getCmpFolder()+File.separator+"profile";
		while(it.hasNext()){
			Object model = it.next();
			if(model instanceof OPRoSServiceTypeElementModel){
				OPRoSServiceTypeElementModel opsd = (OPRoSServiceTypeElementModel)model;
//				java.util.ArrayList list = getTypeRefMethodModelAll(path+File.separator+opsd.getServiceTypeFileName());
//				for(int i=0;i<list.size();i++){
//					ServiceModel sm = (ServiceModel)list.get(i);
//					this.writeServicePortProvidedHeader(sm);
//					this.writeServicePortRequiredHeader(sm);
//				}
//				WJH 100819 S 내용없는 리스트 제거
				if(opsd.getServiceTypeFileName() != null && !opsd.getServiceTypeFileName().equals("")){
					java.util.ArrayList list = getTypeRefMethodModelAll(path+File.separator+opsd.getServiceTypeFileName());
					for(int i=0;i<list.size();i++){
						ServiceModel sm = (ServiceModel)list.get(i);
						this.writeServicePortProvidedHeader(sm);
						this.writeServicePortRequiredHeader(sm);
					}
				}
//				WJH 100819 E 내용없는 리스트 제거
			}
		}
		
	}
	
	public void writeServicePortProvidedHeader(ServiceModel sm){
		StringBuffer sb = new StringBuffer();
//		for(int i=0;i<this.getPortManager().getPorts().size();i++){
//			PortModel pm = (PortModel)this.getPortManager().getPorts().get(i);
//			if(pm instanceof MethodOutputPortModel){
				sb.append("#ifndef _"+sm.typeName+"_PROVIDED_PORT_H"+rn);
				sb.append("#define _"+sm.typeName+"_PROVIDED_PORT_H"+rn+rn);
				sb.append("#include <OPRoSTypes.h>"+rn);
				sb.append("#include <InputDataPort.h>"+rn);
				sb.append("#include <OutputDataPort.h>"+rn);
				sb.append("#include <InputEventPort.h>"+rn);
				sb.append("#include <OutputEventPort.h>"+rn);
				sb.append("#include <ProvidedServicePort.h>"+rn);
				sb.append("#include <RequiredServicePort.h>"+rn);
				sb.append("#include <ProvidedMethod.h>"+rn);
				sb.append("#include <RequiredMethod.h>"+rn+rn);
//				String typeRef = pm.getTypeRef();
				java.util.ArrayList list = sm.methods;
				if(list.size()>0){
					for(int j=0; j<list.size(); j++){
						MethodModel method = (MethodModel)list.get(j);
						
						ArrayList list2 = (ArrayList)method.getParams();
						if(list2.size()>0){
							for(int k=0; k<list2.size(); k++){
								ParamModel para = (ParamModel)list2.get(k);
								if(para.type.startsWith("std::")){
									String sub = para.type.substring(5, para.type.length());
//									WJH 100721 S 코드 수정
									int index = sub.lastIndexOf("<");
									if(sub.lastIndexOf("<")>0){
										sub = sub.substring(0, index);										
									}
//									WJH 100721 E 코드 수정
									sb.append("#include < "+sub+" >"+rn+rn);
								}
								else if(para.type.startsWith("boost::")){							
									sb.append("#include \""+para.type+".h\""+rn+rn);
								}
								else{
									
								}
							}
						}
					}
				}
				
				sb.append("/*"+rn);				
				sb.append(" * I"+sm.typeName+rn);
				sb.append(" *"+rn);
				sb.append(" * The comonent inherits this class and implements methods."+rn);
				sb.append(" */"+rn);
				sb.append("class I"+sm.typeName+rn);
				sb.append("{"+rn);
				sb.append(" public:"+rn);
				if(list.size()>0){
					for(int j=0; j<list.size(); j++){
						MethodModel method = (MethodModel)list.get(j);
						sb.append("\tvirtual "+method.returnType+" "+method.name+"(");//	WJH 100721 S 코드 수정
						ArrayList list2 = (ArrayList)method.getParams();
						if(list2.size()>0){
							for(int k=0; k<list2.size(); k++){
								ParamModel para = (ParamModel)list2.get(k);
								if(k==0)
									sb.append(para.type+" "+para.name);
								else
									sb.append(","+para.type+" "+para.name);
							}
						}
						sb.append(")=0;"+rn);
					}
				}
				sb.append("};"+rn);
				
				sb.append("/*"+rn);
				sb.append(" *"+rn);				
				sb.append("\t"+sm.typeName+" : public ProvidedServicePort"+rn);
				sb.append(" *"+rn);
				sb.append(" */"+rn);
				sb.append("class "+sm.typeName+"Provided"+rn);
				sb.append("	:public ProvidedServicePort, public I"+sm.typeName+rn);
				sb.append("{"+rn);
				sb.append("protected:"+rn);
				sb.append("\tI"+sm.typeName+" *pcom;"+rn+rn);
				
				if(list.size()>0){
					for(int j=0; j<list.size(); j++){
						MethodModel method = (MethodModel)list.get(j);
						sb.append("\ttypedef ProvidedMethod<"+method.returnType+"> "+method.name+"FuncType;"+rn);
						sb.append("\t"+method.name+"FuncType *"+method.name+"Func;"+rn);
					}
					sb.append(rn);
					sb.append("public: // default method"+rn);
					
					for(int j=0; j<list.size(); j++){
//						WJH 100721 S 코드 수정
						MethodModel method = (MethodModel)list.get(j);
						sb.append("\tvirtual "+method.returnType+" ");
//						WJH 100721 E 코드 수정
						sb.append(method.name+"(");
						ArrayList list2 = (ArrayList)method.getParams();
						if(list2.size()>0){
							for(int k=0; k<list2.size(); k++){
								ParamModel para = (ParamModel)list2.get(k);
								if(k==0)
									sb.append(para.type+" "+para.name);
								else
									sb.append(","+para.type+" "+para.name);
							}
						}
						sb.append(")"+rn);						
						sb.append("\t{"+rn);
						sb.append("\t\tassert("+method.name+"Func != NULL);"+rn+rn);
//						WJH 100721 S 코드 수정
						if(method.returnType.equals("void")){
							sb.append("\t\t");
						}
						else{
							sb.append("\t\treturn ");
						}
//						WJH 100721 E 코드 수정
						sb.append("(*"+method.name+"Func)(");
						if(list2.size()>0){
							for(int k=0; k<list2.size(); k++){
								ParamModel para = (ParamModel)list2.get(k);
								if(k==0)
									sb.append(para.name);
								else
									sb.append(","+para.name);
							}
						}
						sb.append(");"+rn);
						sb.append("\t}"+rn+rn);
					}					
				}

				sb.append("public:"+rn);
				sb.append("\t//"+rn);
				sb.append("\t//  Constructor"+rn);
				sb.append("\t//"+rn);
				sb.append("\t"+sm.typeName+"Provided(I"+sm.typeName+" *fn)"+rn);
				sb.append("\t{"+rn+rn);
				sb.append("\t\tpcom = fn;"+rn+rn);
				if(list.size()>0){
					for(int j=0; j<list.size(); j++){
						MethodModel method = (MethodModel)list.get(j);
						sb.append("\t\t"+method.name+"Func = NULL;"+rn);						
					}
					sb.append("\t\tsetup();"+rn+rn);	
				}
				sb.append("\t}"+rn+rn);
				sb.append("\t// generated setup function"+rn);
				sb.append("\tvirtual void setup()"+rn);
				sb.append("\t{"+rn);
				sb.append("\t\tMethod *ptr_method;"+rn+rn);
				if(list.size()>0){
					for(int j=0; j<list.size(); j++){
						MethodModel method = (MethodModel)list.get(j);
						sb.append("\t\tptr_method = makeProvidedMethod(&I"+sm.typeName+"::"+method.name+",pcom,\""+method.name+"\");"+rn+rn);
						sb.append("\t\tassert(ptr_method != NULL);"+rn);
						sb.append("\t\taddMethod(\""+method.name+"\",ptr_method);"+rn);
						sb.append("\t\t"+method.name+"Func = reinterpret_cast<"+method.name+"FuncType *>(ptr_method);"+rn);
						sb.append("\t\tptr_method = NULL;"+rn);
					}
				}
				sb.append("\t}"+rn);
				sb.append("};"+rn);
				sb.append("#endif"+rn);
//			}
			try{
				if(!sb.toString().isEmpty()){
					File  f = new File(this.getCmpFolder()+File.separator+sm.typeName+"Provided"+".h");
					this.writeFile( sb,f);
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
//		}
		
		
	}
	
//	public void addOPRoSServiceTypeElementModel(OPRoSServiceTypeElementModel om){
//		java.util.ArrayList list = (java.util.ArrayList)this.uMLDataModel.getElementProperty("getServiceTypesModel");
//		if(list==null){
//			java.util.ArrayList list1 = new java.util.ArrayList();
//			list1.add(om);
//			this.uMLDataModel.setElementProperty("getServiceTypesModel", list1);
//		}
//		else{
//			list.add(om);
//		}
//		
//		
//	}
//	
	
	public List getDataTypesModel(){
		OPRoSDataTypesElementModel a = (OPRoSDataTypesElementModel)this.getClassModel().getUMLDataModel().getElementProperty("OPRoSDataTypesElementModel");
//		a.getChildren().
//		a.getChildren()
		return a.getChildren();
		
	}
	
	public Object clone() {
		AtomicComponentModel clone = new AtomicComponentModel((UMLDataModel)this.getUMLDataModel().clone(),-1);
		AtomicComponentModel t = this.getCoreDiagramCmpModel();
		if(t!=null){
			OPRoSExeSemanticsElementModel sm1 = (OPRoSExeSemanticsElementModel)t.getClassModel().getUMLDataModel().getElementProperty("OPRoSExeSemanticsElementModel");
			OPRoSExeEnvironmentElementModel am1 = (OPRoSExeEnvironmentElementModel)t.getClassModel().getUMLDataModel().getElementProperty("OPRoSExeEnvironmentElementModel");
			OPRoSPropertiesElementModel pms1 = (OPRoSPropertiesElementModel)t.getClassModel().getUMLDataModel().getElementProperty("OPRoSPropertiesElementModel");
			OPRoSDataTypesElementModel dmts1 = (OPRoSDataTypesElementModel)t.getClassModel().getUMLDataModel().getElementProperty("OPRoSDataTypesElementModel");
			OPRoSServiceTypesElementModel smts1 = (OPRoSServiceTypesElementModel)t.getClassModel().getUMLDataModel().getElementProperty("OPRoSServiceTypesElementModel");
			OPRoSMonitorVariablesElementModel mvs1 = (OPRoSMonitorVariablesElementModel)t.getClassModel().getUMLDataModel().getElementProperty("OPRoSMonitorVariablesElementModel");
			clone.getClassModel().getUMLDataModel().setElementProperty("OPRoSExeSemanticsElementModel", sm1);
			clone.getClassModel().getUMLDataModel().setElementProperty("OPRoSExeEnvironmentElementModel", am1);
			clone.getClassModel().getUMLDataModel().setElementProperty("OPRoSPropertiesElementModel", pms1);
			clone.getClassModel().getUMLDataModel().setElementProperty("OPRoSDataTypesElementModel", dmts1);
			clone.getClassModel().getUMLDataModel().setElementProperty("OPRoSServiceTypesElementModel", smts1);
			clone.getClassModel().getUMLDataModel().setElementProperty("OPRoSMonitorVariablesElementModel", mvs1);
			if(am1!=null && sm1!=null && pms1!=null && smts1!=null && dmts1!=null){
				clone.addChild(sm1);
				clone.addChild(am1);
				clone.addChild(pms1);
				clone.addChild(dmts1);
				clone.addChild(smts1);
				clone.addChild(mvs1);
			}
			else{
				
			}
		}
		else{
			OPRoSExeSemanticsElementModel sm1 = (OPRoSExeSemanticsElementModel)this.getClassModel().getUMLDataModel().getElementProperty("OPRoSExeSemanticsElementModel");
			OPRoSExeEnvironmentElementModel am1 = (OPRoSExeEnvironmentElementModel)this.getClassModel().getUMLDataModel().getElementProperty("OPRoSExeEnvironmentElementModel");
			OPRoSPropertiesElementModel pms1 = (OPRoSPropertiesElementModel)this.getClassModel().getUMLDataModel().getElementProperty("OPRoSPropertiesElementModel");
			OPRoSDataTypesElementModel dmts1 = (OPRoSDataTypesElementModel)this.getClassModel().getUMLDataModel().getElementProperty("OPRoSDataTypesElementModel");
			OPRoSServiceTypesElementModel smts1 = (OPRoSServiceTypesElementModel)this.getClassModel().getUMLDataModel().getElementProperty("OPRoSServiceTypesElementModel");
			OPRoSMonitorVariablesElementModel mvs1 = (OPRoSMonitorVariablesElementModel)this.getClassModel().getUMLDataModel().getElementProperty("OPRoSMonitorVariablesElementModel");
			
			clone.getClassModel().getUMLDataModel().setElementProperty("OPRoSExeSemanticsElementModel", sm1);
			clone.getClassModel().getUMLDataModel().setElementProperty("OPRoSExeEnvironmentElementModel", am1);
			clone.getClassModel().getUMLDataModel().setElementProperty("OPRoSPropertiesElementModel", pms1);
			clone.getClassModel().getUMLDataModel().setElementProperty("OPRoSDataTypesElementModel", dmts1);
			clone.getClassModel().getUMLDataModel().setElementProperty("OPRoSServiceTypesElementModel", smts1);
			clone.getClassModel().getUMLDataModel().setElementProperty("OPRoSMonitorVariablesElementModel", mvs1);
			if(am1!=null && sm1!=null && pms1!=null && smts1!=null && dmts1!=null){
				clone.addChild(sm1);
				clone.addChild(am1);
				clone.addChild(pms1);
				clone.addChild(dmts1);
				clone.addChild(smts1);
				clone.addChild(mvs1);
			}
		}
		
	
		
        //		clone.temp_childs = this.activityModel.getChildren();
//        for (int i = 0; i < this.boundaryModel.getChildren().size(); i++) {
//            UMLModel um = (UMLModel)this.boundaryModel.getChildren().get(i);
//            clone.boundaryModel.addChild((UMLElementModel)um.clone());
//        }
//        clone.setPortManager(this.getPortManager().clone(clone));
        clone.setSize(this.getSize());
        clone.setBackGroundColor(this.getBackGroundColor());
        clone.setLocation(this.getLocation());
        clone.sourceModel = this;
//        ProjectManager.getInstance().setTempCopyMap(clone.sourceModel.getID(), clone);
//        for (int i1 = 0; i1 < this.getSourceConnections().size(); i1++) {
//            LineModel li = (LineModel)this.getSourceConnections().get(i1);
//            LineModel liCopy = (LineModel)li.clone();
//            ProjectManager.getInstance().addSelectLineModel(liCopy);
//        }
        return clone;
    }
	

	
public void writeProFile(){
		
		Element parentEle = new Element("component_profile");
		Element ele;
		Element subEle;
		ele = new Element("id");
		ele.setText(this.getId());
		parentEle.addContent(ele);
		
		ele=new Element("name");
		ele.setText(this.getName());
		parentEle.addContent(ele);
		
		if(!getVersion().isEmpty()){
			ele = new Element("version");
			ele.setText(getVersion());
			parentEle.addContent(ele);
		}
		if(!this.getDesc().isEmpty()){
			ele = new Element("description");
			ele.setText(this.getDesc());
			parentEle.addContent(ele);
		}
//		ele = new Element("layout");
//		ele.setText(Integer.toString(getLayout().x)+","+Integer.toString(getLayout().y)
//				+","+Integer.toString(getLayout().width)+","+Integer.toString(getLayout().height));
//		parentEle.addContent(ele);
		
		ele = new Element("copyright");
		parentEle.addContent(ele);
		Element companyEle = new Element("company");
		ele.addContent(companyEle);
		subEle = new Element("name");
		subEle.setText(this.getcompNameProp());
		companyEle.addContent(subEle);
		subEle = new Element("phone");
		subEle.setText(this.getcompPhomeProp());
		companyEle.addContent(subEle);
		subEle = new Element("address");
		subEle.setText(this.getCompAddressProp());
		companyEle.addContent(subEle);
		subEle = new Element("homepage");
		subEle.setText(this.getcompHomepageProp());
		companyEle.addContent(subEle);
		subEle = new Element("license_policy");
		subEle.setText(this.getgetlicensePolicyString());
		ele.addContent(subEle);
		
		ele = new Element("execution_environment");
		parentEle.addContent(ele);
		OPRoSExeSemanticsElementModel sm1 = (OPRoSExeSemanticsElementModel)this.getClassModel().getUMLDataModel().getElementProperty("OPRoSExeSemanticsElementModel");
		OPRoSExeEnvironmentElementModel am1 = (OPRoSExeEnvironmentElementModel)this.getClassModel().getUMLDataModel().getElementProperty("OPRoSExeEnvironmentElementModel");
		OPRoSPropertiesElementModel pms1 = (OPRoSPropertiesElementModel)this.getClassModel().getUMLDataModel().getElementProperty("OPRoSPropertiesElementModel");
		OPRoSDataTypesElementModel dmts1 = (OPRoSDataTypesElementModel)this.getClassModel().getUMLDataModel().getElementProperty("OPRoSDataTypesElementModel");
		OPRoSServiceTypesElementModel smts1 = (OPRoSServiceTypesElementModel)this.getClassModel().getUMLDataModel().getElementProperty("OPRoSServiceTypesElementModel");
		java.util.ArrayList monitorVariables = (java.util.ArrayList)this.getMonitorVariables();
		
		Iterator it =  am1.getChildren().iterator();
		while(it.hasNext()){
			Object model = it.next();
			if(model instanceof OPRoSExeEnvironmentOSElementModel){
				subEle = new Element("os");
				Attribute attr = new Attribute("name",((OPRoSExeEnvironmentOSElementModel)model).getOs());
				Attribute version = new Attribute("version",((OPRoSExeEnvironmentOSElementModel)model).getOsVersion());
				subEle.setAttribute(attr);
				subEle.setAttribute(version);
				ele.addContent(subEle);
			}
		}
		it = sm1.getChildren().iterator();
		while(it.hasNext()){
			Object model = it.next();
			if(model instanceof OPRoSExeEnvironmentCPUElementModel){
				subEle = new Element("cpu");
				subEle.setText(((OPRoSExeEnvironmentCPUElementModel)model).getCpu());
				ele.addContent(subEle);
			}
		}
		
		am1.doSave(ele);
		
		ele = new Element("execution_semantics");
		parentEle.addContent(ele);
		sm1.doSave(ele);
		
		ele = new Element("properties");
		parentEle.addContent(ele);
		pms1.doSave(ele);
		
		ele = new Element("data_type_list");
		Iterator usingDTIt = this.getDataTypeReference().iterator();
		while(usingDTIt.hasNext()){
			subEle = new Element("reference");
			String dtl = (String)usingDTIt.next();
			if(dtl!=null && !"".equals(dtl)){
				Attribute attr = new Attribute("name",dtl);
				subEle.setAttribute(attr);
				ele.addContent(subEle);
			}
		}
		parentEle.addContent(ele);
		
		ele = new Element("defined_data_types");
		it = dmts1.getChildren().iterator();
		while(it.hasNext()){
			Object model = it.next();
			if(model instanceof OPRoSDataTypeElementModel){
				OPRoSDataTypeElementModel opsd = (OPRoSDataTypeElementModel)model;
//				subEle = new Element("cpu");
//				subEle.setText(((OPRoSExeEnvironmentCPUElementModel)model).getCpu());
				subEle =new Element("defined_data_type");
				subEle.setText(opsd.getDataTypeFileName());
//				parentEle.addContent(subEle);
				ele.addContent(subEle);
			}
		}
		parentEle.addContent(ele);
	
		
		ele = new Element("defined_service_types");
		it = smts1.getChildren().iterator();
		while(it.hasNext()){
			Object model = it.next();
			if(model instanceof OPRoSServiceTypeElementModel){
				OPRoSServiceTypeElementModel opsd = (OPRoSServiceTypeElementModel)model;
//				opsd.getServiceTypeDoc()
//				subEle = new Element("cpu");
//				subEle.setText(((OPRoSExeEnvironmentCPUElementModel)model).getCpu());
				subEle =new Element("defined_service_type");
				subEle.setText(opsd.getServiceTypeFileName());
//				parentEle.addContent(subEle);
				ele.addContent(subEle);
			}
		}
		parentEle.addContent(ele);
		
		if(monitorVariables!=null && monitorVariables.size()>0){
			//ijs monitoring->exports
			if(this.isExport)
			ele = new Element("exports");
			else
				ele = new Element("monitoring");
			for(int i=0;i<monitorVariables.size();i++){
				DetailPropertyTableItem dp = (DetailPropertyTableItem)monitorVariables.get(i);
				subEle = new Element("var");
				Attribute name = new Attribute("name",dp.tapName);
				Attribute type = new Attribute("type",dp.sTagType);
				subEle.setAttribute(name);
				subEle.setAttribute(type);
				ele.addContent(subEle);
			}
			parentEle.addContent(ele);
		}
		
		
		
		ele = new Element("ports");
		for(int i=0;i<this.getPortManager().getPorts().size();i++){
			PortModel pm = (PortModel)this.getPortManager().getPorts().get(i);
			if((pm instanceof MethodInputPortModel) || (pm instanceof MethodOutputPortModel)){
				subEle=new Element("service_port");
				pm.doSave(subEle);
				ele.addContent(subEle);
			}
		}
		for(int i=0;i<this.getPortManager().getPorts().size();i++){
			PortModel pm = (PortModel)this.getPortManager().getPorts().get(i);
			if((pm instanceof DataInputPortModel) || (pm instanceof DataOutputPortModel)){
				subEle=new Element("data_port");
				pm.doSave(subEle);
				ele.addContent(subEle);
			}
		}
		
		for(int i=0;i<this.getPortManager().getPorts().size();i++){
			PortModel pm = (PortModel)this.getPortManager().getPorts().get(i);
			if((pm instanceof EventInputPortModel) || (pm instanceof EventOutputPortModel)){
				subEle=new Element("event_port");
				pm.doSave(subEle);
				ele.addContent(subEle);
			}
		}
		
		
		parentEle.addContent(ele);
//		String path = this.getCmpFolder();
		
		Document compDoc = new Document(parentEle);
		
		File  f = new File("C:\\"+File.separator+"profile"+File.separator+this.getName()+".xml");
		try{
		this.writeXMLFile(compDoc, f);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		dmts1.wirteProfile(this.getCmpFolder());
		smts1.wirteProfile(this.getCmpFolder());
		IWorkspace workspace = ResourcesPlugin.getWorkspace();//워크스페이스 핸들
		IWorkspaceRoot root = workspace.getRoot();//워크스페이스 루트핸들
		final IProject newProjectHandle = root.getProject(this.getName()); 
		try{
		newProjectHandle.refreshLocal(IResource.DEPTH_INFINITE, null);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		
		
	}
	
//20110705 서동민 - 전체..
public Document wholeProFile(){
	Element parentEle;
	Element subEle;
	parentEle = new Element("profile");
	Attribute profile = new Attribute("name", this.getName()+".xml");
	parentEle.setAttribute(profile);

	//dataType
	Element dataEle = new Element("dataType");
	parentEle.addContent(dataEle);
	
	OPRoSDataTypesElementModel desList = (OPRoSDataTypesElementModel)this.getClassModel().getUMLDataModel().getElementProperty("OPRoSDataTypesElementModel");
	Iterator itDataType = desList.getChildren().iterator();
	
	while(itDataType.hasNext()){
		Object obj = itDataType.next();
		if(obj instanceof OPRoSDataTypeElementModel){
			OPRoSDataTypeElementModel model = (OPRoSDataTypeElementModel)obj;
			
			subEle = new Element("dataType");
			dataEle.addContent(subEle);
			Attribute dataType = new Attribute("name", model.getDataTypeFileName());
			subEle.setAttribute(dataType);
		}
	}
		
	
	//serviceType
	Element serviceEle = new Element("serviceType");
	parentEle.addContent(serviceEle);
	
	OPRoSServiceTypesElementModel sesList = (OPRoSServiceTypesElementModel)this.getClassModel().getUMLDataModel().getElementProperty("OPRoSServiceTypesElementModel");
	Iterator itServiceType = sesList.getChildren().iterator();
	
	while(itServiceType.hasNext()){
		Object obj = itServiceType.next();
		if(obj instanceof OPRoSServiceTypeElementModel){
			OPRoSServiceTypeElementModel model = (OPRoSServiceTypeElementModel)obj;
			
			subEle = new Element("serviceType");
			serviceEle.addContent(subEle);
			Attribute serviceType = new Attribute("name", model.getServiceTypeFileName());
			subEle.setAttribute(serviceType);
		}
	}
	
	
	
	
	Document compDoc = new Document(parentEle);
	
//	File  f = new File("C:\\"+"profile"+File.separator+"profile"+".xml");
	File  f = new File("test.xml");
	try{

	this.writeXMLFile(compDoc, f);
	}
	catch(Exception e){
		e.printStackTrace();
	}
	
	return compDoc;
}

////20110705 서동민 - 서비스타입xml
//public void serviceProFile(){
//	Element parentEle = new Element("service_port_type_profile");
//	Element ele;
//	Element subEle;
//	
//	ele = new Element("service_port_type");
//	parentEle.addContent(ele);
//	Element companyEle = new Element("param");
//	ele.addContent(companyEle);
//	subEle = new Element("name");
//	subEle.setText(this.getServiceTypeFileName());
//	companyEle.addContent(subEle);
//	subEle = new Element("type");
//	subEle.setText(this.getServiceTypeFileName());
//	companyEle.addContent(subEle);
//}

//20110705 서동민 - 컴포넌트 xml
public Document componentProFile(){
	
	Element parentEle = new Element("component_profile");
	Element ele;
	Element subEle;
	ele = new Element("id");
	ele.setText(this.getId());
	parentEle.addContent(ele);
	
	ele=new Element("name");
	ele.setText(this.getName());
	parentEle.addContent(ele);
	
	if(!getVersion().isEmpty()){
		ele = new Element("version");
		ele.setText(getVersion());
		parentEle.addContent(ele);
	}
	if(!this.getDesc().isEmpty()){
		ele = new Element("description");
		ele.setText(this.getDesc());
		parentEle.addContent(ele);
	}
	//ele = new Element("layout");
	//ele.setText(Integer.toString(getLayout().x)+","+Integer.toString(getLayout().y)
	//		+","+Integer.toString(getLayout().width)+","+Integer.toString(getLayout().height));
	//parentEle.addContent(ele);
	
	ele = new Element("copyright");
	parentEle.addContent(ele);
	Element companyEle = new Element("company");
	ele.addContent(companyEle);
	subEle = new Element("name");
	subEle.setText(this.getcompNameProp());
	companyEle.addContent(subEle);
	subEle = new Element("phone");
	subEle.setText(this.getcompPhomeProp());
	companyEle.addContent(subEle);
	subEle = new Element("address");
	subEle.setText(this.getCompAddressProp());
	companyEle.addContent(subEle);
	subEle = new Element("homepage");
	subEle.setText(this.getcompHomepageProp());
	companyEle.addContent(subEle);
	subEle = new Element("license_policy");
	subEle.setText(this.getgetlicensePolicyString());
	ele.addContent(subEle);
	
	ele = new Element("execution_environment");
	parentEle.addContent(ele);
	OPRoSExeSemanticsElementModel sm1 = (OPRoSExeSemanticsElementModel)this.getClassModel().getUMLDataModel().getElementProperty("OPRoSExeSemanticsElementModel");
	OPRoSExeEnvironmentElementModel am1 = (OPRoSExeEnvironmentElementModel)this.getClassModel().getUMLDataModel().getElementProperty("OPRoSExeEnvironmentElementModel");
	OPRoSPropertiesElementModel pms1 = (OPRoSPropertiesElementModel)this.getClassModel().getUMLDataModel().getElementProperty("OPRoSPropertiesElementModel");
	OPRoSDataTypesElementModel dmts1 = (OPRoSDataTypesElementModel)this.getClassModel().getUMLDataModel().getElementProperty("OPRoSDataTypesElementModel");
	OPRoSServiceTypesElementModel smts1 = (OPRoSServiceTypesElementModel)this.getClassModel().getUMLDataModel().getElementProperty("OPRoSServiceTypesElementModel");
	java.util.ArrayList monitorVariables = (java.util.ArrayList)this.getMonitorVariables();
	
	Iterator it =  am1.getChildren().iterator();
	while(it.hasNext()){
		Object model = it.next();
		//110902 S SDM 해당 부분 삭제
//		if(model instanceof OPRoSExeEnvironmentOSElementModel){
//			subEle = new Element("os");
//			Attribute attr = new Attribute("name",((OPRoSExeEnvironmentOSElementModel)model).getOs());
//			Attribute version = new Attribute("version",((OPRoSExeEnvironmentOSElementModel)model).getOsVersion());
//			subEle.setAttribute(attr);
//			subEle.setAttribute(version);
//			ele.addContent(subEle);
//		}
		//110902 E SDM 해당 부분 삭제
	}
	it = sm1.getChildren().iterator();
	while(it.hasNext()){
		Object model = it.next();
		if(model instanceof OPRoSExeEnvironmentCPUElementModel){
			subEle = new Element("cpu");
			subEle.setText(((OPRoSExeEnvironmentCPUElementModel)model).getCpu());
			ele.addContent(subEle);
		}
	}
	
	am1.doSave(ele);
	
	ele = new Element("execution_semantics");
	parentEle.addContent(ele);
	sm1.doSave(ele);
	
	ele = new Element("properties");
	parentEle.addContent(ele);
	pms1.doSave(ele);
	
	ele = new Element("data_type_list");
	Iterator usingDTIt = this.getDataTypeReference().iterator();
	while(usingDTIt.hasNext()){
		subEle = new Element("reference");
		String dtl = (String)usingDTIt.next();
		if(dtl!=null && !"".equals(dtl)){
			Attribute attr = new Attribute("name",dtl);
			subEle.setAttribute(attr);
			ele.addContent(subEle);
		}
	}
	parentEle.addContent(ele);
	
	ele = new Element("defined_data_types");
	it = dmts1.getChildren().iterator();
	while(it.hasNext()){
		Object model = it.next();
		if(model instanceof OPRoSDataTypeElementModel){
			OPRoSDataTypeElementModel opsd = (OPRoSDataTypeElementModel)model;
	//		subEle = new Element("cpu");
	//		subEle.setText(((OPRoSExeEnvironmentCPUElementModel)model).getCpu());
			subEle =new Element("defined_data_type");
			subEle.setText(opsd.getDataTypeFileName());
	//		parentEle.addContent(subEle);
			ele.addContent(subEle);
		}
	}
	parentEle.addContent(ele);
	
	
	ele = new Element("defined_service_types");
	it = smts1.getChildren().iterator();
	while(it.hasNext()){
		Object model = it.next();
		if(model instanceof OPRoSServiceTypeElementModel){
			OPRoSServiceTypeElementModel opsd = (OPRoSServiceTypeElementModel)model;
	//		subEle = new Element("cpu");
	//		subEle.setText(((OPRoSExeEnvironmentCPUElementModel)model).getCpu());
			subEle =new Element("defined_service_type");
			subEle.setText(opsd.getServiceTypeFileName());
	//		parentEle.addContent(subEle);
			ele.addContent(subEle);
		}
	}
	parentEle.addContent(ele);
	
	if(monitorVariables!=null && monitorVariables.size()>0){
		//ijs monitoring->exports
		if(this.isExport)
		ele = new Element("exports");
		else
			ele = new Element("monitoring");
		for(int i=0;i<monitorVariables.size();i++){
			DetailPropertyTableItem dp = (DetailPropertyTableItem)monitorVariables.get(i);
			subEle = new Element("var");
			Attribute name = new Attribute("name",dp.tapName);
			Attribute type = new Attribute("type",dp.sTagType);
			subEle.setAttribute(name);
			subEle.setAttribute(type);
			ele.addContent(subEle);
		}
		parentEle.addContent(ele);
	}
	
	
	
	ele = new Element("ports");
	for(int i=0;i<this.getPortManager().getPorts().size();i++){
		PortModel pm = (PortModel)this.getPortManager().getPorts().get(i);
		if((pm instanceof MethodInputPortModel) || (pm instanceof MethodOutputPortModel)){
			subEle=new Element("service_port");
			pm.doSave(subEle);
			ele.addContent(subEle);
		}
	}
	for(int i=0;i<this.getPortManager().getPorts().size();i++){
		PortModel pm = (PortModel)this.getPortManager().getPorts().get(i);
		if((pm instanceof DataInputPortModel) || (pm instanceof DataOutputPortModel)){
			subEle=new Element("data_port");
			pm.doSave(subEle);
			ele.addContent(subEle);
		}
	}
	
	for(int i=0;i<this.getPortManager().getPorts().size();i++){
		PortModel pm = (PortModel)this.getPortManager().getPorts().get(i);
		if((pm instanceof EventInputPortModel) || (pm instanceof EventOutputPortModel)){
			subEle=new Element("event_port");
			pm.doSave(subEle);
			ele.addContent(subEle);
		}
	}
	
	
	parentEle.addContent(ele);
	//String path = this.getCmpFolder();
	
	Document compDoc = new Document(parentEle);
	
//	File  f = new File("C:\\"+"profile"+File.separator+this.getName()+".xml");
	File  f = new File("C:\\test.xml");
	try{
	this.writeXMLFile(compDoc, f);
	}
	catch(Exception e){
		e.printStackTrace();
	}
	
	return compDoc;
//	
//	File  f = new File(this.getCmpFolder()+File.separator+"profile"+File.separator+this.getName()+".xml");
//	try{
//	this.writeXMLFile(compDoc, f);
//	}
//	catch(Exception e){
//		e.printStackTrace();
//	}
	//dmts1.wirteProfile(this.getCmpFolder());
	//smts1.wirteProfile(this.getCmpFolder());
	//IWorkspace workspace = ResourcesPlugin.getWorkspace();//워크스페이스 핸들
	//IWorkspaceRoot root = workspace.getRoot();//워크스페이스 루트핸들
	//final IProject newProjectHandle = root.getProject(this.getName()); 
	//try{
	//newProjectHandle.refreshLocal(IResource.DEPTH_INFINITE, null);
	//}
	//catch(Exception e){
	//	e.printStackTrace();
	//}
	
	

}


	public void doLoad(){
		
	}
	
//	private Element doServicePortSave(Element parentEle){
//		Iterator<OPRoSElementBaseModel> it = getChildrenList().iterator();
//		Element ele;
//		while(it.hasNext()){
//			if(parentEle==null)
//				parentEle = new Element("ports");
//			OPRoSElementBaseModel model = it.next();
//			if(model instanceof OPRoSServiceProvidedPortElementModel){
//				ele=new Element("service_port");
//				((OPRoSServiceProvidedPortElementModel)model).doSave(ele);
//				parentEle.addContent(ele);
//			}else if(model instanceof OPRoSServiceRequiredPortElementModel){
//				ele=new Element("service_port");
//				((OPRoSServiceRequiredPortElementModel)model).doSave(ele);
//				parentEle.addContent(ele);
//			}
//		}
//		return parentEle;
//	}
//	private Element doDataPortSave(Element parentEle){
//		Iterator<OPRoSElementBaseModel> it = getChildrenList().iterator();
//		Element ele;
//		while(it.hasNext()){
//			if(parentEle==null)
//				parentEle = new Element("ports");
//			OPRoSElementBaseModel model = it.next();
//			if(model instanceof OPRoSDataInPortElementModel){
//				ele=new Element("data_port");
//				((OPRoSDataInPortElementModel)model).doSave(ele);
//				parentEle.addContent(ele);
//			}else if(model instanceof OPRoSDataOutPortElementModel){
//				ele=new Element("data_port");
//				((OPRoSDataOutPortElementModel)model).doSave(ele);
//				parentEle.addContent(ele);
//			}
//		}
//		return parentEle;
//	}
//	private Element doEventPortSave(Element parentEle){
//		Iterator<OPRoSElementBaseModel> it = getChildrenList().iterator();
//		Element ele;
//		while(it.hasNext()){
//			if(parentEle==null)
//				parentEle = new Element("ports");
//			OPRoSElementBaseModel model = it.next();
//			if(model instanceof OPRoSEventInPortElementModel){
//				ele=new Element("event_port");
//				((OPRoSEventInPortElementModel)model).doSave(ele);
//				parentEle.addContent(ele);
//			}else if(model instanceof OPRoSEventOutPortElementModel){
//				ele=new Element("event_port");
//				((OPRoSEventOutPortElementModel)model).doSave(ele);
//				parentEle.addContent(ele);
//			}
//		}
//		return parentEle;
//	}
	
	public void addDataTypeReference(String dataTypeFile){
		OPRoSDataTypesElementModel dmts = (OPRoSDataTypesElementModel)this.getClassModel().getUMLDataModel().getElementProperty("OPRoSDataTypesElementModel");
		dmts.addDataTypeReference(dataTypeFile);
		
	}
	
	public java.util.ArrayList getDataTypeReference(){
		OPRoSDataTypesElementModel dmts = (OPRoSDataTypesElementModel)this.getClassModel().getUMLDataModel().getElementProperty("OPRoSDataTypesElementModel");
		return dmts.getataTypeReference();
		
	}
	
	
	public OPRoSProjectInfo getOPRoSProjectInfo(){
		if(info==null)
			info = new OPRoSProjectInfo();
//		this.getCmpFolder()
		info.setBinFolder(this.getCmpFolder());
		info.setImplLanguage(this.getimplementLangString());

		IWorkspace workspace = ResourcesPlugin.getWorkspace();//워크스페이스 핸들
		IWorkspaceRoot root = workspace.getRoot();//워크스페이스 루트핸들
		info.setLocation(root.getLocation().toOSString());
		info.addComponent(this.getName());
		info.setPrjName(this.getName());
		info.setSrcFolder(this.getCmpFolder());
		
		
		
		return info;
	}
	
	

	public java.util.ArrayList getTypeRefMethodModelAll(String typeRef){
		java.util.ArrayList list2 = new java.util.ArrayList();
		String filePath =typeRef;
//		String type = this.getType();
		
		if(!filePath.isEmpty()){
			SAXBuilder builder = new SAXBuilder();
			Document doc = null;
			File f = new File(filePath);	// WJH 100810 추가
			try {
				if(f.exists())	// WJH 100810 추가
					doc = builder.build( new FileInputStream(filePath));
			} catch (JDOMException e2) {
				e2.printStackTrace();
			} catch (IOException e3) {
				e3.printStackTrace();
			}
		Element root;
			root = doc.getRootElement();
			System.out.println("root=====>"+root);
			java.util.List list = root.getChildren("service_port_type");
			for(int i=0;i<list.size();i++){
				ServiceModel serviceModel = new ServiceModel();
				Element child = (Element)list.get(i);
				String text = child.getChildText("type_name");
				System.out.println("text=====>"+text);
//				if(type.equals(text)){
					java.util.List methods = child.getChildren("method");
					for(int i1=0;i1<methods.size();i1++){
						MethodModel mm = new MethodModel();
						Element method = (Element)methods.get(i1);
					mm.name = method.getAttributeValue("name");
				    mm.returnType = method.getAttributeValue("return_type");
				    System.out.println("method=====>"+methods);
					java.util.List params = method.getChildren("param");// WJH 100721 child->method로 변경
					for(int i2=0;i2<params.size();i2++){
						ParamModel pm = new ParamModel();
						Element param = (Element)params.get(i2);
						pm.name = param.getChildText("name");
						pm.type = param.getChildText("type");
						mm.getParams().add(pm);
						System.out.println("param=====>"+params);
					}
					serviceModel.methods.add(mm);
					}
					
//				}
					serviceModel.typeName = text;
					list2.add(serviceModel);
//				child.get
			}
			System.out.println("list=====>"+list);
		

	}

		return list2;
		
	}
	
	
	public java.util.ArrayList getTypeDataModelAll(String path){
		java.util.ArrayList list2 = new java.util.ArrayList();
		String filePath =path;
//		String type = this.getType();
		
		if(!filePath.isEmpty()){
			SAXBuilder builder = new SAXBuilder();
			Document doc = null;
			try {
				doc = builder.build( new FileInputStream(filePath));
			} catch (JDOMException e2) {
				e2.printStackTrace();
			} catch (IOException e3) {
				e3.printStackTrace();
			}
		Element root;
			root = doc.getRootElement();
			System.out.println("root=====>"+root);
			java.util.List list = root.getChildren("struct");
			for(int i=0;i<list.size();i++){
				DataModel dataModel = new DataModel();
				Element child = (Element)list.get(i);
				String text = child.getAttributeValue("name");
				System.out.println("text=====>"+text);
				dataModel.name = text;
//				
					java.util.List methods = child.getChildren("item");
					for(int i1=0;i1<methods.size();i1++){
						ItemModel mm = new ItemModel();
						Element item = (Element)methods.get(i1);
					mm.type = item.getAttributeValue("type");
				    mm.name = item.getText();
				    System.out.println("method=====>"+methods);
					java.util.List params = item.getChildren("param");// WJH 100721 child->item로 변경
					
					dataModel.items.add(mm);
					}
					

					
					list2.add(dataModel);
//				child.get
			}
			System.out.println("list=====>"+list);
		

	}

		return list2;
		
	}
	
	public void setTags(java.util.ArrayList p){
		super.setTags(p);
//		this.getTags().r
		OPRoSPropertiesElementModel pms1 = (OPRoSPropertiesElementModel)this.getClassModel().getUMLDataModel().getElementProperty("OPRoSPropertiesElementModel");
//		if(pms1)
		//		pms1.e
//		if(pms1)
		if(pms1!=null)
		pms1.removeAll();
		for(int i=0;i<p.size();i++){
			Object obj = p.get(i);
			if(obj instanceof DetailPropertyTableItem){
				DetailPropertyTableItem dp = (DetailPropertyTableItem)obj;
				if(pms1!=null)
				pms1.addChild(dp.getOPRoSPropertyElementModel());
			}
		}
//		uMLDataModel.setElementProperty(this.ID_TAG, p);

	}
	
	public void setMonitorVariables(java.util.ArrayList p){
//		super.setMonitorVariables(p);
		UMLTreeModel ut = this.getCoreUMLTreeModel();
        if(ut!=null){
        	UMLModel um = (UMLModel)ut.getRefModel();
        	if(um instanceof AtomicComponentModel){
        		AtomicComponentModel am = (AtomicComponentModel)um;
        		am.setMonitorVariables2(p);
        	}
		
		OPRoSMonitorVariablesElementModel pms1 = (OPRoSMonitorVariablesElementModel)this.getClassModel().getUMLDataModel().getElementProperty("OPRoSMonitorVariablesElementModel");

		if(pms1!=null)
		pms1.removeAll();
		for(int i=0;i<p.size();i++){
			Object obj = p.get(i);
			if(obj instanceof DetailPropertyTableItem){
				DetailPropertyTableItem dp = (DetailPropertyTableItem)obj;
				if(pms1!=null)
				pms1.addChild(dp.getOPRoSMonitorVariableElementModel());
			}
		}
        }
        else{
        	this.setMonitorVariables2(p);
        	OPRoSMonitorVariablesElementModel pms1 = (OPRoSMonitorVariablesElementModel)this.getClassModel().getUMLDataModel().getElementProperty("OPRoSMonitorVariablesElementModel");

    		if(pms1!=null)
    		pms1.removeAll();
    		for(int i=0;i<p.size();i++){
    			Object obj = p.get(i);
    			if(obj instanceof DetailPropertyTableItem){
    				DetailPropertyTableItem dp = (DetailPropertyTableItem)obj;
    				if(pms1!=null)
    				pms1.addChild(dp.getOPRoSMonitorVariableElementModel());
    			}
    		}
        }


	}
	
	public void setMonitorVariables3(java.util.ArrayList p){
//		super.setMonitorVariables(p);
		
        	this.setMonitorVariables2(p);
		
		OPRoSMonitorVariablesElementModel pms1 = (OPRoSMonitorVariablesElementModel)this.getClassModel().getUMLDataModel().getElementProperty("OPRoSMonitorVariablesElementModel");

		if(pms1!=null)
		pms1.removeAll();
		for(int i=0;i<p.size();i++){
			Object obj = p.get(i);
			if(obj instanceof DetailPropertyTableItem){
				DetailPropertyTableItem dp = (DetailPropertyTableItem)obj;
				if(pms1!=null)
				pms1.addChild(dp.getOPRoSMonitorVariableElementModel());
			}
		}
       


	}
	
	public void modifySource(){
		HashSet provideSet = new HashSet();
		ArrayList<String> includeList = new ArrayList<String>();
		String strCompName=this.getName();
		
//		String hfileName = project.getLocation()+"/"+strCompName+"/"+strCompName+".h";
//		String cppfileName = File  f = new File(this.getCmpFolder()+File.separator+this.getName()+".cpp");
		File headerFile = new File(this.getCmpFolder()+File.separator+this.getName()+".h");
		File cppFile = new File(this.getCmpFolder()+File.separator+this.getName()+".cpp");
		int b;
		StringBuffer fileContents = new StringBuffer();
		StringBuffer cppFileContents = new StringBuffer();
		try
		{		
			BufferedReader buffRead = new BufferedReader(new FileReader(headerFile));
			while ((b = buffRead.read()) != -1) {
				fileContents.append((char)b);
			}			
			buffRead.close();		
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
		try
		{		
			BufferedReader buffRead = new BufferedReader(new FileReader(cppFile));
			while ((b = buffRead.read()) != -1) {
				cppFileContents.append((char)b);
			}			
			buffRead.close();		
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
		String addString="";
		Iterator  it;
		PortModel portModel;
		//dataPort delete h modify
		it = this.getPortManager().getDelPorts().iterator();
		while(it.hasNext()){
			portModel = (PortModel)it.next();
			if(portModel instanceof DataInputPortModel){
				int nIndex = findIndexContents(fileContents,portModel.getName(),0,false);
				while(nIndex!=-1){
					int nCommentStart = fileContents.lastIndexOf("\n",nIndex)+1;
					nIndex=findIndexContents(fileContents,";",nIndex,false)+1;
					fileContents.insert(nCommentStart,"/*");
					fileContents.insert(nIndex+2, "*/");
					nIndex = findIndexContents(fileContents,portModel.getName(),nIndex+2,false);
				}
				//Source File 생성자의 데이터 포트 생성자 삭제
				String strCPP=strCompName+"(";
				int nCppIndex = findIndexContents(cppFileContents,strCPP,0,false);
				int nBraceIndex=0;
				int nDelPosition=0;
				while(nCppIndex!=-1){
					nCppIndex = findIndexContents(cppFileContents,")",nCppIndex,false);
					nBraceIndex = findIndexContents(cppFileContents,"{",nCppIndex,false);
					nDelPosition = findIndexContents(cppFileContents,portModel.getName(),nCppIndex,false);
					if(nDelPosition!=-1&&nCppIndex<nDelPosition&&nDelPosition<nBraceIndex){
						int newDelPosition=findDelpositionConstructor(cppFileContents,nDelPosition);
						if(newDelPosition!=-1){
							if(cppFileContents.charAt(newDelPosition)==':'){
								int nReplaceIndex = findNextConstructor(cppFileContents,newDelPosition);
								if(nReplaceIndex!=-1){
									cppFileContents.replace(nReplaceIndex, nReplaceIndex+1, ":");
								}
							}
						}
						nCppIndex = findIndexContents(cppFileContents,")",nDelPosition,false);
						cppFileContents.insert(nDelPosition-1, "/*");//:포함 삭제때문에 -1
						cppFileContents.insert(nCppIndex+3, "*/");
						nCppIndex = findIndexContents(cppFileContents,strCPP,nCppIndex+3,false);
					}else{
						nCppIndex = findIndexContents(cppFileContents,strCPP,nBraceIndex,false);
					}
				}
				//SourceFile addPort 문 삭제
				strCPP="void "+strCompName+"::portSetup()";
				nCppIndex = findIndexContents(cppFileContents,strCPP,0,false);
				int nPortNameIndex=0;
				if(nCppIndex!=-1){
					String strDataDelPort="addPort";
					nDelPosition = findIndexContents(cppFileContents,strDataDelPort,nCppIndex,false);
					nPortNameIndex = findIndexContents(cppFileContents,portModel.getName(),nDelPosition,false);
					int nParaseIndex =0;
					while(nDelPosition!=-1&&nPortNameIndex!=-1){
						nParaseIndex = findIndexContents(cppFileContents,")",nDelPosition,false);
						if(nParaseIndex!=-1){
							if(nPortNameIndex>nParaseIndex){
								nDelPosition = findIndexContents(cppFileContents,strDataDelPort,nParaseIndex,false);
							}
							else if(nDelPosition<nPortNameIndex && nPortNameIndex<nParaseIndex){
								int nCommentStart = nDelPosition;
								nDelPosition = findIndexContents(cppFileContents,";",nDelPosition,false)+1;
								cppFileContents.insert(nCommentStart, "/*");
								cppFileContents.insert(nDelPosition+2, "*/");
								nDelPosition = findIndexContents(cppFileContents,strDataDelPort,nDelPosition+2,false);
								nPortNameIndex = findIndexContents(cppFileContents,portModel.getName(),nDelPosition,false);
							}else{
								nPortNameIndex = findIndexContents(cppFileContents,portModel.getName(),nPortNameIndex+portModel.getName().length(),false);
							}
						}else{
							break;
						}
					}
				}
			}
			else if(portModel instanceof DataOutputPortModel){
				//HeaderFile 인스턴스 삭제
				int nIndex = findIndexContents(fileContents,portModel.getName(),0,false);
				while(nIndex!=-1){
					int nCommentStart = fileContents.lastIndexOf("\n",nIndex)+1;
					nIndex=findIndexContents(fileContents,";",nIndex,false)+1;
					fileContents.insert(nCommentStart, "/*");
					fileContents.insert(nIndex+2, "*/");
					nIndex = findIndexContents(fileContents,portModel.getName(),nIndex+2,false);
				}
				//SourceFile addPort문 삭제
				String strCPP="void "+strCompName+"::portSetup()";
				int nCppIndex = findIndexContents(cppFileContents,strCPP,0,false);
				int nPortNameIndex=0;
				if(nCppIndex!=-1){
					String strDataDelPort="addPort";
					int nDelPosition = findIndexContents(cppFileContents,strDataDelPort,nCppIndex,false);
					nPortNameIndex = findIndexContents(cppFileContents,portModel.getName(),nDelPosition,false);
					int nParaseIndex =0;
					while(nDelPosition!=-1&&nPortNameIndex!=-1){
						nParaseIndex = findIndexContents(cppFileContents,")",nDelPosition,false);
						if(nParaseIndex!=-1){
							if(nPortNameIndex>nParaseIndex){
								nDelPosition = findIndexContents(cppFileContents,strDataDelPort,nParaseIndex,false);
							}
							else if(nDelPosition<nPortNameIndex && nPortNameIndex<nParaseIndex){
								int nCommentStart = nDelPosition;
								nDelPosition = findIndexContents(cppFileContents,";",nDelPosition,false)+1;
								cppFileContents.insert(nCommentStart, "/*");
								cppFileContents.insert(nDelPosition+2, "*/");
								nDelPosition = findIndexContents(cppFileContents,strDataDelPort,nDelPosition+2,false);
								nPortNameIndex = findIndexContents(cppFileContents,portModel.getName(),nDelPosition,false);
							}else{
								nPortNameIndex = findIndexContents(cppFileContents,portModel.getName(),nPortNameIndex+portModel.getName().length(),false);
							}
						}else{
							break;
						}
					}
				}
			}
		}
		//dataPort add h modify
		int classIndex = findIndexContents(fileContents,"class",0,false);
		if(classIndex==-1)
			return;
		it=this.getPortManager().getAddPorts().iterator();
		boolean isAdded=false;
		int protectedIndex=0;
		if(it.hasNext()){
			protectedIndex=addString.length();
			addString="\nprotected:\n//data\n";
		}
		while(it.hasNext()){
			 portModel = (PortModel)it.next();
			 if(portModel instanceof DataInputPortModel){
				 isAdded=true;
				 //HeaderFile 인스턴스 선언
				 addString = addString+"\tInputDataPort< "+portModel.getType()+" > "+portModel.getName()+";\n";
				 //SourceFile 생성자 데이터포트 생성 추가
				 String strCPP=strCompName+"(";
				 int nCppIndex = findIndexContents(cppFileContents,strCPP,0,false);
				 while(nCppIndex!=-1){
					 nCppIndex = findIndexContents(cppFileContents,")",nCppIndex,false);
					 int nBraceIndex = findIndexContents(cppFileContents,"{",nCppIndex,false);
					 int nColonIndex = findIndexContents(cppFileContents,":",nCppIndex,false);
					 if(cppFileContents.charAt(nCppIndex+1)!=';'){
						 if(nCppIndex<nColonIndex&&nColonIndex<nBraceIndex){
							 cppFileContents.insert(nBraceIndex-1, "\n\t,"+portModel.getName()+"(OPROS_"+((DataInputPortModel)portModel).getQueueingPolicy()+","+((DataInputPortModel)portModel).getQueueSize()+")");
						 }else{
							 cppFileContents.insert(nCppIndex+1, "\n\t:"+portModel.getName()+"(OPROS_"+((DataInputPortModel)portModel).getQueueingPolicy()+","+((DataInputPortModel)portModel).getQueueSize()+")"); 
						 }
					 }
					 nCppIndex = findIndexContents(cppFileContents,strCPP,nCppIndex,false);
				 }
				 
				 //SourceFile addPort문 추가
				 strCPP="void "+strCompName+"::portSetup()";
				 nCppIndex = findIndexContents(cppFileContents,strCPP,0,false);
				 if(nCppIndex!=-1){
					 nCppIndex = findIndexContents(cppFileContents,"{",nCppIndex,false);
					 cppFileContents.insert(nCppIndex+1, "\n//data port setup\n\taddPort(\""+portModel.getName()+"\", &"+portModel.getName()+");\n");
				 }
			 }
			 else if(portModel instanceof DataOutputPortModel){
				 isAdded=true;
				 //HeaderFile 인스턴스 선언
				 addString = addString+"\tOutputDataPort< "+portModel.getType()+" > "+portModel.getName()+";\n";
				 //SourceFile addPort문 추가
				 String strCPP="void "+strCompName+"::portSetup()";
				 int nCppIndex = findIndexContents(cppFileContents,strCPP,0,false);
				 if(nCppIndex!=-1){
					 nCppIndex = findIndexContents(cppFileContents,"{",nCppIndex,false);
					 cppFileContents.insert(nCppIndex+1, "\n//data port setup\n\taddPort(\""+portModel.getName()+"\", &"+portModel.getName()+");\n");
				 }
			 }
			 //HeaderFile UserDataType의 include문 추가
			 if(portModel instanceof DataOutputPortModel||portModel instanceof DataInputPortModel){
				 boolean bSystemType=false;
				 for(int i=0;i<20;i++){
					 if(OPRoSUtil.dataTypes[i].compareTo(portModel.getType())==0)
						 bSystemType=true;
					 else if(i>6){
						 if(portModel.getType().startsWith(OPRoSUtil.dataTypes[i]))
							 bSystemType=true;
					 }
				 }
				 if(!bSystemType&&findIndexContents(fileContents,portModel.getType(),0,false)==-1&&!includeList.contains(portModel.getType())){
					 String strInclude="";
					 strInclude = "#include \""+portModel.getType()+".h\"";
					 int nIndex = findIndexContents(fileContents,strInclude,0,false);
					 if(nIndex==-1){
						 fileContents.insert(classIndex-1, "\n"+strInclude+"\n");
						 includeList.add(portModel.getType());
						 classIndex = findIndexContents(fileContents,"class",0,false);
						if(classIndex==-1)return;
					 }
				 }
			 }
		}
		if(!isAdded){
			addString=addString.substring(0, protectedIndex);
		}
		isAdded=false;
		
		//eventPort delete h modify
		it=this.getPortManager().getDelPorts().iterator();
		while(it.hasNext()){
			portModel = (PortModel)it.next();
			if(portModel instanceof EventInputPortModel){
				//HeaderFile EventPort Instance delete
				int nIndex = findIndexContents(fileContents,portModel.getName(),0,false);
				while(nIndex!=-1){
					int nCommentStart = fileContents.lastIndexOf("\n",nIndex)+1;
					nIndex=findIndexContents(fileContents,";",nIndex,false)+1;
					fileContents.insert(nCommentStart, "/*");
					fileContents.insert(nIndex+2, "*/");
					nIndex = findIndexContents(fileContents,portModel.getName(),nIndex+2,false);
				}
				//SourceFile addPort Statement delete
				String strCPP="void "+strCompName+"::portSetup()";
				int nCppIndex = findIndexContents(cppFileContents,strCPP,0,false);
				int nPortNameIndex=0;
				if(nCppIndex!=-1){
					String strEventDelPort = "addPort";
					int nDelPosition = findIndexContents(cppFileContents,strEventDelPort,nCppIndex,false);
					nPortNameIndex = findIndexContents(cppFileContents,portModel.getName(),nDelPosition,false);
					int nParaseIndex =0;
					while(nDelPosition!=-1&&nPortNameIndex!=-1){
						nParaseIndex = findIndexContents(cppFileContents,")",nDelPosition,false);
						if(nParaseIndex!=-1){
							if(nPortNameIndex>nParaseIndex){
								nDelPosition = findIndexContents(cppFileContents,strEventDelPort,nParaseIndex,false);
							}
							else if(nDelPosition<nPortNameIndex && nPortNameIndex<nParaseIndex){
								int nCommentStart=nDelPosition;
								nDelPosition = findIndexContents(cppFileContents,";",nDelPosition,false)+1;
								cppFileContents.insert(nCommentStart, "/*");
								cppFileContents.insert(nDelPosition+2, "*/");
								nDelPosition = findIndexContents(cppFileContents,strEventDelPort,nDelPosition+2,false);
								nPortNameIndex = findIndexContents(cppFileContents,portModel.getName(),nDelPosition,false);
							}else{
								nPortNameIndex = findIndexContents(cppFileContents,portModel.getName(),nPortNameIndex+portModel.getName().length(),false);
							}
						}else{
							break;
						}
					}
					strEventDelPort=portModel.getName()+".setOwner(";
					int nDelPosition1 = findIndexContents(cppFileContents,strEventDelPort,0,false);
					if(nDelPosition1!=-1){
						int nBraceIndex = findIndexContents(cppFileContents,"}",nCppIndex,false);
						if(nCppIndex<nDelPosition1&&nDelPosition1<nBraceIndex){
							int nCommentStart=nDelPosition1;
							nDelPosition1 = findIndexContents(cppFileContents,";",nDelPosition1,false)+1;
							cppFileContents.insert(nCommentStart, "/*");
							cppFileContents.insert(nDelPosition1+2, "*/");
						}
					}
				}
			}
			else if(portModel instanceof EventOutputPortModel){
				//HeaderFile EventPort Instance delete
				int nIndex = findIndexContents(fileContents,portModel.getName(),0,false);
				while(nIndex!=-1){
					int nCommentStart = fileContents.lastIndexOf("\n",nIndex)+1;
					
					nIndex=findIndexContents(fileContents,";",nIndex,false)+1;
					fileContents.insert(nCommentStart, "/*");
					fileContents.insert(nIndex+2, "*/");
					nIndex = findIndexContents(fileContents,portModel.getName(),nIndex+2,false);
				}
				String strCPP="void "+strCompName+"::portSetup()";
				int nCppIndex = findIndexContents(cppFileContents,strCPP,0,false);
				if(nCppIndex!=-1){
					String strEventDelPort = "addPort";
					int nDelPosition = findIndexContents(cppFileContents,strEventDelPort,nCppIndex,false);
					int nPortNameIndex = findIndexContents(cppFileContents,portModel.getName(),nDelPosition,false);
					int nParaseIndex =0;
					while(nDelPosition!=-1&&nPortNameIndex!=-1){
						nParaseIndex = findIndexContents(cppFileContents,")",nDelPosition,false);
						if(nParaseIndex!=-1){
							if(nPortNameIndex>nParaseIndex){
								nDelPosition = findIndexContents(cppFileContents,strEventDelPort,nParaseIndex,false);
							}
							else if(nDelPosition<nPortNameIndex && nPortNameIndex<nParaseIndex){
								int nCommentStart=nDelPosition;
								
								nDelPosition = findIndexContents(cppFileContents,";",nDelPosition,false)+1;
								cppFileContents.insert(nCommentStart, "/*");
								cppFileContents.insert(nDelPosition+2, "*/");
								nDelPosition = findIndexContents(cppFileContents,strEventDelPort,nDelPosition+2,false);
								nPortNameIndex = findIndexContents(cppFileContents,portModel.getName(),nDelPosition,false);
							}else{
								nPortNameIndex = findIndexContents(cppFileContents,portModel.getName(),nPortNameIndex+portModel.getName().length(),false);
							}
						}else{
							break;
						}
					}
				}
			}
		}
		//eventPort add h modify
		classIndex = findIndexContents(fileContents,"class",0,false);
		if(classIndex==-1)
			return;
		it=this.getPortManager().getAddPorts().iterator();
		if(it.hasNext()){
			protectedIndex=addString.length();
			addString=addString + "\nprotected:\n// event\n";
		}
		while(it.hasNext()){
			portModel=(PortModel)it.next();
			if(portModel instanceof EventInputPortModel){
				isAdded=true;
				addString = addString+"\tInputEventPort< "+portModel.getType()+" > "+portModel.getName()+";\n";
				
				String strCPP="void "+strCompName+"::portSetup()";
				int nCppIndex = findIndexContents(cppFileContents,strCPP,0,false);
				if(nCppIndex!=-1){
					String strEventAddPort="addPort(\""+portModel.getName()+"\",";
					if(findIndexContents(cppFileContents,strEventAddPort,0,false)==-1){
						nCppIndex = findIndexContents(cppFileContents,"{",nCppIndex,false);
						cppFileContents.insert(nCppIndex+1, "\n//event port setup\n\taddPort(\""+portModel.getName()+"\", &"
								+portModel.getName()+");\n\t"+portModel.getName()+".setOwner(this);\n");
					}
				}
			}
			else if(portModel instanceof EventOutputPortModel){
				isAdded=true;
				addString = addString+"\tOutputEventPort< "+portModel.getType()+" > "+portModel.getName()+";\n";
				String strCPP="void "+strCompName+"::portSetup()";
				int nCppIndex = findIndexContents(cppFileContents,strCPP,0,false);
				if(nCppIndex!=-1){
					String strEventAddPort="addPort(\""+portModel.getName()+"\",";
					if(findIndexContents(cppFileContents,strEventAddPort,0,false)==-1){
						nCppIndex = findIndexContents(cppFileContents,"{",nCppIndex,false);
						cppFileContents.insert(nCppIndex+1, "\n//event port setup\n\taddPort(\""+portModel.getName()+"\", &"+portModel.getName()+");\n");
					}
				}
			}
			if(portModel instanceof EventOutputPortModel||portModel instanceof EventInputPortModel){
				boolean bSystemType=false;
				for(int i=0;i<20;i++){
					if(OPRoSUtil.dataTypes[i].compareTo(portModel.getType())==0)
						bSystemType=true;
				}
				if(!bSystemType&&findIndexContents(fileContents,portModel.getType(),0,false)==-1&&!includeList.contains(portModel.getType())){
					 String strInclude="";
					 strInclude = "#include \""+portModel.getType()+".h\"";
					 int nIndex = findIndexContents(fileContents,strInclude,0,false);
					 if(nIndex==-1){
						 fileContents.insert(classIndex-1, "\n"+strInclude+"\n");
						 includeList.add(portModel.getType());
						classIndex = findIndexContents(fileContents,"class",0,false);
						if(classIndex==-1)return;
					 }
				}
			}
		}
		if(!isAdded){
			addString=addString.substring(0, protectedIndex);
		}
		isAdded=false;
	
		//methodPort delete h modify
		it=this.getPortManager().getDelPorts().iterator();
		while(it.hasNext()){
			portModel = (PortModel)it.next();
			if(portModel instanceof MethodInputPortModel){
				int nIndex = findIndexContents(fileContents,"ptr"+portModel.getName(),0,false);
				while(nIndex!=-1){
					int nCommentStart = fileContents.lastIndexOf("\n",nIndex)+1;
					nIndex=findIndexContents(fileContents,";",nIndex,false)+1;
					fileContents.insert(nCommentStart,"/*");
					fileContents.insert(nIndex+2, "*/");
					nIndex = findIndexContents(fileContents,"ptr"+portModel.getName(),nIndex+2,false);
				}
				String strCPP="void "+strCompName+"::portSetup()";
				int nCppIndex = findIndexContents(cppFileContents,strCPP,0,false);
				int nPortNameIndex=0;
				if(nCppIndex!=-1){
					String strMethodDelPort="addPort";
					int nDelPosition = findIndexContents(cppFileContents,strMethodDelPort,nCppIndex,false);
					nPortNameIndex = findIndexContents(cppFileContents,portModel.getName(),nDelPosition,true);
					int nParaseIndex =0;
					while(nDelPosition!=-1&&nPortNameIndex!=-1){
						nParaseIndex = findIndexContents(cppFileContents,")",nDelPosition,false);
						if(nParaseIndex!=-1){
							if(nPortNameIndex>nParaseIndex){
								nDelPosition = findIndexContents(cppFileContents,strMethodDelPort,nParaseIndex,false);
							}
							else if(nDelPosition<nPortNameIndex && nPortNameIndex<nParaseIndex){
								//addPort 찾음.
								String strVariableName = findVarName(cppFileContents,nPortNameIndex);
								if(!strVariableName.isEmpty()){
									int nDecla = findIndexContents(cppFileContents,strVariableName,0,false);
									while(nDecla!=-1){
										int nCommentStart = cppFileContents.lastIndexOf("\n",nDecla)+1;
										nDecla = findIndexContents(cppFileContents,";",nDecla,false)+1;
										cppFileContents.insert(nCommentStart, "/*");
										cppFileContents.insert(nDecla+2, "*/");
										nDecla = findIndexContents(cppFileContents,strVariableName,nDecla+2,false);
									}
									break;
								}
							}else{
								nPortNameIndex = findIndexContents(cppFileContents,portModel.getName(),nPortNameIndex+portModel.getName().length(),true);
							}
						}else{
							break;
						}
					}
				}
				
			}
			else if(portModel instanceof MethodOutputPortModel){
				int nIndex = findIndexContents(fileContents,"ptr"+portModel.getName(),0,false);
				while(nIndex!=-1){
					int nCommentStart = fileContents.lastIndexOf("\n",nIndex)+1;
					nIndex=findIndexContents(fileContents,";",nIndex,false)+1;
					fileContents.insert(nCommentStart,"/*");
					fileContents.insert(nIndex+2, "*/");
					nIndex = findIndexContents(fileContents,"ptr"+portModel.getName(),nIndex+2,false);
				}
				String strCPP="void "+strCompName+"::portSetup()";
				int nCppIndex = findIndexContents(cppFileContents,strCPP,0,false);
				int nPortNameIndex=0;
				if(nCppIndex!=-1){
					String strMethodDelPort="addPort";
					int nDelPosition = findIndexContents(cppFileContents,strMethodDelPort,nCppIndex,false);
					nPortNameIndex = findIndexContents(cppFileContents,portModel.getName(),nDelPosition,true);
					int nParaseIndex =0;
					while(nDelPosition!=-1&&nPortNameIndex!=-1){
						nParaseIndex = findIndexContents(cppFileContents,")",nDelPosition,false);
						if(nParaseIndex!=-1){
							if(nPortNameIndex>nParaseIndex){
								nDelPosition = findIndexContents(cppFileContents,strMethodDelPort,nParaseIndex,false);
							}
							else if(nDelPosition<nPortNameIndex && nPortNameIndex<nParaseIndex){
								//addPort 찾음.
								String strVariableName = findVarName(cppFileContents,nPortNameIndex);
								if(!strVariableName.isEmpty()){
									int nDecla = findIndexContents(cppFileContents,strVariableName,0,false);
									while(nDecla!=-1){
										int nCommentStart = cppFileContents.lastIndexOf("\n",nDecla)+1;
										nDecla = findIndexContents(cppFileContents,";",nDecla,false)+1;
										cppFileContents.insert(nCommentStart, "/*");
										cppFileContents.insert(nDecla+2, "*/");
										nDecla = findIndexContents(cppFileContents,strVariableName,nDecla+2,false);
									}
									break;
								}
							}else{
								nPortNameIndex = findIndexContents(cppFileContents,portModel.getName(),nPortNameIndex+portModel.getName().length(),true);
							}
						}else{
							break;
						}
					}
				}
			}
		}
		//methodPort add h modify
		classIndex = findIndexContents(fileContents,"class",0,false);
		if(classIndex==-1)
			return;
		it=this.getPortManager().getAddPorts().iterator();
		if(it.hasNext()){
			protectedIndex=addString.length();
			addString=addString+"\nprotected:\n// service\n";
		}
		while(it.hasNext()){
			portModel=(PortModel)it.next();
			if(portModel instanceof MethodOutputPortModel){
				isAdded=true;
				addString=addString+"// method for provider\n\tI"+portModel.getType()+" *ptr"+portModel.getName()+";\n";
				if(findIndexContents(fileContents,portModel.getType()+"Provided",0,false)==-1&&!includeList.contains(portModel.getType()+"Provided")){
					 String strInclude="";
					 strInclude = "#include \""+portModel.getType()+"Provided.h\"";
					 int nIndex = findIndexContents(fileContents,strInclude,0,false);
					 if(nIndex==-1){
						 fileContents.insert(classIndex-1, "\n"+strInclude+"\n");
						 includeList.add(portModel.getType()+"Provided");
						 provideSet.add(portModel);
						 classIndex = findIndexContents(fileContents,"class",0,false);
						 if(classIndex==-1)return;
					 }
				 }
				
				String strCPP="public I"+portModel.getType();
				 int nCppIndex = findIndexContents(fileContents,strCPP,0,false);
				 if(nCppIndex==-1){
					 int nBraceIndex = findIndexContents(fileContents,"{",classIndex,false);
					 fileContents.insert(nBraceIndex-1, "\n\t,"+strCPP);
				 }
				 
				strCPP="void "+strCompName+"::portSetup()";
				nCppIndex = findIndexContents(cppFileContents,strCPP,0,false);
				if(nCppIndex!=-1){
					nCppIndex = findIndexContents(cppFileContents,"{",nCppIndex,false);
					int nServiceCnt=getServiceCnt(cppFileContents,strCompName);
					cppFileContents.insert(nCppIndex+1, "\n\tProvidedServicePort *pa"+String.valueOf(nServiceCnt+1)+";\n"
						+"\tpa"+String.valueOf(nServiceCnt+1)+"=new "+portModel.getType()+"Provided(this);\n"
						+"\taddPort(\""+portModel.getName()+"\",pa"+String.valueOf(nServiceCnt+1)+");\n");
				}
			}else if(portModel instanceof MethodInputPortModel){
				isAdded=true;
				addString=addString+"// method for required\n\t"+portModel.getType()+"Required *ptr"+portModel.getName()+";\n";
				if(findIndexContents(fileContents,portModel.getType()+"Required",0,false)==-1&&!includeList.contains(portModel.getType()+"Required")){
					 String strInclude="";
					 strInclude = "\n#include \""+portModel.getType()+"Required.h\"\n";
					 int nIndex = findIndexContents(fileContents,strInclude,0,false);
					 if(nIndex==-1){
						 fileContents.insert(classIndex-1, "\n"+strInclude+"\n");
						 includeList.add(portModel.getType()+"Required");
						 classIndex = findIndexContents(fileContents,"class",0,false);
						 if(classIndex==-1)return;
					 }
				 }
				String strCPP=strCompName+"::"+strCompName+"()";
				int nCppIndex = findIndexContents(cppFileContents,strCPP,0,false);
				if(nCppIndex!=-1){
					int nAddPosition = findIndexContents(cppFileContents,"{",nCppIndex,false);
					if(nAddPosition!=-1){
						cppFileContents.insert(nAddPosition+1, "\n\tptr"+portModel.getName()+" = NULL;\n");
					}
				 }
				 strCPP=strCompName+"::"+strCompName+"(const std::string &name)";
				 nCppIndex = findIndexContents(cppFileContents,strCPP,0,false);
				 if(nCppIndex!=-1){
					int nAddPosition = findIndexContents(cppFileContents,"{",nCppIndex,false);
					if(nAddPosition!=-1){
						cppFileContents.insert(nAddPosition+1, "\n\tptr"+portModel.getName()+" = NULL;\n");
					}
				 }
				strCPP="void "+strCompName+"::portSetup()";
				nCppIndex = findIndexContents(cppFileContents,strCPP,0,false);
				if(nCppIndex!=-1){
					nCppIndex = findIndexContents(cppFileContents,"{",nCppIndex,false);
					cppFileContents.insert(nCppIndex+1,"\n\tptr"+portModel.getName()+"=new "+portModel.getType()+"Required();\n"
							+"\taddPort(\""+portModel.getName()+"\",ptr"+portModel.getName()+");\n");
				}
			}
		}
		if(!isAdded){
			addString=addString.substring(0, protectedIndex);
		}
		isAdded=false;
		
		Iterator<PortModel> PMIt = provideSet.iterator();
		if(PMIt.hasNext()){
			addString = addString + "\npublic:\n";
		}
		Document serviceTypeDoc=null;
		while(PMIt.hasNext()){
			PortModel serviceModel=PMIt.next();
			if(serviceModel!=null){
				if(serviceModel instanceof MethodOutputPortModel)
					serviceTypeDoc = this.getServiceTypeDoc(((MethodOutputPortModel)serviceModel).getTypeRef());
			}
			if(serviceTypeDoc!=null){
				Iterator eleIt = serviceTypeDoc.getRootElement().getChildren().iterator();
				while(eleIt.hasNext()){
					Element ele = (Element)eleIt.next();
					String strTypeName = ele.getChildText("type_name");
					if(strTypeName.compareTo(serviceModel.getType())==0){
						Iterator methodIt = ele.getChildren("method").iterator();
						while(methodIt.hasNext()){
							String strMethod="";
							String strCppMethod="";
							Element methodEle = (Element)methodIt.next();
							strMethod = "virtual "+ methodEle.getAttributeValue("return_type")+" "+methodEle.getAttributeValue("name")+"(";
							strCppMethod = methodEle.getAttributeValue("return_type")+" "+strCompName+"::"+methodEle.getAttributeValue("name")+"(";
							Iterator paramIt = methodEle.getChildren("param").iterator();
							if(paramIt.hasNext()){
								Element paramEle = (Element)paramIt.next();
								strMethod = strMethod + paramEle.getChildText("type")+" "+paramEle.getChildText("name");
								strCppMethod = strCppMethod + paramEle.getChildText("type")+" "+paramEle.getChildText("name");
							}
							while(paramIt.hasNext()){
								Element paramEle = (Element)paramIt.next();
								strMethod = strMethod + ","+paramEle.getChildText("type")+" "+paramEle.getChildText("name");
								strCppMethod = strCppMethod + ","+paramEle.getChildText("type")+" "+paramEle.getChildText("name");
							}
							strMethod = strMethod + ");";
							strCppMethod = strCppMethod + ")";
							if(findIndexContents(fileContents,strMethod,0,false)==-1)
								addString = addString+strMethod+"\n";
							strMethod="";
							
							String strCPP=strCompName+"::~"+strCompName+"()";
							int nCppIndex = findIndexContents(cppFileContents,strCPP,0,false);
							if(nCppIndex!=-1){
								nCppIndex = findIndexContents(cppFileContents,"}",nCppIndex,false);
								if(findIndexContents(cppFileContents,strCppMethod,0,false)==-1)
									cppFileContents.insert(nCppIndex+1, "\n"+strCppMethod+"\n{\n\t//user code hear\n}\n");
								strMethod="";
							}
							
						}
						
					}
				}
			}
		}
		
				
		classIndex = findIndexContents(fileContents,"class",0,false);
		if(classIndex==-1)
			return;
		int index = findIndexContents(fileContents,"{",classIndex,false);
		if(index==-1)
			return;
		fileContents.insert(index+1, addString);
		try
		{
			BufferedWriter buffWrite = new BufferedWriter(new FileWriter(headerFile));
			buffWrite.write(fileContents.toString(), 0, fileContents.length());
			buffWrite.flush();
			buffWrite.close();
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
		try
		{
			BufferedWriter buffWrite = new BufferedWriter(new FileWriter(cppFile));
			buffWrite.write(cppFileContents.toString(), 0, cppFileContents.length());
			buffWrite.flush();
			buffWrite.close();
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}
	
	
	//만일 ""안에 찾는 내용이 있을 경우 오류가 날 수 있음.
	private int findIndexContents(StringBuffer fileContents,String str,int index, boolean bQuoter){
		int nIndex=index;
		int nCommentStartIndex=0;
		int nCommentEndIndex=0;
		int nCommentIndex=0;
		nCommentStartIndex= fileContents.indexOf("/*",nCommentStartIndex);
		if(!bQuoter)
			nCommentStartIndex=findIndexContentsQuotation(fileContents,"/*",nCommentStartIndex);
		nCommentEndIndex= fileContents.indexOf("*/",nCommentEndIndex);
		if(!bQuoter)
			nCommentEndIndex=findIndexContentsQuotation(fileContents,"*/",nCommentEndIndex);
		nIndex=fileContents.indexOf(str,nIndex);
		if(nIndex==-1)
			return nIndex;
		else{
			if(str.compareTo("{")!=0&&str.compareTo("}")!=0&&str.compareTo("(")!=0&&str.compareTo(")")!=0&&str.compareTo(";")!=0&&str.compareTo(":")!=0&&str.compareTo(",")!=0)
				nIndex = findIndexContents2(fileContents,str,nIndex);
			if(!bQuoter)
				nIndex = findIndexContentsQuotation(fileContents,str,nIndex);
			if(nIndex==-1)
				return nIndex;
		}
		
		while(nCommentStartIndex!=-1&&nCommentEndIndex!=-1){
			//코멘트 이전에 존재함.
			if(nIndex<nCommentStartIndex){
				return nIndex;
				//코멘트 안에 존재함.
			}else if(nCommentStartIndex<nIndex&&nIndex<nCommentEndIndex){
				nIndex=fileContents.indexOf(str,nIndex+str.length());
				if(nIndex==-1)
					return nIndex;
				else{
					if(str.compareTo("{")!=0&&str.compareTo("}")!=0&&str.compareTo("(")!=0&&str.compareTo(")")!=0&&str.compareTo(";")!=0&&str.compareTo(":")!=0&&str.compareTo(",")!=0)
						nIndex = findIndexContents2(fileContents,str,nIndex);
					if(!bQuoter)
						nIndex = findIndexContentsQuotation(fileContents,str,nIndex);
					if(nIndex==-1)
						return nIndex;
				}
				//코멘트이후에 나옴.
			}else if(nIndex>nCommentEndIndex){
				nCommentIndex = fileContents.indexOf("/*",nCommentStartIndex+2);
				if(!bQuoter)
					nCommentIndex=findIndexContentsQuotation(fileContents,"/*",nCommentIndex);
				//더이상의 코멘트는 없을 경우
				if(nCommentIndex==-1){
					nCommentIndex=fileContents.lastIndexOf("//",nIndex);
					int nCR=fileContents.lastIndexOf("\n",nIndex);
					if(nCommentIndex==-1)
						return nIndex;
					else if(nCommentIndex>nCR){
						nIndex=fileContents.indexOf(str,nIndex+str.length());
						if(nIndex==-1)
							return nIndex;
						else{
							if(str.compareTo("{")!=0&&str.compareTo("}")!=0&&str.compareTo("(")!=0&&str.compareTo(")")!=0&&str.compareTo(";")!=0&&str.compareTo(":")!=0&&str.compareTo(",")!=0)
								nIndex = findIndexContents2(fileContents,str,nIndex);
							if(!bQuoter)
								nIndex = findIndexContentsQuotation(fileContents,str,nIndex);
							if(nIndex==-1)
								return nIndex;
						}
					}else{
						return nIndex;
					}
					//새로운 코멘트 시작
				}else if(nCommentIndex>nCommentEndIndex){
					nCommentStartIndex=nCommentIndex;
					nCommentEndIndex= fileContents.indexOf("*/",nCommentEndIndex+2);
					if(!bQuoter)
						nCommentEndIndex=findIndexContentsQuotation(fileContents,"*/",nCommentEndIndex);
					//코멘트 안의 코맨트 시작
				}else if(nCommentIndex<nCommentEndIndex){
					nCommentStartIndex=fileContents.indexOf("/*",nCommentStartIndex+2);
					if(!bQuoter)
						nCommentStartIndex=findIndexContentsQuotation(fileContents,"/*",nCommentStartIndex);
				}
			}
		}
		return -1;
	}
	
	private int findIndexContents2(StringBuffer fileContents,String str,int nIndex){
		while(nIndex!=-1){
			char ch = fileContents.charAt(nIndex-1);
			if((ch>='0'&&ch<='9')||(ch>='A'&&ch<='Z')||(ch>='a'&&ch<='z')||(ch=='_')||(ch=='~')){
				nIndex=fileContents.indexOf(str,nIndex+str.length());
			}else{
				ch = fileContents.charAt(nIndex+str.length());
				if((ch>='0'&&ch<='9')||(ch>='A'&&ch<='Z')||(ch>='a'&&ch<='z')||(ch=='_')){
					if(fileContents.charAt(nIndex+str.length()-1)=='(')//ComponentName+( 형태를 찾기위해
						return nIndex;
					nIndex=fileContents.indexOf(str,nIndex+str.length());
				}else{
					return nIndex;
				}
			}
		}
		return -1;
	}
	
	private int findIndexContentsQuotation(StringBuffer fileContents,String str,int nIndex){
		int nQuotation=-1;
		while(nIndex!=-1){
			nQuotation=fileContents.indexOf("\"",nQuotation+1);
			if(nQuotation==-1)
				return nIndex;
			if(nQuotation<nIndex){
				nQuotation=fileContents.indexOf("\"",nQuotation+1);
				if(nQuotation>nIndex){
					nIndex=fileContents.indexOf(str,nIndex+str.length()); 
				}
			}else{
				return nIndex;
			}
		}
		return -1;
	}
	
	private int getServiceCnt(StringBuffer fileContents,String strCompName){
		int nCnt=0;
		String strCPP="void "+strCompName+"::portSetup()";
		int nCppIndex = findIndexContents(fileContents,strCPP,0,false);
		int nPort1=fileContents.indexOf("ProvidedServicePort ",nCppIndex);
		while(nPort1!=-1){
			String strPortNo="";
			nPort1=fileContents.indexOf(";",nPort1);
			strPortNo=fileContents.substring(nPort1-1,nPort1);
			int nFindCnt=Integer.valueOf(strPortNo);
			if(nCnt<nFindCnt)
				nCnt=nFindCnt;
			nPort1=fileContents.indexOf("ProvidedServicePort ",nPort1);
		}
		return nCnt;
	}
	
	private int findDelpositionConstructor(StringBuffer cppFileContents,int nDelPosition){
		int nCnt=1;
		char ch = cppFileContents.charAt(nDelPosition-nCnt);
		while(ch!=','&&ch!=':'){
			if(ch==')')
				return -1;
			nCnt++;
			ch = cppFileContents.charAt(nDelPosition-nCnt);
		}
		return nDelPosition-nCnt;
	}
	
	private int findNextConstructor(StringBuffer cppFileContents,int nDelPosition){
		int nIndex=-1;
		int nBraceIndex = findIndexContents(cppFileContents,"{",nDelPosition,false);
		int nParaseIndex = findIndexContents(cppFileContents,"(",nDelPosition,false);
		nParaseIndex = findIndexContents(cppFileContents,"(",nParaseIndex+1,false);
		if(nDelPosition<nParaseIndex&&nParaseIndex<nBraceIndex){
			nIndex = cppFileContents.lastIndexOf(",",nParaseIndex);
		}
		return nIndex;
	}
	
	private String findVarName(StringBuffer cppFileContents,int nPortNameIndex){
		int nComma = findIndexContents(cppFileContents,",",nPortNameIndex,false);
		int nParase = findIndexContents(cppFileContents,")",nComma,false);
		String strRet = cppFileContents.substring(nComma+1, nParase);
		strRet = strRet.trim();
		return strRet;
	}
	
	public Document getServiceTypeDoc(String fileName){
//		Iterator<OPRoSElementBaseModel> it = serviceTypes.getChildrenList().iterator();
//		OPRoSServiceTypeElementModel model;
//		while(it.hasNext()){
//			model = (OPRoSServiceTypeElementModel)it.next();
//			if(fileName.compareTo(model.getServiceTypeFileName())==0){
//				return model.getServiceTypeDoc();
//			}
//		}
//		return null;
		
		
		

		OPRoSServiceTypesElementModel smts1 = (OPRoSServiceTypesElementModel)this.getClassModel().getUMLDataModel().getElementProperty("OPRoSServiceTypesElementModel");
		Iterator it = smts1.getChildren().iterator();
//		File  f = new File(this.getCmpFolder()+File.separator+"profile"+File.separator+this.getName()+".xml");
//		String path = this.getCmpFolder()+File.separator+"profile";
//		OPRoSServiceTypeElementModel model=null;
		while(it.hasNext()){
			Object obj = it.next();
			if(obj instanceof OPRoSServiceTypeElementModel){
				OPRoSServiceTypeElementModel model = (OPRoSServiceTypeElementModel)obj;
				if(fileName.compareTo(model.getServiceTypeFileName())==0){
					//임시
					Document temp = model.getServiceTypeDoc();
			        File  f = new File("C:\\"+"profile"+File.separator+"aaa"+".xml");
			    	try{
			    	this.writeXMLFile(temp, f);
			    	}
			    	catch(Exception e){
			    		e.printStackTrace();
			    	}
			    	
					return model.getServiceTypeDoc();
				}
			}
		}
		return null;
		
	
	}
	
	public Document getDataTypeDoc(String fileName){
//		Iterator<OPRoSElementBaseModel> it = serviceTypes.getChildrenList().iterator();
//		OPRoSServiceTypeElementModel model;
//		while(it.hasNext()){
//			model = (OPRoSServiceTypeElementModel)it.next();
//			if(fileName.compareTo(model.getServiceTypeFileName())==0){
//				return model.getServiceTypeDoc();
//			}
//		}
//		return null;
		
		
		

		OPRoSDataTypesElementModel smts1 = (OPRoSDataTypesElementModel)this.getClassModel().getUMLDataModel().getElementProperty("OPRoSDataTypesElementModel");
		Iterator it = smts1.getChildren().iterator();
//		File  f = new File(this.getCmpFolder()+File.separator+"profile"+File.separator+this.getName()+".xml");
//		String path = this.getCmpFolder()+File.separator+"profile";
//		OPRoSServiceTypeElementModel model=null;
		while(it.hasNext()){
			Object obj = it.next();
			if(obj instanceof OPRoSDataTypeElementModel){
				OPRoSDataTypeElementModel model = (OPRoSDataTypeElementModel)obj;
				if(fileName.compareTo(model.getDataTypeFileName())==0){
					//임시
					Document temp1 = model.getDataTypeDoc();
			        File  f = new File("C:\\"+"profile"+File.separator+"zzz"+".xml");
			    	try{
			    	this.writeXMLFile(temp1, f);
			    	}
			    	catch(Exception e){
			    		e.printStackTrace();
			    	}
			    	
					return model.getDataTypeDoc();
				}
			}
		}
		return null;
		
	
	}
	
	
	//20110707 서동민>>전체  servvice와 data타입 doc를 가져오기 위한 메소드
	public List getServiceTypeDoc(){
//		Iterator<OPRoSElementBaseModel> it = serviceTypes.getChildrenList().iterator();
//		OPRoSServiceTypeElementModel model;
//		while(it.hasNext()){
//			model = (OPRoSServiceTypeElementModel)it.next();
//			if(fileName.compareTo(model.getServiceTypeFileName())==0){
//				return model.getServiceTypeDoc();
//			}
//		}
//		return null;
		
		

		List listServiceDoc = new ArrayList();
		
		OPRoSServiceTypesElementModel smts1 = (OPRoSServiceTypesElementModel)this.getClassModel().getUMLDataModel().getElementProperty("OPRoSServiceTypesElementModel");
		Iterator it = smts1.getChildren().iterator();
//		File  f = new File(this.getCmpFolder()+File.separator+"profile"+File.separator+this.getName()+".xml");
//		String path = this.getCmpFolder()+File.separator+"profile";
//		OPRoSServiceTypeElementModel model=null;
		while(it.hasNext()){
			Object obj = it.next();
			if(obj instanceof OPRoSServiceTypeElementModel){
				OPRoSServiceTypeElementModel model = (OPRoSServiceTypeElementModel)obj;
				listServiceDoc.add(model);

			}
		}
		return listServiceDoc;
		
	
	}
	
	public List getDataTypeDoc(){
//		Iterator<OPRoSElementBaseModel> it = serviceTypes.getChildrenList().iterator();
//		OPRoSServiceTypeElementModel model;
//		while(it.hasNext()){
//			model = (OPRoSServiceTypeElementModel)it.next();
//			if(fileName.compareTo(model.getServiceTypeFileName())==0){
//				return model.getServiceTypeDoc();
//			}
//		}
//		return null;
		
		List listDataDoc = new ArrayList();

		OPRoSDataTypesElementModel smts1 = (OPRoSDataTypesElementModel)this.getClassModel().getUMLDataModel().getElementProperty("OPRoSDataTypesElementModel");
		Iterator it = smts1.getChildren().iterator();
//		File  f = new File(this.getCmpFolder()+File.separator+"profile"+File.separator+this.getName()+".xml");
//		String path = this.getCmpFolder()+File.separator+"profile";
//		OPRoSServiceTypeElementModel model=null;
		while(it.hasNext()){
			Object obj = it.next();
			if(obj instanceof OPRoSDataTypeElementModel){
				OPRoSDataTypeElementModel model = (OPRoSDataTypeElementModel)obj;
				listDataDoc.add(model);
			}
		}
		return listDataDoc;
		
	
	}
	
	
	
	
	public boolean isNeedSourceModify(){
		File  f = new File(this.getCmpFolder()+File.separator+this.getName()+".cpp");
		boolean bRet=false;
		if(f.exists()){
//			boolean bRet=false;
			if(!this.getPortManager().addPorts.isEmpty()){
				bRet=true;
			}
			if(!this.getPortManager().delPorts.isEmpty()){
				bRet=true;
			}
		}
		return bRet;
	}
	
	
	
	


	

	
	
	
	
	
//	public void addOPRoSOPRoSDataTypeElementModel(OPRoSDataTypeElementModel om){
//		java.util.ArrayList list = (java.util.ArrayList)this.uMLDataModel.getElementProperty("getDataTypesModel");
//		if(list==null){
//			java.util.ArrayList list1 = new java.util.ArrayList();
//			list1.add(om);
//			this.uMLDataModel.setElementProperty("getDataTypesModel", list1);
//		}
//		else{
//			list.add(om);
//		}
//		
//		
//	}
	
	//서동민 테스트
	public void setRetModel(ComponentProfile retModel) {
//		this.retModel = retModel;
		this.getUMLDataModel().setElementProperty("retModel", retModel);
	}
}
