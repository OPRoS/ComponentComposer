package uuu.views;

import java.util.Arrays;
import java.util.Comparator;

import kr.co.n3soft.n3com.lang.N3Messages;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.views.properties.IPropertySheetEntry;
import org.eclipse.ui.views.properties.PropertySheetEntry;
import org.eclipse.ui.views.properties.PropertySheetPage;
import org.eclipse.ui.views.properties.PropertySheetSorter;
//PKY 08100607 S
public class PropertyPage extends PropertySheetPage {

	public PropertyPage() {
		super();
	}

	public void createControl(Composite parent) {		
		super.createControl(parent);
//		super.getControl().getMenu().dispose();//PKY 09042758 S

		super.setSorter(new NameSorter2());
		
	}
	public void setActionBars(IActionBars actionBars) {//PKY 090402005 S
//		super.setActionBars(actionBars);
	}//PKY 090402005 E


}
class NameSorter2 extends PropertySheetSorter {
	public void sort(IPropertySheetEntry[] entries) {

		Arrays.sort(entries, new Comparator() {

			public int compare(Object obj1, Object obj2) {
				// TODO Auto-generated method stub
				if(obj1 instanceof PropertySheetEntry && obj2 instanceof PropertySheetEntry  ){
					PropertySheetEntry propery1 = (PropertySheetEntry)obj1;
					PropertySheetEntry propery2 = (PropertySheetEntry)obj2;
					String proName1 = propery1.getDisplayName();
					String proName2 = propery2.getDisplayName();
					int proIndex1 = -1;
					int proIndex2 = -1;
					if(proName1.equals(N3Messages.POPUP_PACKAGE)){
						proIndex1 = 0;
					}else if(proName1.equals(N3Messages.POPUP_NAME)){
						proIndex1 = 1;
					}else if(proName1.equals(N3Messages.POPUP_STEREOTYPE)){
						proIndex1 = 2;
					}else if(proName1.equals(N3Messages.POPUP_ATTRIBUTES)){
						proIndex1 = 3;
					}else if(proName1.equals(N3Messages.POPUP_OPERATION)){
						proIndex1 = 4;
					}else if(proName1.equals(N3Messages.POPUP_PROPERTIES)){
						proIndex1 = 5;
					}else if(proName1.equals(N3Messages.DIALOG_MULTIPLICITY)){
						proIndex1 = 6;
					}else if(proName1.equals(N3Messages.POPUP_INSTANCE_CLASSIFIER)){
						proIndex1 = 7;
//					}else if(proName1.equals(N3Messages.DIALOG_STATELIFELINE_MAX)){
//						proIndex1 = 8;
					}else if(proName1.equals(N3Messages.POPUP_CONFIGURE_TIMELINE)){
						proIndex1 = 9;
					}else if(proName1.equals(N3Messages.DIALOG_MESSAGE_PROPERTIEST)){
						proIndex1 = 10;
					}else if(proName1.equals(N3Messages.DIALOG_NEW_ACTION)){
						proIndex1 = 11;
					}else if(proName1.equals(N3Messages.DIALOG_NEW_STCUTUREACTIVITY)){
						proIndex1 = 12;
					}else if(proName1.equals(N3Messages.POPUP_SCOPE)){
						proIndex1 = 13;
					}else if(proName1.equals(N3Messages.DIALOG_IS_ACTIVE)){
						proIndex1 = 14;
					}else if(proName1.equals(N3Messages.DIALOG_IS_REFERENCE)){
						proIndex1 = 15;
//					}else if(proName1.equals(N3Messages.DIALOG_READONLY)){
//						proIndex1 = 16;
					}else if(proName1.equals(N3Messages.DIALOG_IS_SINGLEEXECUTION)){
						proIndex1 = 17;
					}else if(proName1.equals(N3Messages.DIALOG_PRECONDITION)){
						proIndex1 = 18;
					}else if(proName1.equals(N3Messages.DIALOG_POSTCONDITION)){
						proIndex1 = 19;
					}else if(proName1.equals(N3Messages.DIALOG_IS_PARAMETER_NAME)){
						proIndex1 = 20;
					}else if(proName1.equals(N3Messages.DIALOG_SET_OBJECT_STATE)){
						proIndex1 = 21;
					}else if(proName1.equals(N3Messages.DIALOG_EFFECT)){
						proIndex1 = 22;
//					}else if(proName1.equals(N3Messages.DIALOG_CONTEXT)){
//						proIndex1 = 23;
//					}else if(proName1.equals(N3Messages.DIALOG_FILENAME)){
//						proIndex1 = 24;
//					}else if(proName1.equals(N3Messages.DIALOG_SIMPLE)){
//						proIndex1 = 25;
//					}else if(proName1.equals(N3Messages.DIALOG_SUBMACHINESTATE)){
//						proIndex1 = 26;
//					}else if(proName1.equals(N3Messages.DIALOG_ORTHOGONAL)){
//						proIndex1 = 27;
//					}else if(proName1.equals(N3Messages.DIALOG_COMPOSITE)){
//						proIndex1 = 28;
//					}else if(proName1.equals(N3Messages.DIALOG_INDIRECTLYINSTANTIATED)){
//						proIndex1 = 29;
					}else if(proName1.equals(N3Messages.DIALOG_TYPE)){
						proIndex1 = 30;
					}else if(proName1.equals(N3Messages.DIALOG_IMAGE)){
						proIndex1 = 31;
					}else if(proName1.equals(N3Messages.POPUP_REQID)){
						proIndex1 = 32;
					}else if(proName1.equals(N3Messages.DIALOG_SHOW_ATTR)){
						proIndex1 = 33;
					}else if(proName1.equals(N3Messages.DIALOG_SHOW_OPER)){
						proIndex1 = 34;
					}else if(proName1.equals(N3Messages.POPUP_BACKGROUND_COLOR)){
						proIndex1 = 35;
//					}else if(proName1.equals(N3Messages.DIALOG_AUTOROUTING)){
//						proIndex1 = 36;
//					}else if(proName1.equals(N3Messages.DIALOG_COULMN)){
//						proIndex1 = 37;
//					}else if(proName1.equals(N3Messages.DIALOG_ROW)){
//						proIndex1 = 38;
//					}else if(proName1.equals(N3Messages.DIALOG_SCREEN_TABLE_EDIT)){
//						proIndex1 = 39;
//					}else if(proName1.equals(N3Messages.DIALOG_TITEL_BAR)){
//						proIndex1 = 39;
//					}else if(proName1.equals(N3Messages.DIALOG_IS_ENABLE)){
//						proIndex1 = 40;
					}else if(proName1.equals(N3Messages.DIALOG_KIND)){
						proIndex1 = 41;
					}else if(proName1.equals(N3Messages.POPUP_DESCRIPTION)){//PKY 09040212 S
						proIndex1 = 42;
					}else if (proName1.equals(N3Messages.POPUP_EDIT_MESSAGE)){//PKY 09040212 E
						proIndex1 = 43;
//					}else if(proName1.equals(N3Messages.DIALOG_METHODOLOGY_ID)){
//						proIndex1 = 44;
//					}else if(proName1.equals(N3Messages.DIALOG_ESSENTIALITY)){
//						proIndex1 = 44;
					}
					


					if(proName2.equals(N3Messages.POPUP_PACKAGE)){
						proIndex2 = 0;
					}else if(proName2.equals(N3Messages.POPUP_NAME)){
						proIndex2 = 1;
					}else if(proName2.equals(N3Messages.POPUP_STEREOTYPE)){
						proIndex2 = 2;
					}else if(proName2.equals(N3Messages.POPUP_ATTRIBUTES)){
						proIndex2 = 3;
					}else if(proName2.equals(N3Messages.POPUP_OPERATION)){
						proIndex2 = 4;
					}else if(proName2.equals(N3Messages.POPUP_PROPERTIES)){
						proIndex2 = 5;
					}else if(proName2.equals(N3Messages.DIALOG_MULTIPLICITY)){
						proIndex2 = 6;
					}else if(proName2.equals(N3Messages.POPUP_INSTANCE_CLASSIFIER)){
						proIndex2 = 7;
//					}else if(proName2.equals(N3Messages.DIALOG_STATELIFELINE_MAX)){
//						proIndex2 = 8;
					}else if(proName2.equals(N3Messages.POPUP_CONFIGURE_TIMELINE)){
						proIndex2 = 9;
					}else if(proName2.equals(N3Messages.DIALOG_MESSAGE_PROPERTIEST)){
						proIndex2 = 10;
					}else if(proName2.equals(N3Messages.DIALOG_NEW_ACTION)){
						proIndex2 = 11;
					}else if(proName2.equals(N3Messages.DIALOG_NEW_STCUTUREACTIVITY)){
						proIndex2 = 12;
					}else if(proName2.equals(N3Messages.POPUP_SCOPE)){
						proIndex2 = 13;
					}else if(proName2.equals(N3Messages.DIALOG_IS_ACTIVE)){
						proIndex2 = 14;
					}else if(proName2.equals(N3Messages.DIALOG_IS_REFERENCE)){
						proIndex2 = 15;
//					}else if(proName2.equals(N3Messages.DIALOG_READONLY)){
//						proIndex2 = 16;
					}else if(proName2.equals(N3Messages.DIALOG_IS_SINGLEEXECUTION)){
						proIndex2 = 17;
					}else if(proName2.equals(N3Messages.DIALOG_PRECONDITION)){
						proIndex2 = 18;
					}else if(proName2.equals(N3Messages.DIALOG_POSTCONDITION)){
						proIndex2 = 19;
					}else if(proName2.equals(N3Messages.DIALOG_IS_PARAMETER_NAME)){
						proIndex2 = 20;
					}else if(proName2.equals(N3Messages.DIALOG_SET_OBJECT_STATE)){
						proIndex2 = 21;
					}else if(proName2.equals(N3Messages.DIALOG_EFFECT)){
						proIndex2 = 22;
//					}else if(proName2.equals(N3Messages.DIALOG_CONTEXT)){
//						proIndex2 = 23;
//					}else if(proName2.equals(N3Messages.DIALOG_FILENAME)){
//						proIndex2 = 24;
//					}else if(proName2.equals(N3Messages.DIALOG_SIMPLE)){
//						proIndex2 = 25;
//					}else if(proName2.equals(N3Messages.DIALOG_SUBMACHINESTATE)){
//						proIndex2 = 26;
//					}else if(proName2.equals(N3Messages.DIALOG_ORTHOGONAL)){
//						proIndex2 = 27;
//					}else if(proName2.equals(N3Messages.DIALOG_COMPOSITE)){
//						proIndex2 = 28;
//					}else if(proName2.equals(N3Messages.DIALOG_INDIRECTLYINSTANTIATED)){
//						proIndex2 = 29;
					}else if(proName2.equals(N3Messages.DIALOG_TYPE)){
						proIndex2 = 30;
					}else if(proName2.equals(N3Messages.DIALOG_IMAGE)){
						proIndex2 = 31;
					}else if(proName2.equals(N3Messages.POPUP_REQID)){
						proIndex2 = 32;
					}else if(proName2.equals(N3Messages.DIALOG_SHOW_ATTR)){
						proIndex2 = 33;
					}else if(proName2.equals(N3Messages.DIALOG_SHOW_OPER)){
						proIndex2 = 34;
					}else if(proName2.equals(N3Messages.POPUP_BACKGROUND_COLOR)){
						proIndex2 = 35;
//					}else if(proName2.equals(N3Messages.DIALOG_AUTOROUTING)){
//						proIndex2 = 36;
//					}else if(proName2.equals(N3Messages.DIALOG_COULMN)){
//						proIndex2 = 37;
//					}else if(proName2.equals(N3Messages.DIALOG_ROW)){
//						proIndex2 = 38;
//					}else if(proName2.equals(N3Messages.DIALOG_SCREEN_TABLE_EDIT)){
//						proIndex2 = 39;
//					}else if(proName2.equals(N3Messages.DIALOG_TITEL_BAR)){
//						proIndex2 = 39;
//					}else if(proName2.equals(N3Messages.DIALOG_IS_ENABLE)){
//						proIndex2 = 40;
					}else if(proName2.equals(N3Messages.DIALOG_KIND)){
						proIndex2 = 41;
					}else if(proName2.equals(N3Messages.POPUP_DESCRIPTION)){//PKY 09040212 S
						proIndex2 = 42;
					}else if (proName2.equals(N3Messages.POPUP_EDIT_MESSAGE)){//PKY 09040212 E
						proIndex2 = 43;
					}
//					}else if(proName2.equals(N3Messages.DIALOG_METHODOLOGY_ID)){
//						proIndex2 = 44;
//					}else if(proName2.equals(N3Messages.DIALOG_ESSENTIALITY)){
//						proIndex2 = 44;
//					}

					if(proIndex1<=proIndex2){
						return -1;
					}else{
						return 1;
					}
				}
				return -1;
			}

		});

	}

}
