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
		setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getImage(301)));//PKY 08070101 S �˾� �޴� �̹��� ����
		this.setAccelerator(SWT.CTRL |'s'); //PKY 08061801 S ����Ű ����
	}
	
	public SaveAction() {
		setId("SaveAction");
		this.setText(N3Messages.POPUP_SAVE);
//		workbenchWindow = window;
		setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getImage(301)));//PKY 08070101 S �˾� �޴� �̹��� ����
	}

	//111206 SDM - ���������� ����� Ȯ���ڰ� ����� ǥ�� �ȵǴ� ���� ���� ->getFileName()�� �� �ڿ� .nmdl�� ����
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
				
//				V1.02 WJH E 080822 S ���̺� �Ҷ� �̹� �ִ� ������ �����ϸ� ���� ���θ� ������ ���� 
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
//				V1.02 WJH E 080822 E ���̺� �Ҷ� �̹� �ִ� ������ �����ϸ� ���� ���θ� ������ ����
				
				//PKY 08062601 S �����Ҷ� ��������� ����� �ȵɰ�� �����߻� ó��
				if(!strFileName.trim().equals("")){
					String fileName = strFileName;
					String path = fsd.getFilterPath();

					ProjectManager.getInstance().setCurrentProjectName(fileName);
					ProjectManager.getInstance().setCurrentProjectPath(path+File.separator);
				}
				//PKY 08062601 E �����Ҷ� ��������� ����� �ȵɰ�� �����߻� ó��
//			}
			}
			//PKY 08072401 S �ܼ� â �������¿��� ���� �ҷ����� �ȵǴ� ���� ����
//			if(!ProjectManager.getInstance().getCurrentProjectName().trim().equals("")){
			ProjectManager.getInstance().getModelBrowser().writeModel(ProjectManager.getInstance().getCurrentProject());				
			String title = ProjectManager.getInstance().getCurrentProjectName().replaceAll(".nmdl", "");
//			ProjectManager.getInstance().getConfigurer().setTitle(title+" - N3COM Constructor V3.0");
//			ProjectManager.getInstance().addOpenProject(ProjectManager.getInstance().getCurrentProject());
			if(ProjectManager.getInstance().getConsole()!=null){//KHG 2010.05.19 �ܼ�â�� ���� ��� ���� �ȵǴ� ���� ����
				ProjectManager.getInstance().getConsole().addMessage("Save complete "+ProjectManager.getInstance().getCurrentProject());
				ProjectManager.getInstance().getConsole().addMessage("Save "+ProjectManager.getInstance().getCurrentProject());
			}
//				CompAssemManager.getInstance().saveLib(null);
//			}
			//PKY 08072401 E �ܼ� â �������¿��� ���� �ҷ����� �ȵǴ� ���� ����
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
