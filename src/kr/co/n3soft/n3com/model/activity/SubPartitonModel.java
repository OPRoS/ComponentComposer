package kr.co.n3soft.n3com.model.activity;

import java.util.ArrayList;

import kr.co.n3soft.n3com.model.comm.BarModel;
import kr.co.n3soft.n3com.model.comm.ElementLabelModel;
import kr.co.n3soft.n3com.model.comm.LineModel;
import kr.co.n3soft.n3com.model.comm.UMLDataModel;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.diagram.UMLDiagramModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.draw2d.BorderLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;

public class SubPartitonModel extends UMLDiagramModel {
	static final long serialVersionUID = 1;
	public static int count = 0;
	ElementLabelModel name = null;
	ElementLabelModel streotype = null;
	ObjectNodeModel objectNodeModel = null;
	BarModel barModel = null;
	java.util.List temp_childs = null;
	boolean isMode = false;

	public SubPartitonModel() {
		size.width = 100;
		size.height = 20;
		name = new ElementLabelModel();//PKY 08052101 S 컨테이너에서 그룹으로 변경
		name.setType("NAME");
		name.setLayout(BorderLayout.TOP);
		name.setParentLayout(1);
		name.setLabelContents("[partition]");
		this.addChild(name);
		this.uMLDataModel.setElementProperty(name.getType(), name);
	}

	public SubPartitonModel(UMLDataModel p) {
		name = (ElementLabelModel)p.getElementProperty("NAME");
		this.addChild(name);
		this.setUMLDataModel(p);
		//		this.setAttachElementLabelModel(name);
	}

	public void setName(String p){
		name.setLabelContents(p);
	}

	public void setIsMode(boolean p) {
		this.isMode = p;
	}

	public boolean getIsMode() {
		return this.isMode;
	}

	public void setSize(Dimension d) {
		if (size.equals(d))
			return;
		d.height = 20;
		//		d.width = d.width-20;
		size = d;
		firePropertyChange(ID_SIZE, null, size); //$NON-NLS-1$
	}

	public void setLocation(Point p) {
		if (location.equals(p))
			return;
		//PKY 08052101 S 컨테이너에서 그룹으로 변경
		if(!(this instanceof SubPartitonModel)){	
			p.x = 0;
		}
		//PKY 08081801 S 엑티비티 파티션 움직임이 비정상적인 문제
		if(this.getParentModel()!=null){
			p.x = this.getParentModel().getLocation().x ;
			if( p.y <= this.getParentModel().getLocation().y){
				if(((UMLModel)this.getAcceptParentModel()).getStereotype().equals(""))
				p.y = this.getParentModel().getLocation().y+15;
				else
					p.y = this.getParentModel().getLocation().y+29;	
			}if(p.y >= this.getParentModel().getLocation().y+this.getParentModel().getSize().height){
				p.y = this.getParentModel().getLocation().y+this.getParentModel().getSize().height-25;
			}
		}
		


//		if(ProjectManager.getInstance().getSelectNodes()!=null)
//			if(!(ProjectManager.getInstance().getSelectNodes().size()>1)){
//
//				if(this instanceof SubPartitonModel){
//					if (this.getParentModel() instanceof UMLModel){
//						UMLModel model= (UMLModel)this.getParentModel();
//						int width=model.getLocation().x+model.getSize().width;
//						int height=model.getLocation().y+model.getSize().height;
//						System.out.println(this.location.x+">"+model.getLocation().x+"||"+this.location.x+">"+width);
////						if((p.x<model.getLocation().x-10) || (p.x>width)){
////						return;
////						}
//						p.x=model.getLocation().x;
//						if((p.y<model.getLocation().y) || (p.y>height)){
//							return;
//						}
//
//					}else if(this.getUMLDataModel().getElementProperty("GROUP_PARENTS")!=null){//PKY 08052601 S Partition 저장 불러올시 해제되는문제 
//						ArrayList arrayItem=(ArrayList)this.getUMLDataModel().getElementProperty("GROUP_PARENTS");
//						for(int i=0;i<arrayItem.size();i++){
//							if(arrayItem.get(i) instanceof FinalActivityModel ||arrayItem.get(i) instanceof FinalStrcuturedActivityModel){
//
//								UMLModel model= (UMLModel)arrayItem.get(i);
//								int width=model.getLocation().x+model.getSize().width;
//								int height=model.getLocation().y+model.getSize().height;
//								System.out.println(this.location.x+">"+model.getLocation().x+"||"+this.location.x+">"+width);
////								if((p.x<model.getLocation().x-10) || (p.x>width)){
////								return;
////								}
//								p.x=model.getLocation().x;
////								p.y=model.getLocation().y;
//								if((p.y<model.getLocation().y) || (p.y>height)){
//									return;
//								}
//
//
//							}
//						}
//					}
//					//PKY 08052601 E Partition 저장 불러올시 해제되는문제 
//				}
//			}
		//PKY 08052101 E 컨테이너에서 그룹으로 변경
		//PKY 08081801 E 엑티비티 파티션 움직임이 비정상적인 문제
		location = p;
		firePropertyChange(ID_LOCATION, null, p); //$NON-NLS-1$

	}
	public String writeModel(){
		StringBuffer sb = new StringBuffer();
		sb.append("property SubPartiton="+this.getLocation().x+","+this.getLocation().y+","+this.getSize().width+","+this.getSize().height+","+this.name.getLabelContents()+","+this.isMode+"PROPERTY_N3EOF");
		return sb.toString();
	}

	public Object clone() {
		SubPartitonModel clone = new SubPartitonModel((UMLDataModel)this.getUMLDataModel().clone());
		//		clone.temp_childs = this.activityModel.getChildren();
		clone.setSize(this.getSize());
		clone.setLocation(this.getLocation());
		clone.sourceModel = this;
		ProjectManager.getInstance().setTempCopyMap(clone.sourceModel.getID(), clone);
		for (int i1 = 0; i1 < this.getSourceConnections().size(); i1++) {
			LineModel li = (LineModel)this.getSourceConnections().get(i1);
			LineModel liCopy = (LineModel)li.clone();
			ProjectManager.getInstance().addSelectLineModel(liCopy);
		}
		return clone;
	}
}
