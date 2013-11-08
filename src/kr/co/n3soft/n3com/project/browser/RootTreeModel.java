package kr.co.n3soft.n3com.project.browser;

import java.util.HashMap;

import javac.parser.SourceManager;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.component.AtomicComponentModel;
import kr.co.n3soft.n3com.parser.ModelManager;

public class RootTreeModel extends UMLTreeParentModel  {
    private int newpackageCount = 1;
    

    public RootTreeModel(String name) {
        super(name);
    }
    
//	 public void run(IProgressMonitor monitor) throws InvocationTargetException,
//    InterruptedException {
//    	try	{
//    		monitor.beginTask("Save" +
//					" Project",
//			        true ? IProgressMonitor.UNKNOWN : 1000);
//    		for(int i=0;i<this.getChildren().length;i++){
//        		UMLTreeModel ut = (UMLTreeModel)this.getChildren()[i];
//        		if(ut instanceof UMLTreeParentModel){
//        			UMLTreeParentModel up = (UMLTreeParentModel)ut;	
//        			up.writeModel(sb);
//        		}
//        		else{
//        			UMLModel um1 = (UMLModel)ut.getRefModel();
//        			ModelManager.getInstance().writeModel(um1);
//        			
//        		}
//        	}
//        	
//        	ModelManager.getInstance().writeViews(sb);
//			monitor.done();
//			
//    	}
//    	catch(Exception e){
//    		e.printStackTrace();
//    	}
//			
//    	
//    }
    
    public void writeModel(StringBuffer sb){
    	
    		HashMap hm = ModelManager.getInstance().getViewStore();
    		hm.clear();
    	for(int i=0;i<this.getChildren().length;i++){
    		UMLTreeModel ut = (UMLTreeModel)this.getChildren()[i];
    		if(ut instanceof RootLibTreeModel || ut instanceof RootServerRepositoryTreeMode
    				|| ut instanceof RootTemplateTreeModel)
    			continue;
    		if(ut instanceof UMLTreeParentModel){
    			UMLTreeParentModel up = (UMLTreeParentModel)ut;	
    			up.writeModel(sb);
    		}
    		else{
    			UMLModel um1 = (UMLModel)ut.getRefModel();
    			ModelManager.getInstance().writeModel(um1,null);
    			
    			
    			
    		}
    	}
    	
    	ModelManager.getInstance().writeViews(sb);
    }
    
    
    public void writeJavas(){
    	for(int i=0;i<this.getChildren().length;i++){
    		UMLTreeModel ut = (UMLTreeModel)this.getChildren()[i];
    		if(ut instanceof UMLTreeParentModel){
    			UMLTreeParentModel up = (UMLTreeParentModel)ut;	
    			up.writeJava();
    		}
    		else{
    			UMLModel um1 = (UMLModel)ut.getRefModel();
    			SourceManager.getInstance().writeJava(um1);
    			
    		}
    	}
    }
    
    public void writeHs(){
    	for(int i=0;i<this.getChildren().length;i++){
    		UMLTreeModel ut = (UMLTreeModel)this.getChildren()[i];
    		if(ut instanceof UMLTreeParentModel){
    			UMLTreeParentModel up = (UMLTreeParentModel)ut;	
    			up.writeH();
    		}
    		else{
    			UMLModel um1 = (UMLModel)ut.getRefModel();
    			SourceManager.getInstance().writeH(um1);
    			
    		}
    	}
    }
   
}
