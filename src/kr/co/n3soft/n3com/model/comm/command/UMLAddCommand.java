package kr.co.n3soft.n3com.model.comm.command;

import kr.co.n3soft.n3com.model.activity.TextAttachModel;
import kr.co.n3soft.n3com.model.comm.LineTextModel;
import kr.co.n3soft.n3com.model.comm.PortContainerModel;
import kr.co.n3soft.n3com.model.comm.UMLContainerModel;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.diagram.UMLDiagramModel;

import org.eclipse.gef.examples.logicdesigner.LogicMessages;

public class UMLAddCommand extends org.eclipse.gef.commands.Command {
    private UMLModel child;
    private UMLDiagramModel parent;
    private int index = -1;

    public UMLAddCommand() {
        super(LogicMessages.AddCommand_Label);
    }

    public void execute() {
    	
  
    	 if(child instanceof LineTextModel){
          	return;
          }
    	//PKY 08060201 S GRoup 포함형태로 변경
//    	 if(child instanceof GroupModel){
//    		 return;
//    	 }
//    	 if(child instanceof StateLifelineModel ){
//    		 return;
//    	 }
    	//PKY 08060201 E GRoup 포함형태로 변경
    	if (index < 0) {
            parent.addChild(child);
        }
        else {
            parent.addChild(child, index);
        }

        
        //등록
        if (child instanceof PortContainerModel) {
            PortContainerModel ipc = (PortContainerModel)child;
            ipc.createPort(null, parent);
        }
        if (child instanceof TextAttachModel) {
            TextAttachModel ipc = (TextAttachModel)child;
            ipc.addTextAttachParentDiagram(parent, null);
        }
        if (parent instanceof UMLContainerModel) {
            UMLModel childUMLModel = (UMLModel)child;
            if (childUMLModel.getUMLTreeModel() != null) {
                childUMLModel.getUMLTreeModel().addN3UMLModelDeleteListener(parent);
            }
        }
        
    }

    public UMLDiagramModel getParent() {
        return parent;
    }

    public void redo() {
        if (index < 0)
            parent.addChild(child);
        else
            parent.addChild(child, index);
    }

    public void setChild(UMLModel subpart) {
        child = subpart;
    }

    public void setIndex(int i) {
        index = i;
    }

    public void setParent(UMLDiagramModel newParent) {
        parent = newParent;
    }

    public void undo() {
        parent.removeChild(child);
        //등록
        if (child instanceof PortContainerModel) {
            PortContainerModel ipc = (PortContainerModel)child;
            ipc.undoCreatePort(null, parent);
        }
        if (child instanceof TextAttachModel) {
            TextAttachModel ipc = (TextAttachModel)child;
            ipc.removeTextAttachParentDiagram(parent, null);
        }
    }
}
