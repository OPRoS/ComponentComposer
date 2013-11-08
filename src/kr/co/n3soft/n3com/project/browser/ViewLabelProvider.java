package kr.co.n3soft.n3com.project.browser;

import kr.co.n3soft.n3com.model.comm.StrcuturedPackageTreeModel;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.component.ComponentModel;
import kr.co.n3soft.n3com.model.diagram.N3EditorDiagramModel;
import kr.co.n3soft.n3com.model.usecase.UseCaseModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

public class ViewLabelProvider extends LabelProvider {
	public String getText(Object obj) {
		return obj.toString();
	}

	public Image getImage(Object obj) {
		String imageKey = ISharedImages.IMG_OBJ_ELEMENT;
		if (obj instanceof RootAppPackageTreeModel ) {
			return ProjectManager.getInstance().getImage(0);
		}
		//이미지 교체
		else if (obj instanceof RootTreeModel && !(obj instanceof RootLibTreeModel)) {
			return ImageDescriptor.createFromFile(UseCaseModel.class, "icons/icno_16x16/21_Package_1.png").createImage();
		}
		else if(obj instanceof NodeRootTreeModel){
			return ImageDescriptor.createFromFile(UseCaseModel.class, "icons/icno_16x16/12_RobotPackage.png").createImage();
		}
		else if(obj instanceof RootServerRepositoryTreeMode){
			return ImageDescriptor.createFromFile(UseCaseModel.class, "icons/icno_16x16/22_Package_Repository.png").createImage();
		}
		else if(obj instanceof ServerRepositoryTreeModel){
			return ImageDescriptor.createFromFile(UseCaseModel.class, "icons/icno_16x16/29_Repositoryip.png").createImage();
		}
		else if(obj instanceof NodePackageTreeModel){
			return ImageDescriptor.createFromFile(UseCaseModel.class, "icons/icno_16x16/13_Robot.png").createImage();
		}
		else if(obj instanceof RootLibTreeModel){
			return ImageDescriptor.createFromFile(UseCaseModel.class, "icons/icno_16x16/17_Library.png").createImage();
		}
		else if(obj instanceof MonitorRootTreeModel){
			return ImageDescriptor.createFromFile(UseCaseModel.class, "icons/webAsm16.gif").createImage();
		}
		else if(obj instanceof ServerMonitorTreeModel){
			return ImageDescriptor.createFromFile(UseCaseModel.class, "icons/icno_16x16/29_Repositoryip.png").createImage();
		}
		else if(obj instanceof RootCmpEdtTreeModel){
			return ImageDescriptor.createFromFile(UseCaseModel.class, "icons/icno_16x16/01_Editor.png").createImage();
		}
		else if(obj instanceof RootCmpFnshTreeModel){
			return ImageDescriptor.createFromFile(UseCaseModel.class, "icons/icno_16x16/011_EditorProject.png").createImage();
		}
		else if(obj instanceof RootCmpComposingTreeModel){
			return ImageDescriptor.createFromFile(UseCaseModel.class, "icons/icno_16x16/02_Composer.png").createImage();
		}
		else if(obj instanceof RootTemplateTreeModel){
			return ImageDescriptor.createFromFile(UseCaseModel.class, "icons/template.png").createImage();
		}
		else if(obj instanceof MonitorCompTreeModel){
			MonitorCompTreeModel mct = (MonitorCompTreeModel)obj;
			if(mct.isAtomic)
			return ImageDescriptor.createFromFile(UseCaseModel.class, "icons/icno_16x16/11_Automic.png").createImage();
			else
				return ImageDescriptor.createFromFile(UseCaseModel.class, "icons/icno_16x16/23_Composite.png").createImage();	
		}
		else if(obj instanceof AppTreeModel){
			AppTreeModel atm = (AppTreeModel)obj;
			if(atm.isRun)
			return ImageDescriptor.createFromFile(UseCaseModel.class, "icons/icno_16x16/16_PunApplication.png").createImage();
			else
				return ImageDescriptor.createFromFile(UseCaseModel.class, "icons/icno_16x16/15_StopApplication.png").createImage();	
		}
		/*
		else if (obj instanceof RootTreeModel && !(obj instanceof RootLibTreeModel)) {
			return ImageDescriptor.createFromFile(UseCaseModel.class, "icons/project2.gif").createImage();
		}
		else if(obj instanceof NodeRootTreeModel){
			return ImageDescriptor.createFromFile(UseCaseModel.class, "icons/deployment_titel.gif").createImage();
		}
		else if(obj instanceof RootServerRepositoryTreeMode){
			return ImageDescriptor.createFromFile(UseCaseModel.class, "icons/repo.gif").createImage();
		}
		else if(obj instanceof ServerRepositoryTreeModel){
			return ImageDescriptor.createFromFile(UseCaseModel.class, "icons/host.gif").createImage();
		}
		else if(obj instanceof NodePackageTreeModel){
			return ImageDescriptor.createFromFile(UseCaseModel.class, "icons/robot.gif").createImage();
		}
		else if(obj instanceof RootLibTreeModel){
			return ImageDescriptor.createFromFile(UseCaseModel.class, "icons/library.gif").createImage();
		}
		else if(obj instanceof MonitorRootTreeModel){
			return ImageDescriptor.createFromFile(UseCaseModel.class, "icons/webAsm16.gif").createImage();
		}
		else if(obj instanceof ServerMonitorTreeModel){
			return ImageDescriptor.createFromFile(UseCaseModel.class, "icons/host.gif").createImage();
		}
		else if(obj instanceof RootCmpEdtTreeModel){
			return ImageDescriptor.createFromFile(UseCaseModel.class, "icno_16x16/01_Editor.png").createImage();
		}
		else if(obj instanceof RootCmpComposingTreeModel){
			return ImageDescriptor.createFromFile(UseCaseModel.class, "icons/title.gif").createImage();
		}
		else if(obj instanceof RootTemplateTreeModel){
			return ImageDescriptor.createFromFile(UseCaseModel.class, "icons/template.png").createImage();
		}
		else if(obj instanceof MonitorCompTreeModel){
			MonitorCompTreeModel mct = (MonitorCompTreeModel)obj;
			if(mct.isAtomic)
			return ImageDescriptor.createFromFile(UseCaseModel.class, "icons/16x16_Atomic.gif").createImage();
			else
				return ImageDescriptor.createFromFile(UseCaseModel.class, "icons/16x16_Composite.gif").createImage();	
		}
		else if(obj instanceof AppTreeModel){
			AppTreeModel atm = (AppTreeModel)obj;
			if(atm.isRun)
			return ImageDescriptor.createFromFile(UseCaseModel.class, "icons/run.gif").createImage();
			else
				return ImageDescriptor.createFromFile(UseCaseModel.class, "icons/stop.gif").createImage();	
		}
*/		
		else if (obj instanceof StrcuturedPackageTreeModel) {
			UMLTreeModel utm = (UMLTreeModel)obj;
//			UMLTreeModel utm = (UMLTreeModel)obj;
			if(utm.getModelType()==1){
				if(utm.getRefModel() instanceof N3EditorDiagramModel){
					N3EditorDiagramModel nd = (N3EditorDiagramModel)utm.getRefModel();
					//useCase diagram
//					if(nd.getDiagramType()==8){

					return ProjectManager.getInstance().getDiagramImage(nd.getDiagramType());
//					}
				}
			}//useCase
//			WJH 090809 S 
			if(utm.getRefModel() instanceof ComponentModel){
				ComponentModel clm = (ComponentModel)utm.getRefModel();
				String ste = clm.getStereotype();
				if("<<atomic>>".equals(ste)){
				
					return ProjectManager.getInstance().getImage(30);
				}
				else{
					return ProjectManager.getInstance().getImage(29);
				}
			}
//			WJH 090809 E
			return ProjectManager.getInstance().getImage(utm.getModelType());
		}
		else if (obj instanceof NodeItemTreeModel) {
			return ProjectManager.getInstance().getImage(37);
		}
		else if (obj instanceof RepoComponentTreeModel) {
			//이미지 교체
			return ImageDescriptor.createFromFile(UseCaseModel.class, "icons/icno_16x16/11_Automic.png").createImage();
//			return ImageDescriptor.createFromFile(UseCaseModel.class, "icons/16x16_Atomic.gif").createImage();
		}

		else if (obj instanceof PackageTreeModel) {
//			return ImageDescriptor.createFromFile(UseCaseModel.class, "icons/package.gif").createImage();
			//20080811IJS 시작
			PackageTreeModel pm = (PackageTreeModel)obj;
			if(pm.getIsLinkType()==0){
				return ProjectManager.getInstance().getImage(1000);
			}
			else if(pm.getIsLinkType()==1){
				UMLModel um = (UMLModel)pm.getRefModel();
				if(um.isReadOnlyModel){
					return ProjectManager.getInstance().getImage(1002);
				}
				else
					return ProjectManager.getInstance().getImage(1001);
				//PKY 08090502 S 
			}else{
				return ProjectManager.getInstance().getImage(pm.getModelType());
			}
			//PKY 08090502 E 

			//20080811IJS 끝
		}
		//PKY 08090502 E 
		if (obj instanceof UMLTreeModel) {
			UMLTreeModel utm = (UMLTreeModel)obj;
			if(utm.getModelType()==1){
				if(utm.getRefModel() instanceof N3EditorDiagramModel){
					N3EditorDiagramModel nd = (N3EditorDiagramModel)utm.getRefModel();
					//useCase diagram
//					if(nd.getDiagramType()==8){

					return ProjectManager.getInstance().getDiagramImage(nd.getDiagramType());
//					}
				}
			}//useCase
			return ProjectManager.getInstance().getImage(utm.getModelType());
//			else if(utm.getModelType()==2){
//			return ImageDescriptor.createFromFile(UseCaseModel.class, "icons/usecase.gif").createImage();
//			}//FinalActorModel
//			else if(utm.getModelType()==3){
//			return ImageDescriptor.createFromFile(FinalActorModel.class, "icons/actor.gif").createImage();
//			}//CollaborationModel
//			else if(utm.getModelType()==5){
//			return ImageDescriptor.createFromFile(CollaborationModel.class, "icons/collaboration.gif").createImage();
//			}//FinalBoundryModel
//			else if(utm.getModelType()==4){
//			return ImageDescriptor.createFromFile(FinalBoundryModel.class, "icons/boundary.gif").createImage();
//			}
		}
		return PlatformUI.getWorkbench().getSharedImages().getImage(imageKey);
	}
}
