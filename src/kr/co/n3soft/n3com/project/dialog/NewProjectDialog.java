package kr.co.n3soft.n3com.project.dialog;

import java.io.File;
import java.util.ArrayList;

import kr.co.n3soft.n3com.lang.N3Messages;
import kr.co.n3soft.n3com.model.diagram.N3EditorDiagramModel;
import kr.co.n3soft.n3com.parser.ModelManager;
import kr.co.n3soft.n3com.parser.RefLinkModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ListSelectionDialog;

/**
 * 20080729 KDI s 새프로젝트 다이얼로그 추가
 * @author Administrator
 *
 */
//PKY 08090201 S New Project Dialog 변경

//PKY 08080501 S NewPorject에 불러온 프로젝트 리스트도 보여주도록 하여 사용자가 편의성 개선
public class NewProjectDialog extends Dialog {
    private static final int RESET_ID = IDialogConstants.NO_TO_ALL_ID + 1;
    private static final int OK_ID = 0;
    private static final int CANCEL_ID = 1;
    
    private String name = "";
    private java.util.ArrayList modelType = new java.util.ArrayList();

    private Text prjNameField;
    
    private Label descriptionField;
        
    Group type;
    Group description;
    
    Label nameLabel = null;
    
    private TreeViewer tableViewer;
    private TableViewer openProjectList;
    
    boolean openProjList =false;
    
    public java.util.ArrayList models = new ArrayList();
    
    public boolean isOK = false;
    
    private static final String NAME_PROPERTY = N3Messages.POPUP_NAME; 
	private static final String DESC_PROPERTY = N3Messages.POPUP_DESCRIPTION; 
	
    private String diagramName = "";
    
    private ArrayList addDiagram = new ArrayList();
    
    public NewProjectDialog(Shell parentShell) {
        super(parentShell);
        this.setShellStyle(SWT.APPLICATION_MODAL | SWT.RESIZE | SWT.DIALOG_TRIM);//PKY 08081801 S 새 프로젝트 다이얼로그 사이즈 늘어날수 있도록 수정
        init();        
    }
    
    protected Control createDialogArea(Composite parent) {
        Composite comp = (Composite)super.createDialogArea(parent);

        try {

        	comp.getShell().setText(N3Messages.POPUP_NEW_PROJECT);
//			comp.getShell().setImage(ProjectManager.getInstance().getImage(316));
			
			
            GridLayout layout = (GridLayout)comp.getLayout();
            layout.numColumns = 3;
            layout.marginTop = 7;
            layout.marginLeft = 7;
            layout.marginRight = 7;
            layout.marginBottom = 7;
            layout.verticalSpacing = 7;
            layout.horizontalSpacing = 7;

            if(ProjectManager.getInstance()!=null&&ProjectManager.getInstance().getOpenProjects().size()>0){ 
            	GridData data2 = new GridData(GridData.FILL_BOTH);
            	data2.horizontalSpan=3;            
            	type = new Group(comp, SWT.SHADOW_ETCHED_IN);
            	type.setText(N3Messages.POPUP_RECENT_OPEN_PROJECT);
            	type.setLayoutData(data2);
            	type.setLayout(new GridLayout());
            	final Table openProjectTable = new Table(type, SWT.MULTI |  SWT.FULL_SELECTION);
            	GridData data3 = new GridData(GridData.FILL_BOTH);
            	data3.heightHint=75;
            	openProjectTable.setLayoutData(data3);
            	openProjectList = buildProjectLyoutTable(openProjectTable);
    			attachContentProvider(openProjectList);
    			attachLabelProjectListProvider(openProjectList);
    			
            	Object obj = ProjectManager.getInstance().getOpenProjects();
            	openProjectList.setInput(ProjectManager.getInstance().getOpenProjects().toArray());
            	openProjList=true;
            	openProjectList.addSelectionChangedListener(new ISelectionChangedListener(){
            		  public void selectionChanged(SelectionChangedEvent event){
            			  prjNameField.setText("");
            			//PKY 08091701 S
            			  for(int i = 0 ; i < tableViewer.getTree().getItemCount(); i ++){
            				  if(tableViewer.getTree().getItem(i) instanceof TreeItem){
            					  TreeItem treeItem = (TreeItem)tableViewer.getTree().getItem(i);
            					  treeItem.setChecked(false);
            					  for(int j = 0; j < treeItem.getItems().length;  j++){
            						  treeItem.getItems()[j].setChecked(false);
            					  }
            				  }
            			  }
            			//PKY 08091701 E

              			System.out.print("");
             /* 			if(obj instanceof TreeItem){
              				System.out.print("");
              			}*/

              		
            			//PKY 08091701 E

//            			  tableViewer.getTable().getItem(0).setChecked(false);
//            			  tableViewer.getTable().getItem(1).setChecked(false);
//            			  tableViewer.getTable().getItem(2).setChecked(false);
//            			  tableViewer.getTable().getItem(3).setChecked(false);
//            			  tableViewer.getTable().getItem(4).setChecked(false);
//            			  tableViewer.getTable().getItem(5).setChecked(false);
//            			  tableViewer.getTable().getItem(6).setChecked(false);
//            			  tableViewer.getTable().getItem(7).setChecked(false);
//            			  tableViewer.getTable().getItem(8).setChecked(false);
//            			  tableViewer.getTable().getItem(9).setChecked(false);
//            			  tableViewer.getTable().getItem(10).setChecked(false);
//            			  tableViewer.getTable().getItem(11).setChecked(false);
//            			  tableViewer.getTable().getItem(12).setChecked(false);
//            			  tableViewer.getTable().getItem(13).setChecked(false);
//            			  tableViewer.getTable().setSelection(-1);
            		  }
            		
            	});


            }
            
            Label nameLabel = new Label(comp, SWT.RIGHT);
            nameLabel.setText(N3Messages.DIALOG_PROJECT_NAME+": ");
            prjNameField = new Text(comp, SWT.SINGLE | SWT.BORDER);
       
            GridData data = new GridData(GridData.FILL_HORIZONTAL);
            data.horizontalSpan = 2;
            prjNameField.setLayoutData(data);
            GridData data1 = new GridData(GridData.FILL_BOTH);
            data1.horizontalSpan = 3;
            type = new Group(comp, SWT.SHADOW_ETCHED_IN);
            type.setText(N3Messages.DIALOG_DIAGRAMTYPE);

            type.setLayoutData(data1);
            type.setLayout(new GridLayout());
//            final Table table = new Table(type, SWT.MULTI | SWT.CHECK | SWT.FULL_SELECTION);
//			table.setLayoutData(data1);
            
			tableViewer= new TreeViewer(type, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER|SWT.CHECK|SWT.MULTI);
			

			data1 = new GridData(GridData.FILL_BOTH);

			data1.widthHint = 400;
			data1.heightHint = 300;


			tableViewer.setContentProvider(new ViewContentProvider());
			tableViewer.setLabelProvider(new ViewLabelProvider());

			tableViewer.setInput(this.initialize());
			
			
			tableViewer.expandAll();
			
        	tableViewer.getTree().addSelectionListener(new SelectionListener(){
        		public void widgetSelected(SelectionEvent e){
        			TreeItem treeItem = (TreeItem)e.item;
        			if(treeItem.getParentItem()!=null){
        				treeItem.getParentItem().setChecked(true);
        				boolean isCheck = false;
        				for(int i = 0; i <treeItem.getParentItem().getItems().length; i ++){
        					if(treeItem.getParentItem().getItems()[i].getChecked()){
        						isCheck = true;
        					}
        				}
        				if(!isCheck){
        					treeItem.getParentItem().setChecked(false);
        				}
        			}else{
        				if(treeItem.getChecked()){
            				for(int i = 0; i <treeItem.getItems().length; i ++){
            						treeItem.getItems()[i].setChecked(true);
            				}
        				}else{
            				for(int i = 0; i <treeItem.getItems().length; i ++){
            						treeItem.getItems()[i].setChecked(false);
            				}
        				}
        			}
        			openProjectList.getTable().setSelection(-1);//PKY 08091701 S
        			System.out.print("");
       /* 			if(obj instanceof TreeItem){
        				System.out.print("");
        			}*/
 
        		}
        		public void widgetDefaultSelected(SelectionEvent e){
        			
        		}
        	});
        	tableViewer.getTree().setLayoutData(data1);
            type.pack();
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return comp;
    }
    
    private TableViewer buildProjectLyoutTable(final Table table){
		TableViewer tableViewer = new TableViewer(table);
		TableLayout layout = new TableLayout();
		layout.addColumnData(new ColumnWeightData(30, 40, true));
		layout.addColumnData(new ColumnWeightData(50, 220, true));


		table.setLayout(layout);
		TableColumn typeColumn = new TableColumn(table, SWT.CENTER);
		typeColumn.setText(NAME_PROPERTY);
		
		table.setHeaderVisible(true);
		return tableViewer;
    }
    
	private TableViewer buildAndLayoutTable(final Table table) {
		TableViewer tableViewer = new TableViewer(table);
		TableLayout layout = new TableLayout();
		layout.addColumnData(new ColumnWeightData(30, 40, true));
		layout.addColumnData(new ColumnWeightData(50, 220, true));


		table.setLayout(layout);
		TableColumn typeColumn = new TableColumn(table, SWT.CENTER);
		typeColumn.setText(NAME_PROPERTY);
		TableColumn initColumn = new TableColumn(table, SWT.CENTER);
		initColumn.setText(DESC_PROPERTY);
		
		table.setHeaderVisible(true);
		return tableViewer;
	}
	
	private void attachContentProvider(TableViewer viewer) {
		viewer.setContentProvider(
				new IStructuredContentProvider() {
					public Object[] getElements(Object inputElement) {
						return (Object[]) inputElement;
					}
					public void dispose() {
					}
					public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
					}
				});
	}
	
	private void attachLabelProvider(TableViewer viewer) {
        viewer.setLabelProvider(
            new ITableLabelProvider() {
                public Image getColumnImage(Object element, int columnIndex) {
                	java.util.HashMap map = (java.util.HashMap)element;
                	if(columnIndex==0)
                		return ProjectManager.getInstance().getDiagramImage(Integer.parseInt((String)map.get("type")));
                	else
                		return null;
                }
                public String getColumnText(Object element, int columnIndex) {
                	java.util.HashMap map = (java.util.HashMap)element;
                    switch (columnIndex) {
                        case 0:
                            String type = (String)map.get("name");
                            return type;                           
                        case 1:
                        	  String desc = (String)map.get("desc");
                              return desc;
                        default:
                            return "Invalid column: " + columnIndex;
                    }
                }
                public void addListener(ILabelProviderListener listener) {
                }
                public void dispose() {
                }
                public boolean isLabelProperty(Object element, String property) {
                    return false;
                }
                public void removeListener(ILabelProviderListener lpl) {
                }
            });
    }
	
	private void attachLabelProjectListProvider(TableViewer viewer) {
        viewer.setLabelProvider(
            new ITableLabelProvider() {
                public Image getColumnImage(Object element, int columnIndex) {
                	return null;
                }
                public String getColumnText(Object element, int columnIndex) {
                	String url = (String)element;
                    switch (columnIndex) {
                        case 0:
                            String type =url;
                            return type;                           
                        default:
                            return "Invalid column: " + columnIndex;
                    }
                }
                public void addListener(ILabelProviderListener listener) {
                }
                public void dispose() {
                }
                public boolean isLabelProperty(Object element, String property) {
                    return false;
                }
                public void removeListener(ILabelProviderListener lpl) {
                }
            });
    }
	
	
    protected void createButtonsForButtonBar(Composite parent) {
        super.createButtonsForButtonBar(parent);
    }

    protected void buttonPressed(int buttonId) {
    
        if (buttonId == RESET_ID) {

        }
        else {
        	//PKY 08091806 S

            if (buttonId == this.OK_ID) {
            	if(openProjectList!=null){
            	if(openProjectList.getTable().getSelectionCount()==0){
                    if (prjNameField.getText().equals("")) {
                    	modelType.clear();
                        MessageDialog dialog = new MessageDialog(this.getShell(), "경고", null, "프로젝트 이름을 넣어 주세요.", MessageDialog.ERROR,
                            new String[] { "확인" }, 0);
                        dialog.open();
                        return;
                    }else{
                    	this.name = prjNameField.getText();
                    }

            		ArrayList checkData = new ArrayList();
            		ArrayList addDiagram = new ArrayList();
            		Object obj = tableViewer.getTree().getItems();
            		for( int i = 0 ; i <tableViewer.getTree().getItems().length; i++){
            			if(tableViewer.getTree().getItem(i).getChecked()){
            				checkData.add(tableViewer.getTree().getItem(i));
            			}
            		}
            		for(int i = 0; i < checkData.size(); i ++){
            			TreeItem item = (TreeItem)checkData.get(i);
            			for(int k = 0; k < item.getItems().length; k ++){
            				if(item.getItems()[k].getChecked()){
            					addDiagram.add(item.getItems()[k]);
            				}
            			}
            		}
            		for(int i = 0; i < addDiagram.size(); i ++){
            			TreeItem item = (TreeItem)addDiagram.get(i);
            			TreeParent s = (TreeParent)item.getData();
            			java.util.HashMap hashMap = new java.util.HashMap();
            			hashMap.put("name", s.getName());
            			hashMap.put("type", s.getType());;
            			modelType.add(hashMap);
            		}
                    if (addDiagram.size() <= 0) {
                    	modelType.clear();
                        MessageDialog dialog = new MessageDialog(this.getShell(), "경고", null, "다이어그램을 선택하지 않았습니다.", MessageDialog.ERROR,
                            new String[] { "확인" }, 0);
                        dialog.open();
                        return;
                    }
            		this.isOK=true;

            	}else if(openProjectList.getTable().getSelectionCount()>0){//PKY 08082201 S New 프로젝트 다이얼로그에서 List를 선택하지 않고 엔터누루면 에러발생 
            		try{
            		StructuredSelection iSelection = (StructuredSelection)openProjectList.getSelection();
            		String fileUrl=(String)iSelection.getFirstElement();
            		String fileName=fileUrl.substring(fileUrl.lastIndexOf("\\")+1,fileUrl.length());
            		String path=fileUrl.substring(0,fileUrl.lastIndexOf("\\"));
        			ProjectManager.getInstance().init();
        			ProjectManager.getInstance().setCurrentProjectName(fileName);
        			ProjectManager.getInstance().setCurrentProjectPath(path);


//        			lr.setNp((N3ModelParser)ModelManager.getInstance().getParser());
        			ModelManager.getInstance().parse(new File(ProjectManager.getInstance().getCurrentProject()));

        			ProjectManager.getInstance().addOpenProject(ProjectManager.getInstance().getCurrentProject());//PKY 08072401 S OpenList 개선
        			ProgressMonitorDialog pd = new ProgressMonitorDialog(ProjectManager.getInstance().window.getShell());
        			pd.run(true, true, (IRunnableWithProgress)ModelManager.getInstance().getParser());
        			ProjectManager.getInstance().getModelBrowser().refresh(ProjectManager.getInstance().getModelBrowser().getRoot());
        		   	String title = ProjectManager.getInstance().getCurrentProjectName().replaceAll(".nmdl", "");
        			if(ModelManager.getInstance().getRefStore().size()>0){
        				for(int i=0;i<ModelManager.getInstance().getRefStore().size();i++){
        					RefLinkModel rf = (RefLinkModel)ModelManager.getInstance().getRefStore().get(i);
        					rf.link();
        				}
        			}
        			//PKY 08062601 S 저장로드 반복시 다이어그램 오픈리스트에 다이어그램이 중복으로 나오는문제
        			if(ModelManager.getInstance().getOpenDiagarams()!=null)
        				if(ModelManager.getInstance().getOpenDiagarams().size()>0){
        					ListSelectionDialog lsd = new ListSelectionDialog(ProjectManager.getInstance().window.getShell(), ModelManager.getInstance().getOpenDiagarams(), new ArrayContentProvider(), new LabelProvider(), "ListSelectionDialog Message");
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
//        						ModelManager.getInstance().getOpenDiagarams().clear();
        					}
        				}
        			//PKY 08062601 E 저장로드 반복시 다이어그램 오픈리스트에 다이어그램이 중복으로 나오는문제
        			ProjectManager.getInstance().getConfigurer().setTitle(title+" - N3COM Constructor V3.0");
        			ModelManager.getInstance().init();


        			ModelManager.getInstance().getOpenDiagarams().clear();
        			ProjectManager.getInstance().getConsole().addMessage("Open "+ProjectManager.getInstance().getCurrentProject());
            		}catch(Exception e){
            			//PKY 08081101 S NewProject에서 해당 파일이 없을경우 에러 발생 수정
            			StructuredSelection iSelection = (StructuredSelection)openProjectList.getSelection();
            			String fileUrl=(String)iSelection.getFirstElement();
            			MessageBox dialog = new MessageBox(ProjectManager.getInstance().window.getShell(),SWT.ICON_WARNING);
            			dialog.setText("Message");
            			dialog.setMessage(N3Messages.DIALOG_OPEN_PROJECT_NOT_FILE_EXCEPTION+fileUrl);
            			dialog.open();

            			//PKY 08072401 E Open시 프로젝트 파일이 존재하지 않을경우 에러 발생문제 수정
            			MessageBox dialog1 = new MessageBox(ProjectManager.getInstance().window.getShell(),SWT.ICON_QUESTION|SWT.YES|SWT.NO);
            			dialog1.setText("Message");
            			dialog1.setMessage(N3Messages.DIALOG_OPEN_PROJECT_LIST_DELETE);
            			int i = dialog1.open();
            			if(i==64){
            				ProjectManager.getInstance().removeOpenProject(fileUrl);
            			}
            			ProjectManager.getInstance().getModelBrowser().refresh(ProjectManager.getInstance().getModelBrowser().getRoot());
            			e.printStackTrace();
            			//PKY 08081101 E NewProject에서 해당 파일이 없을경우 에러 발생 수정

            		}
            	}
            }
            	else{

                    if (prjNameField.getText().equals("")) {
                    	modelType.clear();
                        MessageDialog dialog = new MessageDialog(this.getShell(), "경고", null, "프로젝트 이름을 넣어 주세요.", MessageDialog.ERROR,
                            new String[] { "확인" }, 0);
                        dialog.open();
                        return;
                    }else{
                    	this.name = prjNameField.getText();
                    }

            		ArrayList checkData = new ArrayList();
            		ArrayList addDiagram = new ArrayList();
            		Object obj = tableViewer.getTree().getItems();
            		for( int i = 0 ; i <tableViewer.getTree().getItems().length; i++){
            			if(tableViewer.getTree().getItem(i).getChecked()){
            				checkData.add(tableViewer.getTree().getItem(i));
            			}
            		}
            		for(int i = 0; i < checkData.size(); i ++){
            			TreeItem item = (TreeItem)checkData.get(i);
            			for(int k = 0; k < item.getItems().length; k ++){
            				if(item.getItems()[k].getChecked()){
            					addDiagram.add(item.getItems()[k]);
            				}
            			}
            		}
            		for(int i = 0; i < addDiagram.size(); i ++){
            			TreeItem item = (TreeItem)addDiagram.get(i);
            			TreeParent s = (TreeParent)item.getData();
            			java.util.HashMap hashMap = new java.util.HashMap();
            			hashMap.put("name", s.getName());
            			hashMap.put("type", s.getType());;
            			modelType.add(hashMap);
            		}
                    if (addDiagram.size() <= 0) {
                    	modelType.clear();
                        MessageDialog dialog = new MessageDialog(this.getShell(), "경고", null, "다이어그램을 선택하지 않았습니다.", MessageDialog.ERROR,
                            new String[] { "확인" }, 0);
                        dialog.open();
                        return;
                    }
            		this.isOK=true;

            	
            	}
            }
          //PKY 08091806 E

            super.buttonPressed(buttonId);
        }
  
 
    }

    public String getName() {
        return this.name;
    }

    public java.util.ArrayList getModelType() {
        return this.modelType;
    }
    
    public void init(){
    	this.models.clear();
    	
    	//패키지 다이어그램
    	java.util.HashMap map = new java.util.HashMap();
		map.put("name", "Package");
    	map.put("type", "1");
    	map.put("desc", "");
    	models.add(map);
    	
    	//클래스 다이어그램
    	map = new java.util.HashMap();
		map.put("name", "Class");
    	map.put("type", "2");
    	map.put("desc", "");
    	models.add(map); 	
    	
    	//객체 다이어그램
    	map = new java.util.HashMap();
		map.put("name", "Object");
    	map.put("type", "3");
    	map.put("desc", "");
    	models.add(map);
    	
    	//합성 다이어그램
    	map = new java.util.HashMap();
		map.put("name", "Composite");
    	map.put("type", "4");
    	map.put("desc", "");
    	models.add(map);
    	
    	//컴포넌트 다이어그램
    	map = new java.util.HashMap();
		map.put("name", "Component");
    	map.put("type", "5");
    	map.put("desc", "");
    	models.add(map);
    	
    	//배치 다이어그램
    	map = new java.util.HashMap();
		map.put("name", "Deployment");
    	map.put("type", "6");
    	map.put("desc", "");
    	models.add(map);
    	
    	//유즈케이스  다이어그램
    	map = new java.util.HashMap();
		map.put("name", "Use Case");
    	map.put("type", "8");
    	map.put("desc", "");
    	models.add(map);
    	
    	//액티비티 다이어그램
    	map = new java.util.HashMap();
		map.put("name", "Activity");
    	map.put("type", "9");
    	map.put("desc", "");
    	models.add(map);
    	
    	//상태 다이어그램
    	map = new java.util.HashMap();
		map.put("name", "State");
    	map.put("type", "10");
    	map.put("desc", "");
    	models.add(map);
    	
    	//커뮤니케이션 다이어그램
    	map = new java.util.HashMap();
		map.put("name", "Communication");
    	map.put("type", "11");
    	map.put("desc", "");
    	models.add(map);
    	
    	//시퀀스 다이어그램
    	map = new java.util.HashMap();
		map.put("name", "Sequence");
    	map.put("type", "12");
    	map.put("desc", "");
    	models.add(map);
    	
    	//타이밍 다이어그램
    	map = new java.util.HashMap();
		map.put("name", "Timing");
    	map.put("type", "13");
    	map.put("desc", "");
    	models.add(map);
    	
    	//인터렉션 오버뷰 다이어그램
    	map = new java.util.HashMap();
		map.put("name", "Interaction Overview");
    	map.put("type", "14");
    	map.put("desc", "");
    	models.add(map);
    	
    	//행위 다이어그램
    	map = new java.util.HashMap();
		map.put("name", "Requirment");
    	map.put("type", "17");
    	map.put("desc", "");
    	models.add(map);
    }
    class TreeObject implements IAdaptable {
        private String name;
        private int type;
        private TreeParent parent;

        public TreeObject(String name) {
            this.name = name;
        }

        public TreeObject(String name, int p) {
            this.name = name;
            this.type = p;
        }

        public String getName() {
            return name;
        }

        public int getType() {
            return type;
        }

        public void setParent(TreeParent parent) {
            this.parent = parent;
        }

        public TreeParent getParent() {
            return parent;
        }

        public String toString() {
            return getName();
        }

        public Object getAdapter(Class key) {
            return null;
        }
    }


    class TreeParent extends TreeObject {
        private ArrayList children;

        public TreeParent(String name) {
            super(name);
            children = new ArrayList();
        }

        public TreeParent(String name, int p) {
            super(name, p);
            children = new ArrayList();
        }

        public void addChild(TreeObject child) {
            children.add(child);
            child.setParent(this);
        }

        public void removeChild(TreeObject child) {
            children.remove(child);
            child.setParent(null);
        }

        public TreeObject[] getChildren() {
            return (TreeObject[]) children.toArray(new TreeObject[children.size()]);
        }

        public boolean hasChildren() {
            return children.size() > 0;
        }
    }


    class ViewContentProvider implements IStructuredContentProvider, ITreeContentProvider {
        //		private TreeParent invisibleRoot;
        public void inputChanged(Viewer v, Object oldInput, Object newInput) {
        }

        public void dispose() {
        }

        public Object[] getElements(Object parent) {
            //			if (invisibleRoot==null){ initialize();
            //			return getChildren(invisibleRoot);
            //			}
            return getChildren(parent);
        }

        public Object getParent(Object child) {
            if (child instanceof TreeObject) {
                return ((TreeObject)child).getParent();
            }
            return null;
        }

        public Object[] getChildren(Object parent) {
            if (parent instanceof TreeParent) {
                return ((TreeParent)parent).getChildren();
            }
            return new Object[0];
        }

        public boolean hasChildren(Object parent) {
            if (parent instanceof TreeParent)
                return ((TreeParent)parent).hasChildren();
            return false;
        }
    }


    private TreeParent initialize() {
        TreeParent to1 = new TreeParent(N3Messages.DIALOG_PACKAGE, 1);
        TreeParent to2 = new TreeParent(N3Messages.PALETTE_TITLE_CLASS, 2);//2008040301 PKY S 
        TreeParent to3 = new TreeParent(N3Messages.DIALOG_OBJECT, 3);
        TreeParent to4 = new TreeParent(N3Messages.PALETTE_TITLE_COMPOSITE, 4);//2008040301 PKY S
        TreeParent to5 = new TreeParent(N3Messages.PALETTE_TITLE_COMPONENT, 5);//2008040301 PKY S
        TreeParent to6 = new TreeParent(N3Messages.PALETTE_TITLE_DEPLOYMENT, 6);//2008040301 PKY S
        TreeParent p1 = new TreeParent(N3Messages.DIALOG_STRUCTURAL, 7);
        p1.addChild(to1);
        p1.addChild(to2);
        p1.addChild(to3);
        p1.addChild(to4);
        p1.addChild(to5);
        p1.addChild(to6);
        TreeParent to1_1 = new TreeParent(N3Messages.PALETTE_TITLE_USECASE, 8);//2008040301 PKY S
        TreeParent to2_1 = new TreeParent(N3Messages.PALETTE_TITLE_ACTIVITY, 9);//2008040301 PKY S
        TreeParent to3_1 = new TreeParent(N3Messages.PALETTE_TITLE_STATE, 10);//2008040301 PKY S
        TreeParent to4_1 = new TreeParent(N3Messages.PALETTE_TITLE_COMMUNICATION, 11);//2008040301 PKY S
        TreeParent to5_1 = new TreeParent(N3Messages.PALETTE_TITLE_INTERACTION, 12);//2008040301 PKY S
        TreeParent to6_1 = new TreeParent(N3Messages.PALETTE_TITLE_TIMING, 13);//2008040301 PKY S
        TreeParent to7_1 = new TreeParent(N3Messages.DIALOG_INTERACTION_OVERVIEW, 14);
        TreeParent p2 = new TreeParent(N3Messages.DIALOG_BEHAVIORAL, 15);
        p2.addChild(to1_1);
        p2.addChild(to2_1);
        p2.addChild(to3_1);
        p2.addChild(to4_1);
        p2.addChild(to5_1);
        p2.addChild(to6_1);
        p2.addChild(to7_1);
        //20080721IJS
        TreeParent p3 = new TreeParent(N3Messages.DIALOG_EXTENDED, 16);
        //20080721IJS
        TreeParent to1_2 = new TreeParent(N3Messages.PALETTE_REQUIRMENT, 17);
        //20080721IJS
        p3.addChild(to1_2);
        
        TreeParent root = new TreeParent("Model");
        root.addChild(p1);
        root.addChild(p2);
        root.addChild(p3);
        return root;
    }

    class ViewLabelProvider extends LabelProvider {
        public String getText(Object obj) {
            return obj.toString();
        }

        public Image getImage(Object obj) {
            String imageKey = ISharedImages.IMG_OBJ_ELEMENT;
            if(obj instanceof TreeParent){
            	TreeParent tp = (TreeParent)obj;
            	return ProjectManager.getInstance().getDiagramImage(tp.getType());
            }

            return PlatformUI.getWorkbench().getSharedImages().getImage(imageKey);
        }
    }


    class NameSorter extends ViewerSorter {
    }


	public ArrayList getAddDiagram() {
		return addDiagram;
	}

	public void setAddDiagram(ArrayList addDiagram) {
		this.addDiagram = addDiagram;
	}
}
