package javac.parser;

import java.util.Iterator;

import kr.co.n3soft.n3com.model.comm.LineModel;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.diagram.N3EditorDiagramModel;
import kr.co.n3soft.n3com.model.usecase.GeneralizeLineModel;
import kr.co.n3soft.n3com.model.usecase.RealizeLineModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.draw2d.geometry.Point;

public class ImportManager {
	public java.util.ArrayList searchModel = new java.util.ArrayList();
	public java.util.HashMap pkgData = new java.util.HashMap();
	public void layoutDiagarm(){
		for(int i=0;i<this.searchModel.size();i++){
			ReverseFromJavaToModel rtm = (ReverseFromJavaToModel)this.searchModel.get(i);
		    for(int i1=0;i1<rtm.extendsClass.size();i1++){
		    	String ec = (String)rtm.extendsClass.get(i1);
		    	java.util.ArrayList list = (java.util.ArrayList)pkgData.get(rtm.pkg);
		    	if(list!=null && list.size()>0){
		    		for(int i2=0;i2<list.size();i2++){
		    			ReverseFromJavaToModel rtm2 = (ReverseFromJavaToModel)list.get(i2);
		    			if(rtm2.name.equals(ec)){
		    				rtm.extendsClassUmlModels.add(rtm2.um);
		    				break;
		    			}
		    		}
		    	}
		    	for(int i3=0;i3<rtm.imports.size();i3++){
		    		ImportData id = (ImportData)rtm.imports.get(i3);
		    		if(id.key.equals("*")){
		    			java.util.ArrayList list3 = (java.util.ArrayList)pkgData.get(id.pkg);
		    			if(list3!=null && list3.size()>0){
				    		for(int i2=0;i2<list3.size();i2++){
				    			ReverseFromJavaToModel rtm2 = (ReverseFromJavaToModel)list3.get(i2);
				    			if(rtm2.name.equals(ec)){
				    				rtm.extendsClassUmlModels.add(rtm2.um);
				    				break;
				    			}
				    		}
				    	}
		    		}
		    		else{
		    			if(id.key.equals(ec)){
		    				java.util.ArrayList list3 = (java.util.ArrayList)pkgData.get(id.pkg);
		    				if(list3!=null && list3.size()>0){
					    		for(int i2=0;i2<list3.size();i2++){
					    			ReverseFromJavaToModel rtm2 = (ReverseFromJavaToModel)list3.get(i2);
					    			if(rtm2.name.equals(ec)){
					    				rtm.extendsClassUmlModels.add(rtm2.um);
					    				break;
					    			}
					    		}
					    	}
		    			}
//		    			java.util.ArrayList list3 = (java.util.ArrayList)pkgData.get(id.pkg);
		    			
		    		}
		    	}
		    }
		    
		    for(int i1=0;i1<rtm.interfaces.size();i1++){
		    	String ec = (String)rtm.interfaces.get(i1);
		    	java.util.ArrayList list = (java.util.ArrayList)pkgData.get(rtm.pkg);
		    	if(list!=null && list.size()>0){
		    		for(int i2=0;i2<list.size();i2++){
		    			ReverseFromJavaToModel rtm2 = (ReverseFromJavaToModel)list.get(i2);
		    			if(rtm2.name.equals(ec)){
		    				rtm.interfaceUmlModels.add(rtm2.um);
		    				break;
		    			}
		    		}
		    	}
		    	for(int i3=0;i3<rtm.imports.size();i3++){
		    		ImportData id = (ImportData)rtm.imports.get(i3);
		    		if(id.key.equals("*")){
		    			java.util.ArrayList list3 = (java.util.ArrayList)pkgData.get(id.pkg);
		    			if(list3!=null && list3.size()>0){
				    		for(int i2=0;i2<list3.size();i2++){
				    			ReverseFromJavaToModel rtm2 = (ReverseFromJavaToModel)list3.get(i2);
				    			if(rtm2.name.equals(ec)){
				    				rtm.interfaceUmlModels.add(rtm2.um);
				    				break;
				    			}
				    		}
				    	}
		    		}
		    		else{
		    			if(id.key.equals(ec)){
		    				java.util.ArrayList list3 = (java.util.ArrayList)pkgData.get(id.pkg);
		    				if(list3!=null && list3.size()>0){
					    		for(int i2=0;i2<list3.size();i2++){
					    			ReverseFromJavaToModel rtm2 = (ReverseFromJavaToModel)list3.get(i2);
					    			if(rtm2.name.equals(ec)){
					    				rtm.interfaceUmlModels.add(rtm2.um);
					    				break;
					    			}
					    		}
					    	}
		    			}
//		    			java.util.ArrayList list3 = (java.util.ArrayList)pkgData.get(id.pkg);
		    			
		    		}
		    	}
		    }
		}
		
		Iterator it =  pkgData.keySet().iterator();
		while(it.hasNext()){
			 String pkg = (String)it.next();
			    java.util.ArrayList list = (java.util.ArrayList)ProjectManager.getInstance().getReverseModelMap().get(pkg);
			    for(int k=0;k<list.size();k++){
			    	ReverseFromJavaToModel rtm = (ReverseFromJavaToModel)list.get(k);
			    	rtm.n3EditorDiagramModel.addChild(rtm.um);
			    	Point pt = new Point();
			    	int y = 0;
			    	int x = 0;
			    	if(k>0){
	                	y = k/5*200;
	                	x = k%5*200;
	                }
	                
	                pt.x = x;
	                pt.y = y;
			    	
			    	rtm.um.setLocation(pt);
			    	rtm.um.getUMLTreeModel().addN3UMLModelDeleteListener(rtm.n3EditorDiagramModel);
			    }
			    for(int k=0;k<list.size();k++){
			    	ReverseFromJavaToModel rtm = (ReverseFromJavaToModel)list.get(k);
			    	if(rtm.extendsClassUmlModels.size()>0){
			    		for(int k1=0;k1<rtm.extendsClassUmlModels.size();k1++){
			    			UMLModel um = (UMLModel)rtm.extendsClassUmlModels.get(k1);
			    			if(!this.isInDiagram(um, rtm.n3EditorDiagramModel)){
			    				rtm.n3EditorDiagramModel.addChild(um);
			    				um.getUMLTreeModel().addN3UMLModelDeleteListener(rtm.n3EditorDiagramModel);
			    			}
			    			this.createGeneralizeLineModel(rtm.um, um,rtm.n3EditorDiagramModel);
			    		}
			    	}
			    	
			    	if(rtm.interfaceUmlModels.size()>0){
			    		for(int k1=0;k1<rtm.interfaceUmlModels.size();k1++){
			    			UMLModel um = (UMLModel)rtm.interfaceUmlModels.get(k1);
			    			if(!this.isInDiagram(um, rtm.n3EditorDiagramModel)){
			    				rtm.n3EditorDiagramModel.addChild(um);
			    				um.getUMLTreeModel().addN3UMLModelDeleteListener(rtm.n3EditorDiagramModel);
			    			}
			    			this.createRealizeLineModel(rtm.um, um,rtm.n3EditorDiagramModel);
			    		}
			    	}
//			    	rtm.n3EditorDiagramModel.addChild(rtm.um);
//			    	rtm.um.getUMLTreeModel().addN3UMLModelDeleteListener(rtm.n3EditorDiagramModel);
			    }
			   }
		
		
		
		
	}
	
	public boolean isInDiagram(UMLModel um,N3EditorDiagramModel ndm ){
		for(int i=0;i<ndm.getChildren().size();i++){
			Object obj = ndm.getChildren().get(i);
			if(obj instanceof UMLModel){
				if(um==obj){
					return true;
				}
			}
			
		}
		return false;
		
	}
	
	public void createGeneralizeLineModel(UMLModel source,UMLModel target,N3EditorDiagramModel ndm){
		LineModel wire = null;
//		N3EditorDiagramModel ndm = null;
		wire=new GeneralizeLineModel();
		if (source != null) {
	        
        	if(wire instanceof GeneralizeLineModel){
        		if(source.getParentModel()!=null &&  target.getParentModel()!=null){
        			source.getParentModel().getParents().add(target.getParentModel());
        	
        		}
        		else if(source.getParentModel()==null && target.getParentModel()!=null){
        			source.getParents().add(target.getParentModel());
        		}
        		else if(source.getParentModel()!=null && target.getParentModel()==null){
        			source.getParentModel().getParents().add(target);
        		}
        		else if(source.getParentModel()==null && target.getParentModel()==null){
        			source.getParents().add(target);
        		}
        	}
        	
            wire.detachSource();
            wire.setSource(source);
            wire.setSourceTerminal("B");
            wire.attachSource();
        }
        if (target != null) {
            wire.detachTarget();
            wire.setTarget(target);
            wire.setTargetTerminal("B");
            wire.attachTarget(ndm);
       
           
        }
	}
	
	public void createRealizeLineModel(UMLModel source,UMLModel target,N3EditorDiagramModel ndm){
		LineModel wire = null;
//		N3EditorDiagramModel ndm = null;
		wire=new RealizeLineModel();
		if (source != null) {
	              	
            wire.detachSource();
            wire.setSource(source);
            wire.setSourceTerminal("B");
            wire.attachSource();
        }
        if (target != null) {
            wire.detachTarget();
            wire.setTarget(target);
            wire.setTargetTerminal("B");
            wire.attachTarget(ndm);
       
           
        }
	}

}
