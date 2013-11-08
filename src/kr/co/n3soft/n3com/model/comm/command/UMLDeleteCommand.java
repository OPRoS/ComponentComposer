package kr.co.n3soft.n3com.model.comm.command;

import java.util.ArrayList;
import java.util.List;

import kr.co.n3soft.n3com.model.activity.TextAttachModel;
import kr.co.n3soft.n3com.model.comm.LineModel;
import kr.co.n3soft.n3com.model.comm.NodeContainerModel;
import kr.co.n3soft.n3com.model.comm.PortContainerModel;
import kr.co.n3soft.n3com.model.comm.PortModel;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.comm.UMLModelGuide;
import kr.co.n3soft.n3com.model.component.AtomicComponentModel;
import kr.co.n3soft.n3com.model.component.ComponentModel;
import kr.co.n3soft.n3com.model.diagram.N3EditorDiagramModel;
import kr.co.n3soft.n3com.model.diagram.UMLDiagramModel;
import kr.co.n3soft.n3com.project.browser.UMLTreeModel;
import kr.co.n3soft.n3com.projectmanager.PortManager;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.examples.logicdesigner.LogicMessages;

public class UMLDeleteCommand extends Command {
    private UMLModel child;
    private UMLDiagramModel parent;
    private UMLModelGuide vGuide, hGuide;
    private int vAlign, hAlign;
    private int index = -1;
    private List sourceConnections = new ArrayList();
    private List targetConnections = new ArrayList();

    public UMLDeleteCommand() {
        super(LogicMessages.DeleteCommand_Label);
    }

    private void deleteConnections(UMLModel part) {
        if (part instanceof UMLDiagramModel) {
            List children = ((UMLDiagramModel)part).getChildren();
            for (int i = 0; i < children.size(); i++)
            	//2008042201PKY S
            	if(!(children.get(i) instanceof NodeContainerModel)){
                deleteConnections((UMLModel)children.get(i));
            	}
          //2008042201PKY E
        }
        sourceConnections.addAll(part.getSourceConnections());
        for (int i = 0; i < sourceConnections.size(); i++) {
            LineModel wire = (LineModel)sourceConnections.get(i);
            wire.detachSource();
            wire.detachTarget();
        }
        targetConnections.addAll(part.getTargetConnections());
        for (int i = 0; i < targetConnections.size(); i++) {
            LineModel wire = (LineModel)targetConnections.get(i);
            wire.detachSource();
            wire.detachTarget();
        }
    }

    private void detachFromGuides(UMLModel part) {
        if (part.getVerticalGuide() != null) {
            vGuide = part.getVerticalGuide();
            vAlign = vGuide.getAlignment(part);
            vGuide.detachPart(part);
        }
        if (part.getHorizontalGuide() != null) {
            hGuide = part.getHorizontalGuide();
            hAlign = hGuide.getAlignment(part);
            hGuide.detachPart(part);
        }
    }

    public void execute() {
        primExecute();
    }

    protected void primExecute() {
        try {
        
        	
            deleteConnections(child);
            detachFromGuides(child);
            index = parent.getChildren().indexOf(child);
            try{
            parent.removeChild(child);
            }
            catch(Exception e){
            	e.printStackTrace();
            }
            
           
            //등록
            if (child instanceof PortContainerModel) {
                PortContainerModel ipc = (PortContainerModel)child;
                for (int i = 0; i < ipc.getPorts().size(); i++) {
                    deleteConnections((UMLModel)ipc.getPorts().get(i));
                    detachFromGuides((UMLModel)ipc.getPorts().get(i));
                }
                ipc.deletePort(null, parent);
            }
            if (child instanceof TextAttachModel) {
                TextAttachModel ipc = (TextAttachModel)child;
                ipc.removeTextAttachParentDiagram(parent, null);
            }
            if(child instanceof PortModel){
            	PortModel pm = (PortModel)child;
            	UMLModel atomic = pm.getPortContainerModel();
            	if(atomic instanceof AtomicComponentModel){
            		AtomicComponentModel acm = (AtomicComponentModel)atomic;
            		AtomicComponentModel acore  = acm.getCoreDiagramCmpModel();
            		if(acore!=null)
            		ProjectManager.getInstance().getModelBrowser().searchCoreDiagramIdModel2(acore.getUMLDataModel().getId());
            		else
            			ProjectManager.getInstance().getModelBrowser().searchCoreDiagramIdModel2(acm.getUMLDataModel().getId());
            	}
            	else
            	 ProjectManager.getInstance().getModelBrowser().searchComponentDiagramIdModel(pm.getPortContainerModel().getUMLDataModel().getId());
         		java.util.ArrayList al = ProjectManager.getInstance().getSearchModel();
         		System.out.println("dddd");
         		for(int i = 0; i<al.size();i++){
         			UMLModel um1 = (UMLModel)al.get(i);
         			if(um1!=pm){
         				
         				    if(um1 instanceof AtomicComponentModel){
         				    	AtomicComponentModel cm = (AtomicComponentModel)um1;
         				    	
//         				    	cm.getCoreDiagramCmpModel()
             					PortManager pmr = cm.getPortManager();
             					for(int i1=0;i1<pmr.getPorts().size();i1++){
             						PortModel pm1 = (PortModel)pmr.getPorts().get(i1);
             						if(pm1.getUMLDataModel().getId().equals(pm.getUMLDataModel().getId())){
             							  deleteConnections(pm1);
             					            detachFromGuides(pm1);
             					           UMLTreeModel treeObject= pm1.getUMLTreeModel();
             					           if(treeObject!=null){
             					        	  ProjectManager.getInstance().deleteUMLModel(treeObject);

             									ProjectManager.getInstance().removeUMLNode(treeObject.getParent(), treeObject);
             									//PKY 08060201 S 모델 브라우져 여러 개 삭제되도록 수정
//             									if(treeObject.getRefModel()!=null
//             											&& treeObject.getRefModel() instanceof PortModel){
//             										PortModel pm = (PortModel)treeObject.getRefModel();
//
//             										ProjectManager.getInstance().getModelBrowser().searchComponentDiagramIdModel(pm.getUMLDataModel().getId());
//             										java.util.ArrayList sm = ProjectManager.getInstance().getSearchModel();
//             										//						    System.out.println("------>");
//             										for(int i=0;i<sm.size();i++){
//             											UMLModel um =(UMLModel)sm.get(i);
//             											if(um instanceof PortModel){
//             												N3EditorDiagramModel nm =um.getN3EditorDiagramModelTemp();
//             												if(nm!=null){
//             													PortModel pm1 =(PortModel)um;
//             													ComponentModel cm = (ComponentModel)pm1.getPortContainerModel();
//             													cm.getPortManager().remove(pm1);
//             													nm.removeChild(um);
//
//             												}
//             											}
//             										}
//             									}
             					           }
             					           else{
//             					        	  UMLTreeModel ut  = cm.getCoreUMLTreeModel(); 
             					        	   cm.removePortTreeModel(pm1);
             					           }
             					            if(pm1.getAcceptParentModel() instanceof N3EditorDiagramModel){
             					            	N3EditorDiagramModel nd = (N3EditorDiagramModel)pm1.getAcceptParentModel();
             					            index = nd.getChildren().indexOf(pm1);
             					            nd.removeChild(pm1);
             					            }
             						}
             					}
         				    }
         				    else if(um1 instanceof ComponentModel){
             					ComponentModel cm = (ComponentModel)um1;
             					PortManager pmr = cm.getPortManager();
             					for(int i1=0;i1<pmr.getPorts().size();i1++){
             						PortModel pm1 = (PortModel)pmr.getPorts().get(i1);
             						if(pm1.getUMLDataModel().getId().equals(pm.getUMLDataModel().getId())){
             							  deleteConnections(pm1);
             					            detachFromGuides(pm1);
             					            if(pm1.getAcceptParentModel() instanceof N3EditorDiagramModel){
             					            	N3EditorDiagramModel nd = (N3EditorDiagramModel)pm1.getAcceptParentModel();
             					            index = nd.getChildren().indexOf(pm1);
             					            nd.removeChild(pm1);
             					            }
             						}
             					}
             					
//             					PortModel pm2 = new EventInputPortModel(cm);

             				}
             					
             				
//             				else if(pm1 instanceof MethodInputPortModel)
//             				newModelpm.createPort(new MethodInputPortModel(newModelpm), (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
//             				else if(pm1 instanceof DataInputPortModel)
//             					newModelpm.createPort(new DataInputPortModel(newModelpm), (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
//             				else if(pm1 instanceof DataOutputPortModel)
//             					newModelpm.createPort(new DataOutputPortModel(newModelpm), (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
//             				else if(pm1 instanceof EventInputPortModel)
//             					newModelpm.createPort(new EventInputPortModel(newModelpm), (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
//             				else if(pm1 instanceof EventOutputPortModel)
//             					newModelpm.createPort(new EventOutputPortModel(newModelpm), (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram());
             				
             				
//             			}
         				
         			}
         		}
            }
            
            //		if(child instanceof IPortContainer){
            //			IPortContainer em = (IPortContainer)child;
            //			deleteConnections((UMLModel)em.getPort());
            //			detachFromGuides((UMLModel)em.getPort());
            //			em.deletePort(null,parent);
            ////			parent.removeChild(em.exceptionObjectNode);
            ////			parent.removeChild(em.exceptionObjectNode.getElementLabelModel());
            //			
            ////			parent.addChild(child,index);
            //		}
            //		if(child instanceof InitialModel){
            //			InitialModel em = (InitialModel)child;
            ////			deleteConnections(em.exceptionObjectNode);
            ////			detachFromGuides(em.exceptionObjectNode);
            ////			parent.removeChild(em.exceptionObjectNode);
            //			parent.removeChild(em.getElementLabelModel());
            //			
            ////			parent.addChild(child,index);
            //		}
            //		if(child instanceof FinalModel){
            //			FinalModel em = (FinalModel)child;
            ////			deleteConnections(em.exceptionObjectNode);
            ////			detachFromGuides(em.exceptionObjectNode);
            ////			parent.removeChild(em.exceptionObjectNode);
            //			parent.removeChild(em.getElementLabelModel());
            //			
            ////			parent.addChild(child,index);
            //		}
            //		if(child instanceof FlowFinalModel){
            //			FlowFinalModel em = (FlowFinalModel)child;
            ////			deleteConnections(em.exceptionObjectNode);
            ////			detachFromGuides(em.exceptionObjectNode);
            ////			parent.removeChild(em.exceptionObjectNode);
            //			parent.removeChild(em.getElementLabelModel());
            //			
            ////			parent.addChild(child,index);
            //	
            //		}
            //		if(child instanceof SynchModel){
            //			SynchModel em = (SynchModel)child;
            ////			deleteConnections(em.exceptionObjectNode);
            ////			detachFromGuides(em.exceptionObjectNode);
            ////			parent.removeChild(em.exceptionObjectNode);
            //			parent.removeChild(em.getElementLabelModel());
            //			
            ////			parent.addChild(child,index);
            //		
            //		}
            //		if(child instanceof DecisionModel){
            //			DecisionModel em = (DecisionModel)child;
            ////			deleteConnections(em.exceptionObjectNode);
            ////			detachFromGuides(em.exceptionObjectNode);
            ////			parent.removeChild(em.exceptionObjectNode);
            //			parent.removeChild(em.getElementLabelModel());
            //			
            ////			parent.addChild(child,index);
            //		
            //		}
            //		if(child instanceof HForkJoinModel){
            //			HForkJoinModel em = (HForkJoinModel)child;
            ////			deleteConnections(em.exceptionObjectNode);
            ////			detachFromGuides(em.exceptionObjectNode);
            ////			parent.removeChild(em.exceptionObjectNode);
            //			parent.removeChild(em.getElementLabelModel());
            //			
            ////			parent.addChild(child,index);
            //		
            //		}
            //		if(child instanceof VForkJoinModel){
            //			VForkJoinModel em = (VForkJoinModel)child;
            ////			deleteConnections(em.exceptionObjectNode);
            ////			detachFromGuides(em.exceptionObjectNode);
            ////			parent.removeChild(em.exceptionObjectNode);
            //			parent.removeChild(em.getElementLabelModel());
            //			
            ////			parent.addChild(child,index);
            //		
            //		}
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        //		UMLTreeModel treeModel = parent.getUMLTreeModel().getParent();
        //		
        //		if(treeModel instanceof UMLTreeParentModel){
        //			ProjectManager.getInstance().removeUMLNode(child);
        //		}
    }

    private void reattachToGuides(UMLModel part) {
        if (vGuide != null)
            vGuide.attachPart(part, vAlign);
        if (hGuide != null)
            hGuide.attachPart(part, hAlign);
    }

    public void redo() {
        primExecute();
    }

    private void restoreConnections() {
        for (int i = 0; i < sourceConnections.size(); i++) {
            LineModel wire = (LineModel)sourceConnections.get(i);
            wire.attachSource();
            wire.attachTarget();
        }
        sourceConnections.clear();
        for (int i = 0; i < targetConnections.size(); i++) {
            LineModel wire = (LineModel)targetConnections.get(i);
            wire.attachSource();
            wire.attachTarget();
        }
        targetConnections.clear();
    }

    public void setChild(UMLModel c) {
        child = c;
    }

    public void setParent(UMLDiagramModel p) {
        parent = p;
    }

    public void undo() {
        parent.addChild(child, index);
        restoreConnections();
        reattachToGuides(child);
        UMLTreeModel treeModel = parent.getUMLTreeModel().getParent();
        if (child instanceof PortContainerModel) {
            PortContainerModel ipc = (PortContainerModel)child;
            for (int i = 0; i < ipc.getPorts().size(); i++) {
                restoreConnections();
                reattachToGuides((UMLModel)ipc.getPorts().get(i));
            }
            ipc.undoDeletePort(null, parent);
        }
        if (child instanceof TextAttachModel) {
            TextAttachModel ipc = (TextAttachModel)child;
            ipc.addTextAttachParentDiagram(parent, null);
        }
    }
}
