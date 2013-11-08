package kr.co.n3soft.n3com.edit.policy;

import kr.co.n3soft.n3com.comm.figures.UMLFigure;
import kr.co.n3soft.n3com.edit.EndPointEditPart;
import kr.co.n3soft.n3com.edit.LifeLineEditPart;
import kr.co.n3soft.n3com.edit.UMLDiagramEditPart;
import kr.co.n3soft.n3com.edit.UMLEditPart;
import kr.co.n3soft.n3com.figures.UMLFigureFactory;
import kr.co.n3soft.n3com.model.activity.ControlFlowLineModel;
import kr.co.n3soft.n3com.model.activity.ObjectFlowLineModel;
import kr.co.n3soft.n3com.model.comm.LineModel;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.comm.command.UMLConnectionCommand;
import kr.co.n3soft.n3com.model.communication.MessageAssoicateLineModel;
import kr.co.n3soft.n3com.model.composite.ConnectorLineModel;
import kr.co.n3soft.n3com.model.composite.DelegateLineModel;
import kr.co.n3soft.n3com.model.composite.OccurrenceLineModel;
import kr.co.n3soft.n3com.model.composite.ProvidedInterfaceLineModel;
import kr.co.n3soft.n3com.model.composite.RepresentsLineModel;
import kr.co.n3soft.n3com.model.composite.RequiredInterfaceLineModel;
import kr.co.n3soft.n3com.model.composite.RoleBindingLineModel;
import kr.co.n3soft.n3com.model.umlclass.DependencyLineModel;
import kr.co.n3soft.n3com.model.umlclass.ImportLineModel;
import kr.co.n3soft.n3com.model.umlclass.MergeLineModel;
import kr.co.n3soft.n3com.model.usecase.AssociateLineModel;
import kr.co.n3soft.n3com.model.usecase.ExtendLineModel;
import kr.co.n3soft.n3com.model.usecase.GeneralizeLineModel;
import kr.co.n3soft.n3com.model.usecase.IncludeLineModel;
import kr.co.n3soft.n3com.model.usecase.NoteLineModel;
import kr.co.n3soft.n3com.model.usecase.RealizeLineModel;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.ReconnectRequest;

public class UMLNodeEditPolicy extends org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy {
    protected Connection createDummyConnection(Request req) {
        PolylineConnection conn = UMLFigureFactory.createNewWire(null);
        return conn;
    }

    protected Command getConnectionCompleteCommand(CreateConnectionRequest request) {
        UMLConnectionCommand command = (UMLConnectionCommand)request.getStartCommand();
      //20080822IJS
        command.setTarget(this.getParentModel());
        ConnectionAnchor ctor = getUMLEditPart().getTargetConnectionAnchor(request);
        //seq
        if(getUMLEditPart() instanceof LifeLineEditPart){
        	LifeLineEditPart lep = (LifeLineEditPart)getUMLEditPart();
        	lep.createTargetConnectionAnchor(ctor, command.getWire().getId());
        	 command.setTargetTerminal(command.getWire().getId());
              ctor = getUMLEditPart().getSourceConnectionAnchor(request);
             	lep.createSourceConnectionAnchor(ctor, command.getWire().getId());
             	 command.setSourceTerminal(command.getWire().getId());
        	 
        }
        if(getUMLEditPart() instanceof EndPointEditPart){
        	EndPointEditPart lep = (EndPointEditPart)getUMLEditPart();
        	lep.createTargetConnectionAnchor(ctor, command.getWire().getId());
        	 command.setTargetTerminal(command.getWire().getId());
              ctor = getUMLEditPart().getSourceConnectionAnchor(request);
             	lep.createSourceConnectionAnchor(ctor, command.getWire().getId());
             	 command.setSourceTerminal(command.getWire().getId());
        }
        else
        	command.setTargetTerminal(getUMLEditPart().mapConnectionAnchorToTerminal(ctor));
        if (ctor == null)
            return null;
        
        return command;
    }

    protected Command getConnectionCreateCommand(CreateConnectionRequest request) {
        //선 등록
        UMLConnectionCommand command = new UMLConnectionCommand();
        Object type = request.getNewObject();
//        MessageModel m = new MessageModel();
        if (type instanceof ExtendLineModel) {
            command.setWire(new ExtendLineModel());
        }
        else if (type instanceof IncludeLineModel) {
            command.setWire(new IncludeLineModel());
        }
        else if (type instanceof ConnectorLineModel) {
            command.setWire(new ConnectorLineModel());
        }
        else if (type instanceof MessageAssoicateLineModel) {
            command.setWire(new MessageAssoicateLineModel());
        }
        else if (type instanceof AssociateLineModel) {
            command.setWire(new AssociateLineModel());
        }
        else if (type instanceof GeneralizeLineModel) {
            command.setWire(new GeneralizeLineModel());
        }
        else if (type instanceof RealizeLineModel) {
            command.setWire(new RealizeLineModel());
        }
        else if (type instanceof NoteLineModel) {
            command.setWire(new NoteLineModel());
        }
        else if (type instanceof DependencyLineModel) {
            command.setWire(new DependencyLineModel());
        }
      //PKY 08072401 S ObjectFlow모델 추가
        else if (type instanceof ObjectFlowLineModel) {
            command.setWire(new ObjectFlowLineModel());
        }
      //PKY 08072401 E ObjectFlow모델 추가

        else if (type instanceof ControlFlowLineModel) {
            command.setWire(new ControlFlowLineModel());
        }
        else if (type instanceof RequiredInterfaceLineModel) {
            command.setWire(new RequiredInterfaceLineModel());
        }
        else if (type instanceof ProvidedInterfaceLineModel) {
            command.setWire(new ProvidedInterfaceLineModel());
        }
        else if (type instanceof RoleBindingLineModel) {
            command.setWire(new RoleBindingLineModel());
        }
        else if (type instanceof DelegateLineModel) {
            command.setWire(new DelegateLineModel());
        }
        else if (type instanceof OccurrenceLineModel) {
            command.setWire(new OccurrenceLineModel());
        }
        else if (type instanceof RepresentsLineModel) {
            command.setWire(new RepresentsLineModel());
        }
//        else if (type instanceof ManifestLineModel) {
//            command.setWire(new ManifestLineModel());
//        }
//        else if (type instanceof DeployLineModel) {
//            command.setWire(new DeployLineModel());
//        }

//        else if (type instanceof CommunicationPathLineModel) {
//            command.setWire(new CommunicationPathLineModel());
//        }
        
       
        
        else if (type instanceof ImportLineModel) {
            command.setWire(new ImportLineModel());
        }
        else if (type instanceof MergeLineModel) {
            command.setWire(new MergeLineModel());
        }
        
        else {
            command.setWire(new LineModel());
        }
        //		command.setWire(new LineModel());
        //20080822IJS
        command.setSource(getParentModel());
        ConnectionAnchor ctor = getUMLEditPart().getSourceConnectionAnchor(request);
        //seq
//        if(getUMLEditPart() instanceof LifeLineEditPart){
//        	LifeLineEditPart lep = (LifeLineEditPart)getUMLEditPart();
//        	lep.createSourceConnectionAnchor(ctor, m.getId());
//        	 command.setSourceTerminal(m.getId());
//        }
//        else
        command.setSourceTerminal(getUMLEditPart().mapConnectionAnchorToTerminal(ctor));
       
        request.setStartCommand(command);
        return command;
    }

    /**
     * Feedback should be added to the scaled feedback layer.
     * @see org.eclipse.gef.editpolicies.GraphicalEditPolicy#getFeedbackLayer()
     */
    protected IFigure getFeedbackLayer() {
		/*
		 * Fix for Bug# 66590
		 * Feedback needs to be added to the scaled feedback layer
		 */

        return getLayer(LayerConstants.SCALED_FEEDBACK_LAYER);
    }

    protected UMLEditPart getUMLEditPart() {
        return (UMLEditPart)getHost();
    }

    protected UMLModel getUMLModel() {
        return (UMLModel)getHost().getModel();
    }
    
  //20080822IJS 시작
    
    public UMLModel getParentModel(){
    	return this.getParentModel(this.getUMLEditPart());
    	
    }
    
    public UMLModel  getParentModel(UMLEditPart part){
    	if(part.getParent() instanceof UMLDiagramEditPart){
    	
    		return (UMLModel)part.getModel();
    	}
    	else if(part.getParent()!=null
    			&& part.getParent() instanceof UMLEditPart){
    		return this.getParentModel((UMLEditPart )part.getParent());
    	}
    	return null;
    	
    }
  //20080822IJS 끝
    
    

    protected Command getReconnectTargetCommand(ReconnectRequest request) {
        //		if (getUMLModel() instanceof LiveOutput ||
        //				getUMLModel() instanceof GroundOutput)
        //				return null;
        UMLConnectionCommand cmd = new UMLConnectionCommand();
        cmd.setWire((LineModel)request.getConnectionEditPart().getModel());
        ConnectionAnchor ctor = getUMLEditPart().getTargetConnectionAnchor(request);
      //20080822IJS
        cmd.setTarget(getParentModel());
        cmd.setTargetTerminal(getUMLEditPart().mapConnectionAnchorToTerminal(ctor));
        return cmd;
    }

    protected Command getReconnectSourceCommand(ReconnectRequest request) {
        UMLConnectionCommand cmd = new UMLConnectionCommand();
        cmd.setWire((LineModel)request.getConnectionEditPart().getModel());
        ConnectionAnchor ctor = getUMLEditPart().getSourceConnectionAnchor(request);
      //20080822IJS
        cmd.setSource(this.getParentModel());
        cmd.setSourceTerminal(getUMLEditPart().mapConnectionAnchorToTerminal(ctor));
        return cmd;
    }

    protected UMLFigure getNodeFigure() {
        return (UMLFigure)((GraphicalEditPart)getHost()).getFigure();
    }
}
