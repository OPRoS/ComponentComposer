package kr.co.n3soft.n3com.project.dialog;

import kr.co.n3soft.n3com.UMLEditor;
import kr.co.n3soft.n3com.lang.N3Messages;
import kr.co.n3soft.n3com.model.comm.ClassifierModel;
import kr.co.n3soft.n3com.model.comm.ClassifierModelTextAttach;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.diagram.N3EditorDiagramModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

//PKY 08080501 S 사용자가 컬러색상을 수정 할 수있도록 수정
//PKY 08081101 S N3Com 프로퍼티를 오픈 후 OK버튼을 누루면 내용이 저장되도록 기존에는 정상적인 종료를 했을때만 프로그램 내용이 저장되도록 되어있었음

public class N3StandardColorsPage extends PreferencePage {
	  // Names for preferences
	  private static final String ONE = "one.one";
	  private static final String TWO = "one.two";
	  private static final String THREE = "one.three";

	  // Text fields for user to enter preferences
//	  private Text fieldOne;
	  private Text fieldTwo;
	  private Text fieldThree;
	  Button buttonDirectory = null;
	  public String name = "일반";
	  private Color backFill = ProjectManager.getInstance().getDefaultColor();
	  private Label colorFill =null;
	  private Color changeColor= null;
	  /**
	   * Creates the controls for this page
	   */
	  protected Control createContents(Composite parent) {
	    Composite composite = new Composite(parent, SWT.NONE);
	    composite.setLayout(new GridLayout(3, false));

	    // Get the preference store
	    IPreferenceStore preferenceStore = getPreferenceStore();

	    // Create three text fields.
	    // Set the text in each from the preference store
	    new Label(composite, SWT.LEFT).setText(N3Messages.DIALOG_STANDARD_COLORS_ELEMENT_FILL);

	    GridData gr1 =   new GridData(GridData.FILL_HORIZONTAL);
	  

	    GridData gr2 =   new GridData();
	    gr2.widthHint = 50;
	    colorFill = new Label(composite, SWT.NONE);
	    
	    colorFill.setLayoutData(gr1);
	    colorFill.setSize(new Point(colorFill.getSize().x,colorFill.getSize().y-10));
//	    GridData gr2 =   new GridData();
	    gr2.widthHint = 60;
	    buttonDirectory = new Button(composite,SWT.PUSH);
	    buttonDirectory.setLayoutData(gr2);

	    colorFill.pack();
	    //	    la.setBackground(color)
	    colorFill.setBackground(ProjectManager.getInstance().getDefaultColor());
	    changeColor =  ProjectManager.getInstance().getDefaultColor();
	    colorFill.addMouseListener(new MouseListener(){
	    	public void mouseDoubleClick(MouseEvent e){
	    		System.out.print("a");
	    	}
	    	public void mouseDown(MouseEvent e){
				
				ColorDialog dd = new ColorDialog(ProjectManager.getInstance().window.getShell(),SWT.SINGLE);
				RGB d = dd.open();

				colorFill.setBackground(new Color(null,d));
				changeColor=colorFill.getBackground();
				if(ProjectManager.getInstance().getUMLEditor()!=null){
					UMLEditor viewer =  ProjectManager.getInstance().getUMLEditor();
					N3EditorDiagramModel n3Diagram = viewer.getDiagram();
					for(int i = 0; i < n3Diagram.getChildren().size(); i++){
						UMLModel umlModel = (UMLModel) n3Diagram.getChildren().get(i);
						if(umlModel instanceof ClassifierModel){
							((ClassifierModel)umlModel).refresh();
						}else if(umlModel instanceof ClassifierModelTextAttach){
							((ClassifierModelTextAttach)umlModel).refresh();
						}
					}
					System.out.print("");
				}
				
//			   ProjectManager.getInstance().setSourceFolder(fieldOne.getText());
				
			}
	    	public void mouseUp(MouseEvent e){
	    		System.out.print("c");
	    	}
	    });
	    buttonDirectory.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e)
			{
				
				ColorDialog dd = new ColorDialog(ProjectManager.getInstance().window.getShell(),SWT.SINGLE);
				RGB d = dd.open();

				colorFill.setBackground(new Color(null,d));
				changeColor=colorFill.getBackground();
				if(ProjectManager.getInstance().getUMLEditor()!=null){
					UMLEditor viewer =  ProjectManager.getInstance().getUMLEditor();
					N3EditorDiagramModel n3Diagram = viewer.getDiagram();
					for(int i = 0; i < n3Diagram.getChildren().size(); i++){
						UMLModel umlModel = (UMLModel) n3Diagram.getChildren().get(i);
						if(umlModel instanceof ClassifierModel){
							((ClassifierModel)umlModel).refresh();
						}else if(umlModel instanceof ClassifierModelTextAttach){
							((ClassifierModelTextAttach)umlModel).refresh();
						}
					}
					System.out.print("");
				}
				
//			   ProjectManager.getInstance().setSourceFolder(fieldOne.getText());
				
			}

			public void widgetDefaultSelected(SelectionEvent e)
			{
			}

		});
//	    fieldOne.setText(preferenceStore.getString(ONE));

//	    new Label(composite, SWT.LEFT).setText("Field Two:");
//	    fieldTwo = new Text(composite, SWT.BORDER);
//	    fieldTwo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
////	    fieldTwo.setText(preferenceStore.getString(TWO));
//
//	    new Label(composite, SWT.LEFT).setText("Field Three:");
//	    fieldThree = new Text(composite, SWT.BORDER);
//	    fieldThree.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
//	    fieldThree.setText(preferenceStore.getString(THREE));

	    return composite;
	  }

	  /**
	   * Called when user clicks Restore Defaults
	   */
	  protected void performDefaults() {
	    // Get the preference store
	    IPreferenceStore preferenceStore = getPreferenceStore();
		 colorFill.setBackground(new Color(null,0,0,0));
		 changeColor=colorFill.getBackground();
	  }

	  /**
	   * Called when user clicks Apply or OK
	   * 
	   * @return boolean
	   */
	  public boolean performOk() {
		//PKY 08082201 S 프로퍼티 오픈후 컬러쪽 화면을 보지 않을경우 에러발생
	  if(changeColor!=null){
		  ProjectManager.getInstance().setDefaultColor(changeColor);
			if(ProjectManager.getInstance().getUMLEditor()!=null){
				UMLEditor viewer =  ProjectManager.getInstance().getUMLEditor();
				N3EditorDiagramModel n3Diagram = viewer.getDiagram();
				for(int i = 0; i < n3Diagram.getChildren().size(); i++){
					UMLModel umlModel = (UMLModel) n3Diagram.getChildren().get(i);
					if(umlModel instanceof ClassifierModel){
						((ClassifierModel)umlModel).refresh();
					}else if(umlModel instanceof ClassifierModelTextAttach){
						((ClassifierModelTextAttach)umlModel).refresh();
					}
				}
				System.out.print("");
			}
	  }
		  return true;
	  }
	 protected void updateApplyButton() {

	    }
	  protected void performApply() {
		  if(changeColor!=null){//PKY 08082201 S 프로퍼티 오픈후 컬러쪽 화면을 보지 않을경우 에러발생
		ProjectManager.getInstance().setDefaultColor(changeColor);
		ProjectManager.getInstance().writeConfig();
		if(ProjectManager.getInstance().getUMLEditor()!=null){
			UMLEditor viewer =  ProjectManager.getInstance().getUMLEditor();
			N3EditorDiagramModel n3Diagram = viewer.getDiagram();
			for(int i = 0; i < n3Diagram.getChildren().size(); i++){
				UMLModel umlModel = (UMLModel) n3Diagram.getChildren().get(i);
				if(umlModel instanceof ClassifierModel){
					((ClassifierModel)umlModel).refresh();
				}else if(umlModel instanceof ClassifierModelTextAttach){
					((ClassifierModelTextAttach)umlModel).refresh();
				}
			}
			System.out.print("");
		}
		  }
	  }
	}

