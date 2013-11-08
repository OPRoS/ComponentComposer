package kr.co.n3soft.n3com.rcp.actions;

import java.util.List;

import kr.co.n3soft.n3com.dnd.CopySimpleFactory;
import kr.co.n3soft.n3com.dnd.CopySimpleLineFactory;
import kr.co.n3soft.n3com.edit.UMLDiagramEditPart;
import kr.co.n3soft.n3com.edit.UMLEditPart;
import kr.co.n3soft.n3com.model.comm.LineModel;
import kr.co.n3soft.n3com.model.comm.NodeContainerModel;
import kr.co.n3soft.n3com.model.comm.UMLContainerModel;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.diagram.N3EditorDiagramModel;
import kr.co.n3soft.n3com.project.browser.UMLTreeParentModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gef.requests.SimpleFactory;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IEditorPart;
//2008042901 PKY S 

public class N3PasteTemplateAction extends PasteTemplateAction {
	public Point location = new Point();
	java.util.List FinalBoundryModels = null;
	//	Object template = null;

	/**
	 * Constructor for LogicPasteTemplateAction.
	 * @param editor
	 */
	public N3PasteTemplateAction(IEditorPart editor) {
		super(editor);
		this.setAccelerator(SWT.CTRL|'v');//PKY 08071601 S 붙여넣기 단축키 잘못된부분 수정

	}

	/** @see PasteTemplateAction#getPasteLocation(GraphicalEditPart) */
	protected Point getPasteLocation(GraphicalEditPart container, Point location) {
		Point result = new Point(10, 10);
		result.x = location.x;
		result.y = location.y;
		IFigure fig = container.getContentPane();
		result.translate(fig.getClientArea(Rectangle.SINGLETON).getLocation());
		fig.translateToAbsolute(result);
		return result;
	}

	protected Point getPasteLocation(GraphicalEditPart container) {
		Point result = ProjectManager.getInstance().copyTarget;
		//		IFigure fig = container.getContentPane();
		//		result.translate(fig.getClientArea(Rectangle.SINGLETON).getLocation());
		//		fig.translateToAbsolute(result);
		return result;
	}

	protected boolean calculateEnabled() {
		//2008040306PKY S  Copy 여러 번 가능하도록
		List selection = getSelectedObjects();
//		if (this.getClipboardContents() == null)
//		return false;
//		Object template = getClipboardContents();
		java.util.List l = ProjectManager.getInstance().getCopyList();
		
		if (l != null && l instanceof java.util.List) {
			java.util.List list = (java.util.List) l;
			if (list.size() == 0) {
				return false;
			}
		}

		if (selection != null && selection.size() == 1) {
			Object obj = selection.get(0);
			if ((obj instanceof UMLDiagramEditPart ) ) {
				return true;
			}
		}

		// TODO Workaround for Bug 82622/39369.  Should be removed when 39369 is fixed.
		Object obj = this.getClipboardContents();
		return false;
		//2008040306PKY E  Copy 여러 번 가능하도록
	}

	protected Command createPasteCommand() {
		Command result = null;
		List selection = getSelectedObjects();
		try {
			if (selection != null && selection.size() == 1) {
				Object obj = selection.get(0);
				if (obj instanceof GraphicalEditPart) {
					GraphicalEditPart gep = (GraphicalEditPart)obj;
					Object template = getClipboardContents();
					if (template != null && template instanceof java.util.List) {
						java.util.List list = (java.util.List) template;
						if (list.size() == 1) {
							CopySimpleFactory factory = (CopySimpleFactory)getFactory(list.get(0));
							if (factory != null) {
								CreateRequest request = new CreateRequest();
								request.setFactory(factory);
								location = factory.getUMLModel().getLocation();
								request.setLocation(getPasteLocation(gep));
								result = gep.getCommand(request);
							}
						}
						else {
							for (int i = 0; i < list.size(); i++) {
								CopySimpleFactory factory = (CopySimpleFactory)getFactory(list.get(i));
								if (factory != null) {
									CreateRequest request = new CreateRequest();
									request.setFactory(factory);
									location = factory.getUMLModel().getLocation();
									request.setLocation(getPasteLocation(gep, location));
									result = gep.getCommand(request);
								}
							}
						}
					}
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	protected Command createPasteCommand(SimpleFactory factory, boolean isOne, boolean isModel) {
		
		Command result = null;
		List selection = getSelectedObjects();
		try {
			if (selection != null && selection.size() == 1) {
				Object obj = selection.get(0);
				if (obj instanceof GraphicalEditPart) {
					GraphicalEditPart gep = (GraphicalEditPart)obj;
					if (isModel) {
						if (factory != null) {
							ProjectManager.getInstance().setCopyTarget(null);
							CreateRequest request = new CreateRequest();
							request.setFactory(factory);
							if (isOne) {
								request.setLocation(getPasteLocation(gep));
							}
							else {
								request.setLocation(getPasteLocation(gep,
										((CopySimpleFactory)factory).calculateLocation(ProjectManager.getInstance().getCopyTarget())));
							}
							result = gep.getCommand(request);
						}
					}
					else {
						CopySimpleLineFactory lineModel = (CopySimpleLineFactory)factory;
						//						lineModel.getLineModel().setSourceTerminal(s)
						lineModel.getSource().connectOutput(lineModel.getLineModel());
						lineModel.getTarget().connectInput(lineModel.getLineModel());
						lineModel.getLineModel().setSource(lineModel.getSource());
						lineModel.getLineModel().setTarget(lineModel.getTarget());
						//						lineModel.getLineModel().cloneLineModel(lineModel.getLineModel().sourceLineTextModel,
						// lineModel.getLineModel().middleLineTextModel, lineModel.getLineModel().targetLineTextModel);
						//						UMLConnectionCommand command = new UMLConnectionCommand();
						//						command.setFactory(factory);
						//						ProjectManager.getInstance().getUMLEditor().getUMLDefaultEditDomain().getCommandStack().execute(command);
						//						ProjectManager.getInstance().get
						//						if (factory != null) {
						//							ProjectManager.getInstance().setCopyTarget(null);
						//							CreateRequest request = new CreateRequest();
						//							request.setFactory(factory);
						//							if(isOne){
						//								request.setLocation(getPasteLocation(gep));
						//							}
						//							else{
						//								request.setLocation(getPasteLocation(gep,((CopySimpleFactory)factory).calculateLocation(ProjectManager.getInstance().getCopyTarget())));
						//							}
						//							result = gep.getCommand(request);
						//						}
					}
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public void run() {
		//2008040306PKY S  Copy 여러 번 가능하도록
		java.util.List list = null;
		ProjectManager.getInstance().setCopy(true);
		try {
			/**
			 *  Copy Logic 
			 */
			List l =ProjectManager.getInstance().getCopyList();
//			Object template = getClipboardContents();
			if (l != null) {
				java.util.List list1 = (java.util.List) l;                


				if (l.size() > 0) {
//					templateList = new java.util.ArrayList();
					list = new java.util.ArrayList();
//					templateLineList = new java.util.ArrayList();
					UMLModel container = null;
					UMLModel um = null;
					for (int i = 0; i < l.size(); i++) {
						Object obj = l.get(i);
						if (obj instanceof UMLEditPart) {
							UMLEditPart uMLEditPart = (UMLEditPart)obj;
							//					ProjectManager.getInstance().setTempCopyMap(((UMLModel)uMLEditPart.getModel()).getID(), uMLModel);
							int ok = ProjectManager.getInstance().getCopyModelType((UMLModel)uMLEditPart.getModel());
							UMLModel p = (UMLModel)uMLEditPart.getModel();
							if (ok >= 0 && ok!=500) {
								if (container == null) {
									container = (UMLModel)p.getAcceptParentModel();
									um = p;
								}
								else if (container != p.getAcceptParentModel()) {
									UMLModel container1 = (UMLModel)p.getAcceptParentModel();
									if (container1 instanceof NodeContainerModel) {
										NodeContainerModel ncm = (NodeContainerModel)container1;
										for (int i1 = 0; i1 < l.size(); i1++) {
											Object obj2 = l.get(i1);
											if (obj2 instanceof UMLEditPart) {
												UMLEditPart uMLEditPart1 = (UMLEditPart)obj2;
												//
												// ProjectManager.getInstance().setTempCopyMap(((UMLModel)uMLEditPart.getModel()).getID(),
														// uMLModel);
												//											int ok =
													// ProjectManager.getInstance().getCopyModelType((UMLModel)uMLEditPart.getModel());
												UMLModel p1 = (UMLModel)uMLEditPart1.getModel();
												if (p1.getAcceptParentModel() == container1) {
													container = (UMLModel)p.getAcceptParentModel();
													um = p;
													break;
												}
											}
										}
										//								if(ncm.isContainModel(um)){
										//									container = (UMLModel)p.getAcceptParentModel();
										//									um = p;
										//								}
									}
								}
								if (p.getAcceptParentModel() instanceof N3EditorDiagramModel) {
									container = (UMLModel)p.getAcceptParentModel();
									break;
								}
							}
						}
					}
					//            if(container instanceof NodeContainerModel){
					//            	Point pt = ProjectManager.getInstance().getCopySource();
					//            	ProjectManager.getInstance().setCopySource(new Point(pt.x+100,pt.y+100));
					//            }
					for (int i = 0; i < l.size(); i++) {
						Object obj = l.get(i);
						if (obj instanceof UMLEditPart) {
							UMLEditPart uMLEditPart = (UMLEditPart)obj;
							int ok = ProjectManager.getInstance().getCopyModelType((UMLModel)uMLEditPart.getModel());
							UMLModel p = (UMLModel)uMLEditPart.getModel();
							if (ok >= 0) {
								//						if(container==null){
									//							container = (UMLModel)p.getAcceptParentModel();
									//						}
								if (container != p.getAcceptParentModel())
									continue;
								UMLModel uMLModel = (UMLModel)uMLEditPart.getCloneModel();
								CopySimpleFactory copySimpleFactory = new CopySimpleFactory(null);
								copySimpleFactory.setUMLModel(uMLModel);
								copySimpleFactory.calculateDifference(ProjectManager.getInstance().getCopySource());
								list.add(copySimpleFactory);
							}
						}
					}
					List lines = ProjectManager.getInstance().getSelectLineModel();
					for (int i = 0; i < lines.size(); i++) {
						Object obj = lines.get(i);
						if (obj instanceof LineModel) {
							//					LineEditPart uMLEditPart = (LineEditPart)obj;
							LineModel lineModel = (LineModel)obj;
							UMLModel uMLSource = lineModel.getOldSource();
							UMLModel uMLTarget = lineModel.getOldTraget();
							UMLModel cloneUMLSource = (UMLModel)ProjectManager.getInstance().getCloneValue(uMLSource.getID());
							UMLModel cloneUMLTarget = (UMLModel)ProjectManager.getInstance().getCloneValue(uMLTarget.getID());
							if (cloneUMLSource != null && cloneUMLTarget != null) {
								lineModel.setSource(cloneUMLSource);
								lineModel.setTarget(cloneUMLTarget);
								lineModel.getSource().connectOutput(lineModel);
								lineModel.getTarget().connectInput(lineModel);
								lineModel.setDiagram((N3EditorDiagramModel)container);

								//						lineModel.cloneLineModel(lineModel.sourceLineTextModel,
										// lineModel.middleLineTextModel, lineModel.targetLineTextModel);
							}
						}
					}
					ProjectManager.getInstance().initSelectLineModel();
					ProjectManager.getInstance().initTempCopyMap();
				}

				/**
				 *  Paste Logic 
				 */

//				java.util.List list = (java.util.List) list1.get(0);
				if (list.size() == 1) {
					CopySimpleFactory factory = (CopySimpleFactory)getFactory(list.get(0));
					ProjectManager.getInstance().setTempCopyMap(factory.getUMLModel().getID(), factory.getUMLModel());
					execute(createPasteCommand(factory, true, true));
				}
				else {
					//				FinalBoundryModels = new java.util.ArrayList();
					//				
					//				for(int i=0;i<list.size();i++){
					//					CopySimpleFactory factory = (CopySimpleFactory)getFactory(list.get(i));
					//					ProjectManager.getInstance().setTempCopyMap(factory.getUMLModel().getID(), factory.getUMLModel());
					//					if (factory != null) {
					//						if(factory.getUMLModel() instanceof FinalBoundryModel){
					//							FinalBoundryModels.add(factory.getUMLModel());
					//							execute(createPasteCommand(factory,false,true));
					//							
					//						}
					////						else if(factory.getUMLModel() instanceof FinalActivityModel){
					////							FinalBoundryModels.add(factory.getUMLModel());
					////							execute(createPasteCommand(factory,false,true));
					////							
					////						}
					//					}
					//				}
					for (int i = 0; i < list.size(); i++) {
						boolean isChild = false;
						CopySimpleFactory factory = (CopySimpleFactory)getFactory(list.get(i));
						if (factory != null) {
							//						if(!(factory.getUMLModel() instanceof FinalBoundryModel)){
							//						for(int i1=0;i1<this.FinalBoundryModels.size();i1++){
							//							FinalBoundryModel	finalBoundryModel =(FinalBoundryModel)FinalBoundryModels.get(i1);
							////							UMLModel child =
							// (UMLModel)ProjectManager.getInstance().getCloneValue(((CopySimpleFactory)factory).getUMLModel().getID());
							//							UMLModel child = factory.getUMLModel().sourceModel;
							//							if(finalBoundryModel.isChild(child)){
							//								finalBoundryModel.getBoundaryModel().addChild(factory.getUMLModel());
							//								isChild = true;
							//							}
							//							
							//						}
							//						if(!isChild)
							execute(createPasteCommand(factory, false, true));
							//						}
						}
					}
				}
				//			list = (java.util.List)list1.get(1);
				//			if(list.size()>0){
				//				for(int i=0;i<list.size();i++){
				//					CopySimpleLineFactory factory = (CopySimpleLineFactory)getFactory(list.get(i));
				//					if (factory != null) {
				//						execute(createPasteCommand(factory,false,false));
				//					}
				//				}
				//			}
				//			List lines = ProjectManager.getInstance().getSelectLineModel();
				//			for(int i=0;i<lines.size();i++){
				//				Object obj = lines.get(i);
				//				if(obj instanceof LineModel){
				////					LineEditPart uMLEditPart = (LineEditPart)obj;
				//					LineModel lineModel = (LineModel)obj;
				//					UMLModel uMLSource = lineModel.getOldSource();
				//					UMLModel uMLTarget = lineModel.getOldTraget();
				//					UMLModel cloneUMLSource = (UMLModel)ProjectManager.getInstance().getCloneValue(uMLSource.getID());
				//					UMLModel cloneUMLTarget = (UMLModel)ProjectManager.getInstance().getCloneValue(uMLTarget.getID());
				//					if(cloneUMLSource!=null && cloneUMLTarget!=null){
				//
				//						lineModel.setSource(cloneUMLSource);
				//						lineModel.setTarget(cloneUMLTarget);
				//						lineModel.getSource().connectOutput(lineModel);
				//						lineModel.getTarget().connectInput(lineModel);
				//					}
				//
				//					
				//					
				//				}
				//			}
				//			
				//			ProjectManager.getInstance().initSelectLineModel();
				//			ProjectManager.getInstance().initTempCopyMap();
//				list1.clear();
			}
			//2008040306PKY S  Copy 여러 번 가능하도록
			this.dispose();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		//		execute(createPasteCommand());
	}

	public void dispose() {
		System.out.println("====>");
		java.util.ArrayList list = ProjectManager.getInstance().getChildCopyList();
		UMLTreeParentModel utp = (UMLTreeParentModel)ProjectManager.getInstance().getCopyTreeModel();
		for (int i = 0; i < list.size(); i++) {
			Object obj = list.get(i);
			if (obj instanceof UMLModel && utp != null) {
				UMLModel um = (UMLModel)obj;
				ProjectManager.getInstance().addUMLModel(null, utp, um, -1,-1);
				if (um.getAcceptParentModel() instanceof UMLContainerModel) {
					UMLModel childUMLModel = (UMLModel)um;
					if (childUMLModel.getUMLTreeModel() != null) {
						childUMLModel.getUMLTreeModel().addN3UMLModelDeleteListener((UMLContainerModel)um.getAcceptParentModel());
					}
				}
			}
		}
		ProjectManager.getInstance().initChildCopyList();
		ProjectManager.getInstance().setCopyTreeModel(null);
		ProjectManager.getInstance().setCopy(false);

//		Clipboard.getDefault().setContents(null);

//		Clipboard.getDefault().setContents(contents);
//		templateList = null;
	}
}
