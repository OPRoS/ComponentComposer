package kr.co.n3soft.n3com.project.browser;

import kr.co.n3soft.n3com.dnd.TreeSimpleFactory;
import kr.co.n3soft.n3com.model.comm.StrcuturedPackageTreeModel;
import kr.co.n3soft.n3com.model.component.ComponentLibModel;
import kr.co.n3soft.n3com.model.component.ComponentModel;
import kr.co.n3soft.n3com.model.component.RepoComponent;
import kr.co.n3soft.n3com.net.NetManager;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;
import opros.ExecutableInfo;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerDropAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.TransferData;

public class ModelBrowerViewerDropAdapter extends ViewerDropAdapter {
	boolean isTarget = true;
    public ModelBrowerViewerDropAdapter(Viewer viewer) {
        super(viewer);
    }

    public boolean performDrop(Object data) {
        System.out.println("performDrop " + data.toString());
        if (data instanceof String[]) {
            //	       MessageDialog.openInformation(...);
        }
        return true;
    }

    //	  public void dropAccept(DropTargetEvent event) {
    ////	        super.dropAccept(event);
    //	        Object obj = this.getCurrentTarget();
    //	        if(obj instanceof PackageTreeModel || obj instanceof RootTreeModel){
    //	        	
    //	        }
    //	        else{
    //	        	event.detail = DND.DROP_NONE;
    //	        }
    //	        	
    //	        TreeSimpleFactory treeSimpleFactory =  (TreeSimpleFactory)event.data;
    //	        if(obj instanceof RootTreeModel){
    //	        	
    //				  if(treeSimpleFactory.getUMLTreeModel()!=null){
    //					  UMLTreeModel uMLTreeModel =  (UMLTreeParentModel)treeSimpleFactory.getUMLTreeModel();
    //					  if(!(uMLTreeModel instanceof PackageTreeModel)){
    //						  event.detail = DND.DROP_NONE;
    //					  }
    //				  }
    //				  else{
    //					  event.detail = DND.DROP_NONE;
    //				  }
    //	        }
    //	        if(treeSimpleFactory.getUMLTreeModel()==null){
    //	        	 event.detail = DND.DROP_NONE;
    //	        }
    //	        	
    //	
    //	    }
    public boolean validateDrop(Object target, int operation, TransferData transferType) {
    	isTarget = true;
    	if ((target instanceof PackageTreeModel || target instanceof RootTreeModel)) {
        }
        else {
            return false;
        }
//    	if(target instanceof StrcuturedPackageTreeModel){
//    		StrcuturedPackageTreeModel st = (StrcuturedPackageTreeModel)target;
//	    	if(st.getRefModel() instanceof ComponentLibModel){
//				ComponentLibModel clm = (ComponentLibModel)st.getRefModel();
//				if(clm.isInstance)
//					return false;
//			}
//			else if(st.getRefModel() instanceof ComponentModel){
//				ComponentModel clm = (ComponentLibModel)st.getRefModel();
//				if("<<composite>>".equals(clm.getStereotype())){
//					return false;
//				}
//				if(clm.isInstance)
//					return false;
//				
//			}
//    	}
        
        if ((target instanceof ServerRepositoryTreeModel)){
        	 UMLTreeModel uMLTreeModel = ProjectManager.getInstance().getDragUMLTreeModel();
        	 if(uMLTreeModel instanceof StrcuturedPackageTreeLibModel){
        		 StrcuturedPackageTreeLibModel spt = (StrcuturedPackageTreeLibModel)uMLTreeModel;
        		 if(spt.getRefModel() instanceof ComponentLibModel){
        			 isTarget = false;
        			 return true;
        		 }
        	 }
        	
        }
        UMLTreeModel uMLTreeModel = ProjectManager.getInstance().getDragUMLTreeModel();
        if(uMLTreeModel instanceof StrcuturedPackageTreeLibModel)
        	return false;
        //		        TreeSimpleFactory treeSimpleFactory =  (TreeSimpleFactory)event.data;
        if (target instanceof RootTreeModel) {
            if (uMLTreeModel != null) {
                if (!(uMLTreeModel instanceof PackageTreeModel)) {
                    return false;
                }
            }
            else {
                return false;
            }
        }
        if (uMLTreeModel == null) {
            return false;
        }
        
        
        return true;
    }
  //PKY 08090101 S 링크파일 하위로 트리를이동하려고할경우 안되도록 막도록 수정

    public void drop(DropTargetEvent event) {
        try {
        	Object obj = this.getCurrentTarget();
        
            UMLTreeParentModel parent = (UMLTreeParentModel)obj;
        	if(!isTarget){
        		TreeSimpleFactory treeSimpleFactory = (TreeSimpleFactory)event.data;
                UMLTreeModel child = (UMLTreeModel)treeSimpleFactory.getUMLTreeModel();
        		NetManager.getInstance().uploadRepo(parent, child);
        		return;
        	}
            
            System.out.println("drop");
            TreeSimpleFactory treeSimpleFactory = (TreeSimpleFactory)event.data;
            UMLTreeModel child = (UMLTreeModel)treeSimpleFactory.getUMLTreeModel();
            if(!(child instanceof RepoComponentTreeModel)){
          //PKY 08062601 S 모델 브라우져 이동 시 패키지 자체가 사라지는 문제 발생 문제 수정
            if(child!=parent){
            	if(child instanceof PackageTreeModel){
            		PackageTreeModel finalPackageModel = (PackageTreeModel)child;
            		if(finalPackageModel.isLink()){
            			if(ProjectManager.getInstance().isTreeModelLink(parent)){
            				return;
            			}
            		}

            	}
            	 
            UMLTreeParentModel sourceParent = child.getParent();
            ProjectManager.getInstance().addUMLNode(parent, child);
            ProjectManager.getInstance().removeUMLNode(sourceParent, child);
            	
            child.setParent(parent);
           
            }else{
//            	MessageBox dialog = new MessageBox(ProjectManager.getInstance().window.getShell(),SWT.ICON_WARNING)	;
//    			dialog.setText(N3Messages.DIALOG_MESSAGE);
//    			dialog.setMessage(N3Messages.DIALOG_MODELBROWSER_DROP_MISS);
//    			dialog.open();
//            	;
            }
            //PKY 08062601 E 모델 브라우져 이동 시 패키지 자체가 사라지는 문제 발생 문제 수정
            //			  uMLTreeParentModel.addChild(uMLTreeModel);
            //			  ProjectManager.getInstance().getModelBrowser().refresh(uMLTreeParentModel);
            //			  sourceParent.removeChild(uMLTreeModel);
            //			  ProjectManager.getInstance().getModelBrowser().refresh(sourceParent);
        	}
        	else if(child instanceof RepoComponentTreeModel){
        		RepoComponentTreeModel rct = (RepoComponentTreeModel)child;
            	
            	N3ViewContentProvider nvc = (N3ViewContentProvider)ProjectManager.getInstance().getModelBrowser().getViewer().getContentProvider();
            	if(rct.getRefModel()!=null && rct.getRefModel() instanceof RepoComponent){
            		RepoComponent rc = (RepoComponent)rct.getRefModel();
            		NetManager.getInstance().downloadRepo((ExecutableInfo)rc.getExecutableInfo());
            		ProjectManager.getInstance().getModelBrowser().refreshLib();
            	}
            	
            	
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
//PKY 08090101 E 링크파일 하위로 트리를이동하려고할경우 안되도록 막도록 수정
