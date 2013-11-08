package kr.co.n3soft.n3com.project.dialog;

import kr.co.n3soft.n3com.lang.N3Messages;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;


//PKY 08081101 S N3Com 프로퍼티를 오픈 후 OK버튼을 누루면 내용이 저장되도록 기존에는 정상적인 종료를 했을때만 프로그램 내용이 저장되도록 되어있었음

public class N3DefaultPage extends PreferencePage {
	  // Names for preferences
	  private static final String ONE = "one.one";
	  private static final String TWO = "one.two";
	  private static final String THREE = "one.three";

	  // Text fields for user to enter preferences
	  private Text fieldOne;
	  private Text fieldTwo;
	  private Text fieldThree;
	  private Text field4;
	  private Text field5;
	  Button buttonDirectory = null;
	  Button button1 = null;
	  Button button2 = null;
	  Button button3 = null;
	  Button button4 = null;
	  Button buttonTeamFolderDirectory = null;
	  public String name = "일반";
	  public String ChangeFileName = ProjectManager.getInstance().getSourceFolder();	
	  
	  public String mPath = ProjectManager.getInstance().getMPath();
	  public String sPath = ProjectManager.getInstance().getSPath();
	  public String etc1Path = ProjectManager.getInstance().getEtc1Path();
	  public String ect2Path = ProjectManager.getInstance().getEct2Path();
	  
	  public String ChangeTeamProjectFileName = ProjectManager.getInstance().getTeamProjectFolder();
	  
	  public String templateFile = ProjectManager.getInstance().getReportTemplatePath(); //20080903 KDI s

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
	    new Label(composite, SWT.LEFT).setText("라이브러리 경로:");
	    fieldOne = new Text(composite, SWT.BORDER);
	    GridData gr1 =   new GridData(GridData.FILL_HORIZONTAL);
	  
	    fieldOne.setLayoutData(gr1);
	    GridData gr2 =   new GridData();
	    gr2.widthHint = 50;
	    buttonDirectory = new Button(composite,SWT.PUSH);
	    buttonDirectory.setLayoutData(gr2);
	    if(ProjectManager.getInstance().getSourceFolder()!=null){
	    	fieldOne.setText(ProjectManager.getInstance().getSourceFolder());
	    }
	    buttonDirectory.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e)
			{
				
				DirectoryDialog dd = new DirectoryDialog(ProjectManager.getInstance().window.getShell(),SWT.SINGLE);
				String d = dd.open();
				if(d!=null)
				fieldOne.setText(d);
				ChangeFileName = fieldOne.getText();//PKY 08081801 S 프로퍼티 수정하다보면 에러발생하는 문제 수정

				
			}

			public void widgetDefaultSelected(SelectionEvent e)
			{
			}

		});
	    new Label(composite, SWT.LEFT).setText("모니터링 실행파일:");
	    fieldTwo = new Text(composite, SWT.BORDER);
	     gr1 =   new GridData(GridData.FILL_HORIZONTAL);
	  
	    fieldTwo.setLayoutData(gr1);
	    gr2 =   new GridData();
	    gr2.widthHint = 50;
	    button1 = new Button(composite,SWT.PUSH);
	    button1.setLayoutData(gr2);
	    if(ProjectManager.getInstance().getMPath()!=null){
	    	fieldTwo.setText(ProjectManager.getInstance().getMPath());
	    }
	    button1.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e)
			{
				FileDialog dd = new FileDialog(ProjectManager.getInstance().window.getShell(),SWT.SINGLE);
				String d = dd.open();
				if(d!=null)
					fieldTwo.setText(d);
				mPath = fieldTwo.getText();//PKY 08081801 S 프로퍼티 수정하다보면 에러발생하는 문제 수정
			}
			public void widgetDefaultSelected(SelectionEvent e)
			{
			}

		});
	    
	    new Label(composite, SWT.LEFT).setText("시물레이션 실행파일:");
	    fieldThree = new Text(composite, SWT.BORDER);
	     gr1 =   new GridData(GridData.FILL_HORIZONTAL);
	  
	     fieldThree.setLayoutData(gr1);
	    gr2 =   new GridData();
	    gr2.widthHint = 50;
	    button2 = new Button(composite,SWT.PUSH);
	    button2.setLayoutData(gr2);
	    if(ProjectManager.getInstance().getSPath()!=null){
	    	fieldThree.setText(ProjectManager.getInstance().getSPath());
	    }
	    button2.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e)
			{
				FileDialog dd = new FileDialog(ProjectManager.getInstance().window.getShell(),SWT.SINGLE);
				String d = dd.open();
				if(d!=null)
					fieldThree.setText(d);
				sPath = fieldThree.getText();//PKY 08081801 S 프로퍼티 수정하다보면 에러발생하는 문제 수정
			}
			public void widgetDefaultSelected(SelectionEvent e)
			{
			}

		});
	    
	    new Label(composite, SWT.LEFT).setText("T1  실행파일:");
	    field4 = new Text(composite, SWT.BORDER);
	     gr1 =   new GridData(GridData.FILL_HORIZONTAL);
	  
	     field4.setLayoutData(gr1);
	    gr2 =   new GridData();
	    gr2.widthHint = 50;
	   
	    button3 = new Button(composite,SWT.PUSH);
	    button3.setLayoutData(gr2);
	    if(ProjectManager.getInstance().getEtc1Path()!=null){
	    	field4.setText(ProjectManager.getInstance().getEtc1Path());
	    }
	    button3.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e)
			{
				FileDialog dd = new FileDialog(ProjectManager.getInstance().window.getShell(),SWT.SINGLE);
				String d = dd.open();
				if(d!=null)
					field4.setText(d);
				etc1Path = field4.getText();//PKY 08081801 S 프로퍼티 수정하다보면 에러발생하는 문제 수정
			}
			public void widgetDefaultSelected(SelectionEvent e)
			{
			}

		});
	    
	    new Label(composite, SWT.LEFT).setText("T2 실행파일:");
	    field5 = new Text(composite, SWT.BORDER);
	     gr1 =   new GridData(GridData.FILL_HORIZONTAL);
	  
	     field5.setLayoutData(gr1);
	    gr2 =   new GridData();
	    gr2.widthHint = 50;
	    button4 = new Button(composite,SWT.PUSH);
	    button4.setLayoutData(gr2);
	    if(ProjectManager.getInstance().getEct2Path()!=null){
	    	field5.setText(ProjectManager.getInstance().getEct2Path());
	    }
	    button4.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e)
			{
				FileDialog dd = new FileDialog(ProjectManager.getInstance().window.getShell(),SWT.SINGLE);
				String d = dd.open();
				if(d!=null)
					field5.setText(d);
				ect2Path = field5.getText();//PKY 08081801 S 프로퍼티 수정하다보면 에러발생하는 문제 수정
			}
			public void widgetDefaultSelected(SelectionEvent e)
			{
			}

		});
	    
	  //PKY 08082201 S 팀 프로젝트 경로를 프로젝트에서 변경가능하도록
//	    new Label(composite, SWT.LEFT).setText(N3Messages.DIALOG_TEAM_FOLDER);
//	    fieldTwo = new Text(composite, SWT.BORDER);
//	    fieldTwo.setLayoutData(gr1);
//	    buttonTeamFolderDirectory = new Button(composite,SWT.PUSH);
//	    buttonTeamFolderDirectory.setLayoutData(gr2);
//	    if(ProjectManager.getInstance().getTeamProjectFolder()!=null){
//	    	fieldTwo.setText(ProjectManager.getInstance().getTeamProjectFolder());
//	    }
//	    buttonTeamFolderDirectory.addSelectionListener(new SelectionListener() {
//
//			public void widgetSelected(SelectionEvent e)
//			{
//				
//				DirectoryDialog dd = new DirectoryDialog(ProjectManager.getInstance().window.getShell(),SWT.SINGLE);
//				if(!fieldTwo.getText().equals(""))
//				dd.setFilterPath(fieldTwo.getText());
//				String d = dd.open();
//				if(d!=null)
//					fieldTwo.setText(d);
//				ChangeTeamProjectFileName = fieldTwo.getText();//PKY 08081801 S 프로퍼티 수정하다보면 에러발생하는 문제 수정
//
//				
//			}
//
//			public void widgetDefaultSelected(SelectionEvent e)
//			{
//			}
//
//		});
	  //PKY 08082201 E 팀 프로젝트 경로를 프로젝트에서 변경가능하도록


	    
	    //20080903 KDI s
//	    Group report = new Group(composite, SWT.FILL);
//	    report.setText("리포트 템플릿 파일");
//	    
//	    GridData grid = new GridData(GridData.FILL_HORIZONTAL);
//	    grid.horizontalSpan = 3;
//	    grid.widthHint = 350;
//	    
//	    report.setLayout(new GridLayout(3, false));
//	    report.setLayoutData(grid);	    
//	    
//	    Label template_Label = new Label(report, SWT.LEFT);
//	    template_Label.setText(N3Messages.REPORT_USE_TEMPLATE+": ");
//	    
//	    grid = new GridData(GridData.FILL_HORIZONTAL);
////	    grid.widthHint = 250;
//	    
//	    final Text template_Field = new Text(report, SWT.BORDER);
//	    template_Field.setLayoutData(grid);
//	    
//	    template_Field.setText(templateFile);
//	    
//	    Button button_Template = new Button(report,SWT.PUSH);
//	    button_Template.setLayoutData(gr2);
//	    button_Template.addSelectionListener(new SelectionListener() {
//			public void widgetSelected(SelectionEvent e){
//				FileDialog dialog = new FileDialog(ProjectManager.getInstance().window.getShell());
//				dialog.setFilterNames(new String[]{"htm Files","All Files (* *)"});
//				dialog.setFilterExtensions(new String[]{"*.htm","*.*"});
//				dialog.setFilterPath(templateFile);
//				
//				String path = dialog.open();
//				if(path != null && !path.trim().equals("")){
//					template_Field.setText(dialog.getFilterPath()+"\\"+dialog.getFileName());
//					ProjectManager.getInstance().setReportTemplatePath(template_Field.getText());
//				}
//			}
//
//			public void widgetDefaultSelected(SelectionEvent e){
//			}
//
//		});
	    //20080903 KDI e

	    return composite;
	  }

	  /**
	   * Called when user clicks Restore Defaults
	   */
	  protected void performDefaults() {
	    // Get the preference store
	    IPreferenceStore preferenceStore = getPreferenceStore();

	    // Reset the fields to the defaults
	    fieldOne.setText("");
//	    fieldTwo.setText(preferenceStore.getDefaultString(TWO));
//	    fieldThree.setText(preferenceStore.getDefaultString(THREE));
	  }

	  /**
	   * Called when user clicks Apply or OK
	   * 
	   * @return boolean
	   */

	  public boolean performOk() {
		
		  ProjectManager.getInstance().setSourceFolder();	//110905 SDM - 로컬경로부분 수정 //PKY 08081801 S 프로퍼티 수정하다보면 에러발생하는 문제 수정
		  
		  
		  ProjectManager.getInstance().setMPath(mPath);//PKY 08081801 S 프로퍼티 수정하다보면 에러발생하는 문제 수정
		  ProjectManager.getInstance().setSPath(sPath);//PKY 08081801 S 프로퍼티 수정하다보면 에러발생하는 문제 수정
		  ProjectManager.getInstance().setEtc1Path(etc1Path);//PKY 08081801 S 프로퍼티 수정하다보면 에러발생하는 문제 수정
		  ProjectManager.getInstance().setEct2Path(ect2Path);//PKY 08081801 S 프로퍼티 수정하다보면 에러발생하는 문제 수정
		  
		  
//		  ProjectManager.getInstance().setTeamProjectFolder(ChangeTeamProjectFileName);
		  
		  return true;
	  }
	  protected void performApply() {
		  ProjectManager.getInstance().setSourceFolder();	//110905 SDM - 로컬경로부분 수정
		  ProjectManager.getInstance().setMPath(mPath);//PKY 08081801 S 프로퍼티 수정하다보면 에러발생하는 문제 수정
		  ProjectManager.getInstance().setSPath(sPath);//PKY 08081801 S 프로퍼티 수정하다보면 에러발생하는 문제 수정
		  ProjectManager.getInstance().setEtc1Path(etc1Path);//PKY 08081801 S 프로퍼티 수정하다보면 에러발생하는 문제 수정
		  ProjectManager.getInstance().setEct2Path(ect2Path);//PKY 08081801 S 프로퍼티 수정하다보면 에러발생하는 문제 수정
//		  ProjectManager.getInstance().setTeamProjectFolder(ChangeTeamProjectFileName);
		  ProjectManager.getInstance().writeConfig();
	  }
	}

