package kr.co.n3soft.n3com.edit;

import kr.co.n3soft.n3com.figures.UMLNoteFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.tools.CellEditorLocator;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.widgets.Text;

public class UMLNoteCellEditorLocator implements CellEditorLocator {
    private UMLNoteFigure stickyNote;

    public UMLNoteCellEditorLocator(UMLNoteFigure stickyNote) {
        setLabel(stickyNote);
    }

    public void relocate(CellEditor celleditor) {
        Text text = (Text)celleditor.getControl();
        //		text.set
        Rectangle rect = stickyNote.getClientArea();
        stickyNote.translateToAbsolute(rect);
        org.eclipse.swt.graphics.Rectangle trim = text.computeTrim(0, 0, 0, 0);
        rect.translate(trim.x, trim.y);
        rect.width += trim.width;
        rect.height += trim.height;
        text.setBounds(rect.x, rect.y, rect.width, rect.height);
    }

    /** Returns the stickyNote figure. */
    protected UMLNoteFigure getLabel() {
        return stickyNote;
    }

    /**
     * Sets the Sticky note figure.
     * @param stickyNote The stickyNote to set
     */
    protected void setLabel(UMLNoteFigure stickyNote) {
        this.stickyNote = stickyNote;
    }
}
