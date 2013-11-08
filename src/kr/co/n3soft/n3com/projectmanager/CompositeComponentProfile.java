package kr.co.n3soft.n3com.projectmanager;

import java.io.File;

import kr.co.n3soft.n3com.lang.N3Messages;
import kr.co.n3soft.n3com.model.comm.DataInputPortModel;
import kr.co.n3soft.n3com.model.comm.DataOutputPortModel;
import kr.co.n3soft.n3com.model.comm.EventInputPortModel;
import kr.co.n3soft.n3com.model.comm.EventOutputPortModel;
import kr.co.n3soft.n3com.model.comm.LineModel;
import kr.co.n3soft.n3com.model.comm.MethodInputPortModel;
import kr.co.n3soft.n3com.model.comm.MethodOutputPortModel;
import kr.co.n3soft.n3com.model.comm.PortModel;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.component.ComponentLibModel;
import kr.co.n3soft.n3com.model.component.ComponentModel;
import kr.co.n3soft.n3com.model.diagram.N3EditorDiagramModel;
import kr.co.n3soft.n3com.model.umlclass.DependencyLineModel;
import kr.co.n3soft.n3com.project.browser.UMLTreeModel;
import kr.co.n3soft.n3com.project.browser.UMLTreeParentModel;

import org.eclipse.core.runtime.Path;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.examples.shapes.LogicEditorInput;
import org.eclipse.ui.IEditorInput;

public class CompositeComponentProfile extends ComponentProfile{
	
	public N3EditorDiagramModel nd = new N3EditorDiagramModel();
	
	public java.util.ArrayList subcomponents = new java.util.ArrayList();
	public java.util.ArrayList exportPorts = new java.util.ArrayList();
	public java.util.ArrayList portConnections = new java.util.ArrayList();
	public int y = 100;
	public int x = 100;
	public N3EditorDiagramModel makeDiagram(){
//		N3EditorDiagramModel nd = new N3EditorDiagramModel();
		
		
		return nd;
	}
	
	public ComponentLibModel makeComponentLibMainModelView(String path){
		ComponentLibModel cm = null;
		String name = this.name;
		int index = path.lastIndexOf(".xml");
		String subdir = path.substring(0,index);
//		String path = pfName.substring(0,index);
	
//		UMLTreeParentModel up = (UMLTreeParentModel)CompAssemManager.getInstance().getModelStore().get(name);
		UMLTreeParentModel up = (UMLTreeParentModel)CompAssemManager.getInstance().getDirModel().get(path);
		if(up==null){
			up = (UMLTreeParentModel)CompAssemManager.getInstance().getImportViews().get(name);
		}
		int diagramType = 5;
		UMLTreeModel to1 =new UMLTreeModel(name);
		to1.setParent(up);
		if (to1 != null) {
			up.addChild(to1);
		}
		IEditorInput input = null;
		input = new LogicEditorInput(new Path(N3Messages.PALETTE_TITLE_COMPONENT));
		to1.setRefModel(nd);
		nd.setName(name);
		nd.setTreeModel(to1);
		to1.setParent(up);
		nd.setIEditorInput(input);
		nd.setDiagramType( diagramType);
		if(up!=null){
		UMLModel um=(UMLModel)up.getRefModel();
		
		UMLModel child = this.createModle(um);
		if(child instanceof ComponentLibModel){
			cm = (ComponentLibModel)child;
			
			cm.setSize(new Dimension(800,500));
			cm.setLocation(new Point(10,10));
			
		
	
			
//			cm.setBackGroundColor(this.backGroundColor);
			nd.addChild(child);
			up.addN3UMLModelDeleteListener(nd);
			java.util.ArrayList aprts = cm.getPortManager().getPorts();
			for(int i=0;i<this.subcomponents.size();i++){
				
				Subcomponent subView = (Subcomponent)subcomponents.get(i);
				this.makeComponentLibSubModelView(subView,i,subdir);
			}
			
			
			
			
			for(int i1=0;i1<aprts.size();i1++){
				
				PortModel pm = (PortModel)aprts.get(i1);
				cm.createPort2(pm, nd);
				pm.setPortContainerModel(cm);
				
               
			}
			for(int i2 =0;i2<this.exportPorts.size();i2++){
				ExportPort ep =(ExportPort)this.exportPorts.get(i2);
				
				PortConnection pc = ep.getPortConnection();
				pc.source_component_name = cm.getName();
				this.portConnections.add(pc);
			}
			
			
			CompAssemManager.getInstance().autoLayoutPort(aprts);
			CompAssemManager.getInstance().getDirView().put(path, cm);
			CompAssemManager.getInstance().getViewStore().put(cm.getName(), cm);
			
			for(int i=0;i<portConnections.size();i++){
				PortConnection pview = (PortConnection)portConnections.get(i);
				this.createLineModel(pview,subdir);
			}
			return cm;
			
			
		}
		}
	
	return null;
		
		
	}
	
//	public ComponentLibModel makeComponentLibMainModelView(){
//		ComponentLibModel cm = null;
//		String name = this.name;
//	
//		UMLTreeParentModel up = (UMLTreeParentModel)CompAssemManager.getInstance().getModelStore().get(name);
//		int diagramType = 5;
//		UMLTreeModel to1 =new UMLTreeModel(name);
//		to1.setParent(up);
//		if (to1 != null) {
//			up.addChild(to1);
//		}
//		IEditorInput input = null;
//		input = new LogicEditorInput(new Path(N3Messages.PALETTE_TITLE_COMPONENT));
//		to1.setRefModel(nd);
//		nd.setName(name);
//		nd.setTreeModel(to1);
//		to1.setParent(up);
//		nd.setIEditorInput(input);
//		nd.setDiagramType( diagramType);
//		if(up!=null){
//		UMLModel um=(UMLModel)up.getRefModel();
//		
//		UMLModel child = this.createModle(um);
//		if(child instanceof ComponentLibModel){
//			cm = (ComponentLibModel)child;
//			cm.setSize(new Dimension(800,500));
//			cm.setLocation(new Point(10,10));
////			cm.setBackGroundColor(this.backGroundColor);
//			nd.addChild(child);
//			up.addN3UMLModelDeleteListener(nd);
//			java.util.ArrayList aprts = cm.getPortManager().getPorts();
//			for(int i=0;i<this.subcomponents.size();i++){
//				
//				Subcomponent subView = (Subcomponent)subcomponents.get(i);
//				this.makeComponentLibSubModelView(subView,i);
//			}
//			
//			
//			
//			
//			for(int i1=0;i1<aprts.size();i1++){
//				
//				PortModel pm = (PortModel)aprts.get(i1);
//				cm.createPort2(pm, nd);
//				pm.setPortContainerModel(cm);
//				
//               
//			}
//			for(int i2 =0;i2<this.exportPorts.size();i2++){
//				ExportPort ep =(ExportPort)this.exportPorts.get(i2);
//				
//				PortConnection pc = ep.getPortConnection();
//				pc.source_component_name = cm.getName();
//				this.portConnections.add(pc);
//			}
//			
//			
//			CompAssemManager.getInstance().autoLayoutPort(aprts);
//			CompAssemManager.getInstance().getViewStore().put(cm.getName(), cm);
//			
//			for(int i=0;i<portConnections.size();i++){
//				PortConnection pview = (PortConnection)portConnections.get(i);
//				this.createLineModel(pview);
//			}
//			return cm;
//			
//			
//		}
//		}
//	
//	return null;
//		
//		
//	}
	
	

		
		public void createLineModel(PortConnection pview){
			try{
			LineModel lm = null;
			N3EditorDiagramModel ndm = null;
			lm=new DependencyLineModel();
			
			ComponentModel  umSourceC = (ComponentModel)CompAssemManager.getInstance().getViewStore().get(pview.source_component_name);
			ComponentModel  umTargetC = (ComponentModel)CompAssemManager.getInstance().getViewStore().get(pview.target_component_name);
			boolean isSourceExist = false;
			boolean isTargetExist = false;
			for(int i=0;i<umSourceC.getPortManager().getPorts().size();i++){
				PortModel pm = (PortModel)umSourceC.getPortManager().getPorts().get(i);
				if(pm!=null){
				    if(pm.getName().equals(pview.source_port_name)){
				    	lm.setSourceTerminal(pview.source_port_name);
				    	lm.setSource(pm);
				    	isSourceExist = true;
				    }
				}
				
			}
			

			for(int i=0;i<umTargetC.getPortManager().getPorts().size();i++){
				PortModel pm = (PortModel)umTargetC.getPortManager().getPorts().get(i);
				if(pm!=null){
				    if(pm.getName().equals(pview.target_port_name)){
				    	lm.setTargetTerminal(pview.target_port_name);
				    	lm.setTarget(pm);
				    	isTargetExist = true;
				    }
				}
				
			}
			if(isSourceExist && isTargetExist){
				lm.attachSource();
				lm.attachTarget(nd);
			}
			}
			catch(Exception e){
				e.printStackTrace();
			}
			

		}
		
		
		public void createLineModel(PortConnection pview,String path){
			LineModel lm = null;
			N3EditorDiagramModel ndm = null;
			lm=new DependencyLineModel();
			try{
			
//			ComponentModel  umSourceC = (ComponentModel)CompAssemManager.getInstance().getViewStore().get(pview.source_component_name);
//			ComponentModel  umTargetC = (ComponentModel)CompAssemManager.getInstance().getViewStore().get(pview.target_component_name);
			String fPaths = path+File.separator+pview.source_component_name+".xml";
			String fPatht = path+File.separator+pview.target_component_name+".xml";
			ComponentModel  umSourceC = (ComponentModel)CompAssemManager.getInstance().getDirView().get(fPaths);
			ComponentModel  umTargetC = (ComponentModel)CompAssemManager.getInstance().getDirView().get(fPatht);
			boolean isSourceExist = false;
			boolean isTargetExist = false;
			if(umSourceC==null){
				File p = new File(path);
				fPaths = p.getParent()+File.separator+pview.source_component_name+".xml";
				umSourceC = (ComponentModel)CompAssemManager.getInstance().getDirView().get(fPaths);
			}
			if(umTargetC==null){
				File p = new File(path);
				fPaths = p.getParent()+File.separator+pview.target_component_name+".xml";
				umTargetC = (ComponentModel)CompAssemManager.getInstance().getDirView().get(fPaths);
			}
			
			if(umSourceC==null){
				System.out.println("-----");
			}
			for(int i=0;i<umSourceC.getPortManager().getPorts().size();i++){
				PortModel pm = (PortModel)umSourceC.getPortManager().getPorts().get(i);
				if(pm!=null){
				    if(pm.getName().equals(pview.source_port_name)){
				    	lm.setSourceTerminal(pview.source_port_name);
				    	lm.setSource(pm);
				    	isSourceExist = true;
				    }
				}
				
			}
			

			for(int i=0;i<umTargetC.getPortManager().getPorts().size();i++){
				PortModel pm = (PortModel)umTargetC.getPortManager().getPorts().get(i);
				if(pm!=null){
				    if(pm.getName().equals(pview.target_port_name)){
				    	lm.setTargetTerminal(pview.target_port_name);
				    	lm.setTarget(pm);
				    	isTargetExist = true;
				    }
				}
				
			}
			if(isSourceExist && isTargetExist){
				lm.attachSource();
				lm.attachTarget(nd);
			}
			}
			catch(Exception e){
				e.printStackTrace();
			}
			

		}
		
		
		
//		public ComponentLibModel makeComponentLibSubModelView(Subcomponent subView,int i){
//			
//			ComponentLibModel cm = null;
//			String name = subView.name;
//			UMLTreeModel up = (UMLTreeModel)CompAssemManager.getInstance().getModelStore().get(name);
//			if(up!=null){
//				UMLModel um=(UMLModel)up.getRefModel();
//				UMLModel child = this.createModle(um);
//				if(child instanceof ComponentLibModel){
//					cm = (ComponentLibModel)child;
//					cm.setSize(new Dimension(150,100));
//					int m = (i+1)%5;
////					if(x!=100){
//						if(m==0){
//							x=50;
//							y=y+200;
//						}
//						else{
//							x = x + 200;
//						}
////					}
//					
//					
//					cm.setLocation(new Point(x,y));
//					nd.addChild(child);
//					up.addN3UMLModelDeleteListener(nd);
//					java.util.ArrayList aprts = cm.getPortManager().getPorts();
//					
//					for(int i1=0;i1<aprts.size();i1++){
//						
//						PortModel pm = (PortModel)aprts.get(i1);
//						cm.createPort2(pm, nd);
//						pm.setPortContainerModel(cm);
//						
//	                   
//					}
//					CompAssemManager.getInstance().autoLayoutPort(aprts);
//					
//					
//				}
//				CompAssemManager.getInstance().getViewStore().put(cm.getName(), cm);
//			}
//			
//			
//			return cm;
//		}
		
		
		
		
		
public ComponentLibModel makeComponentLibSubModelView(Subcomponent subView,int i,String path){
			
			ComponentLibModel cm = null;
			String name = subView.name;
			String key = path+File.separator+name+".xml";
//			UMLTreeModel up = (UMLTreeModel)CompAssemManager.getInstance().getModelStore().get(name);
			UMLTreeModel up = (UMLTreeModel)CompAssemManager.getInstance().getDirModel().get(key);
			if(up!=null){
				UMLModel um=(UMLModel)up.getRefModel();
				UMLModel child = this.createModle(um);
				if(child instanceof ComponentLibModel){
					cm = (ComponentLibModel)child;
					cm.setSize(new Dimension(150,100));
					int m = (i+1)%5;
//					if(x!=100){
						if(m==0){
							x=50;
							y=y+200;
						}
						else{
							x = x + 200;
						}
//					}
					
					
					cm.setLocation(new Point(x,y));
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
//						newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
						}
						else if(pm1 instanceof LifecycleMethodPortModel){
							pm2 = new LifecycleMethodPortModel(newModelpm);
//						newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
						}
						else if(pm1 instanceof MethodOutputPortModel){
							pm2 = new MethodOutputPortModel(newModelpm);
//						newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
						}
						else if(pm1 instanceof MethodInputPortModel){
							pm2 = new MethodInputPortModel(newModelpm);
//							pm2.getAttachElementLabelModel().setText(pm1.getAttachElementLabelModel().getLabelContents());
//						newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
						}
						else if(pm1 instanceof DataInputPortModel){
							pm2 = new DataInputPortModel(newModelpm);
//							newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
						}
						else if(pm1 instanceof DataOutputPortModel){
							pm2 = new DataOutputPortModel(newModelpm);
//							newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
						}
						else if(pm1 instanceof EventInputPortModel){
							pm2 = new EventInputPortModel(newModelpm);
//							newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
						}
						else if(pm1 instanceof EventOutputPortModel){
							pm2 = new EventOutputPortModel(newModelpm);
//							newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
						}
					
							pm2.getElementLabelModel().setReadOnly(true);
					
						pm2.getElementLabelModel().setPortId(pm1.getID());
						pm2.getUMLDataModel().setId(pm1.getID());
						pm2.getAttachElementLabelModel().setText(pm1.getAttachElementLabelModel().getLabelContents());
						pm2.getElementLabelModel().setTreeModel(pm1.getUMLTreeModel());
						newModelpm.getPortManager().getPorts().add(pm2);
//						newModelpm.getUMLDataModel().setId(pm1.getID());
						
				
			
				}
				newModel.setSize(new Dimension(150,100));
				
			}
			return newModel;
		}
		
	
//	public ComponentLibModel makeComponentLibMainModelView(){
//		ComponentLibModel cm = null;
//		String name = this.name;
//		UMLTreeParentModel up = (UMLTreeParentModel)CompAssemManager.getInstance().getModelStore().get(name);
//		
////		UMLTreeParentModel uMLTreeParentModel = (UMLTreeParentModel)parentObject;
//		int diagramType = 5;
//		UMLTreeModel to1 =new UMLTreeModel(name);
//		to1.setParent(up);
////		UMLTreeParentModel up= (UMLTreeParentModel)parentObject;
//		if (to1 != null) {
//			up.addChild(to1);
//		}
//		
//		IEditorInput input = null;
//		//PKY 08081101 S 저장 불러오기 할 경우 다이어그램 ToolTip초기화 되는문제 
//	
//			input = new LogicEditorInput(new Path(N3Messages.PALETTE_TITLE_COMPONENT));
//		
//		//PKY 08081101 E 저장 불러오기 할 경우 다이어그램 ToolTip초기화 되는문제
//		to1.setRefModel(nd);
//		nd.setName(name);
//		nd.setTreeModel(to1);
//		to1.setParent(up);
//		nd.setIEditorInput(input);
//		nd.setDiagramType( diagramType);
//		
//		
//		if(up!=null){
//			UMLModel um=(UMLModel)up.getRefModel();
//			
//			UMLModel child = this.createModle(um);
//			if(child instanceof ComponentLibModel){
//				cm = (ComponentLibModel)child;
//				cm.setSize(this.size);
//				cm.setLocation(this.location);
//				cm.setBackGroundColor(this.backGroundColor);
//				nd.addChild(child);
//				up.addN3UMLModelDeleteListener(nd);
//				java.util.ArrayList aprts = cm.getPortManager().getPorts();
//				for(int i=0;i<this.subcomponents.size();i++){
//					
//					SubcomponentView subView = (SubcomponentView)subcomponents.get(i);
//					this.makeComponentLibSubModelView(subView);
//				}
//				
//				
//				for(int i=0;i<aprts.size();i++){
//					
//					PortModel pm = (PortModel)aprts.get(i);
//					ExportPortView pfv =(ExportPortView)this.portViewes.get(pm.getName());
//					pm.setPortContainerModel(cm);
//					pm.setPtDifference(pfv.ptDifference);
//                    pm.setLocation(pfv.location);
//                    cm.createPort(pm, nd);
//				}
//				
//				
//			}
//			
//			cm.setN3EditorDiagramModel(nd);
//			CompAssemManager.getInstance().getViewStore().put(cm.getName(), cm);
//			
//			for(int i=0;i<portConnections.size();i++){
//				PortConnectionView pview = (PortConnectionView)portConnections.get(i);
//				this.createLineModel(pview);
//			}
//			if(cm.getName().equals("Component0")){
//				System.out.println("=======================>");
//			}
//			
//			
//			
//			
//		
//		}
//		return cm;
//	}
//	public void createLineModel(PortConnectionView pview){
//		LineModel lm = null;
//		N3EditorDiagramModel ndm = null;
//		lm=new DependencyLineModel();
//		
//		ComponentModel  umSourceC = (ComponentModel)CompAssemManager.getInstance().getViewStore().get(pview.source_component_name);
//		ComponentModel  umTargetC = (ComponentModel)CompAssemManager.getInstance().getViewStore().get(pview.target_component_name);
//		
//		for(int i=0;i<umSourceC.getPortManager().getPorts().size();i++){
//			PortModel pm = (PortModel)umSourceC.getPortManager().getPorts().get(i);
//			if(pm!=null){
//			    if(pm.getName().equals(pview.source_port_name)){
//			    	lm.setSourceTerminal(pview.source_port_name);
//			    	lm.setSource(pm);
//			    }
//			}
//			
//		}
//		lm.attachSource();
//
//		for(int i=0;i<umTargetC.getPortManager().getPorts().size();i++){
//			PortModel pm = (PortModel)umTargetC.getPortManager().getPorts().get(i);
//			if(pm!=null){
//			    if(pm.getName().equals(pview.target_port_name)){
//			    	lm.setTargetTerminal(pview.target_port_name);
//			    	lm.setTarget(pm);
//			    }
//			}
//			
//		}
//		lm.attachTarget(nd);
//		if(pview.getBendpoints().size()>0)
//		lm.setBendpoints(pview.getBendpoints());
//		
////		if(bendpoints!=null)
////			lm.setBendpoints(bendpoints);
//	}
//	public ComponentLibModel makeComponentLibSubModelView(SubcomponentView subView){
//		subView.setViewValue();
//		ComponentLibModel cm = null;
//		String name = subView.name;
//		UMLTreeModel up = (UMLTreeModel)CompAssemManager.getInstance().getModelStore().get(name);
//		if(up!=null){
//			UMLModel um=(UMLModel)up.getRefModel();
//			UMLModel child = this.createModle(um);
//			if(child instanceof ComponentLibModel){
//				cm = (ComponentLibModel)child;
//				cm.setSize(subView.size);
//				cm.setLocation(subView.location);
//				cm.setBackGroundColor(subView.backGroundColor);
//				nd.addChild(child);
//				up.addN3UMLModelDeleteListener(nd);
//				java.util.ArrayList aprts = cm.getPortManager().getPorts();
//				for(int i=0;i<aprts.size();i++){
//					
//					PortModel pm = (PortModel)aprts.get(i);
//					PortProfileView pfv =(PortProfileView)subView.portViewes.get(pm.getName());
//					 cm.createPort(pm, nd);
//					pm.setPortContainerModel(cm);
//					pm.setPtDifference(pfv.ptDifference);
//                    pm.setLocation(pfv.location);
//                   
//				}
//				
//				
//			}
//			CompAssemManager.getInstance().getViewStore().put(cm.getName(), cm);
//		}
//		
//		
//		return cm;
//	}
//	
//	
//	
//	public UMLModel createModle(UMLModel model) {
//		ComponentLibModel newModel = null;
//		if (model instanceof ComponentLibModel){
//			 newModel = new ComponentLibModel(model.uMLDataModel);
//			ComponentLibModel cmodel = (ComponentLibModel)model;
//			
//			////etri 디폴트 컴포넌트 생성
//			ComponentModel newModelpm = 	(ComponentModel)newModel;
//			
//			PortManager pm = cmodel.getPortManager();
//			UMLTreeParentModel upm = (UMLTreeParentModel)cmodel.getUMLTreeModel();
//		
//				for(int i=0;i<pm.getPorts().size();i++){
//					
//					PortModel pm1 = (PortModel)pm.getPorts().get(i);
//					PortModel pm2 = null;
//					if(pm1 instanceof MonitoringMethodPortModel){
//						pm2 = new MonitoringMethodPortModel(newModelpm);
////					newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
//					}
//					else if(pm1 instanceof LifecycleMethodPortModel){
//						pm2 = new LifecycleMethodPortModel(newModelpm);
////					newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
//					}
//					else if(pm1 instanceof MethodOutputPortModel){
//						pm2 = new MethodOutputPortModel(newModelpm);
////					newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
//					}
//					else if(pm1 instanceof MethodInputPortModel){
//						pm2 = new MethodInputPortModel(newModelpm);
////						pm2.getAttachElementLabelModel().setText(pm1.getAttachElementLabelModel().getLabelContents());
////					newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
//					}
//					else if(pm1 instanceof DataInputPortModel){
//						pm2 = new DataInputPortModel(newModelpm);
////						newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
//					}
//					else if(pm1 instanceof DataOutputPortModel){
//						pm2 = new DataOutputPortModel(newModelpm);
////						newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
//					}
//					else if(pm1 instanceof EventInputPortModel){
//						pm2 = new EventInputPortModel(newModelpm);
////						newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
//					}
//					else if(pm1 instanceof EventOutputPortModel){
//						pm2 = new EventOutputPortModel(newModelpm);
////						newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
//					}
//					pm2.getElementLabelModel().setPortId(pm1.getID());
//					pm2.getUMLDataModel().setId(pm1.getID());
//					pm2.getAttachElementLabelModel().setText(pm1.getAttachElementLabelModel().getLabelContents());
//					pm2.getElementLabelModel().setTreeModel(pm1.getUMLTreeModel());
//					newModelpm.getPortManager().getPorts().add(pm2);
////					newModelpm.getUMLDataModel().setId(pm1.getID());
//					
//			
//		
//			}
//			newModel.setSize(new Dimension(150,100));
//			
//		}
//		return newModel;
//	}
//	public void setViewValue(){
//		String[] svalue = value.split(",");
//		if(svalue.length==7){
//			String w = svalue[0];
//			String h = svalue[1];
//			String x = svalue[2];
//			String y = svalue[3];
//			String r = svalue[4];
//			String g = svalue[5];
//			String b = svalue[6];
//			
//			this.size = new Dimension(Integer.valueOf(w), Integer.valueOf(h));
//			this.location = new Point(Integer.valueOf(x), Integer.valueOf(y));
//			Color backGroundColor = new Color(null,Integer.valueOf(r), Integer.valueOf(g),Integer.valueOf(b));
//			
//		}
//		
//	}

}
