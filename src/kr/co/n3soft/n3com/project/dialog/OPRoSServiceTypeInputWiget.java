package kr.co.n3soft.n3com.project.dialog;

import java.util.ArrayList;
import java.util.List;

import kr.co.n3soft.n3com.OPRoSUtil;
import kr.co.n3soft.n3com.OPRoSStrings;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

/**
 * 서비스 타입 생성 다이얼로그 오른쪽에 들어가는 화면 생성 페이지
 * @author hclee
 *
 */
public class OPRoSServiceTypeInputWiget {
	
	public OPRoSServiceTypeInputDialogComposite parent;
	
	public Composite composite;
	
	public Text text_name;
	
	public OPRoSServiceTypeInputWiget(OPRoSServiceTypeInputDialogComposite parent) {
		this.parent = parent;
		this.composite = parent.getChangeContainer();
	}
	
	
	/**
	 * 서비스 그룹 루트가 선택되었을때 화면
	 * @param parent
	 */
	public Control[] createServiceRootGroup(final TreeItem item) {
		Label label = createLabel("Group Name of Service Calls : " +
				"Group Names of functions or methods that are accessed between service ports.", 50, SWT.BORDER | SWT.NONE | SWT.CENTER);
		
		Button btn_AddMethod = getButtonInstance(composite, false, 2);
		btn_AddMethod.setText("Add Method Type");
		btn_AddMethod.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				disposeWiget(composite);
				composite.layout(createServiceCreateGroup(item, true));
			}			
		});
		
		Button btn_Delete = getButtonInstance(composite, false, 1);
		btn_Delete.setText("Delete");
		btn_Delete.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				deleteTreeItem(item);
			}			
		});
		
		List<Control> list = new ArrayList<Control>();
		list.add(label);
		list.add(btn_AddMethod);
		list.add(btn_Delete);
		
		return list.toArray(new Control[list.size()]);
	}	
	
	/**
	 * 서비스 타입 설정화면
	 * @param parent
	 */
	public Control[] createServiceCreateGroup(final TreeItem item, final boolean isNew) {		
		Label label = createLabel("Service Type Name", 0, SWT.NONE);	
		
		final Text text_ServiceTypeName = new Text(composite, SWT.BORDER);
		GridData gd = new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1);
		text_ServiceTypeName.setLayoutData(gd);		
		
		Button btn_Previous = getButtonInstance(composite, true, 1);
		btn_Previous.setText("Previous");
		btn_Previous.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				disposeWiget(composite);
				if(isNew)
					composite.layout(createServiceRootGroup(item));
				else
					composite.layout(createMethodRootGroup(item));
			}
			
		});		
		
		
		Button btn_Ok = getButtonInstance(composite, true, 1);
		btn_Ok.setText("OK");
		btn_Ok.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String name = text_ServiceTypeName.getText();	
				disposeWiget(composite);
				if(isNew) {
					if(!isExistItem(parent.getRootItem(), name)){
						TreeItem item = new TreeItem(parent.getRootItem(),SWT.NONE);
						item.setText(name);							
						composite.layout(createServiceRootGroup(item));
					}
					else{
						OPRoSUtil.openMessageBox(OPRoSStrings.getString("DuplicationErrorMessage"),
								SWT.OK|SWT.ICON_WARNING);
					}
				}				
				else {
					item.setText(name);
					composite.layout(createMethodRootGroup(item));
					
				}
				item.setExpanded(true);		
			}
		});
		
		if(!isNew)
			text_ServiceTypeName.setText(item.getText());
		
		List<Control> list = new ArrayList<Control>();
		list.add(label);
		list.add(text_ServiceTypeName);
		list.add(btn_Previous);
		list.add(btn_Ok);
		
		return list.toArray(new Control[list.size()]);
	}
	
	/**
	 * 메소드를 선택하면 나오는 그룹
	 * @return
	 */
	public Control[] createMethodRootGroup(final TreeItem item) {	
		Label label = createLabel("Service Call Names : " +
				"Names of functions or methods that are accessed between service ports.", 50, SWT.BORDER | SWT.NONE | SWT.CENTER);
		
		Button btn_AddMethod = getButtonInstance(composite, false, 1);
		btn_AddMethod.setText("Add Method");
		btn_AddMethod.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				disposeWiget(composite);
				composite.layout(createMethodCreateGroup(item, true));
			}			
		});
		
		Button btn_Edit = getButtonInstance(composite, false, 1);
		btn_Edit.setText("Edit");
		btn_Edit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				disposeWiget(composite);
				composite.layout(createServiceCreateGroup(item, false));
			}			
		});
		
		Button btn_Delete = getButtonInstance(composite, false, 1);
		btn_Delete.setText("Delete");
		btn_Delete.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				deleteTreeItem(item);
			}			
		});
		
		List<Control> list = new ArrayList<Control>();
		list.add(label);
		list.add(btn_AddMethod);
		list.add(btn_Edit);
		list.add(btn_Delete);
		
		return list.toArray(new Control[list.size()]);
	}
	
	/**
	 * 메소드를 선택하면 나오는 그룹
	 * @return
	 */
	public Control[] createMethodCreateGroup(final TreeItem item, final boolean isNew) {	
		Label label = createLabel("Service Name", 0, SWT.NONE);
		
		final Text text_ServiceTypeName = new Text(composite, SWT.BORDER);
		GridData gd = new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1);
		text_ServiceTypeName.setLayoutData(gd);
		
		Label label2 = createLabel("Return Data Type", 0, SWT.NONE);
		final Combo typeCombo = new Combo(composite, SWT.NONE);
		gd = new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1);
		typeCombo.setLayoutData(gd);
		typeCombo.setItems(OPRoSUtil.dataNotBoostTypes);
		for(String type : parent.getTypeItem(true)){
			typeCombo.add(type, 0);
		}
		typeCombo.select(0);
		
		Label label3 = createLabel("Call Type", 0, SWT.NONE);
		final Combo callTypeCombo = new Combo(composite, SWT.NONE | SWT.READ_ONLY);
		gd = new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1);
		callTypeCombo.setLayoutData(gd);		
		
		typeCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {				
				String strDataType = ((Combo)e.getSource()).getText();
				if(strDataType.compareTo("void")==0){
					if(callTypeCombo.getItemCount()==1){
						callTypeCombo.add("nonblocking");
					}
				}else{
					if(callTypeCombo.getItemCount()==2){
						callTypeCombo.remove(1);
					}
				}
			}			
		});
				
		Button btn_Previous = getButtonInstance(composite, true, 1);
		btn_Previous.setText("Previous");
		btn_Previous.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				disposeWiget(composite);
				if(isNew)
					composite.layout(createMethodRootGroup(item));
				else 
					composite.layout(createParameterRootGroup(item));
			}
			
		});
		
		Button btn_Ok = getButtonInstance(composite, true, 1);
		btn_Ok.setText("OK");
		btn_Ok.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String name = text_ServiceTypeName.getText()+"():"+typeCombo.getText()+":"+callTypeCombo.getText();
				disposeWiget(composite);
				if(isNew) {
					TreeItem newItem = new TreeItem(item, SWT.NONE);
					newItem.setText(name);
					composite.layout(createMethodRootGroup(item));
				}
				else {
					item.setText(name);
					composite.layout(createParameterRootGroup(item));
				}
				item.setExpanded(true);	
			}
		});
		
		
		
		if(!isNew) {
			String[] str = item.getText().split(":");
			text_ServiceTypeName.setText(str[0].replace("()", ""));
			for(int i=0; i<typeCombo.getItems().length; i++) {
				String typeName = typeCombo.getItems()[i];
				if(typeName.equals(str[1])){
					typeCombo.select(i);
					break;
				}
			}			
		}
		
		if(typeCombo.getText().equals("void"))
			callTypeCombo.setItems(new String[]{"blocking","nonblocking"});
		else
			callTypeCombo.setItems(new String[]{"blocking"});
		callTypeCombo.select(0);
		
		if(!isNew) {
			String[] str = item.getText().split(":");
			for(int i=0; i<callTypeCombo.getItems().length; i++) {
				String callType = callTypeCombo.getItems()[i];
				if(callType.equals(str[2])){
					callTypeCombo.select(i);
					break;
				}
			}
		}
		
		List<Control> list = new ArrayList<Control>();
		list.add(label);
		list.add(text_ServiceTypeName);
		list.add(label2);
		list.add(typeCombo);
		list.add(label3);
		list.add(callTypeCombo);
		
		return list.toArray(new Control[list.size()]);
	}
	
	/**
	 * 메소드를 선택하면 나오는 그룹
	 * @return
	 */
	public Control[] createParameterRootGroup(final TreeItem item) {	
		Label label = createLabel("Parameter Names of Service Call : " +
				"Parameters of the function or the method.", 50, SWT.BORDER | SWT.NONE | SWT.CENTER);
		
		Button btn_AddMethod = getButtonInstance(composite, false, 1);
		//btn_AddMethod.setText("Add method\nParameter");
		btn_AddMethod.setText("Add Parameter");
		btn_AddMethod.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				disposeWiget(composite);
				composite.layout(createParameterCreateGroup(item, true));
			}			
		});
		
		Button btn_Edit = getButtonInstance(composite, false, 1);
		btn_Edit.setText("Edit");
		btn_Edit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				disposeWiget(composite);
				composite.layout(createMethodCreateGroup(item, false));
			}			
		});
		
		Button btn_Delete = getButtonInstance(composite, false, 1);
		btn_Delete.setText("Delete");
		btn_Delete.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				deleteTreeItem(item);
			}			
		});
		
		List<Control> list = new ArrayList<Control>();
		list.add(label);
		list.add(btn_AddMethod);
		list.add(btn_Edit);
		list.add(btn_Delete);
		
		return list.toArray(new Control[list.size()]);
	}	
	
	/**
	 * 메소드를 선택하면 나오는 그룹
	 * @return
	 */
	public Control[] createParameterCreateGroup(final TreeItem item, final boolean isNew) {	
		Label label = createLabel("Parameter Name", 0, SWT.NONE);
		
		final Text text_ServiceTypeName = new Text(composite, SWT.BORDER);
		GridData gd = new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1);
		text_ServiceTypeName.setLayoutData(gd);
		
		Label label2 = createLabel("Parameter Data Type", 0, SWT.NONE);
		final Combo typeCombo = new Combo(composite, SWT.NONE);
		gd = new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1);
		typeCombo.setLayoutData(gd);
		typeCombo.setItems(OPRoSUtil.dataNotBoostTypes);
		for(String type : parent.getTypeItem(false)){
			typeCombo.add(type, 0);
		}
		typeCombo.select(0);
		
		Button btn_Previous = getButtonInstance(composite, true, 1);
		btn_Previous.setText("Previous");
		btn_Previous.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				disposeWiget(composite);
				if(isNew)
					composite.layout(createParameterRootGroup(item));
				else
					composite.layout(createParameterGroup(item));
			}			
		});
		
		Button btn_Ok = getButtonInstance(composite, true, 1);
		btn_Ok.setText("OK");
		btn_Ok.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String name = text_ServiceTypeName.getText()+":"+typeCombo.getText();
				disposeWiget(composite);
				if(isNew) {
					TreeItem newItem = new TreeItem(item, SWT.NONE);
					newItem.setText(name);
					composite.layout(createParameterRootGroup(item));
				}
				else {
					item.setText(name);
					composite.layout(createParameterGroup(item));
				}
				item.setExpanded(true);
			}			
		});
		
		
		if(!isNew) {
			String[] str = item.getText().split(":");
			text_ServiceTypeName.setText(str[0]);
			for(int i=0; i<typeCombo.getItems().length; i++) {
				String typeName = typeCombo.getItems()[i];
				if(typeName.equals(str[1])){
					typeCombo.select(i);
					break;
				}
			}
		}	
		
		List<Control> list = new ArrayList<Control>();
		list.add(label);
		list.add(text_ServiceTypeName);
		list.add(label2);
		list.add(typeCombo);
		
		return list.toArray(new Control[list.size()]);
	}
	
	/**
	 * 메소드를 선택하면 나오는 그룹
	 * @return
	 */
	public Control[] createParameterGroup(final TreeItem item) {	
		Label label = createLabel("Parameter of the function or the method.", 50, SWT.BORDER | SWT.NONE | SWT.CENTER);
		
		Button btn_Edit = getButtonInstance(composite, false, 1);
		btn_Edit.setText("Edit");
		btn_Edit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				disposeWiget(composite);
				composite.layout(createParameterCreateGroup(item, false));
			}			
		});
		
		Button btn_Delete = getButtonInstance(composite, false, 1);
		btn_Delete.setText("Delete");
		btn_Delete.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				deleteTreeItem(item);
			}			
		});
		
		List<Control> list = new ArrayList<Control>();
		list.add(label);
		list.add(btn_Edit);
		list.add(btn_Delete);
		
		return list.toArray(new Control[list.size()]);
	}	
	
	/**
	 * 라벨을 생성한다.
	 * @param text
	 * @param height
	 * @param style
	 * @return
	 */
	private Label createLabel(String text, int height, int style){
		Label label = new Label(composite, style);
		label.setText(text);
		GridData gd = new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1);
		gd.widthHint = 250;
		if(height != 0)
			gd.heightHint = height;
		label.setLayoutData(gd);
		return label;
	}
	
	
	
	/**
	 * 버튼 인스턴스를 리턴한다.
	 * @param parent
	 * @param isBottom
	 * @return
	 */
	public Button getButtonInstance(Composite composite, boolean isBottom, int index) {
		Button button = new Button(composite, SWT.PUSH | SWT.CENTER | SWT.MULTI);
		GridData gd = new GridData(GridData.END, SWT.BOTTOM, true, isBottom, index, 5);
		gd.widthHint = 120;
		//gd.heightHint = 40;
		button.setLayoutData(gd);
		return button;
	}
	
	/**
	 * 선택된 트리아이템을 삭제한다.
	 * @param item
	 */
	public void deleteTreeItem(TreeItem item) {
		if(item != null){
			if(!item.equals(parent.rootItem)){
				item.removeAll();
				item.dispose();
			}
			else{
				item.removeAll();
			}
		}
	}
	
	/**
	 * 해당 트리에 추가하려는 이름의 트리가 존재하는지 검사
	 * @param parent
	 * @param itemName
	 * @return
	 */
	public boolean isExistItem(TreeItem parent, String itemName){
		int cnt = parent.getItemCount();
		for(int i=0;i<cnt;i++){
			if(parent.getItem(i).getText().compareTo(itemName)==0)
				return true;
		}
		return false;
	}
	
	/**
	 * 위젯에 포함된 하위 위젯들을 삭제한다.
	 * @param parent
	 */
	public void disposeWiget(Composite parent){
		for(Control control : parent.getChildren()) {
			control.dispose();
		}
	}
	
	
}
