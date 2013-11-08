package kr.co.n3soft.n3com.project.dialog;

import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;


public class CInputDialog extends Dialog {

	// dialog °á°ú°ª
	boolean result = false;
	int returnCode = -1;
    
	public boolean isBlockView = true;

	public int getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(int returnCode) {
		this.returnCode = returnCode;
	}

	private String fileName = "";
	private String orginName = "";
	private String title = "";
	private boolean isBlock = false;
	public boolean isBlock() {
		return isBlock;
	}

	public void setBlock(boolean isBlock) {
		this.isBlock = isBlock;
	}

	private int comboIndex = -1;
	public int getComboIndex() {
		return comboIndex;
	}

	public void setComboIndex(int comboIndex) {
		this.comboIndex = comboIndex;
	}

	private String description = "";

	private Text fileNameText;
//	private Text titleText;
//	private Text descriptionText;
	Combo combo;
	
	Button blockChk;
	
//	final Combo combo = new Combo(shell, SWT.NULL);

	
	public Button getBlockChk() {
		return blockChk;
	}

	public void setBlockChk(Button blockChk) {
		this.blockChk = blockChk;
	}

	Shell shell;
	// /////////////////////////////////////////////////////

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	// /////////////////////////////////////////////////////

	public CInputDialog(Shell parent) {
		this(parent,SWT.NULL);
	}

	public CInputDialog(Shell parent, int style) {
		super(parent, style);
	}

	public boolean open() {
		shell = new Shell(getParent(), getStyle());
		shell.setText(getText());
		createContents(shell);
		shell.pack();
		shell.open();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	private void createContents(final Shell shell) {

		shell.setText("New OPRoS Component");
		shell.setLayout(new GridLayout(2, true));

		// //////////////////////////////
		// fileName
		Label fileNameLabel = new Label(shell, SWT.NONE);
		fileNameLabel.setText("Component Name");
		fileNameLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		fileNameText = new Text(shell, SWT.BORDER);
		fileNameText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		((GridData) fileNameText.getLayoutData()).widthHint = 200;

		// //////////////////////////////
		// title
		Label titleLabel = new Label(shell, SWT.NONE);
		titleLabel.setText("Selecting Language");
		titleLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		combo = new  Combo(shell, SWT.BORDER);
		combo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		String[] languages = new String[]{"MinGW C++", "MSVC C++"};

		for(int i=0; i<languages.length; i++)
		      combo.add(languages[i]);
		combo.select(1);
//		combo.set
		
//		Label blockLabel = new Label(shell, SWT.NONE);
//		blockLabel.setText("BlockEditor Use");
//		blockLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
//		if(this.isBlockView){
//			Label blaockLabel = new Label(shell, SWT.NONE);
//			blaockLabel.setText("BlockEditor Use");
//			blaockLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
//			blockChk = new Button(shell,SWT.CHECK);
////			blockChk.setText("BlockEditor Use");
//			blockChk.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
////			blockChk.get
//			blockChk.addSelectionListener(new SelectionAdapter() {
//				public void widgetSelected(SelectionEvent event) {
//					System.out.println("ddddd======>"+blockChk.getSelection());
//					isBlock = blockChk.getSelection();
////					if(isBlock){
////						orginName = fileNameText.getText();
////						fileNameText.append("_bl");
////					}
////					else{
////						fileNameText.setText(orginName);
////					}
//					ProjectManager.getInstance().setUseBlock(isBlock);
////					result = true;
////					fileName = fileNameText.getText();
////				
////					comboIndex = combo.getSelectionIndex();
////					returnCode = 0;
////					shell.close();
//				}
//			});
//		}
		// //////////////////////////////

		Button ok = new Button(shell, SWT.PUSH);
		ok.setText("OK");
		ok.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		ok.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				result = true;
				fileName = fileNameText.getText();
				if("".equals(fileName) || fileName ==null){
					MessageBox dialog = new MessageBox(shell,SWT.ERROR);
					dialog.setText("Message");
					dialog.setMessage("no component name");
					dialog.open();
					return;
				}
			
				comboIndex = combo.getSelectionIndex();
				returnCode = 0;
				shell.close();
			}
		});

		Button cancel = new Button(shell, SWT.PUSH);
		cancel.setText("Cancel");
		cancel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		cancel.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				result = false;

				shell.close();
			}
		});

		shell.setDefaultButton(ok);

	}
}
