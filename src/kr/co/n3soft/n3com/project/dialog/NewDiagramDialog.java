package kr.co.n3soft.n3com.project.dialog;

import java.util.ArrayList;

import kr.co.n3soft.n3com.lang.N3Messages;
import kr.co.n3soft.n3com.model.usecase.UseCaseModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
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

public class NewDiagramDialog extends Dialog {
    private static final int RESET_ID = IDialogConstants.NO_TO_ALL_ID + 1;
    private static final int OK_ID = 0;
    private static final int CANCEL_ID = 1;
    private Text usernameField;
    private Text passwordField;
    //	private TextArea descriptionField;
    private DrillDownAdapter drillDownAdapter;
    private String diagramName = "";
    private int diagramType = -1;
    public boolean isOK = false;
    private Text diagramNameField;
    ListViewer selectFrom = null;
    ListViewer diagramTypes = null;
    Group type;
    Group description;
    StringBuffer descClass;
    Label descriptionField = null;
    private TreeViewer viewer;

    public NewDiagramDialog(Shell parentShell) {
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
        	comp.getShell().setText(N3Messages.POPUP_DIAGRAME_ADD);//20080717 KDI s			 
			comp.getShell().setImage(ProjectManager.getInstance().getImage(317));//20080724 KDI s
			
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
            type.setText(N3Messages.DIALOG_DIAGRAMTYPE);//2008040301 PKY S 
            //			type.setBounds(10, 10, 400, 300);
            type.setLayoutData(data1);
            viewer = new TreeViewer(type, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
            viewer.setContentProvider(new ViewContentProvider());
            viewer.setLabelProvider(new ViewLabelProvider());
            //			viewer.setSorter(new NameSorter());
            viewer.setInput(this.initialize());
            viewer.getTree().pack();
            viewer.getTree().setBounds(10, 30, 250, 400);
            type.pack();
            GridData data2 = new GridData(GridData.FILL_BOTH);
            description = new Group(comp, SWT.SHADOW_ETCHED_IN);
            description.setText(N3Messages.POPUP_DESCRIPTION);//2008040302 PKY S ;
            descriptionField = new Label(description, SWT.LEFT);
            //			descriptionField.setSize(500, 300);
            descriptionField.pack();
            descriptionField.setBounds(10, 25, 500, 400);
            descriptionField.setImage(this.getDiagramDescImage("usecasedesc.bmp"));
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
                            diagramType = treeObject.getType();
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
    
    public Image getDiagramDescImage(String p){
    	return ImageDescriptor.createFromFile(UseCaseModel.class, "icons/"+p).createImage();
    	
    	
    }

    protected void buttonPressed(int buttonId) {
        if (buttonId == RESET_ID) {
            usernameField.setText("");
            passwordField.setText("");
        }
        else {
            if (buttonId == this.OK_ID) {
                this.diagramName = this.diagramNameField.getText();
//              V1.02 WJH E 080813 S 다이어그램 이름이 없을때 다이어그램 생성되지 않도록 수정
                if(diagramName == null || diagramName.equals("")){
            		MessageDialog dialog = new MessageDialog(this.getShell(), "경고", null, "다이어그램 이름을 쓰지 않았습니다.", MessageDialog.ERROR,
                            new String[] { "확인" }, 0);
            		dialog.open();
            		return;
            	}
//              V1.02 WJH E 080813 E 다이어그램 이름이 없을때 다이어그램 생성되지 않도록 수정
                if (this.diagramType == -1) {
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


    private TreeParent initialize() {
        TreeParent to1 = new TreeParent(N3Messages.DIALOG_PACKAGE, 1);
        TreeParent to2 = new TreeParent(N3Messages.PALETTE_TITLE_CLASS, 2);//2008040301 PKY S 
        TreeParent to3 = new TreeParent(N3Messages.DIALOG_OBJECT, 3);
        TreeParent to4 = new TreeParent(N3Messages.PALETTE_TITLE_COMPOSITE, 4);//2008040301 PKY S
        TreeParent to5 = new TreeParent(N3Messages.PALETTE_TITLE_COMPONENT, 5);//2008040301 PKY S
        TreeParent to6 = new TreeParent(N3Messages.PALETTE_TITLE_DEPLOYMENT, 6);//2008040301 PKY S
        TreeParent p1 = new TreeParent(N3Messages.DIALOG_STRUCTURAL, 7);
        p1.addChild(to1);
        p1.addChild(to2);
        p1.addChild(to3);
        p1.addChild(to4);
        p1.addChild(to5);
        p1.addChild(to6);
        TreeParent to1_1 = new TreeParent(N3Messages.PALETTE_TITLE_USECASE, 8);//2008040301 PKY S
        TreeParent to2_1 = new TreeParent(N3Messages.PALETTE_TITLE_ACTIVITY, 9);//2008040301 PKY S
        TreeParent to3_1 = new TreeParent(N3Messages.PALETTE_TITLE_STATE, 10);//2008040301 PKY S
        TreeParent to4_1 = new TreeParent(N3Messages.PALETTE_TITLE_COMMUNICATION, 11);//2008040301 PKY S
        TreeParent to5_1 = new TreeParent(N3Messages.PALETTE_TITLE_INTERACTION, 12);//2008040301 PKY S
        TreeParent to6_1 = new TreeParent(N3Messages.PALETTE_TITLE_TIMING, 13);//2008040301 PKY S
        TreeParent to7_1 = new TreeParent(N3Messages.DIALOG_INTERACTION_OVERVIEW, 14);
        TreeParent p2 = new TreeParent(N3Messages.DIALOG_BEHAVIORAL, 15);
        p2.addChild(to1_1);
        p2.addChild(to2_1);
        p2.addChild(to3_1);
        p2.addChild(to4_1);
        p2.addChild(to5_1);
        p2.addChild(to6_1);
        p2.addChild(to7_1);
        //20080721IJS
        TreeParent p3 = new TreeParent(N3Messages.DIALOG_EXTENDED, 16);
        //20080721IJS
        TreeParent to1_2 = new TreeParent(N3Messages.PALETTE_REQUIRMENT, 17);
        //20080721IJS
        p3.addChild(to1_2);
        
        TreeParent root = new TreeParent("Model");
        root.addChild(p1);
        root.addChild(p2);
        root.addChild(p3);
        return root;
    }

    class ViewLabelProvider extends LabelProvider {
        public String getText(Object obj) {
            return obj.toString();
        }

        public Image getImage(Object obj) {
            String imageKey = ISharedImages.IMG_OBJ_ELEMENT;
            if(obj instanceof TreeParent){
            	TreeParent tp = (TreeParent)obj;
            	return ProjectManager.getInstance().getDiagramImage(tp.getType());
            }

            return PlatformUI.getWorkbench().getSharedImages().getImage(imageKey);
        }
    }


    class NameSorter extends ViewerSorter {
    }


    public String getDiagramName() {
        return this.diagramName;
    }

    public int getDiagramTYpe() {
        return this.diagramType;
    }
    
    
}
