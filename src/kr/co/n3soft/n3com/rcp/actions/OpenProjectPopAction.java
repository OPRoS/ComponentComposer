package kr.co.n3soft.n3com.rcp.actions;

import java.io.File;

import kr.co.n3soft.n3com.lang.N3Messages;
import kr.co.n3soft.n3com.model.diagram.N3EditorDiagramModel;
import kr.co.n3soft.n3com.parser.ModelManager;
import kr.co.n3soft.n3com.parser.RefLinkModel;
import kr.co.n3soft.n3com.project.browser.RootTreeModel;
import kr.co.n3soft.n3com.project.dialog.ProjectSearchDialog;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.dialogs.ListSelectionDialog;

public class OpenProjectPopAction extends Action {

	IWorkbenchWindow workbenchWindow;
	public static   int ID=0;
	IEditorInput input = null;
	String fileName = "";
	String path ="";
	public OpenProjectPopAction(IWorkbenchWindow window) {
		setId("OpenProjectPopAction");
		this.setText(N3Messages.POPUP_FIND);
		workbenchWindow = window;
		setImageDescriptor(uuu.Activator.getImageDescriptor("/icons/newproject.gif"));
	}

	public void setInit(){
		String text = this.getText();
		File f = new File(text);
		if(f.canRead()){
			fileName = f.getName();
			path = f.getParent();
		}

	}

	public void run() {
		//PKY 08072401 S �ܼ� â �������¿��� ���� �ҷ����� �ȵǴ� ���� ����
		//PKY 08072401 S ������Ʈ �ҷ��� �ÿ� Ÿ ������ ���� ���� �޽����� �����ϵ��� ����
		try{
			if(ProjectManager.getInstance().getModelBrowser()!=null&&ProjectManager.getInstance().getModelBrowser().getRoot()!=null){
				RootTreeModel rootTreeModel=ProjectManager.getInstance().getModelBrowser().getRoot();
				if(rootTreeModel.getChildren().length>0){
					MessageBox dialog = new MessageBox(ProjectManager.getInstance().window.getShell(),SWT.ICON_QUESTION|SWT.YES|SWT.NO|SWT.CANCEL);
					dialog.setText("Message");
					dialog.setMessage(N3Messages.DIALOG_OPEN_PROJECT_MESSAGE);
					int i=dialog.open();

					switch(i) {
					case IDialogConstants.FINISH_ID:
						System.out.println("Scrip Wizard Finish!!");
						break;
					case SWT.CANCEL:
						return;
					
					case SWT.YES:
						try{
							//			ProgressMonitorDialog pd = new ProgressMonitorDialog(workbenchWindow.getShell());
							//			pd.run(true, true, (IRunnableWithProgress)ProjectManager.getInstance().getModelBrowser());
							if(ProjectManager.getInstance().getCurrentProjectName()==null || ProjectManager.getInstance().getCurrentProjectName().trim().equals("")){
								FileDialog fsd = new FileDialog(workbenchWindow.getShell(),SWT.SAVE);
								fsd.setFilterExtensions(new String[] {"*.nmdl","*.*"});
								fsd.setText("Select Project Files...");
								fsd.open();
								//				LoadProgressRun lr =	new LoadProgressRun(true);
								//PKY 08062601 S �����Ҷ� ��������� ����� �ȵɰ�� �����߻� ó��
								if(!fsd.getFileName().trim().equals("")){
									String fileName = fsd.getFileName();
									String path = fsd.getFilterPath();

									ProjectManager.getInstance().setCurrentProjectName(fileName);
									ProjectManager.getInstance().setCurrentProjectPath(path+File.separator);
								}
								//PKY 08062601 E �����Ҷ� ��������� ����� �ȵɰ�� �����߻� ó��
							}
							//PKY 08062601 S �����Ҷ� ��������� ����� �ȵɰ�� �����߻� ó��
							if(!ProjectManager.getInstance().getCurrentProjectName().trim().equals("")){
								ProjectManager.getInstance().getConsole().addMessage("Save "+ProjectManager.getInstance().getCurrentProject());
								ProjectManager.getInstance().getModelBrowser().writeModel(ProjectManager.getInstance().getCurrentProject());
								ProjectManager.getInstance().getConsole().addMessage("Save complete "+ProjectManager.getInstance().getCurrentProject());
								String title = ProjectManager.getInstance().getCurrentProjectName().replaceAll(".nmdl", "");
								ProjectManager.getInstance().getConfigurer().setTitle(title+" - N3COM Constructor V3.0");
								ProjectManager.getInstance().addOpenProject(ProjectManager.getInstance().getCurrentProject());
							}
							workbenchWindow.getActivePage().closeAllEditors(false);

							//PKY 08062601 E �����Ҷ� ��������� ����� �ȵɰ�� �����߻� ó��

						}
						catch(Exception e){
							e.printStackTrace();
						}

						//	    ProjectManager.getInstance().getModelBrowser().writeModel();



					}
				}
			}
			//PKY 08072401 E ������Ʈ �ҷ��� �ÿ� Ÿ ������ ���� ���� �޽����� �����ϵ��� ����

			ProjectManager.getInstance().init();
			workbenchWindow.getActivePage().closeAllEditors(false);

			ProjectManager.getInstance().setCurrentProjectName(fileName);
			ProjectManager.getInstance().setCurrentProjectPath(path);


//			lr.setNp((N3ModelParser)ModelManager.getInstance().getParser());
			ModelManager.getInstance().parse(new File(ProjectManager.getInstance().getCurrentProject()));

			ProjectManager.getInstance().addOpenProject(ProjectManager.getInstance().getCurrentProject());//PKY 08072401 S OpenList ����
			ProgressMonitorDialog pd = new ProgressMonitorDialog(workbenchWindow.getShell());
//			ProjectManager.getInstance().setLoad(true);
			pd.run(true, true, (IRunnableWithProgress)ModelManager.getInstance().getParser()); 
//			ModelManager.getInstance().setLoadProgressRun(lr);

			ProjectManager.getInstance().getModelBrowser().refresh(ProjectManager.getInstance().getModelBrowser().getRoot());
		}
		catch(Exception e){
			//PKY 08072401 S Open�� ������Ʈ ������ �������� ������� ���� �߻����� ����
			 MessageBox dialog = new MessageBox(ProjectManager.getInstance().window.getShell(),SWT.ICON_WARNING);
			 dialog.setText("Message");
			 dialog.setMessage(N3Messages.DIALOG_OPEN_PROJECT_NOT_FILE_EXCEPTION+getText());
			 dialog.open();
			
			//PKY 08072401 E Open�� ������Ʈ ������ �������� ������� ���� �߻����� ����
			 MessageBox dialog1 = new MessageBox(ProjectManager.getInstance().window.getShell(),SWT.ICON_QUESTION|SWT.YES|SWT.NO);
			 dialog1.setText("Message");
			 dialog1.setMessage(N3Messages.DIALOG_OPEN_PROJECT_LIST_DELETE);
			 int i = dialog1.open();
			 if(i==64){
				 ProjectManager.getInstance().removeOpenProject(getText());
			 }
			 ProjectManager.getInstance().getModelBrowser().refresh(ProjectManager.getInstance().getModelBrowser().getRoot());
			e.printStackTrace();
		}
		String title = ProjectManager.getInstance().getCurrentProjectName().replaceAll(".nmdl", "");
		if(ModelManager.getInstance().getRefStore().size()>0){
			for(int i=0;i<ModelManager.getInstance().getRefStore().size();i++){
				RefLinkModel rf = (RefLinkModel)ModelManager.getInstance().getRefStore().get(i);
				rf.link();
			}
		}
		//PKY 08062601 S ����ε� �ݺ��� ���̾�׷� ���¸���Ʈ�� ���̾�׷��� �ߺ����� �����¹���
		if(ModelManager.getInstance().getOpenDiagarams()!=null)
			if(ModelManager.getInstance().getOpenDiagarams().size()>0){
				ListSelectionDialog lsd = new ListSelectionDialog(workbenchWindow.getShell(), ModelManager.getInstance().getOpenDiagarams(), new ArrayContentProvider(), new LabelProvider(), "ListSelectionDialog Message");
				lsd.setInitialSelections( ModelManager.getInstance().getOpenDiagarams().toArray());
				lsd.setTitle("Open Diagrams");
				int j=lsd.open();
				if(j==0){
					Object[] objs = lsd.getResult();
					for(int i=0;i<objs.length;i++){
						Object obj = objs[i];
						if(obj instanceof N3EditorDiagramModel){
							ProjectManager.getInstance().openDiagram((N3EditorDiagramModel)obj);
						}
					}
				}else {
//					ModelManager.getInstance().getOpenDiagarams().clear();
				}
			}
		ProjectManager.getInstance().writeConfig();//PKY 08081101 S N3Com ������Ƽ�� ���� �� OK��ư�� ����� ������ ����ǵ��� �������� �������� ���Ḧ �������� ���α׷� ������ ����ǵ��� �Ǿ��־���
		//PKY 08062601 E ����ε� �ݺ��� ���̾�׷� ���¸���Ʈ�� ���̾�׷��� �ߺ����� �����¹���
		ProjectManager.getInstance().getConfigurer().setTitle(title+" - N3COM Constructor V3.0");
		ModelManager.getInstance().init();


		ModelManager.getInstance().getOpenDiagarams().clear();
		ProjectManager.getInstance().getConsole().addMessage("Open "+ProjectManager.getInstance().getCurrentProject());
//		ProjectManager.getInstance().setLoad(false);
		//PKY 08072401 E �ܼ� â �������¿��� ���� �ҷ����� �ȵǴ� ���� ����

	}


}