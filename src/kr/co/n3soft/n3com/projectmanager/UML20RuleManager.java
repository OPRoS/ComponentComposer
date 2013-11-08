package kr.co.n3soft.n3com.projectmanager;

import kr.co.n3soft.n3com.model.comm.DataInputPortModel;
import kr.co.n3soft.n3com.model.comm.DataOutputPortModel;
import kr.co.n3soft.n3com.model.comm.EventInputPortModel;
import kr.co.n3soft.n3com.model.comm.EventOutputPortModel;
import kr.co.n3soft.n3com.model.comm.LineModel;
import kr.co.n3soft.n3com.model.comm.MethodInputPortModel;
import kr.co.n3soft.n3com.model.comm.MethodOutputPortModel;
import kr.co.n3soft.n3com.model.comm.PortModel;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.comm.UMLNoteModel;
import kr.co.n3soft.n3com.model.component.ComponentModel;
import kr.co.n3soft.n3com.model.diagram.N3EditorDiagramModel;
import kr.co.n3soft.n3com.model.umlclass.DependencyLineModel;
import kr.co.n3soft.n3com.model.usecase.NoteLineModel;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
//¼±µî
public class UML20RuleManager {
	 private static UML20RuleManager instance;
	 public static UML20RuleManager getInstance() {
	        if (instance == null) {
	            instance = new UML20RuleManager();
	            return instance;
	        }
	        else {
	            return instance;
	        }
	    }
	 
	 public boolean isLink(UMLModel source,UMLModel target,LineModel lineModel){
		 boolean isLink = true;
		 N3EditorDiagramModel nm = ProjectManager.getInstance().getUMLEditor().getDiagram();
		 

		 if(lineModel instanceof DependencyLineModel ){
			 isLink = true;
			if(source instanceof UMLNoteModel || target instanceof UMLNoteModel){
				return false;
			}
			 if(source instanceof PortModel && target instanceof ComponentModel){
				 isLink=  false;
			 }
			 if(source instanceof ComponentModel && target instanceof ComponentModel){
				 isLink=  false;
			 }
			 if(source instanceof PortModel && target instanceof ComponentModel){
				 isLink=  false;
			 }
			 if(source instanceof PortModel && target instanceof PortModel){
				 if(source instanceof MethodOutputPortModel){
					 if(target instanceof MethodInputPortModel){
						 isLink=  false;
					 }
					 else  if(target instanceof DataInputPortModel){
						 isLink=  false;
					 }
					 else  if(target instanceof EventInputPortModel){
						 isLink=  false;
					 }
					 else  if(target instanceof DataOutputPortModel){
						 isLink=  false;
					 }
					 else  if(target instanceof EventOutputPortModel){
						 isLink=  false;
					 }
				 }
				 else if(source instanceof MethodInputPortModel){
					
					 if(target instanceof DataInputPortModel){
						 isLink=  false;
					 }
					 else  if(target instanceof EventInputPortModel){
						 isLink=  false;
					 }
					 else  if(target instanceof DataOutputPortModel){
						 isLink=  false;
					 }
					 else  if(target instanceof EventOutputPortModel){
						 isLink=  false;
					 }
				 }
				 
				 else if(source instanceof DataOutputPortModel){
					 if(target instanceof MethodInputPortModel){
						 isLink=  false;
					 }
					 else  if(target instanceof EventInputPortModel){
						 isLink=  false;
					 }
					 else  if(target instanceof MethodOutputPortModel){
						 isLink=  false;
					 }
					 else  if(target instanceof EventOutputPortModel){
						 isLink=  false;
					 }
				 }
				 else if(source instanceof DataInputPortModel){
					
					 if(target instanceof DataOutputPortModel){
						 isLink=  false;
					 }
					 else  if(target instanceof EventInputPortModel){
						 isLink=  false;
					 }
					 else  if(target instanceof MethodInputPortModel){
						 isLink=  false;
					 }
					 else  if(target instanceof MethodOutputPortModel){
						 isLink=  false;
					 }
					 else  if(target instanceof EventOutputPortModel){
						 isLink=  false;
					 }
				 }
				 else if(source instanceof EventOutputPortModel){
					 if(target instanceof MethodInputPortModel){
						 isLink=  false;
					 }
					 else  if(target instanceof DataInputPortModel){
						 isLink=  false;
					 }
					 else  if(target instanceof MethodOutputPortModel){
						 isLink=  false;
					 }
					 else  if(target instanceof DataOutputPortModel){
						 isLink=  false;
					 }
				 }
				 else if(source instanceof EventInputPortModel){
					
					 if(target instanceof DataOutputPortModel){
						 isLink=  false;
					 }
					 else  if(target instanceof MethodInputPortModel){
						 isLink=  false;
					 }
					 else  if(target instanceof MethodOutputPortModel){
						 isLink=  false;
					 }
					 else  if(target instanceof DataInputPortModel){
						 isLink=  false;
					 }
				 }
				 if(source instanceof MethodOutputPortModel && target instanceof MethodOutputPortModel){
					 if(nm!=null){
						 boolean ischk = false;
						 MethodOutputPortModel mo =(MethodOutputPortModel)source;
						 UMLModel um = mo.getPortContainerModel();
						 UMLModel main = (UMLModel)nm.getChildren().get(0);
						 MethodOutputPortModel msource = (MethodOutputPortModel)source;
						 for(int i=0;i<msource.getSourceConnections().size();i++){
							 Object lm1 = (Object)msource.getSourceConnections().get(i);
							 if(lm1 instanceof LineModel){
								 LineModel lm2 = (LineModel)lm1;
								 if(lm2.getTarget() instanceof MethodOutputPortModel){
									 ischk=  true;
									 break;
								 }
							 }
							 
						 }
						 
						 
						 
						 
						 if(um != main){
							 isLink=  false;
						 }
						 else if(!ischk){
							 isLink=  true;
						 }
						 else{
							 isLink=  false;
						 }
					 }
					 
				 }
				 else if(source instanceof MethodInputPortModel && target instanceof MethodInputPortModel){
					 if(nm!=null){
						 MethodInputPortModel mo =(MethodInputPortModel)source;
						 UMLModel um = mo.getPortContainerModel();
						 UMLModel main = (UMLModel)nm.getChildren().get(0);
						 if(um != main){
							 isLink=  true;
						 }
						 else{
							 isLink=  false;
						 }
					 }
				 }
				 else if(source instanceof DataInputPortModel && target instanceof DataInputPortModel){
					 if(nm!=null){
						 DataInputPortModel mo =(DataInputPortModel)source;
						 UMLModel um = mo.getPortContainerModel();
						 UMLModel main = (UMLModel)nm.getChildren().get(0);
						 if(um != main){
							 isLink=  false;
						 }
						 else{
							 isLink=  true;
						 }
					 }
				 }
				 else if(source instanceof DataOutputPortModel && target instanceof DataOutputPortModel){
					 if(nm!=null){
						 DataOutputPortModel mo =(DataOutputPortModel)source;
						 UMLModel um = mo.getPortContainerModel();
						 UMLModel main = (UMLModel)nm.getChildren().get(0);
						 if(um != main){
							 isLink=  true;
						 }
						 else{
							 isLink=  false;
						 }
					 }
				 }
				 else if(source instanceof EventInputPortModel && target instanceof EventInputPortModel){
					 if(nm!=null){
						 EventInputPortModel mo =(EventInputPortModel)source;
						 UMLModel um = mo.getPortContainerModel();
						 UMLModel main = (UMLModel)nm.getChildren().get(0);
						 if(um != main){
							 isLink=  false;
						 }
						 else{
							 isLink=  true;
						 }
					 }
				 }
				 else if(source instanceof EventOutputPortModel && target instanceof EventOutputPortModel){
					 if(nm!=null){
						 EventOutputPortModel mo =(EventOutputPortModel)source;
						 UMLModel um = mo.getPortContainerModel();
						 UMLModel main = (UMLModel)nm.getChildren().get(0);
						 if(um != main){
							 isLink=  true;
						 }
						 else{
							 isLink=  false;
						 }
					 }
				 }
				 
//				 if(source instanceof MethodInputPortModel && target instanceof MethodOutputPortModel){
//					 MethodInputPortModel mo = (MethodInputPortModel)source;
//					 MethodOutputPortModel mi = (MethodOutputPortModel)target;
//					 ComponentModel cm = (ComponentModel)mi.getPortContainerModel();
//					 String type = "";
////					 if("<<composite>>".equals(cm.getStereotype())){
////						 N3EditorDiagramModel nm1 = ProjectManager.getInstance().getModelBrowser().searchSubComponentDiagram((UMLTreeParentModel)cm.getUMLTreeModel());
////						 if(nm1!=null ){
////							
////								 
//////							 N3EditorDiagramModel nm1 = (N3EditorDiagramModel)obj;
////							 UMLModel main = (UMLModel)nm1.getChildren().get(0);
////							 if(main instanceof ComponentModel){
////								 ComponentModel cmm = (ComponentModel)main;
////								 for(int i=0;i<cmm.getPortManager().getPorts().size();i++){
////									 PortModel pm = (PortModel)cmm.getPortManager().getPorts().get(i);
////									 if(pm instanceof MethodOutputPortModel){
////										 String name = pm.getName();
////										 System.out.print("name=========>"+name);
////										 if(mi.getName().equals(pm.getName())){
////											 for(int i1=0;i1<pm.getSourceConnections().size();i1++){
////												 LineModel lm = (LineModel)pm.getSourceConnections().get(i1);
////												 UMLModel up = lm.getTarget();
////												 if(up instanceof PortModel){
////													 PortModel ppm = (PortModel)up;
////													 type = ppm.getType();
////													 break;
////												 }
////											 }
////										 }
////									 }
////								 }
////							 }
////						 }
////					 
////					 }
////					 else{
////						 type = mi.getType();
////					 }
//					 
//					 
////					 if(mo.getType()!=null && !mo.getType().trim().equals("") && !mo.getType().equals(type))
////						 isLink=  false;
//				 }
//				 
//				 else if(source instanceof DataOutputPortModel && target instanceof DataInputPortModel){
//					 DataOutputPortModel mo = (DataOutputPortModel)source;
//					 DataInputPortModel mi = (DataInputPortModel)target;
//					 ComponentModel cm = (ComponentModel)mi.getPortContainerModel();
//					 String type = "";
////					 if("<<composite>>".equals(cm.getStereotype())){
////						 N3EditorDiagramModel nm1 = ProjectManager.getInstance().getModelBrowser().searchSubComponentDiagram((UMLTreeParentModel)cm.getUMLTreeModel());
////						 if(nm1!=null ){
////							
////								 
//////							 N3EditorDiagramModel nm1 = (N3EditorDiagramModel)obj;
////							 UMLModel main = (UMLModel)nm1.getChildren().get(0);
////							 if(main instanceof ComponentModel){
////								 ComponentModel cmm = (ComponentModel)main;
////								 for(int i=0;i<cmm.getPortManager().getPorts().size();i++){
////									 PortModel pm = (PortModel)cmm.getPortManager().getPorts().get(i);
////									 if(pm instanceof MethodOutputPortModel){
////										 String name = pm.getName();
////										 System.out.print("name=========>"+name);
////										 if(mi.getName().equals(pm.getName())){
////											 for(int i1=0;i1<pm.getSourceConnections().size();i1++){
////												 LineModel lm = (LineModel)pm.getSourceConnections().get(i1);
////												 UMLModel up = lm.getTarget();
////												 if(up instanceof PortModel){
////													 PortModel ppm = (PortModel)up;
////													 type = ppm.getType();
////													 break;
////												 }
////											 }
////										 }
////									 }
////								 }
////							 }
////						 }
////					 
////					 }
////					 else{
////						 type = mi.getType();
////					 }
//					 
//					 
////					 if(mo.getType()!=null && !mo.getType().trim().equals("") && !mo.getType().equals(type))
////						 isLink=  false;
//				 }
//				 
//				 else if(source instanceof EventOutputPortModel && target instanceof EventInputPortModel){
//					 EventOutputPortModel mo = (EventOutputPortModel)source;
//					 EventInputPortModel mi = (EventInputPortModel)target;
//					 ComponentModel cm = (ComponentModel)mi.getPortContainerModel();
//					 String type = "";
////					 if("<<composite>>".equals(cm.getStereotype())){
////						 N3EditorDiagramModel nm1 = ProjectManager.getInstance().getModelBrowser().searchSubComponentDiagram((UMLTreeParentModel)cm.getUMLTreeModel());
////						 if(nm1!=null ){
////							
////								 
//////							 N3EditorDiagramModel nm1 = (N3EditorDiagramModel)obj;
////							 UMLModel main = (UMLModel)nm1.getChildren().get(0);
////							 if(main instanceof ComponentModel){
////								 ComponentModel cmm = (ComponentModel)main;
////								 for(int i=0;i<cmm.getPortManager().getPorts().size();i++){
////									 PortModel pm = (PortModel)cmm.getPortManager().getPorts().get(i);
////									 if(pm instanceof MethodOutputPortModel){
////										 String name = pm.getName();
////										 System.out.print("name=========>"+name);
////										 if(mi.getName().equals(pm.getName())){
////											 for(int i1=0;i1<pm.getSourceConnections().size();i1++){
////												 LineModel lm = (LineModel)pm.getSourceConnections().get(i1);
////												 UMLModel up = lm.getTarget();
////												 if(up instanceof PortModel){
////													 PortModel ppm = (PortModel)up;
////													 type = ppm.getType();
////													 break;
////												 }
////											 }
////										 }
////									 }
////								 }
////							 }
////						 }
////					 
////					 }
////					 else{
////						 type = mi.getType();
////					 }
//					 
//					 
////					 if(mo.getType()!=null && !mo.getType().trim().equals("") && !mo.getType().equals(type))
////						 isLink=  false;
//				 }
				 
				 
			 
				 if(source instanceof MethodOutputPortModel && target instanceof MethodInputPortModel){
					 isLink=  false;
				 }
				 else if(source instanceof DataInputPortModel && target instanceof MethodInputPortModel){
					 isLink=  false;
				 }
				 
				 else if(source instanceof MethodOutputPortModel){
					 if(target instanceof DataInputPortModel){
						 isLink=  false;
					 }
					 else if(target instanceof EventInputPortModel){
						 isLink=  false;
					 }
//					 else if(target instanceof MethodInputPortModel){
//						 MethodOutputPortModel mo = (MethodOutputPortModel)source;
//						 MethodInputPortModel mi = (MethodInputPortModel)target;
//						 if(mo.getType()!=null && !mo.getType().equals(mi.getType()))
//							 isLink=  false;
//						 
//					 }
				 }
				 else if(source instanceof DataOutputPortModel){
					 if(target instanceof MethodInputPortModel){
						 isLink=  false;
					 }
					 else if(target instanceof EventInputPortModel){
						 isLink=  false;
					 }
//					 else if(target instanceof DataInputPortModel){
//						 DataOutputPortModel mo = (DataOutputPortModel)source;
//						 DataInputPortModel mi = (DataInputPortModel)target;
//						 if(mo.getType()!=null && !mo.getType().equals(mi.getType()))
//							 isLink=  false;
//						 
//					 }
				 }
				 else if(source instanceof EventOutputPortModel){
					 if(target instanceof MethodInputPortModel){
						 isLink=  false;
					 }
					 else if(target instanceof DataInputPortModel){
						 isLink=  false;
				 }
//					 else if(target instanceof EventInputPortModel){
//						 EventOutputPortModel mo = (EventOutputPortModel)source;
//						 EventInputPortModel mi = (EventInputPortModel)target;
//						 if(mo.getType()!=null && !mo.getType().equals(mi.getType()))
//							 isLink=  false;
//						 
//					 }
				 }
			 }
			 
			 
//			 if()
			 if( isLink!=true){
				 MessageBox dialog = new MessageBox(ProjectManager.getInstance().window.getShell(),SWT.ICON_WARNING);
				 dialog.setText("Message");
				 dialog.setMessage("The requested connection is not compliant");
				 dialog.open();
				 
				 return false;
			 }
			 
		 }
		 else if(lineModel instanceof NoteLineModel ){
			 
			 if(source instanceof UMLNoteModel || 
					 target instanceof UMLNoteModel){
				 return true;
			 }
			 else{
				 return false;
			 }
		 }
		
			 
		 
		 return true;
		 
	 }

}
