package kr.co.n3soft.n3com.model.comm;

import java.io.IOException;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.gef.examples.logicdesigner.LogicMessages;
import org.eclipse.gef.examples.logicdesigner.model.LED;
import org.eclipse.swt.graphics.Image;

public class UMLNoteModel extends UMLModel {
    static final long serialVersionUID = 1;
    private String text = LogicMessages.LogicPlugin_Tool_CreationTool_LogicLabel;
    private static Image LOGIC_LABEL_ICON = createImage(LED.class, "icons/label16.gif"); //$NON-NLS-1$
    private static int count;

    public UMLNoteModel() {
        super();
        size.width = 100;
        size.height = 50;
    }

    public String getLabelContents() {
        return text;
    }

    public Image getIconImage() {
        return LOGIC_LABEL_ICON;
    }

    protected String getNewID() {
        return Integer.toString(count++);
    }

    //	public Dimension getSize(){
    //		return new Dimension(size.width, -1);
    //	}
    private void readObject(java.io.ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
    }

    //
    //	public void setSize(Dimension d) {
    //		d.height = -1;
    //		super.setSize(d);
    //	}
    public void setLabelContents(String s) {
        text = s;
        firePropertyChange("labelContents", null, text); //$NON-NLS-2$//$NON-NLS-1$
    }

    public String toString() {
        return LogicMessages.LogicPlugin_Tool_CreationTool_LogicLabel + " #" + getID() + " " //$NON-NLS-1$ //$NON-NLS-2$
            + LogicMessages.PropertyDescriptor_Label_Text + "=" + getLabelContents(); //$NON-NLS-1$
    }
}
