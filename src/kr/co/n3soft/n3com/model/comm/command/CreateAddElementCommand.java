package kr.co.n3soft.n3com.model.comm.command;

import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.comm.UMLModelGuide;
import kr.co.n3soft.n3com.model.comm.UMLModelRuler;
import kr.co.n3soft.n3com.model.diagram.UMLDiagramModel;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.examples.logicdesigner.LogicMessages;

public class CreateAddElementCommand extends Command {
	  private UMLModel child;
	    private Rectangle rect;
	    private UMLDiagramModel parent;

    public CreateAddElementCommand() {
       
    }

    public boolean canUndo() {
        return true;
    }

    public void execute() {
       
    }

    public void undo() {
       
    }
}

