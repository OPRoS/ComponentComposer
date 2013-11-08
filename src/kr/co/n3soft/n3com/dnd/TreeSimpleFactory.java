package kr.co.n3soft.n3com.dnd;

import kr.co.n3soft.n3com.model.activity.CentralBufferNodeModel;
import kr.co.n3soft.n3com.model.activity.DataStoreModel;
import kr.co.n3soft.n3com.model.activity.DecisionModel;
import kr.co.n3soft.n3com.model.activity.ExceptionModel;
import kr.co.n3soft.n3com.model.activity.FinalActiionModel;
import kr.co.n3soft.n3com.model.activity.FinalActivityModel;
import kr.co.n3soft.n3com.model.activity.FinalModel;
import kr.co.n3soft.n3com.model.activity.FinalObjectNodeModel;
import kr.co.n3soft.n3com.model.activity.FinalStrcuturedActivityModel;
import kr.co.n3soft.n3com.model.activity.FlowFinalModel;
import kr.co.n3soft.n3com.model.activity.HForkJoinModel;
import kr.co.n3soft.n3com.model.activity.HPartitionModel;
import kr.co.n3soft.n3com.model.activity.InitialModel;
import kr.co.n3soft.n3com.model.activity.ReceiveModel;
import kr.co.n3soft.n3com.model.activity.SendModel;
import kr.co.n3soft.n3com.model.activity.SynchModel;
import kr.co.n3soft.n3com.model.activity.VForkJoinModel;
import kr.co.n3soft.n3com.model.activity.VPartitionModel;
import kr.co.n3soft.n3com.model.comm.DataInputPortModel;
import kr.co.n3soft.n3com.model.comm.DataOutputPortModel;
import kr.co.n3soft.n3com.model.comm.ElementLabelModel;
import kr.co.n3soft.n3com.model.comm.EventInputPortModel;
import kr.co.n3soft.n3com.model.comm.EventOutputPortModel;
import kr.co.n3soft.n3com.model.comm.FinalPackageModel;
import kr.co.n3soft.n3com.model.comm.FrameModel;
import kr.co.n3soft.n3com.model.comm.MethodInputPortModel;
import kr.co.n3soft.n3com.model.comm.MethodOutputPortModel;
import kr.co.n3soft.n3com.model.comm.PortModel;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.component.ArtfifactModel;
import kr.co.n3soft.n3com.model.component.AtomicComponentModel;
import kr.co.n3soft.n3com.model.component.ComponentLibModel;
import kr.co.n3soft.n3com.model.component.ComponentModel;
import kr.co.n3soft.n3com.model.component.TemplateComponentModel;
import kr.co.n3soft.n3com.model.composite.PartModel;
import kr.co.n3soft.n3com.model.diagram.N3EditorDiagramModel;
import kr.co.n3soft.n3com.model.diagram.UMLDiagramModel;
import kr.co.n3soft.n3com.model.umlclass.FinalClassModel;
import kr.co.n3soft.n3com.model.umlclass.InterfaceModel;
import kr.co.n3soft.n3com.model.usecase.CollaborationModel;
import kr.co.n3soft.n3com.model.usecase.FinalActorModel;
import kr.co.n3soft.n3com.model.usecase.FinalBoundryModel;
import kr.co.n3soft.n3com.model.usecase.UseCaseModel;
import kr.co.n3soft.n3com.parser.ModelManager;
import kr.co.n3soft.n3com.parser.TeamProjectManager;
import kr.co.n3soft.n3com.project.browser.StrcuturedPackageTreeLibModel;
import kr.co.n3soft.n3com.project.browser.UMLTreeModel;
import kr.co.n3soft.n3com.project.browser.UMLTreeParentModel;
import kr.co.n3soft.n3com.projectmanager.CompAssemManager;
import kr.co.n3soft.n3com.projectmanager.LifecycleMethodPortModel;
import kr.co.n3soft.n3com.projectmanager.MonitoringMethodPortModel;
import kr.co.n3soft.n3com.projectmanager.PortManager;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.gef.requests.SimpleFactory;

public class TreeSimpleFactory extends SimpleFactory {
	public UMLModel uMLModel = null;
	public UMLTreeModel UMLTreeModel = null;
	N3EditorDiagramModel n3d;
	boolean isDrag = false;
	boolean isTest = true;

	public boolean isDrag() {
		return isDrag;
	}

	public void setDrag(boolean isDrag) {
		this.isDrag = isDrag;
	}

	public TreeSimpleFactory(Class aClass) {
		super(aClass);
	}

	public TreeSimpleFactory() {
		super(null);
	}

	public Object getNewObject() {
		try {
			return this.createModle(uMLModel);
		} catch (Exception exc) {
			return null;
		}
	}



	public void setUMLModel(UMLModel p) {
//		p.sets("sdsd");
		uMLModel = p;
	}

	public UMLModel getUMLModel() {
		return uMLModel;
	}

	public void setUMLTreeModel(UMLTreeModel p) {
		UMLTreeModel = p;
	}

	public UMLTreeModel getUMLTreeModel() {
		return UMLTreeModel;
	}
	public void setRef(UMLModel source,UMLModel target){
		
//		}
	}
	//20080811IJS 시작
	public UMLModel createModle(int type) {
		UMLModel newModel = null;
		if(type==0){//유즈케이스
			//path.append("Package");
		}//액터
		if(type==1){//유즈케이스
			//path.append("Diagram");
		}//액터
		if(type==2){//유즈케이스
			newModel = new UseCaseModel();
			//path.append("Usecase");
		}//액터
		else if(type==3){
			
		}//바운더
		else if(type==4){
			newModel = new FinalBoundryModel();
			//path.append("Boundary");
		}//컬레보레이션
		else if(type==5){
			newModel = new CollaborationModel();
			//path.append("Collaboration");
		}//클래스
		else if(type==6){
			newModel = new FinalClassModel();
		 if(n3d!=null){
				if(n3d!=null && n3d.getDiagramType()==12){



				}
			}
			//path.append("Class");
		}//인터페이스
		else if(type==7){
			newModel = new InterfaceModel();
			if(ProjectManager.getInstance().getUMLEditor()!=null){
				N3EditorDiagramModel nd =(N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram();
				if(nd!=null && nd.getDiagramType()==12){



				}
			}
			//path.append("Interface");
		}//Enumeration
		else if(type==8){
			newModel = new FinalClassModel();
			//path.append("Enumeration");
		}//Exception
		else if(type==9){
			newModel = new ExceptionModel();
			//path.append("Exception");
		}//액티비티
		else if(type==10){
			newModel = new FinalActivityModel();
			//path.append("Activity");
		}//액션
		else if(type==11){
			newModel = new FinalActiionModel();
			//path.append("Action");
		}//Send
		else if(type==12){
			newModel = new SendModel();
			//path.append("Send");
		}//Receive
		else if(type==13){
			newModel = new ReceiveModel();
			//path.append("Receive");
		}//Initial
		else if(type==14){
			newModel = new InitialModel();
			//path.append("Initial");
		}//Final
		else if(type==15){
			newModel = new FinalModel();
			//path.append("Final");
		}//FlowFinal
		else if(type==16){
			newModel = new FlowFinalModel();
			//path.append("Flow_final");
		}//Synch
		else if(type==17){
			newModel = new SynchModel();
			//path.append("Synch");
		}//Decision
		else if(type==18){
			newModel = new DecisionModel();
			//path.append("Decision");
		}//Object
		else if(type==19){
			newModel = new FinalObjectNodeModel();
			//path.append("Object");
		}//CentralBufferNode
		else if(type==20){
			newModel = new CentralBufferNodeModel();
			//path.append("Centralbuffernode");
		}//DataStore
		else if(type==21){
			newModel = new DataStoreModel();
			//path.append("Datastore");
		}//Swimlaine
		else if(type==22){
			newModel = new HPartitionModel();
			//path.append("Partition");
		}
		else if(type==23){
			newModel = new VPartitionModel();
			//path.append("Usecase");
		}//Fork/Join
		else if(type==24){
			newModel = new HForkJoinModel();
			//path.append("Forkjoin");
		}//Fork/Join
		else if(type==25){
			newModel = new VForkJoinModel();
			//path.append("Forkjoin");
		}//StrcuturedActivity
		else if(type==26){
			newModel = new FinalStrcuturedActivityModel();
			//path.append("Structured_activity");
		}//Part
		else if(type==27){
			newModel = new PartModel();
			//path.append("Part");
		}//LifeLine
		else if(type==50){
			
			//path.append("LifeLine");
		}//Component
		else if(type==28){
			
		}//Component
		else if(type==29){
			newModel = new ComponentModel();//PKY 08082201 S 프로젝트 모델 뷰 상태에서 생성 시 컴퓨넌트->유즈케이스,State LifeLine -> 이름표시못함
			//path.append("Component");
		}//Artfifact
		else if(type==30){
			newModel = new ArtfifactModel();//PKY 08082201 S 프로젝트 모델 뷰 상태에서 생성 시 컴퓨넌트->유즈케이스,State LifeLine -> 이름표시못함
			//path.append("Artifact");
		}//State
		else if(type==31){
			
		}//StrcuturedState
		else if(type==32){
			
		}//History
		else if(type==33){
			
		}//Terminate
		else if(type==34){
			
		}//Node
		else if(type==35){
//			newModel = new NodeModel();
			//path.append("Node");
		}//EntryPoint
		else if(type==36){
			
		}//Device
		else if(type==37){
//			newModel = new DeviceModel();
			//path.append("Device");
		}//ExecutionEnvironment
		else if(type==38){
//			newModel = new ExecutionEnvironmentModel();
			//path.append("Executionenvironment");
		}//DeploymentSpecification
		else if(type==39){
//			newModel = new DeploymentSpecificationModel();
			//path.append("Deployment_spec");
		}//StateLifeline
		
		else if(type==41){
			newModel = new UseCaseModel();
			//path.append("Usecase");
		}
		else if(type==42){
			newModel = new UseCaseModel();
			//path.append("tank");
		}

		else if(type==200){
			newModel = new UseCaseModel();
			//path.append("basic");
		}
		else if(type==201){
			newModel = new UseCaseModel();
			//path.append("alternative");
		}
		else if(type==202){
			newModel = new UseCaseModel();
			//path.append("exception");
		}
		else if(type==502){//PKY 08052101 S 컨테이너에서 그룹으로 변경
			newModel = new UseCaseModel();
			//path.append("partiton");
		}//PKY 08052101 E 컨테이너에서 그룹으로 변경
		
		newModel.setExistModel(false);
		return  newModel;
	}
	//20080811IJS 끝


	//등록
	public UMLModel createModle(UMLModel model) {
		//PKY 08081801 S 모델 브라우져에서 드래그해서 동시에 다이어그램에 같은객체가 있을경우 에러 발생 문제 수정
//		if(ProjectManager.getInstance()!=null && ProjectManager.getInstance().getUMLEditor()!=null && ProjectManager.getInstance().getUMLEditor().getDiagram() !=null){
//		N3EditorDiagramModel n3diagram =(N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram();
//		if(n3diagram!=null){
//			for(int i = 0; i < n3diagram.getChildren().size(); i ++)
//				if(n3diagram.getChildren().get(i)==model){
//					
//					return null;	
//				}
						
//		}
		StrcuturedPackageTreeLibModel to1 = null;
		UMLTreeParentModel up = null;
		N3EditorDiagramModel parent = null;
		if(ProjectManager.getInstance().getUMLEditor()!=null){
		 parent = (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram();
		to1 = null;
		up = (UMLTreeParentModel)parent.getUMLTreeModel().getParent();
		if(parent instanceof N3EditorDiagramModel && this.isDrag && model instanceof ComponentModel)
			for(int i1 = 0; i1 < parent.getChildren().size(); i1++){
				if(parent.getChildren().get(i1) instanceof UMLModel){
					UMLModel umlModel = (UMLModel)parent.getChildren().get(i1);
					if(umlModel.getUMLTreeModel()!=null && model.getUMLTreeModel()!=null)
						if(umlModel.getUMLTreeModel() == model.getUMLTreeModel() ){
//							ijs 091126
							
							to1 = new StrcuturedPackageTreeLibModel(model.getName());
							up = (UMLTreeParentModel)parent.getUMLTreeModel().getParent();
							up.addChild(to1);
						    if(model instanceof ComponentModel){
						    	ComponentModel cm = (ComponentModel)model;
						    	if(cm.isInstance()){
						    		UMLTreeModel ut = (UMLTreeModel)cm.getInstanceUMLTreeModel();
						    		if(ut!=null)
						    			model = (UMLModel)ut.getRefModel();
						    	}
						    }
							
//							ProjectManager.getInstance().getModelBrowser().refresh(up);
//							up.setRefModel(p);
//							return;
						}
				}
			}
			
//		if(to1==null&& this.isDrag && model instanceof TemplateComponentModel){
//			
//		}
//		else 
		if(to1==null&& this.isDrag && model instanceof ComponentLibModel){
				to1 = new StrcuturedPackageTreeLibModel(model.getName());
				up = (UMLTreeParentModel)parent.getUMLTreeModel().getParent();
				up.addChild(to1);
			    if(model instanceof ComponentModel){
			    	ComponentModel cm = (ComponentModel)model;
			    	if(cm.isInstance()){
			    		UMLTreeModel ut = (UMLTreeModel)cm.getInstanceUMLTreeModel();
			    		if(ut!=null)
			    			model = (UMLModel)ut.getRefModel();
			    	}
			    }
			}
			else if(to1==null&& this.isDrag && model instanceof AtomicComponentModel){
				to1 = new StrcuturedPackageTreeLibModel(model.getName());
//				RootCmpEdtTreeModel rcet = ProjectManager.getInstance().getModelBrowser().getN3ViewContentProvider().getRcetm();
				up = (UMLTreeParentModel)parent.getUMLTreeModel().getParent();
				up.addChild(to1);
//			    if(model instanceof ComponentModel){
//			    	ComponentModel cm = (ComponentModel)model;
//			    	if(cm.isInstance()){
//			    		UMLTreeModel ut = (UMLTreeModel)cm.getInstanceUMLTreeModel();
//			    		if(ut!=null)
//			    			model = (UMLModel)ut.getRefModel();
//			    	}
//			    }
			}
			
		}
		//PKY 08081801 E 모델 브라우져에서 드래그해서 동시에 다이어그램에 같은객체가 있을경우 에러 발생 문제 수정

		UMLModel newModel = null;
		if (model instanceof UseCaseModel){
			newModel = new UseCaseModel(model.uMLDataModel);
		}
		else if (model instanceof FinalActorModel){
			newModel = new FinalActorModel(model.uMLDataModel);
			//PKY 08090101 S 시퀀스 다이어그램이 열린상태에서 링크에 있는 클래스 인터페이스 Actor를 불러들이면 시퀀스모양처럼 생성되는문제
			 if(n3d!=null){
				if(n3d!=null && n3d.getDiagramType()==12){
					
				}
			}else if(ProjectManager.getInstance().getUMLEditor()!=null){
				N3EditorDiagramModel nd =(N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram();
				if(nd!=null && nd.getDiagramType()==12){

				
				

				}
			}
			//PKY 08090101 E 시퀀스 다이어그램이 열린상태에서 링크에 있는 클래스 인터페이스 Actor를 불러들이면 시퀀스모양처럼 생성되는문제

		}
		else if (model instanceof FinalBoundryModel){

			newModel = new FinalBoundryModel(model.uMLDataModel);
		}
//		else if (model instanceof CollaborationModel)
//			newModel = new CollaborationModel(model.uMLDataModel);
		else if (model instanceof FinalPackageModel)
			newModel = new FinalPackageModel(model.uMLDataModel);
		else if (model instanceof FinalClassModel) {
			//합성클래스
			newModel = new FinalClassModel(model.uMLDataModel);
			//PKY 08090101 S 시퀀스 다이어그램이 열린상태에서 링크에 있는 클래스 인터페이스 Actor를 불러들이면 시퀀스모양처럼 생성되는문제
			 if(n3d!=null){
				if(n3d!=null && n3d.getDiagramType()==12){

				

				}
			}else if(ProjectManager.getInstance().getUMLEditor()!=null){
				N3EditorDiagramModel nd =(N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram();
				if(nd!=null && nd.getDiagramType()==12 ){

				
//					 ProjectManager.getInstance().addUMLModel(null, (UMLTreeParentModel)nd.getUMLTreeModel().getParent(), newModel, -1,12);//PKY 08081801 S 참조하여 LifeLine생성할경우 모델브라우져에 안나오는 문제

				}
			}
			//PKY 08090101 E 시퀀스 다이어그램이 열린상태에서 링크에 있는 클래스 인터페이스 Actor를 불러들이면 시퀀스모양처럼 생성되는문제

		}
		else if (model instanceof InterfaceModel) {
			//합성클래스
			newModel = new InterfaceModel(model.uMLDataModel);

			if(n3d!=null){
			if(n3d!=null && n3d.getDiagramType()==12){
				
			}
			}else if(ProjectManager.getInstance().getUMLEditor()!=null){
				N3EditorDiagramModel nd =(N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram();
				//PKY 08090101 S 시퀀스 다이어그램이 열린상태에서 링크에 있는 클래스 인터페이스 Actor를 불러들이면 시퀀스모양처럼 생성되는문제

				if(nd!=null && nd.getDiagramType()==12){

				


				}
			}
			//PKY 08090101 E 시퀀스 다이어그램이 열린상태에서 링크에 있는 클래스 인터페이스 Actor를 불러들이면 시퀀스모양처럼 생성되는문제

			//PKY 08081801 E 인터페이스 드래그 시 안되는 문제

		}
		else if (model instanceof ExceptionModel)
			newModel = new ExceptionModel(model.uMLDataModel);
		else if (model instanceof FinalStrcuturedActivityModel)
			newModel = new FinalStrcuturedActivityModel(model.uMLDataModel);
		else if (model instanceof FinalActivityModel)
			newModel = new FinalActivityModel(model.uMLDataModel);
		else if (model instanceof FinalActiionModel)
			newModel = new FinalActiionModel(model.uMLDataModel);
		else if (model instanceof SendModel)
			newModel = new SendModel(model.uMLDataModel);
		else if (model instanceof ReceiveModel)
			newModel = new ReceiveModel(model.uMLDataModel);
		else if (model instanceof InitialModel)
			newModel = new InitialModel(model.uMLDataModel);
		else if (model instanceof FinalModel)
			newModel = new FinalModel(model.uMLDataModel);
		else if (model instanceof FlowFinalModel)
			newModel = new FlowFinalModel(model.uMLDataModel);
		else if (model instanceof SynchModel)
			newModel = new SynchModel(model.uMLDataModel);
		else if (model instanceof DecisionModel)
			newModel = new DecisionModel(model.uMLDataModel);
		else if (model instanceof FinalObjectNodeModel)
			newModel = new FinalObjectNodeModel(model.uMLDataModel);
		else if (model instanceof CentralBufferNodeModel)
			newModel = new CentralBufferNodeModel(model.uMLDataModel);
		else if (model instanceof DataStoreModel)
			newModel = new DataStoreModel(model.uMLDataModel);
		else if (model instanceof HPartitionModel)
			newModel = new HPartitionModel(model.uMLDataModel);
		else if (model instanceof VPartitionModel)
			newModel = new VPartitionModel(model.uMLDataModel);
		else if (model instanceof HForkJoinModel)
			newModel = new HForkJoinModel(model.uMLDataModel);
		else if (model instanceof VForkJoinModel)
			newModel = new VForkJoinModel(model.uMLDataModel);
		else if (model instanceof PartModel)
			newModel = new PartModel(model.uMLDataModel);
		
		else if(model instanceof ComponentLibModel){
			//ijs 091126			
			ComponentLibModel cmodel = (ComponentLibModel)model;
			
			if(this.isDrag && to1!=null){
					newModel = (UMLModel)model.clone();
					ComponentLibModel cl = (ComponentLibModel)newModel;
					cl.setInstance(true);
					cl.setInstanceUMLTreeModel(cmodel.getUMLTreeModel());
					cl.setId(null);
					cl.getId();
			
				
				
			}
			else {
				newModel = new ComponentLibModel(model.uMLDataModel);
				
			}
			
//			newModel.setSize(new Dimension(10,10));
			
			////etri 디폴트 컴포넌트 생성
			ComponentLibModel newModelpm = 	(ComponentLibModel)newModel;
			System.out.println("id1:==================>"+newModelpm.getUMLDataModel().getId());
			System.out.println("id2:==================>"+model.uMLDataModel.getId());
			PortManager pm = cmodel.getPortManager();
			UMLTreeParentModel upm = (UMLTreeParentModel)cmodel.getUMLTreeModel();
			if(ProjectManager.getInstance().isLoad()){
				for(int i=0;i<pm.getPorts().size();i++){
					
					PortModel pm1 = (PortModel)pm.getPorts().get(i);
					PortModel pm2 = null;
					if(pm1 instanceof MonitoringMethodPortModel){
						pm2 = new MonitoringMethodPortModel(newModelpm);
//					newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
					}
					else if(pm1 instanceof LifecycleMethodPortModel){
						pm2 = new LifecycleMethodPortModel(newModelpm);
//					newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
					}
					else if(pm1 instanceof MethodOutputPortModel){
						pm2 = new MethodOutputPortModel(newModelpm);
//					newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
					}
					else if(pm1 instanceof MethodInputPortModel){
						pm2 = new MethodInputPortModel(newModelpm);
//						pm2.getAttachElementLabelModel().setText(pm1.getAttachElementLabelModel().getLabelContents());
//					newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
					}
					else if(pm1 instanceof DataInputPortModel){
						pm2 = new DataInputPortModel(newModelpm);
//						newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
					}
					else if(pm1 instanceof DataOutputPortModel){
						pm2 = new DataOutputPortModel(newModelpm);
//						newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
					}
					else if(pm1 instanceof EventInputPortModel){
						pm2 = new EventInputPortModel(newModelpm);
//						newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
					}
					else if(pm1 instanceof EventOutputPortModel){
						pm2 = new EventOutputPortModel(newModelpm);
//						newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
					}
					if(pm1.getElementLabelModel().isReadOnly()){
						pm2.getElementLabelModel().setReadOnly(true);
					}
					pm2.getElementLabelModel().setPortId(pm1.getID());
					pm2.getUMLDataModel().setId(pm1.getID());
					pm2.getAttachElementLabelModel().setText(pm1.getAttachElementLabelModel().getLabelContents());
					pm2.getElementLabelModel().setTreeModel(pm1.getUMLTreeModel());
					pm2.setType(pm1.getType());
					newModelpm.getPortManager().getPorts().add(pm2);
					
//					newModelpm.getUMLDataModel().setId(pm1.getID());
					
				}
			}
			else	if(!ProjectManager.getInstance().isCopy() && !ProjectManager.getInstance().isLoad() && (!(this.isDrag && to1!=null))){
			for(int i=0;i<upm.getChildren().length;i++){
				UMLTreeModel um = (UMLTreeModel)upm.getChildren()[i];
				if(!(um.getRefModel() instanceof PortModel))
					continue;
				PortModel pm1 = (PortModel)um.getRefModel();
				PortModel pm2 = null;
				
				if(pm1 instanceof MonitoringMethodPortModel){
					pm2 = new MonitoringMethodPortModel(newModelpm);
//				newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
				}
				else if(pm1 instanceof LifecycleMethodPortModel){
					pm2 = new LifecycleMethodPortModel(newModelpm);
//				newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
				}
				else if(pm1 instanceof MethodOutputPortModel){
					pm2 = new MethodOutputPortModel(newModelpm);
//				newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
				}
				else if(pm1 instanceof MethodInputPortModel){
					pm2 = new MethodInputPortModel(newModelpm);
					
//				newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
				}
				else if(pm1 instanceof DataInputPortModel){
					pm2 = new DataInputPortModel(newModelpm);
//					newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
				}
				else if(pm1 instanceof DataOutputPortModel){
					pm2 = new DataOutputPortModel(newModelpm);
//					newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
				}
				else if(pm1 instanceof EventInputPortModel){
					pm2 = new EventInputPortModel(newModelpm);
//					newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
				}
				else if(pm1 instanceof EventOutputPortModel){
					pm2 = new EventOutputPortModel(newModelpm);
//					newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
				}
//				if(pm1.getElementLabelModel().isReadOnly()){
//					pm2.getElementLabelModel().setReadOnly(true);
//				}
				pm2.getElementLabelModel().setReadOnly(true);
				pm2.getElementLabelModel().setPortId(pm1.getID());
				pm2.getUMLDataModel().setId(pm1.getID());
				pm2.getAttachElementLabelModel().setText(pm1.getAttachElementLabelModel().getLabelContents());
				pm2.getElementLabelModel().setTreeModel(um);
				newModelpm.getPortManager().getPorts().add(pm2);
				pm2.setType(pm1.getType());
//				newModelpm.getUMLDataModel().setId(pm1.getID());
				
			}
			CompAssemManager.getInstance().autoLayoutPort(newModelpm.getPortManager().getPorts());
			}
			else if(this.isDrag && to1!=null){
				
				for(int i=0;i<upm.getChildren().length;i++){
					UMLTreeModel um = (UMLTreeModel)upm.getChildren()[i];
					if(!(um.getRefModel() instanceof PortModel))
						continue;
					PortModel pm1 = (PortModel)um.getRefModel();
					PortModel pm2 = null;
					
					if(pm1 instanceof MonitoringMethodPortModel){
						pm2 = new MonitoringMethodPortModel(newModelpm);
//					newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
					}
					else if(pm1 instanceof LifecycleMethodPortModel){
						pm2 = new LifecycleMethodPortModel(newModelpm);
//					newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
					}
					else if(pm1 instanceof MethodOutputPortModel){
						pm2 = new MethodOutputPortModel(newModelpm);
//					newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
					}
					else if(pm1 instanceof MethodInputPortModel){
						pm2 = new MethodInputPortModel(newModelpm);
						
//					newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
					}
					else if(pm1 instanceof DataInputPortModel){
						pm2 = new DataInputPortModel(newModelpm);
//						newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
					}
					else if(pm1 instanceof DataOutputPortModel){
						pm2 = new DataOutputPortModel(newModelpm);
//						newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
					}
					else if(pm1 instanceof EventInputPortModel){
						pm2 = new EventInputPortModel(newModelpm);
//						newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
					}
					else if(pm1 instanceof EventOutputPortModel){
						pm2 = new EventOutputPortModel(newModelpm);
//						newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
					}
//					if(pm1.getElementLabelModel().isReadOnly()){
						pm2.getElementLabelModel().setReadOnly(true);
//					}
					
					UMLTreeModel port = new UMLTreeModel(pm1.getAttachElementLabelModel().getLabelContents());
					to1.addChild(port);
					port.setRefModel(pm2);
					pm2.getElementLabelModel().setTreeModel(port);
					
					pm2.getElementLabelModel().setPortId(pm2.getID());
					pm2.getUMLDataModel().setId(pm2.getID());
					pm2.getAttachElementLabelModel().setText(pm1.getAttachElementLabelModel().getLabelContents());
					pm2.getElementLabelModel().setTreeModel(port);
					newModelpm.getPortManager().getPorts().add(pm2);
					pm2.setType(pm1.getType());
					  ProjectManager.getInstance().getModelBrowser().refresh(to1);
//					newModelpm.getUMLDataModel().setId(pm1.getID());
					
				}
				CompAssemManager.getInstance().autoLayoutPort(newModelpm.getPortManager().getPorts());
				
			}
//			newModel.setSize(new Dimension(150,300));
			
		
		}
		
		else if (model instanceof ComponentModel){
			if(((ComponentModel) model).isInstance()){
				newModel = new ComponentLibModel(model.uMLDataModel);
			}
			else if(model instanceof AtomicComponentModel){
				
				newModel = new AtomicComponentModel(model.uMLDataModel);
				
				if(this.isDrag && to1!=null){
//					if(this.isDrag && to1!=null){

						newModel = (UMLModel)model.clone();
//						AtomicComponentModel cl = (AtomicComponentModel)newModel;
////						cl.setInstance(true);
////						cl.setInstanceUMLTreeModel(model.getUMLTreeModel());
////						cl.setId(null);
//						cl.getId();
				
					
					
//				}
					AtomicComponentModel newModelpm = (AtomicComponentModel)newModel; 
					AtomicComponentModel cmodel = (AtomicComponentModel)model;
					
				    
					PortManager pm = cmodel.getPortManager();
					UMLTreeParentModel upm = (UMLTreeParentModel)model.getUMLTreeModel();
					newModelpm.setCoreUMLTreeModel(upm);
					
					for(int i=0;i<upm.getChildren().length;i++){
						UMLTreeModel um = (UMLTreeModel)upm.getChildren()[i];
						if(!(um.getRefModel() instanceof PortModel))
							continue;
						PortModel pm1 = (PortModel)um.getRefModel();
						PortModel pm2 = null;
						
						if(pm1 instanceof MonitoringMethodPortModel){
							pm2 = new MonitoringMethodPortModel(newModelpm);
//						newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
						}
						else if(pm1 instanceof LifecycleMethodPortModel){
							pm2 = new LifecycleMethodPortModel(newModelpm);
//						newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
						}
						else if(pm1 instanceof MethodOutputPortModel){
							pm2 = new MethodOutputPortModel(newModelpm);
//						newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
						}
						else if(pm1 instanceof MethodInputPortModel){
							pm2 = new MethodInputPortModel(newModelpm);
							
//						newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
						}
						else if(pm1 instanceof DataInputPortModel){
							pm2 = new DataInputPortModel(newModelpm);
//							newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
						}
						else if(pm1 instanceof DataOutputPortModel){
							pm2 = new DataOutputPortModel(newModelpm);
//							newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
						}
						else if(pm1 instanceof EventInputPortModel){
							pm2 = new EventInputPortModel(newModelpm);
//							newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
						}
						else if(pm1 instanceof EventOutputPortModel){
							pm2 = new EventOutputPortModel(newModelpm);
//							newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
						}
//						if(pm1.getElementLabelModel().isReadOnly()){
							pm2.getElementLabelModel().setReadOnly(true);
//						}
						
//						UMLTreeModel port = new UMLTreeModel(pm1.getAttachElementLabelModel().getLabelContents());
//						to1.addChild(port);
//						port.setRefModel(pm2);
						pm2.getElementLabelModel().setTreeModel(um);
						
						pm2.getElementLabelModel().setPortId(pm2.getID());
						pm2.getUMLDataModel().setId(pm2.getID());
						pm2.getAttachElementLabelModel().setText(pm1.getAttachElementLabelModel().getLabelContents());
//						pm2.getElementLabelModel().setTreeModel(port);
//						newModelpm.getPortManager().getPorts().add(pm2);
						pm2.setType(pm1.getType());
						  ProjectManager.getInstance().getModelBrowser().refresh(to1);
//						newModelpm.getUMLDataModel().setId(pm1.getID());
						
					}
//						newModelpm.getUMLDataModel().setId(pm1.getID());
						
					}
			}
			
//			else if(this.isDrag){
//				
//			}
			else
				newModel = new ComponentModel(model.uMLDataModel);
			if(parent!=null){
				String newName = parent.getCompName(newModel.getName());
				newModel.setName(newName);
			}
			ComponentModel cmodel = (ComponentModel)model;
			
			////etri 디폴트 컴포넌트 생성
			ComponentModel newModelpm = 	(ComponentModel)newModel;
			System.out.println("id1:==================>"+newModelpm.getUMLDataModel().getId());
			System.out.println("id2:==================>"+model.uMLDataModel.getId());
			PortManager pm = cmodel.getPortManager();
			UMLTreeParentModel upm = (UMLTreeParentModel)cmodel.getUMLTreeModel();
			if(ProjectManager.getInstance().isLoad()){
				for(int i=0;i<pm.getPorts().size();i++){
					
					PortModel pm1 = (PortModel)pm.getPorts().get(i);
					PortModel pm2 = null;
					if(pm1 instanceof MonitoringMethodPortModel){
						pm2 = new MonitoringMethodPortModel(newModelpm);
//					newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
					}
					else if(pm1 instanceof LifecycleMethodPortModel){
						pm2 = new LifecycleMethodPortModel(newModelpm);
//					newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
					}
					else if(pm1 instanceof MethodOutputPortModel){
						pm2 = new MethodOutputPortModel(newModelpm);
//					newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
					}
					else if(pm1 instanceof MethodInputPortModel){
						pm2 = new MethodInputPortModel(newModelpm);
//						pm2.getAttachElementLabelModel().setText(pm1.getAttachElementLabelModel().getLabelContents());
//					newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
					}
					else if(pm1 instanceof DataInputPortModel){
						pm2 = new DataInputPortModel(newModelpm);
//						newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
					}
					else if(pm1 instanceof DataOutputPortModel){
						pm2 = new DataOutputPortModel(newModelpm);
//						newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
					}
					else if(pm1 instanceof EventInputPortModel){
						pm2 = new EventInputPortModel(newModelpm);
//						newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
					}
					else if(pm1 instanceof EventOutputPortModel){
						pm2 = new EventOutputPortModel(newModelpm);
//						newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
					}
					pm2.getElementLabelModel().setPortId(pm1.getID());
					pm2.getUMLDataModel().setId(pm1.getID());
					pm2.getAttachElementLabelModel().setText(pm1.getAttachElementLabelModel().getLabelContents());
					pm2.getElementLabelModel().setTreeModel(pm1.getUMLTreeModel());
					newModelpm.getPortManager().getPorts().add(pm2);
//					newModelpm.getUMLDataModel().setId(pm1.getID());
					
				}
			}
			if(!ProjectManager.getInstance().isCopy() && !ProjectManager.getInstance().isLoad()){
			for(int i=0;i<upm.getChildren().length;i++){
				UMLTreeModel um = (UMLTreeModel)upm.getChildren()[i];
				if(!(um.getRefModel() instanceof PortModel))
					continue;
				PortModel pm1 = (PortModel)um.getRefModel();
				PortModel pm2 = null;
				
				if(pm1 instanceof MonitoringMethodPortModel){
					pm2 = new MonitoringMethodPortModel(newModelpm);
//				newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
				}
				else if(pm1 instanceof LifecycleMethodPortModel){
					pm2 = new LifecycleMethodPortModel(newModelpm);
//				newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
				}
				else if(pm1 instanceof MethodOutputPortModel){
					pm2 = new MethodOutputPortModel(newModelpm);
//				newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
				}
				else if(pm1 instanceof MethodInputPortModel){
					pm2 = new MethodInputPortModel(newModelpm);
					
//				newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
				}
				else if(pm1 instanceof DataInputPortModel){
					pm2 = new DataInputPortModel(newModelpm);
//					newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
				}
				else if(pm1 instanceof DataOutputPortModel){
					pm2 = new DataOutputPortModel(newModelpm);
//					newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
				}
				else if(pm1 instanceof EventInputPortModel){
					pm2 = new EventInputPortModel(newModelpm);
//					newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
				}
				else if(pm1 instanceof EventOutputPortModel){
					pm2 = new EventOutputPortModel(newModelpm);
//					newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
				}
				if(((ComponentModel) model).isInstance()){
					pm2.getElementLabelModel().setReadOnly(true);
				}
//				if(pm1.getElementLabelModel().isReadOnly()){
//					pm2.getElementLabelModel().setReadOnly(true);
//				}
				pm2.getElementLabelModel().setPortId(pm1.getID());
				pm2.getUMLDataModel().setId(pm1.getID());
				pm2.getAttachElementLabelModel().setText(pm1.getAttachElementLabelModel().getLabelContents());
				pm2.getElementLabelModel().setTreeModel(um);
				newModelpm.getPortManager().getPorts().add(pm2);
//				newModelpm.getUMLDataModel().setId(pm1.getID());
				
			}
			CompAssemManager.getInstance().autoLayoutPort(newModelpm.getPortManager().getPorts());
			}
			newModel.setSize(new Dimension(150,100));
			
		}
		else if (model instanceof ArtfifactModel)
			newModel = new ArtfifactModel(model.uMLDataModel);
		
//		else if (model instanceof NodeModel)
//			newModel = new NodeModel(model.uMLDataModel);
//		else if (model instanceof DeviceModel)
//			newModel = new DeviceModel(model.uMLDataModel);
//		else if (model instanceof ExecutionEnvironmentModel)
//			newModel = new ExecutionEnvironmentModel(model.uMLDataModel);
//		else if (model instanceof DeploymentSpecificationModel)
//			newModel = new DeploymentSpecificationModel(model.uMLDataModel);
		
		else if (model instanceof ElementLabelModel)
			newModel = new ElementLabelModel();
		else if (model instanceof UMLDiagramModel){
			FrameModel  p = new FrameModel();
			//PKY 08071601 S 시퀀스 다이어그램 드래그 시 표시 부분  모양변경
			if(model instanceof N3EditorDiagramModel){
				N3EditorDiagramModel nd = (N3EditorDiagramModel)model;
				if(nd.getDiagramType()==12){
					p.setType("ref");
					p.setFragmentName(nd.getName());
				}else{
				String type = ProjectManager.getInstance().getFrameType(nd.getDiagramType());
				p.setType(type);
				p.setFragmentName(nd.getName());
				}
				p.setN3EditorDiagramModel(nd);
			}
			//PKY 08071601 E 시퀀스 다이어그램 드래그 시 표시 부분  모양변경

			newModel = p;
		}

		newModel.setTreeModel(model.getUMLTreeModel());
		//PKY 08061801 S 지우고 트리에서 재 생성할경우 사이즈 100,100되는문제 수정
//		if(!(newModel instanceof LifeLineModel))
            if(model.getUMLDataModel().getElementProperty("Size")!=null && !this.isDrag){
            	newModel.setSize((Dimension)model.getUMLDataModel().getElementProperty("Size"));
            }else{
//			newModel.setSize(model.getSize());
            }
		//PKY 08061801 E 지우고 트리에서 재 생성할경우 사이즈 100,100되는문제 수정
		String refId = model.uMLDataModel.getProperty("REF_ID");
		if(refId!=null && !refId.trim().equals("")){
//			if(newModel instanceof LifeLineModel){
				UMLTreeModel um = (UMLTreeModel)ModelManager.getInstance().getModelStore().get(refId);
				if(um!=null){
					this.setRef((UMLModel)um.getRefModel(), newModel);
					model.uMLDataModel.setProperty("REF_ID", null);
				}//20080811IJS 시작
				else{
					TeamProjectManager.getInstance().getNoTypeModelMap().put(refId, newModel);
				}
				//20080811IJS 끝
//			}
		}

//		newModel.setBackGroundColor(model.getBackGroundColor());
		//ijs 091126
		if(to1!=null){
			newModel.setTreeModel(to1);
			to1.setRefModel(newModel);
//			newModel.getUMLDataModel().setProperty("instance", "yes");
			if(parent!=null){
				
					String newName = parent.getCompName(newModel.getName());
					newModel.setName(newName);
				
			}
			
			if(up!=null)
			ProjectManager.getInstance().getModelBrowser().refresh(up);
		}
		this.isDrag = false;
		return newModel;
//		}
//		}
//		}
//		return null;
//		return null;
	}
	
	//20110712서동민
	public UMLModel reModle(UMLModel model) {
		//PKY 08081801 S 모델 브라우져에서 드래그해서 동시에 다이어그램에 같은객체가 있을경우 에러 발생 문제 수정
//		if(ProjectManager.getInstance()!=null && ProjectManager.getInstance().getUMLEditor()!=null && ProjectManager.getInstance().getUMLEditor().getDiagram() !=null){
//		N3EditorDiagramModel n3diagram =(N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram();
//		if(n3diagram!=null){
//			for(int i = 0; i < n3diagram.getChildren().size(); i ++)
//				if(n3diagram.getChildren().get(i)==model){
//					
//					return null;	
//				}
						
//		}
		StrcuturedPackageTreeLibModel to1 = null;
		UMLTreeParentModel up = null;
		N3EditorDiagramModel parent = null;
		if(ProjectManager.getInstance().getUMLEditor()!=null){
		 parent = (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram();
		to1 = null;
		up = (UMLTreeParentModel)parent.getUMLTreeModel().getParent();
		if(parent instanceof N3EditorDiagramModel && this.isDrag && model instanceof ComponentModel)
			for(int i1 = 0; i1 < parent.getChildren().size(); i1++){
				if(parent.getChildren().get(i1) instanceof UMLModel){
					UMLModel umlModel = (UMLModel)parent.getChildren().get(i1);
					if(umlModel.getUMLTreeModel()!=null && model.getUMLTreeModel()!=null)
						if(umlModel.getUMLTreeModel() == model.getUMLTreeModel() ){
//							ijs 091126
							
							to1 = new StrcuturedPackageTreeLibModel(model.getName());
							up = (UMLTreeParentModel)parent.getUMLTreeModel().getParent();
							up.addChild(to1);
						    if(model instanceof ComponentModel){
						    	ComponentModel cm = (ComponentModel)model;
						    	if(cm.isInstance()){
						    		UMLTreeModel ut = (UMLTreeModel)cm.getInstanceUMLTreeModel();
						    		if(ut!=null)
						    			model = (UMLModel)ut.getRefModel();
						    	}
						    }
							
//							ProjectManager.getInstance().getModelBrowser().refresh(up);
//							up.setRefModel(p);
//							return;
						}
				}
			}
			
//		if(to1==null&& this.isDrag && model instanceof TemplateComponentModel){
//			
//		}
//		else 
		if(to1==null&& this.isDrag && model instanceof ComponentLibModel){
				to1 = new StrcuturedPackageTreeLibModel(model.getName());
				up = (UMLTreeParentModel)parent.getUMLTreeModel().getParent();
				up.addChild(to1);
			    if(model instanceof ComponentModel){
			    	ComponentModel cm = (ComponentModel)model;
			    	if(cm.isInstance()){
			    		UMLTreeModel ut = (UMLTreeModel)cm.getInstanceUMLTreeModel();
			    		if(ut!=null)
			    			model = (UMLModel)ut.getRefModel();
			    	}
			    }
			}
			else if(to1==null&& this.isDrag && model instanceof AtomicComponentModel){
				to1 = new StrcuturedPackageTreeLibModel(model.getName());
//				RootCmpEdtTreeModel rcet = ProjectManager.getInstance().getModelBrowser().getN3ViewContentProvider().getRcetm();
				up = (UMLTreeParentModel)parent.getUMLTreeModel().getParent();
				up.addChild(to1);
//			    if(model instanceof ComponentModel){
//			    	ComponentModel cm = (ComponentModel)model;
//			    	if(cm.isInstance()){
//			    		UMLTreeModel ut = (UMLTreeModel)cm.getInstanceUMLTreeModel();
//			    		if(ut!=null)
//			    			model = (UMLModel)ut.getRefModel();
//			    	}
//			    }
			}
			
		}
		//PKY 08081801 E 모델 브라우져에서 드래그해서 동시에 다이어그램에 같은객체가 있을경우 에러 발생 문제 수정

		UMLModel newModel = null;
		if (model instanceof UseCaseModel){
			newModel = new UseCaseModel(model.uMLDataModel);
		}
		else if (model instanceof FinalActorModel){
			newModel = new FinalActorModel(model.uMLDataModel);
			//PKY 08090101 S 시퀀스 다이어그램이 열린상태에서 링크에 있는 클래스 인터페이스 Actor를 불러들이면 시퀀스모양처럼 생성되는문제
			 if(n3d!=null){
				if(n3d!=null && n3d.getDiagramType()==12){
					
				}
			}else if(ProjectManager.getInstance().getUMLEditor()!=null){
				N3EditorDiagramModel nd =(N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram();
				if(nd!=null && nd.getDiagramType()==12){

				
				

				}
			}
			//PKY 08090101 E 시퀀스 다이어그램이 열린상태에서 링크에 있는 클래스 인터페이스 Actor를 불러들이면 시퀀스모양처럼 생성되는문제

		}
		else if (model instanceof FinalBoundryModel){

			newModel = new FinalBoundryModel(model.uMLDataModel);
		}
//		else if (model instanceof CollaborationModel)
//			newModel = new CollaborationModel(model.uMLDataModel);
		else if (model instanceof FinalPackageModel)
			newModel = new FinalPackageModel(model.uMLDataModel);
		else if (model instanceof FinalClassModel) {
			//합성클래스
			newModel = new FinalClassModel(model.uMLDataModel);
			//PKY 08090101 S 시퀀스 다이어그램이 열린상태에서 링크에 있는 클래스 인터페이스 Actor를 불러들이면 시퀀스모양처럼 생성되는문제
			 if(n3d!=null){
				if(n3d!=null && n3d.getDiagramType()==12){

				

				}
			}else if(ProjectManager.getInstance().getUMLEditor()!=null){
				N3EditorDiagramModel nd =(N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram();
				if(nd!=null && nd.getDiagramType()==12 ){

				
//					 ProjectManager.getInstance().addUMLModel(null, (UMLTreeParentModel)nd.getUMLTreeModel().getParent(), newModel, -1,12);//PKY 08081801 S 참조하여 LifeLine생성할경우 모델브라우져에 안나오는 문제

				}
			}
			//PKY 08090101 E 시퀀스 다이어그램이 열린상태에서 링크에 있는 클래스 인터페이스 Actor를 불러들이면 시퀀스모양처럼 생성되는문제

		}
		else if (model instanceof InterfaceModel) {
			//합성클래스
			newModel = new InterfaceModel(model.uMLDataModel);

			if(n3d!=null){
			if(n3d!=null && n3d.getDiagramType()==12){
				
			}
			}else if(ProjectManager.getInstance().getUMLEditor()!=null){
				N3EditorDiagramModel nd =(N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram();
				//PKY 08090101 S 시퀀스 다이어그램이 열린상태에서 링크에 있는 클래스 인터페이스 Actor를 불러들이면 시퀀스모양처럼 생성되는문제

				if(nd!=null && nd.getDiagramType()==12){

				


				}
			}
			//PKY 08090101 E 시퀀스 다이어그램이 열린상태에서 링크에 있는 클래스 인터페이스 Actor를 불러들이면 시퀀스모양처럼 생성되는문제

			//PKY 08081801 E 인터페이스 드래그 시 안되는 문제

		}
		else if (model instanceof ExceptionModel)
			newModel = new ExceptionModel(model.uMLDataModel);
		else if (model instanceof FinalStrcuturedActivityModel)
			newModel = new FinalStrcuturedActivityModel(model.uMLDataModel);
		else if (model instanceof FinalActivityModel)
			newModel = new FinalActivityModel(model.uMLDataModel);
		else if (model instanceof FinalActiionModel)
			newModel = new FinalActiionModel(model.uMLDataModel);
		else if (model instanceof SendModel)
			newModel = new SendModel(model.uMLDataModel);
		else if (model instanceof ReceiveModel)
			newModel = new ReceiveModel(model.uMLDataModel);
		else if (model instanceof InitialModel)
			newModel = new InitialModel(model.uMLDataModel);
		else if (model instanceof FinalModel)
			newModel = new FinalModel(model.uMLDataModel);
		else if (model instanceof FlowFinalModel)
			newModel = new FlowFinalModel(model.uMLDataModel);
		else if (model instanceof SynchModel)
			newModel = new SynchModel(model.uMLDataModel);
		else if (model instanceof DecisionModel)
			newModel = new DecisionModel(model.uMLDataModel);
		else if (model instanceof FinalObjectNodeModel)
			newModel = new FinalObjectNodeModel(model.uMLDataModel);
		else if (model instanceof CentralBufferNodeModel)
			newModel = new CentralBufferNodeModel(model.uMLDataModel);
		else if (model instanceof DataStoreModel)
			newModel = new DataStoreModel(model.uMLDataModel);
		else if (model instanceof HPartitionModel)
			newModel = new HPartitionModel(model.uMLDataModel);
		else if (model instanceof VPartitionModel)
			newModel = new VPartitionModel(model.uMLDataModel);
		else if (model instanceof HForkJoinModel)
			newModel = new HForkJoinModel(model.uMLDataModel);
		else if (model instanceof VForkJoinModel)
			newModel = new VForkJoinModel(model.uMLDataModel);
		else if (model instanceof PartModel)
			newModel = new PartModel(model.uMLDataModel);
		
		else if(model instanceof ComponentLibModel){
			//ijs 091126
			ComponentLibModel cmodel = (ComponentLibModel)model;
			if(this.isDrag && to1!=null){

					newModel = (UMLModel)model.clone();
					ComponentLibModel cl = (ComponentLibModel)newModel;
					cl.setInstance(true);
					cl.setInstanceUMLTreeModel(cmodel.getUMLTreeModel());
					cl.setId(null);
					cl.getId();
			
				
				
			}
			else {
				newModel = new ComponentLibModel(model.uMLDataModel);
				
			}
			
//			newModel.setSize(new Dimension(10,10));
			
			////etri 디폴트 컴포넌트 생성
			ComponentLibModel newModelpm = 	(ComponentLibModel)newModel;
			System.out.println("id1:==================>"+newModelpm.getUMLDataModel().getId());
			System.out.println("id2:==================>"+model.uMLDataModel.getId());
			PortManager pm = cmodel.getPortManager();
			UMLTreeParentModel upm = (UMLTreeParentModel)cmodel.getUMLTreeModel();
			if(ProjectManager.getInstance().isLoad()){
				for(int i=0;i<pm.getPorts().size();i++){
					
					PortModel pm1 = (PortModel)pm.getPorts().get(i);
					PortModel pm2 = null;
					if(pm1 instanceof MonitoringMethodPortModel){
						pm2 = new MonitoringMethodPortModel(newModelpm);
//					newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
					}
					else if(pm1 instanceof LifecycleMethodPortModel){
						pm2 = new LifecycleMethodPortModel(newModelpm);
//					newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
					}
					else if(pm1 instanceof MethodOutputPortModel){
						pm2 = new MethodOutputPortModel(newModelpm);
//					newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
					}
					else if(pm1 instanceof MethodInputPortModel){
						pm2 = new MethodInputPortModel(newModelpm);
//						pm2.getAttachElementLabelModel().setText(pm1.getAttachElementLabelModel().getLabelContents());
//					newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
					}
					else if(pm1 instanceof DataInputPortModel){
						pm2 = new DataInputPortModel(newModelpm);
//						newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
					}
					else if(pm1 instanceof DataOutputPortModel){
						pm2 = new DataOutputPortModel(newModelpm);
//						newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
					}
					else if(pm1 instanceof EventInputPortModel){
						pm2 = new EventInputPortModel(newModelpm);
//						newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
					}
					else if(pm1 instanceof EventOutputPortModel){
						pm2 = new EventOutputPortModel(newModelpm);
//						newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
					}
					if(pm1.getElementLabelModel().isReadOnly()){
						pm2.getElementLabelModel().setReadOnly(true);
					}
					pm2.getElementLabelModel().setPortId(pm1.getID());
					pm2.getUMLDataModel().setId(pm1.getID());
					pm2.getAttachElementLabelModel().setText(pm1.getAttachElementLabelModel().getLabelContents());
					pm2.getElementLabelModel().setTreeModel(pm1.getUMLTreeModel());
					pm2.setType(pm1.getType());
					newModelpm.getPortManager().getPorts().add(pm2);
					
//					newModelpm.getUMLDataModel().setId(pm1.getID());
					
				}
			}
			else	if(!ProjectManager.getInstance().isCopy() && !ProjectManager.getInstance().isLoad() && (!(this.isDrag && to1!=null))){
			for(int i=0;i<upm.getChildren().length;i++){
				UMLTreeModel um = (UMLTreeModel)upm.getChildren()[i];
				if(!(um.getRefModel() instanceof PortModel))
					continue;
				PortModel pm1 = (PortModel)um.getRefModel();
				PortModel pm2 = null;
				
				if(pm1 instanceof MonitoringMethodPortModel){
					pm2 = new MonitoringMethodPortModel(newModelpm);
//				newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
				}
				else if(pm1 instanceof LifecycleMethodPortModel){
					pm2 = new LifecycleMethodPortModel(newModelpm);
//				newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
				}
				else if(pm1 instanceof MethodOutputPortModel){
					pm2 = new MethodOutputPortModel(newModelpm);
//				newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
				}
				else if(pm1 instanceof MethodInputPortModel){
					pm2 = new MethodInputPortModel(newModelpm);
					
//				newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
				}
				else if(pm1 instanceof DataInputPortModel){
					pm2 = new DataInputPortModel(newModelpm);
//					newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
				}
				else if(pm1 instanceof DataOutputPortModel){
					pm2 = new DataOutputPortModel(newModelpm);
//					newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
				}
				else if(pm1 instanceof EventInputPortModel){
					pm2 = new EventInputPortModel(newModelpm);
//					newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
				}
				else if(pm1 instanceof EventOutputPortModel){
					pm2 = new EventOutputPortModel(newModelpm);
//					newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
				}
//				if(pm1.getElementLabelModel().isReadOnly()){
//					pm2.getElementLabelModel().setReadOnly(true);
//				}
				pm2.getElementLabelModel().setReadOnly(true);
				pm2.getElementLabelModel().setPortId(pm1.getID());
				pm2.getUMLDataModel().setId(pm1.getID());
				pm2.getAttachElementLabelModel().setText(pm1.getAttachElementLabelModel().getLabelContents());
				pm2.getElementLabelModel().setTreeModel(um);
				newModelpm.getPortManager().getPorts().add(pm2);
				pm2.setType(pm1.getType());
//				newModelpm.getUMLDataModel().setId(pm1.getID());
				
			}
			CompAssemManager.getInstance().autoLayoutPort(newModelpm.getPortManager().getPorts());
			}
			else if(this.isDrag && to1!=null){
				
				for(int i=0;i<upm.getChildren().length;i++){
					UMLTreeModel um = (UMLTreeModel)upm.getChildren()[i];
					if(!(um.getRefModel() instanceof PortModel))
						continue;
					PortModel pm1 = (PortModel)um.getRefModel();
					PortModel pm2 = null;
					
					if(pm1 instanceof MonitoringMethodPortModel){
						pm2 = new MonitoringMethodPortModel(newModelpm);
//					newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
					}
					else if(pm1 instanceof LifecycleMethodPortModel){
						pm2 = new LifecycleMethodPortModel(newModelpm);
//					newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
					}
					else if(pm1 instanceof MethodOutputPortModel){
						pm2 = new MethodOutputPortModel(newModelpm);
//					newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
					}
					else if(pm1 instanceof MethodInputPortModel){
						pm2 = new MethodInputPortModel(newModelpm);
						
//					newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
					}
					else if(pm1 instanceof DataInputPortModel){
						pm2 = new DataInputPortModel(newModelpm);
//						newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
					}
					else if(pm1 instanceof DataOutputPortModel){
						pm2 = new DataOutputPortModel(newModelpm);
//						newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
					}
					else if(pm1 instanceof EventInputPortModel){
						pm2 = new EventInputPortModel(newModelpm);
//						newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
					}
					else if(pm1 instanceof EventOutputPortModel){
						pm2 = new EventOutputPortModel(newModelpm);
//						newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
					}
//					if(pm1.getElementLabelModel().isReadOnly()){
						pm2.getElementLabelModel().setReadOnly(true);
//					}
					
					UMLTreeModel port = new UMLTreeModel(pm1.getAttachElementLabelModel().getLabelContents());
					to1.addChild(port);
					port.setRefModel(pm2);
					pm2.getElementLabelModel().setTreeModel(port);
					
					pm2.getElementLabelModel().setPortId(pm2.getID());
					pm2.getUMLDataModel().setId(pm2.getID());
					pm2.getAttachElementLabelModel().setText(pm1.getAttachElementLabelModel().getLabelContents());
					pm2.getElementLabelModel().setTreeModel(port);
					newModelpm.getPortManager().getPorts().add(pm2);
					pm2.setType(pm1.getType());
					  ProjectManager.getInstance().getModelBrowser().refresh(to1);
//					newModelpm.getUMLDataModel().setId(pm1.getID());
					
				}
				CompAssemManager.getInstance().autoLayoutPort(newModelpm.getPortManager().getPorts());
				
			}
//			newModel.setSize(new Dimension(150,300));
			
		
		}
		
		else if (model instanceof ComponentModel){
			if(((ComponentModel) model).isInstance()){
				newModel = new ComponentLibModel(model.uMLDataModel);
			}
			else if(model instanceof AtomicComponentModel){
				
				newModel = new AtomicComponentModel(model.uMLDataModel);
				
				if(this.isDrag && to1!=null){
//					if(this.isDrag && to1!=null){

						newModel = (UMLModel)model.clone();
//						AtomicComponentModel cl = (AtomicComponentModel)newModel;
////						cl.setInstance(true);
////						cl.setInstanceUMLTreeModel(model.getUMLTreeModel());
////						cl.setId(null);
//						cl.getId();
				
					
					
//				}
					AtomicComponentModel newModelpm = (AtomicComponentModel)newModel; 
					AtomicComponentModel cmodel = (AtomicComponentModel)model;
					
				    
					PortManager pm = cmodel.getPortManager();
					UMLTreeParentModel upm = (UMLTreeParentModel)model.getUMLTreeModel();
					newModelpm.setCoreUMLTreeModel(upm);
					
					for(int i=0;i<upm.getChildren().length;i++){
						UMLTreeModel um = (UMLTreeModel)upm.getChildren()[i];
						if(!(um.getRefModel() instanceof PortModel))
							continue;
						PortModel pm1 = (PortModel)um.getRefModel();
						PortModel pm2 = null;
						
						if(pm1 instanceof MonitoringMethodPortModel){
							pm2 = new MonitoringMethodPortModel(newModelpm);
//						newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
						}
						else if(pm1 instanceof LifecycleMethodPortModel){
							pm2 = new LifecycleMethodPortModel(newModelpm);
//						newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
						}
						else if(pm1 instanceof MethodOutputPortModel){
							pm2 = new MethodOutputPortModel(newModelpm);
//						newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
						}
						else if(pm1 instanceof MethodInputPortModel){
							pm2 = new MethodInputPortModel(newModelpm);
							
//						newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
						}
						else if(pm1 instanceof DataInputPortModel){
							pm2 = new DataInputPortModel(newModelpm);
//							newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
						}
						else if(pm1 instanceof DataOutputPortModel){
							pm2 = new DataOutputPortModel(newModelpm);
//							newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
						}
						else if(pm1 instanceof EventInputPortModel){
							pm2 = new EventInputPortModel(newModelpm);
//							newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
						}
						else if(pm1 instanceof EventOutputPortModel){
							pm2 = new EventOutputPortModel(newModelpm);
//							newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
						}
//						if(pm1.getElementLabelModel().isReadOnly()){
							pm2.getElementLabelModel().setReadOnly(true);
//						}
						
//						UMLTreeModel port = new UMLTreeModel(pm1.getAttachElementLabelModel().getLabelContents());
//						to1.addChild(port);
//						port.setRefModel(pm2);
						pm2.getElementLabelModel().setTreeModel(um);
						
						pm2.getElementLabelModel().setPortId(pm2.getID());
						pm2.getUMLDataModel().setId(pm2.getID());
						pm2.getAttachElementLabelModel().setText(pm1.getAttachElementLabelModel().getLabelContents());
//						pm2.getElementLabelModel().setTreeModel(port);
//						newModelpm.getPortManager().getPorts().add(pm2);
						pm2.setType(pm1.getType());
						  ProjectManager.getInstance().getModelBrowser().refresh(to1);
//						newModelpm.getUMLDataModel().setId(pm1.getID());
						
					}
//						newModelpm.getUMLDataModel().setId(pm1.getID());
						
					}
			}
			
//			else if(this.isDrag){
//				
//			}
			else
				newModel = new ComponentModel(model.uMLDataModel);
			if(parent!=null){
				String newName = parent.getCompName(newModel.getName());
				newModel.setName(newName);
			}
			ComponentModel cmodel = (ComponentModel)model;
			
			////etri 디폴트 컴포넌트 생성
			ComponentModel newModelpm = 	(ComponentModel)newModel;
			System.out.println("id1:==================>"+newModelpm.getUMLDataModel().getId());
			System.out.println("id2:==================>"+model.uMLDataModel.getId());
			PortManager pm = cmodel.getPortManager();
			UMLTreeParentModel upm = (UMLTreeParentModel)cmodel.getUMLTreeModel();
			if(ProjectManager.getInstance().isLoad()){
				for(int i=0;i<pm.getPorts().size();i++){
					
					PortModel pm1 = (PortModel)pm.getPorts().get(i);
					PortModel pm2 = null;
					if(pm1 instanceof MonitoringMethodPortModel){
						pm2 = new MonitoringMethodPortModel(newModelpm);
//					newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
					}
					else if(pm1 instanceof LifecycleMethodPortModel){
						pm2 = new LifecycleMethodPortModel(newModelpm);
//					newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
					}
					else if(pm1 instanceof MethodOutputPortModel){
						pm2 = new MethodOutputPortModel(newModelpm);
//					newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
					}
					else if(pm1 instanceof MethodInputPortModel){
						pm2 = new MethodInputPortModel(newModelpm);
//						pm2.getAttachElementLabelModel().setText(pm1.getAttachElementLabelModel().getLabelContents());
//					newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
					}
					else if(pm1 instanceof DataInputPortModel){
						pm2 = new DataInputPortModel(newModelpm);
//						newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
					}
					else if(pm1 instanceof DataOutputPortModel){
						pm2 = new DataOutputPortModel(newModelpm);
//						newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
					}
					else if(pm1 instanceof EventInputPortModel){
						pm2 = new EventInputPortModel(newModelpm);
//						newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
					}
					else if(pm1 instanceof EventOutputPortModel){
						pm2 = new EventOutputPortModel(newModelpm);
//						newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
					}
					pm2.getElementLabelModel().setPortId(pm1.getID());
					pm2.getUMLDataModel().setId(pm1.getID());
					pm2.getAttachElementLabelModel().setText(pm1.getAttachElementLabelModel().getLabelContents());
					pm2.getElementLabelModel().setTreeModel(pm1.getUMLTreeModel());
					newModelpm.getPortManager().getPorts().add(pm2);
//					newModelpm.getUMLDataModel().setId(pm1.getID());
					
				}
			}
			if(!ProjectManager.getInstance().isCopy() && !ProjectManager.getInstance().isLoad()){
			for(int i=0;i<upm.getChildren().length;i++){
				UMLTreeModel um = (UMLTreeModel)upm.getChildren()[i];
				if(!(um.getRefModel() instanceof PortModel))
					continue;
				PortModel pm1 = (PortModel)um.getRefModel();
				PortModel pm2 = null;
				
				if(pm1 instanceof MonitoringMethodPortModel){
					pm2 = new MonitoringMethodPortModel(newModelpm);
//				newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
				}
				else if(pm1 instanceof LifecycleMethodPortModel){
					pm2 = new LifecycleMethodPortModel(newModelpm);
//				newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
				}
				else if(pm1 instanceof MethodOutputPortModel){
					pm2 = new MethodOutputPortModel(newModelpm);
//				newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
				}
				else if(pm1 instanceof MethodInputPortModel){
					pm2 = new MethodInputPortModel(newModelpm);
					
//				newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
				}
				else if(pm1 instanceof DataInputPortModel){
					pm2 = new DataInputPortModel(newModelpm);
//					newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
				}
				else if(pm1 instanceof DataOutputPortModel){
					pm2 = new DataOutputPortModel(newModelpm);
//					newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
				}
				else if(pm1 instanceof EventInputPortModel){
					pm2 = new EventInputPortModel(newModelpm);
//					newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
				}
				else if(pm1 instanceof EventOutputPortModel){
					pm2 = new EventOutputPortModel(newModelpm);
//					newModelpm.createPort2(pm2, (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
				}
				if(((ComponentModel) model).isInstance()){
					pm2.getElementLabelModel().setReadOnly(true);
				}
//				if(pm1.getElementLabelModel().isReadOnly()){
//					pm2.getElementLabelModel().setReadOnly(true);
//				}
				pm2.getElementLabelModel().setPortId(pm1.getID());
				pm2.getUMLDataModel().setId(pm1.getID());
				pm2.getAttachElementLabelModel().setText(pm1.getAttachElementLabelModel().getLabelContents());
				pm2.getElementLabelModel().setTreeModel(um);
				newModelpm.getPortManager().getPorts().add(pm2);
//				newModelpm.getUMLDataModel().setId(pm1.getID());
				
			}
			CompAssemManager.getInstance().autoLayoutPort(newModelpm.getPortManager().getPorts());
			}
			newModel.setSize(new Dimension(150,100));
			
		}
		else if (model instanceof ArtfifactModel)
			newModel = new ArtfifactModel(model.uMLDataModel);
		
//		else if (model instanceof NodeModel)
//			newModel = new NodeModel(model.uMLDataModel);
//		else if (model instanceof DeviceModel)
//			newModel = new DeviceModel(model.uMLDataModel);
//		else if (model instanceof ExecutionEnvironmentModel)
//			newModel = new ExecutionEnvironmentModel(model.uMLDataModel);
//		else if (model instanceof DeploymentSpecificationModel)
//			newModel = new DeploymentSpecificationModel(model.uMLDataModel);
		
		else if (model instanceof ElementLabelModel)
			newModel = new ElementLabelModel();
		else if (model instanceof UMLDiagramModel){
			FrameModel  p = new FrameModel();
			//PKY 08071601 S 시퀀스 다이어그램 드래그 시 표시 부분  모양변경
			if(model instanceof N3EditorDiagramModel){
				N3EditorDiagramModel nd = (N3EditorDiagramModel)model;
				if(nd.getDiagramType()==12){
					p.setType("ref");
					p.setFragmentName(nd.getName());
				}else{
				String type = ProjectManager.getInstance().getFrameType(nd.getDiagramType());
				p.setType(type);
				p.setFragmentName(nd.getName());
				}
				p.setN3EditorDiagramModel(nd);
			}
			//PKY 08071601 E 시퀀스 다이어그램 드래그 시 표시 부분  모양변경

			newModel = p;
		}

		newModel.setTreeModel(model.getUMLTreeModel());
		//PKY 08061801 S 지우고 트리에서 재 생성할경우 사이즈 100,100되는문제 수정
//		if(!(newModel instanceof LifeLineModel))
            if(model.getUMLDataModel().getElementProperty("Size")!=null && !this.isDrag){
            	newModel.setSize((Dimension)model.getUMLDataModel().getElementProperty("Size"));
            }else{
//			newModel.setSize(model.getSize());
            }
		//PKY 08061801 E 지우고 트리에서 재 생성할경우 사이즈 100,100되는문제 수정
		String refId = model.uMLDataModel.getProperty("REF_ID");
		if(refId!=null && !refId.trim().equals("")){
//			if(newModel instanceof LifeLineModel){
				UMLTreeModel um = (UMLTreeModel)ModelManager.getInstance().getModelStore().get(refId);
				if(um!=null){
					this.setRef((UMLModel)um.getRefModel(), newModel);
					model.uMLDataModel.setProperty("REF_ID", null);
				}//20080811IJS 시작
				else{
					TeamProjectManager.getInstance().getNoTypeModelMap().put(refId, newModel);
				}
				//20080811IJS 끝
//			}
		}

//		newModel.setBackGroundColor(model.getBackGroundColor());
		//ijs 091126
		if(to1!=null){
			newModel.setTreeModel(to1);
			to1.setRefModel(newModel);
//			newModel.getUMLDataModel().setProperty("instance", "yes");
			if(parent!=null){
				
					String newName = parent.getCompName(newModel.getName());
					newModel.setName(newName);
				
			}
			
			if(up!=null)
			ProjectManager.getInstance().getModelBrowser().refresh(up);
		}
		this.isDrag = false;
		return newModel;
//		}
//		}
//		}
//		return null;
//		return null;
	}
	


	public N3EditorDiagramModel getN3d() {
		return n3d;
	}

	public void setN3d(N3EditorDiagramModel n3d) {
		this.n3d = n3d;
	}
	
	public void copyModel(UMLTreeParentModel source){
//		System.out.println("ddddddddddddddddd");
//		if("<<composite>>".equals(cmodel.getStereotype()) ){
//			System.out.println("ddddd");
//			UMLTreeModel ut = cmodel.getUMLTreeModel();
//			if(ut instanceof UMLTreeParentModel){
//				UMLTreeParentModel upm = (UMLTreeParentModel)ut;
//			}
//		}
//		else{
//			newModel = (UMLModel)model.clone();
//			ComponentLibModel cl = (ComponentLibModel)newModel;
//			cl.setInstance(true);
//			cl.setInstanceUMLTreeModel(cmodel.getUMLTreeModel());
//			cl.setId(null);
//			cl.getId();
//		}
		
	}
	
	public N3EditorDiagramModel copyDiagram(N3EditorDiagramModel source){
		return null;
		
	}
}
