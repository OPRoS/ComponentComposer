package uuu.preferences;

import kr.co.n3soft.n3com.projectmanager.CompAssemManager;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;



import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.jface.preference.FileFieldEditor;
import org.eclipse.jface.preference.PathEditor;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import uuu.Activator;

/**
 * This class represents a preference page that
 * is contributed to the Preferences dialog. By 
 * subclassing <samp>FieldEditorPreferencePage</samp>, we
 * can use the field support built into JFace that allows
 * us to create a page that is small and knows how to 
 * save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They
 * are stored in the preference store that belongs to
 * the main plug-in class. That way, preferences can
 * be accessed directly via the preference store.
 */

public class SamplePreferencePage
	extends FieldEditorPreferencePage
	implements IWorkbenchPreferencePage {
	private RadioGroupFieldEditor deployOp;
//	private PathEditor otherInc;

	public SamplePreferencePage() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("Folder Setting Page of OPRoS Component Composer");	//110905 SDM
	}
	//KHG 10.04.29 ���̺귯�� ����Ǹ� �ٷ� ���� �ϱ� ����..
	DirectoryFieldEditor p_lib,p_tp;
	FileFieldEditor p_m,p_s,p_t1,p_t2;
	
	public void createFieldEditors() {
		
		//110905 SDM �����н� �κ� �������� ���� ����.
//		this.getPreferenceStore().setValue(PreferenceConstants.P_LIB, ProjectManager.getInstance().getSourceFolder());//KHG 10.04.29 ���̺귯�� Preferences ��ũ ���߱�..
		
		this.getPreferenceStore().setValue(PreferenceConstants.P_M, ProjectManager.getInstance().getMPath());//KHG 10.04.29 ���̺귯�� Preferences ��ũ ���߱�..
		
		this.getPreferenceStore().setValue(PreferenceConstants.P_S, ProjectManager.getInstance().getSPath());//KHG 10.04.29 ���̺귯�� Preferences ��ũ ���߱�..
		
		this.getPreferenceStore().setValue(PreferenceConstants.P_T1, ProjectManager.getInstance().getEtc1Path());//KHG 10.04.29 ���̺귯�� Preferences ��ũ ���߱�..
		
		this.getPreferenceStore().setValue(PreferenceConstants.P_T2, ProjectManager.getInstance().getEct2Path());//KHG 10.04.29 ���̺귯�� Preferences ��ũ ���߱�..
		this.getPreferenceStore().setValue(PreferenceConstants.P_MG, ProjectManager.getInstance().getVsPath());//KHG 10.04.29 ���̺귯�� Preferences ��ũ ���߱�..
		
		
//		 p_lib=new DirectoryFieldEditor(PreferenceConstants.P_LIB,"&Local Repository Path:", getFieldEditorParent()); 
//		 p_m=new FileFieldEditor(PreferenceConstants.P_M,"&����͸� ��������:", getFieldEditorParent());
		 p_tp=new DirectoryFieldEditor(PreferenceConstants.TEMPLATE_PATH,"&Template Path:", getFieldEditorParent());	
		 p_s=new FileFieldEditor(PreferenceConstants.P_S,"&SimulationTool Path:", getFieldEditorParent());	//110902 SDM �ùķ��̼� �κ� �츲
//		 p_t1=new FileFieldEditor(PreferenceConstants.P_T1,"&T1  ��������:", getFieldEditorParent());
//		 p_t2=new FileFieldEditor(PreferenceConstants.P_T2,"&T2 ��������:", getFieldEditorParent());
		 
//		 p_vs=new FileFieldEditor(PreferenceConstants.P_MG,"&Merge Tool Path:", getFieldEditorParent());
		 
		
		 
//		 deployOp=new RadioGroupFieldEditor(
//					PreferenceConstants.OPROS_DEPLOY_OPTION,
//				"Deploy Option for Component :",
//				1,
//				new String[][] { { "Release", "RELEASE" }, {
//					"Debug", "DEBUG" }
//			}, getFieldEditorParent());
//			addField(deployOp);
		
//		addField(p_lib);	//110905 SDM �����н� �κ� �������� ���� ����.
		addField(p_tp);
		addField(p_s);	//110902 SDM �ùķ��̼� �κ� �츲
//		addField(p_tp);
//		addField(p_m);
//		addField(p_s);
//		addField(p_t1);
//		addField(p_t2);
//		addField(p_vs);
		/*
		addField(new DirectoryFieldEditor(PreferenceConstants.P_LIB, 
				"&���̺귯�� ���:", getFieldEditorParent()));
		addField(new FileFieldEditor(PreferenceConstants.P_M, 
				"&����͸� ��������:", getFieldEditorParent()));
		addField(new FileFieldEditor(PreferenceConstants.P_S, 
				"&�ù����̼� ��������:", getFieldEditorParent()));
		addField(new FileFieldEditor(PreferenceConstants.P_T1, 
				"&T1  ��������:", getFieldEditorParent()));
		addField(new FileFieldEditor(PreferenceConstants.P_T2, 
				"&T2 ��������:", getFieldEditorParent()));*/
//		''
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}
	
	public boolean performOk() {
	
		
		String lib="";//SDM 110906 NULL����Ʈ ���� ����	//khg 10.4.29 ���̺귯�� ����Ǹ� �ٷ� ����
//		String m=p_m.getStringValue();	
		String s=p_s.getStringValue();	//110902 SDM �ùķ��̼� �κ� �츲
//		String t1=p_t1.getStringValue();	
//		String t2=p_t2.getStringValue();
//		String vs=p_vs.getStringValue();	
		String tp=p_tp.getStringValue();
		
//		IWorkspaceRoot cc = ResourcesPlugin.getWorkspace().getRoot();
//		IPath aa = cc.getLocation();
//		String vvvv = aa.getFileExtension();
//		String vva = aa.toString();
//		Activator.getDefault().savePluginPreferences();
		

		Activator.getDefault().savePluginPreferences();
		
		
//		return bRet; 
	
		
		
		/*
		String lib  = this.getPreferenceStore().getString(PreferenceConstants.P_LIB);
		String m  = this.getPreferenceStore().getString(PreferenceConstants.P_M);
		String s  = this.getPreferenceStore().getString(PreferenceConstants.P_S);
		String t1  = this.getPreferenceStore().getString(PreferenceConstants.P_T1);
		String t2  = this.getPreferenceStore().getString(PreferenceConstants.P_T2);
		*/	
		String oldlib=ProjectManager.getInstance().getSourceFolder();//KHG 10.04.29 ���̺귯��  ����� ���̺귯�� Ȯ�� ����

		
		ProjectManager.getInstance().setSourceFolder();//PKY 08081801 S ������Ƽ �����ϴٺ��� �����߻��ϴ� ���� ����
//		ProjectManager.getInstance().setMPath(m);//PKY 08081801 S ������Ƽ �����ϴٺ��� �����߻��ϴ� ���� ����
		ProjectManager.getInstance().setSPath(s);//PKY 08081801 S ������Ƽ �����ϴٺ��� �����߻��ϴ� ���� ����	//110902 SDM �ùķ��̼� �κ� �츲
//		ProjectManager.getInstance().setEtc1Path(t1);//PKY 08081801 S ������Ƽ �����ϴٺ��� �����߻��ϴ� ���� ����
//		ProjectManager.getInstance().setEct2Path(t2);//PKY 08081801 S ������Ƽ �����ϴٺ��� �����߻��ϴ� ���� ����
//		ProjectManager.getInstance().setVsPath(vs);
		ProjectManager.getInstance().writeConfig();
		//KHG 10.04.29 ���̺귯�� ����Ǹ� �ٷ� ����
	
		if(lib.equals(oldlib)){
			System.out.println("");
		}else{
			CompAssemManager.getInstance().getModelStore().clear();
			CompAssemManager.getInstance().getViewStore().clear();
			CompAssemManager.getInstance().getDirModel().clear();
			CompAssemManager.getInstance().getDirView().clear();
			//CompAssemManager.getInstance().makeLib(ProjectManager.getInstance().getModelBrowser().getRootLibTreeModel());
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
		
		IWorkspaceRoot rootWorkspace = ResourcesPlugin.getWorkspace().getRoot();//��ũ�����̽� �ڵ�
//		OPRoSUtil.createMakeFile(null,null,null);
		IProject projects[]=rootWorkspace.getProjects();
		for(int i=0;i<projects.length;i++){
			try{
				projects[i].refreshLocal(IResource.DEPTH_INFINITE, null);
			}catch(CoreException e){
				e.printStackTrace();
			}
		}
		boolean bRet = super.performOk();
		return bRet;
	}
	
	protected void performApply() {
//		String lib  = this.getPreferenceStore().getString(PreferenceConstants.P_LIB);
//		String m  = this.getPreferenceStore().getString(PreferenceConstants.P_M);
//		String s  = this.getPreferenceStore().getString(PreferenceConstants.P_S);	
//		String t1  = this.getPreferenceStore().getString(PreferenceConstants.P_T1);
//		String t2  = this.getPreferenceStore().getString(PreferenceConstants.P_T2);
//		ProjectManager.getInstance().setSourceFolder(lib);//PKY 08081801 S ������Ƽ �����ϴٺ��� �����߻��ϴ� ���� ����
//		  ProjectManager.getInstance().setMPath(m);//PKY 08081801 S ������Ƽ �����ϴٺ��� �����߻��ϴ� ���� ����
//		  ProjectManager.getInstance().setSPath(s);//PKY 08081801 S ������Ƽ �����ϴٺ��� �����߻��ϴ� ���� ����
//		  ProjectManager.getInstance().setEtc1Path(t1);//PKY 08081801 S ������Ƽ �����ϴٺ��� �����߻��ϴ� ���� ����
//		  ProjectManager.getInstance().setEct2Path(t2);//PKY 08081801 S ������Ƽ �����ϴٺ��� �����߻��ϴ� ���� ����
//		  ProjectManager.getInstance().writeConfig();
//		  return super.performApply();
	  }
	
}