package kr.co.n3soft.n3com.project.browser;

import java.util.ArrayList;
import java.util.List;

import javac.parser.SourceManager;
import kr.co.n3soft.n3com.OPRoSProjectInfo;
import kr.co.n3soft.n3com.edit.FinalPackageEditPart;
import kr.co.n3soft.n3com.lang.N3Messages;
import kr.co.n3soft.n3com.model.comm.FinalPackageModel;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.component.AtomicComponentModel;
import kr.co.n3soft.n3com.model.component.ComponentLibModel;
import kr.co.n3soft.n3com.model.component.ComponentModel;
import kr.co.n3soft.n3com.model.diagram.N3EditorDiagramModel;
import kr.co.n3soft.n3com.parser.ModelManager;
import kr.co.n3soft.n3com.parser.TeamProjectManager;
import kr.co.n3soft.n3com.projectmanager.OPRoSManifest;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;

//등록
public class UMLTreeParentModel extends UMLTreeModel {
	public int rank = -1;
	private ArrayList children;
	//20080811IJS
	private boolean isLink = false;
	//20080811IJS
	//-1(default),0(x load),1(load)
	private int isLinkType = -1;
	private String linkPath = null;
	
	public OPRoSManifest oPRoSManifest = null;

	//2008040301 PKY S 
	public static String UseCaseDiagramDefaultName = "유즈케이스다이어그램";
	public static String PackageName = N3Messages.PALETTE_PACKAGE;
	public static String UseCaseName = N3Messages.PALETTE_USECASE;
	public static String ActorName = N3Messages.PALETTE_ACTOR;
	public static String BoundaryName = N3Messages.PALETTE_BOUNDARY;
	public static String CollaborationName = N3Messages.PALETTE_COLLABORATION;
	public static String ClassName = N3Messages.PALETTE_CLASS;
	public static String InterfaceName = N3Messages.PALETTE_INTERFACE;
	public static String EnumerationName = N3Messages.PALETTE_ENUMERATION;
	public static String ExceptionHandlerName = "ExceptionHandler";
	public static String FinalActivityModelName = N3Messages.PALETTE_ACTIVITY;
	public static String FinalActiionModelName = N3Messages.PALETTE_ACTION;
	public static String SendModelName = N3Messages.MODEL_EVENT;
	public static String ReceiveModelName = N3Messages.MODEL_EVENT;
	public static String FinalObjectNodeModelName = N3Messages.PALETTE_OBJECT;
	public static String CentralBufferNodeModelName = N3Messages.PALETTE_CENTRALBUFFERNODE;
	public static String DataStoreModelName = N3Messages.PALETTE_DATASTORE;
	public static String PartitionModelName = N3Messages.PALETTE_SWIMLANE;
	public static String StrcuturedActivityModelName =N3Messages.PALETTE_STCUTUREACTIVITY;
	public static String PartModellName = N3Messages.PALETTE_PART;
	public static String LifeLineModellName =N3Messages.PALETTE_OBJECT;
	public static String ComponentModelName = N3Messages.PALETTE_COMPONENT;
	public static String ArtfifactModelName = N3Messages.PALETTE_ARTIFACT;
	public static String StateModelName =  N3Messages.PALETTE_STATE;
	public static String StrcuturedStateModelName = N3Messages.PALETTE_STATE;//PKY 08081101 S subMachineState 구조 변경
	public static String LifeLineModelName =  N3Messages.PALETTE_LIFELINE;
	public static String NodeModelName = N3Messages.PALETTE_NODE;
	public static String DeviceModelName = N3Messages.PALETTE_DEVICE;
	public static String ExecutionEnvironmentModelName = N3Messages.PALETTE_EXECUTIONENVIRONMENT;
	public static String DeploymentSpecificationModelName = N3Messages.PALETTE_DEPLOYMENTSPECIFICATION;
	public static String StateLifelineModelName = N3Messages.PALETTE_STATELIFELINE;
	//20080721IJS
	public static String RequirementModelName = N3Messages.PALETTE_REQUIRMENT;
	//2008040301 PKY E


	private int newUseCaseDiagramCount = 1;
	private int newpackageCount = 1;
	private int newUseCaseCount = 1;
	private int newActorCount = 1;
	private int newBoundaryCount = 1;
	private int newCollaborationCount = 1;
	private int newClassCount = 1;
	private int newInterfaceCount = 1;
	private int newEnumerationCount = 1;
	private int newExceptionHandlerCount = 1;
	private int newFinalActivityCount = 1;
	private int newFinalActiionCount = 1;
	private int newSendModelCount = 1;
	private int newReceiveModelCount = 1;
	private int newObjectNodeModelNameCount = 1;
	private int newCentralBufferNodeModelNameCount = 1;
	private int newDataStoreModelNameCount = 1;
	private int newPartitionModelNameCount = 1;
	private int newStrcuturedActivityNameCount = 1;
	private int newPartModellNameCount = 1;
	private int newLifeLineModellNameCount = 1;
	private int newComponentModelNameCount = 1;
	private int newArtfifactModelNameCount = 1;
	private int newStateModelNameCount = 1;
	private int newStrcuturedStateModelNameCount = 1;
	private int newNodeModelNameCount = 1;
	private int newDeviceModelNameCount = 1;

	private int newExecutionEnvironmentModelNameCount = 1;
	private int newDeploymentSpecificationModelNameCount = 1;

	private int newStateLifelineModelNameCount = 1;

	private int newLifeLineModelNameCount = 1;
	//20080721IJS
	private int newRequirementModelNameCount = 1;
	
	
//	 public void setRefModel(Object p) {
//		 if(p instanceof ComponentModel){
//			 this.rank = 1;
//		 }
//		 else if(p instanceof N3EditorDiagramModel){
//			 this.rank = 0;
//		 }
//		 
//		 super.setRefModel(p);
//	 }
	public UMLTreeParentModel(String name) {
		super(name);
		children = new ArrayList();
	}

	public void addChild(UMLTreeModel child) {
		children.add(child);
		child.setParent(this);
	}

	public void removeChild(UMLTreeModel child) {
		children.remove(child);
		child.setParent(null);
	}

//	V1.01 WJH E 080501 S 다른곳에서 이 함수를 호출하는 부분이 없어서 함수 자체 수정
	public UMLTreeModel searchChildModel(String type,String text,boolean isCaseSensitive){
		if(text != null && !text.equals("")){
			for(int i=0;i<this.getChildren().length;i++){
				UMLTreeModel ut = (UMLTreeModel)this.getChildren()[i];
				System.out.println(text);
				System.out.println(((UMLModel)ut.getRefModel()).getUMLDataModel().getId());
//				V1.02 WJH E 080522 S 타입 추가 변경
				if(type.equals("id")){
					if(((UMLModel)ut.getRefModel()).getUMLDataModel().getId().equals(text)){
						return ut;
					}
				}
				else if(type.equals("name")){
					String newName = ((UMLModel)ut.getRefModel()).getUMLDataModel().getFullName((UMLTreeModel)ut, ut.getName());
					if(newName.equals(text)){
						return ut;
					}
				}
//				V1.02 WJH E 080522 E 타입 추가 변경
				if(ut instanceof UMLTreeParentModel){

				}

				if(ut.getModelType()==0){
					PackageTreeModel ptm = (PackageTreeModel)ut;
					UMLTreeModel temp = null;
					temp = ptm.searchChildModel(type, text, isCaseSensitive);
					if(temp != null)
						return temp;    			
				}
			}
		}
		return null;    	
	}
//	V1.01 WJH E 080501 E 다른곳에서 이 함수를 호출하는 부분이 없어서 함수 자체 수정

	//2008040401PKY S "패키지 오른쪽 클릭 시 OpenDiagram 메뉴 추가    여러 다이어그램이있을경우 리스트 나오도록"
	public void diagramList(FinalPackageEditPart finalPackageEditPart ){
		for(int i=0;i<this.getChildren().length;i++){
			UMLTreeModel ut = (UMLTreeModel)this.getChildren()[i];
			System.out.print("");
		}

	}
	//2008040401PKY S "패키지 오른쪽 클릭 시 OpenDiagram 메뉴 추가    여러 다이어그램이있을경우 리스트 나오도록"
	public UMLTreeModel[] getChildren() {
		return (UMLTreeModel[]) children.toArray(new UMLTreeModel[children.size()]);
	}
	
	public void modChildrenItem(UMLTreeModel o, UMLTreeModel n) {
		int index = children.indexOf(o);
		children.remove(index);
		children.add(index, n);
	}
	
	public List getChildrenList() {
		return children;
	}

	public boolean hasChildren() {
		return children.size() > 0;
	}


	public int getNewUseCaseDiagramCount() {
		return this.newUseCaseDiagramCount++;
	}

	public int getNewPackageCount() {
		return this.newpackageCount++;
	}

	public int getNewUseCaseCount() {
		return this.newUseCaseCount++;
	}

	public int getNewActorCountCount() {
		return this.newActorCount++;
	}

	public int getNewBoundaryCountCount() {
		return this.newBoundaryCount++;
	}

	public int getNewCollaborationCountCount() {
		return this.newCollaborationCount++;
	}

	public int getNewClassCountCount() {
		return this.newClassCount++;
	}

	public int getNewInterfaceCount() {
		return this.newInterfaceCount++;
	}

	public int getNewEnumerationCount() {
		return this.newEnumerationCount++;
	}

	public int getNewExceptionHandlerCount() {
		return this.newExceptionHandlerCount++;
	}

	public int getNewFinalActivityCount() {
		return this.newFinalActivityCount++;
	}

	public int getNewFinalActiionCount() {
		return this.newFinalActiionCount++;
	}

	public int getNewSendModelCount() {
		return this.newSendModelCount++;
	}

	public int getNewReceiveModelCount() {
		return this.newReceiveModelCount++;
	}

	public int getNewFinalObjectNodeModelCount() {
		return this.newObjectNodeModelNameCount++;
	}

	public int getNewCentralBufferNodeModelCount() {
		return this.newCentralBufferNodeModelNameCount++;
	}

	public int getNewDataStoreModelCount() {
		return this.newDataStoreModelNameCount++;
	}

	public int getNewPartitionModelNameCount() {
		return this.newPartitionModelNameCount++;
	}

	public int getNewStrcuturedActivityModelNameCount() {
		return this.newStrcuturedActivityNameCount++;
	}

	public int getNewPartModellNameCount() {
		return this.newPartModellNameCount++;
	}

	public int getNewLifeLineModellNameCount() {
		return this.newLifeLineModellNameCount++;
	}

	public int getNewComponentModelNameCount() {
		return this.newComponentModelNameCount++;
	}

	public int getNewArtfifactModelNameCount() {
		return this.newArtfifactModelNameCount++;
	}

	public int getNewStateModelNameCount() {
		return this.newStateModelNameCount++;
	}

	public int getNewStrcuturedStateModelNameCount() {
		return this.newStrcuturedStateModelNameCount++;
	}
	public int getNewNodeModelNameCount() {
		return this.newNodeModelNameCount++;
	}
	public int getNewDeviceModelNameCount() {
		return this.newDeviceModelNameCount++;
	}
	public int getNewExecutionEnvironmentModelNameCount() {
		return this.newExecutionEnvironmentModelNameCount++;
	}

	public int getNewDeploymentSpecificationModelNameCount() {
		return this.newDeploymentSpecificationModelNameCount++;
	}

	public int getNewStateLifelineModelNameCount() {
		return this.newStateLifelineModelNameCount++;
	}

	public int getNewLifeLineModelNameCount() {
		return this.newLifeLineModelNameCount++;
	}
	//20080721IJS
	public int getNewRequirementModelNameCount() {
		return newRequirementModelNameCount++;
	}

	//20080811IJS
	public UMLTreeModel searchId(String id){
		for(int i=0;i<this.getChildren().length;i++){
			UMLTreeModel ut = (UMLTreeModel)this.getChildren()[i];
			UMLModel um = (UMLModel)ut.getRefModel();
			if(um!=null){
				String reId = um.getUMLDataModel().getId();
				if(id.equals(reId)){
					return ut;
				}
				if(ut instanceof UMLTreeParentModel){


					UMLTreeModel utm = 	((UMLTreeParentModel)ut).searchId(id);
					if(utm!=null)
						return utm;

				}
			}

		}
		return null;
	}

	public void writeModel(StringBuffer sb){
		try{
			if(!(this instanceof RootTreeModel)){
				UMLModel um = (UMLModel)this.getRefModel();
				//20080811IJS 시작
				if(this.isLink() && !TeamProjectManager.getInstance().isLinkSave()){
					sb.append(ModelManager.getInstance().writeLinkModel(um,this.linkPath));
					return;
				}
				else
					sb.append(ModelManager.getInstance().writeModel(um,null));
				//20080811IJS 끝
			}
			for(int i=0;i<this.getChildren().length;i++){
				UMLTreeModel ut = (UMLTreeModel)this.getChildren()[i];
				if(ut instanceof UMLTreeParentModel){
					UMLTreeParentModel up = (UMLTreeParentModel)ut;	
					up.writeModel(sb);
				}
				else{
					UMLModel um1 = (UMLModel)ut.getRefModel();
					sb.append(ModelManager.getInstance().writeModel(um1,null));

				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}


	public void writeJava(){
		try{
			if(!(this instanceof RootTreeModel)){
				UMLModel um = (UMLModel)this.getRefModel();
				SourceManager.getInstance().writeJava(um);
			}
			for(int i=0;i<this.getChildren().length;i++){
				UMLTreeModel ut = (UMLTreeModel)this.getChildren()[i];
				if(ut instanceof UMLTreeParentModel){
					UMLTreeParentModel up = (UMLTreeParentModel)ut;	
					up.writeJava();
				}
				else{
					ut.writeJava();
//					UMLModel um1 = (UMLModel)ut.getRefModel();
//					SourceManager.getInstance().writeJava(um1);
//					sb.append(ModelManager.getInstance().writeModel(um1,null));

				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	public void writeH(){
		try{
			if(!(this instanceof RootTreeModel)){
				UMLModel um = (UMLModel)this.getRefModel();
				SourceManager.getInstance().writeH(um);
			}
			for(int i=0;i<this.getChildren().length;i++){
				UMLTreeModel ut = (UMLTreeModel)this.getChildren()[i];
				if(ut instanceof UMLTreeParentModel){
					UMLTreeParentModel up = (UMLTreeParentModel)ut;	
					up.writeH();
				}
				else{
					ut.writeH();
//					UMLModel um1 = (UMLModel)ut.getRefModel();
//					SourceManager.getInstance().writeJava(um1);
//					sb.append(ModelManager.getInstance().writeModel(um1,null));

				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

//	public java.util.ArrayList getDiagrams(){
//	java.util.ArrayList a
//	}


	public UMLTreeParentModel getSearchUMLTreeModel(String path){
		String[] arr = null;
		if(path==null){
			arr = new String[1];
			arr[0]= "default";
		}
		else
			arr = path.split(",");

		StringBuffer sb = null;
//		boolean isSearch = false;
		boolean isEnd = true;
		UMLTreeParentModel up = null;

		for(int i=0;i<this.getChildren().length;i++){
			UMLTreeModel ut = (UMLTreeModel)this.getChildren()[i];
			if(ut instanceof UMLTreeParentModel){
				if(arr[0].equals(ut.getName())){
//					isSearch = true;
					up = (UMLTreeParentModel)ut;
					sb = new StringBuffer();
					if(arr.length>1){
						isEnd = false;
						for(int k=1;k<arr.length;k++){
							sb.append(arr[k]);
							if(k<arr.length-1){
								sb.append(",");
							}
						}

					}
					break;

				}
			}
		}
		if(up==null){
			UMLTreeParentModel parent = this;
			PackageTreeModel to1 = null;
			for(int k=0;k<arr.length;k++){
				String name = arr[k];
				to1 = new PackageTreeModel(name);
				UMLModel finalPackageModel = null;

				finalPackageModel = new FinalPackageModel();

				to1.setRefModel(finalPackageModel);
				finalPackageModel.setName(name);
				finalPackageModel.setTreeModel(to1);
				to1.setParent(parent);
				parent.addChild(to1);
				parent = to1;
			}
			return to1;
		}
		else{
			if(isEnd){
				return up;
			}
			else{
				return up.getSearchUMLTreeModel(sb.toString());
			}
		}



	}
	//20080721IJS
	public void searchReqIDModel(String id,String type){

		for(int i=0;i<this.getChildren().length;i++){
			UMLTreeModel ut = (UMLTreeModel)this.getChildren()[i];

			String type1 = ProjectManager.getInstance().getModelTypeName(ProjectManager.getInstance().getModelType((UMLModel)ut.getRefModel(), -1));
			UMLModel um = (UMLModel)ut.getRefModel();
			//PKY 08081101 S RequirementID 여러 개 넣을 수 있도록 개선
			if(um.getReqID().lastIndexOf(",")>=0){
				String[] reqId=um.getReqID().split(",");
				for(int k = 0 ; k < reqId.length; k++){
					if(type==null){
						if( !id.trim().equals("") && id.equals(reqId[k])){
							ProjectManager.getInstance().getSearchModel().add(ut);
						}
					}
					else if(type1.equals(type)){
						if( id.trim().equals("") && id.equals(reqId[k])){
							ProjectManager.getInstance().getSearchModel().add(ut);
						}
					}
				}
			}else{
				if(type==null){
					if( !id.trim().equals("") && id.equals(um.getReqID())){
						ProjectManager.getInstance().getSearchModel().add(ut);
					}
				}
				else if(type1.equals(type)){
					if(id.trim().equals("") && id.equals(um.getReqID())){
						ProjectManager.getInstance().getSearchModel().add(ut);
					}
				}
			}
			//PKY 08081101 E RequirementID 여러 개 넣을 수 있도록 개선

			if(ut instanceof UMLTreeParentModel){
				UMLTreeParentModel utp = (UMLTreeParentModel)ut;
				utp.searchReqIDModel(id,type);
			}

		}

	}

	//PKY 08080501 S RequirementID를 다이얼로그 리스트로 보여주도록 
	public void searchReqIDModel(){

		for(int i=0;i<this.getChildren().length;i++){
			UMLTreeModel ut = (UMLTreeModel)this.getChildren()[i];
			UMLModel um = (UMLModel)ut.getRefModel();

			if(um.getReqID()!=null&&!um.getReqID().trim().equals("")){
				boolean doubleCheck=false;
				//PKY 08081101 S RequirementID 여러 개 넣을 수 있도록 개선
				if(um.getReqID().lastIndexOf(",")==-1){
					for(int j = 0; j < ProjectManager.getInstance().getReqIdList().size() ; j ++){
						if(((String)ProjectManager.getInstance().getReqIdList().get(j)).trim().equals(um.getReqID())){
							doubleCheck=true;
						}
					}
					if(doubleCheck==false)
						ProjectManager.getInstance().getReqIdList().add(um.getReqID());
				}
				//PKY 08081101 E RequirementID 여러 개 넣을 수 있도록 개선
			}


			if(ut instanceof UMLTreeParentModel){
				UMLTreeParentModel utp = (UMLTreeParentModel)ut;
				utp.searchReqIDModel();
			}
		}

	}
	
	public void searchCoreDiagramIdModel2(String id){
		for(int i=0;i<this.getChildren().length;i++){
			UMLTreeModel ut = (UMLTreeModel)this.getChildren()[i];
			if(ut.getRefModel() instanceof N3EditorDiagramModel){
				N3EditorDiagramModel nd = (N3EditorDiagramModel)ut.getRefModel();
				nd.searchCoreDiagramIdModel2(id);
//				nd.setPropertyValue("ID_NAME",nd.getUMLTreeModel().getParent().getName()+" diagram");//khg 2010.05.19 워크스페이스와 다이어그램 이름 일치 작업
			}
			if(ut instanceof UMLTreeParentModel){
				UMLTreeParentModel utp = (UMLTreeParentModel)ut;
				utp.searchCoreDiagramIdModel2(id);
			}
		}
		
	}
	
	public void searchCoreDiagramIdModel(String id){
		for(int i=0;i<this.getChildren().length;i++){
			UMLTreeModel ut = (UMLTreeModel)this.getChildren()[i];
			if(ut.getRefModel() instanceof N3EditorDiagramModel){
				N3EditorDiagramModel nd = (N3EditorDiagramModel)ut.getRefModel();
				nd.searchCoreDiagramIdModel(id);
//				nd.setPropertyValue("ID_NAME",nd.getUMLTreeModel().getParent().getName()+" diagram");//khg 2010.05.19 워크스페이스와 다이어그램 이름 일치 작업
			}
			if(ut instanceof UMLTreeParentModel){
				UMLTreeParentModel utp = (UMLTreeParentModel)ut;
				utp.searchCoreDiagramIdModel(id);
			}
		}
		
	}
	
	
	public void searchComponentDiagramIdModel(String id){
		for(int i=0;i<this.getChildren().length;i++){
			UMLTreeModel ut = (UMLTreeModel)this.getChildren()[i];
			if(ut.getRefModel() instanceof N3EditorDiagramModel){
				N3EditorDiagramModel nd = (N3EditorDiagramModel)ut.getRefModel();
				nd.searchComponentDiagramIdModel(id);
				nd.setPropertyValue("ID_NAME",nd.getUMLTreeModel().getParent().getName()+" diagram");//khg 2010.05.19 워크스페이스와 다이어그램 이름 일치 작업
			}
			if(ut instanceof UMLTreeParentModel){
				UMLTreeParentModel utp = (UMLTreeParentModel)ut;
				utp.searchComponentDiagramIdModel(id);
			}
		}
		
	}

	//PKY 08080501 E RequirementID를 다이얼로그 리스트로 보여주도록 

	public void searchModel(String type,String text,boolean isCase,boolean isWwo, boolean isDesc){
		if(ProjectManager.getInstance().isSearchModel()){
			for(int i=0;i<this.getChildren().length;i++){
				UMLTreeModel ut = (UMLTreeModel)this.getChildren()[i];

				String type1 = ProjectManager.getInstance().getModelTypeName(ProjectManager.getInstance().getModelType((UMLModel)ut.getRefModel(), -1));
				if(type.equals("All") || type1.equals(type)){
					UMLModel um = (UMLModel)ut.getRefModel();
					if(isDesc){

						if(um.getDesc()!=null && um.getDesc().indexOf(text)!=-1){
							ProjectManager.getInstance().getSearchModel().add(ut);
						}
					}
					else{
						if(isCase==false){
							if(!isWwo){
								if(um.getName().indexOf(text)>=0){

									ProjectManager.getInstance().getSearchModel().add(ut);

								}

							}
							else if(um.getName().equals(text)){

								ProjectManager.getInstance().getSearchModel().add(ut);

							}
						}
						else{
//							if(!isWwo){
//							if(ut.getName().indexOf(text)>=0){

//							ProjectManager.getInstance().getSearchModel().add(ut);

//							}

//							}
							if(um.getName().equalsIgnoreCase(text)){

								ProjectManager.getInstance().getSearchModel().add(ut);

							}

						}
					}
				}
				if(ut instanceof UMLTreeParentModel){
					UMLTreeParentModel utp = (UMLTreeParentModel)ut;
					utp.searchModel(type, text, isCase,isWwo,isDesc);
				}

			}
		}
		else{
			for(int i=0;i<this.getChildren().length;i++){
				UMLTreeModel ut = (UMLTreeModel)this.getChildren()[i];
				if(ut.getRefModel() instanceof N3EditorDiagramModel){
					N3EditorDiagramModel nd = (N3EditorDiagramModel)ut.getRefModel();
					nd.searchModel(type, text, isCase, isWwo);
				}
				if(ut instanceof UMLTreeParentModel){
					UMLTreeParentModel utp = (UMLTreeParentModel)ut;
					utp.searchModel(type, text, isCase,isWwo,isDesc);
				}
			}
		}
		return ;

	}

	//20080721IJS
	public void setNewRequirementModelNameCount(int newRequirementModelNameCount) {
		this.newRequirementModelNameCount = newRequirementModelNameCount;
	}
	//20080811IJS 시작
	public boolean isLink() {
		return isLink;
	}

	public void setLink(boolean isLink) {
		this.isLink = isLink;
	}
	//20080811IJS 끝

	public String getLinkPath() {
		return linkPath;
	}

	public void setLinkPath(String linkPath) {
		this.linkPath = linkPath;
	}

	public int getIsLinkType() {
		return isLinkType;
	}

	public void setIsLinkType(int isLinkType) {
		this.isLinkType = isLinkType;
	}
	
	public UMLTreeParentModel clone(UMLTreeParentModel parent){
		N3EditorDiagramModel diagram = (N3EditorDiagramModel)ProjectManager.getInstance().getUMLEditor().getDiagram();
		if(parent!=null){
			for(int i=0;i<this.getChildren().length;i++){
				Object obj = this.getChildren()[i];
				if(obj instanceof ComponentLibModel){
					ComponentLibModel clm = (ComponentLibModel)obj;
					diagram.getCompName(clm.getName());
//					StrcuturedPackageTreeLibModel to1 = new StrcuturedPackageTreeLibModel(model.getName());
					
				}
			}
		}
		
		return null;
	}
	
	public String getCompName(String name){
		boolean isEqName = false;
		String tname = "";
		for(int i1=0;i1<999;i1++){
			isEqName = false;
			if(i1==0){
				tname = name;
			}
			else 
			tname = name+i1;
			for(int i=0;i<this.getChildren().length;i++){
				Object obj = this.getChildren()[i];
				if(obj instanceof UMLTreeModel){
					UMLTreeModel ut = (UMLTreeModel)obj;
				if(ut.getRefModel() instanceof ComponentModel){
					ComponentModel cep = (ComponentModel)ut.getRefModel();
					if(tname.equals(cep.getName())){
						isEqName = true;
						break;
					}
				}
				}
			}
			if(!isEqName)
				break;
		}
		return tname;
	}
	
	public OPRoSProjectInfo getOPRoSProjectInfo(){
		OPRoSProjectInfo info = null;
//		if(info==null)
			info = new OPRoSProjectInfo();
//		this.getCmpFolder()
//		info.setBinFolder(this.getCmpFolder());
//		info.setImplLanguage(this.getimplementLangString());
//
		IWorkspace workspace = ResourcesPlugin.getWorkspace();//워크스페이스 핸들
		IWorkspaceRoot root = workspace.getRoot();//워크스페이스 루트핸들
		info.setLocation(root.getLocation().toOSString());
	    for(int i=0;i<this.getChildren().length;i++){
	    	UMLTreeModel ut = this.getChildren()[i];
	    	if(ut.getRefModel() instanceof AtomicComponentModel)
	    	info.addComponent(ut.getName());
	    }
		info.setPrjName(this.getName());
//		info.setSrcFolder(this.getCmpFolder());
		
		
		
		return info;
	}
	
	
	




}
