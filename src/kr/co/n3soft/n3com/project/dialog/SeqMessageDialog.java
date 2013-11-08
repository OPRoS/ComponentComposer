package kr.co.n3soft.n3com.project.dialog;

import kr.co.n3soft.n3com.lang.N3Messages;
import kr.co.n3soft.n3com.model.communication.MessageCommunicationModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
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
import org.eclipse.swt.widgets.Text;

//20080721IJS 전체 변경
public class SeqMessageDialog extends Dialog {
	private static final int RESET_ID = 
		IDialogConstants.NO_TO_ALL_ID + 1;
	private static final int OK_ID = 0;
	private static final int CANCEL_ID = 1;

	Group signature;
	Group sequence_Expression;
	Group control_Flow_Type;
	//signature
	Label message = null;
	Combo comboMessage;
	Button buttonOperation;
	Label parameters = null;
	Text textParameters;
	Label isReturn = null;
	Combo comboReturn;
	//end
	//Sequence Expression
	Label condition = null;
	Label constraint = null;
	Text textCondition;
	Text textConstraint;
	//end
	//Control_Flow_Type
	Label Synch = null;
	Label Kind = null;
	Label Lifecycle = null;
	Combo comboSynch;
	Combo comboKind;
	Combo comboLifecycle;
	//end


	MessageCommunicationModel mcm = null;
	MessageCommunicationModel mcmCondition = null;
	MessageCommunicationModel mcmConstraint = null;
	
	public SeqMessageDialog(Shell parentShell) {
		super(parentShell);


		this.setShellStyle(SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.RESIZE);

	}
	protected Control createDialogArea(Composite parent) {
		Composite comp = (Composite)super.createDialogArea(parent);
		
		comp.getShell().setText(N3Messages.POPUP_EDIT_MESSAGE);//20080717 KDI s			 
		comp.getShell().setImage(ProjectManager.getInstance().getImage(325));//20080724 KDI s
		
		GridLayout layout = (GridLayout)comp.getLayout();
		layout.numColumns = 2;

		GridData data1 = new GridData(GridData.FILL_BOTH);
		data1.horizontalSpan = 2;
//		data1.heightHint = 70;
		data1.widthHint = 500;
		GridData data2 = new GridData(GridData.FILL_BOTH);
		data2.horizontalSpan = 2;
//		data2.heightHint = 70;
		data2.widthHint =500;
		GridData data3 = new GridData(GridData.FILL_BOTH);
		data3.horizontalSpan = 2;
//		data3.heightHint = 100;
		data3.widthHint = 500;
		
		//20080717 KDI s	
		GridLayout glayout = new GridLayout(2, false);
		glayout.horizontalSpacing = 5;
		glayout.marginLeft = 5;
		glayout.marginRight = 5;
		glayout.marginBottom = 5;
		glayout.marginTop = 5;
		
		GridData data4 = new GridData(GridData.FILL_BOTH);
//		data4.widthHint = 395;
		//20080717 KDI e	
			
		signature = new Group(comp, SWT.SHADOW_ETCHED_IN);
		signature.setText(N3Messages.DIALOG_SIGNATURE);
		signature.setLayoutData(data1);
		signature.setLayout(glayout);
		
		message  = new Label(signature, SWT.RIGHT);
		message.setText(N3Messages.DIALOG_MESSAGE+": ");
//		message.setBounds(20, 20, 60, 20);
		comboMessage = new Combo(signature,SWT.DROP_DOWN);
		comboMessage.setLayoutData(data4);
//		comboMessage.setBounds(90, 20, 400, 20);
//		parameters = new Label(signature, SWT.LEFT);
//		parameters.setText("Parameters:");
//		parameters.setBounds(20, 50, 60,20);
//		textParameters = new Text(signature, SWT.SINGLE | SWT.BORDER);
//		textParameters.setBounds(90, 50, 400, 20);

		isReturn  = new Label(signature, SWT.RIGHT);
		isReturn.setText(N3Messages.DIALOG_SIGNATURE+": ");
//		isReturn.setBounds(20, 50, 60, 20);

		comboReturn = new Combo(signature,SWT.READ_ONLY);
//		comboReturn.setBounds(90, 50, 100, 20);
		comboReturn.add("false");
		comboReturn.add("true");
		comboReturn.select(0);
		data4 = new GridData(GridData.BEGINNING);
		data4.widthHint = 100;
		comboReturn.setLayoutData(data4);


		sequence_Expression = new Group(comp, SWT.SHADOW_ETCHED_IN);
		sequence_Expression.setText(N3Messages.DIALOG_SEQUENCE_EXPRESSION);
		sequence_Expression.setLayoutData(data2);
		sequence_Expression.setLayout(glayout);

		condition  = new Label(sequence_Expression, SWT.RIGHT);
		condition.setText(N3Messages.DIALOG_CONDITION+": ");
//		condition.setBounds(20, 20, 60, 20);
		textCondition = new Text(sequence_Expression, SWT.SINGLE | SWT.BORDER);
		data4 = new GridData(GridData.FILL_BOTH);
//		data4.widthHint = 409;
		textCondition.setLayoutData(data4);
//		textCondition.setBounds(90, 20, 400, 20);

		constraint  = new Label(sequence_Expression, SWT.RIGHT);
		constraint.setText(N3Messages.DIALOG_CONSTRAINT+": ");//PKY 08070701 S
//		constraint.setBounds(20, 50, 60, 50);
		textConstraint = new Text(sequence_Expression, SWT.SINGLE | SWT.BORDER);
		textConstraint.setLayoutData(data4);
//		textConstraint.setBounds(90, 50, 400, 20);


		control_Flow_Type = new Group(comp, SWT.SHADOW_ETCHED_IN);
		control_Flow_Type.setText(N3Messages.DIALOG_CONTROL_FLOW_TYPE);
		control_Flow_Type.setLayoutData(data3);
		control_Flow_Type.setLayout(glayout);
		
		Synch  = new Label(control_Flow_Type, SWT.RIGHT);
		Synch.setText(N3Messages.DIALOG_SYNCH+": ");
//		Synch.setBounds(20, 20, 60, 20);
		comboSynch = new Combo(control_Flow_Type,SWT.READ_ONLY);
//		comboSynch.setBounds(90, 20, 120, 20);
		comboSynch.add("Synchronous");
		comboSynch.add("Asynchronous");
		comboSynch.select(0);
//		comboSynch.setLayout(data4);

		Kind  = new Label(control_Flow_Type, SWT.RIGHT);
		Kind.setText(N3Messages.DIALOG_KIND+": ");
//		Kind.setBounds(20, 50, 60, 20);
		comboKind = new Combo(control_Flow_Type,SWT.READ_ONLY);
//		comboKind.setBounds(90, 50, 120, 20);
		comboKind.add("Call");
		comboKind.add("Signal");
		comboKind.select(0);
		
		Lifecycle  = new Label(control_Flow_Type, SWT.RIGHT);
		Lifecycle.setText(N3Messages.DIALOG_LIFECYCLE + ": ");
//		Lifecycle.setBounds(20, 80, 60, 20);
		comboLifecycle = new Combo(control_Flow_Type,SWT.READ_ONLY);
//		comboLifecycle.setBounds(90, 80, 120, 20);
		comboLifecycle.add("none");
		comboLifecycle.add("New");
		comboLifecycle.add("Delete");
		comboLifecycle.select(0);
		this.buildControls();

		return comp;
	}
	public void buildControls(){
		



	}

	protected void buttonPressed(int buttonId)
	{
		
	}
}
