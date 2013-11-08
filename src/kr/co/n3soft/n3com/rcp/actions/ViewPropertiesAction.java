package kr.co.n3soft.n3com.rcp.actions;

import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchWindow;

public class ViewPropertiesAction extends Action {
boolean isCheck = false;
IWorkbenchWindow workbenchWindow;
    public ViewPropertiesAction(IWorkbenchWindow window) {
    	
    	setId("ViewPropertiesAction");
		this.setText("Properties");
		workbenchWindow = window;
		setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getImage(325)));//PKY 08070101 S ÆË¾÷ ¸Þ´º ÀÌ¹ÌÁö »ðÀÔ
//        setChecked(isChecked());
    }
//    public boolean isChecked() {
//    	
//    		return true;
//    	
//    }
    protected boolean calculateEnabled() {
    	
    	
    	return true;
      
    }

    public void run() {
    	try{
    		
    	
    	    	IViewPart iv = this.workbenchWindow.getActivePage().findView(IPageLayout.ID_PROP_SHEET);
    	    	
    	    	
    		
    	   	if(iv==null){
    	   		
    	   		workbenchWindow.getActivePage().showView(IPageLayout.ID_PROP_SHEET);
    	   	}
    	   	
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
    	
        
    }
	public IWorkbenchWindow getWorkbenchWindow() {
		return workbenchWindow;
	}
	public void setWorkbenchWindow(IWorkbenchWindow workbenchWindow) {
		this.workbenchWindow = workbenchWindow;
	}
}