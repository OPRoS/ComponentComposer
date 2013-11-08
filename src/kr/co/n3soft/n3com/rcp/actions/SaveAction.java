package kr.co.n3soft.n3com.rcp.actions;

import java.io.File;

import kr.co.n3soft.n3com.lang.N3Messages;
import kr.co.n3soft.n3com.parser.LinkModel;
import kr.co.n3soft.n3com.parser.TeamProjectManager;
import kr.co.n3soft.n3com.projectmanager.CompAssemManager;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchWindow;

public class SaveAction extends Action {

	IWorkbenchWindow workbenchWindow;
	public static   int ID=0;
	IEditorInput input = null;
	public SaveAction(IWorkbenchWindow window) {
		setId("SaveAction");
		this.setText(N3Messages.POPUP_SAVE);
		workbenchWindow = window;
		setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getImage(301)));//PKY 08070101 S 팝업 메뉴 이미지 삽입
		this.setAccelerator(SWT.CTRL |'s'); //PKY 08061801 S 단축키 정의
	}
	
	public SaveAction() {
		setId("SaveAction");
		this.setText(N3Messages.POPUP_SAVE);
//		workbenchWindow = window;
		setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getImage(301)));//PKY 08070101 S 팝업 메뉴 이미지 삽입
	}

	//111206 SDM - 리눅스에서 저장시 확장자가 제대로 표시 안되는 현상 수정 ->getFileName()의 값 뒤에 .nmdl를 넣음
	public void run() {
		try{
			//			ProgressMonitorDialog pd = new ProgressMonitorDialog(workbenchWindow.getShell());
			//			pd.run(true, true, (IRunnableWithProgress)ProjectManager.getInstance().getModelBrowser());
//			if(ProjectManager.getInstance().getCurrentProjectName()==null || ProjectManager.getInstance().getCurrentProjectName().trim().equals("")){
			String prjName = ProjectManager.getInstance().getCurrentProjectName();
			if("".equals(prjName)){	
			FileDialog fsd = new FileDialog(ProjectManager.getInstance().window.getShell(),SWT.SAVE);
				fsd.setFilterExtensions(new String[] {"*.nmdl","*.*"});
				fsd.setText("Save Select Project Files...");
				fsd.setFileName(ProjectManager.getInstance().getCurrentProjectName());
				String ir = fsd.open();
				if(ir==null)
					return;
				//				LoadProgressRun lr =	new LoadProgressRun(true);
				
				String strFileName = fsd.getFileName();
				if(strFileName.indexOf(".nmdl") < 0){
					strFileName = strFileName + ".nmdl";
				}
				
//				V1.02 WJH E 080822 S 세이브 할때 이미 있는 파일을 선택하면 저장 여부를 묻도록 수정 
				String fullPath = fsd.getFilterPath()+File.separator+strFileName;
				File tempFile = new File(fullPath);
				if(!(strFileName.trim().equals("")||fsd.getFilterPath().trim().equals("")))//PKY 08090908 S
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
						return;
					}
				}
//				V1.02 WJH E 080822 E 세이브 할때 이미 있는 파일을 선택하면 저장 여부를 묻도록 수정
				
				//PKY 08062601 S 저장할때 경로지정이 제대로 안될경우 에러발생 처리
				if(!strFileName.trim().equals("")){
					String fileName = strFileName;
					String path = fsd.getFilterPath();

					ProjectManager.getInstance().setCurrentProjectName(fileName);
					ProjectManager.getInstance().setCurrentProjectPath(path+File.separator);
				}
				//PKY 08062601 E 저장할때 경로지정이 제대로 안될경우 에러발생 처리
//			}
			}
			//PKY 08072401 S 콘솔 창 닫은상태에서 저장 불러오기 안되는 문제 수정
//			if(!ProjectManager.getInstance().getCurrentProjectName().trim().equals("")){
			ProjectManager.getInstance().getModelBrowser().writeModel(ProjectManager.getInstance().getCurrentProject());				
			String title = ProjectManager.getInstance().getCurrentProjectName().replaceAll(".nmdl", "");
//			ProjectManager.getInstance().getConfigurer().setTitle(title+" - N3COM Constructor V3.0");
//			ProjectManager.getInstance().addOpenProject(ProjectManager.getInstance().getCurrentProject());
			if(ProjectManager.getInstance().getConsole()!=null){//KHG 2010.05.19 콘솔창이 없을 경우 저장 안되는 에러 수정
				ProjectManager.getInstance().getConsole().addMessage("Save complete "+ProjectManager.getInstance().getCurrentProject());
				ProjectManager.getInstance().getConsole().addMessage("Save "+ProjectManager.getInstance().getCurrentProject());
			}
//				CompAssemManager.getInstance().saveLib(null);
//			}
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
			if(ProjectManager.getInstance().getCommandStack()!=null)
			ProjectManager.getInstance().getCommandStack().markSaveLocation();
			if(ProjectManager.getInstance()!=null && ProjectManager.getInstance().getModelBrowser()!=null){
				ProjectManager.getInstance().getModelBrowser().setChange(false);
			}

		}
		catch(Exception e){
			e.printStackTrace();
		}
		

		//	    ProjectManager.getInstance().getModelBrowser().writeModel();

	}

//	private String openFileDialog() {
//	FileDialog dialog = new FileDialog(workbenchWindow.getShell(), SWT.OPEN);
//	dialog.setText("shapes");
//	//dialog.setFilterExtensions(new String[] { ".shapes" });
//	return dialog.open();
//	}
}
