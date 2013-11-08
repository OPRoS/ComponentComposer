package kr.co.n3soft.n3com.rcp.actions;

import java.io.File;

import kr.co.n3soft.n3com.lang.N3Messages;
import kr.co.n3soft.n3com.model.diagram.N3EditorDiagramModel;
import kr.co.n3soft.n3com.parser.ModelManager;
import kr.co.n3soft.n3com.parser.RefLinkModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.dialogs.ListSelectionDialog;

public class LoadAction extends Action {
	
	IWorkbenchWindow workbenchWindow;
	public static   int ID=0;
	IEditorInput input = null;
	public LoadAction(IWorkbenchWindow window) {
		setId("LoadAction");
		this.setText(N3Messages.POPUP_LOAD);
		workbenchWindow = window;
		setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getImage(303)));//PKY 08070101 S 팝업 메뉴 이미지 삽입
		this.setAccelerator(SWT.CTRL|'o');//PKY 08062601 S 단축키 정의

	}
	
	public LoadAction() {
		setId("LoadAction");
		this.setText(N3Messages.POPUP_LOAD);
		
		setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getImage(303)));//PKY 08070101 S 팝업 메뉴 이미지 삽입
//		this.setAccelerator(SWT.CTRL|'o');//PKY 08062601 S 단축키 정의

	}
	
	public void run() {

//		ProjectManager.getInstance().getModelBrowser().writeModel();
		try{
			//20080805 KDI s 주석
			
			
			if((ProjectManager.getInstance().getCommandStack()!=null && ProjectManager.getInstance().getModelBrowser()!=null) && (ProjectManager.getInstance().getCommandStack().isDirty() ||  ProjectManager.getInstance().getModelBrowser().isChange)){
				Display dis = Display.getDefault();		
				Shell newShell = new Shell(dis, SWT.DIALOG_TRIM);
				MessageBox dialog = new MessageBox(newShell,SWT.ICON_QUESTION|SWT.YES|SWT.NO);
				dialog.setText("Message");
				dialog.setMessage("OPRos Component Composer 프로젝트를 세이브 하시겠습니까?");
				int i=dialog.open();
				switch(i) {
				case 64:
					System.out.println("Scrip Wizard Finish!!");
					ProjectManager.getInstance().getUMLEditor().doSaveAs(newShell);
					ProjectManager.getInstance().getConsole().addMessage("Save "+ProjectManager.getInstance().getCurrentProject());
					break;
				case SWT.NO:
					break;
				case SWT.CANCEL:
					break;
				}
			}

			FileDialog fsd = new FileDialog(ProjectManager.getInstance().window.getShell(),SWT.OPEN);
			fsd.setFilterExtensions(new String[] {"*.nmdl","*.*"});
			fsd.setText("Open Select Project Files...");

			String ii = fsd.open();
//			LoadProgressRun lr =	new LoadProgressRun(true);
			//2008043002 PKY S 
			if(!fsd.getFileName().equals("")){
				ProjectManager.getInstance().setLoad(true);
				//20080805 KDI s 오픈 다이얼로그 취소 시
				ProjectManager.getInstance().window.getActivePage().closeAllEditors(false);
				ProjectManager.getInstance().init();
				//20080805 KDI e
				
				String fileName = fsd.getFileName();				
//				V1.02 WJH 080910 S 파일 오픈시 확장명 안쓰면 로드 안되는 문제 수정
				String path = fsd.getFilterPath();
			
				if(fileName == null || fileName.equals(""))
					return;
				if(!fileName.endsWith(".nmdl"))
					fileName = fileName+".nmdl";
//				V1.02 WJH 080910 E 파일 오픈시 확장명 안쓰면 로드 안되는 문제 수정
				ProjectManager.getInstance().setCurrentProjectName(fileName);
				ProjectManager.getInstance().setCurrentProjectPath(path+File.separator);
				ModelManager.getInstance().init();
				String path1 = ProjectManager.getInstance().getCurrentProject();

//				lr.setNp((N3ModelParser)ModelManager.getInstance().getParser());
				ModelManager.getInstance().parse(new File(ProjectManager.getInstance().getCurrentProject()));

				ProgressMonitorDialog pd = new ProgressMonitorDialog(ProjectManager.getInstance().window.getShell());
				pd.run(true, true, (IRunnableWithProgress)ModelManager.getInstance().getParser()); 
//				ModelManager.getInstance().setLoadProgressRun(lr);

				ProjectManager.getInstance().getModelBrowser().refresh(ProjectManager.getInstance().getModelBrowser().getRoot());
				ProjectManager.getInstance().setLoad(false);
				try{
				ProjectManager.getInstance().getConsole().addMessage("Open "+ProjectManager.getInstance().getCurrentProject());
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
			else{
				return;
			}
			//2008043002 PKY E 

		}
		catch(Exception e){
			ProjectManager.getInstance().getModelBrowser().refresh(ProjectManager.getInstance().getModelBrowser().getRoot());
			e.printStackTrace();
			ProjectManager.getInstance().writeLog(null, e);
		}
		String title = ProjectManager.getInstance().getCurrentProjectName().replaceAll(".nmdl", "");
		ProjectManager.getInstance().getConfigurer().setTitle(title+" - N3COM Constructor V3.0");
		ProjectManager.getInstance().getConsole().addMessage("Open complete"+ProjectManager.getInstance().getCurrentProject());
		ProjectManager.getInstance().addOpenProject(ProjectManager.getInstance().getCurrentProject());
		if(ModelManager.getInstance().getRefStore().size()>0){
			for(int i=0;i<ModelManager.getInstance().getRefStore().size();i++){
				RefLinkModel rf = (RefLinkModel)ModelManager.getInstance().getRefStore().get(i);
				rf.link();
			}
		}
		
		if(ModelManager.getInstance().getOpenDiagarams().size()>0){
		 ListSelectionDialog lsd = new ListSelectionDialog(ProjectManager.getInstance().window.getShell(), ModelManager.getInstance().getOpenDiagarams(), new ArrayContentProvider(), new LabelProvider(), "ListSelectionDialog Message");
		
		 lsd.setInitialSelections( ModelManager.getInstance().getOpenDiagarams().toArray());
	         lsd.setTitle("Open Diagrams");
	       int k = lsd.open();
	       if(k==0){
	        Object[] objs = lsd.getResult();
	        for(int i=0;i<objs.length;i++){
	        	Object obj = objs[i];
	        	if(obj instanceof N3EditorDiagramModel){
	        		ProjectManager.getInstance().openDiagram((N3EditorDiagramModel)obj);
	        	}
	        }
	       }
		}
		
		ModelManager.getInstance().init();
		ModelManager.getInstance().getOpenDiagarams().clear();
		ProjectManager.getInstance().setLoad(false);
	}
	
//	private String openFileDialog() {
//		FileDialog dialog = new FileDialog(workbenchWindow.getShell(), SWT.OPEN);
//		dialog.setText("shapes");
//		//dialog.setFilterExtensions(new String[] { ".shapes" });
//		return dialog.open();
//	}
}