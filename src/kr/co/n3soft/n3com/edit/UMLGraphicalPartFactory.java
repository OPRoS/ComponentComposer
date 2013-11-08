package kr.co.n3soft.n3com.edit;

import kr.co.n3soft.n3com.model.activity.ActivityModel;
import kr.co.n3soft.n3com.model.activity.ActivityParameterModel;
import kr.co.n3soft.n3com.model.activity.CallBehaviorModel;
import kr.co.n3soft.n3com.model.activity.CallOperationModel;
import kr.co.n3soft.n3com.model.activity.CentralBufferNodeModel;
import kr.co.n3soft.n3com.model.activity.ControlFlowLineModel;
import kr.co.n3soft.n3com.model.activity.DataStoreModel;
import kr.co.n3soft.n3com.model.activity.DecisionModel;
import kr.co.n3soft.n3com.model.activity.ExceptionModel;
import kr.co.n3soft.n3com.model.activity.ExpansionNodeModel;
import kr.co.n3soft.n3com.model.activity.FinalActiionModel;
import kr.co.n3soft.n3com.model.activity.FinalActivityModel;
import kr.co.n3soft.n3com.model.activity.FinalModel;
import kr.co.n3soft.n3com.model.activity.FinalObjectNodeModel;
import kr.co.n3soft.n3com.model.activity.FinalStrcuturedActivityModel;
import kr.co.n3soft.n3com.model.activity.FlowFinalModel;
import kr.co.n3soft.n3com.model.activity.HForkJoinModel;
import kr.co.n3soft.n3com.model.activity.HPartitionModel;
import kr.co.n3soft.n3com.model.activity.InitialModel;
import kr.co.n3soft.n3com.model.activity.ObjectFlowLineModel;
import kr.co.n3soft.n3com.model.activity.ObjectNodeModel;
import kr.co.n3soft.n3com.model.activity.ReceiveModel;
import kr.co.n3soft.n3com.model.activity.SendModel;
import kr.co.n3soft.n3com.model.activity.StrcuturedModel;
import kr.co.n3soft.n3com.model.activity.SubPartitonModel;
import kr.co.n3soft.n3com.model.activity.SynchModel;
import kr.co.n3soft.n3com.model.activity.VForkJoinModel;
import kr.co.n3soft.n3com.model.activity.VPartitionModel;
import kr.co.n3soft.n3com.model.comm.BarModel;
import kr.co.n3soft.n3com.model.comm.BorderContainlModel;
import kr.co.n3soft.n3com.model.comm.ClassModel;
import kr.co.n3soft.n3com.model.comm.DataInputPortModel;
import kr.co.n3soft.n3com.model.comm.DataOutputPortModel;
import kr.co.n3soft.n3com.model.comm.ElementLabelModel;
import kr.co.n3soft.n3com.model.comm.EntryPointModel;
import kr.co.n3soft.n3com.model.comm.EventInputPortModel;
import kr.co.n3soft.n3com.model.comm.EventOutputPortModel;
import kr.co.n3soft.n3com.model.comm.ExitPointModel;
import kr.co.n3soft.n3com.model.comm.FinalPackageModel;
import kr.co.n3soft.n3com.model.comm.FrameModel;
import kr.co.n3soft.n3com.model.comm.LineModel;
import kr.co.n3soft.n3com.model.comm.LineTextModel;
import kr.co.n3soft.n3com.model.comm.MethodInputPortModel;
import kr.co.n3soft.n3com.model.comm.MethodOutputPortModel;
import kr.co.n3soft.n3com.model.comm.NodeContainerModel;
import kr.co.n3soft.n3com.model.comm.ObjectPortModel;
import kr.co.n3soft.n3com.model.comm.PortModel;
import kr.co.n3soft.n3com.model.comm.TempBorderLayoutContainerModel;
import kr.co.n3soft.n3com.model.comm.TempModel;
import kr.co.n3soft.n3com.model.comm.UMLMainPackageModel;
import kr.co.n3soft.n3com.model.comm.UMLNamePackageModel;
import kr.co.n3soft.n3com.model.comm.UMLNoteModel;
import kr.co.n3soft.n3com.model.communication.ArrowModel;
import kr.co.n3soft.n3com.model.communication.MessageAssoicateLineModel;
import kr.co.n3soft.n3com.model.communication.MessageCommunicationModel;
import kr.co.n3soft.n3com.model.component.ArtfifactModel;
import kr.co.n3soft.n3com.model.component.ComponentModel;
import kr.co.n3soft.n3com.model.composite.DelegateLineModel;
import kr.co.n3soft.n3com.model.composite.OccurrenceLineModel;
import kr.co.n3soft.n3com.model.composite.PartModel;
import kr.co.n3soft.n3com.model.composite.ProvidedInterfaceLineModel;
import kr.co.n3soft.n3com.model.composite.RepresentsLineModel;
import kr.co.n3soft.n3com.model.composite.RequiredInterfaceLineModel;
import kr.co.n3soft.n3com.model.composite.RoleBindingLineModel;
import kr.co.n3soft.n3com.model.diagram.UMLDiagramModel;
import kr.co.n3soft.n3com.model.umlclass.ClassBoundaryModel;
import kr.co.n3soft.n3com.model.umlclass.CompartmentModel;
import kr.co.n3soft.n3com.model.umlclass.DependencyLineModel;
import kr.co.n3soft.n3com.model.umlclass.FinalClassModel;
import kr.co.n3soft.n3com.model.umlclass.ImportLineModel;
import kr.co.n3soft.n3com.model.umlclass.InterfaceModel;
import kr.co.n3soft.n3com.model.umlclass.MergeLineModel;
import kr.co.n3soft.n3com.model.usecase.ActorModel;
import kr.co.n3soft.n3com.model.usecase.AssociateLineModel;
import kr.co.n3soft.n3com.model.usecase.BoundaryModel;
import kr.co.n3soft.n3com.model.usecase.CollaborationModel;
import kr.co.n3soft.n3com.model.usecase.ContainerBoundaryModel;
import kr.co.n3soft.n3com.model.usecase.ExtendLineModel;
import kr.co.n3soft.n3com.model.usecase.FinalActorModel;
import kr.co.n3soft.n3com.model.usecase.FinalBoundryModel;
import kr.co.n3soft.n3com.model.usecase.GeneralizeLineModel;
import kr.co.n3soft.n3com.model.usecase.IncludeLineModel;
import kr.co.n3soft.n3com.model.usecase.NoteLineModel;
import kr.co.n3soft.n3com.model.usecase.RealizeLineModel;
import kr.co.n3soft.n3com.model.usecase.UseCaseModel;
import kr.co.n3soft.n3com.project.dialog.NewStructuredActivityDialog;
import kr.co.n3soft.n3com.project.dialog.SelectBehaviorDialog;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

public class UMLGraphicalPartFactory implements EditPartFactory {
    //등록
    //선 등록
	//포트 등록
    public EditPart createEditPart(EditPart context, Object model) {
        EditPart child = null;
        if (model instanceof UseCaseModel)
            child = new UseCaseEditPart();
        else if (model instanceof FinalActorModel)
            child = new FinalActorEditPart();
        else if (model instanceof ActorModel)
            child = new ActorEditPart();
        else if (model instanceof BoundaryModel)
            child = new BoundaryEditPart();
        else if (model instanceof ContainerBoundaryModel)
            child = new ContainerBoundaryEditPart();
        else if (model instanceof FinalBoundryModel)
            child = new FinalBoundryEditPart();
        else if (model instanceof CollaborationModel)
            child = new CollaborationEditPart();
        else if (model instanceof FinalPackageModel)
            child = new FinalPackageEditPart();
        else if (model instanceof UMLMainPackageModel)
            child = new UMLMainPackageEditPart();
        else if (model instanceof UMLNamePackageModel)
            child = new UMLNamePackageEditPart();
        else if (model instanceof TempModel)
            child = new TempEditPart();
        else if (model instanceof TempBorderLayoutContainerModel)
            child = new TempBorderLayoutContainerEditPart();
        else if (model instanceof BorderContainlModel)
            child = new BorderContainEditPart();
        else if (model instanceof CompartmentModel)
            child = new CompartmentEditPart();
        else if (model instanceof ClassBoundaryModel) {
            child = new ClassBoundaryEditPart();
        }
        else if (model instanceof InterfaceModel) {
            child = new InterfaceEditPart();
        }
        else if (model instanceof FinalClassModel) {
            child = new FinalClassEditPart();
        }
        else if (model instanceof PartModel) {
            child = new PartEditPart();
        }
        else if (model instanceof ClassModel) {
            //합성클래스
            child = new ClassEditPart();
        }
        else if (model instanceof ExceptionModel)
            child = new ExceptionEditPart();
        else if (model instanceof EntryPointModel)
            child = new EntryPointEditPart();
        else if (model instanceof ExitPointModel)
            child = new ExitPointEditPart();
        else if (model instanceof ExpansionNodeModel)
            child = new ExpansionNodeEditPart();
        else if (model instanceof ActivityParameterModel)
            child = new ActivityParameterEditPart();
        else if (model instanceof ObjectPortModel)
            child = new ObjectPortEditPart();
        else if (model instanceof MethodInputPortModel)
            child = new MethodInputPortEditPart();
        else if (model instanceof MethodOutputPortModel)
            child = new MethodOutputPortEditPart();
        
        else if (model instanceof DataInputPortModel)
            child = new DataInputPortEditPart();
        else if (model instanceof DataOutputPortModel)
            child = new DataOutputPortEditPart();
        
        else if (model instanceof EventInputPortModel)
            child = new EventInputPortEditPart();
        else if (model instanceof EventOutputPortModel)
            child = new EventOutputPortEditPart();
        
        else if (model instanceof PortModel)
            child = new PortEditPart();
       
        else if (model instanceof FinalStrcuturedActivityModel) {
            FinalStrcuturedActivityModel fsm = (FinalStrcuturedActivityModel)model;
            int type = fsm.getType();
            if (type == -1) {
                NewStructuredActivityDialog newStructuredActivityDialog =
                    new NewStructuredActivityDialog(ProjectManager.getInstance().window.getShell());
                newStructuredActivityDialog.setName(fsm.getName());
              //PKY 08072401 S structuredActivity 프로퍼티에서 선택 할 수있도록 수정
//                newStructuredActivityDialog.open();
//                type = newStructuredActivityDialog.getActivityTYpe();
              //PKY 08072401 E structuredActivity 프로퍼티에서 선택 할 수있도록 수정

                fsm.setType(0);
                String name = newStructuredActivityDialog.getActivityName();
                if (name != null && name.trim().length() > 0)
                    fsm.setName(name);
            }
            if (type == 1) {
            }
            else if (type == 2) {
                fsm.setStereotype("structure");
            }
            else if (type == 3) {
                fsm.setStereotype("conditional");
            }
            else if (type == 4) {
                fsm.setStereotype("loop");
            }
            else if (type == 5) {
                fsm.setStereotype("sequential");
            }
            child = new FinalStrcuturedActivityEditPart();
        }
        else if (model instanceof FinalActivityModel)
            child = new FinalActivityEditPart();
        else if (model instanceof ActivityModel)
            child = new ActivityEditPart();
        else if (model instanceof FinalActiionModel) {
            child = new FinalActionEditPart();
            FinalActiionModel fam = (FinalActiionModel)model;
            String name = fam.getName();
            if (fam.getType() == -1) {
            	//PKY 08072401 S 액션 추가 시 타입묻는 다이어그램 오픈되지 않도록 기본타입 노멀
//                NewActionDialog newActionDialog = new NewActionDialog(ProjectManager.getInstance().window.getShell());
//                newActionDialog.setName(fam.getName());
//                newActionDialog.open();
//                int type = newActionDialog.getActionTYpe();
//            	  name = newActionDialog.getActionName();
            	int type=0;
            	//PKY 08072401 E 액션 추가 시 타입묻는 다이어그램 오픈되지 않도록 기본타입 노멀            	
                if (type == 2) {
                    SelectBehaviorDialog sbd = new SelectBehaviorDialog(ProjectManager.getInstance().window.getShell());
                    sbd.open();
                    fam.setCallBeavior(sbd.getSelectedUMLModel());
                }
                fam.setName(name);
                if(fam.getUMLDataModel().getElementProperty("ActionType")==null)//PKY 08072401 S 액션 추가 시 타입묻는 다이어그램 오픈되지 않도록 기본타입 노멀
                fam.setType(type);
                //				}
            }
        }
        else if (model instanceof NodeContainerModel)
            child = new ActionEditPart();
        else if (model instanceof SendModel)
            child = new SendEditPart();
        else if (model instanceof ReceiveModel)
            child = new ReceiveEditPart();
        else if (model instanceof InitialModel)
            child = new InitialEditPart();
        else if (model instanceof FinalModel)
            child = new FinalEditPart();
        else if (model instanceof FlowFinalModel)
            child = new FlowFinalEditPart();
        else if (model instanceof SynchModel)
            child = new SynchEditPart();
        else if (model instanceof DecisionModel)
            child = new DecisionEditPart();
        else if (model instanceof ObjectNodeModel)
            child = new ObjectNodeEditPart();
        else if (model instanceof FinalObjectNodeModel)
            child = new FinalObjectNodeEditPart();
        else if (model instanceof BarModel)
            child = new BarEditPart();
        else if (model instanceof CentralBufferNodeModel)
            child = new CentralBufferNodeEditPart();
        else if (model instanceof DataStoreModel)
            child = new DataStoreEditPart();
        else if (model instanceof HPartitionModel)
            child = new HPartitionEditPart();
        else if (model instanceof VPartitionModel)
            child = new VPartitionEditPart();
        else if (model instanceof HForkJoinModel)
            child = new HForkJoinEditPart();
        else if (model instanceof VForkJoinModel)
            child = new HForkJoinEditPart();
        else if (model instanceof CallBehaviorModel)
            child = new CallBehaviorEditPart();
        else if (model instanceof CallOperationModel)
            child = new CallOperationEditPart();
        else if (model instanceof StrcuturedModel)
            child = new StrcuturedEditPart();
        else if (model instanceof ExtendLineModel)
            child = new ExtendLineEditPart();
        else if (model instanceof IncludeLineModel)
            child = new IncludeLineEditPart();
        else if (model instanceof MessageAssoicateLineModel)
            child = new MessageAssoicateLineEditPart();
        else if (model instanceof AssociateLineModel)
            child = new AssociateLineEditPart();
        else if (model instanceof GeneralizeLineModel)
            child = new GeneralizeLineEditPart();
        else if (model instanceof RealizeLineModel)
            child = new RealizeLineEditPart();
        else if (model instanceof NoteLineModel)
            child = new NoteLineEditPart();
        else if (model instanceof DependencyLineModel)
            child = new DependencyLineEditPart();
      //PKY 08072401 S ObjectFlow모델 추가
        else if (model instanceof ObjectFlowLineModel)
            child = new ObjectFlowLineEditPart();
        else if (model instanceof ControlFlowLineModel)
            child = new ControlFlowLineEditPart();
        else if (model instanceof RequiredInterfaceLineModel)
            child = new RequiredInterfaceEditPart();
        else if (model instanceof ProvidedInterfaceLineModel)
            child = new ProvidedInterfaceEditPart();
        else if (model instanceof RoleBindingLineModel)
            child = new RoleBindingLineEditPart();
        else if (model instanceof DelegateLineModel)
            child = new DelegateLineEditPart();
        else if (model instanceof OccurrenceLineModel)
            child = new OccurrenceLineEditPart();
        else if (model instanceof RepresentsLineModel)
            child = new RepresentsLineEditPart();
       
       
    
        else if (model instanceof ComponentModel)
            child = new ComponentEditPart();
        else if (model instanceof ArtfifactModel)
            child = new ArtfifactEditPart();
        else if (model instanceof SubPartitonModel)
            child = new SubPartitonEditPart();
       
      
        
        else if (model instanceof ArrowModel)
            child = new ArrowEditPart();
      
        else if (model instanceof MessageCommunicationModel)
            child = new MessageCommunicationEditPart();
       
       
        
        else if (model instanceof ImportLineModel)
            child = new ImportLineEditPart();
        else if (model instanceof MergeLineModel)
            child = new MergeLineEditPart();
        
        
        else if (model instanceof UMLNoteModel)
            child = new UMLNoteEditPart();
        else if (model instanceof LineModel)
            child = new LineEditPart();
        else if (model instanceof LineTextModel)
            child = new LineTextEditPart();
        //		else if (model instanceof ClassPanModel){
        //		ClassPanModel classPanModel = (ClassPanModel)model;
        //		child = new ClassPanContainerEditPart(classPanModel.getSize().width,classPanModel.getSize().height);
        //		}
        //		else if (model instanceof UMLUseCaseDiagramModel)
        //		child = new LogicFlowContainerEditPart();
        else if (model instanceof ElementLabelModel)
            child = new UMLElementEditPart();
        else if (model instanceof FrameModel)
            child = new FrameEditPart();
        //		else if (model instanceof LED)
        //		child = new LEDEditPart();
        //		else if (model instanceof LogicLabel)
        //		child = new LogicLabelEditPart();
        //		else if (model instanceof Circuit)
        //		child = new CircuitEditPart();
        //		else if (model instanceof Gate)
        //		child = new GateEditPart();
        //		else if (model instanceof SimpleOutput)
        //		child = new OutputEditPart();
        //		else if (model instanceof ClassModel)
        //		child = new ClassContainerEditPart();
        //Note that subclasses of LogicDiagram have already been matched above, like Circuit
        else if (model instanceof UMLDiagramModel)
            child = new UMLDiagramEditPart();
        if (child != null)
            child.setModel(model);
        return child;
    }
}
