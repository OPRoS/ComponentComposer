package kr.co.n3soft.n3com.rcp.actions;

import kr.co.n3soft.n3com.edit.StateEditPart;
import kr.co.n3soft.n3com.edit.UMLEditPart;
import kr.co.n3soft.n3com.lang.N3Messages;
import kr.co.n3soft.n3com.model.activity.FinalActiionModel;
import kr.co.n3soft.n3com.model.activity.HPartitionModel;
import kr.co.n3soft.n3com.model.comm.ClassModel;
import kr.co.n3soft.n3com.model.comm.ClassifierModel;
import kr.co.n3soft.n3com.model.comm.ClassifierModelTextAttach;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.umlclass.OperationEditableTableItem;
import kr.co.n3soft.n3com.model.usecase.FinalBoundryModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorPart;

public class ADDoperation extends SelectionAction {
	ClassModel classModel = null;
	java.util.ArrayList opeations = null;
	public boolean isState = false;
	public ADDoperation(IEditorPart editor) {
		super(editor);
		this.setId("ADDoperation");
		this.setText(N3Messages.POPUP_ADD_OPERATION);
		setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getImage(101)));//PKY 08070101 S �˾� �޴� �̹��� ����
	}

	protected boolean calculateEnabled() {
		// TODO Workaround for Bug 82622/39369.  Should be removed when 39369 is fixed.
//		Object obj1 = this.getSelection();
		java.util.List list = ProjectManager.getInstance().getSelectNodes();
		if (list != null && list.size() == 1) {
			Object obj = list.get(0);
			if (obj instanceof UMLEditPart) {
				UMLEditPart u = (UMLEditPart)obj;
				UMLModel um = (UMLModel)u.getModel();
				//PKY 08072401 S RequirementModel,��Ʈ����Ʈ ���۷��̼� �����ʵ���
				
				//PKY 08072401 E RequirementModel,��Ʈ����Ʈ ���۷��̼� �����ʵ���
				//PKY 08070901 S group ���۷��̼�/��Ʈ����Ʈ �߰������ѹ��� ����
				
				//PKY 08070901 E group ���۷��̼�/��Ʈ����Ʈ �߰������ѹ��� ����
				//20080811IJS ����
				//PKY 08082201 S �� ������Ʈ �б������϶� �����Ұ����ϵ��� �۾�
//				if(um.isReadOnlyModel)
//					return false;
//
//				if(!um.isExistModel)
//					return false;
				//PKY 08082201 e �� ������Ʈ �б������϶� �����Ұ����ϵ��� �۾�

				//20080811IJS ��
				//PKY 08091006 S
				if(um instanceof FinalActiionModel){
					FinalActiionModel action = (FinalActiionModel)um;
					if(action.getCallBeavior()==null){
						return false;
					}
				}
				//PKY 08091006 E
				if ((um instanceof ClassifierModel || um instanceof ClassifierModelTextAttach)
						&& !(um instanceof FinalBoundryModel)&&!(um instanceof HPartitionModel)) {//PKY 08060201 S Swinlane=��Ʈ����Ʈ,���۷��̼�,�����̳� ���� �ʵ��� ����
					if(um instanceof ClassifierModel){
						ClassifierModel classifierModel = (ClassifierModel)um;
						classModel = classifierModel.getClassModel();
						
//							this.checkClassType();
							isState = false;
						
						opeations = (java.util.ArrayList) classModel.getOperations().clone();
						return true;
					}
					else{
						ClassifierModelTextAttach classifierModel = (ClassifierModelTextAttach)um;
						classModel = classifierModel.getClassModel();

						opeations = (java.util.ArrayList) classModel.getOperations().clone();
						return true;
					}
				}
			}
		}
		return false;
	}

	public void run() {
		//		this.getCommandStack().execute(command)
		//PKY 08060201 S ���� �ҷ����� �Ұ�� ��Ʈ����Ʈ,���۷��̼� ���� �̸� �ѹ������� �ʱ�ȭ �Ǵ¹���
		if(classModel!=null && opeations!=null){
			OperationEditableTableItem newItem = null;
			if(classModel.getOperationCount()==0&&classModel.getOperations().size()>0){
				for(int i=0; i<classModel.getOperations().size();i++){
					if(((OperationEditableTableItem)classModel.getOperations().get(i)).getName().lastIndexOf("operation")>-1){
						OperationEditableTableItem att=(OperationEditableTableItem)classModel.getOperations().get(i);
						String name=att.getName().substring(((OperationEditableTableItem)classModel.getOperations().get(i)).getName().lastIndexOf("n")+1,
								((OperationEditableTableItem)classModel.getOperations().get(i)).getName().length());
						char [] charString =name.toCharArray();
						boolean ischar=false;
						for(int j=0; j<charString.length;j++){                              
							if(!Character.isDigit(charString[j])) {
								ischar=true;
							}
						}
						if(ischar==false){
							int s=Integer.parseInt(att.getName().substring(((OperationEditableTableItem)classModel.getOperations().get(i)).getName().lastIndexOf("n")+1,
									((OperationEditableTableItem)classModel.getOperations().get(i)).getName().length()));

							if(classModel.getOperationCount()<=s){
								classModel.setOperationCount(s+1);
							}
						}
					}
				}
				//PKY 08071601 S State ���۷��̼� do�̿��� �ٸ� ���۷��̼� ����Ÿ�����ص� �ȵ��¹��� ����
				if(ProjectManager.getInstance().getSelectNodes().size()>0){
					if(ProjectManager.getInstance().getSelectNodes().get(0) instanceof StateEditPart){
						newItem = new
						OperationEditableTableItem(new Integer(0), "operation"+ classModel.addOperationCount(), "do", "");  
					}else{
						newItem = new
						OperationEditableTableItem(new Integer(0), "operation"+ classModel.addOperationCount(), "void", "");    		
					}
				}
				//PKY 08071601 E State ���۷��̼� do�̿��� �ٸ� ���۷��̼� ����Ÿ�����ص� �ȵ��¹��� ����


			}
			else{
				//PKY 08071601 S State ���۷��̼� do�̿��� �ٸ� ���۷��̼� ����Ÿ�����ص� �ȵ��¹��� ����
				if(ProjectManager.getInstance().getSelectNodes().size()>0){
					if(ProjectManager.getInstance().getSelectNodes().get(0) instanceof StateEditPart){
						newItem = new
						OperationEditableTableItem(new Integer(0), "operation"+ classModel.addOperationCount(), "do", "");  
					}else{
						newItem = new
						OperationEditableTableItem(new Integer(0), "operation"+ classModel.addOperationCount(), "void", "");    		
					}
				}
				//PKY 08071601 E State ���۷��̼� do�̿��� �ٸ� ���۷��̼� ����Ÿ�����ص� �ȵ��¹��� ����
			}
			if (isState) {
				newItem.setIsState(true);
			}
			else {
				newItem.setIsState(false);
			}
			opeations.add(newItem);
			classModel.setOperations(opeations);
			if(classModel.isMode()) //2008043001  ���յ��� ���� �� ���� 
				ProjectManager.getInstance().autoSize(classModel);

		}
		//PKY 08060201 E ���� �ҷ����� �Ұ�� ��Ʈ����Ʈ,���۷��̼� ���� �̸� �ѹ������� �ʱ�ȭ �Ǵ¹���

	}
}
