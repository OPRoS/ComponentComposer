package kr.co.n3soft.n3com.rcp.actions;

import java.io.File;

import kr.co.n3soft.n3com.lang.N3Messages;
import kr.co.n3soft.n3com.project.browser.RootTreeModel;
import kr.co.n3soft.n3com.project.browser.UMLTreeModel;
import kr.co.n3soft.n3com.project.browser.UMLTreeParentModel;
import kr.co.n3soft.n3com.project.dialog.NewProjectDialog;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchWindow;

public class NewProjectAction extends Action {

	IWorkbenchWindow workbenchWindow;
	public static   int ID=0;
	IEditorInput input = null;
	public NewProjectAction(IWorkbenchWindow window) {
		setId("NewProjectAction");
		this.setText(N3Messages.POPUP_NEW_PROJECT); //20080730 KDI s
		workbenchWindow = window;
		setImageDescriptor(uuu.Activator.getImageDescriptor("/icons/newproject.gif"));
		this.setAccelerator(SWT.CTRL|'n'); //PKY 08061801 S 단축키 정의
	}

	public void run() {

		//20080730 KDI s
		UMLTreeParentModel treeObject = null;
		
		TreeItem trees2[] = ProjectManager.getInstance().getModelBrowser().getViewer().getTree().getItems();
		if(trees2 != null && trees2.length>0){
			treeObject = (UMLTreeParentModel)trees2[0].getData();
		}
		
        if (treeObject != null && treeObject.getChildren().length>0){
    		//PKY 08071601 S 다이얼로그 UI작업
    		if(ProjectManager.getInstance().getCurrentProjectName()!=null || !ProjectManager.getInstance().getCurrentProjectName().trim().equals("")){	        
    			MessageBox dialog = new MessageBox(ProjectManager.getInstance().window.getShell(),SWT.ICON_QUESTION|SWT.YES|SWT.NO|SWT.CANCEL);
    			dialog.setText("Message");
    			dialog.setMessage(N3Messages.DIALOG_NEW_PROJECT_MESSAGE);
    			int i=dialog.open();

    			switch(i) {
    			case IDialogConstants.FINISH_ID:
    				System.out.println("Scrip Wizard Finish!!");
    				break;
    			case SWT.YES:
    				try{
    					//			ProgressMonitorDialog pd = new ProgressMonitorDialog(workbenchWindow.getShell());
    					//			pd.run(true, true, (IRunnableWithProgress)ProjectManager.getInstance().getModelBrowser());
    					if(ProjectManager.getInstance().getCurrentProjectName()==null || ProjectManager.getInstance().getCurrentProjectName().trim().equals("")){
    						FileDialog fsd = new FileDialog(workbenchWindow.getShell(),SWT.SAVE);
    						fsd.setFilterExtensions(new String[] {"*.nmdl","*.*"});
    						fsd.setText("Save Select Project Files...");
    						fsd.open();
    						//				LoadProgressRun lr =	new LoadProgressRun(true);
    						//PKY 08062601 S 저장할때 경로지정이 제대로 안될경우 에러발생 처리
    						if(!fsd.getFileName().trim().equals("")){
    							String fileName = fsd.getFileName();
    							String path = fsd.getFilterPath();

    							ProjectManager.getInstance().setCurrentProjectName(fileName);
    							ProjectManager.getInstance().setCurrentProjectPath(path+File.separator);
    						}
    						//PKY 08062601 E 저장할때 경로지정이 제대로 안될경우 에러발생 처리
    					}
    					//PKY 08062601 S 저장할때 경로지정이 제대로 안될경우 에러발생 처리
    					if(!ProjectManager.getInstance().getCurrentProjectName().trim().equals("")){
    						ProjectManager.getInstance().getConsole().addMessage("Save "+ProjectManager.getInstance().getCurrentProject());
    						ProjectManager.getInstance().getModelBrowser().writeModel(ProjectManager.getInstance().getCurrentProject());
    						ProjectManager.getInstance().getConsole().addMessage("Save complete "+ProjectManager.getInstance().getCurrentProject());
    						String title = ProjectManager.getInstance().getCurrentProjectName().replaceAll(".nmdl", "");
//    						ProjectManager.getInstance().getConfigurer().setTitle(title+" - N3COM Constructor V3.0");
//    						ProjectManager.getInstance().addOpenProject(ProjectManager.getInstance().getCurrentProject());
    					}
    					workbenchWindow.getActivePage().closeAllEditors(false);
    					try{
    						ProjectManager.getInstance().init();
    						//20080730 KDI s
    						if(ProjectManager.getInstance().getConsole() != null)
    							ProjectManager.getInstance().getConsole().addMessage("");
    						//20080730 KDI e
    					}
    					catch(Exception e){
    						e.printStackTrace();
    					}
    					//PKY 08062601 E 저장할때 경로지정이 제대로 안될경우 에러발생 처리
    				}
    				catch(Exception e){
    					e.printStackTrace();
    				}
    				break; //20080730 KDI s

    			case SWT.CANCEL:
    				return;
    			}
    		}
    		//PKY 08071601 E 다이얼로그 UI작업
        }
        
		//20080730 KDI e        

		workbenchWindow.getActivePage().closeAllEditors(false);

		try{
			ProjectManager.getInstance().init();
			//20080730 KDI s
			if(ProjectManager.getInstance().getConsole() != null)
				ProjectManager.getInstance().getConsole().addMessage("");
			//20080730 KDI e
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		//20080729 KDI s 새 프로젝트 관련 액션 추가
		NewProjectDialog da = new NewProjectDialog(workbenchWindow.getShell());
		int btn = da.open();
		
		if(btn == IDialogConstants.OK_ID){
//			UMLTreeParentModel treeObject = null;
			
			TreeItem trees[] = ProjectManager.getInstance().getModelBrowser().getViewer().getTree().getItems();
			if(trees != null && trees.length>0){
				treeObject = (UMLTreeParentModel)trees[0].getData();
			}
			
	        String name = da.getName();
	        java.util.ArrayList list = da.getModelType();
	        
	        if (da.isOK && treeObject != null){        	
	            ProjectManager.getInstance().addUMLModel(name, treeObject, null, 0,-1);
	            UMLTreeParentModel root = (UMLTreeParentModel)treeObject.getChildren()[0];
	            if(root != null){
	            	for(int i=0;i<list.size();i++){
	            		java.util.HashMap map = (java.util.HashMap)list.get(i);
	            		ProjectManager.getInstance().addUMLDiagram((String)map.get("name"), root, null, 1, true, (Integer)map.get("type"));
	            	}
	            }
	            ProjectManager.getInstance().getModelBrowser().refresh(root);
	        }	        
		}		        
		//20080729 KDI e

	}
}
