package kr.co.n3soft.n3com.rcp.actions;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import kr.co.ed.opros.ce.OPRoSActivator;
import kr.co.n3soft.n3com.edit.ComponentEditPart;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.component.AtomicComponentModel;
import kr.co.n3soft.n3com.model.component.OPRoSDataTypeElementModel;
import kr.co.n3soft.n3com.model.component.OPRoSServiceTypeElementModel;
import kr.co.n3soft.n3com.model.diagram.N3EditorDiagramModel;
import kr.co.n3soft.n3com.model.diagram.UMLDiagramModel;
import kr.co.n3soft.n3com.project.browser.UMLTreeModel;
import kr.co.n3soft.n3com.project.dialog.OPRoSServiceTypeInputDialog;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorPart;
import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;



public class OpenComponentEditorAction extends SelectionAction {
	private static OpenComponentEditorAction instance;
	N3EditorDiagramModel n3EditorDiagramModel;
	N3EditorDiagramModel n3EditorPackageDiagramModel;//2008040401PKY S "��Ű�� ������ Ŭ�� �� OpenDiagram �޴� �߰�  ���� ���̾�׷���������� ����Ʈ ��������"
	private UMLDiagramModel parent;
	UMLTreeModel treeModel = null;
	  private UMLModel child;
	  AtomicComponentModel am;
	  
	  public static OpenComponentEditorAction getInstance() {
			if (instance == null) {
				instance = new OpenComponentEditorAction();



				return instance;
			}
			else {
				return instance;
			}
		}
	  
	  public OpenComponentEditorAction(IEditorPart editor) {
        super(editor);
        this.setId("OpenComponentEditorAction");
        this.setText("Open ComponentEditor");
		this.setImageDescriptor(ImageDescriptor.createFromImage(ProjectManager.getInstance().getImage(353)));//PKY 08070101 S �˾� �޴� �̹��� ����

       
    }
    public OpenComponentEditorAction(){
   	 super(null);
   }

    protected boolean calculateEnabled() {
        // TODO Workaround for Bug 82622/39369.  Should be removed when 39369 is fixed.
    	List list = ProjectManager.getInstance().getSelectNodes();
        if (list != null && list.size() == 1) {
            Object obj = list.get(0);
            if(obj instanceof ComponentEditPart){
            	ComponentEditPart ce = (ComponentEditPart)obj;
            	Object obj2 = ce.getModel();
            	if(obj2 instanceof AtomicComponentModel){
            		am = (AtomicComponentModel)obj2;
            		return true;
            	}
            }
            
        }
        return false;
        
          
    }

    //20110707������>> ������/������������ ���� �� �ʿ� �߰� �� �����ͷ� ����
    public void run() {
    	if(false){
    		test();
    	}
    	else{
    		OpenCmpEdt(am);
    	}
    }
    
    //20110805������>>�и�
    public void OpenCmpEdt(AtomicComponentModel am1){

        HashMap map = new HashMap();
        
        AtomicComponentModel coream = am1.getCoreDiagramCmpModel();
        Document profileDoc = coream.componentProFile();	//������Ʈ ��������
        
        List listServiceDoc = new ArrayList();
        List listDataDoc = new ArrayList();
        
        listServiceDoc = coream.getServiceTypeDoc();
        listDataDoc = coream.getDataTypeDoc();
        
//        Iterator<OPRoSDataTypeElementModel> itDataType = listDataDoc.iterator();
//        OPRoSDataTypeElementModel deTempModel;
//        List listDtFileName = new ArrayList();
//        
//        while(itDataType.hasNext()){
//        	listDtFileName.add(itDataType.next().getDataTypeFileName());
//        }
        
//        Document temp = am1.getServiceTypeDoc("aaa.xml");
//        Document temp1 = am1.getDataTypeDoc("zzz.xml");
//        Document temp = dlg.getServiceTypeDoc();
        
        
//        am1.getCoreDiagramCmpModel();
        
//        am1.writeProFile();
        
        Document doc = coream.wholeProFile(); //��ü ��������
        map.put("profile", doc);
        
        map.put(coream.getName()+".xml", profileDoc);	//������Ʈ ��������
        
        // ������Ÿ�� ����Ÿ��
        Iterator<OPRoSDataTypeElementModel> itDataType = listDataDoc.iterator();
        OPRoSDataTypeElementModel deTempModel;
        
        while(itDataType.hasNext()){
        	deTempModel = itDataType.next();
        	map.put(deTempModel.getDataTypeFileName(), deTempModel.getDataTypeDoc());
        }
        
     // ����Ÿ�� ����Ÿ��
        Iterator<OPRoSServiceTypeElementModel> itServiceType = listServiceDoc.iterator();
        OPRoSServiceTypeElementModel seTempModel;
        
        while(itServiceType.hasNext()){
        	seTempModel = itServiceType.next();
        	map.put(seTempModel.getServiceTypeFileName(), seTempModel.getServiceTypeDoc());
        }
        
        
        
        
//        map.put("zzz.xml", temp1);
//        map.put("aaa.xml", temp);
//      this.ssss();
        
//		Preferences pref = OPRoSActivator.getDefault().getPluginPreferences();
//		String strBoostLibPath = pref.getString(PreferenceConstants.BOOST_LIB_PATH);
        String id[] = OPRoSActivator.getDefault().newProjectForComposer(map);
        System.out.println("�迭��"+id.length);
		System.out.println("---------------------"+id[0] + "////" + id[1]);
//      am.setId(p)
	
		
		//20110721������ - ���ϰ��/���̵� ����
		File path = new File(id[1]);
		
		AtomicComponentModel nam = new AtomicComponentModel();
		nam = coream;
		
		System.out.println("���̵� : " + nam.getId() + "���� : " + nam.getFile());
		
		nam.setId(id[0]);
      	nam.setFile(path);
      	
      	System.out.println("���̵� : " + nam.getId() + "���� : " + nam.getFile());

      	
      	coream.setCoreDiagramCmpModel(nam);
      	
 //    	am1.getCoreDiagramCmpModel();
      	
      	System.out.println("���̵� : " + coream.getId() + "���� : " + coream.getFile());
    
    }
    
    public void test(){
		SAXBuilder builder = new SAXBuilder();
		Document doc = null;
		try {
			doc = builder.build("c:/test/profile/profile.xml");
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("profile", doc);
		File[] files = new File("c:/test/profile/").listFiles();
		for (File file : files) {
			if (!file.getName().equals("profile.xml")) {
				try {
					map.put(file.getName(), builder.build(file));
				} catch (JDOMException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		String id[] = OPRoSActivator.getDefault().newProjectForComposer(map);
		System.out.println("---------------------"+id[0] + "////" + id[1]);
		
    }
    
//    public static String newProjectForComposer(HashMap<String, Object> map){
//    	String uuid = "test.uuid";
//    	
//    	// �۽���Ƽ�� ����
//    	IPerspectiveRegistry reg = (IPerspectiveRegistry)UIPlugin.getDefault().getWorkbench().getPerspectiveRegistry();
//    	IPerspectiveDescriptor desc = (IPerspectiveDescriptor)reg.findPerspectiveWithId(OPRoSPerspectiveFactory.ID);//
//    	PlatformUI.getWorkbench().getWorkbenchWindows()[0].getActivePage().setPerspective(desc);
//    	
//    	return uuid;
//    }
    //2008040401PKY S "��Ű�� ������ Ŭ�� �� OpenDiagram �޴� �߰�  ���� ���̾�׷���������� ����Ʈ ��������"
	
    //2008040401PKY E "��Ű�� ������ Ŭ�� �� OpenDiagram �޴� �߰�  ���� ���̾�׷���������� ����Ʈ ��������"
}
