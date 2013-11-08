package kr.co.n3soft.n3com.model.node;

import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import kr.co.n3soft.n3com.project.dialog.MonitoringThread;

public class NodeItemModel extends UMLModel{
	static final long serialVersionUID = 1;
	Image LOGIC_ICON	= ProjectManager.getInstance().getImage(ProjectManager.getInstance().getElementType(this, -1));
	java.util.ArrayList tempCmp = new java.util.ArrayList();
	String appName = "";
	MonitoringThread mt = null;
	public MonitoringThread getMt() {
		return mt;
	}

	public void setMt(MonitoringThread mt) {
		this.mt = mt;
	}

	Thread thd = null;
	public boolean isConnectm = false;
	public boolean initData = false;

public Thread getThd() {
		return thd;
	}

	public void setThd(Thread thd) {
		this.thd = thd;
	}

public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	//	MonitoringThread mt = new MonitoringThread();
    Thread t = null;
	public java.util.ArrayList getTempCmp() {
		return tempCmp;
	}

	public void setTempCmp(java.util.ArrayList tempCmp) {
		this.tempCmp = tempCmp;
	}

	public void addTempCmp(Object omd) {
		if(!tempCmp.contains(omd))
			this.tempCmp.add(omd);
	}

	public void delTempCmp(Object omd) {
		if(tempCmp.contains(omd))
			this.tempCmp.remove(omd);
	}

	public Image getIconImage() {
		return LOGIC_ICON;
	}

	public void setBackGroundColor(Color um) {
		this.backGroundColor = um;
		this.uMLDataModel.setProperty(ID_COLOR,um.getRed()+","+um.getGreen()+","+um.getBlue());
		//		firePropertyChange(ID_COLOR, null, backGroundColor); //$NON-NLS-1$
	}

	public Color getBackGroundColor() {

		return this.backGroundColor;
	}
	
	
//	public MonitoringThread getRunable(){
//		if(mt==null){
//			 mt = new MonitoringThread();
//			mt.setNitem(this);
//			mt.appName = this.appName;
//			return mt; 
//		}
//		else{
//			return mt; 
//		}
//	}
//	
//	
//	
//	public void start(){
//		if(t==null){
//			MonitoringThread mt = new MonitoringThread();
//			mt.setNitem(this);
//			mt.appName = this.appName;
////			mt.setApp(ut)
//			t = new Thread(mt);
//			t.start();
//		}
//	}
//	
//	public void stop(){
//		if(t==null){
//			
//			t.stop();
//		}
//	}

//	class MonitoringThread implements Runnable {
//		public void run() {
//			//			for (index = 0; index <= 100; index++) {
//			//				if(!initData){
//
//
//			//				}
//			while(true){
//				try {
//					PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
//						public void run() {
//							try{
//								if(!isConnectm){
//									return ;
//								}
//							}
//						}
//						catch(Exception e){
//							e.printStackTrace();
//							isConnectm = false;
//							initData = false;
//							closeNetwork();
//							MessageBox dialog = new MessageBox(ProjectManager.getInstance().window.getShell(),SWT.ERROR);
//							dialog.setText("Error");
//							dialog.setMessage(e.getMessage());
//							dialog.open();
//							
//						}
//						}
//					
//						});
//
//				} catch (InterruptedException ex) {
//					ex.printStackTrace();
//				}
//			}
//
//
//		}
//	}
}
