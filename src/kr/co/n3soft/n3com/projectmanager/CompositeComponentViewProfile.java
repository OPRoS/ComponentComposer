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
import kr.co.n3soft.n3com.model.comm.UMLNoteModel;
import kr.co.n3soft.n3com.model.component.ComponentLibModel;
import kr.co.n3soft.n3com.model.component.ComponentModel;
import kr.co.n3soft.n3com.model.diagram.N3EditorDiagramModel;
import kr.co.n3soft.n3com.model.umlclass.DependencyLineModel;
import kr.co.n3soft.n3com.model.usecase.NoteLineModel;
import kr.co.n3soft.n3com.project.browser.UMLTreeModel;
import kr.co.n3soft.n3com.project.browser.UMLTreeParentModel;

import org.eclipse.core.runtime.Path;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.examples.logicdesigner.figures.LogicColorConstants;
import org.eclipse.gef.examples.shapes.LogicEditorInput;
import org.eclipse.swt.graphics.Color;
import org.eclipse.ui.IEditorInput;

public class CompositeComponentViewProfile extends CompositeComponentProfile{
	N3EditorDiagramModel nd = new N3EditorDiagramModel();
	public Dimension size = new Dimension(-1, -1);
	public Point location = new Point(0, 0);
	public Color backGroundColor = LogicColorConstants.defaultFillColor;
	public String value = "";
	public java.util.HashMap portViewes = new java.util.HashMap();
	
	public java.util.ArrayList notes = new java.util.ArrayList();
	public java.util.ArrayList noteConnects = new java.util.ArrayList();
	
	
	public N3EditorDiagramModel makeDiagram(){
//		N3EditorDiagramModel nd = new N3EditorDiagramModel();
		
		
		return nd;
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
//			um.setView(true);
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
//					if(pfv!=null){
//						pm.setPortContainerModel(cm);
//						cm.createPort2(pm, nd);
//						pm.setPtDifference(pfv.ptDifference);
//	                    pm.setLocation(pfv.location);
//					}
//					else{
//						System.out.println("ddddd");
//					}
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
//			
//			
//			
//			
//			
//		
//		}
//		return cm;
//	}
//	
	
	public ComponentLibModel makeComponentLibMainModelView(File pf){
		ComponentLibModel cm = null;
		String name = this.name;
		String pfName = pf.getPath();
		int index = pfName.lastIndexOf("_view.xml");
		pfName = pfName.substring(0,index)+".xml";
		String path = pfName.substring(0,index);
//		UMLTreeParentModel up = (UMLTreeParentModel)CompAssemManager.getInstance().getModelStore().get(name);
		UMLTreeParentModel up = (UMLTreeParentModel)CompAssemManager.getInstance().getDirModel().get(pfName);
		
//		UMLTreeParentModel uMLTreeParentModel = (UMLTreeParentModel)parentObject;
		int diagramType = 5;
		UMLTreeModel to1 =new UMLTreeModel("diagram");
		to1.setParent(up);
//		UMLTreeParentModel up= (UMLTreeParentModel)parentObject;
		if (to1 != null) {
			up.addChild(to1);
		}
		
		IEditorInput input = null;
		//PKY 08081101 S 저장 불러오기 할 경우 다이어그램 ToolTip초기화 되는문제 
	
			input = new LogicEditorInput(new Path(N3Messages.PALETTE_TITLE_COMPONENT));
		
		//PKY 08081101 E 저장 불러오기 할 경우 다이어그램 ToolTip초기화 되는문제
		nd.setReadOnly(true);
			to1.setRefModel(nd);
		nd.setName("diagram");
		nd.setTreeModel(to1);
		to1.setParent(up);
		nd.setIEditorInput(input);
		nd.setDiagramType( diagramType);
		
		
		if(up!=null){
			
			UMLModel um=(UMLModel)up.getRefModel();
			um.setView(true);
			UMLModel child = this.createModle(um);
			if(child instanceof ComponentLibModel){
				cm = (ComponentLibModel)child;
				cm.setSize(this.size);
				cm.setLocation(this.location);
				cm.setBackGroundColor(this.backGroundColor);
				nd.addChild(child);
				up.addN3UMLModelDeleteListener(nd);
				java.util.ArrayList aprts = cm.getPortManager().getPorts();
				for(int i=0;i<this.subcomponents.size();i++){
					
					SubcomponentView subView = (SubcomponentView)subcomponents.get(i);
					this.makeComponentLibSubModelView(subView,path);
				}
				for(int i=0;i<this.notes.size();i++){
					NoteView nv = (NoteView)this.notes.get(i);
					UMLNoteModel UMLNoteModel=new UMLNoteModel();
					UMLNoteModel.setView_ID(nv.viewId);
					UMLNoteModel.setSize(nv.size);
					UMLNoteModel.setLabelContents(nv.text);
					UMLNoteModel.setBackGroundColor(nv.backGroundColor);
					nd.addChild(UMLNoteModel);
					UMLNoteModel.setLocation(nv.location);
					
					CompAssemManager.getInstance().getModelStore().put(nv.noteId, UMLNoteModel);
				}
				
				for(int i=0;i<aprts.size();i++){
					
					PortModel pm = (PortModel)aprts.get(i);
					ExportPortView pfv =(ExportPortView)this.portViewes.get(pm.getName());
					if(pfv!=null){
						pm.setPortContainerModel(cm);
						 pm.getElementLabelModel().setReadOnly(true);
						cm.createPort2(pm, nd);
						pm.setPtDifference(pfv.ptDifference);
	                    pm.setLocation(pfv.location);
	                   
					}
					else{
						System.out.println("ddddd");
					}
				}
				
				
			}
			
			cm.setN3EditorDiagramModel(nd);
			CompAssemManager.getInstance().getDirView().put(pfName, cm);
			CompAssemManager.getInstance().getViewStore().put(cm.getName(), cm);
			
			for(int i=0;i<portConnections.size();i++){
				PortConnectionView pview = (PortConnectionView)portConnections.get(i);
				this.createLineModel(pview,path);
			}
			
			for(int i=0;i<this.noteConnects.size();i++){
				NoteViewLine pview = (NoteViewLine)noteConnects.get(i);
				this.createNoteLineModel(pview,path);
			}
			
			
			
			
			
		
		}
		return cm;
	}
	
	public void createNoteLineModel(NoteViewLine pview,String path){
		LineModel lm = null;
		N3EditorDiagramModel ndm = null;
		lm=new NoteLineModel();
		if(pview.note_connection_type==0){
//			pview.target_component_name;
			UMLModel utarget = (UMLModel)CompAssemManager.getInstance().getModelStore().get(pview.target_component_name);
			String fPaths = path+File.separator+pview.source_component_name+".xml";
			if(pview.link_type==1){
				ComponentModel  umSourceC = (ComponentModel)CompAssemManager.getInstance().getDirView().get(fPaths);
				if(umSourceC==null){
					File p = new File(path);
					fPaths = p.getParent()+File.separator+pview.source_component_name+".xml";
					umSourceC = (ComponentModel)CompAssemManager.getInstance().getDirView().get(fPaths);
					if(umSourceC==null){
						umSourceC = (ComponentModel)CompAssemManager.getInstance().getModelStore().get(pview.source_component_name);
					}
				}
//				lm.attachSource();
//				lm.attachTarget(nd);
				if(utarget!=null && umSourceC!=null){
					lm.setSourceTerminal(umSourceC.getName());
					lm.setTargetTerminal(utarget.getView_ID());
					lm.setTarget(utarget);
					lm.setSource(umSourceC);
					lm.attachSource();
					lm.attachTarget(nd);
					if(pview.getBendpoints().size()>0)
						lm.setBendpoints(pview.getBendpoints());
				}
			}
			else{
				ComponentModel  umSourceC = (ComponentModel)CompAssemManager.getInstance().getDirView().get(fPaths);
				if(umSourceC==null){
					File p = new File(path);
					fPaths = p.getParent()+File.separator+pview.source_component_name+".xml";
					umSourceC = (ComponentModel)CompAssemManager.getInstance().getDirView().get(fPaths);
					if(umSourceC==null){
						umSourceC = (ComponentModel)CompAssemManager.getInstance().getModelStore().get(pview.source_component_name);
						for(int i=0;i<umSourceC.getPortManager().getPorts().size();i++){
							PortModel pm = (PortModel)umSourceC.getPortManager().getPorts().get(i);
							if(pm!=null){
							    if(pm.getName().equals(pview.source_port_name)){
//							    	lm.setSourceTerminal(pview.source_port_name);
							    	lm.setSource(pm);
							    	if(utarget!=null && umSourceC!=null){
										lm.setTarget(utarget);
										lm.setSourceTerminal(pm.getName());
										lm.setTargetTerminal(utarget.getView_ID());
//										lm.setSource(umSourceC);
										lm.attachSource();
										lm.attachTarget(nd);
										if(pview.getBendpoints().size()>0)
											lm.setBendpoints(pview.getBendpoints());
									}
							    	
							    }
							}
							
						}
					}
				}
			}
		}
		else{
			UMLModel uSource = (UMLModel)CompAssemManager.getInstance().getModelStore().get(pview.source_component_name);
			String fPaths = path+File.separator+pview.target_component_name+".xml";
			if(pview.link_type==1){
				ComponentModel  umTarget = (ComponentModel)CompAssemManager.getInstance().getDirView().get(fPaths);
				if(umTarget==null){
					File p = new File(path);
					fPaths = p.getParent()+File.separator+pview.target_component_name+".xml";
					umTarget = (ComponentModel)CompAssemManager.getInstance().getDirView().get(fPaths);
					if(umTarget==null){
						umTarget = (ComponentModel)CompAssemManager.getInstance().getModelStore().get(pview.target_component_name);
					}
				}
//				lm.attachSource();
//				lm.attachTarget(nd);
				if(uSource!=null && umTarget!=null){
					lm.setSourceTerminal(uSource.getView_ID());
					lm.setSource(uSource);
					lm.setTargetTerminal(umTarget.getName());
					lm.setTarget(umTarget);
					lm.attachSource();
					lm.attachTarget(nd);
					if(pview.getBendpoints().size()>0)
						lm.setBendpoints(pview.getBendpoints());
				}
			}
			else{
				ComponentModel  umTarget = (ComponentModel)CompAssemManager.getInstance().getDirView().get(fPaths);
				if(umTarget==null){
					File p = new File(path);
					fPaths = p.getParent()+File.separator+pview.source_component_name+".xml";
					umTarget = (ComponentModel)CompAssemManager.getInstance().getDirView().get(fPaths);
					if(umTarget==null){
						umTarget = (ComponentModel)CompAssemManager.getInstance().getModelStore().get(pview.target_component_name);
						for(int i=0;i<umTarget.getPortManager().getPorts().size();i++){
							PortModel pm = (PortModel)umTarget.getPortManager().getPorts().get(i);
							if(pm!=null){
							    if(pm.getName().equals(pview.source_port_name)){
//							    	lm.setSourceTerminal(pview.source_port_name);
							    	lm.setSource(pm);
							    	if(uSource!=null && umTarget!=null){
										lm.setTarget(umTarget);
										lm.setSourceTerminal(uSource.getView_ID());
										lm.setTargetTerminal(pm.getName());
//										lm.setSource(umSourceC);
										lm.attachSource();
										lm.attachTarget(nd);
										if(pview.getBendpoints().size()>0)
											lm.setBendpoints(pview.getBendpoints());
									}
							    	
							    }
							}
							
						}
					}
				}
			}
		}
	}
	
	
	public void createLineModel(PortConnectionView pview,String path){
		LineModel lm = null;
		N3EditorDiagramModel ndm = null;
		lm=new DependencyLineModel();
		
//		ComponentModel  umSourceC = (ComponentModel)CompAssemManager.getInstance().getViewStore().get(pview.source_component_name);
//		ComponentModel  umTargetC = (ComponentModel)CompAssemManager.getInstance().getViewStore().get(pview.target_component_name);
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
			umSourceC = (ComponentModel)CompAssemManager.getInstance().getModelStore().get(pview.source_component_name);
		}
		if(umTargetC==null){
			umTargetC = (ComponentModel)CompAssemManager.getInstance().getModelStore().get(pview.target_component_name);
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
			if(pview.getBendpoints().size()>0)
				lm.setBendpoints(pview.getBendpoints());
		}
		

	}
	
	public void createLineModel(PortConnectionView pview){
		LineModel lm = null;
		N3EditorDiagramModel ndm = null;
		lm=new DependencyLineModel();
		
		ComponentModel  umSourceC = (ComponentModel)CompAssemManager.getInstance().getViewStore().get(pview.source_component_name);
		ComponentModel  umTargetC = (ComponentModel)CompAssemManager.getInstance().getViewStore().get(pview.target_component_name);
		
		for(int i=0;i<umSourceC.getPortManager().getPorts().size();i++){
			PortModel pm = (PortModel)umSourceC.getPortManager().getPorts().get(i);
			if(pm!=null){
			    if(pm.getName().equals(pview.source_port_name)){
			    	lm.setSourceTerminal(pview.source_port_name);
			    	lm.setSource(pm);
			    }
			}
			
		}
		lm.attachSource();

		for(int i=0;i<umTargetC.getPortManager().getPorts().size();i++){
			PortModel pm = (PortModel)umTargetC.getPortManager().getPorts().get(i);
			if(pm!=null){
			    if(pm.getName().equals(pview.target_port_name)){
			    	lm.setTargetTerminal(pview.target_port_name);
			    	lm.setTarget(pm);
			    }
			}
			
		}
		lm.attachTarget(nd);
		if(pview.getBendpoints().size()>0)
		lm.setBendpoints(pview.getBendpoints());
		
//		if(bendpoints!=null)
//			lm.setBendpoints(bendpoints);
	}
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
//					 cm.createPort2(pm, nd);
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
	
	public ComponentLibModel makeComponentLibSubModelView(SubcomponentView subView,String path){
		subView.setViewValue();
		ComponentLibModel cm = null;
		String name = subView.name;
//		UMLTreeModel up = (UMLTreeModel)CompAssemManager.getInstance().getModelStore().get(name);
		UMLTreeModel up = (UMLTreeModel)CompAssemManager.getInstance().getDirModel().get(path+File.separator+name+".xml");
		if(up!=null){
			UMLModel um=(UMLModel)up.getRefModel();
			UMLModel child = this.createModle(um);
			if(child instanceof ComponentLibModel){
				nd.setReadOnly(true);
				cm = (ComponentLibModel)child;
				cm.setSize(subView.size);
				cm.setLocation(subView.location);
				cm.setBackGroundColor(subView.backGroundColor);
				nd.addChild(child);
				up.addN3UMLModelDeleteListener(nd);
				java.util.ArrayList aprts = cm.getPortManager().getPorts();
				for(int i=0;i<aprts.size();i++){
					
					PortModel pm = (PortModel)aprts.get(i);
					pm.getElementLabelModel().setReadOnly(true);
					PortProfileView pfv =(PortProfileView)subView.portViewes.get(pm.getName());
					 cm.createPort2(pm, nd);
					pm.setPortContainerModel(cm);
					pm.setPtDifference(pfv.ptDifference);
                    pm.setLocation(pfv.location);
                   
				}
				
				
			}
			CompAssemManager.getInstance().getDirView().put(path+File.separator+name+".xml", cm);
			CompAssemManager.getInstance().getViewStore().put(cm.getName(), cm);
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
					pm2.setType(pm1.getType());
					pm2.getElementLabelModel().setPortId(pm1.getID());
					pm2.getUMLDataModel().setId(pm1.getID());
					pm2.getAttachElementLabelModel().setText(pm1.getAttachElementLabelModel().getLabelContents());
					pm2.getElementLabelModel().setReadOnly(true);
					pm2.getElementLabelModel().setTreeModel(pm1.getUMLTreeModel());
					newModelpm.getPortManager().getPorts().add(pm2);
//					newModelpm.getUMLDataModel().setId(pm1.getID());
					
			
		
			}
			newModel.setSize(new Dimension(150,100));
			
		}
		return newModel;
	}
	public void setViewValue(){
		String[] svalue = value.split(",");
		if(svalue.length==7){
			String w = svalue[0];
			String h = svalue[1];
			String x = svalue[2];
			String y = svalue[3];
			String r = svalue[4];
			String g = svalue[5];
			String b = svalue[6];
			
			this.size = new Dimension(Integer.valueOf(w), Integer.valueOf(h));
			this.location = new Point(Integer.valueOf(x), Integer.valueOf(y));
			Color backGroundColor = new Color(null,Integer.valueOf(r), Integer.valueOf(g),Integer.valueOf(b));
			
		}
		
	}

}
