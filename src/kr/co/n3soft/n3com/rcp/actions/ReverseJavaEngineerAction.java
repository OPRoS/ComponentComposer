package kr.co.n3soft.n3com.rcp.actions;

import java.io.File;
import java.util.Iterator;

import javac.parser.ImportManager;
import javac.parser.ReverseFromJavaToModel;
import javac.test.ReleaseTest;
import kr.co.n3soft.n3com.model.diagram.N3EditorDiagramModel;
import kr.co.n3soft.n3com.project.browser.UMLTreeParentModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchWindow;

public class ReverseJavaEngineerAction extends Action {
	
	IWorkbenchWindow workbenchWindow;
	public static   int ID=0;
	IEditorInput input = null;
	public ReverseJavaEngineerAction(IWorkbenchWindow window) {
		setId("ReverseJavaEngineerAction");
		this.setText("JAVA");//PKY 08072201 S 도구 툴바 통합
		workbenchWindow = window;
		setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getImage(307)));//PKY 08070101 S 팝업 메뉴 이미지 삽입
	}
	
	public void run() {


		try{
//			DirectoryDialog fsd = new DirectoryDialog(workbenchWindow.getShell(),SWT.OPEN);
			FileDialog fsd = new FileDialog(workbenchWindow.getShell(),SWT.MULTI);
			
			fsd.setFilterExtensions(new String[] {"*.java","*.*"});
//			fsd.setText("Select Project Files...");
//			ReverseDialog rd = new ReverseDialog(workbenchWindow.getShell());
			fsd.open();
			String path = fsd.getFilterPath();
			String[] filenames = fsd.getFileNames();
//			String fileName = fsd.getFileName();
//			String path = fsd.getFilterPath();
			String fileName = null;
//			String path = null;
			if(filenames!=null && filenames.length>0){
				ImportManager immgr = new ImportManager();
				for(int i=0;i<filenames.length;i++){
					String filename = filenames[i];
					int index = filename.lastIndexOf(".");
					String  ext = filename.substring(index+1);
					
					ReleaseTest releaseTest = new ReleaseTest();
					if("java".equals(ext)){
						releaseTest.setJava(true);
					}
					else{
						releaseTest.setJava(false);
					}
					releaseTest.test(path+File.separator+filename);
				}
					Iterator it = ProjectManager.getInstance().getReverseModelMap().keySet().iterator();
					while(it.hasNext()){
					    String pkg = (String)it.next();
					    java.util.ArrayList list = (java.util.ArrayList)ProjectManager.getInstance().getReverseModelMap().get(pkg);
					    UMLTreeParentModel up = ProjectManager.getInstance().getModelBrowser().makeParentTree(pkg);
					    N3EditorDiagramModel n3EditorDiagramModel  = ProjectManager.getInstance().makeDiagram(up);
					    java.util.ArrayList diagramData = new java.util.ArrayList(); 
					    for(int k=0;k<list.size();k++){
					    	ReverseFromJavaToModel rtm = (ReverseFromJavaToModel)list.get(k);
					    	if(rtm.extendsClass.size()>0 || rtm.interfaces.size()>0){
					    		immgr.searchModel.add(rtm);
					    	}
					    	ProjectManager.getInstance().getModelBrowser().makeTreeModel(rtm,rtm.getModel(),up ,n3EditorDiagramModel,k);
					    	rtm.n3EditorDiagramModel = n3EditorDiagramModel;
					    	diagramData.add(rtm);
					     }
					    
					    immgr.pkgData.put(pkg, diagramData);
					}
					immgr.layoutDiagarm();
					ProjectManager.getInstance().getModelBrowser().refresh(ProjectManager.getInstance().getModelBrowser().getRoot());
			
			
	}
//			rd.open();
//				if(fileName!=null){
//					ReleaseTest releaseTest = new ReleaseTest();
//					releaseTest.test(path+File.separator+fileName);
//					Iterator it = ProjectManager.getInstance().getReverseModelMap().keySet().iterator();
//					while(it.hasNext()){
//					    String pkg = (String)it.next();
//					    java.util.ArrayList list = (java.util.ArrayList)ProjectManager.getInstance().getReverseModelMap().get(pkg);
//					    UMLTreeParentModel up = ProjectManager.getInstance().getModelBrowser().makeParentTree(pkg);
//					    N3EditorDiagramModel n3EditorDiagramModel  = ProjectManager.getInstance().makeDiagram(up);
//					     for(int i=0;i<list.size();i++){
//					    	ReverseFromJavaToModel rtm = (ReverseFromJavaToModel)list.get(i);
//					    	ProjectManager.getInstance().getModelBrowser().makeTreeModel(rtm,up ,n3EditorDiagramModel,i);
//					    }
//					}
//					ProjectManager.getInstance().getModelBrowser().refresh(ProjectManager.getInstance().getModelBrowser().getRoot());
//				}
			}
		catch(Exception e){
			ProjectManager.getInstance().getModelBrowser().refresh(ProjectManager.getInstance().getModelBrowser().getRoot());
			e.printStackTrace();
		}
		ProjectManager.getInstance().getReverseModelMap().clear();
	}
	

}