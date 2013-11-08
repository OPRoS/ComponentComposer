//khg 10.4.27 노드삭제 
package kr.co.n3soft.n3com.rcp.actions;

import kr.co.n3soft.n3com.UMLEditor;
import kr.co.n3soft.n3com.edit.UMLEditPart;
import kr.co.n3soft.n3com.model.comm.UMLElementModel;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.component.ComponentModel;
import kr.co.n3soft.n3com.model.diagram.N3EditorDiagramModel;
import kr.co.n3soft.n3com.project.browser.UMLTreeParentModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindow;

public class DeleteNodeAction extends SelectionAction {
	
	IWorkbenchWindow workbenchWindow;
	public static   int ID=0;
	IEditorInput input = null;
	UMLModel selectModel;
	
	
	public DeleteNodeAction(IEditorPart editor) {
		super(editor);
		setId("DeleteNodeAction");
		
//		String id =  N3MessageManager.getN3Message().POPUP_DELETE;

		this.setText( "Delete Node");
		//setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getImage(319)));
	}
	
	protected boolean calculateEnabled() {
		
		StructuredSelection list = (StructuredSelection)this.getSelection();
		if (list != null && list.size() == 1) {
			Object obj = list.getFirstElement();
			if(obj instanceof UMLEditPart){
				UMLEditPart u = (UMLEditPart)obj;
				selectModel= (UMLModel)u.getModel();
			}
		}else{
			return false;
		}
		UMLElementModel aaa = ProjectManager.getInstance().getSelectPropertyUMLElementModel();
		if(aaa instanceof ComponentModel){
			String adv=((ComponentModel)aaa).getNodeItemModelId();

			if(adv!=null){
				return true;
			}
		}
		return false;
	}

	

	public void run() {
		try{
			UMLEditor ue	 = ProjectManager.getInstance().getUMLEditor();	
			N3EditorDiagramModel nd = ue.getDiagram();
			UMLModel addModel = selectModel;			
			ComponentModel cm = (ComponentModel)addModel;
			UMLTreeParentModel utm = (UMLTreeParentModel)cm.getUMLTreeModel();
			cm.setNodeItemModel(null);
			cm.setNodeItemModelId(null);
			cm.setNode(null);
			//addModel.setNode("");삭제후 다시 삽입 방법을 쓰는 방식에는 필요 없음
			//addModel.setPropertyValue("ID_NODE","");삭제후 다시 삽입 방법을 쓰는 방식에는 필요 없음
			//N3SelectionManager aa1=new N3SelectionManager();
			nd.removeChild(selectModel,false);
			nd.addChild(addModel,0);
			
			ProjectManager.getInstance().setSelectPropertyUMLElementModel((UMLElementModel)cm);			
			//ProjectManager.getInstance().setSelectPropertyUMLElementModel(addModel);
		
			
		
		
			
			//addModel.nodeProp=null;
			//addModel.setNode("");
			
		
			//((UMLElementModel)ProjectManager.getInstance().getSelectPropertyUMLElementModel()).setPropertyValue("ID_NODE",null);
			//firePropertyChange("ID_NODE", null, null);
			//UMLElementModel aaa = ProjectManager.getInstance().getSelectPropertyUMLElementModel();
			//NodeItemModel nodeItemModel = (NodeItemModel)selectModel.getClassModel().getUMLDataModel().getElementProperty("NodeItemModel");
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
