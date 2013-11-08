package kr.co.n3soft.n3com;

import java.util.ArrayList;
import java.util.List;

import kr.co.n3soft.n3com.lang.N3Messages;
import kr.co.n3soft.n3com.model.comm.ClassModel;
import kr.co.n3soft.n3com.model.comm.DataInputPortModel;
import kr.co.n3soft.n3com.model.comm.DataOutputPortModel;
import kr.co.n3soft.n3com.model.comm.EventInputPortModel;
import kr.co.n3soft.n3com.model.comm.EventOutputPortModel;
import kr.co.n3soft.n3com.model.comm.FinalPackageModel;
import kr.co.n3soft.n3com.model.comm.MethodInputPortModel;
import kr.co.n3soft.n3com.model.comm.MethodOutputPortModel;
import kr.co.n3soft.n3com.model.comm.UMLNoteModel;
import kr.co.n3soft.n3com.model.component.AtomicComponentModel;
import kr.co.n3soft.n3com.model.component.ComponentModel;
import kr.co.n3soft.n3com.model.component.OPRoSDataTypeElementModel;
import kr.co.n3soft.n3com.model.component.OPRoSExeEnvironmentCPUElementModel;
import kr.co.n3soft.n3com.model.component.OPRoSExeEnvironmentOSElementModel;
import kr.co.n3soft.n3com.model.component.OPRoSPropertyElementModel;
import kr.co.n3soft.n3com.model.component.OPRoSServiceTypeElementModel;
import kr.co.n3soft.n3com.model.composite.ConnectorLineModel;
import kr.co.n3soft.n3com.model.composite.DelegateLineModel;
import kr.co.n3soft.n3com.model.composite.OccurrenceLineModel;
import kr.co.n3soft.n3com.model.composite.PartModel;
import kr.co.n3soft.n3com.model.composite.ProvidedInterfaceLineModel;
import kr.co.n3soft.n3com.model.composite.RepresentsLineModel;
import kr.co.n3soft.n3com.model.composite.RequiredInterfaceLineModel;
import kr.co.n3soft.n3com.model.composite.RoleBindingLineModel;
import kr.co.n3soft.n3com.model.umlclass.DependencyLineModel;
import kr.co.n3soft.n3com.model.umlclass.EnumerationModel;
import kr.co.n3soft.n3com.model.umlclass.FinalClassModel;
import kr.co.n3soft.n3com.model.umlclass.ImportLineModel;
import kr.co.n3soft.n3com.model.umlclass.InterfaceModel;
import kr.co.n3soft.n3com.model.umlclass.MergeLineModel;
import kr.co.n3soft.n3com.model.usecase.AssociateLineModel;
import kr.co.n3soft.n3com.model.usecase.CollaborationModel;
import kr.co.n3soft.n3com.model.usecase.ExtendLineModel;
import kr.co.n3soft.n3com.model.usecase.FinalActorModel;
import kr.co.n3soft.n3com.model.usecase.FinalBoundryModel;
import kr.co.n3soft.n3com.model.usecase.GeneralizeLineModel;
import kr.co.n3soft.n3com.model.usecase.IncludeLineModel;
import kr.co.n3soft.n3com.model.usecase.NoteLineModel;
import kr.co.n3soft.n3com.model.usecase.RealizeLineModel;
import kr.co.n3soft.n3com.model.usecase.UseCaseModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.gef.examples.logicdesigner.LogicMessages;
import org.eclipse.gef.examples.logicdesigner.model.Circuit;
import org.eclipse.gef.palette.CombinedTemplateCreationEntry;
import org.eclipse.gef.palette.ConnectionCreationToolEntry;
import org.eclipse.gef.palette.MarqueeToolEntry;
import org.eclipse.gef.palette.PaletteContainer;
import org.eclipse.gef.palette.PaletteDrawer;
import org.eclipse.gef.palette.PaletteEntry;
import org.eclipse.gef.palette.PaletteGroup;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.PaletteStack;
import org.eclipse.gef.palette.PanningSelectionToolEntry;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.gef.requests.SimpleFactory;
import org.eclipse.gef.tools.MarqueeSelectionTool;
import org.eclipse.jface.resource.ImageDescriptor;
import org.osgi.framework.BundleContext;

public class N3Plugin extends org.eclipse.ui.plugin.AbstractUIPlugin {
	private static N3Plugin singleton;
//	public  PaletteDrawer createUseCaseDiagramDrawer = null;
//	public  PaletteDrawer createCompositeDiagramDrawer = null;
//	public  PaletteDrawer createClassDiagramDrawer = null;
//	public  PaletteDrawer createActivityDiagramDrawer = null;
//	public  PaletteDrawer createSequenceDiagramDrawer = null;
//	public  PaletteDrawer createComponentDiagramDrawer = null;
//	public  PaletteDrawer createStateDiagramDrawer = null;
//	public  PaletteDrawer createDeploymentDiagramDrawer = null;
//	public  PaletteDrawer createCommunicationDiagramDrawer = null;
//	public  PaletteDrawer createTimingDiagramDrawer = null;

//	public void open(int num){
//	if(num==8){
//	createUseCaseDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_OPEN);
////	createUseCaseDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);
//	createCompositeDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);
//	createClassDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);
//	createActivityDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);
//	createSequenceDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);
//	createComponentDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);
//	createStateDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);
//	createDeploymentDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);
//	createCommunicationDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);
//	createTimingDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);
//	}
//	else if(num==4){
//	createCompositeDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_OPEN);
//	createUseCaseDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);
////	createCompositeDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);
//	createClassDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);
//	createActivityDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);
//	createSequenceDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);
//	createComponentDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);
//	createStateDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);
//	createDeploymentDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);
//	createCommunicationDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);
//	createTimingDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);

//	}
//	else if(num==2){
//	createClassDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_OPEN);
//	createUseCaseDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);
//	createCompositeDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);
////	createClassDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);
//	createActivityDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);
//	createSequenceDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);
//	createComponentDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);
//	createStateDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);
//	createDeploymentDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);
//	createCommunicationDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);
//	createTimingDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);
//	}
//	else if(num==9){
//	createActivityDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_OPEN);
//	createUseCaseDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);
//	createCompositeDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);
//	createClassDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);
////	createActivityDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);
//	createSequenceDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);
//	createComponentDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);
//	createStateDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);
//	createDeploymentDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);
//	createCommunicationDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);
//	createTimingDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);
//	}
//	else if(num==12){
//	createSequenceDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_OPEN);
//	createUseCaseDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);
//	createCompositeDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);
//	createClassDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);
//	createActivityDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);
////	createSequenceDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);
//	createComponentDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);
//	createStateDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);
//	createDeploymentDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);
//	createCommunicationDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);
//	createTimingDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);
//	}
//	else if(num==5){
//	createComponentDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_OPEN);
//	createUseCaseDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);
//	createCompositeDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);
//	createClassDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);
//	createActivityDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);
//	createSequenceDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);
////	createComponentDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);
//	createStateDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);
//	createDeploymentDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);
//	createCommunicationDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);
//	createTimingDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);
//	}
//	else if(num==10){
//	createStateDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_OPEN);
//	createUseCaseDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);
//	createCompositeDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);
//	createClassDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);
//	createActivityDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);
//	createSequenceDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);
//	createComponentDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);
////	createStateDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);
//	createDeploymentDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);
//	createCommunicationDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);
//	createTimingDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);
//	}
//	else if(num==6){
//	createDeploymentDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_OPEN);
//	createUseCaseDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);
//	createCompositeDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);
//	createClassDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);
//	createActivityDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);
//	createSequenceDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);
//	createComponentDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);
//	createStateDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);
////	createDeploymentDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);
//	createCommunicationDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);
//	createTimingDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);
//	}
//	else if(num==11){
//	createCommunicationDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_OPEN);
//	createUseCaseDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);
//	createCompositeDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);
//	createClassDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);
//	createActivityDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);
//	createSequenceDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);
//	createComponentDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);
//	createStateDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);
//	createDeploymentDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);
////	createCommunicationDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);
//	createTimingDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);
//	}
//	else if(num==13){
//	createTimingDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_OPEN);
//	createUseCaseDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);
//	createCompositeDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);
//	createClassDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);
//	createActivityDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);
//	createSequenceDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);
//	createComponentDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);
//	createStateDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);
//	createDeploymentDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);
//	createCommunicationDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);
////	createTimingDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_CLOSED);
//	}



//	}

	static private List createUseCaseDiagramCategories(PaletteRoot root) {


		PaletteContainer createUseCaseDiagramDrawer = createUseCaseDiagramDrawer();
		PaletteDrawer p = (PaletteDrawer)createUseCaseDiagramDrawer;
		
		
		if(ProjectManager.tempDiagramType!=8)
			p.setInitialState(p.INITIAL_STATE_CLOSED);


		
	

		PaletteContainer createComponentDiagramDrawer = createComponentDiagramDrawer();
		p = (PaletteDrawer)createComponentDiagramDrawer;
		if(ProjectManager.tempDiagramType!=5)
			p.setInitialState(p.INITIAL_STATE_CLOSED);

		

		List categories = new ArrayList();
//		if(ProjectManager.tempDiagramType!=-1){
		categories.add(createControlGroup(root));

		categories.add(createComponentDiagramDrawer);





		//		createStateDiagramDrawer
		//		createActivityDiagramDrawer
		categories.add(createCommonDrawer());
//		}

//		root.setDefaultEntry(usecase.);
		return categories;
	}
	
	static private List createAtomicDiagramCategories(PaletteRoot root) {


//		PaletteContainer createUseCaseDiagramDrawer = createUseCaseDiagramDrawer();
//		PaletteDrawer p = (PaletteDrawer)createUseCaseDiagramDrawer;
//		if(ProjectManager.tempDiagramType!=8)
//			p.setInitialState(p.INITIAL_STATE_CLOSED);
//
//
//		
//
//
//
//		PaletteContainer createAtomicDiagramDrawer = createPortToolDiagramDrawer();
//		p = (PaletteDrawer)createAtomicDiagramDrawer;
		
		

		List categories = new ArrayList();
		categories.add(createControlGroup(root));

		categories.add(createPortToolDiagramDrawer());
		categories.add(createInfoToolDiagramDrawer());





		//		createStateDiagramDrawer
		//		createActivityDiagramDrawer
		categories.add(createCommonDrawer());

//		root.setDefaultEntry(usecase.);
		return categories;
	}

	static private PaletteContainer createCommonDrawer() {
		ToolEntry tool = new PanningSelectionToolEntry();
		PaletteDrawer drawer = new PaletteDrawer("Notes", ImageDescriptor.createFromFile(UseCaseModel.class, "icons/note_titel.gif")); //$NON-NLS-1$
		List entries = new ArrayList();
		//		N3MessageFactory.getInstance();
		CombinedTemplateCreationEntry combined = new
		CombinedTemplateCreationEntry(N3Messages.PALETTE_NOTE, LogicMessages.LogicPlugin_Tool_CreationTool_FlowContainer_Description,
				//이미지 교체
				new SimpleFactory(UMLNoteModel.class), ImageDescriptor.createFromFile(UseCaseModel.class, "icons/icno_16x16/24_note.png"), //$NON-NLS-1$
//				new SimpleFactory(UMLNoteModel.class), ImageDescriptor.createFromFile(UseCaseModel.class, "icons/note.gif"), //$NON-NLS-1$
				ImageDescriptor.createFromFile(UseCaseModel.class, "icons/note.gif") //$NON-NLS-1$
		);
		entries.add(combined);
		tool = new ConnectionCreationToolEntry(N3Messages.PALETTE_NOTELINK, LogicMessages.LogicPlugin_Tool_ConnectionCreationTool_ConnectionCreationTool_Description,
				new SimpleFactory(NoteLineModel.class), ImageDescriptor.createFromFile(UseCaseModel.class, "icons/notelink.gif"), //$NON-NLS-1$
				ImageDescriptor.createFromFile(Circuit.class, "icons/connection24.gif") //$NON-NLS-1$
		);
		entries.add(tool);
		
		// test
//		CombinedTemplateCreationEntry test = new
//		CombinedTemplateCreationEntry("TEST", "ETSTSAFDGAYSGDISGDIUGSIDUGA",
//				//이미지 교체
//				new SimpleFactory(UMLNoteModel.class), ImageDescriptor.createFromFile(UseCaseModel.class, "icons/icno_16x16/24_note.png"), //$NON-NLS-1$
////				new SimpleFactory(UMLNoteModel.class), ImageDescriptor.createFromFile(UseCaseModel.class, "icons/note.gif"), //$NON-NLS-1$
//				ImageDescriptor.createFromFile(Circuit.class, "icons/note.gif") //$NON-NLS-1$
//		);
//		entries.add(test);
//		// TEST 끝
//		
		drawer.addAll(entries);

		return drawer;
	}

	

	static private PaletteContainer createUseCaseDiagramDrawer() {
		ToolEntry tool = new PanningSelectionToolEntry();
		PaletteDrawer drawer = new PaletteDrawer(N3Messages.PALETTE_TITLE_USECASE, ImageDescriptor.createFromFile(UseCaseModel.class, "icons/usecase_title.gif")); //$NON-NLS-1$
//		createUseCaseDiagramDrawer.setInitialState(createUseCaseDiagramDrawer.INITIAL_STATE_OPEN);
		List entries = new ArrayList();
		//		N3MessageFactory.getInstance();
		CombinedTemplateCreationEntry combined = new
		CombinedTemplateCreationEntry(N3Messages.PALETTE_ACTOR, LogicMessages.LogicPlugin_Tool_CreationTool_FlowContainer_Description,
				new SimpleFactory(FinalActorModel.class), ImageDescriptor.createFromFile(UseCaseModel.class, "icons/actor.gif"), //$NON-NLS-1$
				ImageDescriptor.createFromFile(UseCaseModel.class, "icons/usecase.gif") //$NON-NLS-1$
		);
		entries.add(combined);
		combined = new CombinedTemplateCreationEntry(N3Messages.PALETTE_USECASE, LogicMessages.LogicPlugin_Tool_CreationTool_FlowContainer_Description,
				new SimpleFactory(UseCaseModel.class), ImageDescriptor.createFromFile(UseCaseModel.class, "icons/usecase.gif"), //$NON-NLS-1$
				ImageDescriptor.createFromFile(FinalActorModel.class, "icons/actor.gif") //$NON-NLS-1$
		);
		entries.add(combined);
		combined = new CombinedTemplateCreationEntry(N3Messages.PALETTE_COLLABORATION, LogicMessages.LogicPlugin_Tool_CreationTool_FlowContainer_Description,
				new SimpleFactory(CollaborationModel.class), ImageDescriptor.createFromFile(UseCaseModel.class, "icons/collaboration.gif"), //$NON-NLS-1$
				ImageDescriptor.createFromFile(CollaborationModel.class, "icons/collaboration.gif") //$NON-NLS-1$
		);
		entries.add(combined);
		combined = new CombinedTemplateCreationEntry(N3Messages.PALETTE_BOUNDARY, LogicMessages.LogicPlugin_Tool_CreationTool_FlowContainer_Description,
				new SimpleFactory(FinalBoundryModel.class), ImageDescriptor.createFromFile(UseCaseModel.class, "icons/boundary.gif"), //$NON-NLS-1$
				ImageDescriptor.createFromFile(FinalBoundryModel.class, "icons/boundary.gif") //$NON-NLS-1$
		);
		entries.add(combined);
		combined = new CombinedTemplateCreationEntry(N3Messages.PALETTE_PACKAGE, LogicMessages.LogicPlugin_Tool_CreationTool_FlowContainer_Description,
				new SimpleFactory(FinalPackageModel.class), ImageDescriptor.createFromFile(UseCaseModel.class, "icons/package.gif"), //$NON-NLS-1$
				ImageDescriptor.createFromFile(FinalPackageModel.class, "icons/package.gif") //$NON-NLS-1$
		);
		entries.add(combined);
		tool = new ConnectionCreationToolEntry(N3Messages.PALETTE_ASSOCIATE, LogicMessages.LogicPlugin_Tool_ConnectionCreationTool_ConnectionCreationTool_Description,
				new SimpleFactory(AssociateLineModel.class), ImageDescriptor.createFromFile(UseCaseModel.class, "icons/associate.gif"), //$NON-NLS-1$
				ImageDescriptor.createFromFile(AssociateLineModel.class, "icons/associate.gif") //$NON-NLS-1$
		);
		entries.add(tool);
		tool = new ConnectionCreationToolEntry(N3Messages.PALETTE_GENERALIZATION, LogicMessages.LogicPlugin_Tool_ConnectionCreationTool_ConnectionCreationTool_Description,
				new SimpleFactory(GeneralizeLineModel.class), ImageDescriptor.createFromFile(UseCaseModel.class, "icons/generalization.gif"), //$NON-NLS-1$
				ImageDescriptor.createFromFile(GeneralizeLineModel.class, "icons/generalization.gif") //$NON-NLS-1$
		);
		entries.add(tool);
		tool = new ConnectionCreationToolEntry(N3Messages.PALETTE_INCLUDE, LogicMessages.LogicPlugin_Tool_ConnectionCreationTool_ConnectionCreationTool_Description,
				new SimpleFactory(IncludeLineModel.class), ImageDescriptor.createFromFile(UseCaseModel.class, "icons/include.gif"), //$NON-NLS-1$
				ImageDescriptor.createFromFile(IncludeLineModel.class, "icons/include.gif") //$NON-NLS-1$
		);
		entries.add(tool);
		tool = new ConnectionCreationToolEntry(N3Messages.PALETTE_EXTEND, LogicMessages.LogicPlugin_Tool_ConnectionCreationTool_ConnectionCreationTool_Description,
				new SimpleFactory(ExtendLineModel.class), ImageDescriptor.createFromFile(UseCaseModel.class, "icons/extends.gif"), //$NON-NLS-1$
				ImageDescriptor.createFromFile(ExtendLineModel.class, "icons/extends.gif") //$NON-NLS-1$
		);
		entries.add(tool);
		tool = new ConnectionCreationToolEntry(N3Messages.PALETTE_REALIZE, LogicMessages.LogicPlugin_Tool_ConnectionCreationTool_ConnectionCreationTool_Description,
				new SimpleFactory(RealizeLineModel.class), ImageDescriptor.createFromFile(UseCaseModel.class, "icons/realize.gif"), //$NON-NLS-1$
				ImageDescriptor.createFromFile(RealizeLineModel.class, "icons/realize.gif") //$NON-NLS-1$
		);
		entries.add(tool);
		drawer.addAll(entries);
		return drawer;
	}

	//선 등
	static private PaletteContainer createCompositeDiagramDrawer() {
		ToolEntry tool = new PanningSelectionToolEntry();
		PaletteDrawer drawer = new PaletteDrawer(N3Messages.PALETTE_TITLE_COMPOSITE, ImageDescriptor.createFromFile(UseCaseModel.class, "icons/composite_titel.gif")); //$NON-NLS-1$
		List entries = new ArrayList();
		//		N3MessageFactory.getInstance();
		//		CombinedTemplateCreationEntry combined = new CombinedTemplateCreationEntry(
		//				"클래스",
		//				LogicMessages.LogicPlugin_Tool_CreationTool_FlowContainer_Description,
		//				new SimpleFactory(ClassModel.class),
		//				ImageDescriptor.createFromFile(ClassModel.class, "icons/usecase16.gif"), //$NON-NLS-1$
		//				ImageDescriptor.createFromFile(ClassModel.class, "icons/usecase16.gif")//$NON-NLS-1$
		//		);
		//		entries.add(combined);
		CombinedTemplateCreationEntry combined = new
		CombinedTemplateCreationEntry(N3Messages.PALETTE_CLASS, LogicMessages.LogicPlugin_Tool_CreationTool_FlowContainer_Description,
				new SimpleFactory(FinalClassModel.class), ImageDescriptor.createFromFile(UseCaseModel.class, "icons/class.gif"), //$NON-NLS-1$
				ImageDescriptor.createFromFile(FinalClassModel.class, "icons/usecase16.gif") //$NON-NLS-1$
		);
		entries.add(combined);
		combined = new CombinedTemplateCreationEntry(N3Messages.PALETTE_INTERFACE, LogicMessages.LogicPlugin_Tool_CreationTool_FlowContainer_Description,
				new SimpleFactory(InterfaceModel.class), ImageDescriptor.createFromFile(UseCaseModel.class, "icons/Interface.gif"), //$NON-NLS-1$
				ImageDescriptor.createFromFile(ClassModel.class, "icons/usecase16.gif") //$NON-NLS-1$
		);
		entries.add(combined);
		combined = new CombinedTemplateCreationEntry(N3Messages.PALETTE_PART, LogicMessages.LogicPlugin_Tool_CreationTool_FlowContainer_Description,
				new SimpleFactory(PartModel.class), ImageDescriptor.createFromFile(UseCaseModel.class, "icons/part.gif"), //$NON-NLS-1$
				ImageDescriptor.createFromFile(PartModel.class, "icons/package16.gif") //$NON-NLS-1$
		);
		entries.add(combined);
		combined = new CombinedTemplateCreationEntry(N3Messages.PALETTE_COLLABORATION, LogicMessages.LogicPlugin_Tool_CreationTool_FlowContainer_Description,
				new SimpleFactory(CollaborationModel.class), ImageDescriptor.createFromFile(UseCaseModel.class, "icons/collaboration.gif"), //$NON-NLS-1$
				ImageDescriptor.createFromFile(CollaborationModel.class, "icons/usecase16.gif") //$NON-NLS-1$
		);
		entries.add(combined);
		tool = new ConnectionCreationToolEntry(N3Messages.PALETTE_REQUIREDINTERFACE,
				LogicMessages.LogicPlugin_Tool_ConnectionCreationTool_ConnectionCreationTool_Description,
				new SimpleFactory(RequiredInterfaceLineModel.class),
				ImageDescriptor.createFromFile(UseCaseModel.class, "icons/pro.gif"), //PKY 08080501 S 모델 그림자 넣기 작업
				ImageDescriptor.createFromFile(RequiredInterfaceLineModel.class, "icons/association.gif") //$NON-NLS-1$
		);
		entries.add(tool);
		tool = new ConnectionCreationToolEntry(N3Messages.PALETTE_PROVIDEDINTERFACE,
				LogicMessages.LogicPlugin_Tool_ConnectionCreationTool_ConnectionCreationTool_Description,
				new SimpleFactory(ProvidedInterfaceLineModel.class),
				ImageDescriptor.createFromFile(UseCaseModel.class, "icons/req.gif"), //PKY 08080501 S 모델 그림자 넣기 작업
				ImageDescriptor.createFromFile(ProvidedInterfaceLineModel.class, "icons/association.gif") //$NON-NLS-1$
		);
		entries.add(tool);
		tool = new ConnectionCreationToolEntry(N3Messages.PALETTE_CONNECTOR,
				LogicMessages.LogicPlugin_Tool_ConnectionCreationTool_ConnectionCreationTool_Description,
				new SimpleFactory(ConnectorLineModel.class), ImageDescriptor.createFromFile(UseCaseModel.class, "icons/connector.gif"), //$NON-NLS-1$
				ImageDescriptor.createFromFile(ConnectorLineModel.class, "icons/generalization.gif") //$NON-NLS-1$
		);
		entries.add(tool);
		tool = new ConnectionCreationToolEntry(N3Messages.PALETTE_DELEGATE,
				LogicMessages.LogicPlugin_Tool_ConnectionCreationTool_ConnectionCreationTool_Description,
				new SimpleFactory(DelegateLineModel.class), ImageDescriptor.createFromFile(UseCaseModel.class, "icons/delegate.gif"), //$NON-NLS-1$
				ImageDescriptor.createFromFile(DelegateLineModel.class, "icons/connection24.gif") //$NON-NLS-1$
		);
		entries.add(tool);
		tool = new ConnectionCreationToolEntry(N3Messages.PALETTE_ROLEBINDING,
				LogicMessages.LogicPlugin_Tool_ConnectionCreationTool_ConnectionCreationTool_Description,
				new SimpleFactory(RoleBindingLineModel.class), ImageDescriptor.createFromFile(UseCaseModel.class, "icons/role_binding.gif"), //$NON-NLS-1$
				ImageDescriptor.createFromFile(RoleBindingLineModel.class, "icons/connection24.gif") //$NON-NLS-1$
		);
		entries.add(tool);
		tool = new ConnectionCreationToolEntry(N3Messages.PALETTE_REPRESENTS,
				LogicMessages.LogicPlugin_Tool_ConnectionCreationTool_ConnectionCreationTool_Description,
				new SimpleFactory(RepresentsLineModel.class), ImageDescriptor.createFromFile(UseCaseModel.class, "icons/represents.gif"), //$NON-NLS-1$
				ImageDescriptor.createFromFile(RepresentsLineModel.class, "icons/connection24.gif") //$NON-NLS-1$
		);
		entries.add(tool);
		tool = new ConnectionCreationToolEntry(N3Messages.PALETTE_OCCURRENCE,
				LogicMessages.LogicPlugin_Tool_ConnectionCreationTool_ConnectionCreationTool_Description,
				new SimpleFactory(OccurrenceLineModel.class), ImageDescriptor.createFromFile(UseCaseModel.class, "icons/occurrence.gif"), //$NON-NLS-1$
				ImageDescriptor.createFromFile(OccurrenceLineModel.class, "icons/connection24.gif") //$NON-NLS-1$
		);
		entries.add(tool);
		//		RoleBindingLineModel

		drawer.addAll(entries);
		return drawer;
	}

	static private PaletteContainer createStateDiagramDrawer() {
		ToolEntry tool = new PanningSelectionToolEntry();
		PaletteDrawer drawer = new PaletteDrawer(N3Messages.PALETTE_TITLE_STATE, ImageDescriptor.createFromFile(UseCaseModel.class, "icons/state_title.gif")); //$NON-NLS-1$
		
		return drawer;
	}
	
	
	
	static private PaletteContainer createPortToolDiagramDrawer() {
		ToolEntry tool = new PanningSelectionToolEntry();
		PaletteDrawer drawer = new PaletteDrawer("Port Tool", ImageDescriptor.createFromFile(UseCaseModel.class, "icons/component_title.gif")); //$NON-NLS-1$
		List entries = new ArrayList();

//		CombinedTemplateCreationEntry combined = new
//		CombinedTemplateCreationEntry("Composite", LogicMessages.LogicPlugin_Tool_CreationTool_FlowContainer_Description,
//				new SimpleFactory(ComponentModel.class), ImageDescriptor.createFromFile(UseCaseModel.class, "icons/16x16_Composite.gif"), // WJH 090809
//				ImageDescriptor.createFromFile(ComponentModel.class, "icons/usecase16.gif") //$NON-NLS-1$
//		);
//		entries.add(combined);
//		 combined = new
//		CombinedTemplateCreationEntry("Atomic", LogicMessages.LogicPlugin_Tool_CreationTool_FlowContainer_Description,
//				new SimpleFactory(AtomicComponentModel.class), ImageDescriptor.createFromFile(UseCaseModel.class, "icons/16x16_Atomic.gif"), // WJH 090809
//				ImageDescriptor.createFromFile(ComponentModel.class, "icons/16x16_Atomic.gif") //$NON-NLS-1$
//		);
//		entries.add(combined);

		CombinedTemplateCreationEntry combined = new CombinedTemplateCreationEntry("Service Required",
				LogicMessages.LogicPlugin_Tool_ConnectionCreationTool_ConnectionCreationTool_Description,
				new SimpleFactory(MethodInputPortModel.class),
				ImageDescriptor.createFromFile(UseCaseModel.class, "icons/icno_16x16/24_MethodRequired.png"), // WJH 090809
				ImageDescriptor.createFromFile(ProvidedInterfaceLineModel.class, "icons/req.gif") //PKY 08081101 S
		);
		entries.add(combined);
		combined = new CombinedTemplateCreationEntry("Service Provided",
				LogicMessages.LogicPlugin_Tool_ConnectionCreationTool_ConnectionCreationTool_Description,
				new SimpleFactory(MethodOutputPortModel.class),
				ImageDescriptor.createFromFile(UseCaseModel.class, "icons/icno_16x16/24_MethodProvided.png"), // WJH 090809
				ImageDescriptor.createFromFile(RequiredInterfaceLineModel.class, "icons/pro.gif")//PKY 08081101 S
		);
		entries.add(combined);
		
		
		combined = new CombinedTemplateCreationEntry("Data OutputPort",
				LogicMessages.LogicPlugin_Tool_ConnectionCreationTool_ConnectionCreationTool_Description,
				new SimpleFactory(DataOutputPortModel.class),
				ImageDescriptor.createFromFile(UseCaseModel.class, "icons/icno_16x16/24_DataOutputPort.png"), // WJH 090809
				ImageDescriptor.createFromFile(ProvidedInterfaceLineModel.class, "icons/req.gif") //PKY 08081101 S
		);
		entries.add(combined);
		combined = new CombinedTemplateCreationEntry("Data InputPort",
				LogicMessages.LogicPlugin_Tool_ConnectionCreationTool_ConnectionCreationTool_Description,
				new SimpleFactory(DataInputPortModel.class),
				ImageDescriptor.createFromFile(UseCaseModel.class, "icons/icno_16x16/24_DataInputPort.png"), // WJH 090809
				ImageDescriptor.createFromFile(RequiredInterfaceLineModel.class, "icons/pro.gif")//PKY 08081101 S
		);
		entries.add(combined);
		
		combined = new CombinedTemplateCreationEntry("Event OutputPort",
				LogicMessages.LogicPlugin_Tool_ConnectionCreationTool_ConnectionCreationTool_Description,
				new SimpleFactory(EventOutputPortModel.class),
				ImageDescriptor.createFromFile(UseCaseModel.class, "icons/icno_16x16/24_EventOutputPort.png"),// WJH 090809
				ImageDescriptor.createFromFile(ProvidedInterfaceLineModel.class, "icons/req.gif") //PKY 08081101 S
		);
		entries.add(combined);
		combined = new CombinedTemplateCreationEntry("Event InputPort",
				LogicMessages.LogicPlugin_Tool_ConnectionCreationTool_ConnectionCreationTool_Description,
				new SimpleFactory(EventInputPortModel.class),
				ImageDescriptor.createFromFile(UseCaseModel.class, "icons/icno_16x16/24_EventInputPort.png"), // WJH 090809
				ImageDescriptor.createFromFile(RequiredInterfaceLineModel.class, "icons/pro.gif")//PKY 08081101 S
		);
		entries.add(combined);
//		tool = new ConnectionCreationToolEntry("Connection", LogicMessages.LogicPlugin_Tool_ConnectionCreationTool_ConnectionCreationTool_Description,
//				new SimpleFactory(DependencyLineModel.class), ImageDescriptor.createFromFile(UseCaseModel.class, "icons/16x16_Connect.gif"), // WJH 090809
//				ImageDescriptor.createFromFile(UseCaseModel.class, "icons/dependency.gif") //$NON-NLS-1$
//		);
//		entries.add(tool);

		drawer.addAll(entries);
		return drawer;
	}
	
	
	static private PaletteContainer createInfoToolDiagramDrawer() {
		ToolEntry tool = new PanningSelectionToolEntry();
		PaletteDrawer drawer = new PaletteDrawer("Info Tool", ImageDescriptor.createFromFile(UseCaseModel.class, "icons/component_title.gif")); //$NON-NLS-1$
		List entries = new ArrayList();

//		CombinedTemplateCreationEntry combined = new
//		CombinedTemplateCreationEntry("Composite", LogicMessages.LogicPlugin_Tool_CreationTool_FlowContainer_Description,
//				new SimpleFactory(ComponentModel.class), ImageDescriptor.createFromFile(UseCaseModel.class, "icons/16x16_Composite.gif"), // WJH 090809
//				ImageDescriptor.createFromFile(ComponentModel.class, "icons/usecase16.gif") //$NON-NLS-1$
//		);
//		entries.add(combined);
//		 combined = new
//		CombinedTemplateCreationEntry("Atomic", LogicMessages.LogicPlugin_Tool_CreationTool_FlowContainer_Description,
//				new SimpleFactory(AtomicComponentModel.class), ImageDescriptor.createFromFile(UseCaseModel.class, "icons/16x16_Atomic.gif"), // WJH 090809
//				ImageDescriptor.createFromFile(ComponentModel.class, "icons/16x16_Atomic.gif") //$NON-NLS-1$
//		);
//		entries.add(combined);

		
//		CombinedTemplateCreationEntry combined = new CombinedTemplateCreationEntry("OS ExeEnv",
//				LogicMessages.LogicPlugin_Tool_ConnectionCreationTool_ConnectionCreationTool_Description,
//				new SimpleFactory(OPRoSExeEnvironmentOSElementModel.class),
//				ImageDescriptor.createFromFile(UseCaseModel.class, "icons/os_icon.gif"), // WJH 090809
//				ImageDescriptor.createFromFile(RequiredInterfaceLineModel.class, "icons/pro.gif")//PKY 08081101 S
//		);
//		entries.add(combined);
//		combined = new CombinedTemplateCreationEntry("CPU ExeEnv",
//				LogicMessages.LogicPlugin_Tool_ConnectionCreationTool_ConnectionCreationTool_Description,
//				new SimpleFactory(OPRoSExeEnvironmentCPUElementModel.class),
//				ImageDescriptor.createFromFile(UseCaseModel.class, "icons/cpu_icon.gif"), // WJH 090809
//				ImageDescriptor.createFromFile(UseCaseModel.class, "icons/req.gif") //PKY 08081101 S
//		);
//		entries.add(combined);
		CombinedTemplateCreationEntry combined = new CombinedTemplateCreationEntry("Property",
				LogicMessages.LogicPlugin_Tool_ConnectionCreationTool_ConnectionCreationTool_Description,
				new SimpleFactory(OPRoSPropertyElementModel.class),
				ImageDescriptor.createFromFile(UseCaseModel.class, "icons/property_icon.gif"), // WJH 090809
				ImageDescriptor.createFromFile(RequiredInterfaceLineModel.class, "icons/pro.gif")//PKY 08081101 S
		);
		entries.add(combined);
		combined = new CombinedTemplateCreationEntry("Data Type",
				LogicMessages.LogicPlugin_Tool_ConnectionCreationTool_ConnectionCreationTool_Description,
				new SimpleFactory(OPRoSDataTypeElementModel.class),
				ImageDescriptor.createFromFile(UseCaseModel.class, "icons/data_type_icon.gif"), // WJH 090809
				ImageDescriptor.createFromFile(ProvidedInterfaceLineModel.class, "icons/req.gif") //PKY 08081101 S
		);
		entries.add(combined);
		combined = new CombinedTemplateCreationEntry("Service Type",
				LogicMessages.LogicPlugin_Tool_ConnectionCreationTool_ConnectionCreationTool_Description,
				new SimpleFactory(OPRoSServiceTypeElementModel.class),
				ImageDescriptor.createFromFile(UseCaseModel.class, "icons/service_type_icon.gif"), // WJH 090809
				ImageDescriptor.createFromFile(RequiredInterfaceLineModel.class, "icons/pro.gif")//PKY 08081101 S
		);
		entries.add(combined);
		
//		entries.add(tool);

		drawer.addAll(entries);
		return drawer;
	}

	

	static private PaletteContainer createComponentDiagramDrawer() {
		ToolEntry tool = new PanningSelectionToolEntry();
		//이미지 교체?
//		PaletteDrawer drawer = new PaletteDrawer("Components", ImageDescriptor.createFromFile(UseCaseModel.class, "icons/icno_16x16/10_Diagram.png")); //$NON-NLS-1$
		PaletteDrawer drawer = new PaletteDrawer("Components", ImageDescriptor.createFromFile(UseCaseModel.class, "icons/component_title.gif")); //$NON-NLS-1$
		List entries = new ArrayList();

		//이미지 교체
		CombinedTemplateCreationEntry combined = new
		CombinedTemplateCreationEntry("Composite", LogicMessages.LogicPlugin_Tool_CreationTool_FlowContainer_Description,
				new SimpleFactory(ComponentModel.class), ImageDescriptor.createFromFile(UseCaseModel.class, "icons/icno_16x16/23_Composite.png"), // WJH 090809
				ImageDescriptor.createFromFile(ComponentModel.class, "icons/usecase16.gif") //$NON-NLS-1$
		);
		entries.add(combined);
		 combined = new
		CombinedTemplateCreationEntry("Atomic", LogicMessages.LogicPlugin_Tool_CreationTool_FlowContainer_Description,
				new SimpleFactory(AtomicComponentModel.class), ImageDescriptor.createFromFile(UseCaseModel.class, "icons/icno_16x16/11_Automic.png"), // WJH 090809
				ImageDescriptor.createFromFile(ComponentModel.class, "icons/16x16_Atomic.gif") //$NON-NLS-1$
		);
		entries.add(combined);

		
		combined = new CombinedTemplateCreationEntry("Service Required",
				LogicMessages.LogicPlugin_Tool_ConnectionCreationTool_ConnectionCreationTool_Description,
				new SimpleFactory(MethodInputPortModel.class),
				ImageDescriptor.createFromFile(UseCaseModel.class, "icons/icno_16x16/24_MethodRequired.png"), // WJH 090809
				ImageDescriptor.createFromFile(ProvidedInterfaceLineModel.class, "icons/req.gif") //PKY 08081101 S
		);
		entries.add(combined);
		combined = new CombinedTemplateCreationEntry("Service Provided",
				LogicMessages.LogicPlugin_Tool_ConnectionCreationTool_ConnectionCreationTool_Description,
				new SimpleFactory(MethodOutputPortModel.class),
				ImageDescriptor.createFromFile(UseCaseModel.class, "icons/icno_16x16/24_MethodProvided.png"), // WJH 090809
				ImageDescriptor.createFromFile(RequiredInterfaceLineModel.class, "icons/pro.gif")//PKY 08081101 S
		);
		entries.add(combined);
		
		combined = new CombinedTemplateCreationEntry("Data OutputPort",
				LogicMessages.LogicPlugin_Tool_ConnectionCreationTool_ConnectionCreationTool_Description,
				new SimpleFactory(DataOutputPortModel.class),
				ImageDescriptor.createFromFile(UseCaseModel.class, "icons/icno_16x16/24_DataOutputPort.png"), // WJH 090809
				ImageDescriptor.createFromFile(ProvidedInterfaceLineModel.class, "icons/req.gif") //PKY 08081101 S
		);
		entries.add(combined);
		combined = new CombinedTemplateCreationEntry("Data InputPort",
				LogicMessages.LogicPlugin_Tool_ConnectionCreationTool_ConnectionCreationTool_Description,
				new SimpleFactory(DataInputPortModel.class),
				ImageDescriptor.createFromFile(UseCaseModel.class, "icons/icno_16x16/24_DataInputPort.png"), // WJH 090809
				ImageDescriptor.createFromFile(RequiredInterfaceLineModel.class, "icons/pro.gif")//PKY 08081101 S
		);
		entries.add(combined);
		
		combined = new CombinedTemplateCreationEntry("Event OutputPort",
				LogicMessages.LogicPlugin_Tool_ConnectionCreationTool_ConnectionCreationTool_Description,
				new SimpleFactory(EventOutputPortModel.class),
				ImageDescriptor.createFromFile(UseCaseModel.class, "icons/icno_16x16/24_EventOutputPort.png"),// WJH 090809
				ImageDescriptor.createFromFile(ProvidedInterfaceLineModel.class, "icons/req.gif") //PKY 08081101 S
		);
		entries.add(combined);
		combined = new CombinedTemplateCreationEntry("Event InputPort",
				LogicMessages.LogicPlugin_Tool_ConnectionCreationTool_ConnectionCreationTool_Description,
				new SimpleFactory(EventInputPortModel.class),
				ImageDescriptor.createFromFile(UseCaseModel.class, "icons/icno_16x16/24_EventInputPort.png"), // WJH 090809
				ImageDescriptor.createFromFile(RequiredInterfaceLineModel.class, "icons/pro.gif")//PKY 08081101 S
		);
		entries.add(combined);
		
		tool = new ConnectionCreationToolEntry("Connection", LogicMessages.LogicPlugin_Tool_ConnectionCreationTool_ConnectionCreationTool_Description,
				new SimpleFactory(DependencyLineModel.class), ImageDescriptor.createFromFile(UseCaseModel.class, "icons/16x16_Connect.gif"), // WJH 090809
				ImageDescriptor.createFromFile(UseCaseModel.class, "icons/dependency.gif") //$NON-NLS-1$
		);
		entries.add(tool);

		drawer.addAll(entries);
		return drawer;
	}
		/*
		CombinedTemplateCreationEntry combined = new
		CombinedTemplateCreationEntry("Composite", LogicMessages.LogicPlugin_Tool_CreationTool_FlowContainer_Description,
				new SimpleFactory(ComponentModel.class), ImageDescriptor.createFromFile(UseCaseModel.class, "icons/16x16_Composite.gif"), // WJH 090809
				ImageDescriptor.createFromFile(ComponentModel.class, "icons/usecase16.gif") //$NON-NLS-1$
		);
		entries.add(combined);
		 combined = new
		CombinedTemplateCreationEntry("Atomic", LogicMessages.LogicPlugin_Tool_CreationTool_FlowContainer_Description,
				new SimpleFactory(AtomicComponentModel.class), ImageDescriptor.createFromFile(UseCaseModel.class, "icons/16x16_Atomic.gif"), // WJH 090809
				ImageDescriptor.createFromFile(ComponentModel.class, "icons/16x16_Atomic.gif") //$NON-NLS-1$
		);
		entries.add(combined);

		combined = new CombinedTemplateCreationEntry("Method Provided",
				LogicMessages.LogicPlugin_Tool_ConnectionCreationTool_ConnectionCreationTool_Description,
				new SimpleFactory(MethodOutputPortModel.class),
				ImageDescriptor.createFromFile(UseCaseModel.class, "icons/16x16_Method_Required.png"), // WJH 090809
				ImageDescriptor.createFromFile(RequiredInterfaceLineModel.class, "icons/pro.gif")//PKY 08081101 S
		);
		entries.add(combined);
		combined = new CombinedTemplateCreationEntry("Method Required",
				LogicMessages.LogicPlugin_Tool_ConnectionCreationTool_ConnectionCreationTool_Description,
				new SimpleFactory(MethodInputPortModel.class),
				ImageDescriptor.createFromFile(UseCaseModel.class, "icons/16x16_Method_Provided.png"), // WJH 090809
				ImageDescriptor.createFromFile(ProvidedInterfaceLineModel.class, "icons/req.gif") //PKY 08081101 S
		);
		entries.add(combined);
		combined = new CombinedTemplateCreationEntry("Data InputPort",
				LogicMessages.LogicPlugin_Tool_ConnectionCreationTool_ConnectionCreationTool_Description,
				new SimpleFactory(DataInputPortModel.class),
				ImageDescriptor.createFromFile(UseCaseModel.class, "icons/16x16_Data_InputPort.png"), // WJH 090809
				ImageDescriptor.createFromFile(RequiredInterfaceLineModel.class, "icons/pro.gif")//PKY 08081101 S
		);
		entries.add(combined);
		combined = new CombinedTemplateCreationEntry("Data OutputPort",
				LogicMessages.LogicPlugin_Tool_ConnectionCreationTool_ConnectionCreationTool_Description,
				new SimpleFactory(DataOutputPortModel.class),
				ImageDescriptor.createFromFile(UseCaseModel.class, "icons/16x16_Data_OutputPort.png"), // WJH 090809
				ImageDescriptor.createFromFile(ProvidedInterfaceLineModel.class, "icons/req.gif") //PKY 08081101 S
		);
		entries.add(combined);
		combined = new CombinedTemplateCreationEntry("Event InputPort",
				LogicMessages.LogicPlugin_Tool_ConnectionCreationTool_ConnectionCreationTool_Description,
				new SimpleFactory(EventInputPortModel.class),
				ImageDescriptor.createFromFile(UseCaseModel.class, "icons/16x16_Event_InputPort.png"), // WJH 090809
				ImageDescriptor.createFromFile(RequiredInterfaceLineModel.class, "icons/pro.gif")//PKY 08081101 S
		);
		entries.add(combined);
		combined = new CombinedTemplateCreationEntry("Event OutputPort",
				LogicMessages.LogicPlugin_Tool_ConnectionCreationTool_ConnectionCreationTool_Description,
				new SimpleFactory(EventOutputPortModel.class),
				ImageDescriptor.createFromFile(UseCaseModel.class, "icons/16x16_Event_OutputPort.png"),// WJH 090809
				ImageDescriptor.createFromFile(ProvidedInterfaceLineModel.class, "icons/req.gif") //PKY 08081101 S
		);
		entries.add(combined);
		tool = new ConnectionCreationToolEntry("Connection", LogicMessages.LogicPlugin_Tool_ConnectionCreationTool_ConnectionCreationTool_Description,
				new SimpleFactory(DependencyLineModel.class), ImageDescriptor.createFromFile(UseCaseModel.class, "icons/16x16_Connect.gif"), // WJH 090809
				ImageDescriptor.createFromFile(UseCaseModel.class, "icons/dependency.gif") //$NON-NLS-1$
		);
		entries.add(tool);

		drawer.addAll(entries);
		return drawer;
	}
*/
	static private PaletteContainer createClassDiagramDrawer() {
		ToolEntry tool = new PanningSelectionToolEntry();
		PaletteDrawer drawer = new PaletteDrawer(N3Messages.PALETTE_TITLE_CLASS, ImageDescriptor.createFromFile(UseCaseModel.class, "icons/class_title.gif")); //$NON-NLS-1$
		List entries = new ArrayList();
		//		N3MessageFactory.getInstance();
		//		CombinedTemplateCreationEntry combined = new CombinedTemplateCreationEntry(
		//				"클래스",
		//				LogicMessages.LogicPlugin_Tool_CreationTool_FlowContainer_Description,
		//				new SimpleFactory(ClassModel.class),
		//				ImageDescriptor.createFromFile(ClassModel.class, "icons/usecase16.gif"), //$NON-NLS-1$
		//				ImageDescriptor.createFromFile(ClassModel.class, "icons/usecase16.gif")//$NON-NLS-1$
		//		);
		//		entries.add(combined);
		CombinedTemplateCreationEntry combined = new
		CombinedTemplateCreationEntry(N3Messages.PALETTE_PACKAGE, LogicMessages.LogicPlugin_Tool_CreationTool_FlowContainer_Description,
				new SimpleFactory(FinalPackageModel.class), ImageDescriptor.createFromFile(UseCaseModel.class, "icons/package.gif"), //$NON-NLS-1$
				ImageDescriptor.createFromFile(FinalPackageModel.class, "icons/package.gif")
		);
		entries.add(combined);
		combined = new CombinedTemplateCreationEntry(N3Messages.PALETTE_CLASS, LogicMessages.LogicPlugin_Tool_CreationTool_FlowContainer_Description,
				new SimpleFactory(FinalClassModel.class), ImageDescriptor.createFromFile(UseCaseModel.class, "icons/class.gif"), //$NON-NLS-1$
				ImageDescriptor.createFromFile(FinalClassModel.class, "icons/class.gif") //$NON-NLS-1$
		);
		entries.add(combined);
		combined = new CombinedTemplateCreationEntry(N3Messages.PALETTE_INTERFACE, LogicMessages.LogicPlugin_Tool_CreationTool_FlowContainer_Description,
				new SimpleFactory(InterfaceModel.class), ImageDescriptor.createFromFile(UseCaseModel.class, "icons/interface.gif"), //$NON-NLS-1$
				ImageDescriptor.createFromFile(ClassModel.class, "icons/interface.gif") //$NON-NLS-1$
		);
		entries.add(combined);
		combined = new CombinedTemplateCreationEntry(N3Messages.PALETTE_ENUMERATION, LogicMessages.LogicPlugin_Tool_CreationTool_FlowContainer_Description,
				new SimpleFactory(EnumerationModel.class), ImageDescriptor.createFromFile(UseCaseModel.class, "icons/enumeration.gif"), //$NON-NLS-1$
				ImageDescriptor.createFromFile(ClassModel.class, "icons/enumeration.gif") //$NON-NLS-1$
		);
		entries.add(combined);
		tool = new ConnectionCreationToolEntry(N3Messages.PALETTE_ASSOCIATE, LogicMessages.LogicPlugin_Tool_ConnectionCreationTool_ConnectionCreationTool_Description,
				new SimpleFactory(AssociateLineModel.class), ImageDescriptor.createFromFile(AssociateLineModel.class, "icons/associate.gif"), //$NON-NLS-1$
				ImageDescriptor.createFromFile(AssociateLineModel.class, "icons/associate.gif") //$NON-NLS-1$
		);
		entries.add(tool);
		tool = new ConnectionCreationToolEntry(N3Messages.PALETTE_GENERALIZATION, LogicMessages.LogicPlugin_Tool_ConnectionCreationTool_ConnectionCreationTool_Description,
				new SimpleFactory(GeneralizeLineModel.class), ImageDescriptor.createFromFile(GeneralizeLineModel.class, "icons/generalization.gif"), //$NON-NLS-1$
				ImageDescriptor.createFromFile(GeneralizeLineModel.class, "icons/generalization.gif") //$NON-NLS-1$
		);
		entries.add(tool);
		tool = new ConnectionCreationToolEntry(N3Messages.PALETTE_REALIZE, LogicMessages.LogicPlugin_Tool_ConnectionCreationTool_ConnectionCreationTool_Description,
				new SimpleFactory(RealizeLineModel.class), ImageDescriptor.createFromFile(RealizeLineModel.class, "icons/realize.gif"), //$NON-NLS-1$
				ImageDescriptor.createFromFile(Circuit.class, "icons/connection24.gif") //$NON-NLS-1$
		);
		entries.add(tool);
		tool = new ConnectionCreationToolEntry(N3Messages.PALETTE_DEPENDENCY, LogicMessages.LogicPlugin_Tool_ConnectionCreationTool_ConnectionCreationTool_Description,
				new SimpleFactory(DependencyLineModel.class), ImageDescriptor.createFromFile(UseCaseModel.class, "icons/dependency.gif"), //$NON-NLS-1$
				ImageDescriptor.createFromFile(UseCaseModel.class, "icons/dependency.gif") //$NON-NLS-1$
		);
		entries.add(tool);


		tool = new ConnectionCreationToolEntry(N3Messages.PALETTE_MERGE, LogicMessages.LogicPlugin_Tool_ConnectionCreationTool_ConnectionCreationTool_Description,
				new SimpleFactory(MergeLineModel.class), ImageDescriptor.createFromFile(UseCaseModel.class, "icons/pkg_merge.gif"), //$NON-NLS-1$
				ImageDescriptor.createFromFile(UseCaseModel.class, "icons/pkg_merge.gif") //$NON-NLS-1$
		);
		entries.add(tool);
		tool = new ConnectionCreationToolEntry(N3Messages.PALETTE_IMPORT, LogicMessages.LogicPlugin_Tool_ConnectionCreationTool_ConnectionCreationTool_Description,
				new SimpleFactory(ImportLineModel.class), ImageDescriptor.createFromFile(UseCaseModel.class, "icons/pkg_import.gif"), //$NON-NLS-1$
				ImageDescriptor.createFromFile(UseCaseModel.class, "icons/pkg_import.gif") //$NON-NLS-1$
		);
		entries.add(tool);
		drawer.addAll(entries);
		return drawer;
	}

	

	

	static private PaletteContainer createControlGroup(PaletteRoot root) {
		PaletteGroup controlGroup = new PaletteGroup(LogicMessages.LogicPlugin_Category_ControlGroup_Label);
		List entries = new ArrayList();
		ToolEntry tool = new PanningSelectionToolEntry();

		entries.add(tool);
		root.setDefaultEntry(tool);
//		PanningSelectionTool
		PaletteStack marqueeStack = new PaletteStack(LogicMessages.Marquee_Stack, "", null); //$NON-NLS-1$
		marqueeStack.add(new MarqueeToolEntry());
		MarqueeToolEntry marquee = new MarqueeToolEntry();
		marquee.setToolProperty(MarqueeSelectionTool.PROPERTY_MARQUEE_BEHAVIOR,
				new Integer(MarqueeSelectionTool.BEHAVIOR_CONNECTIONS_TOUCHED));
		marqueeStack.add(marquee);
		marquee = new MarqueeToolEntry();
		marquee.setToolProperty(MarqueeSelectionTool.PROPERTY_MARQUEE_BEHAVIOR,
				new Integer(MarqueeSelectionTool.BEHAVIOR_CONNECTIONS_TOUCHED | MarqueeSelectionTool.BEHAVIOR_NODES_CONTAINED));
		marqueeStack.add(marquee);
		marqueeStack.setUserModificationPermission(PaletteEntry.PERMISSION_NO_MODIFICATION);
		entries.add(marqueeStack);
		//		tool = new ConnectionCreationToolEntry(
		//				N3Messages.PALETTE_ASSOCIATE,
		//				LogicMessages.LogicPlugin_Tool_ConnectionCreationTool_ConnectionCreationTool_Description,
		//				new SimpleFactory(AssociateLineModel.class),
		//				ImageDescriptor.createFromFile(AssociateLineModel.class, "icons/association.gif"),//$NON-NLS-1$
		//				ImageDescriptor.createFromFile(AssociateLineModel.class, "icons/association.gif")//$NON-NLS-1$
		//		);
		//		entries.add(tool);
		//		tool = new ConnectionCreationToolEntry(
		//				"확장관계",
		//				LogicMessages.LogicPlugin_Tool_ConnectionCreationTool_ConnectionCreationTool_Description,
		//				new SimpleFactory(ExtendLineModel.class),
		//				ImageDescriptor.createFromFile(ExtendLineModel.class, "icons/extends.gif"),//$NON-NLS-1$
		//				ImageDescriptor.createFromFile(ExtendLineModel.class, "icons/extends.gif")//$NON-NLS-1$
		//		);
		//		entries.add(tool);
		//		tool = new ConnectionCreationToolEntry(
		//				"포함관계",
		//				LogicMessages.LogicPlugin_Tool_ConnectionCreationTool_ConnectionCreationTool_Description,
		//				new SimpleFactory(IncludeLineModel.class),
		//				ImageDescriptor.createFromFile(IncludeLineModel.class, "icons/Include16.gif"),//$NON-NLS-1$
		//				ImageDescriptor.createFromFile(IncludeLineModel.class, "icons/Include16.gif")//$NON-NLS-1$
		//		);
		//		entries.add(tool);
		//		
		//		tool = new ConnectionCreationToolEntry(
		//				N3Messages.PALETTE_GENERALIZATION,
		//				LogicMessages.LogicPlugin_Tool_ConnectionCreationTool_ConnectionCreationTool_Description,
		//				new SimpleFactory(GeneralizeLineModel.class),
		//				ImageDescriptor.createFromFile(GeneralizeLineModel.class, "icons/generalization.gif"),//$NON-NLS-1$
		//				ImageDescriptor.createFromFile(GeneralizeLineModel.class, "icons/generalization.gif")//$NON-NLS-1$
		//		);
		//		entries.add(tool);
		//		tool = new ConnectionCreationToolEntry(
		//				N3Messages.PALETTE_REALIZE,
		//				LogicMessages.LogicPlugin_Tool_ConnectionCreationTool_ConnectionCreationTool_Description,
		//				new SimpleFactory(RealizeLineModel.class),
		//				ImageDescriptor.createFromFile(Circuit.class, "icons/connection16.gif"),//$NON-NLS-1$
		//				ImageDescriptor.createFromFile(Circuit.class, "icons/connection24.gif")//$NON-NLS-1$
		//		);
		//		
		//		
		//		entries.add(tool);
		//		
		//		tool = new ConnectionCreationToolEntry(
		//				"노트링크",
		//				LogicMessages.LogicPlugin_Tool_ConnectionCreationTool_ConnectionCreationTool_Description,
		//				new SimpleFactory(NoteLineModel.class),
		//				ImageDescriptor.createFromFile(Circuit.class, "icons/connection16.gif"),//$NON-NLS-1$
		//				ImageDescriptor.createFromFile(Circuit.class, "icons/connection24.gif")//$NON-NLS-1$
		//		);
		//		
		//		
		//		entries.add(tool);
		controlGroup.addAll(entries);
		return controlGroup;
	}

	static PaletteRoot createUseCaseDiagramPalette() {
		PaletteRoot n3Palette = new PaletteRoot();
		n3Palette.addAll(createUseCaseDiagramCategories(n3Palette));
		return n3Palette;
	}
	
	static PaletteRoot createAtomicDiagramPalette() {
		PaletteRoot n3Palette = new PaletteRoot();
		n3Palette.addAll(createAtomicDiagramCategories(n3Palette));
		
		return n3Palette;
	}
	
	static PaletteRoot createTemplateDiagramPalette() {
		PaletteRoot n3Palette = new PaletteRoot();
		n3Palette.setVisible(false);
		n3Palette.setUserModificationPermission(n3Palette.PERMISSION_HIDE_ONLY);
//		n3Palette.setType(newType)
//		root.PERMISSION_HIDE_ONLY
		
		
		return n3Palette;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		singleton = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		singleton = null;
		super.stop(context);
	}

	public static N3Plugin getDefault() {
		return singleton;
	}

	public N3Plugin() {
		if (singleton == null) {
			singleton = this;
		}
	}
}
