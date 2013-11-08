package kr.co.n3soft.n3com.model.comm;

import java.util.ArrayList;
import java.util.List;

import kr.co.n3soft.n3com.model.activity.FinalActivityModel;
import kr.co.n3soft.n3com.model.activity.FinalObjectNodeModel;
import kr.co.n3soft.n3com.model.activity.FinalStrcuturedActivityModel;
import kr.co.n3soft.n3com.model.activity.SubPartitonModel;
import kr.co.n3soft.n3com.model.diagram.UMLDiagramModel;
import kr.co.n3soft.n3com.projectmanager.PortManager;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;

public class PortContainerModel extends UMLDiagramModel {
	PortManager pm = new PortManager();

	public PortContainerModel() {
	}


	public void addParentDiagram(UMLContainerModel p, PortModel iPort) {
		pm.addPort(iPort);
		pm.addParentDiagram(p, iPort);
	}

	public void setPorts(java.util.ArrayList p) {
		pm.setPorts(p);
	}

	public void createNewPort(PortModel ip) {
		pm.addPort(ip);
	}

	public PortModel getPort() {
		return null;
	}

	public void orphanChildPort(PortModel ip, UMLContainerModel p) {
		pm.removeAllParentDiagram(p, null);
	}

	public void undoOrphanChildPort(PortModel ip, UMLContainerModel p) {
		pm.addAllParentDiagram(p);
	}

	public void createPort(PortModel iPort, UMLContainerModel p) {
		if (iPort == null) {
			pm.addAllParentDiagram(p, this.getLocation());
		}
		else {
			pm.addParentDiagram(p, iPort);
		}
//		ProjectManager.getInstance().setSearchModel(false);
		
	}
	
	
	public void createPort2(PortModel iPort, UMLContainerModel p) {
		if (iPort == null) {
			pm.addAllParentDiagram(p, this.getLocation());
		}
		else {
			pm.addParentDiagram2(p, iPort);
		}
//		ProjectManager.getInstance().setSearchModel(false);
		
	}

	public void undoCreatePort(PortModel ip, UMLContainerModel p) {
		pm.removeAllParentDiagram(p, null);
	}

	public void deletePort(PortModel ip, UMLContainerModel p) {
		pm.removeAllParentDiagram(p, null);
	}

	public void undoDeletePort(PortModel ip, UMLContainerModel p) {
		pm.addAllParentDiagram(p);
	}

	public void setLocation(Point p) {
		//PKY 08052101 S 컨테이너에서 그룹으로 변경

		//port
		//PKY 08081101 S 포함관계 오른쪽 메뉴로 해서 합치는 불편함 문제 개선
		Point moveLocation = new Point();
		moveLocation.x = p.x - this.getLocation().x ;
		moveLocation.y = p.y - this.getLocation().y;
		lumpLocation(this,moveLocation);

		int x = this.getLocation().x;
		int y = this.getLocation().y;
		System.out.println("p1:" + p);
		//PKY 08081101 E 포함관계 오른쪽 메뉴로 해서 합치는 불편함 문제 개선
		//		pm.checkMove(this.getRectangle());
		//		if (location.equals(p)){
		//			
		//			return;
		//		}

		location = p;
		firePropertyChange(ID_LOCATION, null, p); //$NON-NLS-1$
//		if(ProjectManager.getInstance().getSelectNodes().size()>1){
//		return;
//		}
		pm.setPortLocations(p);

		java.util.ArrayList models = new  java.util.ArrayList();
		if(ProjectManager.getInstance().getSelectNodes()!=null)
			if(!(ProjectManager.getInstance().getSelectNodes().size()>1)){
				if( this.getUMLDataModel().getElementProperty("GROUP_CHILDRENS")!=null){
					x=p.x-x;
					y=p.y-y;	
					List list=ProjectManager.getInstance().getSelectNodes();
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
									//PKY 08052601 S 파티션 이름 저장되지 않는문제 수정
									if(this instanceof FinalActivityModel || this instanceof FinalStrcuturedActivityModel){
										ArrayList model = new ArrayList();
										ArrayList partt = new ArrayList();
										for(int i=0; i<models.size();i++){
											if(models.get(i) instanceof SubPartitonModel){
												partt.add(models.get(i));
											}
											else{
												model.add(models.get(i));
											}
										}
										models= new ArrayList();
										for(int i=0; i<model.size();i++){
											models.add(model.get(i));
										}
										for(int i=0; i<partt.size();i++){
											models.add(partt.get(i));
										}
									}
									//PKY 08052601 E 파티션 이름 저장되지 않는문제 수정
									boolean check=true;
									for(int i=0; i<models.size();i++){
										if(models.get(i) instanceof UMLModel){
											//PKY 08052801 S 파티션 중복으로 저장 불러올경우 움직임이 비정상적인문제
											int k=i-1;
											for(int j=k; j > -1; j--){										
												if(models.get(i)==models.get(j)){
													check=false;
												}
											}
											if(check==true){
											UMLModel model=(UMLModel)models.get(i);
											Point point = new Point(model.getLocation().x+x, model.getLocation().y+y);
											model.setLocation(point);
											//PKY 08052601 S Partition 저장 불러올시 해제되는문제 
											if(model instanceof SubPartitonModel){
												ArrayList array=(ArrayList)model.getUMLDataModel().getElementProperty("GROUP_PARENTS");
												for(int b=0;b<array.size();b++){
													if(array.get(b) instanceof UMLModel)
														model.setSize(new Dimension(((UMLModel)array.get(b)).getSize().width ,15));
												}
											}
											
											//PKY 08060201 E GRoup 포함형태로 변경
											//PKY 08052601 E Partition 저장 불러올시 해제되는문제 
											}
											else
												check=false;
											//PKY 08052801 E 파티션 중복으로 저장 불러올경우 움직임이 비정상적인문제

										}
									}
								}
							}
						}

					}
				}
				//PKY 08052801 S 파티션 중복으로 저장 불러올경우 움직임이 비정상적인문제

				if( this.getUMLDataModel().getElementProperty("GROUP_PARENTS")!=null){
					models=(java.util.ArrayList)this.getUMLDataModel().getElementProperty("GROUP_PARENTS");
					for(int i=0; i<models.size();i++){
						System.out.println("x"+((UMLModel)models.get(i)).getLocation().x);
						System.out.println("width"+((UMLModel)models.get(i)).getSize().width);
						System.out.println("y"+((UMLModel)models.get(i)).getLocation().y);
						System.out.println("height"+((UMLModel)models.get(i)).getSize().height);
//						int width=((UMLModel)models.get(i)).getLocation().x+((UMLModel)models.get(i)).getSize().width;
//						int height=((UMLModel)models.get(i)).getLocation().y+((UMLModel)models.get(i)).getSize().height;
						int width=((UMLModel)models.get(i)).getLocation().x+((UMLModel)models.get(i)).getSize().width;
						int height=((UMLModel)models.get(i)).getLocation().y+((UMLModel)models.get(i)).getSize().height;
						if(width<this.location.x||((UMLModel)models.get(i)).getLocation().x>this.location.x){
							if(((UMLModel)models.get(i)).getUMLDataModel().getElementProperty("GROUP_CHILDRENS")!=null){
								java.util.ArrayList arrayList=(java.util.ArrayList)((UMLModel)models.get(i)).getUMLDataModel().getElementProperty("GROUP_CHILDRENS");
								for(int j=0;j<arrayList.size();j++){
									if(arrayList.get(j)==this){
										for(int k=0; k<models.size();k++){
											if(models.get(k)==arrayList.get(j)){
												models.remove(k);
												k=0;
												i=0;
											}
										}
										
										arrayList.remove(j);    						   
									}
								}
								((UMLModel)models.get(i)).getUMLDataModel().setElementProperty("GROUP_CHILDRENS", arrayList);
							}
							
							this.getUMLDataModel().setElementProperty("GROUP_PARENTS",models);
						}
						else if(height<this.location.y||((UMLModel)models.get(i)).getLocation().y>this.location.y){
							if(((UMLModel)models.get(i)).getUMLDataModel().getElementProperty("GROUP_CHILDRENS")!=null){
								java.util.ArrayList arrayList=(java.util.ArrayList)((UMLModel)models.get(i)).getUMLDataModel().getElementProperty("GROUP_CHILDRENS");
								for(int j=0;j<arrayList.size();j++){
									if(arrayList.get(j)==this){
										for(int k=0; k<models.size();k++){
											if(models.get(k)==arrayList.get(j)){
												models.remove(k);
												k=0;
												i=0;
											}
										}
										arrayList.remove(j);    						   
									}
								}
								((UMLModel)models.get(i)).getUMLDataModel().setElementProperty("GROUP_CHILDRENS", arrayList);
							}
							this.getUMLDataModel().setElementProperty("GROUP_PARENTS",models);
						}
					}
				}
				//PKY 08052801 E 파티션 중복으로 저장 불러올경우 움직임이 비정상적인문제

			}
		//PKY 08052101 E 컨테이너에서 그룹으로 변경

	}

	public void setPortManager(PortManager p) {
		this.pm = p;
	}

	public PortManager getPortManager() {
		return this.pm;
	}

	public Rectangle getBoundRectangle() {
		Rectangle dd = new Rectangle(this.getLocation().x - 5, this.getLocation().y - 5, (int)(this.getSize().width) + 5,
				(int)(this.getSize().height) + 5);
		return dd;
	}

	public Rectangle getRectangle() {
		Rectangle dd = new Rectangle(this.getLocation().x, this.getLocation().y, (int)(this.getSize().width),
				(int)(this.getSize().height));
		return dd;
	}

	public java.util.ArrayList getPorts() {
		return pm.getPorts();
	}
	
	 public void setChageSize(Dimension oldSize, Dimension newSize){

         int x = 0;

         int y = 0;

         int  umlModelStartX  = this.getLocation().x;

         int  umlModelStartY  = this.getLocation().y;

         int umlModelEndX = this.getLocation().x+this.getSize().width;

         int umlModelEndy = this.getLocation().y+this.getSize().height;

                    x = newSize.width - oldSize.width ;

                    y = newSize.height - oldSize.height ;

                    

         for(int i = 0; i <this.getPorts().size(); i ++){

                    if(this.getPorts().get(i) instanceof PortModel){

                              PortModel portModel = (PortModel)this.getPorts().get(i);

//                            if(portModel.getLocation().x >umlModelStartX){                                      

                              Point p = portModel.getLocation();

                              

//                            WJH 100330 S 사이즈 변경시 포트위치를 수렴하도록 수정 

                              int arrow = portModel.getArrow();

//                            boolean check = false;

//                            Point model = this.location;

                              if(arrow==1 || arrow==3){

                                         if(p.x>this.getLocation().x+newSize.width){

                                                    newSize.width = p.x-this.getLocation().x+8;

//                                                  check = true;

                                         }

//                                       if(p.x<this.getLocation().x){

//                                                  newSize.width = newSize.width+this.getLocation().x-p.x;

////                                                this.getLocation().x = p.x;

//                                                  check = true;

//                                       }

                              }

                              if(arrow==2 || arrow==4){

                                         if(p.y>this.getLocation().y+newSize.height){

                                                    newSize.height = p.y-this.getLocation().y+10;

//                                                  check = true;

                                         }

//                                       if(p.y<this.getLocation().y){

//                                                  newSize.height = p.y+this.getLocation().y;

//                                                  check = true;

//                                       }

                              }

//                            WJH 100330 E 사이즈 변경시 포트위치를 수렴하도록 수정

                              

                              double wPercent = (double)newSize.width / (double)oldSize.width;

                              double hPercent = (double)newSize.height / (double)oldSize.height;

                              if(oldSize.width!=newSize.width)

                                         p.x = p.x + x;

                              if(oldSize.height!=newSize.width)

                                         p.y = p.y + y;



                              

                              if(portModel.getLocation().x > this.getLocation().x+5 || portModel.getLocation().x > this.getLocation().x-5){ 

                                         ;

                              }else{



                                         p.x = this.getLocation().x-5;

                              }

                              if(portModel.getLocation().y > this.getLocation().y+5 || portModel.getLocation().y > this.getLocation().y-5){ 

                                         ;

                              }else{



                                         p.y = this.getLocation().y-8;

                              }

                              

                              if(portModel.getLocation().y < umlModelStartY){

//                                       p.y = this.getLocation().y;

//                                       portModel.setLocation(p);

                              }

                              





                              

//                            if(x > -5 && x < 5)

//                                       ;

//                            else{

//                                       if(p.x > umlModelEndX){

//                                                  double abc = (double)p.x * wPercent;

//                                                  if((int)abc<umlModelStartX)

//                                                            p.x = umlModelStartX;

//                                                 else

//                                                            p.x = (int)abc;

//                                       }

//                                       else if(p.x < umlModelEndX)

////                                                p.x = p.x + x;

//                                                  ;

//                            }

//                            

//                            if(y > -5 && y < 5)

//                                       ;

//                            else

//                                       if(p.y > umlModelEndy){

//                                                  double abc = (double)p.y * hPercent;

//                                                  if((int)abc<umlModelStartY)

//                                                            p.y = umlModelStartY;

//                                                  else

//                                                            p.y = (int)abc;

//                                       }

//                                       else if(p.y < umlModelEndy)

////                                                p.x = p.x + x;

//                                                  ;

                              

                              

//                            portModel.setLocation(p);

                              



//                            }

                    }

         }

}


	public void setSize(Dimension d) {
		if (size.equals(d))
			return;
		System.out.println("=====================  Size Change  =================");
		int n_width = d.width;
		int n_height = d.height;
		int o_width = size.width;
		int o_height = size.height;
		double w = n_width / o_width;
		double h = n_height / o_height;
		Dimension oldSize  = new Dimension(size.width,size.height);
		//2008042301PKY S

		if(d.height<15){
			d.height=15;
		}
//		V1.02 WJH E 080814 S 길이 변경 시 기본 크기보다 작아지는 문제 수정
		if(this instanceof FinalObjectNodeModel && d.height < 50)
			d.height = 50;
//		V1.02 WJH E 080814 E 길이 변경 시 기본 크기보다 작아지는 문제 수정
		if(d.width<20){
			d.width=20;
		}
		//2008042301PKY E
		
		size = d;
		this.setSizeChange(true);	//	WJH 090817
		this.uMLDataModel.setElementProperty("Size", size.getCopy());		
		firePropertyChange(ID_SIZE, null, size); //$NON-NLS-1$
		setChageSize(oldSize,d);		
		//		pm.setPortLocations(this.getLocation());
		//		pm.setPortLocations(this.getLocation(), w, h);
	}

}
