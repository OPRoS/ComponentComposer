package kr.co.n3soft.n3com.model.component;



import kr.co.n3soft.n3com.model.comm.DataInputPortModel;
import kr.co.n3soft.n3com.model.comm.DataOutputPortModel;
import kr.co.n3soft.n3com.model.comm.EventInputPortModel;
import kr.co.n3soft.n3com.model.comm.EventOutputPortModel;
import kr.co.n3soft.n3com.model.comm.MethodInputPortModel;
import kr.co.n3soft.n3com.model.comm.MethodOutputPortModel;
import kr.co.n3soft.n3com.model.comm.PortModel;
import kr.co.n3soft.n3com.model.comm.UMLContainerModel;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.project.browser.UMLTreeModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.draw2d.geometry.Point;

public class SyncManager {
	private static SyncManager instance;
	public static SyncManager getInstance() {
		if (instance == null) {
			instance = new SyncManager();
			
			

			return instance;
		}
		else {
			return instance;
		}
	}
	
	public void ExeSync(AtomicComponentModel acm,PortModel pm,UMLModel um ,int key){
//		java.util.ArrayList list = new java.util.ArrayList();
		String id = acm.getUMLDataModel().getId();
		ProjectManager.getInstance().getModelBrowser().searchCoreDiagramIdModel(id);
		 
		switch (key) {
		//port add
		case 1:
			java.util.ArrayList al = ProjectManager.getInstance().getSearchModel();
			for(int i = 0; i<al.size();i++){
    			UMLModel um1 = (UMLModel)al.get(i);
    			if(um1!=um){
        				if(um1 instanceof ComponentModel){
        					ComponentModel cm = (ComponentModel)um1;
        					PortModel pm2 = null;
        					if(pm instanceof MethodInputPortModel)
        					pm2 = new MethodInputPortModel(cm);
        					else if(pm instanceof MethodOutputPortModel)
            					pm2 = new MethodOutputPortModel(cm);
        					else if(pm instanceof DataInputPortModel)
            					pm2 = new DataInputPortModel(cm);
        					else if(pm instanceof DataOutputPortModel)
            					pm2 = new DataOutputPortModel(cm);
        					else if(pm instanceof EventInputPortModel)
            					pm2 = new EventInputPortModel(cm);
        					else if(pm instanceof EventOutputPortModel)
            					pm2 = new EventOutputPortModel(cm);
        					
        					
        					
        					cm.createPort(pm2, (UMLContainerModel)cm.getAcceptParentModel());
        					if(ProjectManager.getInstance().getUMLEditor()!=null && 
        							ProjectManager.getInstance().getUMLEditor().getDiagram()==cm.getAcceptParentModel()){
        						
        						int cx = cm.getLocation().x;
        						int cy = cm.getLocation().y;
        						int cheight = cm.getSize().height;
        						int cwidth = cm.getSize().width;
        						
        						System.out.println("tempx============>"+pm.temp_x);
        						System.out.println("temp_y============>"+pm.temp_y);
        						System.out.println("cx============>"+cx);
        						System.out.println("cy============>"+cy);
        						System.out.println("cheight============>"+cheight);
        						System.out.println("cwidth============>"+cwidth);
        						if(cm.getRectangle().contains(new Point(pm.temp_x,pm.temp_y))){
        							if(pm.temp_x<cx+cwidth/2){
        								pm.temp_x = cx-30;
        							}
        							else if(pm.temp_x>cx+cwidth/2){
        								pm.temp_x = cx+cwidth;
        							}
        							
//        							if(pm.temp_y<cy+cheight/2){
//        								pm.temp_y = cy;
//        							}
//        							else if(pm.temp_y>cy+cm.getBoundRectangle().height/2){
//        								pm.temp_y = cy+cheight;
//        							}
        							
        						    pm2.setLocation(new Point(pm.temp_x,pm.temp_y));
//        						}
        						}
        					}
        					
        					pm2.getUMLDataModel().setId(pm.getID());
        					pm2.getElementLabelModel().setPortId(pm.getID());
        					pm2.setName(pm.getName());
        				}
        					
        				

    				
    			}
    		}
    		ProjectManager.getInstance().getSearchModel().clear();
			break;
		case 2:
			
			break;
		case 3:
			
			break;

		default:
			break;
		}
		
		
	}
	
	public void ExeSyncB(AtomicComponentModel acm,PortModel pm,UMLModel um ,int key){
//		java.util.ArrayList list = new java.util.ArrayList();
		String id = acm.getUMLDataModel().getId();
	
		 
		switch (key) {
		//port add
		case 1:
			UMLTreeModel ut = acm.getCoreUMLTreeModel();
			break;
		case 2:
			
			break;
		case 3:
			
			break;

		default:
			break;
		}
		
		
	}

}
