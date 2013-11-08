package kr.co.n3soft.n3com.rcp.actions;

import java.util.ArrayList;

import kr.co.n3soft.n3com.edit.FinalActivityEditPart;
import kr.co.n3soft.n3com.edit.FinalStrcuturedActivityEditPart;
import kr.co.n3soft.n3com.edit.LineTextEditPart;
import kr.co.n3soft.n3com.edit.PortEditPart;
import kr.co.n3soft.n3com.edit.StateEditPart;
import kr.co.n3soft.n3com.edit.StrcuturedStateEditPart;
import kr.co.n3soft.n3com.edit.SubPartitonEditPart;
import kr.co.n3soft.n3com.edit.TimingMessageLineEditPart;
import kr.co.n3soft.n3com.edit.UMLDiagramEditPart;
import kr.co.n3soft.n3com.edit.UMLEditPart;
import kr.co.n3soft.n3com.lang.N3Messages;
import kr.co.n3soft.n3com.model.activity.SubPartitonModel;
import kr.co.n3soft.n3com.model.comm.ClassifierModel;
import kr.co.n3soft.n3com.model.comm.LineTextModel;
import kr.co.n3soft.n3com.model.comm.PortModel;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.component.AtomicComponentModel;
import kr.co.n3soft.n3com.project.browser.PackageTreeModel;
import kr.co.n3soft.n3com.project.browser.UMLTreeModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.ui.actions.DeleteAction;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPart;

public class N3DeleteAction extends DeleteAction {
	public N3DeleteAction(IEditorPart editor) {
		super(editor);
		this.setText(N3Messages.POPUP_DELETE);
//		String id =  N3MessageManager.getN3Message().POPUP_DELETE;

//		this.setText( id);
	}
	public N3DeleteAction(IWorkbenchPart part) {

		super(part);
		String id =  N3Messages.POPUP_DELETE;
		this.setText( id);
	}
	
	public void delete(PackageTreeModel packageTreeModel){

//		PackageTreeModel packageTreeModel=(PackageTreeModel)treeObject;
		UMLTreeModel[] childList= packageTreeModel.getChildren();
		for(int j=0;j<childList.length;j++){
			if(childList[j] instanceof UMLTreeModel){
//				ProjectManager.getInstance().deleteUMLModel(childList[j]);
				ProjectManager.getInstance().removeUMLNode(childList[j].getParent(), childList[j]);
			}
		}
//		ProjectManager.getInstance().deleteUMLModel(packageTreeModel);

		ProjectManager.getInstance().removeUMLNode(packageTreeModel.getParent(), packageTreeModel);
	
		
	}

	public void run() {

		java.util.List alist = new java.util.ArrayList();
		java.util.List blist = new java.util.ArrayList();
		alist = getSelectedObjects();

		for(int i=0;i<alist.size();i++){
			Object obj = alist.get(i);
			if(obj instanceof UMLEditPart){
				UMLEditPart uMLEditPart= (UMLEditPart)obj;
				int ok = ProjectManager.getInstance().getDeleteModelType((UMLModel)uMLEditPart.getModel());
				//PKY 08091603 S
				if(uMLEditPart instanceof LineTextEditPart){
					if(uMLEditPart.getModel()!=null && uMLEditPart.getModel() instanceof LineTextModel){
						((LineTextModel)uMLEditPart.getModel()).setSize(new Dimension(0,0));
					}
				}
				//PKY 08091603 E

				if(ok>-1){
					blist.add(obj);
					//PKY 08081801 S 엑티비티 파티션 움직임이 비정상적인 문제
					//PKY 08052601 S Activity안에 포함된 파티션 지울 시 같이 삭제안되는문제
//					if(uMLEditPart instanceof FinalActivityEditPart||uMLEditPart instanceof FinalStrcuturedActivityEditPart){
//						EditPart edit =(EditPart)uMLEditPart;
//						if(edit.getParent()!=null){
//							if(edit.getParent() instanceof UMLDiagramEditPart){
//								UMLDiagramEditPart digramEdit=(UMLDiagramEditPart)edit.getParent();
//								if(digramEdit.getChildren()!=null&&digramEdit.getChildren().size()>0){
//									ArrayList arrayItem=(ArrayList)digramEdit.getChildren();
//									for(int j=0; j<arrayItem.size();j++){
//										if(arrayItem.get(j) instanceof SubPartitonEditPart){
//											if(((UMLModel)edit.getModel()).getUMLDataModel().getElementProperty("GROUP_CHILDRENS")!=null){
//												ArrayList list=(ArrayList)((UMLModel)edit.getModel()).getUMLDataModel().getElementProperty("GROUP_CHILDRENS");
//												for(int k=0; k<list.size();k++){    					
//													if(((SubPartitonEditPart)arrayItem.get(j))!=null 
//															&&((SubPartitonEditPart)arrayItem.get(j)).getModel()!=null){
//														if(list.get(k)==((SubPartitonEditPart)arrayItem.get(j)).getModel()){
//															blist.add(arrayItem.get(j));
//														}    												
//													}
//												}
//											}
//										}
//									}
//								}
//							}
//						}
//
//					}
					//PKY 08091601 S
					if(uMLEditPart instanceof PortEditPart){
						PortEditPart edit =(PortEditPart)uMLEditPart;
						if(edit.getModel()!=null){
						PortModel portModel = (PortModel)edit.getModel();
						if(portModel.getPortContainerModel()!=null &&
								portModel.getPortContainerModel() instanceof ClassifierModel){
							ClassifierModel classModel = (ClassifierModel)portModel.getPortContainerModel();
							// SDM 110822 S - 아토믹 컴포넌트 포트 삭제정보를 저장
							if(classModel.getPortManager()!=null){
								if(classModel instanceof AtomicComponentModel){
									AtomicComponentModel am = ((AtomicComponentModel) classModel).getCoreDiagramCmpModel();
									am.setDeleteList(portModel.getName());
								}
								classModel.getPortManager().remove(portModel);
							}
							// SDM 110822 E - 포트 삭제정보를 저장
								
						}
						}
					}
					//PKY 08091601 E
					//PKY 08081801 S State 모델 삭제 시 파티션 삭제되도록



					//PKY 08052601 S Activity안에 포함된 파티션 지울 시 같이 삭제안되는문제
					if(uMLEditPart instanceof FinalActivityEditPart||uMLEditPart instanceof FinalStrcuturedActivityEditPart||uMLEditPart instanceof StateEditPart||uMLEditPart instanceof  StrcuturedStateEditPart){
						EditPart edit =(EditPart)uMLEditPart;
						if(edit.getParent()!=null){
							if(edit.getParent() instanceof UMLDiagramEditPart){
								UMLDiagramEditPart digramEdit=(UMLDiagramEditPart)edit.getParent();
								if(digramEdit.getChildren()!=null&&digramEdit.getChildren().size()>0){
									ArrayList arrayItem=(ArrayList)digramEdit.getChildren();
									for(int j=0; j<arrayItem.size();j++){
										if(arrayItem.get(j) instanceof SubPartitonEditPart){
											SubPartitonEditPart subEdit = (SubPartitonEditPart)arrayItem.get(j);
											if(subEdit.getModel()!=null){
												SubPartitonModel subModel = (SubPartitonModel)subEdit.getModel();
												if(uMLEditPart.getModel() ==subModel.getParentModel()){
													blist.add(subEdit);
												}
											}
										}
									}
								}
							}
						}

					}
					//PKY 08081801 E State 모델 삭제 시 파티션 삭제되도록

					//PKY 08081801 E 엑티비티 파티션 움직임이 비정상적인 문제

				}

			}
			else{
				//PKY 08051401 S Timing Message를 지울경우 Point 가 남아있는 문제 수정
				if(obj instanceof TimingMessageLineEditPart){
					TimingMessageLineEditPart timg=(TimingMessageLineEditPart)obj;
					if(timg.getTarget()!=null)
						blist.add(timg.getTarget());
					if(timg.getSource()!=null)
						blist.add(timg.getSource());
				}
				//PKY 08051401 E Timing Message를 지울경우 Point 가 남아있는 문제 수정

				blist.add(obj);
			}



		}
		execute(createDeleteCommand(blist));
		for(int i=0;i<blist.size();i++){
			Object obj1 = blist.get(i);
			if(obj1 instanceof UMLEditPart){
			UMLEditPart uMLEditPart= (UMLEditPart)blist.get(i);
			
			Object obj = uMLEditPart.getModel();
			if(obj instanceof UMLModel){
				UMLModel um = (UMLModel)obj;
				UMLTreeModel utm = um.getUMLTreeModel();
				if(utm instanceof PackageTreeModel)
					this.delete((PackageTreeModel)utm);
			}
			}
			
		}
		

//		ProjectManager.getInstance().getUMLEditor().removeSeqNumber(m)
	}
}
