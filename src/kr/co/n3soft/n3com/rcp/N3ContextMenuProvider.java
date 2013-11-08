package kr.co.n3soft.n3com.rcp;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import kr.co.n3soft.n3com.edit.ComponentEditPart;
import kr.co.n3soft.n3com.edit.FinalActivityEditPart;
import kr.co.n3soft.n3com.edit.FinalActorEditPart;
import kr.co.n3soft.n3com.edit.FinalClassEditPart;
import kr.co.n3soft.n3com.edit.FinalPackageEditPart;
import kr.co.n3soft.n3com.edit.FinalStrcuturedActivityEditPart;
import kr.co.n3soft.n3com.edit.FrameEditPart;
import kr.co.n3soft.n3com.edit.InterfaceEditPart;
import kr.co.n3soft.n3com.edit.LineTextEditPart;
import kr.co.n3soft.n3com.edit.MessageEditPart;
import kr.co.n3soft.n3com.edit.NodeEditPart;
import kr.co.n3soft.n3com.edit.SelfMessageEditPart;
import kr.co.n3soft.n3com.edit.StateEditPart;
import kr.co.n3soft.n3com.edit.StrcuturedStateEditPart;
import kr.co.n3soft.n3com.edit.UMLContainerTreeEditPart;
import kr.co.n3soft.n3com.edit.UMLElementEditPart;
import kr.co.n3soft.n3com.edit.UMLTreeEditPart;
import kr.co.n3soft.n3com.edit.UseCaseEditPart;
import kr.co.n3soft.n3com.lang.N3Messages;
import kr.co.n3soft.n3com.model.activity.FinalActivityModel;
import kr.co.n3soft.n3com.model.activity.FinalStrcuturedActivityModel;
import kr.co.n3soft.n3com.model.comm.AttributeElementModel;
import kr.co.n3soft.n3com.model.comm.ClassModel;
import kr.co.n3soft.n3com.model.comm.ClassifierModel;
import kr.co.n3soft.n3com.model.comm.FinalPackageModel;
import kr.co.n3soft.n3com.model.comm.FrameModel;
import kr.co.n3soft.n3com.model.comm.NodeContainerModel;
import kr.co.n3soft.n3com.model.comm.OperationElementModel;
import kr.co.n3soft.n3com.model.comm.StrcuturedActivityPackageTreeModel;
import kr.co.n3soft.n3com.model.comm.StrcuturedPackageTreeModel;
import kr.co.n3soft.n3com.model.comm.TypeRefModel;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.component.AtomicComponentModel;
import kr.co.n3soft.n3com.model.component.ComponentLibModel;
import kr.co.n3soft.n3com.model.component.ComponentModel;
import kr.co.n3soft.n3com.model.diagram.N3EditorDiagramModel;
import kr.co.n3soft.n3com.model.umlclass.AttributeEditableTableItem;
import kr.co.n3soft.n3com.model.umlclass.CompartmentModel;
import kr.co.n3soft.n3com.model.umlclass.FinalClassModel;
import kr.co.n3soft.n3com.model.umlclass.InterfaceModel;
import kr.co.n3soft.n3com.model.umlclass.OperationEditableTableItem;
import kr.co.n3soft.n3com.model.usecase.FinalActorModel;
import kr.co.n3soft.n3com.model.usecase.UseCaseModel;
import kr.co.n3soft.n3com.project.browser.PackageTreeModel;
import kr.co.n3soft.n3com.project.browser.UMLTreeModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;
import kr.co.n3soft.n3com.rcp.actions.OpenDiagramAction;

import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.examples.logicdesigner.LogicMessages;
import org.eclipse.gef.examples.logicdesigner.actions.IncrementDecrementAction;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.gef.ui.parts.TreeViewer;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.ui.actions.ActionFactory;

public class N3ContextMenuProvider extends org.eclipse.gef.ContextMenuProvider {
	private ActionRegistry actionRegistry;

	public N3ContextMenuProvider(EditPartViewer viewer, ActionRegistry registry) {
		super(viewer);
		setActionRegistry(registry);

//		getViewer().addDragSourceListener(new TransferDragSourceListener(){
//
//			public Transfer getTransfer() {
//				System.out.print("");
//				return null;
//			}
//
//			public void dragFinished(DragSourceEvent event) {
//				System.out.print("dragFinished");
//				return;
//				
//			}
//
//			public void dragSetData(DragSourceEvent event) {
//				System.out.print("dragSetData");
//				
//			}
//
//			public void dragStart(DragSourceEvent event) {
//				System.out.print("dragStart");
//				return;
////				if(getViewer() instanceof TreeViewer){
////					StructuredSelection iSelection = (StructuredSelection)getViewer().getSelection();
////					Object selectObj = iSelection.getFirstElement();
////					if(selectObj instanceof UMLTreeEditPart){
////						UMLTreeEditPart umlTreeEdit = (UMLTreeEditPart)selectObj;
////							if(!ProjectManager.getInstance().isItemEnable(umlTreeEdit))
////								
////					}
////				}
//				
//			}
//			
//		});
		
	}
	public void dragStart(final DragSourceEvent event) {
		System.out.print("");
	}
	/* (non-Javadoc)
	 * @see org.eclipse.gef.ContextMenuProvider#menuAboutToShow(org.eclipse.jface.action.IMenuManager)
	 */

	

	
	private N3ContextMenuProvider nmp;
	public N3ContextMenuProvider getNmp() {
		return nmp;
	}

	public void setNmp(N3ContextMenuProvider nmp) {
		this.nmp = nmp;
	}

	public void buildContextMenu(IMenuManager manager) {
		try{
			//액션
			//111206 SDM S - 불필요한 LOG작성 삭제(주석처리)
			System.out.println("[buildContextMenu] " + null);
//			ProjectManager.getInstance().writeLog("buildContextMenu", null);
			//111206 SDM E - 불필요한 LOG작성 삭제(주석처리)
			
			N3ContextMenuProvider nmp = 	(N3ContextMenuProvider)manager;	
			setNmp(nmp);
			//111206 SDM S - 불필요한 LOG작성 삭제(주석처리)
			System.out.println("[nmp:" + nmp + "] " + null);
//			ProjectManager.getInstance().writeLog("nmp:;"+nmp, null);
			//111206 SDM E - 불필요한 LOG작성 삭제(주석처리)
			if(nmp.getViewer() instanceof TreeViewer){
				//111206 SDM S - 불필요한 LOG작성 삭제(주석처리)
				System.out.println("[buildContextMenu22] " + null);
//				ProjectManager.getInstance().writeLog("buildContextMenu22", null);
				//111206 SDM E - 불필요한 LOG작성 삭제(주석처리)

				GEFActionConstants.addStandardActionGroups(manager);
				IAction copy = null;
				
				/**
				 * Copy 메뉴 액션 
				 */
				copy = new Action() {
					public void run() {	
						ProjectManager.getInstance().setCopyattList(null);
						ProjectManager.getInstance().setCopyoperList(null);
						N3ContextMenuProvider nmp=getNmp(); 
						TreeViewer viewer=(TreeViewer)nmp.getViewer();
						Object selectionList= viewer.getSelection();
						StructuredSelection container=null;
						ArrayList att= new ArrayList();
						ArrayList oper= new ArrayList();
						HashMap  doubleId = new HashMap();
						if(selectionList instanceof StructuredSelection){
							container =(StructuredSelection) selectionList;
							List copylist= new ArrayList();

							List listz=container.toList();
							for(int i=0;i<listz.size();i++){
								copylist.add(listz.get(i));
							}

							Vector removeList =new Vector();
							for(int k=0; k<copylist.size();k++){
								if(copylist.get(k) instanceof UMLContainerTreeEditPart){
									UMLContainerTreeEditPart uMLContainerTreeEditPart=(UMLContainerTreeEditPart)copylist.get(k);
									if(uMLContainerTreeEditPart.getChildren()!=null){
										List list=uMLContainerTreeEditPart.getChildren();
										for(int j=0;j<list.size();j++){
											if(list.get(j) instanceof UMLTreeEditPart){
												UMLTreeEditPart UMLTreeEditPart=(UMLTreeEditPart)list.get(j);
												//2008042903 PKY S
												if(UMLTreeEditPart.getModel()instanceof AttributeElementModel){
													AttributeElementModel uMLContainerTreeEditPart1 = (AttributeElementModel)UMLTreeEditPart.getModel();
//													AttributeElementModel uMLContainerTreeEditPart2(new AttributeElementModel UMLTreeEditPart.getModel());
//													AttributeEditableTableItem newItem = new AttributeEditableTableItem(new Integer(0), "attribute"+"", "String", "");

//													uMLContainerTreeEditPart1.getAttributeEditableTableItem().id=newItem.id;
													doubleId.put(uMLContainerTreeEditPart1.getUMLDataModel().getId().toString(),
															uMLContainerTreeEditPart1.getUMLDataModel().getId().toString());
													att.add(uMLContainerTreeEditPart1.getAttributeEditableTableItem());												

												}
												if(UMLTreeEditPart.getModel()instanceof OperationElementModel){
													OperationElementModel uMLContainerTreeEditPart1=(OperationElementModel)UMLTreeEditPart.getModel();
//													OperationEditableTableItem newItem = new OperationEditableTableItem(new Integer(0), "attribute"+"", "String", "");
//													uMLContainerTreeEditPart1.getAttributeEditableTableItem().id=newItem.id;
													doubleId.put(uMLContainerTreeEditPart1.getUMLDataModel().getId().toString(),
															uMLContainerTreeEditPart1.getUMLDataModel().getId().toString());
													oper.add(uMLContainerTreeEditPart1.getAttributeEditableTableItem());

												}
												if(UMLTreeEditPart.getModel()instanceof TypeRefModel){
													ClassModel typeRefModel=(TypeRefModel)UMLTreeEditPart.getModel();
													if(typeRefModel.getAttributes()!=null){
														List classAtt=typeRefModel.getAttributes();
														for(int i=0;i<classAtt.size();i++){
															AttributeEditableTableItem attributeEditableTableItem=(AttributeEditableTableItem)classAtt.get(i);
															if(attributeEditableTableItem.id!=null){
																doubleId.put(attributeEditableTableItem.id.toString(),
																		attributeEditableTableItem.id.toString());
															}
															att.add(attributeEditableTableItem);		
														}
													}
													if(typeRefModel.getOperations()!=null){ //2008043001 Copy 객체 복사 붙여넣기 시  한개 더 추가되는문제 수정
														List classAtt=typeRefModel.getOperations();
														for(int i=0;i<classAtt.size();i++){
															OperationEditableTableItem attributeEditableTableItem=(OperationEditableTableItem)classAtt.get(i);
															if(attributeEditableTableItem.id!=null){
																doubleId.put(attributeEditableTableItem.id.toString(),
																		attributeEditableTableItem.id.toString());
															}
															oper.add(attributeEditableTableItem);		

														}
													}
													//2008042903 PKY S 
												} 
												else if(UMLTreeEditPart.getModel()instanceof ClassModel){ //2008043001 Copy 객체 복사 붙여넣기 시  한개 더 추가되는문제 수정
													ClassModel classModel=(ClassModel)UMLTreeEditPart.getModel();
													List attr=classModel.getAttributes();
													List opers=classModel.getOperations();
													for(int u=0; u<attr.size();u++){
														AttributeEditableTableItem attributeEditableTableItem=(AttributeEditableTableItem)attr.get(u);
														if(attributeEditableTableItem.id!=null){
															doubleId.put(attributeEditableTableItem.id.toString(),
																	attributeEditableTableItem.id.toString());
														}
														att.add(attributeEditableTableItem);
													}
													for(int q=0; q<opers.size();q++){
														OperationEditableTableItem attributeEditableTableItem=(OperationEditableTableItem)opers.get(q);
														if(attributeEditableTableItem.id!=null){
															doubleId.put(attributeEditableTableItem.id.toString(),
																	attributeEditableTableItem.id.toString());
														}
														oper.add(attributeEditableTableItem);	
													}
												}//2008042903 PKY E
											}

										}
									}
								}else if(copylist.get(k) instanceof UMLTreeEditPart){
									UMLTreeEditPart UMLTreeEditPart=(UMLTreeEditPart)copylist.get(k);

									if(UMLTreeEditPart.getModel()instanceof AttributeElementModel){
										AttributeElementModel attributeElementModel =(AttributeElementModel)UMLTreeEditPart.getModel();
										if(doubleId.get(attributeElementModel.getUMLDataModel().getId().toString())==null){
											att.add(attributeElementModel.getAttributeEditableTableItem());
										}
									}
									if(UMLTreeEditPart.getModel()instanceof OperationElementModel){
										OperationElementModel operationElementModel=(OperationElementModel)UMLTreeEditPart.getModel();
										if(doubleId.get(operationElementModel.getUMLDataModel().getId().toString())==null){
											oper.add(operationElementModel.getAttributeEditableTableItem());
										}
									}	
								}
							}
							if(removeList!=null){
								for(int j=0;j<removeList.size();j++){
									copylist.remove(removeList.get(j));
								}
							}
							if(oper.size()>0||att.size()>0){
								if(att.size()>0)
									ProjectManager.getInstance().setCopyattList(att);
								if(oper.size()>0)
									ProjectManager.getInstance().setCopyoperList(oper);
								ProjectManager.getInstance().initSelectLineModel();
								ProjectManager.getInstance().initTempCopyMap();
								ProjectManager.getInstance().setCopySource(null);
							}
						}
					}
				};

				/**
				 * Paste 메뉴 액션
				 */
				IAction paste = null;

				paste = new Action() {
					public void run() {
						ClassModel selecttypeRefModel=null;
						List attList =ProjectManager.getInstance().getCopyattList();
						List operList =ProjectManager.getInstance().getCopyoperList();


						N3ContextMenuProvider nmp=getNmp(); 
						TreeViewer viewer=(TreeViewer)nmp.getViewer();
						Object selectionList= viewer.getSelection();
						StructuredSelection container=null;
						if(selectionList instanceof StructuredSelection){
							container =(StructuredSelection) selectionList;
							List nowSelect=container.toList();
							if(nowSelect.get(0) instanceof UMLContainerTreeEditPart){
								UMLContainerTreeEditPart selectUMLContainerTreeEditPart =(UMLContainerTreeEditPart)nowSelect.get(0);
								if(selectUMLContainerTreeEditPart.getModel()!=null){
									if(selectUMLContainerTreeEditPart.getModel() instanceof CompartmentModel){
										CompartmentModel slectcompartmentModel=(CompartmentModel)selectUMLContainerTreeEditPart.getModel();
										if(slectcompartmentModel.getParentModel()!=null){
											if(slectcompartmentModel.getParentModel() instanceof ClassifierModel){
												ClassifierModel slectmodelType=(ClassifierModel)slectcompartmentModel.getParentModel();
												if(slectmodelType.getClassModel()!=null){
													if(slectmodelType.getClassModel() instanceof TypeRefModel){
														selecttypeRefModel=(TypeRefModel)slectmodelType.getClassModel();
													}

												}
											}

										}

									}else if(selectUMLContainerTreeEditPart.getModel() instanceof ClassifierModel){
										ClassifierModel slectcompartmentModel=(ClassifierModel)selectUMLContainerTreeEditPart.getModel();
										selecttypeRefModel=(TypeRefModel)slectcompartmentModel.getClassModel();
									}else if(selectUMLContainerTreeEditPart.getModel() instanceof InterfaceModel){
										InterfaceModel slectcompartmentModel=(InterfaceModel)selectUMLContainerTreeEditPart.getModel();
										if(slectcompartmentModel.getClassModel()!=null){
											if(slectcompartmentModel.getClassModel() instanceof ClassModel){
												selecttypeRefModel=(ClassModel)slectcompartmentModel.getClassModel();

											}

										}
									}
								}
							}
						}
						/**
						 *  CopyList에 있는 정보를 비교하여 Operation를 객체에 넣어주는 부분 
						 */
						if(selecttypeRefModel!=null){
							if(operList!=null&&operList.size()>0){		
								ArrayList operAdd =new ArrayList();
								List beforeOper =selecttypeRefModel.getOperations();
								for(int i=0;i<beforeOper.size();i++){
									operAdd.add(beforeOper.get(i));
								}
								for(int i=0; i<operList.size();i++){
									OperationEditableTableItem operationEditableTableItem=(OperationEditableTableItem)operList.get(i);

									operAdd.add(operationEditableTableItem.clone());	
								}
								selecttypeRefModel.setOperations(operAdd);

							}
							if(attList!=null&&attList.size()>0){


								ArrayList attAdd = new ArrayList();
								List beforeAtt =selecttypeRefModel.getAttributes();
								for(int i=0;i<beforeAtt.size();i++){
									attAdd.add(beforeAtt.get(i));
								}
								for(int i=0; i<attList.size();i++){
									AttributeEditableTableItem attributeEditableTableItem =(AttributeEditableTableItem)attList.get(i);
									attAdd.add(attributeEditableTableItem.clone());	
								}
								selecttypeRefModel.setAttributes(attAdd);

							}

							if(selecttypeRefModel!=null){
								if(selecttypeRefModel instanceof ClassModel){
									if(selecttypeRefModel.isMode()==true){
										ProjectManager.getInstance().setOutlineAutoSize(true);
										ProjectManager.getInstance().autoSize(selecttypeRefModel);
										ProjectManager.getInstance().setOutlineAutoSize(false);
									}
								}else{
									ProjectManager.getInstance().setOutlineAutoSize(true);
									ProjectManager.getInstance().autoSize(selecttypeRefModel);
									ProjectManager.getInstance().setOutlineAutoSize(false);
								}
							}
						}
					}
				};
				IAction delete = null;
				/**
				 * Delte메뉴 액션 
				 */
				delete = new Action() {
					public void run() {

						N3ContextMenuProvider nmp=getNmp(); 
						TreeViewer viewer=(TreeViewer)nmp.getViewer();
						Object selectionList= viewer.getSelection();
						StructuredSelection container=null;
						ArrayList att= new ArrayList();
						ArrayList oper= new ArrayList();
						HashMap  doubleId = new HashMap();
						if(selectionList instanceof StructuredSelection){
							container =(StructuredSelection) selectionList;
							List copylist= new ArrayList();

							List listz=container.toList();
							for(int i=0;i<listz.size();i++){
								copylist.add(listz.get(i));
							}
							for(int k=0; k<copylist.size();k++){
								if(copylist.get(k) instanceof UMLContainerTreeEditPart){

									UMLContainerTreeEditPart uMLContainerTreeEditPart=(UMLContainerTreeEditPart)copylist.get(k);
									if(uMLContainerTreeEditPart.getChildren()!=null){
										List list=uMLContainerTreeEditPart.getChildren();
										for(int j=0;j<list.size();j++){
											if(list.get(j) instanceof UMLTreeEditPart){
												UMLTreeEditPart UMLTreeEditPart=(UMLTreeEditPart)list.get(j);
												if(UMLTreeEditPart.getModel()instanceof AttributeElementModel){
													AttributeElementModel uMLContainerTreeEditPart1=(AttributeElementModel)UMLTreeEditPart.getModel();
													if(uMLContainerTreeEditPart1.getAttributeEditableTableItem().id!=null){
														doubleId.put(uMLContainerTreeEditPart1.getAttributeEditableTableItem().id.toString(),
																uMLContainerTreeEditPart1.getAttributeEditableTableItem().id.toString());
														att.add(uMLContainerTreeEditPart1.getAttributeEditableTableItem());
													}else{
														doubleId.put(uMLContainerTreeEditPart1.getUMLDataModel().getId().toString(),
																uMLContainerTreeEditPart1.getUMLDataModel().getId().toString());
														att.add(uMLContainerTreeEditPart1.getAttributeEditableTableItem());
													}

												}
												if(UMLTreeEditPart.getModel()instanceof OperationElementModel){
													OperationElementModel uMLContainerTreeEditPart1=(OperationElementModel)UMLTreeEditPart.getModel();
													doubleId.put(uMLContainerTreeEditPart1.getAttributeEditableTableItem().id.toString(),
															uMLContainerTreeEditPart1.getAttributeEditableTableItem().id.toString());
													oper.add(uMLContainerTreeEditPart1.getAttributeEditableTableItem());
												}	

											}
										}
									}

								}else if(copylist.get(k) instanceof UMLTreeEditPart){
									UMLTreeEditPart UMLTreeEditPart=(UMLTreeEditPart)copylist.get(k);

									if(UMLTreeEditPart.getModel()instanceof AttributeElementModel){
										AttributeElementModel attributeElementModel =(AttributeElementModel)UMLTreeEditPart.getModel();
										doubleId.put(attributeElementModel.getAttributeEditableTableItem().id.toString(),
												attributeElementModel.getAttributeEditableTableItem().id.toString());
										att.add(attributeElementModel.getAttributeEditableTableItem());


									}
									if(UMLTreeEditPart.getModel()instanceof OperationElementModel){
										OperationElementModel operationElementModel=(OperationElementModel)UMLTreeEditPart.getModel();
										doubleId.put(operationElementModel.getAttributeEditableTableItem().id.toString(),
												operationElementModel.getAttributeEditableTableItem().id.toString());
										oper.add(operationElementModel.getAttributeEditableTableItem());


									}	
								}
							}

							/**
							 * 현재 마우스가 선택한 위치 
							 */

							ClassModel selecttypeRefModel=null;
							if(selectionList instanceof StructuredSelection){
								container =(StructuredSelection) selectionList;
								List nowSelect=container.toList();
								if(nowSelect.get(0) instanceof UMLContainerTreeEditPart){
									UMLContainerTreeEditPart selectUMLContainerTreeEditPart =(UMLContainerTreeEditPart)nowSelect.get(0);
									if(selectUMLContainerTreeEditPart.getModel()!=null){
										if(selectUMLContainerTreeEditPart.getModel() instanceof CompartmentModel){
											CompartmentModel slectcompartmentModel=(CompartmentModel)selectUMLContainerTreeEditPart.getModel();
											if(slectcompartmentModel.getParentModel()!=null){
												if(slectcompartmentModel.getParentModel() instanceof ClassifierModel){
													ClassifierModel slectmodelType=(ClassifierModel)slectcompartmentModel.getParentModel();
													if(slectmodelType.getClassModel()!=null){
														if(slectmodelType.getClassModel() instanceof TypeRefModel){
															selecttypeRefModel=(TypeRefModel)slectmodelType.getClassModel();
														}

													}
												}else if(slectcompartmentModel.getParentModel() instanceof InterfaceModel){
													InterfaceModel slectmodelType=(InterfaceModel)slectcompartmentModel.getParentModel();
													if(slectmodelType.getClassModel()!=null){
														if(slectmodelType.getClassModel() instanceof ClassModel){
															selecttypeRefModel=(ClassModel)slectmodelType.getClassModel();
														}

													}
												}

											}

										}else if(selectUMLContainerTreeEditPart.getModel() instanceof ClassifierModel){
											ClassifierModel slectcompartmentModel=(ClassifierModel)selectUMLContainerTreeEditPart.getModel();
											selecttypeRefModel=(TypeRefModel)slectcompartmentModel.getClassModel();
										}else if(selectUMLContainerTreeEditPart.getModel() instanceof InterfaceModel){
											InterfaceModel slectcompartmentModel=(InterfaceModel)selectUMLContainerTreeEditPart.getModel();
											if(slectcompartmentModel.getClassModel()!=null){
												if(slectcompartmentModel.getClassModel() instanceof ClassModel){
													selecttypeRefModel=(ClassModel)slectcompartmentModel.getClassModel();

												}

											}
										}
									}
								}else if(nowSelect.get(0) instanceof UMLTreeEditPart){
									UMLTreeEditPart uMLTreeEditPart=(UMLTreeEditPart)nowSelect.get(0);
									if(uMLTreeEditPart.getParent() instanceof UMLContainerTreeEditPart){
										UMLContainerTreeEditPart selectUMLContainerTreeEditPart =(UMLContainerTreeEditPart)uMLTreeEditPart.getParent();
										if(selectUMLContainerTreeEditPart.getModel()!=null){
											if(selectUMLContainerTreeEditPart.getModel() instanceof CompartmentModel){
												CompartmentModel slectcompartmentModel=(CompartmentModel)selectUMLContainerTreeEditPart.getModel();
												if(slectcompartmentModel.getParentModel()!=null){
													if(slectcompartmentModel.getParentModel() instanceof ClassifierModel){
														ClassifierModel slectmodelType=(ClassifierModel)slectcompartmentModel.getParentModel();
														if(slectmodelType.getClassModel()!=null){
															if(slectmodelType.getClassModel() instanceof TypeRefModel){
																selecttypeRefModel=(TypeRefModel)slectmodelType.getClassModel();
															}

														}
													}

												}

											}else if(selectUMLContainerTreeEditPart.getModel() instanceof ClassifierModel){
												ClassifierModel slectcompartmentModel=(ClassifierModel)selectUMLContainerTreeEditPart.getModel();
												selecttypeRefModel=(TypeRefModel)slectcompartmentModel.getClassModel();
											}else if(selectUMLContainerTreeEditPart.getModel() instanceof InterfaceModel){
												InterfaceModel slectcompartmentModel=(InterfaceModel)selectUMLContainerTreeEditPart.getModel();
												if(slectcompartmentModel.getClassModel()!=null){
													if(slectcompartmentModel.getClassModel() instanceof ClassModel){
														selecttypeRefModel=(ClassModel)slectcompartmentModel.getClassModel();

													}

												}
											}
										}
									}

								}
							}
							if(selecttypeRefModel!=null){
								if(oper!=null&&oper.size()>0){		
									ArrayList operAdd =new ArrayList();
									List beforeOper =selecttypeRefModel.getOperations();
									for(int i=0;i<beforeOper.size();i++){
										OperationEditableTableItem operationElementModel=(OperationEditableTableItem)beforeOper.get(i);
										if(doubleId.get(operationElementModel.id.toString())==null){	
											operAdd.add(beforeOper.get(i));
											oper.remove(beforeOper.get(i));
										}else{
											selecttypeRefModel.downOperationCount();
										}
									}
									selecttypeRefModel.setOperations(operAdd);

								}
								if(att!=null&&att.size()>0){
									if(att.size()>0){										
										ArrayList attAdd = new ArrayList();
										List beforeAtt =selecttypeRefModel.getAttributes();
										for(int i=0;i<beforeAtt.size();i++){
											AttributeEditableTableItem attributeElementModel=(AttributeEditableTableItem)	beforeAtt.get(i);
											if(doubleId.get(attributeElementModel.id.toString())==null){	
												attAdd.add(beforeAtt.get(i));
												att.remove(beforeAtt.get(i));
											}else{
												selecttypeRefModel.downAttributeCount();
											}
										}


										selecttypeRefModel.setAttributes(attAdd);
									}
								}

								if(selecttypeRefModel!=null){
									ProjectManager.getInstance().setOutlineAutoSize(true);
									ProjectManager.getInstance().autoSize(selecttypeRefModel);
									ProjectManager.getInstance().setOutlineAutoSize(false);
								}
							}
						}





					}
				};

				/**
				 *  Menu 별 활성화 구분 Copy,Paste/Copy/Copy,Paste,Delete/
				 */
				//PKY 08082201 S 팀 프로젝트 읽기전용일때 수정불가능하도록 작업
				//111206 SDM S - 불필요한 LOG작성 삭제(주석처리)
				System.out.println("[ProjectManager.getInstance().getSelectPropertyUMLElementModel():" + ProjectManager.getInstance().getSelectPropertyUMLElementModel() + "] " + null);
//				ProjectManager.getInstance().writeLog("ProjectManager.getInstance().getSelectPropertyUMLElementModel():;"+ProjectManager.getInstance().getSelectPropertyUMLElementModel(), null);
				//111206 SDM E - 불필요한 LOG작성 삭제(주석처리)
				if(ProjectManager.getInstance()!=null && ProjectManager.getInstance().getSelectPropertyUMLElementModel()!=null)
					if(ProjectManager.getInstance().isItemEnable(ProjectManager.getInstance().getSelectPropertyUMLElementModel()))
					//PKY 08082201 E 팀 프로젝트 읽기전용일때 수정불가능하도록 작업
				if(nmp.getViewer() instanceof TreeViewer){
					TreeViewer viewer=(TreeViewer)nmp.getViewer();
					Object selectionList= viewer.getSelection();
					StructuredSelection container=null;
					if(selectionList instanceof StructuredSelection){
						container =(StructuredSelection) selectionList;
						List nowSelect=container.toList();
						List attList =ProjectManager.getInstance().getCopyattList();
						List operList =ProjectManager.getInstance().getCopyoperList();
						if(nowSelect.get(0)instanceof UMLContainerTreeEditPart){
							UMLContainerTreeEditPart uMLContainerTreeEditPart=(UMLContainerTreeEditPart)nowSelect.get(0);
							if(uMLContainerTreeEditPart.getModel() instanceof ClassifierModel
									||uMLContainerTreeEditPart.getModel() instanceof InterfaceModel){
//								GEFActionConstants.addStandardActionGroups(manager);
//								IAction action;
//								action = getActionRegistry().getAction(ActionFactory.COPY.getId());
//								if (action.isEnabled())
//								manager.appendToGroup(GEFActionConstants.GROUP_EDIT, action);
//								if(ProjectManager.getInstance().getCopyList().size()>0){
//								action = getActionRegistry().getAction(ActionFactory.PASTE.getId());
//								manager.appendToGroup(GEFActionConstants.GROUP_EDIT, action);
//								}
//								action = getActionRegistry().getAction(ActionFactory.DELETE.getId());

//								if (action.isEnabled())
//								manager.appendToGroup(GEFActionConstants.GROUP_EDIT, action);
								copy.setText(N3Messages.POPUP_COPY);
								paste.setText(N3Messages.POPUP_PASTE);
								manager.add(copy);
								if(attList!=null){
									if(attList.size()>0){
										manager.add(paste);
									}
								}
								else if(operList!=null){
									if(operList.size()>0){
										manager.add(paste);

									}
								}
							}else if(uMLContainerTreeEditPart.getModel() instanceof TypeRefModel
									||uMLContainerTreeEditPart.getModel() instanceof NodeContainerModel
							){

							}else{
								copy.setText(N3Messages.POPUP_COPY);
								paste.setText(N3Messages.POPUP_PASTE);
								delete.setText(N3Messages.POPUP_DELETE);
								manager.add(copy);
								if(attList!=null){
									if(attList.size()>0){
										manager.add(paste);
									}
								}
								else if(operList!=null){
									if(operList.size()>0){
										manager.add(paste);

									}
								}

								manager.add(delete);
							}
						}else if(nowSelect.get(0)instanceof UMLTreeEditPart){
							UMLTreeEditPart uMLTreeEditPart=(UMLTreeEditPart)nowSelect.get(0);
							if(uMLTreeEditPart.getModel() instanceof AttributeElementModel||
									uMLTreeEditPart.getModel() instanceof OperationElementModel){

								copy.setText(N3Messages.POPUP_COPY);
								paste.setText(N3Messages.POPUP_PASTE);
								delete.setText(N3Messages.POPUP_DELETE);
								manager.add(copy);
								if(attList!=null){
									if(attList.size()>0){
										manager.add(paste);
									}
								}
								else if(operList!=null){
									if(operList.size()>0){
										manager.add(paste);

									}
								}

								manager.add(delete);

							}
						}
						else{
							copy.setText(N3Messages.POPUP_COPY);
							paste.setText(N3Messages.POPUP_PASTE);
							delete.setText(N3Messages.POPUP_DELETE);
							manager.add(copy);
							if(attList!=null){
								if(attList.size()>0){
									manager.add(paste);
								}
							}
							else if(operList!=null){
								if(operList.size()>0){
									manager.add(paste);

								}
							}

							manager.add(delete);
						}
					}
				}

				//2008042401PKY E
				
			}

			else{

		        //PKY 08082201 S 팀 프로젝트 읽기전용일때 수정불가능하도록 작업
				N3EditorDiagramModel nd =	ProjectManager.getInstance().getUMLEditor().getDiagram();
				if(nd!=null && nd.isReadOnly()){
					return;
				}
				if(ProjectManager.getInstance()!=null && ProjectManager.getInstance().getSelectPropertyUMLElementModel()!=null)
				if(ProjectManager.getInstance().isItemEnable(ProjectManager.getInstance().getSelectPropertyUMLElementModel())){
					boolean isEnable = ProjectManager.getInstance().isItemEnable(ProjectManager.getInstance().getSelectPropertyUMLElementModel());
				GEFActionConstants.addStandardActionGroups(manager);
				IAction action;
				MenuManager elementSubmenu = new MenuManager(N3Messages.POPUP_ADD);
				MenuManager zOrderSubmenu = new MenuManager(N3Messages.POPUP_Z_ODRDER, ImageDescriptor.createFromImage(ProjectManager.getInstance().getImage(357)), null);
				MenuManager messages = new MenuManager(N3Messages.POPUP_ADD_SEQ_MESSAGE);
				MenuManager subDiagramList = new MenuManager(N3Messages.POPUP_OPEN_DIAGRAM, ImageDescriptor.createFromImage(ProjectManager.getInstance().getImage(356)), null);
				
				MenuManager deployList = new MenuManager("Deploy");
				//PKY 08072201 S 텍스트 필드 오른쪽 클릭 시 Delete뜨지않도록 수정
				java.util.List selectList = ProjectManager.getInstance().getSelectNodes();
				//PKY 08072401 S 다이어그램 오른쪽 메뉴 팝업 시 메뉴가 안나오는 문제 수정
				if(selectList!=null&&selectList.size()==0){
//					action = getActionRegistry().getAction(ActionFactory.UNDO.getId());
//					manager.appendToGroup(GEFActionConstants.GROUP_UNDO, action);
//					action = getActionRegistry().getAction(ActionFactory.REDO.getId());
//					manager.appendToGroup(GEFActionConstants.GROUP_UNDO, action);
//					action = getActionRegistry().getAction(ActionFactory.PASTE.getId());
//					if (action.isEnabled())
//						manager.appendToGroup(GEFActionConstants.GROUP_EDIT, action);

				}

				if (selectList != null && selectList.size() == 1) {
					Object obj = selectList.get(0);
					if (!(obj instanceof LineTextEditPart||obj instanceof UMLElementEditPart)) {
						action = getActionRegistry().getAction(ActionFactory.DELETE.getId());
						if (action.isEnabled())
							manager.appendToGroup(GEFActionConstants.GROUP_EDIT, action);
						//PKY 08080501 S Frame 복사안되도록 수정
						if(!(obj instanceof FrameEditPart)){
//							action = getActionRegistry().getAction(ActionFactory.COPY.getId());
//							if (action.isEnabled())
//								manager.appendToGroup(GEFActionConstants.GROUP_EDIT, action);
						}
						//PKY 08080501 E Frame 복사안되도록 수정
					}//else if(obj instanceof LineTextEditPart){
					//	action = getActionRegistry().getAction(ActionFactory.DELETE.getId());
					//	if (action.isEnabled())
					//		manager.appendToGroup(GEFActionConstants.GROUP_EDIT, action);
					//}
				}else{
					
					action = getActionRegistry().getAction(ActionFactory.DELETE.getId());
					if (action.isEnabled()){
						
							manager.appendToGroup(GEFActionConstants.GROUP_EDIT, action);
					}

//					action = getActionRegistry().getAction(ActionFactory.COPY.getId());
//					if (action.isEnabled())
//						manager.appendToGroup(GEFActionConstants.GROUP_EDIT, action);
				}
				//PKY 08072201 E 텍스트 필드 오른쪽 클릭 시 Delete뜨지않도록 수정
				//PKY 08072401 E 다이어그램 오른쪽 메뉴 팝업 시 메뉴가 안나오는 문제 수정

				//20080721IJS 시작
				action = getActionRegistry().getAction("TracingRequirementsAction");
				if (action.isEnabled())
					manager.appendToGroup(GEFActionConstants.GROUP_EDIT, action);
				//20080721IJS 끝

				action = getActionRegistry().getAction("NewOperationAction");
				if (action.isEnabled())
					manager.appendToGroup(GEFActionConstants.GROUP_EDIT, action);

				action = getActionRegistry().getAction("ChanageAggregateAction");
				if (action.isEnabled())
					manager.appendToGroup(GEFActionConstants.GROUP_EDIT, action);

				action = getActionRegistry().getAction("ChanageCompositeAction");
				if (action.isEnabled())
					manager.appendToGroup(GEFActionConstants.GROUP_EDIT, action);

				action = getActionRegistry().getAction("ChanageNavigableAction");
				if (action.isEnabled())
					manager.appendToGroup(GEFActionConstants.GROUP_EDIT, action);

				action = getActionRegistry().getAction("AddRoleNameAction");
				if (action.isEnabled())
					manager.appendToGroup(GEFActionConstants.GROUP_EDIT, action);
				//PKY 08052101 S 컨테이너에서 그룹으로 변경
				//PKY 08081101 S 포함관계 오른쪽 메뉴로 해서 합치는 불편함 문제 개선
//				action = getActionRegistry().getAction("ModelGroupMerge");
//				if (action.isEnabled())
//					manager.appendToGroup(GEFActionConstants.GROUP_EDIT, action);
//
//				action = getActionRegistry().getAction("ModelGroupMergeCencel");
//				if (action.isEnabled())
//					manager.appendToGroup(GEFActionConstants.GROUP_EDIT, action);//PKY 08081101 S 오른쪽 RoleName 메뉴 두개나는 문제

				//PKY 08081101 E 포함관계 오른쪽 메뉴로 해서 합치는 불편함 문제 개선

				//PKY 08052101 E 컨테이너에서 그룹으로 변경

				List list = ProjectManager.getInstance().getSelectNodes();
				if (list != null && list.size() == 1) {
					Object obj = list.get(0);


					if (obj instanceof MessageEditPart) {
						
						
					


						
					}

					//2008040401PKY S "패키지 오른쪽 클릭 시 OpenDiagram 메뉴 추가    여러 다이어그램이있을경우 리스트 나오도록"
					if (obj instanceof FinalPackageEditPart) {//2008040401PKY S 
						FinalPackageEditPart finalPackageEditPart = (FinalPackageEditPart)obj;
						FinalPackageModel finalPackageModel=(FinalPackageModel)finalPackageEditPart.getModel();
						PackageTreeModel packageTreeModel =(PackageTreeModel)finalPackageModel.getUMLTreeModel();
						ArrayList array=ProjectManager.getInstance().getModelBrowser().DiagramList(packageTreeModel);
						ProjectManager.getInstance().setDiagramsSub(array);

						for(int i = 0; i<array.size();i++){
							Object obj1=array.get(i);
							N3EditorDiagramModel n3EditorPackageDiagramModel =(N3EditorDiagramModel)obj1;
							OpenDiagramAction action1 = new OpenDiagramAction();
							action1.setText(n3EditorPackageDiagramModel.getName());
							action1.setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getDiagramImage(5)));

							action1.setN3EditorPackageDiagramModel(n3EditorPackageDiagramModel.getN3EditorDiagramModelTemp());
							subDiagramList.add(action1);

						}

					}
					//PKY 08071601 S 다이어그램 드래그 해서 오른쪽마우스 로 오픈하도록 누락되어 추가
					if (obj instanceof FrameEditPart){
						FrameEditPart finalPackageEditPart = (FrameEditPart)obj;
						FrameModel finalPackageModel=(FrameModel)finalPackageEditPart.getModel();

						ArrayList array= new ArrayList();
						array.add(finalPackageModel.getN3EditorDiagramModel());
						ProjectManager.getInstance().setDiagramsSub(array);

						for(int i = 0; i<array.size();i++){
							Object obj1=array.get(i);
							N3EditorDiagramModel n3EditorPackageDiagramModel =(N3EditorDiagramModel)obj1;
							OpenDiagramAction action1 = new OpenDiagramAction();
							action1.setText(n3EditorPackageDiagramModel.getName());
							action1.setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getDiagramImage(5)));

							action1.setN3EditorPackageDiagramModel(n3EditorPackageDiagramModel.getN3EditorDiagramModelTemp());
							subDiagramList.add(action1);

						}

					}
					//PKY 08071601 E 다이어그램 드래그 해서 오른쪽마우스 로 오픈하도록 누락되어 추가 


					//2008042902 PKY S 
					else if (obj instanceof UseCaseEditPart||obj instanceof FinalActorEditPart
							||obj instanceof FinalClassEditPart||obj instanceof InterfaceEditPart
							||obj instanceof FinalStrcuturedActivityEditPart||obj instanceof ComponentEditPart
							||obj instanceof StateEditPart||obj instanceof StrcuturedStateEditPart
							||obj instanceof NodeEditPart||obj instanceof FinalActivityEditPart) { 

						ArrayList array = new ArrayList();
						if(obj instanceof FinalActivityEditPart){
							FinalActivityEditPart finalPackageEditPart = (FinalActivityEditPart)obj;
							FinalActivityModel finalPackageModel=(FinalActivityModel)finalPackageEditPart.getModel();
							if(finalPackageModel.getUMLTreeModel() instanceof StrcuturedPackageTreeModel ){
								StrcuturedPackageTreeModel packageTreeModel =(StrcuturedPackageTreeModel)finalPackageModel.getUMLTreeModel();      
								array=ProjectManager.getInstance().getModelBrowser().DiagramList(packageTreeModel);
								ProjectManager.getInstance().setDiagramsSub(array);
							}
							else if(finalPackageModel.getUMLTreeModel() instanceof StrcuturedActivityPackageTreeModel){
								StrcuturedActivityPackageTreeModel packageTreeModel =(StrcuturedActivityPackageTreeModel)finalPackageModel.getUMLTreeModel();      
								array=ProjectManager.getInstance().getModelBrowser().DiagramList(packageTreeModel);
								ProjectManager.getInstance().setDiagramsSub(array);
							}
						}
						else if(obj instanceof UseCaseEditPart){
							UseCaseEditPart finalPackageEditPart = (UseCaseEditPart)obj;
							UseCaseModel finalPackageModel=(UseCaseModel)finalPackageEditPart.getModel();
							StrcuturedPackageTreeModel packageTreeModel =(StrcuturedPackageTreeModel)finalPackageModel.getUMLTreeModel();						
							array=ProjectManager.getInstance().getModelBrowser().DiagramList(packageTreeModel);
							ProjectManager.getInstance().setDiagramsSub(array);
						}
						else if(obj instanceof FinalActorEditPart){
							FinalActorEditPart finalPackageEditPart = (FinalActorEditPart)obj;
							FinalActorModel finalPackageModel=(FinalActorModel)finalPackageEditPart.getModel();
							UMLTreeModel packageTreeModel =(UMLTreeModel)finalPackageModel.getUMLTreeModel();						
							array=ProjectManager.getInstance().getModelBrowser().DiagramList(packageTreeModel);
							ProjectManager.getInstance().setDiagramsSub(array);
						}
						else if(obj instanceof FinalClassEditPart){
							FinalClassEditPart finalPackageEditPart = (FinalClassEditPart)obj;
							FinalClassModel finalPackageModel=(FinalClassModel)finalPackageEditPart.getModel();
							//PKY 08051401 S Enumeration 오른쪽 버튼 누룰 시 에러발생
							System.out.print(finalPackageModel.getUMLTreeModel());
							if(finalPackageModel.getUMLTreeModel() instanceof StrcuturedPackageTreeModel ){
								StrcuturedPackageTreeModel packageTreeModel =(StrcuturedPackageTreeModel)finalPackageModel.getUMLTreeModel();						
								array=ProjectManager.getInstance().getModelBrowser().DiagramList(packageTreeModel);
								ProjectManager.getInstance().setDiagramsSub(array);
							}
							//PKY 08051401 E Enumeration 오른쪽 버튼 누룰 시 에러발생
							if(finalPackageModel.getUMLTreeModel() instanceof StrcuturedPackageTreeModel ){

							}
						}
						else if(obj instanceof InterfaceEditPart){
							InterfaceEditPart finalPackageEditPart = (InterfaceEditPart)obj;
							InterfaceModel finalPackageModel=(InterfaceModel)finalPackageEditPart.getModel();
							StrcuturedPackageTreeModel packageTreeModel =(StrcuturedPackageTreeModel)finalPackageModel.getUMLTreeModel();						
							array=ProjectManager.getInstance().getModelBrowser().DiagramList(packageTreeModel);
							ProjectManager.getInstance().setDiagramsSub(array);
						}
						else if(obj instanceof FinalStrcuturedActivityEditPart){
							FinalStrcuturedActivityEditPart finalPackageEditPart = (FinalStrcuturedActivityEditPart)obj;
							FinalStrcuturedActivityModel finalPackageModel=(FinalStrcuturedActivityModel)finalPackageEditPart.getModel();
							StrcuturedActivityPackageTreeModel packageTreeModel =(StrcuturedActivityPackageTreeModel)finalPackageModel.getUMLTreeModel();						
							array=ProjectManager.getInstance().getModelBrowser().DiagramList(packageTreeModel);
							ProjectManager.getInstance().setDiagramsSub(array);
						}
						else if(obj instanceof ComponentEditPart){
							ComponentEditPart finalPackageEditPart = (ComponentEditPart)obj;
							ComponentModel finalPackageModel=(ComponentModel)finalPackageEditPart.getModel();
							StrcuturedPackageTreeModel packageTreeModel = null;
							if(finalPackageModel instanceof ComponentLibModel){
								ComponentLibModel clm = (ComponentLibModel)finalPackageModel;
								if(clm.isInstance()){
									packageTreeModel =(StrcuturedPackageTreeModel)clm.getInstanceUMLTreeModel();
								}
								else
									packageTreeModel =(StrcuturedPackageTreeModel)finalPackageModel.getUMLTreeModel();		
							}
							else if(finalPackageModel instanceof AtomicComponentModel){
								AtomicComponentModel clm = (AtomicComponentModel)finalPackageModel;
								packageTreeModel =(StrcuturedPackageTreeModel)clm.getCoreUMLTreeModel();
								
							}
							else 
								packageTreeModel =(StrcuturedPackageTreeModel)finalPackageModel.getUMLTreeModel();		
							
							array=ProjectManager.getInstance().getModelBrowser().DiagramList(packageTreeModel);
							ProjectManager.getInstance().setDiagramsSub(array);
						}
						else if(obj instanceof StateEditPart){
							
						}
						else if(obj instanceof StrcuturedStateEditPart){
							
						}
						else if(obj instanceof NodeEditPart){
//							NodeEditPart finalPackageEditPart = (NodeEditPart)obj;
//							NodeModel finalPackageModel=(NodeModel)finalPackageEditPart.getModel();
//							StrcuturedPackageTreeModel packageTreeModel =(StrcuturedPackageTreeModel)finalPackageModel.getUMLTreeModel();						
//							array=ProjectManager.getInstance().getModelBrowser().DiagramList(packageTreeModel);
//							ProjectManager.getInstance().setDiagramsSub(array);
						}						
						else if(obj instanceof FinalActivityEditPart){
							FinalActivityEditPart finalPackageEditPart = (FinalActivityEditPart)obj;
							FinalActivityModel finalPackageModel=(FinalActivityModel)finalPackageEditPart.getModel();
							StrcuturedPackageTreeModel packageTreeModel =(StrcuturedPackageTreeModel)finalPackageModel.getUMLTreeModel();						
							array=ProjectManager.getInstance().getModelBrowser().DiagramList(packageTreeModel);
							ProjectManager.getInstance().setDiagramsSub(array);
						}
						if(array.size()>0){
							for(int i = 0; i<array.size();i++){
								Object obj1=array.get(i);
								N3EditorDiagramModel n3EditorPackageDiagramModel =(N3EditorDiagramModel)obj1;
								OpenDiagramAction action1 = new OpenDiagramAction();
								action1.setText(n3EditorPackageDiagramModel.getName());
								action1.setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getDiagramImage(5)));

								action1.setN3EditorPackageDiagramModel(n3EditorPackageDiagramModel.getN3EditorDiagramModelTemp());
								subDiagramList.add(action1);

							}
						}
					}
					//2008042902 PKY E
					//2008040401PKY E "패키지 오른쪽 클릭 시 OpenDiagram 메뉴 추가    여러 다이어그램이있을경우 리스트 나오도록"
					else if(obj instanceof SelfMessageEditPart){//2008041502PKY S 셀프메세지 오퍼레이션 보일수 있도록 수정
						

					}//2008041502PKY E
				}
//				}






				action = getActionRegistry().getAction("ADD ADDPartitionAction");
				if (action.isEnabled()){
					elementSubmenu.add(action);
					manager.appendToGroup(GEFActionConstants.GROUP_EDIT, elementSubmenu);
				}

				action = getActionRegistry().getAction("ADDStateRegionAction");
				if (action.isEnabled()){
					elementSubmenu.add(action);
					manager.appendToGroup(GEFActionConstants.GROUP_EDIT, elementSubmenu);
				}
				
				
				//khg 10.4.28 노드삭제
				action = getActionRegistry().getAction("DeleteNodeAction");
				if (action.isEnabled())
					manager.appendToGroup(GEFActionConstants.GROUP_EDIT, action);

				action = getActionRegistry().getAction("OpenDiagramAction");
				if (action.isEnabled())
					manager.appendToGroup(GEFActionConstants.GROUP_EDIT, subDiagramList);       //2008040401PKY S "패키지 오른쪽 클릭 시 OpenDiagram 메뉴 추가  여러 다이어그램이있을경우 리스트 나오도록"

				action = getActionRegistry().getAction("OpenSeqMessageDialogAction");
				if (action.isEnabled())
					manager.appendToGroup(GEFActionConstants.GROUP_EDIT, action);


				action = getActionRegistry().getAction("DeleteNumberBar");
				if (action.isEnabled())
					manager.appendToGroup(GEFActionConstants.GROUP_EDIT, action);
				//PKY 08051401 S 라인 꺽인것 바로 직선으로 만들기
				action = getActionRegistry().getAction("DirectLine");
				if (action.isEnabled())
					manager.appendToGroup(GEFActionConstants.GROUP_EDIT, action);
				//PKY 08051401 S 라인 꺽인것 바로 직선으로 만들기
				action = getActionRegistry().getAction("ADDMessageAction");
				if (action.isEnabled())
					manager.appendToGroup(GEFActionConstants.GROUP_EDIT, action);
				//PKY 08070301 S Communication Dialog 추가작업
				action = getActionRegistry().getAction("ADDBackMessageAction");
				if (action.isEnabled())
					manager.appendToGroup(GEFActionConstants.GROUP_EDIT, action);
				//PKY 08070301 E Communication Dialog 추가작업


				action = getActionRegistry().getAction("ChangeDeepHistoryAction");
				if (action.isEnabled())
					manager.appendToGroup(GEFActionConstants.GROUP_EDIT, action);
				action = getActionRegistry().getAction("ADD ADDContainerAction");
				if (action.isEnabled()){
					elementSubmenu.add(action);
					manager.appendToGroup(GEFActionConstants.GROUP_EDIT, elementSubmenu);
				}		
				action = getActionRegistry().getAction("ChangeInterfaceModeAction");
				if (action.isEnabled())
					manager.appendToGroup(GEFActionConstants.GROUP_EDIT, action);
				list = ProjectManager.getInstance().getSelectNodes();
				boolean isComponentLibModel = false;
				if (list != null && list.size() == 1) {
					Object obj = list.get(0);
				if(obj instanceof ComponentEditPart){
					ComponentEditPart cep = (ComponentEditPart)obj;
					if(cep.getModel() instanceof ComponentLibModel){
						isComponentLibModel = true;
					}
					action = getActionRegistry().getAction("OpenPeopertyAction");
					if (action.isEnabled()) {
//						elementSubmenu.add(action);
						manager.appendToGroup(GEFActionConstants.GROUP_EDIT, action);
					}
					
					action = getActionRegistry().getAction("OpenMonitoringAction");
					if (action.isEnabled()) {
//						elementSubmenu.add(action);
						manager.appendToGroup(GEFActionConstants.GROUP_EDIT, action);
					}
					
					action = getActionRegistry().getAction("MonitoringRunAction");
					if (action.isEnabled()) {
//						elementSubmenu.add(action);
						manager.appendToGroup(GEFActionConstants.GROUP_EDIT, action);
					}
					
					action = getActionRegistry().getAction("MonitoringStopAction");
					if (action.isEnabled()) {
//						elementSubmenu.add(action);
						manager.appendToGroup(GEFActionConstants.GROUP_EDIT, action);
					}
					
					action = getActionRegistry().getAction("OpenComponentEditorAction");
					if (action.isEnabled()) {
//						elementSubmenu.add(action);
						manager.appendToGroup(GEFActionConstants.GROUP_EDIT, action);
					}
					
				}
				}
//				if(!isComponentLibModel){
//					NodeDeployAction nda = new NodeDeployAction();
//					nda.setText("hello_node");
//					deployList.add(nda);
//					NodeDeployAction nda1 = new NodeDeployAction();
//					nda1.setText("print_node");
//					deployList.add(nda1);
////					action = getActionRegistry().getAction("SendFileAction");
////					if (action.isEnabled()) {
//						manager.appendToGroup(GEFActionConstants.GROUP_EDIT, deployList);       //2008040401PKY
////					}	
//					
//				action = getActionRegistry().getAction("ADDMethodInputPortAction");
//				if (action.isEnabled()) {
//					elementSubmenu.add(action);
//					manager.appendToGroup(GEFActionConstants.GROUP_EDIT, elementSubmenu);
//				}
//				
//				action = getActionRegistry().getAction("ADDMethodOutputPorAction");
//				if (action.isEnabled()) {
//					elementSubmenu.add(action);
//					manager.appendToGroup(GEFActionConstants.GROUP_EDIT, elementSubmenu);
//				}
//				
//				action = getActionRegistry().getAction("ADDDataInputPortAction");
//				if (action.isEnabled()) {
//					elementSubmenu.add(action);
//					manager.appendToGroup(GEFActionConstants.GROUP_EDIT, elementSubmenu);
//				}
//				
//				action = getActionRegistry().getAction("ADDDataOutputPortAction");
//				if (action.isEnabled()) {
//					elementSubmenu.add(action);
//					manager.appendToGroup(GEFActionConstants.GROUP_EDIT, elementSubmenu);
//				}
//				
//				action = getActionRegistry().getAction("ADDEventInputPortAction");
//				if (action.isEnabled()) {
//					elementSubmenu.add(action);
//					manager.appendToGroup(GEFActionConstants.GROUP_EDIT, elementSubmenu);
//				}
//				
//				action = getActionRegistry().getAction("ADDEventOutputPortAction");
//				if (action.isEnabled()) {
//					elementSubmenu.add(action);
//					manager.appendToGroup(GEFActionConstants.GROUP_EDIT, elementSubmenu);
//				}
//				}


				action = getActionRegistry().getAction("ADDObjectPortAction");
				if (action.isEnabled()) {
					elementSubmenu.add(action);
					manager.appendToGroup(GEFActionConstants.GROUP_EDIT, elementSubmenu);
				}
				action = getActionRegistry().getAction("ADD ActivityParameter");
				if (action.isEnabled()) {
					elementSubmenu.add(action);
					manager.appendToGroup(GEFActionConstants.GROUP_EDIT, elementSubmenu);
				}
				action = getActionRegistry().getAction("ADD ADDExpansionNodeAction");
				if (action.isEnabled()) {
					elementSubmenu.add(action);
					manager.appendToGroup(GEFActionConstants.GROUP_EDIT, elementSubmenu);
				}
				action = getActionRegistry().getAction("ADDExitPointAction");
				if (action.isEnabled()) {
					elementSubmenu.add(action);
					manager.appendToGroup(GEFActionConstants.GROUP_EDIT, elementSubmenu);
				}
				action = getActionRegistry().getAction("ADDEntryPointAction");
				if (action.isEnabled()) {
					elementSubmenu.add(action);
					manager.appendToGroup(GEFActionConstants.GROUP_EDIT, elementSubmenu);
				}
				action = getActionRegistry().getAction("ADDActionPinAction");
				if (action.isEnabled()){
					elementSubmenu.add(action);
					manager.appendToGroup(GEFActionConstants.GROUP_EDIT, elementSubmenu);
				}

//				action = getActionRegistry().getAction("ADDAttribute");
//				if (action.isEnabled()) {
//					elementSubmenu.add(action);
//					manager.appendToGroup(GEFActionConstants.GROUP_EDIT, elementSubmenu);
//				}
//
//				action = getActionRegistry().getAction("ADDoperation");
//				if (action.isEnabled()) {
//					elementSubmenu.add(action);
//					manager.appendToGroup(GEFActionConstants.GROUP_EDIT, elementSubmenu);
//				}

				action = getActionRegistry().getAction(GEFActionConstants.DIRECT_EDIT);
				if (action.isEnabled())
					manager.appendToGroup(GEFActionConstants.GROUP_EDIT, action);

				action = getActionRegistry().getAction(IncrementDecrementAction.INCREMENT);
				if (action.isEnabled())
					manager.appendToGroup(GEFActionConstants.GROUP_REST, action);

				action = getActionRegistry().getAction(IncrementDecrementAction.DECREMENT);
				if (action.isEnabled())
					manager.appendToGroup(GEFActionConstants.GROUP_REST, action);

				MenuManager submenu = new MenuManager(LogicMessages.AlignmentAction_AlignSubmenu_ActionLabelText);

				action = getActionRegistry().getAction(GEFActionConstants.ALIGN_LEFT);
				if (action.isEnabled())
					submenu.add(action);

				action = getActionRegistry().getAction(GEFActionConstants.ALIGN_CENTER);
				if (action.isEnabled())
					submenu.add(action);

				action = getActionRegistry().getAction(GEFActionConstants.ALIGN_RIGHT);
				if (action.isEnabled())
					submenu.add(action);

				submenu.add(new Separator());
				action = getActionRegistry().getAction(GEFActionConstants.ALIGN_TOP);
				if (action.isEnabled())
					submenu.add(action);
				action = getActionRegistry().getAction(GEFActionConstants.ALIGN_MIDDLE);
				if (action.isEnabled())
					submenu.add(action);
				action = getActionRegistry().getAction(GEFActionConstants.ALIGN_BOTTOM);
				if (action.isEnabled())
					submenu.add(action);
				if (!submenu.isEmpty())
					manager.appendToGroup(GEFActionConstants.GROUP_REST, submenu);

				//PKY 08072201 S 객체 팝업 Add메뉴위치가 틀려서 객체별로 메뉴 순서가 틀리게 뜨는문제 수정
				if(ProjectManager.getInstance().getSelectPropertyUMLElementModel() instanceof UMLModel){
				UMLModel model = (UMLModel)ProjectManager.getInstance().getSelectPropertyUMLElementModel();
				if(!model.isReadOnlyModel()){
				action = getActionRegistry().getAction("ZOrderForwardAction");
				if (action.isEnabled()){
					zOrderSubmenu.add(action);
					manager.appendToGroup(GEFActionConstants.GROUP_EDIT, zOrderSubmenu);
				}
				action = getActionRegistry().getAction("ZOrderBackAction");
				if (action.isEnabled()){
					zOrderSubmenu.add(action);
					manager.appendToGroup(GEFActionConstants.GROUP_EDIT, zOrderSubmenu);
				}
				}
				}
				//PKY 08072201 S 객체 팝업 Add메뉴위치가 틀려서 객체별로 메뉴 순서가 틀리게 뜨는문제 수정


				//20080718 KDI s 다이어그램 팝업 이미지 저장 구현
				action = getActionRegistry().getAction(N3Messages.POPUP_SAVE_IMAGE);
				if (action.isEnabled()) {
//					elementSubmenu.add(action);
					manager.appendToGroup(GEFActionConstants.GROUP_EDIT, action);
				}
				//20080718 KDI e 다이어그램 팝업 이미지 저장 구현
			}else{
			
				GEFActionConstants.addStandardActionGroups(manager);
				IAction action;
				MenuManager elementSubmenu = new MenuManager(N3Messages.POPUP_ADD);
				MenuManager zOrderSubmenu = new MenuManager(N3Messages.POPUP_Z_ODRDER, ImageDescriptor.createFromImage(ProjectManager.getInstance().getImage(357)), null);
				MenuManager messages = new MenuManager(N3Messages.POPUP_ADD_SEQ_MESSAGE);
				MenuManager subDiagramList = new MenuManager(N3Messages.POPUP_OPEN_DIAGRAM, ImageDescriptor.createFromImage(ProjectManager.getInstance().getImage(356)), null);
				
				if(ProjectManager.getInstance()!=null && ProjectManager.getInstance().getSelectPropertyUMLElementModel()!=null){
					UMLModel selectxModel = (UMLModel)ProjectManager.getInstance().getSelectPropertyUMLElementModel();
					if(!selectxModel.isReadOnlyModel()){
						action = getActionRegistry().getAction(ActionFactory.DELETE.getId());
						if (action.isEnabled())
							manager.appendToGroup(GEFActionConstants.GROUP_EDIT, action);
						action = getActionRegistry().getAction("ZOrderForwardAction");
						if (action.isEnabled()){
							zOrderSubmenu.add(action);
							manager.appendToGroup(GEFActionConstants.GROUP_EDIT, zOrderSubmenu);
						}
						action = getActionRegistry().getAction("ZOrderBackAction");
						if (action.isEnabled()){
							zOrderSubmenu.add(action);
							manager.appendToGroup(GEFActionConstants.GROUP_EDIT, zOrderSubmenu);
						}
					}
				}
			}
			}
		}
		catch(Exception e1){
			e1.printStackTrace();
			try{
				//111206 SDM  S - 로그파일 생성 이클립스 폴더 내부로 옮김.
				Properties P = System.getProperties();
				String strEclipseDir = P.get("user.dir").toString();
				FileWriter fw = new FileWriter(new File(strEclipseDir + File.separator + "composer_err.log"),true);
				//111206 SDM  E - 로그파일 생성 이클립스 폴더 내부로 옮김.
				PrintWriter bw=new PrintWriter(fw);
				
				e1.printStackTrace(bw);
//				bw.write(e1.printStackTrace());
				bw.close();
//				saveValue = -1;

			}
			catch(Exception e){
				e.printStackTrace();

			}
		}
		//		action = getActionRegistry().getAction(ActionFactory.SAVE.getId());
		//		manager.appendToGroup(GEFActionConstants.GROUP_SAVE, action);
	}

	private ActionRegistry getActionRegistry() {
		return actionRegistry;
	}

	private void setActionRegistry(ActionRegistry registry) {
		actionRegistry = registry;
	}



}
