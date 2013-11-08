package kr.co.n3soft.n3com.dnd;

import kr.co.n3soft.n3com.model.comm.FinalPackageModel;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.component.AtomicComponentModel;
import kr.co.n3soft.n3com.model.component.ComponentLibModel;
import kr.co.n3soft.n3com.model.component.ComponentModel;
import kr.co.n3soft.n3com.model.component.RepoComponent;
import kr.co.n3soft.n3com.model.node.NodeItemModel;
import kr.co.n3soft.n3com.model.node.NodePackageModel;
import kr.co.n3soft.n3com.model.node.NodeRootModel;
import kr.co.n3soft.n3com.project.browser.NodeItemTreeModel;
import kr.co.n3soft.n3com.project.browser.StrcuturedPackageTreeLibModel;
import kr.co.n3soft.n3com.project.browser.UMLTreeModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.dnd.TemplateTransferDropTargetListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.dnd.DropTargetEvent;

public class N3TemplateTransferDropTargetListener extends TemplateTransferDropTargetListener {
    public N3TemplateTransferDropTargetListener(EditPartViewer viewer) {
        super(viewer);
    }

    public void dragOver(DropTargetEvent event) {
        try {
            super.dragOver(event);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void drop(DropTargetEvent event) {
    	
//    	if(this.getTargetEditPart() instanceof UMLDiagramEditPart){
//    		UMLDiagramEditPart ud = (UMLDiagramEditPart)this.getTargetEditPart();
//    		UMLModel um = (UMLModel)ud.getModel();
//    			System.out.println("dddd");
//    	}
    	//PKY 08091001 S
    	boolean isDrage = false;
    	try{
    	if(event.data!=null && event.data instanceof TreeSimpleFactory ){
    		TreeSimpleFactory treeSimple = (TreeSimpleFactory)event.data;
    		if(treeSimple.uMLModel instanceof FinalPackageModel)
    			return;
    		if(treeSimple.uMLModel instanceof RepoComponent)
    			return;
    		if(treeSimple.uMLModel instanceof NodeRootModel)
    			return;
    		if(treeSimple.uMLModel instanceof NodePackageModel)
    			return;
    		if(treeSimple.uMLModel instanceof ComponentLibModel){
    			ComponentLibModel clm = (ComponentLibModel)treeSimple.uMLModel;
    			if(clm.isInstance)
    				return;
    		}
    		else if(treeSimple.uMLModel instanceof ComponentModel){
    			ComponentModel clm = (ComponentModel)treeSimple.uMLModel;
    			if("<<composite>>".equals(clm.getStereotype())){
    				return;
    			}
    			if(clm.isInstance)
    				return;
    			
    		}
    		if(ProjectManager.getInstance()!=null && ProjectManager.getInstance().getModelBrowser()!=null && ProjectManager.getInstance().getModelBrowser().getViewer()!=null){
    			IStructuredSelection istr= (IStructuredSelection)ProjectManager.getInstance().getModelBrowser().getViewer().getSelection();
    			java.util.List list = istr.toList();
    			StrcuturedPackageTreeLibModel to1 = null;
    			for(int i = 0; i < list.size(); i++){
    				if(list.get(i) instanceof UMLTreeModel){
    					if(((UMLTreeModel)list.get(i)).getRefModel()!=null){
    						// 110902 S  SDM - 드래그 할때 문제점 해결
    						UMLModel um = (UMLModel)(((UMLTreeModel)list.get(i))).getRefModel();
    						UMLTreeModel ut = (UMLTreeModel)list.get(i);
    						
    						if(um instanceof AtomicComponentModel){
    							um = ((AtomicComponentModel)um).getCoreDiagramCmpModel();
    							ut = ((AtomicComponentModel)um).getCoreUMLTreeModel();
    						}
    						
    						
    						treeSimple.setUMLModel(um);
    					
    						treeSimple.setUMLTreeModel(ut);
    						
    						// 110902 E  SDM - 드래그 할때 문제점 해결
    						
    						treeSimple.setDrag(true);
    							
    						
    						isDrage=true;
    						super.drop(event);
    					}
    				}
    			}
    			if(list.size()==1){
    				if(list.get(0) instanceof NodeItemTreeModel){
    					if(((UMLTreeModel)list.get(0)).getRefModel()!=null){
//    						treeSimple.setUMLModel((UMLModel)((UMLTreeModel)list.get(0)).getRefModel());
//    						treeSimple.setUMLTreeModel((UMLTreeModel)list.get(0));
    						this.setCurrentEvent(event);
    						NodeItemModel nodeItemModel= (NodeItemModel)((UMLTreeModel)list.get(0)).getRefModel();
    						Point pt = this.getDropLocation();
    						ComponentModel um = (ComponentModel)ProjectManager.getInstance().getUMLEditor().getDiagram().getModel(pt.x, pt.y);
                            if(um!=null){
                            	um.setNodeItemModel(nodeItemModel);
                            	um.setBackGroundColor(nodeItemModel.getBackGroundColor());
                            }
    						
    						System.out.println();
//    						isDrage=true;
//    						super.drop(event);
    					}
    				}
    			}

    		}
    	}
    	
    	if(!isDrage){
			super.drop(event);
    	}
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	//PKY 08091001 E

    }
}
