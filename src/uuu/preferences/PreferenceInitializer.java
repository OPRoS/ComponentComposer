package uuu.preferences;

import kr.co.n3soft.n3com.OPRoSUtil;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import uuu.Activator;

/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	public void initializeDefaultPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
//		  String ChangeFileName = ProjectManager.getInstance().getSourceFolder();
		  
		   String mPath = ProjectManager.getInstance().getMPath();
		   String sPath = ProjectManager.getInstance().getSPath();
		   String etc1Path = ProjectManager.getInstance().getEtc1Path();
		   String ect2Path = ProjectManager.getInstance().getEct2Path();
		   
//		   if(ChangeFileName!=null && !"".equals(ChangeFileName)){
//			   store.setDefault(PreferenceConstants.P_LIB, ChangeFileName);
//		   }
		   if(mPath!=null && !"".equals(mPath)){
			   store.setDefault(PreferenceConstants.P_M, mPath);
		   }
		   if(sPath!=null && !"".equals(sPath)){
			   store.setDefault(PreferenceConstants.P_S, sPath);
		   }
		   if(etc1Path!=null && !"".equals(etc1Path)){
			   store.setDefault(PreferenceConstants.P_T1, etc1Path);
		   }
		   if(ect2Path!=null && !"".equals(ect2Path)){
			   store.setDefault(PreferenceConstants.P_T2, ect2Path);
		   }
		   String path = OPRoSUtil.getOPRoSFilesPath();
			path+="OPRoSFiles/";
			store.setDefault(PreferenceConstants.OPROS_LIB_PATH,path+"OPRoSLib");
			store.setDefault(PreferenceConstants.OPROS_INC_PATH,path+"OPRoSInc");
	}

}
