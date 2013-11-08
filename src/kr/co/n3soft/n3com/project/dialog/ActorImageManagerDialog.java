package kr.co.n3soft.n3com.project.dialog;

import java.util.ArrayList;

import kr.co.n3soft.n3com.lang.N3Messages;
import kr.co.n3soft.n3com.model.activity.BehaviorActivityTableItem;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.umlclass.AttributeEditableTableItem;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.DrillDownAdapter;

public class ActorImageManagerDialog  extends Dialog {
	private static final int RESET_ID = IDialogConstants.NO_TO_ALL_ID + 1;
	private static final int OK_ID = 0;
	private static final int CANCEL_ID = 1;
	private Text usernameField;
	private Text passwordField;
	//	private TextArea descriptionField;
	private DrillDownAdapter drillDownAdapter;
	private String diagramName = "";
	private int diagramType = -1;
	public boolean isOK = false;
	private Text diagramNameField;
	ListViewer selectFrom = null;
	ListViewer diagramTypes = null;
	Group type;
	Group description;
	StringBuffer descClass;
	Label descriptionField = null;
	//	private TreeViewer viewer;
	private TableViewer tableViewer;
	private StyledText styleField;
	private Combo typeList;
	UMLModel selectedModel = null;
	int attrCount = 0;
	private String name="";
	BehaviorActivityTableItem selectedBehaviorActivityTableItem;
	Text imageUrl=null;
	private boolean checkBoolean=true;
	private boolean checkOkButton =false;
	private boolean insertNameType = false;
	Button oldActor=null;//PKY 08081101 S Actor 이미지 넣은 후 다시 이미지 제거하려면 삭제해야되는 불편한 문제 수정
	Image image=null;
	Label img =null;
	boolean oldActorCheck=false;//PKY 08081101 S Actor 이미지 넣은 후 다시 이미지 제거하려면 삭제해야되는 불편한 문제 수정

	
	public java.util.ArrayList attributes = new ArrayList();


	private static final Object[] CONTENT = new Object[] {
		new AttributeEditableTableItem(new Integer(0), "ddd", new Integer(0), "ff"),
		new AttributeEditableTableItem(new Integer(1), "sss", new Integer(0), "dfd")
	};

	private static final String[] VALUE_SET = new String[] {
		"xxx", "yyy", "zzz"
	};

	public static final String[] SCOPE_SET = new String[] {
		"public", "protected", "private"
	};

	public static final String[] SCOPEA_SET = new String[] {
		"+", "#", "-"
	};

	public static final String[] TYPE_SET = new String[] {
		"int", "boolean", "char", "double", "float", "long", "short"
	};
	//PKY 08071601 S State  Operation 표시 다른것 수정
	public static final String[] STATE_TYPE_SET = new String[] {
		"do", "entry", "exit"
	};
	//PKY 08071601 E State  Operation 표시 다른것 수정

	private static final String NAME_PROPERTY = N3Messages.DIALOG_FILE_URL;//2008040302 PKY S 
	private static final String TYPE_PROPERTY = N3Messages.POPUP_TYPE;//2008040302 PKY S 
	private static final String SCOPE_PROPERTY = N3Messages.PALETTE_PACKAGE;//2008040301 PKY S 
	private static final String INITVALUE_PROPERTY = "부모노드";

	public ActorImageManagerDialog(Shell parentShell) {
		super(parentShell);
		//		ProjectManager.getInstance();
		this.setShellStyle(SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.RESIZE);
		
		this.attributes =ProjectManager.getInstance().getActorImageList(); 
		
	}

	public BehaviorActivityTableItem getBehaviorActivityTableItem() {
		return this.selectedBehaviorActivityTableItem;
	}

	public void initDesc() {
	}

	protected Control createDialogArea(Composite parent) {
		final Composite comp = (Composite)super.createDialogArea(parent);
		ArrayList Models = new ArrayList();
		//2008040702PKY S

		comp.getShell().setText(N3Messages.DIALOG_IMAGE);//20080717 KDI s			 
		comp.getShell().setImage(ProjectManager.getInstance().getImage(325));//20080724 KDI s


		//2008040702PKY E

		try {

			GridLayout gridLayout = (GridLayout)comp.getLayout();
			gridLayout.numColumns = 2;

			GridLayout gridLayout2 = new GridLayout();
			
			Group group = new Group(comp, SWT.NONE);
			group.setLayout(gridLayout2);
			group.setText(N3Messages.DIALOG_IMG_PREVIEW);
			
			GridData data = new GridData(GridData.FILL_BOTH);
			data.heightHint = 400;
			data.widthHint = 300;
			group.setLayoutData(data);
			
			img = new Label(group, SWT.NONE);
			data = new GridData(GridData.FILL_BOTH);
//			data.heightHint = 380;
//			data.widthHint = 280;
			img.setLayoutData(data);
			
			Group group2 = new Group(comp, SWT.NONE);
			gridLayout2 = new GridLayout(3, false);
			
			group2.setLayout(gridLayout2);
			data = new GridData(GridData.FILL_BOTH);
			data.heightHint = 400;
			data.widthHint = 300;
			group2.setLayoutData(data);
			
			Label name = new Label(group2, SWT.NONE);
			name.setText(N3Messages.DIALOG_FILE_URL);
			
			final Text text = new Text(group2,SWT.SINGLE| SWT.BORDER);
			data = new GridData();
			data.widthHint = 178;

			data.verticalAlignment = GridData.CENTER;
			text.setLayoutData(data);
			
			Button button = new Button(group2, SWT.NONE);
			button.setText(N3Messages.DIALOG_OPEN);
			button.addSelectionListener(new SelectionListener() {

				public void widgetSelected(SelectionEvent e)
				{
					
			    	FileDialog dialog = new FileDialog(ProjectManager.getInstance().window.getShell(),SWT.SINGLE);
			    	dialog.setFilterNames(new String[]{"JPG Files","GIF Files","BMP Files"});
			    	dialog.setFilterExtensions(new String[]{"*.jpg","*.gif","*.bmp"});
//			    	dialog.setFilterPath("C:\\Documents and Settings\\admin\\workspace02\\uuu\\bin\\kr\\co\\n3soft\\n3com\\model\\usecase\\icons");
					String d = dialog.open();
					if(d!=null){
						text.setText(d);
						ProjectManager.getInstance().addActorImageList(d);
						tableViewer.setInput(attributes.toArray());
						tableViewer.refresh();
						tableViewer.getTable().select(tableViewer.getTable().getItemCount()-1);
						img.setText("이미지를 불러오고 있습니다");
						img.setImage(ProjectManager.getInstance().getActorImageDialog(d));
						oldActor.setSelection(false);//PKY 08081101 S Actor 이미지 넣은 후 다시 이미지 제거하려면 삭제해야되는 불편한 문제 수정
						oldActorCheck=false;//PKY 08081101 S Actor 이미지 넣은 후 다시 이미지 제거하려면 삭제해야되는 불편한 문제 수정

					}else{
						text.setText("");
					}
					
				}

				public void widgetDefaultSelected(SelectionEvent e)
				{
				}

			});
		
//			canvas.addPaintListener(new PaintListener() {
//				public void paintControl(PaintEvent e) {
//					if(image!=null){
//					GC gc = e.gc;
//					Rectangle bounds = image.getBounds();
//					int w = bounds.width;
//					int h = bounds.height;
//					gc.drawText("ccc", 0, 0, 10);
//					}
//				}
//			});
			Label name2 = new Label(group2, SWT.NONE);
			name2.setText(N3Messages.DIALOG_FILE_LIST);
			data = new GridData(GridData.BEGINNING);
			data.horizontalSpan = 3;
			name2.setLayoutData(data);
			
			Table table = new Table(group2, SWT.FULL_SELECTION );
			data = new GridData(GridData.FILL_BOTH);
			data.horizontalSpan = 3;
			
			table.setLayoutData(data);

			tableViewer = buildAndLayoutTable(table);
			attachContentProvider(tableViewer);
			attachLabelProvider(tableViewer);
			
			tableViewer.setInput(attributes.toArray());
			
			tableViewer.addSelectionChangedListener(new ISelectionChangedListener(){
				public void selectionChanged(SelectionChangedEvent event){
					IStructuredSelection tableItem= (IStructuredSelection) tableViewer.getSelection();
					img.setText("이미지를 불러오고 있습니다");
					img.setImage(ProjectManager.getInstance().getActorImageDialog((String)tableItem.getFirstElement()));
					oldActor.setSelection(false);//PKY 08081101 S Actor 이미지 넣은 후 다시 이미지 제거하려면 삭제해야되는 불편한 문제 수정
					oldActorCheck=false;//PKY 08081101 S Actor 이미지 넣은 후 다시 이미지 제거하려면 삭제해야되는 불편한 문제 수정
				}
			});
			
			//PKY 08081101 S Actor 이미지 넣은 후 다시 이미지 제거하려면 삭제해야되는 불편한 문제 수정
			oldActor = new Button (group2,SWT.CHECK);
			data = new GridData(GridData.BEGINNING);
			data.horizontalSpan = 3;
			oldActor.setLayoutData(data);
			oldActor.setText(N3Messages.DIALOG_DELETE_ACTOR_IMAGE);
			oldActor.addSelectionListener(new SelectionListener(){
				public void widgetSelected(SelectionEvent e){
					if(oldActor.getSelection()){
					oldActorCheck=true;
					tableViewer.getTable().setSelection(-1);
					img.setText("");
					}else{
						oldActorCheck=false;
					}
				}
				public void widgetDefaultSelected(SelectionEvent e){
					System.out.print("");	
				}
			});
			//PKY 08081101 E Actor 이미지 넣은 후 다시 이미지 제거하려면 삭제해야되는 불편한 문제 수정

			
			MenuManager popupMenu = new MenuManager();			
			IAction deleteRowAction = new deleteRowAction();
			popupMenu.add(deleteRowAction);
			
			Menu menu = popupMenu.createContextMenu(table);
			table.setMenu(menu);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return comp;
	}

	protected void createButtonsForButtonBar(Composite parent) {
		super.createButtonsForButtonBar(parent);
	}

	protected void buttonPressed(int buttonId) {
		if (buttonId == RESET_ID) {
			usernameField.setText("");
			passwordField.setText("");
		}
		else {
			if (buttonId == this.OK_ID) {
				this.setCheckOkButton(true);
				ProjectManager.getInstance().writeConfig();//PKY 08081101 S N3Com 프로퍼티를 오픈 후 OK버튼을 누루면 내용이 저장되도록 기존에는 정상적인 종료를 했을때만 프로그램 내용이 저장되도록 되어있었음
				//PKY 08081101 S Actor 이미지 넣은 후 다시 이미지 제거하려면 삭제해야되는 불편한 문제 수정
				if(!oldActorCheck){
					IStructuredSelection sel = (IStructuredSelection)tableViewer.getSelection();
					name = (String) sel.getFirstElement();
				}else
					name="";
				//PKY 08081101 E Actor 이미지 넣은 후 다시 이미지 제거하려면 삭제해야되는 불편한 문제 수정




			}
			super.buttonPressed(buttonId);

		}
	}

	public UMLModel getSelectedUMLModel() {
		if (selectedBehaviorActivityTableItem != null)
			return this.selectedBehaviorActivityTableItem.refModel;
		else
			return null;
	}



	private void attachLabelProvider(TableViewer viewer) {
		viewer.setLabelProvider(
				new ITableLabelProvider() {
					public Image getColumnImage(Object element, int columnIndex) {
						return null;
					}
					public String getColumnText(Object element, int columnIndex) {
						String reqId="";
						reqId = ((String)element);


						switch (columnIndex) {						
						case 0:
							return reqId;
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

	private TableViewer buildAndLayoutTable(final Table table) {
		TableViewer tableViewer = new TableViewer(table);
		TableLayout layout = new TableLayout();
		layout.addColumnData(new ColumnWeightData(250	, 250, true));
		table.setLayout(layout);
		TableColumn typeColumn = new TableColumn(table, SWT.LEFT);
		typeColumn.setText(this.NAME_PROPERTY);

		table.setHeaderVisible(true);
		return tableViewer;
	}

	public TableViewer getTableViewer() {
		return tableViewer;
	}

	public void setTableViewer(TableViewer tableViewer) {
		this.tableViewer = tableViewer;
	}

	public UMLModel getSelectedModel() {
		return selectedModel;
	}

	public void setSelectedModel(UMLModel selectedModel) {
		this.selectedModel = selectedModel;
	}

	public boolean isCheckBoolean() {
		return checkBoolean;
	}

	public void setCheckBoolean(boolean checkBoolean) {
		this.checkBoolean = checkBoolean;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isCheckOkButton() {
		return checkOkButton;
	}

	public void setCheckOkButton(boolean checkOkButton) {
		this.checkOkButton = checkOkButton;
	}
	private class deleteRowAction extends Action {
		public deleteRowAction() {
			super(N3Messages.POPUP_DELETE);//2008040302 PKY S 
		}

		public void run() {
			IStructuredSelection tableItem= (IStructuredSelection) tableViewer.getSelection();
			for(int i =0 ; i < ProjectManager.getInstance().getActorImageList().size(); i++ ){
				if(((String)ProjectManager.getInstance().getActorImageList().get(i)).equals(((String)tableItem.getFirstElement()))){
						ProjectManager.getInstance().getActorImageList().remove(i);
//						if(i!=0){
							img.setText("");
//						}
						i=ProjectManager.getInstance().getActorImageList().size();
						
				}

			}
			tableViewer.remove(tableItem.getFirstElement());
//			tableViewer.setInput(attributes.toArray());
//			tableViewer.refresh();
		}
	}
}

