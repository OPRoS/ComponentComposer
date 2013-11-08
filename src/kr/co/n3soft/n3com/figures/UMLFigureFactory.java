package kr.co.n3soft.n3com.figures;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;

import kr.co.n3soft.n3com.UMLEditor;
import kr.co.n3soft.n3com.anchor.N3ConnectionLocator;
import kr.co.n3soft.n3com.comm.figures.N3PolylineConnection;
import kr.co.n3soft.n3com.comm.figures.UMLElementFigure;
import kr.co.n3soft.n3com.edit.ControlFlowLineEditPart;
import kr.co.n3soft.n3com.edit.GeneralizeLineEditPart;
import kr.co.n3soft.n3com.edit.ImportLineEditPart;
import kr.co.n3soft.n3com.edit.LineEditPart;
import kr.co.n3soft.n3com.edit.ManifestLineEditPart;
import kr.co.n3soft.n3com.edit.MergeLineEditPart;
import kr.co.n3soft.n3com.edit.RealizeLineEditPart;
import kr.co.n3soft.n3com.edit.TransitionFlowLineEditPart;
import kr.co.n3soft.n3com.model.comm.ConstraintsModel;
import kr.co.n3soft.n3com.model.comm.LineModel;
import kr.co.n3soft.n3com.model.comm.LineTextModel;
import kr.co.n3soft.n3com.model.comm.RoleModel;
import kr.co.n3soft.n3com.model.communication.MessageCommunicationModel;
import kr.co.n3soft.n3com.model.composite.RequiredInterfaceLineModel;
import kr.co.n3soft.n3com.model.diagram.N3EditorDiagramModel;
import kr.co.n3soft.n3com.model.usecase.GeneralizeLineModel;
import kr.co.n3soft.n3com.model.usecase.RealizeLineModel;
import kr.co.n3soft.n3com.project.dialog.DetailPropertyTableItem;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.ConnectionLocator;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.PolylineDecoration;
import org.eclipse.draw2d.RoutingAnimator;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.PrecisionPoint;

public class UMLFigureFactory {
	//선 등록
	/**
	 * .setLabelAlignment(4);// 중앙 
	 * 
	 * .setLabelAlignment(2);// Source 
	 * 
	 * .setLabelAlignment(3); // Target
	 */
	public static PolylineConnection createNewBendableWire(LineModel wire) {
		PolylineConnection conn = new PolylineConnection();
		conn.addRoutingListener(RoutingAnimator.getDefault());
		//		conn.setSourceDecoration(new PolygonDecoration());
		//		conn.setTargetDecoration(new PolylineDecoration());
		return conn;
	}

	public static void setDetailPropertyPolylineDecoration(PolylineConnection conn, String prop, HashMap map, LineEditPart le) {
//		if (LineModel.ID_SOURCE_ROLE.equals(prop)) {

		String value = (String)map.get(LineModel.ID_SOURCE_ROLE);
		StringBuffer sb = new StringBuffer("{");
		String name = (String)map.get(LineModel.ID_NAME);
		boolean isMul = false; 
		boolean isUnion = false;
		int index = 0;
		LineModel wire = (LineModel)le.getModel();
		LineTextModel  e2 = wire.getSourceLineTextModel();
		LineTextModel  e3 = wire.getMiddleLineTextModel();
//		LineTextModel  e4 = new LineTextModel();				//PKY 08060201 S 커뮤니케이션 Associate라인의 디스크립션을 넣을 경우 기존에있던 Messgae들이 모두 사라지는문제 초기화문제
		MessageCommunicationModel nameMessageCommunicationModel = null;
		MessageCommunicationModel weightMessageCommunicationModel = null;
		MessageCommunicationModel guardMessageCommunicationModel = null;
		MessageCommunicationModel sourceRoleMessageCommunicationModel = null;
		//PKY 08071601 S State  TransitionFlow 표시가 다른문제 수정
		MessageCommunicationModel trigersMessageCommunicationModel = null;
		MessageCommunicationModel effectMessageCommunicationModel = null;
		//PKY 08071601 E State  TransitionFlow 표시가 다른문제 수정

		MessageCommunicationModel derivedMessageCommunicationModel = null;
		MessageCommunicationModel mulMessageCommunicationModel = null;
		MessageCommunicationModel derivedunionMessageCommunicationModel = null;
		MessageCommunicationModel orderedRoleMessageCommunicationModel = null;
//		MessageCommunicationModel guardMessageCommunicationModel = null;
		//PKY 08051401 S 디스크립션 넣을경우 방향선이 사라지는문제 Name이 사라지는문제
		String text = null;
		if(e3.getMessageSize()>0){
			text=e3.getText(0);
			nameMessageCommunicationModel = e3.getMessageCommunicationModelType(MessageCommunicationModel.TYPE_NAME);
			weightMessageCommunicationModel = e3.getMessageCommunicationModelType(MessageCommunicationModel.TYPE_WEIGHT);
			guardMessageCommunicationModel  = e3.getMessageCommunicationModelType(MessageCommunicationModel.TYPE_GUARD);
			trigersMessageCommunicationModel = e3.getMessageCommunicationModelType(MessageCommunicationModel.TARGET_TYPE_STATE_TRIGER);//PKY 08071601 S State  TransitionFlow 표시가 다른문제 수정
			effectMessageCommunicationModel = e3.getMessageCommunicationModelType(MessageCommunicationModel.TARGET_TYPE_STATE_EFFECT);//PKY 08071601 S State  TransitionFlow 표시가 다른문제 수정
		}

		if(e2.getMessageSize()>0){
			derivedMessageCommunicationModel = e2.getMessageCommunicationModelType(MessageCommunicationModel.TYPE_DERIVED);
			mulMessageCommunicationModel = e2.getMessageCommunicationModelType(MessageCommunicationModel.TYPE_MULTPLICITY);
			derivedunionMessageCommunicationModel = e2.getMessageCommunicationModelType(MessageCommunicationModel.TYPE_DERIVED_UNION);
			orderedRoleMessageCommunicationModel = e2.getMessageCommunicationModelType(MessageCommunicationModel.TYPE_ORDERED);
			sourceRoleMessageCommunicationModel = e2.getMessageCommunicationModelType(MessageCommunicationModel.TYPE_ROLE);
		}
		//PKY 08091106 S
		if(name!=null && !name.trim().equals("")){
			text = name;
			if(map.get(LineModel.ID_NAME)!=null){
				if(le.getModel()!=null && le.getModel() instanceof LineModel){
					((LineModel)le.getModel()).setName((String)map.get(LineModel.ID_NAME)); 
				}
			}
		}
		//PKY 08091106 E

		//PKY 08051401 E 디스크립션 넣을경우 방향선이 사라지는문제 Name이 사라지는문제
//		e4=(LineTextModel) e3.clone();				//PKY 08060201 S 커뮤니케이션 Associate라인의 디스크립션을 넣을 경우 기존에있던 Messgae들이 모두 사라지는문제 초기화문제
		e2.removeAll();
		e3.removeAll();
		String guard = (String)map.get(LineModel.ID_GUARD);
		String weight = (String)map.get(LineModel.ID_WEIGHT);
		//PKY 08091106 S
		//PKY 08051401 S 디스크립션 넣을경우 방향선이 사라지는문제 Name이 사라지는문제
//		if(text!=null){
//			Label tt= new Label();
//			if(nameMessageCommunicationModel==null){
//				MessageCommunicationModel m= new MessageCommunicationModel();
//				wire.addMiddleLineTextModel(m);
//				m.setType(MessageCommunicationModel.TYPE_NAME);
//				m.getLabelContents().setLineModel(wire);
//				e3.setText(text, 0);
//
//				N3ConnectionLocator n3 = new N3ConnectionLocator(conn,ConnectionLocator.MIDDLE);
//				n3.setModel(e3);
//				tt.setIconAlignment(4);
//				if(lableAdd(conn,n3))
//					conn.add(tt, n3);
//
//
//			}
//			else{
//
//				wire.addMiddleLineTextModel(nameMessageCommunicationModel);
//				e3.setText(nameMessageCommunicationModel.getName(),index);
//				//PKY 08060201 S 커뮤니케이션 Associate라인의 디스크립션을 넣을 경우 기존에있던 Messgae들이 모두 사라지는문제 초기화문제
////				if(e4.getMessageSize()>1){
////				for(int j=1; j<e4.getMessageSize();j++){
////				wire.addMiddleLineTextModel(e4.getMessageCommunicationModel(j));
////				}
////				}
//				//PKY 08060201 E 커뮤니케이션 Associate라인의 디스크립션을 넣을 경우 기존에있던 Messgae들이 모두 사라지는문제 초기화문제
//
//			}
//			index++;
//		}
		//PKY 08051401 E 디스크립션 넣을경우 방향선이 사라지는문제 Name이 사라지는문제
		//PKY 08091106 E

		//PKY 08061101 S ControlFlowLine,guard,weight 수정,삭제 시 추가되거나 삭제되지 않는문제


		if(weight!=null && !weight.trim().equals("")){
			if(weightMessageCommunicationModel==null){
				Label tt = new Label();
				MessageCommunicationModel m = new MessageCommunicationModel();
				wire.addMiddleLineTextModel(m);
				m.getLabelContents().setLineModel(wire);
				m.getLabelContents().setMessageIntType(Integer.valueOf(MessageCommunicationModel.TYPE_WEIGHT));
				m.setType(MessageCommunicationModel.TYPE_WEIGHT);
				e3.setText("{weight="+weight+"}", index);
				N3ConnectionLocator n3 = new N3ConnectionLocator(conn, ConnectionLocator.MIDDLE);
				n3.setModel(e3);

				((LineTextModel)e3).setN3ConnectionLocator(n3);//PKY 08061101 SControlFlowLine,guard,weight 위치및 사이즈 저장안되는문제
				tt.setIconAlignment(4);
				if(lableAdd(conn,n3))
					conn.add(tt, n3);

			}
			else{
				weightMessageCommunicationModel.setName("{weight="+weight+"}");
				wire.addMiddleLineTextModel(weightMessageCommunicationModel);
			}
			index++;
		}else{
			if(weightMessageCommunicationModel!=null){
				wire.getMiddleLineTextModel().removeChild(weightMessageCommunicationModel);
			}
		}
		//PKY 08071601 S State  TransitionFlow 표시가 다른문제 수정
		if(map.get(LineModel.ID_TRAN_TRIGERS)!=null){
			ArrayList list=(ArrayList)map.get(LineModel.ID_TRAN_TRIGERS);
			if(list.size()>0)
				if(trigersMessageCommunicationModel==null){
					Label tt = new Label();
					MessageCommunicationModel m = new MessageCommunicationModel();
					m.getLabelContents().setLineModel(wire);
					wire.addMiddleLineTextModel(m);
					m.setType(MessageCommunicationModel.TARGET_TYPE_STATE_TRIGER);
					m.getLabelContents().setMessageIntType(Integer.valueOf(MessageCommunicationModel.TARGET_TYPE_STATE_TRIGER));
					String sName="";
					for(int i = 0; i < list.size(); i++){
						DetailPropertyTableItem item = (DetailPropertyTableItem)list.get(i);
						sName=sName+item.sName+",";
					}				
					e3.setText(sName.substring(0, sName.length()-1), e3.getMessageSize()-1);	
					N3ConnectionLocator n3 = new N3ConnectionLocator(conn, ConnectionLocator.MIDDLE);
					n3.setModel(e3);
					((LineTextModel)e2).setN3ConnectionLocator(n3);//PKY 08061101 SControlFlowLine,guard,weight 위치및 사이즈 저장안되는문제
					tt.setIconAlignment(4);
					if(lableAdd(conn,n3))
						conn.add(tt, n3);	
				}else{
					boolean check=false;
					for(int i = 0; i < e3.getMessageSize(); i++){
						if(e3.getMessageCommunicationModel(i)== trigersMessageCommunicationModel){
							check=true;
						}
					}
					String sName="";
					for(int i = 0; i < list.size(); i++){
						DetailPropertyTableItem item = (DetailPropertyTableItem)list.get(i);
						sName=sName+item.sName+",";
					}		
					if(check==false)
						wire.addMiddleLineTextModel(trigersMessageCommunicationModel);
					trigersMessageCommunicationModel.setName(sName.substring(0, sName.length()-1));

				}

		}
		//PKY 08071601 E State  TransitionFlow 표시가 다른문제 수정

		//PKY 08071601 S State  TransitionFlow 표시가 다른문제 수정
		if(le instanceof TransitionFlowLineEditPart&& map.get(LineModel.ID_TRAN_EFFECTS)!=null&& 
				!((String)map.get(LineModel.ID_TRAN_EFFECTS)).trim().equals("")&&guard!=null && !guard.trim().equals("")){
			//PKY 08071601 S State  TransitionFlow 표시가 다른문제 수정
			if(guardMessageCommunicationModel==null){

				//PKY 08071601 E State  TransitionFlow 표시가 다른문제 수정

				Label tt = new Label();
				MessageCommunicationModel m = new MessageCommunicationModel();
				m.getLabelContents().setLineModel(wire);
				wire.addMiddleLineTextModel(m);
				m.setType(MessageCommunicationModel.TYPE_GUARD);
				m.getLabelContents().setMessageIntType(Integer.valueOf(MessageCommunicationModel.TYPE_GUARD));					
				e3.setText("["+guard+"]"+" / "+(String)map.get(LineModel.ID_TRAN_EFFECTS), e3.getMessageSize()-1);
				//PKY 08071601 S 라인 뷰쪽에서 개행사이즈까지 구하지 않아서 사이즈가 작게되어 글씨가 짤리는현상
				if(((String)map.get(LineModel.ID_TRAN_EFFECTS)).indexOf("\r")>-1){
					String[] names=((String)map.get(LineModel.ID_TRAN_EFFECTS)).split("\r");
					int i = names.length*10;
					wire.getMiddleLineTextModel().setSize(new Dimension(wire.getMiddleLineTextModel().getSize().width,wire.getMiddleLineTextModel().getSize().height+i));
				}
				//PKY 08071601 E 라인 뷰쪽에서 개행사이즈까지 구하지 않아서 사이즈가 작게되어 글씨가 짤리는현상
				N3ConnectionLocator n3 = new N3ConnectionLocator(conn, ConnectionLocator.MIDDLE);
				n3.setModel(e3);

				((LineTextModel)e2).setN3ConnectionLocator(n3);//PKY 08061101 SControlFlowLine,guard,weight 위치및 사이즈 저장안되는문제
				tt.setIconAlignment(4);
				if(lableAdd(conn,n3))
					conn.add(tt, n3);

			}
			else{
				boolean check=false;
				for(int i = 0; i < e3.getMessageSize(); i++){
					if(e3.getMessageCommunicationModel(i)== guardMessageCommunicationModel){
						check=true;
					}
				}
				guardMessageCommunicationModel.setName("["+guard+"]"+" / "+(String)map.get(LineModel.ID_TRAN_EFFECTS));
				if(check==false)
					wire.addMiddleLineTextModel(guardMessageCommunicationModel);
//				wire.addMiddleLineTextModel(guardMessageCommunicationModel);
			}
		}else if ((map.get(LineModel.ID_TRAN_EFFECTS)==null||((String)map.get(LineModel.ID_TRAN_EFFECTS)).trim().equals(""))&&
				guard!=null && !guard.trim().equals("")){
			if(guardMessageCommunicationModel==null){
				Label tt = new Label();
				MessageCommunicationModel m = new MessageCommunicationModel();
				m.getLabelContents().setLineModel(wire);
				wire.addMiddleLineTextModel(m);
				m.setType(MessageCommunicationModel.TYPE_GUARD);
				m.getLabelContents().setMessageIntType(Integer.valueOf(MessageCommunicationModel.TYPE_GUARD));
				e3.setText("["+guard+"]", e3.getMessageSize()-1);
				N3ConnectionLocator n3 = new N3ConnectionLocator(conn, ConnectionLocator.MIDDLE);
				n3.setModel(e3);

				((LineTextModel)e2).setN3ConnectionLocator(n3);//PKY 08061101 SControlFlowLine,guard,weight 위치및 사이즈 저장안되는문제
				tt.setIconAlignment(4);
				if(lableAdd(conn,n3))
					conn.add(tt, n3);

			}
			else if(guard!=null&&!guard.trim().equals("")){
				boolean check=false;
				for(int i = 0; i < e3.getMessageSize(); i++){
					if(e3.getMessageCommunicationModel(i)== guardMessageCommunicationModel){
						check=true;
					}
				}
				guardMessageCommunicationModel.setName("["+guard+"]");
				if(check==false)
					wire.addMiddleLineTextModel(guardMessageCommunicationModel);
//				wire.addMiddleLineTextModel(guardMessageCommunicationModel);
			}
		}else if((guard==null ||guard.trim().equals(""))&&
				le instanceof TransitionFlowLineEditPart&& map.get(LineModel.ID_TRAN_EFFECTS)!=null&& !((String)map.get(LineModel.ID_TRAN_EFFECTS)).trim().equals("")){
			if(guardMessageCommunicationModel==null){
				Label tt = new Label();
				MessageCommunicationModel m = new MessageCommunicationModel();
				m.getLabelContents().setLineModel(wire);
				wire.addMiddleLineTextModel(m);
				m.setType(MessageCommunicationModel.TYPE_GUARD);
				m.getLabelContents().setMessageIntType(Integer.valueOf(MessageCommunicationModel.TYPE_GUARD));					
				e3.setText("/ "+(String)map.get(LineModel.ID_TRAN_EFFECTS), e3.getMessageSize()-1);
				N3ConnectionLocator n3 = new N3ConnectionLocator(conn, ConnectionLocator.MIDDLE);
				n3.setModel(e3);
				//PKY 08071601 S 라인 뷰쪽에서 개행사이즈까지 구하지 않아서 사이즈가 작게되어 글씨가 짤리는현상
				if(((String)map.get(LineModel.ID_TRAN_EFFECTS)).indexOf("\r")>-1){
					String[] names=((String)map.get(LineModel.ID_TRAN_EFFECTS)).split("\r");
					int i = names.length*10;
					wire.getMiddleLineTextModel().setSize(new Dimension(wire.getMiddleLineTextModel().getSize().width,wire.getMiddleLineTextModel().getSize().height+i));
				}
				//PKY 08071601 E 라인 뷰쪽에서 개행사이즈까지 구하지 않아서 사이즈가 작게되어 글씨가 짤리는현상

				((LineTextModel)e2).setN3ConnectionLocator(n3);//PKY 08061101 SControlFlowLine,guard,weight 위치및 사이즈 저장안되는문제
				tt.setIconAlignment(4);
				if(lableAdd(conn,n3))
					conn.add(tt, n3);

			}
			else{
				boolean check=false;
				for(int i = 0; i < e3.getMessageSize(); i++){
					if(e3.getMessageCommunicationModel(i)== guardMessageCommunicationModel){
						check=true;
					}
				}
				guardMessageCommunicationModel.setName("/ "+(String)map.get(LineModel.ID_TRAN_EFFECTS));
				if(check==false)
					wire.addMiddleLineTextModel(guardMessageCommunicationModel);
			}				
		}else{
			if(guardMessageCommunicationModel!=null){
				boolean check=false;
				for(int i = 0; i < e3.getMessageSize(); i++){
					if(e3.getMessageCommunicationModel(i)== guardMessageCommunicationModel){
						check=true;
					}
				}
				guardMessageCommunicationModel.setName("/ "+(String)map.get(LineModel.ID_TRAN_EFFECTS));
				if(check==false)
					wire.addMiddleLineTextModel(guardMessageCommunicationModel);
			}
		}

//		if(guard==null || guard.trim().equals("")){
//		if(le instanceof TransitionFlowLineEditPart&& map.get(LineModel.ID_TRAN_EFFECTS)!=null&& !((String)map.get(LineModel.ID_TRAN_EFFECTS)).trim().equals("")){

//		}
//		}else if(effectMessageCommunicationModel!=null){
//		e3.getLineTextm().removeAll();
//		}
		//PKY 08071601 E State  TransitionFlow 표시가 다른문제 수정

		//PKY 08061101 E ControlFlowLine,guard,weight 수정,삭제 시 추가되거나 삭제되지 않는문제
		index = 0;

		if(value!=null && !value.trim().equals("")){
			Label tt = new Label();
			Label tt1 = new Label();
			Label tt2 = new Label();

			String derived = (String)map.get(LineModel.ID_SOURCE_DERIVED);
			if(derived!=null && derived.trim().equals("true")){
				value = "+/"+value;
			}
			else{
				value = "+"+value;
			}


			if(derivedMessageCommunicationModel==null){
				derivedMessageCommunicationModel = new MessageCommunicationModel();
				wire.addSourceLineTextModel(derivedMessageCommunicationModel);
//				e2.setText(value, 0);
				derivedMessageCommunicationModel.setName(value);
				derivedMessageCommunicationModel.setType(MessageCommunicationModel.TYPE_DERIVED);
				derivedMessageCommunicationModel.getLabelContents().setMessageIntType(Integer.valueOf(MessageCommunicationModel.TYPE_DERIVED));
				derivedMessageCommunicationModel.getLabelContents().setLineModel(wire);
				N3ConnectionLocator n3 = new N3ConnectionLocator(conn, ConnectionLocator.SOURCE);
				((LineTextModel)e2).setN3ConnectionLocator(n3);//PKY 08061101 SControlFlowLine,guard,weight 위치및 사이즈 저장안되는문제
				n3.setModel(e2);
				tt.setIconAlignment(2);
				if(lableAdd(conn,n3))
					conn.add(tt, n3);

			}
			else{
				derivedMessageCommunicationModel.setName(value);
				wire.addSourceLineTextModel(derivedMessageCommunicationModel);
			}

			index++;

		}

		value = (String)map.get(LineModel.ID_SOURCE_MUL);
		if(value!=null && !value.trim().equals("")){
			Label tt = new Label();



			if(mulMessageCommunicationModel==null){

				mulMessageCommunicationModel = new MessageCommunicationModel();
				mulMessageCommunicationModel.getLabelContents().setLineModel(wire);
				wire.addSourceLineTextModel(mulMessageCommunicationModel);
				mulMessageCommunicationModel.setName(value);
				mulMessageCommunicationModel.setType(MessageCommunicationModel.TYPE_MULTPLICITY);
				mulMessageCommunicationModel.getLabelContents().setMessageIntType(Integer.valueOf(MessageCommunicationModel.TYPE_MULTPLICITY));

				N3ConnectionLocator n3 = new N3ConnectionLocator(conn, ConnectionLocator.SOURCE);
				n3.setModel(e2);
				tt.setIconAlignment(2);
				if(lableAdd(conn,n3))
					conn.add(tt, n3);

			}
			else{
				mulMessageCommunicationModel.setName(value);
				wire.addSourceLineTextModel(mulMessageCommunicationModel); 
			}
			index++;
			isMul = true;

		}

		value = (String)map.get(LineModel.ID_SOURCE_DERIVEDUNION);
		if(value!=null && value.trim().equals("true")){
			Label tt = new Label();

			sb.append("union");
			if(derivedunionMessageCommunicationModel==null){
				derivedunionMessageCommunicationModel = new MessageCommunicationModel();
				derivedunionMessageCommunicationModel.setType(MessageCommunicationModel.TYPE_DERIVED_UNION);
				derivedunionMessageCommunicationModel.getLabelContents().setLineModel(wire);
				derivedunionMessageCommunicationModel.getLabelContents().setMessageIntType(Integer.valueOf(MessageCommunicationModel.TYPE_DERIVED_UNION));
				wire.addSourceLineTextModel(derivedunionMessageCommunicationModel);
				e2.setText(sb.toString()+"}", index);


				N3ConnectionLocator n3 = new N3ConnectionLocator(conn, ConnectionLocator.SOURCE);
				n3.setModel(e2);
				tt.setIconAlignment(2);
				if(lableAdd(conn,n3))
					conn.add(tt, n3);

			}
			else{
				derivedunionMessageCommunicationModel.setName(sb.toString());
				wire.addSourceLineTextModel(derivedunionMessageCommunicationModel); 
			}
			isUnion = true;

		}
		if(isMul && isUnion){

			value = (String)map.get(LineModel.ID_SOURCE_ORDERED);
			String value2 = (String)map.get(LineModel.ID_SOURCE_ALLOWDUPLICATES);
			if(value!=null && value.trim().equals("true")
					&& value2!=null && value2.trim().equals("true")){
				sb.append(",sequence");

			}
			else  if(value!=null && value.trim().equals("true")){
				sb.append(",ordered");
			}
			else  if(value2!=null && value2.trim().equals("true")){
				sb.append(",bag");
			}

			derivedunionMessageCommunicationModel.setName(sb.toString()+"}");
//			e2.setText(sb.toString()+"}", index);

		}
		else if(isMul){
			if(orderedRoleMessageCommunicationModel==null){
				Label tt = new Label();
				orderedRoleMessageCommunicationModel = new MessageCommunicationModel();
				orderedRoleMessageCommunicationModel.getLabelContents().setLineModel(wire);

				orderedRoleMessageCommunicationModel.setType(MessageCommunicationModel.TYPE_ORDERED);


				orderedRoleMessageCommunicationModel.getLabelContents().setMessageIntType(Integer.valueOf(MessageCommunicationModel.TYPE_ORDERED));

				value = (String)map.get(LineModel.ID_SOURCE_ORDERED);
				String value2 = (String)map.get(LineModel.ID_SOURCE_ALLOWDUPLICATES);
				if(value!=null && value.trim().equals("true")
						&& value2!=null && value2.trim().equals("true")){
					sb.append("sequence");
					wire.addSourceLineTextModel(orderedRoleMessageCommunicationModel);
					e2.setText(sb.toString()+"}", index);

				}
				else  if(value!=null && value.trim().equals("true")){
					sb.append("ordered");
					wire.addSourceLineTextModel(orderedRoleMessageCommunicationModel);
					e2.setText(sb.toString()+"}", index);
				}
				else  if(value2!=null && value2.trim().equals("true")){
					sb.append("bag");
					wire.addSourceLineTextModel(orderedRoleMessageCommunicationModel);
					e2.setText(sb.toString()+"}", index);
				}





				N3ConnectionLocator n3 = new N3ConnectionLocator(conn, ConnectionLocator.SOURCE);
				n3.setModel(e2);
				tt.setIconAlignment(2);
				if(lableAdd(conn,n3))
					conn.add(tt, n3); 

			}
			else{
				orderedRoleMessageCommunicationModel.setName(sb.toString());
				wire.addSourceLineTextModel(orderedRoleMessageCommunicationModel); 
			}
		}

		value = (String)map.get(LineModel.ID_SOURCE_AGGREGATION);
		String value2 = (String)map.get(LineModel.ID_SOURCE_NAVIGABILITY);
		PolygonDecoration polyLine = new PolygonDecoration();
		if(value!=null && value.trim().equals("Aggregate")){

			PointList decorationPointList = new PointList();
			decorationPointList.addPoint(0, 0);
			decorationPointList.addPoint(-2, 2);
			decorationPointList.addPoint(-4, 0);
			decorationPointList.addPoint(-2, -2);
			polyLine.setTemplate(decorationPointList);
			//			polyLine.setScale(15,5);
			polyLine.setBackgroundColor(ColorConstants.tooltipBackground);
			conn.setSourceDecoration(polyLine);


		}
		else  if(value!=null && value.trim().equals("Composite")){
			PointList decorationPointList = new PointList();
			decorationPointList.addPoint(0, 0);
			decorationPointList.addPoint(-2, 2);
			decorationPointList.addPoint(-4, 0);
			decorationPointList.addPoint(-2, -2);
			polyLine.setTemplate(decorationPointList);
			//			polyLine.setScale(15,5);
			polyLine.setBackgroundColor(ColorConstants.black);
			conn.setSourceDecoration(polyLine);
		}
		else if(value2!=null && value2.trim().equals("Navigable")){
			PolylineDecoration polyLine2 = new PolylineDecoration();
			polyLine2.setTemplate(polyLine2.TRIANGLE_TIP);

			conn.setSourceDecoration(polyLine2);

//			else {
//			conn.setTargetDecoration(polyLine2);
//			}

		}
		else{
			PointList pl = new PointList();
			polyLine.setTemplate(pl);
			polyLine.setBackgroundColor(ColorConstants.tooltipBackground);
			conn.setSourceDecoration(polyLine);
		}


		value = (String)map.get(LineModel.ID_TARGET_ROLE);
		sb = new StringBuffer("{");

		isMul = false; 
		isUnion = false;
		index = 0;

		e2 = wire.getTargetLineTextModel();
		derivedMessageCommunicationModel = null;
		mulMessageCommunicationModel = null;
		derivedunionMessageCommunicationModel = null;
		orderedRoleMessageCommunicationModel = null;
		sourceRoleMessageCommunicationModel = null;
		if(e2.getMessageSize()>0){
			derivedMessageCommunicationModel = e2.getMessageCommunicationModelType(MessageCommunicationModel.TARGET_TYPE_DERIVED);
			mulMessageCommunicationModel = e2.getMessageCommunicationModelType(MessageCommunicationModel.TARGET_TYPE_MULTPLICITY);
			derivedunionMessageCommunicationModel = e2.getMessageCommunicationModelType(MessageCommunicationModel.TARGET_TYPE_DERIVED_UNION);
			orderedRoleMessageCommunicationModel = e2.getMessageCommunicationModelType(MessageCommunicationModel.TARGET_TYPE_ORDERED);
			sourceRoleMessageCommunicationModel = e2.getMessageCommunicationModelType(MessageCommunicationModel.TARGET_TYPE_ROLE);
		}
		e2.removeAll();

		if(value!=null && !value.trim().equals("")){
			Label tt = new Label();
			Label tt1 = new Label();
			Label tt2 = new Label();

			String derived = (String)map.get(LineModel.ID_TARGET_DERIVED);
			if(derived!=null && derived.trim().equals("true")){
				value = "+/"+value;
			}
			else{
				value = "+"+value;
			}


			e2 = wire.getTargetLineTextModel();
			if(derivedMessageCommunicationModel==null){
				derivedMessageCommunicationModel = new MessageCommunicationModel();
				derivedMessageCommunicationModel.getLabelContents().setLineModel(wire);
				wire.addTargetLineTextModel(derivedMessageCommunicationModel);
//				e2.setText(value, 0);
				derivedMessageCommunicationModel.setName(value);
				derivedMessageCommunicationModel.setType(MessageCommunicationModel.TARGET_TYPE_DERIVED);

				derivedMessageCommunicationModel.getLabelContents().setMessageIntType(Integer.valueOf(MessageCommunicationModel.TARGET_TYPE_DERIVED));
				N3ConnectionLocator n3 = new N3ConnectionLocator(conn, ConnectionLocator.TARGET);
				((LineTextModel)e2).setN3ConnectionLocator(n3);//PKY 08061101 SControlFlowLine,guard,weight 위치및 사이즈 저장안되는문제
				n3.setModel(e2);
				tt.setIconAlignment(3);
				if(lableAdd(conn,n3))
					conn.add(tt, n3);

			}
			else{
				derivedMessageCommunicationModel.setName(value);
				wire.addTargetLineTextModel(derivedMessageCommunicationModel);
			}
			index++;

		}

		value = (String)map.get(LineModel.ID_TARGET_MUL);
		if(value!=null && !value.trim().equals("")){
			Label tt = new Label();




			if(mulMessageCommunicationModel==null){
				mulMessageCommunicationModel = new MessageCommunicationModel();
				mulMessageCommunicationModel.getLabelContents().setLineModel(wire);
				wire.addTargetLineTextModel(mulMessageCommunicationModel);
				mulMessageCommunicationModel.setType(MessageCommunicationModel.TARGET_TYPE_MULTPLICITY);
				mulMessageCommunicationModel.setName(value);

				mulMessageCommunicationModel.getLabelContents().setMessageIntType(Integer.valueOf(MessageCommunicationModel.TARGET_TYPE_MULTPLICITY));
				N3ConnectionLocator n3 = new N3ConnectionLocator(conn, ConnectionLocator.TARGET);
				n3.setModel(e2);
				tt.setIconAlignment(3);
				if(lableAdd(conn,n3))
					conn.add(tt, n3);

			}
			else{
				mulMessageCommunicationModel.setName(value);
				wire.addTargetLineTextModel(mulMessageCommunicationModel); 
			}
			index++;
			isMul = true;

		}

		value = (String)map.get(LineModel.ID_TARGET_DERIVEDUNION);
		if(value!=null && value.trim().equals("true")){
			Label tt = new Label();

			sb.append("union");
			if(derivedunionMessageCommunicationModel==null){
				derivedunionMessageCommunicationModel = new MessageCommunicationModel();
				derivedunionMessageCommunicationModel.setType(MessageCommunicationModel.TARGET_TYPE_DERIVED_UNION);

				derivedunionMessageCommunicationModel.getLabelContents().setLineModel(wire);
				wire.addTargetLineTextModel(derivedunionMessageCommunicationModel);
				derivedunionMessageCommunicationModel.setName(sb.toString()+"}");
//				e2.setText(sb.toString()+"}", index);
				derivedunionMessageCommunicationModel.getLabelContents().setMessageIntType(Integer.valueOf(MessageCommunicationModel.TARGET_TYPE_DERIVED_UNION));

				N3ConnectionLocator n3 = new N3ConnectionLocator(conn, ConnectionLocator.TARGET);
				n3.setModel(e2);
				tt.setIconAlignment(3);
				if(lableAdd(conn,n3))
					conn.add(tt, n3);

			}
			else{
				derivedunionMessageCommunicationModel.setName(sb.toString());
				wire.addTargetLineTextModel(derivedunionMessageCommunicationModel);  
			}
			isUnion = true;

		}
		if(isMul && isUnion){

			value = (String)map.get(LineModel.ID_TARGET_ORDERED);
			value2 = (String)map.get(LineModel.ID_TARGET_ALLOWDUPLICATES);
			if(value!=null && value.trim().equals("true")
					&& value2!=null && value2.trim().equals("true")){
				sb.append(",sequence");

			}
			else  if(value!=null && value.trim().equals("true")){
				sb.append(",ordered");
			}
			else  if(value2!=null && value2.trim().equals("true")){
				sb.append(",bag");
			}


			derivedunionMessageCommunicationModel.setName(sb.toString()+"}");

		}
		else if(isMul){
			Label tt = new Label();
			if(orderedRoleMessageCommunicationModel==null){
				orderedRoleMessageCommunicationModel = new MessageCommunicationModel();
				orderedRoleMessageCommunicationModel.setType(MessageCommunicationModel.TARGET_TYPE_ORDERED);
				orderedRoleMessageCommunicationModel.getLabelContents().setLineModel(wire);
				value = (String)map.get(LineModel.ID_TARGET_ORDERED);
				value2 = (String)map.get(LineModel.ID_TARGET_ALLOWDUPLICATES);
				if(value!=null && value.trim().equals("true")
						&& value2!=null && value2.trim().equals("true")){
					sb.append("sequence");
					wire.addTargetLineTextModel(orderedRoleMessageCommunicationModel);
					orderedRoleMessageCommunicationModel.setName(sb.toString()+"}");

				}
				else  if(value!=null && value.trim().equals("true")){
					sb.append("ordered");
					wire.addTargetLineTextModel(orderedRoleMessageCommunicationModel);
					orderedRoleMessageCommunicationModel.setName(sb.toString()+"}");
				}
				else  if(value2!=null && value2.trim().equals("true")){
					sb.append("bag");
					wire.addTargetLineTextModel(orderedRoleMessageCommunicationModel);
					orderedRoleMessageCommunicationModel.setName(sb.toString()+"}");
				}





				N3ConnectionLocator n3 = new N3ConnectionLocator(conn, ConnectionLocator.TARGET);
				n3.setModel(e2);
				tt.setIconAlignment(3);
				if(lableAdd(conn,n3))
					conn.add(tt, n3); 

			}
			else{
				orderedRoleMessageCommunicationModel.setName(sb.toString());
				wire.addTargetLineTextModel(orderedRoleMessageCommunicationModel); 
			}
		}

		value = (String)map.get(LineModel.ID_TARGET_AGGREGATION);
		value2 = (String)map.get(LineModel.ID_TARGET_NAVIGABILITY);
		polyLine = new PolygonDecoration();
		if(value!=null && value.trim().equals("Aggregate")){

			PointList decorationPointList = new PointList();
			decorationPointList.addPoint(0, 0);
			decorationPointList.addPoint(-2, 2);
			decorationPointList.addPoint(-4, 0);
			decorationPointList.addPoint(-2, -2);
			polyLine.setTemplate(decorationPointList);
			//			polyLine.setScale(15,5);
			polyLine.setBackgroundColor(ColorConstants.tooltipBackground);
			conn.setTargetDecoration(polyLine);


		}
		else  if(value!=null && value.trim().equals("Composite")){
			PointList decorationPointList = new PointList();
			decorationPointList.addPoint(0, 0);
			decorationPointList.addPoint(-2, 2);
			decorationPointList.addPoint(-4, 0);
			decorationPointList.addPoint(-2, -2);
			polyLine.setTemplate(decorationPointList);
			//			polyLine.setScale(15,5);
			polyLine.setBackgroundColor(ColorConstants.black);
			conn.setTargetDecoration(polyLine);
		}
		else if(value2!=null && value2.trim().equals("Navigable")){
			PolylineDecoration polyLine2 = new PolylineDecoration();
			polyLine2.setTemplate(polyLine2.TRIANGLE_TIP);

			conn.setTargetDecoration(polyLine2);

//			else {
//			conn.setTargetDecoration(polyLine2);
//			}

		}
		else{
			PointList pl = new PointList();
			polyLine.setTemplate(pl);
			polyLine.setBackgroundColor(ColorConstants.tooltipBackground);
			conn.setTargetDecoration(polyLine);
		}
		//PKY 08061801 S LineText모델 자동으로 글씨 크기에따라서 늘어나도록
		int mstrlen = 0;
		int msize=0;
		int sstrlen = 0;
		int ssize=0;
		int tstrlen = 0;
		int tsize=0;
		if(wire.getMiddleLineTextModel()!=null)
			if(wire.getMiddleLineTextModel().getLineTextm()!=null)
				if(wire.getMiddleLineTextModel().getLineTextm().getLineTextModels()!=null)
					if(wire.getMiddleLineTextModel().getLineTextm().getLineTextModels().size()>0){
						for(int i = 0; i < wire.getMiddleLineTextModel().getLineTextm().getLineTextModels().size(); i++){
							if( wire.getMiddleLineTextModel().getLineTextm().getLineTextModels().get(i) instanceof MessageCommunicationModel){
								String lineName=((MessageCommunicationModel)wire.getMiddleLineTextModel().getLineTextm().getLineTextModels().get(i)).getName();
								mstrlen=ProjectManager.getInstance().widthAutoSize(lineName);
								if(mstrlen>msize){
									msize=mstrlen;
								}
							}
						}
					}
		if(msize>0){
			wire.getMiddleLineTextModel().setSize(new Dimension(msize+12,wire.getMiddleLineTextModel().getSize().height));
		}
		if(wire.getSourceLineTextModel()!=null)
			if(wire.getSourceLineTextModel().getLineTextm()!=null)
				if(wire.getSourceLineTextModel().getLineTextm().getLineTextModels()!=null)
					if(wire.getSourceLineTextModel().getLineTextm().getLineTextModels().size()>0){
						for(int i = 0; i < wire.getSourceLineTextModel().getLineTextm().getLineTextModels().size(); i++){
							if( wire.getSourceLineTextModel().getLineTextm().getLineTextModels().get(i) instanceof MessageCommunicationModel){
								String lineName=((MessageCommunicationModel)wire.getSourceLineTextModel().getLineTextm().getLineTextModels().get(i)).getName();
								sstrlen=ProjectManager.getInstance().widthAutoSize(lineName);
								if(sstrlen>ssize){
									ssize=sstrlen;
								}
							}
						}
					}
		if(ssize>0){
			wire.getSourceLineTextModel().setSize(new Dimension(ssize+12,wire.getSourceLineTextModel().getSize().height));
		}
		if(wire.getTargetLineTextModel()!=null)
			if(wire.getTargetLineTextModel().getLineTextm()!=null)
				if(wire.getTargetLineTextModel().getLineTextm().getLineTextModels()!=null)
					if(wire.getTargetLineTextModel().getLineTextm().getLineTextModels().size()>0){
						for(int i = 0; i < wire.getTargetLineTextModel().getLineTextm().getLineTextModels().size(); i++){
							if( wire.getTargetLineTextModel().getLineTextm().getLineTextModels().get(i) instanceof MessageCommunicationModel){
								String lineName=((MessageCommunicationModel)wire.getTargetLineTextModel().getLineTextm().getLineTextModels().get(i)).getName();
								tstrlen=ProjectManager.getInstance().widthAutoSize(lineName);
								if(tstrlen>tsize){
									tsize=tstrlen;
								}
							}
						}
					}
		if(tsize>0){
			wire.getTargetLineTextModel().setSize(new Dimension(tsize+12,wire.getTargetLineTextModel().getSize().height));
		}
		if(le instanceof GeneralizeLineEditPart||le instanceof RealizeLineEditPart){
			polyLine.setTemplate(polyLine.TRIANGLE_TIP);
			polyLine.setBackgroundColor(ColorConstants.tooltipBackground);
			polyLine.setScale(15, 5);
			conn.setTargetDecoration(polyLine);

		}
		//PKY 08071601 S 라인 스트레오타입 복사 할 경우 복사안되는문제
		if(map.get(LineModel.ID_STEREOTYPE)!=null){
			if(le.getModel()!=null && le.getModel() instanceof LineModel){
				((LineModel)le.getModel()).setStereotype((String)map.get(LineModel.ID_STEREOTYPE)); 
			}
		}
		if(wire instanceof RequiredInterfaceLineModel){
			PolylineDecoration polyLine1 = new PolylineDecoration();
			PointList p = new PointList();
			p.addPoint(38, 40);
			p.addPoint(20, 35);
			p.addPoint(7, 22);
			p.addPoint(2, 10);
			p.addPoint(0, 0);
			p.addPoint(2, -10);
			p.addPoint(7, -22);
			p.addPoint(20, -35);
			p.addPoint(38, -40);
			polyLine1.setTemplate(p);
			polyLine1.setScale(0.5, 0.5);
			polyLine1.setLineWidth(3);
			conn.setTargetDecoration(polyLine1);
		}
		//PKY 08071601 E 라인 스트레오타입 복사 할 경우 복사안되는문제

		//PKY 08061801 E LineText모델 자동으로 글씨 크기에따라서 늘어나도록
//		}

//		if(wire.getMiddleLineTextModel()!=null)			
//		wire.getMiddleLineTextModel().setLocation(wire.getMiddleLineTextModel().getLocation());
//		if(wire.getSourceLineTextModel()!=null)
//		wire.getSourceLineTextModel().setLocation(wire.getSource().getLocation());
//		if(wire.getTargetLineTextModel()!=null)
//		wire.getTargetLineTextModel().setLocation(wire.getTarget().getLocation());
	}

	public static void setDetailPropertyPolylineDecoration(PolylineConnection conn, String prop, HashMap map, LineModel wire) {
//		if (LineModel.ID_SOURCE_ROLE.equals(prop)) {

		String value = (String)map.get(LineModel.ID_SOURCE_ROLE);
		String name = (String)map.get("ID_NAME");//PKY 08052101 S LineNAME저장안되는문제
		String stereotype = (String)map.get("ID_STEREOTYPE");//PKY 08070904 S include,extends 스트레오값나오도록

		StringBuffer sb = new StringBuffer("{");

		boolean isMul = false; 
		boolean isUnion = false;
		int index = 0;
//		LineModel wire = (LineModel)le.getModel();
		LineTextModel  e2 = wire.getSourceLineTextModel();
		LineTextModel  e3 = wire.getMiddleLineTextModel();


		e2.removeAll();
		e3.removeAll();
		String guard = (String)map.get(LineModel.ID_GUARD);
		String weight = (String)map.get(LineModel.ID_WEIGHT);

		//PKY 08052101 S LineNAME저장안되는문제
		//PKY 08070904 S include,extends 스트레오값나오도록
		if(stereotype!=null&&!stereotype.trim().equals("")){

			Label tt = new Label();

			if(wire.getMiddleLineTextModel().getMessageSize()==0){//PKY 08051401 S 라인 Name(Text)을 지우고 다시 추가 할 경우 Name(Text)가 추가 되지 않는 문제
				LineTextModel e = wire.getMiddleLineTextModel();
				MessageCommunicationModel m = new MessageCommunicationModel();
				wire.addMiddleLineTextModel(m);
				String text=(String)stereotype;
				boolean ok =false;
				if(stereotype.indexOf("<<")!=0)
					if(stereotype.lastIndexOf(">>")!=0){
						e.setText("<<"+text+">>",0);
						ok=true;
//						wire.setStereotype("<<"+text+">>");
					}
				if(ok==false){
					e.setText(text,0);
//					wire.setStereotype(text);
				}
//				e.setText("<<"+prop+">>", 0);
				m.getLabelContents().setLineModel(wire);
				m.getLabelContents().setMessageIntType(Integer.valueOf(MessageCommunicationModel.TYPE_STEREO));

				N3ConnectionLocator n3 = new N3ConnectionLocator(conn,ConnectionLocator.MIDDLE);
				n3.setModel(e3);
				//PKY 08052901 S 라인(타켓,소스) 저장되도록 수정
				if(map.get("ID_NAME_LOCATION_MIDDLE")!=null){
					String loca =(String)map.get("ID_NAME_LOCATION_MIDDLE");
					String[] data=loca.split(",");
					if(data.length==2){					
						n3.getPp().setLocation(new Point(Integer.parseInt(data[0]),Integer.parseInt(data[1])));
					}
				}
				if(map.get("ID_NAME_POINT_MIDDLE")!=null){
					String loca =(String)map.get("ID_NAME_POINT_MIDDLE");
					String[] data=loca.split(",");
					if(data.length==2){
						n3.setOldPoint((new Point(Integer.parseInt(data[0]),Integer.parseInt(data[1]))));
					}
				}
				//PKY 08052901 E 라인(타켓,소스) 저장되도록 수정
				e3.setN3ConnectionLocator(n3);
				//PKY 08052801 E LineOladPoint 추가
				tt.setIconAlignment(4);
				if(lableAdd(conn,n3))
					conn.add(tt,n3);
				index++;
			}
			else{
				boolean steroBoolean=false;
				for(int i = 0; i < wire.getMiddleLineTextModel().getMessageSize(); i++){
					MessageCommunicationModel msg =(MessageCommunicationModel) wire.getMiddleLineTextModel().getMessageCommunicationModel(i);
					if(msg.getLabelContents().getMessageIntType()==Integer.valueOf(MessageCommunicationModel.TYPE_STEREO)){
						steroBoolean=true;
						String text=(String)stereotype;
						boolean ok =false;
						if(stereotype.indexOf("<<")!=0)
							if(stereotype.lastIndexOf(">>")!=0){
								msg.setName("<<"+text+">>");
								ok=true;
//								wire.setStereotype("<<"+text+">>");
							}
						if(ok==false){
							msg.setName(text);
//							wire.setStereotype(text);
						}
//						wire.setStereotype(stereotype);
					}
				}
				if(steroBoolean==false){
					LineTextModel e = wire.getMiddleLineTextModel();
					MessageCommunicationModel m = new MessageCommunicationModel();
					wire.addMiddleLineTextModel(m);
					String text=(String)stereotype;
					boolean ok =false;
					if(stereotype.indexOf("<<")!=0)
						if(stereotype.lastIndexOf(">>")!=0){
							e.setText("<<"+text+">>",wire.getMiddleLineTextModel().getMessageSize()-1);
							ok=true;
//							wire.setStereotype("<<"+text+">>");
						}
					if(ok==false){
						e.setText(text,wire.getMiddleLineTextModel().getMessageSize()-1);
//						wire.setStereotype(text);
					}
//					e.setText("<<"+prop+">>", 0);
					m.getLabelContents().setLineModel(wire);
					m.getLabelContents().setMessageIntType(Integer.valueOf(MessageCommunicationModel.TYPE_STEREO));
				}
			}

		}
		//PKY 08070904 E include,extends 스트레오값나오도록

		if(name!=null&&!name.trim().equals("")){
			Label tt= new Label();
			MessageCommunicationModel m= new MessageCommunicationModel();
			m.setType(MessageCommunicationModel.TYPE_NAME);
			m.getLabelContents().setLineModel(wire);
			m.getLabelContents().setMessageIntType(Integer.valueOf(MessageCommunicationModel.TYPE_NAME));
//			if(map.get("ID_SIZE")!=null){
//			String ss= (String)map.get("ID_SIZE");
//			String[] value1 = ss.split(",");
//			if(value1.length==2){
//			m.setSize(new Dimension(Integer.parseInt(value1[0]),Integer.parseInt(value1[0])));

//			}
//			}
			wire.addMiddleLineTextModel(m);
			//PKY 08052101 S 텍스트 글씨내용이 많을경우 저장 불러들이기 하였을때 다시 축소되는 문제 수정
			if(map.get("ID_SIZE_MIDDLE")!=null){
				String ss= (String)map.get("ID_SIZE_MIDDLE");
				String[] value1 = ss.split(",");
				if(value1.length==2){
					((LineTextModel)wire.getMiddleLineTextModel()).setSize(new Dimension(Integer.parseInt(value1[1]),Integer.parseInt(value1[0])));

				}
			}
			//PKY 08052101 E 텍스트 글씨내용이 많을경우 저장 불러들이기 하였을때 다시 축소되는 문제 수정
			//PKY 08070904 S include,extends 스트레오값나오도록
			if(e3.getMessageSize()==0)
				e3.setText(name, 0);
			else
				e3.setText(name, e3.getMessageSize()-1);
			//PKY 08070904 E include,extends 스트레오값나오도록	
			wire.setName(name);//PKY 08060201 S
			N3ConnectionLocator n3 = new N3ConnectionLocator(conn,ConnectionLocator.MIDDLE);
			n3.setModel(e3);
			//PKY 08052901 S 라인(타켓,소스) 저장되도록 수정
			if(map.get("ID_NAME_LOCATION_MIDDLE")!=null){
				String loca =(String)map.get("ID_NAME_LOCATION_MIDDLE");
				String[] data=loca.split(",");
				if(data.length==2){					
					n3.getPp().setLocation(new Point(Integer.parseInt(data[0]),Integer.parseInt(data[1])));
				}
			}
			if(map.get("ID_NAME_POINT_MIDDLE")!=null){
				String loca =(String)map.get("ID_NAME_POINT_MIDDLE");
				String[] data=loca.split(",");
				if(data.length==2){
					n3.setOldPoint((new Point(Integer.parseInt(data[0]),Integer.parseInt(data[1]))));
				}
			}
			//PKY 08052901 E 라인(타켓,소스) 저장되도록 수정
			e3.setN3ConnectionLocator(n3);
			//PKY 08052801 E LineOladPoint 추가
			tt.setIconAlignment(4);
			if(lableAdd(conn,n3))
				conn.add(tt,n3);
			index++;
		}
		//PKY 08052101 E LineNAME저장안되는문제

		if(weight!=null && !weight.trim().equals("")){
			Label tt = new Label();
			MessageCommunicationModel m = new MessageCommunicationModel();
			m.setType(MessageCommunicationModel.TYPE_WEIGHT);
			m.getLabelContents().setLineModel(wire);
			m.getLabelContents().setMessageIntType(Integer.valueOf(MessageCommunicationModel.TYPE_WEIGHT));
			wire.addMiddleLineTextModel(m);
			e3.setText("{weight="+weight+"}", index);
			N3ConnectionLocator n3 = new N3ConnectionLocator(conn, ConnectionLocator.MIDDLE);
			n3.setModel(e3);
			tt.setIconAlignment(4);
			if(lableAdd(conn,n3))
				conn.add(tt, n3);
			index++;
		}
		if(guard!=null && !guard.trim().equals("")){

			Label tt = new Label();
			MessageCommunicationModel m = new MessageCommunicationModel();
			m.setType(MessageCommunicationModel.TYPE_GUARD);
			wire.addMiddleLineTextModel(m);
			m.getLabelContents().setLineModel(wire);
			m.getLabelContents().setMessageIntType(Integer.valueOf(MessageCommunicationModel.TYPE_GUARD));
			e3.setText("["+guard+"]", index);
			N3ConnectionLocator n3 = new N3ConnectionLocator(conn, ConnectionLocator.MIDDLE);
			n3.setModel(e3);
			tt.setIconAlignment(4);
			if(lableAdd(conn,n3))
				conn.add(tt, n3);

		}
		index = 0;

		if(value!=null && !value.trim().equals("")){
			Label tt = new Label();
			Label tt1 = new Label();
			Label tt2 = new Label();

			String derived = (String)map.get(LineModel.ID_SOURCE_DERIVED);
			if(derived!=null && derived.trim().equals("true")){
				value = "+/"+value;
			}
			else{
				value = "+"+value;
			}



			MessageCommunicationModel m = new MessageCommunicationModel();
			m.setType(MessageCommunicationModel.TYPE_DERIVED);
			m.getLabelContents().setLineModel(wire);
			m.getLabelContents().setMessageIntType(Integer.valueOf(MessageCommunicationModel.TYPE_DERIVED));
			wire.addSourceLineTextModel(m);
			if(map.get("ID_SIZE_SOURCE")!=null){
				String ss= (String)map.get("ID_SIZE_SOURCE");
				String[] value1 = ss.split(",");
				if(value1.length==2){
					((LineTextModel)wire.getSourceLineTextModel()).setSize(new Dimension(Integer.parseInt(value1[1]),Integer.parseInt(value1[0])));

				}
			}
			e2.setText(value, 0);
			N3ConnectionLocator n3 = new N3ConnectionLocator(conn, ConnectionLocator.SOURCE);
			n3.setModel(e2);
			//PKY 08052901 S 라인(타켓,소스) 저장되도록 수정
			if(map.get("ID_NAME_LOCATION_SOURCE")!=null){
				String data=(String)map.get("ID_NAME_LOCATION_SOURCE");
				String[] data2=data.split(",");
				if(data2.length==2)
					n3.getPp().setLocation(new Point(Integer.parseInt(data2[0]),Integer.parseInt(data2[1])));
			}
			if(map.get("ID_NAME_POINT_SOURCE")!=null){
				String data=(String)map.get("ID_NAME_POINT_SOURCE");
				String[] data2=data.split(",");
				if(data2.length==2)
					n3.setOldPoint(new Point(Integer.parseInt(data2[0]),Integer.parseInt(data2[1])));
			}
			//PKY 08052901 E 라인(타켓,소스) 저장되도록 수정
			tt.setIconAlignment(2);
			if(lableAdd(conn,n3))
				conn.add(tt, n3);

			index++;

		}

		value = (String)map.get(LineModel.ID_SOURCE_MUL);
		if(value!=null && !value.trim().equals("")){
			Label tt = new Label();





			MessageCommunicationModel m = new MessageCommunicationModel();
			m.getLabelContents().setLineModel(wire);
			m.getLabelContents().setMessageIntType(Integer.valueOf(MessageCommunicationModel.TYPE_MULTPLICITY));
			m.setType(MessageCommunicationModel.TYPE_MULTPLICITY);
			wire.addSourceLineTextModel(m);
			e2.setText(value, index);


			N3ConnectionLocator n3 = new N3ConnectionLocator(conn, ConnectionLocator.SOURCE);
			n3.setModel(e2);
			tt.setIconAlignment(2);
			if(lableAdd(conn,n3))
				conn.add(tt, n3);
			index++;
			isMul = true;

		}

		value = (String)map.get(LineModel.ID_SOURCE_DERIVEDUNION);
		MessageCommunicationModel derivedunionMessageCommunicationModel = null;
		if(value!=null && value.trim().equals("true")){
			Label tt = new Label();

			sb.append("union");
			derivedunionMessageCommunicationModel = new MessageCommunicationModel();
			derivedunionMessageCommunicationModel.setType(MessageCommunicationModel.TYPE_DERIVED_UNION);

			wire.addSourceLineTextModel(derivedunionMessageCommunicationModel);
			e2.setText(sb.toString()+"}", index);


			N3ConnectionLocator n3 = new N3ConnectionLocator(conn, ConnectionLocator.SOURCE);
			n3.setModel(e2);
			tt.setIconAlignment(2);
			if(lableAdd(conn,n3))
				conn.add(tt, n3);
			isUnion = true;

		}
		if(isMul && isUnion){

			value = (String)map.get(LineModel.ID_SOURCE_ORDERED);
			String value2 = (String)map.get(LineModel.ID_SOURCE_ALLOWDUPLICATES);
			if(value!=null && value.trim().equals("true")
					&& value2!=null && value2.trim().equals("true")){
				sb.append(",sequence");

			}
			else  if(value!=null && value.trim().equals("true")){
				sb.append(",ordered");
			}
			else  if(value2!=null && value2.trim().equals("true")){
				sb.append(",bag");
			}


			e2.setText(sb.toString()+"}", index);

		}
		else if(isMul){
			Label tt = new Label();
			MessageCommunicationModel m = new MessageCommunicationModel();
			m.setType(MessageCommunicationModel.TYPE_ORDERED);
			m.getLabelContents().setLineModel(wire);
			m.getLabelContents().setMessageIntType(Integer.valueOf(MessageCommunicationModel.TYPE_ORDERED));
			value = (String)map.get(LineModel.ID_SOURCE_ORDERED);
			String value2 = (String)map.get(LineModel.ID_SOURCE_ALLOWDUPLICATES);
			if(value!=null && value.trim().equals("true")
					&& value2!=null && value2.trim().equals("true")){
				sb.append("sequence");
				wire.addSourceLineTextModel(m);
				e2.setText(sb.toString()+"}", index);

			}
			else  if(value!=null && value.trim().equals("true")){
				sb.append("ordered");
				wire.addSourceLineTextModel(m);
				e2.setText(sb.toString()+"}", index);
			}
			else  if(value2!=null && value2.trim().equals("true")){
				sb.append("bag");
				wire.addSourceLineTextModel(m);
				e2.setText(sb.toString()+"}", index);
			}





			N3ConnectionLocator n3 = new N3ConnectionLocator(conn, ConnectionLocator.SOURCE);
			n3.setModel(e2);
			tt.setIconAlignment(2);
			if(lableAdd(conn,n3))
				conn.add(tt, n3); 
		}

		value = (String)map.get(LineModel.ID_SOURCE_AGGREGATION);
		String value2 = (String)map.get(LineModel.ID_SOURCE_NAVIGABILITY);
		PolygonDecoration polyLine = new PolygonDecoration();
		if(value!=null && value.trim().equals("Aggregate")){

			PointList decorationPointList = new PointList();
			decorationPointList.addPoint(0, 0);
			decorationPointList.addPoint(-2, 2);
			decorationPointList.addPoint(-4, 0);
			decorationPointList.addPoint(-2, -2);
			polyLine.setTemplate(decorationPointList);
			//			polyLine.setScale(15,5);
			polyLine.setBackgroundColor(ColorConstants.tooltipBackground);
			conn.setSourceDecoration(polyLine);


		}
		else  if(value!=null && value.trim().equals("Composite")){
			PointList decorationPointList = new PointList();
			decorationPointList.addPoint(0, 0);
			decorationPointList.addPoint(-2, 2);
			decorationPointList.addPoint(-4, 0);
			decorationPointList.addPoint(-2, -2);
			polyLine.setTemplate(decorationPointList);
			//			polyLine.setScale(15,5);
			polyLine.setBackgroundColor(ColorConstants.black);
			conn.setSourceDecoration(polyLine);
		}
		else if(value2!=null && value2.trim().equals("Navigable")){
			PolylineDecoration polyLine2 = new PolylineDecoration();
			polyLine2.setTemplate(polyLine2.TRIANGLE_TIP);

			conn.setSourceDecoration(polyLine2);

//			else {
//			conn.setTargetDecoration(polyLine2);
//			}

		}
		else if(value2!=null && value2.trim().equals("Navigable")){
			PolylineDecoration polyLine2 = new PolylineDecoration();
			polyLine2.setTemplate(polyLine2.TRIANGLE_TIP);

			conn.setSourceDecoration(polyLine2);

//			else {
//			conn.setTargetDecoration(polyLine2);
//			}

		}
		else{
			PointList pl = new PointList();
			polyLine.setTemplate(pl);
			polyLine.setBackgroundColor(ColorConstants.tooltipBackground);
			conn.setSourceDecoration(polyLine);
		}


		value = (String)map.get(LineModel.ID_TARGET_ROLE);
		sb = new StringBuffer("{");

		isMul = false; 
		isUnion = false;
		index = 0;

		e2 = wire.getTargetLineTextModel();
		e2.removeAll();

		if(value!=null && !value.trim().equals("")){
			Label tt = new Label();
			Label tt1 = new Label();
			Label tt2 = new Label();

			String derived = (String)map.get(LineModel.ID_TARGET_DERIVED);
			if(derived!=null && derived.trim().equals("true")){
				value = "+/"+value;
			}
			else{
				value = "+"+value;
			}


			e2 = wire.getTargetLineTextModel();
			MessageCommunicationModel m = new MessageCommunicationModel();
			m.setType(MessageCommunicationModel.TYPE_DERIVED);
			m.getLabelContents().setLineModel(wire);
			m.getLabelContents().setMessageIntType(Integer.valueOf(MessageCommunicationModel.TARGET_TYPE_DERIVED));
			m.setName(value);

			wire.addTargetLineTextModel(m);
//			e2.setText(value, 0);

			N3ConnectionLocator n3 = new N3ConnectionLocator(conn, ConnectionLocator.TARGET);
			if(map.get("ID_SIZE_TARGET")!=null){
				String ss= (String)map.get("ID_SIZE_TARGET");
				String[] value1 = ss.split(",");
				if(value1.length==2){
					((LineTextModel)wire.getTargetLineTextModel()).setSize(new Dimension(Integer.parseInt(value1[1]),Integer.parseInt(value1[0])));
				}
			}
			n3.setModel(e2);
			//PKY 08052901 S 라인(타켓,소스) 저장되도록 수정
			if(map.get("ID_NAME_LOCATION_TARGET")!=null){
				String loca =(String)map.get("ID_NAME_LOCATION_TARGET");
				String[] data=loca.split(",");
				if(data.length==2){					
					n3.getPp().setLocation(new Point(Integer.parseInt(data[0]),Integer.parseInt(data[1])));
				}
			}
			if(map.get("ID_NAME_POINT_TARGET")!=null){
				String loca =(String)map.get("ID_NAME_POINT_TARGET");
				String[] data=loca.split(",");
				if(data.length==2){
					n3.setOldPoint((new Point(Integer.parseInt(data[0]),Integer.parseInt(data[1]))));
				}
			}
			e2.setN3ConnectionLocator(n3);
			//PKY 08052901 E 라인(타켓,소스) 저장되도록 수정
			tt.setIconAlignment(3);
			if(lableAdd(conn,n3))
				conn.add(tt, n3);

			index++;

		}

		value = (String)map.get(LineModel.ID_TARGET_MUL);
		if(value!=null && !value.trim().equals("")){
			Label tt = new Label();





			MessageCommunicationModel m = new MessageCommunicationModel();
			wire.addTargetLineTextModel(m);
			m.setType(MessageCommunicationModel.TYPE_MULTPLICITY);
			m.getLabelContents().setLineModel(wire);
			m.getLabelContents().setMessageIntType(Integer.valueOf(MessageCommunicationModel.TARGET_TYPE_MULTPLICITY));
			m.setName(value);


			N3ConnectionLocator n3 = new N3ConnectionLocator(conn, ConnectionLocator.TARGET);
			n3.setModel(e2);
			tt.setIconAlignment(3);
			if(lableAdd(conn,n3))
				conn.add(tt, n3);
			index++;
			isMul = true;

		}

		value = (String)map.get(LineModel.ID_TARGET_DERIVEDUNION);
		derivedunionMessageCommunicationModel = null;
		if(value!=null && value.trim().equals("true")){
			Label tt = new Label();

			sb.append("union");
			derivedunionMessageCommunicationModel = new MessageCommunicationModel();
			derivedunionMessageCommunicationModel.setType(MessageCommunicationModel.TYPE_DERIVED_UNION);
			derivedunionMessageCommunicationModel.getLabelContents().setLineModel(wire);
			derivedunionMessageCommunicationModel.getLabelContents().setMessageIntType(Integer.valueOf(MessageCommunicationModel.TARGET_TYPE_DERIVED_UNION));


			wire.addTargetLineTextModel(derivedunionMessageCommunicationModel);
			derivedunionMessageCommunicationModel.setName(sb.toString()+"}");


			N3ConnectionLocator n3 = new N3ConnectionLocator(conn, ConnectionLocator.TARGET);
			n3.setModel(e2);
			tt.setIconAlignment(3);
			if(lableAdd(conn,n3))
				conn.add(tt, n3);
			isUnion = true;

		}
		if(isMul && isUnion){

			value = (String)map.get(LineModel.ID_TARGET_ORDERED);
			value2 = (String)map.get(LineModel.ID_TARGET_ALLOWDUPLICATES);
			if(value!=null && value.trim().equals("true")
					&& value2!=null && value2.trim().equals("true")){
				sb.append(",sequence");

			}
			else  if(value!=null && value.trim().equals("true")){
				sb.append(",ordered");
			}
			else  if(value2!=null && value2.trim().equals("true")){
				sb.append(",bag");
			}


			derivedunionMessageCommunicationModel.setName(sb.toString()+"}");

		}
		else if(isMul){
			Label tt = new Label();
			MessageCommunicationModel m = new MessageCommunicationModel();
			m.setType(MessageCommunicationModel.TYPE_ORDERED);
			m.getLabelContents().setLineModel(wire);
			m.getLabelContents().setMessageIntType(Integer.valueOf(MessageCommunicationModel.TARGET_TYPE_ORDERED));
			value = (String)map.get(LineModel.ID_TARGET_ORDERED);
			value2 = (String)map.get(LineModel.ID_TARGET_ALLOWDUPLICATES);
			if(value!=null && value.trim().equals("true")
					&& value2!=null && value2.trim().equals("true")){
				sb.append("sequence");
				wire.addTargetLineTextModel(m);
//				e2.setText(sb.toString()+"}", index);
				m.setName(sb.toString()+"}");

			}
			else  if(value!=null && value.trim().equals("true")){
				sb.append("ordered");
				wire.addTargetLineTextModel(m);
				m.setName(sb.toString()+"}");
			}
			else  if(value2!=null && value2.trim().equals("true")){
				sb.append("bag");
				wire.addTargetLineTextModel(m);
				m.setName(sb.toString()+"}");
			}





			N3ConnectionLocator n3 = new N3ConnectionLocator(conn, ConnectionLocator.TARGET);
			n3.setModel(e2);
			tt.setIconAlignment(3);
			if(lableAdd(conn,n3))
				conn.add(tt, n3); 
		}

		value = (String)map.get(LineModel.ID_TARGET_AGGREGATION);
		value2 = (String)map.get(LineModel.ID_TARGET_NAVIGABILITY);
		polyLine = new PolygonDecoration();
		if(value!=null && value.trim().equals("Aggregate")){

			PointList decorationPointList = new PointList();
			decorationPointList.addPoint(0, 0);
			decorationPointList.addPoint(-2, 2);
			decorationPointList.addPoint(-4, 0);
			decorationPointList.addPoint(-2, -2);
			polyLine.setTemplate(decorationPointList);
			//			polyLine.setScale(15,5);
			polyLine.setBackgroundColor(ColorConstants.tooltipBackground);
			conn.setTargetDecoration(polyLine);


		}
		else  if(value!=null && value.trim().equals("Composite")){
			PointList decorationPointList = new PointList();
			decorationPointList.addPoint(0, 0);
			decorationPointList.addPoint(-2, 2);
			decorationPointList.addPoint(-4, 0);
			decorationPointList.addPoint(-2, -2);
			polyLine.setTemplate(decorationPointList);
			//			polyLine.setScale(15,5);
			polyLine.setBackgroundColor(ColorConstants.black);
			conn.setTargetDecoration(polyLine);
		}
		else if(value2!=null && value2.trim().equals("Navigable")){
			PolylineDecoration polyLine2 = new PolylineDecoration();
			polyLine2.setTemplate(polyLine2.TRIANGLE_TIP);

			conn.setTargetDecoration(polyLine2);

//			else {
//			conn.setTargetDecoration(polyLine2);
//			}

		}
		else{
			PointList pl = new PointList();
			polyLine.setTemplate(pl);
			polyLine.setBackgroundColor(ColorConstants.tooltipBackground);
			conn.setTargetDecoration(polyLine);
		}





//		}
	}
	//PKY 08070904 S include,extends 스트레오값나오도록

	public static void setStereotypePropertyPolylineDecoration(PolylineConnection conn, String prop, String stereotype, LineEditPart le) {
		if (LineModel.ID_STEREOTYPE.equals(prop)) {
			if(stereotype!=null && !stereotype.trim().equals("")){
				LineModel wire = (LineModel)le.getModel();

				LineTextModel  e3 = wire.getMiddleLineTextModel();//PKY 08081801 S 스트레오타입 떨어져 생기는 문제 수정

				Label tt = new Label();

				if(wire.getMiddleLineTextModel().getMessageSize()==0){//PKY 08051401 S 라인 Name(Text)을 지우고 다시 추가 할 경우 Name(Text)가 추가 되지 않는 문제
					LineTextModel e = wire.getMiddleLineTextModel();
					MessageCommunicationModel m = new MessageCommunicationModel();
					wire.addMiddleLineTextModel(m);
					String text=(String)stereotype;
					boolean ok =false;
					if(stereotype.indexOf("<<")!=0)
						if(stereotype.lastIndexOf(">>")!=0){
							e.setText("<<"+text+">>",0);
							wire.getDetailProp().put(LineModel.ID_STEREOTYPE, "<<"+text+">>");//PKY 08072401 S 다이어그램에서 스트레오변경시 저장 불러오기 할때 변경안되는문제 수정
							ok=true;

						}
					if(ok==false){
						e.setText(text,0);
						wire.getDetailProp().put(LineModel.ID_STEREOTYPE, text);//PKY 08072401 S 다이어그램에서 스트레오변경시 저장 불러오기 할때 변경안되는문제 수정
					}
					m.getLabelContents().setLineModel(wire);
					m.getLabelContents().setMessageIntType(Integer.valueOf(MessageCommunicationModel.TYPE_STEREO));
					N3ConnectionLocator n3 = new N3ConnectionLocator(conn, ConnectionLocator.MIDDLE);
					((LineTextModel)e3).setN3ConnectionLocator(n3);//PKY 08052801 S LineOladPoint 추가
					n3.setModel(e3);
					tt.setIconAlignment(4);
					if(lableAdd(conn,n3))
						conn.add(tt, n3);
				}
				else{
					boolean steroBoolean=false;
					for(int i = 0; i < wire.getMiddleLineTextModel().getMessageSize(); i++){
						MessageCommunicationModel msg =(MessageCommunicationModel) wire.getMiddleLineTextModel().getMessageCommunicationModel(i);
						if(msg.getLabelContents().getMessageIntType()==Integer.valueOf(MessageCommunicationModel.TYPE_STEREO)){
							steroBoolean=true;
							String text=(String)stereotype;
							boolean ok =false;
							if(stereotype.indexOf("<<")!=0)
								if(stereotype.lastIndexOf(">>")!=0){
									msg.setName("<<"+text+">>");
									ok=true;
									wire.getDetailProp().put(LineModel.ID_STEREOTYPE, "<<"+text+">>");//PKY 08072401 S 다이어그램에서 스트레오변경시 저장 불러오기 할때 변경안되는문제 수정
//									wire.setStereotype("<<"+text+">>");
								}
							if(ok==false){
								msg.setName(text);
								wire.getDetailProp().put(LineModel.ID_STEREOTYPE, text);//PKY 08072401 S 다이어그램에서 스트레오변경시 저장 불러오기 할때 변경안되는문제 수정
//								wire.setStereotype(text);
							}
//							wire.setStereotype(stereotype);
						}
					}
					if(steroBoolean==false){
						LineTextModel e = wire.getMiddleLineTextModel();
						MessageCommunicationModel m = new MessageCommunicationModel();
						wire.addMiddleLineTextModel(m);
						String text=(String)stereotype;
						boolean ok =false;
						if(stereotype.indexOf("<<")!=0)
							if(stereotype.lastIndexOf(">>")!=0){
								e.setText("<<"+text+">>",wire.getMiddleLineTextModel().getMessageSize()-1);
								wire.getDetailProp().put(LineModel.ID_STEREOTYPE, "<<"+text+">>");//PKY 08072401 S 다이어그램에서 스트레오변경시 저장 불러오기 할때 변경안되는문제 수정
								ok=true;
//								wire.setStereotype("<<"+text+">>");
							}
						if(ok==false){
							e.setText(text,wire.getMiddleLineTextModel().getMessageSize()-1);
							wire.getDetailProp().put(LineModel.ID_STEREOTYPE, text);//PKY 08072401 S 다이어그램에서 스트레오변경시 저장 불러오기 할때 변경안되는문제 수정
//							wire.setStereotype(text);
						}
//						e.setText("<<"+prop+">>", 0);
						m.getLabelContents().setLineModel(wire);
						m.getLabelContents().setMessageIntType(Integer.valueOf(MessageCommunicationModel.TYPE_STEREO));
						wire.getDetailProp().remove(LineModel.ID_STEREOTYPE);//PKY 08072401 S 다이어그램에서 스트레오변경시 저장 불러오기 할때 변경안되는문제 수정
					}
				}
			}else{
				LineModel wire = (LineModel)le.getModel();

				LineTextModel  e3 = wire.getMiddleLineTextModel();
				for(int i = 0; i < wire.getMiddleLineTextModel().getMessageSize(); i++){
					MessageCommunicationModel msg =(MessageCommunicationModel) wire.getMiddleLineTextModel().getMessageCommunicationModel(i);
					if(msg.getLabelContents().getMessageIntType()==Integer.valueOf(MessageCommunicationModel.TYPE_STEREO)){
						e3.removeModel(e3.getMessageCommunicationModel(i));//PKY 08072401 S 스트레오타입 지워지지않는문제 수정
					}
				}
				//PKY 08082201 S RoleName 삭제시 NullPoint 메시지 뜨는문제
				 if(wire.getMiddleLineTextModel().getMessageSize()==0){
					 wire.getMiddleLineTextModel().setSize(new Dimension(0,0));
				 }
				//PKY 08082201 E RoleName 삭제시 NullPoint 메시지 뜨는문제

			}
		}
	}
	//PKY 08070904 E include,extends 스트레오값나오도록

	public static void setNamePropertyPolylineDecoration(PolylineConnection conn, String prop, String name, LineEditPart le) {

		if (LineModel.ID_NAME.equals(prop)) {
			if(name!=null && !name.trim().equals("")){
				if (le instanceof ControlFlowLineEditPart) {
					LineModel wire = (LineModel)le.getModel();

					LineTextModel  e3 = wire.getTargetLineTextModel();

					Label tt = new Label();

					if(wire.getTargetLineTextModel().getMessageSize()==0){//PKY 08051401 S 라인 Name(Text)을 지우고 다시 추가 할 경우 Name(Text)가 추가 되지 않는 문제
						MessageCommunicationModel m = new MessageCommunicationModel();
						m.setType(MessageCommunicationModel.TYPE_NAME);
						m.getLabelContents().setLineModel(wire);
//						V1.02 WJH E 080828 S 중앙으로 가도록 변경
						wire.addTargetLineTextModel(m);
//						wire.addMiddleLineTextModel(m);
//						V1.02 WJH E 080828 E 중앙으로 가도록 변경
						m.setName(name);
						e3.setText(name, e3.getMessageSize()-1);
//						e3.setLocation(new Point(e3.getLocation().x,e3.getLocation().y));
					}
//					e3.setText(name, e3.getMessageSize()-1);
//					e3.setNameIndex(e3.getMessageSize()-1);
					N3ConnectionLocator n3 = new N3ConnectionLocator(conn, ConnectionLocator.TARGET);
					((LineTextModel)e3).setN3ConnectionLocator(n3);//PKY 08052801 S LineOladPoint 추가
					//PKY 08070101 S 이름 변경 후 재 변경할경우 안됨
					for(int i=0; i<wire.getTargetLineTextModel().getMessageSize();i++){
						MessageCommunicationModel m1=wire.getTargetLineTextModel().getMessageCommunicationModel(i);
						if(m1.getType()==MessageCommunicationModel.TYPE_NAME){
//							wire.addMiddleLineTextModel(m1);		// V1.02 WJH E 080828 S 중앙으로 가도록 추가
							m1.setName(name);
							e3.setText(name, i);
//							e3.setLocation(new Point(e3.getLocation().x,e3.getLocation().y));
						}
					}
					//PKY 08070101 E 이름 변경 후 재 변경할경우 안됨
					n3.setModel(e3);
					tt.setIconAlignment(3);
					if(lableAdd(conn,n3))
						conn.add(tt, n3);
					//PKY 08061101 S marge이름 생성 하게되면 <<marge>>하위에 생기도록 수정
				}else if(le instanceof MergeLineEditPart||le instanceof ImportLineEditPart||
						le instanceof ManifestLineEditPart){ //PKY 08061801 S manifest 저장 불러오기 할 경우 이름 표시안되는문제 
					LineModel wire = (LineModel)le.getModel();

					LineTextModel  e3 = wire.getMiddleLineTextModel();

					Label tt = new Label();
					MessageCommunicationModel m =null; //PKY 08051401 S 라인 name변경하면 이름이 계속 하단에 추가되어 들어가는문제
					if(wire.getMiddleLineTextModel().getMessageSize()==1){//PKY 08051401 S 라인 Name(Text)을 지우고 다시 추가 할 경우 Name(Text)가 추가 되지 않는 문제
						m = new MessageCommunicationModel();
						wire.addMiddleLineTextModel(m);
						wire.getMiddleLineTextModel().setName(true);
						m.setType(MessageCommunicationModel.TYPE_NAME);
						m.setMessageIntType(Integer.valueOf(MessageCommunicationModel.TYPE_NAME));
						m.getLabelContents().setLineModel(wire);
					}
					//PKY 08061801 S 저장 불러오기할경우 이름을 변경하면 아래생기는문제 수정
					if(wire.getMiddleLineTextModel().getMessageSize()==3){
						if(wire.getMiddleLineTextModel().getMessageCommunicationModel(2).getName().trim().equals("")){
							wire.getMiddleLineTextModel().removeModel(wire.getMiddleLineTextModel().getMessageCommunicationModel(2));
						}
					}
					//PKY 08061801 E 저장 불러오기할경우 이름을 변경하면 아래생기는문제 수정
					//PKY 08051401 E 라인 name변경하면 이름이 계속 하단에 추가되어 들어가는문제
					e3.setText(name, e3.getMessageSize()-1);
//					e3.setLocation(new Point(e3.getLocation().x,e3.getLocation().y));
					e3.setNameIndex(e3.getMessageSize()-1);
					N3ConnectionLocator n3 = new N3ConnectionLocator(conn, ConnectionLocator.MIDDLE);
					((LineTextModel)e3).setN3ConnectionLocator(n3);//PKY 08052801 S LineOladPoint 추가
					n3.setModel(e3);
					tt.setIconAlignment(4);
					if(lableAdd(conn,n3))
						conn.add(tt, n3);
				}
				//PKY 08061101 E marge이름 생성 하게되면 <<marge>>하위에 생기도록 수정
				else{
					//PKY 08070101 S 이름 변경 후 재 변경할경우 안됨
					LineModel wire = (LineModel)le.getModel();

					LineTextModel  e3 = wire.getMiddleLineTextModel();

					Label tt = new Label();
//					wire.getMiddleLineTextModel().setLocation(new Point(10,10)); //PKY 08070101 S 라인텍스트가 저장불러드리고 나면 위치가 변경되는문제
					MessageCommunicationModel m =null; //PKY 08051401 S 라인 name변경하면 이름이 계속 하단에 추가되어 들어가는문제
					if(wire.getMiddleLineTextModel().getMessageSize()==0){//PKY 08051401 S 라인 Name(Text)을 지우고 다시 추가 할 경우 Name(Text)가 추가 되지 않는 문제
						m = new MessageCommunicationModel();
						wire.addMiddleLineTextModel(m);
						wire.getMiddleLineTextModel().setName(true);
						m.setType(MessageCommunicationModel.TYPE_NAME);
						m.setMessageIntType(Integer.valueOf(MessageCommunicationModel.TYPE_NAME));
						m.getLabelContents().setLineModel(wire);
						e3.setText(name, e3.getMessageSize()-1);
						wire.getDetailProp().put(LineModel.ID_NAME, name);//PKY 08070101 S 오퍼레이션 다이얼 로그에서 파라미터 삭제안되는 문제
						e3.setNameIndex(e3.getMessageSize()-1);
						N3ConnectionLocator n3 = new N3ConnectionLocator(conn, ConnectionLocator.MIDDLE);
						((LineTextModel)e3).setN3ConnectionLocator(n3);//PKY 08052801 S LineOladPoint 추가
						n3.setModel(e3);
						tt.setIconAlignment(4);
						if(lableAdd(conn,n3))
							conn.add(tt, n3);

//						m.getLabelContents().addUpdateLineListener((IUMLModelUpdateListener)wire);
					}else{
						boolean nameBoolean=false;//PKY 08070904 S include,extends 스트레오값나오도록
						for(int i=0; i<wire.getMiddleLineTextModel().getMessageSize();i++){
							MessageCommunicationModel m1=wire.getMiddleLineTextModel().getMessageCommunicationModel(i);
							if(m1.getType()==MessageCommunicationModel.TYPE_NAME){
								nameBoolean=true;//PKY 08070904 S include,extends 스트레오값나오도록
								m1.setName(name);
								e3.setText(name, i);
								wire.getDetailProp().put(LineModel.ID_NAME, name);//PKY 08070101 S 오퍼레이션 다이얼 로그에서 파라미터 삭제안되는 문제
								e3.setLocation(new Point(e3.getLocation().x,e3.getLocation().y));
							}
						}
						//PKY 08070904 S include,extends 스트레오값나오도록
						if(nameBoolean ==false){
							m = new MessageCommunicationModel();
							wire.addMiddleLineTextModel(m);
							wire.getMiddleLineTextModel().setName(true);
							m.setType(MessageCommunicationModel.TYPE_NAME);
							m.setMessageIntType(Integer.valueOf(MessageCommunicationModel.TYPE_NAME));
							m.getLabelContents().setLineModel(wire);
							e3.setText(name, e3.getMessageSize()-1);
							wire.getDetailProp().put(LineModel.ID_NAME, name);//PKY 08070101 S 오퍼레이션 다이얼 로그에서 파라미터 삭제안되는 문제
							e3.setNameIndex(e3.getMessageSize()-1);
							N3ConnectionLocator n3 = new N3ConnectionLocator(conn, ConnectionLocator.MIDDLE);
							((LineTextModel)e3).setN3ConnectionLocator(n3);//PKY 08052801 S LineOladPoint 추가
							n3.setModel(e3);
							tt.setIconAlignment(4);
							if(lableAdd(conn,n3))
								conn.add(tt, n3);

							//20080729IJS 끝
						}
						//PKY 08070904 E include,extends 스트레오값나오도록

					}
					//PKY 08070101 E 이름 변경 후 재 변경할경우 안됨

					//PKY 08051401 E 라인 name변경하면 이름이 계속 하단에 추가되어 들어가는문제
					Point point =new Point();
					point.x=wire.getMiddleLineTextModel().getLocation().x;
					point.y=wire.getMiddleLineTextModel().getLocation().y;


				}
			}
			else{
				if (le instanceof ControlFlowLineEditPart) {
					LineModel wire = (LineModel)le.getModel();

					LineTextModel  e3 = wire.getTargetLineTextModel();

					e3.removeModel(e3.getMessageCommunicationModel(e3.getNameIndex()));
					//PKY 08071601 S 라인 스트레오타입 복사 할 경우 복사안되는문제
					if(wire.getDetailProp().get(LineModel.ID_NAME)!=null)
						wire.getDetailProp().remove(LineModel.ID_NAME);
					//PKY 08071601 E 라인 스트레오타입 복사 할 경우 복사안되는문제
					//PKY 08082201 S RoleName 삭제시 NullPoint 메시지 뜨는문제
					 if(wire.getTargetLineTextModel().getMessageSize()==0){
						 wire.getTargetLineTextModel().setSize(new Dimension(0,0));
					 }
					//PKY 08082201 E RoleName 삭제시 NullPoint 메시지 뜨는문제
				}
				else{
					//PKY 08072401 S 스트레오타입 지워지지않는문제 수정
					LineModel wire = (LineModel)le.getModel();

					LineTextModel  e3 = wire.getMiddleLineTextModel();
					for(int i = 0; i < wire.getMiddleLineTextModel().getMessageSize(); i++){
						MessageCommunicationModel msg =(MessageCommunicationModel) wire.getMiddleLineTextModel().getMessageCommunicationModel(i);
						if(msg.getLabelContents().getMessageIntType()==Integer.valueOf(MessageCommunicationModel.TYPE_NAME)){
							e3.removeModel(e3.getMessageCommunicationModel(i));
						}
					}
					//PKY 08072401 E 스트레오타입 지워지지않는문제 수정
					//PKY 08082201 S RoleName 삭제시 NullPoint 메시지 뜨는문제
					 if(wire.getMiddleLineTextModel().getMessageSize()==0){
						 wire.getMiddleLineTextModel().setSize(new Dimension(0,0));
					 }
					//PKY 08082201 E RoleName 삭제시 NullPoint 메시지 뜨는문제
				}

			}


		}
	}

	public static void setPropertyPolylineDecoration(PolylineConnection conn, String prop, HashMap value, LineEditPart le) {
		try {
			if (LineModel.ID_NAME.equals(prop)) {
				if (le instanceof ControlFlowLineEditPart) {
					if (conn instanceof N3PolylineConnection) {
						N3PolylineConnection nconn = (N3PolylineConnection)conn;
						String name = (String)value.get(prop);
						nconn.setTarget("", name);
					}
				}
			}
			else if (LineModel.CONSTRAINTS_ROLE.equals(prop)) {
				if (le instanceof ControlFlowLineEditPart) {
					//				UMLElementFigure uMLElementFigure = new UMLElementFigure();
					//				UMLElementFigure uMLElementFigure2 = new UMLElementFigure();
					ConstraintsModel constraintsModel = (ConstraintsModel)value.get(prop);
					StringBuffer sb = new StringBuffer();
					//				conn.remove(figure)
					String top = "";
					String bottom = "";
					if (constraintsModel.getWeight() != null && !constraintsModel.getWeight().trim().equals("")) {
						top = "{weight=" + constraintsModel.getWeight() + "}\r";
						//					conn.add(uMLElementFigure,new ConnectionLocator(conn,ConnectionLocator.MIDDLE));
					}
					if (constraintsModel.getGuard() != null && !constraintsModel.getGuard().trim().equals("")) {
						bottom = "{" + constraintsModel.getGuard() + "}\r";
					}
					//				uMLElementFigure.setText(sb.toString());
					if (conn instanceof N3PolylineConnection) {
						N3PolylineConnection nconn = (N3PolylineConnection)conn;
						nconn.setMiddle(top, bottom);
					}
					//				conn.add(uMLElementFigure,new ConnectionLocator(conn,ConnectionLocator.MIDDLE));
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void setMessageLine(boolean isSynchronous,boolean isCall,boolean isReturn,boolean isNew, PolylineConnection conn) {






		if(isReturn || isNew){
			PolylineDecoration polyLine = new PolylineDecoration();
			polyLine.setTemplate(polyLine.TRIANGLE_TIP);
			conn.setLineStyle(Graphics.LINE_DOT);
			conn.setTargetDecoration(polyLine);
		}
		else{
			if(isSynchronous ){
				PolygonDecoration polyLine = new PolygonDecoration();
				polyLine.setTemplate(polyLine.TRIANGLE_TIP);
				polyLine.setBackgroundColor(ColorConstants.tooltipBackground);
				polyLine.setScale(10, 10);
				conn.setLineStyle(Graphics.LINE_CUSTOM);
				conn.setTargetDecoration(polyLine);

			}
			else{
				PolylineDecoration polyLine = new PolylineDecoration();
				polyLine.setTemplate(polyLine.TRIANGLE_TIP);
				polyLine.setScale(10, 10);
				conn.setLineStyle(Graphics.LINE_CUSTOM);
				conn.setTargetDecoration(polyLine);
			}

		}
	}

	public static void setPolylineDecoration(boolean isSource, RoleModel p, PolylineConnection conn) {
		PolygonDecoration polyLine = new PolygonDecoration();
		if (p.getAggregation().intValue() == 0) {
			PointList pl = new PointList();
			polyLine.setTemplate(pl);
			polyLine.setBackgroundColor(ColorConstants.tooltipBackground);
		}
		else if (p.getAggregation().intValue() == 1) {
			PolylineDecoration polyLine2 = new PolylineDecoration();
			polyLine2.setTemplate(polyLine2.TRIANGLE_TIP);
			if (isSource) {
				conn.setSourceDecoration(polyLine2);
			}
			else {
				conn.setTargetDecoration(polyLine2);
			}
			return;
		}
		else if (p.getAggregation().intValue() == 2) {
			PointList decorationPointList = new PointList();
			decorationPointList.addPoint(0, 0);
			decorationPointList.addPoint(-2, 2);
			decorationPointList.addPoint(-4, 0);
			decorationPointList.addPoint(-2, -2);
			polyLine.setTemplate(decorationPointList);
			//			polyLine.setScale(15,5);
			polyLine.setBackgroundColor(ColorConstants.tooltipBackground);
		}
		else if (p.getAggregation().intValue() == 3) {
			PointList decorationPointList = new PointList();
			decorationPointList.addPoint(0, 0);
			decorationPointList.addPoint(-2, 2);
			decorationPointList.addPoint(-4, 0);
			decorationPointList.addPoint(-2, -2);
			polyLine.setTemplate(decorationPointList);
			//			polyLine.setScale(15,5);
			polyLine.setBackgroundColor(ColorConstants.black);
		}
		if (isSource) {
			conn.setSourceDecoration(polyLine);
		}
		else {
			conn.setTargetDecoration(polyLine);
		}
	}

	public static PolylineConnection createProvidedInterfaceLineModelFigure(LineModel wire) {
		PolylineConnection conn = new PolylineConnection();
		conn.addRoutingListener(RoutingAnimator.getDefault());
		conn.setForegroundColor(ColorConstants.blue);
		//PKY 08081101 S Provieded Interfacef 스트레오,이름 저장 불러올 경우 표시못함
		if(wire.isLoad()){
			UMLFigureFactory.setDetailPropertyPolylineDecoration(conn, null, wire.getDetailProp(), wire);
		}
		wire.setLoad(false);
		//PKY 08081101 E Provieded Interfacef 스트레오,이름 저장 불러올 경우 표시못함

		return conn;
	}

	public static PolylineConnection CommunicationPathLineFigure(LineModel wire) {
		PolylineConnection conn = new PolylineConnection();
		UMLElementFigure uMLElementFigure = new UMLElementFigure();
		Label tt = new Label();
		Label ttTarget = new Label();
		Label ttSource = new Label();
		UMLEditor dd = ProjectManager.getInstance().getUMLEditor();
		//msg
		LineTextModel e = wire.getMiddleLineTextModel();
		LineTextModel e1 = wire.getTargetLineTextModel();
		LineTextModel e2 = wire.getSourceLineTextModel();
		e1.setText("1", 0);
//		e2.setText("1", 0);
//		N3ConnectionLocator n3 = new N3ConnectionLocator(conn, ConnectionLocator.MIDDLE);
//		N3ConnectionLocator n3Target = new N3ConnectionLocator(conn, ConnectionLocator.TARGET);
//		N3ConnectionLocator n3Source = new N3ConnectionLocator(conn, ConnectionLocator.SOURCE);
//		n3.setModel(e);
//		n3Target.setModel(e1);
//		n3Source.setModel(e2);
//
//		tt.setIconAlignment(4);
//		if(lableAdd(conn,n3))
//			conn.add(tt, n3);
//
//		tt.setIconAlignment(3);
//		if(lableAdd(conn,n3Target))
//			conn.add(ttTarget, n3Target);
//
//		tt.setIconAlignment(2);
//		if(lableAdd(conn,n3Source))
//			conn.add(ttSource, n3Source);

		//PKY 08081101 S CommunicationPath 저장불러올 경우 스트레오,이름  표시못함
		if(wire.isLoad()){
			UMLFigureFactory.setDetailPropertyPolylineDecoration(conn, null, wire.getDetailProp(), wire);
		}
		wire.setLoad(false);
		//PKY 08081101 E CommunicationPath 저장불러올 경우 스트레오,이름  표시못함

		conn.addRoutingListener(RoutingAnimator.getDefault());
		return conn;
	}

	public static PolylineConnection createAssociateLineFigure(LineModel wire) {
		PolylineConnection conn = new PolylineConnection();
		conn.addRoutingListener(RoutingAnimator.getDefault());
		//PKY 08052101 S LineNAME저장안되는문제

		if(wire.isLoad()){
			UMLFigureFactory.setDetailPropertyPolylineDecoration(conn, null, wire.getDetailProp(), wire);
		}
		wire.setLoad(false);
		//PKY 08052101 E LineNAME저장안되는문제

//		conn.setConnectionRouter(new ManhattanConnectionRouter());
		return conn;
	}

	public static PolylineConnection createMessageLineFigure(LineModel wire) {
		PolylineConnection conn = new PolylineConnection();
		


		return conn;
	}

	public static PolylineConnection createDelegateLineFigure(LineModel wire) {
		PolylineConnection conn = new PolylineConnection();
		conn.addRoutingListener(RoutingAnimator.getDefault());
		//PKY 08052901 S 일부 라인 Name안나오는문제

		if(wire.isLoad()){
			UMLFigureFactory.setDetailPropertyPolylineDecoration(conn, null, wire.getDetailProp(), wire);
		}
		wire.setLoad(false);
		//PKY 08052901 E 일부 라인 Name안나오는문제
		UMLElementFigure uMLElementFigure = new UMLElementFigure();

		conn.add(uMLElementFigure, new ConnectionLocator(conn, ConnectionLocator.MIDDLE));
		PolylineDecoration polyLine = new PolylineDecoration();
		polyLine.setTemplate(polyLine.TRIANGLE_TIP);
		conn.setTargetDecoration(polyLine);
		wire.getDetailProp().put(LineModel.ID_TARGET_NAVIGABILITY, "Navigable");//PKY 08090801 S Component>Delegate 속성을 넣게되면 화살표가 사라지는문제

		return conn;
	}

	public static PolylineConnection createGeneralizeLineFigure(GeneralizeLineModel wire) {
		PolylineConnection conn = new PolylineConnection();
		conn.addRoutingListener(RoutingAnimator.getDefault());
		//PKY 08052901 S 일부 라인 Name안나오는문제

		if(wire.isLoad()){
			UMLFigureFactory.setDetailPropertyPolylineDecoration(conn, null, wire.getDetailProp(), wire);
		}
		wire.setLoad(false);
		//PKY 08052901 E 일부 라인 Name안나오는문제
		//		UMLElementFigure uMLElementFigure = new UMLElementFigure();
		//		uMLElementFigure.setText("<<extend>>\r");
		//		conn.add(uMLElementFigure,new ConnectionLocator(conn,ConnectionLocator.MIDDLE));
		PolygonDecoration polyLine = new PolygonDecoration();
		polyLine.setTemplate(polyLine.TRIANGLE_TIP);
		polyLine.setBackgroundColor(ColorConstants.tooltipBackground);
		//		conn.setLineStyle(Graphics.LINE_DOT);
		polyLine.setScale(15, 5);
		//		conn.setLineStyle(Graphics.LINE_DOT);
		conn.setTargetDecoration(polyLine);
		return conn;
	}

	public static PolylineConnection createRealizeLineModelFigure(RealizeLineModel wire) {
		PolylineConnection conn = new PolylineConnection();
		conn.addRoutingListener(RoutingAnimator.getDefault());
		//PKY 08052901 S 일부 라인 Name안나오는문제

		if(wire.isLoad()){
			UMLFigureFactory.setDetailPropertyPolylineDecoration(conn, null, wire.getDetailProp(), wire);
		}
		wire.setLoad(false);
		//PKY 08052901 E 일부 라인 Name안나오는문제
		//		UMLElementFigure uMLElementFigure = new UMLElementFigure();
		//		uMLElementFigure.setText("<<extend>>\r");
		//		conn.add(uMLElementFigure,new ConnectionLocator(conn,ConnectionLocator.MIDDLE));
		PolygonDecoration polyLine = new PolygonDecoration();
		polyLine.setTemplate(polyLine.TRIANGLE_TIP);
		polyLine.setBackgroundColor(ColorConstants.tooltipBackground);
		polyLine.setScale(15, 5);
		//		conn.setLineStyle(Graphics.LINE_DOT);
		conn.setTargetDecoration(polyLine);
		conn.setLineStyle(Graphics.LINE_DOT);
		return conn;
	}

	public static PolylineConnection createDeployLineFigure(LineModel wire) {
		PolylineConnection conn = new PolylineConnection();
		conn.addRoutingListener(RoutingAnimator.getDefault());
		//PKY 08052901 S 일부 라인 Name안나오는문제


		//PKY 08052901 E 일부 라인 Name안나오는문제
		UMLElementFigure uMLElementFigure = new UMLElementFigure();
		//		uMLElementFigure.setText("<<extend>>\r");
		Label tt = new Label();
		UMLEditor dd = ProjectManager.getInstance().getUMLEditor();
		//msg
		LineTextModel e = wire.getMiddleLineTextModel();
		//PKY 08090803 S 

		//PKY 08070904 S include,extends 스트레오값나오도록
//		e.setText("<<deploy>>", 0);


		//PKY 08070904 E include,extends 스트레오값나오도록
		if(wire.isLoad()){
			UMLFigureFactory.setDetailPropertyPolylineDecoration(conn, null, wire.getDetailProp(), wire);
		}else{
			MessageCommunicationModel m = new MessageCommunicationModel();
			wire.addMiddleLineTextModel(m);

			e.setText("<<deploy>>",0);
			e.setStereotype("<<deploy>>");
//			wire.setStereotype(text);

//			e.setText("<<"+prop+">>", 0);
			m.getLabelContents().setLineModel(wire);
			m.getLabelContents().setMessageIntType(Integer.valueOf(MessageCommunicationModel.TYPE_STEREO));
		}
		//PKY 08090803 E 

		wire.setLoad(false);
		N3ConnectionLocator n3 = new N3ConnectionLocator(conn, ConnectionLocator.MIDDLE);
		((LineTextModel)wire.getMiddleLineTextModel()).setN3ConnectionLocator(n3);//PKY 08071501 S 스트레오값 자동 생성시 위치저장안되는문제
		n3.setModel(e);

		tt.setIconAlignment(4);
		if(lableAdd(conn,n3))
			conn.add(tt, n3);
		PolylineDecoration polyLine = new PolylineDecoration();
		polyLine.setTemplate(polyLine.TRIANGLE_TIP);
		conn.setLineStyle(Graphics.LINE_DOT);
		conn.setTargetDecoration(polyLine);
		wire.getDetailProp().put(LineModel.ID_TARGET_NAVIGABILITY, "Navigable");//PKY 08071501 S include등 생성시 관계선이 있는것중 선 복사 붙여넣기 할 경우 방향성 복사안됨
		return conn;
	}

	public static PolylineConnection createTimingMessageLineFigure(LineModel wire) {
		PolylineConnection conn = new PolylineConnection();
		conn.addRoutingListener(RoutingAnimator.getDefault());
		//PKY 08052901 S 일부 라인 Name안나오는문제
		if(wire.isLoad()){
			UMLFigureFactory.setDetailPropertyPolylineDecoration(conn, null, wire.getDetailProp(), wire);
		}
		wire.setLoad(false);

		//PKY 08052901 E 일부 라인 Name안나오는문제
		UMLElementFigure uMLElementFigure = new UMLElementFigure();
		//		uMLElementFigure.setText("<<extend>>\r");
		Label tt = new Label();
		UMLEditor dd = ProjectManager.getInstance().getUMLEditor();
		//msg
		LineTextModel e = wire.getMiddleLineTextModel();

		N3ConnectionLocator n3 = new N3ConnectionLocator(conn, ConnectionLocator.MIDDLE);
		n3.setModel(e);
		tt.setIconAlignment(4);
		if(lableAdd(conn,n3))
			conn.add(tt, n3);
		PolylineDecoration polyLine = new PolylineDecoration();
		polyLine.setTemplate(polyLine.TRIANGLE_TIP);
//		conn.setLineStyle(Graphics.LINE_DOT);
		conn.setTargetDecoration(polyLine);
		//PKY 08052101 S LineNAME저장안되는문제


		//PKY 08052101 E LineNAME저장안되는문제
		return conn;
	}

	public static PolylineConnection createManifestLineFigure(LineModel wire) {
		PolylineConnection conn = new PolylineConnection();
		conn.addRoutingListener(RoutingAnimator.getDefault());
		//PKY 08052901 S 일부 라인 Name안나오는문제


		//PKY 08052901 E 일부 라인 Name안나오는문제
		UMLElementFigure uMLElementFigure = new UMLElementFigure();
//		ElementLabelModel d = new ElementLabelModel();

		//		uMLElementFigure.setText("<<extend>>\r");
		Label tt = new Label();

		//msg

		//PKY 08070904 S include,extends 스트레오값나오도록
		LineTextModel e = wire.getMiddleLineTextModel();
//		e.setText("<<deploy>>", 0);
		MessageCommunicationModel m = new MessageCommunicationModel();
		//PKY 08090803 S 


		//PKY 08070904 E include,extends 스트레오값나오도록

//		e.setText("<<manifest>>", 1);
		//inmsg
		N3ConnectionLocator n3 = new N3ConnectionLocator(conn, ConnectionLocator.MIDDLE);
		((LineTextModel)wire.getMiddleLineTextModel()).setN3ConnectionLocator(n3);//PKY 08071501 S 스트레오값 자동 생성시 위치저장안되는문제
		n3.setModel(e);
		tt.setIconAlignment(4);
		if(lableAdd(conn,n3))
			conn.add(tt, n3);
		//PKY 08061801 S manifest 저장 불러오기 할 경우 이름 표시안되는문제 
		if(wire.getDetailProp().get("ID_NAME")!=null&&!((String)wire.getDetailProp().get("ID_NAME")).trim().equals("")){
			LineTextModel e1 = wire.getMiddleLineTextModel();
			MessageCommunicationModel m1 = new MessageCommunicationModel();
			wire.addMiddleLineTextModel(m1);
			e.setText((String)wire.getDetailProp().get("ID_NAME"), 1);
			N3ConnectionLocator n31 = new N3ConnectionLocator(conn, ConnectionLocator.MIDDLE);
			n3.setModel(e1);	
			tt.setIconAlignment(4);
			if(lableAdd(conn,n31))
				conn.add(tt, n31);
		}
		//PKY 08061801 E manifest 저장 불러오기 할 경우 이름 표시안되는문제 
		if(wire.isLoad()){
			UMLFigureFactory.setDetailPropertyPolylineDecoration(conn, null, wire.getDetailProp(), wire);
		}else{
			wire.addMiddleLineTextModel(m);

			wire.getMiddleLineTextModel().setText("<<manifest>>",0);
			wire.setStereotype("<<manifest>>");

//			e.setText("<<"+prop+">>", 0);
			m.getLabelContents().setLineModel(wire);
			m.getLabelContents().setMessageIntType(Integer.valueOf(MessageCommunicationModel.TYPE_STEREO));
		}
		//PKY 08090803 E 

		wire.setLoad(false);
		PolylineDecoration polyLine = new PolylineDecoration();
		polyLine.setTemplate(polyLine.TRIANGLE_TIP);
		conn.setLineStyle(Graphics.LINE_DOT);
		conn.setTargetDecoration(polyLine);
		wire.getDetailProp().put(LineModel.ID_TARGET_NAVIGABILITY, "Navigable");//PKY 08071501 S include등 생성시 관계선이 있는것중 선 복사 붙여넣기 할 경우 방향성 복사안됨
		return conn;
	}

	public static PolylineConnection createMessageAssoicateLineFigure(LineModel wire) {
		PolylineConnection conn = new PolylineConnection();
		conn.addRoutingListener(RoutingAnimator.getDefault());


		//		uMLElementFigure.setText("<<extend>>\r");
		Label tt = new Label();
//		UMLEditor dd = ProjectManager.getInstance().getUMLEditor();

		LineTextModel e = wire.getMiddleLineTextModel();
		//PKY 08081101 S 저장 불러올 경우 Communication Message불러오지 못하는 문제
		if(wire.isLoad()){
			UMLEditor ue = ProjectManager.getInstance().getUMLEditor();
			N3EditorDiagramModel nm = ue.getDiagram();
			for(int i=0;i<e.getMessageSize();i++){
				MessageCommunicationModel mm = (MessageCommunicationModel)e.getMessageCommunicationModel(i);

				ue.addSeqNumber(mm);

			}
		}
		//PKY 08081101 E 저장 불러올 경우 Communication Message불러오지 못하는 문제

		wire.setLoad(false);
		//PKY 08052901 S Communication Line Message나오지않는 문제
		N3ConnectionLocator n3 = new N3ConnectionLocator(conn, ConnectionLocator.MIDDLE);
		((LineTextModel)e).setN3ConnectionLocator(n3);
		n3.setModel(e);
		String name=(String)wire.getDetailProp().get("ID_NAME");
		if(wire.getDetailProp().get("ID_SIZE_MIDDLE")!=null){
			String ss= (String)wire.getDetailProp().get("ID_SIZE_MIDDLE");
			String[] value1 = ss.split(",");
			if(value1.length==2){
				((LineTextModel)wire.getMiddleLineTextModel()).setSize(new Dimension(Integer.parseInt(value1[1]),Integer.parseInt(value1[0])));

			}
		}
		if(name!=null&&!name.trim().equals("")){
			MessageCommunicationModel m= new MessageCommunicationModel();
			m.setType(MessageCommunicationModel.TYPE_NAME);
			m.setName(name);
			wire.addMiddleLineTextModel(m);


			e.setText(name, 0);

			n3.setModel(e);

			if(wire.getDetailProp().get("ID_NAME_LOCATION_MIDDLE")!=null){
				String loca =(String)wire.getDetailProp().get("ID_NAME_LOCATION_MIDDLE");
				String[] data=loca.split(",");
				if(data.length==2){					
					n3.getPp().setLocation(new Point(Integer.parseInt(data[0]),Integer.parseInt(data[1])));
				}
			}
			if(wire.getDetailProp().get("ID_NAME_POINT_MIDDLE")!=null){
				String loca =(String)wire.getDetailProp().get("ID_NAME_POINT_MIDDLE");
				String[] data=loca.split(",");
				if(data.length==2){
					n3.setOldPoint((new Point(Integer.parseInt(data[0]),Integer.parseInt(data[1]))));
				}
			}

		}
		//PKY 08052901 E Communication Line Message나오지않는 문제
		tt.setIconAlignment(4);
		if(lableAdd(conn,n3))
			conn.add(tt, n3);

		return conn;
	}

	public static PolylineConnection createImportLineFigure(LineModel wire) {
		PolylineConnection conn = new PolylineConnection();
		conn.addRoutingListener(RoutingAnimator.getDefault());
		//PKY 08052901 S 일부 라인 Name안나오는문제


		//PKY 08052901 E 일부 라인 Name안나오는문제
		//		uMLElementFigure.setText("<<extend>>\r");
		Label tt = new Label();
		UMLEditor dd = ProjectManager.getInstance().getUMLEditor();
		//msg
		//PKY 08070904 S include,extends 스트레오값나오도록
		LineTextModel e = wire.getMiddleLineTextModel();
		//PKY 08090803 S 


		//PKY 08070904 E include,extends 스트레오값나오도록

		//inmsg
		N3ConnectionLocator n3 = new N3ConnectionLocator(conn, ConnectionLocator.MIDDLE);
		((LineTextModel)wire.getMiddleLineTextModel()).setN3ConnectionLocator(n3);//PKY 08071501 S 스트레오값 자동 생성시 위치저장안되는문제
		n3.setModel(e);
		tt.setIconAlignment(4);
		if(lableAdd(conn,n3))
			conn.add(tt, n3);		
		//PKY 08061101 S 저장 불러오기 후 marge에 이름을 못넣는 부분 수정
		if(wire.getDetailProp().get("ID_NAME")!=null&&!((String)wire.getDetailProp().get("ID_NAME")).trim().equals("")){
			LineTextModel e1 = wire.getMiddleLineTextModel();
			MessageCommunicationModel m1 = new MessageCommunicationModel();
			wire.addMiddleLineTextModel(m1);
			e.setText((String)wire.getDetailProp().get("ID_NAME"), 1);
			N3ConnectionLocator n31 = new N3ConnectionLocator(conn, ConnectionLocator.MIDDLE);
			n3.setModel(e1);	
			tt.setIconAlignment(4);
			if(lableAdd(conn,n31))
				conn.add(tt, n31);
			
		}
		//PKY 08061101 S 저장 불러오기 후 marge에 이름을 못넣는 부분 수정
		if(wire.isLoad()){
			UMLFigureFactory.setDetailPropertyPolylineDecoration(conn, null, wire.getDetailProp(), wire);
		}else{
			MessageCommunicationModel m = new MessageCommunicationModel();
			wire.addMiddleLineTextModel(m);

			wire.getMiddleLineTextModel().setText("<<import>>",0);
			wire.setStereotype("<<import>>");

			m.getLabelContents().setLineModel(wire);
			m.getLabelContents().setMessageIntType(Integer.valueOf(MessageCommunicationModel.TYPE_STEREO));
		}
		//PKY 08090803 E 

		wire.setLoad(false);
		PolylineDecoration polyLine = new PolylineDecoration();
		polyLine.setTemplate(polyLine.TRIANGLE_TIP);
		conn.setLineStyle(Graphics.LINE_DOT);
		conn.setTargetDecoration(polyLine);
		wire.getDetailProp().put(LineModel.ID_TARGET_NAVIGABILITY, "Navigable");//PKY 08071501 S include등 생성시 관계선이 있는것중 선 복사 붙여넣기 할 경우 방향성 복사안됨
		return conn;
	}

	public static PolylineConnection createMergeLineFigure(LineModel wire) {
		PolylineConnection conn = new PolylineConnection();
		conn.addRoutingListener(RoutingAnimator.getDefault());
		//PKY 08052901 S 일부 라인 Name안나오는문제


		//PKY 08052901 E 일부 라인 Name안나오는문제
		//		uMLElementFigure.setText("<<extend>>\r");
		Label tt = new Label();
		UMLEditor dd = ProjectManager.getInstance().getUMLEditor();
		//msg
		//PKY 08070904 S include,extends 스트레오값나오도록
		
		LineTextModel e = wire.getMiddleLineTextModel();
		//PKY 08090803 S 



		//PKY 08070904 E include,extends 스트레오값나오도록
		if(wire.isLoad()){
			UMLFigureFactory.setDetailPropertyPolylineDecoration(conn, null, wire.getDetailProp(), wire);
		}else{
			MessageCommunicationModel m = new MessageCommunicationModel();
			wire.addMiddleLineTextModel(m);

			wire.getMiddleLineTextModel().setText("<<merge>>",0);
			wire.setStereotype("<<merge>>");

			m.getLabelContents().setLineModel(wire);
			m.getLabelContents().setMessageIntType(Integer.valueOf(MessageCommunicationModel.TYPE_STEREO));
		}
		//PKY 08090803 E 

		wire.setLoad(false);
		e.setName(true);//PKY 08051401 S 이름 추가 할 경우 1회 더 추가되는 문제
		//inmsg
		N3ConnectionLocator n3 = new N3ConnectionLocator(conn, ConnectionLocator.MIDDLE);
		((LineTextModel)wire.getMiddleLineTextModel()).setN3ConnectionLocator(n3);//PKY 08071501 S 스트레오값 자동 생성시 위치저장안되는문제
		n3.setModel(e);
		tt.setIconAlignment(4);
		if(lableAdd(conn,n3))
			conn.add(tt, n3);

		//PKY 08061101 S 저장 불러오기 후 marge에 이름을 못넣는 부분 수정
		if(wire.getDetailProp().get("ID_NAME")!=null&&!((String)wire.getDetailProp().get("ID_NAME")).trim().equals("")){
			LineTextModel e1 = wire.getMiddleLineTextModel();
			MessageCommunicationModel m1 = new MessageCommunicationModel();
			wire.addMiddleLineTextModel(m1);
			e.setText((String)wire.getDetailProp().get("ID_NAME"), 1);
			N3ConnectionLocator n31 = new N3ConnectionLocator(conn, ConnectionLocator.MIDDLE);
			n3.setModel(e1);
			tt.setIconAlignment(4);
			if(lableAdd(conn,n31))
				conn.add(tt, n31);
		}
		//PKY 08061101 S 저장 불러오기 후 marge에 이름을 못넣는 부분 수정
		PolylineDecoration polyLine = new PolylineDecoration();
		polyLine.setTemplate(polyLine.TRIANGLE_TIP);
		conn.setLineStyle(Graphics.LINE_DOT);
		conn.setTargetDecoration(polyLine);
		wire.getDetailProp().put(LineModel.ID_TARGET_NAVIGABILITY, "Navigable");//PKY 08071501 S include등 생성시 관계선이 있는것중 선 복사 붙여넣기 할 경우 방향성 복사안됨
		return conn;

	}

	public static PolylineConnection createExtendLineFigure(LineModel wire) {
		PolylineConnection conn = new PolylineConnection();
		conn.addRoutingListener(RoutingAnimator.getDefault());
		//PKY 08052901 S 일부 라인 Name안나오는문제


		//PKY 08052901 E 일부 라인 Name안나오는문제
		//		uMLElementFigure.setText("<<extend>>\r");
		Label tt = new Label();
		UMLEditor dd = ProjectManager.getInstance().getUMLEditor();
		//msg
		//PKY 08070904 S include,extends 스트레오값나오도록
		LineTextModel e = wire.getMiddleLineTextModel();

		//PKY 08090803 S 

		//PKY 08070904 E include,extends 스트레오값나오도록
		if(wire.isLoad()){
			UMLFigureFactory.setDetailPropertyPolylineDecoration(conn, null, wire.getDetailProp(), wire);
		}else{
			MessageCommunicationModel m = new MessageCommunicationModel();
			wire.addMiddleLineTextModel(m);

			wire.getMiddleLineTextModel().setText("<<extend>>",0);
			wire.setStereotype("<<extend>>");

			m.getLabelContents().setLineModel(wire);
			m.getLabelContents().setMessageIntType(Integer.valueOf(MessageCommunicationModel.TYPE_STEREO));
		}
		//PKY 08090803 E 

		wire.setLoad(false);
		//inmsg
		N3ConnectionLocator n3 = new N3ConnectionLocator(conn, ConnectionLocator.MIDDLE);
		((LineTextModel)wire.getMiddleLineTextModel()).setN3ConnectionLocator(n3);//PKY 08071501 S 스트레오값 자동 생성시 위치저장안되는문제
		n3.setModel(e);
		tt.setIconAlignment(4);
		if(lableAdd(conn,n3))
			conn.add(tt, n3);
		PolylineDecoration polyLine = new PolylineDecoration();
		polyLine.setTemplate(polyLine.TRIANGLE_TIP);
		conn.setLineStyle(Graphics.LINE_DOT);
		conn.setTargetDecoration(polyLine);
		wire.getDetailProp().put(LineModel.ID_TARGET_NAVIGABILITY, "Navigable");//PKY 08071501 S include등 생성시 관계선이 있는것중 선 복사 붙여넣기 할 경우 방향성 복사안됨
		return conn;
	}

	public static PolylineConnection createRoleBindingLineFigure(LineModel wire) {
		PolylineConnection conn = new PolylineConnection();
		conn.addRoutingListener(RoutingAnimator.getDefault());
		//PKY 08052901 S 일부 라인 Name안나오는문제

		if(wire.isLoad()){
			UMLFigureFactory.setDetailPropertyPolylineDecoration(conn, null, wire.getDetailProp(), wire);
		}else{//PKY 08090501 S Role Binding 생성시 스트레오타입입력안됨
			MessageCommunicationModel m = new MessageCommunicationModel();
			wire.addMiddleLineTextModel(m);

			wire.getMiddleLineTextModel().setText("<<role binding>>",0);
			wire.setStereotype("<<role binding>>");

			m.getLabelContents().setLineModel(wire);
			m.getLabelContents().setMessageIntType(Integer.valueOf(MessageCommunicationModel.TYPE_STEREO));
		}
		//PKY 08090501 E Role Binding 생성시 스트레오타입입력안됨
		wire.setLoad(false);
		//PKY 08052901 E 일부 라인 Name안나오는문제
		//		UMLElementFigure uMLElementFigure = new UMLElementFigure();
		//		uMLElementFigure.setText("<<extend>>\r");
		//		conn.add(uMLElementFigure,new ConnectionLocator(conn,ConnectionLocator.MIDDLE));
		PolylineDecoration polyLine = new PolylineDecoration();
		polyLine.setTemplate(polyLine.TRIANGLE_TIP);
		conn.setLineStyle(Graphics.LINE_DOT);
		conn.setTargetDecoration(polyLine);
		wire.getDetailProp().put(LineModel.ID_TARGET_NAVIGABILITY, "Navigable");//PKY 08071501 S include등 생성시 관계선이 있는것중 선 복사 붙여넣기 할 경우 방향성 복사안됨
		return conn;
	}
	//PKY 08070904 S include,extends 스트레오값나오도록
	public static PolylineConnection createOccurrenceLineFigure(LineModel wire) {
		PolylineConnection conn = new PolylineConnection();
		conn.addRoutingListener(RoutingAnimator.getDefault());
		Label tt = new Label();


		//PKY 08070904 S include,extends 스트레오값나오도록
		LineTextModel e = wire.getMiddleLineTextModel();


		if(wire.isLoad()){
			UMLFigureFactory.setDetailPropertyPolylineDecoration(conn, null, wire.getDetailProp(), wire);
		}else{//PKY 08090501 S 스트레오타입 변경 후 저장 불러오기 할 경우 스트레오타입 초기화되는 문제
			MessageCommunicationModel m = new MessageCommunicationModel();
			wire.addMiddleLineTextModel(m);

			wire.getMiddleLineTextModel().setText("<<occurrence>>",0);
			wire.setStereotype("<<occurrence>>");

			m.getLabelContents().setLineModel(wire);
			m.getLabelContents().setMessageIntType(Integer.valueOf(MessageCommunicationModel.TYPE_STEREO));
		}
		//PKY 08090501 E 스트레오타입 변경 후 저장 불러오기 할 경우 스트레오타입 초기화되는 문제
		wire.setLoad(false);
		//PKY 08070904 E include,extends 스트레오값나오도록
		N3ConnectionLocator n3 = new N3ConnectionLocator(conn, ConnectionLocator.MIDDLE);
		((LineTextModel)wire.getMiddleLineTextModel()).setN3ConnectionLocator(n3);//PKY 08071501 S 스트레오값 자동 생성시 위치저장안되는문제
		n3.setModel(e);
		tt.setIconAlignment(4);
		if(lableAdd(conn,n3))
			conn.add(tt, n3);
		PolylineDecoration polyLine = new PolylineDecoration();
		polyLine.setTemplate(polyLine.TRIANGLE_TIP);
		conn.setLineStyle(Graphics.LINE_DOT);
		conn.setTargetDecoration(polyLine);
//		UMLElementFigure uMLElementFigure = new UMLElementFigure();
//		uMLElementFigure.setText("<<occurrence>>\r");
//		conn.add(uMLElementFigure, new ConnectionLocator(conn, ConnectionLocator.MIDDLE));
//		PolylineDecoration polyLine = new PolylineDecoration();
//		polyLine.setTemplate(polyLine.TRIANGLE_TIP);
//		conn.setLineStyle(Graphics.LINE_DOT);
//		conn.setTargetDecoration(polyLine);
		wire.getDetailProp().put(LineModel.ID_TARGET_NAVIGABILITY, "Navigable");//PKY 08071501 S include등 생성시 관계선이 있는것중 선 복사 붙여넣기 할 경우 방향성 복사안됨
		return conn;
	}



	public static PolylineConnection createRepresentsLineFigure(LineModel wire) {
		PolylineConnection conn = new PolylineConnection();
		conn.addRoutingListener(RoutingAnimator.getDefault());
		Label tt = new Label();


		//PKY 08070904 S include,extends 스트레오값나오도록
		LineTextModel e = wire.getMiddleLineTextModel();


		//PKY 08070904 E include,extends 스트레오값나오도록

		if(wire.isLoad()){
			UMLFigureFactory.setDetailPropertyPolylineDecoration(conn, null, wire.getDetailProp(), wire);
		}else{//PKY 08090501 S 스트레오타입 변경 후 저장 불러오기 할 경우 스트레오타입 초기화되는 문제
			MessageCommunicationModel m = new MessageCommunicationModel();
			wire.addMiddleLineTextModel(m);

			wire.getMiddleLineTextModel().setText("<<represents>>",0);
			wire.setStereotype("<<represents>>");

			m.getLabelContents().setLineModel(wire);
			m.getLabelContents().setMessageIntType(Integer.valueOf(MessageCommunicationModel.TYPE_STEREO));
		}
		//PKY 08090501 E 스트레오타입 변경 후 저장 불러오기 할 경우 스트레오타입 초기화되는 문제
		wire.setLoad(false);

		N3ConnectionLocator n3 = new N3ConnectionLocator(conn, ConnectionLocator.MIDDLE);
		((LineTextModel)wire.getMiddleLineTextModel()).setN3ConnectionLocator(n3);//PKY 08071501 S 스트레오값 자동 생성시 위치저장안되는문제
		n3.setModel(e);
		tt.setIconAlignment(4);
		if(lableAdd(conn,n3))
			conn.add(tt, n3);
		PolylineDecoration polyLine = new PolylineDecoration();
		polyLine.setTemplate(polyLine.TRIANGLE_TIP);
		conn.setLineStyle(Graphics.LINE_DOT);
		conn.setTargetDecoration(polyLine);
		wire.getDetailProp().put(LineModel.ID_TARGET_NAVIGABILITY, "Navigable");//PKY 08071501 S include등 생성시 관계선이 있는것중 선 복사 붙여넣기 할 경우 방향성 복사안됨
//		UMLElementFigure uMLElementFigure = new UMLElementFigure();
//		uMLElementFigure.setText("<<represents>>\r");
//		conn.add(uMLElementFigure, new ConnectionLocator(conn, ConnectionLocator.MIDDLE));
//		PolylineDecoration polyLine = new PolylineDecoration();
//		polyLine.setTemplate(polyLine.TRIANGLE_TIP);
//		conn.setLineStyle(Graphics.LINE_DOT);
//		conn.setTargetDecoration(polyLine);
		wire.getDetailProp().put(LineModel.ID_TARGET_NAVIGABILITY, "Navigable");//PKY 08071501 S include등 생성시 관계선이 있는것중 선 복사 붙여넣기 할 경우 방향성 복사안됨
		return conn;
	}
	//PKY 08070904 E include,extends 스트레오값나오도록

	public static PolylineConnection createRequiredInterfaceLineFigure(LineModel wire) {
		N3PolylineConnection conn = new N3PolylineConnection();
		conn.addRoutingListener(RoutingAnimator.getDefault());
		//PKY 08052901 S 일부 라인 Name안나오는문제

		if(wire.isLoad()){
			UMLFigureFactory.setDetailPropertyPolylineDecoration(conn, null, wire.getDetailProp(), wire);
		}
		wire.setLoad(false);
		//PKY 08052901 E 일부 라인 Name안나오는문제
		PolylineDecoration polyLine = new PolylineDecoration();
		PointList p = new PointList();
		p.addPoint(38, 40);
		p.addPoint(20, 35);
		p.addPoint(7, 22);
		p.addPoint(2, 10);
		p.addPoint(0, 0);
		p.addPoint(2, -10);
		p.addPoint(7, -22);
		p.addPoint(20, -35);
		p.addPoint(38, -40);
		polyLine.setTemplate(p);
		polyLine.setScale(0.5, 0.5);
		polyLine.setLineWidth(3);
		conn.setTargetDecoration(polyLine);
		return conn;
	}
	//PKY 08070904 S include,extends 스트레오값나오도록
	public static PolylineConnection createIncludeLineFigure(LineModel wire) {
		PolylineConnection conn = new PolylineConnection();
		conn.addRoutingListener(RoutingAnimator.getDefault());
		Label tt = new Label();




		//PKY 08070904 S include,extends 스트레오값나오도록
		LineTextModel e = wire.getMiddleLineTextModel();
//		e.setText("<<deploy>>", 0);
		MessageCommunicationModel m = new MessageCommunicationModel();
		wire.addMiddleLineTextModel(m);


		

		//PKY 08090803 S 
		//PKY 08070904 E include,extends 스트레오값나오도록
		if(wire.isLoad()){
			UMLFigureFactory.setDetailPropertyPolylineDecoration(conn, null, wire.getDetailProp(), wire);
		}else{
			wire.getMiddleLineTextModel().setText("<<include>>",0);
			wire.setStereotype("<<include>>"); //PKY 08071501 S 스트레오값 자동 생성시 위치저장안되는문제
//			e.setText("<<"+prop+">>", 0);
			m.getLabelContents().setLineModel(wire);
			m.getLabelContents().setMessageIntType(Integer.valueOf(MessageCommunicationModel.TYPE_STEREO));
		}
		//PKY 08090803 E 

		wire.setLoad(false);

		N3ConnectionLocator n3 = new N3ConnectionLocator(conn, ConnectionLocator.MIDDLE);
		((LineTextModel)wire.getMiddleLineTextModel()).setN3ConnectionLocator(n3);//PKY 08071501 S 스트레오값 자동 생성시 위치저장안되는문제		
		n3.setModel(e);
		tt.setIconAlignment(4);
		if(lableAdd(conn,n3))
			conn.add(tt, n3);
		PolylineDecoration polyLine = new PolylineDecoration();
		polyLine.setTemplate(polyLine.TRIANGLE_TIP);
		conn.setLineStyle(Graphics.LINE_DOT);
		conn.setTargetDecoration(polyLine);
		wire.getDetailProp().put(LineModel.ID_TARGET_NAVIGABILITY, "Navigable");//PKY 08071501 S include등 생성시 관계선이 있는것중 선 복사 붙여넣기 할 경우 방향성 복사안됨

		return conn;
	}
	//PKY 08070904 E include,extends 스트레오값나오도록
	public static PointList getRoundedRectilinearSmoothPoints(boolean isDuringDrag, PointList points, int smoothness) {
		//        if (isDuringDrag) {
		//            return points;
		//        }
		if (smoothness < 10 || points.size() == 0) {
			return points;
		}
		double PREFERRED_RADIUS = smoothness < 20 ? 8.0 : smoothness < 25 ? 12.0 : smoothness < 35 ? 16.0 : 22;
		PointList newPoints = new PointList();
		newPoints.addPoint(points.getFirstPoint());
		for (int i = 1; i < points.size() - 1; i++) {
			Point prev = points.getPoint(i - 1);
			Point curr = points.getPoint(i);
			Point next = points.getPoint(i + 1);
			//first thing we do is to put ourselves in the situtation
			//where the prev point is on the left of the next.
			//it is easier to visualize (we assume x-s of prev and next are
			//not equal.)
			//we will make sure we reverse the order in which the points are added
			//though.
			boolean reverse = false;
			if (prev.x - next.x > 2) { //sometimes it is very close such as one pixel!
				//                System.err.println(i + " is reversed");
				prev = next;
				next = points.getPoint(i - 1);
				reverse = true;
			}
			// calculate radius
			double halfSeg1 = curr.getDistance(prev) / 2.0;
			double halfSeg2 = curr.getDistance(next) / 2.0;
			double radius = Math.min(halfSeg1, halfSeg2);
			radius = Math.min(radius, PREFERRED_RADIUS);
			// calculate center of arc and direction
			boolean centerIsLeft = curr.x - prev.x > 2;
			boolean centerIsTop;
			double centerX;
			double centerY;
			int direction = 1;
			if (centerIsLeft) {
				centerX = curr.x - radius;
				centerIsTop = next.y < prev.y;
			} else {
				centerX = curr.x + radius;
				centerIsTop = next.y > prev.y;
			}
			if (centerIsTop) {
				centerY = curr.y - radius;
			} else {
				centerY = curr.y + radius;
			}
			// calculate arc start angle
			double startAngle;
			if (centerIsTop) {
				direction = -1;
				if (centerIsLeft) {
					startAngle = -90;
				} else {
					startAngle = 0;
				}
			} else {
				direction = 1;
				if (centerIsLeft) {
					// third quarter
					startAngle = 90;
				} else {
					// fourth quarter
					startAngle = 0;
				}
			}
			int pointsCount = 5; //five is the smoothest curve it seems
			//a lot more makes it really ugly (chart like)
			//less is not good. scale does not really matter surprisingly.
			//Tried adding more points when scaling or when smootheness changes
			//and it is worse
			//smoothness < 20 ? 5 : smoothness < 25 ? 5 : smoothness < 35 ? 5 : 5 ;
			double deltaAngle = 90.0 / pointsCount;
			PointList pl = reverse ? new PointList() : null;
			for (int k = 0; k <= pointsCount; k++) {
				double angle = Math.toRadians(startAngle + direction * deltaAngle * k);
				double x = centerX - radius * Math.cos(angle);
				double y = centerY - radius * Math.sin(angle);
				if (reverse) {
					pl.addPoint(new PrecisionPoint(x, y));
				} else {
					newPoints.addPoint(new PrecisionPoint(x, y));
				}
			}
			if (reverse) {
				pl.reverse();
				newPoints.addAll(pl);
			}
		}
		newPoints.addPoint(points.getLastPoint());
		return newPoints;
	}

	public static PolylineConnection createControlFlowLineFigure(LineModel wire) {
		N3PolylineConnection conn = new N3PolylineConnection();
		
		return conn;
	}
	//CONNECTION 
	public static PolylineConnection createDependencyLineFigure(LineModel wire) {
		PolylineConnection conn = new PolylineConnection();
		conn.addRoutingListener(RoutingAnimator.getDefault());
		//		UMLElementFigure uMLElementFigure = new UMLElementFigure();
		//		uMLElementFigure.setText("<<include>>\r");
		//		conn.add(uMLElementFigure,new ConnectionLocator(conn,ConnectionLocator.MIDDLE));

		//PKY 08081101 S Dependency , 이름 스트레오타입 표시못함
		if(wire.isLoad()){
			UMLFigureFactory.setDetailPropertyPolylineDecoration(conn, null, wire.getDetailProp(), wire);
		}
		wire.setLoad(false);
		//PKY 08081101 E Dependency , 이름 스트레오타입 표시못함

		PolylineDecoration polyLine = new PolylineDecoration();
		polyLine.setTemplate(polyLine.TRIANGLE_TIP);
		conn.setLineStyle(Graphics.LINE_DOT);
//		conn.setBorder(new LineBorder(ColorConstants.blue,1));
		conn.setTargetDecoration(polyLine);
		wire.getDetailProp().put(LineModel.ID_TARGET_NAVIGABILITY, "Navigable");//PKY 08071501 S include등 생성시 관계선이 있는것중 선 복사 붙여넣기 할 경우 방향성 복사안됨
		return conn;
	}

	private static org.eclipse.swt.graphics.Color Color(int i, int j, int k) {
		// TODO Auto-generated method stub
		return null;
	}

	public static PolylineConnection createNoteLineFigure(LineModel wire) {
		PolylineConnection conn = new PolylineConnection();
		conn.addRoutingListener(RoutingAnimator.getDefault());
		//		conn.setConnectionRouter(new ShortestPathConnectionRouter());
		//		UMLElementFigure uMLElementFigure = new UMLElementFigure();
		//		uMLElementFigure.setText("<<include>>\r");
		//		conn.add(uMLElementFigure,new ConnectionLocator(conn,ConnectionLocator.MIDDLE));
		//		PolylineDecoration polyLine = new PolylineDecoration();
		//		polyLine.setTemplate(polyLine.TRIANGLE_TIP);
		conn.setLineStyle(Graphics.LINE_DOT);
		//		conn.setTargetDecoration(polyLine);
		return conn;
	}

	public static PolylineConnection createNewWire(LineModel wire) {
		PolylineConnection conn = new PolylineConnection();
		UMLElementFigure uMLElementFigure = new UMLElementFigure();


		conn.add(uMLElementFigure, new ConnectionLocator(conn, ConnectionLocator.MIDDLE));


		//		UMLElementFigure uMLElementFigure = new UMLElementFigure();
		//		uMLElementFigure.setText("안녕하새ㅔ요");
		//		conn.add(uMLElementFigure);
		//		conn.setSize(1000, 1000);
		//		BorderLayout flowLayout = new BorderLayout();
		//		ActorFigure actorFigure = new ActorFigure();
		//		FlowLayout flowLayout = new FlowLayout();
		//		flowLayout.setMinorSpacing(0);
		//		flowLayout.setMajorSpacing(0);
		//		figure.setLayoutManager(flowLayout);
		//		conn.setLayoutManager(flowLayout);
		//		conn.set

		//		conn.setLineStyle(Graphics.LINE_SOLID);
		//		conn.setLineStyle(s)
		return conn;
	}
	public static boolean lableAdd(PolylineConnection conn,N3ConnectionLocator n3){
		boolean inCheck=false;
		if(conn.getChildren().size()==0){
			return true;
		}
		for(int i = 0 ; i < conn.getChildren().size(); i++){
			if( conn.getChildren().get(i) instanceof Label){
				Label label = (Label)conn.getChildren().get(i);
				if(n3.getAlignment()==label.getIconAlignment()){
					inCheck=true;
					return false;
				}
			}
		}
		if(!inCheck){
			return true;
		}
		return false;
	}
	//	public static IFigure createNewLED(){
	//		return new LEDFigure();
	//	}
	//
	//	public static IFigure createNewCircuit(){
	//		CircuitFigure f = new CircuitFigure();
	//		return f;
	//	}
}
