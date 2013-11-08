package kr.co.n3soft.n3com.dnd;

import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.dnd.TemplateTransferDropTargetListener;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTargetEvent;
//20080729IJS
public class OutLineTemplateTransferDropTargetListener extends TemplateTransferDropTargetListener {
    public OutLineTemplateTransferDropTargetListener(EditPartViewer viewer) {
        super(viewer);
    }

    public void dragOver(DropTargetEvent event) {
        try {
            super.dragOver(event);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void drop(DropTargetEvent event) {
    	System.out.println("dddddddddddddddddd");
    	
//    	if(this.getTargetEditPart() instanceof UMLDiagramEditPart){
//    		UMLDiagramEditPart ud = (UMLDiagramEditPart)this.getTargetEditPart();
//    		UMLModel um = (UMLModel)ud.getModel();
//    			System.out.println("dddd");
//    	}
    	
    	super.drop(event);
    }
    protected void handleDragOperationChanged() {
    	getCurrentEvent().detail = DND.DROP_COPY;
    	super.handleDragOperationChanged();
    }
    protected void handleDragOver() {
    	getCurrentEvent().detail = DND.DROP_COPY;
    	getCurrentEvent().feedback = DND.FEEDBACK_SCROLL | DND.FEEDBACK_EXPAND;
    	super.handleDragOver();
    }

    /**
     * Overridden to select the created object.
     * @see org.eclipse.gef.dnd.AbstractTransferDropTargetListener#handleDrop()
     */
    protected void handleDrop() {
    	super.handleDrop();
//    	selectAddedObject();
    }

}

