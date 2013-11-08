package kr.co.n3soft.n3com.model.comm;

import org.eclipse.gef.examples.logicdesigner.model.Circuit;
import org.eclipse.swt.graphics.Image;

import kr.co.n3soft.n3com.model.umlclass.AttributeEditableTableItem;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

public class AttributeElementModel extends ElementLabelModel {
    private AttributeEditableTableItem att = null;
//    public  Image LOGIC_ICON = createImage(Circuit.class, "icons/circuit16.gif"); //$NON-NLS-1$

    public AttributeElementModel(AttributeEditableTableItem p) {
    	LOGIC_LABEL_ICON	= ProjectManager.getInstance().getImage(ProjectManager.getInstance().getElementType(this, -1));
        this.att = p;
        this.setLabelContents(att.toString());
        if (p.id != null) {
            this.setID(p.id);
        }
        else {
            p.id = this.getID();
        }
    }

    public void setAttributeEditableTableItem(AttributeEditableTableItem p) {
        this.att = p;
    }

    public AttributeEditableTableItem getAttributeEditableTableItem() {
        return this.att;
    }

    public String toString() {
        if (att != null)
            return this.att.toString();
        else
            return "";
    }
}
