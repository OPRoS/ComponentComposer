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
    	//PKY 08072201 S 시퀀스라인 오른쪽 메뉴에 메뉴가 아닌 왼쪽 프로퍼티에서 다이얼로그 오픈할 수 있도록 수정
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
    	//PKY 08072201 E 시퀀스라인 오른쪽 메뉴에 메뉴가 아닌 왼쪽 프로퍼티에서 다이얼로그 오픈할 수 있도록 수정

        return false;
    }

    public void run() {
    	SeqMessageDialog dialog = new SeqMessageDialog(null);
//        Object value = getValue();
        dialog.open();
        //		this.getCommandStack().execute(command)


    }
}