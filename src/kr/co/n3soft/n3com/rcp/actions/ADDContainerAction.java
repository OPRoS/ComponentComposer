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
		//PKY 08070101 S �����̳� �޴� �� Ȱ��ȭ
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
//						&& !(um instanceof FinalBoundryModel)&&!(um instanceof HPartitionModel)) { //PKY 08060201 S Swinlane=��Ʈ����Ʈ,���۷��̼�,�����̳� ���� �ʵ��� ����
//					//20080325 PKY S �����̳� 1ȸ�� �߰� �ǵ��� 
//					ClassifierModel cm = (ClassifierModel)um;
////					if(cm.isContainer())
////					return false;
//					//20080325 PKY E�����̳� 1ȸ�� �߰� �ǵ��� 
//					//2008042106PKY S �����̳� �߰� ������ �� �ֵ��� ����
//					//PKY 08051401 S �����̳� �ִ� �� �� ����� �� �� �ٽ� ������ ��� �����̳� �� ���������� ����� ���� 
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
//					//PKY 08051401 E �����̳� �ִ� �� �� ����� �� �� �ٽ� ������ ��� �����̳� �� ���������� ����� ���� 
//					//2008042106PKY E �����̳� �߰� ������ �� �ֵ��� ����
//					return true;
//				}
//				else {
//					return false;
//				}
//			}
//		}
		//		return false;
		//PKY 08070101 E �����̳� �޴� �� Ȱ��ȭ
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

				if(um.isContainer()==false){//2008042106PKY S �����̳� �߰� ������ �� �ֵ��� ����
					if(um.getSaveContainer()!=null){
						um.addContainer(um.getSaveContainer());
					}else{
						um.addContainer(new NodeContainerModel());
					}
					um.setSize(new Dimension(um.getSize().width + 150, um.getSize().height + 150));
					um.setContainer(true);//2008040305PKY S
					um.uMLDataModel.setElementProperty("Container", "true");//PKY 08051401 S �����̳� �ִ� �� �� ����� �� �� �ٽ� ������ ��� �����̳� �� ���������� ����� ���� 
					setChecked(true);
				}else{
					um.setSize(new Dimension(um.getSize().width - um.getBoundaryModel().getSize().width,
							um.getSize().height - um.getBoundaryModel().getSize().height));
					um.setSaveContainer(um.getBoundaryModel());
					um.removeContainer(new NodeContainerModel());
					um.setContainer(false);
					um.uMLDataModel.setElementProperty("Container", "false");//PKY 08051401 S �����̳� �ִ� �� �� ����� �� �� �ٽ� ������ ��� �����̳� �� ���������� ����� ���� 
					setChecked(false);

				}
//				2008042106PKY E �����̳� �߰� ������ �� �ֵ��� ����
			}
		}
	}

}
