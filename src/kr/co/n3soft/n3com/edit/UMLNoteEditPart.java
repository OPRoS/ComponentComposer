package kr.co.n3soft.n3com.edit;

import java.beans.PropertyChangeEvent;
import kr.co.n3soft.n3com.edit.policy.UMLElementEditPolicy;
import kr.co.n3soft.n3com.edit.policy.UMLNoteDirectEditPolicy;
import kr.co.n3soft.n3com.figures.UMLNoteFigure;
import kr.co.n3soft.n3com.manager.UMLNoteEditManager;
import kr.co.n3soft.n3com.model.comm.UMLNoteModel;
import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.AccessibleEditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.examples.logicdesigner.LogicMessages;
import org.eclipse.swt.accessibility.AccessibleControlEvent;
import org.eclipse.swt.accessibility.AccessibleEvent;

public class UMLNoteEditPart extends UMLEditPart {
    protected AccessibleEditPart createAccessible() {
        return new AccessibleGraphicalEditPart() {
            public void getValue(AccessibleControlEvent e) {
                e.result = getLogicLabel().getLabelContents();
            }
            public void getName(AccessibleEvent e) {
                e.result = LogicMessages.LogicPlugin_Tool_CreationTool_LogicLabel;
            }
        };
    }

    protected void createEditPolicies() {
        super.createEditPolicies();
        //		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, null);		
        installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new UMLNoteDirectEditPolicy());
        installEditPolicy(EditPolicy.COMPONENT_ROLE, new UMLElementEditPolicy());
    }

    protected IFigure createFigure() {
        UMLNoteFigure label = new UMLNoteFigure();
        return label;
    }

    private UMLNoteModel getLogicLabel() {
        return (UMLNoteModel)getModel();
    }

    private void performDirectEdit() {
        new UMLNoteEditManager(this, new UMLNoteCellEditorLocator((UMLNoteFigure)getFigure())).show();
    }

    public void performRequest(Request request) {
        if (request.getType() == RequestConstants.REQ_DIRECT_EDIT)
            performDirectEdit();
    }

    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equalsIgnoreCase("labelContents")) //$NON-NLS-1$
                refreshVisuals();
        else
            super.propertyChange(evt);
    }

    protected void refreshVisuals() {
        ((UMLNoteFigure)getFigure()).setText(getLogicLabel().getLabelContents());
        super.refreshVisuals();
    }
}
