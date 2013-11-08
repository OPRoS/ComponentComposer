package kr.co.n3soft.n3com.rcp.actions;

import java.util.List;

import kr.co.n3soft.n3com.edit.ActivityParameterEditPart;
import kr.co.n3soft.n3com.edit.EntryPointEditPart;
import kr.co.n3soft.n3com.edit.ExitPointEditPart;
import kr.co.n3soft.n3com.edit.ExpansionNodeEditPart;
import kr.co.n3soft.n3com.edit.LineEditPart;
import kr.co.n3soft.n3com.edit.PortEditPart;
import kr.co.n3soft.n3com.edit.SubPartitonEditPart;
import kr.co.n3soft.n3com.lang.N3Messages;
import kr.co.n3soft.n3com.model.comm.UMLNoteModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.gef.ui.actions.Clipboard;
import org.eclipse.gef.ui.actions.CopyTemplateAction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IEditorPart;

public class MultiCopyAction extends CopyTemplateAction {
    public java.util.List templateModelList = null;
    public java.util.List templateLineList = null;
    public java.util.List templateList = null;


    public MultiCopyAction(IEditorPart editor) {
        super(editor);
        this.setText(N3Messages.POPUP_COPY);
        this.setAccelerator(SWT.CTRL|'c');//PKY 08062601 S 단축키 정의
        setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getImage(311)));//PKY 08070101 S 팝업 메뉴 이미지 삽입
    }

    //	protected boolean calculateEnabled() {
    ////		return template != null;
    //		return true;
    //	}
    //
    //	/**
    //	 * @see org.eclipse.gef.ui.actions.EditorPartAction#dispose()
    //	 */
    //	public void dispose() {
    ////		template = null;
    //	}
    //
    //	/**
    //	 * Sets the default {@link Clipboard Clipboard's} contents to be the currently selected
    //	 * template.
    //	 */
    public void run() {
    	 //2008040306PKY S  Copy 여러 번 가능하도록
        //		if(templateList!=null){	
    	 java.util.ArrayList l = new java.util.ArrayList();
    	 java.util.List l1 = null;
//        try {
            l1 = ProjectManager.getInstance().getSelectNodes();
            for(int i=0;i<l1.size();i++){
            	
            	l.add(l1.get(i));
//            	if(l1.get(i) instanceof UMLEditPart){
//            		UMLEditPart editPart = (UMLEditPart)l1.get(i);
//            		if(editPart.getModel()!=null && editPart.getModel() instanceof UMLModel){
//            			UMLModel umlModel = (UMLModel)	editPart.getModel();
//            			for(int k = 0; k < umlModel.getOutputs().size(); k++){
//            				umlModel.getOutputs().get(k);
//            				if(editPart.getParent()!=null && editPart.getParent() instanceof UMLDiagramEditPart){
//            					UMLDiagramEditPart n3Diagarm = (UMLDiagramEditPart)editPart.getParent();
//            					for(int b = 0; b < n3Diagarm.getChildren().size(); b++){
//            						if(n3Diagarm.getChildren().get(b) instanceof LineEditPart){
//            							System.out.print("");
//            						}
//            					}
//            				}
//            			}
//            		}
//            			
//            	}
            }
            ProjectManager.getInstance().setCopyList(l);
            ProjectManager.getInstance().initSelectLineModel();
            ProjectManager.getInstance().initTempCopyMap();
            ProjectManager.getInstance().setCopySource(null);
         

//            if (l.size() > 0) {
//                templateList = new java.util.ArrayList();
//                templateModelList = new java.util.ArrayList();
//                templateLineList = new java.util.ArrayList();
//                UMLModel container = null;
//                UMLModel um = null;
//                for (int i = 0; i < l.size(); i++) {
//                    Object obj = l.get(i);
//                    if (obj instanceof UMLEditPart) {
//                        UMLEditPart uMLEditPart = (UMLEditPart)obj;
//                        //					ProjectManager.getInstance().setTempCopyMap(((UMLModel)uMLEditPart.getModel()).getID(), uMLModel);
//                        int ok = ProjectManager.getInstance().getCopyModelType((UMLModel)uMLEditPart.getModel());
//                        UMLModel p = (UMLModel)uMLEditPart.getModel();
//                        if (ok >= 0 && ok!=500) {
//                            if (container == null) {
//                                container = (UMLModel)p.getAcceptParentModel();
//                                um = p;
//                            }
//                            else if (container != p.getAcceptParentModel()) {
//                                UMLModel container1 = (UMLModel)p.getAcceptParentModel();
//                                if (container1 instanceof NodeContainerModel) {
//                                    NodeContainerModel ncm = (NodeContainerModel)container1;
//                                    for (int i1 = 0; i1 < l.size(); i1++) {
//                                        Object obj2 = l.get(i1);
//                                        if (obj2 instanceof UMLEditPart) {
//                                            UMLEditPart uMLEditPart1 = (UMLEditPart)obj2;
//                                            //
//                                            // ProjectManager.getInstance().setTempCopyMap(((UMLModel)uMLEditPart.getModel()).getID(),
//                                            // uMLModel);
//                                            //											int ok =
//                                            // ProjectManager.getInstance().getCopyModelType((UMLModel)uMLEditPart.getModel());
//                                            UMLModel p1 = (UMLModel)uMLEditPart1.getModel();
//                                            if (p1.getAcceptParentModel() == container1) {
//                                                container = (UMLModel)p.getAcceptParentModel();
//                                                um = p;
//                                                break;
//                                            }
//                                        }
//                                    }
//                                    //								if(ncm.isContainModel(um)){
//                                    //									container = (UMLModel)p.getAcceptParentModel();
//                                    //									um = p;
//                                    //								}
//                                }
//                            }
//                            if (p.getAcceptParentModel() instanceof N3EditorDiagramModel) {
//                                container = (UMLModel)p.getAcceptParentModel();
//                                break;
//                            }
//                        }
//                    }
//                }
//                //            if(container instanceof NodeContainerModel){
//                //            	Point pt = ProjectManager.getInstance().getCopySource();
//                //            	ProjectManager.getInstance().setCopySource(new Point(pt.x+100,pt.y+100));
//                //            }
//                for (int i = 0; i < l.size(); i++) {
//                    Object obj = l.get(i);
//                    if (obj instanceof UMLEditPart) {
//                        UMLEditPart uMLEditPart = (UMLEditPart)obj;
//                        int ok = ProjectManager.getInstance().getCopyModelType((UMLModel)uMLEditPart.getModel());
//                        UMLModel p = (UMLModel)uMLEditPart.getModel();
//                        if (ok >= 0) {
//                            //						if(container==null){
//                            //							container = (UMLModel)p.getAcceptParentModel();
//                            //						}
//                            if (container != p.getAcceptParentModel())
//                                continue;
//                            UMLModel uMLModel = (UMLModel)uMLEditPart.getCloneModel();
//                            CopySimpleFactory copySimpleFactory = new CopySimpleFactory(null);
//                            copySimpleFactory.setUMLModel(uMLModel);
//                            copySimpleFactory.calculateDifference(ProjectManager.getInstance().getCopySource());
//                            templateModelList.add(copySimpleFactory);
//                        }
//                    }
//                }
//                List lines = ProjectManager.getInstance().getSelectLineModel();
//                for (int i = 0; i < lines.size(); i++) {
//                    Object obj = lines.get(i);
//                    if (obj instanceof LineModel) {
//                        //					LineEditPart uMLEditPart = (LineEditPart)obj;
//                        LineModel lineModel = (LineModel)obj;
//                        UMLModel uMLSource = lineModel.getOldSource();
//                        UMLModel uMLTarget = lineModel.getOldTraget();
//                        UMLModel cloneUMLSource = (UMLModel)ProjectManager.getInstance().getCloneValue(uMLSource.getID());
//                        UMLModel cloneUMLTarget = (UMLModel)ProjectManager.getInstance().getCloneValue(uMLTarget.getID());
//                        if (cloneUMLSource != null && cloneUMLTarget != null) {
//                            lineModel.setSource(cloneUMLSource);
//                            lineModel.setTarget(cloneUMLTarget);
//                            lineModel.getSource().connectOutput(lineModel);
//                            lineModel.getTarget().connectInput(lineModel);
//                           
//                            //						lineModel.cloneLineModel(lineModel.sourceLineTextModel,
//                            // lineModel.middleLineTextModel, lineModel.targetLineTextModel);
//                        }
//                    }
//                }
//                ProjectManager.getInstance().initSelectLineModel();
//                ProjectManager.getInstance().initTempCopyMap();
//            }
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//            //			ProjectManager.getInstance().initSelectLineModel();
//            //			ProjectManager.getInstance().initTempCopyMap();
//        }
//        templateList.add(this.templateModelList);
//        templateList.add(this.templateLineList);
//        Clipboard.getDefault().setContents(l);
        //		}
          //2008040306PKY E 

    }

    protected boolean calculateEnabled() {
        //		try{
        List l = ProjectManager.getInstance().getSelectNodes();
      //PKY 08062601 S  오픈다이어그램 리스트로 계속 클릭하여 오픈할경우 Copy로직에 Null포인트발생하여 다시 재 오픈안되는문제
        if( l != null){
        ProjectManager.getInstance().initSelectLineModel();
        ProjectManager.getInstance().initTempCopyMap();
        if (l.size() > 0) {
        	if(l.size()==1){
        		Object obj = l.get(0);
        		//PKY 08091201 S
        		if(obj instanceof  LineEditPart || obj instanceof UMLNoteModel
        				||obj instanceof PortEditPart||obj instanceof ActivityParameterEditPart|| obj instanceof ExpansionNodeEditPart
        				||obj instanceof SubPartitonEditPart||obj instanceof ExitPointEditPart||obj instanceof EntryPointEditPart){
        			return false;
        		}
        		//PKY 08091201 E

        	}
            return true;
        }
        else
            return false;
        }
        return false;
        //PKY 08062601 E  오픈다이어그램 리스트로 계속 클릭하여 오픈할경우 Copy로직에 Null포인트발생하여 다시 재 오픈안되는문제
        //		ProjectManager.getInstance().setCopySource(null);
        //		if(l.size()>0){
        //			templateList = new java.util.ArrayList();
        //			templateModelList = new java.util.ArrayList();
        //			templateLineList = new java.util.ArrayList();
        //
        //			
        //			
        //
        //			for(int i=0;i<l.size();i++){
        //				Object obj = l.get(i);
        //				if(obj instanceof UMLEditPart){
        //					UMLEditPart uMLEditPart = (UMLEditPart)obj;
        //					
        ////					ProjectManager.getInstance().setTempCopyMap(((UMLModel)uMLEditPart.getModel()).getID(), uMLModel);
        //					int ok = ProjectManager.getInstance().getCopyModelType((UMLModel)uMLEditPart.getModel());
        //					UMLModel p = (UMLModel)uMLEditPart.getModel();
        //					if(ok>0 && p.getAcceptParentModel() instanceof N3EditorDiagramModel){
        //						UMLModel uMLModel = (UMLModel)uMLEditPart.getCloneModel();
        //						CopySimpleFactory copySimpleFactory = new CopySimpleFactory(null);
        //
        //						copySimpleFactory.setUMLModel(uMLModel);
        //						copySimpleFactory.calculateDifference(ProjectManager.getInstance().getCopySource());
        //						templateModelList.add(copySimpleFactory);
        //					
        //					}
        //					
        //				}
        //				
        //			}
        //			
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
        //			
        //			
        //			if(templateModelList
        //					.size()>0){
        //				return true;
        //			}
        //			
        //		}
        //		return false;
        //		}
        //		catch(Exception e){
        //			e.printStackTrace();
        ////			ProjectManager.getInstance().initSelectLineModel();
        ////			ProjectManager.getInstance().initTempCopyMap();
        //		}
        //		return false;
    }

    public void dispose() {
        Clipboard.getDefault().setContents(null);
        templateList = null;
    }
}
