package kr.co.n3soft.n3com.project.browser;

import kr.co.n3soft.n3com.dnd.TreeSimpleFactory;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.component.AtomicComponentModel;
import kr.co.n3soft.n3com.model.component.ComponentLibModel;
import kr.co.n3soft.n3com.model.component.ComponentModel;
import kr.co.n3soft.n3com.model.diagram.N3EditorDiagramModel;
import kr.co.n3soft.n3com.model.node.NodePackageModel;
import kr.co.n3soft.n3com.model.node.NodeRootModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.gef.dnd.TemplateTransfer;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;

public class ModelBrowserDragListener implements DragSourceListener {
	private ModelBrowser browser;
	TreeSimpleFactory template = null;

	public ModelBrowserDragListener(ModelBrowser browser) {
		this.browser = browser;
	}

	public void dragStart(DragSourceEvent event) {
		//20080811IJS Ω√¿€
		event.doit = true;
		template = new TreeSimpleFactory(null);
		if (template == null)
			event.doit = false;
		ProjectManager.getInstance().InitDragUMLModel();
		UMLModel um = ProjectManager.getInstance().getDragUMLModel();
		if(um instanceof NodeRootModel){
			event.doit = false;
			return;
		}
		if(um instanceof NodePackageModel){
			event.doit = false;
			return;
		}
		if(um instanceof ComponentLibModel){
			ComponentLibModel clm = (ComponentLibModel)um;
			if(clm.isInstance){
				event.doit = false;
				return ;
			}
		}
		else if(um instanceof AtomicComponentModel){
//			ComponentLibModel clm = (ComponentLibModel)um;
//			if(clm.isInstance){
//				event.doit = false;
//				return ;
//			}
		}
		else if(um instanceof ComponentModel){
			ComponentModel clm = (ComponentLibModel)um;
			if("<<composite>>".equals(clm.getStereotype())){
				event.doit = false;
				return ;
			}
			if(clm.isInstance){
				event.doit = false;
				return ;
			}

		}
		if(um.isReadOnlyModel){
			event.doit = false;
			return;
		}
		if(um instanceof N3EditorDiagramModel){
			event.doit = false;
			return;
		}
		template.setUMLModel(ProjectManager.getInstance().getDragUMLModel());
		template.setUMLTreeModel(ProjectManager.getInstance().getDragUMLTreeModel());
		TemplateTransfer.getInstance().setTemplate(template);
		//20080811IJS ≥°
	}

	public void dragSetData(DragSourceEvent event) {
		//		  System.out.println("ddd");
		//		  ProjectManager.getInstance().InitDragUMLModel();
		event.data = template;
		//	    event.data = browser
	}

	public void dragFinished(DragSourceEvent event) {
		template = null;
		TemplateTransfer.getInstance().setTemplate(null);
		ProjectManager.getInstance().successDragUMLModel();
	}
}
