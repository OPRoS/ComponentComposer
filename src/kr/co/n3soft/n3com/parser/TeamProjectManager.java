//20080811IJS
package kr.co.n3soft.n3com.parser;

import java.util.HashMap;
import java.util.Iterator;

import kr.co.n3soft.n3com.model.comm.ClassifierModel;
import kr.co.n3soft.n3com.model.comm.ClassifierModelTextAttach;
import kr.co.n3soft.n3com.model.comm.IN3UMLModelDeleteListener;
import kr.co.n3soft.n3com.model.comm.TypeRefModel;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.communication.MessageCommunicationModel;
import kr.co.n3soft.n3com.model.diagram.N3EditorDiagramModel;
import kr.co.n3soft.n3com.model.umlclass.OperationEditableTableItem;
import kr.co.n3soft.n3com.project.browser.UMLTreeModel;
import kr.co.n3soft.n3com.project.browser.UMLTreeParentModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

public class TeamProjectManager {
	private static TeamProjectManager instance;
	private java.util.ArrayList links = new java.util.ArrayList();
	private boolean addLink = false;
	private String addLinkPath = "";
	private UMLTreeParentModel parent = null;
	private boolean isLinkSave = false;
	private boolean isReadOnly = false;
	private boolean isLinkLoad = false;

	private java.util.HashMap noModelMap = new HashMap();
	private java.util.HashMap noTypeModelMap = new HashMap();
	private java.util.HashMap noOperationModelMap = new HashMap();
	private java.util.Vector  noRefLinkMap = new java.util.Vector();
	
	public String ID_TEAM_PROJECT_FOLDER="ID_TEAM_PROJECT_FOLDER";//PKY 08082201 S 팀 프로젝트 경로를 프로젝트에서 변경가능하도록
	
	

	public static TeamProjectManager getInstance() {
		if (instance == null) {
			instance = new TeamProjectManager();

			return instance;
		}
		else {
			return instance;
		}
	}
	public java.util.ArrayList getLinks() {
		return links;
	}
	public void setLinks(java.util.ArrayList links) {
		this.links = links;
	}

	public void addLink(LinkModel lm){
		if(!this.isLink(lm)){
			this.links.add(lm);
			lm.getUt().setLink(true);
		}
	}

	public void addLink(UMLTreeParentModel up,String path){
		LinkModel lm = new LinkModel();
		lm.linkPath = path;
		lm.ut = up;
//		up.setIsLinkType(1);
		this.addLink(lm);

	}

	public boolean isLink(LinkModel lm){
		return  links.contains(lm);

	}

	public void addNoModelMap(UMLModel um){
		//PKY 08082201 S Link ,마지막 추가된 객체만 모델이 변경되는문제
		if(this.noModelMap.get(um.getUMLDataModel().getId())==null){
			java.util.ArrayList arrayList = new java.util.ArrayList(); 
			arrayList.add(um);
			this.noModelMap.put(um.getUMLDataModel().getId(), arrayList);
		}else{
			java.util.ArrayList arrayList = (java.util.ArrayList)this.noModelMap.get(um.getUMLDataModel().getId());
			arrayList.add(um);
			this.noModelMap.put(um.getUMLDataModel().getId(), arrayList);
		}
		//PKY 08082201 E Link ,마지막 추가된 객체만 모델이 변경되는문제

	}

	public void removeNoModelMap(UMLModel um){
		this.noModelMap.remove(um.getUMLDataModel().getId());
	}

	public void initModel(){
		Iterator it = this.noModelMap.keySet().iterator();
		while(it.hasNext()){
			String id = (String)it.next();
			UMLTreeModel ut =  (UMLTreeModel)ProjectManager.getInstance().getModelBrowser().searchId(id);
			if(ut!=null){
				this.setModel((UMLModel)ut.getRefModel());
			}
		}

		it = this.noTypeModelMap.keySet().iterator();
		while(it.hasNext()){
			String id = (String)it.next();
			UMLTreeModel ut =  (UMLTreeModel)ProjectManager.getInstance().getModelBrowser().searchId(id);
			if(ut!=null){
				UMLModel newModel = (UMLModel)this.noTypeModelMap.get(id);
				this.setRef((UMLModel)ut.getRefModel(), newModel);
			}

		}

		it = this.noOperationModelMap.keySet().iterator();
		while(it.hasNext()){
			String id = (String)it.next();
			String[] d = id.split(",");
			UMLTreeModel ut =  (UMLTreeModel)ProjectManager.getInstance().getModelBrowser().searchId(d[0]);
			if(ut!=null && ut.getRefModel() instanceof ClassifierModel){
				ClassifierModel cm = (ClassifierModel)ut.getRefModel();
				//PKY 08082201 S 시퀀스 같은 객체의 오퍼레이션이 두개 이상 일 경우 마지막 추가된 오퍼레이션이 들어가는문제
				java.util.ArrayList msgList = (java.util.ArrayList) this.noOperationModelMap.get(id);
				for(int i = 0 ; i < msgList.size(); i ++){
					
					if(msgList.get(i) instanceof MessageCommunicationModel){
						MessageCommunicationModel mcm = (MessageCommunicationModel)msgList.get(i);
						mcm.setTrm((TypeRefModel)cm.getClassModel());
						OperationEditableTableItem opm = cm.getClassModel().searchOperationEditableTableItem(d[1]);
						if(opm!=null){
							mcm.setOti(opm);
						}
					}
				}
				//PKY 08082201 E시퀀스 같은 객체의 오퍼레이션이 두개 이상 일 경우 마지막 추가된 오퍼레이션이 들어가는문제

			}//PKY 08082201 S 시퀀스 다이어그램 인터페이션 오퍼레이션 맵핑안되는문제
			else if(ut!=null && ut.getRefModel() instanceof ClassifierModelTextAttach){
				ClassifierModelTextAttach cm = (ClassifierModelTextAttach)ut.getRefModel();
				//PKY 08082201 S 시퀀스 같은 객체의 오퍼레이션이 두개 이상 일 경우 마지막 추가된 오퍼레이션이 들어가는문제
				java.util.ArrayList msgList = (java.util.ArrayList) this.noOperationModelMap.get(id);
				for(int i = 0 ; i < msgList.size(); i ++){
					
					if(msgList.get(i) instanceof MessageCommunicationModel){
						MessageCommunicationModel mcm = (MessageCommunicationModel)msgList.get(i);
						mcm.setTrm((TypeRefModel)cm.getClassModel());
						OperationEditableTableItem opm = cm.getClassModel().searchOperationEditableTableItem(d[1]);
						if(opm!=null){
							mcm.setOti(opm);
						}
					}
				}
				//PKY 08082201 E시퀀스 같은 객체의 오퍼레이션이 두개 이상 일 경우 마지막 추가된 오퍼레이션이 들어가는문제

			}

		}

		if(this.getNoRefLinkMap().size()>0){
			for(int i=0;i<this.getNoRefLinkMap().size();i++){
				RefLinkModel rf = (RefLinkModel)this.getNoRefLinkMap().get(i);
				rf.link();
			}
		}
	}
	//PKY 08082201 S Link ,마지막 추가된 객체만 모델이 변경되는문제
	public void setModel(UMLModel um){
		UMLModel nUm = null;
		if(this.noModelMap.get(um.getUMLDataModel().getId())!=null){
			java.util.ArrayList arrayItem = (java.util.ArrayList)this.noModelMap.get(um.getUMLDataModel().getId());
			for(int i = 0; i < arrayItem.size(); i++){
				nUm = (UMLModel)arrayItem.get(i);

				if(nUm!=null){
					nUm.setTreeModel(um.getUMLTreeModel());
					nUm.getUMLTreeModel().addN3UMLModelDeleteListener((IN3UMLModelDeleteListener)nUm.getAcceptParentModel());
//					nUm.setUMLDataModel(um.getUMLDataModel());
					nUm.setExistModel(true);

					if(nUm instanceof ClassifierModel){
						ClassifierModel cm = (ClassifierModel)nUm;
						cm.setModel(um.getUMLDataModel());
					}//PKY 08082201 S 인터페이스 맵핑안되는문제
					else if(nUm instanceof ClassifierModelTextAttach){
						ClassifierModelTextAttach cm = (ClassifierModelTextAttach)nUm;
						cm.setModel(um.getUMLDataModel());
					}
					//PKY 08082201 E 인터페이스 맵핑안되는문제
					if(nUm.getAcceptParentModel() instanceof N3EditorDiagramModel){
						N3EditorDiagramModel nd = (N3EditorDiagramModel)nUm.getAcceptParentModel();
						if(nd.getDiagramType()==12){

//							nUm= new LifeLineModel();
							nUm.setTreeModel(um.getUMLTreeModel());
							nUm.getUMLTreeModel().addN3UMLModelDeleteListener((IN3UMLModelDeleteListener)nUm.getAcceptParentModel());
//							nUm.setUMLDataModel(um.getUMLDataModel());
							nUm.setExistModel(true);
//							newModel.getUMLDataModel().setId(p)

							nUm.getUMLDataModel().setId(um.uMLDataModel.getId());
//							this.setRef(um, nUm);
//							nUm.setName("object");
							return;

						}
					}

					nUm.setName(um.getName());
				}
			}

		}
	}
	//PKY 08082201 E Link ,마지막 추가된 객체만 모델이 변경되는문제
	public void setRef(UMLModel source,UMLModel target){
		
	}

	public boolean isAddLink() {
		return addLink;
	}
	public void setAddLink(boolean addLink) {
		this.addLink = addLink;
	}
	public String getAddLinkPath() {
		return addLinkPath;
	}
	public void setAddLinkPath(String addLinkPath) {
		this.addLinkPath = addLinkPath;
	}
	public UMLTreeParentModel getParent() {
		return parent;
	}
	public void setParent(UMLTreeParentModel parent) {
		this.parent = parent;
	}

	public void setModel(){

	}

	public void init(){
		noModelMap.clear();
		links.clear();
		addLink = false;
		addLinkPath = "";
		parent = null;
		this.noModelMap.clear();
		this.isReadOnly = false;
		this.noOperationModelMap.clear();
		this.noRefLinkMap.clear();
		isLinkLoad = false;
		
	}
	public boolean isLinkSave() {
		return isLinkSave;
	}
	public void setLinkSave(boolean isLinkSave) {
		this.isLinkSave = isLinkSave;
	}
	public boolean isReadOnly() {
		return isReadOnly;
	}
	public void setReadOnly(boolean isReadOnly) {
		this.isReadOnly = isReadOnly;
	}
	public java.util.HashMap getNoTypeModelMap() {
		return noTypeModelMap;
	}
	public void setNoTypeModelMap(java.util.HashMap noTypeModelMap) {
		this.noTypeModelMap = noTypeModelMap;
	}
	public java.util.HashMap getNoOperationModelMap() {
		return noOperationModelMap;
	}
	public void setNoOperationModelMap(java.util.HashMap noOperationModelMap) {
		this.noOperationModelMap = noOperationModelMap;
	}
	public java.util.Vector getNoRefLinkMap() {
		return noRefLinkMap;
	}
	public void setNoRefLinkMap(java.util.Vector noRefLinkMap) {
		this.noRefLinkMap = noRefLinkMap;
	}
	public boolean isLinkLoad() {
		return isLinkLoad;
	}
	public void setLinkLoad(boolean isLinkLoad) {
		this.isLinkLoad = isLinkLoad;
	}


}
