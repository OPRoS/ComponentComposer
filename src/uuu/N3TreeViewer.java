package uuu;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;

public class N3TreeViewer extends TreeViewer{
	
	public N3TreeViewer(Composite parent) {
		super(parent);
	}
	
	public N3TreeViewer(Composite parent, int style) {
		super(parent,style);
	}
	
	public N3TreeViewer(Tree tree) {
		super(tree);
		
	}

}
