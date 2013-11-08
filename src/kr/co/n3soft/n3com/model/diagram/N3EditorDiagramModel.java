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
		//20080325 PKY S �˻�
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

	//PKY 08080501 S �� ���������� ���̾�׷��� �̸��� �����ص� ���̾�׷� �̸����� ����ȵǴ� ����
	public void setName(String uname) {
//		uname = "diagram";
		name.setLabelContents(uname);

		//PKY 08080501 S Ʈ������ ���̾�׷� ��ü�� �ٸ� ���̾�׷��� �巹�� �� ��� ������Ƽ���� �̸� ����� ������� �ʰ�, ���� �巹���� ���̾�׷��� �̸��� �����ص� �巹���� ���� �̸��� ������� ����
		for(int i=0; i<this.frameModels.size();i++){
			FrameModel fm = (FrameModel)this.frameModels.get(i);
			fm.refreshDiagram(this);
		}
		//PKY 08080501 E Ʈ������ ���̾�׷� ��ü�� �ٸ� ���̾�׷��� �巹�� �� ��� ������Ƽ���� �̸� ����� ������� �ʰ�, ���� �巹���� ���̾�׷��� �̸��� �����ص� �巹���� ���� �̸��� ������� ����
		if(this.getUMLEditor()!=null)
			this.getUMLEditor().setTitleName(uname);

	}
	//PKY 08080501 E �� ���������� ���̾�׷��� �̸��� �����ص� ���̾�׷� �̸����� ����ȵǴ� ����

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

	
	//20080326 PKY S ���̾�׷� �巡�� �� �巡���� ���̾�׷� ���� ���濡 ���� �ݿ��� �ȵǴ¹���
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
	//20080326 PKY E ���̾�׷� �巡�� �� �巡���� ���̾�׷� ���� ���濡 ���� �ݿ��� �ȵǴ¹���

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
	//PKY 08080501 S �� ���������� ���̾�׷��� �̸��� �����ص� ���̾�׷� �̸����� ����ȵǴ� ����
	public void setPropertyValue(Object id, Object value) {
//      V1.02 WJH E 080813 S ���̾�׷� �̸��� �ƿ� �������� �ʵ��� ����
		if(value == null || value.toString().equals(""))
			return;
//      V1.02 WJH E 080813 E ���̾�׷� �̸��� �ƿ� �������� �ʵ��� ����
//		if(this.getName()!=null || !this.getName().trim().equals("")){
//			return;
//		}
//		value = "diagram";
		value=this.getUMLTreeModel().getParent().getName()+" diagram";//Khg 2010.05.19 ���ø����̼� �̸��� ��ġ�ϵ��� ����
		if (ID_NAME.equals(id)) {
			setName((String)value);
		}
	//PKY 08080501 E �� ���������� ���̾�׷��� �̸��� �����ص� ���̾�׷� �̸����� ����ȵǴ� ����
	}
	//PKY 08082201 S ���̾�׷� Frame Link���� �����߻�
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
				//������20110718>>�̸��񱳿��� id�񱳷� ����>>>0727 �̸���
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

//110822 S SDM  - �޼ҵ� ��ü ����
//			     1. �Ķ���� CompoentModel um���� AtomicCompoentModel tempAm�� ����
//               2. ����������  ����/������ ��Ʈ�� ����ȭ �ȵǵ��� ����
//				 3. �޼ҵ� ��ü�� ���ʿ��� �ּ��ҽ� ����
//				 4. ��Ʈ �Է�����(Type, Desc ��) ������� ���� (110823)
//20110726 ������-����
public void setModels(DocComponentModel doc, AtomicComponentModel tempAm, File path){
	
	java.util.ArrayList addComps = new java.util.ArrayList();
	AtomicComponentModel am = tempAm.getCoreDiagramCmpModel();
	
	// �����������Ʈ�� ��Ʈ���� �� ���� ���� ����Ʈ�� ����
	List modList = am.getReNameList();
	List delList = am.getDeleteList();

			System.out.println("ut================================>"+am);
			//������20110718>>�̸��񱳿��� id�񱳷� ����
			//������20110727>>�̸���
			System.out.println("umID>>>" + am.getId());
			System.out.println("docID>>>" + doc.getId());
			System.out.println("umName>>>" + am.getName());
			System.out.println("docName>>>" + doc.getName());
			
			
			
			if(am!=null && am.getName().equals(doc.getName())){
				System.out.println("asdasdasdadsasdasdasfsadfhlsdfjhljsdfhj");
				
				
				for(int i2=0;i2<doc.getPortlists().size();i2++){
					 PortProfile pf = (PortProfile)doc.getPortlists().get(i2);
					 boolean isAdd = true;
					 
					 // ���� �� ���������� ���� ��� ��Ʈ �߰� ����.
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
								 
								 //��Ʈ �Է�����(Type, Desc ��) ������� ����
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
			
			//20110809������ ��ƮŸ��xml ����ȭ?
			
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
							System.out.println("��������" + eleData.getDataTypeFileName());
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
							System.out.println("��������" + eleService.getDataTypeFileName());
							if(eleService.getServiceTypeFileName().equals(contents[i])){
								add = false;
							}
						}
						
					}
					
					//Ÿ�� xml �߰�
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
//110822 E SDM  - �޼ҵ� ��ü ����


//110818 SDM S ��Ʈ�� ���� - �ӽ�
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
//110818 SDM E ��Ʈ�� ���� - �ӽ�

//110817 SDM S �߰� - ��Ʈ�� ���� 
//am - ��Ʈ�� ������ ��� ������Ʈ
//alter - "������ ��Ʈ��:������ ��Ʈ��"
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
	
	//110818 SDM S ���̾�׷� �� ��Ʈ�� ����
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
	//110818 SDM E ���̾�׷� �� ��Ʈ�� ����
	
	
}
//118017 SDM E

//110817 SDM S �߰� - ��Ʈ ����
//am - ��Ʈ ������ ��� ������Ʈ
//alter - ������ ��Ʈ �̸�
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

public void deletePort(UMLModel uMLModel,AtomicComponentModel am){ //110817 SDM �Ķ���� AtomicComponentModel am ���� ����
	

//	UMLModel uMLModel = (UMLModel)treeObject.getRefModel();
	//								UMLDeleteCommand.deleteConnections(uMLModel);
	PortModel pm = (PortModel)uMLModel;
	
	//110817 SDM treeObject���� ���ϴ� ���� getCorePortUMLTreeModel(pm)���� ����
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

//PKY 08060201 E �𵨺��������� �����Ұ�� ������ ������ �ȵǴ� ��� �߻� ����
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
