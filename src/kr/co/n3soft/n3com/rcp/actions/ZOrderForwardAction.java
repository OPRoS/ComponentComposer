package kr.co.n3soft.n3com.rcp.actions;

import kr.co.n3soft.n3com.UMLEditor;
import kr.co.n3soft.n3com.edit.UMLEditPart;
import kr.co.n3soft.n3com.lang.N3Messages;
import kr.co.n3soft.n3com.model.activity.FinalActivityModel;
import kr.co.n3soft.n3com.model.activity.SubPartitonModel;
import kr.co.n3soft.n3com.model.comm.ClassifierModel;
import kr.co.n3soft.n3com.model.comm.PortModel;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.diagram.N3EditorDiagramModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindow;

public class ZOrderForwardAction extends SelectionAction {
	
	IWorkbenchWindow workbenchWindow;
	public static   int ID=0;
	IEditorInput input = null;
	UMLModel selectModel; 
	 public ZOrderForwardAction(IEditorPart editor) {
	        super(editor);
	        this.setId("ZOrderForwardAction");
	        this.setText(N3Messages.POPUP_Z_ORDER_FORWARD);
	        setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getImage(318)));//PKY 08070101 S �˾� �޴� �̹��� ����
//	        setChecked(isChecked());
	    }
	 protected boolean calculateEnabled() {
		
	        StructuredSelection list = (StructuredSelection)this.getSelection();
	        if (list != null && list.size() == 1) {
	            Object obj = list.getFirstElement();
	            if(obj instanceof UMLEditPart){
	            UMLEditPart u = (UMLEditPart)obj;
	            selectModel= (UMLModel)u.getModel();
		          //PKY 08060201 S ������ ���̾�׷� �յ� �����ϴ·��� ������ �ʵ���
	            if(selectModel.getAcceptParentModel()!=null
								&&selectModel.getAcceptParentModel() instanceof N3EditorDiagramModel
								&&((N3EditorDiagramModel)selectModel.getAcceptParentModel()).getDiagramType()!=12
								&&!(selectModel instanceof SubPartitonModel)){
	            int i = ProjectManager.getInstance().getModelType(selectModel, -1);
	          //PKY 08091602 S
	            if(selectModel instanceof PortModel){
	            	return true;
	            }
	          //PKY 08091602 E

	            if(i>=0){
	            	return true;
	            }
	            }
	          //PKY 08060201 E ������ ���̾�׷� �յ� �����ϴ·��� ������ �ʵ���
	            }
	        }
		 return false;
	 }
	public void run() {
		try{
			UMLEditor ue	 = ProjectManager.getInstance().getUMLEditor();	
			N3EditorDiagramModel nd = ue.getDiagram();
//			nd.addChild(selectModel);
			//PKY 08062601 S �� �յ� ��ġ ��ü�� �Ѱ��ϰ�� �����߻� ����

			if(nd.getChildren().size()>1){ 
			UMLModel addModel = selectModel;
			Point pt = addModel.getLocation();

			nd.removeChild(selectModel,false);//PKY 08081101 S Zoder �̿�� �� ����¹��� 
			addModel.setLocation(pt);

			
			nd.addChild(addModel);
			//PKY 08082201 S ��Ƽ��Ƽ Z-Oder�� ���� �� ��� ��Ƽ���� �ڷ� ���¹��� 
			if(addModel instanceof ClassifierModel){
				ClassifierModel classModel = (ClassifierModel) addModel;
				for(int i = 0; i < classModel.getPorts().size(); i ++){

					UMLModel portModel   = (UMLModel)classModel.getPorts().get(i);
					pt = new Point(portModel.getLocation().x,portModel.getLocation().y);
					nd.removeChild(portModel,false);//PKY 08081101 S Zoder �̿�� �� ����¹��� 
					portModel.setLocation(pt);
//					nd.removeModel(addModel);
					nd.addChild(portModel);
//					nd.setName("["+addModel.getName()+"]");
				}
			}
			if(addModel instanceof FinalActivityModel||addModel instanceof PortModel){
				if(addModel instanceof FinalActivityModel){
					FinalActivityModel activityModel = (FinalActivityModel) addModel;
					for(int i = 0; i < activityModel.getPm().getPartitions().size(); i ++){

						UMLModel  umlMode = (UMLModel)activityModel.getPm().getPartitions().get(i);
						pt = new Point(umlMode.getLocation().x,umlMode.getLocation().y);
						nd.removeChild(umlMode,false);//PKY 08081101 S Zoder �̿�� �� ����¹��� 
						umlMode.setLocation(pt);
						nd.removeModel(umlMode);
						nd.addChild(umlMode);
//						nd.setName("["+addModel.getName()+"]");
					}
				}else if(addModel instanceof PortModel){
					PortModel portModel = (PortModel) addModel;
					if(portModel.getPortContainerModel()!=null && portModel.getPortContainerModel() instanceof ClassifierModel){
						ClassifierModel classModel = (ClassifierModel)portModel.getPortContainerModel();
						for(int i = 0; i < classModel.getPorts().size(); i ++){

							UMLModel umlModel = (UMLModel)classModel.getPorts().get(i);
							pt = new Point(umlModel.getLocation().x,umlModel.getLocation().y);
							nd.removeChild(umlModel,false);//PKY 08081101 S Zoder �̿�� �� ����¹��� 
							umlModel.setLocation(pt);
//							nd.removeModel(addModel);
							nd.addChild(umlModel);
//							nd.setName("["+addModel.getName()+"]");
						}
						if(classModel instanceof FinalActivityModel){
							FinalActivityModel activityModel = (FinalActivityModel) classModel;
							for(int i = 0; i < activityModel.getPm().getPartitions().size(); i ++){

								UMLModel umlModel = (UMLModel)activityModel.getPm().getPartitions().get(i);
								pt = new Point(umlModel.getLocation().x,umlModel.getLocation().y);
								nd.removeChild(umlModel,false);//PKY 08081101 S Zoder �̿�� �� ����¹��� 
								umlModel.setLocation(pt);
								nd.removeModel(umlModel);
								nd.addChild(umlModel);
//								nd.setName("["+addModel.getName()+"]");
							}
						}
					}

				}
			}
			//PKY 08082201 E ��Ƽ��Ƽ Z-Oder�� ���� �� ��� ��Ƽ���� �ڷ� ���¹��� 

			}
			//PKY 08062601 E �� �յ� ��ġ ��ü�� �Ѱ��ϰ�� �����߻� ����
//			nd.addChild(selectModel);
//			nd.getChildren().set(nd.getChildren().size()-1, selectModel);
			
			 
		}
		catch(Exception e){
			e.printStackTrace();
		}
	    
//	    ProjectManager.getInstance().getModelBrowser().writeModel();
		
	}
	
//	private String openFileDialog() {
//		FileDialog dialog = new FileDialog(workbenchWindow.getShell(), SWT.OPEN);
//		dialog.setText("shapes");
//		//dialog.setFilterExtensions(new String[] { ".shapes" });
//		return dialog.open();
//	}
}

