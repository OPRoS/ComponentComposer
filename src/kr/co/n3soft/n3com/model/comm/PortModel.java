package kr.co.n3soft.n3com.model.comm;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import kr.co.n3soft.n3com.edit.UMLElementEditPart;
import kr.co.n3soft.n3com.model.activity.TextAttachModel;
import kr.co.n3soft.n3com.model.component.ComponentModel;
import kr.co.n3soft.n3com.model.component.DataModel;
import kr.co.n3soft.n3com.model.component.ItemModel;
import kr.co.n3soft.n3com.model.component.MethodModel;
import kr.co.n3soft.n3com.model.component.ParamModel;
import kr.co.n3soft.n3com.model.component.ServiceModel;
import kr.co.n3soft.n3com.model.diagram.N3EditorDiagramModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.events.MouseEvent;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class PortModel extends TextAttachModel {
    static final long serialVersionUID = 1;
    public static int count = 0;
    public ElementLabelModel name = null;
    //	ElementLabelModel streotype = null;
    public Integer layout = null;
    public int parentLayout = 0;
    public PortContainerModel portCm = null;
    private Dimension ptDifference = new Dimension();
    
    private String type = "";
    
    public int x;
    public int y;
    public int w;
    public int h;
    public int dw;
    public int dh;
    public String pid = null;
    // index  = 0 위
    //index = 1 오른쪽
    //index = 2 아래
    //index = 3 왼쪽
    //index = 4 오른쪽 구석
    //index = 5 왼쪽 구석
    int index = 0;
    int arrow = 1;
    
public int temp_x;
	
	public int temp_y;
public String rn = "\r\n";
	
	private String[] basicType = {"int","short","long","bool","float","double"};
	private String unsigned ="unsigned";

    public int getIndex() {
        return index;
    }

    public Rectangle getRectangle() {
        Rectangle dd = new Rectangle(this.getLocation().x, this.getLocation().y, (int)(this.getSize().width),
            (int)(this.getSize().height));
        return dd;
    }

    public void setIndex() {
        int x1 = this.getLocation().x + this.getSize().width;
        if (this.getPortContainerModel() == null)
            return;
        Rectangle dd = this.getPortContainerModel().getRectangle();
        if (dd.contains(this.getLocation().x, this.getLocation().y)) {
            if (dd.contains(x1, this.getLocation().y)) {
                this.index = 2;
            }
            else {
                this.index = 1;
            }
            if (this.getRectangle().contains(dd.x + dd.width, dd.y + dd.height)) {
                index = 4;
            }
        }
        else {
            if (this.getRectangle().contains(dd.x, dd.y + dd.height)) {
                index = 5;
            }
            else if (dd.contains(this.getRectangle().x + this.getRectangle().width, this.getRectangle().y)) {
                index = 3;
            }
            else {
                index = 0;
            }
        }
        ////		fig.translateToAbsolute(dd);
        ////		if(dd.contains(new Point(mouseEvent.x,mouseEvent.y)))
        ////			return null;
        //	
        //		if(dd.y+dd.getSize().height/2<this.getLocation().y){
        //			if(dd.x+dd.getSize().width/2<this.getLocation().x){
        //				mouseEvent.x = dd.x+dd.getSize().width;
        //			}
        //			
        //		}
        //		if(dd.x>mouseEvent.x){
        //			mouseEvent.x = dd.x;
        //		}
        //		else if(dd.x+dd.getSize().width<mouseEvent.x){
        //			mouseEvent.x = dd.x+dd.getSize().width;
        //		}
    }

    public PortModel() {
        name = new ElementLabelModel();
        name.setType("NAME");
        this.size.height = 15;
        this.size.width = 15;
        this.setAttachElementLabelModel(name);
        this.uMLDataModel.setElementProperty(name.getType(), name);
    }

    public PortModel(UMLDataModel p) {
        name = (ElementLabelModel)p.getElementProperty("NAME");
        this.setAttachElementLabelModel(name);
        this.setUMLDataModel(p);
    }

    public ElementLabelModel getElementLabelModel() {
        return this.name;
    }
    
    public void setElementLabelModel(ElementLabelModel name1){
    	this.name = name1;
    }
    
    public String wirteModel(){
    	StringBuffer sb= new StringBuffer();
    	
    	return sb.toString();
    	
    }

    public PortModel(PortContainerModel p) {
        this.size.height = 15;
        this.size.width = 15;
        this.portCm = p;
        name = new ElementLabelModel();
        name.setType("NAME");
        this.setAttachElementLabelModel(name);
        this.uMLDataModel.setElementProperty(name.getType(), name);
    }

    public void setPortContainerModel(PortContainerModel p) {
        this.portCm = p;
    }

    public PortContainerModel getPortContainerModel() {
        return this.portCm;
    }

    //	public void setExceptionModel(ExceptionModel p){
    //		this.portCm = p;
    //	}
    //	
    //	public ExceptionModel getExceptionModel(){
    //		return this.emp;
    //	}
    public void setName(String uname) {
        name.setLabelContents(uname);
       
        
//        name.setName(uname);
    }

    //	public void setStreotype(String uname){
    //		streotype.setLabelContents(uname);
    //	}
    public String getName() {
        return name.getLabelContents();
    }

    public static String TERMINALS_OUT[] = new String[] {
        "1", "2", "3", "4", //$NON-NLS-4$//$NON-NLS-3$//$NON-NLS-2$//$NON-NLS-1$
            "5", "6", "7", "8"
    }; //$NON-NLS-4$//$NON-NLS-3$//$NON-NLS-2$//$NON-NLS-1$

    public static String TERMINALS_IN[] = new String[] {
        "A", "B", "C", "D", //$NON-NLS-4$//$NON-NLS-3$//$NON-NLS-2$//$NON-NLS-1$
            "E", "F", "G", "H"
    }; //$NON-NLS-4$//$NON-NLS-3$//$NON-NLS-2$//$NON-NLS-1$

    public void setLayout(Integer s) {
        this.layout = s;
        //		text = s;
        //		firePropertyChange("labelContents", null, text); //$NON-NLS-2$//$NON-NLS-1$
    }

    public void setParentLayout(int s) {
        this.parentLayout = s;
        //		text = s;
        //		firePropertyChange("labelContents", null, text); //$NON-NLS-2$//$NON-NLS-1$
    }

    public Integer getLayout() {
        return this.layout;
        //		text = s;
        //		firePropertyChange("labelContents", null, text); //$NON-NLS-2$//$NON-NLS-1$
    }

    public int getParentLayout() {
        return this.parentLayout;
        //		text = s;
        //		firePropertyChange("labelContents", null, text); //$NON-NLS-2$//$NON-NLS-1$
    }

    public Dimension getPtDifference() {
        return ptDifference;
    }

    public Rectangle getBoundRectangle() {
        Rectangle dd = new Rectangle(this.getLocation().x, this.getLocation().y, (int)(this.getSize().width),
            (int)(this.getSize().height));
        return dd;
    }

//    WJH 090801 S 
    public void move(Point pt) {
    	 if (ptDifference != null) {
    		   int x = pt.x - ptDifference.width;
    		   int y = pt.y - ptDifference.height;
    		//   if(ptDifference.width!=this.portCm.getSize().width){
    		//   int width = ptDifference.width - this.portCm.getSize().width;
    		//   x = x - width;
    		//   }
    		//   if(ptDifference.height!=this.portCm.getSize().height){
    		//   int height = ptDifference.height - this.portCm.getSize().height;
    		//   y = height-y;
    		//   }
    		   Point mpt = new Point(x, y);
    		   //PKY 08101424 S
    		   if(ProjectManager.getInstance()!=null){
    		    java.util.List selectList = ProjectManager.getInstance().getSelectNodes();
    		    //PKY 08101477 S
    		    if(selectList!=null && selectList.size()>1){
    		     boolean isContains = true;
    		     if(this.getElementLabelModel()!=null){     
    		      for(int i = 0 ; i < selectList.size(); i ++){
    		       if(selectList.get(i) instanceof UMLElementEditPart){
    		        if(((UMLElementEditPart)selectList.get(i)).getModel()!=null&& ((UMLElementEditPart)selectList.get(i)).getModel()==this.getElementLabelModel()){
    		         isContains =false;
    		         break;
    		        }
    		       }


    		      }
    		     }
    		     //PKY 08101477 E
    		     if(ProjectManager.getInstance().isAlignAction()){
    		    	 System.out.println("====== move ======");
    		      this.setLocation(mpt,true);//PKY 08101362 S
    		     }
    		     else     
    		      this.setLocation(mpt,isContains);//PKY 08101362 S

    		     return;

    		    }
    		   }
    		   //PKY 08101424 E
    		   //PKY 09042734 S


    		   this.setLocation(mpt,true);//PKY 08101362 S
    		   
    		  }
    }

    public void setLocation(Point p,boolean isAllMove) {//PKY 08101362 S
		//port

		Point oLocation = this.location.getCopy();//PKY 09040202 S

		location = p;
		if(isAllMove){//PKY 08101424 S
			PortModel pm = this;
			int px = pm.getPortContainerModel().getLocation().x;
			int py = pm.getPortContainerModel().getLocation().y;
			int pw = pm.getPortContainerModel().getSize().width;
			int ph = pm.getPortContainerModel().getSize().height;
			int x = pm.getLocation().x;
			int y = pm.getLocation().y;
			int w = pm.getSize().width;
			int h = pm.getSize().height;
			Rectangle pr = new Rectangle(px,py,pw,ph);
			Rectangle r = new Rectangle(x,y,w,h);
//			WJH 090817 S
//			System.out.println("location move    r.height : "+r.height);
//			System.out.println("location move    r.width : "+r.width);
			N3EditorDiagramModel diagram = (N3EditorDiagramModel)this.getAcceptParentModel();
	        if(diagram != null && diagram.getUMLTreeModel().getParent().getRefModel() instanceof ComponentModel){	        	
				if(r.y<pr.y){
					
					pm.setArrow(1);
				}
				else if(r.x+w>pr.x+pw){
	
					pm.setArrow(2);
				}
				else if(r.x<pr.x){
	
					pm.setArrow(4);
	
				}
				else if(r.y+w>pr.y+ph){
					pm.setArrow(3);
				}
				if(this.arrow==1){
					location.setLocation(new Point(r.x,pr.y-r.height+h/2));
				}
				else if(this.arrow==2){
					location.setLocation(new Point(pr.x+pw-w/2,r.y));
				}
				else if(this.arrow==3){
					location.setLocation(new Point(r.x,pr.y+ph-h/2-4));
				}
				else if(this.arrow==4){
					if(((PortModel)this).getPortContainerModel().getStereotype().equals("<<atomic>>") ||
							((PortModel)this).getPortContainerModel().getStereotype().equals("<<atomic>>")){
						location.setLocation(new Point(pr.x-8,r.y));
					}
					else{
						location.setLocation(new Point(pr.x-14,r.y));
					}						
						pm.setArrow(4);
	//				}
				}
	        }
	        else{
				if(r.y<pr.y){
	
					pm.setArrow(1);
				}
				else if(r.x+w>pr.x+pw){
	
					pm.setArrow(2);
				}
				else if(r.x<pr.x){
	
					pm.setArrow(4);
	
				}
				else if(r.y+w>pr.y+ph){
					pm.setArrow(3);
				}
				if(this.arrow==1){
					location.setLocation(new Point(r.x,pr.y-r.height+h/2));
				}
				else if(this.arrow==2){
					location.setLocation(new Point(pr.x+pw-w/2,r.y));
				}
				else if(this.arrow==3){
					location.setLocation(new Point(r.x,pr.y+ph-h/2));
				}
				else if(this.arrow==4){
	//				if(pm instanceof ExpansionNodeModel){
	//					location.setLocation(new Point(pr.x-w+10,r.y));
	//					pm.setArrow(4);
	//				}else{
						location.setLocation(new Point(pr.x-8,r.y));
						pm.setArrow(4);
	//				}
				}
	        }
//			WJH 090817 E
			 //ijs 090311-1 시작
			int pcy = this.getPortContainerModel().getLocation().y;
			int pch = this.getPortContainerModel().getSize().height;
			int poy = this.getLocation().y+20;
			//ijs090331 시작
			int pcx = this.getPortContainerModel().getLocation().x;
			int pcw = this.getPortContainerModel().getSize().width;
			int pox = this.getLocation().x;
			
			
			if(pcy>poy){
				location.setLocation(this.getLocation().x,pcy+5);
			}
			else if(pcy+pch<poy){
				location.setLocation(this.getLocation().x,pcy+pch-10);
			}
			
			if(pcx+pcw<pox)
				location.setLocation(pcx+pcw-10,this.getLocation().y);
			//ijs090331 끝
			
			//PKY 09040202 S
			int x1 = oLocation.x - location.x;
			int y1 = oLocation.y - location.y;
			//PKY 09040202 E

//			if (this.portCm != null) {
//				Point pt1 = this.portCm.getLocation();
//				Point pt2 = this.getLocation();
//				ptDifference = pt1.getDifference(pt2);
//				if(this.name.getLocation().x!=0 && this.name.getLocation().y!=0){
//					Point pt3 = new Point(this.getAttachElementLabelModel().getLocation().x-x1,this.getAttachElementLabelModel().getLocation().y-y1);
//					this.getAttachElementLabelModel().setLocation(pt3);
//				}else{
//					Point pt3 = new Point(p.x, p.y - 10);
//					this.getAttachElementLabelModel().setLocation(pt3);
//				}
//			}
			if (this.portCm != null) {
		    	  Point pt1 = this.portCm.getLocation();

		    	  Point pt2 = this.getLocation();
		    	  ptDifference = pt1.getDifference(pt2);
		    	  Point pt3 = null;
		    	  if(this.arrow==1){
		    		  pt3 = new Point(p.x, p.y - 10);
		    	  }
		    	  else if(this.arrow==2){
		    		  pt3 = new Point(p.x+10, p.y - 10);
		    	  }
		    	  else if(this.arrow==3){
		    		  pt3 = new Point(p.x,pt2.y+this.getSize().height);
		    	  }
		    	  else if(this.arrow==4){
		    		  String name = this.getAttachElementLabelModel().getLabelContents();
		    		  int aw = name.length()*5;
		    		  pt3 = new Point(p.x-aw, p.y - 10);
		    	  }
		    	  this.getAttachElementLabelModel().setLocation(pt3);
		      }
		}
		//		this.setSize(new Dimension(100,100));
		firePropertyChange(ID_LOCATION, null,p); //$NON-NLS-1$ S
		this.setIndex();
	}
	public void setLocation(Point p) {
		  //ijs 090311-1 전체 수정
//		//port

		if(ProjectManager.getInstance()!=null && 
				ProjectManager.getInstance().getSelectNodes()!=null && 
				ProjectManager.getInstance().getSelectNodes().size()>1){	
			return;
		}

		//port

//		if (location.equals(p))
//		return;
		Point oLocation = this.location.getCopy();
		location = p;
		PortModel pm = this;
//		V1.03 WJH 090303 S 널포인트 참조 수정
		if(pm.getPortContainerModel() == null)
			return;
//		V1.03 WJH 090303 E 널포인트 참조 수정		
		int px = pm.getPortContainerModel().getLocation().x;
		int py = pm.getPortContainerModel().getLocation().y;
		int pw = pm.getPortContainerModel().getSize().width;
		int ph = pm.getPortContainerModel().getSize().height;
		int x = pm.getLocation().x;
		int y = pm.getLocation().y;
		int w = pm.getSize().width;
		int h = pm.getSize().height;
		Rectangle pr = new Rectangle(px,py,pw,ph);
		Rectangle r = new Rectangle(x,y,w,h);
//		WJH 090817 S
//		System.out.println("location p ==>    r.height : "+r.height);
//		System.out.println("location p ==>    r.width : "+r.width);
		N3EditorDiagramModel diagram = (N3EditorDiagramModel)this.getAcceptParentModel();
        if(diagram != null && diagram.getUMLTreeModel()!=null && diagram.getUMLTreeModel().getParent().getRefModel() instanceof ComponentModel){
//        	System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ == Large Size");        	
			if(r.y<pr.y){
//				if(this.bFirst){
//					location.setLocation(new Point(r.x,pr.y-r.height+h/2-5));
//					this.bFirst = false;
//				}
//				else{
					location.setLocation(new Point(r.x,pr.y-r.height+h/2));				
//				}
				pm.setArrow(1);
			}
			else if(r.x+w>pr.x+pw){
				location.setLocation(new Point(pr.x+pw-w/2,r.y));
				pm.setArrow(2);
				
			}
			else if(r.x<pr.x){
				if(((PortModel)this).getPortContainerModel().getStereotype().equals("<<atomic>>") ||
						((PortModel)this).getPortContainerModel().getStereotype().equals("<<atomic>>")){
					location.setLocation(new Point(pr.x-8,r.y));
				}
				else{
					location.setLocation(new Point(pr.x-14,r.y));
				}
					pm.setArrow(4);				
			}
			else if(r.y+w>pr.y+ph){
				location.setLocation(new Point(r.x,pr.y+ph-h/2-4));
				pm.setArrow(3);				
			}
	
			else{
			    //ijs 090311-1 시작
				if(this.arrow==1){
					location.setLocation(new Point(r.x,pr.y-r.height+h/2-6));					
				}
				else if(this.arrow==2){
					location.setLocation(new Point(pr.x+pw-10,r.y));
				}
				else if(this.arrow==3){
					location.setLocation(new Point(r.x,pr.y+ph-10));
				}
				else if(this.arrow==4){
	//				if(pm instanceof ExpansionNodeModel)
	//				location.setLocation(new Point(pr.x-w+40,r.y));
	//				else
					
						location.setLocation(new Point(pr.x-w/2,r.y));	
				}
				this.setArrow(this.arrow);
				//ijs 090311-1 끝
			}
        }
        else{
//        	System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&& == Small Size");
			if(r.y<pr.y){
				location.setLocation(new Point(r.x,pr.y-r.height+h/2));
				pm.setArrow(1);				
			}
			else if(r.x+w>pr.x+pw){
				location.setLocation(new Point(pr.x+pw-w/2,r.y));
				pm.setArrow(2);				
			}
			else if(r.x<pr.x){				
					location.setLocation(new Point(pr.x-8,r.y));
					pm.setArrow(4);
	//			}
			}
			else if(r.y+w>pr.y+ph){
				location.setLocation(new Point(r.x,pr.y+ph-h/2));
				pm.setArrow(3);
			}
	
			else{
			    //ijs 090311-1 시작
				if(this.arrow==1){
					location.setLocation(new Point(r.x,pr.y-r.height+h/2));
				}
				else if(this.arrow==2){
					location.setLocation(new Point(pr.x+pw-10,r.y));
				}
				else if(this.arrow==3){
					location.setLocation(new Point(r.x,pr.y+ph-10));
				}
				else if(this.arrow==4){
					
						location.setLocation(new Point(pr.x-w/2,r.y));
				}
				this.setArrow(this.arrow);
				//ijs 090311-1 끝
			}
        }
//		WJH 090817 E
		System.out.println("arrow : "+this.arrow);
//		if (this.portCm != null) {//				Point pt1 = this.portCm.getLocation();
//		Point pt2 = this.getLocation();//				ptDifference = pt1.getDifference(pt2);//				Point pt3 = null;//				if(this.arrow==1){//				pt3 = new Point(p.x, p.y - 10);//				}//				else if(this.arrow==2){//				pt3 = new Point(p.x+10, p.y - 10);//				}//				else if(this.arrow==3){//				pt3 = new Point(p.x,pt2.y+this.getSize().height);//				}//				else if(this.arrow==4){////				int aw = this.getAttachElementLabelModel().getSize().width;//				String name = this.getAttachElementLabelModel().getLabelContents();//				int aw = name.length()*5;//				pt3 = new Point(p.x-aw, p.y - 10);//				}//				this.getAttachElementLabelModel().setLocation(pt3);
//		}
		int x1 = oLocation.x - location.x;//PKY 08101424 S
		int y1 = oLocation.y - location.y;//PKY 08101424 S
//		if (this.portCm != null) {
//
//			Point pt1 = this.portCm.getLocation();
//			Point pt2 = this.getLocation();
//			ptDifference = pt1.getDifference(pt2);
//			if(this.name.getLocation().x!=0 && this.name.getLocation().y!=0){
//				Point pt3 = new Point(this.getAttachElementLabelModel().getLocation().x-x1,this.getAttachElementLabelModel().getLocation().y-y1);
//				this.getAttachElementLabelModel().setLocation(pt3);
//			}else{
//				Point pt3 = new Point(this.location.x, location.y - 10);
//				this.getAttachElementLabelModel().setLocation(pt3);
//			}
//		}
      if (this.portCm != null) {
    	  Point pt1 = this.portCm.getLocation();

    	  Point pt2 = this.getLocation();
    	  ptDifference = pt1.getDifference(pt2);
    	  Point pt3 = null;
    	  if(this.arrow==1){
    		  pt3 = new Point(p.x, p.y - 10);
    	  }
    	  else if(this.arrow==2){
    		  pt3 = new Point(p.x+10, p.y - 10);
    	  }
    	  else if(this.arrow==3){
    		  pt3 = new Point(p.x,pt2.y+this.getSize().height);
    	  }
    	  else if(this.arrow==4){
    		  String name = this.getAttachElementLabelModel().getLabelContents();
    		  int aw = name.length()*5;
    		  pt3 = new Point(p.x-aw, p.y - 10);
    	  }
    	  this.getAttachElementLabelModel().setLocation(pt3);
      }
//		if (um instanceof PortModel) {

		//		this.setSize(new Dimension(100,100));
		firePropertyChange(ID_LOCATION, null, location); //$NON-NLS-1$
		this.setIndex();
//		this.checkLocation();
	}
	
//    public void setLocation(Point p) {
//        //port
//        System.out.println("p1:" + p);
////        if (location.equals(p))
////            return;
//        location = p;
//        PortModel pm = this;
//		int px = pm.getPortContainerModel().getLocation().x;
//		int py = pm.getPortContainerModel().getLocation().y;
//		int pw = pm.getPortContainerModel().getSize().width;
//		int ph = pm.getPortContainerModel().getSize().height;
//		int x = pm.getLocation().x;
//		int y = pm.getLocation().y;
//		int w = pm.getSize().width;
//		int h = pm.getSize().height;
//		Rectangle pr = new Rectangle(px,py,pw,ph);
//		Rectangle r = new Rectangle(x,y,w,h);
//		if(r.y<pr.y){
//			location.setLocation(new Point(r.x,pr.y-r.height));
//			pm.setArrow(1);
//		}
//		else if(r.x+w>pr.x+pw){
//			location.setLocation(new Point(pr.x+pw,r.y));
//			pm.setArrow(2);
//		}
//		else if(r.y+w>pr.y+ph){
//			location.setLocation(new Point(r.x,pr.y+ph));
//			pm.setArrow(3);
//		}
//		else if(r.x<pr.x){
//			location.setLocation(new Point(pr.x-w,r.y));
//			pm.setArrow(4);
//		}
//		else{
//			if(this.arrow==1){
//				location.setLocation(new Point(r.x,pr.y-r.height));
//			}
//			else if(this.arrow==2){
//				location.setLocation(new Point(pr.x+pw,r.y));
//			}
//			else if(this.arrow==3){
//				location.setLocation(new Point(r.x,pr.y+ph));
//			}
//			else if(this.arrow==4){
//				location.setLocation(new Point(pr.x-w,r.y));
//			}
//			
//			
//		}
//        
//        if (this.portCm != null) {
//            Point pt1 = this.portCm.getLocation();
//            
//            Point pt2 = this.getLocation();
//            ptDifference = pt1.getDifference(pt2);
//            Point pt3 = null;
//            if(this.arrow==1){
//            	  pt3 = new Point(p.x, p.y - 10);
//			}
//			else if(this.arrow==2){
//				pt3 = new Point(p.x+10, p.y - 10);
//			}
//			else if(this.arrow==3){
//				pt3 = new Point(p.x,pt2.y+this.getSize().height);
//			}
//			else if(this.arrow==4){
////				int aw = this.getAttachElementLabelModel().getSize().width;
//				String name = this.getAttachElementLabelModel().getLabelContents();
//				int aw = name.length()*5;
//				 pt3 = new Point(p.x-aw, p.y - 10);
//			}
//            this.getAttachElementLabelModel().setLocation(pt3);
//        }
////        if (um instanceof PortModel) {
//			
//        //		this.setSize(new Dimension(100,100));
//        firePropertyChange(ID_LOCATION, null, location); //$NON-NLS-1$
//        this.setIndex();
////        this.checkLocation();
//    }
//	WJH 090801 E
	
    public void checkLocation(){
    	PortModel pm = this;
		int px = pm.getPortContainerModel().getLocation().x;
		int py = pm.getPortContainerModel().getLocation().y;
		int pw = pm.getPortContainerModel().getSize().width;
		int ph = pm.getPortContainerModel().getSize().height;
		int x = pm.getLocation().x;
		int y = pm.getLocation().y;
		int w = pm.getSize().width;
		int h = pm.getSize().height;
		Rectangle pr = new Rectangle(px,py,pw,ph);
		Rectangle r = new Rectangle(x,y,w,h);
		if(r.y<pr.y){
			pm.setLocation(new Point(r.x,pr.y-r.height));
			pm.setArrow(1);
		}
		else if(r.x+w>pr.x+pw){
			pm.setLocation(new Point(pr.x+pw,r.y));
			pm.setArrow(2);
		}
		else if(r.y+w>pr.y+ph){
			pm.setLocation(new Point(r.x,pr.y+ph));
			pm.setArrow(3);
		}
		else if(r.x<pr.x){
			pm.setLocation(new Point(pr.x-w,r.y));
			pm.setArrow(4);
		}
    }

    public Object clone() {
        PortModel clone = new PortModel((UMLDataModel)this.getUMLDataModel().clone());
        //		clone.setUMLDataModel((UMLDataModel)this.getUMLDataModel().clone());
        clone.ptDifference = this.getPtDifference().getCopy();
        clone.setSize(this.getSize());
        //		clone.setLocation(this.getLocation());
        clone.sourceModel = this;
        ProjectManager.getInstance().setTempCopyMap(clone.sourceModel.getID(), clone);
        for (int i1 = 0; i1 < this.getSourceConnections().size(); i1++) {
            LineModel li = (LineModel)this.getSourceConnections().get(i1);
            LineModel liCopy = (LineModel)li.clone();
            ProjectManager.getInstance().addSelectLineModel(liCopy);
        }
        return clone;
    }

    public void setSize(Dimension d) {
        if (size.equals(d))
            return;
        //		if(15 !=d.height)
        //			return;
        //		if(15 !=d.width)
        //			return;
        System.out.println("setSize : "+d);
        size = d;
        firePropertyChange(ID_SIZE, null, size); //$NON-NLS-1$
    }

    //	public PortContainerModel getPortContainerModel(){
    //		return this.portCm;
    //	}
    public MouseEvent getDrag(Figure fig, MouseEvent mouseEvent) {
        Rectangle dd = this.getPortContainerModel().getBoundRectangle();
        fig.translateToAbsolute(dd);
//        if (dd.contains(new Point(mouseEvent.x, mouseEvent.y)))
//            return null;
        if (dd.y > mouseEvent.y) {
            mouseEvent.y = dd.y;
            
        }
        else if (dd.y + dd.getSize().height < mouseEvent.y) {
            mouseEvent.y = dd.y + dd.getSize().height;
        }
        if (dd.x > mouseEvent.x) {
            mouseEvent.x = dd.x;
        }
        else if (dd.x + dd.getSize().width < mouseEvent.x) {
            mouseEvent.x = dd.x + dd.getSize().width;
        }
        return mouseEvent;
    }

    public void update() { }

	public void setPtDifference(Dimension ptDifference) {
		this.ptDifference = ptDifference;
	}

	public int getArrow() {
		return arrow;
	}

	public void setArrow(int arrow) {
		this.arrow = arrow;
	}

	public String getType() {
		return (String)this.uMLDataModel.getElementProperty("portType");
//		if()
	}

	public void setType(String type) {
		this.type = type;
		 this.uMLDataModel.setElementProperty("portType", type);
	}
	
	public String getUsage() {
		if(this instanceof EventInputPortModel){
			return "input";
		}
		else if(this instanceof EventOutputPortModel){
			return "output";
		}
		return (String)this.uMLDataModel.getElementProperty("PortUsage");
	}

	public void setUsage(String usage) {
//		this.type = type;
		 this.uMLDataModel.setElementProperty("PortUsage", usage);
	}
	
	public String getTypeRef() {
		return (String)this.uMLDataModel.getElementProperty("TypeRef");
	}

	public void setTypeRef(String typeRef) {
//		this.type = type;
		 this.uMLDataModel.setElementProperty("TypeRef", typeRef);
	}
	
	
	public String getQueueingPolicy() {
		return (String)this.uMLDataModel.getElementProperty("QueueingPolicy");
	}

	public void setQueueingPolicy(String queueingPolicy) {
//		this.type = type;
		 this.uMLDataModel.setElementProperty("QueueingPolicy", queueingPolicy);
	}
	
	public String getQueueSize() {
		return (String)this.uMLDataModel.getElementProperty("QueueSize");
	}

	public void setQueueSize(String queueSize) {
//		this.type = type;
		 this.uMLDataModel.setElementProperty("QueueSize", queueSize);
	}
	
	public String wirteDataTypeSourceHeader(){
		StringBuffer sb = new StringBuffer();
		String dataType1 = this.getType();
		if(dataType1!=null){
			String dataType = dataType1.trim();
		if(dataType.startsWith("std::")){
			int i = dataType.lastIndexOf("<");
			String includeName ="";
			if(i>0){
				includeName = dataType.substring(5,i);
			}
			else{
				includeName = dataType.substring(5);
			}
			sb.append("#include <"+this.getType()+">"+rn);
		}
		else{
			boolean isB = false;
			int j = dataType.indexOf("unsigned ");
			if(j>=0)
				isB= true;
			if(!isB){
				for(int i=0;i<basicType.length;i++){
					
					if(dataType.equals(basicType[i])){
						isB = true;
						break;
					}
					
				}
			}
			if(!isB){
				sb.append("#include \""+this.getType()+".h\""+rn);
			}
			
			
		}
		}
		return sb.toString();
		
		
	}
	
	public String wirteServicePortSourceHeader(){
		StringBuffer sb = new StringBuffer();
		String usage = this.getUsage();
		if(usage!=null){			
			if("required".equals(usage)){
				sb.append("#include \""+this.getType()+"Required.h\""+rn);//		 WJH 100614 추가 작업
			}
			else{
				sb.append("#include \""+this.getType()+"Provided.h\""+rn);//		 WJH 100614 추가 작업
			}
		}
		return sb.toString();
	}
	
//	 WJH 100614 S 추가 작업
	public String wirteServicePortSourceBody2(){
		StringBuffer sb = new StringBuffer();
		String usage = this.getUsage();
		
		if(usage!=null){
			if("output".equals(usage)){
				String para = this.type;
				
				if(para.startsWith("std::")){
					String sub = para.substring(5, para.length());
//				WJH 100721 S 코드 수정
					int index = sub.lastIndexOf("<");
					if(sub.lastIndexOf("<")>0){
						sub = sub.substring(0, index);										
					}
//				WJH 100721 E 코드 수정
					sb.append("#include < "+sub+" >"+rn+rn);
				}
				else if(para.startsWith("boost::")){							
					sb.append("#include \""+para+".h\""+rn+rn);
					}
				else{
							
				}				
			}
			else{
			}
		}
		return sb.toString();			
	}
	
	public String wirteServicePortSourceBody(){
		StringBuffer sb = new StringBuffer();
		String usage = this.getUsage();
		if(usage!=null){
			if("required".equals(usage)){
				sb.append("\tptr"+this.getName()+" = NULL;"+rn);
			}
			else{
			}
		}
		return sb.toString();			
	}
	
	public String wirteDataPortPortSetup(){
		StringBuffer sb = new StringBuffer();
		String usage = this.getUsage();
		if(usage!=null){			
			sb.append("\taddPort(\""+this.getName()+"\", &"+this.getName()+");"+rn);			
		}
		return sb.toString();			
	}
//	 WJH 100614 E 추가 작업
	
	public String wirteDataPortSourceHeaderBody(){
		StringBuffer sb = new StringBuffer();
		String usage = this.getUsage();
		if(usage!=null){
			if("input".equals(usage)){
				sb.append("\tInputDataPort< "+this.getType()+" > "+this.getName()+";"+rn);
			}
			else{
				sb.append("\tOutputDataPort< "+this.getType()+" >"+this.getName()+";"+rn);
			}
		}
		return sb.toString();
		
	}
	
//	 WJH 100614 S 추가 작업
	public String wirteDataPortSourceBody(){
		StringBuffer sb = new StringBuffer();
		String usage = this.getUsage();
		if(usage!=null){
			if("input".equals(usage)){				
				sb.append(this.getName()+"(OPROS_"+this.getQueueingPolicy()+","+this.getQueueSize()+")");				
			}
			else{
				sb.append(this.getName()+"(OPROS_LAST,1)");
			}
		}
		return sb.toString();
		
	}
	
	public String wirteEventPortSourceHeaderBody(){
		StringBuffer sb = new StringBuffer();
		String usage = this.getUsage();
		if(usage!=null){
			if("input".equals(usage)){
				sb.append("\tInputEventPort< "+this.getType()+" > "+this.getName()+";"+rn);
			}
			else{
				sb.append("\tOutputEventPort< "+this.getType()+" > "+this.getName()+";"+rn);
			}
		}
		return sb.toString();
		
	}
	
//	 WJH 100614 S 추가 작업
	public String wirteEventPortSourcePortSetup(){
		StringBuffer sb = new StringBuffer();
		String usage = this.getUsage();
		if(usage!=null){
			if("input".equals(usage)){
				sb.append("\taddPort(\""+this.getName()+"\", &"+this.getName()+");"+rn);
				sb.append("\t"+this.getName()+".setOwner(this);"+rn);
			}
			else{
				sb.append("\taddPort(\""+this.getName()+"\", &"+this.getName()+");"+rn);
			}
		}
		return sb.toString();
		
	}
	
	public String wirteServiceSourceHeaderBody(){
		StringBuffer sb = new StringBuffer();
		String usage = this.getUsage();
		if(usage!=null){
			if("provided".equals(usage)){
				sb.append("\tI"+this.getType()+" *ptr"+this.getName()+";"+rn);
			}
			else{
				sb.append("\t"+this.getType()+"Required *ptr"+this.getName()+";"+rn);
			}
		}
		return sb.toString();
		
	}
	
	public String wirteServiceSourceHeaderBody2(){
		StringBuffer sb = new StringBuffer();
		String usage = this.getUsage();
		String typeRef = this.getTypeRef();
		if(usage!=null){
			if("provided".equals(usage)){
				java.util.ArrayList list = this.getTypeRefMethodModel(this.portCm.getCmpFolder()+File.separator+"profile"+File.separator+this.getTypeRef());
				sb.append(rn);
				for(int i=0;i<list.size();i++){
					MethodModel mm = (MethodModel)list.get(i);
					sb.append(mm.writeSource()+rn);
				}
				if(!this.existType){
					sb.append("<error> code generation error </error>"+rn);
					sb.append("<error> Please Check Your Component Profile or Service Port Profile</error>"+rn); 
					sb.append("<error> Profile name or profile type mismatch</error> "+rn);
					this.existType = false;
				}
					
				
			}
			
		}
		return sb.toString();
		
	}
	
//	public java.util.
	
	public java.util.ArrayList getTypeRefMethodModelAll(String typeRef){
		java.util.ArrayList list2 = new java.util.ArrayList();
		String filePath =typeRef;
//		String type = this.getType();
		
		if(!filePath.isEmpty()){
			SAXBuilder builder = new SAXBuilder();
			Document doc = null;
			try {
				doc = builder.build( new FileInputStream(filePath));
			} catch (JDOMException e2) {
				e2.printStackTrace();
			} catch (IOException e3) {
				e3.printStackTrace();
			}
		Element root;
			root = doc.getRootElement();
			System.out.println("root=====>"+root);
			java.util.List list = root.getChildren("service_port_type");
			for(int i=0;i<list.size();i++){
				ServiceModel serviceModel = new ServiceModel();
				Element child = (Element)list.get(i);
				String text = child.getChildText("type_name");
				System.out.println("text=====>"+text);
//				if(type.equals(text)){
					java.util.List methods = child.getChildren("method");
					for(int i1=0;i1<methods.size();i1++){
						MethodModel mm = new MethodModel();
						Element method = (Element)methods.get(i1);
					mm.name = method.getAttributeValue("name");
				    mm.returnType = method.getAttributeValue("return_type");
				    System.out.println("method=====>"+methods);
					java.util.List params = method.getChildren("param");// WJH 100721 child->method로 변경
					for(int i2=0;i2<params.size();i2++){// WJH 100721 i1->i2로 변경
						ParamModel pm = new ParamModel();
						Element param = (Element)params.get(i2);
						pm.name = param.getChildText("name");
						pm.type = param.getChildText("type");
						mm.getParams().add(pm);
						System.out.println("param=====>"+params);
					}
					serviceModel.methods.add(mm);
					}
					
//				}
					serviceModel.typeName = text;
					list2.add(serviceModel);
//				child.get
			}
			System.out.println("list=====>"+list);
		

	}

		return list2;
		
	}
	
	
	public java.util.ArrayList getTypeDataModelAll(String path){
		java.util.ArrayList list2 = new java.util.ArrayList();
		String filePath =path;
//		String type = this.getType();
		
		if(!filePath.isEmpty()){
			SAXBuilder builder = new SAXBuilder();
			Document doc = null;
			try {
				doc = builder.build( new FileInputStream(filePath));
			} catch (JDOMException e2) {
				e2.printStackTrace();
			} catch (IOException e3) {
				e3.printStackTrace();
			}
		Element root;
			root = doc.getRootElement();
			System.out.println("root=====>"+root);
			java.util.List list = root.getChildren("struct");
			for(int i=0;i<list.size();i++){
				DataModel dataModel = new DataModel();
				Element child = (Element)list.get(i);
				String text = child.getAttributeValue("name");
				System.out.println("text=====>"+text);
				dataModel.name = text;
//				
					java.util.List methods = child.getChildren("item");
					for(int i1=0;i1<methods.size();i1++){
						ItemModel mm = new ItemModel();
						Element item = (Element)methods.get(i1);
					mm.type = item.getAttributeValue("type");
				    mm.name = item.getText();
				    System.out.println("method=====>"+methods);
					java.util.List params = item.getChildren("param");// WJH 100721 child->item로 변경
					
					dataModel.items.add(mm);
					}
					

					
					list2.add(dataModel);
//				child.get
			}
			System.out.println("list=====>"+list);
		

	}

		return list2;
		
	}
	
//	public java.util.ArrayList getTypeRefDataModel(String typeName){
//		java.util.ArrayList list2 = new java.util.ArrayList();
//		String filePath =typeName;
//		String type = this.getType();
//		
//		if(!filePath.isEmpty()){
//			SAXBuilder builder = new SAXBuilder();
//			Document doc = null;
//			try {
//				doc = builder.build( new FileInputStream(filePath));
//			} catch (JDOMException e2) {
//				e2.printStackTrace();
//			} catch (IOException e3) {
//				e3.printStackTrace();
//			}
//		Element root;
//			root = doc.getRootElement();
//			System.out.println("root=====>"+root);
//			java.util.List list = root.getChildren("service_port_type");
//			for(int i=0;i<list.size();i++){
//				Element child = (Element)list.get(i);
//				String text = child.getChildText("type_name");
//				System.out.println("text=====>"+text);
//				if(type.equals(text)){
//					java.util.List methods = child.getChildren("method");
//					for(int i1=0;i1<methods.size();i1++){
//						MethodModel mm = new MethodModel();
//						Element method = (Element)methods.get(i1);
//					mm.name = method.getAttributeValue("name");
//				    mm.returnType = method.getAttributeValue("return_type");
//				    System.out.println("method=====>"+methods);
//					java.util.List params = child.getChildren("param");
//					for(int i2=0;i2<params.size();i1++){
//						ParamModel pm = new ParamModel();
//						Element param = (Element)params.get(i2);
//						pm.name = param.getChildText("name");
//						pm.type = param.getChildText("type");
//						mm.getParams().add(pm);
//						System.out.println("param=====>"+params);
//					}
//					list2.add(mm);
//					}
//					
//				}
////				child.get
//			}
//			System.out.println("list=====>"+list);
//		
//
//	}
//
//		return list2;
//		
//	}
	
	public boolean existType = false;
	
	public java.util.ArrayList getTypeRefMethodModel(String typeName){
		java.util.ArrayList list2 = new java.util.ArrayList();
		String filePath =typeName;
		String type = this.getType();
		
		if(!filePath.isEmpty()){
			SAXBuilder builder = new SAXBuilder();
			Document doc = null;
			try {
				doc = builder.build( new FileInputStream(filePath));
			} catch (JDOMException e2) {
				e2.printStackTrace();
			} catch (IOException e3) {
				e3.printStackTrace();
			}
		Element root;
			root = doc.getRootElement();
			System.out.println("root=====>"+root);
			java.util.List list = root.getChildren("service_port_type");
			for(int i=0;i<list.size();i++){
				Element child = (Element)list.get(i);
				String text = child.getChildText("type_name");
				System.out.println("text=====>"+text);
				if(type.equals(text)){
					existType = true;
					java.util.List methods = child.getChildren("method");
					for(int i1=0;i1<methods.size();i1++){
						MethodModel mm = new MethodModel();
						Element method = (Element)methods.get(i1);
					mm.name = method.getAttributeValue("name");
				    mm.returnType = method.getAttributeValue("return_type");
				    System.out.println("method=====>"+methods);
					java.util.List params = method.getChildren("param");// WJH 100721 child->method로 변경
					for(int i2=0;i2<params.size();i2++){// WJH 100721 i1->i2로 변경
						ParamModel pm = new ParamModel();
						Element param = (Element)params.get(i2);
						pm.name = param.getChildText("name");
						pm.type = param.getChildText("type");
						mm.getParams().add(pm);
						System.out.println("param=====>"+params);
					}
					list2.add(mm);
					}
					
				}
//				child.get
			}
			System.out.println("list=====>"+list);
		

	}

		return list2;
		
	}
	
	public void doSave(Element parentEle){
		
	}
//	public
	
//	String filePath = dialog.open();
//	if(!filePath.isEmpty()){
//		SAXBuilder builder = new SAXBuilder();
//		Document doc = null;
//		try {
//			doc = builder.build( new FileInputStream(filePath));
//		} catch (JDOMException e2) {
//			e2.printStackTrace();
//		} catch (IOException e3) {
//			e3.printStackTrace();
//		}
//	Element root;
//		root = doc.getRootElement();
	
}
