package kr.co.n3soft.n3com.rcp.actions;

import kr.co.n3soft.n3com.edit.UMLEditPart;
import kr.co.n3soft.n3com.lang.N3Messages;
import kr.co.n3soft.n3com.model.activity.FinalActiionModel;
import kr.co.n3soft.n3com.model.activity.HPartitionModel;
import kr.co.n3soft.n3com.model.comm.ClassModel;
import kr.co.n3soft.n3com.model.comm.ClassifierModel;
import kr.co.n3soft.n3com.model.comm.ClassifierModelTextAttach;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.umlclass.AttributeEditableTableItem;
import kr.co.n3soft.n3com.model.usecase.FinalBoundryModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorPart;

public class ADDAttribute extends SelectionAction {
	ClassModel classModel = null;
	java.util.ArrayList attributes = null;
	public ADDAttribute(IEditorPart editor) {
		super(editor);
		this.setId("ADDAttribute");
		this.setText(N3Messages.POPUP_ADD_ATTRIBUTE);
		setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getImage(100)));//PKY 08070101 S �˾� �޴� �̹��� ����
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
						ClassifierModel classifierModel = null;
						classifierModel = (ClassifierModel)um;
						classModel = classifierModel.getClassModel();
						attributes = (java.util.ArrayList) classModel.getAttributes().clone();
					}
					else{
						ClassifierModelTextAttach classifierModel = null;
						classifierModel = (ClassifierModelTextAttach)um;
						classModel = classifierModel.getClassModel();
						attributes = (java.util.ArrayList) classModel.getAttributes().clone();
					}

					return true;
				}
			}
		}
		return false;
	}

	public void run() {
		//		this.getCommandStack().execute(command)
		//PKY 08060201 S ���� �ҷ����� �Ұ�� ��Ʈ����Ʈ,���۷��̼� ���� �̸� �ѹ������� �ʱ�ȭ �Ǵ¹���
		if(classModel!=null && attributes!=null){
			AttributeEditableTableItem newItem = null;
			if(classModel.getAttributeCount()==0&&classModel.getAttributes().size()>0){
				for(int i=0; i<classModel.getAttributes().size();i++){
					if(((AttributeEditableTableItem)classModel.getAttributes().get(i)).getName().lastIndexOf("attribute")>-1){
						AttributeEditableTableItem att=(AttributeEditableTableItem)classModel.getAttributes().get(i);
						String name=att.getName().substring(((AttributeEditableTableItem)classModel.getAttributes().get(i)).getName().lastIndexOf("e")+1,
								((AttributeEditableTableItem)classModel.getAttributes().get(i)).getName().length());
						char [] charString =name.toCharArray();
						boolean ischar=false;
						for(int j=0; j<charString.length;j++){                              
							if(!Character.isDigit(charString[j])) {
								ischar=true;
							}
						}
						if(ischar==false){
							int s=Integer.parseInt(att.getName().substring(((AttributeEditableTableItem)classModel.getAttributes().get(i)).getName().lastIndexOf("e")+1,
									((AttributeEditableTableItem)classModel.getAttributes().get(i)).getName().length()));

							if(classModel.getAttributeCount()<=s){
								classModel.setAttributeCount(s+1);
							}
						}

					}
				}
				newItem = new
				AttributeEditableTableItem(new Integer(0), "attribute"+classModel.addAttributeCount(), "String", "");
			}
			else{
				newItem = new
				AttributeEditableTableItem(new Integer(0), "attribute"+classModel.addAttributeCount(), "String", "");
			}
			attributes.add(newItem);
			classModel.setAttributes(attributes);

			if(classModel.isMode()) //2008043001  ���յ��� ���� �� ���� 
				ProjectManager.getInstance().autoSize(classModel);

		}
		//PKY 08060201 E ���� �ҷ����� �Ұ�� ��Ʈ����Ʈ,���۷��̼� ���� �̸� �ѹ������� �ʱ�ȭ �Ǵ¹���
	}
}
