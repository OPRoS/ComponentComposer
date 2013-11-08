package kr.co.n3soft.n3com.model.comm.command;

import java.io.File;

import kr.co.n3soft.n3com.lang.N3Messages;
import kr.co.n3soft.n3com.model.activity.HPartitionModel;
import kr.co.n3soft.n3com.model.activity.TextAttachModel;
import kr.co.n3soft.n3com.model.comm.DataInputPortModel;
import kr.co.n3soft.n3com.model.comm.DataOutputPortModel;
import kr.co.n3soft.n3com.model.comm.EventInputPortModel;
import kr.co.n3soft.n3com.model.comm.EventOutputPortModel;
import kr.co.n3soft.n3com.model.comm.FinalPackageModel;
import kr.co.n3soft.n3com.model.comm.LineModel;
import kr.co.n3soft.n3com.model.comm.MethodInputPortModel;
import kr.co.n3soft.n3com.model.comm.MethodOutputPortModel;
import kr.co.n3soft.n3com.model.comm.PortContainerModel;
import kr.co.n3soft.n3com.model.comm.PortModel;
import kr.co.n3soft.n3com.model.comm.UMLContainerModel;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.comm.UMLNoteModel;
import kr.co.n3soft.n3com.model.component.AtomicComponentModel;
import kr.co.n3soft.n3com.model.component.ComponentLibModel;
import kr.co.n3soft.n3com.model.component.ComponentModel;
import kr.co.n3soft.n3com.model.component.OPRoSDataTypeElementModel;
import kr.co.n3soft.n3com.model.component.OPRoSDataTypesElementModel;
import kr.co.n3soft.n3com.model.component.OPRoSExeEnvironmentCPUElementModel;
import kr.co.n3soft.n3com.model.component.OPRoSExeEnvironmentElementModel;
import kr.co.n3soft.n3com.model.component.OPRoSExeEnvironmentOSElementModel;
import kr.co.n3soft.n3com.model.component.OPRoSPropertyElementModel;
import kr.co.n3soft.n3com.model.component.OPRoSServiceTypeElementModel;
import kr.co.n3soft.n3com.model.component.OPRoSServiceTypesElementModel;
import kr.co.n3soft.n3com.model.component.SyncManager;
import kr.co.n3soft.n3com.model.diagram.N3EditorDiagramModel;
import kr.co.n3soft.n3com.model.diagram.UMLDiagramModel;
import kr.co.n3soft.n3com.model.usecase.FinalBoundryModel;
import kr.co.n3soft.n3com.project.browser.PackageTreeModel;
import kr.co.n3soft.n3com.project.browser.RootCmpEdtTreeModel;
import kr.co.n3soft.n3com.project.browser.StrcuturedPackageTreeLibModel;
import kr.co.n3soft.n3com.project.browser.UMLTreeModel;
import kr.co.n3soft.n3com.project.browser.UMLTreeParentModel;
import kr.co.n3soft.n3com.project.dialog.CInputDialog;
import kr.co.n3soft.n3com.project.dialog.DetailPropertyTableItem;
import kr.co.n3soft.n3com.project.dialog.OPRoSDataPortDialog;
import kr.co.n3soft.n3com.project.dialog.OPRoSDataTypeInputDialog;
import kr.co.n3soft.n3com.project.dialog.OPRoSEventPortDialog;
import kr.co.n3soft.n3com.project.dialog.OPRoSExeEnvOSInputDialog;
import kr.co.n3soft.n3com.project.dialog.OPRoSPropertyInputDialog;
import kr.co.n3soft.n3com.project.dialog.OPRoSServicePortDialog;
import kr.co.n3soft.n3com.project.dialog.OPRoSServiceTypeInputDialog;
import kr.co.n3soft.n3com.projectmanager.PortProfile;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.examples.logicdesigner.LogicMessages;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.MessageBox;


public class UMLCreateCommand extends org.eclipse.gef.commands.Command {
	private UMLModel child;
	private Rectangle rect;
	private UMLDiagramModel parent;
	private int index = -1;
	private boolean isTreeAdd = false;
	
//	WJH 100730 S �ν��Ͻ� �߰�
	private static UMLCreateCommand instance;
	
	public static UMLCreateCommand getInstance(){
		if(instance == null){
			return instance = new UMLCreateCommand();
		}
		return instance;
	}
//	WJH 100730 E �ν��Ͻ� �߰�

	public UMLCreateCommand() {
		super(LogicMessages.CreateCommand_Label);
	}

	public boolean canExecute() {
		//20080811IJS ����
		if(parent!=null && this.parent instanceof UMLModel){
			UMLModel um = this.parent;
			if(um.isReadOnlyModel){
				return false;
			}
		}
		//20080811IJS ��
		return child != null && parent != null;
	}

	public void execute() {
		try {
			if (rect != null) {
				Insets expansion = getInsets();
				if (!rect.isEmpty())
					rect.expand(expansion);
				else {
					rect.x -= expansion.left;
					rect.y -= expansion.top;
				}
				N3EditorDiagramModel nd =	ProjectManager.getInstance().getUMLEditor().getDiagram();
				if(nd!=null && nd.isReadOnly()){
					return;
				}
				
//				WJH 090801 S
				System.out.println("================Font Change Targeting=================");
				System.out.println("child type : "+child.getName());
				if(child instanceof ComponentModel){
					ProjectManager.getInstance().setComponent(true);
					System.out.println("component");					
				}
				else{
					ProjectManager.getInstance().setComponent(false);
					System.out.println("not component");
				}
//				WJH 090801 E
				
//				if(child instanceof MethodInputPortModel){
//					ComponentModel um = (ComponentModel)this.getPortContainerModel((PortModel)child);
//					if(um instanceof AtomicComponentModel){
//						OPRoSServicePortDialog opd = new OPRoSServicePortDialog();
//					}
//				}
				UMLContainerModel uc  = null;
				ComponentModel um = null;
//				if(child instanceof PortModel){
//					
//				}
				if(child instanceof PortModel){
					 um = (ComponentModel)this.getPortContainerModel((PortModel)child);
				if(um instanceof AtomicComponentModel){
					AtomicComponentModel atomicComponentModel1 = (AtomicComponentModel)um;
					if(atomicComponentModel1.getCoreUMLTreeModel()==null){
						um = atomicComponentModel1;
					}
					else{
//						AtomicComponentModel atomicComponentModel1 = (AtomicComponentModel)um;
						UMLTreeParentModel ut = (UMLTreeParentModel)atomicComponentModel1.getCoreUMLTreeModel();
						um = (AtomicComponentModel)ut.getRefModel();
//						for(int i=0;i<ut.getChildren().length;i++){
//							UMLTreeModel obj = (UMLTreeModel)ut.getChildren()[i];
//							
//							if(obj.getRefModel() instanceof N3EditorDiagramModel){
//								uc = (N3EditorDiagramModel)obj.getRefModel();
//								break;
//							}
//						}
						
					}
				}
				}
				
				if(child instanceof MethodInputPortModel){
//					(PortModel)child;
					 um = (ComponentModel)this.getPortContainerModel((PortModel)child);
					if(um instanceof ComponentLibModel){
						return;
					}
					else if(um instanceof AtomicComponentModel){
						AtomicComponentModel atomicComponentModel1 = (AtomicComponentModel)um;
						if(atomicComponentModel1.getCoreUMLTreeModel()==null){
							um = atomicComponentModel1;
						}
						else{
//							AtomicComponentModel atomicComponentModel1 = (AtomicComponentModel)um;
							UMLTreeParentModel ut = (UMLTreeParentModel)atomicComponentModel1.getCoreUMLTreeModel();
							um = ((AtomicComponentModel)ut.getRefModel()).getCoreDiagramCmpModel();
//							um.get
//							for(int i=0;i<ut.getChildren().length;i++){
//								UMLTreeModel obj = (UMLTreeModel)ut.getChildren()[i];
//								
//								if(obj.getRefModel() instanceof N3EditorDiagramModel){
//									uc = (N3EditorDiagramModel)obj.getRefModel();
//									break;
//								}
//							}
							
						}
					}
					PortModel pm = new MethodInputPortModel(um);
					if(um instanceof AtomicComponentModel){
						
						OPRoSServicePortDialog opd = new OPRoSServicePortDialog(ProjectManager.getInstance().window.getShell(),false,true,pm);
//						opd.setNew(true);
//						opd.setPm(pm);
						int i = opd.open();
						if(i!=Dialog.OK)
							return;
						
						
						
					}
					
					if(um.getAcceptParentModel()!=null){
						um.createPort( pm, (UMLContainerModel)um.getAcceptParentModel());
//						ijs 110902
						if(ProjectManager.getInstance().getUMLEditor()!=null && 
    							ProjectManager.getInstance().getUMLEditor().getDiagram()==um.getAcceptParentModel()){
							//110905 S SDM 110905 - Y������ ��Ʈ ���⵵�� ����
							pm.temp_x = rect.x+expansion.left;
							pm.temp_y = rect.y+expansion.top;
							
							int cx = um.getLocation().x;
    						int cy = um.getLocation().y;
    						int cheight = um.getSize().height;
    						int cwidth = um.getSize().width;
    						
    						System.out.println("tempx============>"+pm.temp_x);
    						System.out.println("temp_y============>"+pm.temp_y);
    						System.out.println("cx============>"+cx);
    						System.out.println("cy============>"+cy);
    						System.out.println("cheight============>"+cheight);
    						System.out.println("cwidth============>"+cwidth);
    						
    						if(um.getRectangle().contains(new Point(pm.temp_x,pm.temp_y))){
    							if(pm.temp_x<=cx+cwidth/2 && pm.temp_y<=cy+cheight/2){
    								int nX = (cx+cwidth/2) - pm.temp_x;
    								int nY = (cy+cheight/2) - pm.temp_y;
    								
    								if(nX > nY){
    									pm.temp_x = cx-30;
    								}
    								else{
    									pm.temp_y = cy-30;
    								}
    								
    							}
    							else if(pm.temp_x<=cx+cwidth/2 && pm.temp_y>cy+cheight/2){
    								int nX = (cx+cwidth/2) - pm.temp_x;
    								int nY = pm.temp_y - (cy+cheight/2);
    								
    								if(nX > nY){
    									pm.temp_x = cx-30;
    								}
    								else{
    									pm.temp_y = cy+cheight;
    								}
    							}
    							else if(pm.temp_x>cx+cwidth/2 && pm.temp_y<=cy+cheight/2){
    								int nX = pm.temp_x - (cx+cwidth/2);
    								int nY = (cy+cheight/2) - pm.temp_y;
    								
    								if(nX > nY){
    									pm.temp_x = cx+cwidth;
    								}
    								else{
    									pm.temp_y = cy-30;
    								}
    							}
    							else if(pm.temp_x>cx+cwidth/2 && pm.temp_y>cy+cheight/2){
    								int nX = pm.temp_x - (cx+cwidth/2);
    								int nY = pm.temp_y - (cy+cheight/2);
    								
    								if(nX > nY){
    									pm.temp_x = cx+cwidth;
    								}
    								else{
    									pm.temp_y = cy+cheight;
    								}
    							}
    							
//    							if(pm.temp_y<cy+cheight/2){
//    								pm.temp_y = cy;
//    							}
//    							else if(pm.temp_y>cy+cm.getBoundRectangle().height/2){
//    								pm.temp_y = cy+cheight;
//    							}
    							
    						    pm.setLocation(new Point(pm.temp_x,pm.temp_y));
//    						}
    						}
    						//110905 E SDM 110905 - Y������ ��Ʈ ���⵵�� ����
    					}
					}
					
//					else if(uc!=null){
//						um.createPort( pm, uc);
//					}
		                UMLTreeParentModel to1 = (UMLTreeParentModel)um.getUMLTreeModel();
						UMLTreeModel port = new UMLTreeModel(pm.getName());
						to1.addChild(port);
						port.setRefModel(pm);
						pm.getElementLabelModel().setTreeModel(port);
		                ProjectManager.getInstance().getModelBrowser().refresh(to1);
		                pm.getUMLDataModel().setId(child.getID());
		                pm.getElementLabelModel().setPortId(child.getID());
		                //ijs 110902
//		                pm.setLocation(p)
		                
		                
		                ProjectManager.getInstance().getModelBrowser().searchComponentDiagramIdModel(um.getUMLDataModel().getId());
		        		java.util.ArrayList al = ProjectManager.getInstance().getSearchModel();
		        		for(int i = 0; i<al.size();i++){
		        			UMLModel um1 = (UMLModel)al.get(i);
		        			if(um1!=um){
//		        				 if(um1 instanceof AtomicComponentModel){
//	            					ComponentModel cm = (ComponentModel)um1;
////	            					PortModel pm2 = new MethodInputPortModel(cm);
////	            				    pm2.setName(pm.getName());
//                					cm.createPort(pm, (UMLContainerModel)cm.getAcceptParentModel());
////	            					pm.getUMLDataModel().setId(pm.getID());
////	            					
////	            					pm.getElementLabelModel().setPortId(pm.getID());
//	            				}
//		        				 else	
		        				if(um1 instanceof ComponentModel){
		            					ComponentModel cm = (ComponentModel)um1;
		            					PortModel pm2 = new MethodInputPortModel(cm);
		            				    pm2.setName(pm.getName());
	                					cm.createPort(pm2, (UMLContainerModel)cm.getAcceptParentModel());
		            					pm2.getUMLDataModel().setId(pm.getID());
		            					
		            					pm2.getElementLabelModel().setPortId(pm.getID());
		            				}
		            				
		            					
		            				

		        				
		        			}
		        		}
		        		ProjectManager.getInstance().getSearchModel().clear();
					if(um instanceof AtomicComponentModel){
						AtomicComponentModel acm = (AtomicComponentModel)um;
						pm.temp_x = rect.x+expansion.left;
						pm.temp_y = rect.y+expansion.top;
						SyncManager.getInstance().ExeSync(acm, pm, null, 1);
					}
					
					
//	                ProjectManager.getInstance().getModelBrowser().refresh(to1);
	                
	                
//	                ProjectManager.getInstance().getModelBrowser().searchComponentDiagramIdModel(um.getUMLDataModel().getId());
//	        		java.util.ArrayList al = ProjectManager.getInstance().getSearchModel();
	        		
	        		
	        		

					return;
					
				}
				else if(child instanceof MethodOutputPortModel){
					 um = (ComponentModel)this.getPortContainerModel((PortModel)child);
					 if(um instanceof ComponentLibModel){
							return;
						}
						else if(um instanceof AtomicComponentModel){
							AtomicComponentModel atomicComponentModel1 = (AtomicComponentModel)um;
							if(atomicComponentModel1.getCoreUMLTreeModel()==null){
								um = atomicComponentModel1;
							}
							else{
//								AtomicComponentModel atomicComponentModel1 = (AtomicComponentModel)um;
								UMLTreeParentModel ut = (UMLTreeParentModel)atomicComponentModel1.getCoreUMLTreeModel();
								um = ((AtomicComponentModel)ut.getRefModel()).getCoreDiagramCmpModel();
//								um.get
//								for(int i=0;i<ut.getChildren().length;i++){
//									UMLTreeModel obj = (UMLTreeModel)ut.getChildren()[i];
//									
//									if(obj.getRefModel() instanceof N3EditorDiagramModel){
//										uc = (N3EditorDiagramModel)obj.getRefModel();
//										break;
//									}
//								}
								
							}
						}
					PortModel pm = new MethodOutputPortModel(um);
					if(um instanceof AtomicComponentModel){
						
						OPRoSServicePortDialog opd = new OPRoSServicePortDialog(ProjectManager.getInstance().window.getShell(),true,true,pm);
//						opd.setNew(true);
//						opd.setPm(pm);
						int i = opd.open();
						if(i!=Dialog.OK)
							return;
					}
					
	                um.createPort( pm, (UMLContainerModel)um.getAcceptParentModel());
	                
//					ijs 110902
					if(ProjectManager.getInstance().getUMLEditor()!=null && 
							ProjectManager.getInstance().getUMLEditor().getDiagram()==um.getAcceptParentModel()){
						//110905 S SDM 110905 - Y������ ��Ʈ ���⵵�� ����
						pm.temp_x = rect.x+expansion.left;
						pm.temp_y = rect.y+expansion.top;
						
						int cx = um.getLocation().x;
						int cy = um.getLocation().y;
						int cheight = um.getSize().height;
						int cwidth = um.getSize().width;
						
						System.out.println("tempx============>"+pm.temp_x);
						System.out.println("temp_y============>"+pm.temp_y);
						System.out.println("cx============>"+cx);
						System.out.println("cy============>"+cy);
						System.out.println("cheight============>"+cheight);
						System.out.println("cwidth============>"+cwidth);
						
						if(um.getRectangle().contains(new Point(pm.temp_x,pm.temp_y))){
							if(pm.temp_x<=cx+cwidth/2 && pm.temp_y<=cy+cheight/2){
								int nX = (cx+cwidth/2) - pm.temp_x;
								int nY = (cy+cheight/2) - pm.temp_y;
								
								if(nX > nY){
									pm.temp_x = cx-30;
								}
								else{
									pm.temp_y = cy-30;
								}
								
							}
							else if(pm.temp_x<=cx+cwidth/2 && pm.temp_y>cy+cheight/2){
								int nX = (cx+cwidth/2) - pm.temp_x;
								int nY = pm.temp_y - (cy+cheight/2);
								
								if(nX > nY){
									pm.temp_x = cx-30;
								}
								else{
									pm.temp_y = cy+cheight;
								}
							}
							else if(pm.temp_x>cx+cwidth/2 && pm.temp_y<=cy+cheight/2){
								int nX = pm.temp_x - (cx+cwidth/2);
								int nY = (cy+cheight/2) - pm.temp_y;
								
								if(nX > nY){
									pm.temp_x = cx+cwidth;
								}
								else{
									pm.temp_y = cy-30;
								}
							}
							else if(pm.temp_x>cx+cwidth/2 && pm.temp_y>cy+cheight/2){
								int nX = pm.temp_x - (cx+cwidth/2);
								int nY = pm.temp_y - (cy+cheight/2);
								
								if(nX > nY){
									pm.temp_x = cx+cwidth;
								}
								else{
									pm.temp_y = cy+cheight;
								}
							}
							
//							if(pm.temp_y<cy+cheight/2){
//								pm.temp_y = cy;
//							}
//							else if(pm.temp_y>cy+cm.getBoundRectangle().height/2){
//								pm.temp_y = cy+cheight;
//							}
							
						    pm.setLocation(new Point(pm.temp_x,pm.temp_y));
//						}
						}
						//110905 E SDM 110905 - Y������ ��Ʈ ���⵵�� ����
					}
	                
	                
	                UMLTreeParentModel to1 = (UMLTreeParentModel)um.getUMLTreeModel();
					UMLTreeModel port = new UMLTreeModel(pm.getName());
					to1.addChild(port);
					port.setRefModel(pm);
					 pm.getUMLDataModel().setId(child.getID());
		                pm.getElementLabelModel().setPortId(child.getID());
					pm.getElementLabelModel().setTreeModel(port);
					System.out.println("id=======================>"+child.getID());
	                ProjectManager.getInstance().getModelBrowser().refresh(to1);
	                ProjectManager.getInstance().getModelBrowser().searchComponentDiagramIdModel(um.getUMLDataModel().getId());
	        		java.util.ArrayList al = ProjectManager.getInstance().getSearchModel();
	        		System.out.println("dddd");
	        		for(int i = 0; i<al.size();i++){
	        			UMLModel um1 = (UMLModel)al.get(i);
	        			if(um1!=um){
	            				if(um1 instanceof ComponentModel){
	            					ComponentModel cm = (ComponentModel)um1;
	            					PortModel pm2 = new MethodOutputPortModel(cm);
                					cm.createPort(pm2, (UMLContainerModel)cm.getAcceptParentModel());
	            					pm2.getUMLDataModel().setId(pm.getID());
	            					pm2.getElementLabelModel().setPortId(pm.getID());
	            				}
	            					
	            				

	        				
	        			}
	        		}
	        		ProjectManager.getInstance().getSearchModel().clear();
	        		if(um instanceof AtomicComponentModel){
						AtomicComponentModel acm = (AtomicComponentModel)um;
						pm.temp_x = rect.x+expansion.left;
						pm.temp_y = rect.y+expansion.top;
						SyncManager.getInstance().ExeSync(acm, pm, null, 1);
					}

					return;
					
				}
				else if(child instanceof DataInputPortModel){
					 um = (ComponentModel)this.getPortContainerModel((PortModel)child);
					 if(um instanceof ComponentLibModel){
							return;
						}
						else if(um instanceof AtomicComponentModel){
							AtomicComponentModel atomicComponentModel1 = (AtomicComponentModel)um;
							if(atomicComponentModel1.getCoreUMLTreeModel()==null){
								um = atomicComponentModel1;
							}
							else{
//								AtomicComponentModel atomicComponentModel1 = (AtomicComponentModel)um;
								UMLTreeParentModel ut = (UMLTreeParentModel)atomicComponentModel1.getCoreUMLTreeModel();
								um = ((AtomicComponentModel)ut.getRefModel()).getCoreDiagramCmpModel();
//								um.get
//								for(int i=0;i<ut.getChildren().length;i++){
//									UMLTreeModel obj = (UMLTreeModel)ut.getChildren()[i];
//									
//									if(obj.getRefModel() instanceof N3EditorDiagramModel){
//										uc = (N3EditorDiagramModel)obj.getRefModel();
//										break;
//									}
//								}
								
							}
						}
					PortModel pm = new DataInputPortModel(um);
					if(um instanceof AtomicComponentModel){
						
						OPRoSDataPortDialog opd = new OPRoSDataPortDialog(ProjectManager.getInstance().window.getShell(),true,true,pm);
//						opd.setNew(true);
//						opd.setPm(pm);
						int i = opd.open();
						if(i!=Dialog.OK)
							return;
					}
					
	                um.createPort( pm, (UMLContainerModel)um.getAcceptParentModel());
	                
//					ijs 110902
					if(ProjectManager.getInstance().getUMLEditor()!=null && 
							ProjectManager.getInstance().getUMLEditor().getDiagram()==um.getAcceptParentModel()){
						//110905 S SDM 110905 - Y������ ��Ʈ ���⵵�� ����
						pm.temp_x = rect.x+expansion.left;
						pm.temp_y = rect.y+expansion.top;
						
						int cx = um.getLocation().x;
						int cy = um.getLocation().y;
						int cheight = um.getSize().height;
						int cwidth = um.getSize().width;
						
						System.out.println("tempx============>"+pm.temp_x);
						System.out.println("temp_y============>"+pm.temp_y);
						System.out.println("cx============>"+cx);
						System.out.println("cy============>"+cy);
						System.out.println("cheight============>"+cheight);
						System.out.println("cwidth============>"+cwidth);
						
						if(um.getRectangle().contains(new Point(pm.temp_x,pm.temp_y))){
							if(pm.temp_x<=cx+cwidth/2 && pm.temp_y<=cy+cheight/2){
								int nX = (cx+cwidth/2) - pm.temp_x;
								int nY = (cy+cheight/2) - pm.temp_y;
								
								if(nX > nY){
									pm.temp_x = cx-30;
								}
								else{
									pm.temp_y = cy-30;
								}
								
							}
							else if(pm.temp_x<=cx+cwidth/2 && pm.temp_y>cy+cheight/2){
								int nX = (cx+cwidth/2) - pm.temp_x;
								int nY = pm.temp_y - (cy+cheight/2);
								
								if(nX > nY){
									pm.temp_x = cx-30;
								}
								else{
									pm.temp_y = cy+cheight;
								}
							}
							else if(pm.temp_x>cx+cwidth/2 && pm.temp_y<=cy+cheight/2){
								int nX = pm.temp_x - (cx+cwidth/2);
								int nY = (cy+cheight/2) - pm.temp_y;
								
								if(nX > nY){
									pm.temp_x = cx+cwidth;
								}
								else{
									pm.temp_y = cy-30;
								}
							}
							else if(pm.temp_x>cx+cwidth/2 && pm.temp_y>cy+cheight/2){
								int nX = pm.temp_x - (cx+cwidth/2);
								int nY = pm.temp_y - (cy+cheight/2);
								
								if(nX > nY){
									pm.temp_x = cx+cwidth;
								}
								else{
									pm.temp_y = cy+cheight;
								}
							}
							
//							if(pm.temp_y<cy+cheight/2){
//								pm.temp_y = cy;
//							}
//							else if(pm.temp_y>cy+cm.getBoundRectangle().height/2){
//								pm.temp_y = cy+cheight;
//							}
							
						    pm.setLocation(new Point(pm.temp_x,pm.temp_y));
//						}
						}
						//110905 E SDM 110905 - Y������ ��Ʈ ���⵵�� ����
					}
	                
	                UMLTreeParentModel to1 = (UMLTreeParentModel)um.getUMLTreeModel();
					UMLTreeModel port =  new UMLTreeModel(pm.getName());
					to1.addChild(port);
					port.setRefModel(pm);
					 pm.getUMLDataModel().setId(child.getID());
		                pm.getElementLabelModel().setPortId(child.getID());
					pm.getElementLabelModel().setTreeModel(port);
	                ProjectManager.getInstance().getModelBrowser().refresh(to1);
	                ProjectManager.getInstance().getModelBrowser().searchComponentDiagramIdModel(um.getUMLDataModel().getId());
	        		java.util.ArrayList al = ProjectManager.getInstance().getSearchModel();
	        		System.out.println("dddd");
	        		for(int i = 0; i<al.size();i++){
	        			UMLModel um1 = (UMLModel)al.get(i);
	        			if(um1!=um){
	            				if(um1 instanceof ComponentModel){
	            					ComponentModel cm = (ComponentModel)um1;
	            					PortModel pm2 = new DataInputPortModel(cm);
                					cm.createPort(pm2, (UMLContainerModel)cm.getAcceptParentModel());
	            					pm2.getUMLDataModel().setId(pm.getID());
	            					pm2.getElementLabelModel().setPortId(pm.getID());
	            				}
	            					
	            				

	        				
	        			}
	        		}
	        		ProjectManager.getInstance().getSearchModel().clear();
	        		if(um instanceof AtomicComponentModel){
						AtomicComponentModel acm = (AtomicComponentModel)um;
						pm.temp_x = rect.x+expansion.left;
						pm.temp_y = rect.y+expansion.top;
						SyncManager.getInstance().ExeSync(acm, pm, null, 1);
					}

					return;
					
				}
				else if(child instanceof DataOutputPortModel){
					 um = (ComponentModel)this.getPortContainerModel((PortModel)child);
					 if(um instanceof ComponentLibModel){
							return;
						}
						else if(um instanceof AtomicComponentModel){
							AtomicComponentModel atomicComponentModel1 = (AtomicComponentModel)um;
							if(atomicComponentModel1.getCoreUMLTreeModel()==null){
								um = atomicComponentModel1;
							}
							else{
//								AtomicComponentModel atomicComponentModel1 = (AtomicComponentModel)um;
								UMLTreeParentModel ut = (UMLTreeParentModel)atomicComponentModel1.getCoreUMLTreeModel();
								um = ((AtomicComponentModel)ut.getRefModel()).getCoreDiagramCmpModel();
//								um.get
//								for(int i=0;i<ut.getChildren().length;i++){
//									UMLTreeModel obj = (UMLTreeModel)ut.getChildren()[i];
//									
//									if(obj.getRefModel() instanceof N3EditorDiagramModel){
//										uc = (N3EditorDiagramModel)obj.getRefModel();
//										break;
//									}
//								}
								
							}
						}
					PortModel pm = new DataOutputPortModel(um);
					
					
					if(um instanceof AtomicComponentModel){
						
						OPRoSDataPortDialog opd = new OPRoSDataPortDialog(ProjectManager.getInstance().window.getShell(),false,true,pm);
//						opd.setNew(true);
//						opd.setPm(pm);
						int i = opd.open();
						if(i!=Dialog.OK)
							return;
					}
	                um.createPort( pm, (UMLContainerModel)um.getAcceptParentModel());
//					ijs 110902
					if(ProjectManager.getInstance().getUMLEditor()!=null && 
							ProjectManager.getInstance().getUMLEditor().getDiagram()==um.getAcceptParentModel()){
						//110905 S SDM 110905 - Y������ ��Ʈ ���⵵�� ����
						pm.temp_x = rect.x+expansion.left;
						pm.temp_y = rect.y+expansion.top;
						
						int cx = um.getLocation().x;
						int cy = um.getLocation().y;
						int cheight = um.getSize().height;
						int cwidth = um.getSize().width;
						
						System.out.println("tempx============>"+pm.temp_x);
						System.out.println("temp_y============>"+pm.temp_y);
						System.out.println("cx============>"+cx);
						System.out.println("cy============>"+cy);
						System.out.println("cheight============>"+cheight);
						System.out.println("cwidth============>"+cwidth);
						
						if(um.getRectangle().contains(new Point(pm.temp_x,pm.temp_y))){
							if(pm.temp_x<=cx+cwidth/2 && pm.temp_y<=cy+cheight/2){
								int nX = (cx+cwidth/2) - pm.temp_x;
								int nY = (cy+cheight/2) - pm.temp_y;
								
								if(nX > nY){
									pm.temp_x = cx-30;
								}
								else{
									pm.temp_y = cy-30;
								}
								
							}
							else if(pm.temp_x<=cx+cwidth/2 && pm.temp_y>cy+cheight/2){
								int nX = (cx+cwidth/2) - pm.temp_x;
								int nY = pm.temp_y - (cy+cheight/2);
								
								if(nX > nY){
									pm.temp_x = cx-30;
								}
								else{
									pm.temp_y = cy+cheight;
								}
							}
							else if(pm.temp_x>cx+cwidth/2 && pm.temp_y<=cy+cheight/2){
								int nX = pm.temp_x - (cx+cwidth/2);
								int nY = (cy+cheight/2) - pm.temp_y;
								
								if(nX > nY){
									pm.temp_x = cx+cwidth;
								}
								else{
									pm.temp_y = cy-30;
								}
							}
							else if(pm.temp_x>cx+cwidth/2 && pm.temp_y>cy+cheight/2){
								int nX = pm.temp_x - (cx+cwidth/2);
								int nY = pm.temp_y - (cy+cheight/2);
								
								if(nX > nY){
									pm.temp_x = cx+cwidth;
								}
								else{
									pm.temp_y = cy+cheight;
								}
							}
							
//							if(pm.temp_y<cy+cheight/2){
//								pm.temp_y = cy;
//							}
//							else if(pm.temp_y>cy+cm.getBoundRectangle().height/2){
//								pm.temp_y = cy+cheight;
//							}
							
						    pm.setLocation(new Point(pm.temp_x,pm.temp_y));
//						}
						}
						//110905 E SDM 110905 - Y������ ��Ʈ ���⵵�� ����
					}
	                
	                UMLTreeParentModel to1 = (UMLTreeParentModel)um.getUMLTreeModel();
					UMLTreeModel port =  new UMLTreeModel(pm.getName());
					to1.addChild(port);
					port.setRefModel(pm);
					 pm.getUMLDataModel().setId(child.getID());
		                pm.getElementLabelModel().setPortId(child.getID());
					pm.getElementLabelModel().setTreeModel(port);
	                ProjectManager.getInstance().getModelBrowser().refresh(to1);
	                ProjectManager.getInstance().getModelBrowser().searchComponentDiagramIdModel(um.getUMLDataModel().getId());
	        		java.util.ArrayList al = ProjectManager.getInstance().getSearchModel();
	        		System.out.println("dddd");
	        		for(int i = 0; i<al.size();i++){
	        			UMLModel um1 = (UMLModel)al.get(i);
	        			if(um1!=um){
	            				if(um1 instanceof ComponentModel){
	            					ComponentModel cm = (ComponentModel)um1;
	            					PortModel pm2 = new DataOutputPortModel(cm);
                					cm.createPort(pm2, (UMLContainerModel)cm.getAcceptParentModel());
	            					pm2.getUMLDataModel().setId(pm.getID());
	            					pm2.getElementLabelModel().setPortId(pm.getID());
	            				}
	            					
	            				

	        				
	        			}
	        		}
	        		ProjectManager.getInstance().getSearchModel().clear();
	        		if(um instanceof AtomicComponentModel){
						AtomicComponentModel acm = (AtomicComponentModel)um;
						pm.temp_x = rect.x+expansion.left;
						pm.temp_y = rect.y+expansion.top;
						SyncManager.getInstance().ExeSync(acm, pm, null, 1);
					}

					return;
					
				}
				else if(child instanceof EventInputPortModel){
					 um = (ComponentModel)this.getPortContainerModel((PortModel)child);
					 if(um instanceof ComponentLibModel){
							return;
						}
						else if(um instanceof AtomicComponentModel){
							AtomicComponentModel atomicComponentModel1 = (AtomicComponentModel)um;
							if(atomicComponentModel1.getCoreUMLTreeModel()==null){
								um = atomicComponentModel1;
							}
							else{
//								AtomicComponentModel atomicComponentModel1 = (AtomicComponentModel)um;
								UMLTreeParentModel ut = (UMLTreeParentModel)atomicComponentModel1.getCoreUMLTreeModel();
								um = ((AtomicComponentModel)ut.getRefModel()).getCoreDiagramCmpModel();
//								um.get
//								for(int i=0;i<ut.getChildren().length;i++){
//									UMLTreeModel obj = (UMLTreeModel)ut.getChildren()[i];
//									
//									if(obj.getRefModel() instanceof N3EditorDiagramModel){
//										uc = (N3EditorDiagramModel)obj.getRefModel();
//										break;
//									}
//								}
								
							}
						}
					PortModel pm = new EventInputPortModel(um);	
					
					
					if(um instanceof AtomicComponentModel){
						
						OPRoSEventPortDialog opd = new OPRoSEventPortDialog(ProjectManager.getInstance().window.getShell(),true,true,pm);
//						opd.setNew(true);
//						opd.setPm(pm);
						int i = opd.open();
						if(i!=Dialog.OK)
							return;
						((AtomicComponentModel)um).addDataTypeReference(opd.getUsingDataTypeFileName());
					}
	                um.createPort( pm, (UMLContainerModel)um.getAcceptParentModel());
	                
//					ijs 110902
					if(ProjectManager.getInstance().getUMLEditor()!=null && 
							ProjectManager.getInstance().getUMLEditor().getDiagram()==um.getAcceptParentModel()){
						//110905 S SDM 110905 - Y������ ��Ʈ ���⵵�� ����
						pm.temp_x = rect.x+expansion.left;
						pm.temp_y = rect.y+expansion.top;
						
						int cx = um.getLocation().x;
						int cy = um.getLocation().y;
						int cheight = um.getSize().height;
						int cwidth = um.getSize().width;
						
						System.out.println("tempx============>"+pm.temp_x);
						System.out.println("temp_y============>"+pm.temp_y);
						System.out.println("cx============>"+cx);
						System.out.println("cy============>"+cy);
						System.out.println("cheight============>"+cheight);
						System.out.println("cwidth============>"+cwidth);
						
						if(um.getRectangle().contains(new Point(pm.temp_x,pm.temp_y))){
							if(pm.temp_x<=cx+cwidth/2 && pm.temp_y<=cy+cheight/2){
								int nX = (cx+cwidth/2) - pm.temp_x;
								int nY = (cy+cheight/2) - pm.temp_y;
								
								if(nX > nY){
									pm.temp_x = cx-30;
								}
								else{
									pm.temp_y = cy-30;
								}
								
							}
							else if(pm.temp_x<=cx+cwidth/2 && pm.temp_y>cy+cheight/2){
								int nX = (cx+cwidth/2) - pm.temp_x;
								int nY = pm.temp_y - (cy+cheight/2);
								
								if(nX > nY){
									pm.temp_x = cx-30;
								}
								else{
									pm.temp_y = cy+cheight;
								}
							}
							else if(pm.temp_x>cx+cwidth/2 && pm.temp_y<=cy+cheight/2){
								int nX = pm.temp_x - (cx+cwidth/2);
								int nY = (cy+cheight/2) - pm.temp_y;
								
								if(nX > nY){
									pm.temp_x = cx+cwidth;
								}
								else{
									pm.temp_y = cy-30;
								}
							}
							else if(pm.temp_x>cx+cwidth/2 && pm.temp_y>cy+cheight/2){
								int nX = pm.temp_x - (cx+cwidth/2);
								int nY = pm.temp_y - (cy+cheight/2);
								
								if(nX > nY){
									pm.temp_x = cx+cwidth;
								}
								else{
									pm.temp_y = cy+cheight;
								}
							}
							
//							if(pm.temp_y<cy+cheight/2){
//								pm.temp_y = cy;
//							}
//							else if(pm.temp_y>cy+cm.getBoundRectangle().height/2){
//								pm.temp_y = cy+cheight;
//							}
							
						    pm.setLocation(new Point(pm.temp_x,pm.temp_y));
//						}
						}
						//110905 E SDM 110905 - Y������ ��Ʈ ���⵵�� ����
					}
	                
	                UMLTreeParentModel to1 = (UMLTreeParentModel)um.getUMLTreeModel();
					UMLTreeModel port =  new UMLTreeModel(pm.getName());
					to1.addChild(port);
					port.setRefModel(pm);
					pm.getElementLabelModel().setTreeModel(port);
					 pm.getUMLDataModel().setId(child.getID());
		                pm.getElementLabelModel().setPortId(child.getID());
	                ProjectManager.getInstance().getModelBrowser().refresh(to1);
	                ProjectManager.getInstance().getModelBrowser().searchComponentDiagramIdModel(um.getUMLDataModel().getId());
	        		java.util.ArrayList al = ProjectManager.getInstance().getSearchModel();
	        		System.out.println("dddd");
	        		for(int i = 0; i<al.size();i++){
	        			UMLModel um1 = (UMLModel)al.get(i);
	        			if(um1!=um){
	            				if(um1 instanceof ComponentModel){
	            					ComponentModel cm = (ComponentModel)um1;
	            					PortModel pm2 = new EventInputPortModel(cm);
                					cm.createPort(pm2, (UMLContainerModel)cm.getAcceptParentModel());
	            					pm2.getUMLDataModel().setId(pm.getID());
	            					pm2.getElementLabelModel().setPortId(pm.getID());
	            				}
	            					
	            				

	        				
	        			}
	        		}
	        		ProjectManager.getInstance().getSearchModel().clear();
	        		if(um instanceof AtomicComponentModel){
						AtomicComponentModel acm = (AtomicComponentModel)um;
						pm.temp_x = rect.x+expansion.left;
						pm.temp_y = rect.y+expansion.top;
						SyncManager.getInstance().ExeSync(acm, pm, null, 1);
					}

					return;
					
				}
				else if(child instanceof EventOutputPortModel){
					 um = (ComponentModel)this.getPortContainerModel((PortModel)child);
					 if(um instanceof ComponentLibModel){
							return;
						}
						else if(um instanceof AtomicComponentModel){
							AtomicComponentModel atomicComponentModel1 = (AtomicComponentModel)um;
							if(atomicComponentModel1.getCoreUMLTreeModel()==null){
								um = atomicComponentModel1;
							}
							else{
//								AtomicComponentModel atomicComponentModel1 = (AtomicComponentModel)um;
								UMLTreeParentModel ut = (UMLTreeParentModel)atomicComponentModel1.getCoreUMLTreeModel();
								um = ((AtomicComponentModel)ut.getRefModel()).getCoreDiagramCmpModel();
//								um.get
//								for(int i=0;i<ut.getChildren().length;i++){
//									UMLTreeModel obj = (UMLTreeModel)ut.getChildren()[i];
//									
//									if(obj.getRefModel() instanceof N3EditorDiagramModel){
//										uc = (N3EditorDiagramModel)obj.getRefModel();
//										break;
//									}
//								}
								
							}
						}
					PortModel pm = new EventOutputPortModel(um);
					if(um instanceof AtomicComponentModel){
						
						OPRoSEventPortDialog opd = new OPRoSEventPortDialog(ProjectManager.getInstance().window.getShell(),false,true,pm);
//						opd.setNew(true);
//						opd.setPm(pm);
						int i = opd.open();
						if(i!=Dialog.OK)
							return;
						((AtomicComponentModel)um).addDataTypeReference(opd.getUsingDataTypeFileName());
					}
	                um.createPort( pm, (UMLContainerModel)um.getAcceptParentModel());
	                
//					ijs 110902
					if(ProjectManager.getInstance().getUMLEditor()!=null && 
							ProjectManager.getInstance().getUMLEditor().getDiagram()==um.getAcceptParentModel()){
						//110905 S SDM 110905 - Y������ ��Ʈ ���⵵�� ����
						pm.temp_x = rect.x+expansion.left;
						pm.temp_y = rect.y+expansion.top;
						
						int cx = um.getLocation().x;
						int cy = um.getLocation().y;
						int cheight = um.getSize().height;
						int cwidth = um.getSize().width;
						
						System.out.println("tempx============>"+pm.temp_x);
						System.out.println("temp_y============>"+pm.temp_y);
						System.out.println("cx============>"+cx);
						System.out.println("cy============>"+cy);
						System.out.println("cheight============>"+cheight);
						System.out.println("cwidth============>"+cwidth);
						
						if(um.getRectangle().contains(new Point(pm.temp_x,pm.temp_y))){
							if(pm.temp_x<=cx+cwidth/2 && pm.temp_y<=cy+cheight/2){
								int nX = (cx+cwidth/2) - pm.temp_x;
								int nY = (cy+cheight/2) - pm.temp_y;
								
								if(nX > nY){
									pm.temp_x = cx-30;
								}
								else{
									pm.temp_y = cy-30;
								}
								
							}
							else if(pm.temp_x<=cx+cwidth/2 && pm.temp_y>cy+cheight/2){
								int nX = (cx+cwidth/2) - pm.temp_x;
								int nY = pm.temp_y - (cy+cheight/2);
								
								if(nX > nY){
									pm.temp_x = cx-30;
								}
								else{
									pm.temp_y = cy+cheight;
								}
							}
							else if(pm.temp_x>cx+cwidth/2 && pm.temp_y<=cy+cheight/2){
								int nX = pm.temp_x - (cx+cwidth/2);
								int nY = (cy+cheight/2) - pm.temp_y;
								
								if(nX > nY){
									pm.temp_x = cx+cwidth;
								}
								else{
									pm.temp_y = cy-30;
								}
							}
							else if(pm.temp_x>cx+cwidth/2 && pm.temp_y>cy+cheight/2){
								int nX = pm.temp_x - (cx+cwidth/2);
								int nY = pm.temp_y - (cy+cheight/2);
								
								if(nX > nY){
									pm.temp_x = cx+cwidth;
								}
								else{
									pm.temp_y = cy+cheight;
								}
							}
							
//							if(pm.temp_y<cy+cheight/2){
//								pm.temp_y = cy;
//							}
//							else if(pm.temp_y>cy+cm.getBoundRectangle().height/2){
//								pm.temp_y = cy+cheight;
//							}
							
						    pm.setLocation(new Point(pm.temp_x,pm.temp_y));
//						}
						}
						//110905 E SDM 110905 - Y������ ��Ʈ ���⵵�� ����
					}
	                
	                UMLTreeParentModel to1 = (UMLTreeParentModel)um.getUMLTreeModel();
					UMLTreeModel port =  new UMLTreeModel(pm.getName());
					to1.addChild(port);
					port.setRefModel(pm);
					 pm.getUMLDataModel().setId(child.getID());
		                pm.getElementLabelModel().setPortId(child.getID());
					pm.getElementLabelModel().setTreeModel(port);
	                ProjectManager.getInstance().getModelBrowser().refresh(to1);
	                ProjectManager.getInstance().getModelBrowser().searchComponentDiagramIdModel(um.getUMLDataModel().getId());
	        		java.util.ArrayList al = ProjectManager.getInstance().getSearchModel();
	        		System.out.println("dddd");
	        		for(int i = 0; i<al.size();i++){
	        			UMLModel um1 = (UMLModel)al.get(i);
	        			if(um1!=um){
	            				if(um1 instanceof ComponentModel){
	            					ComponentModel cm = (ComponentModel)um1;
	            					PortModel pm2 = new EventOutputPortModel(cm);
                					cm.createPort(pm2, (UMLContainerModel)cm.getAcceptParentModel());
	            					pm2.getUMLDataModel().setId(pm.getID());
	            					pm2.getElementLabelModel().setPortId(pm.getID());
	            				}
	            					
	            				

	        				
	        			}
	        		}
	        		ProjectManager.getInstance().getSearchModel().clear();
	        		if(um instanceof AtomicComponentModel){
						AtomicComponentModel acm = (AtomicComponentModel)um;
						pm.temp_x = rect.x+expansion.left;
						pm.temp_y = rect.y+expansion.top;
						SyncManager.getInstance().ExeSync(acm, pm, null, 1);
					}

					return;
					
				}
				else if(child instanceof OPRoSExeEnvironmentOSElementModel){
					ComponentModel um1 = (ComponentModel)this.getPortContainerModel();
					if(um instanceof ComponentLibModel){
						return;
					}
//					PortModel pm = new EventOutputPortModel(um);
					if(um1 instanceof AtomicComponentModel){
						AtomicComponentModel atomicComponentModel1 = (AtomicComponentModel)um1;
						if(atomicComponentModel1.getCoreUMLTreeModel()==null){
							um1 = atomicComponentModel1;
						}
						else{
//							AtomicComponentModel atomicComponentModel1 = (AtomicComponentModel)um;
							UMLTreeParentModel ut = (UMLTreeParentModel)atomicComponentModel1.getCoreUMLTreeModel();
							um1 = ((AtomicComponentModel)ut.getRefModel()).getCoreDiagramCmpModel();
//							for(int i=0;i<ut.getChildren().length;i++){
//								UMLTreeModel obj = (UMLTreeModel)ut.getChildren()[i];
//								
//								if(obj.getRefModel() instanceof N3EditorDiagramModel){
//									uc = (N3EditorDiagramModel)obj.getRefModel();
//									break;
//								}
//							}
							
						}
						
						
						OPRoSExeEnvOSInputDialog opd = new OPRoSExeEnvOSInputDialog(ProjectManager.getInstance().window.getShell());
//						opd.setNew(true);
//						opd.setPm(pm);
						int i = opd.open();
						if(i==Dialog.OK){
							OPRoSExeEnvironmentOSElementModel os = new OPRoSExeEnvironmentOSElementModel();
							os.setOs(opd.getOSName());
							os.setOsVersion(opd.getOSVersion());
							OPRoSExeEnvironmentElementModel am = (OPRoSExeEnvironmentElementModel)um1.getClassModel().getUMLDataModel().getElementProperty("OPRoSExeEnvironmentElementModel");
							am.addChild(os);
							
						}
						else
							return;
					}
	               

					return;
					
				}
				else if(child instanceof OPRoSExeEnvironmentCPUElementModel){
					ComponentModel um1 = (ComponentModel)this.getPortContainerModel();
					if(um instanceof ComponentLibModel){
						return;
					}
//					PortModel pm = new EventOutputPortModel(um);
					if(um1 instanceof AtomicComponentModel){
						AtomicComponentModel atomicComponentModel1 = (AtomicComponentModel)um1;
						if(atomicComponentModel1.getCoreUMLTreeModel()==null){
							um1 = atomicComponentModel1;
						}
						else{
//							AtomicComponentModel atomicComponentModel1 = (AtomicComponentModel)um;
							UMLTreeParentModel ut = (UMLTreeParentModel)atomicComponentModel1.getCoreUMLTreeModel();
							um1 = (AtomicComponentModel)ut.getRefModel();
//							for(int i=0;i<ut.getChildren().length;i++){
//								UMLTreeModel obj = (UMLTreeModel)ut.getChildren()[i];
//								
//								if(obj.getRefModel() instanceof N3EditorDiagramModel){
//									uc = (N3EditorDiagramModel)obj.getRefModel();
//									break;
//								}
//							}
							
						}
						
						InputDialog inputDialog = new InputDialog(null,
								"Input CPU Name" , "Input CPU Name", "NoNameCPU", null);//PKY 08070101 S ����� ����
						inputDialog.open();
						int retCode = inputDialog.getReturnCode();
						if (retCode == 0) {
							String name = inputDialog.getValue();
							OPRoSExeEnvironmentCPUElementModel os = new OPRoSExeEnvironmentCPUElementModel();
							os.setCpu(name);
//							os.setOsVersion(opd.getOSVersion());
							OPRoSExeEnvironmentElementModel am = (OPRoSExeEnvironmentElementModel)um1.getClassModel().getUMLDataModel().getElementProperty("OPRoSExeEnvironmentElementModel");
							am.addChild(os);
						}
					}
	               

					return;
					
				}
				else if(child instanceof OPRoSPropertyElementModel){
					ComponentModel um1 = (ComponentModel)this.getPortContainerModel();
					if(um1 instanceof ComponentLibModel){
						return;
					}
					
//					PortModel pm = new EventOutputPortModel(um);
					if(um1 instanceof AtomicComponentModel){
						AtomicComponentModel atomicComponentModel1 = (AtomicComponentModel)um1;
//						if(atomicComponentModel1.getCoreUMLTreeModel()==null){
//							um1 = atomicComponentModel1;
//						}
//						else{
////							AtomicComponentModel atomicComponentModel1 = (AtomicComponentModel)um;
//							UMLTreeParentModel ut = (UMLTreeParentModel)atomicComponentModel1.getCoreUMLTreeModel();
//							um1 = (AtomicComponentModel)ut.getRefModel();
////							for(int i=0;i<ut.getChildren().length;i++){
////								UMLTreeModel obj = (UMLTreeModel)ut.getChildren()[i];
////								
////								if(obj.getRefModel() instanceof N3EditorDiagramModel){
////									uc = (N3EditorDiagramModel)obj.getRefModel();
////									break;
////								}
////							}
//							
//						}
						
						OPRoSPropertyInputDialog opd = new OPRoSPropertyInputDialog(ProjectManager.getInstance().window.getShell(),(AtomicComponentModel)um1);
//						opd.setNew(true);
//						opd.setPm(pm);
						int i = opd.open();
						if(i==Dialog.OK){
//							OPRoSPropertiesElementModel
//							OPRoSPropertyElementModel os = new OPRoSPropertyElementModel();
//							os.setPropertyName(opd.getName());
//							os.setDataType(opd.getType());
//							os.setDefaultValue(opd.getDefaultValue());
							
							DetailPropertyTableItem dp = new DetailPropertyTableItem();
							dp.desc = opd.getDefaultValue();
							dp.sTagType = opd.getType();
							dp.tapName = opd.getName();
							atomicComponentModel1.getTags().add(dp);
							atomicComponentModel1.setTags(atomicComponentModel1.getTags());
//							
//							os.setCpu(name);
//							os.setOsVersion(opd.getOSVersion());
//							OPRoSPropertiesElementModel am = (OPRoSPropertiesElementModel)um1.getClassModel().getUMLDataModel().getElementProperty("OPRoSPropertiesElementModel");
						    
//							am.addChild(os);
							
						}
						else
							return;
					}
	               

					return;
					
				}
				else if(child instanceof OPRoSDataTypeElementModel){
					ComponentModel um1 = (ComponentModel)this.getPortContainerModel();
					if(um1 instanceof ComponentLibModel){
						return;
					}
//					PortModel pm = new EventOutputPortModel(um);
					if(um1 instanceof AtomicComponentModel){
						AtomicComponentModel atomicComponentModel1 = (AtomicComponentModel)um1;
						if(atomicComponentModel1.getCoreUMLTreeModel()==null){
							um1 = atomicComponentModel1;
						}
						else{
//							AtomicComponentModel atomicComponentModel1 = (AtomicComponentModel)um;
							UMLTreeParentModel ut = (UMLTreeParentModel)atomicComponentModel1.getCoreUMLTreeModel();
							um1 = (AtomicComponentModel)ut.getRefModel();
//							for(int i=0;i<ut.getChildren().length;i++){
//								UMLTreeModel obj = (UMLTreeModel)ut.getChildren()[i];
//								
//								if(obj.getRefModel() instanceof N3EditorDiagramModel){
//									uc = (N3EditorDiagramModel)obj.getRefModel();
//									break;
//								}
//							}
							
						}
						OPRoSDataTypeInputDialog dlg = new OPRoSDataTypeInputDialog(ProjectManager.getInstance().window.getShell(),(AtomicComponentModel)um1);
////				opd.setNew(true);
////				opd.setPm(pm);
				int i = dlg.open();
				if(i==Dialog.OK){
					AtomicComponentModel acm = (AtomicComponentModel)um1;
					OPRoSDataTypeElementModel element = new OPRoSDataTypeElementModel();
					element.setDataTypeFileName(dlg.getDataTypeFileName());
					element.setDataTypeDoc(dlg.getDataTypeDoc());
//					servTypesModel.addChild(element);
					String str = element.getServiceTypeFileName();
//					acm.addOPRoSServiceTypeElementModel(element);
					OPRoSDataTypesElementModel ops = (OPRoSDataTypesElementModel)acm.getClassModel().getUMLDataModel().getElementProperty("OPRoSDataTypesElementModel");
					ops.addChild(element);
//					return;
					
					
//					dlg.serviceTypeList.add(element.getServiceTypeFileName());
//					dlg.map.put(element.getServiceTypeFileName(), element.getServiceTypeDoc());
				}
				return;
					
					}
					return;
		
					
				}
				else if(child instanceof OPRoSServiceTypeElementModel){
					ComponentModel um1 = (ComponentModel)this.getPortContainerModel();
					if(um instanceof ComponentLibModel){
						return;
					}
//					PortModel pm = new EventOutputPortModel(um);
					if(um1 instanceof AtomicComponentModel){
						
						AtomicComponentModel atomicComponentModel1 = (AtomicComponentModel)um1;
						if(atomicComponentModel1.getCoreUMLTreeModel()==null){
							um1 = atomicComponentModel1;
						}
						else{
//							AtomicComponentModel atomicComponentModel1 = (AtomicComponentModel)um;
							UMLTreeParentModel ut = (UMLTreeParentModel)atomicComponentModel1.getCoreUMLTreeModel();
							um1 = (AtomicComponentModel)ut.getRefModel();
//							for(int i=0;i<ut.getChildren().length;i++){
//								UMLTreeModel obj = (UMLTreeModel)ut.getChildren()[i];
//								
//								if(obj.getRefModel() instanceof N3EditorDiagramModel){
//									uc = (N3EditorDiagramModel)obj.getRefModel();
//									break;
//								}
//							}
							
						}
						
						OPRoSServiceTypeInputDialog dlg = new OPRoSServiceTypeInputDialog(ProjectManager.getInstance().window.getShell(),(AtomicComponentModel)um1);
////						opd.setNew(true);
////						opd.setPm(pm);
						int i = dlg.open();
						if(i==Dialog.OK){
							AtomicComponentModel acm = (AtomicComponentModel)um1;
							OPRoSServiceTypeElementModel element = new OPRoSServiceTypeElementModel();
							element.setServiceTypeFileName(dlg.getServiceTypeFileName());
							element.setServiceTypeDoc(dlg.getServiceTypeDoc());
//							servTypesModel.addChild(element);
//							String str = element.getServiceTypeFileName();
//							acm.addOPRoSServiceTypeElementModel(element);
							OPRoSServiceTypesElementModel ops = (OPRoSServiceTypesElementModel)acm.getClassModel().getUMLDataModel().getElementProperty("OPRoSServiceTypesElementModel");
							ops.addChild(element);
							
							
//							dlg.serviceTypeList.add(element.getServiceTypeFileName());
//							dlg.map.put(element.getServiceTypeFileName(), element.getServiceTypeDoc());
						}
						
							return;
					}
	               

					return;
					
				}
				
				
				
				child.setLocation(rect.getLocation());
				if (!rect.isEmpty())
					child.setSize(rect.getSize());
			}
			redo();
			ProjectManager.getInstance().setComponent(false);  // WJH 090805
		
//			WJH 100730 S ����Ʈ ��Ʈ �߰� 
			if(ProjectManager.getInstance().getUseBlock()){
				((AtomicComponentModel)child).setBlockComponent(true);//			 WJH 100909 S ���� ������Ʈ �߰�
				ProjectManager.getInstance().createBlockComponent(((AtomicComponentModel)child));
				ProjectManager.getInstance().setUseBlock(false);
			}
//			WJH 100730 E ����Ʈ ��Ʈ �߰�
		
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//20110725������ ����ȭ�� ���
	public void addPort(ComponentModel um, PortProfile pf){		
		try {
			if (true) {

				System.out.println("================Font Change Targeting=================");
				System.out.println("child type : "+child.getName());
				if(child instanceof ComponentModel){
					ProjectManager.getInstance().setComponent(true);
					System.out.println("component");					
				}
				else{
					ProjectManager.getInstance().setComponent(false);
					System.out.println("not component");
				}

				UMLContainerModel uc  = null;
				
				if(child instanceof MethodInputPortModel){
	//				(PortModel)child;
//					 um = (ComponentModel)this.getPortContainerModel((PortModel)child);
					if(um instanceof ComponentLibModel){
						return;
					}
					else if(um instanceof AtomicComponentModel){
						AtomicComponentModel atomicComponentModel1 = (AtomicComponentModel)um;
						if(atomicComponentModel1.getCoreUMLTreeModel()==null){
							um = atomicComponentModel1;
						}
						else{
	//						AtomicComponentModel atomicComponentModel1 = (AtomicComponentModel)um;
							UMLTreeParentModel ut = (UMLTreeParentModel)atomicComponentModel1.getCoreUMLTreeModel();
							um = ((AtomicComponentModel)ut.getRefModel()).getCoreDiagramCmpModel();
	//						um.get
	//						for(int i=0;i<ut.getChildren().length;i++){
	//							UMLTreeModel obj = (UMLTreeModel)ut.getChildren()[i];
	//							
	//							if(obj.getRefModel() instanceof N3EditorDiagramModel){
	//								uc = (N3EditorDiagramModel)obj.getRefModel();
	//								break;
	//							}
	//						}
							
						}
					}
					PortModel pm = new MethodInputPortModel(um);
					pm = pf.setPortModelData(pm);
					if(um instanceof AtomicComponentModel){
						
//						OPRoSServicePortDialog opd = new OPRoSServicePortDialog(ProjectManager.getInstance().window.getShell(),false,true,pm);
//	//					opd.setNew(true);
//	//					opd.setPm(pm);
//						int i = opd.open();
//						if(i!=Dialog.OK)
//							return;
						
						
						
					}
					
					if(um.getAcceptParentModel()!=null){
						um.createPort( pm, (UMLContainerModel)um.getAcceptParentModel());
					}
					
	//				else if(uc!=null){
	//					um.createPort( pm, uc);
	//				}
		                UMLTreeParentModel to1 = (UMLTreeParentModel)um.getUMLTreeModel();
						UMLTreeModel port = new UMLTreeModel(pm.getName());
						to1.addChild(port);
						port.setRefModel(pm);
						pm.getElementLabelModel().setTreeModel(port);
		                ProjectManager.getInstance().getModelBrowser().refresh(to1);
		                pm.getUMLDataModel().setId(child.getID());
		                pm.getElementLabelModel().setPortId(child.getID());
		                
		                
		                ProjectManager.getInstance().getModelBrowser().searchComponentDiagramIdModel(um.getUMLDataModel().getId());
		        		java.util.ArrayList al = ProjectManager.getInstance().getSearchModel();
		        		for(int i = 0; i<al.size();i++){
		        			UMLModel um1 = (UMLModel)al.get(i);
		        			if(um1!=um){
	//	        				 if(um1 instanceof AtomicComponentModel){
	//            					ComponentModel cm = (ComponentModel)um1;
	////            					PortModel pm2 = new MethodInputPortModel(cm);
	////            				    pm2.setName(pm.getName());
	//            					cm.createPort(pm, (UMLContainerModel)cm.getAcceptParentModel());
	////            					pm.getUMLDataModel().setId(pm.getID());
	////            					
	////            					pm.getElementLabelModel().setPortId(pm.getID());
	//            				}
	//	        				 else	
		        				if(um1 instanceof ComponentModel){
		            					ComponentModel cm = (ComponentModel)um1;
		            					PortModel pm2 = new MethodInputPortModel(cm);
		            				    pm2.setName(pm.getName());
	                					cm.createPort(pm2, (UMLContainerModel)cm.getAcceptParentModel());
		            					pm2.getUMLDataModel().setId(pm.getID());
		            					
		            					pm2.getElementLabelModel().setPortId(pm.getID());
		            				}
		            				
		            					
		            				
	
		        				
		        			}
		        		}
		        		ProjectManager.getInstance().getSearchModel().clear();
					if(um instanceof AtomicComponentModel){
						AtomicComponentModel acm = (AtomicComponentModel)um;
						SyncManager.getInstance().ExeSync(acm, pm, null, 1);
					}
					
					
	//                ProjectManager.getInstance().getModelBrowser().refresh(to1);
	                
	                
	//                ProjectManager.getInstance().getModelBrowser().searchComponentDiagramIdModel(um.getUMLDataModel().getId());
	//        		java.util.ArrayList al = ProjectManager.getInstance().getSearchModel();
	        		
	        		
	        		
	
					return;
					
				}
				else if(child instanceof MethodOutputPortModel){
//					 um = (ComponentModel)this.getPortContainerModel((PortModel)child);
					 if(um instanceof ComponentLibModel){
							return;
						}
						else if(um instanceof AtomicComponentModel){
							AtomicComponentModel atomicComponentModel1 = (AtomicComponentModel)um;
							if(atomicComponentModel1.getCoreUMLTreeModel()==null){
								um = atomicComponentModel1;
							}
							else{
	//							AtomicComponentModel atomicComponentModel1 = (AtomicComponentModel)um;
								UMLTreeParentModel ut = (UMLTreeParentModel)atomicComponentModel1.getCoreUMLTreeModel();
								um = ((AtomicComponentModel)ut.getRefModel()).getCoreDiagramCmpModel();
	//							um.get
	//							for(int i=0;i<ut.getChildren().length;i++){
	//								UMLTreeModel obj = (UMLTreeModel)ut.getChildren()[i];
	//								
	//								if(obj.getRefModel() instanceof N3EditorDiagramModel){
	//									uc = (N3EditorDiagramModel)obj.getRefModel();
	//									break;
	//								}
	//							}
								
							}
						}
					PortModel pm = new MethodOutputPortModel(um);
					pm = pf.setPortModelData(pm);
					if(um instanceof AtomicComponentModel){
						
//						OPRoSServicePortDialog opd = new OPRoSServicePortDialog(ProjectManager.getInstance().window.getShell(),true,true,pm);
//	//					opd.setNew(true);
//	//					opd.setPm(pm);
//						int i = opd.open();
//						if(i!=Dialog.OK)
//							return;
					}
					
	                um.createPort( pm, (UMLContainerModel)um.getAcceptParentModel());
	                UMLTreeParentModel to1 = (UMLTreeParentModel)um.getUMLTreeModel();
					UMLTreeModel port = new UMLTreeModel(pm.getName());
					to1.addChild(port);
					port.setRefModel(pm);
					 pm.getUMLDataModel().setId(child.getID());
		                pm.getElementLabelModel().setPortId(child.getID());
					pm.getElementLabelModel().setTreeModel(port);
					System.out.println("id=======================>"+child.getID());
	                ProjectManager.getInstance().getModelBrowser().refresh(to1);
	                ProjectManager.getInstance().getModelBrowser().searchComponentDiagramIdModel(um.getUMLDataModel().getId());
	        		java.util.ArrayList al = ProjectManager.getInstance().getSearchModel();
	        		System.out.println("dddd");
	        		for(int i = 0; i<al.size();i++){
	        			UMLModel um1 = (UMLModel)al.get(i);
	        			if(um1!=um){
	            				if(um1 instanceof ComponentModel){
	            					ComponentModel cm = (ComponentModel)um1;
	            					PortModel pm2 = new MethodOutputPortModel(cm);
	            					cm.createPort(pm2, (UMLContainerModel)cm.getAcceptParentModel());
	            					pm2.getUMLDataModel().setId(pm.getID());
	            					pm2.getElementLabelModel().setPortId(pm.getID());
	            				}
	            					
	            				
	
	        				
	        			}
	        		}
	        		ProjectManager.getInstance().getSearchModel().clear();
	        		if(um instanceof AtomicComponentModel){
						AtomicComponentModel acm = (AtomicComponentModel)um;
						SyncManager.getInstance().ExeSync(acm, pm, null, 1);
					}
	
					return;
					
				}
				else if(child instanceof DataInputPortModel){
//					 um = (ComponentModel)this.getPortContainerModel((PortModel)child);
					 if(um instanceof ComponentLibModel){
							return;
						}
						else if(um instanceof AtomicComponentModel){
							AtomicComponentModel atomicComponentModel1 = (AtomicComponentModel)um;
							if(atomicComponentModel1.getCoreUMLTreeModel()==null){
								um = atomicComponentModel1;
							}
							else{
	//							AtomicComponentModel atomicComponentModel1 = (AtomicComponentModel)um;
								UMLTreeParentModel ut = (UMLTreeParentModel)atomicComponentModel1.getCoreUMLTreeModel();
								um = ((AtomicComponentModel)ut.getRefModel()).getCoreDiagramCmpModel();
	//							um.get
	//							for(int i=0;i<ut.getChildren().length;i++){
	//								UMLTreeModel obj = (UMLTreeModel)ut.getChildren()[i];
	//								
	//								if(obj.getRefModel() instanceof N3EditorDiagramModel){
	//									uc = (N3EditorDiagramModel)obj.getRefModel();
	//									break;
	//								}
	//							}
								
							}
						}
					PortModel pm = new DataInputPortModel(um);
					pm = pf.setPortModelData(pm);
					if(um instanceof AtomicComponentModel){
						
//						OPRoSDataPortDialog opd = new OPRoSDataPortDialog(ProjectManager.getInstance().window.getShell(),true,true,pm);
//	//					opd.setNew(true);
//	//					opd.setPm(pm);
//						int i = opd.open();
//						if(i!=Dialog.OK)
//							return;
					}
					
	                um.createPort( pm, (UMLContainerModel)um.getAcceptParentModel());
	                UMLTreeParentModel to1 = (UMLTreeParentModel)um.getUMLTreeModel();
					UMLTreeModel port =  new UMLTreeModel(pm.getName());
					to1.addChild(port);
					port.setRefModel(pm);
					 pm.getUMLDataModel().setId(child.getID());
		                pm.getElementLabelModel().setPortId(child.getID());
					pm.getElementLabelModel().setTreeModel(port);
	                ProjectManager.getInstance().getModelBrowser().refresh(to1);
	                ProjectManager.getInstance().getModelBrowser().searchComponentDiagramIdModel(um.getUMLDataModel().getId());
	        		java.util.ArrayList al = ProjectManager.getInstance().getSearchModel();
	        		System.out.println("dddd");
	        		for(int i = 0; i<al.size();i++){
	        			UMLModel um1 = (UMLModel)al.get(i);
	        			if(um1!=um){
	            				if(um1 instanceof ComponentModel){
	            					ComponentModel cm = (ComponentModel)um1;
	            					PortModel pm2 = new DataInputPortModel(cm);
	            					cm.createPort(pm2, (UMLContainerModel)cm.getAcceptParentModel());
	            					pm2.getUMLDataModel().setId(pm.getID());
	            					pm2.getElementLabelModel().setPortId(pm.getID());
	            				}
	            					
	            				
	
	        				
	        			}
	        		}
	        		ProjectManager.getInstance().getSearchModel().clear();
	        		if(um instanceof AtomicComponentModel){
						AtomicComponentModel acm = (AtomicComponentModel)um;
						SyncManager.getInstance().ExeSync(acm, pm, null, 1);
					}
	
					return;
					
				}
				else if(child instanceof DataOutputPortModel){
//					 um = (ComponentModel)this.getPortContainerModel((PortModel)child);
					 if(um instanceof ComponentLibModel){
							return;
						}
						else if(um instanceof AtomicComponentModel){
							AtomicComponentModel atomicComponentModel1 = (AtomicComponentModel)um;
							if(atomicComponentModel1.getCoreUMLTreeModel()==null){
								um = atomicComponentModel1;
							}
							else{
	//							AtomicComponentModel atomicComponentModel1 = (AtomicComponentModel)um;
								UMLTreeParentModel ut = (UMLTreeParentModel)atomicComponentModel1.getCoreUMLTreeModel();
								um = ((AtomicComponentModel)ut.getRefModel()).getCoreDiagramCmpModel();
	//							um.get
	//							for(int i=0;i<ut.getChildren().length;i++){
	//								UMLTreeModel obj = (UMLTreeModel)ut.getChildren()[i];
	//								
	//								if(obj.getRefModel() instanceof N3EditorDiagramModel){
	//									uc = (N3EditorDiagramModel)obj.getRefModel();
	//									break;
	//								}
	//							}
								
							}
						}
					PortModel pm = new DataOutputPortModel(um);
					pm = pf.setPortModelData(pm);
					
					if(um instanceof AtomicComponentModel){
						
//						OPRoSDataPortDialog opd = new OPRoSDataPortDialog(ProjectManager.getInstance().window.getShell(),false,true,pm);
//	//					opd.setNew(true);
//	//					opd.setPm(pm);
//						int i = opd.open();
//						if(i!=Dialog.OK)
//							return;
					}
	                um.createPort( pm, (UMLContainerModel)um.getAcceptParentModel());
	                UMLTreeParentModel to1 = (UMLTreeParentModel)um.getUMLTreeModel();
					UMLTreeModel port =  new UMLTreeModel(pm.getName());
					to1.addChild(port);
					port.setRefModel(pm);
					 pm.getUMLDataModel().setId(child.getID());
		                pm.getElementLabelModel().setPortId(child.getID());
					pm.getElementLabelModel().setTreeModel(port);
	                ProjectManager.getInstance().getModelBrowser().refresh(to1);
	                ProjectManager.getInstance().getModelBrowser().searchComponentDiagramIdModel(um.getUMLDataModel().getId());
	        		java.util.ArrayList al = ProjectManager.getInstance().getSearchModel();
	        		System.out.println("dddd");
	        		for(int i = 0; i<al.size();i++){
	        			UMLModel um1 = (UMLModel)al.get(i);
	        			if(um1!=um){
	            				if(um1 instanceof ComponentModel){
	            					ComponentModel cm = (ComponentModel)um1;
	            					PortModel pm2 = new DataOutputPortModel(cm);
	            					cm.createPort(pm2, (UMLContainerModel)cm.getAcceptParentModel());
	            					pm2.getUMLDataModel().setId(pm.getID());
	            					pm2.getElementLabelModel().setPortId(pm.getID());
	            				}
	            					
	            				
	
	        				
	        			}
	        		}
	        		ProjectManager.getInstance().getSearchModel().clear();
	        		if(um instanceof AtomicComponentModel){
						AtomicComponentModel acm = (AtomicComponentModel)um;
						SyncManager.getInstance().ExeSync(acm, pm, null, 1);
					}
	
					return;
					
				}
				else if(child instanceof EventInputPortModel){
//					 um = (ComponentModel)this.getPortContainerModel((PortModel)child);
					 if(um instanceof ComponentLibModel){
							return;
						}
						else if(um instanceof AtomicComponentModel){
							AtomicComponentModel atomicComponentModel1 = (AtomicComponentModel)um;
							if(atomicComponentModel1.getCoreUMLTreeModel()==null){
								um = atomicComponentModel1;
							}
							else{
	//							AtomicComponentModel atomicComponentModel1 = (AtomicComponentModel)um;
								UMLTreeParentModel ut = (UMLTreeParentModel)atomicComponentModel1.getCoreUMLTreeModel();
								um = ((AtomicComponentModel)ut.getRefModel()).getCoreDiagramCmpModel();
	//							um.get
	//							for(int i=0;i<ut.getChildren().length;i++){
	//								UMLTreeModel obj = (UMLTreeModel)ut.getChildren()[i];
	//								
	//								if(obj.getRefModel() instanceof N3EditorDiagramModel){
	//									uc = (N3EditorDiagramModel)obj.getRefModel();
	//									break;
	//								}
	//							}
								
							}
						}
					PortModel pm = new EventInputPortModel(um);	
					pm = pf.setPortModelData(pm);
					
					if(um instanceof AtomicComponentModel){
						
//						OPRoSEventPortDialog opd = new OPRoSEventPortDialog(ProjectManager.getInstance().window.getShell(),true,true,pm);
//	//					opd.setNew(true);
//	//					opd.setPm(pm);
//						int i = opd.open();
//						if(i!=Dialog.OK)
//							return;
//						((AtomicComponentModel)um).addDataTypeReference(opd.getUsingDataTypeFileName());
						((AtomicComponentModel)um).addDataTypeReference(null);
					}
	                um.createPort( pm, (UMLContainerModel)um.getAcceptParentModel());
	                UMLTreeParentModel to1 = (UMLTreeParentModel)um.getUMLTreeModel();
					UMLTreeModel port =  new UMLTreeModel(pm.getName());
					to1.addChild(port);
					port.setRefModel(pm);
					pm.getElementLabelModel().setTreeModel(port);
					 pm.getUMLDataModel().setId(child.getID());
		                pm.getElementLabelModel().setPortId(child.getID());
	                ProjectManager.getInstance().getModelBrowser().refresh(to1);
	                ProjectManager.getInstance().getModelBrowser().searchComponentDiagramIdModel(um.getUMLDataModel().getId());
	        		java.util.ArrayList al = ProjectManager.getInstance().getSearchModel();
	        		System.out.println("dddd");
	        		for(int i = 0; i<al.size();i++){
	        			UMLModel um1 = (UMLModel)al.get(i);
	        			if(um1!=um){
	            				if(um1 instanceof ComponentModel){
	            					ComponentModel cm = (ComponentModel)um1;
	            					PortModel pm2 = new EventInputPortModel(cm);
	            					cm.createPort(pm2, (UMLContainerModel)cm.getAcceptParentModel());
	            					pm2.getUMLDataModel().setId(pm.getID());
	            					pm2.getElementLabelModel().setPortId(pm.getID());
	            				}
	            					
	            				
	
	        				
	        			}
	        		}
	        		ProjectManager.getInstance().getSearchModel().clear();
	        		if(um instanceof AtomicComponentModel){
						AtomicComponentModel acm = (AtomicComponentModel)um;
						SyncManager.getInstance().ExeSync(acm, pm, null, 1);
					}
	
					return;
					
				}
				else if(child instanceof EventOutputPortModel){
//					 um = (ComponentModel)this.getPortContainerModel((PortModel)child);
					 if(um instanceof ComponentLibModel){
							return;
						}
						else if(um instanceof AtomicComponentModel){
							AtomicComponentModel atomicComponentModel1 = (AtomicComponentModel)um;
							if(atomicComponentModel1.getCoreUMLTreeModel()==null){
								um = atomicComponentModel1;
							}
							else{
	//							AtomicComponentModel atomicComponentModel1 = (AtomicComponentModel)um;
								UMLTreeParentModel ut = (UMLTreeParentModel)atomicComponentModel1.getCoreUMLTreeModel();
								um = ((AtomicComponentModel)ut.getRefModel()).getCoreDiagramCmpModel();
	//							um.get
	//							for(int i=0;i<ut.getChildren().length;i++){
	//								UMLTreeModel obj = (UMLTreeModel)ut.getChildren()[i];
	//								
	//								if(obj.getRefModel() instanceof N3EditorDiagramModel){
	//									uc = (N3EditorDiagramModel)obj.getRefModel();
	//									break;
	//								}
	//							}
								
							}
						}
					PortModel pm = new EventOutputPortModel(um);
					
					pm = pf.setPortModelData(pm);
					
					if(um instanceof AtomicComponentModel){
						
						((AtomicComponentModel)um).addDataTypeReference(null);
					}
	                um.createPort( pm, (UMLContainerModel)um.getAcceptParentModel());
	                UMLTreeParentModel to1 = (UMLTreeParentModel)um.getUMLTreeModel();
					UMLTreeModel port =  new UMLTreeModel(pm.getName());
					to1.addChild(port);
					port.setRefModel(pm);
					 pm.getUMLDataModel().setId(child.getID());
		                pm.getElementLabelModel().setPortId(child.getID());
					pm.getElementLabelModel().setTreeModel(port);
	                ProjectManager.getInstance().getModelBrowser().refresh(to1);
	                ProjectManager.getInstance().getModelBrowser().searchComponentDiagramIdModel(um.getUMLDataModel().getId());
	        		java.util.ArrayList al = ProjectManager.getInstance().getSearchModel();
	        		System.out.println("dddd");
	        		for(int i = 0; i<al.size();i++){
	        			UMLModel um1 = (UMLModel)al.get(i);
	        			if(um1!=um){
	            				if(um1 instanceof ComponentModel){
	            					ComponentModel cm = (ComponentModel)um1;
	            					PortModel pm2 = new EventOutputPortModel(cm);
	            					cm.createPort(pm2, (UMLContainerModel)cm.getAcceptParentModel());
	            					pm2.getUMLDataModel().setId(pm.getID());
	            					pm2.getElementLabelModel().setPortId(pm.getID());
	            				}
	            					
	            				
	
	        				
	        			}
	        		}
	        		ProjectManager.getInstance().getSearchModel().clear();
	        		if(um instanceof AtomicComponentModel){
						AtomicComponentModel acm = (AtomicComponentModel)um;
						SyncManager.getInstance().ExeSync(acm, pm, null, 1);
					}
	
					return;
					
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}


	
	public PortContainerModel getPortContainerModel(PortModel pm){
//		pm.setda
		if(parent instanceof N3EditorDiagramModel){
			N3EditorDiagramModel nd = (N3EditorDiagramModel)parent;
			for(int i=1;i<nd.getChildren().size();i++){
				Object obj = nd.getChildren().get(i);
				if(obj instanceof ComponentModel){
					ComponentModel cm = (ComponentModel)obj;
					Rectangle rc = new Rectangle(cm.getLocation().x,cm.getLocation().y
							,cm.getSize().width,cm.getSize().height);
					if(rc.contains(rect.x, rect.y)){
						System.out.println("================");
						return cm;
					}
					
				}
			}
			Object obj = nd.getChildren().get(0);
			if(obj instanceof ComponentModel){
				ComponentModel cm = (ComponentModel)obj;
				Rectangle rc = new Rectangle(cm.getLocation().x,cm.getLocation().y
						,cm.getSize().width,cm.getSize().height);
				if(rc.contains(rect.x, rect.y)){
					System.out.println("================");
					return cm;
				}
				
			}
			
		}
		return null;
	}
	
	
	public PortContainerModel getPortContainerModel(){
		if(parent instanceof N3EditorDiagramModel){
			N3EditorDiagramModel nd = (N3EditorDiagramModel)parent;
			for(int i=1;i<nd.getChildren().size();i++){
				Object obj = nd.getChildren().get(i);
				if(obj instanceof ComponentModel){
					ComponentModel cm = (ComponentModel)obj;
					Rectangle rc = new Rectangle(cm.getLocation().x,cm.getLocation().y
							,cm.getSize().width,cm.getSize().height);
					if(rc.contains(rect.x, rect.y)){
						System.out.println("================");
						return cm;
					}
					
				}
			}
			Object obj = nd.getChildren().get(0);
			if(obj instanceof ComponentModel){
				ComponentModel cm = (ComponentModel)obj;
				Rectangle rc = new Rectangle(cm.getLocation().x,cm.getLocation().y
						,cm.getSize().width,cm.getSize().height);
				if(rc.contains(rect.x, rect.y)){
					System.out.println("================");
					return cm;
				}
				
			}
			
		}
		return null;
	}
	

	private Insets getInsets() {
		//		if (child instanceof LED || child instanceof Circuit)
		//			return new Insets(2, 0, 2, 0);
		return new Insets();
	}

	public void redo() {
		try {

			UMLTreeModel treeModel = null;
			int diagramType = -1;
			//2008041608PKY S
			//PKY 08090807 S 
			
			//PKY 08061801 S �߰��Ϸ��� ���̾�׷��� �ش� ��ü�� ������ �߰����ϵ��� ����

//			ijs 091126
//			if(parent instanceof N3EditorDiagramModel )
//				for(int i = 0; i < parent.getChildren().size(); i++){
//					if(parent.getChildren().get(i) instanceof UMLModel){
//						UMLModel umlModel = (UMLModel)parent.getChildren().get(i);
//						if(umlModel.getUMLTreeModel()!=null && child.getUMLTreeModel()!=null)
//							if(umlModel.getUMLTreeModel() == child.getUMLTreeModel() ){
////								ijs 091126
//								return;
//							}
//					}
//				}

			//PKY 08090807 E 

			//PKY 08061801 E �߰��Ϸ��� ���̾�׷��� �ش� ��ü�� ������ �߰����ϵ��� ����

			
			if(parent instanceof N3EditorDiagramModel){
				N3EditorDiagramModel nd = (N3EditorDiagramModel)parent;
				diagramType = nd.getDiagramType();
				

			}
			if (child.getUMLTreeModel() == null) {
				if (parent.getParentModel() != null) {
					//2008042102PKY S Group�� �����Ұ�� ���� �߻�
				
						treeModel = parent.getParentModel().getUMLTreeModel().getParent();
					
					//2008042102PKY E Group�� �����Ұ�� ���� �߻�
				}
				else {
					treeModel = parent.getUMLTreeModel().getParent();
				}
			}
			//				FinalBoundryModel
			if (treeModel instanceof UMLTreeParentModel && child.getUMLTreeModel() == null &&
					!(child instanceof UMLNoteModel) ) {
				if (child.getName().trim().equals("")) {
					if(child instanceof AtomicComponentModel){
						
						if(!(treeModel instanceof RootCmpEdtTreeModel)){
//							String pp = ProjectManager.getInstance().getProjectPath();
//							if(pp==null || "".equals(pp)){
//								MessageBox dialog = new MessageBox(ProjectManager.getInstance().window.getShell(),SWT.ICON_WARNING);
//								dialog.setText("Message");
//								dialog.setMessage("Project Folder is not Exist");
//								dialog.open();
//								return;
//							}
//							InputDialog inputDialog = new InputDialog(null,
//									"New OPRos Component",  "Component Name", "", null);//PKY 08070101 S ����� ����
//							
//							
//							inputDialog.open();
//							int retCode = inputDialog.getReturnCode()			
							
							
							CInputDialog cInputDialog = new CInputDialog(ProjectManager.getInstance().window.getShell());
							cInputDialog.open();
							int retCode = cInputDialog.getReturnCode();
							if (retCode == 0) {
								String name = cInputDialog.getFileName();
//								if(n)
								//KDI 080908 0002 s	//111025 SDM - ����� �ߺ�üũ �ȵƴ� �κ� ����
								boolean isOverlapping = ProjectManager.getInstance().isOverlapping((UMLTreeParentModel)treeModel, 29, name);
								if(isOverlapping) {
									MessageBox dialog = new MessageBox(ProjectManager.getInstance().window.getShell(),SWT.ICON_WARNING);
									dialog.setText("Message");
									dialog.setMessage(N3Messages.DIALOG_SAME_NAME_CONFLICT_MESSAGE);
									dialog.open();
									return ;
								}
								
								IWorkspace workspace = ResourcesPlugin.getWorkspace();//��ũ�����̽� �ڵ�
								IWorkspaceRoot root = workspace.getRoot();//��ũ�����̽� ��Ʈ�ڵ�
								//		root.getRawLocation()
								
								//110825 SDM S ������Ʈ �����Ϳ� ������Ʈ ������Ʈ�� ���� ��� �˸� �� ������Ʈ�� ���� �ϸ� ������ ������Ʈ ������ ����ȭ
								int nSelect = -1;
								final IProject newProjectHandle = root.getProject(name); //������Ʈ �ڵ��� �����ͺ���.

								if(newProjectHandle.exists()){
									MessageBox dialog = new MessageBox(ProjectManager.getInstance().window.getShell(),SWT.OK|SWT.CANCEL|SWT.ICON_QUESTION);
									dialog.setText("Message");
									dialog.setMessage("Same project exists to Component editor. Would you synchronize after creation?");
									nSelect = dialog.open();
									if(nSelect == SWT.CANCEL){
										return ;
									}
									
//									MessageBox dialog = new MessageBox(ProjectManager.getInstance().window.getShell(),SWT.ICON_WARNING);
//									dialog.setText("Message");
//									dialog.setMessage("The same component in Project Explorer folder exists");
//									dialog.open();
//									return ;
								}
								//110825 SDM E ������Ʈ �����Ϳ� ������Ʈ ������Ʈ�� ���� ��� �˸� �� ������Ʈ�� ���� �ϸ� ������ ������Ʈ ������ ����ȭ
								
								child.lang = cInputDialog.getComboIndex();
								StrcuturedPackageTreeLibModel to1 = new StrcuturedPackageTreeLibModel(name);
								child.setStereotype("atomic");
								((UMLTreeParentModel)to1).setRefModel(child);
								child.setTreeModel(to1);
								child.setName(name);
								to1.setParent((UMLTreeParentModel)treeModel);
								((UMLTreeParentModel)treeModel).addChild(to1);
								ProjectManager.getInstance().getModelBrowser().refresh(treeModel);
								ProjectManager.getInstance().getModelBrowser().expend(to1);
								UMLTreeParentModel treeModel1 = ProjectManager.getInstance().getModelBrowser().getRcet();
//								boolean isApp = false;
//								UMLTreeParentModel app = null;
//								for(int i=0;i<treeModel1.getChildren().length;i++){
//									UMLTreeParentModel up =	(UMLTreeParentModel)treeModel1.getChildren()[i];
//									if(up.getName().equals(treeModel.getName())){
////										isApp = true;
//										app = up;
//										break;
//									}
//								}
//								
//								if(app==null){
//									FinalPackageModel finalPackageModel = new FinalPackageModel();
//									app = new PackageTreeModel(treeModel.getName());
//									app.setRefModel(finalPackageModel);
//									finalPackageModel.setName(treeModel.getName());
//									finalPackageModel.setTreeModel(app);
//									app.setParent(treeModel1);
//									treeModel1.addChild(app);
//								}
//								isOverlapping = ProjectManager.getInstance().isOverlapping((UMLTreeParentModel)treeModel, 0, name);
								
								
								ProjectManager.getInstance().addUMLModel(name, (UMLTreeParentModel)treeModel1, child, 1000,diagramType);
								ProjectManager.getInstance().getModelBrowser().refresh(treeModel1);
								
								//110825 SDM S ������ ������Ʈ ����ȭ (������ ������Ʈ PATH ����)
								if(nSelect == SWT.OK){
									if(child instanceof AtomicComponentModel){
										AtomicComponentModel am = ((AtomicComponentModel)child).getCoreDiagramCmpModel();
										String strCompPath = root.getLocation().toString() + newProjectHandle.getFullPath().toFile() + newProjectHandle.getFullPath().toFile();
										File fPath = new File(strCompPath);
										am.setFile(fPath);
									}
									
								}
								//110825 SDM E ������ ������Ʈ ����ȭ
							}
							else
								return;
							
						}
//						else
//						ProjectManager.getInstance().addUMLModel(null, (UMLTreeParentModel)treeModel, child, -1,100);
						else if(!(treeModel instanceof RootCmpEdtTreeModel)){
							
						}
						
					}
					else
					ProjectManager.getInstance().addUMLModel(null, (UMLTreeParentModel)treeModel, child, -1,diagramType);
				}
				else {
					String name = child.getName();
					ProjectManager.getInstance().addUMLModel(name, (UMLTreeParentModel)treeModel, child, -1,diagramType);
				}
				isTreeAdd = true;
			}
		
			//PKY 08081801 E ������, ���� �� �ش� ���̾�׷��� ��ü�� ������� �ش� ��ü ������� �����ϵ��� ����

			//PKY 08060201 S �÷����� �� �����Ұ�� ����ȵǴ¹���

			if(child.getUMLDataModel().getElementProperty("ID_COLOR")!=null){
				Color obj=(Color)child.getUMLDataModel().getElementProperty("ID_COLOR");
				child.setBackGroundColor(obj);
			}
			//PKY 08060201 E �÷����� �� �����Ұ�� ����ȵǴ¹���
			//PKY 08072401 S �ٿ����,�׷�,swimlane ���� �� ��ü �ڷ� ������ ����
			if(child instanceof FinalBoundryModel||child instanceof HPartitionModel){
				parent.addChild(child, 0);
			}else{
				parent.addChild(child, index);
			}
			//PKY 08072401 E �ٿ����,�׷�,swimlane ���� �� ��ü �ڷ� ������ ����

			//���
			ProjectManager.getInstance().setCopyTreeModel(treeModel);
			if (child instanceof PortContainerModel) {
				PortContainerModel ipc = (PortContainerModel)child;
				ipc.createPort(null, parent);
			}
			if (child instanceof TextAttachModel) {
				TextAttachModel ipc = (TextAttachModel)child;
				ipc.addTextAttachParentDiagram(parent, null);
			}
			if (child.getSourceConnections().size() > 0) {
				System.out.println("dddd");
				for (int i = 0; i < child.getSourceConnections().size(); i++) {
					LineModel li = (LineModel)child.getSourceConnections().get(i);
					li.cloneLineModel(parent);
					li.setDetailProp(li.getDetailProp());
				}
			}
			//		if(child instanceof InitialModel){
			//			InitialModel em = (InitialModel)child;
			//			parent.addChild(em.getElementLabelModel());
			//		}
			//		if(child instanceof FinalModel){
			//			FinalModel em = (FinalModel)child;
			//			parent.addChild(em.getElementLabelModel());
			//		}
			//		if(child instanceof FlowFinalModel){
			//			FlowFinalModel em = (FlowFinalModel)child;
			//			parent.addChild(em.getElementLabelModel());
			//
			//		}
			//		if(child instanceof SynchModel){
			//			SynchModel em = (SynchModel)child;
			//			parent.addChild(em.getElementLabelModel());
			//		}
			//		if(child instanceof DecisionModel){
			//			DecisionModel em = (DecisionModel)child;
			//			parent.addChild(em.getElementLabelModel());
			//
			//		}
			//		if(child instanceof HForkJoinModel){
			//			HForkJoinModel em = (HForkJoinModel)child;
			////			parent.addChild(em.exceptionObjectNode);
			//			parent.addChild(em.getElementLabelModel());
			//
			//		}
			//		if(child instanceof VForkJoinModel){
			//			VForkJoinModel em = (VForkJoinModel)child;
			////			parent.addChild(em.exceptionObjectNode);
			//			parent.addChild(em.getElementLabelModel());
			//
			//		}
			if (parent instanceof UMLContainerModel) {
				UMLModel childUMLModel = (UMLModel)child;
				if (childUMLModel.getUMLTreeModel() != null) {
					childUMLModel.getUMLTreeModel().addN3UMLModelDeleteListener(parent);
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setChild(UMLModel subpart) {
		child = subpart;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public void setLocation(Rectangle r) {
		rect = r;
	}

	public void setParent(UMLDiagramModel newParent) {
		parent = newParent;
	}
	
	public void cloneAtomicComponentModel(UMLModel child){
		StrcuturedPackageTreeLibModel	to1 = new StrcuturedPackageTreeLibModel(child.getName());
		
	}

	//���
	public void undo() {
		parent.removeChild(child);
		UMLTreeModel treeModel = parent.getUMLTreeModel().getParent();
		if (treeModel instanceof UMLTreeParentModel && isTreeAdd) {
			ProjectManager.getInstance().removeUMLNode(child);
		}
		if (child instanceof PortContainerModel) {
			PortContainerModel ipc = (PortContainerModel)child;
			ipc.undoCreatePort(null, parent);
		}
		if (child instanceof TextAttachModel) {
			TextAttachModel ipc = (TextAttachModel)child;
			ipc.removeTextAttachParentDiagram(parent, null);
		}
		//		if(child instanceof InitialModel){
			//			InitialModel em = (InitialModel)child;
		//			parent.removeChild(em.getElementLabelModel());
		//			
		////			parent.addChild(child,index);
		//		}
		//		if(child instanceof FinalModel){
		//			FinalModel em = (FinalModel)child;
		//			parent.removeChild(em.getElementLabelModel());
		//			
		////			parent.addChild(child,index);
		//		}
		//		if(child instanceof FlowFinalModel){
		//			FlowFinalModel em = (FlowFinalModel)child;
		//			parent.removeChild(em.getElementLabelModel());
		//			
		////			parent.addChild(child,index);
		//		}
		//		if(child instanceof SynchModel){
		//			SynchModel em = (SynchModel)child;
		//			parent.removeChild(em.getElementLabelModel());
		//			
		////			parent.addChild(child,index);
		//		}
		//		if(child instanceof DecisionModel){
		//			DecisionModel em = (DecisionModel)child;
		//			parent.removeChild(em.getElementLabelModel());
		//			
		////			parent.addChild(child,index);
		//		}
		//		if(child instanceof HForkJoinModel){
		//			HForkJoinModel em = (HForkJoinModel)child;
		//			parent.removeChild(em.getElementLabelModel());
		//			
		////			parent.addChild(child,index);
		//		}
		//		if(child instanceof VForkJoinModel){
		//			VForkJoinModel em = (VForkJoinModel)child;
		//			parent.removeChild(em.getElementLabelModel());
		//			
		////			parent.addChild(child,index);
		//		}
	}
}