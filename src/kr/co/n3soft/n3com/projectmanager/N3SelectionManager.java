package kr.co.n3soft.n3com.projectmanager;

import kr.co.n3soft.n3com.edit.ArtfifactEditPart;
import kr.co.n3soft.n3com.edit.CentralBufferNodeEditPart;
import kr.co.n3soft.n3com.edit.CollaborationEditPart;
import kr.co.n3soft.n3com.edit.ComponentEditPart;
import kr.co.n3soft.n3com.edit.DataStoreEditPart;
import kr.co.n3soft.n3com.edit.DecisionEditPart;
import kr.co.n3soft.n3com.edit.DeploymentSpecificationEditPart;
import kr.co.n3soft.n3com.edit.DeviceEditPart;
import kr.co.n3soft.n3com.edit.EntryPointNoPortEditPart;
import kr.co.n3soft.n3com.edit.ExceptionEditPart;
import kr.co.n3soft.n3com.edit.ExecutionEnvironmentEditPart;
import kr.co.n3soft.n3com.edit.FinalActionEditPart;
import kr.co.n3soft.n3com.edit.FinalActivityEditPart;
import kr.co.n3soft.n3com.edit.FinalActorEditPart;
import kr.co.n3soft.n3com.edit.FinalBoundryEditPart;
import kr.co.n3soft.n3com.edit.FinalClassEditPart;
import kr.co.n3soft.n3com.edit.FinalEditPart;
import kr.co.n3soft.n3com.edit.FinalObjectNodeEditPart;
import kr.co.n3soft.n3com.edit.FinalPackageEditPart;
import kr.co.n3soft.n3com.edit.FinalStrcuturedActivityEditPart;
import kr.co.n3soft.n3com.edit.FlowFinalEditPart;
import kr.co.n3soft.n3com.edit.GroupEditPart;
import kr.co.n3soft.n3com.edit.HForkJoinEditPart;
import kr.co.n3soft.n3com.edit.HistoryEditPart;
import kr.co.n3soft.n3com.edit.InitialEditPart;
import kr.co.n3soft.n3com.edit.InterfaceEditPart;
import kr.co.n3soft.n3com.edit.LifeLineEditPart;
import kr.co.n3soft.n3com.edit.LineTextEditPart;
import kr.co.n3soft.n3com.edit.NodeEditPart;
import kr.co.n3soft.n3com.edit.PartEditPart;
import kr.co.n3soft.n3com.edit.PortEditPart;
import kr.co.n3soft.n3com.edit.ReceiveEditPart;
import kr.co.n3soft.n3com.edit.RequirementEditPart;
import kr.co.n3soft.n3com.edit.SendEditPart;
import kr.co.n3soft.n3com.edit.StateEditPart;
import kr.co.n3soft.n3com.edit.StateLifelineEditPart;
import kr.co.n3soft.n3com.edit.StrcuturedStateEditPart;
import kr.co.n3soft.n3com.edit.SynchEditPart;
import kr.co.n3soft.n3com.edit.TerminateEditPart;
import kr.co.n3soft.n3com.edit.UMLDiagramEditPart;
import kr.co.n3soft.n3com.edit.UMLEditPart;
import kr.co.n3soft.n3com.edit.UseCaseEditPart;
import kr.co.n3soft.n3com.model.comm.UMLModel;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.SelectionManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;

public class N3SelectionManager extends SelectionManager{
	public void appendSelection(EditPart editpart) {
		System.out.println("editpart==>"+editpart);
		
		super.appendSelection(editpart);
		if(editpart instanceof UMLEditPart)
			ProjectManager.getInstance().setSelectPropertyUMLElementModel((UMLModel)editpart.getModel());
	}
	
	public void setSelection(ISelection newSelection) {
//		V1.02 WJH E 080813 S 객체 다중 선택 시 기준이 되는 객체가 항상 있도록 수정
		StructuredSelection list = (StructuredSelection)newSelection;
		if(list.size()>0){
			StructuredSelection list2 = null;
			java.util.List tempList = new java.util.ArrayList();
			UMLEditPart temp = null;
			int index = list.toList().size();
			UMLEditPart select = (UMLEditPart)list.toList().get(index-1);
			
//			while(select instanceof LineTextEditPart){
//				int k=2;
//				select = (UMLEditPart)list.toList().get(index-k);
//				k++;
//				if(k==index-1)
//					break;
//			}
			EditPart parent = select.getParent();
			if(parent instanceof UMLDiagramEditPart){
				System.out.println("success : Last element is ModelEditPart");
				PortEditPart pe = null;
				for(int i=0; i<index; i++){
					UMLEditPart editPart = (UMLEditPart)list.toList().get(i);
					parent = editPart.getParent();
					System.out.println(editPart.getParent());
//					V1.02 WJH E 080901 S 라인 텍스트는 선택 안되도록 수정
					
					if(editPart instanceof PortEditPart){
						pe = (PortEditPart)editPart;
						continue;
					}
//					V1.02 WJH E 080901 E 라인 텍스트는 선택 안되도록 수정s
					if(parent instanceof UMLDiagramEditPart){
		//				UMLDiagramModel model = (UMLDiagramModel)editPart.getModel();
		//				Point location = model.getLocation();				
						if(temp == null)
							temp = editPart;
						else
							tempList.add(editPart);
					}
					else{
						tempList.add(editPart);
					}
				}
				if(temp != null)
					tempList.add(temp);
				if(tempList.size()>0){
					list2 = new StructuredSelection(tempList);
					newSelection = list2;
				}
//				else{
//					if(pe!=null){
//						tempList.add(pe);
//						list2 = new StructuredSelection(tempList);
//						newSelection = list2;
//					}
//				}
		
			}
			else{
				for(int i=0; i<index; i++){
					UMLEditPart editPart = (UMLEditPart)list.toList().get(i);
					parent = editPart.getParent();
					System.out.println(editPart.getParent());
//					V1.02 WJH E 080901 S 라인 텍스트는 선택 안되도록 수정
					if(editPart instanceof LineTextEditPart)
						continue;
					if(editPart instanceof PortEditPart)
						continue;
//					V1.02 WJH E 080901 E 라인 텍스트는 선택 안되도록 수정s
					if(parent instanceof UMLDiagramEditPart){
		//				UMLDiagramModel model = (UMLDiagramModel)editPart.getModel();
		//				Point location = model.getLocation();				
						if(temp == null)
							temp = editPart;
						else
							tempList.add(editPart);
					}
					else{
						tempList.add(editPart);
					}
				}
				if(temp != null)
					tempList.add(temp);
				if(tempList.size()>0){
					list2 = new StructuredSelection(tempList);
					newSelection = list2;
				}
			}
		}
//		V1.02 WJH E 080813 E 객체 다중 선택 시 기준이 되는 객체가 항상 있도록 수정
//		System.out.println("editpart==>"+newSelection);
//		StructuredSelection list =  (StructuredSelection)newSelection;
//		java.util.List alist = new java.util.ArrayList();
//		java.util.List blist = new java.util.ArrayList();
//		alist = list.toList();
//	
//		for(int i=0;i<alist.size();i++){
//			UMLEditPart uMLEditPart = (UMLEditPart)alist.get(i);
//			int ok = ProjectManager.getInstance().getCopyModelType((UMLModel)uMLEditPart.getModel());
//			if(ok>0){
//				blist.add(uMLEditPart);
//			}
//			
//		}
//		StructuredSelection bStructuredSelection = new StructuredSelection(blist.toArray());
		
		super.setSelection(newSelection);
	}
	
	public void setSelection(EditPart editpart){
		UMLModel model = null;
		if(editpart != null){
			Object obj = editpart.getModel();
			if(obj instanceof UMLModel){
				model = (UMLModel)obj;
			}
			


		
			
		}
		if(model != null)
			ProjectManager.getInstance().setSelectPropertyUMLElementModel(model);
	}
}
