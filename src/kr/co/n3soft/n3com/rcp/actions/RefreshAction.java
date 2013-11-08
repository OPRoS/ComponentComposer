package kr.co.n3soft.n3com.rcp.actions;

import kr.co.n3soft.n3com.project.browser.RootLibTreeModel;
import kr.co.n3soft.n3com.project.browser.RootPackageTreeModel;
import kr.co.n3soft.n3com.project.browser.UMLTreeModel;
import kr.co.n3soft.n3com.projectmanager.CompAssemManager;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchWindow;

public class RefreshAction extends Action {
	
	private static final int OK_ID = 0;
	private static final int CANCEL_ID = 1;
	private static final int RESET_ID = IDialogConstants.NO_TO_ALL_ID + 1;
	IWorkbenchWindow workbenchWindow;
	public static   int ID=0;
	IEditorInput input = null;
	public RefreshAction(IWorkbenchWindow window) {
		setId("RefreshAction");
		this.setText("refresh");
		workbenchWindow = window;
		setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getImage(302)));//PKY 08070101 S 팝업 메뉴 이미지 삽입
	}
	
	public RefreshAction() {
		setId("RefreshAction");
		this.setText("Refresh");
//		workbenchWindow = window;
		setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getImage(330)));//PKY 08070101 S 팝업 메뉴 이미지 삽입
	}
	
	public void run() {
		try{
			
			
			buttonPressed(IDialogConstants.OK_ID);
			
			
		
			 
		}
		catch(Exception e){
			ProjectManager.getInstance().writeLog(null, e);
			e.printStackTrace();
		}
	    

		
	}
	//PKY 08081101 S N3Com 프로퍼티를 오픈 후 OK버튼을 누루면 내용이 저장되도록 기존에는 정상적인 종료를 했을때만 프로그램 내용이 저장되도록 되어있었음
	protected void buttonPressed(int buttonId) {
		try{
		switch (buttonId) {
		case IDialogConstants.OK_ID: {
			IStructuredSelection sel = (IStructuredSelection)ProjectManager.getInstance().getModelBrowser().getViewer().getSelection();
			Object selectModel = sel.getFirstElement();
			
//			ProjectManager.getInstance().getSearchModel()
//			ProjectManager.getInstance().writeConfig();
//			CompAssemManager.getInstance().getModelStore().clear();
//			CompAssemManager.getInstance().getViewStore().clear();
//			CompAssemManager.getInstance().getDirModel().clear();
//			CompAssemManager.getInstance().getDirView().clear();
////			CompAssemManager.getInstance().makeLib(ProjectManager.getInstance().getModelBrowser().getRootLibTreeModel());
////			ProjectManager.getInstance().getModelBrowser().refresh(ProjectManager.getInstance().getModelBrowser().getRootLibTreeModel());
//			ProgressMonitorDialog progress =  new ProgressMonitorDialog(ProjectManager.getInstance().window.getShell());
////			LoadLibProgress lp = new LoadLibProgress(true);
			ProgressMonitorDialog progress =  new ProgressMonitorDialog(ProjectManager.getInstance().window.getShell());
			if(selectModel instanceof UMLTreeModel){
				if(selectModel instanceof RootLibTreeModel){
					 Display dis = Display.getDefault();		
						Shell newShell = new Shell(dis, SWT.DIALOG_TRIM);
						MessageBox dialog = new MessageBox(newShell,SWT.ICON_QUESTION|SWT.YES|SWT.NO);
						dialog.setText("Message");
						dialog.setMessage("Library를 새로 로드  하시겠습니까?");
						int i=dialog.open();
						switch(i) {
						case 64:
							
							CompAssemManager.getInstance().setTotal(true);
							break;
						case SWT.NO:
//							CompAssemManager.getInstance().setTotal(false);
							return;
//							break;
						case SWT.CANCEL:
							break;
						}
						ProjectManager.getInstance().writeConfig();
						CompAssemManager.getInstance().getModelStore().clear();
						CompAssemManager.getInstance().getViewStore().clear();
						CompAssemManager.getInstance().getDirModel().clear();
						CompAssemManager.getInstance().getDirView().clear();
//						CompAssemManager.getInstance().makeLib(ProjectManager.getInstance().getModelBrowser().getRootLibTreeModel());
//						ProjectManager.getInstance().getModelBrowser().refresh(ProjectManager.getInstance().getModelBrowser().getRootLibTreeModel());
						
						
					CompAssemManager.getInstance().setRtm(ProjectManager.getInstance().getModelBrowser().getRootLibTreeModel());
					CompAssemManager.getInstance().setRunType(1);
//					CompAssemManager.getInstance().setTotal(true);
					try{
						CompAssemManager.getInstance().deleteModel2(ProjectManager.getInstance().getModelBrowser().getRootLibTreeModel());
					progress.run(true, true, CompAssemManager.getInstance());
					}
					catch(Exception e){
						e.printStackTrace();
					}
//					CompAssemManager.getInstance().setTotal(false);
					
				}
				else if(selectModel instanceof RootPackageTreeModel){
					RootPackageTreeModel rpt = (RootPackageTreeModel)selectModel;
//					CompAssemManager.getInstance().setRtm();
					CompAssemManager.getInstance().setRpt(rpt);
					CompAssemManager.getInstance().setRunType(4);
					CompAssemManager.getInstance().setTotal(true);
					try{
						CompAssemManager.getInstance().deleteModel2(rpt);
					progress.run(true, true, CompAssemManager.getInstance());
					}
					catch(Exception e){
						e.printStackTrace();
					}
				}
				
			}
			CompAssemManager.getInstance().setTotal(false);
			CompAssemManager.getInstance().setRtm(ProjectManager.getInstance().getModelBrowser().getRootLibTreeModel());
//			CompAssemManager.getInstance().setRunType(1);
//			try{
//				CompAssemManager.getInstance().deleteModel2(ProjectManager.getInstance().getModelBrowser().getRootLibTreeModel());
//			progress.run(true, true, CompAssemManager.getInstance());
//			}
//			catch(Exception e){
//				e.printStackTrace();
//			}
//			String xmlPath = "d:\\lib3\\HelloMaker.xml";
//			CompAssemManager.getInstance().setModel(xmlPath);
			
			ProjectManager.getInstance().getModelBrowser().refresh(ProjectManager.getInstance().getModelBrowser().getRootLibTreeModel());
			
			
		}
		case IDialogConstants.CANCEL_ID: {
			System.out.print("");
		}
		case IDialogConstants.HELP_ID: {
			System.out.print("");
		}
		}
		}catch(Exception e){
		e.printStackTrace();
		}
	}
	

}
