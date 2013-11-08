package uuu.views;


import kr.co.n3soft.n3com.lang.N3Messages;
import kr.co.n3soft.n3com.model.comm.StrcuturedPackageTreeModel;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.diagram.N3EditorDiagramModel;
import kr.co.n3soft.n3com.model.diagram.UMLDiagramModel;
import kr.co.n3soft.n3com.project.browser.UMLTreeModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;


/**
 * This sample class demonstrates how to plug-in a new
 * workbench view. The view shows data obtained from the
 * model. The sample creates a dummy model on the fly,
 * but a real implementation would connect to the model
 * available either in this or another plug-in (e.g. the workspace).
 * The view is connected to the model using a content provider.
 * <p>
 * The view uses a label provider to define how model
 * objects should be presented in the view. Each
 * view can present the same model objects using
 * different labels and icons, if needed. Alternatively,
 * a single label provider can be shared between views
 * in order to ensure that objects of the same type are
 * presented in the same way everywhere.
 * <p>
 */

public class SampleView extends ViewPart {
	private TableViewer viewer;
	private Action action1;
	private Action action2;
	private Action doubleClickAction;
	Composite parent = null;
	UMLModel um;
	
	private java.util.ArrayList searchModel = null;

	/*
	 * The content provider class is responsible for
	 * providing objects to the view. It can wrap
	 * existing objects in adapters or simply return
	 * objects as-is. These objects may be sensitive
	 * to the current input of the view, or ignore
	 * it and always show the same content 
	 * (like Task List, for example).
	 */
	

	
	class ViewContentProvider implements IStructuredContentProvider {
		public void inputChanged(Viewer v, Object oldInput, Object newInput) {
		}
		public void dispose() {
		}
		public Object[] getElements(Object parent) {
			return new String[] { " " };
		}
	}
	class ViewLabelProvider extends LabelProvider implements ITableLabelProvider {
		public String getColumnText(Object obj, int index) {
			return getText(obj);
		}
		public Image getColumnImage(Object obj, int index) {
			if(obj instanceof ConsoleTableItem){
				ConsoleTableItem ct = (ConsoleTableItem)obj;
				return ct.getModelImage();
			}
			return getImage(obj);
		}
		public Image getImage(Object obj) {
			return null;
		}
	}
	class NameSorter extends ViewerSorter {
	}

	/**
	 * The constructor.
	 */
	public SampleView() {
		ProjectManager.getInstance().setConsole(this);
	}
	//ijs080429
	public void addMessage(String msg){
		int index = viewer.getTable().getItemCount();
		viewer.getTable().removeAll();
//		   for(int i=0;i<index;i++){
//				viewer.getTable().remove(0);
//			}
		   ConsoleTableItem temp =new ConsoleTableItem();
		   temp.msg = msg;
		   this.viewer.add(temp);
		
	}
	
	public void appendMessage(String msg){
		   ConsoleTableItem temp =new ConsoleTableItem();
		   temp.msg = msg;
		   this.viewer.add(temp);		
	}
	
	public void appendMessage2(String msg){
		   ConsoleTableItem temp =new ConsoleTableItem();
		   temp.msg = msg;
		   int index = viewer.getTable().getItemCount();
		   this.viewer.insert(temp, index);
		  
	}
	
	public void setProjectSearch(java.util.ArrayList list){
//		this.searchModel = list;

		int index = viewer.getTable().getItemCount();
	   for(int i=0;i<index;i++){
			viewer.getTable().remove(0);
		}
		
		if(ProjectManager.getInstance().isSearchModel()){
		for(int i=0;i<list.size();i++){
			UMLTreeModel ut = (UMLTreeModel)list.get(i);
			if(ut.getRefModel() instanceof N3EditorDiagramModel){
				N3EditorDiagramModel nd = (N3EditorDiagramModel)ut.getRefModel();
				String sType = ProjectManager.getInstance().getDiagramPath(nd.getDiagramType());
				ConsoleTableItem temp =new ConsoleTableItem();
				temp.ref = ut;
				temp.typeName = sType;
				UMLModel um = (UMLModel)ut.getRefModel();
				temp.packageName = um.getPackage();
			    this.viewer.add(temp);
			}
			else{
				int type = ut.getModelType();
				String sType = ProjectManager.getInstance().getModelTypeName(type);
				ConsoleTableItem temp =new ConsoleTableItem();
				UMLModel um = (UMLModel)ut.getRefModel();
				
				 temp.packageName = um.getPackage();
				temp.ref = ut;
				temp.typeName = sType;
			    this.viewer.add(temp);
			    
				
			}
		}
		}
		else{
			for(int i=0;i<list.size();i++){
				UMLModel umd = (UMLModel)list.get(i);
				UMLTreeModel ut = (UMLTreeModel)umd.getUMLTreeModel();
		
					int type = ut.getModelType();
					String sType = ProjectManager.getInstance().getModelTypeName(type);
					ConsoleTableItem temp =new ConsoleTableItem();
					UMLModel um = umd;
					if(um.getN3EditorDiagramModelTemp()!=null){
						temp.packageName = um.getN3EditorDiagramModelTemp().getPackage()+"."+um.getN3EditorDiagramModelTemp().getName();
					}
					else
						temp.packageName = um.getPackage();
					temp.ref = ut;
					temp.refModel =um;
					temp.typeName = sType;
				    this.viewer.add(temp);
				    
					
				
			}
		}
//		System.out.println("list.size():"+list.size());
		 viewer.add(list.size()+" element found");
	}

	/**
	 * This is a callback that will allow us
	 * to create the viewer and initialize it.
	 */
	public void createPartControl(Composite parent) {
		
		viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		viewer.setContentProvider(new ViewContentProvider());
		viewer.setLabelProvider(new ViewLabelProvider());
//		viewer.setSorter(new NameSorter());
		viewer.setInput(getViewSite());
	
	
		
		makeActions();
		hookContextMenu();
		hookDoubleClickAction();
		contributeToActionBars();
	}

	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				SampleView.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillLocalPullDown(IMenuManager manager) {
//		manager.add(action1);
		manager.add(new Separator());
//		manager.add(action2);
	}

	private void fillContextMenu(IMenuManager manager) {
		manager.add(action1);
		manager.add(action2);
		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
		//PKY 08082201 S 콘솔 정리작업
		if(viewer!=null&&viewer.getSelection()!=null){

			//PKY 08072401 S 콘솔 창 오른쪽 버튼 클릭시 다이어그램 오픈 메뉴 떠서 오픈할 수 있도록 
			StructuredSelection selection =	 (StructuredSelection) viewer.getSelection();
			Object obj=selection.getFirstElement();
			if(!(obj instanceof ConsoleTableItem)){
				action1.setEnabled(false);
				return;
			}
			//20080721IJS 끝
			boolean isEnableTrue = false;

			

			//20080721IJS 시작
			if(!(obj instanceof ConsoleTableItem))
				return;
			//20080721IJS 끝
			//PKY 08090401 S 콘솔 저장 불러올경우 더블클릭하여 오픈안되는문제
			ConsoleTableItem ct = (ConsoleTableItem)obj;
			if(ct.ref!=null && ct.packageName!=null)//PKY 08090901 S 
			ProjectManager.getInstance().getModelBrowser().select(ct.ref);
			if(ct.ref!=null && ct.packageName!=null)//PKY 08090901 S 
			if(ct.ref instanceof UMLTreeModel){
				UMLTreeModel umlTreeModel = (UMLTreeModel)ct.ref ;
				if(umlTreeModel.getIN3UMLModelDeleteListeners()!=null && umlTreeModel.getIN3UMLModelDeleteListeners().size() > 0 ){
					for(int i = 0; i < umlTreeModel.getIN3UMLModelDeleteListeners().size(); i ++){
						N3EditorDiagramModel n3EditorDiagramModel = (N3EditorDiagramModel)umlTreeModel.getIN3UMLModelDeleteListeners().get(i);
						System.out.println(n3EditorDiagramModel.getPackage());
						System.out.println(ct.packageName);
						if(ct.packageName.equals(n3EditorDiagramModel.getPackage()+"."+n3EditorDiagramModel.getName())){
						if (n3EditorDiagramModel != null && n3EditorDiagramModel.getUMLTreeModel() != null){
							action1.setEnabled(true);
							isEnableTrue=true;
						}
						}else if(i==umlTreeModel.getIN3UMLModelDeleteListeners().size()-1){
							action1.setEnabled(true);
							isEnableTrue=true;
						}

					}
				}
			}
			if(!isEnableTrue){
				action1.setEnabled(false);
			}
			//PKY 08090401 E 콘솔 저장 불러올경우 더블클릭하여 오픈안되는문제

//			if(ProjectManager.getInstance().isSearchModel()){
//			if(ct.ref instanceof UMLTreeModel)
//				ProjectManager.getInstance().getModelBrowser().select((UMLTreeModel)ct.ref);
//			}
//			else{

//			}
		
			if(!isEnableTrue){
				action1.setEnabled(false);
			}
		
		}
		//PKY 08082201 E 콘솔 정리작업

	}
	
	private void fillLocalToolBar(IToolBarManager manager) {
//		manager.add(action1);
//		manager.add(action2);
	}
	//PKY 08090801 S 

	private void makeActions() {
		action1 = new Action() {
			public void run() {
				
				ISelection selection = viewer.getSelection();
				Object obj = ((IStructuredSelection)selection).getFirstElement();
				//20080721IJS 시작
				if(!(obj instanceof ConsoleTableItem))
					return;
				//20080721IJS 끝
				//PKY 08090401 S 콘솔 저장 불러올경우 더블클릭하여 오픈안되는문제
				ConsoleTableItem ct = (ConsoleTableItem)obj;
				if(ct.ref!=null && ct.packageName!=null)//PKY 08090901 S 
				ProjectManager.getInstance().getModelBrowser().select(ct.ref);
				if(ct.ref!=null && ct.packageName!=null)//PKY 08090901 S 
				if(ct.ref instanceof UMLTreeModel){
					UMLTreeModel umlTreeModel = (UMLTreeModel)ct.ref ;
					if(umlTreeModel.getIN3UMLModelDeleteListeners()!=null && umlTreeModel.getIN3UMLModelDeleteListeners().size() > 0 ){
						for(int i = 0; i < umlTreeModel.getIN3UMLModelDeleteListeners().size(); i ++){
							N3EditorDiagramModel n3EditorDiagramModel = (N3EditorDiagramModel)umlTreeModel.getIN3UMLModelDeleteListeners().get(i);
							System.out.println(n3EditorDiagramModel.getPackage());
							System.out.println(ct.packageName);
							if(ct.packageName.equals(n3EditorDiagramModel.getPackage()+"."+n3EditorDiagramModel.getName())){
							if (n3EditorDiagramModel != null && n3EditorDiagramModel.getUMLTreeModel() != null)
								ProjectManager.getInstance().getModelBrowser().select(umlTreeModel);
							if (n3EditorDiagramModel != null) {
								ProjectManager.getInstance().openDiagram(n3EditorDiagramModel);
							}
							}else if(i==umlTreeModel.getIN3UMLModelDeleteListeners().size()-1){
								if (n3EditorDiagramModel != null && n3EditorDiagramModel.getUMLTreeModel() != null)
									ProjectManager.getInstance().getModelBrowser().select(umlTreeModel);
								if (n3EditorDiagramModel != null) {
									ProjectManager.getInstance().openDiagram(n3EditorDiagramModel);
								}
							}

						}
					}
				}
				//PKY 08090401 E 콘솔 저장 불러올경우 더블클릭하여 오픈안되는문제

//				if(ProjectManager.getInstance().isSearchModel()){
//				if(ct.ref instanceof UMLTreeModel)
//					ProjectManager.getInstance().getModelBrowser().select((UMLTreeModel)ct.ref);
//				}
//				else{

//				}
			}
		};
		action1.setText(N3Messages.POPUP_OPEN_DIAGRAM);
		//PKY 08072401 E 콘솔 창 오른쪽 버튼 클릭시 다이어그램 오픈 메뉴 떠서 오픈할 수 있도록 
		
		action1.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
			getImageDescriptor(ISharedImages.IMG_TOOL_FORWARD));
		
		action2 = new Action() {
			public void run() {
				viewer.getTable().clearAll();
			}
		};
		action2.setText(N3Messages.POPUP_CLEAR);
		
		action2.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
				getImageDescriptor(ISharedImages.IMG_TOOL_DELETE));
		doubleClickAction = new Action() {
			public void run() {
				
				ISelection selection = viewer.getSelection();
				Object obj = ((IStructuredSelection)selection).getFirstElement();
				//20080721IJS 시작
				if(!(obj instanceof ConsoleTableItem))
					return;
				//20080721IJS 끝
				//PKY 08090401 S 콘솔 저장 불러올경우 더블클릭하여 오픈안되는문제
				ConsoleTableItem ct = (ConsoleTableItem)obj;
				if(ct.ref!=null && ct.packageName!=null)//PKY 08090901 S 
				ProjectManager.getInstance().getModelBrowser().select(ct.ref);
				//PKY 08090801 S 
				if(ct.ref!=null && ct.packageName!=null)//PKY 08090901 S 
				if(ct.ref instanceof UMLTreeModel){
					UMLTreeModel umlTreeModel = (UMLTreeModel)ct.ref ;
					if(umlTreeModel.getIN3UMLModelDeleteListeners()!=null && umlTreeModel.getIN3UMLModelDeleteListeners().size() > 0 ){
						for(int i = 0; i < umlTreeModel.getIN3UMLModelDeleteListeners().size(); i ++){
							N3EditorDiagramModel n3EditorDiagramModel = (N3EditorDiagramModel)umlTreeModel.getIN3UMLModelDeleteListeners().get(i);
							System.out.println(n3EditorDiagramModel.getPackage());
							System.out.println(ct.packageName);
							if(ct.packageName.equals(n3EditorDiagramModel.getPackage()+"."+n3EditorDiagramModel.getName())){
							if (n3EditorDiagramModel != null && n3EditorDiagramModel.getUMLTreeModel() != null)
								ProjectManager.getInstance().getModelBrowser().select(umlTreeModel);
							if (n3EditorDiagramModel != null) {
								ProjectManager.getInstance().openDiagram(n3EditorDiagramModel);
							}
							}else if(i==umlTreeModel.getIN3UMLModelDeleteListeners().size()-1){
								if (n3EditorDiagramModel != null && n3EditorDiagramModel.getUMLTreeModel() != null)
									ProjectManager.getInstance().getModelBrowser().select(umlTreeModel);
								if (n3EditorDiagramModel != null) {
									ProjectManager.getInstance().openDiagram(n3EditorDiagramModel);
								}
							}

						}
					}
				}
				//PKY 08090801 ES 

				//PKY 08090401 E 콘솔 저장 불러올경우 더블클릭하여 오픈안되는문제

//				if(ProjectManager.getInstance().isSearchModel()){
//				if(ct.ref instanceof UMLTreeModel)
//					ProjectManager.getInstance().getModelBrowser().select((UMLTreeModel)ct.ref);
//				}
//				else{

//				}
			}
		};
		
	}
	//PKY 08090801 E 

	private void hookDoubleClickAction() {
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				doubleClickAction.run();
			}
		});
	}
	private void showMessage(String message) {
		MessageDialog.openInformation(
			viewer.getControl().getShell(),
			"Sample View",
			message);
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		viewer.getControl().setFocus();
	}
}