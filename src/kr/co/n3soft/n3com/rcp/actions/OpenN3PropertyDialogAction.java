package kr.co.n3soft.n3com.rcp.actions;

import kr.co.n3soft.n3com.projectmanager.CompAssemManager;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.core.runtime.Preferences;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.jface.preference.PreferenceNode;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchWindow;

import uuu.Activator;
import uuu.preferences.SamplePreferencePage;

public class OpenN3PropertyDialogAction extends Action {
	
	private static final int OK_ID = 0;
	private static final int CANCEL_ID = 1;
	private static final int RESET_ID = IDialogConstants.NO_TO_ALL_ID + 1;
	IWorkbenchWindow workbenchWindow;
	public static   int ID=0;
	IEditorInput input = null;
	String sourcePath =""; 
	
	public OpenN3PropertyDialogAction(IWorkbenchWindow window) {
		setId("OpenN3PropertyDialogAction");
		this.setText("Preferences");
		workbenchWindow = window;
		setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getImage(302)));//PKY 08070101 S �˾� �޴� �̹��� ����
	}
	
	public OpenN3PropertyDialogAction() {
		setId("OpenN3PropertyDialogAction");
		this.setText("Preferences");
//		workbenchWindow = window;
		setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getImage(302)));//PKY 08070101 S �˾� �޴� �̹��� ����
	}
	
	public void run() {
		try{
			
			this.sourcePath = ProjectManager.getInstance().getSourceFolder();
			//PKY 08080501 S ����ڰ� �÷������� ���� �� ���ֵ��� ����
			PreferenceManager manager = new PreferenceManager();
//			PreferenceNode one = new PreferenceNode("one", "One", uuu.Activator.getImageDescriptor("/icons/newproject.gif"),
//					N3DefaultPage.class.getName());
			/**
			 * Source Code Setting
			 */
			SamplePreferencePage page = new SamplePreferencePage();
			page.setTitle("OPRoS_CC_Directory Preference");//PKY 08082201 S ������Ƽ ������ �÷��� ȭ���� ���� ������� �����߻�
			manager.addToRoot(new PreferenceNode(page.getTitle(),page));  
//			Activator.getDefault().getPreferenceStore().
			
//			Preferences pref = Activator.getDefault().getPluginPreferences();
		

			/**
			 * Color Setting
			 */
			
//			N3StandardColorsPage colorPage = new N3StandardColorsPage();
//			colorPage.setTitle(N3Messages.DIALOG_STANDARD_COLORS);
//			manager.addToRoot(new PreferenceNode(colorPage.getTitle(),colorPage));
			//PKY 08080501 E ����ڰ� �÷������� ���� �� ���ֵ��� ����
			
			PreferenceDialog property = new PreferenceDialog(null,manager);
			property.setMinimumPageSize(350,300);
			
			
			
			int i =property.open();
			
			buttonPressed(i);
			
			
		
			 
		}
		catch(Exception e){
			ProjectManager.getInstance().writeLog(null, e);
			e.printStackTrace();
		}
	    

		
	}
	//PKY 08081101 S N3Com ������Ƽ�� ���� �� OK��ư�� ����� ������ ����ǵ��� �������� �������� ���Ḧ �������� ���α׷� ������ ����ǵ��� �Ǿ��־���
	protected void buttonPressed(int buttonId) {
		try{
		switch (buttonId) {
		case IDialogConstants.OK_ID: {
			ProjectManager.getInstance().writeConfig();
			
			if(this.sourcePath.equals(ProjectManager.getInstance().getSourceFolder())){
				return;
			}
			
			CompAssemManager.getInstance().getModelStore().clear();
			CompAssemManager.getInstance().getViewStore().clear();
			CompAssemManager.getInstance().getDirModel().clear();
			CompAssemManager.getInstance().getDirView().clear();
//			CompAssemManager.getInstance().makeLib(ProjectManager.getInstance().getModelBrowser().getRootLibTreeModel());
//			ProjectManager.getInstance().getModelBrowser().refresh(ProjectManager.getInstance().getModelBrowser().getRootLibTreeModel());
			ProgressMonitorDialog progress =  new ProgressMonitorDialog(ProjectManager.getInstance().window.getShell());
////			LoadLibProgress lp = new LoadLibProgress(true);
			CompAssemManager.getInstance().setRtm(ProjectManager.getInstance().getModelBrowser().getRootLibTreeModel());
			CompAssemManager.getInstance().setRunType(1);
			try{
				CompAssemManager.getInstance().deleteModel(ProjectManager.getInstance().getModelBrowser().getRootLibTreeModel());
			progress.run(true, true, CompAssemManager.getInstance());
			}
			catch(Exception e){
				e.printStackTrace();
			}
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