package uuu;

import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.ICoolBarManager;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;

/**
 * An action bar advisor is responsible for creating, adding, and disposing of
 * the actions added to a workbench window. Each window will be populated with
 * new actions.
 */
public class ApplicationActionBarAdvisor extends ActionBarAdvisor {

	private IWorkbenchAction exitAction;
	private IWorkbenchAction viewAction;
	private IAction newAction;
	private IAction findAction;
	private IAction closeAllDiagramAction;
	private IAction newProjectAction;
	private IAction saveAction;
	private IAction loadAction;
	private IAction reverseJavaEngineerAction;
	private IAction reverseCplusEngineerAction;
	private IWorkbenchAction aboutAction;
	private IWorkbenchAction helpAction;
	private IWorkbenchAction introAction;
	private IAction printAction;//2008042901 PKY S 
	
	private IAction roseloadAction;		// V1.01 WJH E 080506 추가

	private IAction reportGenerateAction; //20080903 KDI s
	
	private IAction openN3PropertyDialogAction;
	 //PKY 08062601 S 단축키 정의
	private IAction saveAsAction;
	private IWorkbenchAction DeleteAction;
	private IWorkbenchAction CopyeAction;
	private IWorkbenchAction PasteAction;
	 //PKY 08062601 E 단축키 정의
	
	private IAction viewPropertiesAction;
	private IAction viewOutlineAction;
	private IAction viewConsoleAction;
	
	private IAction viewDescriptionAction; //20080728 KDI s
	
	private IAction OpenApplyPatternAction;
	
	
	//PKY 08070301 S 툴바 추가작업
	private IAction addPackage;
	private IAction addDiagramAction;
	private IAction addModelAction;
	private IAction writeCAction;
	private IAction writeJavaAction;
	
	private IAction windowsPropertyAction;
	private IAction windowsOutlineyAction;
	private IAction windowsConsoleAction;
	
	private IAction windowsDescriptionAction; //20080728 KDI s
	
	//PKY 08070301 E 툴바 추가작업
	
	public ApplicationActionBarAdvisor(IActionBarConfigurer configurer) {
		super(configurer);
		
		ProjectManager.getInstance().setApplication(this);//PKY 08070301 S 툴바 추가작업
		
	}

	protected void makeActions(final IWorkbenchWindow window) {

	}

	protected void fillMenuBar(IMenuManager menuBar) {

    	}
	protected void fillCoolBar(ICoolBarManager coolBar) {

		 }

	//PKY 08070301 S 툴바 추가작업
	public IAction getAddPackage() {
		return addPackage;
	}

	public void setAddPackage(IAction addPackage) {
		this.addPackage = addPackage;
	}

	public IAction getAddDiagramAction() {
		return addDiagramAction;
	}

	public void setAddDiagramAction(IAction addDiagramAction) {
		this.addDiagramAction = addDiagramAction;
	}

	public IAction getAddModelAction() {
		return addModelAction;
	}

	public void setAddModelAction(IAction addModelAction) {
		this.addModelAction = addModelAction;
	}

	public IAction getWriteCAction() {
		return writeCAction;
	}

	public void setWriteCAction(IAction writeCAction) {
		this.writeCAction = writeCAction;
	}

	public IAction getWriteJavaAction() {
		return writeJavaAction;
	}

	public void setWriteJavaAction(IAction writeJavaAction) {
		this.writeJavaAction = writeJavaAction;
	}
	//PKY 08070301 E 툴바 추가작업
	//PKY 08071601 S 다이얼로그 UI작업
	public IAction getOpenApplyPatternAction() {
		return OpenApplyPatternAction;
	}

	public void setOpenApplyPatternAction(IAction openApplyPatternAction) {
		OpenApplyPatternAction = openApplyPatternAction;
	}
	//PKY 08071601 E 다이얼로그 UI작업
	//PKY 08082201 S 팀 프로젝트 읽기전용일때 수정불가능하도록 작업
	public IAction getReverseJavaEngineerAction() {
		return reverseJavaEngineerAction;
	}

	public void setReverseJavaEngineerAction(IAction reverseJavaEngineerAction) {
		this.reverseJavaEngineerAction = reverseJavaEngineerAction;
	}

	public IAction getReverseCplusEngineerAction() {
		return reverseCplusEngineerAction;
	}

	public void setReverseCplusEngineerAction(IAction reverseCplusEngineerAction) {
		this.reverseCplusEngineerAction = reverseCplusEngineerAction;
	}
	//PKY 08082201 E 팀 프로젝트 읽기전용일때 수정불가능하도록 작업


}