package kr.co.n3soft.n3com.rcp.actions;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import kr.co.n3soft.n3com.UMLEditor;
import kr.co.n3soft.n3com.edit.UMLDiagramEditPart;
import kr.co.n3soft.n3com.lang.N3Messages;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.diagram.N3EditorDiagramModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;
//import kr.co.n3soft.n3com.report.builder.ExportImage;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.SWTGraphics;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.editparts.LayerManager;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindow;

/**
 * 20080718 KDI s 다이어그램 이미지 저장 로직 관련 클래스 생성
 * @author Administrator
 *
 */
public class SaveImageAction extends SelectionAction {

	IEditorPart editorPart;
	
	public SaveImageAction(IEditorPart editor) {	
		super(editor);
		this.setId(N3Messages.POPUP_SAVE_IMAGE);
		this.setText(N3Messages.POPUP_SAVE_IMAGE);
		setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getImage(301)));
		editorPart = editor;
//		this.setAccelerator(SWT.CTRL|SWT.SHIFT|'s');
	}
	protected boolean calculateEnabled() {

		List selection = getSelectedObjects();
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
		return false;
	}
	
	public void run() {
		try{
			UMLEditor uMLEditor = ProjectManager.getInstance().getUMLEditor();	
			if(uMLEditor != null){
				N3EditorDiagramModel dgm = uMLEditor.getDiagram();
				
				FileDialog dialog = new FileDialog(editorPart.getEditorSite().getShell(), SWT.SAVE);
				dialog.setFilterExtensions(new String[] {"*.jpg","*.png","*.*"});
				dialog.setFilterPath(ProjectManager.getInstance().getCurrentProjectPath());
				String btn = dialog.open(); //20080722 KDI s
				
				String str = dialog.getFilterPath() + java.io.File.separator +dialog.getFileName();
				
				if(btn != null && str != null && str.length()>0){ //20080722 KDI s
					GraphicalViewer viewer = uMLEditor.getScrollingGraphicalViewer();
					ScalableFreeformRootEditPart rootEditPart = (ScalableFreeformRootEditPart) viewer.getEditPartRegistry().get(LayerManager.ID); 
					IFigure rootFigure = ((LayerManager) rootEditPart).getLayer(LayerConstants.PRINTABLE_LAYERS);
					
					int imageType = SWT.IMAGE_BMP;
					if (str.toLowerCase().endsWith(".jpg")) {
						imageType = SWT.IMAGE_JPEG;
					} 
					else if (str.toLowerCase().endsWith(".png")) {
						imageType = SWT.IMAGE_PNG;
					}
//					else if (str.toLowerCase().endsWith(".gif")) {
//						imageType = SWT.IMAGE_GIF;
//					}
					
					Rectangle r = rootFigure.getBounds();					
					ByteArrayOutputStream result = new ByteArrayOutputStream();
					Image image = null;
					GC gc = null;
					Graphics g = null;
					try {
						image = new Image(null, r.width, r.height);
						gc = new GC(image);
						g = new SWTGraphics(gc);
						g.translate(r.x * -1, r.y * -1);
						rootFigure.paint(g);
						ImageLoader imageLoader = new ImageLoader();
						imageLoader.data = new ImageData[] { image.getImageData() };
						imageLoader.save(result, imageType);
					} finally {
						if (g != null) {
							g.dispose();
						}
						if (gc != null) {
							gc.dispose();
						}
						if (image != null) {
							image.dispose();
						}
					}
					
						
					java.io.File file = new java.io.File(str.trim());
					if(file.exists())
						file.delete();
					
					FileOutputStream fos = new FileOutputStream(file.toString());
					fos.write(result.toByteArray());
					fos.flush();
					fos.close();
					
				}				
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}

