package kr.co.n3soft.n3com.model.activity;

import kr.co.n3soft.n3com.model.comm.ElementLabelModel;
import kr.co.n3soft.n3com.model.comm.UMLContainerModel;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.diagram.UMLDiagramModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.EditPart;

public class TextAttachModel extends UMLDiagramModel {
    public ElementLabelModel text = null;

    public void setAttachElementLabelModel(ElementLabelModel p) {
        this.text = p;
    }

    public void addTextAttachParentDiagram(UMLContainerModel p, Point location) {
        if (text != null)
            p.addChild(text);
    }

    public void removeTextAttachParentDiagram(UMLContainerModel p, Point location) {
        if (text != null)
            p.removeChild(text);
    }

    public ElementLabelModel getAttachElementLabelModel() {
        return this.text;
    }

    public void setLocation(Point p) {
    	//PKY 08081101 S 포함관계 오른쪽 메뉴로 해서 합치는 불편함 문제 개선
    	int x = this.getLocation().x;
		int y = this.getLocation().y;

		Point moveLocation = new Point();
		moveLocation.x = p.x - this.getLocation().x ;
		moveLocation.y = p.y - this.getLocation().y;
		lumpLocation(this,moveLocation);
		//PKY 08081101 E 포함관계 오른쪽 메뉴로 해서 합치는 불편함 문제 개선
        if (location.equals(p))
            return;
        location = p;

        Point pt3 = new Point(p.x, p.y - 10);
        if (text != null)
            text.setLocation(pt3);
        firePropertyChange(ID_LOCATION, null, p); //$NON-NLS-1$
		if(ProjectManager.getInstance().getSelectNodes()!=null)
			if(ProjectManager.getInstance().getSelectNodes().size()>1){
				return;
			}

		java.util.ArrayList models = new  java.util.ArrayList();
        if( this.getUMLDataModel().getElementProperty("GROUP_CHILDRENS")!=null){
			x=p.x-x;
			y=p.y-y;	
			java.util.List list=ProjectManager.getInstance().getSelectNodes();
		if(list.size()>0){
				UMLModel model1=null;
				if(list.get(0) instanceof EditPart){
					EditPart EditPart =(EditPart)list.get(0);
					model1=(UMLModel)EditPart.getModel();
				}
				else if(list.get(0) instanceof UMLModel){
					model1=(UMLModel)list.get(0);
				}
				if(model1!=null){
					if(ProjectManager.getInstance().getCopyModelType(model1)!=-1){
						if(model1==this){   

							models=(java.util.ArrayList)this.getUMLDataModel().getElementProperty("GROUP_CHILDRENS");
							for(int i=0; i<models.size();i++){
								if(models.get(i) instanceof UMLModel){
									UMLModel model=(UMLModel)models.get(i);
									Point point = new Point(model.getLocation().x+x, model.getLocation().y+y);
									model.setLocation(point);
								}
							}
						}
					}
				}

			}
		}
		if( this.getUMLDataModel().getElementProperty("GROUP_PARENTS")!=null){
			models=(java.util.ArrayList)this.getUMLDataModel().getElementProperty("GROUP_PARENTS");
			for(int i=0; i<models.size();i++){
				System.out.println("x"+((UMLModel)models.get(i)).getLocation().x);
				System.out.println("width"+((UMLModel)models.get(i)).getSize().width);
				System.out.println("y"+((UMLModel)models.get(i)).getLocation().y);
				System.out.println("height"+((UMLModel)models.get(i)).getSize().height);
//				int width=((UMLModel)models.get(i)).getLocation().x+((UMLModel)models.get(i)).getSize().width;
//				int height=((UMLModel)models.get(i)).getLocation().y+((UMLModel)models.get(i)).getSize().height;
				int width=((UMLModel)models.get(i)).getLocation().x+((UMLModel)models.get(i)).getSize().width;
				int height=((UMLModel)models.get(i)).getLocation().y+((UMLModel)models.get(i)).getSize().height;
				if(width<this.location.x||((UMLModel)models.get(i)).getLocation().x>this.location.x){
					if(((UMLModel)models.get(i)).getUMLDataModel().getElementProperty("GROUP_CHILDRENS")!=null){
						java.util.ArrayList arrayList=(java.util.ArrayList)((UMLModel)models.get(i)).getUMLDataModel().getElementProperty("GROUP_CHILDRENS");
						for(int j=0;j<arrayList.size();j++){
							if(arrayList.get(j)==this){
								arrayList.remove(j);    						   
							}
						}
						((UMLModel)models.get(i)).getUMLDataModel().setElementProperty("GROUP_CHILDRENS", arrayList);
					}
					this.getUMLDataModel().removeProperty("GROUP_PARENTS");
				}
				else if(height<this.location.y||((UMLModel)models.get(i)).getLocation().y>this.location.y){
					if(((UMLModel)models.get(i)).getUMLDataModel().getElementProperty("GROUP_CHILDRENS")!=null){
						java.util.ArrayList arrayList=(java.util.ArrayList)((UMLModel)models.get(i)).getUMLDataModel().getElementProperty("GROUP_CHILDRENS");
						for(int j=0;j<arrayList.size();j++){
							if(arrayList.get(j)==this){
								arrayList.remove(j);    						   
							}
						}
						((UMLModel)models.get(i)).getUMLDataModel().setElementProperty("GROUP_CHILDRENS", arrayList);
					}
					this.getUMLDataModel().removeProperty("GROUP_PARENTS");
				}
			}
		}
		//PKY 08071601 E 인터페이스 그룹범위에서 벗어나면 자동으로 그룹해제가 안되는문제 수정

    }
}
