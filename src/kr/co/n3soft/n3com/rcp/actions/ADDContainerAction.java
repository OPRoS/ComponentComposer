package kr.co.n3soft.n3com.rcp.actions;

import kr.co.n3soft.n3com.edit.UMLEditPart;
import kr.co.n3soft.n3com.lang.N3Messages;
import kr.co.n3soft.n3com.model.activity.FinalActivityModel;
import kr.co.n3soft.n3com.model.activity.HPartitionModel;
import kr.co.n3soft.n3com.model.activity.SubPartitonModel;
import kr.co.n3soft.n3com.model.comm.ClassifierModel;
import kr.co.n3soft.n3com.model.comm.NodeContainerModel;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.usecase.FinalBoundryModel;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorPart;

public class ADDContainerAction extends SelectionAction {
	public ADDContainerAction(IEditorPart editor) {
		super(editor);
		this.setId("ADD ADDContainerAction");
		this.setText(N3Messages.POPUP_ADD_CONTAINER);
	}

	protected boolean calculateEnabled() {
		// TODO Workaround for Bug 82622/39369.  Should be removed when 39369 is fixed.
		//PKY 08070101 S 컨테이너 메뉴 비 활성화
//		Object obj1 = this.getSelection();
//		StructuredSelection list = (StructuredSelection)this.getSelection();
//		if (list != null && list.size() == 1) {
//			Object obj = list.getFirstElement();
//			if (obj instanceof UMLEditPart) {
//				UMLEditPart u = (UMLEditPart)obj;
//				UMLModel um = (UMLModel)u.getModel();
//
//
//				if (um.isPartition()==false&&um instanceof ClassifierModel
//						&& !(um instanceof FinalBoundryModel)&&!(um instanceof HPartitionModel)) { //PKY 08060201 S Swinlane=어트리뷰트,오퍼레이션,컨테이너 들어가지 않도록 수정
//					//20080325 PKY S 컨테이너 1회만 추가 되도록 
//					ClassifierModel cm = (ClassifierModel)um;
////					if(cm.isContainer())
////					return false;
//					//20080325 PKY E컨테이너 1회만 추가 되도록 
//					//2008042106PKY S 컨테이너 추가 삭제할 수 있도록 수정
//					//PKY 08051401 S 컨테이너 있는 모델 뷰 지우기 한 후 다시 생성할 경우 컨테이너 비 정상적으로 생기는 문제 
//					setChecked(false);
//					String s=(String)um.uMLDataModel.getElementProperty("Container");
//					if(s!=null)
//						if(s.equals("false")){
//							cm.setContainer(false);
//							setChecked(false);
//						}else{
//							cm.setContainer(true);
//							setChecked(true);
//						}
//					//PKY 08051401 E 컨테이너 있는 모델 뷰 지우기 한 후 다시 생성할 경우 컨테이너 비 정상적으로 생기는 문제 
//					//2008042106PKY E 컨테이너 추가 삭제할 수 있도록 수정
//					return true;
//				}
//				else {
//					return false;
//				}
//			}
//		}
		//		return false;
		//PKY 08070101 E 컨테이너 메뉴 비 활성화
		return false;
	}

	public void run() {
		//		this.getCommandStack().execute(command)
		Object obj1 = this.getSelection();
		StructuredSelection list = (StructuredSelection)this.getSelection();
		if (list != null && list.size() == 1) {
			Object obj = list.getFirstElement();
			if (obj instanceof UMLEditPart) {

				UMLEditPart u = (UMLEditPart)obj;
				ClassifierModel um = (ClassifierModel)u.getModel();


//				um.getBoundaryModel()

				if(um.isContainer()==false){//2008042106PKY S 컨테이너 추가 삭제할 수 있도록 수정
					if(um.getSaveContainer()!=null){
						um.addContainer(um.getSaveContainer());
					}else{
						um.addContainer(new NodeContainerModel());
					}
					um.setSize(new Dimension(um.getSize().width + 150, um.getSize().height + 150));
					um.setContainer(true);//2008040305PKY S
					um.uMLDataModel.setElementProperty("Container", "true");//PKY 08051401 S 컨테이너 있는 모델 뷰 지우기 한 후 다시 생성할 경우 컨테이너 비 정상적으로 생기는 문제 
					setChecked(true);
				}else{
					um.setSize(new Dimension(um.getSize().width - um.getBoundaryModel().getSize().width,
							um.getSize().height - um.getBoundaryModel().getSize().height));
					um.setSaveContainer(um.getBoundaryModel());
					um.removeContainer(new NodeContainerModel());
					um.setContainer(false);
					um.uMLDataModel.setElementProperty("Container", "false");//PKY 08051401 S 컨테이너 있는 모델 뷰 지우기 한 후 다시 생성할 경우 컨테이너 비 정상적으로 생기는 문제 
					setChecked(false);

				}
//				2008042106PKY E 컨테이너 추가 삭제할 수 있도록 수정
			}
		}
	}

}
