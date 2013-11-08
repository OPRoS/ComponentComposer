package kr.co.n3soft.n3com.model.comm.descriptor;

//import kr.co.n3soft.n3com.model.comm.descriptor.AttributeCellEditor.ColorCellLayout;
import kr.co.n3soft.n3com.lang.N3Messages;
import kr.co.n3soft.n3com.model.activity.FinalModel;
import kr.co.n3soft.n3com.model.activity.HPartitionModel;
import kr.co.n3soft.n3com.model.activity.InitialModel;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.usecase.FinalBoundryModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.MessageBox;

public class UMLColorCellEditor extends DialogCellEditor {
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
    public UMLColorCellEditor(Composite parent) {
        super(parent, SWT.NONE);
    }

    /**
     * Creates a new color cell editor parented under the given control.
     * The cell editor value is black (<code>RGB(0,0,0)</code>) initially, and has no validator.
     * @param parent the parent control
     * @param style the style bits
     * @since 2.1
     */
    public UMLColorCellEditor(Composite parent, int style) {
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
    	RGB rgb= null;
//		Shell shell = new Shell();
		//PKY 08090907 S
    	if(ProjectManager.getInstance()!=null && ProjectManager.getInstance().getSelectPropertyUMLElementModel()!=null){
    		UMLModel list = (UMLModel) ProjectManager.getInstance().getSelectPropertyUMLElementModel();

    			 if(list instanceof FinalBoundryModel ||list instanceof HPartitionModel){
    				UMLModel excut=(UMLModel)	list;
    				if(excut.getBackGroundColor().getRed()!=255||excut.getBackGroundColor().getGreen()!=255||excut.getBackGroundColor().getBlue()!=255){
    					MessageBox dialog = new MessageBox(ProjectManager.getInstance().window.getShell(),SWT.ICON_QUESTION|SWT.YES|SWT.NO);
    					dialog.setText("Message");
    					dialog.setMessage(N3Messages.DIALOG_COLOR_DEFAULT_MESSAGE);
    					int i=dialog.open();
    					switch(i) {
    					case IDialogConstants.FINISH_ID:
    						System.out.println("Scrip Wizard Finish!!");
    						break;
    					case SWT.YES:
    						ColorDialog clordialog = new ColorDialog(ProjectManager.getInstance().window.getShell());
    						rgb= clordialog.open();
    						return rgb;
    					case SWT.NO:    
    						rgb= new RGB(255,255,255);
    						return rgb;
    					}
    				}else{
    					ColorDialog clordialog = new ColorDialog(ProjectManager.getInstance().window.getShell());
    					rgb= clordialog.open();
						return rgb;
    				}
    			}else if(list instanceof InitialModel ||list instanceof  FinalModel){//PKY 08052601 S Fragment 아무런 내용없이 저장 불러오기 할 경우 다이얼로그창이 다시 뜨는 문제

    				UMLModel excut=(UMLModel)	list;
    				if(excut.getBackGroundColor().getRed()!=0||excut.getBackGroundColor().getGreen()!=0||excut.getBackGroundColor().getBlue()!=0){
    					MessageBox dialog = new MessageBox(ProjectManager.getInstance().window.getShell(),SWT.ICON_QUESTION|SWT.YES|SWT.NO);
    					dialog.setText("Message");
    					dialog.setMessage(N3Messages.DIALOG_COLOR_DEFAULT_MESSAGE);
    					int i=dialog.open();
    					switch(i) {
    					case IDialogConstants.FINISH_ID:
    						System.out.println("Scrip Wizard Finish!!");
    						break;
    					case SWT.YES:
    						ColorDialog clordialog = new ColorDialog(ProjectManager.getInstance().window.getShell());
    						rgb= clordialog.open();
    						return rgb;
    					case SWT.NO:    
    						rgb= new RGB(0,0,0);
    						return rgb;
    					}
    				}else{
    					ColorDialog clordialog = new ColorDialog(ProjectManager.getInstance().window.getShell());
    					rgb= clordialog.open();
						return rgb;
    				}
    			
    			}//PKY 08052601 E Fragment 아무런 내용없이 저장 불러오기 할 경우 다이얼로그창이 다시 뜨는 문제
    			else {
    				UMLModel excut=(UMLModel)	list;
    				if(excut.getBackGroundColor().getRed()!=255||excut.getBackGroundColor().getGreen()!=247||excut.getBackGroundColor().getBlue()!=205){
    					MessageBox dialog = new MessageBox(ProjectManager.getInstance().window.getShell(),SWT.ICON_QUESTION|SWT.YES|SWT.NO);
    					dialog.setText("Message");
    					dialog.setMessage(N3Messages.DIALOG_COLOR_DEFAULT_MESSAGE);
    					int i=dialog.open();
    					rgb=null;
    					switch(i) {
    					case IDialogConstants.FINISH_ID:
    						System.out.println("Scrip Wizard Finish!!");
    						break;
    					case SWT.YES:
    						ColorDialog clordialog = new ColorDialog(ProjectManager.getInstance().window.getShell());
    						rgb= clordialog.open();
    						return rgb;
    					case SWT.NO:    
    						rgb= new RGB(255,247,205);
    						return rgb;
    					}
    				}else{

    					ColorDialog clordialog = new ColorDialog(ProjectManager.getInstance().window.getShell());
    					rgb= clordialog.open();
						return rgb;
    				}
    			}
    		
    	}
return rgb;

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
