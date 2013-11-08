package kr.co.n3soft.n3com.project.dialog;

import java.lang.reflect.InvocationTargetException;

import kr.co.n3soft.n3com.lang.N3Messages;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class ProjectSearchDialog  extends Dialog {
	Group projectSearchGroup;
	Combo findTextCombo;
	Label findLabel = null;
	Label findModelTypeLabel = null;
	Combo findModelTypeCombo;
	Group optionGroup;
	Label caseSensitiveLabel = null;
	Button caseSensitiveButton;

	Label wholeWordOnlyLabel = null;
	Button wholeWordOnlyButton;

	Label inDiagaramLabel = null;
	Button inDiagaramButton;

	Label inDescriptionLabel = null;
	Button inDescriptionButton;

	Label inModelLabel = null;
	Button inModelButton;



	private static final int RESET_ID = IDialogConstants.NO_TO_ALL_ID + 1;
	private static final int OK_ID = 0;
	private static final int CANCEL_ID = 1;

	public ProjectSearchDialog(Shell parentShell)
	{
		super(parentShell);
		this.setShellStyle(SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
	}

	protected Control createDialogArea(Composite parent) {
		Composite comp = (Composite)super.createDialogArea(parent);
		
		comp.getShell().setText(N3Messages.DIALOG_TEXT_TO_FIND);//20080717 KDI s		 
		comp.getShell().setImage(ProjectManager.getInstance().getImage(300));//20080724 KDI s					
		
		GridData data1 = new GridData(GridData.FILL_BOTH);
		data1.horizontalSpan = 2;
//		data1.heightHint = 150;
//		data1.widthHint = 400;
		
		//20080717 KDI s
		GridLayout layout = new GridLayout(2, false);
		layout.horizontalSpacing = 5;
		layout.marginBottom = 5;
		layout.marginTop = 5;
		layout.marginLeft = 5;
		layout.marginRight = 5;
		
		GridData data2 = new GridData(GridData.FILL_BOTH);
		
		//20080717 KDI e
		
		projectSearchGroup = new Group(comp, SWT.SHADOW_ETCHED_IN);
//		projectSearchGroup.setLayoutData(data1);
		projectSearchGroup.setLayout(layout); //20080717 KDI s
		Label findLabel = new Label(projectSearchGroup, SWT.RIGHT);
		findLabel.setText(N3Messages.DIALOG_TEXT_TO_FIND+": ");//2008040301 PKY S 
//		findLabel.setBounds(20, 20, 100, 20);
		findTextCombo = new Combo(projectSearchGroup,SWT.DROP_DOWN);
		findTextCombo.setLayoutData(data2);
//		findTextCombo.setBounds(20, 40, 350, 20);

		for(int i=0;i<ProjectManager.getInstance().getSearchText().size();i++){
			String str = (String)ProjectManager.getInstance().getSearchText().get(i);
			findTextCombo.add(str);
		}

		Group optionGroup = new Group(projectSearchGroup, SWT.SHADOW_ETCHED_IN);
		optionGroup.setText(N3Messages.DIALOG_OPTION);//PKY 08060201 S
		layout = new GridLayout(6, false);
		layout.horizontalSpacing = 5;
		layout.marginBottom = 5;
		layout.marginTop = 5;
		layout.marginLeft = 5;
		layout.marginRight = 5;
		optionGroup.setLayout(layout);
		optionGroup.setLayoutData(data1);
//		optionGroup.setBounds(10, 60, 380, 100);

		findModelTypeLabel =  new Label(optionGroup, SWT.RIGHT);
		findModelTypeLabel.setText(N3Messages.DIALOG_MODELTYPE);//2008040301 PKY S 
//		findModelTypeLabel.setBounds(10, 20, 80, 20);

		findModelTypeCombo = new Combo(optionGroup,SWT.DROP_DOWN);
//		findModelTypeCombo.setBounds(90, 20, 100, 20);
		findModelTypeCombo.add("All");
		findModelTypeCombo.add(N3Messages.PALETTE_USECASE);//2008040301 PKY S 
		findModelTypeCombo.add(N3Messages.PALETTE_ACTOR);//2008040301 PKY S 
		findModelTypeCombo.add("Class");
		findModelTypeCombo.add("Interface");
		findModelTypeCombo.add("Component");
		findModelTypeCombo.add("Activity");
		findModelTypeCombo.add("State");
		findModelTypeCombo.add("Action");
		findModelTypeCombo.add("Object");
		findModelTypeCombo.add("Node");
		findModelTypeCombo.select(0);
		
		Group type = new Group(optionGroup, SWT.SHADOW_ETCHED_IN);
		GridLayout layout2 = new GridLayout(4, false);
		type.setLayout(layout2);
		GridData data7 = new GridData(GridData.FILL_BOTH);
		data7.horizontalSpan = 6;
		type.setLayoutData(data7);
		
		data7 = new GridData(GridData.FILL_BOTH);
		data7.widthHint = 60;
		
		inModelLabel =  new Label(type, SWT.RIGHT);
		inModelLabel.setText(N3Messages.DIALOG_IN_MODEL+":");
		inModelButton = new Button(type,SWT.RADIO);
		inModelButton.setLayoutData(data7);

		inDiagaramLabel =  new Label(type, SWT.RIGHT);
		inDiagaramLabel.setText(N3Messages.DIALOG_IN_DIAGARAM+":");
		inDiagaramButton = new Button(type,SWT.RADIO);
		inDiagaramButton.setLayoutData(data7);
		
		
		data2 = new GridData(GridData.FILL_BOTH);
		data2.horizontalSpan = 5;
		findModelTypeCombo.setLayoutData(data2);
		
		type = new Group(optionGroup, SWT.SHADOW_ETCHED_IN);
		layout2 = new GridLayout(2, false);
		type.setLayout(layout2);
		data7 = new GridData(GridData.FILL_BOTH);
		data7.horizontalSpan = 6;
		type.setLayoutData(data7);

		caseSensitiveLabel =  new Label(type, SWT.RIGHT);
		caseSensitiveLabel.setText(N3Messages.DIALOG_CASE_SENSITIVE+":");
//		caseSensitiveLabel.setBounds(10, 50, 90, 20);

		caseSensitiveButton = new Button(type,SWT.CHECK);
		data7 = new GridData(GridData.FILL_BOTH);
//		data7.horizontalSpan = 5;
		caseSensitiveButton.setLayoutData(data7);
//		caseSensitiveButton.setBounds(120, 48, 20, 20);

		wholeWordOnlyLabel =  new Label(type, SWT.RIGHT);
		wholeWordOnlyLabel.setText(N3Messages.DIALOG_EXACT_MATCH+":");
//		wholeWordOnlyLabel.setBounds(150, 50, 80, 20);

		wholeWordOnlyButton = new Button(type,SWT.CHECK);
		wholeWordOnlyButton.setLayoutData(data7);
//		wholeWordOnlyButton.setBounds(240, 48, 20, 20);


		inDescriptionLabel =  new Label(type, SWT.RIGHT);
		inDescriptionLabel.setText(N3Messages.DIALOG_IN_DESCRIPTION+":");
//		inDescriptionLabel.setBounds(260, 50, 80, 20);

		inDescriptionButton = new Button(type,SWT.CHECK);
//		inDescriptionButton.setBounds(350, 48, 20, 20);





		try {

		}
		catch(Exception e){
			e.printStackTrace();
		}
		return comp;
	}

	protected void buttonPressed(int buttonId) {
		if (buttonId == RESET_ID) {

		}
		else {
			if (buttonId == this.OK_ID) {
				try{
					String text = this.findTextCombo.getText();
					String type = this.findModelTypeCombo.getText();
					boolean  isCaseSensitive = this.caseSensitiveButton.getSelection();
					boolean  isWwo = this.wholeWordOnlyButton.getSelection();
					boolean  isDesc = this.inDescriptionButton.getSelection();
					boolean isChk = false;
					if(this.inModelButton.getSelection()){
						ProjectManager.getInstance().setSearchModel(true);
						
						ProjectManager.getInstance().getModelBrowser().searchModel(type, text, isCaseSensitive,isWwo,isDesc);
						


						
					}else{
						ProjectManager.getInstance().setSearchModel(false);
						ProjectManager.getInstance().setisSearchDiagaramModel(true); //20080325 PKY S °Ë»ö 
						ProjectManager.getInstance().getModelBrowser().searchModel(type, text, isCaseSensitive,isWwo,isDesc);
					}
					for(int i=0;i<ProjectManager.getInstance().getSearchText().size();i++){
						String str = (String)ProjectManager.getInstance().getSearchText().get(i);
						if(str.equals(text)){
							isChk = true;
							break;
						}
					}
					if(!isChk)
						ProjectManager.getInstance().getSearchText().add(text);
				}
				catch(Exception e){
					e.printStackTrace();
				}

			}
			IRunnableWithProgress op = new IRunnableWithProgress() {
				public void run(IProgressMonitor monitor) throws InvocationTargetException {
					try {
//						doFinish(..., monitor); // operation
					} catch (Exception e) {
						throw new InvocationTargetException(e);
					} finally {
						monitor.done();
					}
				}
			};

			super.buttonPressed(buttonId);
		}
	}

}
