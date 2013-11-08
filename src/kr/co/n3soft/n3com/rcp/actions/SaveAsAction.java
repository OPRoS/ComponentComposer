package kr.co.n3soft.n3com.rcp.actions;

import java.io.File;

import kr.co.n3soft.n3com.lang.N3Messages;
import kr.co.n3soft.n3com.parser.LinkModel;
import kr.co.n3soft.n3com.parser.TeamProjectManager;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchWindow;

public class SaveAsAction extends Action {
	//PKY 08062601 S 단축키 정의

	IWorkbenchWindow workbenchWindow;
	public static   int ID=0;
	IEditorInput input = null;
	public SaveAsAction(IWorkbenchWindow window) {
		setId("SaveAsAction");
		this.setText(N3Messages.POPUP_SAVE_AS);
		workbenchWindow = window;
//		setImageDescriptor(uuu.Activator.getImageDescriptor("/icons/save.gif"));
		this.setAccelerator(SWT.CTRL|SWT.SHIFT|'s'); //PKY 08061801 S 단축키 정의
	}
	
	public SaveAsAction() {
		setId("SaveAsAction");
		this.setText(N3Messages.POPUP_SAVE_AS);
//		workbenchWindow = window;
		setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getImage(302)));//PKY 08070101 S 팝업 메뉴 이미지 삽입
	}

	public void run() {
		try{
//			ProgressMonitorDialog pd = new ProgressMonitorDialog(workbenchWindow.getShell());
//			pd.run(true, true, (IRunnableWithProgress)ProjectManager.getInstance().getModelBrowser());

			FileDialog fsd = new FileDialog(ProjectManager.getInstance().window.getShell(),SWT.SAVE);
			fsd.setFilterExtensions(new String[] {"*.nmdl","*.*"});
			fsd.setText("Select Project Files...");
			fsd.open();
//			LoadProgressRun lr =	new LoadProgressRun(true);
			String fileName = fsd.getFileName();
			String path = fsd.getFilterPath();
			
//			V1.02 WJH E 080822 S 세이브 할때 이미 있는 파일을 선택하면 저장 여부를 묻도록 수정
			String fullPath = fsd.getFilterPath()+File.separator+fsd.getFileName();
			File tempFile = new File(fullPath);
			if(tempFile.exists()){
				MessageBox dialog = new MessageBox(ProjectManager.getInstance().window.getShell(),SWT.ICON_QUESTION|SWT.YES|SWT.NO);
				dialog.setText("Message");
				dialog.setMessage(N3Messages.DIALOG_SAVE_MESSAGE);
				int i=dialog.open();
				switch(i) {
				case IDialogConstants.FINISH_ID:
					System.out.println("Scrip Wizard Finish!!");
					break;
				case SWT.NO:
					return;
				case SWT.CANCEL:
					break;
				}
			}
//			V1.02 WJH E 080822 E 세이브 할때 이미 있는 파일을 선택하면 저장 여부를 묻도록 수정
			
			//PKY 08062601 S 저장할때 경로지정이 제대로 안될경우 에러발생 처리
			//PKY 08072401 S 콘솔 창 닫은상태에서 저장 불러오기 안되는 문제 수정
			if(!fileName.trim().equals("")){
				ProjectManager.getInstance().setCurrentProjectName(fileName);
				ProjectManager.getInstance().setCurrentProjectPath(path);				
				ProjectManager.getInstance().getModelBrowser().writeModel(ProjectManager.getInstance().getCurrentProject());
				String title = ProjectManager.getInstance().getCurrentProjectName().replaceAll(".nmdl", "");
				ProjectManager.getInstance().getConfigurer().setTitle(title+" - N3COM Constructor V3.0");
				ProjectManager.getInstance().addOpenProject(ProjectManager.getInstance().getCurrentProject());
				ProjectManager.getInstance().getConsole().addMessage("Save As"+ProjectManager.getInstance().getCurrentProject());
				ProjectManager.getInstance().getConsole().addMessage("Save As complete "+ProjectManager.getInstance().getCurrentProject());
			}
			//PKY 08072401 E 콘솔 창 닫은상태에서 저장 불러오기 안되는 문제 수정
			//20080811IJS
			java.util.ArrayList list = TeamProjectManager.getInstance().getLinks();
			TeamProjectManager.getInstance().setLinkSave(true);
			for(int i=0;i<list.size();i++){
				LinkModel lm = (LinkModel)list.get(i);
				int k = lm.getUt().getIsLinkType();
				if(k==1){
					ProjectManager.getInstance().getModelBrowser().writeLinkModel(lm.getLinkPath(),lm.getUt());
				}
			}
			TeamProjectManager.getInstance().setLinkSave(false);

		}
		catch(Exception e){
			e.printStackTrace();
		}

//		ProjectManager.getInstance().getModelBrowser().writeModel();

	}

//	private String openFileDialog() {
//	FileDialog dialog = new FileDialog(workbenchWindow.getShell(), SWT.OPEN);
//	dialog.setText("shapes");
//	//dialog.setFilterExtensions(new String[] { ".shapes" });
//	return dialog.open();
//	}
}
