package kr.co.n3soft.n3com;

import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import kr.co.n3soft.n3com.edit.LineTextEditPart;
import kr.co.n3soft.n3com.edit.MessageEditPart;
import kr.co.n3soft.n3com.edit.UMLDiagramEditPart;
import kr.co.n3soft.n3com.edit.UMLEditPart;
import kr.co.n3soft.n3com.model.comm.DataInputPortModel;
import kr.co.n3soft.n3com.model.comm.DataOutputPortModel;
import kr.co.n3soft.n3com.model.comm.EventInputPortModel;
import kr.co.n3soft.n3com.model.comm.EventOutputPortModel;
import kr.co.n3soft.n3com.model.comm.MethodInputPortModel;
import kr.co.n3soft.n3com.model.comm.MethodOutputPortModel;
import kr.co.n3soft.n3com.model.comm.PortModel;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.component.AtomicComponentModel;
import kr.co.n3soft.n3com.model.component.ComponentModel;
import kr.co.n3soft.n3com.model.diagram.N3EditorDiagramModel;
import kr.co.n3soft.n3com.project.browser.UMLTreeModel;
import kr.co.n3soft.n3com.project.dialog.OPRoSDataPortDialog;
import kr.co.n3soft.n3com.project.dialog.OPRoSEventPortDialog;
import kr.co.n3soft.n3com.project.dialog.OPRoSServicePortDialog;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FreeformViewport;
import org.eclipse.draw2d.RangeModel;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.Tool;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.ui.parts.ScrollingGraphicalViewer;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.ui.IEditorPart;
//20080908IJS 전체
public class UMLDefaultEditDomain extends DefaultEditDomain {
	boolean isDrag = true;

	public UMLDefaultEditDomain(IEditorPart editorPart) {
		super(editorPart);
	}

	//	public void mouseMove(MouseEvent mouseEvent, EditPartViewer viewer) {
	//		ScrollingGraphicalViewer viewer1 = ProjectManager.getInstance().getUMLEditor().getScrollingGraphicalViewer();
	//		
	//		StructuredSelection list =  (StructuredSelection)viewer1.getSelection();
	//		if(list!=null && list.size()==1){
	//			Object obj = list.getFirstElement();
	//			if(obj instanceof MessageEditPart){
	//
	//				System.out.println("ddddd");
	//
	//
	//			}
	//		}
	//		super.mouseMove(mouseEvent, viewer);
	//	}

	public void mouseDown(MouseEvent mouseEvent, EditPartViewer viewer) {
		//20080908IJS 시작
		ScrollingGraphicalViewer viewer1 = ProjectManager.getInstance().getUMLEditor().getScrollingGraphicalViewer();
		ScalableFreeformRootEditPart sed = (ScalableFreeformRootEditPart)viewer1.getRootEditPart();
		FreeformViewport fv = (FreeformViewport)sed.getFigure();
		RangeModel rm = fv.getVerticalRangeModel();

		int value = rm.getValue();
		Point pt = new Point(mouseEvent.x, mouseEvent.y+value);
		Point pt2 = new Point(pt.x, pt.y);

		ProjectManager.getInstance().setMouseSelectPoint(pt);



		//		fv.translateToRelative(pt2);
		//		fv.translateFromParent(pt2);
		//		pt2.translate(fv.getVerticalRangeModel().getNegated());


		//		Control ct = viewer1.getControl();
		ZoomManager manager = (ZoomManager)ProjectManager.getInstance().getUMLEditor().getGraphicalViewerp().getProperty(ZoomManager.class.toString());
		double zoom = 0;
		if(manager!=null){
			zoom=manager.getZoom();
			//			pt.y;
			pt2.y = (int)(pt2.y/zoom);
		}
		super.mouseDown(mouseEvent, viewer);
		StructuredSelection list =  (StructuredSelection)viewer1.getSelection();
		if(list!=null && list.size()==1){
			Object obj = list.getFirstElement();
			if(obj instanceof MessageEditPart){

				//				MessageEditPart m1 = (MessageEditPart)obj;
				//				MessageModel m  = (MessageModel)m1.getModel();
				//				if((m.getY()-2)<=pt2.y&& (m.getY()+2)>=pt2.y){
				//					isDrag = true;
				//				}
				//				else{
				//					isDrag = false;
				//				}


			}
		}
		ProjectManager.getInstance().setMsgDrag(false);
		//20080908IJS 끝


	}

	public void mouseUp(MouseEvent mouseEvent, EditPartViewer viewer) {
		//		return;
		//		viewer.getSelection()
		//		Point pt  = new Point(mouseEvent.x,mouseEvent.y);
		//		ProjectManager.getInstance().setMouseSelectPoint(pt);
		//20080908IJS 시작
		ScrollingGraphicalViewer viewer1 = ProjectManager.getInstance().getUMLEditor().getScrollingGraphicalViewer();
		;
		ScalableFreeformRootEditPart sed = (ScalableFreeformRootEditPart)viewer1.getRootEditPart();
		FreeformViewport fv = (FreeformViewport)sed.getFigure();
		RangeModel rm = fv.getVerticalRangeModel();
		RangeModel hm = fv.getHorizontalRangeModel();

		int value = rm.getValue();
		int hValue = hm.getValue();
		Point pt = new Point(mouseEvent.x+hValue, mouseEvent.y+value);
		ProjectManager.getInstance().setVvalue(value);
		//		Point pt = new Point(mouseEvent.x, mouseEvent.y);
		ZoomManager manager = (ZoomManager)ProjectManager.getInstance().getUMLEditor().getGraphicalViewerp().getProperty(ZoomManager.class.toString());
		double zoom = 0;
		if(manager!=null){
			zoom=manager.getZoom();
			pt.y = (int)((pt.y)/zoom);
		}

		super.mouseUp(mouseEvent, viewer);
		//		Tool tool = ProjectManager.getInstance().getUMLEditor().getUMLDefaultEditDomain().getActiveTool();
		//		if(tool instanceof MarqueeSelectionTool){
		//		MarqueeSelectionTool mst = (MarqueeSelectionTool)tool;

		//		}

		Object obj1 = viewer1.getSelection();
		StructuredSelection list =  (StructuredSelection)viewer1.getSelection();
		if(list!=null && list.size()==1){
			try{
				Object obj = list.getFirstElement();
				//ijs08619
				if(obj instanceof MessageEditPart && isDrag){


					//					int distance = (int)(m.getY())-(int)(pt.y);

					//					m.setY((int)(pt.y));

					//					if(m.getSource() instanceof LifeLineModel){
					//					LifeLineModel lf = (LifeLineModel)m.getSource();
					//					lf.setDrag(false);
					//					lf.changeGroup();

					//					}
					//					if(m.getTarget() instanceof LifeLineModel){
					//					LifeLineModel lf2 = (LifeLineModel)m.getTarget();


					//					lf2.setDrag(false);
					//					lf2.changeGroup();
					//					if(m.getSource() instanceof LifeLineModel){
					//					int h = lf2.targetLinkMove(m, 0, (int)(distance));
					//					LifeLineModel lf = (LifeLineModel)m.getSource();
					//					if(h>lf.getSize().height){
					//					lf.setSize(new Dimension(lf.getSize().width,h));
					//					}
					//					}
					//					}

					//					if(m.getTarget() instanceof EndPointModel){
					//					EndPointModel lf = (EndPointModel)m.getTarget();
					//					lf.setLocation(new Point(lf.getLocation().x,pt.y));


					//					}
					//					if(m.getSource() instanceof EndPointModel){
					//					EndPointModel lf = (EndPointModel)m.getSource();
					//					lf.setLocation(new Point(lf.getLocation().x,pt.y));


					//					}






				}
				//				else if (list != null && list.size() == 1) {
				//					 obj = list.getFirstElement();
				//					if (obj instanceof UMLEditPart) {
				//						UMLEditPart u = (UMLEditPart)obj;
				//						Figure fi = (Figure)u.getFigure();
				//						UMLModel um = (UMLModel)u.getModel();
				//						if (um instanceof PortModel) {
				//							PortModel pm = (PortModel)u.getModel();
				//							int px = pm.getPortContainerModel().getLocation().x;
				//							int py = pm.getPortContainerModel().getLocation().y;
				//							int pw = pm.getPortContainerModel().getSize().width;
				//							int ph = pm.getPortContainerModel().getSize().height;
				//							int x = pm.getLocation().x;
				//							int y = pm.getLocation().y;
				//							int w = pm.getSize().width;
				//							int h = pm.getSize().height;
				//							Rectangle pr = new Rectangle(px,py,pw,ph);
				//							Rectangle r = new Rectangle(x,y,w,h);
				//							if(r.y<pr.y){
				//								pm.setLocation(new Point(r.x,pr.y-r.height));
				//							}
				//							else if(r.x+w>pr.x+pw){
				//								pm.setLocation(new Point(pr.x+pw,r.y));
				//							}
				//							else if(r.y+w>pr.y+ph){
				//								pm.setLocation(new Point(r.x,pr.y+ph));
				//							}
				//							else if(r.x>pr.y+ph){
				//								pm.setLocation(new Point(r.x,pr.y+ph));
				//							}
				//						}
				//						//					System.out.println("mouseEvent==>"+mouseEvent);
				//					}
				//				}


			}



			catch(Exception e){
				e.printStackTrace();
			}
		}

		//20080908IJS 끝
		//		Tool tool = getActiveTool();
		//		if (tool != null)	
		//		tool.mouseUp(mouseEvent, viewer);	
	}

	public void mouseDoubleClick(MouseEvent mouseEvent, EditPartViewer viewer) {
		//20080811IJS 시작
		ScrollingGraphicalViewer viewer1 = ProjectManager.getInstance().getUMLEditor().getScrollingGraphicalViewer();
		;
		StructuredSelection list =  (StructuredSelection)viewer1.getSelection();
		if(list!=null){

			Object obj = list.getFirstElement();

			if(obj instanceof UMLEditPart){
				UMLEditPart ue = (UMLEditPart)obj;
				UMLModel um = (UMLModel)ue.getModel();
				if(um.isReadOnlyModel){
					return;
				}
				if(um instanceof MethodInputPortModel){
					PortModel pm = (PortModel)um;
					ComponentModel p = (ComponentModel)pm.getPortContainerModel();
					//					if(p instanceof ComponentLibModel){
					//						return;
					//					}
					if(p instanceof AtomicComponentModel){

						AtomicComponentModel atomicComponentModel1 = (AtomicComponentModel)p;
						if(atomicComponentModel1.getCoreUMLTreeModel()!=null){
							AtomicComponentModel t = atomicComponentModel1.getCoreDiagramCmpModel();
							//								AtomicComponentModel t = (AtomicComponentModel)ut.getRefModel();
							for(int i=0;i<t.getPortManager().getPorts().size();i++){
								PortModel obj1 = (PortModel)t.getPortManager().getPorts().get(i);
								
								//110822 SDM S 포트수정시 수정 정보 남김
								String strName = obj1.getName(); //20110822 SDM
								String strType = obj1.getType(); //20110822 SDM
								String strDesc = obj1.getDesc(); //20110822 SDM
								String strTypeRef = obj1.getTypeRef(); //20110822 SDM
								
								if(obj1 instanceof  MethodInputPortModel){
									if(obj1.getName().equals(pm.getName())){
										OPRoSServicePortDialog opd = new OPRoSServicePortDialog(ProjectManager.getInstance().window.getShell(),false,false,obj1);
										opd.open();
										pm.setName(obj1.getName());
										
										//110822 SDM S 포트 수정 시 기록 남김
										if(!strName.equals(obj1.getName())){	
											Iterator <String> it = ((AtomicComponentModel)t).getReNameList().iterator();
											boolean bool = false;
											String str1 = new String();
											
											while(it.hasNext()){
												str1 = it.next();
												StringTokenizer stk = new StringTokenizer(str1,",");
												while(stk.hasMoreTokens()){
													String str2 = stk.nextToken();
													if(str2.equals(strName)){
														bool = true;
														break;
													}
												}
											}
											
											if(bool){	//이전에 포트명 변경이 있었을 때
												String str2 = str1 + obj1.getName() + ",";
												((AtomicComponentModel)t).setReNameList(str1, str2);
											}
											else{	//포트명 변경이 없었을 때
												String str = strName + "," + obj1.getName() + ",";
												((AtomicComponentModel)t).setReNameList(str);
											}

											
										}
										else if(!strType.equals(obj1.getType()) || !strDesc.equals(obj1.getDesc()) || !strTypeRef.equals(obj1.getTypeRef())){
											Iterator <String> it = ((AtomicComponentModel)t).getReNameList().iterator();
											boolean bool = false;
											String str1 = new String();
											
											while(it.hasNext()){
												str1 = it.next();
												String[] temp = str1.split(",");
												
												if(!temp[0].equals(strName)){	//첫 수정이면 포트명 삽입
													((AtomicComponentModel)t).setReNameList(str1);
												}
											}
										}
										//110822 SDM E 포트 수정 시 기록 남김
										
										break;
									}
								}
							}
						}
						else{
							OPRoSServicePortDialog opd = new OPRoSServicePortDialog(ProjectManager.getInstance().window.getShell(),false,false,pm);
							int i = opd.open();
						}



					}
				}
				else if(um instanceof MethodOutputPortModel){
					PortModel pm = (PortModel)um;
					ComponentModel p = (ComponentModel)pm.getPortContainerModel();
					//					if(p instanceof ComponentLibModel){
					//						return;
					//					}
					if(p instanceof AtomicComponentModel){
						AtomicComponentModel atomicComponentModel1 = (AtomicComponentModel)p;
						if(atomicComponentModel1.getCoreUMLTreeModel()!=null){
							AtomicComponentModel t = atomicComponentModel1.getCoreDiagramCmpModel();
							//							AtomicComponentModel t = (AtomicComponentModel)ut.getRefModel();
							for(int i=0;i<t.getPortManager().getPorts().size();i++){
								PortModel obj1 = (PortModel)t.getPortManager().getPorts().get(i);
								//110822 SDM S 포트수정시 수정 정보 남김
								String strName = obj1.getName(); //20110822 SDM
								String strType = obj1.getType(); //20110822 SDM
								String strDesc = obj1.getDesc(); //20110822 SDM
								String strTypeRef = obj1.getTypeRef(); //20110822 SDM
								
								if(obj1 instanceof  MethodOutputPortModel){
									if(obj1.getName().equals(pm.getName())){
										OPRoSServicePortDialog opd = new OPRoSServicePortDialog(ProjectManager.getInstance().window.getShell(),true,false,obj1);
										opd.open();
										pm.setName(obj1.getName());
										
										//110822 SDM S 포트 수정 시 기록 남김
										if(!strName.equals(obj1.getName())){	
											Iterator <String> it = ((AtomicComponentModel)t).getReNameList().iterator();
											boolean bool = false;
											String str1 = new String();
											
											while(it.hasNext()){
												str1 = it.next();
												StringTokenizer stk = new StringTokenizer(str1,",");
												while(stk.hasMoreTokens()){
													String str2 = stk.nextToken();
													if(str2.equals(strName)){
														bool = true;
														break;
													}
												}
											}
											
											if(bool){	//이전에 포트명 변경이 있었을 때
												String str2 = str1 + obj1.getName() + ",";
												((AtomicComponentModel)t).setReNameList(str1, str2);
											}
											else{	//포트명 변경이 없었을 때
												String str = strName + "," + obj1.getName() + ",";
												((AtomicComponentModel)t).setReNameList(str);
											}

											
										}
										else if(!strType.equals(obj1.getType()) || !strDesc.equals(obj1.getDesc()) || !strTypeRef.equals(obj1.getTypeRef())){
											Iterator <String> it = ((AtomicComponentModel)t).getReNameList().iterator();
											boolean bool = false;
											String str1 = new String();
											
											while(it.hasNext()){
												str1 = it.next();
												String[] temp = str1.split(",");
												
												if(!temp[0].equals(strName)){	//첫 수정이면 포트명 삽입
													((AtomicComponentModel)t).setReNameList(str1);
												}
											}
										}
										//110822 SDM E 포트 수정 시 기록 남김
										break;
									}
								}
							}
						}
						else{

							OPRoSServicePortDialog opd = new OPRoSServicePortDialog(ProjectManager.getInstance().window.getShell(),true,false,pm);
							//						opd.setPm(pm);
							int i = opd.open();
						}
					}
				}
				else if(um instanceof DataInputPortModel){
					PortModel pm = (PortModel)um;
					ComponentModel p = (ComponentModel)pm.getPortContainerModel();
					//					if(p instanceof ComponentLibModel){
					//						return;
					//					}
					if(p instanceof AtomicComponentModel){
						AtomicComponentModel atomicComponentModel1 = (AtomicComponentModel)p;
						if(atomicComponentModel1.getCoreUMLTreeModel()!=null){
							AtomicComponentModel t = atomicComponentModel1.getCoreDiagramCmpModel();
							//							AtomicComponentModel t = (AtomicComponentModel)ut.getRefModel();
							for(int i=0;i<t.getPortManager().getPorts().size();i++){
								PortModel obj1 = (PortModel)t.getPortManager().getPorts().get(i);
								
								//110822 SDM S 포트수정시 수정 정보 남김
								String strName = obj1.getName(); //20110822 SDM
								String strType = obj1.getType(); //20110822 SDM
								String strDesc = obj1.getDesc(); //20110822 SDM
								String strTypeRef = obj1.getTypeRef(); //20110822 SDM
								
								if(obj1 instanceof  DataInputPortModel){
									if(obj1.getName().equals(pm.getName())){
										OPRoSDataPortDialog opd = new OPRoSDataPortDialog(ProjectManager.getInstance().window.getShell(),true,false,obj1);
										opd.open();
										pm.setName(obj1.getName());
										
										//110822 SDM S 포트 수정 시 기록 남김
										if(!strName.equals(obj1.getName())){	
											Iterator <String> it = ((AtomicComponentModel)t).getReNameList().iterator();
											boolean bool = false;
											String str1 = new String();
											
											while(it.hasNext()){
												str1 = it.next();
												StringTokenizer stk = new StringTokenizer(str1,",");
												while(stk.hasMoreTokens()){
													String str2 = stk.nextToken();
													if(str2.equals(strName)){
														bool = true;
														break;
													}
												}
											}
											
											if(bool){	//이전에 포트명 변경이 있었을 때
												String str2 = str1 + obj1.getName() + ",";
												((AtomicComponentModel)t).setReNameList(str1, str2);
											}
											else{	//포트명 변경이 없었을 때
												String str = strName + "," + obj1.getName() + ",";
												((AtomicComponentModel)t).setReNameList(str);
											}

											
										}
										else if(!strType.equals(obj1.getType()) || !strDesc.equals(obj1.getDesc()) || !strTypeRef.equals(obj1.getTypeRef())){
											Iterator <String> it = ((AtomicComponentModel)t).getReNameList().iterator();
											boolean bool = false;
											String str1 = new String();
											
											while(it.hasNext()){
												str1 = it.next();
												String[] temp = str1.split(",");
												
												if(!temp[0].equals(strName)){	//첫 수정이면 포트명 삽입
													((AtomicComponentModel)t).setReNameList(str1);
												}
											}
										}
										//110822 SDM E 포트 수정 시 기록 남김
										break;
									}
								}
							}
						}
						else{

							OPRoSDataPortDialog opd = new OPRoSDataPortDialog(ProjectManager.getInstance().window.getShell(),true,false,pm);
							//						opd.setPm(pm);
							int i = opd.open();
						}
					}
				}
				else if(um instanceof DataOutputPortModel){
					PortModel pm = (PortModel)um;
					ComponentModel p = (ComponentModel)pm.getPortContainerModel();
					//					if(p instanceof ComponentLibModel){
					//						return;
					//					}
					if(p instanceof AtomicComponentModel){
						AtomicComponentModel atomicComponentModel1 = (AtomicComponentModel)p;
						if(atomicComponentModel1.getCoreUMLTreeModel()!=null){
						AtomicComponentModel t = atomicComponentModel1.getCoreDiagramCmpModel();
						//						AtomicComponentModel t = (AtomicComponentModel)ut.getRefModel();
						for(int i=0;i<t.getPortManager().getPorts().size();i++){
							PortModel obj1 = (PortModel)t.getPortManager().getPorts().get(i);
							//110822 SDM S 포트수정시 수정 정보 남김
							String strName = obj1.getName(); //20110822 SDM
							String strType = obj1.getType(); //20110822 SDM
							String strDesc = obj1.getDesc(); //20110822 SDM
							String strTypeRef = obj1.getTypeRef(); //20110822 SDM
							
							if(obj1 instanceof  DataOutputPortModel){
								if(obj1.getName().equals(pm.getName())){
									OPRoSDataPortDialog opd = new OPRoSDataPortDialog(ProjectManager.getInstance().window.getShell(),false,false,obj1);
									opd.open();
									pm.setName(obj1.getName());
									
									//110822 SDM S 포트 수정 시 기록 남김
									if(!strName.equals(obj1.getName())){	
										Iterator <String> it = ((AtomicComponentModel)t).getReNameList().iterator();
										boolean bool = false;
										String str1 = new String();
										
										while(it.hasNext()){
											str1 = it.next();
											StringTokenizer stk = new StringTokenizer(str1,",");
											while(stk.hasMoreTokens()){
												String str2 = stk.nextToken();
												if(str2.equals(strName)){
													bool = true;
													break;
												}
											}
										}
										
										if(bool){	//이전에 포트명 변경이 있었을 때
											String str2 = str1 + obj1.getName() + ",";
											((AtomicComponentModel)t).setReNameList(str1, str2);
										}
										else{	//포트명 변경이 없었을 때
											String str = strName + "," + obj1.getName() + ",";
											((AtomicComponentModel)t).setReNameList(str);
										}

										
									}
									else if(!strType.equals(obj1.getType()) || !strDesc.equals(obj1.getDesc()) || !strTypeRef.equals(obj1.getTypeRef())){
										Iterator <String> it = ((AtomicComponentModel)t).getReNameList().iterator();
										boolean bool = false;
										String str1 = new String();
										
										while(it.hasNext()){
											str1 = it.next();
											String[] temp = str1.split(",");
											
											if(!temp[0].equals(strName)){	//첫 수정이면 포트명 삽입
												((AtomicComponentModel)t).setReNameList(str1);
											}
										}
									}
									//110822 SDM E 포트 수정 시 기록 남김
									break;
								}
							}
						}
					}
					else{

						OPRoSDataPortDialog opd = new OPRoSDataPortDialog(ProjectManager.getInstance().window.getShell(),false,false,pm);
						//						opd.setPm(pm);
						int i = opd.open();
					}
					}

				}
				else if(um instanceof EventInputPortModel){
					PortModel pm = (PortModel)um;
					ComponentModel p = (ComponentModel)pm.getPortContainerModel();
					//					if(p instanceof ComponentLibModel){
					//						return;
					//					}
					if(p instanceof AtomicComponentModel){
						AtomicComponentModel atomicComponentModel1 = (AtomicComponentModel)p;
						if(atomicComponentModel1.getCoreUMLTreeModel()!=null){
						AtomicComponentModel t = atomicComponentModel1.getCoreDiagramCmpModel();
						//						AtomicComponentModel t = (AtomicComponentModel)ut.getRefModel();
						for(int i=0;i<t.getPortManager().getPorts().size();i++){
							PortModel obj1 = (PortModel)t.getPortManager().getPorts().get(i);
							
							//110822 SDM 포트수정시 수정 정보 남김
							String strName = obj1.getName(); //20110822 SDM
							String strType = obj1.getType(); //20110822 SDM
							String strDesc = obj1.getDesc(); //20110822 SDM
							
							if(obj1 instanceof  EventInputPortModel){
								if(obj1.getName().equals(pm.getName())){
									OPRoSEventPortDialog opd = new OPRoSEventPortDialog(ProjectManager.getInstance().window.getShell(),true,false,obj1);
									opd.open();
									pm.setName(obj1.getName());
									((AtomicComponentModel)t).addDataTypeReference(opd.getUsingDataTypeFileName());
									
									//110822 SDM S 포트 수정 시 기록 남김
									if(!strName.equals(obj1.getName())){	
										Iterator <String> it = ((AtomicComponentModel)t).getReNameList().iterator();
										boolean bool = false;
										String str1 = new String();
										
										while(it.hasNext()){
											str1 = it.next();
											StringTokenizer stk = new StringTokenizer(str1,",");
											while(stk.hasMoreTokens()){
												String str2 = stk.nextToken();
												if(str2.equals(strName)){
													bool = true;
													break;
												}
											}
										}
										
										if(bool){	//이전에 포트명 변경이 있었을 때
											String str2 = str1 + obj1.getName() + ",";
											((AtomicComponentModel)t).setReNameList(str1, str2);
										}
										else{	//포트명 변경이 없었을 때
											String str = strName + "," + obj1.getName() + ",";
											((AtomicComponentModel)t).setReNameList(str);
										}

										
									}
									else if(!strType.equals(obj1.getType()) || !!strDesc.equals(obj1.getDesc())){
										Iterator <String> it = ((AtomicComponentModel)t).getReNameList().iterator();
										boolean bool = false;
										String str1 = new String();
										
										while(it.hasNext()){
											str1 = it.next();
											String[] temp = str1.split(",");
											
											if(!temp[0].equals(strName)){	//첫 수정이면 포트명 삽입
												((AtomicComponentModel)t).setReNameList(str1);
											}
										}
									}
									//110822 SDM E 포트 수정 시 기록 남김
										
									break;
								}
							}
						}
					}
					else{

						OPRoSEventPortDialog opd = new OPRoSEventPortDialog(ProjectManager.getInstance().window.getShell(),true,false,pm);
						//						opd.setPm(pm);
						int i = opd.open();

					}
				}
				}
				else if(um instanceof EventOutputPortModel){
					PortModel pm = (PortModel)um;
					ComponentModel p = (ComponentModel)pm.getPortContainerModel();
					//					if(p instanceof ComponentLibModel){
					//						return;
					//					}
					if(p instanceof AtomicComponentModel){
						AtomicComponentModel atomicComponentModel1 = (AtomicComponentModel)p;
						if(atomicComponentModel1.getCoreUMLTreeModel()!=null){
						AtomicComponentModel t = atomicComponentModel1.getCoreDiagramCmpModel();
						//						AtomicComponentModel t = (AtomicComponentModel)ut.getRefModel();
						for(int i=0;i<t.getPortManager().getPorts().size();i++){
							PortModel obj1 = (PortModel)t.getPortManager().getPorts().get(i);
							
							//110822 SDM 포트수정시 수정 정보 남김
							String strName = obj1.getName(); //20110822 SDM
							String strType = obj1.getType(); //20110822 SDM
							String strDesc = obj1.getDesc(); //20110822 SDM
							
							if(obj1 instanceof  EventOutputPortModel){
								if(obj1.getName().equals(pm.getName())){
									OPRoSEventPortDialog opd = new OPRoSEventPortDialog(ProjectManager.getInstance().window.getShell(),false,false,obj1);
									opd.open();
									pm.setName(obj1.getName());
									((AtomicComponentModel)t).addDataTypeReference(opd.getUsingDataTypeFileName());
									
									//110822 SDM S 포트 수정 시 기록 남김
									if(!strName.equals(obj1.getName())){	
										Iterator <String> it = ((AtomicComponentModel)t).getReNameList().iterator();
										boolean bool = false;
										String str1 = new String();
										
										while(it.hasNext()){
											str1 = it.next();
											StringTokenizer stk = new StringTokenizer(str1,",");
											while(stk.hasMoreTokens()){
												String str2 = stk.nextToken();
												if(str2.equals(strName)){
													bool = true;
													break;
												}
											}
										}
										
										if(bool){	//이전에 포트명 변경이 있었을 때
											String str2 = str1 + obj1.getName() + ",";
											((AtomicComponentModel)t).setReNameList(str1, str2);
										}
										else{	//포트명 변경이 없었을 때
											String str = strName + "," + obj1.getName() + ",";
											((AtomicComponentModel)t).setReNameList(str);
										}

										
									}
									else if(!strType.equals(obj1.getType()) || !strDesc.equals(obj1.getDesc())){
										Iterator <String> it = ((AtomicComponentModel)t).getReNameList().iterator();
										boolean bool = false;
										String str1 = new String();
										
										while(it.hasNext()){
											str1 = it.next();
											String[] temp = str1.split(",");
											
											if(!temp[0].equals(strName)){	//첫 수정이면 포트명 삽입
												((AtomicComponentModel)t).setReNameList(str1);
											}
										}
									}
									//110822 SDM E 포트 수정 시 기록 남김
									break;
								}
							}
						}
					}
					else{

						OPRoSEventPortDialog opd = new OPRoSEventPortDialog(ProjectManager.getInstance().window.getShell(),false,false,pm);
						//						opd.setPm(pm);
						int i = opd.open();
					}

				}
				}



			}


		}		
		ProjectManager.getInstance().setIsDoubleClick(true);
		super.mouseDoubleClick(mouseEvent, viewer);

		//		Tool tool = ProjectManager.getInstance().getUMLEditor().getUMLDefaultEditDomain().getActiveTool();
		//		if(!(tool instanceof MarqueeSelectionTool) ){
		//		if(value==2){
		if (ProjectManager.getInstance().getUMLElementEditPart() != null) {
			viewer1.select(ProjectManager.getInstance().getUMLElementEditPart());
			ProjectManager.getInstance().getUMLElementEditPart().performRequest();
			ProjectManager.getInstance().setUMLElementEditPart(null);
		}
		else {
			try {
				Object obj1 = viewer1.getSelection();
				list = (StructuredSelection)viewer1.getSelection();
				if (list != null && list.size() == 1) {
					Object obj = list.getFirstElement();
					//PKY 08062601 S 더블클릭해서 오픈하지 않도록수정

					//					if (obj instanceof FinalPackageEditPart) {
					//					FinalPackageEditPart fp = (FinalPackageEditPart)obj;
					//					FinalPackageModel finalPackageModel = (FinalPackageModel)fp.getModel();
					//					N3EditorDiagramModel n3EditorDiagramModel = finalPackageModel.getN3EditorDiagramModel();
					//					if (n3EditorDiagramModel != null && n3EditorDiagramModel.getUMLTreeModel() != null)
					//					ProjectManager.getInstance().getModelBrowser().select(n3EditorDiagramModel.getUMLTreeModel());
					//					if (n3EditorDiagramModel != null) {
					//					ProjectManager.getInstance().openDiagram(n3EditorDiagramModel);
					//					}
					//					else {
					////					ArrayList diagram=ProjectManager.getInstance().getDiagramsSub();
					////					if(diagram!=null)
					////					if(diagram.size()>0){
					////					if(diagram.get(0) instanceof N3EditorDiagramModel)
					////					finalPackageModel.setN3EditorDiagramModel((N3EditorDiagramModel)diagram.get(0));
					////					N3EditorDiagramModel n3EditorDiagram = finalPackageModel.getN3EditorDiagramModel();
					////					if (n3EditorDiagram != null) {
					////					ProjectManager.getInstance().openDiagram(n3EditorDiagram);
					////					}
					////					}
					//					//PKY 08061101 S 패키지 하위에 다이어그램이 없을경우 더블클릭 시 새로 생성되었으나 생성되지 않도록 수정

					////					N3EditorDiagramModel n3EditorDiagramModel1 =
					////					ProjectManager.getInstance().addUMLDiagram(finalPackageModel.getName(),
					////					(UMLTreeParentModel)finalPackageModel.getUMLTreeModel(), null, 1, true,-1);

					////					finalPackageModel.setN3EditorDiagramModel(n3EditorDiagramModel1);
					////					n3EditorDiagramModel1.setDiagramType(1);
					////					if (n3EditorDiagramModel != null && n3EditorDiagramModel.getUMLTreeModel() != null)
					////					ProjectManager.getInstance().getModelBrowser().select(n3EditorDiagramModel.getUMLTreeModel());
					//					//PKY 08061101 E 패키지 하위에 다이어그램이 없을경우 더블클릭 시 새로 생성되었으나 생성되지 않도록 수정
					//					}
					//					}
					//					else if (obj instanceof FinalStrcuturedActivityEditPart) {
					//					FinalStrcuturedActivityEditPart fp = (FinalStrcuturedActivityEditPart)obj;
					//					FinalStrcuturedActivityModel finalPackageModel = (FinalStrcuturedActivityModel)fp.getModel();
					//					N3EditorDiagramModel n3EditorDiagramModel = finalPackageModel.getN3EditorDiagramModel();
					//					if (n3EditorDiagramModel != null && n3EditorDiagramModel.getUMLTreeModel() != null)
					//					ProjectManager.getInstance().getModelBrowser().select(n3EditorDiagramModel.getUMLTreeModel());
					//					if (n3EditorDiagramModel != null) {
					//					ProjectManager.getInstance().openDiagram(n3EditorDiagramModel);
					//					}
					//					else {
					//					//PKY 08062601 S 저장 불러온 후 StrcuturedActivit,StrcuturedStateModel,FrameModel 더블클릭 다이어그램이 오픈되지않는문제
					////					N3EditorDiagramModel n3EditorDiagramModel1 =
					////					ProjectManager.getInstance().addUMLDiagram(finalPackageModel.getName(),
					////					(UMLTreeParentModel)finalPackageModel.getUMLTreeModel(), null, 1, true,-1);
					////					finalPackageModel.setN3EditorDiagramModel(n3EditorDiagramModel1);
					////					if (n3EditorDiagramModel != null && n3EditorDiagramModel.getUMLTreeModel() != null)
					////					ProjectManager.getInstance().getModelBrowser().select(n3EditorDiagramModel.getUMLTreeModel());
					//					//PKY 08062601 E 저장 불러온 후 StrcuturedActivit,StrcuturedStateModel,FrameModel 더블클릭 다이어그램이 오픈되지않는문제
					//					}
					//					}
					//					else if (obj instanceof StrcuturedStateEditPart) {
					//					StrcuturedStateEditPart fp = (StrcuturedStateEditPart)obj;
					//					StrcuturedStateModel finalPackageModel = (StrcuturedStateModel)fp.getModel();
					//					N3EditorDiagramModel n3EditorDiagramModel = finalPackageModel.getN3EditorDiagramModel();
					//					if (n3EditorDiagramModel != null && n3EditorDiagramModel.getUMLTreeModel() != null)
					//					ProjectManager.getInstance().getModelBrowser().select(n3EditorDiagramModel.getUMLTreeModel());
					//					if (n3EditorDiagramModel != null) {
					//					ProjectManager.getInstance().openDiagram(n3EditorDiagramModel);
					//					}
					//					else {
					//					//PKY 08062601 S 저장 불러온 후 StrcuturedActivit,StrcuturedStateModel,FrameModel 더블클릭 다이어그램이 오픈되지않는문제
					////					N3EditorDiagramModel n3EditorDiagramModel1 =
					////					ProjectManager.getInstance().addUMLDiagram(finalPackageModel.getName(),
					////					(UMLTreeParentModel)finalPackageModel.getUMLTreeModel(), null, 1, true,-1);
					////					finalPackageModel.setN3EditorDiagramModel(n3EditorDiagramModel1);
					////					if (n3EditorDiagramModel != null && n3EditorDiagramModel.getUMLTreeModel() != null)
					////					ProjectManager.getInstance().getModelBrowser().select(n3EditorDiagramModel.getUMLTreeModel());
					//					//PKY 08062601 E 저장 불러온 후 StrcuturedActivit,StrcuturedStateModel,FrameModel 더블클릭 다이어그램이 오픈되지않는문제
					//					}
					//					}
					//					else if (obj instanceof FrameEditPart) {
					//					FrameEditPart fp = (FrameEditPart)obj;
					//					FrameModel finalPackageModel = (FrameModel)fp.getModel();
					//					N3EditorDiagramModel n3EditorDiagramModel = finalPackageModel.getN3EditorDiagramModel();
					//					if (n3EditorDiagramModel != null && n3EditorDiagramModel.getUMLTreeModel() != null)
					//					ProjectManager.getInstance().getModelBrowser().select(n3EditorDiagramModel.getUMLTreeModel());
					//					if (n3EditorDiagramModel != null) {
					//					ProjectManager.getInstance().openDiagram(n3EditorDiagramModel);
					//					}
					//					else {
					//					//PKY 08062601 S 저장 불러온 후 StrcuturedActivit,StrcuturedStateModel,FrameModel 더블클릭 다이어그램이 오픈되지않는문제
					//					N3EditorDiagramModel n3EditorDiagramModel1 =
					//					ProjectManager.getInstance().addUMLDiagram(finalPackageModel.getName(),
					//					(UMLTreeParentModel)finalPackageModel.getUMLTreeModel(), null, 1, true,-1);
					//					finalPackageModel.setN3EditorDiagramModel(n3EditorDiagramModel1);
					//					if (n3EditorDiagramModel != null && n3EditorDiagramModel.getUMLTreeModel() != null)
					//					ProjectManager.getInstance().getModelBrowser().select(n3EditorDiagramModel.getUMLTreeModel());
					//					//PKY 08062601 E 저장 불러온 후 StrcuturedActivit,StrcuturedStateModel,FrameModel 더블클릭 다이어그램이 오픈되지않는문제
					//					}
					//					}
					//PKY 08062601 E 더블클릭해서 오픈하지 않도록수정
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			//			ProjectManager.getInstance().gets
		}
		//20080811IJS 끝
	}

	public void mouseDrag(MouseEvent mouseEvent, EditPartViewer viewer) {
		try {
			//20080908IJS 시작

			//			System.out.println("mouseEvent==>"+mouseEvent);
			ScrollingGraphicalViewer viewer1 = ProjectManager.getInstance().getUMLEditor().getScrollingGraphicalViewer();
			//			ZoomManager zz;
			//			ZoomManager zz = (ZoomManager)viewer1.getProperty(ZoomManager.class.toString());
			Object obj1 = viewer.getSelection();
			StructuredSelection list = (StructuredSelection)viewer.getSelection();
			ScalableFreeformRootEditPart sed = (ScalableFreeformRootEditPart)viewer1.getRootEditPart();
			FreeformViewport fv = (FreeformViewport)sed.getFigure();
			RangeModel rm = fv.getVerticalRangeModel();
			int value = rm.getValue();


			//20080811IJS
			if (list != null ) {
				Object obj = list.getFirstElement();
				if (obj instanceof UMLEditPart) {
					UMLEditPart u = (UMLEditPart)obj;
					////					V1.20 WJH E 080825 S 시퀀스 다이어그렘 메시지 라벨 고정
					if(u.getParent() instanceof UMLDiagramEditPart || u instanceof UMLDiagramEditPart){
						UMLDiagramEditPart diagram = null;
						if(u.getParent() instanceof UMLDiagramEditPart)
							diagram = (UMLDiagramEditPart)u.getParent();
						else
							diagram = (UMLDiagramEditPart)u;


						N3EditorDiagramModel n3diagram = (N3EditorDiagramModel)diagram.getModel();
						System.out.println("dType : "+n3diagram.getDiagramType());
						if(n3diagram.getDiagramType() == 12){
							ProjectManager.getInstance().setMsgDrag(true);
						}
						if(u instanceof LineTextEditPart && n3diagram.getDiagramType() == 12)
							return;
					}
					//					V1.20 WJH E 080825 E 시퀀스 다이어그렘 메시지 라벨 고정
					UMLModel um = (UMLModel)u.getModel();
					if(um.isReadOnlyModel){
						return;
					}
				}else if (obj instanceof MessageEditPart) {//PKY 08082201 S 시퀀스 메시지 읽기전용인데도 움직이는문제
					//					MessageEditPart u = (MessageEditPart)obj;
					//					if(u.getModel()!=null){
					//						ProjectManager.getInstance().setMsgDrag(true);
					//						MessageModel um = (MessageModel)u.getModel();
					//						if(um.getDiagram()!=null){
					//							if(um.getDiagram().isReadOnlyModel())
					//								return;
					//						}
					//					}
				}
				//PKY 08082201 E 시퀀스 메시지 읽기전용인데도 움직이는문제

			}

			//seq
			ZoomManager manager = (ZoomManager)ProjectManager.getInstance().getUMLEditor().getGraphicalViewerp().getProperty(ZoomManager.class.toString());
			double zoom = 0;
			Point pt = new Point();


			if(manager!=null){

				zoom=manager.getZoom();
				//				pt.y;
				pt.y = (int)((mouseEvent.y+value)/zoom);
			}
			ProjectManager.getInstance().setDragPoint(new Point(mouseEvent.x,mouseEvent.y));
			if (list != null && list.size() == 1) {
				Object obj = list.getFirstElement();
				if (obj instanceof UMLEditPart) {
					UMLEditPart u = (UMLEditPart)obj;
					Figure fi = (Figure)u.getFigure();
					UMLModel um = (UMLModel)u.getModel();
					if (um instanceof PortModel) {
						PortModel pm = (PortModel)u.getModel();
						mouseEvent = pm.getDrag(fi, mouseEvent);
						if (mouseEvent == null)
							return;
					}
					//					System.out.println("mouseEvent==>"+mouseEvent);
				}
				else {
					//seq
					obj = list.getFirstElement();
					if (obj instanceof MessageEditPart && this.isDrag) {

					}
				}
			}
			Tool tool = getActiveTool();
			if (tool != null)
				tool.mouseDrag(mouseEvent, viewer);
			//20080908IJS 끝
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
