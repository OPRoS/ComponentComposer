package uuu.preferences;


import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import uuu.Activator;

public class OPRoS_CC_CopyrighterPage extends FieldEditorPreferencePage
		implements IWorkbenchPreferencePage {
	public OPRoS_CC_CopyrighterPage(){
//		try{
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("Copyright Infomation Setting Page of OPRoS Component Composer");
//		}
//		catch(Exc)
	}

	protected void createFieldEditors() {
		addField(
			new StringFieldEditor(PreferenceConstants.COPYRIGHT_NAME, "Copyright Name:", getFieldEditorParent()));
		addField(
				new StringFieldEditor(PreferenceConstants.COPYRIGHT_ADDRESS, "Copyright Address:", getFieldEditorParent()));
		addField(
				new StringFieldEditor(PreferenceConstants.COPYRIGHT_PHONE, "Copyright Phone:", getFieldEditorParent()));
		addField(
				new StringFieldEditor(PreferenceConstants.COPYRIGHT_HOMEPAGE, "Copyright Homepage:", getFieldEditorParent()));
	}


	public void init(IWorkbench arg0) {
		// TODO Auto-generated method stub

	}

}