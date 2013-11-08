package kr.co.n3soft.n3com.project.browser;

import kr.co.n3soft.n3com.dnd.TreeSimpleFactory;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.DropTargetListener;

public class ModelBrowserDropListener implements DropTargetListener {
    private final ModelBrowser browser;

    ModelBrowserDropListener(ModelBrowser browser) {
        this.browser = browser;
    }

    public void dragEnter(DropTargetEvent event) {
        System.out.println("ddd");
    }

    public void dragLeave(DropTargetEvent event) { }

    public void dragOperationChanged(DropTargetEvent event) { }

    public void dragOver(DropTargetEvent event) {
    }

    public void dropAccept(DropTargetEvent event) {
    	
    	
    	System.out.println("ddddddd");
    	
        //		  if(!(event.data instanceof TreeSimpleFactory)){
        //			  return;
        //		  }
        //		  else{
        //			
        //			  UMLTreeModel uMLTreeModel=   ProjectManager.getInstance().getModelBrowser().getUMLTreeModelSelected();
        //			  TreeSimpleFactory treeSimpleFactory =  (TreeSimpleFactory)event.data;
        //			  if(treeSimpleFactory.getUMLTreeModel()==null)
        //				  return;
        //			  if(!(treeSimpleFactory.getUMLTreeModel() instanceof PackageTreeModel))
        //				  return;
        //			  if(!(treeSimpleFactory.getUMLTreeModel() instanceof RootTreeModel))
        //				  return;
        //		  }
        //		  if(!(event.data instanceof TreeSimpleFactory)){
        //			
        //		  }
    }

    public void drop(DropTargetEvent event) {
        System.out.println("drop");
        TreeSimpleFactory treeSimpleFactory = (TreeSimpleFactory)event.data;
        if (treeSimpleFactory.getUMLTreeModel() != null) {
            UMLTreeParentModel uMLTreeParentModel = (UMLTreeParentModel)treeSimpleFactory.getUMLTreeModel();
            ProjectManager.getInstance().getModelBrowser().getUMLTreeModelSelected();
        }
        
        
    }
}
