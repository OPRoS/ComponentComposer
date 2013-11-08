package kr.co.n3soft.n3com.model.diagram;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import kr.co.n3soft.n3com.UMLEditor;
import kr.co.n3soft.n3com.edit.policy.UMLTreeContainerEditPolicy;
import kr.co.n3soft.n3com.model.comm.DataInputPortModel;
import kr.co.n3soft.n3com.model.comm.DataOutputPortModel;
import kr.co.n3soft.n3com.model.comm.DocComponentModel;
import kr.co.n3soft.n3com.model.comm.ElementLabelModel;
import kr.co.n3soft.n3com.model.comm.EventInputPortModel;
import kr.co.n3soft.n3com.model.comm.EventOutputPortModel;
import kr.co.n3soft.n3com.model.comm.FrameModel;
import kr.co.n3soft.n3com.model.comm.LineModel;
import kr.co.n3soft.n3com.model.comm.MethodInputPortModel;
import kr.co.n3soft.n3com.model.comm.MethodOutputPortModel;
import kr.co.n3soft.n3com.model.comm.PortModel;
import kr.co.n3soft.n3com.model.comm.UMLContainerModel;
import kr.co.n3soft.n3com.model.comm.UMLDataModel;
import kr.co.n3soft.n3com.model.comm.UMLElementModel;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.comm.command.UMLCreateCommand;
import kr.co.n3soft.n3com.model.component.AtomicComponentModel;
import kr.co.n3soft.n3com.model.component.ComponentModel;
import kr.co.n3soft.n3com.model.component.OPRoSDataTypeElementModel;
import kr.co.n3soft.n3com.model.component.OPRoSDataTypesElementModel;
import kr.co.n3soft.n3com.model.component.OPRoSServiceTypeElementModel;
import kr.co.n3soft.n3com.model.component.OPRoSServiceTypesElementModel;
import kr.co.n3soft.n3com.model.component.SyncManager;
import kr.co.n3soft.n3com.project.browser.UMLTreeModel;
import kr.co.n3soft.n3com.project.browser.UMLTreeParentModel;
import kr.co.n3soft.n3com.projectmanager.CompAssemManager;
import kr.co.n3soft.n3com.projectmanager.PortProfile;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.draw2d.FreeformViewport;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.RangeModel;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.ui.parts.ScrollingGraphicalViewer;
import org.jdom.Element;

public class N3EditorDiagramModel extends UMLDiagramModel {
	ElementLabelModel name = null;
	UMLEditor uMLEditor = null;
	int diagramType = -1;
	public java.util.ArrayList frameModels = new java.util.ArrayList();
	private boolean isOpen = false;
	private boolean isReadOnly = false;
	int dtype = 0;
	public int getDtype() {
		return dtype;
	}

	public void setDtype(int dtype) {
		this.dtype = dtype;
	}

	public void setDiagramType(int p){
		this.diagramType = p;
	}

	public int getDiagramType(){
		return this.diagramType;
	}

	public N3EditorDiagramModel() {
		//		streotype= new ElementLabelModel(PositionConstants.CENTER);
		//		size.width = 100;
		//		size.height= 50;
		name = new ElementLabelModel(PositionConstants.CENTER);
		name.setType("NAME");
		//		name.setLayout(BorderLayout.CENTER);
		//		name.setParentLayout(1);
		//		this.addChild(name);
		this.uMLDataModel.setElementProperty(name.getType(), name);
//		ProjectManager.getInstance().diagrams
		CompAssemManager.getInstance().getDiagramList().add(this);
	}
	
	public void deleteSearchModelId(String id){
		
	}
	
	public void searchCoreDiagramIdModel2(String id){
		for(int i=0;i<this.getChildren().size();i++){
			Object obj = this.getChildren().get(i);
		if(obj instanceof AtomicComponentModel){
			AtomicComponentModel um = (AtomicComponentModel)obj;
			if(um.getCoreDiagramCmpModel()!=null){
				if(um.getCoreDiagramCmpModel().getUMLDataModel().getId().equals(id)){
//					um.setN3EditorDiagramModelTemp(this);
					ProjectManager.getInstance().getSearchModel().add(um);
				} 
			}
			else{
				if(um.getUMLDataModel().getId().equals(id)){
//					um.setN3EditorDiagramModelTemp(this);
					ProjectManager.getInstance().getSearchModel().add(um);
				} 
			}
//			UMLTreeModel um1 = um.getCoreUMLTreeModel();
//			if(um1==null)
//				continue;
//			AtomicComponentModel am2 = (AtomicComponentModel)um.getRefModel();
			     			
		}
		}
		
	}
	
	public void searchCoreDiagramIdModel(String id){
		for(int i=0;i<this.getChildren().size();i++){
			Object obj = this.getChildren().get(i);
		if(obj instanceof AtomicComponentModel){
			AtomicComponentModel um = (AtomicComponentModel)obj;
			UMLTreeModel um1 = um.getCoreUMLTreeModel();
			if(um1==null)
				continue;
			AtomicComponentModel am2 = (AtomicComponentModel)um1.getRefModel();
			if(am2.getUMLDataModel().getId().equals(id)){
//				um.setN3EditorDiagramModelTemp(this);
				ProjectManager.getInstance().getSearchModel().add(um);
			}      			
		}
		}
		
	}
	
	public void searchComponentDiagramIdModel(String id){
		for(int i=0;i<this.getChildren().size();i++){
			Object obj = this.getChildren().get(i);
		if(obj instanceof UMLModel){
			UMLModel um = (UMLModel)obj;
			if(um.getID().equals(id)){
				
				um.setN3EditorDiagramModelTemp(this);
				ProjectManager.getInstance().getSearchModel().add(um);
			}      			
		}
		}
		
	}
	public void searchModel(String type,String text,boolean isCase,boolean isWwo){
		//20080325 PKY S 검색
		for(int i=0;i<this.getChildren().size();i++){
			Object obj = this.getChildren().get(i);
			if(ProjectManager.getInstance().isSearchModel()||ProjectManager.getInstance().isSearchDiagaramModel()){

				if(obj instanceof UMLModel){
					String type1 = ProjectManager.getInstance().getModelTypeName(ProjectManager.getInstance().getModelType((UMLModel)obj, -1));
					if(type.equals("All") || type1.equals(type)){
						UMLModel um = (UMLModel)obj;

						if(!isWwo){
							if(um.getName().indexOf(text)>=0){
								um.setN3EditorDiagramModelTemp(this);
								ProjectManager.getInstance().getSearchModel().add(um);
							}
						}
						else{
							if(!isCase){
								if(um.getName().equals(text)){
									um.setN3EditorDiagramModelTemp(this);
									ProjectManager.getInstance().getSearchModel().add(um);
								}
							}
							else{
								if(um.getName().equalsIgnoreCase(text)){
									um.setN3EditorDiagramModelTemp(this);
									ProjectManager.getInstance().getSearchModel().add(um);
								}
							}
						}
					}
				}


			}else{
				if(obj instanceof UMLModel){
					UMLModel um = (UMLModel)obj;
					if(um.getID().equals(text)){
						um.setN3EditorDiagramModelTemp(this);
						ProjectManager.getInstance().getSearchModel().add(um);
					}      			
				}

			}

		}


	}

	//PKY 08080501 S 모델 브라우져에서 다이어그램의 이름을 변경해도 다이어그램 이름에는 변경안되는 문제
	public void setName(String uname) {
//		uname = "diagram";
		name.setLabelContents(uname);

		//PKY 08080501 S 트리에서 다이어그램 자체를 다른 다이어그램에 드레그 할 경우 프로퍼티에서 이름 변경시 변경되지 않고, 직접 드레그한 다이어그램의 이름을 변경해도 드레그한 모델의 이름은 병경되지 않음
		for(int i=0; i<this.frameModels.size();i++){
			FrameModel fm = (FrameModel)this.frameModels.get(i);
			fm.refreshDiagram(this);
		}
		//PKY 08080501 E 트리에서 다이어그램 자체를 다른 다이어그램에 드레그 할 경우 프로퍼티에서 이름 변경시 변경되지 않고, 직접 드레그한 다이어그램의 이름을 변경해도 드레그한 모델의 이름은 병경되지 않음
		if(this.getUMLEditor()!=null)
			this.getUMLEditor().setTitleName(uname);

	}
	//PKY 08080501 E 모델 브라우져에서 다이어그램의 이름을 변경해도 다이어그램 이름에는 변경안되는 문제

	public UMLEditor getUMLEditor() {
		return uMLEditor;
	}

	public void setUMLEditor(UMLEditor p) {
		this.uMLEditor = p;
	}

	public String getName() {
		return name.getLabelContents();
	}

	public void setUMLDataModel(UMLDataModel p) {
		uMLDataModel = p;
	}

	public void setTreeModel(UMLTreeModel p) {
		super.setTreeModel(p);
		name.setTreeModel(p);
	}

//	

	
	//20080326 PKY S 다이어그램 드래그 시 드래그한 다이어그램 정보 변경에 따른 반영이 안되는문제
	public void addChild(UMLElementModel child) {
		this.addChild(child, -1);
	}

	public void addChild(UMLElementModel child, int index) {

		super.addChild(child, index);
		for(int i=0; i<this.frameModels.size();i++){
			FrameModel fm = (FrameModel)this.frameModels.get(i);
			fm.refreshDiagram(this);
		}
	}

	public void removeChild(UMLElementModel child) {
		super.removeChild(child);
		for(int i=0; i<this.frameModels.size();i++){
			FrameModel fm = (FrameModel)this.frameModels.get(i);
			fm.refreshDiagram(this);
		}
	}
	//20080326 PKY E 다이어그램 드래그 시 드래그한 다이어그램 정보 변경에 따른 반영이 안되는문제

	public Object clone() {
		N3EditorDiagramModel clone = new N3EditorDiagramModel();

		for (int i = 0; i < this.getChildren().size(); i++) {
			UMLModel um = (UMLModel)this.getChildren().get(i);
			clone.addChild((UMLElementModel)um.clone());
//			for (int i1 = 0; i1 < um.getSourceConnections().size(); i1++) {

//			}
		}

		return clone;
	}

	public boolean isOpen() {
		return isOpen;
	}

	public void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}

	public String toString() {
		return this.getPackage()+"."+this.getName();
	}
	//PKY 08080501 S 모델 브라우져에서 다이어그램의 이름을 변경해도 다이어그램 이름에는 변경안되는 문제
	public void setPropertyValue(Object id, Object value) {
//      V1.02 WJH E 080813 S 다이어그램 이름이 아예 지워지지 않도록 수정
		if(value == null || value.toString().equals(""))
			return;
//      V1.02 WJH E 080813 E 다이어그램 이름이 아예 지워지지 않도록 수정
//		if(this.getName()!=null || !this.getName().trim().equals("")){
//			return;
//		}
//		value = "diagram";
		value=this.getUMLTreeModel().getParent().getName()+" diagram";//Khg 2010.05.19 어플리케이션 이름과 일치하도록 수정
		if (ID_NAME.equals(id)) {
			setName((String)value);
		}
	//PKY 08080501 E 모델 브라우져에서 다이어그램의 이름을 변경해도 다이어그램 이름에는 변경안되는 문제
	}
	//PKY 08082201 S 다이어그램 Frame Link에서 에러발생
	public void frameRefresh(){
		for(int i=0; i<this.frameModels.size();i++){
			FrameModel fm = (FrameModel)this.frameModels.get(i);
			fm.refreshDiagram(this);
		}
	}

	public boolean isReadOnly() {
		return isReadOnly;
	}

	public void setReadOnly(boolean isReadOnly) {
		this.isReadOnly = isReadOnly;
	}
	
	public boolean getCotainModel(int x,int y){
		ScrollingGraphicalViewer viewer1 = ProjectManager.getInstance().getUMLEditor().getScrollingGraphicalViewer();
		ScalableFreeformRootEditPart sed = (ScalableFreeformRootEditPart)viewer1.getRootEditPart();
		FreeformViewport fv = (FreeformViewport)sed.getFigure();
		RangeModel rm = fv.getVerticalRangeModel();
		RangeModel rm1 = fv.getHorizontalRangeModel();
		
		int value = rm.getValue();
		Point pt = new Point(x+rm1.getValue(), y+value);
		Point pt2 = new Point(pt.x, pt.y);
		ZoomManager manager = (ZoomManager)ProjectManager.getInstance().getUMLEditor().getGraphicalViewerp().getProperty(ZoomManager.class.toString());
		double zoom = 0;
		if(manager!=null){
			zoom=manager.getZoom();
//			pt.y;
			pt2.x = (int)(pt2.x/zoom);
			pt2.y = (int)(pt2.y/zoom);
		}
//		Point pt = new Point(x,y);
		for(int i=0;i<this.getChildren().size();i++){
			Object obj = this.getChildren().get(i);
			if(obj instanceof ComponentModel){
				ComponentModel cep = (ComponentModel)obj;
				UMLTreeModel ut = cep.getUMLTreeModel();
//				if(ut instanceof UMLTreeParentModel){
//					UMLTreeParentModel utm = (UMLTreeParentModel)ut;
//					for(int i1=0;i1<utm.getChildren().length;i1++){
//						UMLTreeModel it1= (UMLTreeModel)utm.getChildren()[i1];
//						if(it1.getRefModel()==this){
//							return false;
//						}
//					}
//				}
				UMLModel um = (UMLModel)cep;
				Rectangle re = new Rectangle(um.getLocation(),um.getSize());
				System.out.println(re);
				
				if(re.contains(x,y)){
					if(ut instanceof UMLTreeParentModel){
						UMLTreeParentModel utm = (UMLTreeParentModel)ut;
						for(int i1=0;i1<utm.getChildren().length;i1++){
							UMLTreeModel it1= (UMLTreeModel)utm.getChildren()[i1];
							if(it1.getRefModel()==this){
								return false;
							}
						}
					}
					
					
					return true;
				}
			}
		}
		return false;
	}
	
	public UMLModel getModel(int x,int y){
		ScrollingGraphicalViewer viewer1 = ProjectManager.getInstance().getUMLEditor().getScrollingGraphicalViewer();
		ScalableFreeformRootEditPart sed = (ScalableFreeformRootEditPart)viewer1.getRootEditPart();
		FreeformViewport fv = (FreeformViewport)sed.getFigure();
		RangeModel rm = fv.getVerticalRangeModel();
		RangeModel rm1 = fv.getHorizontalRangeModel();
		
		int value = rm.getValue();
		Point pt = new Point(x+rm1.getValue(), y+value);
		Point pt2 = new Point(pt.x, pt.y);
		ZoomManager manager = (ZoomManager)ProjectManager.getInstance().getUMLEditor().getGraphicalViewerp().getProperty(ZoomManager.class.toString());
		double zoom = 0;
		if(manager!=null){
			zoom=manager.getZoom();
//			pt.y;
			pt2.x = (int)(pt2.x/zoom);
			pt2.y = (int)(pt2.y/zoom);
		}
//		Point pt = new Point(x,y);
		for(int i=0;i<this.getChildren().size();i++){
			Object obj = this.getChildren().get(i);
			if(obj instanceof ComponentModel){
				ComponentModel cep = (ComponentModel)obj;
				UMLModel um = (UMLModel)cep;
				Rectangle re = new Rectangle(um.getLocation(),um.getSize());
				System.out.println(re);
				if(re.contains(pt2)){
					return um;
				}
			}
		}
		return null;
		
	}
	
	public String getCompName(String name){
		boolean isEqName = false;
		String tname = "";
		for(int i1=0;i1<999;i1++){
			isEqName = false;
			if(i1==0){
				tname = name;
			}
			else 
			tname = name+i1;
			for(int i=0;i<this.getChildren().size();i++){
				Object obj = this.getChildren().get(i);
				if(obj instanceof ComponentModel){
					ComponentModel cep = (ComponentModel)obj;
					if(tname.equals(cep.getName())){
						isEqName = true;
						break;
					}
				}
			}
			if(!isEqName)
				break;
		}
		return tname;
	}
	
	public String getCompName2(String name){
		return "";
//		boolean isComp = false;
//		ComponentModel cep = null;
//			for(int i=0;i<this.getChildren().size();i++){
//				Object obj = this.getChildren().get(i);
//				if(obj instanceof ComponentModel){
//					 cep = (ComponentModel)obj;
//					 if("<<composite>>".equals(cep.getStereotype())){
//						 isComp = true;
//						 break;
//					 }
//					
//				}
//			}
//			
////			if(isComp){
////				
////			}
//			
//		
//		return tname;
	}
	

public void setModels(DocComponentModel doc){
		
		java.util.ArrayList addComps = new java.util.ArrayList();
		
		for(int i=0;i<this.getChildren().size();i++){
			Object obj = this.getChildren().get(i);
			if(obj instanceof AtomicComponentModel){
				AtomicComponentModel cep = (AtomicComponentModel)obj;
//				cep.getco
				UMLTreeModel ut = cep.getCoreUMLTreeModel();
				System.out.println("ut================================>"+ut);
				//서동민20110718>>이름비교에서 id비교로 변경>>>0727 이름비교
				if(ut!=null && doc.getName().equals(ut.getName())){
//				if(ut!=null && doc.getId().equals(cep.getId())){
					
						
						 for(int i2=0;i2<doc.getPortlists().size();i2++){
							 PortProfile pf = (PortProfile)doc.getPortlists().get(i2);
							 boolean isAdd = true;
							 for(int i1=0;i1<cep.getPorts().size();i1++){
								 PortModel pm = (PortModel)cep.getPorts().get(i1);
								 if(pf.name.equals(pm.getName())){
									 isAdd = false;
									 break;
								 }
							 }
							 if(isAdd){
								 pf.getPortModel(cep);
								 

							 }
						 }
						 

				}
				
			}
		}
	}

//110822 S SDM  - 메소드 전체 수정
//			     1. 파라미터 CompoentModel um에서 AtomicCompoentModel tempAm로 변경
//               2. 컴포저에서  수정/삭제한 포트는 동기화 안되도록 수정
//				 3. 메소드 전체에 불필요한 주석소스 삭제
//				 4. 포트 입력정보(Type, Desc 등) 변경사항 적용 (110823)
//20110726 서동민-수정
public void setModels(DocComponentModel doc, AtomicComponentModel tempAm, File path){
	
	java.util.ArrayList addComps = new java.util.ArrayList();
	AtomicComponentModel am = tempAm.getCoreDiagramCmpModel();
	
	// 아토믹컴포넌트의 포트변경 및 삭제 정보 리스트로 받음
	List modList = am.getReNameList();
	List delList = am.getDeleteList();

			System.out.println("ut================================>"+am);
			//서동민20110718>>이름비교에서 id비교로 변경
			//서동민20110727>>이름비교
			System.out.println("umID>>>" + am.getId());
			System.out.println("docID>>>" + doc.getId());
			System.out.println("umName>>>" + am.getName());
			System.out.println("docName>>>" + doc.getName());
			
			
			
			if(am!=null && am.getName().equals(doc.getName())){
				System.out.println("asdasdasdadsasdasdasfsadfhlsdfjhljsdfhj");
				
				
				for(int i2=0;i2<doc.getPortlists().size();i2++){
					 PortProfile pf = (PortProfile)doc.getPortlists().get(i2);
					 boolean isAdd = true;
					 
					 // 변경 및 삭제정보에 있을 경우 포트 추가 안함.
					 Iterator<String> itMod = modList.iterator();
					 while(itMod.hasNext()){
						 String str[] = itMod.next().split(",");
						 
						 if(pf.name.equals(str[0]))
							 isAdd = false;
					 }
					 
					 Iterator<String> itDel = delList.iterator();
					 while(itDel.hasNext()){
						 String str = itDel.next();
						 
						 if(pf.name.equals(str))
							 isAdd = false;
					 }
					 
					 
					 if(isAdd){
						 for(int i1=0;i1<am.getPorts().size();i1++){
							 PortModel pm = (PortModel)am.getPorts().get(i1);
							 if(pf.name.equals(pm.getName()) ){
								 isAdd = false;
								 
								 //포트 입력정보(Type, Desc 등) 변경사항 적용
								 if(!pm.getType().equals(pf.type)){
									 pm.setType(pf.type);
								 }
								 else if(!pm.getDesc().equals(pf.desc)){
									 pm.setDesc(pf.desc);
								 }
								 else if(!(pm instanceof EventOutputPortModel) && !(pm instanceof EventInputPortModel)){
									 if(!pm.getTypeRef().equals(pf.reference)){
										 pm.setTypeRef(pf.reference);
									 }
								 }
								 else if(pm instanceof DataInputPortModel){
									 if(!pm.getQueueSize().equals(pf.queue_size)){
										 pm.setQueueSize(pf.queue_size);
									 }
									 if(!pm.getQueueingPolicy().equals(pf.queueing_policy)){
										 pm.setQueueingPolicy(pf.queueing_policy);
									 }
								 }
								 break;
							 }
						 }
					 }
					 if(isAdd){
						 UMLModel subpart = null;
						 if(pf.portType==0){
							 if("required".equals(pf.usage)){//in
								 subpart = new MethodInputPortModel();
									subpart.setName(pf.name);
							 }
							 else {//out
								 subpart = new MethodOutputPortModel();
									subpart.setName(pf.name);
							 }
						 }
						 else if(pf.portType==1){
							 if("input".equals(pf.usage)){//in
								 subpart = new EventInputPortModel();
									subpart.setName(pf.name);
							 }
							 else {//out
								 subpart = new EventOutputPortModel();
								subpart.setName(pf.name);
							 }
						 }
						 if(pf.portType==2){
							 if("input".equals(pf.usage)){//in
								 subpart = new DataInputPortModel();
									subpart.setName(pf.name);
							 }
							 else {//out
								 subpart = new DataOutputPortModel();
									subpart.setName(pf.name);
							 }
						 }
						
//						UMLCreateCommand create = new UMLCreateCommand();
						Rectangle rect = new Rectangle();
						rect.setSize(new Dimension(-1, -1));
						rect.setLocation(0, 0); 
						
						UMLCreateCommand.getInstance().setChild(subpart);
						UMLCreateCommand.getInstance().addPort(am, pf);
					 }
				 }
				}
			
			//20110809서동민 포트타입xml 동기화?
			
			OPRoSDataTypesElementModel elpDataType = new OPRoSDataTypesElementModel();
			OPRoSServiceTypesElementModel elpServiceType = new OPRoSServiceTypesElementModel();
			
			elpDataType = (OPRoSDataTypesElementModel)am.getClassModel().getUMLDataModel().getElementProperty("OPRoSDataTypesElementModel");
			elpServiceType = (OPRoSServiceTypesElementModel)am.getClassModel().getUMLDataModel().getElementProperty("OPRoSServiceTypesElementModel");
			
			Object obj;
			
			String contents[] = path.list();
			
			for(int i = 0; i < contents.length; i++){
				boolean add = true;
				
				if(!contents[i].equals(am.getName()+".xml")){
					Iterator itDataType = elpDataType.getChildren().iterator();
					while(itDataType.hasNext()){
						obj = itDataType.next();
						if(obj instanceof OPRoSDataTypeElementModel)
						{
							OPRoSDataTypeElementModel eleData = (OPRoSDataTypeElementModel)obj;
							System.out.println("ㄱㄱㄱㄱ" + eleData.getDataTypeFileName());
							if(eleData.getDataTypeFileName().equals(contents[i])){
								add = false;
							}
						}
					}
					
					Iterator itServiceType = elpServiceType.getChildren().iterator();
					while(itServiceType.hasNext()){
						obj = itServiceType.next();
						if(obj instanceof OPRoSServiceTypeElementModel){
							OPRoSServiceTypeElementModel eleService = (OPRoSServiceTypeElementModel)obj;
							System.out.println("ㄱㄱㄱㄱ" + eleService.getDataTypeFileName());
							if(eleService.getServiceTypeFileName().equals(contents[i])){
								add = false;
							}
						}
						
					}
					
					//타입 xml 추가
					if(add){
						DocComponentModel dm = new DocComponentModel();
						int div = dm.divisionPort(path.getPath() + File.separator +contents[i]);
						
						if(div == 1){
							OPRoSDataTypeElementModel newType = new OPRoSDataTypeElementModel();
							newType.setDataTypeFileName(contents[i]);
							newType.doLoad(path.getPath() + File.separator +contents[i]);
							if(newType.getDataTypeDoc() != null){
								elpDataType.addChild(newType);
								am.getClassModel().getUMLDataModel().setElementProperty("OPRoSDataTypesElementModel", elpDataType);
							}
							System.out.println("ADD DataPortXML : " + contents[i]);
						}
						
						else if(div==2){
							OPRoSServiceTypeElementModel newType = new OPRoSServiceTypeElementModel();
							newType.setServiceTypeFileName(contents[i]);
							newType.doLoad(path.getPath() + File.separator +contents[i]);
							if(newType.getServiceTypeDoc() != null){
								elpServiceType.addChild(newType);
								am.getClassModel().getUMLDataModel().setElementProperty("OPRoSServiceTypesElementModel", elpServiceType);
							}
							
							System.out.println("ADD ServicePortXML : " + contents[i]);
						}
						
					}
				}
			}
			
			
		
			
		}
//110822 E SDM  - 메소드 전체 수정


//110818 SDM S 포트명 변경 - 임시
public void setModels(String copName, String alter){
	java.util.ArrayList addComps = new java.util.ArrayList();
	
	int index = alter.indexOf(":");
	String strBefor = alter.substring(0, index);
	String strAfter = alter.substring(index+1);
	
	for(int i=0;i<this.getChildren().size();i++){
		Object obj = this.getChildren().get(i);
		if(obj instanceof AtomicComponentModel){
			AtomicComponentModel cep = (AtomicComponentModel)obj;
			UMLTreeModel ut = cep.getCoreUMLTreeModel();
			System.out.println("ut================================>"+ut);
			if(ut!=null && copName.equals(ut.getName())){
				 for(int i1=0;i1<cep.getPorts().size();i1++){
					 PortModel pm = (PortModel)cep.getPorts().get(i1);
					 if(strBefor.equals(pm.getName())){
						 pm.setName(strAfter);
					 }
				 }
			}
		}
	}
}
//110818 SDM E 포트명 변경 - 임시

//110817 SDM S 추가 - 포트명 변경 
//am - 포트명 변경할 대상 컴포넌트
//alter - "변경전 포트명:변경후 포트명"
public void setPortReName(AtomicComponentModel am1, String alter){
	int index = alter.indexOf(":");
	
	AtomicComponentModel am = am1.getCoreDiagramCmpModel();
	String strBefor = alter.substring(0, index);
	String strAfter = alter.substring(index+1);
	
	for(int i=0;i<am.getPorts().size();i++){
		PortModel pm = (PortModel)am.getPorts().get(i);
		if(pm.getName().equals(strBefor)){
//			UMLTreeModel treeObject = am1.getCorePortUMLTreeModel(pm);
//			treeObject.setName(strAfter);
			pm.setName(strAfter);
		}
	}
	
	//110818 SDM S 다이어그램 상 포트명 변경
	java.util.ArrayList addComps = new java.util.ArrayList();
	
	for(int i=0;i<CompAssemManager.getInstance().getDiagramList().size();i++){
		N3EditorDiagramModel nd = (N3EditorDiagramModel)CompAssemManager.getInstance().getDiagramList().get(i);
		if(nd!=null){
			for(int j=0;j<nd.getChildren().size();j++){
				Object obj = nd.getChildren().get(i);
				
				if(obj instanceof AtomicComponentModel){
					AtomicComponentModel cep = (AtomicComponentModel)obj;
					UMLTreeModel ut = cep.getCoreUMLTreeModel();
					
					if(ut!=null && am.getName().equals(ut.getName())){
						 for(int i1=0;i1<cep.getPorts().size();i1++){
							 PortModel pm = (PortModel)cep.getPorts().get(i1);
							 if(strBefor.equals(pm.getName())){
								 pm.setName(strAfter);
							 }
						 }
					}
					
				}
			}
		}
	}
	//110818 SDM E 다이어그램 상 포트명 변경
	
	
}
//118017 SDM E

//110817 SDM S 추가 - 포트 삭제
//am - 포트 삭제할 대상 컴포넌트
//alter - 삭제할 포트 이름
public void setPortDel(AtomicComponentModel am, String alter){
	for(int i=0;i<am.getPorts().size();i++){
		PortModel pm = (PortModel)am.getPorts().get(i);
		if(pm.getName().equals(alter)){
			this.deletePort(pm, am);
//			am.removePortTreeModel(pm);
		}
	}
}
//110817 E SDM

public void deletePort(UMLModel uMLModel,AtomicComponentModel am){ //110817 SDM 파라미터 AtomicComponentModel am 으로 변경
	

//	UMLModel uMLModel = (UMLModel)treeObject.getRefModel();
	//								UMLDeleteCommand.deleteConnections(uMLModel);
	PortModel pm = (PortModel)uMLModel;
	
	//110817 SDM treeObject값을 구하는 것을 getCorePortUMLTreeModel(pm)으로 변경
	UMLTreeModel treeObject = am.getCorePortUMLTreeModel(pm);
	Vector s=uMLModel.getTargetConnections();
	for(int j=0; j<s.size();j++){
		if( s.get(j) instanceof LineModel){
			LineModel lineModel=(LineModel)s.get(j);
			for (int i = 0; i < s.size(); i++) {
				LineModel wire = (LineModel)s.get(i);
				wire.detachSource();
				wire.detachTarget();
			}
			for (int i = 0; i < s.size(); i++) {
				LineModel wire = (LineModel)s.get(i);
				wire.detachSource();
				wire.detachTarget();
			}
		}						
	}
	s=uMLModel.getSourceConnections();
	for(int j=0; j<s.size();j++){
		if( s.get(j) instanceof LineModel){
			LineModel lineModel=(LineModel)s.get(j);
			for (int i = 0; i < s.size(); i++) {
				LineModel wire = (LineModel)s.get(i);
				wire.detachSource();
				wire.detachTarget();
			}
			for (int i = 0; i < s.size(); i++) {
				LineModel wire = (LineModel)s.get(i);
				wire.detachSource();
				wire.detachTarget();
			}
		}						
	}

//PKY 08060201 E 모델브라우져에서 삭제할경우 라인이 삭제가 안되는 경우 발생 수정
ProjectManager.getInstance().deleteUMLModel(treeObject);
try{
ProjectManager.getInstance().removeUMLNode(treeObject.getParent(), treeObject);
}
catch(Exception e){
e.printStackTrace();
}



ProjectManager.getInstance().getModelBrowser().searchComponentDiagramIdModel(pm.getUMLDataModel().getId());
java.util.ArrayList sm = ProjectManager.getInstance().getSearchModel();
//						    System.out.println("------>");
for(int i=0;i<sm.size();i++){
	UMLModel um =(UMLModel)sm.get(i);
	if(um instanceof PortModel){
		N3EditorDiagramModel nm =um.getN3EditorDiagramModelTemp();
		if(nm!=null){
			PortModel pm1 =(PortModel)um;
			ComponentModel cm = (ComponentModel)pm1.getPortContainerModel();
			cm.getPortManager().remove(pm1);
			nm.removeChild(um);

		}
	}

}


}

//public void addPort(PortProfile pf){
//	  um.createPort( pm, (UMLContainerModel)um.getAcceptParentModel());
//      UMLTreeParentModel to1 = (UMLTreeParentModel)um.getUMLTreeModel();
//		UMLTreeModel port =  new UMLTreeModel(pm.getName());
//		to1.addChild(port);
//		port.setRefModel(pm);
//		 pm.getUMLDataModel().setId(child.getID());
//          pm.getElementLabelModel().setPortId(child.getID());
//		pm.getElementLabelModel().setTreeModel(port);
//      ProjectManager.getInstance().getModelBrowser().refresh(to1);
//}



}
