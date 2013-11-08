package kr.co.n3soft.n3com.model.comm.command;

import java.util.List;

import kr.co.n3soft.n3com.model.activity.TextAttachModel;
import kr.co.n3soft.n3com.model.comm.LineTextModel;
import kr.co.n3soft.n3com.model.comm.PortContainerModel;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.diagram.UMLDiagramModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.examples.logicdesigner.LogicMessages;

public class UMLOrphanChildCommand extends Command {
	private Point oldLocation;
	private UMLDiagramModel diagram;
	private UMLModel child;
	private int index;

	public UMLOrphanChildCommand() {
		super(LogicMessages.OrphanChildCommand_Label);
	}

	public void execute() {

		//20080811IJS ����
		if(diagram != null){
			if(diagram instanceof UMLModel){
				UMLModel um = diagram;
				if(um.isReadOnlyModel){
					return;
				}
			}
		}
		//20080811IJS ��

		if(child instanceof LineTextModel){
			return;
		}
		//PKY 08061101 S ���� �� Group���� �����Ⱥκп� �־� ����
//		if(child instanceof GroupModel ){
//		return;
//		}
//		if(child instanceof StateLifelineModel ){
//		return;
//		}
		//PKY 08061101 E ���� �� Group���� �����Ⱥκп� �־� ����


		List children = diagram.getChildren();
		index = children.indexOf(child);
		oldLocation = child.getLocation();
		diagram.removeChild(child);
		//���
		if (child instanceof PortContainerModel) {
			PortContainerModel ipc = (PortContainerModel)child;
			ipc.orphanChildPort(null, diagram);
		}
		if (child instanceof TextAttachModel) {
			TextAttachModel ipc = (TextAttachModel)child;
			ipc.removeTextAttachParentDiagram(diagram, null);
		}
		//		if(child instanceof ExceptionModel){
		//			ExceptionModel em = (ExceptionModel)child;
		//			diagram.removeChild(em.exceptionObjectNode);
		//			diagram.removeChild(em.exceptionObjectNode.getElementLabelModel());
		//			
		////			parent.addChild(child,index);
		//		}
	}

	public void redo() {
		diagram.removeChild(child);
	}

	public void setChild(UMLModel child) {
		this.child = child;
	}

	public void setParent(UMLDiagramModel parent) {
		diagram = parent;
	}

	public void undo() {
		child.setLocation(oldLocation);
		diagram.addChild(child, index);
		//���
		if (child instanceof PortContainerModel) {
			PortContainerModel ipc = (PortContainerModel)child;
			ipc.undoOrphanChildPort(null, diagram);
		}
		if (child instanceof TextAttachModel) {
			TextAttachModel ipc = (TextAttachModel)child;
			ipc.addTextAttachParentDiagram(diagram, null);
		}
	}
}
