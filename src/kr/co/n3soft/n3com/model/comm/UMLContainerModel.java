package kr.co.n3soft.n3com.model.comm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import kr.co.n3soft.n3com.model.activity.TextAttachModel;
import kr.co.n3soft.n3com.model.component.AtomicComponentModel;
import kr.co.n3soft.n3com.model.diagram.UMLDiagramModel;
import kr.co.n3soft.n3com.model.umlclass.CompartmentModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.draw2d.PositionConstants;
import org.eclipse.gef.examples.logicdesigner.LogicMessages;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;

public class UMLContainerModel extends UMLModel implements IN3UMLModelDeleteListener {
	static final long serialVersionUID = 1;
	public static String ID_ROUTER = "router"; //$NON-NLS-1$
	public static Integer ROUTER_MANUAL = new Integer(0);
	public static Integer ROUTER_MANHATTAN = new Integer(1);
	public static Integer ROUTER_SHORTEST_PATH = new Integer(2);
	private static int count;
	//ijs080429
	public  Image LOGIC_ICON = null; //$NON-NLS-1$
	protected List children = new ArrayList();
	protected UMLModelRuler leftRuler, topRuler;
	protected Integer connectionRouter = null;
	private boolean rulersVisibility = false;
	private boolean snapToGeometry = false;
	private boolean gridEnabled = false;
	private double zoom = 1.0;

	public UMLContainerModel() {
		
		LOGIC_ICON	= ProjectManager.getInstance().getImage(ProjectManager.getInstance().getElementType(this, -1));

//		LOGIC_ICON = 
		size.width = 100;
		size.height = 100;
		location.x = 20;
		location.y = 20;
		createRulers();
	}

	public void addChild(UMLElementModel child) {
		addChild(child, -1);
	}

	public void addChild(UMLElementModel child, int index) {
		//	child.setAcceptParentModel(this);
		try {
			//    System.out.println("child==>"+child);
			
			//20080729IJS
if(child!=null)
			if(child.getAcceptParentModel()!=null){
		        if(child.getAcceptParentModel() instanceof CompartmentModel
		        		&& this.getAcceptParentModel() instanceof TypeRefModel){
		        	CompartmentModel cm = (CompartmentModel)child.getAcceptParentModel();
		        	if(cm.getAcceptParentModel()  instanceof TypeRefModel){
		        	TypeRefModel tchild = (TypeRefModel)cm.getAcceptParentModel();
		        	TypeRefModel pchild = (TypeRefModel)this.getAcceptParentModel();
		        	if(!tchild.uMLDataModel.getId().equals(pchild.uMLDataModel.getId())){
		        		if(child instanceof AttributeElementModel){
		        			AttributeElementModel am = (AttributeElementModel)child;
		        			tchild.getAttributes().remove(am.getAttributeEditableTableItem());
		        			pchild.getAttributes().add(am.getAttributeEditableTableItem());
		        		}
		        		else if(child instanceof OperationElementModel){
		        			OperationElementModel am = (OperationElementModel)child;
		        			tchild.getOperations().remove(am.getAttributeEditableTableItem());
		        			pchild.getOperations().add(am.getAttributeEditableTableItem());
		        		}
		        		
		        	}
		        	}
		        }
			}
if(child!=null)
			child.setAcceptParentModel(this);
			//	if(child instanceof UMLModel){
			//		UMLModel um = (UMLModel)child;
			//		um.setParentModel(this);
			//	}
			//PKY 08072201 S 아웃라인에서 어트리뷰트 오퍼레이션 이동시 안되는문제 수정
//			if(ProjectManager.getInstance().getDrage()!=null)
//			if(child instanceof AttributeElementModel){
//					ClassModel classModel=null;
//					TypeRefModel typeRefModel=(TypeRefModel)this.acceptParentModel;
//					if(this.getParentModel() instanceof ClassifierModel){
//						if(((ClassifierModel)this.getParentModel()).getClassModel()!=null)
//						classModel=((ClassifierModel)this.getParentModel()).getClassModel();
//					}else if(this.getParentModel() instanceof ClassifierModelTextAttach) {
//						if(((ClassifierModelTextAttach)this.getParentModel()).getClassModel()!=null)
//							classModel=((ClassifierModelTextAttach)this.getParentModel()).getClassModel();
//					}
//					if(classModel!=null){
//						ArrayList arrayList= (ArrayList)classModel.getAttributes().clone();
//						AttributeEditableTableItem att= (AttributeEditableTableItem)ProjectManager.getInstance().getDrage();
//						arrayList.add(att.clone());
//						ProjectManager.getInstance().setDrage(null);
//						classModel.setAttributes(arrayList);
//						
//					}
//				
//			}
			//PKY 08072201 E 아웃라인에서 어트리뷰트 오퍼레이션 이동시 안되는문제 수정

			

			if (index >= 0)
				children.add(index, child);
			else
				children.add(child);
			fireChildAdded(CHILDREN, child, new Integer(index));

			//	if(this instanceof UMLContainerModel){
			//		UMLModel childUMLModel = (UMLModel)child;
			//		if(childUMLModel.getUMLTreeModel()!=null){
			//			childUMLModel.getUMLTreeModel().addN3UMLModelDeleteListener(this);
			//		}
			//	}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void createRulers() {
		leftRuler = new UMLModelRuler(false);
		topRuler = new UMLModelRuler(true);
	}

	public List getChildren() {
		return children;
	}
	
	public Object modChildrenItem(int index, AtomicComponentModel am) {
		children.remove(index);
		children.add(index, am);
		
		return this;
	}

	public Integer getConnectionRouter() {
		if (connectionRouter == null)
			connectionRouter = ROUTER_MANUAL;
		return connectionRouter;
	}

	public Image getIconImage() {
		return LOGIC_ICON;
	}

	public String getNewID() {
		return Integer.toString(count++);
	}

	public double getZoom() {
		return zoom;
	}

	/**
	 * Returns <code>null</code> for this model. Returns normal descriptors for all subclasses.
	 * @return  Array of property descriptors.
	 */
	public IPropertyDescriptor[] getPropertyDescriptors() {
		if (getClass().equals(UMLElementModel.class)) {
			ComboBoxPropertyDescriptor cbd = new ComboBoxPropertyDescriptor(ID_ROUTER,
					LogicMessages.PropertyDescriptor_LogicDiagram_ConnectionRouter,
					new String[] {
					LogicMessages.PropertyDescriptor_LogicDiagram_Manual,
					LogicMessages.PropertyDescriptor_LogicDiagram_Manhattan, LogicMessages.PropertyDescriptor_LogicDiagram_ShortestPath
			});
			cbd.setLabelProvider(new ConnectionRouterLabelProvider());
			return new IPropertyDescriptor[] { cbd };
		}
		return super.getPropertyDescriptors();
	}

	public Object getPropertyValue(Object propName) {
		if (propName.equals(ID_ROUTER))
			return connectionRouter;
		 //PKY 08062601 S LineText모델을 선택해도 해당되는 line에 대한정보가 보여주도록 수정
		if(this instanceof LineTextModel){			
			return ((LineTextModel)this).getLineModel().getPropertyValue(propName);			
		}
		 //PKY 08062601 E LineText모델을 선택해도 해당되는 line에 대한정보가 보여주도록 수정
		ProjectManager.getInstance().setSelectedModel(this);
		return super.getPropertyValue(propName);
	}

	public UMLModelRuler getRuler(int orientation) {
		UMLModelRuler result = null;
		switch (orientation) {
		case PositionConstants.NORTH:
			result = topRuler;
			break;
		case PositionConstants.WEST:
			result = leftRuler;
			break;
		}
		return result;
	}

	public boolean getRulerVisibility() {
		return rulersVisibility;
	}

	public boolean isGridEnabled() {
		return gridEnabled;
	}

	public boolean isSnapToGeometryEnabled() {
		return snapToGeometry;
	}

	private void readObject(java.io.ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
	}
	//PKY 08081101 S Zoder 이용시 선 끊기는문제 
	/**
	 * Z-ODER 이외의것은 사용하지 마세요 public void removeChild(UMLElementModel child,boolean is) 사용하지 마세요...
	 */
	public void removeChild(UMLElementModel child,boolean is) {
		//	child.setAcceptParentModel(this);
		//	//port
		//	if(child instanceof UseCaseModel){
			//	
			//		return;
			//		
			//	}
		//이유
		try {
			
			//20080721IJS 시작
			if(child instanceof UMLModel){
	            UMLModel uMLModel = (UMLModel)child;
	            for(int i=0;i<this.getChildren().size();i++){
	            	Object obj = this.getChildren().get(i);
	            	if(obj instanceof UMLModel){
	            		UMLModel ch = (UMLModel)obj;
	            		if(ch.getUMLDataModel().getId().equals(uMLModel.getUMLDataModel().getId())){
	            			child= ch;
	            		}
	            	}
	            }
		  }
			//20080721IJS 끝
			
			if (child instanceof UMLDiagramModel) {
				ProjectManager.getInstance().setDeleteModel((UMLModel)child);
			}
			//등록


            if (child instanceof PortContainerModel) {
                PortContainerModel ipc = (PortContainerModel)child;
               
//                ipc.deletePort(null, this);//PKY 08082201 S Z-Oder시 Port 삭제되는문제
            }
            if (child instanceof TextAttachModel) {
                TextAttachModel ipc = (TextAttachModel)child;
                ipc.removeTextAttachParentDiagram(this, null);
            }
            
            
			children.remove(child);
			fireChildRemoved(CHILDREN, child);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	//PKY 08081101 E Zoder 이용시 선 끊기는문제 

	public void removeChild(UMLElementModel child) {
		//	child.setAcceptParentModel(this);
		//	//port
		//	if(child instanceof UseCaseModel){
			//	
			//		return;
			//		
			//	}
		//이유
		try {
			
			//20080721IJS 시작
			if(child instanceof UMLModel){
	            UMLModel uMLModel = (UMLModel)child;
	            for(int i=0;i<this.getChildren().size();i++){
	            	Object obj = this.getChildren().get(i);
	            	if(obj instanceof UMLModel){
	            		UMLModel ch = (UMLModel)obj;
	            		if(ch.getUMLDataModel().getId().equals(uMLModel.getUMLDataModel().getId())){
	            			child= ch;
	            		}
	            	}
	            }
		  }
			//20080721IJS 끝
			
			if (child instanceof UMLDiagramModel) {
				ProjectManager.getInstance().setDeleteModel((UMLModel)child);
			}
			//등록


            if (child instanceof PortContainerModel) {
                PortContainerModel ipc = (PortContainerModel)child;
               
                ipc.deletePort(null, this);
            }
            if (child instanceof TextAttachModel) {
                TextAttachModel ipc = (TextAttachModel)child;
                ipc.removeTextAttachParentDiagram(this, null);
            }
            
            //20080721IJS 시작
            if(child instanceof UMLModel){
                UMLModel uMLModel = (UMLModel)child;
//    			UMLDeleteCommand.deleteConnections(uMLModel);
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
                }
            
            //20080721IJS 끝
            
			children.remove(child);
			fireChildRemoved(CHILDREN, child);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setConnectionRouter(Integer router) {
		Integer oldConnectionRouter = connectionRouter;
		connectionRouter = router;
		firePropertyChange(ID_ROUTER, oldConnectionRouter, connectionRouter);
	}

	public void setPropertyValue(Object id, Object value) {
		//PKY 08082201 S 팀 프로젝트 읽기전용일때 수정불가능하도록 작업

		System.out.println(ProjectManager.getInstance().isItemEnable(ProjectManager.getInstance().getSelectPropertyUMLElementModel()));
		if(ProjectManager.getInstance()!=null && ProjectManager.getInstance().getSelectPropertyUMLElementModel()!=null)					
			if(!ProjectManager.getInstance().isItemEnable(ProjectManager.getInstance().getSelectPropertyUMLElementModel()))
				return;	
		
		//PKY 08062601 S LineText모델을 선택해도 해당되는 line에 대한정보가 보여주도록 수정
		if (ID_ROUTER.equals(id))
			setConnectionRouter((Integer)value);
		else{			
			if(this instanceof LineTextModel){
				((LineTextModel)this).getLineModel().setPropertyValue(id, value);
			}
			else
				super.setPropertyValue(id, value);
		
		}
		//PKY 08062601 E LineText모델을 선택해도 해당되는 line에 대한정보가 보여주도록 수정
	}

	public void setRulerVisibility(boolean newValue) {
		rulersVisibility = newValue;
	}

	public void setGridEnabled(boolean isEnabled) {
		gridEnabled = isEnabled;
	}

	public void setSnapToGeometry(boolean isEnabled) {
		snapToGeometry = isEnabled;
	}

	public void setZoom(double zoom) {
		this.zoom = zoom;
	}

	public String toString() {
		return LogicMessages.LogicDiagram_LabelText;
	}

	public void removeModel(UMLModel uMLModel) {
		this.removeChild(uMLModel);
	}

	private class ConnectionRouterLabelProvider extends org.eclipse.jface.viewers.LabelProvider {
		public ConnectionRouterLabelProvider() {
			super();
		}

		public String getText(Object element) {
			if (element instanceof Integer) {
				Integer integer = (Integer)element;
				if (ROUTER_MANUAL.intValue() == integer.intValue())
					return LogicMessages.PropertyDescriptor_LogicDiagram_Manual;
				if (ROUTER_MANHATTAN.intValue() == integer.intValue())
					return LogicMessages.PropertyDescriptor_LogicDiagram_Manhattan;
				if (ROUTER_SHORTEST_PATH.intValue() == integer.intValue())
					return LogicMessages.PropertyDescriptor_LogicDiagram_ShortestPath;
			}
			return super.getText(element);
		}
	}
}
