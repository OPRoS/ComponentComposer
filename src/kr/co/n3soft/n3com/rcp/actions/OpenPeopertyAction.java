package kr.co.n3soft.n3com.rcp.actions;

import java.util.ArrayList;
import java.util.List;

import kr.co.n3soft.n3com.edit.ComponentEditPart;
import kr.co.n3soft.n3com.edit.FinalActivityEditPart;
import kr.co.n3soft.n3com.edit.FinalActorEditPart;
import kr.co.n3soft.n3com.edit.FinalClassEditPart;
import kr.co.n3soft.n3com.edit.FinalPackageEditPart;
import kr.co.n3soft.n3com.edit.FinalStrcuturedActivityEditPart;
import kr.co.n3soft.n3com.edit.FrameEditPart;
import kr.co.n3soft.n3com.edit.InterfaceEditPart;
import kr.co.n3soft.n3com.edit.LifeLineEditPart;
import kr.co.n3soft.n3com.edit.NodeEditPart;
import kr.co.n3soft.n3com.edit.StateEditPart;
import kr.co.n3soft.n3com.edit.StrcuturedStateEditPart;
import kr.co.n3soft.n3com.edit.UseCaseEditPart;
import kr.co.n3soft.n3com.lang.N3Messages;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.diagram.N3EditorDiagramModel;
import kr.co.n3soft.n3com.model.diagram.UMLDiagramModel;
import kr.co.n3soft.n3com.project.browser.UMLTreeModel;
import kr.co.n3soft.n3com.project.dialog.DetailPropertyDiaolg;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorPart;

public class OpenPeopertyAction extends SelectionAction {
	N3EditorDiagramModel n3EditorDiagramModel;
	N3EditorDiagramModel n3EditorPackageDiagramModel;//2008040401PKY S "패키지 오른쪽 클릭 시 OpenDiagram 메뉴 추가  여러 다이어그램이있을경우 리스트 나오도록"
	private UMLDiagramModel parent;
	UMLTreeModel treeModel = null;
	  private UMLModel child;
    public OpenPeopertyAction(IEditorPart editor) {
        super(editor);
        this.setId("OpenPeopertyAction");
        this.setText("OpenProperty");
        this.setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getImage(354)));//PKY 08070101 S 팝업 메뉴 이미지 삽입
       
    }
    public OpenPeopertyAction(){
   	 super(null);
   }

    protected boolean calculateEnabled() {
        // TODO Workaround for Bug 82622/39369.  Should be removed when 39369 is fixed.
    	List list = ProjectManager.getInstance().getSelectNodes();
        if (list != null && list.size() == 1) {
            Object obj = list.get(0);
        }
        return true;
          
    }

    public void run() {
    	DetailPropertyDiaolg dialog = new DetailPropertyDiaolg(this.getWorkbenchPart().getSite().getShell());
//        Object value = getValue();
        //        if (value != null) {
        //			dialog.attributes = (java.util.ArrayList)value;
        //		}
        dialog.open();
    	
    }
    //2008040401PKY S "패키지 오른쪽 클릭 시 OpenDiagram 메뉴 추가  여러 다이어그램이있을경우 리스트 나오도록"
	public N3EditorDiagramModel getN3EditorPackageDiagramModel() {
		return n3EditorPackageDiagramModel;
	}
	public void setN3EditorPackageDiagramModel(	N3EditorDiagramModel editorPackageDiagramModel) {
		n3EditorPackageDiagramModel = editorPackageDiagramModel;
	}
    //2008040401PKY E "패키지 오른쪽 클릭 시 OpenDiagram 메뉴 추가  여러 다이어그램이있을경우 리스트 나오도록"
}