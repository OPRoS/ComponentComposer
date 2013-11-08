package kr.co.n3soft.n3com.rcp.actions;

import kr.co.n3soft.n3com.lang.N3Messages;
import kr.co.n3soft.n3com.project.dialog.SeqMessageDialog;

import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.IEditorPart;

public class OpenSeqMessageDialogAction extends SelectionAction {
    public OpenSeqMessageDialogAction(IEditorPart editor) {
        super(editor);
        this.setId("OpenSeqMessageDialogAction");
        this.setText(N3Messages.POPUP_EDIT_MESSAGE);
//        this.setText("OpenSeqMessageDialogAction");
    }

    protected boolean calculateEnabled() {
    	//PKY 08072201 S ���������� ������ �޴��� �޴��� �ƴ� ���� ������Ƽ���� ���̾�α� ������ �� �ֵ��� ����
        // TODO Workaround for Bug 82622/39369.  Should be removed when 39369 is fixed.
//    	List list = ProjectManager.getInstance().getSelectNodes();
//        if (list != null && list.size() == 1) {
//            Object obj = list.get(0);
//            if (obj instanceof LineEditPart) {
//            	LineEditPart u = (LineEditPart)obj;
//                LineModel um = (LineModel)u.getModel();
//                if (um instanceof MessageModel) {
//                    return true;
//                }
//                else {
//                    return false;
//                }
//            }
//        }
    	
        //		return false;
    	//PKY 08072201 E ���������� ������ �޴��� �޴��� �ƴ� ���� ������Ƽ���� ���̾�α� ������ �� �ֵ��� ����

        return false;
    }

    public void run() {
    	SeqMessageDialog dialog = new SeqMessageDialog(null);
//        Object value = getValue();
        dialog.open();
        //		this.getCommandStack().execute(command)


    }
}