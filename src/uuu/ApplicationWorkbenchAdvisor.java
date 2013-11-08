package uuu;

import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.ui.application.IWorkbenchConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchAdvisor;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;
//import org.eclipse.ui.console.ConsolePlugin;
//import org.eclipse.ui.console.IConsole;

public class ApplicationWorkbenchAdvisor extends WorkbenchAdvisor {

	private static final String PERSPECTIVE_ID = "uuu.perspective";

	public WorkbenchWindowAdvisor createWorkbenchWindowAdvisor(
			IWorkbenchWindowConfigurer configurer) {
		return new ApplicationWorkbenchWindowAdvisor(configurer);
	}

	public String getInitialWindowPerspectiveId() {
		return PERSPECTIVE_ID;
	}
	
	public void initialize(IWorkbenchConfigurer configurer) {
		System.out.println("dddd");
		ProjectManager.getInstance().loadConfig();
//		configurer.setSaveAndRestore(true);

//		// configure the Smack library
//		Chat.setFilteredOnThreadID(false);
		
//		ConsolePlugin.getDefault().getConsoleManager().addConsoles(
//				new IConsole[] { new DebugConsole() });
	}

}
