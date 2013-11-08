package uuu;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.eclipse.ui.IViewLayout;

public class PerspectiveFreeMoving implements IPerspectiveFactory {
	public static final String ID = "org.eclipsercp.hyperbola.perspectives.freeMoving";

	public void createInitialLayout(IPageLayout layout) {
//		IFolderLayout folder = layout.createFolder("contacts",
//				IPageLayout.LEFT, 0.33f, layout.getEditorArea());
//		folder.addPlaceholder(ContactsView.ID + ":*");
//		folder.addView(ContactsView.ID);
//		IViewLayout l = layout.getViewLayout(ContactsView.ID);
//		l.setCloseable(false);
//
//		layout.addPerspectiveShortcut(Perspective.ID);
//		layout.addPerspectiveShortcut(PerspectiveFreeMoving.ID);
	}
}