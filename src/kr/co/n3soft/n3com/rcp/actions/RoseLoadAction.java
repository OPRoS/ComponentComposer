package kr.co.n3soft.n3com.rcp.actions;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import kr.co.n3soft.n3com.dnd.TreeSimpleFactory;
import kr.co.n3soft.n3com.lang.N3Messages;
import kr.co.n3soft.n3com.model.comm.ClassModel;
import kr.co.n3soft.n3com.model.comm.FinalPackageModel;
import kr.co.n3soft.n3com.model.comm.LineModel;
import kr.co.n3soft.n3com.model.comm.RoseModel;
import kr.co.n3soft.n3com.model.comm.RoseObject;
import kr.co.n3soft.n3com.model.comm.StrcuturedPackageTreeModel;
import kr.co.n3soft.n3com.model.comm.TypeRefModel;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.component.ComponentModel;
import kr.co.n3soft.n3com.model.diagram.N3EditorDiagramModel;
import kr.co.n3soft.n3com.model.diagram.UMLDiagramModel;
import kr.co.n3soft.n3com.model.umlclass.AttributeEditableTableItem;
import kr.co.n3soft.n3com.model.umlclass.DependencyLineModel;
import kr.co.n3soft.n3com.model.umlclass.FinalClassModel;
import kr.co.n3soft.n3com.model.umlclass.InterfaceModel;
import kr.co.n3soft.n3com.model.umlclass.OperationEditableTableItem;
import kr.co.n3soft.n3com.model.usecase.AssociateLineModel;
import kr.co.n3soft.n3com.model.usecase.ExtendLineModel;
import kr.co.n3soft.n3com.model.usecase.FinalActorModel;
import kr.co.n3soft.n3com.model.usecase.GeneralizeLineModel;
import kr.co.n3soft.n3com.model.usecase.IncludeLineModel;
import kr.co.n3soft.n3com.model.usecase.RealizeLineModel;
import kr.co.n3soft.n3com.model.usecase.UseCaseModel;
import kr.co.n3soft.n3com.project.browser.RootTreeModel;
import kr.co.n3soft.n3com.project.browser.UMLTreeModel;
import kr.co.n3soft.n3com.project.browser.UMLTreeParentModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;
import kr.co.n3soft.n3com.rcp.InfoProp;
import kr.co.n3soft.n3com.rcp.RelationshipData;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.jface.action.Action;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchWindow;

import cb.parser.PetalParser;
import cb.petal.Association;
import cb.petal.AssociationViewNew;
import cb.petal.ClassAttribute;
import cb.petal.ClassDiagram;
import cb.petal.DependencyView;
import cb.petal.FocusOfControl;
import cb.petal.ImportView;
import cb.petal.InheritView;
import cb.petal.InheritanceRelationship;
import cb.petal.InterMessView;
import cb.petal.InterObjView;
import cb.petal.InteractionDiagram;
import cb.petal.Link;
import cb.petal.List;
import cb.petal.LogicalCategory;
import cb.petal.Message;
import cb.petal.Module;
import cb.petal.ModuleDiagram;
import cb.petal.ModuleVisibilityRelationship;
import cb.petal.PetalFile;
import cb.petal.RealizeRelationship;
import cb.petal.RealizeView;
import cb.petal.SelfMessView;
import cb.petal.StringLiteral;
import cb.petal.SubSystem;
import cb.petal.UseCase;
import cb.petal.UseCaseCategory;
import cb.petal.UseCaseDiagram;
import cb.petal.UsesRelationship;
import cb.petal.UsesView;

public class RoseLoadAction extends Action {

	IWorkbenchWindow workbenchWindow;
	public static int ID = 0;
	public String FilePath = "";
	private Map sharedModels = new HashMap();
	public HashMap idMap = new HashMap();

	RoseModel usecaseRose = new RoseModel("usecase");
	
	RoseModel classRose = new RoseModel("class");
	
	RoseModel seqRose = new RoseModel("seq");
	
	HashMap associationMap = new HashMap();
	HashMap generalizationMap = new HashMap();
	HashMap keyMap = new HashMap();
	HashMap diagram = new HashMap();
	HashMap stereo = new HashMap();
	private HashMap returnMessage = new HashMap();
	HashMap interfaceTracing = new HashMap();
	HashMap relationship = new HashMap();
	UMLTreeParentModel root = null;
	UMLTreeParentModel parent = null;
	ArrayList emptyModel = new ArrayList();
	HashMap seqMap = new HashMap();
	
	HashMap comDiagram = new HashMap();

	IEditorInput input = null;

	public RoseLoadAction(IWorkbenchWindow window) {
		setId("RoseLoadAction");
		this.setText(N3Messages.POPUP_ROSE_LOAD);//PKY 08090201 S Rose Load를 Rose 2003 Load로 변경
		workbenchWindow = window;
		setImageDescriptor(uuu.Activator
				.getImageDescriptor("/icons/newproject.gif"));
	}

	public void run() {
		try {
			// if(ProjectManager.getInstance().getCurrentProjectName()==null ||
			// ProjectManager.getInstance().getCurrentProjectName().trim().equals("")){
			if (ProjectManager.getInstance().getModelBrowser().getRoot() != null) {
				RootTreeModel getRoot = ProjectManager.getInstance()
						.getModelBrowser().getRoot();
				root = getRoot;
				parent = getRoot;
				FileDialog fsd = new FileDialog(workbenchWindow.getShell(),
						SWT.OPEN);			// V1.02 WJH E 080828 save->open 변경
				fsd.setFilterExtensions(new String[] { "*.mdl", "*.*" });
				fsd.setText("Select Rose Files...");
				fsd.open();
				// LoadProgressRun lr = new LoadProgressRun(true);
				String fileName = fsd.getFileName();
				String path = fsd.getFilterPath();
				if (fileName.equals("") || path.equals(""))
					return;
				FilePath = path + File.separator + fileName;

				readMdl();
				init();
				// ProjectManager.getInstance().setCurrentProjectName(fileName);
				// ProjectManager.getInstance().setCurrentProjectPath(path);
			}

			// ProjectManager.getInstance().getModelBrowser().writeModel(ProjectManager.getInstance().getCurrentProject());
			// ProjectManager.getInstance().getConsole().addMessage("Save
			// complete");
		} catch (Exception e) {
			e.printStackTrace();
		}

		// ProjectManager.getInstance().getModelBrowser().writeModel();
	}
	
	public void init() {		  
		  this.diagram = new HashMap();
		  this.associationMap = new HashMap();
		  
		  this.generalizationMap = new HashMap();
		  this.keyMap = new HashMap();
		  this.seqRose = new RoseModel("seq");
	}
	
	public void init1() {		  
		  this.diagram = new HashMap();
		  this.associationMap = new HashMap();		  
		  this.generalizationMap = new HashMap();		  
	}

	// private String openFileDialog() {
	// FileDialog dialog = new FileDialog(workbenchWindow.getShell(), SWT.OPEN);
	// dialog.setText("shapes");
	// //dialog.setFilterExtensions(new String[] { ".shapes" });
	// return dialog.open();
	// }
	public void readMdl() {
		try {			
			PetalParser parser;
			System.out.println(FilePath);

			parser = PetalParser.createParser(FilePath);
			PetalFile tree = parser.parse();
			UseCaseCategory uc = tree.getUseCaseCategory();
			LogicalCategory lc = tree.getLogicalCategory();
			SubSystem su = tree.getDesign().getRootSubsystem();

			classifyModelUseCaseCategory("", "", uc, null, null, null, null, null, sharedModels);
			parent = root;
			init1();
			classifyModelLogicalView("", "", lc, null, null, null, null, null, sharedModels);
			// ijs mem
			parent = root;
			init1();
			loadSequence();
			java.util.List notations = new ArrayList();
			classifyModelComponentCateogry("",su,null,null,sharedModels,notations);
			System.out.println("node size = " + sharedModels.size());
			init();
			uc = null;
			lc = null;
			su = null;
			System.out.println("메모리===========================>");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void classifyModelUseCaseCategory(String path1, String path2,
			UseCaseCategory uc, Vector useCaseModelList, Vector classModelList,
			Vector useCaseModelDiagramList, Vector classModelDiagramList,
			Vector seqModelDiagramList, Map sharedModels) {

		UMLTreeParentModel packParent = null;
		if (uc != null) {
			cb.petal.List list = uc.getLogicalModels(); // 모델
			cb.petal.List list2 = uc.getLogicalPresentations(); // 다이어그램
			String name = (String) uc.getParameterList().get(0);
			if (name != null && !name.equals("")) {
				String packName = "";
				if(path1 != null && !(path1.equals(""))){
					int index = path1.lastIndexOf(".");
					packName = path1.substring(0, index);
				
					if(packName.equals("")){
						parent = root;
					}
					else{
						UMLTreeParentModel temp = this.searchPackage(root, packName);
						if(temp != null){
							parent = temp;
							packParent = temp;
						}
					}
				}
				ProjectManager.getInstance().addUMLModel(name, parent, null, 0,	-1);				
				if(!(parent instanceof RootTreeModel))
					name = ((UMLModel)parent.getRefModel()).getUMLDataModel().getFullName(parent, parent.getName())+"."+name;
				UMLTreeParentModel temp = this.searchPackage(parent, name);
				if(temp != null){
					parent = temp;
					packParent = temp;
				}
			}
			useCaseModelList = new Vector();
			classModelList = new Vector();
			useCaseModelDiagramList = new Vector();
			classModelDiagramList = new Vector();
			seqModelDiagramList = new Vector();
			HashMap pgkData = new HashMap();
			HashMap pgkData2 = new HashMap();
			UMLDiagramModel model = null;
			for (int i = 0; i < list.size(); i++) {

				Object obj = list.get(i);
				if (obj instanceof UseCaseCategory) {
					UseCaseCategory childUseCaegory = (UseCaseCategory) obj;
					String chname = (String) childUseCaegory.getParameterList().get(0);
					if (path1.toString().equals("")) {
						String n1 = name + "." + chname;
						String n2 = chname;

						this.classifyModelUseCaseCategory(n1, n2,
								childUseCaegory, useCaseModelList,
								classModelList, useCaseModelDiagramList,
								classModelDiagramList, seqModelDiagramList,
								sharedModels);

					} else {
						String n1 = path1 + "." + chname;
						String n2 = path2 + "." + chname;

						this.classifyModelUseCaseCategory(n1, n2,
								childUseCaegory, useCaseModelList,
								classModelList, useCaseModelDiagramList,
								classModelDiagramList, seqModelDiagramList,
								sharedModels);
					}
					continue;
				}
				if (obj instanceof UseCase) {
					UseCase useCase = (UseCase) obj;
					model = new UseCaseModel();
					if(useCase != null)
						setRelationship(useCase);
					// if (useCase.getSuperclassList() != null) {
					// this.generalizationMap.put(useCase.getQuid(), useCase
					// .getSuperclassList());
					// }

					try {
						ArrayList attributes = useCase.getProperties("attributes");
						if (attributes != null) {
							if (attributes.size() == 1) {
								List attribute1 = (List) attributes.get(0);
								cb.petal.Attribute attribute = (cb.petal.Attribute) attribute1.get(0);
								ArrayList arrayList = attribute
										.getProperties("value");
								if (arrayList != null && arrayList.size() == 1) {
									Object va1 = arrayList.get(0);
									if (va1 instanceof cb.petal.Value) {
										cb.petal.Value va = (cb.petal.Value) va1;
										String uid = va.getStringValue();
										if (uid != null
												&& !uid.trim().equals("")) {
											model.getUMLDataModel().setId(uid);
										}
									}
								}
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}

					if (path1.toString().length() > 0) {
						model.setName(path1.toString() + "."+ useCase.getParameterList().get(0));
					} else {
						model.setName((String) useCase.getParameterList().get(0));
					}
					model.setStereotype(useCase.getStereotype());
					// model.setLocation(useCase.get)
					if (useCase.getDocumentation() != null
							&& !useCase.getDocumentation().trim().equals(""))
						model.setDesc(this.getDoc(useCase
								.getProperties("documentation")));
					//					
					// ijs keyMap
					//					
					// model.getUMLDataModel().setId(this.setIdMap(model.getName(),model.getUMLDataModel().getId()));
					// System.out.println(model.getUMLDataModel().getId());
					sharedModels.put(model.getUMLDataModel().getId(), model);
					keyMap.put(useCase.getQuid(), model.getUMLDataModel().getId());

				} else if (obj instanceof cb.petal.Class) {
					// if (obj instanceof cb.petal.Class) {
					cb.petal.Class actor = (cb.petal.Class) obj;
					System.out.println(actor.getStereotype());
					if(actor != null)
						setRelationship(actor);
						
					if(actor.getStereotype() != null && actor.getStereotype().equals("Actor"))
						model = new FinalActorModel();
					else if(actor.getStereotype() != null && actor.getStereotype().equals("Interface")){
						model = new InterfaceModel();
					}
					else{
						model = new FinalClassModel();
					}
					seqMap.put(actor.getQuid(), model);
					if(model != null){
						 if (actor.getStereotype() != null && actor.getStereotype().equals("Actor")) {
							 if (actor.getSuperclassList() != null) {
								 this.generalizationMap.put(actor.getQuid(), actor.getSuperclassList());
							 }
						 }
	
						if (path1.toString().length() > 0) {
							model.setName(path1.toString() + "."+ actor.getParameterList().get(0));
						} else {
							model.setName((String) actor.getParameterList().get(0));
						}
						
						model.setStereotype(actor.getStereotype());
						if(!(model instanceof FinalActorModel)){
							TypeRefModel classModel = null;
							if(model instanceof FinalClassModel){
								classModel = ((FinalClassModel)model).getClassModel();
							}
							else if(model instanceof InterfaceModel){
								classModel = (TypeRefModel)((InterfaceModel)model).getClassModel();
							}
							ArrayList attr = new ArrayList();
							ArrayList oper = new ArrayList();
							if(actor.getClassAttributeList() != null && actor.getClassAttributeList().size()>0){
								List attrList = actor.getClassAttributeList();
								for(int j=0; j<attrList.size(); j++){
									ClassAttribute cattr = (ClassAttribute)attrList.get(j);
																		
									AttributeEditableTableItem newItem = new
									AttributeEditableTableItem(new Integer(0), cattr.getName(), "String", "");
									
									attr.add(newItem);
//									((FinalClassModel)model).getClassModel().getAttributes().add(attr);
								}
								if(attr.size()>0){
									classModel.setAttributes(attr);									 
								}
							}
							if(actor.getOperationList() != null && actor.getOperationList().size()>0){
								List operList = actor.getOperationList();
								for(int j=0; j<operList.size(); j++){									
									cb.petal.Operation coper = (cb.petal.Operation)operList.get(j); 
									int index = setScope(coper.getExportControl());									
																		
									OperationEditableTableItem newItem = new OperationEditableTableItem(index, coper.getNameParameter(), "String", "");
									
									oper.add(newItem);
								}
								if(oper.size()>0){
									classModel.setOperations(oper);
								}
							}
							if(classModel.isMode()) 
								ProjectManager.getInstance().autoSize(classModel);							
						}
						// actor.getSuperclasses()
						// model.setPackage("Usecase Model");
						// model.setParentSharedID("usecasemodelmanagerid");
						if (actor.getDocumentation() != null
								&& !actor.getDocumentation().trim().equals(""))
							model.setDesc(this.getDoc(actor
									.getProperties("documentation")));
						// ijs keyMap
						// System.out.println(this.setIdMap(model.getName(),model.getUMLDataModel().getId()));
						// model.getUMLDataModel().setId(this.setIdMap(model.getName(),model.getUMLDataModel().getId()));
						sharedModels.put(model.getUMLDataModel().getId(), model);
						keyMap.put(actor.getQuid(), model.getUMLDataModel().getId());
					}

				} else if (obj instanceof cb.petal.Association) {
					cb.petal.Association association = (cb.petal.Association) obj;					
					System.out.println();
					if(getRelationship(association))
						associationMap.put(association.getQuid(), association);
					
//					sharedModels.put(linemodel.getId(), linemodel);
//					keyMap.put(association.getQuid(), linemodel.getId());
					continue;
					// association.getFirstClient().getQuid();

				} else if (obj instanceof cb.petal.Mechanism) {
					cb.petal.Mechanism mechanism = (cb.petal.Mechanism) obj;
					List logicalModels = mechanism.getLogicalModels();
					for (int k = 0; k < logicalModels.size(); k++) {
						cb.petal.Object object = (cb.petal.Object) logicalModels
								.get(k);
						List collaborators = object.getCollaborators();
						RoseObject ro = new RoseObject();

						if (collaborators != null) {
							for (int k1 = 0; k1 < collaborators.size(); k1++) {
								Link link = (Link) collaborators.get(k1);
								String quidu = link
										.getPropertyAsString("quidu");
								ro.link.add(quidu);
								List messages = link.getMessages();
								for (int k2 = 0; k2 < messages.size(); k2++) {
									Message message = (Message) messages.get(k2);
									ro.msg.put(message.getQuid(), message);
								}
							}
						}
						try {
							ro.classGuid = object.getQuidu();
							keyMap.put(object.getQuid(), ro);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					// associationMap.put(association.getQuid(), association);
				}
				UMLTreeModel to1 = null;
				if (packParent != null) {
					UMLModel um = model;
					String name1 = um.getName();
					if(name1.indexOf(".")>0){
						name1 = name1.substring(name1.lastIndexOf(".")+1, name1.length());
					}
					to1 = new StrcuturedPackageTreeModel(name1);
					to1.setRefModel(model);
					um.setName(name1);
					um.setTreeModel(to1);
					to1.setParent(packParent);
					packParent.addChild(to1);
//					dModel.addChild(um);
//					to1.addN3UMLModelDeleteListener(dModel);
				}
			}
			ProjectManager.getInstance().getModelBrowser().refresh(packParent);

			for (int i = 0; i < list2.size(); i++) {

				Object obj = list2.get(i);
				System.out.println(obj.getClass());
				if (obj instanceof cb.petal.UseCaseDiagram) {
					// cb.petal.ClassView cv = uc.getProperties("ClassView");
					cb.petal.UseCaseDiagram useCaseDiagram = (cb.petal.UseCaseDiagram) obj;
					String chname = (String) useCaseDiagram.getParameterList().get(0);
					// UsecaseDiagramSharedDataModel model = new
					// UsecaseDiagramSharedDataModel();

					N3EditorDiagramModel dModel = ProjectManager.getInstance().addUMLDiagram(chname, packParent, null, 1, false, 8);
					usecaseEtcLines(dModel, useCaseDiagram);
//					N3EditorDiagramModel dModel = ProjectManager.getInstance().tempDiagram;//ProjectManager.getInstance().addUMLDiagram(chname, packParent, null, 1, true, 8);
//					System.out.println(ProjectManager.getInstance().tempDiagram);
//					if (path1.toString().equals("")) {
//						dModel.setName("" + chname);
//					} else {
//						dModel.setName("" + chname);
//					}

					// model.setParentSharedID("usecasemodelmanagerid");
					// ijs keyMap
					dModel.getUMLDataModel().setId(this.setIdMap(dModel.getName(), dModel.getUMLDataModel().getId()));
//					UMLEditor edit = new UMLEditor();
//					ProjectManager.getInstance().getUMLEditor().setUMLDiagramModel(dModel);
					sharedModels.put(dModel.getUMLDataModel().getId(), dModel);
					keyMap.put(useCaseDiagram.getQuid(), dModel.getUMLDataModel().getId());
					diagram.put(dModel.getUMLDataModel().getId(), dModel);

					List item = useCaseDiagram.getItems();
					UMLDiagramModel tempModel = null;
					String getname = "";
					if (item.size() > 0) {
						for (int k = 0; k < item.size(); k++) {
							boolean bModel = false;
							Object obj1 = item.get(k);
							if (obj1 instanceof cb.petal.ClassView) {
								cb.petal.ClassView cv = (cb.petal.ClassView) obj1;								
//								sharedModels.get(cv.getQuidu());
//								tempModel = new FinalActorModel();
//								
								String value = (String) keyMap.get(cv.getQuidu());
								Object tempObj = sharedModels.get(value);
								tempModel = (UMLDiagramModel)tempObj;
//								tempModel.setStereotype(cv.getStereotype().toString());								
								tempModel.setLocation(new Point(cv.getLocation().getFirstValue()/5, cv.getLocation().getSecondValue()/5));
								
								tempModel.getUMLDataModel().setId(this.setIdMap(tempModel.getName(),tempModel.getUMLDataModel().getId()));

							} else if (obj1 instanceof cb.petal.UseCaseView) {
//								tempModel = new UseCaseModel();
								cb.petal.UseCaseView uv = (cb.petal.UseCaseView) obj1;
//								// model = new
//								// kr.co.n3soft.n3com.model.comm.ClassModel();
//								tempModel.setName(uv.getLabel().getLabel()); // 이름
								String value = (String) keyMap.get(uv.getQuidu());
								Object tempObj = sharedModels.get(value);
								tempModel = (UMLDiagramModel) tempObj;
								tempModel.setLocation(new Point(uv.getLocation().getFirstValue()/5, uv.getLocation().getSecondValue()/5));

								tempModel.getUMLDataModel().setId(this.setIdMap(tempModel.getName(),tempModel.getUMLDataModel().getId()));
//								 ArrayList attr = uv.getProperties("Attribute");
//								 ArrayList oper = uv.getProperties("Operation");
//								 ((UseCaseModel)tempModel).setAttribute(attr);
//								 ((UseCaseModel)tempModel).setOperation(oper);
								// dModel.addChild(model);
//							} else if (obj1 instanceof cb.petal.AssociationViewNew) {
//								cb.petal.AssociationViewNew av = (cb.petal.AssociationViewNew) obj1;
//								LineModel line = new LineModel();
								// line.setSource(av.get);
								// dModel.addChild(line);
							}
							else{
								continue;
							}
//							if (bModel) {
							UMLTreeModel to1 = null;
							if (packParent != null) {
								UMLModel um = tempModel;
//								
								to1 = root.searchChildModel("id", tempModel.getID(), false);
								
//								to1.setRefModel(tempModel);
//								um.setName(name1);
								um.setTreeModel(to1);
								dModel.addChild(um);
								to1.addN3UMLModelDeleteListener(dModel);
							}
//								bModel = false;
//							} else {
//								UMLTreeModel to1 = null;
//								UMLModel um = tempModel;
//								System.out.println(um.getName());
//								to1 = root.searchChildModel("name", getname
//										+ "." + tempModel.getName(), false);
//								if (to1 != null) {
//									um.setTreeModel(to1);
//									dModel.addChild(tempModel);
//									to1.addN3UMLModelDeleteListener(dModel);
//								} else {
//									emptyModel.add(um);
//								}
//							}							
							// model.getUMLDataModel().setId(this.setIdMap(model.getName(),model.getUMLDataModel().getId()));cv.getpro)
						}
						ProjectManager.getInstance().getModelBrowser().refresh(packParent);
						makeLine(dModel);
					}
				}
				else if(obj instanceof cb.petal.ClassDiagram){
					cb.petal.ClassDiagram classDiagram = (cb.petal.ClassDiagram) obj;
					
					String chname = (String) classDiagram.getParameterList().get(0);

					N3EditorDiagramModel dModel = ProjectManager.getInstance().addUMLDiagram(chname, packParent, null, 1, false, 2);

					classEtcLines(dModel, classDiagram);
					
					if (path1.toString().equals("")) {
						dModel.setName("" + chname);
					} else {
						dModel.setName("" + chname);
					}

					dModel.getUMLDataModel().setId(this.setIdMap(dModel.getName(), dModel.getUMLDataModel().getId()));

					sharedModels.put(dModel.getUMLDataModel().getId(), dModel);
					keyMap.put(classDiagram.getQuid(), dModel.getUMLDataModel().getId());
					diagram.put(dModel.getUMLDataModel().getId(), dModel);

					List item = classDiagram.getItems();
					UMLDiagramModel tempModel = null;
					String getname = "";
					if (item.size() > 0) {
						for (int k = 0; k < item.size(); k++) {
							Object obj1 = item.get(k);
							if (obj1 instanceof cb.petal.ClassView) {
								cb.petal.ClassView cv = (cb.petal.ClassView) obj1;								
								String value = (String) keyMap.get(cv.getQuidu());
								Object tempObj = sharedModels.get(value);
								tempModel = (UMLDiagramModel)tempObj;
								if(cv.getStereotype() != null)
									tempModel.setStereotype(cv.getStereotype().toString());								
								tempModel.setLocation(new Point(cv.getLocation().getFirstValue()/5, cv.getLocation().getSecondValue()/5));
//								ArrayList attr = cv.getProperties("Attribute");
//								ArrayList oper = cv.getProperties("Operation");
//								ClassModel cm = new ClassModel();
//								tempModel.setAttribute(attr);
//								tempModel.setOperation(oper);
								tempModel.getUMLDataModel().setId(this.setIdMap(tempModel.getName(),tempModel.getUMLDataModel().getId()));

							} else if (obj1 instanceof cb.petal.UseCaseView) {
								cb.petal.UseCaseView uv = (cb.petal.UseCaseView) obj1;
								String value = (String) keyMap.get(uv.getQuidu());
								Object tempObj = sharedModels.get(value);
								tempModel = (UMLDiagramModel) tempObj;
								tempModel.setLocation(new Point(uv.getLocation().getFirstValue()/5, uv.getLocation().getSecondValue()/5));

								tempModel.getUMLDataModel().setId(this.setIdMap(tempModel.getName(),tempModel.getUMLDataModel().getId()));
								 
								// dModel.addChild(model);
//							} else if (obj1 instanceof cb.petal.AssociationViewNew) {
//								cb.petal.AssociationViewNew av = (cb.petal.AssociationViewNew) obj1;
//								LineModel line = new LineModel();
								// line.setSource(av.get);
								// dModel.addChild(line);
							}
							else{
								continue;
							}

							UMLTreeModel to1 = null;
							if (packParent != null) {
								UMLModel um = tempModel;
//								
								to1 = root.searchChildModel("id", tempModel.getID(), false);
								

								um.setTreeModel(to1);
								dModel.addChild(um);
								to1.addN3UMLModelDeleteListener(dModel);
							}
							// model.getUMLDataModel().setId(this.setIdMap(model.getName(),model.getUMLDataModel().getId()));cv.getpro)
						}
						ProjectManager.getInstance().getModelBrowser().refresh(packParent);
						makeLine(dModel);
					}
					
				}				
				System.out.println(obj.getClass());
				System.out.println("aaaaa=====================");
			}
			
			// if (useCaseModelList.size() > 0
			// || useCaseModelDiagramList.size() > 0) {
			// if (path1.toString().equals("")) {
			// String p1 = ""
			// + ".";
			//					
			// this.usecaseRose.packNames.add(p1);
			// pgkData.put("useCaseModelList", useCaseModelList);
			// pgkData.put("useCaseModelDiagramList",
			// useCaseModelDiagramList);
			// this.usecaseRose.packDatas.add(pgkData);
			// } else {
			// this.usecaseRose.packNames.add(path1);
			// pgkData.put("useCaseModelList", useCaseModelList);
			// pgkData.put("useCaseModelDiagramList",
			// useCaseModelDiagramList);
			// this.usecaseRose.packDatas.add(pgkData);
			// }
			// }

//			 if (classModelList.size() > 0 || classModelDiagramList.size() > 0
//			 || seqModelDiagramList.size() > 0) {
//			 System.out
//			 .println("여기당 ======================================>size"
//			 + classModelList.size());
//			 System.out
//			 .println("여기당 ======================================>size"
//			 + classModelDiagramList.size());
//			 System.out
//			 .println("여기당 ======================================>path"
//			 + path2);
//			 if (path1.toString().equals("")) {
//			 String p1 = "root";
//			 pgkData2.put("classModelList", classModelList);
//								
//			 pgkData2
//			 .put("classModelDiagramList", classModelDiagramList);
//			 pgkData2.put("seqModelDiagramList", seqModelDiagramList);
//			 this.classRose.packDatas.add(pgkData2);
//								
//			 this.classRose.packNames.add(p1);
//			 } else {
//			 pgkData2.put("classModelList", classModelList);
//			 pgkData2.put("classModelDiagramList", classModelDiagramList);
//			 pgkData2.put("seqModelDiagramList", seqModelDiagramList);
//			 this.classRose.packDatas.add(pgkData2);
//			 this.classRose.packNames.add(path2);
//			 }
//			 // this.classRose.packNames.add(path2);
//							
//			 }
//			 if (seqModelDiagramList.size() > 0) {
//			 this.seqRose.packNames.add(path2);
//			 this.seqRose.packDatas.add(seqModelDiagramList);
//			 }
		}
		init1();
	}


	public void classifyModelLogicalView(String path1, String path2,
			LogicalCategory uc, Vector useCaseModelList, Vector classModelList,
			Vector useCaseModelDiagramList, Vector classModelDiagramList,
			Vector seqModelDiagramList, Map sharedModels) {
		
		UMLTreeParentModel packParent = null;
		if (uc != null) {
			cb.petal.List list = uc.getLogicalModels();
			cb.petal.List list2 = uc.getLogicalPresentations();
			String name = (String) uc.getParameterList().get(0);
			
			if (name != null && !name.equals("")) {
				String packName = "";
				if(path1 != null && !(path1.equals(""))){
					int index = path1.lastIndexOf(".");
					packName = path1.substring(0, index);
				
					if(packName.equals("")){
						parent = root;
					}
					else{
						UMLTreeParentModel temp = this.searchPackage(root, packName);
						if(temp != null){
							parent = temp;
							packParent = temp;
						}
					}
				}
				ProjectManager.getInstance().addUMLModel(name, parent, null, 0,	-1);
				if(!(parent instanceof RootTreeModel))
					name = ((UMLModel)parent.getRefModel()).getUMLDataModel().getFullName(parent, parent.getName())+"."+name;
				UMLTreeParentModel temp = this.searchPackage(parent, name);
				if(temp != null){
					parent = temp;
					packParent = temp;
				}
//				ProjectManager.getInstance().addUMLModel(name, parent, null, 0,	-1);
//				UMLTreeModel[] utm = parent.getChildren();
//				for (int j = 0; j < utm.length; j++) {
//					UMLTreeParentModel upm = (UMLTreeParentModel) utm[j];
//					Object obj = upm.getRefModel();
//					if (obj instanceof FinalPackageModel) {
//						UMLTreeParentModel pack = (UMLTreeParentModel) upm;
//						if(pack.getName().equals(name)){
//							parent = pack;
//							packParent = pack;
//						}
//					}
//				}
			}
			
			useCaseModelList = new Vector();
			classModelList = new Vector();
			useCaseModelDiagramList = new Vector();
			classModelDiagramList = new Vector();
			seqModelDiagramList = new Vector();
			HashMap pgkData = new HashMap();
			HashMap pgkData2 = new HashMap();
			String pkgStereoType = uc.getPropertyAsString("stereotype");
			String pkgDoc =this.getDoc(uc.getProperties("documentation"));
			InfoProp infoProp = new InfoProp();
			UMLDiagramModel model = null;
			
			if (pkgStereoType != null && !pkgStereoType.trim().equals("")) {
				infoProp.stereo = pkgStereoType;
			}
			else{
				infoProp.stereo = "";
			}
			
			if (pkgDoc != null && !pkgDoc.trim().equals("")) {
				infoProp.desc = pkgDoc;
			}
			else{
				infoProp.desc = "";
			}
			this.stereo.put(path2, infoProp);
			for (int i = 0; i < list.size(); i++) {
				
				Object obj = list.get(i);
				if (obj instanceof LogicalCategory) {
					LogicalCategory childUseCaegory = (LogicalCategory) obj;
					
					String chname = (String) childUseCaegory.getParameterList().get(0);
					System.out.println("chname11:==============================>"	+ chname);
					
					
					int index = chname.indexOf(".");
					if (chname != null && index > 0) {
						chname = chname.substring(0, index) + "_"
						+ chname.substring(index + 1);
					}
					
					if (path1.toString().equals("")) {
						String n1 = name + "." + chname;
						String n2 = chname;
//						String n1 = chname;
//						String n2 = "root." +  chname;
						keyMap.put(childUseCaegory.getQuid(),n2);
						
						this.classifyModelLogicalView(n1, n2, childUseCaegory,
								useCaseModelList, classModelList,
								useCaseModelDiagramList, classModelDiagramList,
								seqModelDiagramList, sharedModels);
						
						
					} else {
						String n1 = path1 + "."	+ chname;
						String n2 = path2 + "." + chname;
						keyMap.put(childUseCaegory.getQuid(),n2);
						
						this.classifyModelLogicalView(n1, n2, childUseCaegory,
								useCaseModelList, classModelList,
								useCaseModelDiagramList, classModelDiagramList,
								seqModelDiagramList, sharedModels);
						
					}
				}
				if (obj instanceof UseCase) {
					UseCase useCase = (UseCase) obj;
					ArrayList list3 = useCase.getProperties("logical_presentations");
					
					if (useCase.getStereotype() != null	&& 
							useCase.getStereotype().trim().equals("use-case realization")) {
						String tUcrName = "";
						
						if (list3 != null) {
							Vector seq = new Vector();
							String ucrName = (String)useCase.getParameterList().get(0);
							
							if(ucrName!=null ){
								
//								cb.petal.List realizedInterfaces = useCase.getProperties("realized_interfaces");
								ArrayList realizedInterfaces = useCase.getProperties("realized_interfaces");
								if(realizedInterfaces!=null && realizedInterfaces.size()>0){
									cb.petal.List realize_rel_list = (cb.petal.List)realizedInterfaces.get(0);
									if(realize_rel_list!=null && realize_rel_list.size()>0){
										Object robj = realize_rel_list.get(0);
										if(robj instanceof cb.petal.RealizeRelationship){
											cb.petal.RealizeRelationship rRelationship = (cb.petal.RealizeRelationship)robj;
											tUcrName = rRelationship.getSupplier();
											if(tUcrName!=null){
												int index = tUcrName.lastIndexOf("::");
												if(index>1){
													ucrName = tUcrName.substring(index+2);
												}
											}
										}
									}
								}
								else{
									cb.petal.List realize_rel_list = useCase.getSuperclassList();
									if(realize_rel_list!=null && realize_rel_list.size()>0){
										Object robj = realize_rel_list.get(0);
										if(robj instanceof cb.petal.InheritanceRelationship){
											cb.petal.InheritanceRelationship rRelationship = (cb.petal.InheritanceRelationship)robj;
											tUcrName = rRelationship.getSupplier();
											if(tUcrName!=null){
												int index = tUcrName.lastIndexOf("::");
												if(index>1){
													ucrName = tUcrName.substring(index+2);

												}
											}
										}
									}
									else{
										ArrayList visibleModules = useCase.getProperties("visible_modules");
										if(visibleModules!=null && visibleModules.size()>0){
											cb.petal.List realize_rel_list1 = (cb.petal.List)visibleModules.get(0);
											if(realize_rel_list1!=null && realize_rel_list1.size()>0){
												Object robj = realize_rel_list1.get(0);
												if(robj instanceof cb.petal.DependencyRelationship){
													cb.petal.DependencyRelationship rRelationship = (cb.petal.DependencyRelationship)robj;
													tUcrName = rRelationship.getSupplier();
													if(tUcrName!=null){
														int index = tUcrName.lastIndexOf("::");
														if(index>1){
															ucrName = tUcrName.substring(index+2);

														}
													}
												}
											}
										}
									}
								}

							}

							String p1 = path2 + "." + ucrName;
							//ijs
							this.interfaceTracing.put(tUcrName,p1);
							this.seqRose.packNames.add(p1);
							InfoProp infoProp2 = new InfoProp();
							infoProp2.stereo = "";
							infoProp2.desc = "";
							this.stereo.put(p1, infoProp2);
							for (int i1 = 0; i1 < list3.size(); i1++) {
								Object obj1 = list3.get(i1);
								
								if (obj1 instanceof cb.petal.List) {
									cb.petal.List list4 = (cb.petal.List) obj1;
									for (int k = 0; k < list4.size(); k++) {
										Object obj2 = list4.get(k);
										if (obj2 instanceof cb.petal.InteractionDiagram) {
											seq.add(obj2);
										}
									}
								}
							}
							ArrayList list4 = useCase.getProperties("logical_models");
							if (list4 != null) {
								for (int i1 = 0; i1 < list4.size(); i1++) {
									Object obj1 = list4.get(i1);
									if (obj1 instanceof cb.petal.List) {
										cb.petal.List list5 = (cb.petal.List) obj1;
										for (int i2 = 0; i2 < list5.size(); i2++) {
											Object obj5 = list5.get(i2);
											if (obj5 instanceof cb.petal.Mechanism) {
												cb.petal.Mechanism mechanism = (cb.petal.Mechanism) obj5;
												List logicalModels = mechanism.getLogicalModels();
												for (int k = 0; k < logicalModels
												.size(); k++) {
													cb.petal.Object object = (cb.petal.Object) logicalModels.get(k);
													
													RoseObject ro = new RoseObject();
													List collaborators = object.getCollaborators();
													if (collaborators != null) {
														for (int k1 = 0; k1 < collaborators.size(); k1++) {
															Link link = (Link) collaborators.get(k1);
															String quidu = "";
															try{																
															  quidu = link.getPropertyAsString("quidu");
															}
															catch(Exception e){
																System.out.println("ppp");
																e.printStackTrace();
																continue;
															}
															ro.link.add(quidu);
															List messages = link.getMessages();
															if (messages != null) {
																for (int k2 = 0; k2 < messages.size(); k2++) {
																	Message message = (Message) messages.get(k2);
																	ro.msg.put(message.getQuid(), message);
																}
															}
														}
													}
													try{
													ro.classGuid = object.getQuidu();
													keyMap.put(object.getQuid(), ro);
													}
													catch(Exception e){
														System.out.println("ppp");
														e.printStackTrace();
														continue;
													}
													
												}
												
												// associationMap.put(association.getQuid(),
												// association);
												
											}
										}
									}
								}
							}
							this.seqRose.packDatas.add(seq);
							this.classRose.packNames.add(p1);
							pgkData2.put("classModelList", new Vector(0));
							pgkData2.put("classModelDiagramList", new Vector(0));
							pgkData2.put("seqModelDiagramList", seq);
							this.classRose.packDatas.add(pgkData2);
							
						}
					}
					else{
					
						if(useCase != null)
							setRelationship(useCase);
						
						model = new UseCaseModel();
						if (useCase.getSuperclassList() != null) {
							this.generalizationMap.put(useCase.getQuid(), useCase.getSuperclassList());
						}
						if (path1.toString().length() > 0) {
							model.setName(path1.toString()+ "."	+ useCase.getParameterList().get(0));
						} else {
							model.setName((String)useCase.getParameterList().get(0));
						}

						model.setStereotype(useCase.getStereotype());
						if (useCase.getDocumentation() != null
								&& !useCase.getDocumentation().trim().equals(""))
							model.setDesc(this.getDoc(useCase.getProperties("documentation")));
						
	//					model.setSharedID(this.setIdMap(model.getName(), model.getSharedID()));
						sharedModels.put(model.getUMLDataModel().getId(), model);
						keyMap.put(useCase.getQuid(), model.getUMLDataModel().getId());
					
					}
					// useCaseModelList.add(useCase);
				} else if (obj instanceof cb.petal.Class) {
					cb.petal.Class actor = (cb.petal.Class) obj;
					if(actor != null)
						setRelationship(actor);
					if (actor.getStereotype() != null
							&& actor.getStereotype().equals("Actor")) {
						if (actor.getSuperclassList() != null) {
							this.generalizationMap.put(actor.getQuid(), actor
									.getSuperclassList());
						}
						model = new FinalActorModel();
					}
					else if(actor.getStereotype() != null && actor.getStereotype().equals("Interface")){
						model = new InterfaceModel();
					}
					else{
						model = new FinalClassModel();
					}
					seqMap.put(actor.getQuid(), model);
					if( model != null){						
						if (path1.toString().length() > 0) {
							model.setName(path1.toString() + "."+ actor.getParameterList().get(0));
						} else {
							model.setName((String)actor.getParameterList().get(0));
						}

						if(!(model instanceof FinalActorModel)){
							TypeRefModel classModel = null;
							if(model instanceof FinalClassModel){
								classModel = ((FinalClassModel)model).getClassModel();
							}
							else if(model instanceof InterfaceModel){
								classModel = (TypeRefModel)((InterfaceModel)model).getClassModel();
							}
							ArrayList attr = new ArrayList();
							ArrayList oper = new ArrayList();
							if(actor.getClassAttributeList() != null && actor.getClassAttributeList().size()>0){
								List attrList = actor.getClassAttributeList();
								for(int j=0; j<attrList.size(); j++){
									ClassAttribute cattr = (ClassAttribute)attrList.get(j);
																		
									AttributeEditableTableItem newItem = new
									AttributeEditableTableItem(new Integer(0), cattr.getName(), "String", "");
									
									attr.add(newItem);
//									((FinalClassModel)model).getClassModel().getAttributes().add(attr);
								}
								if(attr.size()>0){
									classModel.setAttributes(attr);									 
								}
							}
							if(actor.getOperationList() != null && actor.getOperationList().size()>0){
								List operList = actor.getOperationList();
								for(int j=0; j<operList.size(); j++){									
									cb.petal.Operation coper = (cb.petal.Operation)operList.get(j); 
									int index = setScope(coper.getExportControl());									
																		
									OperationEditableTableItem newItem = new OperationEditableTableItem(index, coper.getNameParameter(), "String", "");
									
									oper.add(newItem);
								}
								if(oper.size()>0){
									classModel.setOperations(oper);
								}
							}
							if(classModel.isMode()) 
								ProjectManager.getInstance().autoSize(classModel);							
						}
//						model.setSharedID(this.setIdMap(model.getName(),model.getSharedID()));
						sharedModels.put(model.getUMLDataModel().getId(), model);
						keyMap.put(actor.getQuid(), model.getUMLDataModel().getId());						
						// useCaseModelList.add(actor);
//					} else if ((actor.getStereotype() != null && !actor
//							.getStereotype().equals("Interface"))
//							|| actor.getClassType().equals("Class")) {
//						
//						classModelList.add(actor);
					}
					
				} else if (obj instanceof cb.petal.Association) {
					cb.petal.Association association = (cb.petal.Association) obj;
					if(getRelationship(association))
						associationMap.put(association.getQuid(), association);
					continue;
				} else if (obj instanceof cb.petal.Mechanism) {
					cb.petal.Mechanism mechanism = (cb.petal.Mechanism) obj;
					List logicalModels = mechanism.getLogicalModels();
					for (int k = 0; k < logicalModels.size(); k++) {
						cb.petal.Object object = (cb.petal.Object) logicalModels.get(k);
						
						RoseObject ro = new RoseObject();
						List collaborators = object.getCollaborators();
						if (collaborators != null) {
							for (int k1 = 0; k1 < collaborators.size(); k1++) {
								Link link = (Link) collaborators.get(k1);
								String quidu = link.getPropertyAsString("quidu");
								ro.link.add(quidu);
								List messages = link.getMessages();
								if (messages != null) {
									for (int k2 = 0; k2 < messages.size(); k2++) {
										Message message = (Message) messages.get(k2);
//										ijs seq
										if(message.getSynchronization()!=null && "Return".equals(message.getSynchronization())){
											this.returnMessage.put(message.getQuid(),"ret");
										}
										System.out.println(message.getQuid());
										ro.msg.put(message.getQuid(), message);
									}
								}
							}
						}
						try{
							System.out.println(object.getQuidu());
							if(object.getQuidu()!=null)
								ro.classGuid = object.getQuidu();
							keyMap.put(object.getQuid(), ro);
						}
						catch(RuntimeException e){
							e.printStackTrace();
						}
					}
					
					// associationMap.put(association.getQuid(), association);
					
				}
				else{
					continue;
				}
				UMLTreeModel to1 = null;
				if (packParent != null && model != null) {
					UMLModel um = model;
					String name1 = um.getName();
					if(name1.indexOf(".")>0){
						name1 = name1.substring(name1.lastIndexOf(".")+1, name1.length());
					}
					to1 = new StrcuturedPackageTreeModel(name1);
					to1.setRefModel(model);
					um.setName(name1);
					um.setTreeModel(to1);
					to1.setParent(packParent);
					packParent.addChild(to1);
//					dModel.addChild(um);
//					to1.addN3UMLModelDeleteListener(dModel);
				}
			}
			ProjectManager.getInstance().getModelBrowser().refresh(packParent);
			
			for (int i = 0; i < list2.size(); i++) {				
				Object obj = list2.get(i);
				if (obj instanceof UseCaseDiagram) {
					
					UseCaseDiagram useCaseDiagram = (UseCaseDiagram) obj;
					String chname = (String) useCaseDiagram.getParameterList().get(0);
					if(chname!=null && !"추적성".equals(chname.trim())){
						N3EditorDiagramModel dModel = ProjectManager.getInstance().addUMLDiagram(chname, packParent, null, 1, false, 8);
//						if (path1.toString().equals("")) {
//							model.setName(chname);
//						} else {
//							model.setName(path1+ "." + chname);
//						}

						dModel.getUMLDataModel().setId(this.setIdMap(dModel.getName(), dModel.getUMLDataModel().getId()));
						sharedModels.put(dModel.getUMLDataModel().getId(), dModel);
						keyMap.put(useCaseDiagram.getQuid(), dModel.getUMLDataModel().getId());
						diagram.put(dModel.getUMLDataModel().getId(), dModel);
						
						List item = useCaseDiagram.getItems();
						UMLDiagramModel tempModel = null;
						String getname = "";
						if (item.size() > 0) {
							for (int k = 0; k < item.size(); k++) {
								boolean bModel = false;
								Object obj1 = item.get(k);
								if (obj1 instanceof cb.petal.ClassView) {
									cb.petal.ClassView cv = (cb.petal.ClassView) obj1;								
//									sharedModels.get(cv.getQuidu());
//									tempModel = new FinalActorModel();
//									
									String value = (String) keyMap.get(cv.getQuidu());
									Object tempObj = sharedModels.get(value);
									tempModel = (UMLDiagramModel)tempObj;
//									tempModel.setStereotype(cv.getStereotype().toString());								
									tempModel.setLocation(new Point(cv.getLocation().getFirstValue()/5, cv.getLocation().getSecondValue()/5));
									
									tempModel.getUMLDataModel().setId(this.setIdMap(tempModel.getName(),tempModel.getUMLDataModel().getId()));

								} else if (obj1 instanceof cb.petal.UseCaseView) {

									cb.petal.UseCaseView uv = (cb.petal.UseCaseView) obj1;

									String value = (String) keyMap.get(uv.getQuidu());
									Object tempObj = sharedModels.get(value);
									tempModel = (UMLDiagramModel) tempObj;
									tempModel.setLocation(new Point(uv.getLocation().getFirstValue()/5, uv.getLocation().getSecondValue()/5));

									tempModel.getUMLDataModel().setId(this.setIdMap(tempModel.getName(),tempModel.getUMLDataModel().getId()));

								}
								else{
									continue;
								}
//								if (bModel) {
								UMLTreeModel to1 = null;
								if (packParent != null) {
									UMLModel um = tempModel;
//									
									to1 = root.searchChildModel("id", tempModel.getID(), false);
									
//									to1.setRefModel(tempModel);
//									um.setName(name1);
									um.setTreeModel(to1);
									dModel.addChild(um);
									to1.addN3UMLModelDeleteListener(dModel);
								}
							}
							ProjectManager.getInstance().getModelBrowser().refresh(packParent);
							makeLine(dModel);
						}
					}
					
				}
				if (obj instanceof ClassDiagram) {
					ClassDiagram classDiagram = (ClassDiagram) obj;
					classModelDiagramList.add(classDiagram);
					
					String chname = (String) classDiagram.getParameterList().get(0);

					N3EditorDiagramModel dModel = ProjectManager.getInstance().addUMLDiagram(chname, packParent, null, 1, false, 2);
					if(dModel != null){
						if (path1.toString().equals("")) {
							dModel.setName("" + chname);
						} else {
							dModel.setName("" + chname);
						}
	
						dModel.getUMLDataModel().setId(this.setIdMap(dModel.getName(), dModel.getUMLDataModel().getId()));
	
						sharedModels.put(dModel.getUMLDataModel().getId(), dModel);
						keyMap.put(classDiagram.getQuid(), dModel.getUMLDataModel().getId());
						diagram.put(dModel.getUMLDataModel().getId(), dModel);
	
						List item = classDiagram.getItems();
						UMLDiagramModel tempModel = null;
						String getname = "";
						if (item.size() > 0) {
							for (int k = 0; k < item.size(); k++) {
								Object obj1 = item.get(k);
								if (obj1 instanceof cb.petal.ClassView) {
									cb.petal.ClassView cv = (cb.petal.ClassView) obj1;								
									String value = (String) keyMap.get(cv.getQuidu());
									Object tempObj = sharedModels.get(value);
									tempModel = (UMLDiagramModel)tempObj;
									if(cv.getStereotype() != null)
										tempModel.setStereotype(cv.getStereotype().toString());								
									tempModel.setLocation(new Point(cv.getLocation().getFirstValue()/5, cv.getLocation().getSecondValue()/5));
	//								ArrayList attr = cv.getProperties("Attribute");
	//								ArrayList oper = cv.getProperties("Operation");
	//								ClassModel cm = new ClassModel();
	//								tempModel.setAttribute(attr);
	//								tempModel.setOperation(oper);
									tempModel.getUMLDataModel().setId(this.setIdMap(tempModel.getName(),tempModel.getUMLDataModel().getId()));
	
								} else if (obj1 instanceof cb.petal.UseCaseView) {
									cb.petal.UseCaseView uv = (cb.petal.UseCaseView) obj1;
									String value = (String) keyMap.get(uv.getQuidu());
									Object tempObj = sharedModels.get(value);
									tempModel = (UMLDiagramModel) tempObj;
									tempModel.setLocation(new Point(uv.getLocation().getFirstValue()/5, uv.getLocation().getSecondValue()/5));
	
									tempModel.getUMLDataModel().setId(this.setIdMap(tempModel.getName(),tempModel.getUMLDataModel().getId()));
									 
									// dModel.addChild(model);
	//							} else if (obj1 instanceof cb.petal.AssociationViewNew) {
	//								cb.petal.AssociationViewNew av = (cb.petal.AssociationViewNew) obj1;
	//								LineModel line = new LineModel();
									// line.setSource(av.get);
									// dModel.addChild(line);
								}
								else{
									continue;
								}
	
								UMLTreeModel to1 = null;
								if (packParent != null) {
									UMLModel um = tempModel;
	//								
									to1 = root.searchChildModel("id", tempModel.getID(), false);
									
	
									um.setTreeModel(to1);
									dModel.addChild(um);
									to1.addN3UMLModelDeleteListener(dModel);
								}
								// model.getUMLDataModel().setId(this.setIdMap(model.getName(),model.getUMLDataModel().getId()));cv.getpro)
							}
							ProjectManager.getInstance().getModelBrowser().refresh(packParent);
							makeLine(dModel);
						}
					}
					
				} else if (obj instanceof InteractionDiagram) {
					InteractionDiagram sequenceDiagram = (InteractionDiagram) obj;
					seqModelDiagramList.add(sequenceDiagram);
//					String chname = (String) sequenceDiagram.getParameterList().get(0);
//
//					N3EditorDiagramModel dModel = ProjectManager.getInstance().addUMLDiagram(chname, packParent, null, 1, false, 12);
//					if(dModel != null){
//						if (path1.toString().equals("")) {
//							dModel.setName("" + chname);
//						} else {
//							dModel.setName("" + chname);
//						}
//						
//						dModel.getUMLDataModel().setId(this.setIdMap(dModel.getName(), dModel.getUMLDataModel().getId()));
//	
//						sharedModels.put(dModel.getUMLDataModel().getId(), dModel);
//						keyMap.put(sequenceDiagram.getQuid(), dModel.getUMLDataModel().getId());
//						diagram.put(dModel.getUMLDataModel().getId(), dModel);
//						
//						List item = sequenceDiagram.getItems();
//						UMLDiagramModel tempModel = null;
//						String getname = "";
//						if (item.size() > 0) {
//							for (int k = 0; k < item.size(); k++) {
//								Object obj1 = item.get(k);
//								if (obj1 instanceof cb.petal.InterObjView) {
//									cb.petal.InterObjView interView = (cb.petal.InterObjView) obj1;
//									String id1 = (String)interView.getQuidu();
//									Object iv1 = keyMap.get(interView.getQuidu());
//									if (iv1 == null) {
//										iv1 = keyMap.get(dModel);
//									}
//									
//									if(iv1 != null && iv1 instanceof RoseObject){
//										RoseObject roseObject = (RoseObject)iv1;
//										Object iv2 = 
//									}
////									LifeLineActorModel
////									LifeLineModel
////									FragmentModel
////									EndPointModel
////									SelfMessageModel
////
////
////									treeModel = PackageTreeModel
////									child = Model
////									ProjectManager.getInstance().addUMLModel(null, (UMLTreeParentModel)packParent, tempModel, -1, 12);
//									
//									RoseObject value = (RoseObject)keyMap.get(interView.getQuidu());
//									FocusOfControl foc = interView.getFocusOfControl();
//									
//									ArrayList al = foc.getParameterList();
//									Tag tag = foc.getInterObjView();
//									ArrayList prop = foc.getPropertyList();
//									String id = value.link.get(0).toString();
//									Object tempObj = sharedModels.get(id);
//									tempModel = (UMLDiagramModel)tempObj;
////									if(interView.getStereotype() != null)
////										tempModel.setStereotype(interView.getStereotype().toString());								
////									tempModel.setLocation(new Point(interView.getLocation().getFirstValue()/5, 
////																interView.getLocation().getSecondValue()/5));
//	//								ArrayList attr = cv.getProperties("Attribute");
//	//								ArrayList oper = cv.getProperties("Operation");
//	//								ClassModel cm = new ClassModel();
//	//								tempModel.setAttribute(attr);
//	//								tempModel.setOperation(oper);
//									tempModel.getUMLDataModel().setId(this.setIdMap(tempModel.getName(),tempModel.getUMLDataModel().getId()));
//	
//								} else if (obj1 instanceof cb.petal.UseCaseView) {
//									cb.petal.UseCaseView uv = (cb.petal.UseCaseView) obj1;
//									String value = (String) keyMap.get(uv.getQuidu());
//									Object tempObj = sharedModels.get(value);
//									tempModel = (UMLDiagramModel) tempObj;
//									tempModel.setLocation(new Point(uv.getLocation().getFirstValue()/5, uv.getLocation().getSecondValue()/5));
//	
//									tempModel.getUMLDataModel().setId(this.setIdMap(tempModel.getName(),tempModel.getUMLDataModel().getId()));
//									 
//									// dModel.addChild(model);
//	//							} else if (obj1 instanceof cb.petal.AssociationViewNew) {
//	//								cb.petal.AssociationViewNew av = (cb.petal.AssociationViewNew) obj1;
//	//								LineModel line = new LineModel();
//									// line.setSource(av.get);
//									// dModel.addChild(line);
//								}
//								else{
//									continue;
//								}
//	
//								UMLTreeModel to1 = null;
//								if (packParent != null) {
//									UMLModel um = tempModel;
//	//								
//									to1 = root.searchChildModel("id", tempModel.getID(), false);
//									
//	
//									um.setTreeModel(to1);
//									dModel.addChild(um);
//									to1.addN3UMLModelDeleteListener(dModel);
//								}
//								// model.getUMLDataModel().setId(this.setIdMap(model.getName(),model.getUMLDataModel().getId()));cv.getpro)
//							}
//							ProjectManager.getInstance().getModelBrowser().refresh(packParent);
//							makeLine(dModel);
//						}
//					}
				}
				else{
					continue;
				}
				
			}
			//ijs lg건 삭제
//			if (useCaseModelList.size() > 0
//					|| useCaseModelDiagramList.size() > 0) {
//				if (path1.toString().equals("")) {
//					String p1 = ""+ ".";
//					
//					this.usecaseRose.packNames.add(p1);
//				} else {
//					this.usecaseRose.packNames.add(path1);
//				}
//				
//				pgkData.put("useCaseModelList", useCaseModelList);
//				pgkData.put("useCaseModelDiagramList", useCaseModelDiagramList);
//				this.usecaseRose.packDatas.add(pgkData);
//				
//			}
			if (classModelList.size() > 0 || classModelDiagramList.size() > 0
					|| seqModelDiagramList.size() > 0) {

				if (path2.trim().toString().equals("")) {

				} else {
					this.classRose.packNames.add(path2);
					pgkData2.put("classModelList", classModelList);
					pgkData2.put("classModelDiagramList", classModelDiagramList);
					pgkData2.put("seqModelDiagramList", seqModelDiagramList);
					this.classRose.packDatas.add(pgkData2);
				}
				// this.classRose.packNames.add(path2);
				
			}
			if (seqModelDiagramList.size() > 0) {
				this.seqRose.packNames.add(path1);
				this.seqRose.packDatas.add(seqModelDiagramList);				
			}
		}
		init1();
	}
	
	public void classifyModelComponentCateogry(String path1, SubSystem su,
			Vector cmpSharedList1, Vector seqModelDiagramList,
			Map sharedModels, java.util.List notations) {
		UMLTreeParentModel packParent = null;
		cb.petal.List list = su.getPhysicalModels();
		cb.petal.List list2 = su.getPhysicalPresentations();
		HashMap pgkData = new HashMap();
		HashMap pgkData2 = new HashMap();
		UMLDiagramModel model = null;
		
		String name1 = (String) su.getParameterList().get(0);
		if (name1 != null && !name1.equals("")) {
			String packName = "";
			if(path1 != null && !(path1.equals(""))){
				int index = path1.lastIndexOf(".");
				packName = path1.substring(0, index);
			
				if(packName.equals("")){
					parent = root;
				}
				else{
					UMLTreeParentModel temp = this.searchPackage(root, packName);
					if(temp != null){
						parent = temp;
						packParent = temp;
					}
				}
			}
			ProjectManager.getInstance().addUMLModel(name1, parent, null, 0,	-1);				
			if(!(parent instanceof RootTreeModel))
				name1 = ((UMLModel)parent.getRefModel()).getUMLDataModel().getFullName(parent, parent.getName())+"."+name1;
			UMLTreeParentModel temp = this.searchPackage(parent, name1);
			if(temp != null){
				parent = temp;
				packParent = temp;
			}
		}
		for (int i = 0; i < list.size(); i++) {
			Object obj = list.get(i);
			if (obj instanceof SubSystem) {
				SubSystem childUseCaegory = (SubSystem) obj;
				String chname = (String) childUseCaegory.getParameterList().get(0);
				String n1 = "";
				if (path1.toString().equals("")) {
					n1 = name1 + "."	+ chname;
				} else {
					n1 = path1 + "." + chname;
					
				}
				this.classifyModelComponentCateogry(n1, childUseCaegory, null,
						null, sharedModels, notations);
			} 
			else {
				if (obj instanceof Module) {
					Module module = (Module) obj;
					model = new ComponentModel();
					
					String name = (String) module.getParameterList().get(0);
					// path1 + "__" + name
					model.setName(path1 + "." + name);					
					String stereo = module.getStereotype();
					// cmpSharedList.put(sdm.getSharedID(), sdm);
					this.keyMap.put(module.getQuid(), model.getID());
					System.out.println(model.getID());
					sharedModels.put(model.getID(), model);
					ArrayList intefaceLists = module.getProperties("realized_interfaces");
					for (int j = 0; j < intefaceLists.size(); j++) {
						cb.petal.List realizeRelationships = (cb.petal.List) intefaceLists.get(j);
						for (int k = 0; k < realizeRelationships.size(); k++) {
							RealizeRelationship realizeRelationship = (RealizeRelationship) realizeRelationships.get(k);
							model = new InterfaceModel();
							
							model.setName(path1 + "." + name);
							String stereo1 = model.getStereotype();
							model.setStereotype(stereo1);
							// interfaceSharedList.put(sdm2.getSharedID(),
							// sdm2);
							this.keyMap.put(realizeRelationship.getQuid(), model.getID());
							
							this.keyMap.put(model.getID(), realizeRelationship.getQuidu());
							// this.keyMap.put(sdm2.getSharedID(),)
//							cm.add.addInterface(im);
//							String quidu = realizeRelationship.getQuidu();
						}
					}
//			        String fileSeparator = System.getProperty("file.separator");
//	                componentPath = new File(getProjectPath() + "ComponentModel");
//	                if (!componentPath.exists())
//	                        componentPath.mkdirs();
//					String path = cobalt.gen.modeler.common.uml.UMLFacade.getInstance().getComponentPath().getAbsolutePath();
//					if (MainWindow.prjManager
//							.getProjectInfo()
//							.getEJBVersion()
//							.equals(
//									MainWindow.prjManager.getProjectConfig().EJB1_1))
//						clsManager = new CCDModelManager(path
//								+ System.getProperty("file.separator")
//								+ sdm.getName());
//					else if (MainWindow.prjManager
//							.getProjectInfo()
//							.getEJBVersion()
//							.equals(
//									MainWindow.prjManager.getProjectConfig().EJB2_0))
//						clsManager = new CCD2ModelManager(path
//								+ System.getProperty("file.separator")
//								+ sdm.getName());
//					else
//						clsManager = new CCDModelManager(path
//								+ System.getProperty("file.separator")
//								+ sdm.getName());
//					// N3soft V2.0200 2006. 12. 19 김동일22 컴포넌트 생성시 root폴더 생성되는 문제
//					// Start
//					File r = new File(path
//							+ System.getProperty("file.separator") + "root");
//					if (r.exists()) {
//						File[] ch = r.listFiles();
//						if (ch.length < 1)
//							r.delete();
//					}
//					// N3soft V2.0200 2006. 12. 19 김동일22 컴포넌트 생성시 root폴더 생성되는 문제
//					// End
//					
//					sdm.setCCDModelManager(clsManager);
//					sdm.getCCDModelManager().setActionHistory(
//							cobalt.gen.commongui.MainWindow.actionHistory
//							.getMainAction());
//					CCDPackageStore packageStore = null;
//					
//					clsManager.getRootDiagram().setName(sdm.getName());
//					sdm.setCCDModelManager(clsManager);
//					// ////////////////////////
//					// ///CCD의 디폴트 다이어그램 모델을 생성/////
//					DiagramModelIF diagramModel = clsManager.getRootDiagram();
//					// ///CCD의 패키지 스토어를 생성/////
//					packageStore = (CCDPackageStore) clsManager
//					.getClassRepository().getRoot();
//					packageStore.setID(sdm.getSharedID());
//					packageStore
//					.addClassModelListener(ComponentManagerTreeHandler
//							.getInstance().getnew());
//					
//					if (MainWindow.prjManager
//							.getProjectInfo()
//							.getEJBVersion()
//							.equals(
//									MainWindow.prjManager.getProjectConfig().EJB1_1)) {
//						CCDTreeHandler fth = new CCDTreeHandler(packageStore);
//					} else if (MainWindow.prjManager
//							.getProjectInfo()
//							.getEJBVersion()
//							.equals(
//									MainWindow.prjManager.getProjectConfig().EJB2_0)) {
//						CCDTreeHandler fth = new CCD2TreeHandler(packageStore);
//					} else {
//						CCDTreeHandler fth = new CCDTreeHandler(packageStore);
//					}
//					// /////////////////////////////
//					// 컴포넌트 쉐어드모델에 패키지스토어를 세팅한다.
//					sdm.setStore(packageStore);
					cb.petal.List visibleModules = module.getVisibleModules();
					if (visibleModules != null) {
						for (int j = 0; j < visibleModules.size(); j++) {
							ModuleVisibilityRelationship visModule = (ModuleVisibilityRelationship) visibleModules.get(j);
							RelationshipData relationshipData = new RelationshipData();
							relationshipData.client = module.getQuid();
							relationshipData.server = visModule.getQuidu();
							System.out.println("visibleModules quid : "+visModule.getQuid());
							System.out.println("module quid : "+module.getQuid());
							associationMap.put(visModule.getQuid(), relationshipData);
							keyMap.put(visModule.getQuid(), relationshipData);
						}
					}
				}
				UMLTreeModel to1 = null;
				if (packParent != null) {
					UMLModel um = model;
					String name2 = um.getName();
					if(name2.indexOf(".")>0){
						name2 = name2.substring(name2.lastIndexOf(".")+1, name2.length());
					}
					to1 = new StrcuturedPackageTreeModel(name2);
					to1.setRefModel(model);
					um.setName(name2);
					um.setTreeModel(to1);
					to1.setParent(packParent);
					packParent.addChild(to1);
//					dModel.addChild(um);
//					to1.addN3UMLModelDeleteListener(dModel);
				}
			}
		}
//		if(packParent == null)
//			packParent = root;
		Vector vc = new Vector();
		for (int i = 0; i < list2.size(); i++) {
			Object obj = list2.get(i);
			if (obj instanceof ModuleDiagram) {
				ModuleDiagram componentDiagram = (ModuleDiagram)obj;
				String chname = (String) componentDiagram.getParameterList().get(0);
				// UsecaseDiagramSharedDataModel model = new
				// UsecaseDiagramSharedDataModel();

				N3EditorDiagramModel dModel = ProjectManager.getInstance().addUMLDiagram(chname, packParent, null, 1, false, 5);
				
				dModel.getUMLDataModel().setId(this.setIdMap(dModel.getName(), dModel.getUMLDataModel().getId()));
//				UMLEditor edit = new UMLEditor();
//				ProjectManager.getInstance().getUMLEditor().setUMLDiagramModel(dModel);
//				sharedModels.put(dModel.getUMLDataModel().getId(), dModel);
				keyMap.put(componentDiagram.getQuid(), dModel.getUMLDataModel().getId());
				diagram.put(dModel.getUMLDataModel().getId(), dModel);

				List item = componentDiagram.getItems();
				UMLDiagramModel tempModel = null;
				String getname = "";
				if (item.size() > 0) {
					for (int k = 0; k < item.size(); k++) {
						boolean bModel = false;
						Object obj1 = item.get(k);
						if (obj1 instanceof cb.petal.ModView) {
							cb.petal.ModView module = (cb.petal.ModView)obj1;
							Object tempObj = sharedModels.get(keyMap.get(module.getQuidu()));
							tempModel = (UMLDiagramModel)tempObj;
							
							tempModel.setLocation(new Point(module.getLocation().getFirstValue()/5, module.getLocation().getSecondValue()/5));
							
							tempModel.getUMLDataModel().setId(this.setIdMap(tempModel.getName(),tempModel.getUMLDataModel().getId()));
						}
						else if (obj1 instanceof cb.petal.ModVisView) {
							cb.petal.ModVisView mv = (cb.petal.ModVisView) obj1;
							RelationshipData relationshipData = (RelationshipData)associationMap.get(mv.getQuidu());

							UMLDiagramModel startModel = null;
							UMLDiagramModel endModel = null;
							
							LineModel line = new DependencyLineModel();
							
							startModel = (UMLDiagramModel)sharedModels.get(keyMap.get(relationshipData.server));
							endModel = (UMLDiagramModel)sharedModels.get(keyMap.get(relationshipData.client));
							
							if(line != null){
								if(mv.getNameParameter() != null){	
									System.out.println(mv.getNameParameter());
									line.setName(mv.getNameParameter());
								}
//								if(mv.getStereotype() != null){
////									line.setstereotype(association.getStereotype());
//									System.out.println("Line Type : "+mv.getStereotype());
//								}
								if(startModel != null){
									line.detachSource();
									line.setSource(startModel);
									line.setSourceTerminal("B");
									line.attachSource();
								}
								if(endModel != null){
									line.detachTarget();
									line.setTarget(endModel);
									line.setTargetTerminal("B");
									line.attachTarget(dModel);
								}
							}
						} 
						else{
							continue;
						}
//						if (bModel) {
						UMLTreeModel to1 = null;
						if (packParent != null) {
							UMLModel um = tempModel;
//							
							to1 = root.searchChildModel("id", tempModel.getID(), false);
							
//							to1.setRefModel(tempModel);
//							um.setName(name1);
							um.setTreeModel(to1);
							dModel.addChild(um);
							to1.addN3UMLModelDeleteListener(dModel);
						}
//							bModel = false;
//						} else {
//							UMLTreeModel to1 = null;
//							UMLModel um = tempModel;
//							System.out.println(um.getName());
//							to1 = root.searchChildModel("name", getname
//									+ "." + tempModel.getName(), false);
//							if (to1 != null) {
//								um.setTreeModel(to1);
//								dModel.addChild(tempModel);
//								to1.addN3UMLModelDeleteListener(dModel);
//							} else {
//								emptyModel.add(um);
//							}
//						}							
						// model.getUMLDataModel().setId(this.setIdMap(model.getName(),model.getUMLDataModel().getId()));cv.getpro)
					}					
//					makeLine(dModel);
				}
				ProjectManager.getInstance().getModelBrowser().refresh(packParent);
				vc.add(obj);
				
			}
		}
		
		this.comDiagram.put(path1, vc);
		
	}
	
	public String getDoc(ArrayList arr) {
		if (arr != null) {
			StringBuffer sbDoc = new StringBuffer();
			// for(int i =0;i<arr.size();i++){
			if (arr.size() > 0) {
				StringLiteral arrl1 = (StringLiteral) arr.get(0);
				Iterator iterator = arrl1.getLines().iterator();
				while (iterator.hasNext()) {
					String str = (String) iterator.next();
					sbDoc.append(str);
					if (iterator.hasNext()) {
						sbDoc.append("\n");
					}
				}
			}
			return sbDoc.toString();
		} else {
			return "";
		}
	}

	public String setIdMap(String path, String id) {
		System.out.println("idMap size==================>" + idMap.size());
		if (!idMap.containsKey(path) && idMap.get(path) == null) {
			System.out.println("path new================>" + path);
			idMap.put(path, id);
			return id;
		} else {
			System.out.println("path old=====================>" + path);
			return (String) idMap.get(path);
		}
	}

	public boolean makeModel() {
		Iterator diagrams = diagram.values().iterator();
		while (diagrams.hasNext()) {
			Object ob = diagrams.next();
			N3EditorDiagramModel dmodel = (N3EditorDiagramModel) ob;
			java.util.List child = dmodel.getChildren();
			if (child.size() > 0) {
				for (int i = 0; i < child.size(); i++) {
					UMLTreeParentModel upm = parent;
					UMLTreeModel utm = null;
					UMLTreeModel[] utm2 = upm.getChildren();
					Object obj = child.get(i);
					if (obj instanceof ClassModel) {
						ClassModel cm = (ClassModel) obj;
						for (int j = 0; j < utm2.length; j++) {
							UMLTreeModel treeModel = utm2[j];
							System.out.println(cm.getUMLDataModel().getId());
							System.out.println(((UMLModel) treeModel
									.getRefModel()).getUMLDataModel().getId());
							if (((UMLModel) treeModel.getRefModel())
									.getUMLDataModel().getId().equals(
											cm.getUMLDataModel().getId())) {
								utm = treeModel;
								break;
							}
						}
					} else if (obj instanceof FinalActorModel) {
						FinalActorModel fam = (FinalActorModel) obj;
						utm = fam.getUMLTreeModel();
						System.out.println(fam.getName());
					} else if (obj instanceof UseCaseModel) {
						UseCaseModel ucm = (UseCaseModel) obj;
						utm = ucm.getUMLTreeModel();
					} else if (obj instanceof LineModel) {
						LineModel lm = (LineModel) obj;
						System.out.println(lm.getname());
					}

					if (utm != null) {
						// kr.co.n3soft.n3com.parser.ModelManager.getInstance()
						// up =
						// (UMLTreeModel)ModelManager.getInstance().getModelStore().get(cm.getUMLDataModel().getId());

						UMLModel um = (UMLModel) utm.getRefModel();
						um.setAcceptParentModel(dmodel);
						TreeSimpleFactory factory = new TreeSimpleFactory();
						UMLModel um1 = factory.createModle(um);
						um1.setSize(new Dimension(100, 100));
						um1.setLocation(um.getLocation());
					}
				}
			}
		}
		return true;
	}
	
	public void makeLine(N3EditorDiagramModel dModel){
		UMLDiagramModel startModel = null;
		UMLDiagramModel endModel = null;
		
		Iterator lines = associationMap.values().iterator();
		HashMap tempMap = (HashMap)associationMap.clone();
		while (lines.hasNext()) {
			Object ob = lines.next();
			Association association = (Association)ob;
			System.out.println(association.getParent());
			String end = (String) keyMap.get(association.getFirstClient().getQuid());
			String start = (String) keyMap.get(association.getSecondClient().getQuid());
			
			LineModel line = new AssociateLineModel();
			
			System.out.println("line Stereotype : "+association.getStereotype());
			
			Object tempObj1 = sharedModels.get(start);
			Object tempObj2 = sharedModels.get(end);
			startModel = (UMLDiagramModel)tempObj1;
			endModel = (UMLDiagramModel)tempObj2;
									
//				if( (startModel instanceof FinalActorModel && endModel instanceof UseCaseModel ) ||
//						(endModel instanceof FinalActorModel && startModel instanceof UseCaseModel ) ||
//						(endModel instanceof UseCaseModel && startModel instanceof UseCaseModel ) ){
//					line = new AssociateLineModel();
//				}
			if(association.getStereotype() != null){
				if(association.getStereotype().equals("include") )							
					line = new IncludeLineModel();
				else if(association.getStereotype().equals("realize") )							
					line = new RealizeLineModel();							
				else if(association.getStereotype().equals("extend") )
					line = new ExtendLineModel();
				else if(association.getStereotype().equals("communicate") )
					line.setName("communicate");
				else if(association.getStereotype().equals("subscribe") )
					line.getMiddleLineTextModel().setMultiplicity("subscribe");
			}
//				line = new ExtendLineModel();
				System.out.println("Style : "+line.getLineStyle());
				System.out.println("Type  : "+line.getLineType());
				if(line != null){
					if(association.getNameParameter() != null){	
						System.out.println(association.getNameParameter());
						line.setName(association.getNameParameter());
					}
					if(association.getStereotype() != null){
//						line.setstereotype(association.getStereotype());
						System.out.println("Line Type : "+association.getStereotype());
					}
					if(startModel != null){
						line.detachSource();
						line.setSource(startModel);
						line.setSourceTerminal("B");
						line.attachSource();
					}
					if(endModel != null){
						line.detachTarget();
						line.setTarget(endModel);
						line.setTargetTerminal("B");
						line.attachTarget(dModel);
					}
				}
		}
		
		Iterator glines = generalizationMap.keySet().iterator();					
		while (glines.hasNext()) {
			String key = (String)glines.next();
			System.out.println("key:" + key);
			List list1 = (List)generalizationMap.get(key);
			for(int k=0; k<list1.size(); k++){
				Object obj1 = list1.get(k);
				
				if(obj1 instanceof cb.petal.InheritanceRelationship){
					cb.petal.InheritanceRelationship ir = (cb.petal.InheritanceRelationship)obj1;
					Object value1 = keyMap.get(ir.getQuidu());
					Object value2 = keyMap.get(key);
					endModel = (UMLDiagramModel)sharedModels.get(value1.toString());
					startModel = (UMLDiagramModel)sharedModels.get(value2.toString());
					GeneralizeLineModel line = new GeneralizeLineModel();
					
					if(line != null){
 						if(list1.getKind() != null)
 							line.setName(list1.getName()); 
// 						if(list1.getStereotype() != null){
// 							System.out.println("Line Type : "+list1.getStereotype());
// 						}
						if(startModel != null){
							line.detachSource();
							line.setSource(startModel);
							line.setSourceTerminal("B");
							line.attachSource();
						}
						if(endModel != null){
							line.detachTarget();
							line.setTarget(endModel);
							line.setTargetTerminal("B");
							line.attachTarget(dModel);
						}
						}
					String id = (String)keyMap.get(key);
					System.out.println();
				}
			}
			System.out.println();
		}
	}
	public void setRelationship(cb.petal.Class actor){
		if(actor != null){
			if(actor.getAssociations() != null && actor.getAssociations().size()>0){
				System.out.println("association have ");
//				for(int j=0; j<actor.getAssociations().size(); j++){
//					RelationshipData relationshipData = new RelationshipData();
//					Object obj = actor.getAssociations().get(j);
//					Association association = (Association)obj;					
//					UsesRelationship usesRelationship = (UsesRelationship)actor.getAssociations().get(j);
//					relationshipData.client = actor.getQuid();
//					relationshipData.server = usesRelationship.getQuidu();
//					keyMap.put(usesRelationship.getQuid(), relationshipData);
//					relationship.put(usesRelationship.getQuid(), relationshipData);
//				}
			}
			if(actor.getUsedClassesList() != null && actor.getUsedClassesList().size()>0){
				for(int j=0; j<actor.getUsedClassesList().size(); j++){
					RelationshipData relationshipData = new RelationshipData();
					UsesRelationship usesRelationship = (UsesRelationship)actor.getUsedClassesList().get(j);
					relationshipData.client = actor.getQuid();
					relationshipData.server = usesRelationship.getQuidu();
					keyMap.put(usesRelationship.getQuid(), relationshipData);
					relationship.put(usesRelationship.getQuid(), relationshipData);
				}
			}
			if(actor.getSuperclassList() != null && actor.getSuperclassList().size()>0){
				for(int j=0; j<actor.getSuperclassList().size(); j++){
					RelationshipData relationshipData = new RelationshipData();
					InheritanceRelationship inheritanceRelationship = (InheritanceRelationship)actor.getSuperclassList().get(j);
					relationshipData.client = actor.getQuid();
					relationshipData.server = inheritanceRelationship.getQuidu();
					keyMap.put(inheritanceRelationship.getQuid(), relationshipData);
					relationship.put(inheritanceRelationship.getQuid(), relationshipData);
				}
			}
			if(actor.getRealizedInterfacesList() != null && actor.getRealizedInterfacesList().size()>0){
				for(int j=0; j<actor.getRealizedInterfacesList().size(); j++){
					RelationshipData relationshipData = new RelationshipData();
					RealizeRelationship realizeRelationship = (RealizeRelationship)actor.getRealizedInterfacesList().get(j);
					relationshipData.client = actor.getQuid();
					relationshipData.server = realizeRelationship.getQuidu();
					keyMap.put(realizeRelationship.getQuid(), relationshipData);
					relationship.put(realizeRelationship.getQuid(), relationshipData);
				}
			}
		}
	}
	
	public void setRelationship(cb.petal.UseCase usecase){
		if(usecase != null){

//			if(usecase.getUsedClassesList() != null && actor.getUsedClassesList().size()>0){
//				for(int j=0; j<actor.getUsedClassesList().size(); j++){
//					RelationshipData relationshipData = new RelationshipData();
//					UsesRelationship usesRelationship = (UsesRelationship)actor.getUsedClassesList().get(j);
//					relationshipData.client = actor.getQuid();
//					relationshipData.server = usesRelationship.getQuidu();
//					keyMap.put(usesRelationship.getQuid(), relationshipData);
//					relationship.put(usesRelationship.getQuid(), relationshipData);
//				}
//			}		
			if(usecase.getSuperclassList() != null && usecase.getSuperclassList().size()>0){
				for(int j=0; j<usecase.getSuperclassList().size(); j++){
					RelationshipData relationshipData = new RelationshipData();
					InheritanceRelationship inheritanceRelationship = (InheritanceRelationship)usecase.getSuperclassList().get(j);
					relationshipData.client = usecase.getQuid();
					relationshipData.server = inheritanceRelationship.getQuidu();
					keyMap.put(inheritanceRelationship.getQuid(), relationshipData);
					relationship.put(inheritanceRelationship.getQuid(), relationshipData);
				}
			}
//			if(usecase.get)
//			if(usecase.get.getRealizedInterfacesList() != null && usecase.getRealizedInterfacesList().size()>0){
//				for(int j=0; j<usecase.getRealizedInterfacesList().size(); j++){
//					RelationshipData relationshipData = new RelationshipData();
//					RealizeRelationship realizeRelationship = (RealizeRelationship)actor.getRealizedInterfacesList().get(j);
//					relationshipData.client = actor.getQuid();
//					relationshipData.server = realizeRelationship.getQuidu();
//					keyMap.put(realizeRelationship.getQuid(), relationshipData);
//					relationship.put(realizeRelationship.getQuid(), relationshipData);
//				}
//			}
		}
	}
	
	public boolean getRelationship(Association association){
		Iterator list = relationship.keySet().iterator();
		while(list.hasNext()){
			String key = (String)list.next();
			System.out.println("key:" + key);
			RelationshipData data = (RelationshipData)relationship.get(key);
			String server = data.server;
			String client = data.client;
			String start = association.getFirstClient().getQuid();
			String end = association.getSecondClient().getQuid();
			
			if(start.equals(server) && end.equals(client)){
				System.out.println("same line ");
				return false;
			}
		}
//		String quid = association.getQuid();
//		Object obj = keyMap.get(quid);
//		if(obj instanceof RelationshipData){
//			RelationshipData tempData = (RelationshipData)obj;
//			String start = association.getFirstClient().getQuid();
//			String server = tempData.server;
//			String end = association.getSecondClient().getQuid();
//			String client = tempData.client;
//			if(start.equals(tempData.server) && end.equals(tempData.client)){
//				return false;
//			}
//		}			
		return true;
	}
	
	public void classEtcLines(N3EditorDiagramModel dModel, ClassDiagram classDiagram){
		cb.petal.List lineList = classDiagram.getItems();
		for(int k=0; k<lineList.size(); k++){
			Object lineObj = lineList.get(k);
			if(lineObj instanceof AssociationViewNew){
				AssociationViewNew associationViewNew = (AssociationViewNew)lineObj;
				Object tempObj = this.keyMap.get(associationViewNew.getQuidu());
				if(tempObj != null){
					if(tempObj instanceof RelationshipData){
						LineModel line = new GeneralizeLineModel();
						RelationshipData relationshipData = (RelationshipData)tempObj;
						
						Object q1 = sharedModels.get(keyMap.get(relationshipData.server));
						Object q2 = sharedModels.get(keyMap.get(relationshipData.client));
						
						UMLDiagramModel start = (UMLDiagramModel)q2;
						UMLDiagramModel end = (UMLDiagramModel)q1;
						
						if(line != null){
							if(associationViewNew.getNameParameter() != null){	
								System.out.println(associationViewNew.getNameParameter());
								line.setName(associationViewNew.getNameParameter());
							}
//							if(associationViewNew.getStereotype() != null){
////								line.setstereotype(association.getStereotype());
//								System.out.println("Line Type : "+associationViewNew.getStereotype());
//							}
							if(start != null){
								line.detachSource();
								line.setSource(start);
								line.setSourceTerminal("B");
								line.attachSource();
							}
							if(end != null){
								line.detachTarget();
								line.setTarget(end);
								line.setTargetTerminal("B");
								line.attachTarget(dModel);
							}
						}
					}
				}
			}
			else if(lineObj instanceof InheritView){
				InheritView inheritView = (InheritView) lineObj;
				Object tempObj = this.keyMap.get(inheritView.getQuidu());
				if(tempObj != null){
					if(tempObj instanceof RelationshipData){
						LineModel line = new GeneralizeLineModel();
						RelationshipData relationshipData = (RelationshipData)tempObj;
						
						Object q1 = sharedModels.get(keyMap.get(relationshipData.server));
						Object q2 = sharedModels.get(keyMap.get(relationshipData.client));
						
						UMLDiagramModel start = (UMLDiagramModel)q2;
						UMLDiagramModel end = (UMLDiagramModel)q1;
						
						if(line != null){
							if(inheritView.getNameParameter() != null){	
								System.out.println(inheritView.getNameParameter());
								line.setName(inheritView.getNameParameter());
							}
							if(inheritView.getStereotype() != null){
//								line.setstereotype(association.getStereotype());
								System.out.println("Line Type : "+inheritView.getStereotype());
							}
							if(start != null){
								line.detachSource();
								line.setSource(start);
								line.setSourceTerminal("B");
								line.attachSource();
							}
							if(end != null){
								line.detachTarget();
								line.setTarget(end);
								line.setTargetTerminal("B");
								line.attachTarget(dModel);
							}
						}
					}
				}
			}
			else if(lineObj instanceof ImportView){
				ImportView importView = (ImportView) lineObj;
				Object tempObj = this.keyMap.get(importView.getQuidu());
				if(tempObj != null){
					if(tempObj instanceof RelationshipData){
						LineModel line = new DependencyLineModel();
						RelationshipData relationshipData = (RelationshipData)tempObj;
						
						Object q1 = sharedModels.get(keyMap.get(relationshipData.server));
						Object q2 = sharedModels.get(keyMap.get(relationshipData.client));
						
						UMLDiagramModel start = (UMLDiagramModel)q2;
						UMLDiagramModel end = (UMLDiagramModel)q1;
						
						if(line != null){
							if(importView.getNameParameter() != null){	
								System.out.println(importView.getNameParameter());
								line.setName(importView.getNameParameter());
							}
//							if(importView.getStereotype() != null){
////								line.setstereotype(association.getStereotype());
//								System.out.println("Line Type : "+importView.getStereotype());
//							}
							if(start != null){
								line.detachSource();
								line.setSource(start);
								line.setSourceTerminal("B");
								line.attachSource();
							}
							if(end != null){
								line.detachTarget();
								line.setTarget(end);
								line.setTargetTerminal("B");
								line.attachTarget(dModel);
							}
						}
					}
				}
			}
			else if(lineObj instanceof UsesView){
				UsesView usesView = (UsesView) lineObj;
				Object tempObj = this.keyMap.get(usesView.getQuidu());
				if(tempObj != null){
					if(tempObj instanceof RelationshipData){
						LineModel line = new DependencyLineModel();
						RelationshipData relationshipData = (RelationshipData)tempObj;
						
						Object q1 = sharedModels.get(keyMap.get(relationshipData.server));
						Object q2 = sharedModels.get(keyMap.get(relationshipData.client));
						
						UMLDiagramModel start = (UMLDiagramModel)q2;
						UMLDiagramModel end = (UMLDiagramModel)q1;
						
						if(line != null){
							if(usesView.getNameParameter() != null){	
								System.out.println(usesView.getNameParameter());
								line.setName(usesView.getNameParameter());
							}
							if(usesView.getStereotype() != null){
//								line.setstereotype(association.getStereotype());
								System.out.println("Line Type : "+usesView.getStereotype());
							}
							if(start != null){
								line.detachSource();
								line.setSource(start);
								line.setSourceTerminal("B");
								line.attachSource();
							}
							if(end != null){
								line.detachTarget();
								line.setTarget(end);
								line.setTargetTerminal("B");
								line.attachTarget(dModel);
							}
						}
					}
				}
			}
			else if(lineObj instanceof RealizeView ){
				RealizeView realizeView = (RealizeView) lineObj;
				Object tempObj = this.keyMap.get(realizeView.getQuidu());
				if(tempObj != null){
					if(tempObj instanceof RelationshipData){
						LineModel line = new RealizeLineModel();
						RelationshipData relationshipData = (RelationshipData)tempObj;
						
						Object q1 = sharedModels.get(keyMap.get(relationshipData.server));
						Object q2 = sharedModels.get(keyMap.get(relationshipData.client));
						
						UMLDiagramModel start = (UMLDiagramModel)q2;
						UMLDiagramModel end = (UMLDiagramModel)q1;
						
						if(line != null){
							if(realizeView.getNameParameter() != null){	
								System.out.println(realizeView.getNameParameter());
								line.setName(realizeView.getNameParameter());
							}
							if(realizeView.getStereotype() != null){
//								line.setstereotype(association.getStereotype());
								System.out.println("Line Type : "+realizeView.getStereotype());
							}
							if(start != null){
								line.detachSource();
								line.setSource(start);
								line.setSourceTerminal("B");
								line.attachSource();
							}
							if(end != null){
								line.detachTarget();
								line.setTarget(end);
								line.setTargetTerminal("B");
								line.attachTarget(dModel);
							}
						}
					}
				}
			}
			System.out.println();
		}
	}
	
	public UMLTreeParentModel searchPackage(UMLTreeParentModel ptm, String name){
		UMLTreeModel[] utm = ptm.getChildren();
		for (int j = 0; j < utm.length; j++) {
			System.out.println(utm[j]);
			if(!(utm[j] instanceof UMLTreeParentModel))
				continue;
			UMLTreeParentModel upm = (UMLTreeParentModel) utm[j];
			Object obj = upm.getRefModel();
			if (obj instanceof FinalPackageModel) {
				UMLTreeParentModel pack = (UMLTreeParentModel) upm;
				String value = ((UMLModel)pack.getRefModel()).getUMLDataModel().getFullName(pack, pack.getName());
				if(value.equals(name)){
					return pack;
				}
				else if(pack.getChildren().length>0){					
					UMLTreeParentModel temp = this.searchPackage(pack, name);
					if(temp != null){
						return temp;
					}
				}
			}
			
		}
		return null;
	}
	
	public void usecaseEtcLines(N3EditorDiagramModel dModel, UseCaseDiagram usecaseDiagram){
		cb.petal.List lineList = usecaseDiagram.getItems();
		for(int k=0; k<lineList.size(); k++){
			Object lineObj = lineList.get(k);
			if(lineObj instanceof DependencyView){
				DependencyView dependencyView = (DependencyView)lineObj;
				Object tempObj = this.keyMap.get(dependencyView.getQuidu());
				if(tempObj != null){
					if(tempObj instanceof RelationshipData){
						LineModel line = new DependencyLineModel();
						RelationshipData relationshipData = (RelationshipData)tempObj;					
						
						Object q1 = sharedModels.get(keyMap.get(relationshipData.server));
						Object q2 = sharedModels.get(keyMap.get(relationshipData.client));
						
						UMLDiagramModel start = (UMLDiagramModel)q2;
						UMLDiagramModel end = (UMLDiagramModel)q1;
						
						if(line != null){
							if(dependencyView.getNameParameter() != null){	
								System.out.println(dependencyView.getNameParameter());
								line.setName(dependencyView.getNameParameter());
							}
							if(dependencyView.getStereotype() != null){
//								line.setstereotype(association.getStereotype());
								System.out.println("Line Type : "+dependencyView.getStereotype());
							}
							if(start != null){
								line.detachSource();
								line.setSource(start);
								line.setSourceTerminal("B");
								line.attachSource();
							}
							if(end != null){
								line.detachTarget();
								line.setTarget(end);
								line.setTargetTerminal("B");
								line.attachTarget(dModel);
							}
						}
					}
				}
			}
			else if(lineObj instanceof InheritView){
				InheritView inheritView = (InheritView) lineObj;
				Object tempObj = this.keyMap.get(inheritView.getQuidu());
				if(tempObj != null){
					if(tempObj instanceof RelationshipData){
						LineModel line = new GeneralizeLineModel();
						RelationshipData relationshipData = (RelationshipData)tempObj;					
						
						Object q1 = sharedModels.get(keyMap.get(relationshipData.server));
						Object q2 = sharedModels.get(keyMap.get(relationshipData.client));
						
						UMLDiagramModel start = (UMLDiagramModel)q2;
						UMLDiagramModel end = (UMLDiagramModel)q1;
						
						if(line != null){
							if(inheritView.getNameParameter() != null){	
								System.out.println(inheritView.getNameParameter());
								line.setName(inheritView.getNameParameter());
							}
							if(inheritView.getStereotype() != null){
//								line.setstereotype(association.getStereotype());
								System.out.println("Line Type : "+inheritView.getStereotype());
							}
							if(start != null){
								line.detachSource();
								line.setSource(start);
								line.setSourceTerminal("B");
								line.attachSource();
							}
							if(end != null){
								line.detachTarget();
								line.setTarget(end);
								line.setTargetTerminal("B");
								line.attachTarget(dModel);
							}
						}
					}
				}
				
			}
			
		}
	}
	public int setScope(String scope){
		int index = -1;
		if(scope.equals("Public")){
			index = 0 ;
		}
		else if(scope.equals("Private")){
			index = 1 ;
		}
		else if(scope.equals("Protected")){
			index = 2 ;
		}
		return index;
	}
	
	public void loadSequence(){
		Vector pkgNames = seqRose.packNames;
		ArrayList seqDgList = new ArrayList();
		N3EditorDiagramModel dModel = null;
		InteractionDiagram id = null;
		for(int i=0; i<pkgNames.size(); i++){
			String name = (String)pkgNames.get(i);
			Vector vc = null;
			if(seqRose.packDatas.size()-1>=i)
				vc = (Vector)seqRose.packDatas.get(i);
			else
				return;
			for(int j1=0; j1<vc.size(); j1++){
				id = (InteractionDiagram)vc.get(j1);
				
				UMLTreeParentModel packParent = searchPackage(root, name);
				dModel = ProjectManager.getInstance().addUMLDiagram((String)id.getParameterList().get(0), packParent, null, 1, false, 12);
								
//				dModel.setName(name+"."+(String)id.getParameterList().get(0));
				seqDgList.add(dModel);
				System.out.println(dModel.getName());
				
			}
			loadSeqDiagram(dModel, id);			
		}
	}
	
	public void loadSeqDiagram(N3EditorDiagramModel dModel, InteractionDiagram id){
		List list = id.getItems();
		
		
	}
}
