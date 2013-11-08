package kr.co.n3soft.n3com.project.dialog;

import java.util.ArrayList;

import kr.co.n3soft.n3com.lang.N3Messages;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.DrillDownAdapter;

public class NewModelDialog extends Dialog {
    private static final int RESET_ID = IDialogConstants.NO_TO_ALL_ID + 1;
    private static final int OK_ID = 0;
    private static final int CANCEL_ID = 1;
    private Text usernameField;
    private Text passwordField;
    //	private TextArea descriptionField;
    private DrillDownAdapter drillDownAdapter;
    private String name = "";
    private int modelType = -1;
    public boolean isOK = false;
    private Text diagramNameField;
    ListViewer selectFrom = null;
    ListViewer diagramTypes = null;
    Group type;
    Group description;
    StringBuffer descClass;
    Label descriptionField = null;
    private TreeViewer viewer;

    public NewModelDialog(Shell parentShell) {
        super(parentShell);
    }

    public void initDesc() {
        descClass = new StringBuffer("클래스 다이어그램에서는\n 시스템화 대상인 업무의");
        //		descClass.append("\\r");
        descClass.append("주요한 정보를 \n클래스로서 정의해 클래스의 특성이나\n 클래스 간의 관련 등을 정적으로 나타낸다.");
    }

    protected Control createDialogArea(Composite parent) {
        Composite comp = (Composite)super.createDialogArea(parent);
        try {
            //			Layout layout = new BorderLayout();
            //comp.setLayout(layout)
            //this.initDesc();
        	comp.getShell().setText(N3Messages.POPUP_MODEL_ADD);//20080717 KDI s			 
			comp.getShell().setImage(ProjectManager.getInstance().getImage(316));//20080724 KDI s
			
			
            GridLayout layout = (GridLayout)comp.getLayout();
            layout.numColumns = 3;
            Label nameLabel = new Label(comp, SWT.RIGHT);
            nameLabel.setText(N3Messages.POPUP_NAME+": ");//2008040302 PKY S 
            diagramNameField = new Text(comp, SWT.SINGLE | SWT.BORDER);
            //			diagramNameField.setForeground(ColorConstants.black);
            GridData data = new GridData(GridData.FILL_HORIZONTAL);
            data.horizontalSpan = 2;
            diagramNameField.setLayoutData(data);
            GridData data1 = new GridData(GridData.FILL_BOTH);
            data1.horizontalSpan = 2;
            type = new Group(comp, SWT.SHADOW_ETCHED_IN);
            type.setText(N3Messages.DIALOG_MODELTYPE);//2008040301 PKY S 
            //			type.setBounds(10, 10, 400, 300);
            type.setLayoutData(data1);
            viewer = new TreeViewer(type, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
            viewer.setContentProvider(new ViewContentProvider());
            viewer.setLabelProvider(new ViewLabelProvider());
            //			viewer.setSorter(new NameSorter());
            viewer.setInput(this.initialize());
            viewer.getTree().pack();
            viewer.getTree().setBounds(10, 30, 300, 300);
            type.pack();
            GridData data2 = new GridData(GridData.FILL_BOTH);
            description = new Group(comp, SWT.SHADOW_ETCHED_IN);
            description.setText(N3Messages.POPUP_DESCRIPTION);//2008040302 PKY S ;
            descriptionField = new Label(description, SWT.LEFT);
            //			descriptionField.setSize(500, 300);
            descriptionField.pack();
            descriptionField.setBounds(10, 30, 200, 300);
            description.setLayoutData(data2);
            description.pack();
            //			descriptionField.setEditable(false);
            viewer.addSelectionChangedListener(
                new ISelectionChangedListener() {
                    public void selectionChanged(SelectionChangedEvent event) {
                        IStructuredSelection sel = (IStructuredSelection)event.getSelection();
                        Object obj = sel.getFirstElement();
                        if (obj instanceof TreeObject) {
                            TreeObject treeObject = (TreeObject)obj;
                            modelType = treeObject.getType();
                            //						descriptionField.setText(descClass.toString());
                        }
                    }
                });
            //			viewer.setInput(this.get);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        //		GridData data2 = new GridData(GridData.FILL_BOTH);
        //		viewer.setLayoutData(data2);
        return comp;
    }

    protected void createButtonsForButtonBar(Composite parent) {
        super.createButtonsForButtonBar(parent);
        //		createButton(parent, RESET_ID, "Reset All", false);
    }

    protected void buttonPressed(int buttonId) {
        if (buttonId == RESET_ID) {
            usernameField.setText("");
            passwordField.setText("");
        }
        else {
            if (buttonId == this.OK_ID) {
                this.name = this.diagramNameField.getText();
                if (this.modelType == -1) {
                    MessageDialog dialog = new MessageDialog(this.getShell(), "경고", null, "다이어그램을 선택하지 않았습니다.", MessageDialog.ERROR,
                        new String[] { "확인" }, 0);
                    dialog.open();
                    return;
                }
                else {
                    this.isOK = true;
                }
            }
            super.buttonPressed(buttonId);
        }
    }

    class TreeObject implements IAdaptable {
        private String name;
        private int type;
        private TreeParent parent;

        public TreeObject(String name) {
            this.name = name;
        }

        public TreeObject(String name, int p) {
            this.name = name;
            this.type = p;
        }

        public String getName() {
            return name;
        }

        public int getType() {
            return type;
        }

        public void setParent(TreeParent parent) {
            this.parent = parent;
        }

        public TreeParent getParent() {
            return parent;
        }

        public String toString() {
            return getName();
        }

        public Object getAdapter(Class key) {
            return null;
        }
    }


    class TreeParent extends TreeObject {
        private ArrayList children;

        public TreeParent(String name) {
            super(name);
            children = new ArrayList();
        }

        public TreeParent(String name, int p) {
            super(name, p);
            children = new ArrayList();
        }

        public void addChild(TreeObject child) {
            children.add(child);
            child.setParent(this);
        }

        public void removeChild(TreeObject child) {
            children.remove(child);
            child.setParent(null);
        }

        public TreeObject[] getChildren() {
            return (TreeObject[]) children.toArray(new TreeObject[children.size()]);
        }

        public boolean hasChildren() {
            return children.size() > 0;
        }
    }


    class ViewContentProvider implements IStructuredContentProvider, ITreeContentProvider {
        //		private TreeParent invisibleRoot;
        public void inputChanged(Viewer v, Object oldInput, Object newInput) {
        }

        public void dispose() {
        }

        public Object[] getElements(Object parent) {
            //			if (invisibleRoot==null){ initialize();
            //			return getChildren(invisibleRoot);
            //			}
            return getChildren(parent);
        }

        public Object getParent(Object child) {
            if (child instanceof TreeObject) {
                return ((TreeObject)child).getParent();
            }
            return null;
        }

        public Object[] getChildren(Object parent) {
            if (parent instanceof TreeParent) {
                return ((TreeParent)parent).getChildren();
            }
            return new Object[0];
        }

        public boolean hasChildren(Object parent) {
            if (parent instanceof TreeParent)
                return ((TreeParent)parent).hasChildren();
            return false;
        }
    }


    //등록
    private TreeParent initialize() {
    	//2008042205PKY S
        TreeParent to1 = new TreeParent(N3Messages.PALETTE_USECASE , 2);//2008040301 PKY S
        TreeParent to2 = new TreeParent(N3Messages.PALETTE_ACTOR, 3);//2008040301 PKY S 
        TreeParent to3 = new TreeParent(N3Messages.PALETTE_COLLABORATION, 5);//2008040301 PKY S 
//        TreeParent to4 = new TreeParent(N3Messages.PALETTE_BOUNDARY, 4); //PKY 08071601 S 바운더리 swimlane 브라우져에 생성되지않아 모델브라우져에서 생성하는 부분 제외
        TreeParent to5 = new TreeParent(N3Messages.PALETTE_PACKAGE, 0);//2008040301 PKY S 
        TreeParent p1 = new TreeParent(N3Messages.PALETTE_TITLE_USECASE, 8);
        p1.addChild(to2);
        p1.addChild(to1);   
        p1.addChild(to3);
//        p1.addChild(to4); //PKY 08071601 S 바운더리 swimlane 브라우져에 생성되지않아 모델브라우져에서 생성하는 부분 제외
        p1.addChild(to5);
        TreeParent to1_1 = new TreeParent(N3Messages.PALETTE_CLASS, 6);//2008040301 PKY S 
        TreeParent to2_1 = new TreeParent(N3Messages.PALETTE_INTERFACE, 7);
        TreeParent to3_1 = new TreeParent(N3Messages.PALETTE_ENUMERATION, 8);
        TreeParent p2 = new TreeParent(N3Messages.PALETTE_TITLE_CLASS, 2);
        TreeParent to4_1 = new TreeParent(N3Messages.PALETTE_PACKAGE, 0);//2008040301 PKY S 
        p2.addChild(to4_1);
        p2.addChild(to1_1);
        p2.addChild(to2_1);
        p2.addChild(to3_1);       
        TreeParent to1_2 = new TreeParent(N3Messages.PALETTE_EXCEPTION , 9);
        TreeParent to2_2 = new TreeParent(N3Messages.PALETTE_ACTIVITY, 10);
        TreeParent to2_3 = new TreeParent(N3Messages.PALETTE_ACTION, 11);
        TreeParent to2_4 = new TreeParent(N3Messages.PALETTE_SEND, 12);
        TreeParent to2_5 = new TreeParent(N3Messages.PALETTE_RECEIVE, 13);
        TreeParent to2_6 = new TreeParent(N3Messages.PALETTE_INITIAL, 14);
        TreeParent to2_7 = new TreeParent(N3Messages.PALETTE_FINAL, 15);
        TreeParent to2_8 = new TreeParent(N3Messages.PALETTE_FLOWFINAL, 16);
        TreeParent to2_9 = new TreeParent(N3Messages.PALETTE_SYNCH, 17);
        TreeParent to2_10 = new TreeParent(N3Messages.PALETTE_DECISION, 18);
        TreeParent to2_11 = new TreeParent(N3Messages.PALETTE_OBJECT, 19);
        TreeParent to2_12 = new TreeParent(N3Messages.PALETTE_CENTRALBUFFERNODE, 20);
        TreeParent to2_13 = new TreeParent(N3Messages.PALETTE_DATASTORE, 21);
//        TreeParent to2_14 = new TreeParent(N3Messages.PALETTE_SWIMLANE, 22); //PKY 08071601 S 바운더리 swimlane 브라우져에 생성되지않아 모델브라우져에서 생성하는 부분 제외
        //		TreeParent to2_15 = new TreeParent("VSwimlaine",23);
        TreeParent to2_16 = new TreeParent(N3Messages.PALETTE_FORK_JOIN, 24);
        TreeParent to2_17 = new TreeParent(N3Messages.PALETTE_FORK_JOIN, 25);
        TreeParent to2_18 = new TreeParent(N3Messages.PALETTE_STCUTUREACTIVITY, 26);
        //		TreeParent to2_20 = new TreeParent("LifeLine",28);
        TreeParent p3 = new TreeParent(N3Messages.PALETTE_TITLE_ACTIVITY, 9);
        p3.addChild(to1_2);
        p3.addChild(to2_2);
        p3.addChild(to2_3);
        p3.addChild(to2_4);
        p3.addChild(to2_5);
        p3.addChild(to2_6);
        p3.addChild(to2_7);
        p3.addChild(to2_8);
        p3.addChild(to2_9);
        p3.addChild(to2_10);
        p3.addChild(to2_11);
        p3.addChild(to2_12);
        p3.addChild(to2_13);
//        p3.addChild(to2_14);
        //		p3.addChild(to2_15);
        p3.addChild(to2_16);
        p3.addChild(to2_17);
        p3.addChild(to2_18);
        TreeParent to300_1 = new TreeParent(N3Messages.PALETTE_PART, 27);
        TreeParent to300_2 = new TreeParent(N3Messages.PALETTE_CLASS, 6);//2008040301 PKY S 
        TreeParent to300_3 = new TreeParent(N3Messages.PALETTE_INTERFACE, 7);
        TreeParent to300_4 = new TreeParent(N3Messages.PALETTE_COLLABORATION, 5);//2008040301 PKY S 
        TreeParent p4 = new TreeParent(N3Messages.PALETTE_TITLE_COMPOSITE, 4);
        p4.addChild(to300_2);
        p4.addChild(to300_3);
        p4.addChild(to300_1);
        p4.addChild(to300_4);
        TreeParent p5 = new TreeParent(N3Messages.PALETTE_TITLE_INTERACTION, 12);
        TreeParent to400_1 = new TreeParent(N3Messages.PALETTE_LIFELINE, 28);
        TreeParent to400_2 = new TreeParent(N3Messages.PALETTE_ACTOR, 50);
        p5.addChild(to400_2);
        p5.addChild(to400_1);
        TreeParent p6 = new TreeParent(N3Messages.PALETTE_TITLE_COMPONENT, 5);
        TreeParent to500_1 = new TreeParent(N3Messages.PALETTE_COMPONENT, 29);
        TreeParent to500_2 = new TreeParent(N3Messages.PALETTE_ARTIFACT, 30);
        TreeParent to500_3 = new TreeParent(N3Messages.PALETTE_PACKAGE, 0);//2008040301 PKY S 
        TreeParent to500_4 = new TreeParent(N3Messages.PALETTE_CLASS, 6);//2008040301 PKY S 
        TreeParent to500_5 = new TreeParent(N3Messages.PALETTE_INTERFACE, 7);
        TreeParent to500_6 = new TreeParent(N3Messages.PALETTE_OBJECT, 19);
        TreeParent to500_7 = new TreeParent(N3Messages.PALETTE_PART, 27);
        p6.addChild(to500_3);
        p6.addChild(to500_1);
        p6.addChild(to500_4);
        p6.addChild(to500_5);
        p6.addChild(to500_6);
        p6.addChild(to500_7);
        p6.addChild(to500_2);
        TreeParent p7 = new TreeParent(N3Messages.PALETTE_TITLE_STATE, 10);
        TreeParent to600_1 = new TreeParent(N3Messages.PALETTE_STATE, 31);
        TreeParent to600_2 = new TreeParent(N3Messages.PALETTE_SUBMACHINESTATE, 32);
        TreeParent to600_3 = new TreeParent(N3Messages.PALETTE_HISTORY, 33);
        TreeParent to600_4 = new TreeParent(N3Messages.PALETTE_TERMINATE, 34);
        TreeParent to600_5 = new TreeParent(N3Messages.PALETTE_ENTRYPOINT, 36);
        TreeParent to600_6 = new TreeParent(N3Messages.PALETTE_INITIAL, 14);
        TreeParent to600_7 = new TreeParent(N3Messages.PALETTE_FINAL, 15);
        TreeParent to600_8 = new TreeParent(N3Messages.PALETTE_SYNCH, 17);
        TreeParent to600_9 = new TreeParent(N3Messages.PALETTE_OBJECT, 19);
        TreeParent to600_10 = new TreeParent(N3Messages.PALETTE_CHOICE, 18);
        TreeParent to600_11 = new TreeParent(N3Messages.PALETTE_EXIT, 16);
        TreeParent to600_12 = new TreeParent(N3Messages.PALETTE_JUNCTION, 14);
        TreeParent to600_13 = new TreeParent(N3Messages.PALETTE_ENTRYPOINT, 36);
        //		TreeParent to500_2 = new TreeParent("Artfifact",30);
        p7.addChild(to600_1);
        p7.addChild(to600_2);
        p7.addChild(to600_6);
        p7.addChild(to600_7);
        p7.addChild(to600_3);
        p7.addChild(to600_8);
        p7.addChild(to600_4);
        p7.addChild(to600_9);
        p7.addChild(to600_10);
        p7.addChild(to600_12);
        p7.addChild(to600_13);
        p7.addChild(to600_11);
//        p7.addChild(to600_5);
        //		p6.addChild(to500_2);
        
        TreeParent p8 = new TreeParent(N3Messages.PALETTE_TITLE_DEPLOYMENT, 6);
        TreeParent to700_1 = new TreeParent(N3Messages.PALETTE_NODE, 35);
        TreeParent to700_2 = new TreeParent(N3Messages.PALETTE_DEVICE, 37);
        TreeParent to700_3 = new TreeParent(N3Messages.PALETTE_EXECUTIONENVIRONMENT, 38);
        TreeParent to700_4 = new TreeParent(N3Messages.PALETTE_DEPLOYMENT, 39);
        TreeParent to700_5 = new TreeParent(N3Messages.PALETTE_ARTIFACT, 30);
        p8.addChild(to700_1);
        p8.addChild(to700_2);
        p8.addChild(to700_3);
        p8.addChild(to700_5);
        p8.addChild(to700_4);
        TreeParent p9 = new TreeParent(N3Messages.PALETTE_TITLE_TIMING, 13);
        TreeParent to800_1 = new TreeParent(N3Messages.PALETTE_STATELIFELINE, 40);
        p9.addChild(to800_1);
        
        TreeParent root = new TreeParent("Model");
        root.addChild(p1);
        root.addChild(p2);
        root.addChild(p3);
        root.addChild(p4);
        root.addChild(p5);
        root.addChild(p6);
        root.addChild(p7);
        root.addChild(p8);
        root.addChild(p9);
        return root;
      //2008042205PKY E
    }

    class ViewLabelProvider extends LabelProvider {
        public String getText(Object obj) {
            return obj.toString();
        }

        public Image getImage(Object obj) {
//          String imageKey = ISharedImages.IMG_OBJ_ELEMENT;
//          //			if (obj instanceof TreeParent){
//          //				return ImageDescriptor.createFromFile(AssociateLineModel.class, "icons/HomeFolder16.gif").createImage();	
//          //			}
//          //			else{
//          //				imageKey = ISharedImages.IMG_OBJ_FOLDER;
          String imageKey = ISharedImages.IMG_OBJ_ELEMENT;
          if(obj instanceof TreeParent){
          	TreeParent tp = (TreeParent)obj;
          	if(tp.children.size()>0){
          		return ProjectManager.getInstance().getDiagramImage(tp.getType());
          	}else{
          		return ProjectManager.getInstance().getImage(tp.getType());
          	}
          	
          }

          return PlatformUI.getWorkbench().getSharedImages().getImage(imageKey);
      }
    }


    class NameSorter extends ViewerSorter {
    }


    public String getName() {
        return this.name;
    }

    public int getModelType() {
        return this.modelType;
    }
}
