package uuu.preferences;



import kr.co.n3soft.n3com.OPRoSUtil;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.FileFieldEditor;
import org.eclipse.jface.preference.PathEditor;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import uuu.Activator;

public class OPRoS_CC_DirectoryPage extends FieldEditorPreferencePage implements
		IWorkbenchPreferencePage {

	private DirectoryFieldEditor boostLib;
	private DirectoryFieldEditor boostInc;
	private DirectoryFieldEditor oprosLib;
	private DirectoryFieldEditor oprosInc;
	private FileFieldEditor oprosEngineFile;
	
	private PathEditor otherInc;
	private RadioGroupFieldEditor vsCompile;
	private RadioGroupFieldEditor vsUnicode;
	
	public OPRoS_CC_DirectoryPage(){
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("Compile Preference Setting Page of OPRoS Component Editor");
	}
	
	
	protected void createFieldEditors() {
		
//		boostLib = new DirectoryFieldEditor(PreferenceConstants.BOOST_LIB_PATH, 
//		"Boost Library Directory:", getFieldEditorParent());
//		addField(boostLib);
//		boostInc = new DirectoryFieldEditor(PreferenceConstants.BOOST_INC_PATH, 
//				"Boost Include Diretory:", getFieldEditorParent());
//		addField(boostInc);
		oprosLib=new DirectoryFieldEditor(PreferenceConstants.OPROS_LIB_PATH, 
				"OPRoS Directory:", getFieldEditorParent());
		addField(oprosLib);
//		oprosInc=new DirectoryFieldEditor(PreferenceConstants.OPROS_INC_PATH, 
//				"OPRoS Include Directory:", getFieldEditorParent());
//		addField(oprosInc);
//		oprosEngineFile = new FileFieldEditor(PreferenceConstants.OPROS_ENGINE_FILE,
//				"OPRoS Engine File :", getFieldEditorParent());
//		addField(oprosEngineFile);
		otherInc = new PathEditor(PreferenceConstants.OTHER_INC_PATH, "Include Path :",
				"Other Include Directory:", getFieldEditorParent());
		addField(otherInc);
		vsCompile=new RadioGroupFieldEditor(
				PreferenceConstants.VS_COMPILE_OPTION,
			"Compile Option for Visual Studio 2008 :",
			1,
			new String[][] { { "Release", "RELEASE" }, {
				"Debug", "DEBUG" }
		}, getFieldEditorParent());
		addField(vsCompile);
//		vsUnicode=new RadioGroupFieldEditor(
//				PreferenceConstants.VS_UNICODE_OPTION,
//				"Encoding Option for Visual Studio 2008 :",
//				1,
//				new String[][] { { "Unicode", "UNICODE" }, {
//					"Multibyte", "MBCS" }
//			}, getFieldEditorParent());
//		addField(vsUnicode);
		this.setSize(new Point(200,200));
	}

	
	public void init(IWorkbench arg0) {

	}


	public boolean performOk() {
		Activator.getDefault().savePluginPreferences();
		boolean bRet = super.performOk();
		IWorkspaceRoot rootWorkspace = ResourcesPlugin.getWorkspace().getRoot();//워크스페이스 핸들
//		OPRoSUtil.createMakeFile(null,null,null);
		IProject projects[]=rootWorkspace.getProjects();
		for(int i=0;i<projects.length;i++){
			try{
				projects[i].refreshLocal(IResource.DEPTH_INFINITE, null);
			}catch(CoreException e){
				e.printStackTrace();
			}
		}
		return bRet; 
	}

}
