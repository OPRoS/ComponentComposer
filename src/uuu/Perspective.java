package uuu;

import kr.co.n3soft.n3com.projectmanager.ProjectManager;


import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class Perspective implements IPerspectiveFactory {

	/**
	 * Creates a simply layout to demonstrate RCP usage
	 */
	public void createInitialLayout(IPageLayout layout) {
		String editorArea = layout.getEditorArea();
//		layout.addView(IPageLayout.ID_TASK_LIST, IPageLayout.LEFT, .20f, editorArea);
//		IFolderLayout folder1= layout.createFolder("topLeft", IPageLayout.LEFT, (float)0.25, editorArea); //$NON-NLS-1$
		
		layout.addView("uuu.views.SampleView4", IPageLayout.LEFT, .25f, editorArea);
//		IFolderLayout folder1= layout.createFolder("bottomLeft", IPageLayout.BOTTOM, (float)0.40, "uuu.views.SampleView4"); //$NON-NLS-1$
//		folder1.addView("org.eclipse.ui.navigator.ProjectExplorer");
////		folder1.addView("uuu.views.SampleView4");
//		folder1.addPlaceholder(CUIPlugin.CVIEW_ID);
//		folder1.addPlaceholder(IPageLayout.ID_RES_NAV);
//		folder1.addPlaceholder(IPageLayout.ID_BOOKMARKS);
		layout.addView(IPageLayout.ID_OUTLINE, IPageLayout.RIGHT, .80f, editorArea);
		layout.addView(IPageLayout.ID_PROP_SHEET, IPageLayout.BOTTOM, .70f, IPageLayout.ID_OUTLINE);
//		layout.addView("uuu.properties.samplePropertyPage", IPageLayout.BOTTOM, .70f, IPageLayout.ID_OUTLINE);
//		layout.addView(IPageLayout.ID_TASK_LIST, IPageLayout.BOTTOM, .70f, editorArea);
		layout.addView("uuu.views.SampleView", IPageLayout.BOTTOM, .80f, editorArea);
//		layout.addView("uuu.views.SampleView4", IPageLayout.LEFT, .30f, editorArea);
		layout.addView("uuu.views.DescriptionView", IPageLayout.RIGHT, .80f, "uuu.views.SampleView");//20080725 KDI s
//		layout.addView("uuu.views.OperationView", IPageLayout.RIGHT, .80f, "uuu.views.SampleView");//20080725 KDI s
//		layout.addStandaloneViewPlaceholder("uuu.views.OperationView", IPageLayout.RIGHT, IPageLayout.RATIO_MAX, "uuu.views.SampleView", true);
//		layout.addStandaloneView("uuu.views.SampleView4",true,IPageLayout.LEFT, .30f, editorArea);
		layout.setEditorAreaVisible(true);
//		layout.addActionSet(CUIPlugin.SEARCH_ACTION_SET_ID);
//		layout.addActionSet(CUIPlugin.ID_CELEMENT_CREATION_ACTION_SET);
		ProjectManager.getInstance().window = ProjectManager.getInstance().at.getWorkbench().getActiveWorkbenchWindow();
//		PageLayout pageLayout = (PageLayout)layout;
//		IPlaceholderFolderLayout iPlaceholderFolderLayout =pageLayout.getFolderForView(IPageLayout.ID_PROP_SHEET);
//		PlaceholderFolderLayout p = (PlaceholderFolderLayout)iPlaceholderFolderLayout;
	
//		IViewLayout iViewLayout = layout.getViewLayout(IPageLayout.ID_PROP_SHEET);
		System.out.println("dddd");
//		layout.setFixed(true);
	}

}