package kr.co.n3soft.n3com.projectmanager;

import kr.co.n3soft.n3com.model.comm.DataInputPortModel;
import kr.co.n3soft.n3com.model.comm.DataOutputPortModel;
import kr.co.n3soft.n3com.model.comm.EventInputPortModel;
import kr.co.n3soft.n3com.model.comm.EventOutputPortModel;
import kr.co.n3soft.n3com.model.comm.MethodInputPortModel;
import kr.co.n3soft.n3com.model.comm.MethodOutputPortModel;
import kr.co.n3soft.n3com.model.comm.PortModel;
import kr.co.n3soft.n3com.model.comm.UMLContainerModel;
import kr.co.n3soft.n3com.model.component.ComponentModel;
import kr.co.n3soft.n3com.project.browser.UMLTreeModel;

public class PortProfile {
	public String name = "";
	public String type = "";
	public String usage = "";
	public String reference = "";
	
	public String desc = "";
	public String queueing_policy = "";
	public String queue_size = "";
	/*
	 * 0  method_port
	 * 1  event_port
	 * 2  data_port
	 * 
	 */
	public int portType = 0;
	
	public void getPortModel(ComponentModel clm){
		if(this.portType==0){
			
			if("required".equals(this.usage)){
				//in
				MethodInputPortModel mip = new MethodInputPortModel(clm);
				mip.getAttachElementLabelModel().setText(this.name);
				mip.setType(this.type);
				clm.getPorts().add(mip);
				
				UMLTreeModel port2 = new UMLTreeModel(this.name);
				port2.setRefModel(mip);

				mip.getElementLabelModel().setTreeModel(port2);
				mip.getElementLabelModel().setPortId(mip.getUMLDataModel().getId());
//				to1.addChild(port2);
				mip.getElementLabelModel().setReadOnly(true);
				clm.createPort( mip, (UMLContainerModel)clm.getAcceptParentModel());


			}
			else{
				//out
				MethodOutputPortModel mip = new MethodOutputPortModel(clm);
				mip.getAttachElementLabelModel().setText(this.name);
				mip.setType(this.type);
				clm.getPorts().add(mip);
				UMLTreeModel port2 = new UMLTreeModel(this.name);
//				to1.addChild(port2);
				port2.setRefModel(mip);
				mip.getElementLabelModel().setTreeModel(port2);
				mip.getElementLabelModel().setPortId(mip.getUMLDataModel().getId());
				mip.getElementLabelModel().setReadOnly(true);
				clm.createPort( mip, (UMLContainerModel)clm.getAcceptParentModel());

			}
		}
		else if(this.portType==1){
			if("input".equals(this.usage)){
				//in
				EventInputPortModel mip = new EventInputPortModel(clm);
				mip.getAttachElementLabelModel().setText(this.name);
				mip.setType(this.type);
				clm.getPorts().add(mip);
				UMLTreeModel port2 = new UMLTreeModel(this.name);
//				to1.addChild(port2);
				port2.setRefModel(mip);
				mip.getElementLabelModel().setTreeModel(port2);
				mip.getElementLabelModel().setPortId(mip.getUMLDataModel().getId());
				mip.getElementLabelModel().setReadOnly(true);
				clm.createPort( mip, (UMLContainerModel)clm.getAcceptParentModel());


			}
			else{
				//out
				EventOutputPortModel mip = new EventOutputPortModel(clm);
				mip.getAttachElementLabelModel().setText(this.name);
				mip.setType(this.type);
				clm.getPorts().add(mip);
				UMLTreeModel port2 = new UMLTreeModel(this.name);
//				to1.addChild(port2);
				port2.setRefModel(mip);
				mip.getElementLabelModel().setTreeModel(port2);
				mip.getElementLabelModel().setPortId(mip.getUMLDataModel().getId());
				mip.getElementLabelModel().setReadOnly(true);
				clm.createPort( mip, (UMLContainerModel)clm.getAcceptParentModel());

			}
		}
		else if(this.portType==2){
			if("input".equals(this.usage)){
				//in
				DataInputPortModel mip = new DataInputPortModel(clm);
				mip.getAttachElementLabelModel().setText(this.name);
				mip.setType(this.type);
				clm.getPorts().add(mip);
				UMLTreeModel port2 = new UMLTreeModel(this.name);
//				to1.addChild(port2);
				port2.setRefModel(mip);
				mip.getElementLabelModel().setTreeModel(port2);
				mip.getElementLabelModel().setPortId(mip.getUMLDataModel().getId());
				mip.getElementLabelModel().setReadOnly(true);
				clm.createPort( mip, (UMLContainerModel)clm.getAcceptParentModel());


			}
			else{
				//out
				DataOutputPortModel mip = new DataOutputPortModel(clm);
				mip.getAttachElementLabelModel().setText(this.name);
				mip.setType(this.type);
				clm.getPorts().add(mip);
				UMLTreeModel port2 = new UMLTreeModel(this.name);
//				to1.addChild(port2);
				port2.setRefModel(mip);
				mip.getElementLabelModel().setTreeModel(port2);
				mip.getElementLabelModel().setPortId(mip.getUMLDataModel().getId());
				mip.getElementLabelModel().setReadOnly(true);
				clm.createPort( mip, (UMLContainerModel)clm.getAcceptParentModel());

			}
			
		} 
		
	}
	
	public String getName(){
		return name;
	}
	
	public String getType(){
		return type;
	}
	
	public int getPortType(){
		return portType;
	}
	
	public String getUsage(){
		return usage;
	}
	
	//20110727서동민 추가될 포트에 데이터 입력
	public PortModel setPortModelData(PortModel pm){
		if(this.portType==0){//Service
			if("required".equals(this.usage)){//in
				if(pm instanceof MethodInputPortModel){
					pm.setType(type);
					pm.setName(name);
					pm.setDesc(desc);
					pm.setTypeRef(reference);
				}
			}
			else{//out
				if(pm instanceof MethodOutputPortModel){
					pm.setType(type);
					pm.setName(name);
					pm.setDesc(desc);
					pm.setTypeRef(reference);
				}
			}
		}
		else if(this.portType==1){//Event
			if("input".equals(this.usage)){//in
				if(pm instanceof EventInputPortModel){
					pm.setType(type);
					pm.setName(name);
					pm.setDesc(desc);
				}
			}
			else{//out
				if(pm instanceof EventOutputPortModel){
					pm.setType(type);
					pm.setName(name);
					pm.setDesc(desc);
				}
			}
		}
		else if(this.portType==2){//Data
			if("input".equals(this.usage)){//in
				if(pm instanceof DataInputPortModel){
					pm.setType(type);
					pm.setName(name);
					pm.setDesc(desc);
					pm.setTypeRef(reference);
					pm.setQueueingPolicy(queueing_policy);	//110823 SDM
					pm.setQueueSize(queue_size);	//110823 SDM
				}
			}
			else{//out
				if(pm instanceof DataOutputPortModel){
					pm.setType(type);
					pm.setName(name);
					pm.setDesc(desc);
					pm.setTypeRef(reference);
				}
			}
		}
		return pm;
	}
	
	
//	public String target_port_name = "";
}
