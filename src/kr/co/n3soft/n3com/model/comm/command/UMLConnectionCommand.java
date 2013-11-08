package kr.co.n3soft.n3com.model.comm.command;

import kr.co.n3soft.n3com.model.activity.FinalActivityModel;
import kr.co.n3soft.n3com.model.activity.ObjectFlowLineModel;
import kr.co.n3soft.n3com.model.comm.LineModel;
import kr.co.n3soft.n3com.model.comm.PortContainerModel;
import kr.co.n3soft.n3com.model.comm.PortModel;
import kr.co.n3soft.n3com.model.comm.UMLContainerModel;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.umlclass.DependencyLineModel;
import kr.co.n3soft.n3com.model.usecase.GeneralizeLineModel;
import kr.co.n3soft.n3com.model.usecase.RealizeLineModel;
import kr.co.n3soft.n3com.projectmanager.UML20RuleManager;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.examples.logicdesigner.LogicMessages;

public class UMLConnectionCommand extends Command {
	protected UMLModel oldSource;
	protected String oldSourceTerminal;
	protected UMLModel oldTarget;
	protected String oldTargetTerminal;
	protected UMLModel source;
	protected String sourceTerminal;
	protected UMLModel target;
	protected String targetTerminal;
	protected LineModel wire;

	public UMLConnectionCommand() {
		super(LogicMessages.ConnectionCommand_Label);
	}

	public boolean canExecute() {
		//20080811IJS 시작
		if(source!=null && this.source instanceof UMLModel){
			UMLModel um = this.source;
			if(um.isReadOnlyModel){
				return false;
			}
		}
		if(target!=null && this.target instanceof UMLModel){
			UMLModel um = this.target;
			if(um.isReadOnlyModel){
				return false;
			}
		}
		//20080811IJS 끝
		//20080908IJS
		
		return true;
	}

	public void execute() {
		//20080908IJS
//		if( !ProjectManager.getInstance().isMsgDrag()){
//                return;
//		}
//		else{
//			ProjectManager.getInstance().setMsgDrag(false);
//		}
		
		try{
			if(source != null && target != null){
				//PKY 08072401 S ControlFlow 연결 시 자동으로 포트 생성되어 연결되도록
				if(source.getParentModel()!=null &&  target.getParentModel()!=null){

					if(wire instanceof ObjectFlowLineModel){
						if(source.getParentModel()instanceof FinalActivityModel){
							PortContainerModel sourceModel = (PortContainerModel)source.getParentModel();
							PortModel sourcePort = new PortModel(sourceModel);
							sourceModel.createPort(sourcePort, (UMLContainerModel)sourceModel.getAcceptParentModel());
							source =sourcePort;
						}
						if(target.getParentModel() instanceof FinalActivityModel){
							PortContainerModel targetModel = (PortContainerModel)target.getParentModel();
							PortModel targetPort = new PortModel(targetModel);
							targetModel.createPort(targetPort, (UMLContainerModel)targetModel.getAcceptParentModel());
							target =targetPort;
						}
					}
					if(!UML20RuleManager.getInstance().isLink(source.getParentModel(), target.getParentModel() , wire)){
						return ;
					}

				}
				else if(source.getParentModel()==null && target.getParentModel()!=null){
					if(wire instanceof ObjectFlowLineModel){
						if(source instanceof FinalActivityModel){
							PortContainerModel sourceModel = (PortContainerModel)source.getParentModel();
							PortModel sourcePort = new PortModel(sourceModel);
							sourceModel.createPort(sourcePort, (UMLContainerModel)sourceModel.getAcceptParentModel());
							source =sourcePort;
						}
						if(target.getParentModel() instanceof FinalActivityModel){
							PortContainerModel targetModel = (PortContainerModel)target.getParentModel();
							PortModel targetPort = new PortModel(targetModel);
							targetModel.createPort(targetPort, (UMLContainerModel)targetModel.getAcceptParentModel());
							target =targetPort;
						}
					}
					if(!UML20RuleManager.getInstance().isLink(source, target.getParentModel() , wire)){
						return ;
					}
				}
				else if(source.getParentModel()!=null && target.getParentModel()==null){
					if(wire instanceof ObjectFlowLineModel){
						if(source.getParentModel()instanceof FinalActivityModel){
							PortContainerModel sourceModel = (PortContainerModel)source.getParentModel();
							PortModel sourcePort = new PortModel(sourceModel);
							sourceModel.createPort(sourcePort, (UMLContainerModel)sourceModel.getAcceptParentModel());
							source =sourcePort;
						}
						if(target.getParentModel() instanceof FinalActivityModel){
							PortContainerModel targetModel = (PortContainerModel)target.getParentModel();
							PortModel targetPort = new PortModel(targetModel);
							targetModel.createPort(targetPort, (UMLContainerModel)targetModel.getAcceptParentModel());
							target =targetPort;
						}
					}
					if(!UML20RuleManager.getInstance().isLink(source.getParentModel(), target , wire)){
						return ;
					}
				}
				else if(source.getParentModel()==null && target.getParentModel()==null){
					if(wire instanceof ObjectFlowLineModel){
						if(source.getParentModel()instanceof FinalActivityModel){
							PortContainerModel sourceModel = (PortContainerModel)source.getParentModel();
							PortModel sourcePort = new PortModel(sourceModel);
							sourceModel.createPort(sourcePort, (UMLContainerModel)sourceModel.getAcceptParentModel());
							source =sourcePort;
						}
						if(target.getParentModel() instanceof FinalActivityModel){
							PortContainerModel targetModel = (PortContainerModel)target.getParentModel();
							PortModel targetPort = new PortModel(targetModel);
							targetModel.createPort(targetPort, (UMLContainerModel)targetModel.getAcceptParentModel());
							target =targetPort;
						}
					}
					if(!UML20RuleManager.getInstance().isLink(source, target , wire)){
						return ;
					}
				}
				//PKY 08072401 E ControlFlow 연결 시 자동으로 포트 생성되어 연결되도록

			}//2008041606PKY S
			
			//2008041606PKY E
			if (source != null) {

				if(wire instanceof GeneralizeLineModel){
					if(source.getParentModel()!=null &&  target.getParentModel()!=null){
						source.getParentModel().getParents().add(target.getParentModel());

					}
					else if(source.getParentModel()==null && target.getParentModel()!=null){
						source.getParents().add(target.getParentModel());
					}
					else if(source.getParentModel()!=null && target.getParentModel()==null){
						source.getParentModel().getParents().add(target);
					}
					else if(source.getParentModel()==null && target.getParentModel()==null){
						source.getParents().add(target);
					}
				}
				else if(wire instanceof RealizeLineModel){
					if(source.getParentModel()!=null &&  target.getParentModel()!=null){
						source.getParentModel().getInterfaces().add(target.getParentModel());

					}
					else if(source.getParentModel()==null && target.getParentModel()!=null){
						source.getInterfaces().add(target.getParentModel());
					}
					else if(source.getParentModel()!=null && target.getParentModel()==null){
						source.getParentModel().getInterfaces().add(target);
					}
					else if(source.getParentModel()==null && target.getParentModel()==null){
						source.getInterfaces().add(target);
					}
				}
				
				if(wire instanceof DependencyLineModel && !UML20RuleManager.getInstance().isLink(source, wire.getSource() , wire)){
					return ;
				}

				wire.detachSource();
				wire.setSource(source);
				wire.setSourceTerminal(sourceTerminal);
				wire.attachSource();

			}
			if (target != null) {
				if(wire instanceof DependencyLineModel && !UML20RuleManager.getInstance().isLink(wire.getTarget(), target , wire)){
					return ;
				}
				wire.detachTarget();
				wire.setTarget(target);
				wire.setTargetTerminal(targetTerminal);
				wire.attachTarget();
				//ijs08619
				


			}
			if (source == null && target == null) {

				if(wire instanceof GeneralizeLineModel){
					if(target!=null)
						source.getParents().remove(wire.getTarget());
				}

				wire.detachSource();
				wire.detachTarget();
				wire.setTarget(null);
				wire.setSource(null);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	public String getLabel() {
		return LogicMessages.ConnectionCommand_Description;
	}

	public UMLModel getSource() {
		return source;
	}

	public java.lang.String getSourceTerminal() {
		return sourceTerminal;
	}

	public UMLModel getTarget() {
		return target;
	}

	public String getTargetTerminal() {
		return targetTerminal;
	}

	public LineModel getWire() {
		return wire;
	}

	public void redo() {
		execute();
	}

	public void setSource(UMLModel newSource) {
		source = newSource;
	}

	public void setSourceTerminal(String newSourceTerminal) {
		sourceTerminal = newSourceTerminal;
	}

	public void setTarget(UMLModel newTarget) {
		target = newTarget;
	}

	public void setTargetTerminal(String newTargetTerminal) {
		targetTerminal = newTargetTerminal;
	}

	public void setWire(LineModel w) {
		wire = w;
		oldSource = w.getSource();
		oldTarget = w.getTarget();
		oldSourceTerminal = w.getSourceTerminal();
		oldTargetTerminal = w.getTargetTerminal();
	}

	public void undo() {
		try{
			source = wire.getSource();
			target = wire.getTarget();
			sourceTerminal = wire.getSourceTerminal();
			targetTerminal = wire.getTargetTerminal();
			wire.detachSource();
			wire.detachTarget();
			if(oldSource!=null && oldTarget!=null){
				wire.setUndo(true);//PKY 08081801 S 저장 불러온 후 메시지 삭제 후 Undo Redo를 할경우 메시지가 계속생기는문제
				wire.setSource(oldSource);
				wire.setTarget(oldTarget);
				wire.setSourceTerminal(oldSourceTerminal);
				wire.setTargetTerminal(oldTargetTerminal);
				wire.attachSource();
				wire.attachTarget();
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
