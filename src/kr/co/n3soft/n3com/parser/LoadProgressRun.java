package kr.co.n3soft.n3com.parser;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;

public class LoadProgressRun implements IRunnableWithProgress {
	  // The total sleep time
	  private static final int TOTAL_TIME = 10000;

	  // The increment sleep time
	  private static final int INCREMENT = 500;

	  private boolean indeterminate;
	  N3ModelParser np = null;
	  public int total = 0;

	  /**
	   * LongRunningOperation constructor
	   * 
	   * @param indeterminate whether the animation is unknown
	   */
	  public LoadProgressRun(boolean indeterminate) {
	    this.indeterminate = indeterminate;
	  }

	  /**
	   * Runs the long running operation
	   * 
	   * @param monitor the progress monitor
	   */
	  public void run(IProgressMonitor monitor) throws InvocationTargetException,
	      InterruptedException {
	    monitor.beginTask("Loading Project",
	        indeterminate ? IProgressMonitor.UNKNOWN : TOTAL_TIME);
	    for (int total = 0; total < TOTAL_TIME && !monitor.isCanceled(); ) {
//	    	total = np.getTotal();
	    	Thread.sleep(INCREMENT);
	      monitor.worked(INCREMENT);
	      if (total == TOTAL_TIME / 2) monitor.subTask("Doing second half");
	    }
	    monitor.done();
	    if (monitor.isCanceled())
	        throw new InterruptedException("The long running operation was cancelled");
	  }

	public N3ModelParser getNp() {
		return np;
	}

	public void setNp(N3ModelParser np) {
		this.np = np;
	}
	}

