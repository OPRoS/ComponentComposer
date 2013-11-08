package kr.co.n3soft.n3com.model.comm;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import kr.co.n3soft.n3com.model.component.OPRoSDataTypesElementModel;
import kr.co.n3soft.n3com.model.component.OPRoSExeEnvironmentElementModel;
import kr.co.n3soft.n3com.model.component.OPRoSMonitorVariableElementModel;
import kr.co.n3soft.n3com.model.component.OPRoSMonitorVariablesElementModel;
import kr.co.n3soft.n3com.model.component.OPRoSPropertiesElementModel;
import kr.co.n3soft.n3com.model.component.OPRoSServiceTypesElementModel;
import kr.co.n3soft.n3com.model.diagram.N3EditorDiagramModel;
import kr.co.n3soft.n3com.model.umlclass.ArrayItem;
import kr.co.n3soft.n3com.model.umlclass.CompartmentModel;
import kr.co.n3soft.n3com.parser.IN3ModelTokenType;
import kr.co.n3soft.n3com.parser.ModelManager;
import kr.co.n3soft.n3com.project.browser.RootTreeModel;
import kr.co.n3soft.n3com.project.browser.UMLTreeModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

public class UMLDataModel {
	//	public static String ID_NAME = "name"; //$NON-NLS-1$
	protected String name = "";
	private String id = "";
	private String cmpid = "";
	public String getCmpid() {
		return cmpid;
	}

	public void setCmpid(String cmpid) {
		this.cmpid = cmpid;
	}

	private HashMap property = new HashMap();
	N3EditorDiagramModel n3EditorDiagramModel = null;
//	java.util.ArrayList ports = new java.util.ArrayList();
//	PortManager pm = new PortManager();

	public UMLDataModel() {
		this.setId(ProjectManager.getInstance().getID(this));
		cmpid = ProjectManager.getInstance().getID2(this);
		
		//		return name;
	}

	public void setId(String p) {
		this.id = p;
	}

	public String getId() {
		return this.id;
	}

	public String getName() {
		return name;
	}

	public void setName(String p) {
		this.name = p;
	}

//  V1.02 WJH E 080522 S 함수 추가  
    public String getFullName(UMLTreeModel ptm, String name){
    	String fullname = "";    	
    	if(ptm.getParent()!= null){
    		if(ptm.getParent() instanceof RootTreeModel){
    			return name;
    		}
    		else {
    			if(name.equals(""))
    				fullname = ptm.getParent().getName()+"."+ptm.getName();
    			else
    				fullname = ptm.getParent().getName()+"."+name;
    			
    			fullname = this.getFullName((UMLTreeModel)ptm.getParent(), fullname);
    		}
    	}
    	else 
    		return this.name;
    	return fullname; 
    }
//  V1.02 WJH E 080522 E 함수 추가
    
	public void setProperty(String key, String value) {
		//		Object obj = property.get(key);
		property.put(key, value);
	}
	//PKY 08052101 S 컨테이너에서 그룹으로 변경
	public void removeProperty(String key) {
		//		Object obj = property.get(key);
		property.remove(key);
	}
	//PKY 08052101 E 컨테이너에서 그룹으로 변경
	public String getProperty(String key) {
		return (String)property.get(key);
	}

	public void setElementProperty(String key, Object value) {
		//임시삭
		//		if(property.get(key)==null)
		property.put(key, value);
	}

	public Object getElementProperty(String key) {
		return (Object)property.get(key);
	}

	public void setProperty(HashMap cloneProperty) {
		this.property = cloneProperty;
	}

	public String writeModel(){
		StringBuffer sb = new StringBuffer();
		Iterator iterator = property.keySet().iterator();
		while (iterator.hasNext()) {
			String key = (String)iterator.next();
			Object obj = property.get(key);

			if (obj instanceof String ) {
				String data = obj.toString();
				if(key.trim().equals("ID")){
					continue;
				}
				sb.append("property "+key+"="+obj.toString()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			}

		}
		iterator = property.keySet().iterator();
		sb.append("propertys_list [\n") ;
		while (iterator.hasNext()) {
			String key = (String)iterator.next();
			Object obj = property.get(key);
			
			if (obj instanceof java.util.ArrayList) {
				sb.append("objects "+key+"+\n") ;
				ArrayList arr = (ArrayList)obj;
				for(int i=0;i<arr.size();i++){
					if(arr.get(i) instanceof PortModel){
						PortModel pm = (PortModel)arr.get(i);
						//PKY 08061101 S 포트와 관련된 저장 불러오기 로직 보완작업
						sb.append("property port="+pm.getLocation().x+","+pm.getLocation().y+","+pm.getSize().width+","+pm.getSize().height+","+pm.getPtDifference().width+","+pm.getPtDifference().height+","+pm.view_ID+","+pm.getName()+","+pm.getScope()+","+pm.getStereotype()+","+pm.getAttachElementLabelModel().getSize().width+","+pm.getAttachElementLabelModel().getSize().height+","+pm.getBackGroundColor().getRed()+","+pm.getBackGroundColor().getGreen()+","+pm.getBackGroundColor().getBlue()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
						//PKY 08061101 E 포트와 관련된 저장 불러오기 로직 보완작업

					}
					
					//2008043001 PKY E Timing 객체 저장 로직 추가
					//PKY 08052101 S 컨테이너에서 그룹으로 변경
					else if(key.equals("GROUP_CHILDRENS")&&arr.get(i) instanceof UMLModel){
						UMLModel pm = (UMLModel)arr.get(i);
						if(pm.getUMLDataModel().getElementProperty("GROUP_PARENTS")!=null){
							if(pm.getUMLDataModel().getElementProperty("GROUP_PARENTS") instanceof java.util.ArrayList){
								java.util.ArrayList arryItem =( java.util.ArrayList)pm.getUMLDataModel().getElementProperty("GROUP_PARENTS");
								for(int j=0; j<arryItem.size();j++){
									if(arryItem.get(j)!=null) //PKY 08061801 S 2회 저장할경우 파티션이 떨어져나가는문제
									if(arryItem.get(j) instanceof UMLModel){
										sb.append("property groupModel="+pm.getView_ID()+","+((UMLModel)arryItem.get(j)).getView_ID()+IN3ModelTokenType.PROPERTY_N3EOF+"\n");
									}
								}
							}
						}
					}
					else if(key.equals("GROUP_PARENTS")&&arr.get(i) instanceof UMLModel){
						UMLModel pm = (UMLModel)arr.get(i);
						if(pm.getUMLDataModel().getElementProperty("GROUP_CHILDRENS")!=null){
							if(pm.getUMLDataModel().getElementProperty("GROUP_CHILDRENS") instanceof java.util.ArrayList){
								java.util.ArrayList arryItem =( java.util.ArrayList)pm.getUMLDataModel().getElementProperty("GROUP_CHILDRENS");
								for(int j=0; j<arryItem.size();j++){
									if(arryItem.get(j)!=null) //PKY 08061801 S 2회 저장할경우 파티션이 떨어져나가는문제
									if(arryItem.get(j) instanceof UMLModel){
										sb.append("property groupParent="+((UMLModel)arryItem.get(j)).getView_ID()+","+pm.getView_ID()+IN3ModelTokenType.PROPERTY_N3EOF+"\n");//PKY 08052601 S Partition 저장 불러올시 해제되는문제 

									}
								}

							}
						}
					}
					//PKY 08052101 E 컨테이너에서 그룹으로 변경
					else {
						if(arr.get(i) instanceof ArrayItem){
							ArrayItem arrayItem = (ArrayItem)arr.get(i);
							sb.append(arrayItem.writeModel());
						}
					}
				}
				sb.append("-\n") ;
			}
//			else if(obj instanceof OPRoSExeEnvironmentElementModel){
//				sb.append("objects "+key+"+\n") ;
//				OPRoSExeEnvironmentElementModel pem =  (OPRoSExeEnvironmentElementModel)obj;
//				pem.writeModel();
//				sb.append("-\n"); 
//				
//			}
//			else if(obj instanceof OPRoSPropertiesElementModel){
//				sb.append("objects "+key+"+\n") ;
//				OPRoSPropertiesElementModel pem =  (OPRoSPropertiesElementModel)obj;
//				pem.writeModel();
//				sb.append("-\n"); 
//				
//			}
//			else if(obj instanceof OPRoSServiceTypesElementModel){
//				sb.append("objects "+key+"+\n") ;
//				OPRoSServiceTypeElementModel pem =  (OPRoSServiceTypeElementModel)obj;
//				pem.writeModel();
//				sb.append("-\n"); 
//				
//			}
//			else if(obj instanceof OPRoSDataTypesElementModel){
//				sb.append("objects "+key+"+\n") ;
//				OPRoSDataTypesElementModel pem =  (OPRoSDataTypesElementModel)obj;
//				pem.writeModel();
//				sb.append("-\n"); 
//				
//			}
			
			else if (obj instanceof TypeRefModel) {
				sb.append("objects TypeRefModel +\n") ;
				ClassModel arr = (ClassModel)obj;
//				arr.getAcceptParentModel();
				sb.append(ModelManager.getInstance().writeModel(arr,id));
				sb.append("-\n") ;
				Iterator iterator1= arr.getUMLDataModel().property.keySet().iterator();
				while (iterator1.hasNext()) {
					String key1 = (String)iterator1.next();
					Object obj1 = arr.getUMLDataModel().property.get(key1);
				if(obj1 instanceof OPRoSExeEnvironmentElementModel){
					sb.append("objects "+key1+"+\n") ;
					OPRoSExeEnvironmentElementModel pem =  (OPRoSExeEnvironmentElementModel)obj1;
					sb.append(pem.writeModel());
					sb.append("-\n"); 
					
				}
				else if(obj1 instanceof OPRoSPropertiesElementModel){
					sb.append("objects "+key1+"+\n") ;
					OPRoSPropertiesElementModel pem =  (OPRoSPropertiesElementModel)obj1;
					sb.append(pem.writeModel());
					sb.append("-\n"); 
					
				}
				else if(obj1 instanceof OPRoSMonitorVariablesElementModel){
					sb.append("objects "+key1+"+\n") ;
					OPRoSMonitorVariablesElementModel pem =  (OPRoSMonitorVariablesElementModel)obj1;
					sb.append(pem.writeModel());
					sb.append("-\n"); 
					
				}
				else if(obj1 instanceof OPRoSServiceTypesElementModel){
					sb.append("objects "+key1+"+\n") ;
					OPRoSServiceTypesElementModel pem =  (OPRoSServiceTypesElementModel)obj1;
					sb.append(pem.writeModel());
					sb.append("-\n"); 
					
				}
				else if(obj1 instanceof OPRoSDataTypesElementModel){
					sb.append("objects "+key1+"+\n") ;
					OPRoSDataTypesElementModel pem =  (OPRoSDataTypesElementModel)obj1;
					sb.append(pem.writeModel());
					sb.append("-\n"); 
					
				}
				}
			}
			else if (obj instanceof ClassModel) {
				sb.append("objects ClassModel +\n") ;
				ClassModel arr = (ClassModel)obj;
				sb.append(ModelManager.getInstance().writeModel(arr,id));
				sb.append("-\n") ;
			}
			

		}
		sb.append("]\n") ;

		return sb.toString();
	}
	//PKY 08072201 S 일부 모델 모델 브라우져에 생성되지 않도록 Initial,Final,FlowFinal,Juction,Exit,EntryPoint 
	public String writeViewModel(){
		StringBuffer sb = new StringBuffer();
		Iterator iterator = property.keySet().iterator();
		while (iterator.hasNext()) {
			String key = (String)iterator.next();
			Object obj = property.get(key);

			if (obj instanceof String ) {
				String data = obj.toString();
				if(data.trim().equals("ID")){
					continue;
				}
				sb.append("property "+key+"="+obj.toString()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
			}

		}
		iterator = property.keySet().iterator();
		sb.append("propertys_list [\n") ;
		while (iterator.hasNext()) {
			String key = (String)iterator.next();
			Object obj = property.get(key);
			if (obj instanceof java.util.ArrayList) {
				sb.append("objects "+key+"+\n") ;
				ArrayList arr = (ArrayList)obj;
				for(int i=0;i<arr.size();i++){
					if(arr.get(i) instanceof PortModel){
						PortModel pm = (PortModel)arr.get(i);
						//PKY 08061101 S 포트와 관련된 저장 불러오기 로직 보완작업
						sb.append("property port="+pm.getLocation().x+","+pm.getLocation().y+","+pm.getSize().width+","+pm.getSize().height+","+pm.getPtDifference().width+","+pm.getPtDifference().height+","+pm.view_ID+","+pm.getName()+","+pm.getScope()+","+pm.getStereotype()+","+pm.getAttachElementLabelModel().getSize().width+","+pm.getAttachElementLabelModel().getSize().height+","+pm.getBackGroundColor().getRed()+","+pm.getBackGroundColor().getGreen()+","+pm.getBackGroundColor().getBlue()+","+pm.getDesc()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
						//PKY 08061101 E 포트와 관련된 저장 불러오기 로직 보완작업

					}
					
					//2008043001 PKY E Timing 객체 저장 로직 추가
					//PKY 08052101 S 컨테이너에서 그룹으로 변경
					else if(key.equals("GROUP_CHILDRENS")&&arr.get(i) instanceof UMLModel){
						UMLModel pm = (UMLModel)arr.get(i);
						if(pm.getUMLDataModel().getElementProperty("GROUP_PARENTS")!=null){
							if(pm.getUMLDataModel().getElementProperty("GROUP_PARENTS") instanceof java.util.ArrayList){
								java.util.ArrayList arryItem =( java.util.ArrayList)pm.getUMLDataModel().getElementProperty("GROUP_PARENTS");
								for(int j=0; j<arryItem.size();j++){
									if(arryItem.get(j)!=null) //PKY 08061801 S 2회 저장할경우 파티션이 떨어져나가는문제
									if(arryItem.get(j) instanceof UMLModel){
										sb.append("property groupModel="+pm.getView_ID()+","+((UMLModel)arryItem.get(j)).getView_ID()+IN3ModelTokenType.PROPERTY_N3EOF+"\n");
									}
								}
							}
						}
					}
					else if(key.equals("GROUP_PARENTS")&&arr.get(i) instanceof UMLModel){
						UMLModel pm = (UMLModel)arr.get(i);
						if(pm.getUMLDataModel().getElementProperty("GROUP_CHILDRENS")!=null){
							if(pm.getUMLDataModel().getElementProperty("GROUP_CHILDRENS") instanceof java.util.ArrayList){
								java.util.ArrayList arryItem =( java.util.ArrayList)pm.getUMLDataModel().getElementProperty("GROUP_CHILDRENS");
								for(int j=0; j<arryItem.size();j++){
									if(arryItem.get(j)!=null) //PKY 08061801 S 2회 저장할경우 파티션이 떨어져나가는문제
									if(arryItem.get(j) instanceof UMLModel){
										sb.append("property groupParent="+((UMLModel)arryItem.get(j)).getView_ID()+","+pm.getView_ID()+IN3ModelTokenType.PROPERTY_N3EOF+"\n");//PKY 08052601 S Partition 저장 불러올시 해제되는문제 

									}
								}

							}
						}
					}
					//PKY 08052101 E 컨테이너에서 그룹으로 변경
					else {

						ArrayItem arrayItem = (ArrayItem)arr.get(i);
						sb.append(arrayItem.writeModel());
					}
				}
				sb.append("-\n") ;
			}

		}
		sb.append("]\n") ;

		return sb.toString();
	}
	//PKY 08072201 E 일부 모델 모델 브라우져에 생성되지 않도록 Initial,Final,FlowFinal,Juction,Exit,EntryPoint 

	public Object clone() {
		UMLDataModel clone = new UMLDataModel();
		try {
			HashMap cloneProperty = new HashMap();
			Iterator iterator = property.keySet().iterator();
			while (iterator.hasNext()) {
				String key = (String)iterator.next();
				Object obj = property.get(key);
				if (obj != null) {
					if (obj instanceof ElementLabelModel) {
						ElementLabelModel elementModel = (ElementLabelModel)obj;
						ElementLabelModel elementClone = (ElementLabelModel)elementModel.clone();
						cloneProperty.put(key, elementClone);
					}
					else if (obj instanceof java.util.ArrayList) {
						java.util.ArrayList elementModel = (java.util.ArrayList) obj;
						java.util.ArrayList cloneModel = this.cloneArray(elementModel);
						cloneProperty.put(key, cloneModel);
					}
					else if (obj instanceof CompartmentModel) {
						CompartmentModel elementModel = new CompartmentModel();
						cloneProperty.put(key, elementModel);
					}
					if (obj instanceof PortModel) {
						PortModel elementModel = (PortModel)obj;
						PortModel elementClone = (PortModel)elementModel.clone();
						cloneProperty.put(key, elementClone);
					}
					if (obj instanceof BorderContainlModel) {
						BorderContainlModel elementModel = (BorderContainlModel)obj;
						BorderContainlModel elementClone = (BorderContainlModel)elementModel.clone();
						cloneProperty.put(key, elementClone);
					}
					if (obj instanceof TypeRefModel) {
						TypeRefModel elementModel = (TypeRefModel)obj;
						//				BorderContainlModel elementClone = (BorderContainlModel)elementModel.clone();
						TypeRefModel classModelClone = (TypeRefModel)elementModel.clone();
						cloneProperty.put(key, classModelClone);
					}
					if (obj instanceof UMLModel) {
						UMLModel elementModel = (UMLModel)obj;
						//				BorderContainlModel elementClone = (BorderContainlModel)elementModel.clone();
						UMLModel classModelClone = (UMLModel)elementModel.clone();
						cloneProperty.put(key, classModelClone);
					}
					if (obj instanceof String) {
						//				UMLModel elementModel = (UMLModel)obj;
						////				BorderContainlModel elementClone = (BorderContainlModel)elementModel.clone();
						//				ClassModel classModelClone = (ClassModel)elementModel.clone();
						cloneProperty.put(key, obj);
					}
					if (obj instanceof File) {
						//				UMLModel elementModel = (UMLModel)obj;
						////				BorderContainlModel elementClone = (BorderContainlModel)elementModel.clone();
						//				ClassModel classModelClone = (ClassModel)elementModel.clone();
						cloneProperty.put(key, obj);
					}
					//			if(obj instanceof UMLModel){
					//				BorderContainlModel elementModel = (BorderContainlModel)obj;
					//				BorderContainlModel elementClone = (BorderContainlModel)elementModel.clone();
					//				cloneProperty.put(key, elementClone);
					//			}
				}
			}
			clone.setProperty(cloneProperty);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return clone;
	}

	public void setN3EditorDiagramModel(N3EditorDiagramModel p) {
		this.n3EditorDiagramModel = p;
		//		 uMLMainPackageModel
	}

	public N3EditorDiagramModel getN3EditorDiagramModel() {
		return this.n3EditorDiagramModel;
	}

	public java.util.ArrayList cloneArray(java.util.ArrayList array) {
		java.util.ArrayList clone = new java.util.ArrayList();
		for (int i = 0; i < array.size(); i++) {
			Object obj = array.get(i);
			if (obj instanceof ArrayItem) {
				ArrayItem arrayItem = (ArrayItem)obj;
				clone.add(arrayItem.clone());
			}
			else if (obj instanceof PortModel) {
				PortModel arrayItem = (PortModel)obj;
				clone.add(arrayItem.clone());
			}
		}
		return clone;
	}
	//	public java.util.ArrayList cloneOperations(){
		//		java.util.ArrayList operationsClone = new java.util.ArrayList();
	//		for(int i=0;i<this.operations.size();i++){
	//			OperationEditableTableItem attrItem = (OperationEditableTableItem)this.operations.get(i);
	//			operationsClone.add(attrItem.clone());
	//		}
	//		this.setOperations(operationsClone);
	//		return operations;
	//		
	//	}

//	public PortManager getPm() {
//		return pm;
//	}
//
//	public void setPm(PortManager pm) {
//		this.pm = pm;
//	}
}
