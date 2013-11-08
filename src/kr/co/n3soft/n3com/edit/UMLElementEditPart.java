package kr.co.n3soft.n3com.edit;

import java.beans.PropertyChangeEvent;

import kr.co.n3soft.n3com.comm.figures.UMLElementFigure;
import kr.co.n3soft.n3com.edit.policy.UMLElementDirectEditPolicy;
import kr.co.n3soft.n3com.edit.policy.UMLElementEditPolicy;
import kr.co.n3soft.n3com.manager.UMLElementEditManager;
import kr.co.n3soft.n3com.model.comm.ElementLabelModel;
import kr.co.n3soft.n3com.model.comm.UMLElementModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.AccessibleEditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.Tool;
import org.eclipse.gef.examples.logicdesigner.LogicMessages;
import org.eclipse.gef.tools.MarqueeSelectionTool;
import org.eclipse.gef.ui.parts.ScrollingGraphicalViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.accessibility.AccessibleControlEvent;
import org.eclipse.swt.accessibility.AccessibleEvent;
import org.eclipse.swt.graphics.Font;

public class UMLElementEditPart extends UMLEditPart {
    protected AccessibleEditPart createAccessible() {
        return new AccessibleGraphicalEditPart() {
            public void getValue(AccessibleControlEvent e) {
                e.result = getElementLabelModel().getLabelContents();
            }
            public void getName(AccessibleEvent e) {
                e.result = LogicMessages.LogicPlugin_Tool_CreationTool_LogicLabel;
            }
        };
    }

    protected void createEditPolicies() {
        super.createEditPolicies();
        installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, null);
        installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new UMLElementDirectEditPolicy());
        installEditPolicy(EditPolicy.COMPONENT_ROLE, new UMLElementEditPolicy());
    }

    protected IFigure createFigure() {
        UMLElementFigure label = new UMLElementFigure(this.getElementLabelModel().getLabelContents(),
            this.getElementLabelModel().align);
        // WJH 090801
//        ProjectManager.getInstance().setDefaultFont(label.getFont());
        String isCom = "";
        if(this.getElementLabelModel().getAcceptParentModel() != null){
        	if(this.getElementLabelModel().getAcceptParentModel().getAcceptParentModel() != null){
        		UMLElementModel uem =  (UMLElementModel)this.getElementLabelModel().getAcceptParentModel().getAcceptParentModel();
        		System.out.println("====== model : "+uem.toString());
        		System.out.println(label.getText());
        		isCom = uem.toString();
        		
        	}
        }
        if(isCom.equals("")){
        	label.setForegroundColor(ColorConstants.black);
        	label.setFont(null);
        }
        else{
        	label.setForegroundColor(ColorConstants.white);
        	//110823 SDM S - OS버전별로 폰트가 틀렸던 부분 해결 >> 기본폰트로 폰트 수정
        	Font boldFont = new Font(ProjectManager.getInstance().window.getShell().getDisplay(), "Verdana", 10, SWT.BOLD); // WJH 090817
        	//110823 SDM E - OS버전별로 폰트가 틀렸던 부분 해결 >> 기본폰트로 폰트 수정
        	label.setFont(boldFont);
        }
//        if(ProjectManager.getInstance().isComponent()){
//        	label.setForegroundColor(ColorConstants.white);
//	        Font newFont = new Font(ProjectManager.getInstance().window.getShell().getDisplay(), "Floralies", 15, SWT.BOLD);
//	        label.setFont(newFont);
//	        System.out.println("Component Label");
//        }
//        else{
//        	label.setForegroundColor(ColorConstants.black);
//        	label.setFont(ProjectManager.getInstance().getDefaultFont());
//        	System.out.println("Port Label");
//        }
        
     // WJH 090801
        return label;
    }

    private ElementLabelModel getElementLabelModel() {
        return (ElementLabelModel)getModel();
    }

    private void performDirectEdit() {
    	ElementLabelModel em = (ElementLabelModel)getModel();
    	if(em.isReadOnly())
    		return;
    	if("Monitoring".equals(em.getLabelContents())){
    		return;
    	}
    	if("Lifecycle".equals(em.getLabelContents())){
    		return;
    	}
    
        new UMLElementEditManager(this, new UMLElementCellEditorLocator((UMLElementFigure)getFigure())).show();
    }

    public void performRequest(Request request) {
        if (request.getType() == RequestConstants.REQ_DIRECT_EDIT)
            performDirectEdit();
    }

    public void performRequest() {
        //		if (request.getType() == RequestConstants.REQ_DIRECT_EDIT)
        performDirectEdit();
    }

    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equalsIgnoreCase("labelContents")) //$NON-NLS-1$
                refreshVisuals();
        else
            super.propertyChange(evt);
    }

    protected void refreshVisuals() {
        ((UMLElementFigure)getFigure()).setText(getElementLabelModel().getLabelContents());
        if (getUMLModel() instanceof ElementLabelModel) {
            ElementLabelModel elementLabelModel = (ElementLabelModel)getUMLModel();
            if (elementLabelModel.getParentLayout() == 1) {
                if (elementLabelModel.getLayout() != null) {
                    ((GraphicalEditPart)getParent()).setLayoutConstraint(this, getFigure(), elementLabelModel.getLayout());
                }
            }
            else {
                super.refreshVisuals();
            }
        }
        //		
    }

    public void setSelected(int value) {
        ScrollingGraphicalViewer viewer = ProjectManager.getInstance().getUMLEditor().getScrollingGraphicalViewer();
        ;
        Tool tool = ProjectManager.getInstance().getUMLEditor().getUMLDefaultEditDomain().getActiveTool();
        //	if(tool instanceof MarqueeSelectionTool){
        //		return;
        //	}
        //	else{
        if (!(tool instanceof MarqueeSelectionTool) && !ProjectManager.getInstance().getIsDoubleClick()) {
            if (value == 2) {
                ProjectManager.getInstance().setUMLElementEditPart(this);
//                if(this.getModel())
                viewer.select(this.getParent());
//                super.setSelected(value);
            }
            else {
                super.setSelected(value);
            }
        }
        else {
            //			if(tool instanceof MarqueeSelectionTool){
            //				super.setSelected(0);
            //			}
            //			else{
            super.setSelected(value);
            ProjectManager.getInstance().initIsDoubleClick();
            //			}
        }
        //	}
        //		this.getParent().setSelected(value);
        //		super.setSelected(value);
        //		if (selected == value)
        //			return;
        //		selected = value;
        //		fireSelectionChanged();
    }
}
