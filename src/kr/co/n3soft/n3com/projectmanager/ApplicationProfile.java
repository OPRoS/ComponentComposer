package kr.co.n3soft.n3com.projectmanager;

import java.io.File;

import kr.co.n3soft.n3com.model.comm.DataInputPortModel;
import kr.co.n3soft.n3com.model.comm.DataOutputPortModel;
import kr.co.n3soft.n3com.model.comm.EventInputPortModel;
import kr.co.n3soft.n3com.model.comm.EventOutputPortModel;
import kr.co.n3soft.n3com.model.comm.LineModel;
import kr.co.n3soft.n3com.model.comm.MethodInputPortModel;
import kr.co.n3soft.n3com.model.comm.MethodOutputPortModel;
import kr.co.n3soft.n3com.model.comm.PortModel;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.component.AtomicComponentModel;
import kr.co.n3soft.n3com.model.component.ComponentLibModel;
import kr.co.n3soft.n3com.model.component.ComponentModel;
import kr.co.n3soft.n3com.model.diagram.N3EditorDiagramModel;
import kr.co.n3soft.n3com.model.umlclass.DependencyLineModel;
import kr.co.n3soft.n3com.project.browser.UMLTreeModel;
import kr.co.n3soft.n3com.project.browser.UMLTreeParentModel;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;

public class ApplicationProfile extends CompositeComponentProfile{
	public UMLTreeParentModel parent = null;
	
	
	
	public void makeTemplatView(){

        this.x = 100;
        this.y = 100;

		for(int i=0;i<this.subcomponents.size();i++){
			Subcomponent subView = (Subcomponent)subcomponents.get(i);
			this.makeComponentLibSubModelTemplateView(subView,i,"");
		}

		for(int i=0;i<portConnections.size();i++){
			PortConnection pview = (PortConnection)portConnections.get(i);
			this.createLineModel(pview,"");
		}
		 this.x = 100;
	        this.y = 100;



	}
	
	public void makeView(){

		 this.x = 100;
	        this.y = 100;

		for(int i=0;i<this.subcomponents.size();i++){
			Subcomponent subView = (Subcomponent)subcomponents.get(i);
			this.makeComponentLibSubModelView2(subView,i,"");
		}

		for(int i=0;i<portConnections.size();i++){
			PortConnection pview = (PortConnection)portConnections.get(i);
			this.createLineModel(pview,"");
		}
		 this.x = 100;
	        this.y = 100;



	}

	public void createLineModel(PortConnection pview,String path){
		LineModel lm = null;
		N3EditorDiagramModel ndm = null;
		lm=new DependencyLineModel();
		try{
			if("".equals(pview.t_source_component_name)){
				pview.t_source_component_name = pview.source_component_name;
			}
			if("".equals(pview.t_target_component_name)){
				pview.t_target_component_name = pview.target_component_name;
			}

			String fPaths = path+File.separator+pview.source_component_name+".xml";
			String fPatht = path+File.separator+pview.target_component_name+".xml";

			ComponentModel  umSourceC = null;
			ComponentModel  umTargetC = null;
			for(int i=0;i<nd.getChildren().size();i++){
				Object obj = nd.getChildren().get(i);
				if(obj instanceof ComponentModel){
					ComponentModel cm = (ComponentModel)obj;
					if(cm.getName().equals(pview.t_source_component_name)){
						umSourceC = cm;
						continue;
					}
					if(cm.getName().equals(pview.t_target_component_name)){
						umTargetC = cm;
						continue;
					}
					if(umSourceC!=null && umTargetC!=null)
						break;
				}
			}


			boolean isSourceExist = false;
			boolean isTargetExist = false;
			if(umSourceC!=null){
			for(int i=0;i<umSourceC.getPortManager().getPorts().size();i++){
				PortModel pm = (PortModel)umSourceC.getPortManager().getPorts().get(i);
				if(pm!=null){
					if(pm instanceof MonitoringMethodPortModel){
						if(pview.source_port_name.equals("RM_MonitoringMethodPort")){
							lm.setSourceTerminal(pview.source_port_name);
							lm.setSource(pm);
							isSourceExist = true;
						}
					}
					else if(pm instanceof LifecycleMethodPortModel){
						if(pview.source_port_name.equals("RM_LifecycleMethodPort")){
							lm.setSourceTerminal(pview.source_port_name);
							lm.setSource(pm);
							isSourceExist = true;
						}
					}
					else if(pm.getName().equals(pview.source_port_name)){
						lm.setSourceTerminal(pview.source_port_name);
						lm.setSource(pm);
						isSourceExist = true;
					}
				}

			}
			}
			if(umTargetC!=null){
			for(int i=0;i<umTargetC.getPortManager().getPorts().size();i++){
				PortModel pm = (PortModel)umTargetC.getPortManager().getPorts().get(i);
				System.out.println("pm name==>"+pm.getName());
				System.out.println("pview.target_port_name==>"+pview.target_port_name);
				if(pm!=null){

					if(pm instanceof MonitoringMethodPortModel){
						if(pview.target_port_name.equals("RM_MonitoringMethodPort")){
							lm.setTargetTerminal(pview.source_port_name);
							lm.setTarget(pm);
							isTargetExist = true;
						}
					}
					else if(pm instanceof LifecycleMethodPortModel){
						if(pview.target_port_name.equals("RM_LifecycleMethodPort")){
							lm.setTargetTerminal(pview.source_port_name);
							lm.setTarget(pm);
							isTargetExist = true;
						}
					}
					else if(pm.getName().equals(pview.target_port_name)){
						lm.setTargetTerminal(pview.target_port_name);
						lm.setTarget(pm);
						isTargetExist = true;
					}
				}

			}
			}
			if(isSourceExist && isTargetExist){
				lm.attachSource();
				lm.attachTarget(nd);
			}
			
//			if("".equals(pview.t_source_component_name)){
				pview.t_source_component_name = "";
//			}
//			if("".equals(pview.t_target_component_name)){
				pview.t_target_component_name = "";
//			}
		}
		catch(Exception e){
			e.printStackTrace();
		}


	}

	public ComponentModel makeComponentLibSubModelView2(Subcomponent subView,int i,String path){

		ComponentModel cm = null;
		String name = subView.name;
		String key = path+File.separator+name+".xml";
		//		UMLTreeModel up = (UMLTreeModel)CompAssemManager.getInstance().getModelStore().get(name);
		UMLTreeModel up = (UMLTreeModel)CompAssemManager.getInstance().getDirModel().get(key);
		if(up==null)
		up = (UMLTreeModel)CompAssemManager.getInstance().getModelStore().get(subView.name);
		else if(up==null){
			up = (UMLTreeModel)CompAssemManager.getInstance().getImportViews().get(name);
		}
		else if(up==null){
			up = subView.createComponentModel(nd.getUMLTreeModel().getParent());
		}
		if(up!=null){
			UMLModel um=(UMLModel)up.getRefModel();
		
			UMLModel child = this.createModle(um);
			if(child==null){
				child = um;
			}
			if(child instanceof ComponentModel){
				cm = (ComponentModel)child;
				cm.setSize(new Dimension(150,100));
				int m = (i+1)%5;
				//				if(x!=100){
				if(m==0){
					x=50;
					y=y+200;
				}
				else{
					x = x + 200;
				}
				//				}


				cm.setLocation(new Point(x,y));
				System.out.println("x===========================================>"+x);
				nd.addChild(child);
				up.addN3UMLModelDeleteListener(nd);
				java.util.ArrayList aprts = cm.getPortManager().getPorts();

				for(int i1=0;i1<aprts.size();i1++){

					PortModel pm = (PortModel)aprts.get(i1);
					cm.createPort2(pm, nd);
					pm.setPortContainerModel(cm);


				}
				CompAssemManager.getInstance().autoLayoutPort(aprts);


			}
			CompAssemManager.getInstance().getViewStore().put(cm.getName(), cm);
			CompAssemManager.getInstance().getDirView().put(key, cm);
		}


		return cm;
	}
	
	
	public ComponentModel makeComponentLibSubModelTemplateView(Subcomponent subView,int i,String path){

		ComponentModel cm = null;
		String name = subView.name;
		String key = path+File.separator+name+".xml";
		UMLTreeModel cmp = (UMLTreeModel)ProjectManager.getInstance().templateCmp.get(name);
		if(cmp!=null){
			if(!cmp.getName().equals(name)){
				subView.t_name = cmp.getName();
				for(int i1=0;i1<=portConnections.size()-1;i1++){
					PortConnection pview = (PortConnection)portConnections.get(i1);
					if(name.equals(pview.source_component_name)){
						pview.t_source_component_name = subView.t_name;
					}
					if(name.equals(pview.target_component_name)){
						pview.t_target_component_name = subView.t_name;
					}
					
				}
				
			}
		}
//		UMLTreeModel cmp = (UMLTreeModel)ProjectManager.getInstance().templateCmp.get(up.getName());
		//		UMLTreeModel up = (UMLTreeModel)CompAssemManager.getInstance().getModelStore().get(name);
		
		UMLTreeModel	up = subView.createComponentTemplateModel(nd.getUMLTreeModel().getParent());
//		 cmp = (UMLTreeModel)ProjectManager.getInstance().templateCmp.get(up.getName());
		if(up!=null){
			UMLModel um=(UMLModel)up.getRefModel();
		
			UMLModel child = this.createModle(um);
			if(child==null){
				child = um;
			}
			if(child instanceof ComponentModel){
				cm = (ComponentModel)child;
				cm.setSize(new Dimension(150,100));
				int m = (i+1)%5;
				//				if(x!=100){
				System.out.println("x1==========================>"+x);
				if(m==0){
					x=50;
					y=y+200;
				}
				else{
					x = x + 200;
				}
				//				}
                if(cmp!=null && child instanceof AtomicComponentModel){
                	AtomicComponentModel am = (AtomicComponentModel)child;
                	am.setCoreUMLTreeModel(cmp);
                }

				cm.setLocation(new Point(x,y));
				System.out.println("x2==========================>"+x);
				nd.addChild(child);
				up.addN3UMLModelDeleteListener(nd);
//				java.util.ArrayList aprts = cm.getPortManager().getPorts();
				AtomicComponentModel cmpa = (AtomicComponentModel)cmp.getRefModel();
				java.util.ArrayList aprts = cmpa.getPortManager().getPorts();
				for(int i1=0;i1<aprts.size();i1++){

					PortModel pm = (PortModel)aprts.get(i1);
					cm.createPort2(pm, nd);
					pm.setPortContainerModel(cm);


				}
				CompAssemManager.getInstance().autoLayoutPort(aprts);


			}
			CompAssemManager.getInstance().getViewStore().put(name, cm);
			CompAssemManager.getInstance().getDirView().put(key, cm);
		}


		return cm;
	}



	public UMLModel createModle(UMLModel model) {
		ComponentLibModel newModel = null;
		if (model instanceof ComponentLibModel){
			newModel = new ComponentLibModel(model.uMLDataModel);
			ComponentLibModel cmodel = (ComponentLibModel)model;

			////etri 디폴트 컴포넌트 생성
			ComponentModel newModelpm = 	(ComponentModel)newModel;

			PortManager pm = cmodel.getPortManager();
			UMLTreeParentModel upm = (UMLTreeParentModel)cmodel.getUMLTreeModel();

			for(int i=0;i<pm.getPorts().size();i++){

				PortModel pm1 = (PortModel)pm.getPorts().get(i);
				PortModel pm2 = null;
				if(pm1 instanceof MonitoringMethodPortModel){
					pm2 = new MonitoringMethodPortModel(newModelpm);
					//					newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
				}
				else if(pm1 instanceof LifecycleMethodPortModel){
					pm2 = new LifecycleMethodPortModel(newModelpm);
					//					newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
				}
				else if(pm1 instanceof MethodOutputPortModel){
					pm2 = new MethodOutputPortModel(newModelpm);
					//					newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
				}
				else if(pm1 instanceof MethodInputPortModel){
					pm2 = new MethodInputPortModel(newModelpm);
					//						pm2.getAttachElementLabelModel().setText(pm1.getAttachElementLabelModel().getLabelContents());
					//					newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
				}
				else if(pm1 instanceof DataInputPortModel){
					pm2 = new DataInputPortModel(newModelpm);
					//						newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
				}
				else if(pm1 instanceof DataOutputPortModel){
					pm2 = new DataOutputPortModel(newModelpm);
					//						newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
				}
				else if(pm1 instanceof EventInputPortModel){
					pm2 = new EventInputPortModel(newModelpm);
					//						newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
				}
				else if(pm1 instanceof EventOutputPortModel){
					pm2 = new EventOutputPortModel(newModelpm);
					//						newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
				}

				pm2.getElementLabelModel().setReadOnly(true);

				pm2.getElementLabelModel().setPortId(pm1.getID());
				pm2.getUMLDataModel().setId(pm1.getID());
				pm2.getAttachElementLabelModel().setText(pm1.getAttachElementLabelModel().getLabelContents());
				pm2.getElementLabelModel().setTreeModel(pm1.getUMLTreeModel());
				newModelpm.getPortManager().getPorts().add(pm2);
				//					newModelpm.getUMLDataModel().setId(pm1.getID());



			}
			newModel.setSize(new Dimension(150,100));

		}
		return newModel;
	}


}
