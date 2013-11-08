package kr.co.n3soft.n3com;


import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

import kr.co.n3soft.n3com.dnd.N3TemplateTransferDropTargetListener;
import kr.co.n3soft.n3com.dnd.OutLineTemplateTransferDropTargetListener;
import kr.co.n3soft.n3com.edit.UMLGraphicalPartFactory;
import kr.co.n3soft.n3com.edit.UMLTreePartFactory;
import kr.co.n3soft.n3com.lang.N3Messages;
import kr.co.n3soft.n3com.model.comm.UMLModelRuler;
import kr.co.n3soft.n3com.model.communication.MessageCommunicationModel;
import kr.co.n3soft.n3com.model.diagram.N3EditorDiagramModel;
import kr.co.n3soft.n3com.model.diagram.UMLDiagramModel;
import kr.co.n3soft.n3com.parser.LinkModel;
import kr.co.n3soft.n3com.parser.TeamProjectManager;
import kr.co.n3soft.n3com.projectmanager.N3SelectionManager;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;
import kr.co.n3soft.n3com.rcp.N3ContextMenuProvider;
import kr.co.n3soft.n3com.rcp.actions.ADDActionPinAction;
import kr.co.n3soft.n3com.rcp.actions.ADDActivityParameterAction;
import kr.co.n3soft.n3com.rcp.actions.ADDAttribute;
import kr.co.n3soft.n3com.rcp.actions.ADDBackMessageAction;
import kr.co.n3soft.n3com.rcp.actions.ADDContainerAction;
import kr.co.n3soft.n3com.rcp.actions.ADDDataInputPortAction;
import kr.co.n3soft.n3com.rcp.actions.ADDDataOutputPortAction;
import kr.co.n3soft.n3com.rcp.actions.ADDEntryPointAction;
import kr.co.n3soft.n3com.rcp.actions.ADDEventInputPortAction;
import kr.co.n3soft.n3com.rcp.actions.ADDEventOutputPortAction;
import kr.co.n3soft.n3com.rcp.actions.ADDExitPointAction;
import kr.co.n3soft.n3com.rcp.actions.ADDExpansionNodeAction;
import kr.co.n3soft.n3com.rcp.actions.ADDMessageAction;
import kr.co.n3soft.n3com.rcp.actions.ADDMethodInputPortAction;
import kr.co.n3soft.n3com.rcp.actions.ADDMethodOutputPorAction;
import kr.co.n3soft.n3com.rcp.actions.ADDObjectPortAction;
import kr.co.n3soft.n3com.rcp.actions.ADDPartitionAction;
import kr.co.n3soft.n3com.rcp.actions.ADDPortAction;
import kr.co.n3soft.n3com.rcp.actions.ADDSEQMessageAction;
import kr.co.n3soft.n3com.rcp.actions.ADDStateRegionAction;
import kr.co.n3soft.n3com.rcp.actions.ADDTargetMessageAction;
import kr.co.n3soft.n3com.rcp.actions.ADDoperation;
import kr.co.n3soft.n3com.rcp.actions.AddRoleNameAction;
import kr.co.n3soft.n3com.rcp.actions.AlignAction;
import kr.co.n3soft.n3com.rcp.actions.ChanageAggregateAction;
import kr.co.n3soft.n3com.rcp.actions.ChanageCompositeAction;
import kr.co.n3soft.n3com.rcp.actions.ChanageNavigableAction;
import kr.co.n3soft.n3com.rcp.actions.ChangeDeepHistoryAction;
import kr.co.n3soft.n3com.rcp.actions.ChangeInterfaceModeAction;
import kr.co.n3soft.n3com.rcp.actions.DeleteNodeAction;
import kr.co.n3soft.n3com.rcp.actions.DeleteNumberBarAction;
import kr.co.n3soft.n3com.rcp.actions.DirectLine;
import kr.co.n3soft.n3com.rcp.actions.DockingConfigureTimelineAction;
import kr.co.n3soft.n3com.rcp.actions.ModelGroupMerge;
import kr.co.n3soft.n3com.rcp.actions.ModelGroupMergeCencel;
import kr.co.n3soft.n3com.rcp.actions.ModelMoveDown;
import kr.co.n3soft.n3com.rcp.actions.ModelMoveLeft;
import kr.co.n3soft.n3com.rcp.actions.ModelMoveRight;
import kr.co.n3soft.n3com.rcp.actions.ModelMoveUp;
import kr.co.n3soft.n3com.rcp.actions.MonitoringRunAction;
import kr.co.n3soft.n3com.rcp.actions.MonitoringStopAction;
import kr.co.n3soft.n3com.rcp.actions.MultiCopyAction;
import kr.co.n3soft.n3com.rcp.actions.N3DeleteAction;
import kr.co.n3soft.n3com.rcp.actions.N3PasteTemplateAction;
import kr.co.n3soft.n3com.rcp.actions.NewOperationAction;
import kr.co.n3soft.n3com.rcp.actions.OpenComponentEditorAction;
import kr.co.n3soft.n3com.rcp.actions.OpenDiagramAction;
import kr.co.n3soft.n3com.rcp.actions.OpenMonitoringAction;
import kr.co.n3soft.n3com.rcp.actions.OpenPeopertyAction;
import kr.co.n3soft.n3com.rcp.actions.OpenSeqMessageDialogAction;
import kr.co.n3soft.n3com.rcp.actions.OutputComponentIdTest;
import kr.co.n3soft.n3com.rcp.actions.PerspectiveChangeAction;
import kr.co.n3soft.n3com.rcp.actions.SaveImageAction;
import kr.co.n3soft.n3com.rcp.actions.SendFileAction;
import kr.co.n3soft.n3com.rcp.actions.TracingRequirementsAction;
import kr.co.n3soft.n3com.rcp.actions.ZOrderBackAction;
import kr.co.n3soft.n3com.rcp.actions.ZOrderForwardAction;
import kr.co.n3soft.n3com.ruler.UMLModelRulerProvider;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.Viewport;
import org.eclipse.draw2d.parts.ScrollableThumbnail;
import org.eclipse.draw2d.parts.Thumbnail;
import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.KeyHandler;
import org.eclipse.gef.KeyStroke;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.MouseWheelHandler;
import org.eclipse.gef.MouseWheelZoomHandler;
import org.eclipse.gef.RootEditPart;
import org.eclipse.gef.SnapToGeometry;
import org.eclipse.gef.SnapToGrid;
import org.eclipse.gef.dnd.TemplateTransferDragSourceListener;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.examples.logicdesigner.LogicPlugin;
import org.eclipse.gef.examples.logicdesigner.actions.IncrementDecrementAction;
import org.eclipse.gef.examples.logicdesigner.dnd.TextTransferDropTargetListener;
import org.eclipse.gef.examples.logicdesigner.palette.LogicPaletteCustomizer;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.rulers.RulerProvider;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.CopyTemplateAction;
import org.eclipse.gef.ui.actions.DirectEditAction;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.gef.ui.actions.MatchHeightAction;
import org.eclipse.gef.ui.actions.MatchWidthAction;
import org.eclipse.gef.ui.actions.PrintAction;
import org.eclipse.gef.ui.actions.SaveAction;
import org.eclipse.gef.ui.actions.SelectAllAction;
import org.eclipse.gef.ui.actions.ToggleGridAction;
import org.eclipse.gef.ui.actions.ToggleRulerVisibilityAction;
import org.eclipse.gef.ui.actions.ToggleSnapToGeometryAction;
import org.eclipse.gef.ui.actions.ZoomInAction;
import org.eclipse.gef.ui.actions.ZoomOutAction;
import org.eclipse.gef.ui.palette.PaletteViewer;
import org.eclipse.gef.ui.palette.PaletteViewerProvider;
import org.eclipse.gef.ui.parts.ContentOutlinePage;
import org.eclipse.gef.ui.parts.GraphicalEditorWithFlyoutPalette;
import org.eclipse.gef.ui.parts.GraphicalViewerKeyHandler;
import org.eclipse.gef.ui.parts.ScrollingGraphicalViewer;
import org.eclipse.gef.ui.parts.TreeViewer;
import org.eclipse.gef.ui.rulers.RulerComposite;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.jface.util.TransferDropTargetListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.part.IPageSite;
import org.eclipse.ui.part.PageBook;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

public class UMLEditor extends GraphicalEditorWithFlyoutPalette {
	public static final String ID = "kr.co.n3soft.n3com.rcp.N3Editor";

	private java.util.ArrayList seqNumList = new java.util.ArrayList();
	private java.util.ArrayList groups = new java.util.ArrayList();
	private N3EditorDiagramModel appDiagram = null;
	private boolean isSave = false;
	//reNum

	public N3EditorDiagramModel getAppDiagram() {
		return appDiagram;
	}

	public void setAppDiagram(N3EditorDiagramModel appDiagram) {
		this.appDiagram = appDiagram;
	}

	public void reNumberSequence(){
		//		for(int i=0;i<groups.size();i++){
		//			SeqGroup sq = (SeqGroup)groups.get(i);
		//		}

	}

	public void addSeqNumber(MessageCommunicationModel m){
		seqNumList.add(m);

	}

	public void removeSeqNumber(MessageCommunicationModel m){
		this.seqNumList.remove(m);

	}
	//PKY 08070701 S 커뮤니케이션 메시지 추가하다보면 텍스트사이즈가 늘어나지않아서 안보이는문제 수정
	public int getSeqNumberSize(){
		if(seqNumList!=null)
			return seqNumList.size();
		return 0;
	}
	//PKY 08070701 E 커뮤니케이션 메시지 추가하다보면 텍스트사이즈가 늘어나지않아서 안보이는문제 수정
	public String getNum(){
		int i = seqNumList.size();
		if(i==0){
			return "1.";
		}
		else{
			return "1."+i;
		}
	}

	public void reNumber(){

		for(int i=0;i<this.seqNumList.size();i++){
			MessageCommunicationModel m = (MessageCommunicationModel)seqNumList.get(i);
			if(i==0){
				m.setNumber("1");
			}
			else{
				m.setNumber("1."+i+"");
			}
			m.setName(m.getName());
		}
	}
	class OutlinePage extends ContentOutlinePage implements IAdaptable {
		private PageBook pageBook;
		private Control outline;
		private Canvas overview;
		private IAction showOutlineAction, showOverviewAction;
		static final int ID_OUTLINE = 0;
		static final int ID_OVERVIEW = 1;
		private Thumbnail thumbnail;
		private DisposeListener disposeListener;





		public OutlinePage(EditPartViewer viewer) {
			super(viewer);
		}

		public void init(IPageSite pageSite) {
			super.init(pageSite);
			ActionRegistry registry = getActionRegistry();
			IActionBars bars = pageSite.getActionBars();
//			String id = ActionFactory.UNDO.getId();
//			bars.setGlobalActionHandler(id, registry.getAction(id));
//			id = ActionFactory.REDO.getId();
//			bars.setGlobalActionHandler(id, registry.getAction(id));
			String id  = ActionFactory.DELETE.getId();
			bars.setGlobalActionHandler(id, registry.getAction(id));
			id = IncrementDecrementAction.INCREMENT;
			bars.setGlobalActionHandler(id, registry.getAction(id));
			id = IncrementDecrementAction.DECREMENT;
			bars.setGlobalActionHandler(id, registry.getAction(id));
			bars.updateActionBars();
		}

		protected void configureOutlineViewer() {
			getViewer().setEditDomain(getEditDomain());
			getViewer().setEditPartFactory(new UMLTreePartFactory());
			ContextMenuProvider provider = new N3ContextMenuProvider(getViewer(), getActionRegistry());
			getViewer().setContextMenu(provider);
			getSite().registerContextMenu("org.eclipse.gef.examples.logic.outline.contextmenu", //$NON-NLS-1$
					provider, getSite().getSelectionProvider());
			getViewer().setKeyHandler(getCommonKeyHandler());
			//20080729IJS
			OutLineTemplateTransferDropTargetListener outLineTemplateTransferDropTargetListener = new OutLineTemplateTransferDropTargetListener(getViewer());
			//20080729IJS
			getViewer().addDropTargetListener((TransferDropTargetListener)outLineTemplateTransferDropTargetListener);

			IToolBarManager tbm = getSite().getActionBars().getToolBarManager();
			showOutlineAction = new Action() {
				public void run() {
					showPage(ID_OUTLINE);
				}
			};
			showOutlineAction.setImageDescriptor(ImageDescriptor.createFromFile(LogicPlugin.class, "icons/outline.gif")); //$NON-NLS-1$
			tbm.add(showOutlineAction);
			showOverviewAction = new Action() {
				public void run() {
					showPage(ID_OVERVIEW);
				}
			};
			showOverviewAction.setImageDescriptor(ImageDescriptor.createFromFile(LogicPlugin.class, "icons/overview.gif")); //$NON-NLS-1$
			tbm.add(showOverviewAction);
			showPage(ID_OUTLINE);
		}

		public void createControl(Composite parent) {
			pageBook = new PageBook(parent, SWT.NONE);
			outline = getViewer().createControl(pageBook);
			overview = new Canvas(pageBook, SWT.NONE);
			pageBook.showPage(outline);
			configureOutlineViewer();
			hookOutlineViewer();
			initializeOutlineViewer();
		}

		public void dispose() {
			unhookOutlineViewer();
			if (thumbnail != null) {
				thumbnail.deactivate();
				thumbnail = null;
			}

			super.dispose();
			UMLEditor.this.outlinePage = null;
			outlinePage = null;
		}

		public Object getAdapter(Class type) {
			if (type == ZoomManager.class)
				return getGraphicalViewer().getProperty(ZoomManager.class.toString());
			return null;
		}

		public Control getControl() {
			return pageBook;
		}

		protected void hookOutlineViewer() {
			getSelectionSynchronizer().addViewer(getViewer());
		}

		protected void initializeOutlineViewer() {
			setContents(getUMLDiagramModel());
		}

		protected void initializeOverview() {
			LightweightSystem lws = new LightweightSystem(overview);
			RootEditPart rep = getGraphicalViewer().getRootEditPart();
			if (rep instanceof ScalableFreeformRootEditPart) {
				ScalableFreeformRootEditPart root = (ScalableFreeformRootEditPart)rep;
				thumbnail = new ScrollableThumbnail((Viewport)root.getFigure());
				thumbnail.setBorder(new MarginBorder(3));
				thumbnail.setSource(root.getLayer(LayerConstants.PRINTABLE_LAYERS));
				lws.setContents(thumbnail);
				disposeListener = new DisposeListener() {
					public void widgetDisposed(DisposeEvent e) {
						if (thumbnail != null) {
							thumbnail.deactivate();
							thumbnail = null;
						}
					}
				};
				getEditor().addDisposeListener(disposeListener);
			}
		}

		public void setContents(Object contents) {
			getViewer().setContents(contents);
		}

		protected void showPage(int id) {
			if (id == ID_OUTLINE) {
				showOutlineAction.setChecked(true);
				showOverviewAction.setChecked(false);
				pageBook.showPage(outline);
				if (thumbnail != null)
					thumbnail.setVisible(false);
			} else if (id == ID_OVERVIEW) {
				if (thumbnail == null)
					initializeOverview();
				showOutlineAction.setChecked(false);
				showOverviewAction.setChecked(true);
				pageBook.showPage(overview);
				thumbnail.setVisible(true);
			}
		}

		protected void unhookOutlineViewer() {
			getSelectionSynchronizer().removeViewer(getViewer());
			if (disposeListener != null && getEditor() != null && !getEditor().isDisposed())
				getEditor().removeDisposeListener(disposeListener);
		}
	}


	private KeyHandler sharedKeyHandler;
	private PaletteRoot root;
	private OutlinePage outlinePage;
	private boolean editorSaving = false;


	private IPartListener partListener = new IPartListener() {
		// If an open, unsaved file was deleted, query the user to either do a "Save As"
		// or close the editor.
		public void partActivated(IWorkbenchPart part) {
			if (part != UMLEditor.this)
				return;
			//	if (!((IFileEditorInput)getEditorInput()).getFile().exists()) {
			//		Shell shell = getSite().getShell();
			//		String title = LogicMessages.GraphicalEditor_FILE_DELETED_TITLE_UI;
			//		String message = LogicMessages.GraphicalEditor_FILE_DELETED_WITHOUT_SAVE_INFO;
			//		String[] buttons = { 	LogicMessages.GraphicalEditor_SAVE_BUTTON_UI,
			//					   			LogicMessages.GraphicalEditor_CLOSE_BUTTON_UI };
			//		MessageDialog dialog = new MessageDialog(
			//				shell, title, null, message, MessageDialog.QUESTION, buttons, 0);			
			//		if (dialog.open() == 0) {
			//			if (!performSaveAs())
			//				partActivated(part);
			//		}
			//		else {
			//			closeEditor(false);
			//		}
			//	}
		}
		public void partBroughtToTop(IWorkbenchPart part) { }
		public void partClosed(IWorkbenchPart part) {
		}
		public void partDeactivated(IWorkbenchPart part) { }
		public void partOpened(IWorkbenchPart part) {
			System.out.println("ddd");
		}
	};

	private N3EditorDiagramModel logicDiagram = new N3EditorDiagramModel();
	//private ResourceTracker resourceListener = new ResourceTracker();
	private RulerComposite rulerComp;
	protected static final String PALETTE_DOCK_LOCATION = "Dock location"; //$NON-NLS-1$
	protected static final String PALETTE_SIZE = "Palette Size"; //$NON-NLS-1$
	protected static final String PALETTE_STATE = "Palette state"; //$NON-NLS-1$
	protected static final int DEFAULT_PALETTE_SIZE = 130;

	//static {
	//	LogicPlugin.getDefault().getPreferenceStore().setDefault(
	//			PALETTE_SIZE, DEFAULT_PALETTE_SIZE);
	//}
	public UMLEditor() {
		//	this.logicDiagram = ProjectManager.getInstance().getDiagram();
		try {
			//setEditDomain(new UMLDefaultEditDomain(this));
			//ProjectManager.getInstance().setUMLEditor(this);
			if (ProjectManager.getInstance().getOpenDiagramModel() != null) {
				this.logicDiagram = (N3EditorDiagramModel)ProjectManager.getInstance().getOpenDiagramModel();
			}
			if (this.logicDiagram != null) {
				this.logicDiagram.setUMLEditor(this);
			}
			setEditDomain(new UMLDefaultEditDomain(this));
			ProjectManager.getInstance().setUMLEditor(this);
			//	this.setTitle("유즈케이스다이어그램");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setPaletteRoot(int diagramType){
		//		this.getPaletteRoot().setDefaultEntry(entry)
	}

	public N3EditorDiagramModel getDiagram() {
		return this.logicDiagram;
	}

	public UMLDefaultEditDomain getUMLDefaultEditDomain() {
		UMLDefaultEditDomain uMLDefaultEditDomain = (UMLDefaultEditDomain)this.getEditDomain();
		return uMLDefaultEditDomain;
	}

	protected void closeEditor(boolean save) {
		getSite().getPage().closeEditor(UMLEditor.this, false);

	}

	public void commandStackChanged(EventObject event) {
		firePropertyChange(IEditorPart.PROP_DIRTY);
		super.commandStackChanged(event);
	}

	public ScrollingGraphicalViewer getScrollingGraphicalViewer() {
		ScrollingGraphicalViewer viewer = (ScrollingGraphicalViewer)getGraphicalViewer();
		return viewer;
	}

	protected void configureGraphicalViewer() {
		super.configureGraphicalViewer();
		ScrollingGraphicalViewer viewer = (ScrollingGraphicalViewer)getGraphicalViewer();
		viewer.setSelectionManager(new N3SelectionManager());

		//PKY 09040209 S
		viewer.getControl().addKeyListener(new KeyListener(){

			public void keyPressed(KeyEvent e) {
				//				System.out.println("in");
				//				System.out.println("e.stateMask="+e.stateMask + " " +"e.keyCode="+e.keyCode + " "+ "e.characte="+ e.character);

				if(e.stateMask==262144& e.keyCode==16777219){
					//					System.out.println("왼쪽");

					ModelMoveLeft modelUp = new ModelMoveLeft(ProjectManager.getInstance().getUMLEditor());
					modelUp.run();
				}else if(e.stateMask==262144& e.keyCode==16777220){
					//					System.out.println("오른쪽");
					ModelMoveRight modelUp = new ModelMoveRight(ProjectManager.getInstance().getUMLEditor());
					modelUp.run();
				}else if(e.stateMask==262144& e.keyCode==16777217){
					//					System.out.println("위쪽");
					ModelMoveUp modelUp = new ModelMoveUp(ProjectManager.getInstance().getUMLEditor());
					modelUp.run();
				}else if(e.stateMask==262144& e.keyCode==16777218){
					ModelMoveDown modelUp = new ModelMoveDown(ProjectManager.getInstance().getUMLEditor());
					modelUp.run();

				}

				//				ProjectManager.getInstance().setMultiClick(false);
				//				if(e.stateMask==0x0&& e.keyCode==0x40000 && e.character==0x0){
				//					if(ProjectManager.getInstance()!=null)
				//						ProjectManager.getInstance().setMultiClick(true);
				//				}else if(e.stateMask==262144&& e.keyCode==122 && e.character==''){
				//					System.out.print("");
				//				}



			}

			public void keyReleased(KeyEvent e) {
				//				ProjectManager.getInstance().setMultiClick(false);
				//				System.out.println("out");
				//				System.out.println("e.stateMask="+e.stateMask + " " +"e.keyCode="+e.keyCode + " "+ "e.characte="+ e.character);
				//				if(e.stateMask==0&& e.keyCode==262144 && e.character=='\0'){
				//					System.out.print("");					
				//				}

			}

		});
		//PKY 09040209 E


		ScalableFreeformRootEditPart root = new ScalableFreeformRootEditPart();
		List zoomLevels = new ArrayList(3);
		zoomLevels.add(ZoomManager.FIT_ALL);
		zoomLevels.add(ZoomManager.FIT_WIDTH);
		zoomLevels.add(ZoomManager.FIT_HEIGHT);
		root.getZoomManager().setZoomLevelContributions(zoomLevels);
		IAction zoomIn = new ZoomInAction(root.getZoomManager());
		IAction zoomOut = new ZoomOutAction(root.getZoomManager());
		getActionRegistry().registerAction(zoomIn);
		getActionRegistry().registerAction(zoomOut);
		getSite().getKeyBindingService().registerAction(zoomIn);
		getSite().getKeyBindingService().registerAction(zoomOut);
		viewer.setRootEditPart(root);
		viewer.setEditPartFactory(new UMLGraphicalPartFactory());
		ContextMenuProvider provider = new N3ContextMenuProvider(viewer, getActionRegistry());
		viewer.setContextMenu(provider);
		getSite().registerContextMenu("org.eclipse.gef.examples.logic.editor.contextmenu", //$NON-NLS-1$
				provider, viewer);
		viewer.setKeyHandler(new GraphicalViewerKeyHandler(viewer).setParent(getCommonKeyHandler()));
		loadProperties();
		// Actions
		IAction showRulers = new ToggleRulerVisibilityAction(getGraphicalViewer());
		getActionRegistry().registerAction(showRulers);
		IAction snapAction = new ToggleSnapToGeometryAction(getGraphicalViewer());
		getActionRegistry().registerAction(snapAction);
		IAction showGrid = new ToggleGridAction(getGraphicalViewer());
		getActionRegistry().registerAction(showGrid);
		Listener listener = new Listener() {
			public void handleEvent(Event event) {
				handleActivationChanged(event);
			}
		};
		getGraphicalControl().addListener(SWT.Activate, listener);
		getGraphicalControl().addListener(SWT.Deactivate, listener);
	}

	protected void writeToOutputStream(OutputStream os) throws IOException {
		ObjectOutputStream out = new ObjectOutputStream(os);
		out.writeObject(getUMLDiagramModel());
		out.close();
	}

	protected CustomPalettePage createPalettePage() {
		return new CustomPalettePage(getPaletteViewerProvider()) {
			public void init(IPageSite pageSite) {
				super.init(pageSite);
				IAction copy = getActionRegistry().getAction(ActionFactory.COPY.getId());
				pageSite.getActionBars().setGlobalActionHandler(ActionFactory.COPY.getId(), copy);
			}
		};
	}

	protected PaletteViewerProvider createPaletteViewerProvider() {
		return new PaletteViewerProvider(getEditDomain()) {
			private IMenuListener menuListener;
			protected void configurePaletteViewer(PaletteViewer viewer) {
				super.configurePaletteViewer(viewer);
				viewer.setCustomizer(new LogicPaletteCustomizer());
				viewer.addDragSourceListener(new TemplateTransferDragSourceListener(viewer));
			}
			protected void hookPaletteViewer(PaletteViewer viewer) {
				super.hookPaletteViewer(viewer);
				final CopyTemplateAction copy = (CopyTemplateAction)getActionRegistry().getAction(ActionFactory.COPY.getId());
				viewer.addSelectionChangedListener(copy);
				if (menuListener == null)
					menuListener = new IMenuListener() {
					public void menuAboutToShow(IMenuManager manager) {
						manager.appendToGroup(GEFActionConstants.GROUP_COPY, copy);
					}
				};
				viewer.getContextMenu().addMenuListener(menuListener);
			}
		};
	}

	public void dispose() {
		System.out.println("");

		this.getDiagram().setOpen(false);
		//	getSite().getWorkbenchWindow().getPartService().removePartListener(partListener);
		//	partListener = null;
		//	((IFileEditorInput)getEditorInput()).getFile().getWorkspace()
		//			.removeResourceChangeListener(resourceListener);
		super.dispose();
	}

	public void doSave(final IProgressMonitor progressMonitor) {
		editorSaving = true;
		Platform.run(
				new SafeRunnable() {
					public void run() throws Exception {
						try{
							//			ProgressMonitorDialog pd = new ProgressMonitorDialog(workbenchWindow.getShell());
							//			pd.run(true, true, (IRunnableWithProgress)ProjectManager.getInstance().getModelBrowser());
							//							if(ProjectManager.getInstance().getCurrentProjectName()==null || ProjectManager.getInstance().getCurrentProjectName().trim().equals("")){

							FileDialog fsd = new FileDialog(ProjectManager.getInstance().window.getShell(),SWT.SAVE);
							fsd.setFilterExtensions(new String[] {"*.nmdl","*.*"});
							fsd.setText("Save Select Project Files...");
							String prjName = ProjectManager.getInstance().getCurrentProjectName();
							if("".equals(prjName)){


								fsd.setFileName(ProjectManager.getInstance().getCurrentProjectName());
								String ir = fsd.open();
								if(ir==null)
									return;
								//				LoadProgressRun lr =	new LoadProgressRun(true);

								//								V1.02 WJH E 080822 S 세이브 할때 이미 있는 파일을 선택하면 저장 여부를 묻도록 수정
								String fullPath = fsd.getFilterPath()+"\\"+fsd.getFileName();
								File tempFile = new File(fullPath);
								if(!(fsd.getFileName().trim().equals("")||fsd.getFilterPath().trim().equals("")))//PKY 08090908 S
									if(tempFile.exists()){
										MessageBox dialog = new MessageBox(ProjectManager.getInstance().window.getShell(),SWT.ICON_QUESTION|SWT.YES|SWT.NO);
										dialog.setText("Message");
										dialog.setMessage(N3Messages.DIALOG_SAVE_MESSAGE);
										int i=dialog.open();
										switch(i) {
										case IDialogConstants.FINISH_ID:
											System.out.println("Scrip Wizard Finish!!");
											break;
										case SWT.NO:
											return;
										case SWT.CANCEL:
											return;
										}
									}
								//								V1.02 WJH E 080822 E 세이브 할때 이미 있는 파일을 선택하면 저장 여부를 묻도록 수정

								//PKY 08062601 S 저장할때 경로지정이 제대로 안될경우 에러발생 처리
								if(!fsd.getFileName().trim().equals("")){
									String fileName = fsd.getFileName();
									String path = fsd.getFilterPath();

									ProjectManager.getInstance().setCurrentProjectName(fileName);
									ProjectManager.getInstance().setCurrentProjectPath(path+File.separator);
								}
								//PKY 08062601 E 저장할때 경로지정이 제대로 안될경우 에러발생 처리
								//							}
							}
							
							//PKY 08072401 S 콘솔 창 닫은상태에서 저장 불러오기 안되는 문제 수정
							//							if(!ProjectManager.getInstance().getCurrentProjectName().trim().equals("")){
							ProjectManager.getInstance().getModelBrowser().writeModel(ProjectManager.getInstance().getCurrentProject());				
							String title = ProjectManager.getInstance().getCurrentProjectName().replaceAll(".nmdl", "");
							if(ProjectManager.getInstance().getConsole()!=null){

//								ProjectManager.getInstance().getModelBrowser().writeModel(ProjectManager.getInstance().getCurrentProject());				
//								String title = ProjectManager.getInstance().getCurrentProjectName().replaceAll(".nmdl", "");
								//								ProjectManager.getInstance().getConfigurer().setTitle(title+" - N3COM Constructor V3.0");
								ProjectManager.getInstance().addOpenProject(ProjectManager.getInstance().getCurrentProject());
								ProjectManager.getInstance().getConsole().addMessage("Save complete "+ProjectManager.getInstance().getCurrentProject());
								ProjectManager.getInstance().getConsole().addMessage("Save "+ProjectManager.getInstance().getCurrentProject());
								//								CompAssemManager.getInstance().saveLib(null);
							}
							//							}
							//PKY 08072401 E 콘솔 창 닫은상태에서 저장 불러오기 안되는 문제 수정
							//20080811IJS
							java.util.ArrayList list = TeamProjectManager.getInstance().getLinks();
							TeamProjectManager.getInstance().setLinkSave(true);
							for(int i=0;i<list.size();i++){
								LinkModel lm = (LinkModel)list.get(i);
								int k = lm.getUt().getIsLinkType();
								if(k==1){
									ProjectManager.getInstance().getModelBrowser().writeLinkModel(lm.getLinkPath(),lm.getUt());
								}
							}
							TeamProjectManager.getInstance().setLinkSave(false);

						}
						catch(Exception e){
							e.printStackTrace();
						}
						isSave = true;

						getCommandStack().markSaveLocation();
//						progressMonitor .isChange
						if(ProjectManager.getInstance()!=null && ProjectManager.getInstance().getModelBrowser()!=null){
							ProjectManager.getInstance().getModelBrowser().setChange(false);
						}
						
					}

				});
		//		this.getEditor().setEnabled(false);
		editorSaving = false;

	}

	public void doSaveAs() {
		try{
			//			ProgressMonitorDialog pd = new ProgressMonitorDialog(workbenchWindow.getShell());
			//			pd.run(true, true, (IRunnableWithProgress)ProjectManager.getInstance().getModelBrowser());

			FileDialog fsd = new FileDialog(ProjectManager.getInstance().window.getShell(),SWT.SAVE);
			fsd.setFilterExtensions(new String[] {"*.nmdl","*.*"});
			fsd.setText("Save Select Project Files...");
			fsd.setFileName(ProjectManager.getInstance().getCurrentProjectName());
			String ir = fsd.open();
			if(ir==null)
				return;
			//			LoadProgressRun lr =	new LoadProgressRun(true);
			String fileName = fsd.getFileName();
			String path = fsd.getFilterPath();

			//			V1.02 WJH E 080822 S 세이브 할때 이미 있는 파일을 선택하면 저장 여부를 묻도록 수정
			String fullPath = fsd.getFilterPath()+File.separator+fsd.getFileName();
			File tempFile = new File(fullPath);
			if(tempFile.exists()){
				MessageBox dialog = new MessageBox(ProjectManager.getInstance().window.getShell(),SWT.ICON_QUESTION|SWT.YES|SWT.NO);
				dialog.setText("Message");
				dialog.setMessage(N3Messages.DIALOG_SAVE_MESSAGE);
				int i=dialog.open();
				switch(i) {
				case IDialogConstants.FINISH_ID:
					System.out.println("Scrip Wizard Finish!!");
					break;
				case SWT.NO:
					return;
				case SWT.CANCEL:
					return;
				}
			}
			//			V1.02 WJH E 080822 E 세이브 할때 이미 있는 파일을 선택하면 저장 여부를 묻도록 수정

			//PKY 08062601 S 저장할때 경로지정이 제대로 안될경우 에러발생 처리
			//PKY 08072401 S 콘솔 창 닫은상태에서 저장 불러오기 안되는 문제 수정
			if(!fileName.trim().equals("")){
				ProjectManager.getInstance().setCurrentProjectName(fileName);
				ProjectManager.getInstance().setCurrentProjectPath(path);				
				ProjectManager.getInstance().getModelBrowser().writeModel(ProjectManager.getInstance().getCurrentProject());
				String title = ProjectManager.getInstance().getCurrentProjectName().replaceAll(".nmdl", "");
				ProjectManager.getInstance().getConfigurer().setTitle(title+" - N3COM Constructor V3.0");
				ProjectManager.getInstance().addOpenProject(ProjectManager.getInstance().getCurrentProject());
				ProjectManager.getInstance().getConsole().addMessage("Save As"+ProjectManager.getInstance().getCurrentProject());
				ProjectManager.getInstance().getConsole().addMessage("Save As complete "+ProjectManager.getInstance().getCurrentProject());
			}
			//PKY 08072401 E 콘솔 창 닫은상태에서 저장 불러오기 안되는 문제 수정
			//20080811IJS
			java.util.ArrayList list = TeamProjectManager.getInstance().getLinks();
			TeamProjectManager.getInstance().setLinkSave(true);
			for(int i=0;i<list.size();i++){
				LinkModel lm = (LinkModel)list.get(i);
				int k = lm.getUt().getIsLinkType();
				if(k==1){
					ProjectManager.getInstance().getModelBrowser().writeLinkModel(lm.getLinkPath(),lm.getUt());
				}
			}
			TeamProjectManager.getInstance().setLinkSave(false);

		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	// WJH 100326 S 종료 시 저장로직 추가
	public void doSaveAs(Shell shell) {
		try{
			//			ProgressMonitorDialog pd = new ProgressMonitorDialog(workbenchWindow.getShell());
			//			pd.run(true, true, (IRunnableWithProgress)ProjectManager.getInstance().getModelBrowser());
			String prjName =  ProjectManager.getInstance().getCurrentProjectName();
			if("".equals(prjName)){
			FileDialog fsd = new FileDialog(shell,SWT.SAVE);
			fsd.setFilterExtensions(new String[] {"*.nmdl","*.*"});
			fsd.setText("Save Select Project Files...");
			//			fsd.setFileName(string)
			fsd.setFileName(ProjectManager.getInstance().getCurrentProjectName());
			String ir = fsd.open();
			if(ir==null)
				return;
			//			LoadProgressRun lr =	new LoadProgressRun(true);
			String fileName = fsd.getFileName();
			String path = fsd.getFilterPath();

			//			V1.02 WJH E 080822 S 세이브 할때 이미 있는 파일을 선택하면 저장 여부를 묻도록 수정
			String fullPath = fsd.getFilterPath()+File.separator+fsd.getFileName();
			File tempFile = new File(fullPath);
			if(fileName.equals(""))
				return;
			if(tempFile.exists()){
				MessageBox dialog = new MessageBox(shell,SWT.ICON_QUESTION|SWT.YES|SWT.NO);
				dialog.setText("Message");
				dialog.setMessage(N3Messages.DIALOG_SAVE_MESSAGE);
				int i=dialog.open();
				switch(i) {
				case IDialogConstants.FINISH_ID:
					System.out.println("Scrip Wizard Finish!!");
					break;
				case SWT.NO:
					return;
				case SWT.CANCEL:
					break;
				}
			}
			ProjectManager.getInstance().setCurrentProjectName(fileName);
			ProjectManager.getInstance().setCurrentProjectPath(path);				
			}
			//			V1.02 WJH E 080822 E 세이브 할때 이미 있는 파일을 선택하면 저장 여부를 묻도록 수정

			//PKY 08062601 S 저장할때 경로지정이 제대로 안될경우 에러발생 처리
			//PKY 08072401 S 콘솔 창 닫은상태에서 저장 불러오기 안되는 문제 수정
//			if(!fileName.trim().equals("")){
				
				ProjectManager.getInstance().getModelBrowser().writeModel2(ProjectManager.getInstance().getCurrentProject());
				//				String title = ProjectManager.getInstance().getCurrentProjectName().replaceAll(".nmdl", "");

//			}
			//PKY 08072401 E 콘솔 창 닫은상태에서 저장 불러오기 안되는 문제 수정
			//20080811IJS
			java.util.ArrayList list = TeamProjectManager.getInstance().getLinks();
			TeamProjectManager.getInstance().setLinkSave(true);
			for(int i=0;i<list.size();i++){
				LinkModel lm = (LinkModel)list.get(i);
				int k = lm.getUt().getIsLinkType();
				if(k==1){
					ProjectManager.getInstance().getModelBrowser().writeLinkModel(lm.getLinkPath(),lm.getUt());
				}
			}
			TeamProjectManager.getInstance().setLinkSave(false);

		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	// WJH 100326 E 종료 시 저장로직 추가

	public Object getAdapter(Class type) {
		if (type == IContentOutlinePage.class) {
			//20080729IJS 
			outlinePage = new OutlinePage(new TreeViewer());
			return outlinePage;
		}
		if (type == ZoomManager.class)
			return getGraphicalViewer().getProperty(ZoomManager.class.toString());
		return super.getAdapter(type);
	}

	protected Control getGraphicalControl() {
		return rulerComp;
	}

	/**
	 * Returns the KeyHandler with common bindings for both the Outline and Graphical Views.
	 * For example, delete is a common action.
	 */
	protected KeyHandler getCommonKeyHandler() {
		if (sharedKeyHandler == null) {
			sharedKeyHandler = new KeyHandler();
			sharedKeyHandler.put(KeyStroke.getPressed(SWT.F2, 0),
					getActionRegistry().getAction(GEFActionConstants.DIRECT_EDIT));
		}
		return sharedKeyHandler;
	}

	protected UMLDiagramModel getUMLDiagramModel() {
		//	return ProjectManager.getInstance().getDiagram();
		//	ProjectManager.getInstace();
		return logicDiagram;
	}

	protected PaletteRoot getPaletteRoot() {
		if (root == null) {
			N3EditorDiagramModel n3m = this.getDiagram();
			if(n3m.getDtype()==2)
				root = N3Plugin.createAtomicDiagramPalette();
			else if(n3m.getDtype()==-1)
				root = N3Plugin.createTemplateDiagramPalette();
			else
			root = N3Plugin.createUseCaseDiagramPalette();
		}
		return root;
	}

	//public void gotoMarker(IMarker marker) {}
	protected void handleActivationChanged(Event event) {
		IAction copy = null;
		if (event.type == SWT.Deactivate)
			copy = getActionRegistry().getAction(ActionFactory.COPY.getId());
		if (getEditorSite().getActionBars().getGlobalActionHandler(ActionFactory.COPY.getId()) != copy) {
			getEditorSite().getActionBars().setGlobalActionHandler(ActionFactory.COPY.getId(), copy);
			getEditorSite().getActionBars().updateActionBars();
		}
	}

	public GraphicalViewer getGraphicalViewerp() {
		return this.getGraphicalViewer();
	}

	protected void initializeGraphicalViewer() {
		super.initializeGraphicalViewer();
		getGraphicalViewer().setContents(getUMLDiagramModel());
		getGraphicalViewer().addDropTargetListener((TransferDropTargetListener)new
				N3TemplateTransferDropTargetListener(getGraphicalViewer()));
		getGraphicalViewer().addDropTargetListener((TransferDropTargetListener)new
				TextTransferDropTargetListener(getGraphicalViewer(), TextTransfer.getInstance()));
	}

	protected void createSuperActions() {
		ActionRegistry registry = getActionRegistry();
		IAction action;

//		action = new UndoAction(this);
//		registry.registerAction(action);
//		getStackActions().add(action.getId());
//
//		action = new RedoAction(this);
//		registry.registerAction(action);
//		getStackActions().add(action.getId());

		action = new SelectAllAction(this);
		registry.registerAction(action);

		action = new N3DeleteAction((IWorkbenchPart)this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		action = new SaveAction(this);
		registry.registerAction(action);
		getPropertyActions().add(action.getId());

		registry.registerAction(new PrintAction(this));
	}

	//액션
	protected void createActions() {
		createSuperActions();
		ActionRegistry registry = getActionRegistry();
		IAction action;

		//20080721IJS 시작
		action = new TracingRequirementsAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());
		//20080721IJS 끝		
		
		//20110816SDM 시작-perspective전환
		action = new PerspectiveChangeAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());
		//20110816SDM 끝

		action = new ZOrderForwardAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		action = new ZOrderBackAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		//khg 10.4.28 노드 삭제
		action = new DeleteNodeAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		action = new NewOperationAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		action = new ADDTargetMessageAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		action = new AddRoleNameAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		action = new ChanageAggregateAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		action = new ChanageCompositeAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		action = new ChanageNavigableAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		action = new ADDSEQMessageAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		action = new ADDStateRegionAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		action = new ADDActionPinAction(this);
		registry.registerAction(action);

		action = new ADDoperation(this);
		registry.registerAction(action);

		action = new ADDAttribute(this);
		registry.registerAction(action);

		//PKY 08051401 S 라인 꺽인것 바로 직선으로 만들기
		action = new DirectLine(this);
		registry.registerAction(action);
		//PKY 08051401 S 라인 꺽인것 바로 직선으로 만들기

		//PKY 08052101 S 컨테이너에서 그룹으로 변경
		action = new ModelGroupMerge(this);
		registry.registerAction(action);

		action = new ModelGroupMergeCencel(this);
		registry.registerAction(action);
		//PKY 08052101 S 컨테이너에서 그룹으로 변경

		action = new ADDMessageAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		//PKY 08070301 S Communication Dialog 추가작업
		action = new ADDBackMessageAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());
		//PKY 08070301 E Communication Dialog 추가작업

		action = new DockingConfigureTimelineAction(this);
		registry.registerAction(action);

		action = new DeleteNumberBarAction(this);
		registry.registerAction(action);



		action = new OpenSeqMessageDialogAction(this);
		registry.registerAction(action);
		action = new OpenDiagramAction(this);
		registry.registerAction(action);

		action = new ChangeDeepHistoryAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		action = new ADDObjectPortAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());


		action = new ADDExitPointAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());
		action = new ADDEntryPointAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());
		//	action = new CopyTemplateAction(this);
		//	registry.registerAction(action);
		action = new ADDPartitionAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());
		action = new ChangeInterfaceModeAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());
		action = new ADDContainerAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());
		action = new ADDExpansionNodeAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());
		action = new ADDActivityParameterAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());
		action = new ADDPortAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		
		action = new ADDMethodOutputPorAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());
		action = new ADDMethodInputPortAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());
		

		action = new ADDDataOutputPortAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());
		action = new ADDDataInputPortAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		
		action = new ADDEventOutputPortAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());
		action = new ADDEventInputPortAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());




		action = new MultiCopyAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());
		action = new MatchWidthAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());
		action = new MatchHeightAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());
		action = new N3PasteTemplateAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());
		action = new IncrementDecrementAction(this, true);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());
		action = new IncrementDecrementAction(this, false);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());
		action = new DirectEditAction((IWorkbenchPart)this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());
		//		V1.02 WJH E 080826 S 클래스를 자바로 생성 AlignmentAction->AlignAction
		action = new AlignAction((IWorkbenchPart)this, PositionConstants.LEFT);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());
		action = new AlignAction((IWorkbenchPart)this, PositionConstants.RIGHT);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());
		action = new AlignAction((IWorkbenchPart)this, PositionConstants.TOP);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());
		action = new AlignAction((IWorkbenchPart)this, PositionConstants.BOTTOM);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());
		action = new AlignAction((IWorkbenchPart)this, PositionConstants.CENTER);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());
		action = new AlignAction((IWorkbenchPart)this, PositionConstants.MIDDLE);
		//		V1.02 WJH E 080826 S 클래스를 자바로 생성
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		//20080718 KDI s 다이어그램 팝업 이미지 저장 구현
		action = new SaveImageAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());
		//20080718 KDI e 다이어그램 팝업 이미지 저장 구현

		action = new SendFileAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());
		
		action = new OpenPeopertyAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());
		
		action = new MonitoringRunAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());
		
		action = new MonitoringStopAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());
		
		action = new OpenMonitoringAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());
		
		action = new OpenComponentEditorAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());
		
		//20110708서동민 >>id체크를 위한 임시 메뉴
		action = new OutputComponentIdTest(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());


	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.ui.parts.GraphicalEditor#createGraphicalViewer(org.eclipse.swt.widgets.Composite)
	 */

	protected void createGraphicalViewer(Composite parent) {
		rulerComp = new RulerComposite(parent, SWT.NONE);
		super.createGraphicalViewer(rulerComp);
		rulerComp.setGraphicalViewer((ScrollingGraphicalViewer)getGraphicalViewer());
	}

	protected FigureCanvas getEditor() {

		return (FigureCanvas)getGraphicalViewer().getControl();
	}

	public boolean isSaveAsAllowed() {
		return false;
	}

	public boolean isSaveOnCloseNeeded() {
		return false;
	}

	protected void loadProperties() {
		// Ruler properties
		this.getDiagram().setOpen(true);
		UMLModelRuler ruler = getUMLDiagramModel().getRuler(PositionConstants.WEST);
		RulerProvider provider = null;
		if (ruler != null) {
			provider = new UMLModelRulerProvider(ruler);
		}
		getGraphicalViewer().setProperty(RulerProvider.PROPERTY_VERTICAL_RULER, provider);
		ruler = getUMLDiagramModel().getRuler(PositionConstants.NORTH);
		provider = null;
		if (ruler != null) {
			provider = new UMLModelRulerProvider(ruler);
		}
		getGraphicalViewer().setProperty(RulerProvider.PROPERTY_HORIZONTAL_RULER, provider);
		getGraphicalViewer().setProperty(RulerProvider.PROPERTY_RULER_VISIBILITY,
				new Boolean(getUMLDiagramModel().getRulerVisibility()));
		// Snap to Geometry property
		getGraphicalViewer().setProperty(SnapToGeometry.PROPERTY_SNAP_ENABLED,
				new Boolean(getUMLDiagramModel().isSnapToGeometryEnabled()));
		// Grid properties
		getGraphicalViewer().setProperty(SnapToGrid.PROPERTY_GRID_ENABLED, new Boolean(getUMLDiagramModel().isGridEnabled()));
		// We keep grid visibility and enablement in sync
		getGraphicalViewer().setProperty(SnapToGrid.PROPERTY_GRID_VISIBLE, new Boolean(getUMLDiagramModel().isGridEnabled()));
		// Zoom
		ZoomManager manager = (ZoomManager)getGraphicalViewer().getProperty(ZoomManager.class.toString());
		if (manager != null)
			manager.setZoom(getUMLDiagramModel().getZoom());
		// Scroll-wheel Zoom
		getGraphicalViewer().setProperty(MouseWheelHandler.KeyGenerator.getKey(SWT.MOD1), MouseWheelZoomHandler.SINGLETON);
	}

	//protected boolean performSaveAs() {
	//	SaveAsDialog dialog = new SaveAsDialog(getSite().getWorkbenchWindow().getShell());
	//	dialog.setOriginalFile(((IFileEditorInput)getEditorInput()).getFile());
	//	dialog.open();
	//	IPath path= dialog.getResult();
	//	
	//	if (path == null)
	//		return false;
	//	
	//	IWorkspace workspace = ResourcesPlugin.getWorkspace();
	//	final IFile file= workspace.getRoot().getFile(path);
	//	
	//	if (!file.exists()) {
	//		WorkspaceModifyOperation op= new WorkspaceModifyOperation() {
	//			public void execute(final IProgressMonitor monitor) {
	//				saveProperties();
	//				try {
	//					ByteArrayOutputStream out = new ByteArrayOutputStream();
	//					writeToOutputStream(out);
	//					file.create(new ByteArrayInputStream(out.toByteArray()), true, monitor);
	//					out.close();
	//				}
	//				catch (Exception e) {
	//					e.printStackTrace();
	//				}
	//			}
	//		};
	//		try {
	//			new ProgressMonitorDialog(getSite().getWorkbenchWindow().getShell())
	//					.run(false, true, op);			
	//		}
	//		catch (Exception e) {
	//			e.printStackTrace();
	//		}
	//	}
	//	
	//	try {
	//		superSetInput(new FileEditorInput(file));
	//		getCommandStack().markSaveLocation();
	//	}
	//	catch (Exception e) {
	//		e.printStackTrace();
	//	}
	//	return true;
	//}
	protected void saveProperties() {
		getUMLDiagramModel().setRulerVisibility(((Boolean)getGraphicalViewer()
				.getProperty(RulerProvider.PROPERTY_RULER_VISIBILITY)).booleanValue());
		getUMLDiagramModel().setGridEnabled(((Boolean)getGraphicalViewer()
				.getProperty(SnapToGrid.PROPERTY_GRID_ENABLED)).booleanValue());
		getUMLDiagramModel().setSnapToGeometry(((Boolean)getGraphicalViewer()
				.getProperty(SnapToGeometry.PROPERTY_SNAP_ENABLED)).booleanValue());
		ZoomManager manager = (ZoomManager)getGraphicalViewer().getProperty(ZoomManager.class.toString());
		if (manager != null)
			getUMLDiagramModel().setZoom(manager.getZoom());
	}

	protected void setInput(IEditorInput input) {
		superSetInput(input);
		//	IFile file = ((IFileEditorInput)input).getFile();
		//	try {
		//		InputStream is = file.getContents(false);
		//		ObjectInputStream ois = new ObjectInputStream(is);
		//		setLogicDiagram((LogicDiagram)ois.readObject());
		//		ois.close();
		//	}
		//	catch (Exception e) {
		//		//This is just an example.  All exceptions caught here.
		//		e.printStackTrace();
		//	}
		//	
		//	if (!editorSaving) {
		//		if (getGraphicalViewer() != null) {
		//			getGraphicalViewer().setContents(getLogicDiagram());
		//			loadProperties();
		//		}
		//		if (outlinePage != null) {
		//			outlinePage.setContents(getLogicDiagram());
		//		}
		//	}
	}

	public void setUMLDiagramModel(N3EditorDiagramModel diagram) {
		logicDiagram = diagram;
	}

	protected void superSetInput(IEditorInput input) {
		// The workspace never changes for an editor.  So, removing and re-adding the
		// resourceListener is not necessary.  But it is being done here for the sake
		// of proper implementation.  Plus, the resourceListener needs to be added
		// to the workspace the first time around.
		//	if(getEditorInput() != null) {
		//		IFile file = ((IFileEditorInput)getEditorInput()).getFile();
		//		file.getWorkspace().removeResourceChangeListener(resourceListener);
		//	}
		super.setInput(input);
		//	if(getEditorInput() != null) {
		//		IFile file = ((IFileEditorInput)getEditorInput()).getFile();
		//		file.getWorkspace().addResourceChangeListener(resourceListener);
		//		setPartName(file.getName());
		//	}
	}

	protected void setSite(IWorkbenchPartSite site) {
		super.setSite(site);
		//	getSite().getWorkbenchWindow().getPartService().addPartListener(partListener);
	}

	public void setTitleName(String titleName) {
		//  V1.02 WJH E 080818 S 다이어그램 타이틀 아이콘 추가
		if(this.getDiagram().getDiagramType() > -1) //KDI080905 0001
			this.setTitleImage(ProjectManager.getInstance().getDiagramImage(this.getDiagram().getDiagramType()));  
		//  V1.02 WJH E 080818 E 다이어그램 타이틀 아이콘 추가
		this.setTitle(titleName);
	}

	public void setFocus() {
		try {
			ProjectManager.getInstance().setUMLEditor(this);
			getGraphicalViewer().getControl().setFocus();
			this.getEditorSite();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	//public void selectionChanged(IWorkbenchPart part, ISelection selection) {
	//	// If not the active editor, ignore selection changed.
	//	if (this.equals(getSite().getPage().getActiveEditor()))
	//		updateActions(selectionActions);
	//}

	public java.util.ArrayList getGroups() {
		return groups;
	}

	public void setGroups(java.util.ArrayList groups) {
		this.groups = groups;
	}
}
