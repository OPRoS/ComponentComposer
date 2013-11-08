package kr.co.n3soft.n3com.projectmanager;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

import kr.co.n3soft.n3com.project.browser.RootLibTreeModel;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;

public class LoadLibProgress implements IRunnableWithProgress {
	  // The total sleep time
	  private static final int TOTAL_TIME = 10000;

	  // The increment sleep time
	  private static final int INCREMENT = 500;
	  
	  private static boolean isBegin = false;
	  
	  RootLibTreeModel rl;

	  private boolean indeterminate;

	  /**
	   * LongRunningOperation constructor
	   * 
	   * @param indeterminate whether the animation is unknown
	   */
	  public LoadLibProgress(boolean indeterminate) {
	    this.indeterminate = indeterminate;
	  }

	  /**
	   * Runs the long running operation
	   * 
	   * @param monitor the progress monitor
	   */
	  public void run(IProgressMonitor monitor) throws InvocationTargetException,
	      InterruptedException {
	    monitor.beginTask("Load library",
	        indeterminate ? IProgressMonitor.UNKNOWN : TOTAL_TIME);
	    String str = ProjectManager.getInstance().getSourceFolder();
	    for (int total = 0; total < TOTAL_TIME && !monitor.isCanceled(); total += INCREMENT) {
	    	
	    	if(!isBegin)
	    	if(str!=null && !str.trim().equals("")){
			CompAssemManager.getInstance().getViewInfos().clear();
			CompAssemManager.getInstance().getCompositeCmp().clear();
			File dirFile = new File(str);
			CompAssemManager.getInstance().deleteModel(rl);
			boolean isRoot = true;
			CompAssemManager.getInstance().recurse(dirFile, rl,isRoot);
			CompAssemManager.getInstance().makeView();
			ProjectManager.getInstance().getModelBrowser().refresh(rl);
			isBegin = true;
		}
	    	monitor.worked(INCREMENT);
	    	if (total == TOTAL_TIME / 2) monitor.subTask("Doing second half");
	    }
//	    for (int total = 0; total < TOTAL_TIME && !monitor.isCanceled(); total += INCREMENT) {
//	      Thread.sleep(INCREMENT);
//	      monitor.worked(INCREMENT);
//	      if (total == TOTAL_TIME / 2) monitor.subTask("Doing second half");
//	    }
	    
	    monitor.done();
	    if (monitor.isCanceled())
	        throw new InterruptedException("The long running operation was cancelled");
	  }

	public RootLibTreeModel getRl() {
		return rl;
	}

	public void setRl(RootLibTreeModel rl) {
		this.rl = rl;
	}
	}