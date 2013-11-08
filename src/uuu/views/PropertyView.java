package uuu.views;

import kr.co.n3soft.n3com.lang.N3Messages;

import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.internal.views.ViewsPlugin;
import org.eclipse.ui.part.IPage;
import org.eclipse.ui.part.IPageBookViewPage;
import org.eclipse.ui.part.PageBook;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.PropertySheet;
//PKY 08100607 S

public class PropertyView extends PropertySheet{
	private PropertyPage page =null;
    public PropertyView() {

        super();
       
    }
	
    protected IPage createDefaultPage(PageBook book) {
    	page = new PropertyPage();
//    	setPartName(N3Messages.DIALOG_PROPERTIES);    	 //20090325 KHG S 프로퍼티창 이름 바뀌는거 수정..
        initPage(page);
        page.createControl(book);
        return page;
        
    }
    protected PageRec doCreatePage(IWorkbenchPart part) {
        // Try to get a custom property sheet page.
        IPropertySheetPage page = (IPropertySheetPage) ViewsPlugin.getAdapter(part,
                IPropertySheetPage.class, false);
        if (page != null) {
            if (page instanceof IPageBookViewPage) {
				initPage((IPageBookViewPage) page);
			}
            page.createControl(getPageBook());
            page = (IPropertySheetPage) createDefaultPage(getPageBook());
            return new PageRec(part, page);
        }


//       setPartName(N3Messages.DIALOG_PROPERTIES);
        // Use the default page		
        return null;
    }

	public PropertyPage getPage() {
		return page;
	}

	public void setPage(PropertyPage page) {
		this.page = page;
	}

}
