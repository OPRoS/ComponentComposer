package kr.co.n3soft.n3com.model.comm.descriptor;

import java.util.List;

import kr.co.n3soft.n3com.edit.UMLEditPart;
import kr.co.n3soft.n3com.model.activity.FinalActiionModel;
import kr.co.n3soft.n3com.project.browser.UMLTreeModel;
import kr.co.n3soft.n3com.project.dialog.NewActionDialog;
import kr.co.n3soft.n3com.project.dialog.SelectBehaviorDialog;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Shell;

//PKY 08072401 S 액션 추가 시 타입묻는 다이어그램 오픈되지 않도록 기본타입 노멀

public class ActionTypeCellEditor extends DialogCellEditor {
    /** The default extent in pixels. */
    private static final int DEFAULT_EXTENT = 16;

    /** Gap between between image and text in pixels. */
    private static final int GAP = 6;

    /** The composite widget containing the color and RGB label widgets */
    private Composite composite;

    /** The label widget showing the current color. */
    private Label colorLabel;

    /** The label widget showing the RGB values. */
    private Label rgbLabel;

    /** The image. */
    private Image image;
    private String text = "";

    /** Internal class for laying out this cell editor. */
    private class ColorCellLayout extends Layout {
        public Point computeSize(Composite editor, int wHint, int hHint, boolean force) {
            if (wHint != SWT.DEFAULT && hHint != SWT.DEFAULT) {
                return new Point(wHint, hHint);
            }
            //            Point colorSize = colorLabel.computeSize(SWT.DEFAULT, SWT.DEFAULT,
            //                    force);
            Point rgbSize = rgbLabel.computeSize(SWT.DEFAULT, SWT.DEFAULT, force);
            return new Point(rgbSize.x, rgbSize.y);
        }

        public void layout(Composite editor, boolean force) {
            Rectangle bounds = editor.getClientArea();
            //            Point colorSize = colorLabel.computeSize(SWT.DEFAULT, SWT.DEFAULT,
            //                    force);
            Point rgbSize = rgbLabel.computeSize(SWT.DEFAULT, SWT.DEFAULT, force);
            int ty = (bounds.height - rgbSize.y) / 2;
            if (ty < 0) {
                ty = 0;
            }
            //            colorLabel.setBounds(-1, 0, colorSize.x, colorSize.y);
            rgbLabel.setBounds(bounds);
        }
    }


    /**
     * Creates a new color cell editor parented under the given control.
     * The cell editor value is black (<code>RGB(0,0,0)</code>) initially, and has no validator.
     * @param parent the parent control
     */
    public ActionTypeCellEditor(Composite parent) {
        super(parent, SWT.NONE);
    }

    /**
     * Creates a new color cell editor parented under the given control.
     * The cell editor value is black (<code>RGB(0,0,0)</code>) initially, and has no validator.
     * @param parent the parent control
     * @param style the style bits
     * @since 2.1
     */
    public ActionTypeCellEditor(Composite parent, int style) {
        super(parent, style);
        doSetValue("");
    }

    /**
     * Creates and returns the color image data for the given control
     * and RGB value. The image's size is either the control's item extent or the cell editor's default extent,
     * which is 16 pixels square.
     * @param w the control
     * @param color the color
     */
    //    private ImageData createColorImage(Control w, RGB color) {
    //
    //        GC gc = new GC(w);
    //        FontMetrics fm = gc.getFontMetrics();
    //        int size = fm.getAscent();
    //        gc.dispose();
    //
    //        int indent = 6;
    //        int extent = DEFAULT_EXTENT;
    //        if (w instanceof Table) {
    //			extent = ((Table) w).getItemHeight() - 1;
    //		} else if (w instanceof Tree) {
    //			extent = ((Tree) w).getItemHeight() - 1;
    //		} else if (w instanceof TableTree) {
    //			extent = ((TableTree) w).getItemHeight() - 1;
    //		}
    //
    //        if (size > extent) {
    //			size = extent;
    //		}
    //
    //        int width = indent + size;
    //        int height = extent;
    //
    //        int xoffset = indent;
    //        int yoffset = (height - size) / 2;
    //
    //        RGB black = new RGB(0, 0, 0);
    //        PaletteData dataPalette = new PaletteData(new RGB[] { black, black,
    //                color });
    //        ImageData data = new ImageData(width, height, 4, dataPalette);
    //        data.transparentPixel = 0;
    //
    //        int end = size - 1;
    //        for (int y = 0; y < size; y++) {
    //            for (int x = 0; x < size; x++) {
    //                if (x == 0 || y == 0 || x == end || y == end) {
    //					data.setPixel(x + xoffset, y + yoffset, 1);
    //				} else {
    //					data.setPixel(x + xoffset, y + yoffset, 2);
    //				}
    //            }
    //        }
    //
    //        return data;
    //    }

    /* (non-Javadoc)
     * Method declared on DialogCellEditor.
     */

    protected Control createContents(Composite cell) {
        try {
            Color bg = cell.getBackground();
            composite = new Composite(cell, getStyle());
            composite.setBackground(bg);
            composite.setLayout(new ColorCellLayout());
            rgbLabel = new Label(composite, SWT.LEFT);
            rgbLabel.setBackground(bg);
            rgbLabel.setFont(cell.getFont());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return composite;
    }

    /* (non-Javadoc)
     * Method declared on CellEditor.
     */

    public void dispose() {
        if (image != null) {
            image.dispose();
            image = null;
        }
        super.dispose();
    }

    /* (non-Javadoc)
     * Method declared on DialogCellEditor.
     */

    protected Object openDialogBox(Control cellEditorWindow) {
		Shell shell = new Shell();
    	if(ProjectManager.getInstance().getSelectNodes()!=null&&ProjectManager.getInstance().getSelectNodes().size()>0){
    		List list = ProjectManager.getInstance().getSelectNodes();
    		if(list.get(0) instanceof UMLEditPart){
    			UMLEditPart umlEdit =(UMLEditPart)list.get(0);
    			FinalActiionModel fam = (FinalActiionModel) umlEdit.getModel();
              NewActionDialog newActionDialog = new NewActionDialog(ProjectManager.getInstance().window.getShell());
              newActionDialog.setName(fam.getName());
              int i=newActionDialog.open();
//              switch(i) {
//				case IDialogConstants.FINISH_ID:
//					break;
//				case SWT.YES:
              if(i==0){
					int type = newActionDialog.getActionTYpe();
		          	  String name = newActionDialog.getActionName();            	
		            if (type == 2) {
		                SelectBehaviorDialog sbd = new SelectBehaviorDialog(ProjectManager.getInstance().window.getShell());
		                sbd.open();
		                fam.setCallBeavior(sbd.getSelectedUMLModel());
		            }
		            fam.setName(name);
		            fam.setType(type);
		            if(fam.getStereotype()!=null)
		            fam.setStereotype(fam.getStereotype());//PKY 08072401 S Action Action 저장 불러오기시 그림이 안되는문제
              }
//				case SWT.NO:    
//					return null;
//				}
                    	  
    		}
    	}else if(ProjectManager.getInstance().getModelBrowser()!=null&&ProjectManager.getInstance().getModelBrowser().getViewer().getSelection()!=null){
    		TreeSelection treeSelection  = (TreeSelection)ProjectManager.getInstance().getModelBrowser().getViewer().getSelection();
    		UMLTreeModel strcutured = (UMLTreeModel)treeSelection.getFirstElement();
    		FinalActiionModel fam = (FinalActiionModel)strcutured.getRefModel();
              NewActionDialog newActionDialog = new NewActionDialog(ProjectManager.getInstance().window.getShell());
            newActionDialog.setName(fam.getName());
            int i=newActionDialog.open();
//            switch(i) {
//				case IDialogConstants.FINISH_ID:
//					break;
//				case SWT.YES:
            if(i==0){
					int type = newActionDialog.getActionTYpe();
		          	  String name = newActionDialog.getActionName();            	
		            if (type == 2) {
		                SelectBehaviorDialog sbd = new SelectBehaviorDialog(ProjectManager.getInstance().window.getShell());
		                sbd.open();
		                fam.setCallBeavior(sbd.getSelectedUMLModel());
		            }
		            fam.setName(name);
		            fam.setType(type);
		            if(fam.getStereotype()!=null)
		            fam.setStereotype(fam.getStereotype());//PKY 08072401 S Action Action 저장 불러오기시 그림이 안되는문제
            }
    	}
return null;

    }

    /* (non-Javadoc)
     * Method declared on DialogCellEditor.
     */

    protected void updateContents(Object value) {
        Object obj = value;
        //       if(obj!=null)
        //        rgbLabel
        //                .setText((String)value);//$NON-NLS-4$//$NON-NLS-3$//$NON-NLS-2$//$NON-NLS-1$
    }
}
