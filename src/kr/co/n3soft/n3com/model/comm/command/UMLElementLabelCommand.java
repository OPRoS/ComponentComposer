package kr.co.n3soft.n3com.model.comm.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;

import kr.co.n3soft.n3com.UMLEditor;
import kr.co.n3soft.n3com.model.activity.ControlFlowLineModel;
import kr.co.n3soft.n3com.model.comm.ElementLabelModel;
import kr.co.n3soft.n3com.model.comm.IUpdateType;
import kr.co.n3soft.n3com.model.comm.LineModel;
import kr.co.n3soft.n3com.model.comm.LineTextModel;
import kr.co.n3soft.n3com.model.comm.PortModel;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.comm.UpdateEvent;
import kr.co.n3soft.n3com.model.communication.MessageCommunicationModel;
import kr.co.n3soft.n3com.model.component.AtomicComponentModel;
import kr.co.n3soft.n3com.model.component.ComponentModel;
import kr.co.n3soft.n3com.project.dialog.DetailPropertyTableItem;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.commands.Command;

public class UMLElementLabelCommand extends Command {
	private String newName, oldName;
	private ElementLabelModel label;
	private int type = -1;

	public UMLElementLabelCommand(ElementLabelModel l, String s) {
		label = l;
		if (s != null)
			newName = s;
		else
			newName = ""; //$NON-NLS-1$
	}

	public void execute() {
//		System.out.print(label.getMessageIntType());
//		if(!newName.equals("")&&label.getMessageIntType()!=-1){
//			if(!(newName.trim().equals("")&&(label.getMessageIntType()!=-1||label.getMessageIntType()!=Integer.valueOf(MessageCommunicationModel.TYPE_DERIVED).intValue()||label.getMessageIntType()!=Integer.valueOf(MessageCommunicationModel.TARGET_TYPE_DERIVED).intValue()))){
			oldName = label.getLabelContents();
			label.setLabelContents(newName);
			UpdateEvent e = new UpdateEvent(IUpdateType.RENAME_TYPE, null);
			label.fireChildUpdate(e);
			int strlen = 0;
			int size = 0;
			
			ProjectManager.getInstance().getModelBrowser().searchComponentDiagramIdModel(label.getPortId());
			for(int i=0;i<ProjectManager.getInstance().getSearchModel().size();i++){
				UMLModel port = (UMLModel)ProjectManager.getInstance().getSearchModel().get(i);
				port.setName(newName);
				
				//110823 SDM S 프로퍼티에서 포트명 수정시 기록 남김
				if(port instanceof PortModel){
					PortModel pm = (PortModel)port;
					ComponentModel cm = (ComponentModel)pm.getPortContainerModel();
					
					if(cm instanceof AtomicComponentModel){
						AtomicComponentModel am = ((AtomicComponentModel) cm).getCoreDiagramCmpModel();
						
						if(!oldName.equals(newName)){
							Iterator <String> it = am.getReNameList().iterator();
							boolean bool = false;
							String str1 = new String();
							
							while(it.hasNext()){
								str1 = it.next();
								StringTokenizer stk = new StringTokenizer(str1,",");
								while(stk.hasMoreTokens()){
									String str2 = stk.nextToken();
									if(str2.equals(oldName)){
										bool = true;
																				
										break;
									}
								}
							}
							
							if(bool){	//이전에 포트명 변경이 있었을 때
								String str2 = str1 + newName + ",";
								am.setReNameList(str1, str2);
							}
							else{	//포트명 변경이 없었을 때
								String str = oldName + "," + newName + ",";
								am.setReNameList(str);
							}
						}
					}
				}
				//110823 SDM E 프로퍼티에서 포트명 수정시 기록 남김
				
			}


			//PKY 08082201 S 시퀀스 메시지 사이즈 자동으로 늘어나도록 
			if(label.getAcceptParentModel()!=null && label.getAcceptParentModel() instanceof MessageCommunicationModel){
				if(label.getAcceptParentModel().getAcceptParentModel() instanceof LineTextModel){
					size = ((LineTextModel)label.getAcceptParentModel().getAcceptParentModel()).getSize().width;
					for( int i = 0; i < ((LineTextModel)label.getAcceptParentModel().getAcceptParentModel()).getLineTextm().getLineTextModels().size(); i ++){
						
						strlen=ProjectManager.getInstance().widthAutoSize(((MessageCommunicationModel)((LineTextModel)label.getAcceptParentModel().getAcceptParentModel()).getLineTextm().getLineTextModels().get(i)).getName());
						if(strlen>size){
							size=strlen;
						}
					}
					((LineTextModel)label.getAcceptParentModel().getAcceptParentModel()).setSize(new Dimension(size,((LineTextModel)label.getAcceptParentModel().getAcceptParentModel()).getSize().height));
				}
			}
			//PKY 08082201 E 시퀀스 메시지 사이즈 자동으로 늘어나도록 
			//PKY 08071601 S 다이어그램에서 이름을 지울경우 에러발생
			if(label.getMessageIntType()==Integer.valueOf(MessageCommunicationModel.TYPE_NAME).intValue()){
				if(label.getLineModel()!=null){
					HashMap  detailProp= (HashMap)label.getLineModel().getDetailProp();
					if(!newName.trim().equals("")){
						label.getLineModel().setName(newName);
						detailProp.put(LineModel.ID_NAME, newName); //PKY 08070101 S 오퍼레이션 다이얼 로그에서 파라미터 삭제안되는 문제
					}else{
						//PKY 08082201 S RoleName 삭제시 NullPoint 메시지 뜨는문제
						if(label.getLineModel()!=null){
							if(label.getLineModel() instanceof ControlFlowLineModel){
								for(int i = 0; i < label.getLineModel().getTargetLineTextModel().getMessageSize(); i++){
									if(label.getLineModel().getTargetLineTextModel().getMessageCommunicationModel(i).getLabelContents().getMessageIntType()==Integer.valueOf(MessageCommunicationModel.TYPE_NAME).intValue()){
										//PKY 08082201 S RoleName 삭제시 NullPoint 메시지 뜨는문제
										
										MessageCommunicationModel msg=label.getLineModel().getTargetLineTextModel().getMessageCommunicationModel(i);
										label.getLineModel().getTargetLineTextModel().getLineTextm().removeElementLabelModel(msg);
//										label.getLineModel().getSourceLineTextModel().removeModel(msg);//PKY 08082201 S RoleName 삭제시 NullPoint 메시지 뜨는문제
										detailProp.remove(LineModel.ID_NAME);
										label.getLineModel().setName("");
										if(label.getLineModel().getTargetLineTextModel().getMessageSize()==0){
											label.getLineModel().getTargetLineTextModel().setSize(new Dimension(0,0));
										}

										//PKY 08082201 E RoleName 삭제시 NullPoint 메시지 뜨는문제

									}
								}
							}else{
							for(int i = 0; i < label.getLineModel().getMiddleLineTextModel().getMessageSize(); i++){
								if(label.getLineModel().getMiddleLineTextModel().getMessageCommunicationModel(i).getLabelContents().getMessageIntType()==Integer.valueOf(MessageCommunicationModel.TYPE_NAME).intValue()){
									//PKY 08082201 S RoleName 삭제시 NullPoint 메시지 뜨는문제
									MessageCommunicationModel msg=label.getLineModel().getMiddleLineTextModel().getMessageCommunicationModel(i);

									label.getLineModel().getMiddleLineTextModel().getLineTextm().removeElementLabelModel(msg);
//									label.getLineModel().getSourceLineTextModel().removeModel(msg);//PKY 08082201 S RoleName 삭제시 NullPoint 메시지 뜨는문제

									detailProp.remove(LineModel.ID_NAME);

									if(label.getLineModel().getMiddleLineTextModel().getMessageSize()==0){
										label.getLineModel().getMiddleLineTextModel().setSize(new Dimension(0,0));
									}

									//PKY 08082201 E RoleName 삭제시 NullPoint 메시지 뜨는문제

								}
							}
							}
						}
						//PKY 08082201 E RoleName 삭제시 NullPoint 메시지 뜨는문제

					}
					
				}
			}
			//PKY 08071601 E 다이어그램에서 이름을 지울경우 에러발생
			//PKY 08070101 S 스트레오타입 다이어그램에서 수정할수 있도록 수정
			//PKY 08071601 S  TransitionFlow 다이어그램에서 수정가능하도록 수정

			if(label.getMessageIntType()==Integer.valueOf(MessageCommunicationModel.TARGET_TYPE_STATE_TRIGER).intValue()){
				HashMap  detailProp= (HashMap)label.getLineModel().getDetailProp();
				if(label.getLineModel()!=null){
					ArrayList list=(ArrayList)label.getLineModel().getDetailProp().get(LineModel.ID_TRAN_TRIGERS);
					String[] labelName= newName.split(",");
					if(list.size()==labelName.length){
						for(int i = 0; i < list.size(); i++){
							DetailPropertyTableItem item=(DetailPropertyTableItem)list.get(i);
							if(!item.sName.trim().equals(labelName[i])){
								item.sName=labelName[i];
							}
						}
					}else{
						label.setLabelContents(oldName);
					}

					detailProp.put(LineModel.ID_TRAN_TRIGERS, list); //PKY 08070101 S 오퍼레이션 다이얼 로그에서 파라미터 삭제안되는 문제
				}
			}
			//PKY 08071601 E TransitionFlow 다이어그램에서 수정가능하도록 수정
			//PKY 08071601 S  TransitionFlow 다이어그램에서 수정가능하도록 수정
			if(label.getMessageIntType()==Integer.valueOf(MessageCommunicationModel.TARGET_TYPE_STATE_TRIGER).intValue()){
				HashMap  detailProp= (HashMap)label.getLineModel().getDetailProp();
				if(label.getLineModel()!=null){
					ArrayList list=(ArrayList)label.getLineModel().getDetailProp().get(LineModel.ID_TRAN_TRIGERS);
					String[] labelName= newName.split(",");
					if(list.size()==labelName.length){
						for(int i = 0; i < list.size(); i++){
							DetailPropertyTableItem item=(DetailPropertyTableItem)list.get(i);
							if(!item.sName.trim().equals(labelName[i])){
								item.sName=labelName[i];
							}
						}
					}else{
						label.setLabelContents(oldName);
					}

					detailProp.put(LineModel.ID_TRAN_TRIGERS, list); //PKY 08070101 S 오퍼레이션 다이얼 로그에서 파라미터 삭제안되는 문제
				}
			}
			//PKY 08071601 E TransitionFlow 다이어그램에서 수정가능하도록 수정
			if(label.getMessageIntType()==Integer.valueOf(MessageCommunicationModel.TYPE_STEREO).intValue()){
				if(label.getLineModel()!=null){
					for(int i = 0; i < label.getLineModel().getMiddleLineTextModel().getMessageSize(); i++){
						if(label.getLineModel().getMiddleLineTextModel().getMessageCommunicationModel(i).getLabelContents().getMessageIntType()==Integer.valueOf(MessageCommunicationModel.TYPE_STEREO).intValue()){
							//PKY 08082201 S RoleName 삭제시 NullPoint 메시지 뜨는문제
							HashMap  detailProp= (HashMap)label.getLineModel().getDetailProp();
							MessageCommunicationModel msg=label.getLineModel().getMiddleLineTextModel().getMessageCommunicationModel(i);
							if(!newName.trim().equals("")){//PKY 08072401 S 라인 메세지들 다이어그램에서 내용을 없애면 사라지도록 수정
								label.getLineModel().setPropertyValue(LineModel.ID_STEREOTYPE, newName);
							}else{
								label.getLineModel().getMiddleLineTextModel().getLineTextm().removeElementLabelModel(msg);
//								label.getLineModel().getSourceLineTextModel().removeModel(msg);//PKY 08082201 S RoleName 삭제시 NullPoint 메시지 뜨는문제
								detailProp.remove(LineModel.ID_STEREOTYPE);
								if(label.getLineModel().getMiddleLineTextModel().getMessageSize()==0){
									label.getLineModel().getMiddleLineTextModel().setSize(new Dimension(0,0));
								}
							}
							//PKY 08082201 E RoleName 삭제시 NullPoint 메시지 뜨는문제

						}
					}
				}
			}
			//PKY 08070101 E 스트레오타입 다이어그램에서 수정할수 있도록 수정
			else if(label.getMessageIntType()==Integer.valueOf(MessageCommunicationModel.TYPE_DERIVED).intValue()){
				if(label.getLineModel()!=null){
					HashMap  detailProp= (HashMap)label.getLineModel().getDetailProp();
					if(newName.indexOf("+")==0){
						newName=newName.substring(1, newName.length());

					}
					label.getLineModel().getSourceLineTextModel().setLocation(new Point(label.getLineModel().getSourceLineTextModel().getLocation().x,label.getLineModel().getSourceLineTextModel().getLocation().y));
					detailProp.put(LineModel.ID_SOURCE_ROLE, newName);
//					label.getLineModel().setDetailProp(detailProp);
					if(newName.indexOf("+")!=0)
						for(int i = 0; i< label.getLineModel().getSourceLineTextModel().getMessageSize(); i++){
							if(label.getLineModel().getSourceLineTextModel().getMessageCommunicationModel(i).getType()==MessageCommunicationModel.TYPE_DERIVED)
								try{
									MessageCommunicationModel msg=label.getLineModel().getSourceLineTextModel().getMessageCommunicationModel(i);
									if(!newName.trim().equals("")){//PKY 08072401 S 라인 메세지들 다이어그램에서 내용을 없애면 사라지도록 수정
										label.getLineModel().getSourceLineTextModel().setText("+"+newName, i);
										if(label.getLineModel().getSourceLineTextModel().getN3ConnectionLocator().oldPoint!=null)
											label.getLineModel().getSourceLineTextModel().setLocation(label.getLineModel().getSourceLineTextModel().getN3ConnectionLocator().oldPoint);
									}else{
										label.getLineModel().getSourceLineTextModel().getLineTextm().removeElementLabelModel(msg);
//										label.getLineModel().getSourceLineTextModel().removeModel(msg);//PKY 08082201 S RoleName 삭제시 NullPoint 메시지 뜨는문제
										detailProp.remove(LineModel.ID_SOURCE_ROLE);
										if(label.getLineModel().getSourceLineTextModel().getMessageSize()==0){
											label.getLineModel().getSourceLineTextModel().setSize(new Dimension(0,0));
										}
									}
									//PKY 08072401 E 라인 메세지들 다이어그램에서 내용을 없애면 사라지도록 수정
								}catch (Exception e1) {
									e1.printStackTrace();
								}       			
						}

					//+
				}
			}        
			else if(label.getMessageIntType()==Integer.valueOf(MessageCommunicationModel.TYPE_MULTPLICITY).intValue()){
				if(label.getLineModel()!=null){
					HashMap  detailProp= (HashMap)label.getLineModel().getDetailProp();
					detailProp.put(LineModel.ID_SOURCE_MUL, newName);
//					label.getLineModel().setDetailProp(detailProp);
				}
			}
			else if(label.getMessageIntType()==Integer.valueOf(MessageCommunicationModel.TARGET_TYPE_DERIVED).intValue()){
				if(label.getLineModel()!=null){
					HashMap  detailProp= (HashMap)label.getLineModel().getDetailProp();
					if(newName.indexOf("+")==0){
						newName=newName.substring(1, newName.length());

					}
					detailProp.put(LineModel.ID_TARGET_ROLE, newName);
//					label.getLineModel().setDetailProp(detailProp);
					label.getLineModel().getTargetLineTextModel().setLocation(new Point(label.getLineModel().getTargetLineTextModel().getLocation().x,label.getLineModel().getTargetLineTextModel().getLocation().y));
					if(newName.indexOf("+")!=0)
						for(int i = 0; i< label.getLineModel().getTargetLineTextModel().getMessageSize(); i++){
							if(label.getLineModel().getTargetLineTextModel().getMessageCommunicationModel(i).getType()==MessageCommunicationModel.TARGET_TYPE_DERIVED)
								try{
									MessageCommunicationModel msg=label.getLineModel().getTargetLineTextModel().getMessageCommunicationModel(i);
									if(!newName.trim().equals("")){//PKY 08072401 S 라인 메세지들 다이어그램에서 내용을 없애면 사라지도록 수정
										msg.setName("+"+newName);
										label.getLineModel().getTargetLineTextModel().setText("+"+newName, i);
										if(label.getLineModel().getTargetLineTextModel().getN3ConnectionLocator().oldPoint!=null)
											label.getLineModel().getTargetLineTextModel().setLocation(label.getLineModel().getTargetLineTextModel().getLocation());

									}else{
										label.getLineModel().getTargetLineTextModel().getLineTextm().removeElementLabelModel(msg);
//										label.getLineModel().getTargetLineTextModel().removeModel(msg);//PKY 08082201 S RoleName 삭제시 NullPoint 메시지 뜨는문제
										detailProp.remove(LineModel.ID_TARGET_ROLE);
										if(label.getLineModel().getTargetLineTextModel().getMessageSize()==0){
											label.getLineModel().getTargetLineTextModel().setSize(new Dimension(0,0));
										}
									}
									//PKY 08072401 E 라인 메세지들 다이어그램에서 내용을 없애면 사라지도록 수정

								}catch (Exception e1) {
									e1.printStackTrace();
								}       			
						}
				}
			}        
			else if(label.getMessageIntType()==Integer.valueOf(MessageCommunicationModel.TARGET_TYPE_MULTPLICITY).intValue()){
				if(label.getLineModel()!=null){
					HashMap  detailProp= (HashMap)label.getLineModel().getDetailProp();
					detailProp.put(LineModel.ID_TARGET_MUL, newName);
//					label.getLineModel().setDetailProp(detailProp);
				}
			}
			else if(label.getMessageIntType()==Integer.valueOf(MessageCommunicationModel.TYPE_WEIGHT).intValue()){
				if(label.getLineModel()!=null){
					HashMap  detailProp= (HashMap)label.getLineModel().getDetailProp();
					//{weight=aaa}
					if(newName.indexOf("{weight=")==0){
						newName=newName.substring(newName.indexOf("=")+1, newName.length()-1);
					}
					label.getLineModel().getMiddleLineTextModel().setLocation(new Point(label.getLineModel().getMiddleLineTextModel().getLocation().x,label.getLineModel().getMiddleLineTextModel().getLocation().y));
					detailProp.put(LineModel.ID_WEIGHT, newName);
					if(newName.indexOf("{weight=")<0)
						for(int i = 0; i< label.getLineModel().getMiddleLineTextModel().getMessageSize(); i++){
							if(label.getLineModel().getMiddleLineTextModel().getMessageCommunicationModel(i).getType()==MessageCommunicationModel.TYPE_WEIGHT)
								try{
									MessageCommunicationModel msg=label.getLineModel().getMiddleLineTextModel().getMessageCommunicationModel(i);
									if(!newName.trim().equals("")){//PKY 08072401 S 라인 메세지들 다이어그램에서 내용을 없애면 사라지도록 수정
										msg.setName("{weight="+newName+"}");
									}else{
										label.getLineModel().getMiddleLineTextModel().getLineTextm().removeElementLabelModel(msg);
										label.getLineModel().getMiddleLineTextModel().removeModel(msg);
										detailProp.remove(LineModel.ID_WEIGHT);
										if(label.getLineModel().getMiddleLineTextModel().getMessageSize()==0){
											label.getLineModel().getMiddleLineTextModel().setSize(new Dimension(0,0));
										}
									}
									//PKY 08072401 E 라인 메세지들 다이어그램에서 내용을 없애면 사라지도록 수정}
								}catch (Exception e1) {
									e1.printStackTrace();
								}       			
						}
//					label.getLineModel().setDetailProp(detailProp);
				}
			}
			//PKY 08071601 S TransitionFlow 다이어그램에서 수정가능하도록 수정
			else if(label.getMessageIntType()==Integer.valueOf(MessageCommunicationModel.TYPE_GUARD).intValue()){
				if(label.getLineModel()!=null){
					HashMap  detailProp= (HashMap)label.getLineModel().getDetailProp();
					if(newName.indexOf("[")>-1&&newName.indexOf("]")>-1&&newName.indexOf("/")>-1){
						String guard=newName.substring(newName.indexOf("[")+1,newName.indexOf("]"));
						detailProp.put(LineModel.ID_GUARD,guard );
						String effect=newName.substring(newName.indexOf("/")+1,newName.length()).trim();
						detailProp.put(LineModel.ID_TRAN_EFFECTS,effect);
						for(int i = 0; i< label.getLineModel().getMiddleLineTextModel().getMessageSize(); i++){
							if(label.getLineModel().getMiddleLineTextModel().getMessageCommunicationModel(i).getType()==MessageCommunicationModel.TYPE_GUARD){
								MessageCommunicationModel msg=label.getLineModel().getMiddleLineTextModel().getMessageCommunicationModel(i);
								msg.setName("["+guard+"]"+" / "+effect);
							}

						}

					}else if(newName.indexOf("[")>-1&&newName.indexOf("]")>-1){
						String guard=newName.substring(newName.indexOf("[")+1,newName.indexOf("]"));
						detailProp.put(LineModel.ID_GUARD,guard);
						for(int i = 0; i< label.getLineModel().getMiddleLineTextModel().getMessageSize(); i++){
							if(label.getLineModel().getMiddleLineTextModel().getMessageCommunicationModel(i).getType()==MessageCommunicationModel.TYPE_GUARD){
								MessageCommunicationModel msg=label.getLineModel().getMiddleLineTextModel().getMessageCommunicationModel(i);
								msg.setName("["+guard+"]");
							}

						}
						if(detailProp.get(LineModel.ID_TRAN_EFFECTS)!=null){
							detailProp.remove(detailProp.get(LineModel.ID_TRAN_EFFECTS));
						}
					}else if(newName.indexOf("/")>-1){
						String effect=newName.substring(newName.indexOf("/")+1,newName.length()).trim();
						detailProp.put(LineModel.ID_TRAN_EFFECTS,effect);
						for(int i = 0; i< label.getLineModel().getMiddleLineTextModel().getMessageSize(); i++){
							if(label.getLineModel().getMiddleLineTextModel().getMessageCommunicationModel(i).getType()==MessageCommunicationModel.TYPE_GUARD){
								MessageCommunicationModel msg=label.getLineModel().getMiddleLineTextModel().getMessageCommunicationModel(i);
								msg.setName("/ "+effect);
							}

						}
						if(detailProp.get(LineModel.ID_GUARD)!=null){
							detailProp.remove(detailProp.get(LineModel.ID_GUARD));
						}
					}else{
						for(int i = 0; i< label.getLineModel().getMiddleLineTextModel().getMessageSize(); i++){
							if(label.getLineModel().getMiddleLineTextModel().getMessageCommunicationModel(i).getType()==MessageCommunicationModel.TYPE_GUARD){
								MessageCommunicationModel msg=label.getLineModel().getMiddleLineTextModel().getMessageCommunicationModel(i);
								msg.setName("["+newName+"]");
							}

						}
						detailProp.put(LineModel.ID_GUARD,newName );
					}
				}
				//PKY 08071601 E TransitionFlow 다이어그램에서 수정가능하도록 수정
//				if(newName.indexOf("[")==0&&newName.indexOf("]")==newName.length()-1){
//				newName=newName.substring(newName.indexOf("[")+1, newName.lastIndexOf("]")-1);
//				}
//				detailProp.put(LineModel.ID_GUARD, newName);
//				label.getLineModel().getMiddleLineTextModel().setLocation(new Point(label.getLineModel().getMiddleLineTextModel().getLocation().x,label.getLineModel().getMiddleLineTextModel().getLocation().y));
//				if(newName.indexOf("[")<0)
//				for(int i = 0; i< label.getLineModel().getMiddleLineTextModel().getMessageSize(); i++){
//				if(label.getLineModel().getMiddleLineTextModel().getMessageCommunicationModel(i).getType()==MessageCommunicationModel.TYPE_GUARD)
//				try{
//				MessageCommunicationModel msg=label.getLineModel().getMiddleLineTextModel().getMessageCommunicationModel(i);
//				msg.setName("["+newName+"]");
//				}catch (Exception e1) {
//				e1.printStackTrace();
//				}       			
//				}
//				label.getLineModel().setDetailProp(detailProp);
				//[]

			}
			else if(label.getMessageIntType()==Integer.valueOf(MessageCommunicationModel.TARGET_TYPE_STATE_TRIGER).intValue()){
				if(label.getLineModel()!=null){
					HashMap  detailProp= (HashMap)label.getLineModel().getDetailProp();
					if(newName.indexOf("[")>-1&&newName.indexOf("/")>-1){					
						detailProp.put(LineModel.ID_GUARD, newName.substring(newName.indexOf("[")+1, newName.indexOf("]")-1));
						System.out.print(newName.substring(newName.trim().indexOf("/")+1));
						if(newName.substring(newName.trim().indexOf("/")+1).equals("")){
							detailProp.put(LineModel.ID_TRAN_EFFECTS, newName.substring(newName.trim().indexOf("/")+1, newName.length()));	
						}else{

						}

					}
//					label.getLineModel().setDetailProp(detailProp);
					//[]
				}
			
			}
			//PKY 08072401 S 라인 메세지들 다이어그램에서 내용을 없애면 사라지도록 수정
			else if(label.getMessageIntType()==Integer.valueOf(MessageCommunicationModel.TYPE_COMMUNICATION_MESSAGE).intValue()){
				if(newName.trim().equals("")){
//					V1.02 WJH 080910 S 협동도 메시지 라벨 삭제시에 화살표 남는 문제 수정
					if(label.getLineModel()!=null){
						for(int i = 0; i< label.getLineModel().getMiddleLineTextModel().getMessageSize(); i++){
							if(label.getLineModel().getMiddleLineTextModel().getMessageCommunicationModel(i).getType()==MessageCommunicationModel.TYPE_WEIGHT){
								MessageCommunicationModel msg=label.getLineModel().getMiddleLineTextModel().getMessageCommunicationModel(i);
								label.getLineModel().getMiddleLineTextModel().getLineTextm().removeElementLabelModel(msg);
								label.getLineModel().getMiddleLineTextModel().removeModel(msg);					
								if(label.getLineModel().getMiddleLineTextModel().getMessageSize()==0){
									label.getLineModel().getMiddleLineTextModel().setSize(new Dimension(0,0));
								}
							}
						}
					}
					else{
						try{
//						MessageCommunicationModel mcm = (MessageCommunicationModel)label.getAcceptParentModel();
//						LineTextModel ltm = (LineTextModel)mcm.getAcceptParentModel();
//						ltm.removeChild(mcm);
//						System.out.println();
							UMLEditor ue = ProjectManager.getInstance().getUMLEditor();
							if(label.getAcceptParentModel()!=null && label.getAcceptParentModel().getAcceptParentModel()!=null && label.getAcceptParentModel().getAcceptParentModel() instanceof LineTextModel){
								LineTextModel  e3 = (LineTextModel)label.getAcceptParentModel().getAcceptParentModel();
								for(int i = 0; i < e3.getMessageSize(); i++){
									MessageCommunicationModel msg =(MessageCommunicationModel) e3.getMessageCommunicationModel(i);
									if(msg.getLabelContents().getMessageIntType()==Integer.valueOf(MessageCommunicationModel.TYPE_COMMUNICATION_MESSAGE)){
										if(label == msg.getLabelContents()){
											e3.getLineTextm().remove(i);
											e3.removeChild(msg);
										}
									}
								}

								if(e3.getLineModel().getMiddleLineTextModel().getMessageSize()==0){
									e3.getLineModel().getMiddleLineTextModel().setSize(new Dimension(0,0));
								}
							}
						}
						catch(Exception ex){
							ex.printStackTrace();
						}
					}
//					V1.02 WJH 080910 E 협동도 메시지 라벨 삭제시에 화살표 남는 문제 수정
				}
			}
			//PKY 08072401 E 라인 메세지들 다이어그램에서 내용을 없애면 사라지도록 수정

//		}else{
//			System.out.print("");
//		}

//		String dd = label.getType();

		//PKY 08072401 S 다이어그램에서 커뮤니케이션 이름을 넣지않고 할 경우 메시지 지워지도록 수정
//		}else 
//		if(label.getAcceptParentModel() instanceof MessageCommunicationModel){
//		MessageCommunicationModel msg=(MessageCommunicationModel)label.getAcceptParentModel();
//		if(msg.getAcceptParentModel() !=null && msg.getAcceptParentModel() instanceof LineTextModel){
//		LineTextModel lineTextModel = (LineTextModel) msg.getAcceptParentModel();
//		if(lineTextModel.getLineModel()!=null){
//		if(label.getMessageIntType()==-1){
//		if(lineTextModel.getLineModel() instanceof ControlFlowLineModel){
//		lineTextModel.getLineModel().getTargetLineTextModel().removeModel(msg);
//		if(lineTextModel.getLineModel().getTargetLineTextModel().getMessageSize()==0){
//		lineTextModel.getLineModel().getTargetLineTextModel().setSize(new Dimension(0,0));
//		}
//		}else{
//		lineTextModel.getLineModel().getMiddleLineTextModel().removeModel(msg);
//		if(lineTextModel.getLineModel().getMiddleLineTextModel().getMessageSize()==0){
//		lineTextModel.getLineModel().getMiddleLineTextModel().setSize(new Dimension(0,0));
//		}
//		}
//		}else if(label.getMessageIntType()==Integer.valueOf(MessageCommunicationModel.TYPE_DERIVED).intValue()){
//		lineTextModel.getLineModel().getSourceLineTextModel().removeModel(msg);
//		lineTextModel.getLineModel().getDetailProp().remove(LineModel.ID_SOURCE_ROLE);
//		if(lineTextModel.getLineModel().getSourceLineTextModel().getMessageSize()==0){
//		lineTextModel.getLineModel().getSourceLineTextModel().setSize(new Dimension(0,0));
//		}
//		}
//		else if(label.getMessageIntType()==Integer.valueOf(MessageCommunicationModel.TARGET_TYPE_DERIVED).intValue()){
//		lineTextModel.getLineModel().getTargetLineTextModel().removeModel(msg);
//		lineTextModel.getLineModel().getDetailProp().remove(LineModel.ID_TARGET_ROLE);
//		if(lineTextModel.getLineModel().getTargetLineTextModel().getMessageSize()==0){
//		lineTextModel.getLineModel().getTargetLineTextModel().setSize(new Dimension(0,0));
//		}
//		}
//		}

//		}
//		}
//		//PKY 08072401 E 다이어그램에서 커뮤니케이션 이름을 넣지않고 할 경우 메시지 지워지도록 수정
	}

	public void undo() {
		label.setLabelContents(oldName);
		UpdateEvent e = new UpdateEvent(IUpdateType.RENAME_TYPE, null);
		label.fireChildUpdate(e);
	}
}
