package kr.co.n3soft.n3com.projectmanager;

import java.awt.GraphicsEnvironment;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import kr.co.n3soft.n3com.model.comm.DataInputPortModel;
import kr.co.n3soft.n3com.model.comm.DataOutputPortModel;
import kr.co.n3soft.n3com.model.comm.DocComponentModel;
import kr.co.n3soft.n3com.model.comm.EventInputPortModel;
import kr.co.n3soft.n3com.model.comm.EventOutputPortModel;
import kr.co.n3soft.n3com.model.comm.FinalPackageModel;
import kr.co.n3soft.n3com.model.comm.LineBendpointModel;
import kr.co.n3soft.n3com.model.comm.LineModel;
import kr.co.n3soft.n3com.model.comm.MethodInputPortModel;
import kr.co.n3soft.n3com.model.comm.MethodOutputPortModel;
import kr.co.n3soft.n3com.model.comm.PortModel;
import kr.co.n3soft.n3com.model.comm.StrcuturedPackageTreeModel;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.comm.UMLNoteModel;
import kr.co.n3soft.n3com.model.component.AtomicComponentModel;
import kr.co.n3soft.n3com.model.component.ComponentLibModel;
import kr.co.n3soft.n3com.model.component.ComponentModel;
import kr.co.n3soft.n3com.model.component.TemplateComponentModel;
import kr.co.n3soft.n3com.model.diagram.N3EditorDiagramModel;
import kr.co.n3soft.n3com.net.NetManager;
import kr.co.n3soft.n3com.project.browser.PackageTreeModel;
import kr.co.n3soft.n3com.project.browser.RootAppPackageTreeModel;
import kr.co.n3soft.n3com.project.browser.RootCmpEdtTreeModel;
import kr.co.n3soft.n3com.project.browser.RootCmpFnshTreeModel;
import kr.co.n3soft.n3com.project.browser.RootLibTreeModel;
import kr.co.n3soft.n3com.project.browser.RootPackageTreeModel;
import kr.co.n3soft.n3com.project.browser.RootTemplatePackageTreeModel;
import kr.co.n3soft.n3com.project.browser.SendFile;
import kr.co.n3soft.n3com.project.browser.StrcuturedPackageTreeLibModel;
import kr.co.n3soft.n3com.project.browser.UMLTreeModel;
import kr.co.n3soft.n3com.project.browser.UMLTreeParentModel;
import kr.co.n3soft.n3com.project.dialog.DetailPropertyTableItem;
import kr.co.n3soft.n3com.xml.ComponentXMLContentHandler;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Preferences;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.PlatformUI;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import uuu.Activator;
import uuu.preferences.PreferenceConstants;

import com.sun.org.apache.xerces.internal.parsers.DOMParser;
import com.sun.org.apache.xerces.internal.parsers.SAXParser;


/**
 * @author Administrator
 *
 */
/**
 * @author Administrator
 *
 */
public class CompAssemManager implements IRunnableWithProgress{

	private static CompAssemManager instance;
	private String libFoler = "c:\\lib";
	private String netFoler = "";	//111102 SDM - TEMP폴더 워크스페이스 안으로 이동
	private String appFileName = "";
	private String dpath="";
	private boolean isTotal = false;
	ApplicationProfile ap = null;
	java.util.ArrayList aps = new java.util.ArrayList();
	
	protected static String strWorkDir = "";	//111102 SDM - TEMP폴더 워크스페이스 안으로 이동
	
	//111102 SDM S - 워크스페이스 경로 받음
	public CompAssemManager() {
		strWorkDir = ResourcesPlugin.getWorkspace().getRoot().getLocation().toString() 
					+ File.separator + ".opros_cc" + File.separator;
		
		File f = new File(strWorkDir);
		
		if(!f.exists())
			f.mkdir();
		
		
		netFoler = strWorkDir + "comp_temp";
	}
	//111102 SDM E - 워크스페이스 경로 받음

	//	private HashMap chgXmlProp = new HashMap();

	public boolean isTotal() {
		return isTotal;
	}

	public void setTotal(boolean isTotal) {
		this.isTotal = isTotal;
	}

	public String getAppFileName() {
		return appFileName;
	}

	public void setAppFileName(String appFileName) {
		int i = appFileName.indexOf(".xml");
		String app = "";
		if(i>0){
			app = appFileName.substring(0,i);
		}
		this.appFileName = app;
	}

	public String getNetFoler() {
		return netFoler;
	}

	public void setNetFoler(String netFoler) {
		this.netFoler = netFoler;
	}

	private java.util.ArrayList libData = new java.util.ArrayList();

	private java.util.ArrayList compFiles = new java.util.ArrayList();
	public void initCompFiles() {
		compFiles.clear();
	}
	public java.util.ArrayList getCompFiles() {
		return compFiles;
	}

	public void setCompFiles(java.util.ArrayList compFiles) {
		this.compFiles = compFiles;
	}

	private HashMap modelStore = new HashMap();

	private HashMap modelStore2 = new HashMap();

	private HashMap viewStore = new HashMap();
	private HashMap dllFile = new HashMap();
	private HashMap etcFile = new HashMap();
	private HashMap dirModel = new HashMap();
	private HashMap dirView = new HashMap();
	public IProgressMonitor monitor = null;
	RootLibTreeModel rtm = null;
	RootCmpEdtTreeModel rcem = null;
	RootCmpFnshTreeModel rcefm = null;
	AtomicComponentModel atmc = null;
	
	public java.util.ArrayList diagramList = new java.util.ArrayList();

	public java.util.ArrayList getDiagramList() {
		return diagramList;
	}

	public void setDiagramList(java.util.ArrayList diagramList) {
		this.diagramList = diagramList;
	}

	private HashMap importViews = new HashMap();

	public HashMap getImportViews() {
		return importViews;
	}

	public void setImportViews(HashMap importViews) {
		this.importViews = importViews;
	}

	RootPackageTreeModel rpt = null;

	public RootPackageTreeModel getRpt() {
		return rpt;
	}

	public void setRpt(RootPackageTreeModel rpt) {
		this.rpt = rpt;
	}

	private HashMap chkcmp = new HashMap();
	private HashMap chkdir = new HashMap();
	private java.util.ArrayList chk = new java.util.ArrayList();
	private boolean isBegin = false;
	private boolean isImport = false;

	UMLTreeParentModel umMake;
	UMLTreeParentModel pmMake; //khg 2010.05.26 importApplication
	File targetMake;

	/*
	 * 1 makeLib
	 * 2 makeCopyMain
	 * 3 makeCopyComposite
	 */
	private int runType = 1;

	private java.util.ArrayList compositeCmp = new java.util.ArrayList();

	private java.util.ArrayList viewInfos = new java.util.ArrayList();
	private java.util.ArrayList viewInfos2 = new java.util.ArrayList();
	private static final int TOTAL_TIME = 10000;

	// The increment sleep time
	private static final int INCREMENT = 500;

	private boolean indeterminate;
	public static CompAssemManager getInstance() {
		if (instance == null) {
			instance = new CompAssemManager();



			return instance;
		}
		else {
			return instance;
		}
	}

	public boolean makeCopyMain(UMLTreeParentModel um,File target){	//111027 SDM - DLL체크후 결과리턴
		boolean bCopy = false;//111027 SDM - DLL체크후 결과리턴
		
		if(um==null){
			um = this.umMake;
		}
		if(target==null){
			target = this.targetMake;
		}

		UMLModel ur = (UMLModel)um.getRefModel();
		if(ur instanceof FinalPackageModel){
			File dirFile = null;
			if(this.appFileName!=null && !this.appFileName.equals("")){
				//				if(appFileName)
				dirFile = new File(target.getPath()+File.separator+appFileName);
			}
			else
				dirFile = new File(target.getPath()+File.separator+um.getName());
			dirFile.mkdir();
			//ijs TarManager -2 
			this.writeExportMain(um, dirFile.getPath());
			
			bCopy = this.makeSubCopy(um, dirFile);
			//    		}
		}
		return bCopy; //111027 SDM - DLL체크후 결과리턴
	}

	public void makeNetCopyMain(UMLTreeParentModel um,File target,UMLTreeParentModel ut){
		if(um==null){
			um = this.umMake;
		}
		if(target==null){
			target = this.targetMake;
		}

		UMLModel ur = (UMLModel)um.getRefModel();
		if(ur instanceof FinalPackageModel){

			File dirFile = new File(target.getPath()+File.separator+um.getName());

			if(dirFile.exists()){
				deleteFilesAndDirs(target.getPath());
			}
			if(!target.exists()){
				boolean ismkTarget = target.mkdir();
				if(ismkTarget){
					ismkTarget =  dirFile.mkdir();
					if(ismkTarget){
						this.writeExportMain(um, target.getPath());
						this.makeCopyComposite(ut, dirFile);
					}
				}
			}
			else{
				boolean	ismkTarget =  dirFile.mkdir();
				if(ismkTarget){
					this.writeExportMain(um, target.getPath());
					this.makeCopyComposite(ut, dirFile);
				}
			}

			//    		}
		}
	}
	
	//ijs TarManager -2 
	public void makeTar(UMLTreeParentModel um,File target){
		try{
			if(um==null){
				um = this.umMake;
			}
			if(target==null){
				target = this.targetMake;
			}
			File dirFile = new File(target.getPath()+File.separator+um.getName());
			if(dirFile.exists()){
				TarManager.getInstance().makeTar(dirFile, dirFile.getParentFile());
//				File dirFile = new File(target.getPath()+File.separator+um.getName());
//				if(dirFile.)
//				Thread.sleep(1000);
				deleteFilesAndDirs2(dirFile.getAbsolutePath());
//				deleteFiles(dirFile.getAbsolutePath());
//				dirFile.delete();
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	public void makeCopyComposite(UMLTreeParentModel um,File target){
		boolean bCopy = false; //111027 SDM - copy결과
		if(um==null){
			um = this.umMake;
		}
		if(target==null){
			target = this.targetMake;
		}
		UMLModel ur = (UMLModel)um.getRefModel();
		if(ur instanceof ComponentModel){
			ComponentModel cm = (ComponentModel)ur;
			String st = cm.getStereotype();
			if("<<composite>>".equals(cm.getStereotype())){
				//    			File source = cm.getFile();
				File dirFile = new File(target.getPath()+File.separator+um.getName());
				dirFile.mkdir();
				
				if(NetManager.getInstance().isJar){
					OPRoSManifest	oPRoSManifest = new OPRoSManifest();
					oPRoSManifest.Archive_type = "OPRoS_CompositeComponent";
					oPRoSManifest.Archive_name =um.getName()+".tar";
					oPRoSManifest.Archive_description = um.getName();
					oPRoSManifest.f = new File(dirFile+File.separator+"OPRoS.mf");
					um.oPRoSManifest = oPRoSManifest;
//					oPRoSManifest.ar
//					sub.getUMLTreeModel().oPRoSManifest = oPRoSManifest;
				}
				//ijs TarManager
				this.writeExportSub(um, dirFile.getPath());

				bCopy = this.makeSubCopy(um, dirFile);	//111027 SDM - copy결과
				if(NetManager.getInstance().isJar){
					if(um.oPRoSManifest!=null){
						um.oPRoSManifest.writeOPRoSManifest();
					}
					um.oPRoSManifest = null;
				}
				//ijs TarManager
//				try{
//					TarManager.getInstance().makeTar(dirFile, target);
//					dirFile.delete();
//				}
//				catch(Exception e){
//					e.printStackTrace();
//				}
			}
		}
	
	}

	public void makeSubComponentCopy(ComponentLibModel sub,File dir){
		try{
			File srcFile = null;
			HashMap map = new HashMap();
			map.put("name", sub.getName());
			map.put("id", sub.getId());
			map.put("description", sub.getDesc());
			map.put("properties",sub.getTags());

			if(sub.isInstance()){
				if(sub.getInstanceUMLTreeModel()!=null){
					ComponentLibModel cmi =	(ComponentLibModel)sub.getInstanceUMLTreeModel().getRefModel();
					if(cmi!=null){
						srcFile = cmi.getFile();
					}
				}
				else{
					srcFile = sub.getFile();
				}
			}
			else{
				srcFile = sub.getFile();
			}


			File desFile = new File(dir.getPath()+File.separator+sub.getName()+".xml");
			//			this.copyFile(srcFile, desFile);
			this.copyInstanceFile(srcFile, desFile, map);
			File viewDescFile = new File(dir.getPath()+File.separator+sub.getName()+"_view.xml");

			File srcViewFile = new File(srcFile.getParent()+File.separator+sub.getName()+"_view.xml");
			if(srcViewFile.exists()){
				this.copyFile(srcViewFile, viewDescFile);
			}


			File desDir = new File(dir.getPath()+File.separator+sub.getName());
			File srcDir = null;
			if(sub.isInstance()){
				if(sub.getInstanceUMLTreeModel()!=null){
					ComponentLibModel cmi =	(ComponentLibModel)sub.getInstanceUMLTreeModel().getRefModel();
					if(cmi!=null){


						srcDir = cmi.getDir();

					}
				}
				else{
					srcDir = sub.getDir();
				}
			}
			else 
				srcDir = sub.getDir();
			desDir.mkdir();

			this.backup(srcDir.getPath(), desDir.getPath());
		}
		catch(Exception e){
			e.printStackTrace();
		}

	}


	public void makeNetSubCopy(Object obj,File dir){
		try{
			OPRoSManifest oPRoSManifest = null;
			if(NetManager.getInstance().isJar){
				if(obj instanceof ComponentLibModel){

					ComponentLibModel sub = (ComponentLibModel)obj;

					HashMap map = new HashMap();
					map.put("name", sub.getName());
					map.put("id", sub.getId());
					map.put("description", sub.getDesc());
					map.put("properties",sub.getTags());
					if("<<composite>>".equals(sub.getStereotype())){
						
							
						File desDir = new File(dir.getPath()+File.separator+sub.getName());
						desDir.mkdir();
//						oPRoSManifest.f = new File(desDir+File.separator+"OPRoS.mf");
						File srcFile = null;

						File desFile = new File(desDir.getPath()+File.separator+sub.getName()+".xml");

						if(sub.isInstance()){
							if(sub.getInstanceUMLTreeModel()!=null){
								ComponentLibModel cmi =	(ComponentLibModel)sub.getInstanceUMLTreeModel().getRefModel();
								if(cmi!=null){
									srcFile = cmi.getFile();
								}
							}
							else{
								srcFile = sub.getFile();
							}
						}
						else{
							srcFile = sub.getFile();

						}


						this.copyInstanceFile(srcFile, desFile, map);


						File viewDescFile = new File(desDir.getPath()+File.separator+sub.getName()+"_view.xml");

						File srcViewFile = new File(srcFile.getPath()+File.separator+sub.getName()+"_view.xml");
						if(srcViewFile.exists()){
							this.copyFile(srcViewFile, viewDescFile);
						}


//						File desDir = new File(dir.getPath()+File.separator+sub.getName());
						File srcDir = null;
						if(sub.isInstance()){
							if(sub.getInstanceUMLTreeModel()!=null){
								ComponentLibModel cmi =	(ComponentLibModel)sub.getInstanceUMLTreeModel().getRefModel();
								if(cmi!=null){


									srcDir = cmi.getDir();

								}
							}
							else{
								srcDir = sub.getDir();
							}
						}
						else 
							srcDir = sub.getDir();

						

						this.backup(srcDir.getPath(), desDir.getPath());
					}
					else if("<<atomic>>".equals(sub.getStereotype())){
						File srcFile = null;
						File desDir = new File(dir.getPath()+File.separator+sub.getName());
						desDir.mkdir();
						if(sub.isInstance()){
							if(sub.getInstanceUMLTreeModel()!=null){
								ComponentLibModel cmi =	(ComponentLibModel)sub.getInstanceUMLTreeModel().getRefModel();
								if(cmi!=null){
									srcFile = cmi.getFile();
								}
							}
							else{
								srcFile = sub.getFile();
							}
						}

						else{
							srcFile = sub.getFile();
						}
						File desFile = new File(desDir.getPath()+File.separator+sub.getName()+".xml");
						if(srcFile!=null){
							this.copyInstanceFile(srcFile, desFile, map);
						}







						File dllFile = null;

						if(sub.isInstance()){
							if(sub.getInstanceUMLTreeModel()!=null){
								ComponentLibModel cmi =	(ComponentLibModel)sub.getInstanceUMLTreeModel().getRefModel();
								if(cmi!=null){
									dllFile = cmi.getDllFile();
								}
							}
							else{
								dllFile = sub.getDllFile();
							}
						}
						else{
							dllFile = sub.getDllFile();
						}
						if(dllFile!=null)
							desFile = new File(desDir.getPath()+File.separator+dllFile.getName());

						if(dllFile!=null){
							this.copyFile(dllFile, desFile);
						}
						File fsmFile = null;
						if(sub.isInstance()){
							if(sub.getInstanceUMLTreeModel()!=null){
								ComponentLibModel cmi =	(ComponentLibModel)sub.getInstanceUMLTreeModel().getRefModel();
								if(cmi!=null){
									fsmFile = cmi.getFsmFile();
								}
							}
							else{
								fsmFile = sub.getFsmFile();
							}
						}
						else{
							fsmFile = sub.getFsmFile();
						}

						if(fsmFile!=null){

							desFile = new File(desDir.getPath()+File.separator+fsmFile.getName());
							this.copyFile(fsmFile, desFile);
						}



					}
				}
				else if(obj instanceof ComponentModel){
					ComponentModel sub = (ComponentModel)obj;
					HashMap map = new HashMap();
					map.put("name", sub.getName());
					map.put("id", sub.getId());
					map.put("description", sub.getDesc());
					map.put("properties",sub.getTags());

					if("<<composite>>".equals(sub.getStereotype())){
						//ijs monitoring->exports
						

						this.makeCopyComposite((UMLTreeParentModel)sub.getUMLTreeModel(), dir);
//						sub.getUMLTreeModel().oPRoSManifest = null;

					}
					else if(obj instanceof AtomicComponentModel){
						
						
						AtomicComponentModel atm = (AtomicComponentModel)obj;
//						File srcFile = null;
//						String str = atm.getCmpFolder();
						AtomicComponentModel core  = atm.getCoreDiagramCmpModel();
						if(core!=null){

							AtomicComponentModel am = (AtomicComponentModel)atm.getCoreUMLTreeModel().getRefModel();
							File f1 = am.getFile();
							File srcFile = null;
							String str = f1.getAbsolutePath();
							srcFile = new File(str+File.separator+am.getName()+".tar");
							File desFile = new File(dir.getPath()+File.separator+am.getName());
							if(srcFile.exists()){
								TarManager.getInstance().extractTar(srcFile, desFile);
							}
							else{
								str = f1.getParentFile().getAbsolutePath();
								srcFile = new File(str+File.separator+am.getName()+".tar");
								if(srcFile.exists()){
									TarManager.getInstance().extractTar(srcFile, desFile);
								}else{
	//								desFile = new File(dir.getPath()+File.separator+am.getName());
	//								if(f)
									MessageBox dialog = new MessageBox(ProjectManager.getInstance().window.getShell(),SWT.ERROR);
									dialog.setText("Error");
									dialog.setMessage("tar file does not exist."); //KDI 080908 0002
									
									dialog.open();
									return;
								}
							}
							
//							core.getf
						}




					}




				}
			}
			else{
			if(obj instanceof ComponentLibModel){

				ComponentLibModel sub = (ComponentLibModel)obj;

				HashMap map = new HashMap();
				map.put("name", sub.getName());
				map.put("id", sub.getId());
				map.put("description", sub.getDesc());
				map.put("properties",sub.getTags());
				if("<<composite>>".equals(sub.getStereotype())){
					File srcFile = null;

					File desFile = new File(dir.getPath()+File.separator+sub.getName()+".xml");

					if(sub.isInstance()){
						if(sub.getInstanceUMLTreeModel()!=null){
							ComponentLibModel cmi =	(ComponentLibModel)sub.getInstanceUMLTreeModel().getRefModel();
							if(cmi!=null){
								srcFile = cmi.getFile();
							}
						}
						else{
							srcFile = sub.getFile();
						}
					}
					else{
						srcFile = sub.getFile();

					}


					this.copyInstanceFile(srcFile, desFile, map);


					File viewDescFile = new File(dir.getPath()+File.separator+sub.getName()+"_view.xml");

					File srcViewFile = new File(srcFile.getParent()+File.separator+sub.getName()+"_view.xml");
					if(srcViewFile.exists()){
						this.copyFile(srcViewFile, viewDescFile);
					}


					File desDir = new File(dir.getPath()+File.separator+sub.getName());
					File srcDir = null;
					if(sub.isInstance()){
						if(sub.getInstanceUMLTreeModel()!=null){
							ComponentLibModel cmi =	(ComponentLibModel)sub.getInstanceUMLTreeModel().getRefModel();
							if(cmi!=null){


								srcDir = cmi.getDir();

							}
						}
						else{
							srcDir = sub.getDir();
						}
					}
					else 
						srcDir = sub.getDir();

					desDir.mkdir();

					this.backup(srcDir.getPath(), desDir.getPath());
				}
				else if("<<atomic>>".equals(sub.getStereotype())){
					File srcFile = null;
					if(sub.isInstance()){
						if(sub.getInstanceUMLTreeModel()!=null){
							ComponentLibModel cmi =	(ComponentLibModel)sub.getInstanceUMLTreeModel().getRefModel();
							if(cmi!=null){
								srcFile = cmi.getFile();
							}
						}
						else{
							srcFile = sub.getFile();
						}
					}

					else{
						srcFile = sub.getFile();
					}
					File desFile = new File(dir.getPath()+File.separator+sub.getName()+".xml");
					if(srcFile!=null){
						this.copyInstanceFile(srcFile, desFile, map);
					}







					File dllFile = null;

					if(sub.isInstance()){
						if(sub.getInstanceUMLTreeModel()!=null){
							ComponentLibModel cmi =	(ComponentLibModel)sub.getInstanceUMLTreeModel().getRefModel();
							if(cmi!=null){
								dllFile = cmi.getDllFile();
							}
						}
						else{
							dllFile = sub.getDllFile();
						}
					}
					else{
						dllFile = sub.getDllFile();
					}
					if(dllFile!=null)
						desFile = new File(dir.getPath()+File.separator+dllFile.getName());

					if(dllFile!=null){
						this.copyFile(dllFile, desFile);
					}
					File fsmFile = null;
					if(sub.isInstance()){
						if(sub.getInstanceUMLTreeModel()!=null){
							ComponentLibModel cmi =	(ComponentLibModel)sub.getInstanceUMLTreeModel().getRefModel();
							if(cmi!=null){
								fsmFile = cmi.getFsmFile();
							}
						}
						else{
							fsmFile = sub.getFsmFile();
						}
					}
					else{
						fsmFile = sub.getFsmFile();
					}

					if(fsmFile!=null){

						desFile = new File(dir.getPath()+File.separator+fsmFile.getName());
						this.copyFile(fsmFile, desFile);
					}



				}
			}
			else if(obj instanceof ComponentModel){
				ComponentModel sub = (ComponentModel)obj;
				HashMap map = new HashMap();
				map.put("name", sub.getName());
				map.put("id", sub.getId());
				map.put("description", sub.getDesc());
				map.put("properties",sub.getTags());

				if("<<composite>>".equals(sub.getStereotype())){

					this.makeCopyComposite((UMLTreeParentModel)sub.getUMLTreeModel(), dir);

				}
				else if(obj instanceof AtomicComponentModel){
					AtomicComponentModel atm = (AtomicComponentModel)obj;
//					File f = atm.getFile();
					AtomicComponentModel am = (AtomicComponentModel)atm.getCoreUMLTreeModel().getRefModel();
					File f1 = am.getFile();
					if(f1 == null){
						
					}
					File srcFile = null;
					String str = f1.getAbsolutePath();
					
					AtomicComponentModel core  = atm.getCoreDiagramCmpModel();
					if(core!=null){
						srcFile = new File(str+File.separator+"profile"+File.separator+core.getName()+".xml");
						File desFile = new File(dir.getPath()+File.separator+atm.getName()+".xml");
						if(srcFile!=null && srcFile.exists()){
							this.copyInstanceFile(srcFile, desFile, map);
						}
						else{
							
						}
						Preferences pref = Activator.getDefault().getPluginPreferences();
						String strDeploy= pref.getString(PreferenceConstants.OPROS_DEPLOY_OPTION);
						File dllFile = null;
						//					if(dllFile!=null)
						desFile = new File(dir.getPath()+File.separator+core.getlibNameProp());
						if(strDeploy!=null && !"".equals(strDeploy)){
							dllFile = new File(str+File.separator+strDeploy+File.separator+am.getName()+".dll");
							if(!dllFile.exists() && "Release".equals(strDeploy)){
								dllFile = new File(str+File.separator+"Debug"+File.separator+am.getName()+".dll");
							}
							else if(!dllFile.exists() && "Debug".equals(strDeploy)){
								dllFile = new File(str+File.separator+"Release"+File.separator+core.getlibNameProp());
							}

						}
						else{
							dllFile = new File(str+File.separator+"Release"+File.separator+core.getlibNameProp());
							if(!dllFile.exists()){
//								if( "Release".equals(strDeploy)){
									dllFile = new File(str+File.separator+"Debug"+File.separator+am.getName()+".dll");
//								}
//								else if("Debug".equals(strDeploy)){
//									dllFile = new File(str+File.separator+"Release"+File.separator+co	re.getlibNameProp());
//								}
							}
						}

						if(dllFile!=null){
							this.copyFile(dllFile, desFile);
						}
						
					}
				}
			}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//ijs TarManager
	public boolean makeSubCopy(UMLTreeParentModel um,File dir){
		UMLModel ur = (UMLModel)um.getRefModel();
		if(ur instanceof ComponentModel){
			ComponentModel cm = (ComponentModel)ur;
			N3EditorDiagramModel nd = cm.getN3EditorDiagramModel();
			ComponentModel main = null;
			try{
				if(nd==null){
					for(int i=0;i<um.getChildren().length;i++){
						UMLTreeModel ut = (UMLTreeModel)um.getChildren()[i];
						if(ut.getRefModel() instanceof N3EditorDiagramModel){
							nd = (N3EditorDiagramModel)ut.getRefModel();
							break;
						}
					}
				}
				for(int i=0;i<nd.getChildren().size();i++){
					Object obj = nd.getChildren().get(i);
					if(obj instanceof ComponentLibModel){

						ComponentLibModel sub = (ComponentLibModel)obj;
						if(NetManager.getInstance().isJar){
							if(um.oPRoSManifest!=null){
								if(!um.oPRoSManifest.Archive_Elements.contains(sub.getName()))
								um.oPRoSManifest.Archive_Elements.add(sub.getName());
							}
						}

						if(sub.getUMLDataModel().getId().equals(cm.getUMLDataModel().getId())){
							main = sub;
							continue;
						}
						HashMap map = new HashMap();
						map.put("name", sub.getName());
						map.put("id", sub.getId());
						map.put("description", sub.getDesc());
						map.put("properties",sub.getTags());
						if("<<composite>>".equals(sub.getStereotype())){
							File srcFile = null;

							if(sub.isInstance()){
								if(sub.getInstanceUMLTreeModel()!=null){
									ComponentLibModel cmi =	(ComponentLibModel)sub.getInstanceUMLTreeModel().getRefModel();
									if(cmi!=null){
										srcFile = cmi.getFile();
									}
								}
								else{
									srcFile = sub.getFile();
								}
							}
							else{
								srcFile = sub.getFile();
							}
							
							File parentDir = new File(dir.getPath()+File.separator+sub.getName());
							
//							File parentDir = new File(dir.getPath()+File.separator+sub.getName());
							if(!parentDir.exists()){
								parentDir.mkdir();
							}

							String fileName = srcFile.getName();
							File desFile = new File(parentDir.getPath()+File.separator+sub.getName()+".xml");
							this.copyInstanceFile(srcFile, desFile,map);
							File viewDescFile = new File(parentDir.getPath()+File.separator+sub.getName()+"_view.xml");

							File srcViewFile = new File(srcFile.getParent()+File.separator+sub.getName()+"_view.xml");
							if(srcViewFile.exists()){
								this.copyFile(srcViewFile, viewDescFile);
							}


//							File desDir = new File(parentDir.getPath()+File.separator+sub.getName());
							File srcDir = sub.getDir();
//							desDir.mkdir();

							this.backup(srcDir.getPath(), parentDir.getPath());
						}
						else if("<<atomic>>".equals(sub.getStereotype())){
							File srcFile = null;
							if(NetManager.getInstance().isJar){
								if(um.oPRoSManifest!=null){
									if(!um.oPRoSManifest.Archive_Elements.contains(cm.getName()))
									um.oPRoSManifest.Archive_Elements.add(sub.getName());
								}
							}
							if(sub.isInstance()){
								if(sub.getInstanceUMLTreeModel()!=null){
									ComponentLibModel cmi =	(ComponentLibModel)sub.getInstanceUMLTreeModel().getRefModel();
									if(cmi!=null){
										srcFile = cmi.getFile();
									}
								}
								else{
									srcFile = sub.getFile();
								}
							}
							else{
								srcFile = sub.getFile();
							}
							if(NetManager.getInstance().isJar){
							File parentDir = new File(dir.getPath()+File.separator+sub.getName());
							
//							File parentDir = new File(dir.getPath()+File.separator+sub.getName());
							if(!parentDir.exists()){
								parentDir.mkdir();
							}
							
							//ijs TarManager
							File desFile = new File(parentDir.getPath()+File.separator+sub.getName()+".xml");
							File srcDir = sub.getDir();
							this.backup(srcFile.getParent(), parentDir.getPath());
							if(srcFile!=null){
								//								this.copyFile(srcFile, desFile);
								this.copyInstanceFile(srcFile, desFile,map);

							}
							}
							else{
								File desFile = new File(dir.getPath()+File.separator+sub.getName()+".xml");
								if(srcFile!=null){
									//								this.copyFile(srcFile, desFile);
									this.copyInstanceFile(srcFile, desFile,map);

								}
								File dllFile = null;
								if(sub.isInstance()){
									if(sub.getInstanceUMLTreeModel()!=null){
										ComponentLibModel cmi =	(ComponentLibModel)sub.getInstanceUMLTreeModel().getRefModel();
										if(cmi!=null){
											dllFile = cmi.getDllFile();
										}
									}
									else{
										dllFile = sub.getDllFile();
									}
								}
								else{
									dllFile = sub.getDllFile();
								}


								if(dllFile!=null)
									desFile = new File(dir.getPath()+File.separator+dllFile.getName());

								if(dllFile!=null){
									this.copyFile(dllFile, desFile);
								}
								File fsmFile = null;
								if(sub.isInstance()){
									if(sub.getInstanceUMLTreeModel()!=null){
										ComponentLibModel cmi =	(ComponentLibModel)sub.getInstanceUMLTreeModel().getRefModel();
										if(cmi!=null){
											fsmFile = cmi.getFsmFile();
										}
									}
									else{
										fsmFile = sub.getFsmFile();
									}
								}
								else{
									fsmFile = sub.getFsmFile();
								}

								if(fsmFile!=null){
									//								File fsmFile = sub.getFsmFile();
									desFile = new File(dir.getPath()+File.separator+fsmFile.getName());
									this.copyFile(fsmFile, desFile);
								}
							}
//							File dllFile = null;
//							if(sub.isInstance()){
//								if(sub.getInstanceUMLTreeModel()!=null){
//									ComponentLibModel cmi =	(ComponentLibModel)sub.getInstanceUMLTreeModel().getRefModel();
//									if(cmi!=null){
//										dllFile = cmi.getDllFile();
//									}
//								}
//								else{
//									dllFile = sub.getDllFile();
//								}
//							}
//							else{
//								dllFile = sub.getDllFile();
//							}
//
//
//							if(dllFile!=null)
//								desFile = new File(dir.getPath()+File.separator+dllFile.getName());
//
//							if(dllFile!=null){
//								this.copyFile(dllFile, desFile);
//							}
//							File fsmFile = null;
//							if(sub.isInstance()){
//								if(sub.getInstanceUMLTreeModel()!=null){
//									ComponentLibModel cmi =	(ComponentLibModel)sub.getInstanceUMLTreeModel().getRefModel();
//									if(cmi!=null){
//										fsmFile = cmi.getFsmFile();
//									}
//								}
//								else{
//									fsmFile = sub.getFsmFile();
//								}
//							}
//							else{
//								fsmFile = sub.getFsmFile();
//							}
//
//							if(fsmFile!=null){
//								//								File fsmFile = sub.getFsmFile();
//								desFile = new File(dir.getPath()+File.separator+fsmFile.getName());
//								this.copyFile(fsmFile, desFile);
//							}
//
//
//
						}
					}
					// IJS 2010-08-13 3:56오후 어플리케이션 익스포트  관련 S
					else if(obj instanceof AtomicComponentModel){
						AtomicComponentModel sub = (AtomicComponentModel)obj;

						if(sub.getUMLDataModel().getId().equals(cm.getUMLDataModel().getId())){
							main = sub;
							continue;
						}
						HashMap map = new HashMap();
						map.put("name", sub.getName());
						map.put("id", sub.getId());
						map.put("description", sub.getDesc());
						map.put("properties",sub.getTags());
						if(NetManager.getInstance().isJar){
							if(um.oPRoSManifest!=null){
								um.oPRoSManifest.Archive_Elements.add(sub.getName());
							}
						}
						if("<<atomic>>".equals(sub.getStereotype())){
							File srcFile = null;
							//110908 S SDM - 파일경로 가져오는 부분 수정
							File fTemp = sub.getFile();
							if(fTemp != null){
								String str = fTemp.getPath();
								
	
								srcFile = new File(str+File.separator+"profile"+File.separator+sub.getName()+".xml");
								File parentDir = new File(dir.getPath()+File.separator+sub.getName());
								
	//							File parentDir = new File(dir.getPath()+File.separator+sub.getName());
								if(!parentDir.exists()){
									parentDir.mkdir();
								}
	
								String fileName = srcFile.getName();
								File desFile = new File(parentDir.getPath()+File.separator+sub.getName()+".xml");
	
	//							File desFile = new File(dir.getPath()+File.separator+sub.getName()+".xml");
								File desDir = new File(parentDir.getPath()+File.separator+sub.getName());
								File srcDir = sub.getDir();
								desDir.mkdir();
								
								
								if(srcFile!=null){
	
									this.copyInstanceFile(srcFile, desFile,map);
	
								}
	//							File dllFile = null;
	//							dllFile = new File(str+File.separator+"Debug"+File.separator+sub.getlibNameProp());
	//							desFile = new File(dir.getPath()+File.separator+sub.getlibNameProp());
	//							if(dllFile.exists()){
	//								CompAssemManager.getInstance().copyFile(dllFile, desFile);
	//							}
	//							else{
	//								dllFile = new File(str+File.separator+"Release"+File.separator+sub.getlibNameProp());
	//								if(dllFile.exists()){
	//									CompAssemManager.getInstance().copyFile(dllFile, desFile);
	//								}
	//								else{
	//
	//								}
	//							}
	
								//							if(dllFile!=null)
								//								desFile = new File(dir.getPath()+File.separator+dllFile.getName());
								//
								//							if(dllFile!=null){
								//								this.copyFile(dllFile, desFile);
								//							}
								//							File fsmFile = null;
	
	
							}
							//110908 S SDM - 파일경로 가져오는 부분 수정
							
						}
					}
					// IJS 2010-08-13 3:56오후 어플리케이션 익스포트  관련 E

					else if(obj instanceof ComponentModel){
						ComponentModel sub = (ComponentModel)obj;

						if(sub.getUMLDataModel().getId().equals(cm.getUMLDataModel().getId())){
							main = sub;
							continue;
						}
						if(NetManager.getInstance().isJar){
							if(um.oPRoSManifest!=null){
								um.oPRoSManifest.Archive_Elements.add(sub.getName());
							}
						}
						if("<<composite>>".equals(sub.getStereotype())){
							um = (UMLTreeParentModel)ProjectManager.getInstance().getModelBrowser().searchId(sub.getUMLDataModel().getId());
							//    					File targetDir = new File(dir.getPath()+File.separator+sub.getName());
							//    					this.writeExportSub(um, dir.getPath());
							//    					targetDir.mkdir();
							if(um!=null)
								this.makeCopyComposite(um, dir);
							else{
								this.makeCopyComposite((UMLTreeParentModel)sub.getUMLTreeModel(), dir);
							}

						}

					}
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}

		}
		else if(ur instanceof FinalPackageModel){
			FinalPackageModel cm = (FinalPackageModel)ur;
			N3EditorDiagramModel nd = cm.getN3EditorDiagramModel();
			ComponentModel main = null;
			try{
				for(int i=0;i<nd.getChildren().size();i++){
					Object obj = nd.getChildren().get(i);
					if(obj instanceof ComponentLibModel){

						ComponentLibModel sub = (ComponentLibModel)obj;
						HashMap map = new HashMap();
						map.put("name", sub.getName());
						map.put("id", sub.getId());
						map.put("description", sub.getDesc());
						map.put("properties",sub.getTags());
						//    				if(sub.getUMLDataModel().getId().equals(cm.getUMLDataModel().getId())){
						//    					main = sub;
						//    					continue;
						//    				}
						if("<<composite>>".equals(sub.getStereotype())){
							File srcFile = null;
							
							if(sub.isInstance()){
								if(sub.getInstanceUMLTreeModel()!=null){
									ComponentLibModel cmi =	(ComponentLibModel)sub.getInstanceUMLTreeModel().getRefModel();
									if(cmi!=null){
										srcFile = cmi.getFile();
									}
								}
								else{
									srcFile = sub.getFile();
								}
							}
							else{
								srcFile = sub.getFile();
							}
							File parentDir = new File(dir.getPath()+File.separator+sub.getName());
							
//							File parentDir = new File(dir.getPath()+File.separator+sub.getName());
							if(!parentDir.exists()){
								parentDir.mkdir();
							}
							File desFile = new File(parentDir.getPath()+File.separator+sub.getName()+".xml");
							//							this.copyFile(srcFile, desFile);
//							File desDir = new File(parentDir.getPath()+File.separator+sub.getName());
							File srcDir = sub.getDir();
//							desDir.mkdir();

							this.backup(srcDir.getPath(), parentDir.getPath());
							this.copyInstanceFile(srcFile, desFile,map);
							
						}
						else if("<<atomic>>".equals(sub.getStereotype())){
							File srcFile = null;
							
							
							
							if(sub.isInstance()){
								if(sub.getInstanceUMLTreeModel()!=null){
									ComponentLibModel cmi =	(ComponentLibModel)sub.getInstanceUMLTreeModel().getRefModel();
									if(cmi!=null){
										srcFile = cmi.getFile();
									}
								}
								else{
									srcFile = sub.getFile();
								}
							}
							else{
								srcFile = sub.getFile();
							}
							
							//							String fileName = srcFile.getName();
							if(srcFile!=null){
								//								this.copyFile(srcFile, desFile);
								File parentDir = new File(dir.getPath()+File.separator+sub.getName());
								if(!parentDir.exists()){
									parentDir.mkdir();	
								}
								//110908 S SDM - TAR 형식의 컴포넌트 구성이 아닌경우 생기는 ExportApp 문제 해결
								String str = sub.getFile().getPath();
								int nBool = str.lastIndexOf(File.separator + "profile" + File.separator + sub.getFileName());
								
								if(nBool == -1)
									this.backup(srcFile.getParent(), parentDir.getPath(), sub);
								else
									this.backup(srcFile.getParent(), parentDir.getPath());
								
								//110908 E SDM - TAR 형식의 컴포넌트 구성이 아닌경우 생기는 ExportApp 문제 해결
								
								
								File desFile = new File(parentDir.getPath()+File.separator+sub.getName()+".xml");
								this.copyInstanceFile(srcFile, desFile,map);
							}
//							File dllFile = null;
//							if(sub.isInstance()){
//								if(sub.getInstanceUMLTreeModel()!=null){
//									ComponentLibModel cmi =	(ComponentLibModel)sub.getInstanceUMLTreeModel().getRefModel();
//									if(cmi!=null){
//										dllFile = cmi.getDllFile();
//									}
//								}
//								else{
//									dllFile = sub.getDllFile();
//								}
//							}
//							else{
//								dllFile = sub.getDllFile();
//							}
//
//
//							if(dllFile!=null)
//								desFile = new File(dir.getPath()+File.separator+dllFile.getName());
//
//							if(dllFile!=null){
//								this.copyFile(dllFile, desFile);
//							}
//							File fsmFile = null;
//							if(sub.isInstance()){
//								if(sub.getInstanceUMLTreeModel()!=null){
//									ComponentLibModel cmi =	(ComponentLibModel)sub.getInstanceUMLTreeModel().getRefModel();
//									if(cmi!=null){
//										fsmFile = cmi.getFsmFile();
//									}
//								}
//								else{
//									fsmFile = sub.getFsmFile();
//								}
//							}
//							else{
//								fsmFile = sub.getFsmFile();
//							}
//
//							if(fsmFile!=null){
//								//								File fsmFile = sub.getFsmFile();
//								desFile = new File(dir.getPath()+File.separator+fsmFile.getName());
//								this.copyFile(fsmFile, desFile);
//							}



						}
					}
					// IJS 2010-08-13 3:56오후 어플리케이션 익스포트  관련 S
					else if(obj instanceof AtomicComponentModel){

						AtomicComponentModel am = (AtomicComponentModel)obj;
						AtomicComponentModel sub = am.getCoreDiagramCmpModel();		//110906  SDM - 파일경로 NULL 부분 해결

						if(sub.getUMLDataModel().getId().equals(cm.getUMLDataModel().getId())){
							main = sub;
							continue;
						}
						HashMap map = new HashMap();
						map.put("name", sub.getName());
						map.put("id", sub.getId());
						map.put("description", sub.getDesc());
						map.put("properties",sub.getTags());
						if("<<atomic>>".equals(sub.getStereotype())){
							File fTemp = sub.getFile();
							if(fTemp != null){
								String str = fTemp.getPath();	//110906  SDM - 파일경로 NULL 부분 해결
								
								//111027 SDM S - DLL체크
								File tempDir1 = new File(str+File.separator+"Debug");
								File tempDir2 = new File(str+File.separator+"Release");	
								
								if((tempDir1.list().length>0) || (tempDir2.list().length>0)){
									
									File srcFile = null;
									srcFile = new File(str+File.separator+"profile");	//110908 SDM - 수정
		
		
		//							File desFile = new File(dir.getPath()+File.separator+sub.getName()+".xml");
									File parentDir = new File(dir.getPath()+File.separator+sub.getName());
									
		//							File parentDir = new File(dir.getPath()+File.separator+sub.getName());
									if(!parentDir.exists()){
										parentDir.mkdir();
									}
		
		//							String fileName = srcFile.getName();	//110908 SDM  - 삭제
									File desFile = new File(parentDir.getPath()+File.separator+am.getName()+".xml");
		
		//							File desFile = new File(dir.getPath()+File.separator+sub.getName()+".xml");
		//							File desDir = new File(parentDir.getPath()+File.separator+sub.getName());
		//							File srcDir = sub.getDir();
		//							desDir.mkdir();
									
									this.backup(srcFile.getParent(), parentDir.getPath());
									if(srcFile!=null){
		
										this.copyInstanceFile(new File(srcFile.getPath() + File.separator + sub.getName() + ".xml"), desFile, map);	//110908 SDM - 수정
		
									}
		//							File dllFile = null;
		//							dllFile = new File(str+File.separator+"Debug"+File.separator+sub.getlibNameProp());
		//							desFile = new File(dir.getPath()+File.separator+sub.getlibNameProp());
		//							if(dllFile.exists()){
		//								CompAssemManager.getInstance().copyFile(dllFile, desFile);
		//							}
		//							else{
		//								dllFile = new File(str+File.separator+"Release"+File.separator+sub.getlibNameProp());
		//								if(dllFile.exists()){
		//									CompAssemManager.getInstance().copyFile(dllFile, desFile);
		//								}
		//								else{
		//
		//								}
		//							}
		
									//							if(dllFile!=null)
									//								desFile = new File(dir.getPath()+File.separator+dllFile.getName());
									//
									//							if(dllFile!=null){
									//								this.copyFile(dllFile, desFile);
									//							}
									//							File fsmFile = null;

								}
								else{
									return false;
								}
								//111027 SDM E - DLL체크
							}
							//111103 SDM S - 컴포넌트 에디터에 프로젝트가 생성되지 않는경우
							else{
								return false;
							}
							//111103 SDM E - 컴포넌트 에디터에 프로젝트가 생성되지 않는경우
						}
					}
					// IJS 2010-08-13 3:56오후 어플리케이션 익스포트  관련 E
					else if(obj instanceof ComponentModel){
						ComponentModel sub = (ComponentModel)obj;
						if("<<composite>>".equals(sub.getStereotype())){
							um = (UMLTreeParentModel)ProjectManager.getInstance().getModelBrowser().searchId(sub.getUMLDataModel().getId());
							if(um!=null)
								this.makeCopyComposite(um, dir);
							else{
								UMLTreeParentModel up = (UMLTreeParentModel)sub.getUMLTreeModel();
								this.makeCopyComposite(up, dir);
							}

						}
					}
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}

		}
		return true;
	}

//	public void makeSubCopy(UMLTreeParentModel um,File dir){
//		UMLModel ur = (UMLModel)um.getRefModel();
//		if(ur instanceof ComponentModel){
//			ComponentModel cm = (ComponentModel)ur;
//			N3EditorDiagramModel nd = cm.getN3EditorDiagramModel();
//			ComponentModel main = null;
//			try{
//				if(nd==null){
//					for(int i=0;i<um.getChildren().length;i++){
//						UMLTreeModel ut = (UMLTreeModel)um.getChildren()[i];
//						if(ut.getRefModel() instanceof N3EditorDiagramModel){
//							nd = (N3EditorDiagramModel)ut.getRefModel();
//							break;
//						}
//					}
//				}
//				for(int i=0;i<nd.getChildren().size();i++){
//					Object obj = nd.getChildren().get(i);
//					if(obj instanceof ComponentLibModel){
//
//						ComponentLibModel sub = (ComponentLibModel)obj;
//
//						if(sub.getUMLDataModel().getId().equals(cm.getUMLDataModel().getId())){
//							main = sub;
//							continue;
//						}
//						HashMap map = new HashMap();
//						map.put("name", sub.getName());
//						map.put("id", sub.getId());
//						map.put("description", sub.getDesc());
//						map.put("properties",sub.getTags());
//						if("<<composite>>".equals(sub.getStereotype())){
//							File srcFile = null;
//
//							if(sub.isInstance()){
//								if(sub.getInstanceUMLTreeModel()!=null){
//									ComponentLibModel cmi =	(ComponentLibModel)sub.getInstanceUMLTreeModel().getRefModel();
//									if(cmi!=null){
//										srcFile = cmi.getFile();
//									}
//								}
//								else{
//									srcFile = sub.getFile();
//								}
//							}
//							else{
//								srcFile = sub.getFile();
//							}
//
//							String fileName = srcFile.getName();
//							File desFile = new File(dir.getPath()+File.separator+sub.getName()+".xml");
//							this.copyInstanceFile(srcFile, desFile,map);
//							File viewDescFile = new File(dir.getPath()+File.separator+sub.getName()+"_view.xml");
//
//							File srcViewFile = new File(srcFile.getParent()+File.separator+sub.getName()+"_view.xml");
//							if(srcViewFile.exists()){
//								this.copyFile(srcViewFile, viewDescFile);
//							}
//
//
//							File desDir = new File(dir.getPath()+File.separator+sub.getName());
//							File srcDir = sub.getDir();
//							desDir.mkdir();
//
//							this.backup(srcDir.getPath(), desDir.getPath());
//						}
//						else if("<<atomic>>".equals(sub.getStereotype())){
//							File srcFile = null;
//							if(sub.isInstance()){
//								if(sub.getInstanceUMLTreeModel()!=null){
//									ComponentLibModel cmi =	(ComponentLibModel)sub.getInstanceUMLTreeModel().getRefModel();
//									if(cmi!=null){
//										srcFile = cmi.getFile();
//									}
//								}
//								else{
//									srcFile = sub.getFile();
//								}
//							}
//							else{
//								srcFile = sub.getFile();
//							}
//
//
//							File desFile = new File(dir.getPath()+File.separator+sub.getName()+".xml");
//							if(srcFile!=null){
//								//								this.copyFile(srcFile, desFile);
//								this.copyInstanceFile(srcFile, desFile,map);
//
//							}
//							File dllFile = null;
//							if(sub.isInstance()){
//								if(sub.getInstanceUMLTreeModel()!=null){
//									ComponentLibModel cmi =	(ComponentLibModel)sub.getInstanceUMLTreeModel().getRefModel();
//									if(cmi!=null){
//										dllFile = cmi.getDllFile();
//									}
//								}
//								else{
//									dllFile = sub.getDllFile();
//								}
//							}
//							else{
//								dllFile = sub.getDllFile();
//							}
//
//
//							if(dllFile!=null)
//								desFile = new File(dir.getPath()+File.separator+dllFile.getName());
//
//							if(dllFile!=null){
//								this.copyFile(dllFile, desFile);
//							}
//							File fsmFile = null;
//							if(sub.isInstance()){
//								if(sub.getInstanceUMLTreeModel()!=null){
//									ComponentLibModel cmi =	(ComponentLibModel)sub.getInstanceUMLTreeModel().getRefModel();
//									if(cmi!=null){
//										fsmFile = cmi.getFsmFile();
//									}
//								}
//								else{
//									fsmFile = sub.getFsmFile();
//								}
//							}
//							else{
//								fsmFile = sub.getFsmFile();
//							}
//
//							if(fsmFile!=null){
//								//								File fsmFile = sub.getFsmFile();
//								desFile = new File(dir.getPath()+File.separator+fsmFile.getName());
//								this.copyFile(fsmFile, desFile);
//							}
//
//
//
//						}
//					}
//					// IJS 2010-08-13 3:56오후 어플리케이션 익스포트  관련 S
//					else if(obj instanceof AtomicComponentModel){
//
//						AtomicComponentModel sub = (AtomicComponentModel)obj;
//
//						if(sub.getUMLDataModel().getId().equals(cm.getUMLDataModel().getId())){
//							main = sub;
//							continue;
//						}
//						HashMap map = new HashMap();
//						map.put("name", sub.getName());
//						map.put("id", sub.getId());
//						map.put("description", sub.getDesc());
//						map.put("properties",sub.getTags());
//						if("<<atomic>>".equals(sub.getStereotype())){
//							File srcFile = null;
//							String str = sub.getCmpFolder();
//
//							srcFile = new File(str+File.separator+"profile"+File.separator+sub.getName()+".xml");
//
//
//							File desFile = new File(dir.getPath()+File.separator+sub.getName()+".xml");
//							if(srcFile!=null){
//
//								this.copyInstanceFile(srcFile, desFile,map);
//
//							}
//							File dllFile = null;
//							dllFile = new File(str+File.separator+"Debug"+File.separator+sub.getlibNameProp());
//							desFile = new File(dir.getPath()+File.separator+sub.getlibNameProp());
//							if(dllFile.exists()){
//								CompAssemManager.getInstance().copyFile(dllFile, desFile);
//							}
//							else{
//								dllFile = new File(str+File.separator+"Release"+File.separator+sub.getlibNameProp());
//								if(dllFile.exists()){
//									CompAssemManager.getInstance().copyFile(dllFile, desFile);
//								}
//								else{
//
//								}
//							}
//
//							//							if(dllFile!=null)
//							//								desFile = new File(dir.getPath()+File.separator+dllFile.getName());
//							//
//							//							if(dllFile!=null){
//							//								this.copyFile(dllFile, desFile);
//							//							}
//							//							File fsmFile = null;
//
//
//
//						}
//					}
//					// IJS 2010-08-13 3:56오후 어플리케이션 익스포트  관련 E
//
//					else if(obj instanceof ComponentModel){
//						ComponentModel sub = (ComponentModel)obj;
//
//						if(sub.getUMLDataModel().getId().equals(cm.getUMLDataModel().getId())){
//							main = sub;
//							continue;
//						}
//						if("<<composite>>".equals(sub.getStereotype())){
//							um = (UMLTreeParentModel)ProjectManager.getInstance().getModelBrowser().searchId(sub.getUMLDataModel().getId());
//							//    					File targetDir = new File(dir.getPath()+File.separator+sub.getName());
//							//    					this.writeExportSub(um, dir.getPath());
//							//    					targetDir.mkdir();
//							if(um!=null)
//								this.makeCopyComposite(um, dir);
//							else{
//								this.makeCopyComposite((UMLTreeParentModel)sub.getUMLTreeModel(), dir);
//							}
//
//						}
//
//					}
//				}
//			}
//			catch(Exception e){
//				e.printStackTrace();
//			}
//
//		}
//		else if(ur instanceof FinalPackageModel){
//			FinalPackageModel cm = (FinalPackageModel)ur;
//			N3EditorDiagramModel nd = cm.getN3EditorDiagramModel();
//			ComponentModel main = null;
//			try{
//				for(int i=0;i<nd.getChildren().size();i++){
//					Object obj = nd.getChildren().get(i);
//					if(obj instanceof ComponentLibModel){
//
//						ComponentLibModel sub = (ComponentLibModel)obj;
//						HashMap map = new HashMap();
//						map.put("name", sub.getName());
//						map.put("id", sub.getId());
//						map.put("description", sub.getDesc());
//						map.put("properties",sub.getTags());
//						//    				if(sub.getUMLDataModel().getId().equals(cm.getUMLDataModel().getId())){
//						//    					main = sub;
//						//    					continue;
//						//    				}
//						if("<<composite>>".equals(sub.getStereotype())){
//							File srcFile = null;
//							if(sub.isInstance()){
//								if(sub.getInstanceUMLTreeModel()!=null){
//									ComponentLibModel cmi =	(ComponentLibModel)sub.getInstanceUMLTreeModel().getRefModel();
//									if(cmi!=null){
//										srcFile = cmi.getFile();
//									}
//								}
//								else{
//									srcFile = sub.getFile();
//								}
//							}
//							else{
//								srcFile = sub.getFile();
//							}
//							File desFile = new File(dir.getPath()+File.separator+sub.getName()+".xml");
//							//							this.copyFile(srcFile, desFile);
//							this.copyInstanceFile(srcFile, desFile,map);
//							File desDir = new File(dir.getPath()+File.separator+sub.getName());
//							File srcDir = sub.getDir();
//							desDir.mkdir();
//
//							this.backup(srcDir.getPath(), desDir.getPath());
//						}
//						else if("<<atomic>>".equals(sub.getStereotype())){
//							File srcFile = null;
//							if(sub.isInstance()){
//								if(sub.getInstanceUMLTreeModel()!=null){
//									ComponentLibModel cmi =	(ComponentLibModel)sub.getInstanceUMLTreeModel().getRefModel();
//									if(cmi!=null){
//										srcFile = cmi.getFile();
//									}
//								}
//								else{
//									srcFile = sub.getFile();
//								}
//							}
//							else{
//								srcFile = sub.getFile();
//							}
//							File desFile = new File(dir.getPath()+File.separator+sub.getName()+".xml");
//							//							String fileName = srcFile.getName();
//							if(srcFile!=null){
//								//								this.copyFile(srcFile, desFile);
//								this.copyInstanceFile(srcFile, desFile,map);
//							}
//							File dllFile = null;
//							if(sub.isInstance()){
//								if(sub.getInstanceUMLTreeModel()!=null){
//									ComponentLibModel cmi =	(ComponentLibModel)sub.getInstanceUMLTreeModel().getRefModel();
//									if(cmi!=null){
//										dllFile = cmi.getDllFile();
//									}
//								}
//								else{
//									dllFile = sub.getDllFile();
//								}
//							}
//							else{
//								dllFile = sub.getDllFile();
//							}
//
//
//							if(dllFile!=null)
//								desFile = new File(dir.getPath()+File.separator+dllFile.getName());
//
//							if(dllFile!=null){
//								this.copyFile(dllFile, desFile);
//							}
//							File fsmFile = null;
//							if(sub.isInstance()){
//								if(sub.getInstanceUMLTreeModel()!=null){
//									ComponentLibModel cmi =	(ComponentLibModel)sub.getInstanceUMLTreeModel().getRefModel();
//									if(cmi!=null){
//										fsmFile = cmi.getFsmFile();
//									}
//								}
//								else{
//									fsmFile = sub.getFsmFile();
//								}
//							}
//							else{
//								fsmFile = sub.getFsmFile();
//							}
//
//							if(fsmFile!=null){
//								//								File fsmFile = sub.getFsmFile();
//								desFile = new File(dir.getPath()+File.separator+fsmFile.getName());
//								this.copyFile(fsmFile, desFile);
//							}
//
//
//
//						}
//					}
//					// IJS 2010-08-13 3:56오후 어플리케이션 익스포트  관련 S
//					else if(obj instanceof AtomicComponentModel){
//
//						AtomicComponentModel sub = (AtomicComponentModel)obj;
//
//						if(sub.getUMLDataModel().getId().equals(cm.getUMLDataModel().getId())){
//							main = sub;
//							continue;
//						}
//						HashMap map = new HashMap();
//						map.put("name", sub.getName());
//						map.put("id", sub.getId());
//						map.put("description", sub.getDesc());
//						map.put("properties",sub.getTags());
//						if("<<atomic>>".equals(sub.getStereotype())){
//							File srcFile = null;
//							String str = sub.getCmpFolder();
//
//							srcFile = new File(str+File.separator+"profile"+File.separator+sub.getName()+".xml");
//
//
//							File desFile = new File(dir.getPath()+File.separator+sub.getName()+".xml");
//							if(srcFile!=null){
//
//								this.copyInstanceFile(srcFile, desFile,map);
//
//							}
//							File dllFile = null;
//							dllFile = new File(str+File.separator+"Debug"+File.separator+sub.getlibNameProp());
//							desFile = new File(dir.getPath()+File.separator+sub.getlibNameProp());
//							if(dllFile.exists()){
//								CompAssemManager.getInstance().copyFile(dllFile, desFile);
//							}
//							else{
//								dllFile = new File(str+File.separator+"Release"+File.separator+sub.getlibNameProp());
//								if(dllFile.exists()){
//									CompAssemManager.getInstance().copyFile(dllFile, desFile);
//								}
//								else{
//
//								}
//							}
//
//							//							if(dllFile!=null)
//							//								desFile = new File(dir.getPath()+File.separator+dllFile.getName());
//							//
//							//							if(dllFile!=null){
//							//								this.copyFile(dllFile, desFile);
//							//							}
//							//							File fsmFile = null;
//
//
//
//						}
//					}
//					// IJS 2010-08-13 3:56오후 어플리케이션 익스포트  관련 E
//					else if(obj instanceof ComponentModel){
//						ComponentModel sub = (ComponentModel)obj;
//						if("<<composite>>".equals(sub.getStereotype())){
//							um = (UMLTreeParentModel)ProjectManager.getInstance().getModelBrowser().searchId(sub.getUMLDataModel().getId());
//							if(um!=null)
//								this.makeCopyComposite(um, dir);
//							else{
//								UMLTreeParentModel up = (UMLTreeParentModel)sub.getUMLTreeModel();
//								this.makeCopyComposite(up, dir);
//							}
//
//						}
//					}
//				}
//			}
//			catch(Exception e){
//				e.printStackTrace();
//			}
//
//		}
//	}

	//111108 SDM - backup메소드 전체적으로 수정.. DLL체크
	public void backup( String currentPath, String backupPath )
	throws IOException
	{
		File [] subList = new File ( currentPath ).listFiles ( );

		for ( int i = 0; i < subList.length; i++ )
		{
			boolean bak = true;
			if ( subList [i].isFile ( ) ){
				if(subList[i].getName().indexOf(".xml") != -1){
					ComponentProfile retModel = this.makeModel(subList[i],null);
					//111102 SDM S - 포트 XML읽을때 NULL포인트 에러 수정
					String strDllName = null;
					if(retModel != null)
						strDllName = retModel.execution_environment_library_name;
					else
						strDllName = "";
					
					if(strDllName==null && strDllName.equals(""))
						bak = false;
					else{
						for(int j=0; j < subList.length; j++){
							if(subList[j].getName().equals(strDllName)){
								bak = true;
								break;
							}
							else{
								bak = false;
							}
						}
					}
						
				}
				
				if(bak){
					//111111 SDM S - 파일입출력 수정
					FileInputStream inputStream = new FileInputStream(currentPath + File.separator + subList [i].getName ( ));         
					FileOutputStream outputStream = new FileOutputStream(backupPath + File.separator + subList [i].getName ( ));

					FileChannel fcin =  inputStream.getChannel();
					FileChannel fcout = outputStream.getChannel();

					long size = fcin.size();
					    
					fcin.transferTo(0, size, fcout);

					fcout.close();
					fcin.close();
					outputStream.close();
					inputStream.close();
					
					
					/*
					InputStream in = new FileInputStream ( currentPath + File.separator + subList [i].getName ( ) );
					OutputStream out = new FileOutputStream ( backupPath + File.separator + subList [i].getName ( ) );
		
					int count = 0;
					for ( int b; ( b = in.read ( ) ) != -1; ){
						++count;
						out.write(b);
					}
					in.close ( );
					out.close ( );
					if(monitor!=null){
						monitor.subTask(subList[i].getPath());
						monitor.worked(INCREMENT);
					}*/
					//111111 SDM E - 파일입출력 수정
				}
			} else if ( subList[i].isDirectory ( ) ) {
				File backupDir = new File ( backupPath + File.separator + subList [i].getName ( ) );
				if ( ! backupDir.isDirectory ( ) ){
					backupDir.mkdir ( );
				}
				this.backup(currentPath + File.separator + subList [i].getName ( ), backupPath + File.separator + subList [i].getName ( ));
			}
		}
	}
	
	//110908 E SDM - TAR 형식의 컴포넌트 구성이 아닌경우 생기는 ExportApp 문제 해결
	public void backup(String currentPath, String backupPath, ComponentLibModel cm)
	throws IOException
	{
		File fDllFile = cm.getDllFile();
		
		if (fDllFile.isFile()){
			InputStream in = new FileInputStream ( currentPath + File.separator + fDllFile.getName ( ) );
			OutputStream out = new FileOutputStream ( backupPath + File.separator + fDllFile.getName ( ) );

			int count = 0;
			for ( int b; ( b = in.read ( ) ) != -1; ){
				++count;
				out.write(b);
			}
			in.close ( );
			out.close ( );
			if(monitor!=null){
				monitor.subTask(fDllFile.getPath());
				monitor.worked(INCREMENT);
			}
		}
	}
	//110908 E SDM - TAR 형식의 컴포넌트 구성이 아닌경우 생기는 ExportApp 문제 해결

	public void copyFile(File srcFile , File desFile){
		try
		{
			InputStream in = new FileInputStream(srcFile);
			OutputStream out = new FileOutputStream(desFile);
			byte buf[] = new byte[1024];

			for (int cnt;(cnt = in.read(buf))!=-1;)
				out.write(buf, 0, cnt);
			in.close();
			out.close();
			if(monitor!=null){
				monitor.subTask(srcFile.getPath());
				monitor.worked(INCREMENT);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public void writeExportMain(UMLTreeParentModel um,String path){
		String fileName = um.getName();
		//String path = um.getFilterPath();
		String tPath = "";
		if((fileName==null || fileName.equals("")) && this.appFileName!=null && !this.appFileName.equals("")){
			tPath = path+File.separator+appFileName+".xml";
		}
		else
			tPath = path+File.separator+fileName+".xml";


		ProjectManager.getInstance().getModelBrowser().writeComponentAppXMLModel(tPath, um);
		//		int i = fileName.lastIndexOf(".xml");
		//		String viewFileName = fileName.substring(0,i)+"_view.xml";
		//		 tPath = path+File.separator+viewFileName;
		//		 ProjectManager.getInstance().getModelBrowser().writeComponentViewXMLModel(tPath, um);
	}

	public void writeExportSub(UMLTreeParentModel um,String path){
		String fileName = um.getName();
		//String path = um.getFilterPath();
		String tPath = path+File.separator+fileName+".xml";


		ProjectManager.getInstance().getModelBrowser().writeComponentXMLModel(tPath, um);
		if(monitor!=null){
			monitor.subTask(tPath);
			monitor.worked(INCREMENT);
		}
		String viewFileName = fileName+"_view.xml";
		tPath = path+File.separator+viewFileName;
		ProjectManager.getInstance().getModelBrowser().writeComponentViewXMLModel(tPath, um);
		if(monitor!=null){
			monitor.subTask(tPath);
			monitor.worked(INCREMENT);
		}
	}


	public String writeExportComponentXML(UMLTreeParentModel um,StringBuffer sb){
		UMLModel ur = (UMLModel)um.getRefModel();
		if(ur instanceof ComponentModel){
			ComponentModel cm = (ComponentModel)ur;
			sb.append("<composite_component_profile>\n");
			sb.append("		<id>"+cm.getId()+"</id>\n");
			sb.append("		<name>"+um.getName()+"</name>\n");
			sb.append("		<version>"+cm.getVersion()+"</version>\n");
			sb.append("		<description>"+cm.getDesc()+"</description>\n");
			sb.append("		<execution_environment>\n");
			sb.append("			<os name=\""+cm.getOs()+"\" version=\""+cm.getOsVersion()+"\"/>\n");
			sb.append("			<cpu>"+cm.getCpu()+"</cpu>\n");
			sb.append("			<library_type>"+cm.getLibType()+"</library_type>\n");
			sb.append("			<impl_language>"+cm.getImplLang()+"</impl_language>\n");
			sb.append("			<compiler>"+cm.getCompiler()+"</compiler>\n");
			sb.append("		</execution_environment>\n");
			sb.append("		\n");
			java.util.ArrayList list =  ur.getTags();
			if(list.size()>0){
				sb.append("		<properties>\n");
				for(int i=0;i<list.size();i++){
					DetailPropertyTableItem dpt = (DetailPropertyTableItem)list.get(i);
					sb.append("			<property name=\""+dpt.tapName+"\" type=\""+dpt.sTagType+"\" >"+dpt.desc+"<property>\n");
				}
				sb.append("		</properties>\n");
				sb.append("		\n");
			}


			sb.append("		<subcomponents>\n");
			N3EditorDiagramModel nd = cm.getN3EditorDiagramModel();

			ComponentModel main = null;
			if(nd==null){
				for(int i=0;i<um.getChildren().length;i++){
					UMLTreeModel ut = (UMLTreeModel)um.getChildren()[i];
					if(ut.getRefModel() instanceof N3EditorDiagramModel){
						nd = (N3EditorDiagramModel)ut.getRefModel();
						break;
					}
				}
			}
			for(int i=0;i<nd.getChildren().size();i++){
				Object obj = nd.getChildren().get(i);
				if(obj instanceof ComponentModel){

					ComponentModel sub = (ComponentModel)obj;
					if(sub.getUMLDataModel().getId().equals(cm.getUMLDataModel().getId())){
						main = sub;
						continue;
					}
					sb.append("			<subcomponent>\n");
					sb.append("				<name>"+sub.getName()+"</name>\n");
					sb.append("			<id>"+sub.getId()+"</id>\n");
					sb.append("			<version>"+sub.getVersion()+"</version>\n");

					String st1 = sub.getStereotype();
					st1 = st1.replaceAll("<", "");
					st1 = st1.replaceAll(">", "");

					sb.append("			<type>"+st1+"</type>\n");
					sb.append("			<reference>"+sub.getName()+".xml"+"</reference>\n");
					//    	    		sb.append("				<subcomponentview>"+sub.getSize().width+","+sub.getSize().height+","+sub.getLocation().x+","+sub.getLocation().y
					//    	    				+","+sub.getBackGroundColor().getRed()+","+sub.getBackGroundColor().getGreen()+","+sub.getBackGroundColor().getBlue()+"</subcomponentview>\n");
					sb.append("			</subcomponent>\n");
				}
			}
			sb.append("		</subcomponents>\n");
			sb.append("		\n");
			sb.append("		<export_ports>\n");

			for(int i=0;i<main.getPortManager().getPorts().size();i++){
				PortModel pm1 = (PortModel)main.getPortManager().getPorts().get(i);
				PortModel linkPort = null;
				
				for (int i1 = 0; i1 < pm1.getSourceConnections().size(); i1++) {
					LineModel li = (LineModel)pm1.getSourceConnections().get(i1);
					if(li.getTarget() instanceof PortModel){
						linkPort = (PortModel)li.getTarget() ;
						ComponentModel cm1 =(ComponentModel)linkPort.getPortContainerModel();
//						break;
						sb.append("		<export_port>\n");
						if(pm1 instanceof MethodOutputPortModel){
							sb.append("			<component_name>"+cm1.getName()+"</component_name>\n");
							if(linkPort instanceof MonitoringMethodPortModel){
								sb.append("			<port_name>RM_MonitoringMethodPort</port_name>\n");
							}
							else if(linkPort instanceof LifecycleMethodPortModel){
								sb.append("			<port_name>RM_LifecycleMethodPort</port_name>\n");
								//				    	sb.append("			<source component_name=\""+sourceC.getName()+"\" port_name=\"RM_LifecycleMethodPort\"/>\n");
							}
							else
								sb.append("			<port_name>"+linkPort.getName()+"</port_name>\n");

							sb.append("			<port_type>service</port_type>\n");
							sb.append("			<port_dir>provided</port_dir>\n");
							sb.append("			<export_port_name>"+pm1.getName()+"</export_port_name>\n");


						}
						else if(pm1 instanceof MethodInputPortModel){
							sb.append("			<component_name>"+cm1.getName()+"</component_name>\n");
							sb.append("			<port_name>"+linkPort.getName()+"</port_name>\n");
							sb.append("			<port_type>service</port_type>\n");
							sb.append("			<port_dir>required</port_dir>\n");
							sb.append("			<export_port_name>"+pm1.getName()+"</export_port_name>\n");
						}
						else if(pm1 instanceof DataInputPortModel){
							sb.append("			<component_name>"+cm1.getName()+"</component_name>\n");
							sb.append("			<port_name>"+linkPort.getName()+"</port_name>\n");
							sb.append("			<port_type>data</port_type>\n");
							sb.append("			<port_dir>input</port_dir>\n");
							sb.append("			<export_port_name>"+pm1.getName()+"</export_port_name>\n");
						}
						else if(pm1 instanceof DataOutputPortModel){
							sb.append("			<component_name>"+cm1.getName()+"</component_name>\n");
							sb.append("			<port_name>"+linkPort.getName()+"</port_name>\n");
							sb.append("			<port_type>data</port_type>\n");
							sb.append("			<port_dir>output</port_dir>\n");
							sb.append("			<export_port_name>"+pm1.getName()+"</export_port_name>\n");
						}
						else if(pm1 instanceof EventInputPortModel){
							sb.append("			<component_name>"+cm1.getName()+"</component_name>\n");
							sb.append("			<port_name>"+linkPort.getName()+"</port_name>\n");
							sb.append("			<port_type>event</port_type>\n");
							sb.append("			<port_dir>input</port_dir>\n");
							sb.append("			<export_port_name>"+pm1.getName()+"</export_port_name>\n");
						}
						else if(pm1 instanceof EventOutputPortModel){
							sb.append("			<component_name>"+cm1.getName()+"</component_name>\n");
							sb.append("			<port_name>"+linkPort.getName()+"</port_name>\n");
							sb.append("			<port_type>event</port_type>\n");
							sb.append("			<port_dir>output</port_dir>\n");
							sb.append("			<export_port_name>"+pm1.getName()+"</export_port_name>\n");
						}
						//				sb.append("				<export_portview>"+pm1.getLocation().x+","+pm1.getLocation().y+","+pm1.getSize().width+","+pm1.getSize().height+","+pm1.getPtDifference().width+","+pm1.getPtDifference().height+","+pm1.getAttachElementLabelModel().getSize().width+","+pm1.getAttachElementLabelModel().getSize().height+"</export_portview>\n");
						sb.append("		</export_port>\n");
						sb.append("		\n");
					}
					//		            li.gets
				}
				for (int i1 = 0; i1 < pm1.getTargetConnections().size(); i1++) {
					LineModel li = (LineModel)pm1.getTargetConnections().get(i1);
					if(li.getSource() instanceof PortModel){
						linkPort = (PortModel)li.getSource() ;
						ComponentModel cm1 =(ComponentModel)linkPort.getPortContainerModel();
//						break;
						sb.append("		<export_port>\n");
						if(pm1 instanceof MethodOutputPortModel){
							sb.append("			<component_name>"+cm1.getName()+"</component_name>\n");
							if(linkPort instanceof MonitoringMethodPortModel){
								sb.append("			<port_name>RM_MonitoringMethodPort</port_name>\n");
							}
							else if(linkPort instanceof LifecycleMethodPortModel){
								sb.append("			<port_name>RM_LifecycleMethodPort</port_name>\n");
								//				    	sb.append("			<source component_name=\""+sourceC.getName()+"\" port_name=\"RM_LifecycleMethodPort\"/>\n");
							}
							else
								sb.append("			<port_name>"+linkPort.getName()+"</port_name>\n");

							sb.append("			<port_type>service</port_type>\n");
							sb.append("			<port_dir>provided</port_dir>\n");
							sb.append("			<export_port_name>"+pm1.getName()+"</export_port_name>\n");


						}
						else if(pm1 instanceof MethodInputPortModel){
							sb.append("			<component_name>"+cm1.getName()+"</component_name>\n");
							sb.append("			<port_name>"+linkPort.getName()+"</port_name>\n");
							sb.append("			<port_type>service</port_type>\n");
							sb.append("			<port_dir>required</port_dir>\n");
							sb.append("			<export_port_name>"+pm1.getName()+"</export_port_name>\n");
						}
						else if(pm1 instanceof DataInputPortModel){
							sb.append("			<component_name>"+cm1.getName()+"</component_name>\n");
							sb.append("			<port_name>"+linkPort.getName()+"</port_name>\n");
							sb.append("			<port_type>data</port_type>\n");
							sb.append("			<port_dir>input</port_dir>\n");
							sb.append("			<export_port_name>"+pm1.getName()+"</export_port_name>\n");
						}
						else if(pm1 instanceof DataOutputPortModel){
							sb.append("			<component_name>"+cm1.getName()+"</component_name>\n");
							sb.append("			<port_name>"+linkPort.getName()+"</port_name>\n");
							sb.append("			<port_type>data</port_type>\n");
							sb.append("			<port_dir>output</port_dir>\n");
							sb.append("			<export_port_name>"+pm1.getName()+"</export_port_name>\n");
						}
						else if(pm1 instanceof EventInputPortModel){
							sb.append("			<component_name>"+cm1.getName()+"</component_name>\n");
							sb.append("			<port_name>"+linkPort.getName()+"</port_name>\n");
							sb.append("			<port_type>event</port_type>\n");
							sb.append("			<port_dir>input</port_dir>\n");
							sb.append("			<export_port_name>"+pm1.getName()+"</export_port_name>\n");
						}
						else if(pm1 instanceof EventOutputPortModel){
							sb.append("			<component_name>"+cm1.getName()+"</component_name>\n");
							sb.append("			<port_name>"+linkPort.getName()+"</port_name>\n");
							sb.append("			<port_type>event</port_type>\n");
							sb.append("			<port_dir>output</port_dir>\n");
							sb.append("			<export_port_name>"+pm1.getName()+"</export_port_name>\n");
						}
						//				sb.append("				<export_portview>"+pm1.getLocation().x+","+pm1.getLocation().y+","+pm1.getSize().width+","+pm1.getSize().height+","+pm1.getPtDifference().width+","+pm1.getPtDifference().height+","+pm1.getAttachElementLabelModel().getSize().width+","+pm1.getAttachElementLabelModel().getSize().height+"</export_portview>\n");
						sb.append("		</export_port>\n");
						sb.append("		\n");
					}
					//		            li.gets
				}

//				if(linkPort==null)
//					continue;
//				ComponentModel cm1 =(ComponentModel)linkPort.getPortContainerModel();
				

			}
			sb.append("		</export_ports>\n");
			sb.append("		\n");
			sb.append("		<port_connections>\n");
			for(int i=0;i<nd.getChildren().size();i++){
				Object obj = nd.getChildren().get(i);
				if(obj instanceof PortModel){

					PortModel sub = (PortModel)obj;
					if(sub.getPortContainerModel().getUMLDataModel().getId().equals(cm.getUMLDataModel().getId())){

						continue;
					}
					for (int i1 = 0; i1 < sub.getSourceConnections().size(); i1++) {
						LineModel li = (LineModel)sub.getSourceConnections().get(i1);
						System.out.println("=====================================>"+sub.getName());
						if(li.getTarget() instanceof PortModel){
							PortModel source = (PortModel)li.getTarget();
							if(source.getPortContainerModel().getUMLDataModel().getId().equals(cm.getUMLDataModel().getId())){

								continue;
							}
							UMLModel sourceC = source.getPortContainerModel();
							//    		            	sb.append("		<port_connection>\n");
							if(source instanceof MethodOutputPortModel){
								sb.append("			<port_connection port_type=\"service\">\n");
								if(sub instanceof MonitoringMethodPortModel){
									sb.append("			<source component_name=\""+sub.getPortContainerModel().getName()+"\" port_name=\"RM_MonitoringMethodPort\"/>\n");
								}
								else if(sub instanceof LifecycleMethodPortModel){
									sb.append("			<source component_name=\""+sub.getPortContainerModel().getName()+"\" port_name=\"RM_LifecycleMethodPort\"/>\n");
								}
								else
									sb.append("			<source component_name=\""+sub.getPortContainerModel().getName()+"\" port_name=\""+sub.getName()+"\"/>\n");

								if(source instanceof MonitoringMethodPortModel){
									sb.append("			<target component_name=\""+sourceC.getName()+"\" port_name=\"RM_MonitoringMethodPort\"/>\n");
								}
								else if(source instanceof LifecycleMethodPortModel){
									sb.append("			<target component_name=\""+sourceC.getName()+"\" port_name=\"RM_LifecycleMethodPort\"/>\n");
								}
								else
									sb.append("			<target component_name=\""+sourceC.getName()+"\" port_name=\""+source.getName()+"\"/>\n");

							}
							else if(source instanceof MethodInputPortModel){
								sb.append("			<port_connection port_type=\"service\">\n");
								if(sub instanceof MonitoringMethodPortModel){
									sb.append("			<source component_name=\""+sub.getPortContainerModel().getName()+"\" port_name=\"RM_MonitoringMethodPort\"/>\n");
								}
								else if(sub instanceof LifecycleMethodPortModel){
									sb.append("			<source component_name=\""+sub.getPortContainerModel().getName()+"\" port_name=\"RM_LifecycleMethodPort\"/>\n");
								}
								else
									sb.append("			<source component_name=\""+sub.getPortContainerModel().getName()+"\" port_name=\""+sub.getName()+"\"/>\n");


								sb.append("			<target component_name=\""+sourceC.getName()+"\" port_name=\""+source.getName()+"\"/>\n");

							}
							else if(source instanceof DataInputPortModel){
								sb.append("			<port_connection port_type=\"data\">\n");
								sb.append("			<source component_name=\""+sub.getPortContainerModel().getName()+"\" port_name=\""+sub.getName()+"\"/>\n");
								sb.append("			<target component_name=\""+sourceC.getName()+"\" port_name=\""+source.getName()+"\"/>\n");
							}
							else if(source instanceof DataOutputPortModel){
								sb.append("			<port_connection port_type=\"data\">\n");
								sb.append("			<source component_name=\""+sub.getPortContainerModel().getName()+"\" port_name=\""+sub.getName()+"\"/>\n");
								sb.append("			<target component_name=\""+sourceC.getName()+"\" port_name=\""+source.getName()+"\"/>\n");
							}
							else if(source instanceof EventInputPortModel){
								sb.append("			<port_connection port_type=\"event\">\n");
								sb.append("			<source component_name=\""+sub.getPortContainerModel().getName()+"\" port_name=\""+sub.getName()+"\"/>\n");
								sb.append("			<target component_name=\""+sourceC.getName()+"\" port_name=\""+source.getName()+"\"/>\n");
							}
							else if(source instanceof EventOutputPortModel){
								sb.append("			<port_connection port_type=\"event\">\n");
								sb.append("			<source component_name=\""+sub.getPortContainerModel().getName()+"\" port_name=\""+sub.getName()+"\"/>\n");
								sb.append("			<target component_name=\""+sourceC.getName()+"\" port_name=\""+source.getName()+"\"/>\n");
							}



							sb.append("		</port_connection>\n");
							sb.append("		\n");
						}

					}

				}
			}
			sb.append("		</port_connections>\n");
			sb.append("</composite_component_profile>\n");

		}
		return sb.toString();



	}



	public String writeExportComponentViewXML(UMLTreeParentModel um,StringBuffer sb){
		UMLModel ur = (UMLModel)um.getRefModel();
		if(ur instanceof ComponentModel){
			ComponentModel cm = (ComponentModel)ur;

			N3EditorDiagramModel nd = cm.getN3EditorDiagramModel();
			ComponentModel main = null;
			if(nd==null){
				for(int i=0;i<um.getChildren().length;i++){
					UMLTreeModel ut = (UMLTreeModel)um.getChildren()[i];
					if(ut.getRefModel() instanceof N3EditorDiagramModel){
						nd = (N3EditorDiagramModel)ut.getRefModel();
						break;
					}
				}
			}
			for(int i=0;i<nd.getChildren().size();i++){
				Object obj = nd.getChildren().get(i);
				if(obj instanceof ComponentModel){

					ComponentModel sub = (ComponentModel)obj;
					//    				String path = sub.getPackage();
					if(sub.getUMLDataModel().getId().equals(cm.getUMLDataModel().getId())){
						main = sub;
						sb.append("<composite_component_view_profile>\n");
						sb.append("		<name>"+um.getName()+"</name>\n");
						sb.append("		<viewInfo>"+main.getSize().width+","+main.getSize().height+","+main.getLocation().x+","+main.getLocation().y
								+","+main.getBackGroundColor().getRed()+","+main.getBackGroundColor().getGreen()+","+main.getBackGroundColor().getBlue()+"</viewInfo>\n");
						sb.append("		\n");
						sb.append("		<subcomponents>\n");
						continue;
					}
					sb.append("			<subcomponent>\n");
					sb.append("				<name>"+sub.getName()+"</name>\n");
					sb.append("				<subcomponentview>"+sub.getSize().width+","+sub.getSize().height+","+sub.getLocation().x+","+sub.getLocation().y
							+","+sub.getBackGroundColor().getRed()+","+sub.getBackGroundColor().getGreen()+","+sub.getBackGroundColor().getBlue()+"</subcomponentview>\n");

					for(int i1=0;i1<sub.getPortManager().getPorts().size();i1++){
						sb.append("		<port>\n");
						PortModel pm1 = (PortModel)sub.getPortManager().getPorts().get(i1);
						if(pm1 instanceof MonitoringMethodPortModel){
							sb.append("			<component_name>"+sub.getName()+"</component_name>\n");
							sb.append("			<port_name>"+pm1.getName()+"</port_name>\n");
							sb.append("			<port_type>monitoring</port_type>\n");
							sb.append("			<port_dir>provided</port_dir>\n");
						}
						else if(pm1 instanceof LifecycleMethodPortModel){
							sb.append("			<component_name>"+sub.getName()+"</component_name>\n");
							sb.append("			<port_name>"+pm1.getName()+"</port_name>\n");
							sb.append("			<port_type>lifecycle</port_type>\n");
							sb.append("			<port_dir>provided</port_dir>\n");
						}
						else if(pm1 instanceof MethodOutputPortModel){
							sb.append("			<component_name>"+sub.getName()+"</component_name>\n");
							sb.append("			<port_name>"+pm1.getName()+"</port_name>\n");
							sb.append("			<port_type>method</port_type>\n");
							sb.append("			<port_dir>provided</port_dir>\n");
						}
						else if(pm1 instanceof MethodInputPortModel){
							sb.append("			<component_name>"+sub.getName()+"</component_name>\n");
							sb.append("			<port_name>"+pm1.getName()+"</port_name>\n");
							sb.append("			<port_type>method</port_type>\n");
							sb.append("			<port_dir>required</port_dir>\n");
						}
						else if(pm1 instanceof DataInputPortModel){
							sb.append("			<component_name>"+sub.getName()+"</component_name>\n");
							sb.append("			<port_name>"+pm1.getName()+"</port_name>\n");
							sb.append("			<port_type>data</port_type>\n");
							sb.append("			<port_dir>input</port_dir>\n");
						}
						else if(pm1 instanceof DataOutputPortModel){
							sb.append("			<component_name>"+sub.getName()+"</component_name>\n");
							sb.append("			<port_name>"+pm1.getName()+"</port_name>\n");
							sb.append("			<port_type>data</port_type>\n");
							sb.append("			<port_dir>output</port_dir>\n");
						}
						else if(pm1 instanceof EventInputPortModel){
							sb.append("			<component_name>"+sub.getName()+"</component_name>\n");
							sb.append("			<port_name>"+pm1.getName()+"</port_name>\n");
							sb.append("			<port_type>event</port_type>\n");
							sb.append("			<port_dir>input</port_dir>\n");
						}
						else if(pm1 instanceof EventOutputPortModel){
							sb.append("			<component_name>"+sub.getName()+"</component_name>\n");
							sb.append("			<port_name>"+pm1.getName()+"</port_name>\n");
							sb.append("			<port_type>event</port_type>\n");
							sb.append("			<port_dir>output</port_dir>\n");
						}
						sb.append("				<portview>"+pm1.getLocation().x+","+pm1.getLocation().y+","+pm1.getPtDifference().width+","+pm1.getPtDifference().height+","+pm1.getAttachElementLabelModel().getSize().width+","+pm1.getAttachElementLabelModel().getSize().height+"</portview>\n");
						sb.append("		</port>\n");
						sb.append("		\n");
					}
					sb.append("			</subcomponent>\n");
				}
			}
			sb.append("		</subcomponents>\n");
			sb.append("		\n");
			sb.append("		<export_ports>\n");

			for(int i=0;i<main.getPortManager().getPorts().size();i++){
				PortModel pm1 = (PortModel)main.getPortManager().getPorts().get(i);
				PortModel linkPort = null;
				for (int i1 = 0; i1 < pm1.getSourceConnections().size(); i1++) {
					LineModel li = (LineModel)pm1.getSourceConnections().get(i1);
					if(li.getTarget() instanceof PortModel){
						linkPort = (PortModel)li.getTarget() ;
						break;
					}
				}
				for (int i1 = 0; i1 < pm1.getTargetConnections().size(); i1++) {
					LineModel li = (LineModel)pm1.getTargetConnections().get(i1);
					if(li.getSource() instanceof PortModel){
						linkPort = (PortModel)li.getSource() ;
						break;
					}
					//		            li.gets
				}

				if(linkPort==null)
					continue;
				ComponentModel cm1 =(ComponentModel)linkPort.getPortContainerModel();
				sb.append("		<export_port>\n");
				if(pm1 instanceof MethodOutputPortModel){
					sb.append("			<component_name>"+cm1.getName()+"</component_name>\n");
					sb.append("			<port_name>"+linkPort.getName()+"</port_name>\n");
					sb.append("			<port_type>method</port_type>\n");
					sb.append("			<port_dir>provided</port_dir>\n");
					sb.append("			<export_port_name>"+pm1.getName()+"</export_port_name>\n");


				}
				else if(pm1 instanceof MethodInputPortModel){
					sb.append("			<component_name>"+cm1.getName()+"</component_name>\n");
					sb.append("			<port_name>"+linkPort.getName()+"</port_name>\n");
					sb.append("			<port_type>method</port_type>\n");
					sb.append("			<port_dir>required</port_dir>\n");
					sb.append("			<export_port_name>"+pm1.getName()+"</export_port_name>\n");
				}
				else if(pm1 instanceof DataInputPortModel){
					sb.append("			<component_name>"+cm1.getName()+"</component_name>\n");
					sb.append("			<port_name>"+linkPort.getName()+"</port_name>\n");
					sb.append("			<port_type>data</port_type>\n");
					sb.append("			<port_dir>input</port_dir>\n");
					sb.append("			<export_port_name>"+pm1.getName()+"</export_port_name>\n");
				}
				else if(pm1 instanceof DataOutputPortModel){
					sb.append("			<component_name>"+cm1.getName()+"</component_name>\n");
					sb.append("			<port_name>"+linkPort.getName()+"</port_name>\n");
					sb.append("			<port_type>data</port_type>\n");
					sb.append("			<port_dir>output</port_dir>\n");
					sb.append("			<export_port_name>"+pm1.getName()+"</export_port_name>\n");
				}
				else if(pm1 instanceof EventInputPortModel){
					sb.append("			<component_name>"+cm1.getName()+"</component_name>\n");
					sb.append("			<port_name>"+linkPort.getName()+"</port_name>\n");
					sb.append("			<port_type>event</port_type>\n");
					sb.append("			<port_dir>input</port_dir>\n");
					sb.append("			<export_port_name>"+pm1.getName()+"</export_port_name>\n");
				}
				else if(pm1 instanceof EventOutputPortModel){
					sb.append("			<component_name>"+cm1.getName()+"</component_name>\n");
					sb.append("			<port_name>"+linkPort.getName()+"</port_name>\n");
					sb.append("			<port_type>event</port_type>\n");
					sb.append("			<port_dir>output</port_dir>\n");
					sb.append("			<export_port_name>"+pm1.getName()+"</export_port_name>\n");
				}
				sb.append("				<export_portview>"+pm1.getLocation().x+","+pm1.getLocation().y+","+pm1.getPtDifference().width+","+pm1.getPtDifference().height+","+pm1.getAttachElementLabelModel().getSize().width+","+pm1.getAttachElementLabelModel().getSize().height+"</export_portview>\n");
				sb.append("		</export_port>\n");
				sb.append("		\n");

			}
			sb.append("		</export_ports>\n");
			sb.append("		\n");
			sb.append("		<port_connections>\n");
			for(int i=0;i<nd.getChildren().size();i++){
				Object obj = nd.getChildren().get(i);
				if(obj instanceof PortModel){

					PortModel sub = (PortModel)obj;
					//    				if(sub.getPortContainerModel().getUMLDataModel().getId().equals(cm.getUMLDataModel().getId())){
					//    				
					//    					continue;
					//    				}
					for (int i1 = 0; i1 < sub.getSourceConnections().size(); i1++) {
						LineModel li = (LineModel)sub.getSourceConnections().get(i1);
						System.out.println("=====================================>"+sub.getName());
						if(li.getTarget() instanceof PortModel){
							PortModel source = (PortModel)li.getTarget();
							UMLModel sourceC = source.getPortContainerModel();
							sb.append("		<port_connection>\n");
							if(source instanceof MethodOutputPortModel){
								//   	    					sb.append("			<port_connection port_type=\"method\"/>\n");
								sb.append("			<source component_name=\""+sub.getPortContainerModel().getName()+"\" port_name=\""+sub.getName()+"\"/>\n");
								sb.append("			<target component_name=\""+sourceC.getName()+"\" port_name=\""+source.getName()+"\"/>\n");


							}
							else if(source instanceof MethodInputPortModel){
								//    	    					sb.append("			<port_connection port_type=\"method\"/>\n");
								sb.append("			<source component_name=\""+sub.getPortContainerModel().getName()+"\" port_name=\""+sub.getName()+"\"/>\n");
								sb.append("			<target component_name=\""+sourceC.getName()+"\" port_name=\""+source.getName()+"\"/>\n");

							}
							else if(source instanceof DataInputPortModel){
								//    	    					sb.append("			<port_connection port_type=\"data\"/>\n");
								sb.append("			<source component_name=\""+sub.getPortContainerModel().getName()+"\" port_name=\""+sub.getName()+"\"/>\n");
								sb.append("			<target component_name=\""+sourceC.getName()+"\" port_name=\""+source.getName()+"\"/>\n");
							}
							else if(source instanceof DataOutputPortModel){
								//    	    					sb.append("			<port_connection port_type=\"data\"/>\n");
								sb.append("			<source component_name=\""+sub.getPortContainerModel().getName()+"\" port_name=\""+sub.getName()+"\"/>\n");
								sb.append("			<target component_name=\""+sourceC.getName()+"\" port_name=\""+source.getName()+"\"/>\n");
							}
							else if(source instanceof EventInputPortModel){
								//    	    					sb.append("			<port_connection port_type=\"event\"/>\n");
								sb.append("			<source component_name=\""+sub.getPortContainerModel().getName()+"\" port_name=\""+sub.getName()+"\"/>\n");
								sb.append("			<target component_name=\""+sourceC.getName()+"\" port_name=\""+source.getName()+"\"/>\n");
							}
							else if(source instanceof EventOutputPortModel){
								//    	    					sb.append("			<port_connection port_type=\"event\"/>\n");
								sb.append("			<source component_name=\""+sub.getPortContainerModel().getName()+"\" port_name=\""+sub.getName()+"\"/>\n");
								sb.append("			<target component_name=\""+sourceC.getName()+"\" port_name=\""+source.getName()+"\"/>\n");
							}

							for(int i2=0;i2<li.getBendpoints().size();i2++){
								LineBendpointModel lbm =  (LineBendpointModel)li.getBendpoints().get(i2);
								sb.append("			<bend_point value=\""+lbm.writeModel()+"\"/>\n");

							}

							sb.append("		</port_connection>\n");
							sb.append("		\n");
						}
						//    		            li.gets
					}

				}
			}
			sb.append("		</port_connections>\n");
			sb.append("			<notes>\n");
			for(int i=0;i<nd.getChildren().size();i++){
				Object obj = nd.getChildren().get(i);
				if(obj instanceof UMLNoteModel){
					UMLNoteModel uNm = (UMLNoteModel)obj;
					sb.append("			<note>\n");
					sb.append("				<text><![CDATA["+uNm.getLabelContents());
					sb.append("]]></text>\n");
					sb.append("			<noteId>![CDATA["+uNm.getView_ID()+"]]</noteId>\n");
					sb.append("		<noteViewInfo>"+uNm.getSize().width+","+uNm.getSize().height+","+uNm.getLocation().x+","+uNm.getLocation().y
							+","+uNm.getBackGroundColor().getRed()+","+uNm.getBackGroundColor().getGreen()+","+uNm.getBackGroundColor().getBlue()+"</noteViewInfo>\n");
					sb.append("			</note>\n");
				}
			}
			sb.append("			</notes>\n");
			sb.append("		<note_connections>\n");
			for(int i=0;i<nd.getChildren().size();i++){
				Object obj = nd.getChildren().get(i);
				if(obj instanceof UMLNoteModel){
					UMLNoteModel uNm = (UMLNoteModel)obj;
					for(int i1=0;i1<uNm.getSourceConnections().size();i1++){
						LineModel li = (LineModel)uNm.getSourceConnections().get(i1);
						UMLModel ump = li.getTarget();
						if(ump instanceof ComponentModel){
							ComponentModel sub =(ComponentModel)ump;
							sb.append("		<note_target_connection>\n");
							sb.append("			<source component_name=\""+sub.getName()+"\" port_name=\"\"  type=\"component\"/>\n");
							sb.append("			<targetNote>![CDATA["+uNm.getView_ID()+"]]</targetNote>\n");
							for(int i2=0;i2<li.getBendpoints().size();i2++){
								LineBendpointModel lbm =  (LineBendpointModel)li.getBendpoints().get(i2);
								sb.append("			<bend_point value=\""+lbm.writeModel()+"\"/>\n");

							}
							sb.append("		</note_target_connection>\n");
						}
						else if(ump instanceof PortModel){
							PortModel sub =(PortModel)ump;
							sb.append("		<note_target_connection>\n");
							sb.append("			<source component_name=\""+sub.getPortContainerModel().getName()+"\" port_name=\""+sub.getName()+"\"  type=\"port\"/>\n");
							sb.append("			<targetNote>![CDATA["+uNm.getView_ID()+"]]</targetNote>\n");
							for(int i2=0;i2<li.getBendpoints().size();i2++){
								LineBendpointModel lbm =  (LineBendpointModel)li.getBendpoints().get(i2);
								sb.append("			<bend_point value=\""+lbm.writeModel()+"\"/>\n");

							}
							sb.append("		</note_target_connection>\n");
						}


					}

					for(int i1=0;i1<uNm.getTargetConnections().size();i1++){
						LineModel li = (LineModel)uNm.getTargetConnections().get(i1);
						UMLModel ump = li.getSource();
						if(ump instanceof ComponentModel){
							ComponentModel sub =(ComponentModel)ump;
							sb.append("		<note_source_connection>\n");
							sb.append("			<sourceNote>![CDATA["+uNm.getView_ID()+"]]</sourceNote>\n");
							sb.append("			<target component_name=\""+sub.getName()+"\" port_name=\"\"  type=\"component\"/>\n");
							for(int i2=0;i2<li.getBendpoints().size();i2++){
								LineBendpointModel lbm =  (LineBendpointModel)li.getBendpoints().get(i2);
								sb.append("			<bend_point value=\""+lbm.writeModel()+"\"/>\n");

							}
							sb.append("		</note_source_connection>\n");
						}
						else if(ump instanceof PortModel){
							PortModel sub =(PortModel)ump;
							sb.append("		<note_source_connection>\n");
							sb.append("			<sourceNote>![CDATA["+uNm.getView_ID()+"]]</sourceNote>\n");
							sb.append("			<target component_name=\""+sub.getPortContainerModel().getName()+"\" port_name=\""+sub.getName()+"\"  type=\"port\"/>\n");
							for(int i2=0;i2<li.getBendpoints().size();i2++){
								LineBendpointModel lbm =  (LineBendpointModel)li.getBendpoints().get(i2);
								sb.append("			<bend_point value=\""+lbm.writeModel()+"\"/>\n");

							}
							sb.append("		</note_source_connection>\n");
						}


					}

				}
			}
			sb.append("		</note_connections>\n");
			sb.append("</composite_component_view_profile>\n");

		}
		return sb.toString();



	}


	public String writeExportAppComponentXML(UMLTreeParentModel um,StringBuffer sb){
		UMLModel ur = (UMLModel)um.getRefModel();
		if(ur instanceof FinalPackageModel){
			FinalPackageModel cm = (FinalPackageModel)ur;
			OPRoSManifest oPRoSManifest = null;
			if(NetManager.getInstance().isJar){
				 um.oPRoSManifest = new OPRoSManifest();
				 um.oPRoSManifest.Archive_name = cm.getName();
			}
			//111026 SDM S - 어플리케이션 저작권 정보 등 수정
			sb.append("<application_profile>\n");
			sb.append("		<id>"+cm.getId()+"</id>\n");
			sb.append("		<name>"+um.getName()+"</name>\n");
			sb.append("		<version>"+ProjectManager.getInstance().getAppVersion()+"</version>\n");
			sb.append("		<description>"+cm.getDesc()+"</description>\n");
			sb.append("		<copyright>\n");
			sb.append("			<license_policy>"+ProjectManager.getInstance().getAppLicense()+"</license_policy>\n");
			sb.append("			<company>\n");
			sb.append("				<name>"+ProjectManager.getInstance().getAppName()+"</name>\n");
			sb.append("				<phone>"+ProjectManager.getInstance().getAppPhone()+"</phone>\n");
			sb.append("				<address>"+ProjectManager.getInstance().getAppAddress()+"</address>\n");
			sb.append("				<homepage>"+ProjectManager.getInstance().getAppHomepage()+"</homepage>\n");
			sb.append("			</company>\n");
			sb.append("		</copyright>\n");
			sb.append("		\n");
			sb.append("		<subcomponents>\n");
			//111026 SDM E - 어플리케이션 저작권 정보 등 수정			
			N3EditorDiagramModel nd = cm.getN3EditorDiagramModel();
			ComponentModel main = null;
			for(int i=0;i<nd.getChildren().size();i++){
				Object obj = nd.getChildren().get(i);
				if(obj instanceof ComponentModel){

					ComponentModel sub = (ComponentModel)obj;
					if(sub.getUMLDataModel().getId().equals(cm.getUMLDataModel().getId())){
						main = sub;
						continue;
					}
					if(NetManager.getInstance().isJar && um.oPRoSManifest!=null){
//						OPRoSManifest oPRoSManifest = new OPRoSManifest();
						if(!um.oPRoSManifest.Archive_Elements.contains(sub.getName()))
							um.oPRoSManifest.Archive_Elements.add(sub.getName());
					}
					sb.append("			<subcomponent>\n");
					sb.append("			<node_id>"+sub.getNode()+"</node_id>\n");
					sb.append("			<name>"+sub.getName()+"</name>\n");
					sb.append("			<id>"+sub.getId()+"</id>\n");
					sb.append("			<version>"+sub.getVersion()+"</version>\n");

					String st1 = sub.getStereotype();
					st1 = st1.replaceAll("<", "");
					st1 = st1.replaceAll(">", "");

					sb.append("			<type>"+st1+"</type>\n");
					sb.append("			<reference>"+sub.getName()+".xml"+"</reference>\n");
					sb.append("			</subcomponent>\n");
				}
			}
			sb.append("		</subcomponents>\n");
			sb.append("		\n");

			sb.append("		\n");
			sb.append("		<port_connections>\n");
			for(int i=0;i<nd.getChildren().size();i++){
				Object obj = nd.getChildren().get(i);
				if(obj instanceof PortModel){

					PortModel sub = (PortModel)obj;

					for (int i1 = 0; i1 < sub.getTargetConnections().size(); i1++) {
						LineModel li = (LineModel)sub.getTargetConnections().get(i1);
						if(li.getSource() instanceof PortModel){
							PortModel source = (PortModel)li.getSource();
							UMLModel sourceC = source.getPortContainerModel();
							//sb.append("		<port_connection>\n");
							//    		            	if(source instanceof MonitoringMethodPortModel){
							//    	    					sb.append("			<port_connection port_type=\"method\">\n");
							////    	    					sb.append("			<source component_name=\""+sourceC.getName()+"\"port_name=\""+source.getName()+"\"/>\n");
							////    	        	    		sb.append("			<target component_name=\""+sub.getPortContainerModel().getName()+"\"port_name=\""+sub.getName()+"\"/>\n");
							//    	    					sb.append("			<source component_name=\""+sourceC.getName()+"\" port_name=\"RM_MonitoringMethodPort\"/>\n");
							//    	    					sb.append("			<target component_name=\""+sub.getPortContainerModel().getName()+"\" port_name=\""+sub.getName()+"\"/>\n");
							////    	        	    		sb.append("			<target component_name=\""+sourceC.getName()+"\" port_name=\""+source.getName()+"\"/>\n");
							//    	        	    		    	        	    		
							//    	    				}
							//    	    				else if(source instanceof LifecycleMethodPortModel){
							//    	    					sb.append("			<port_connection port_type=\"method\">\n");
							//    	    					sb.append("			<source component_name=\""+sourceC.getName()+"\" port_name=\""+source.getName()+"\"/>\n");
							//    	    					sb.append("			<target component_name=\""+sub.getPortContainerModel().getName()+"\" port_name=\""+sub.getName()+"\"/>\n");
							//    	        	    		
							//    	    				}
							//    	    				else 
							if(source instanceof MethodOutputPortModel){
								sb.append("			<port_connection port_type=\"service\">\n");
								//    	    					sb.append("			<source component_name=\""+sourceC.getName()+"\"port_name=\""+source.getName()+"\"/>\n");
								//    	        	    		sb.append("			<target component_name=\""+sub.getPortContainerModel().getName()+"\"port_name=\""+sub.getName()+"\"/>\n");
								if(source instanceof MonitoringMethodPortModel){
									sb.append("			<source component_name=\""+sourceC.getName()+"\" port_name=\"RM_MonitoringMethodPort\"/>\n");
								}
								else if(source instanceof LifecycleMethodPortModel){
									sb.append("			<source component_name=\""+sourceC.getName()+"\" port_name=\"RM_LifecycleMethodPort\"/>\n");
								}
								else
									sb.append("			<source component_name=\""+sourceC.getName()+"\" port_name=\""+source.getName()+"\"/>\n");

								if(sub instanceof MonitoringMethodPortModel){
									sb.append("			<target component_name=\""+sub.getPortContainerModel().getName()+"\" port_name=\"RM_MonitoringMethodPort\"/>\n");
								}
								else if(sub instanceof LifecycleMethodPortModel){
									sb.append("			<target component_name=\""+sub.getPortContainerModel().getName()+"\" port_name=\"RM_LifecycleMethodPort\"/>\n");
								}
								else 
									sb.append("			<target component_name=\""+sub.getPortContainerModel().getName()+"\" port_name=\""+sub.getName()+"\"/>\n");


							}
							else if(source instanceof MethodInputPortModel){
								sb.append("			<port_connection port_type=\"service\">\n");
								sb.append("			<source component_name=\""+sourceC.getName()+"\" port_name=\""+source.getName()+"\"/>\n");
								if(sub instanceof MonitoringMethodPortModel){
									sb.append("			<target component_name=\""+sub.getPortContainerModel().getName()+"\" port_name=\"RM_MonitoringMethodPort\"/>\n");
								}
								else if(sub instanceof LifecycleMethodPortModel){
									sb.append("			<target component_name=\""+sub.getPortContainerModel().getName()+"\" port_name=\"RM_LifecycleMethodPort\"/>\n");
								}
								else
									sb.append("			<target component_name=\""+sub.getPortContainerModel().getName()+"\" port_name=\""+sub.getName()+"\"/>\n");

							}
							else if(source instanceof DataInputPortModel){
								sb.append("			<port_connection port_type=\"data\">\n");
								sb.append("			<source component_name=\""+sourceC.getName()+"\" port_name=\""+source.getName()+"\"/>\n");
								sb.append("			<target component_name=\""+sub.getPortContainerModel().getName()+"\" port_name=\""+sub.getName()+"\"/>\n");
							}
							else if(source instanceof DataOutputPortModel){
								sb.append("			<port_connection port_type=\"data\">\n");
								sb.append("			<source component_name=\""+sourceC.getName()+"\" port_name=\""+source.getName()+"\"/>\n");
								sb.append("			<target component_name=\""+sub.getPortContainerModel().getName()+"\" port_name=\""+sub.getName()+"\"/>\n");
							}
							else if(source instanceof EventInputPortModel){
								sb.append("			<port_connection port_type=\"event\">\n");
								sb.append("			<source component_name=\""+sourceC.getName()+"\" port_name=\""+source.getName()+"\"/>\n");
								sb.append("			<target component_name=\""+sub.getPortContainerModel().getName()+"\" port_name=\""+sub.getName()+"\"/>\n");
							}
							else if(source instanceof EventOutputPortModel){
								sb.append("			<port_connection port_type=\"event\">\n");
								sb.append("			<source component_name=\""+sourceC.getName()+"\" port_name=\""+source.getName()+"\"/>\n");
								sb.append("			<target component_name=\""+sub.getPortContainerModel().getName()+"\" port_name=\""+sub.getName()+"\"/>\n");
							}
							sb.append("		</port_connection>\n");
							sb.append("		\n");
						}
						//    		            li.gets
					}

				}
			}
			sb.append("		</port_connections>\n");

			sb.append("</application_profile>\n");

		}
		return sb.toString();


	}


	public void saveLib(RootLibTreeModel rl){
		try {

			if(rl==null){
				rl = this.rtm;
				this.recurseSave(rl);
			}



		} catch (Exception e) {
			// TODO: handle exception
		}

	}
	// IJS 2010-08-12 4:17오후  라이브러리 이동 관련
	public void makeLib(File profile,File libFile){
		this.viewInfos.clear();
		this.compositeCmp.clear();
	}

	public void makeLib(RootLibTreeModel rl){
		try{
			String str = ProjectManager.getInstance().getSourceFolder();
			
			
			
			//				ProgressMonitorDialog progress =  new ProgressMonitorDialog(ProjectManager.getInstance().window.getShell());
			//				LoadLibProgress lp = new LoadLibProgress(true);
			//				lp.setRl(rl);
			//				progress.run(true, true, lp);
			if(rl==null){
				rl = this.rtm;
			}
			if(str!=null && !str.trim().equals("")){
				this.viewInfos.clear();
				this.compositeCmp.clear();
				File dirFile = new File(str);
				//				try{
				//					this.deleteModel(rl);
				//				}
				//				catch(Exception e1){
				//					e1.printStackTrace();
				//				}
				////ijs TarManager
				TarManager.getInstance().makeTempLib(null);
				dirFile = new File(strWorkDir + "templib_");	//111102 SDM - TEMP폴더 워크스페이스 안으로 이동
//				makeTempLib(null);
				boolean isRoot = true;
				this.recurse(dirFile, rl,isRoot);
				this.makeView();
				//					ProjectManager.getInstance().getModelBrowser().refresh(this.rtm);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}


	}

	public void makeLib2(RootPackageTreeModel rl){
		try{
			String str = ProjectManager.getInstance().getSourceFolder();
			//				ProgressMonitorDialog progress =  new ProgressMonitorDialog(ProjectManager.getInstance().window.getShell());
			//				LoadLibProgress lp = new LoadLibProgress(true);
			//				lp.setRl(rl);
			//				progress.run(true, true, lp);
			//			if(rl==null){
			//				rl = this.rtm;
			//			}
			//			else{
			//			
			//			}
			if(str!=null && !str.trim().equals("")){
				this.viewInfos.clear();
				this.compositeCmp.clear();
				//				File dirFile = new File(str);
				//				try{
				//					this.deleteModel(rl);
				//				}
				//				catch(Exception e1){
				//					e1.printStackTrace();
				//				}

				boolean isRoot = true;
				this.recurse(rl.dir, rl,isRoot);
				this.makeView();
				//					ProjectManager.getInstance().getModelBrowser().refresh(this.rtm);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}


	}
	
	//서동민
	public void makeCompEdit(RootCmpFnshTreeModel rl){
		try{
			String str = "D:\\etri\\runtime-EclipseApplication(1)";
			
			
			
			//				ProgressMonitorDialog progress =  new ProgressMonitorDialog(ProjectManager.getInstance().window.getShell());
			//				LoadLibProgress lp = new LoadLibProgress(true);
			//				lp.setRl(rl);
			//				progress.run(true, true, lp);
			if(rl==null){
				rl = this.rcefm;
			}
			if(str!=null && !str.trim().equals("")){
				this.viewInfos.clear();
				this.compositeCmp.clear();
				File dirFile = new File(str);
				//				try{
				//					this.deleteModel(rl);
				//				}
				//				catch(Exception e1){
				//					e1.printStackTrace();
				//				}

				boolean isRoot = true;
				this.recurseCompEdit(dirFile, rl,isRoot);
				this.makeView();
				//					ProjectManager.getInstance().getModelBrowser().refresh(this.rtm);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}


	}
	
	//서동민20110729
	public void makeCmpEdtFnsh(RootCmpFnshTreeModel rl){
		try{
			String str = "D:\\etri\\runtime-EclipseApplication(1)";
			
			
			
			//				ProgressMonitorDialog progress =  new ProgressMonitorDialog(ProjectManager.getInstance().window.getShell());
			//				LoadLibProgress lp = new LoadLibProgress(true);
			//				lp.setRl(rl);
			//				progress.run(true, true, lp);
			if(rl==null){
				rl = this.rcefm;
			}
			if(str!=null && !str.trim().equals("")){
				this.viewInfos.clear();
				this.compositeCmp.clear();
				File dirFile = new File(str);
				//				try{
				//					this.deleteModel(rl);
				//				}
				//				catch(Exception e1){
				//					e1.printStackTrace();
				//				}

				boolean isRoot = true;
				this.recurseCompEdit(dirFile, rl,isRoot);
				this.makeView();
				//					ProjectManager.getInstance().getModelBrowser().refresh(this.rtm);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}


	}
	
	public void makeApplication(){//Khg 2010.05.26 임포트 어플리케이션
		try{
			//String str = ProjectManager.getInstance().getSourceFolder();
			//				ProgressMonitorDialog progress =  new ProgressMonitorDialog(ProjectManager.getInstance().window.getShell());
			//				LoadLibProgress lp = new LoadLibProgress(true);
			//				lp.setRl(rl);
			//				progress.run(true, true, lp);
			viewInfos2.clear();
			
			UMLTreeParentModel treeObject = ProjectManager.getInstance().getModelBrowser().getRootTemplateTreeModel();
			//			if(str!=null && !str.trim().equals("")){
			//				this.viewInfos.clear();
			//				this.compositeCmp.clear();
			//				File dirFile = new File(str);
			//				try{
			//					this.deleteModel(rl);
			//				}
			//				catch(Exception e1){
			//					e1.printStackTrace();
			//		
//			sss
			Preferences pref = Activator.getDefault().getPluginPreferences();
			String pth = pref.getString(PreferenceConstants.TEMPLATE_PATH);
//            String pth =  "D:\\appTemplate";

//			UMLTreeParentModel treeObject = getPmMake();
			//			if(str!=null && !str.trim().equals("")){
			//				this.viewInfos.clear();
			//				this.compositeCmp.clear();
			//				File dirFile = new File(str);
			//				try{
			//					this.deleteModel(rl);
			//				}
			//				catch(Exception e1){
			//					e1.printStackTrace();
			//				}

			boolean isRoot = true;
			File dFile=new File(pth);
			this.recurseComponent(dFile,treeObject,isRoot);
			this.makeView2();

			//					ProjectManager.getInstance().getModelBrowser().refresh(this.rtm);
			//			}
		}
		catch(Exception e){
			e.printStackTrace();
		}


	}
	
	public void makeTemplateComponent(){//Khg 2010.05.26 임포트 어플리케이션
		try{
			//String str = ProjectManager.getInstance().getSourceFolder();
			//				ProgressMonitorDialog progress =  new ProgressMonitorDialog(ProjectManager.getInstance().window.getShell());
			//				LoadLibProgress lp = new LoadLibProgress(true);
			//				lp.setRl(rl);
			//				progress.run(true, true, lp);
			viewInfos2.clear();

			UMLTreeParentModel treeObject = ProjectManager.getInstance().getModelBrowser().getRootTemplateTreeModel();
			//			if(str!=null && !str.trim().equals("")){
			//				this.viewInfos.clear();
			//				this.compositeCmp.clear();
			//				File dirFile = new File(str);
			//				try{
			//					this.deleteModel(rl);
			//				}
			//				catch(Exception e1){
			//					e1.printStackTrace();
			//				}
            String pth =  "D:\\template";
			boolean isRoot = true;
			File dFile=new File(pth);
			this.recurseComponent(dFile,treeObject,isRoot);
//			this.makeView2();

			//					ProjectManager.getInstance().getModelBrowser().refresh(this.rtm);
			//			}
		}
		catch(Exception e){
			e.printStackTrace();
		}


	}

	public void deleteModel(UMLTreeParentModel parent){
		UMLTreeModel[] childList= parent.getChildren();
		for(int j=0;j<childList.length;j++){
			if(childList[j] instanceof UMLTreeModel){
				ProjectManager.getInstance().deleteUMLModel(childList[j]);
				ProjectManager.getInstance().removeUMLNode(childList[j].getParent(), childList[j]);
			}
		}

	}

	public void deleteModel2(UMLTreeParentModel parent){
		UMLTreeModel[] childList= parent.getChildren();
		for(int j=0;j<childList.length;j++){
			if(childList[j] instanceof UMLTreeModel){
				//				ProjectManager.getInstance().deleteUMLModel(childList[j]);
				ProjectManager.getInstance().removeUMLNode(childList[j].getParent(), childList[j]);
			}
		}

	}


	public void getComponentFile(File dirFile){
		String contents[] = dirFile.list();
		java.util.ArrayList dirList = new java.util.ArrayList();

		for(int i = 0; i < contents.length; i++){
			File child = new File(dirFile, contents[i]);
			if(child.isFile()){
				SendFile tf = new SendFile();
				tf.path = this.netFoler;
				tf.setKey(child.getAbsolutePath());
				tf.value = child;
				this.compFiles.add(tf);
			}
			if(child.isDirectory()){
				dirList.add(contents[i]);
			}
		}
		for(int i=0;i<dirList.size();i++){
			String dirName = (String)dirList.get(i);
			File child = new File(dirFile, dirName);
			this.getComponentFile(child);
		}

	}

	void recurseSave(UMLTreeParentModel parent){
		// 지정된 디렉토리에 존재하는 파일과 하위 디렉토리에 대한 명칭들을 String 형 배열 객체로 리턴합니다.
		try{ 
			if(parent!=null){
				for(int i=0;i<parent.getChildren().length;i++){
					UMLTreeModel ut = (UMLTreeModel)parent.getChildren()[i];
					UMLModel um = (UMLModel)ut.getRefModel();
					if(um instanceof  ComponentLibModel){
						ComponentLibModel clm = (ComponentLibModel)um;
						HashMap map = new HashMap();
						map.put("name", clm.getName());
						map.put("id", clm.getId());
						map.put("description", clm.getDesc());
						if("<<atomic>>".equals(clm.getStereotype()) ){
							File src = clm.getFile();
							File desc = clm.getFile();

							this.saveInstanceFile(src, desc, map);
						}
						else if("<<composite>>".equals(clm.getStereotype()) ){
							File src = clm.getFile();
							File desc = clm.getFile();

							this.saveInstanceFile(src, desc, map);
							this.recurseSave((UMLTreeParentModel)ut);
						}

					}
					else if(ut instanceof UMLTreeParentModel){
						this.recurseSave((UMLTreeParentModel)ut);
					}
				}
			}


		}
		catch(Exception e){
			e.printStackTrace();
		}
		return;
	}
	// IJS 2010-08-12 4:17오후  라이브러리 이동 관련
	public void addLibComponent(File profile,File dllFile,AtomicComponentModel acm){
		RootLibTreeModel rt = ProjectManager.getInstance().getModelBrowser().getRootLibTreeModel();
		//20110712서동민 ComponentLibModel ->>ComponentModel
		ComponentModel cm = this.addComponentModel(acm.getName(), rt,profile);
		if(cm!=null){
			File f = (File)this.getDllFile().get(cm.getDllFileName());
			cm.setDllFile(f);
			File f1 = (File)this.getEtcFile().get(cm.getFsmFileName());
			cm.setFsmFile(f1);
		}
		ProjectManager.getInstance().getModelBrowser().refresh(ProjectManager.getInstance().getModelBrowser().getRootLibTreeModel());
	}

	
	//20110719서동민
	void recurseCmpEdtRef(File dirFile, UMLTreeParentModel parent, boolean isRoot){
		// 지정된 디렉토리에 존재하는 파일과 하위 디렉토리에 대한 명칭들을 String 형 배열 객체로 리턴합니다.
		try{ 
			String contents[] = dirFile.list();
			
			java.util.ArrayList dirList = new java.util.ArrayList();
			java.util.ArrayList cmps = new java.util.ArrayList();
			UMLTreeParentModel uparent = null;
			for(int i = 0; i < contents.length; i++){
				if(!contents[i].equals(".metadata")){
					File child = new File(dirFile + File.separator +  contents[i] + File.separator +  contents[i] + File.separator + "profile", contents[i] +".xml");
					System.out.println(child);
//					ComponentModel cm = this.addComponentModel(contents[i], parent, child);
//					if(cm!=null){
//						cmps.add(cm);
//					}
				
					
					if(child.isFile()){
						if(this.isImport && chkcmp.get(child.getPath()+File.separator+child.getName())!=null)
							continue;
						this.chkcmp.put(child.getPath()+File.separator+child.getName(), "chk");
//						int index = contents[i].lastIndexOf(".xml");
						//					int indexd = contents[i].lastIndexOf(".dll");
//						if(child.getName().length()==index+4
//								&& child.getName().lastIndexOf("_view.xml")==-1){
							//20110712서동민 ComponentLibModel ->>ComponentModel
							ComponentModel cm = this.addCompEdtModel(contents[i], parent,child);
							String xmlPath = contents[i] + ".xml";
							CompAssemManager.getInstance().setModel(child.getPath());
					}
				}
			}
		}
		catch(Exception e){
			
		}
	}
	
	//20110712서동민-컴포넌트에디터 부분 새로고침시 사용
	//20110808서동민-수정
	void recurseCompEdit(File dirFile, UMLTreeParentModel parent, boolean isRoot){
		// 지정된 디렉토리에 존재하는 파일과 하위 디렉토리에 대한 명칭들을 String 형 배열 객체로 리턴합니다.
		try{ 
			String contents[] = dirFile.list();
			
			java.util.ArrayList dirList = new java.util.ArrayList();
			java.util.ArrayList cmps = new java.util.ArrayList();
			UMLTreeParentModel uparent = null;
			for(int i = 0; i < contents.length; i++){
//				if(!contents[i].equals(".metadata")){
//					for(int k = 0; k<0; k++){
//						if(true){
							File child = new File(dirFile + File.separator +  contents[i] + File.separator +  contents[i] + File.separator + "profile", contents[i] +".xml");
							System.out.println(child);
		//					ComponentModel cm = this.addComponentModel(contents[i], parent, child);
		//					if(cm!=null){
		//						cmps.add(cm);
		//					}
						
							
							if(child.isFile()){
								if(this.isImport && chkcmp.get(child.getPath()+File.separator+child.getName())!=null)
									continue;
								this.chkcmp.put(child.getPath()+File.separator+child.getName(), "chk");
		//						int index = contents[i].lastIndexOf(".xml");
								//					int indexd = contents[i].lastIndexOf(".dll");
		//						if(child.getName().length()==index+4
		//								&& child.getName().lastIndexOf("_view.xml")==-1){
									//20110712서동민 ComponentLibModel ->>ComponentModel
									ComponentModel cm = this.addComponentModel(contents[i], parent,child);
									String xmlPath = contents[i] + ".xml";
									CompAssemManager.getInstance().setModel(child.getPath());
									
									
									//						uparent = cm.getUMLTreeModel().getParent();
									//						this.chkcmp.put(child.getPath()+File.separator+child.getName(), "chk");
									if(monitor!=null){
										monitor.subTask(child.getPath());
										monitor.worked(INCREMENT);
									}
									if(cm!=null){
										cmps.add(cm);
									}
		//						}
							}
							
							for(int j=0;j<cmps.size();j++){
								ComponentLibModel cm1 = (ComponentLibModel)cmps.get(j);
		//						ProjectManager.getInstance().addUMLModel(cm1.getName(), parent, cm1, 29,5);
								if(cm1!=null){
									File f = (File)this.getDllFile().get(cm1.getDllFileName());
									cm1.setDllFile(f);
									File f1 = (File)this.getEtcFile().get(cm1.getFsmFileName());
									cm1.setFsmFile(f1);
								}
							}
		
		
		
		
		//					for(int j=0;j<dirList.size();j++){
		//						String dirName = (String)dirList.get(j);
		//						File child1 = new File(dirFile, dirName);
		//						UMLTreeParentModel ut =(UMLTreeParentModel)this.getDirModel().get(child1.getPath()+".xml");
		//						//				UMLTreeParentModel ut =(UMLTreeParentModel)this.getModelStore().get(dirName);
		//						//				this.getModelStore().get("dirName");
		//						//				if("RobotMoveApp".equals(dirName)){
		//						//					System.out.println("ddddd");
		//						//				}
		//
		//						if(ut!=null){
		//							ComponentLibModel clm = (ComponentLibModel)ut.getRefModel();
		//							clm.setDir(child1);
		//							recurseCompEdit(child1, ut,false);
		//						}
		//						else{
		//							//					if("RobotMoveApp".equals(contents[i])){
		//							//						System.out.println("ddddd");
		//							//					}
		//							RootPackageTreeModel rt = new RootPackageTreeModel(dirName);
		//							rt.dir = child1;
		//							parent.addChild(rt);
		//							if(isTotal)
		//								recurseCompEdit(child1, rt,true);
		//						}
		//
		//					}
//						}
//					}	
//				}
			}
		}
		catch(Exception e){
			
		}
	}

	void recurse(File dirFile, UMLTreeParentModel parent,boolean isRoot){
		// 지정된 디렉토리에 존재하는 파일과 하위 디렉토리에 대한 명칭들을 String 형 배열 객체로 리턴합니다.
		try{ 

			String contents[] = dirFile.list();
			
			java.util.ArrayList dirList = new java.util.ArrayList();
			java.util.ArrayList cmps = new java.util.ArrayList();
			UMLTreeParentModel uparent = null;
			for(int i = 0; i < contents.length; i++){
//				if(contents[i]!=".metadata"){
				System.out.println(contents[i]);
				// 현재 디렉토리와 파일 정보를 가지고 File 객체를 생성합니다.
				File child = new File(dirFile, contents[i]);

				if(child.isFile()){
					if(this.isImport && chkcmp.get(child.getPath()+File.separator+child.getName())!=null)
						continue;
					this.chkcmp.put(child.getPath()+File.separator+child.getName(), "chk");
					int index = contents[i].lastIndexOf(".xml");
					//					int indexd = contents[i].lastIndexOf(".dll");
					//110907 S SDM - DLL 파일 있는지 확인
					if(child.getName().length()==index+4
							&& child.getName().lastIndexOf("_view.xml")==-1){
						
						ComponentProfile retModel = this.makeModel(child,null);
						//111102 SDM S - 포트 XML읽을때 NULL포인트 에러 수정
						String strDllName = null;
						if(retModel != null)
							strDllName = retModel.execution_environment_library_name;
						else
							strDllName = "";
						//111102 SDM E - 포트 XML읽을때 NULL포인트 에러 수정

//						String strDllPath = dirFile.getPath() + File.separator + contents[i].substring(0, index) + ".dll";
						
						boolean bDll = false;
						
						if(dirFile.getPath().lastIndexOf(File.separator + "profile") != -1){
							 String str = dirFile.getPath().substring(0, dirFile.getPath().lastIndexOf(File.separator + "profile"));
							 
							 if((new File(str + File.separator + "Debug" + File.separator + strDllName).isFile())
									 ||(new File(str + File.separator + "profile" + File.separator + strDllName).isFile())
									 ||(new File(str + File.separator + "Release" + File.separator + strDllName).isFile()))
								 bDll = true;
						}
						else if(strDllName != null && !strDllName.equals("")){
							File file = new File(dirFile.getPath() + File.separator + strDllName);
							bDll = file.isFile();					
						}
							

						
						if(bDll || (retModel instanceof CompositeComponentProfile) 
								|| (dirFile.getPath().lastIndexOf("profile")!=-1)){
							//20110712서동민 ComponentLibModel ->>ComponentModel
							ComponentModel cm = this.addComponentModel(contents[i].substring(0,index), parent,child);
							String xmlPath = contents[i];
							CompAssemManager.getInstance().setModel(child.getPath());
							
							
							//						uparent = cm.getUMLTreeModel().getParent();
							//						this.chkcmp.put(child.getPath()+File.separator+child.getName(), "chk");
							if(monitor!=null){
								monitor.subTask(child.getPath());
								monitor.worked(INCREMENT);
							}
							if(cm!=null){
								cmps.add(cm);
							}
						}
						else if(retModel != null){
							MessageBox dialog = new MessageBox(ProjectManager.getInstance().window.getShell(),SWT.OK|SWT.ICON_ERROR);
							dialog.setText("Message");
							dialog.setMessage("DLL File there is no Chateulsu <" + child.getName().substring(0, index) + "> libraries create can.");
							dialog.open();
							child.delete();
						}
					}
					//110907 E SDM - DLL 파일 있는지 확인
					
					//					else if(child.getName().length()==indexd+4){
					//						String key = child.getName().substring(0,indexd);
					//						this.getDllFile().put(key, child);
					//					}
					else {
						String key = child.getName();
						this.getDllFile().put(key, child);
					}


					if(child.getName().lastIndexOf("_view.xml")!=-1){
						this.viewInfos.add(child);
					}
				}



				// 현재 디렉토리의 파일이 하위 디렉토리인 경우에만 resurse() 메서드를 재귀 호출합니다.
				if(child.isDirectory()){
					if(this.isImport && chkdir.get(child.getPath())!=null)
						continue;
					String ddd = child.getPath();
					this.chkdir.put(child.getPath(), "chk");
					dirList.add(contents[i]);

				}
			}

			//			if(parent!=null){
			//				for(int i1=0;i1<parent.getChildren().length;i1++){
			//					UMLTreeModel ut = parent.getChildren()[i1];
			//					if(ut.getRefModel() instanceof ComponentLibModel){
			//					boolean ischk = false;
			//					for(int i2=0;i2<contents.length;i2++){
			//						String dd = contents[i1];
			//						int index = dd.lastIndexOf(".xml");
			//						if(index>=0){
			//							String name = dd.substring(0,index);
			//							if(dd.equals(name)){
			//								ischk = true;
			//							}
			//						}
			//						
			//					}
			//					if(!ischk){
			//						ProjectManager.getInstance().deleteUMLModel(ut);
			//					}
			//				}
			//				}
			//			}

			for(int i=0;i<cmps.size();i++){
				ComponentLibModel cm = (ComponentLibModel)cmps.get(i);
				if(cm!=null){
					File f = (File)this.getDllFile().get(cm.getDllFileName());
					cm.setDllFile(f);
					File f1 = (File)this.getEtcFile().get(cm.getFsmFileName());
					cm.setFsmFile(f1);
				}
			}




			for(int i=0;i<dirList.size();i++){
				String dirName = (String)dirList.get(i);
				File child = new File(dirFile, dirName);
				String temp2 = child.getPath()+".xml";
				UMLTreeParentModel ut =(UMLTreeParentModel)this.getDirModel().get(child.getPath()+".xml");
				//				UMLTreeParentModel ut =(UMLTreeParentModel)this.getModelStore().get(dirName);
				//				this.getModelStore().get("dirName");
				//				if("RobotMoveApp".equals(dirName)){
				//					System.out.println("ddddd");
				//				}
				
				//110907 SDM - TAR파일 읽을때의 불필요한 폴더 생성 막음	//110908 SDM dll도  읽도록 수정
				String strTPath = child.getPath() + File.separator + dirName;
				boolean bIsFile = (new File(strTPath + File.separator + "profile" + File.separator + dirName + ".xml")).isFile();


				if(ut!=null){	
					ComponentLibModel clm = (ComponentLibModel)ut.getRefModel();
					clm.setDir(child);
					recurse(child, ut,false);
				}
				
				//110907 S SDM - TAR파일 읽을때의 불필요한 폴더 생성 막음 (예>./comp/comp/profile/comp.xml >> ./comp/comp/xml)
				else if(bIsFile){
					//dll 읽기
					File fDll1 = new File(strTPath + File.separator + "Release");
					if(fDll1.isFile())
						recurse(fDll1, null, false);
					
					File fDll2 = new File(strTPath + File.separator + "Debug");
					if(fDll2.isFile())
						recurse(fDll2, null, false);
					
					//xml 읽기
					File fXml = new File(strTPath + File.separator + "profile");
					RootPackageTreeModel rt = new RootPackageTreeModel(dirName);
					rt.dir = fXml;
					parent.addChild(rt);
					if(isTotal)
						recurse(fXml, rt,true);
					
				}
				//110907 E SDM - TAR파일 읽을때의 불필요한 폴더 생성 막음 (예>./comp/comp/profile/comp.xml >> ./comp/comp/xml)
				
				else{
					//					if("RobotMoveApp".equals(contents[i])){
					//						System.out.println("ddddd");
					//					}
					RootPackageTreeModel rt = new RootPackageTreeModel(dirName);
					rt.dir = child;
					parent.addChild(rt);
					
					if(isTotal)
						recurse(child, rt,true);
				}

			}

			//			if(parent!=null){
			//				for(int i1=0;i1<parent.getChildren().length;i1++){
			//					
			//					UMLTreeModel ut = parent.getChildren()[i1];
			//					
			//					if(!(ut.getRefModel() instanceof ComponentLibModel)){
			//						boolean ischk = false;
			//					for(int i=0;i<dirList.size();i++){
			//						String dirName = (String)dirList.get(i);
			//						
			//							if(dirName.equals(ut.getName())){
			//								ischk = true;
			//							}
			//						}
			//					if(!ischk){
			//						ProjectManager.getInstance().deleteUMLModel(ut);
			//					
			//					}
			//					
			//				}
			//				}
			//			}
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return;
	}

	//	public void parserApplication(File f){
	//		
	//		
	//	}
	
	
	void recurseComponent(File dirFile, UMLTreeParentModel parent,boolean isRoot){

		// 지정된 디렉토리에 존재하는 파일과 하위 디렉토리에 대한 명칭들을 String 형 배열 객체로 리턴합니다.
		try{ 

			String contents[] = dirFile.list();
			java.util.ArrayList dirList = new java.util.ArrayList();
			java.util.ArrayList cmps = new java.util.ArrayList();
			UMLTreeParentModel uparent = null;
			for(int i = 0; i < contents.length; i++){

				System.out.println(contents[i]);
				// 현재 디렉토리와 파일 정보를 가지고 File 객체를 생성합니다.
				File child = new File(dirFile, contents[i]);

				if(child.isFile()){
				
					int index = contents[i].lastIndexOf(".xml");
					//					int indexd = contents[i].lastIndexOf(".dll");
					if(child.getName().length()==index+4
							&& child.getName().lastIndexOf("_view.xml")==-1){

						TemplateComponentModel cm = this.addTemplateComponentModel(contents[i].substring(0,index), parent,child);
						//						uparent = cm.getUMLTreeModel().getParent();
						//						this.chkcmp.put(child.getPath()+File.separator+child.getName(), "chk");
						if(monitor!=null){
							monitor.subTask(child.getPath());
							monitor.worked(INCREMENT);
						}
						if(cm!=null){
							cmps.add(cm);
						}
					}
					//					else if(child.getName().length()==indexd+4){
					//						String key = child.getName().substring(0,indexd);
					//						this.getDllFile().put(key, child);
					//					}
					else {
						String key = child.getName();
						this.getDllFile().put(key, child);
					}


					if(child.getName().lastIndexOf("_view.xml")!=-1){
						this.viewInfos.add(child);
					}
				}



				// 현재 디렉토리의 파일이 하위 디렉토리인 경우에만 resurse() 메서드를 재귀 호출합니다.
				if(child.isDirectory()){
//					if(this.isImport && chkdir.get(child.getPath())!=null)
//						continue;
					String ddd = child.getPath();
//					this.chkdir.put(child.getPath(), "chk");
					dirList.add(contents[i]);

				}
			}

			//			if(parent!=null){
			//				for(int i1=0;i1<parent.getChildren().length;i1++){
			//					UMLTreeModel ut = parent.getChildren()[i1];
			//					if(ut.getRefModel() instanceof ComponentLibModel){
			//					boolean ischk = false;
			//					for(int i2=0;i2<contents.length;i2++){
			//						String dd = contents[i1];
			//						int index = dd.lastIndexOf(".xml");
			//						if(index>=0){
			//							String name = dd.substring(0,index);
			//							if(dd.equals(name)){
			//								ischk = true;
			//							}
			//						}
			//						
			//					}
			//					if(!ischk){
			//						ProjectManager.getInstance().deleteUMLModel(ut);
			//					}
			//				}
			//				}
			//			}

//			for(int i=0;i<cmps.size();i++){
//				TemplateComponentModel cm = (TemplateComponentModel)cmps.get(i);
//				if(cm!=null){
//					File f = (File)this.getDllFile().get(cm.getDllFileName());
//					cm.setDllFile(f);
//					File f1 = (File)this.getEtcFile().get(cm.getFsmFileName());
//					cm.setFsmFile(f1);
//				}
//			}




			for(int i=0;i<dirList.size();i++){
				String dirName = (String)dirList.get(i);
				File child = new File(dirFile, dirName);
				UMLTreeParentModel ut =(UMLTreeParentModel)this.getDirModel().get(child.getPath()+".xml");
				//				UMLTreeParentModel ut =(UMLTreeParentModel)this.getModelStore().get(dirName);
				//				this.getModelStore().get("dirName");
				//				if("RobotMoveApp".equals(dirName)){
				//					System.out.println("ddddd");
				//				}

				if(ut!=null){
					ComponentLibModel clm = (ComponentLibModel)ut.getRefModel();
					clm.setDir(child);
					recurseComponent(child, ut,false);
				}
				else{
					//					if("RobotMoveApp".equals(contents[i])){
					//						System.out.println("ddddd");
					//					}
					RootPackageTreeModel rt = null;
					
					boolean isApp = false;
					for(int i1=0;i1<this.aps.size();i1++){
						ApplicationProfile ap = (ApplicationProfile)this.aps.get(i1);
						if(ap.parent==parent){
							if(ap.name.equals(dirName)){
								isApp = true;
								rt = new RootAppPackageTreeModel(dirName);
								ap.nd = ProjectManager.getInstance().addUMLDiagram(rt.getName()+" diagram", (UMLTreeParentModel)rt, null, 1, false,5);
								ap.nd.setDtype(-1);
								((RootPackageTreeModel)rt).dir = child;
								((RootAppPackageTreeModel)rt).ap = ap;
								parent.addChild(rt);
								break;
							}
						}
						
					}
//					if(isApp){
//						rt = new RootAppPackageTreeModel(dirName);
//						ap.nd = ProjectManager.getInstance().addUMLDiagram(rt.getName()+" diagram", (UMLTreeParentModel)rt, null, 1, false,5);
//					}
					if(!isApp){
						 rt = new RootTemplatePackageTreeModel(dirName);
						((RootPackageTreeModel)rt).dir = child;
						parent.addChild(rt);
					}
//					if(isTotal)
						recurseComponent(child, rt,true);
				}

			}

			//			if(parent!=null){
			//				for(int i1=0;i1<parent.getChildren().length;i1++){
			//					
			//					UMLTreeModel ut = parent.getChildren()[i1];
			//					
			//					if(!(ut.getRefModel() instanceof ComponentLibModel)){
			//						boolean ischk = false;
			//					for(int i=0;i<dirList.size();i++){
			//						String dirName = (String)dirList.get(i);
			//						
			//							if(dirName.equals(ut.getName())){
			//								ischk = true;
			//							}
			//						}
			//					if(!ischk){
			//						ProjectManager.getInstance().deleteUMLModel(ut);
			//					
			//					}
			//					
			//				}
			//				}
			//			}

		}
		catch(Exception e){
			e.printStackTrace();
		}
		return;
	
	}

	public void recurse2( File dirFile,UMLTreeParentModel parent,boolean isRoot){//Khg 2010.05.26 임포트 어플리케이션
		// 지정된 디렉토리에 존재하는 파일과 하위 디렉토리에 대한 명칭들을 String 형 배열 객체로 리턴합니다.
		try{ 
			N3EditorDiagramModel nd = new N3EditorDiagramModel();
			String contents[] = dirFile.list();
			java.util.ArrayList dirList = new java.util.ArrayList();
			java.util.ArrayList cmps = new java.util.ArrayList();
			UMLTreeParentModel uparent = null;
			int cnt=0;
			for(int i = 0; i < contents.length; i++){

				System.out.println(contents[i]);
				// 현재 디렉토리와 파일 정보를 가지고 File 객체를 생성합니다.
				File child = new File(dirFile, contents[i]);

				if(child.isFile()){
					//					this.chkcmp.put(child.getPath()+File.separator+child.getName(), "chk");
					int index = contents[i].lastIndexOf(".xml");
					//					int indexd = contents[i].lastIndexOf(".dll");
					System.out.println(child.getName()+"  "+child.getName().length());
					if(child.getName().length()==index+4
							&& child.getName().lastIndexOf("_view.xml")==-1){

						ComponentLibModel cm = this.addComponentModel2(contents[i].substring(0,index), parent,child);
						
//						TemplateComponentModel cm = this.addTemplateComponentModel(contents[i].substring(0,index), parent,child);
//						if(cm!=null){
//							cmps.add(cm);
//						}
						//						importViews.put(cm.getName(), cm);
						//						sss
						//												uparent = cm.getUMLTreeModel().getParent();
						//												this.chkcmp.put(child.getPath()+File.separator+child.getName(), "chk");
						//						cm.setReadOnly("1");
						//						if(cm!=null){
						//
						//							
						//							if(parent instanceof PackageTreeModel&&parent.getRefModel()==null){
						//								java.util.ArrayList aprts = cm.getPortManager().getPorts();
						//								UMLTreeModel[] cList= parent.getChildren();
						//								for(int j=0;j<cList.length;j++){
						//									if(cList[j].getRefModel() instanceof N3EditorDiagramModel){
						//										nd=(N3EditorDiagramModel)cList[j].getRefModel();
						//									}
						//								}
						//
						//								//여기서 um의 차일드를 가져온다..
						//								
						//								nd.addChild(cm);
						//								
						//								for(int j=0;j<aprts.size();j++){
						////									Point cmp=cm.getLocation();
						//									PortModel pm = (PortModel)aprts.get(j);
						////									Point pmp=new Point(cmp.x+(j*20),pm.getLocation().y);
						////									pm.setLocation(pmp);
						////									System.out.println(p);
						////									ProjectManager.getInstance()CompAssemManager.
						//////									HashMap map=scv.getPortViewes();
						////									ExportPortView pfv =(ExportPortView)map.get(pm.getName());
						//									//							if(pfv!=null){
						//									pm.setPortContainerModel(cm);
						//									pm.getElementLabelModel().setReadOnly(false);
						//									cm.createPort2(pm, nd);
						//									//pm.setPtDifference(pfv.ptDifference);
						//									//			                    pm.setLocation(pfv.location);
						//
						//									//							}
						//									//							else{
						//									//								System.out.println("ddddd");
						//									//							}
						//
						//
						//								}
						//								this.autoLayoutPort(aprts);
						//								int x=cnt%3*200+20;
						//								int y=cnt/3*150+20;
						//								
						//								Point p = new Point(x,y);
						//								cm.setLocation(p);
						////								cm.
						//								cnt++;
						//							}
						//						}


						if(monitor!=null){
							monitor.subTask(child.getPath());
							monitor.worked(INCREMENT);
						}
						//						if(cm!=null){
						//							cmps.add(cm);
						//						}
					}
					//					else if(child.getName().length()==indexd+4){
					//						String key = child.getName().substring(0,indexd);
					//						this.getDllFile().put(key, child);
					//					}
					else if(child.getName().lastIndexOf("_view.xml")!=-1){


						viewInfos2.add(child);
					}
					else {
						String key = child.getName();
						this.getDllFile().put(key, child);
					}


					if(child.getName().lastIndexOf("_view.xml")!=-1){
						this.viewInfos.add(child);
					}
				}



				// 현재 디렉토리의 파일이 하위 디렉토리인 경우에만 resurse() 메서드를 재귀 호출합니다.
				if(child.isDirectory()){

					//					String name=child.getName();
					//					FinalPackageModel finalPackageModel = new FinalPackageModel();
					//					ProjectManager.getInstance().addUMLModel(name, parent, finalPackageModel, 0,5);
					String ddd = child.getPath();

					dirList.add(contents[i]);

				}
			}

//			for(int i=0;i<cmps.size();i++){
//				ComponentLibModel cm = (ComponentLibModel)cmps.get(i);
//				if(cm!=null){
//					File f = (File)this.getDllFile().get(cm.getDllFileName());
//					cm.setDllFile(f);
//					File f1 = (File)this.getEtcFile().get(cm.getFsmFileName());
//					cm.setFsmFile(f1);
//				}
//			}




			for(int i=0;i<dirList.size();i++){
				String dirName = (String)dirList.get(i);
				File child = new File(dirFile, dirName);
				//FinalPackageModel finalPackageModel = new FinalPackageModel();
				//ProjectManager.getInstance().addUMLModel(dirName, parent, finalPackageModel, 0,5);
				UMLTreeParentModel ut =(UMLTreeParentModel)this.getDirModel().get(child.getPath()+".xml");
				//				UMLTreeParentModel ut =(UMLTreeParentModel)this.getModelStore().get(dirName);
				//				this.getModelStore().get("dirName");
				//				if("RobotMoveApp".equals(dirName)){
				//					System.out.println("ddddd");
				//				}

				if(ut!=null){
					ComponentLibModel clm = (ComponentLibModel)ut.getRefModel();
					clm.setDir(child);
					recurse2(child, ut,false);
				}
				else{
					//					if("RobotMoveApp".equals(contents[i])){
					//						System.out.println("ddddd");
					//					}

					UMLTreeModel rt = null;
					rt=new PackageTreeModel(dirName);
					//					FinalPackageModel finalPackageModel = new FinalPackageModel();
					//				    ProjectManager.getInstance().addUMLModel(dirName, parent, finalPackageModel, 0,5);
					//rt.dir = child;
					//					 udm=new UMLDiagramModel();
					//					udm.setName(rt.getName());
					//					rt.addChild(rt);

					nd =ProjectManager.getInstance().addUMLDiagram(rt.getName()+" diagram", (UMLTreeParentModel)rt, null, 1, false,5);
					this.ap.nd = nd;

					//					String contents1[] = child.list();
					//					for(int j=0;i<contents1.length;j++){
					//						String name=contents1[j];
					//						
					//						if(name.lastIndexOf("_view.xml")==-1||name.lastIndexOf(".xml")!=-1){
					//							int index=name.lastIndexOf("_view.xml");
					//							name=name.substring(0,index)+".xml";
					//							ComponentLibModel cm = this.addComponentModel2(contents1[j].substring(0,index), parent,child);
					//							java.util.ArrayList aprts = cm.getPortManager().getPorts();
					//							
					//							for(int k=0;i<aprts.size();k++){
					//								
					//								PortModel pm = (PortModel)aprts.get(k);
					////								ExportPortView pfv =(ExportPortView)portViewes.get(pm.getName());
					////								if(pfv!=null){
					//									pm.setPortContainerModel(cm);
					//									 pm.getElementLabelModel().setReadOnly(true);
					//									cm.createPort2(pm, nd);
					////									pm.setPtDifference(pfv.ptDifference);
					////				                    pm.setLocation(pfv.location);
					//				                   
					////								}
					////								else{
					////									System.out.println("ddddd");
					////								}
					//							}
					//							nd.addChild(cm);


					//						}
					//						
					//						
					//					}

					//					UMLTreeParentModel up =(UMLTreeParentModel)CompAssemManager.getInstance().getDirModel().get(contents[i]);







					parent.addChild(rt);

					//modelStore.put(n3EditorDiagramModel.getId(),n3EditorDiagramModel);
					//if(isTotal)
					recurse2(child,(PackageTreeModel) rt,true);

					//					ProjectManager.getInstance().getModelBrowser().refresh(parent);
				}

			}

			//			if(parent!=null){
			//				for(int i1=0;i1<parent.getChildren().length;i1++){
			//					
			//					UMLTreeModel ut = parent.getChildren()[i1];
			//					
			//					if(!(ut.getRefModel() instanceof ComponentLibModel)){
			//						boolean ischk = false;
			//					for(int i=0;i<dirList.size();i++){
			//						String dirName = (String)dirList.get(i);
			//						
			//							if(dirName.equals(ut.getName())){
			//								ischk = true;
			//							}
			//						}
			//					if(!ischk){
			//						ProjectManager.getInstance().deleteUMLModel(ut);
			//					
			//					}
			//					
			//				}
			//				}
			//			}

		}
		catch(Exception e){
			e.printStackTrace();
		}
		return;
	}
	public void makeView(){
		for(int i = 0; i <this.viewInfos.size(); i++){
			File child = (File)this.viewInfos.get(i);
			this.addComponentViewModel(child);
			if(monitor!=null){
				monitor.subTask(child.getPath());
				monitor.worked(INCREMENT);
			}
		}
		for(int i = 0; i <this.compositeCmp.size(); i++){
			ComponentLibModel child = (ComponentLibModel)this.compositeCmp.get(i);
			if(!child.isView()){
				CompositeComponentProfile cmp =(CompositeComponentProfile)child.getRetModel();
				if(!(cmp instanceof ApplicationProfile))
				cmp.makeComponentLibMainModelView(child.getFile().getPath());
				if(monitor!=null){
					monitor.subTask(child.getFile().getPath());
					monitor.worked(INCREMENT);
				}
			}
		}
	}

	public void makeView2(){//Khg 2010.05.26 임포트 어플리케이션
		for(int i = 0; i <this.viewInfos2.size(); i++){
			System.out.println(this.viewInfos2.get(i));

			File child = (File)this.viewInfos2.get(i);
			this.addComponentViewModel(child);
			if(monitor!=null){
				monitor.subTask(child.getPath());
				monitor.worked(INCREMENT);
			}
		}

		//		this.ap.makeView();

		for(int i = 0; i <this.compositeCmp.size(); i++){
			ComponentLibModel child = (ComponentLibModel)this.compositeCmp.get(i);
			if(!child.isView()){
				CompositeComponentProfile cmp =(CompositeComponentProfile)child.getRetModel();
				if(cmp instanceof ApplicationProfile){
					continue;
				}
				cmp.makeComponentLibMainModelView(child.getFile().getPath());
				if(monitor!=null){
					monitor.subTask(child.getFile().getPath());
					monitor.worked(INCREMENT);
				}
			}
		}
		this.compositeCmp.clear();
		
		for(int i = 0; i <this.aps.size(); i++){
			ApplicationProfile ap = (ApplicationProfile)this.aps.get(i);
			ap.makeView();
		}
		this.aps.clear();
	}


	void recurseLink(File dirFile, UMLTreeParentModel parent){
		// 지정된 디렉토리에 존재하는 파일과 하위 디렉토리에 대한 명칭들을 String 형 배열 객체로 리턴합니다.
		try{

			String contents[] = dirFile.list();
			for(int i = 0; i < contents.length; i++){

				System.out.println(contents[i]);
				// 현재 디렉토리와 파일 정보를 가지고 File 객체를 생성합니다.
				File child = new File(dirFile, contents[i]);
				if(child.isFile()){
					int index = contents[i].lastIndexOf(".link");
					if(child.getName().length()==index+4){


					}
				}
				// 현재 디렉토리의 파일이 하위 디렉토리인 경우에만 resurse() 메서드를 재귀 호출합니다.
				if(child.isDirectory()){
					//		
					//					recurseLink(child);
				}
			}

		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	public void addComponentLink(){

	}

	public ComponentProfile makeModel(File child,UMLTreeModel to1){
		DOMParser parser = new DOMParser();
		SAXParser saxParser = new SAXParser();
		ComponentXMLContentHandler cm = new ComponentXMLContentHandler();
		//		Document dom = null;
		try{
			InputSource src = new InputSource(new FileInputStream(child));
			saxParser.setContentHandler(cm);
			saxParser.parse(src);
			ComponentProfile retObj = cm.getRetObj();
			return retObj;

		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;



	}


	public CompositeComponentViewProfile makeModelView(File child){
		DOMParser parser = new DOMParser();
		SAXParser saxParser = new SAXParser();
		ComponentXMLContentHandler cm = new ComponentXMLContentHandler();
		//		Document dom = null;
		try{
			InputSource src = new InputSource(new FileInputStream(child));
			saxParser.setContentHandler(cm);
			saxParser.parse(src);
			CompositeComponentViewProfile retObj = cm.getRetObjView();
			//			retObj.makeComponentLibMainModelView();
			return retObj;

		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;



	}

	public boolean isCheckComponent(File child){
		DOMParser parser = new DOMParser();
		Document dom = null;
		try{
			InputSource src = new InputSource(new FileInputStream(child));
			parser.parse(src);
			dom = parser.getDocument();
			Element e = dom.getDocumentElement();
			if(e.getNodeName().equals("application_profile")){
				System.out.println("------->application_profile");
				return true;
			}
			else if(e.getNodeName().equals("component_profile")){
				System.out.println("------->component_profile");
				return true;
			}
			else if(e.getNodeName().equals("composite_component_profile")){
				System.out.println("------->composite_component_profile");
				return true;
			}

			//			NodeList n1 = e.getElementsByTagName("execution_environment");
			//			System.out.println("-------");




		}
		catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}

	public void addComponentViewModel(File child) {

		CompositeComponentViewProfile cv =   this.makeModelView(child);
		cv.makeComponentLibMainModelView(child);






	}



	//20110712서동민 ComponentLibModel ->>ComponentModel
	public ComponentModel addCompEdtModel(String name, UMLTreeParentModel tp,File child) {
		//		UMLTreeModel treeModel = parent.getUMLTreeModel().getParent();
		//default
		AtomicComponentModel componentModel = null;

		if (tp != null && tp instanceof UMLTreeParentModel) {
			UMLTreeParentModel uMLTreeParentModel = (UMLTreeParentModel)tp;
			StrcuturedPackageTreeLibModel to1 = null;






			ComponentProfile retModel = this.makeModel(child,to1);
			if(retModel!=null){
				to1 = new StrcuturedPackageTreeLibModel(name);//2008040203 PKY S 
				componentModel = null;

				componentModel = new AtomicComponentModel();
				componentModel.setRetModel(retModel);
				//				componentModel.setReadOnlyModel(true);
				if(retModel.fsm_name!=null && !retModel.fsm_name.trim().equals(""))
					componentModel.setFsmFileName(retModel.fsm_name);

				if(retModel.execution_environment_library_name!=null && !retModel.execution_environment_library_name.trim().equals(""))
					componentModel.setDllFileName(retModel.execution_environment_library_name);
				//                this.chk.add(to1);
				to1.setRefModel(componentModel);
				componentModel.setName(name);
				componentModel.setTreeModel(to1);
				to1.setParent(uMLTreeParentModel);
				uMLTreeParentModel.addChild(to1);
				String pkg = componentModel.getPackage();
				componentModel.getUMLDataModel().setElementProperty("cmp_profile", retModel);
				//ijs 091126
				//				componentModel.getClassModel().getElementLabelModelName().setReadOnly(true);
				//				componentModel.getClassModel().getElementLabelModelStreotype().setReadOnly(true);
				if(retModel instanceof CompositeComponentProfile){
					componentModel.setStereotype("composite");

					AtomicComponentModel clm = (AtomicComponentModel)componentModel;
					CompositeComponentProfile ccp = (CompositeComponentProfile)retModel;


					this.getCompositeCmp().add(clm);
					java.util.ArrayList list = new java.util.ArrayList();
					if(retModel.propetries.size()>0){
						for(int i=0;i<retModel.propetries.size();i++){
							Property p = (Property)retModel.propetries.get(i);
							list.add(p.getDetailPropertyTableItem());
						}
						clm.setTags(list);

					}
					for(int i=0;i<ccp.exportPorts.size();i++){
						ExportPort pp =(ExportPort)ccp.exportPorts.get(i);

						if("service".equals(pp.port_type)){
							if("required".equals(pp.port_dir)){
								//in
								MethodInputPortModel mip = new MethodInputPortModel();
								mip.getAttachElementLabelModel().setText(pp.export_port_name);
								clm.getPorts().add(mip);
								UMLTreeModel port = new UMLTreeModel(pp.export_port_name);
								port.setRefModel(mip);

								mip.getElementLabelModel().setTreeModel(port);
								to1.addChild(port);
								mip.getElementLabelModel().setPortId(mip.getUMLDataModel().getId());
								mip.getElementLabelModel().setReadOnly(true);

							}
							else{
								//out
								MethodOutputPortModel mip = new MethodOutputPortModel();
								mip.getAttachElementLabelModel().setText(pp.export_port_name);
								clm.getPorts().add(mip);
								UMLTreeModel port = new UMLTreeModel(pp.export_port_name);
								to1.addChild(port);
								port.setRefModel(mip);
								mip.getElementLabelModel().setTreeModel(port);
								mip.getElementLabelModel().setPortId(mip.getUMLDataModel().getId());
								mip.getElementLabelModel().setReadOnly(true);
							}
						}
						else if("event".equals(pp.port_type)){
							if("input".equals(pp.port_dir)){
								//in
								EventInputPortModel mip = new EventInputPortModel();
								mip.getAttachElementLabelModel().setText(pp.export_port_name);
								clm.getPorts().add(mip);
								UMLTreeModel port = new UMLTreeModel(pp.export_port_name);
								to1.addChild(port);
								port.setRefModel(mip);
								mip.getElementLabelModel().setTreeModel(port);
								mip.getElementLabelModel().setPortId(mip.getUMLDataModel().getId());
								mip.getElementLabelModel().setReadOnly(true);

							}
							else{
								//out
								EventOutputPortModel mip = new EventOutputPortModel();
								mip.getAttachElementLabelModel().setText(pp.export_port_name);
								clm.getPorts().add(mip);
								UMLTreeModel port = new UMLTreeModel(pp.export_port_name);
								to1.addChild(port);
								port.setRefModel(mip);
								mip.getElementLabelModel().setTreeModel(port);
								mip.getElementLabelModel().setPortId(mip.getUMLDataModel().getId());
								mip.getElementLabelModel().setReadOnly(true);
							}
						}
						else if("data".equals(pp.port_type)){
							if("input".equals(pp.port_dir)){
								//in
								DataInputPortModel mip = new DataInputPortModel();
								mip.getAttachElementLabelModel().setText(pp.export_port_name);
								clm.getPorts().add(mip);
								UMLTreeModel port = new UMLTreeModel(pp.export_port_name);
								to1.addChild(port);
								port.setRefModel(mip);
								mip.getElementLabelModel().setTreeModel(port);
								mip.getElementLabelModel().setPortId(mip.getUMLDataModel().getId());
								mip.getElementLabelModel().setReadOnly(true);

							}
							else{
								//out
								DataOutputPortModel mip = new DataOutputPortModel();
								mip.getAttachElementLabelModel().setText(pp.export_port_name);
								clm.getPorts().add(mip);
								UMLTreeModel port = new UMLTreeModel(pp.export_port_name);
								to1.addChild(port);
								port.setRefModel(mip);
								mip.getElementLabelModel().setTreeModel(port);
								mip.getElementLabelModel().setPortId(mip.getUMLDataModel().getId());
								mip.getElementLabelModel().setReadOnly(true);
							}
						} 

					}





				}
				else if(retModel instanceof ComponentProfile){
					componentModel.setStereotype("atomic");
					
					N3EditorDiagramModel n3EditorDiagramModel =
						ProjectManager.getInstance().addUMLDiagram(name+" diagram", (UMLTreeParentModel)to1, null, 1, false,5);
					((ComponentModel)componentModel).setN3EditorDiagramModel(n3EditorDiagramModel);
					AtomicComponentModel clm = (AtomicComponentModel)componentModel;
//					MonitoringMethodPortModel mmm = new MonitoringMethodPortModel();
//					clm.getPorts().add(mmm);
//					UMLTreeModel port = new UMLTreeModel("Monitoring");
//					to1.addChild(port);
//					port.setRefModel(mmm);
//					mmm.getElementLabelModel().setPortId(mmm.getUMLDataModel().getId());
//					mmm.setName("Monitoring");
//					LifecycleMethodPortModel lm = new LifecycleMethodPortModel();
//					clm.getPorts().add(lm);
//					UMLTreeModel port1 = new UMLTreeModel("Lifecycle");
//					to1.addChild(port1);
//					port1.setRefModel(lm);
//					lm.getElementLabelModel().setPortId(lm.getUMLDataModel().getId());
//					lm.setName("Lifecycle");
					java.util.ArrayList list = new java.util.ArrayList();
					if(retModel.propetries.size()>0){
						for(int i=0;i<retModel.propetries.size();i++){
							Property p = (Property)retModel.propetries.get(i);
							list.add(p.getDetailPropertyTableItem());
						}
						clm.setTags(list);

					}
					java.util.ArrayList list2 = new java.util.ArrayList();
					if(retModel.variables.size()>0){
						for(int i=0;i<retModel.variables.size();i++){
							Property p = (Property)retModel.variables.get(i);
							list2.add(p.getDetailPropertyTableItem());
						}
						clm.setMonitorVariables(list2);

					}

					for(int i=0;i<retModel.ports.size();i++){
						PortProfile pp =(PortProfile)retModel.ports.get(i);

						if(pp.portType==0){
							if("required".equals(pp.usage)){
								//in
								MethodInputPortModel mip = new MethodInputPortModel();
								mip.getAttachElementLabelModel().setText(pp.name);
								mip.setType(pp.type);
								clm.getPorts().add(mip);
								UMLTreeModel port2 = new UMLTreeModel(pp.name);
								port2.setRefModel(mip);

								mip.getElementLabelModel().setTreeModel(port2);
								mip.getElementLabelModel().setPortId(mip.getUMLDataModel().getId());
								to1.addChild(port2);
								mip.getElementLabelModel().setReadOnly(true);
//								componentModel.setCoreUMLTreeModel(to1);

							}
							else{
								//out
								MethodOutputPortModel mip = new MethodOutputPortModel();
								mip.getAttachElementLabelModel().setText(pp.name);
								mip.setType(pp.type);
								clm.getPorts().add(mip);
								UMLTreeModel port2 = new UMLTreeModel(pp.name);
								to1.addChild(port2);
								port2.setRefModel(mip);
								mip.getElementLabelModel().setTreeModel(port2);
								mip.getElementLabelModel().setPortId(mip.getUMLDataModel().getId());
								mip.getElementLabelModel().setReadOnly(true);

							}
						}
						else if(pp.portType==1){
							if("input".equals(pp.usage)){
								//in
								EventInputPortModel mip = new EventInputPortModel();
								mip.getAttachElementLabelModel().setText(pp.name);
								mip.setType(pp.type);
								clm.getPorts().add(mip);
								UMLTreeModel port2 = new UMLTreeModel(pp.name);
								to1.addChild(port2);
								port2.setRefModel(mip);
								mip.getElementLabelModel().setTreeModel(port2);
								mip.getElementLabelModel().setPortId(mip.getUMLDataModel().getId());
								mip.getElementLabelModel().setReadOnly(true);


							}
							else{
								//out
								EventOutputPortModel mip = new EventOutputPortModel();
								mip.getAttachElementLabelModel().setText(pp.name);
								mip.setType(pp.type);
								clm.getPorts().add(mip);
								UMLTreeModel port2 = new UMLTreeModel(pp.name);
								to1.addChild(port2);
								port2.setRefModel(mip);
								mip.getElementLabelModel().setTreeModel(port2);
								mip.getElementLabelModel().setPortId(mip.getUMLDataModel().getId());
								mip.getElementLabelModel().setReadOnly(true);

							}
						}
						else if(pp.portType==2){
							if("input".equals(pp.usage)){
								//in
								DataInputPortModel mip = new DataInputPortModel();
								mip.getAttachElementLabelModel().setText(pp.name);
								mip.setType(pp.type);
								clm.getPorts().add(mip);
								UMLTreeModel port2 = new UMLTreeModel(pp.name);
								to1.addChild(port2);
								port2.setRefModel(mip);
								mip.getElementLabelModel().setTreeModel(port2);
								mip.getElementLabelModel().setPortId(mip.getUMLDataModel().getId());
								mip.getElementLabelModel().setReadOnly(true);


							}
							else{
								//out
								DataOutputPortModel mip = new DataOutputPortModel();
								mip.getAttachElementLabelModel().setText(pp.name);
								mip.setType(pp.type);
								clm.getPorts().add(mip);
								UMLTreeModel port2 = new UMLTreeModel(pp.name);
								to1.addChild(port2);
								port2.setRefModel(mip);
								mip.getElementLabelModel().setTreeModel(port2);
								mip.getElementLabelModel().setPortId(mip.getUMLDataModel().getId());
								mip.getElementLabelModel().setReadOnly(true);

							}
						} 

					}

				}
				componentModel.setName(retModel.name);
				componentModel.setDesc(retModel.description);
				componentModel.setFile(child);
				componentModel.setVersion(retModel.version);
				componentModel.setId(retModel.id);
				if("".equals(pkg))
					this.getModelStore().put(componentModel.getName(), to1);
				else{
					this.getModelStore().put(pkg+"."+componentModel.getName(), to1);
					this.getModelStore().put(componentModel.getName(), to1);

				}
				HashMap hm = this.getModelStore();
				String dirKey = child.getPath();
				this.getDirModel().put(dirKey, to1);

				return componentModel;


			}
			int index = child.getName().lastIndexOf(".xml");
			String key = child.getName().substring(0,index);
			this.getEtcFile().put(key, child);
			return null;

		}
		return null;
	}
	
	
	//20110712서동민 ComponentLibModel ->>ComponentModel
	public ComponentModel addComponentModel(String name, UMLTreeParentModel tp,File child) {
		//		UMLTreeModel treeModel = parent.getUMLTreeModel().getParent();
		//default
		ComponentLibModel componentModel = null;

		if (tp != null && tp instanceof UMLTreeParentModel) {
			UMLTreeParentModel uMLTreeParentModel = (UMLTreeParentModel)tp;
			StrcuturedPackageTreeLibModel to1 = null;




			

			ComponentProfile retModel = this.makeModel(child,to1);
			if(retModel!=null){
				to1 = new StrcuturedPackageTreeLibModel(name);//2008040203 PKY S 
				componentModel = null;

				componentModel = new ComponentLibModel();
				componentModel.setRetModel(retModel);
				//				componentModel.setReadOnlyModel(true);
				if(retModel.fsm_name!=null && !retModel.fsm_name.trim().equals(""))
					componentModel.setFsmFileName(retModel.fsm_name);

				if(retModel.execution_environment_library_name!=null && !retModel.execution_environment_library_name.trim().equals(""))
					componentModel.setDllFileName(retModel.execution_environment_library_name);
				//                this.chk.add(to1);
				to1.setRefModel(componentModel);
				componentModel.setName(name);
				componentModel.setTreeModel(to1);
				to1.setParent(uMLTreeParentModel);
				uMLTreeParentModel.addChild(to1);
				String pkg = componentModel.getPackage();
				componentModel.getUMLDataModel().setElementProperty("cmp_profile", retModel);
				//ijs 091126
				//				componentModel.getClassModel().getElementLabelModelName().setReadOnly(true);
				//				componentModel.getClassModel().getElementLabelModelStreotype().setReadOnly(true);
				if(retModel instanceof CompositeComponentProfile){
					componentModel.setStereotype("composite");

					ComponentLibModel clm = (ComponentLibModel)componentModel;
					CompositeComponentProfile ccp = (CompositeComponentProfile)retModel;


					this.getCompositeCmp().add(clm);
					java.util.ArrayList list = new java.util.ArrayList();
					if(retModel.propetries.size()>0){
						for(int i=0;i<retModel.propetries.size();i++){
							Property p = (Property)retModel.propetries.get(i);
							list.add(p.getDetailPropertyTableItem());
						}
						clm.setTags(list);

					}
					for(int i=0;i<ccp.exportPorts.size();i++){
						ExportPort pp =(ExportPort)ccp.exportPorts.get(i);

						if("service".equals(pp.port_type)){
							if("required".equals(pp.port_dir)){
								//in
								MethodInputPortModel mip = new MethodInputPortModel();
								mip.getAttachElementLabelModel().setText(pp.export_port_name);
								clm.getPorts().add(mip);
								UMLTreeModel port = new UMLTreeModel(pp.export_port_name);
								port.setRefModel(mip);

								mip.getElementLabelModel().setTreeModel(port);
								to1.addChild(port);
								mip.getElementLabelModel().setPortId(mip.getUMLDataModel().getId());
								mip.getElementLabelModel().setReadOnly(true);

							}
							else{
								//out
								MethodOutputPortModel mip = new MethodOutputPortModel();
								mip.getAttachElementLabelModel().setText(pp.export_port_name);
								clm.getPorts().add(mip);
								UMLTreeModel port = new UMLTreeModel(pp.export_port_name);
								to1.addChild(port);
								port.setRefModel(mip);
								mip.getElementLabelModel().setTreeModel(port);
								mip.getElementLabelModel().setPortId(mip.getUMLDataModel().getId());
								mip.getElementLabelModel().setReadOnly(true);
							}
						}
						else if("event".equals(pp.port_type)){
							if("input".equals(pp.port_dir)){
								//in
								EventInputPortModel mip = new EventInputPortModel();
								mip.getAttachElementLabelModel().setText(pp.export_port_name);
								clm.getPorts().add(mip);
								UMLTreeModel port = new UMLTreeModel(pp.export_port_name);
								to1.addChild(port);
								port.setRefModel(mip);
								mip.getElementLabelModel().setTreeModel(port);
								mip.getElementLabelModel().setPortId(mip.getUMLDataModel().getId());
								mip.getElementLabelModel().setReadOnly(true);

							}
							else{
								//out
								EventOutputPortModel mip = new EventOutputPortModel();
								mip.getAttachElementLabelModel().setText(pp.export_port_name);
								clm.getPorts().add(mip);
								UMLTreeModel port = new UMLTreeModel(pp.export_port_name);
								to1.addChild(port);
								port.setRefModel(mip);
								mip.getElementLabelModel().setTreeModel(port);
								mip.getElementLabelModel().setPortId(mip.getUMLDataModel().getId());
								mip.getElementLabelModel().setReadOnly(true);
							}
						}
						else if("data".equals(pp.port_type)){
							if("input".equals(pp.port_dir)){
								//in
								DataInputPortModel mip = new DataInputPortModel();
								mip.getAttachElementLabelModel().setText(pp.export_port_name);
								clm.getPorts().add(mip);
								UMLTreeModel port = new UMLTreeModel(pp.export_port_name);
								to1.addChild(port);
								port.setRefModel(mip);
								mip.getElementLabelModel().setTreeModel(port);
								mip.getElementLabelModel().setPortId(mip.getUMLDataModel().getId());
								mip.getElementLabelModel().setReadOnly(true);

							}
							else{
								//out
								DataOutputPortModel mip = new DataOutputPortModel();
								mip.getAttachElementLabelModel().setText(pp.export_port_name);
								clm.getPorts().add(mip);
								UMLTreeModel port = new UMLTreeModel(pp.export_port_name);
								to1.addChild(port);
								port.setRefModel(mip);
								mip.getElementLabelModel().setTreeModel(port);
								mip.getElementLabelModel().setPortId(mip.getUMLDataModel().getId());
								mip.getElementLabelModel().setReadOnly(true);
							}
						} 

					}





				}
				else if(retModel instanceof ComponentProfile){					
					componentModel.setStereotype("atomic");
					ComponentLibModel clm = (ComponentLibModel)componentModel;
//					MonitoringMethodPortModel mmm = new MonitoringMethodPortModel();
//					clm.getPorts().add(mmm);
//					UMLTreeModel port = new UMLTreeModel("Monitoring");
//					to1.addChild(port);
//					port.setRefModel(mmm);
//					mmm.getElementLabelModel().setPortId(mmm.getUMLDataModel().getId());
//					mmm.setName("Monitoring");
//					LifecycleMethodPortModel lm = new LifecycleMethodPortModel();
//					clm.getPorts().add(lm);
//					UMLTreeModel port1 = new UMLTreeModel("Lifecycle");
//					to1.addChild(port1);
//					port1.setRefModel(lm);
//					lm.getElementLabelModel().setPortId(lm.getUMLDataModel().getId());
//					lm.setName("Lifecycle");
					java.util.ArrayList list = new java.util.ArrayList();
					if(retModel.propetries.size()>0){
						for(int i=0;i<retModel.propetries.size();i++){
							Property p = (Property)retModel.propetries.get(i);
							list.add(p.getDetailPropertyTableItem());
						}
						clm.setTags(list);

					}
					java.util.ArrayList list2 = new java.util.ArrayList();
					if(retModel.variables.size()>0){
						for(int i=0;i<retModel.variables.size();i++){
							Property p = (Property)retModel.variables.get(i);
							list2.add(p.getDetailPropertyTableItem());
						}
						clm.setMonitorVariables(list2);

					}

					for(int i=0;i<retModel.ports.size();i++){
						PortProfile pp =(PortProfile)retModel.ports.get(i);

						if(pp.portType==0){
							if("required".equals(pp.usage)){
								//in
								MethodInputPortModel mip = new MethodInputPortModel();
								mip.getAttachElementLabelModel().setText(pp.name);
								mip.setType(pp.type);
								clm.getPorts().add(mip);
								UMLTreeModel port2 = new UMLTreeModel(pp.name);
								port2.setRefModel(mip);

								mip.getElementLabelModel().setTreeModel(port2);
								mip.getElementLabelModel().setPortId(mip.getUMLDataModel().getId());
								to1.addChild(port2);
								mip.getElementLabelModel().setReadOnly(true);


							}
							else{
								//out
								MethodOutputPortModel mip = new MethodOutputPortModel();
								mip.getAttachElementLabelModel().setText(pp.name);
								mip.setType(pp.type);
								clm.getPorts().add(mip);
								UMLTreeModel port2 = new UMLTreeModel(pp.name);
								to1.addChild(port2);
								port2.setRefModel(mip);
								mip.getElementLabelModel().setTreeModel(port2);
								mip.getElementLabelModel().setPortId(mip.getUMLDataModel().getId());
								mip.getElementLabelModel().setReadOnly(true);

							}
						}
						else if(pp.portType==1){
							if("input".equals(pp.usage)){
								//in
								EventInputPortModel mip = new EventInputPortModel();
								mip.getAttachElementLabelModel().setText(pp.name);
								mip.setType(pp.type);
								clm.getPorts().add(mip);
								UMLTreeModel port2 = new UMLTreeModel(pp.name);
								to1.addChild(port2);
								port2.setRefModel(mip);
								mip.getElementLabelModel().setTreeModel(port2);
								mip.getElementLabelModel().setPortId(mip.getUMLDataModel().getId());
								mip.getElementLabelModel().setReadOnly(true);


							}
							else{
								//out
								EventOutputPortModel mip = new EventOutputPortModel();
								mip.getAttachElementLabelModel().setText(pp.name);
								mip.setType(pp.type);
								clm.getPorts().add(mip);
								UMLTreeModel port2 = new UMLTreeModel(pp.name);
								to1.addChild(port2);
								port2.setRefModel(mip);
								mip.getElementLabelModel().setTreeModel(port2);
								mip.getElementLabelModel().setPortId(mip.getUMLDataModel().getId());
								mip.getElementLabelModel().setReadOnly(true);

							}
						}
						else if(pp.portType==2){
							if("input".equals(pp.usage)){
								//in
								DataInputPortModel mip = new DataInputPortModel();
								mip.getAttachElementLabelModel().setText(pp.name);
								mip.setType(pp.type);
								clm.getPorts().add(mip);
								UMLTreeModel port2 = new UMLTreeModel(pp.name);
								to1.addChild(port2);
								port2.setRefModel(mip);
								mip.getElementLabelModel().setTreeModel(port2);
								mip.getElementLabelModel().setPortId(mip.getUMLDataModel().getId());
								mip.getElementLabelModel().setReadOnly(true);


							}
							else{
								//out
								DataOutputPortModel mip = new DataOutputPortModel();
								mip.getAttachElementLabelModel().setText(pp.name);
								mip.setType(pp.type);
								clm.getPorts().add(mip);
								UMLTreeModel port2 = new UMLTreeModel(pp.name);
								to1.addChild(port2);
								port2.setRefModel(mip);
								mip.getElementLabelModel().setTreeModel(port2);
								mip.getElementLabelModel().setPortId(mip.getUMLDataModel().getId());
								mip.getElementLabelModel().setReadOnly(true);

							}
						} 

					}

				}
				componentModel.setName(retModel.name);
				componentModel.setDesc(retModel.description);
				componentModel.setFile(child);
				componentModel.setVersion(retModel.version);
				componentModel.setId(retModel.id);
				componentModel.setexePriorityProp(retModel.execution_semantics_priority);	//111110 SDM - priority값 세팅
				
				if("".equals(pkg))
					this.getModelStore().put(componentModel.getName(), to1);
				else{
					this.getModelStore().put(pkg+"."+componentModel.getName(), to1);
					this.getModelStore().put(componentModel.getName(), to1);

				}
				HashMap hm = this.getModelStore();
				String dirKey = child.getPath();
				this.getDirModel().put(dirKey, to1);

				return componentModel;


			}
			int index = child.getName().lastIndexOf(".xml");
			String key = child.getName().substring(0,index);
			this.getEtcFile().put(key, child);
			return null;

		}
		return null;
	}
	
	public StrcuturedPackageTreeLibModel addModelComponentModel(File child) {
		TemplateComponentModel componentModel = null;
		StrcuturedPackageTreeLibModel to1 = null;//2008040203 PKY 
		ComponentProfile retModel = this.makeModel(child,null);
		if(retModel instanceof ComponentProfile){
			componentModel.setStereotype("atomic");
			ComponentLibModel clm = (ComponentLibModel)componentModel;
			componentModel.setName(retModel.name);
			componentModel.setDesc(retModel.description);
			componentModel.setFile(child);
			componentModel.setVersion(retModel.version);
			to1 = new StrcuturedPackageTreeLibModel(retModel.name);
			componentModel.setRetModel(retModel);
			componentModel.setTreeModel(to1);
	
		}
	


	
		
		return to1;
	}
	
	
	public TemplateComponentModel addTemplateComponentModel(String name, UMLTreeParentModel tp,File child) {
		//		UMLTreeModel treeModel = parent.getUMLTreeModel().getParent();
		//default

		TemplateComponentModel componentModel = null;

		if (tp != null && tp instanceof UMLTreeParentModel) {
			UMLTreeParentModel uMLTreeParentModel = (UMLTreeParentModel)tp;
			StrcuturedPackageTreeLibModel to1 = null;






			ComponentProfile retModel = this.makeModel(child,to1);
			
			
			if(retModel!=null && retModel instanceof ApplicationProfile){
				ApplicationProfile ap1 = (ApplicationProfile)retModel;
				ap1.parent = uMLTreeParentModel;
//				ap1.nd = ProjectManager.getInstance().addUMLDiagram(rt.getName()+" diagram", (UMLTreeParentModel)rt, null, 1, false,5);
				this.aps.add(ap1);
				return null;

			}
			else if(retModel!=null){
				to1 = new StrcuturedPackageTreeLibModel(name);//2008040203 PKY S 
				componentModel = null;

				componentModel = new TemplateComponentModel();
				componentModel.setFile(child);
				componentModel.setRetModel(retModel);
				//				componentModel.setReadOnlyModel(true);
				if(retModel.fsm_name!=null && !retModel.fsm_name.trim().equals(""))
					componentModel.setFsmFileName(retModel.fsm_name);

				if(retModel.execution_environment_library_name!=null && !retModel.execution_environment_library_name.trim().equals(""))
					componentModel.setDllFileName(retModel.execution_environment_library_name);
				//                this.chk.add(to1);
				to1.setRefModel(componentModel);
				componentModel.setName(name);
				componentModel.setTreeModel(to1);
				to1.setParent(uMLTreeParentModel);
				uMLTreeParentModel.addChild(to1);
				String pkg = componentModel.getPackage();
				componentModel.getUMLDataModel().setElementProperty("cmp_profile", retModel);
				//ijs 091126
				//				componentModel.getClassModel().getElementLabelModelName().setReadOnly(true);
				//				componentModel.getClassModel().getElementLabelModelStreotype().setReadOnly(true);
				if(retModel instanceof CompositeComponentProfile){
					componentModel.setStereotype("composite");

					ComponentLibModel clm = (ComponentLibModel)componentModel;
					CompositeComponentProfile ccp = (CompositeComponentProfile)retModel;


					this.getCompositeCmp().add(clm);
					java.util.ArrayList list = new java.util.ArrayList();
					if(retModel.propetries.size()>0){
						for(int i=0;i<retModel.propetries.size();i++){
							Property p = (Property)retModel.propetries.get(i);
							list.add(p.getDetailPropertyTableItem());
						}
						clm.setTags(list);

					}
					for(int i=0;i<ccp.exportPorts.size();i++){
						ExportPort pp =(ExportPort)ccp.exportPorts.get(i);

						if("service".equals(pp.port_type)){
							if("required".equals(pp.port_dir)){
								//in
								MethodInputPortModel mip = new MethodInputPortModel();
								mip.getAttachElementLabelModel().setText(pp.export_port_name);
								clm.getPorts().add(mip);
								UMLTreeModel port = new UMLTreeModel(pp.export_port_name);
								port.setRefModel(mip);

								mip.getElementLabelModel().setTreeModel(port);
								to1.addChild(port);
								mip.getElementLabelModel().setPortId(mip.getUMLDataModel().getId());
//								mip.setReference(p)
//								mip.setTypeRef(pp.)
								mip.getElementLabelModel().setReadOnly(true);

							}
							else{
								//out
								MethodOutputPortModel mip = new MethodOutputPortModel();
								mip.getAttachElementLabelModel().setText(pp.export_port_name);
								clm.getPorts().add(mip);
								UMLTreeModel port = new UMLTreeModel(pp.export_port_name);
								to1.addChild(port);
								port.setRefModel(mip);
								mip.getElementLabelModel().setTreeModel(port);
								mip.getElementLabelModel().setPortId(mip.getUMLDataModel().getId());
								mip.getElementLabelModel().setReadOnly(true);
							}
						}
						else if("event".equals(pp.port_type)){
							if("input".equals(pp.port_dir)){
								//in
								EventInputPortModel mip = new EventInputPortModel();
								mip.getAttachElementLabelModel().setText(pp.export_port_name);
								clm.getPorts().add(mip);
								UMLTreeModel port = new UMLTreeModel(pp.export_port_name);
								to1.addChild(port);
								port.setRefModel(mip);
								mip.getElementLabelModel().setTreeModel(port);
								mip.getElementLabelModel().setPortId(mip.getUMLDataModel().getId());
								mip.getElementLabelModel().setReadOnly(true);

							}
							else{
								//out
								EventOutputPortModel mip = new EventOutputPortModel();
								mip.getAttachElementLabelModel().setText(pp.export_port_name);
								clm.getPorts().add(mip);
								UMLTreeModel port = new UMLTreeModel(pp.export_port_name);
								to1.addChild(port);
								port.setRefModel(mip);
								mip.getElementLabelModel().setTreeModel(port);
								mip.getElementLabelModel().setPortId(mip.getUMLDataModel().getId());
								mip.getElementLabelModel().setReadOnly(true);
							}
						}
						else if("data".equals(pp.port_type)){
							if("input".equals(pp.port_dir)){
								//in
								DataInputPortModel mip = new DataInputPortModel();
								mip.getAttachElementLabelModel().setText(pp.export_port_name);
								clm.getPorts().add(mip);
								UMLTreeModel port = new UMLTreeModel(pp.export_port_name);
								to1.addChild(port);
								port.setRefModel(mip);
								mip.getElementLabelModel().setTreeModel(port);
								mip.getElementLabelModel().setPortId(mip.getUMLDataModel().getId());
								mip.getElementLabelModel().setReadOnly(true);

							}
							else{
								//out
								DataOutputPortModel mip = new DataOutputPortModel();
								mip.getAttachElementLabelModel().setText(pp.export_port_name);
								clm.getPorts().add(mip);
								UMLTreeModel port = new UMLTreeModel(pp.export_port_name);
								to1.addChild(port);
								port.setRefModel(mip);
								mip.getElementLabelModel().setTreeModel(port);
								mip.getElementLabelModel().setPortId(mip.getUMLDataModel().getId());
								mip.getElementLabelModel().setReadOnly(true);
							}
						} 

					}





				}
				else if(retModel instanceof ComponentProfile){
					componentModel.setStereotype("atomic");
					ComponentLibModel clm = (ComponentLibModel)componentModel;
//					MonitoringMethodPortModel mmm = new MonitoringMethodPortModel();
//					clm.getPorts().add(mmm);
//					UMLTreeModel port = new UMLTreeModel("Monitoring");
//					to1.addChild(port);
//					port.setRefModel(mmm);
//					mmm.getElementLabelModel().setPortId(mmm.getUMLDataModel().getId());
//					mmm.setName("Monitoring");
//					LifecycleMethodPortModel lm = new LifecycleMethodPortModel();
//					clm.getPorts().add(lm);
//					UMLTreeModel port1 = new UMLTreeModel("Lifecycle");
//					to1.addChild(port1);
//					port1.setRefModel(lm);
//					lm.getElementLabelModel().setPortId(lm.getUMLDataModel().getId());
//					lm.setName("Lifecycle");
					
					
					if(retModel.defined_service_types.size()>0){
						for(int i=0;i<retModel.defined_service_types.size();i++){
							String p = (String)retModel.defined_service_types.get(i);
							componentModel.defined_service_types.add(p);
						}
//						clm.setTags(list);

					}
					
					if(retModel.defined_data_types.size()>0){
						for(int i=0;i<retModel.defined_data_types.size();i++){
							String p = (String)retModel.defined_data_types.get(i);
							componentModel.defined_data_types.add(p);
						}
//						clm.setTags(list);

					}
					
					
					java.util.ArrayList list = new java.util.ArrayList();
					if(retModel.propetries.size()>0){
						for(int i=0;i<retModel.propetries.size();i++){
							Property p = (Property)retModel.propetries.get(i);
							list.add(p.getDetailPropertyTableItem());
						}
						clm.setTags(list);

					}
					java.util.ArrayList list2 = new java.util.ArrayList();
					if(retModel.variables.size()>0){
						for(int i=0;i<retModel.variables.size();i++){
							Property p = (Property)retModel.variables.get(i);
							list2.add(p.getDetailPropertyTableItem());
						}
						clm.setMonitorVariables(list2);

					}

					for(int i=0;i<retModel.ports.size();i++){
						PortProfile pp =(PortProfile)retModel.ports.get(i);

						if(pp.portType==0){
							if("required".equals(pp.usage)){
								//in
								MethodInputPortModel mip = new MethodInputPortModel();
								mip.getAttachElementLabelModel().setText(pp.name);
								mip.setType(pp.type);
								clm.getPorts().add(mip);
								UMLTreeModel port2 = new UMLTreeModel(pp.name);
								port2.setRefModel(mip);

								mip.getElementLabelModel().setTreeModel(port2);
								mip.getElementLabelModel().setPortId(mip.getUMLDataModel().getId());
								to1.addChild(port2);
								mip.setTypeRef(pp.reference);
								mip.setDesc(pp.desc);
								mip.setType(pp.type);
								mip.getElementLabelModel().setReadOnly(true);


							}
							else{
								//out
								MethodOutputPortModel mip = new MethodOutputPortModel();
								mip.getAttachElementLabelModel().setText(pp.name);
								mip.setType(pp.type);
								clm.getPorts().add(mip);
								UMLTreeModel port2 = new UMLTreeModel(pp.name);
								to1.addChild(port2);
								port2.setRefModel(mip);
								mip.getElementLabelModel().setTreeModel(port2);
								mip.getElementLabelModel().setPortId(mip.getUMLDataModel().getId());
								mip.setTypeRef(pp.reference);
								mip.setDesc(pp.desc);
								mip.setType(pp.type);
								mip.getElementLabelModel().setReadOnly(true);

							}
						}
						else if(pp.portType==1){
							if("input".equals(pp.usage)){
								//in
								EventInputPortModel mip = new EventInputPortModel();
								mip.getAttachElementLabelModel().setText(pp.name);
								mip.setType(pp.type);
								clm.getPorts().add(mip);
								UMLTreeModel port2 = new UMLTreeModel(pp.name);
								to1.addChild(port2);
								port2.setRefModel(mip);
								mip.getElementLabelModel().setTreeModel(port2);
								mip.getElementLabelModel().setPortId(mip.getUMLDataModel().getId());
								mip.setTypeRef(pp.reference);
								mip.setDesc(pp.desc);
								mip.setType(pp.type);
								mip.getElementLabelModel().setReadOnly(true);


							}
							else{
								//out
								EventOutputPortModel mip = new EventOutputPortModel();
								mip.getAttachElementLabelModel().setText(pp.name);
								mip.setType(pp.type);
								clm.getPorts().add(mip);
								UMLTreeModel port2 = new UMLTreeModel(pp.name);
								to1.addChild(port2);
								port2.setRefModel(mip);
								mip.getElementLabelModel().setTreeModel(port2);
								mip.getElementLabelModel().setPortId(mip.getUMLDataModel().getId());
								mip.setTypeRef(pp.reference);
								mip.setDesc(pp.desc);
								mip.setType(pp.type);
								mip.getElementLabelModel().setReadOnly(true);

							}
						}
						else if(pp.portType==2){
							if("input".equals(pp.usage)){
								//in
								DataInputPortModel mip = new DataInputPortModel();
								mip.getAttachElementLabelModel().setText(pp.name);
								mip.setType(pp.type);
								clm.getPorts().add(mip);
								UMLTreeModel port2 = new UMLTreeModel(pp.name);
								to1.addChild(port2);
								port2.setRefModel(mip);
								mip.getElementLabelModel().setTreeModel(port2);
								mip.getElementLabelModel().setPortId(mip.getUMLDataModel().getId());
								mip.setTypeRef(pp.reference);
								mip.setDesc(pp.desc);
								mip.setType(pp.type);
								mip.setQueueingPolicy(pp.queueing_policy);
								mip.setQueueSize(pp.queue_size);
								mip.getElementLabelModel().setReadOnly(true);


							}
							else{
								//out
								DataOutputPortModel mip = new DataOutputPortModel();
								mip.getAttachElementLabelModel().setText(pp.name);
								mip.setType(pp.type);
								clm.getPorts().add(mip);
								UMLTreeModel port2 = new UMLTreeModel(pp.name);
								to1.addChild(port2);
								port2.setRefModel(mip);
								mip.getElementLabelModel().setTreeModel(port2);
								mip.getElementLabelModel().setPortId(mip.getUMLDataModel().getId());
								mip.setTypeRef(pp.reference);
								mip.setDesc(pp.desc);
								mip.setType(pp.type);
								mip.setQueueingPolicy(pp.queueing_policy);
								mip.setQueueSize(pp.queue_size);
								mip.getElementLabelModel().setReadOnly(true);

							}
						} 

					}

				}
				componentModel.setName(retModel.name);
				componentModel.setDesc(retModel.description);
				componentModel.setFile(child);
				componentModel.setVersion(retModel.version);
				componentModel.setId(retModel.id);
				if("".equals(pkg))
					this.getModelStore().put(componentModel.getName(), to1);
				else{
					this.getModelStore().put(pkg+"."+componentModel.getName(), to1);
					this.getModelStore().put(componentModel.getName(), to1);

				}
				HashMap hm = this.getModelStore();
				String dirKey = child.getPath();
				this.getDirModel().put(dirKey, to1);

				return componentModel;


			}
			int index = child.getName().lastIndexOf(".xml");
			String key = child.getName().substring(0,index);
			this.getEtcFile().put(key, child);
			return null;

		}
		return null;
	}

	public ComponentLibModel addComponentModel2(String name, UMLTreeParentModel tp,File child) {//Khg 2010.05.26 임포트 어플리케이션

		ComponentLibModel componentModel = null;
		java.util.ArrayList mip1 = null;
		if (tp != null && tp instanceof UMLTreeParentModel) {
			UMLTreeParentModel uMLTreeParentModel = (UMLTreeParentModel)tp;
			StrcuturedPackageTreeModel to1 = null;
			ComponentProfile retModel = this.makeModel(child,to1);
			if(retModel!=null && retModel instanceof ApplicationProfile){
//				this.ap = (ApplicationProfile)retModel;
				this.aps.add(ap);
				return null;

			}
			else if(retModel!=null){
				to1 = new StrcuturedPackageTreeModel(name);//2008040203 PKY S 

				componentModel = null;

				componentModel = new ComponentLibModel();
				componentModel.setRetModel(retModel);
				//				componentModel.setReadOnlyModel(true);
				if(retModel.fsm_name!=null && !retModel.fsm_name.trim().equals(""))
					componentModel.setFsmFileName(retModel.fsm_name);

				if(retModel.execution_environment_library_name!=null && !retModel.execution_environment_library_name.trim().equals(""))
					componentModel.setDllFileName(retModel.execution_environment_library_name);
				//                this.chk.add(to1);
				to1.setRefModel(componentModel);
				//				nedm.addChild((ComponentModel)to1.getRefModel());
				componentModel.setName(name);
				componentModel.setTreeModel(to1);
				to1.setParent(uMLTreeParentModel);
				uMLTreeParentModel.addChild(to1);
				String pkg = componentModel.getPackage();
				componentModel.getUMLDataModel().setElementProperty("cmp_profile", retModel);
				//				componentModel.setReadOnlyModel(false);





				//ijs 091126
				//				componentModel.getClassModel().getElementLabelModelName().setReadOnly(true);
				//				componentModel.getClassModel().getElementLabelModelStreotype().setReadOnly(true);
				if(retModel instanceof CompositeComponentProfile && !(retModel instanceof ApplicationProfile)){
					componentModel.setStereotype("composite");

					ComponentLibModel clm = (ComponentLibModel)componentModel;
					CompositeComponentProfile ccp = (CompositeComponentProfile)retModel;

//					if()
					this.getCompositeCmp().add(clm);
					java.util.ArrayList list = new java.util.ArrayList();
					if(retModel.propetries.size()>0){
						for(int i=0;i<retModel.propetries.size();i++){
							Property p = (Property)retModel.propetries.get(i);
							list.add(p.getDetailPropertyTableItem());
						}
						clm.setTags(list);

					}
					for(int i=0;i<ccp.exportPorts.size();i++){
						ExportPort pp =(ExportPort)ccp.exportPorts.get(i);

						if("service".equals(pp.port_type)){
							if("required".equals(pp.port_dir)){
								//in
								MethodInputPortModel mip = new MethodInputPortModel();
								mip.getAttachElementLabelModel().setText(pp.export_port_name);
								clm.getPorts().add(mip);
								UMLTreeModel port = new UMLTreeModel(pp.export_port_name);
								port.setRefModel(mip);


								mip.getElementLabelModel().setTreeModel(port);
								to1.addChild(port);
								mip.getElementLabelModel().setPortId(mip.getUMLDataModel().getId());
								mip.getElementLabelModel().setReadOnly(true);
								//								mip.getElementLabelModel().setParentModel((UMLModel)to1.getRefModel());
								//								mip.getElementLabelModel().setName(pp.port_name);

								//								mip.setName(pp.port_name);
								//								nedm.addChild(mip);
								//								componentModel.addChild(mip);
							}
							else{
								//out
								MethodOutputPortModel mip = new MethodOutputPortModel();
								mip.getAttachElementLabelModel().setText(pp.export_port_name);
								clm.getPorts().add(mip);
								UMLTreeModel port = new UMLTreeModel(pp.export_port_name);
								to1.addChild(port);
								port.setRefModel(mip);

								mip.getElementLabelModel().setTreeModel(port);
								mip.getElementLabelModel().setPortId(mip.getUMLDataModel().getId());
								mip.getElementLabelModel().setReadOnly(true);
								//								mip.getElementLabelModel().setParentModel((UMLModel)to1.getRefModel());
								//								mip.getElementLabelModel().setName(pp.port_name);
								//								mip.setName(pp.port_name);
								//								nedm.addChild(mip);
								//								componentModel.addChild(mip);
							}
						}
						else if("event".equals(pp.port_type)){
							if("input".equals(pp.port_dir)){
								//in
								EventInputPortModel mip = new EventInputPortModel();
								mip.getAttachElementLabelModel().setText(pp.export_port_name);
								clm.getPorts().add(mip);
								UMLTreeModel port = new UMLTreeModel(pp.export_port_name);
								to1.addChild(port);
								port.setRefModel(mip);

								mip.getElementLabelModel().setTreeModel(port);
								mip.getElementLabelModel().setPortId(mip.getUMLDataModel().getId());
								mip.getElementLabelModel().setReadOnly(true);
								//								mip.getElementLabelModel().setParentModel((UMLModel)to1.getRefModel());
								//								mip.getElementLabelModel().setName(pp.port_name);
								//								mip.setName(pp.port_name);
								//								nedm.addChild(mip);
								//								componentModel.addChild(mip);
							}
							else{
								//out
								EventOutputPortModel mip = new EventOutputPortModel();
								mip.getAttachElementLabelModel().setText(pp.export_port_name);
								clm.getPorts().add(mip);
								UMLTreeModel port = new UMLTreeModel(pp.export_port_name);
								to1.addChild(port);
								port.setRefModel(mip);

								mip.getElementLabelModel().setTreeModel(port);
								mip.getElementLabelModel().setPortId(mip.getUMLDataModel().getId());
								mip.getElementLabelModel().setReadOnly(true);
								//								mip.getElementLabelModel().setParentModel((UMLModel)to1.getRefModel());
								//								mip.getElementLabelModel().setName(pp.port_name);
								//								mip.setName(pp.port_name);
								//								nedm.addChild(mip);
								//								componentModel.addChild(mip);
							}
						}
						else if("data".equals(pp.port_type)){
							if("input".equals(pp.port_dir)){
								//in
								DataInputPortModel mip = new DataInputPortModel();
								mip.getAttachElementLabelModel().setText(pp.export_port_name);
								clm.getPorts().add(mip);
								UMLTreeModel port = new UMLTreeModel(pp.export_port_name);

								to1.addChild(port);
								port.setRefModel(mip);

								mip.getElementLabelModel().setTreeModel(port);
								mip.getElementLabelModel().setPortId(mip.getUMLDataModel().getId());
								mip.getElementLabelModel().setReadOnly(true);
								//								mip.getElementLabelModel().setParentModel((UMLModel)to1.getRefModel());
								//								mip.getElementLabelModel().setName(pp.port_name);
								//								mip.setName(pp.port_name);
								//								nedm.addChild(mip);
								//								componentModel.addChild(mip);
							}
							else{
								//out
								DataOutputPortModel mip = new DataOutputPortModel();
								mip.getAttachElementLabelModel().setText(pp.export_port_name);
								clm.getPorts().add(mip);
								UMLTreeModel port = new UMLTreeModel(pp.export_port_name);
								to1.addChild(port);
								port.setRefModel(mip);

								mip.getElementLabelModel().setTreeModel(port);
								mip.getElementLabelModel().setPortId(mip.getUMLDataModel().getId());
								mip.getElementLabelModel().setReadOnly(true);
								//								mip.getElementLabelModel().setParentModel((UMLModel)to1.getRefModel());
								//								mip.getElementLabelModel().setName(pp.port_name);
								//								mip.setName(pp.port_name);
								//								nedm.addChild(mip);
								//								componentModel.addChild(mip);
							}
						} 

					}





				}
				else if(retModel instanceof ComponentProfile){

					componentModel.setStereotype("atomic");
					ComponentLibModel clm = (ComponentLibModel)componentModel;
					MonitoringMethodPortModel mmm = new MonitoringMethodPortModel();
					clm.getPorts().add(mmm);
					UMLTreeModel port = new UMLTreeModel("Monitoring");
					to1.addChild(port);
					port.setRefModel(mmm);
					mmm.getElementLabelModel().setPortId(mmm.getUMLDataModel().getId());
					mmm.setName("Monitoring");
					LifecycleMethodPortModel lm = new LifecycleMethodPortModel();
					clm.getPorts().add(lm);
					UMLTreeModel port1 = new UMLTreeModel("Lifecycle");
					to1.addChild(port1);

					port1.setRefModel(lm);
					lm.getElementLabelModel().setPortId(lm.getUMLDataModel().getId());
					lm.setName("Lifecycle");
					java.util.ArrayList list = new java.util.ArrayList();
					if(retModel.propetries.size()>0){
						for(int i=0;i<retModel.propetries.size();i++){
							Property p = (Property)retModel.propetries.get(i);
							list.add(p.getDetailPropertyTableItem());
						}
						clm.setTags(list);

					}

					for(int i=0;i<retModel.ports.size();i++){
						PortProfile pp =(PortProfile)retModel.ports.get(i);

						if(pp.portType==0){
							if("required".equals(pp.usage)){
								//in
								MethodInputPortModel mip = new MethodInputPortModel();
								mip.getAttachElementLabelModel().setText(pp.name);
								mip.setType(pp.type);
								clm.getPorts().add(mip);
								UMLTreeModel port2 = new UMLTreeModel(pp.name);
								port2.setRefModel(mip);


								mip.getElementLabelModel().setTreeModel(port2);
								mip.getElementLabelModel().setPortId(mip.getUMLDataModel().getId());
								to1.addChild(port2);

								mip.getElementLabelModel().setReadOnly(true);
								//								mip.getElementLabelModel().setParentModel((UMLModel)to1.getRefModel());
								//								mip.getElementLabelModel().setName(pp.name);
								//								mip.setName(pp.name);
								//								
								//								nedm.addChild(mip);
								//								componentModel.addChild(mip);

							}
							else{
								//out
								MethodOutputPortModel mip = new MethodOutputPortModel();
								mip.getAttachElementLabelModel().setText(pp.name);
								mip.setType(pp.type);
								clm.getPorts().add(mip);
								UMLTreeModel port2 = new UMLTreeModel(pp.name);
								to1.addChild(port2);
								port2.setRefModel(mip);

								mip.getElementLabelModel().setTreeModel(port2);
								mip.getElementLabelModel().setPortId(mip.getUMLDataModel().getId());
								mip.getElementLabelModel().setReadOnly(true);
								//								mip.getElementLabelModel().setParentModel((UMLModel)to1.getRefModel());
								//								mip.getElementLabelModel().setName(pp.name);
								//								mip.setName(pp.name);
								//								nedm.addChild(mip);
								//								mip1.add((PortModel)mip);
								//								componentModel.addChild(mip);
							}
						}
						else if(pp.portType==1){
							if("input".equals(pp.usage)){
								//in
								EventInputPortModel mip = new EventInputPortModel();
								mip.getAttachElementLabelModel().setText(pp.name);
								mip.setType(pp.type);
								clm.getPorts().add(mip);
								UMLTreeModel port2 = new UMLTreeModel(pp.name);
								to1.addChild(port2);
								port2.setRefModel(mip);

								mip.getElementLabelModel().setTreeModel(port2);
								mip.getElementLabelModel().setPortId(mip.getUMLDataModel().getId());
								mip.getElementLabelModel().setReadOnly(true);
								//								mip.getElementLabelModel().setParentModel((UMLModel)to1.getRefModel());
								//								mip.getElementLabelModel().setName(pp.name);
								//								mip.setName(pp.name);
								//								nedm.addChild(mip);
								//								componentModel.addChild(mip);

							}
							else{
								//out
								EventOutputPortModel mip = new EventOutputPortModel();
								mip.getAttachElementLabelModel().setText(pp.name);
								mip.setType(pp.type);
								clm.getPorts().add(mip);
								UMLTreeModel port2 = new UMLTreeModel(pp.name);
								to1.addChild(port2);
								port2.setRefModel(mip);

								mip.getElementLabelModel().setTreeModel(port2);
								mip.getElementLabelModel().setPortId(mip.getUMLDataModel().getId());
								mip.getElementLabelModel().setReadOnly(true);
								//								mip.getElementLabelModel().setParentModel((UMLModel)to1.getRefModel());
								//								mip.getElementLabelModel().setName(pp.name);
								//								mip.setName(pp.name);
								//								nedm.addChild(mip);
								//								componentModel.addChild(mip);
							}
						}
						else if(pp.portType==2){
							if("input".equals(pp.usage)){
								//in
								DataInputPortModel mip = new DataInputPortModel();
								mip.getAttachElementLabelModel().setText(pp.name);
								mip.setType(pp.type);
								clm.getPorts().add(mip);
								UMLTreeModel port2 = new UMLTreeModel(pp.name);
								to1.addChild(port2);

								port2.setRefModel(mip);
								mip.getElementLabelModel().setTreeModel(port2);
								mip.getElementLabelModel().setPortId(mip.getUMLDataModel().getId());
								mip.getElementLabelModel().setReadOnly(true);
								//								mip.getElementLabelModel().setParentModel((UMLModel)to1.getRefModel());
								//								mip.getElementLabelModel().setName(pp.name);
								//								mip.setName(pp.name);
								//								nedm.addChild(mip);
								//								componentModel.addChild(mip);

							}
							else{
								//out
								DataOutputPortModel mip = new DataOutputPortModel();
								mip.getAttachElementLabelModel().setText(pp.name);
								mip.setType(pp.type);
								clm.getPorts().add(mip);
								UMLTreeModel port2 = new UMLTreeModel(pp.name);
								to1.addChild(port2);

								port2.setRefModel(mip);
								mip.getElementLabelModel().setTreeModel(port2);
								mip.getElementLabelModel().setPortId(mip.getUMLDataModel().getId());
								mip.getElementLabelModel().setReadOnly(true);
								//								mip.getElementLabelModel().setParentModel((UMLModel)to1.getRefModel());
								//								mip.getElementLabelModel().setName(pp.name);
								//								mip.setName(pp.name);
								//								nedm.addChild(mip);
								//								componentModel.addChild(mip);
							}
						} 

					}

				}
				componentModel.setName(retModel.name);
				componentModel.setDesc(retModel.description);
				componentModel.setFile(child);
				componentModel.setVersion(retModel.version);
				componentModel.setId(retModel.id);
				if("".equals(pkg))
					this.getModelStore().put(componentModel.getName(), to1);
				else{
					this.getModelStore().put(pkg+"."+componentModel.getName(), to1);
					this.getModelStore().put(componentModel.getName(), to1);

				}
				HashMap hm = this.getModelStore();
				String dirKey = child.getPath();
				this.getDirModel().put(dirKey, to1);
				this.getImportViews().put(componentModel.getName(), to1);
				//				UMLTreeModel[] childList= tp.getChildren();
				UMLTreeModel[] childList2=to1.getChildren();
				//	componentModel.addChild((UMLElementModel)childList2[0].getRefModel());



				return componentModel;


			}
			int index = child.getName().lastIndexOf(".xml");
			String key = child.getName().substring(0,index);
			this.getEtcFile().put(key, child);

			return null;

		}
		return null;
	}


	public HashMap getModelStore() {
		return modelStore;
	}
	public void setModelStore(HashMap modelStore) {
		this.modelStore = modelStore;
	}
	public HashMap getViewStore() {
		return viewStore;
	}
	public void setViewStore(HashMap viewStore) {
		this.viewStore = viewStore;
	}

	public void makeCompositeCmp(File file){

	}


	public void autoLayoutPort(java.util.ArrayList ports) {
		int defalut = 0;
		int methodCount = 0;
		int evnetCount = 0;
		int dataCount = 0;
		for(int i=0;i<ports.size();i++){
			PortModel pm = (PortModel)ports.get(i);

			if(pm instanceof MethodOutputPortModel){
				++methodCount;
			}
			else if(pm instanceof MethodInputPortModel){
				++methodCount;
			}
			else if(pm instanceof EventInputPortModel){
				++evnetCount;
			}
			else if(pm instanceof EventOutputPortModel){
				++evnetCount;
			}
			else if(pm instanceof DataInputPortModel){
				++dataCount;
			}
			else if(pm instanceof DataOutputPortModel){
				++dataCount;
			}


		}

		for(int i=0;i<ports.size();i++){
			PortModel pm = (PortModel)ports.get(i);

			if(pm instanceof MonitoringMethodPortModel){
				UMLModel um = (UMLModel)pm.getPortContainerModel();
				int x = um.getLocation().x;
				int y = um.getLocation().y;
				int cmw = um.getSize().width;
				int cmh = um.getSize().height;
				int  width = pm.getSize().width;
				int  height = pm.getSize().height;
				pm.setLocation(new Point(x+cmw,y+10));
			}
			if(pm instanceof LifecycleMethodPortModel){
				UMLModel um = (UMLModel)pm.getPortContainerModel();
				int x = um.getLocation().x;
				int y = um.getLocation().y;
				int cmw = um.getSize().width;
				int cmh = um.getSize().height;
				int  width = pm.getSize().width;
				int  height = pm.getSize().height;
				pm.setLocation(new Point(x+cmw,y+height+10));
			}
		}
		//    	boolean isTop = false;
		int top = 0;
		int right = 0;
		int bottom = 0;
		UMLModel um = null;
		//		int height = 
		int max  = 0;
		for(int i=0;i<ports.size();i++){
			PortModel pm = (PortModel)ports.get(i);
			if(pm instanceof MonitoringMethodPortModel)
				continue;
			if(pm instanceof LifecycleMethodPortModel)
				continue;
			//    		if(pm instanceof MethodOutputPortModel){
			um = (UMLModel)pm.getPortContainerModel();
			int x = um.getLocation().x;
			int y = um.getLocation().y;
			int  width = pm.getSize().width;
			int  height = pm.getSize().height;
			if(top==0){
				pm.setLocation(new Point(um.getLocation().x+10,um.getLocation().y));
				top++;
			}
//			else if(i==ports.size()-1){
//				pm.setLocation(new Point(um.getLocation().x+10,um.getLocation().y+um.getSize().height));
//				bottom++;
//
//			}
			else{
				pm.setLocation(new Point(um.getLocation().x-pm.getSize().height,um.getLocation().y+right*30+10));
				right++;
				if(um.getLocation().y+right*30+15>y+um.getSize().height){
					//					um.setSize(new Dimension(width,y-um.getLocation().y+right*pm.getSize().width+15));
					max = um.getLocation().y+right*30+15-y;
				}


			}


			//    		}

		}

		if(right>2 && max>um.getSize().height){
			if(um!=null){
				um.setSize(new Dimension(um.getSize().width,max));
			}
		}

	}

	public HashMap getDllFile() {
		return dllFile;
	}

	public void setDllFile(HashMap dllFile) {
		this.dllFile = dllFile;
	}

	public HashMap getEtcFile() {
		return etcFile;
	}

	public void setEtcFile(HashMap etcFile) {
		this.etcFile = etcFile;
	}

	public java.util.ArrayList getCompositeCmp() {
		return compositeCmp;
	}

	public void setCompositeCmp(java.util.ArrayList compositeCmp) {
		this.compositeCmp = compositeCmp;
	}

	public HashMap getDirModel() {
		return dirModel;
	}

	public void setDirModel(HashMap dirModel) {
		this.dirModel = dirModel;
	}

	public HashMap getDirView() {
		return dirView;
	}

	public void setDirView(HashMap dirView) {
		this.dirView = dirView;
	}

	public java.util.ArrayList getViewInfos() {
		return viewInfos;
	}

	public void setViewInfos(java.util.ArrayList viewInfos) {
		this.viewInfos = viewInfos;
	}



	public void run(IProgressMonitor monitor) throws InvocationTargetException,
	InterruptedException {

		this.monitor = monitor;
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			//			[출처] swt Invalid thread access 날때 대처법|작성자 곰탱이

			public void run(){
				//		this.monitor = monitor;


				if(runType==1){
					CompAssemManager.getInstance().monitor.beginTask("Loading library Total",
							true ? IProgressMonitor.UNKNOWN : TOTAL_TIME);
					makeLib(null);
					//ijs TarManager -2 
//					makeTar(null,null);
				}
				else if(runType==2){
					CompAssemManager.getInstance().monitor.beginTask("Export Application",
							true ? IProgressMonitor.UNKNOWN : TOTAL_TIME);
					//111027 SDM S - DLL없으면 타르 생성 안함
					boolean bCopy = makeCopyMain(null, null);
					if(bCopy){
						//ijs TarManager -2 
						if(NetManager.getInstance().isJar)
						makeTar(null,null);
					}
					else{
						MessageBox dialog = new MessageBox(ProjectManager.getInstance().window.getShell(),SWT.OK|SWT.ICON_ERROR);
						dialog.setText("Message");
						dialog.setMessage("Can not distribute because Component that there is no DLL file exists.");
						dialog.open();
					}
					//111027 SDM E - DLL없으면 타르 생성 안함
				}
				else if(runType==3){
					CompAssemManager.getInstance().monitor.beginTask("Export Composite",
							true ? IProgressMonitor.UNKNOWN : TOTAL_TIME);
					//111027 SDM S - DLL없으면 타르 생성 안함
					boolean bCopy = makeCopyMain(null, null);
					if(bCopy){
						//ijs TarManager -2 
						if(NetManager.getInstance().isJar)
						makeTar(null,null);
					}
					else{
						MessageBox dialog = new MessageBox(ProjectManager.getInstance().window.getShell(),SWT.OK|SWT.ICON_ERROR);
						dialog.setText("Message");
						dialog.setMessage("Can not distribute because Component that there is no DLL file exists.");
						dialog.open();
					}
					//111027 SDM E - DLL없으면 타르 생성 안함
				}
				else if(runType==4){

					CompAssemManager.getInstance().monitor.beginTask("Loading library",
							true ? IProgressMonitor.UNKNOWN : TOTAL_TIME);
					makeLib2(rpt);
				}
				else if(runType==5){//Khg 2010.05.26 임포트 어플리케이션

					CompAssemManager.getInstance().monitor.beginTask("Loading Template",
							true ? IProgressMonitor.UNKNOWN : TOTAL_TIME);
					makeApplication();
					//makeTemplateComponent();
				}
				else if(runType==6){//Khg 2010.05.26 임포트 어플리케이션

					CompAssemManager.getInstance().monitor.beginTask("Import Application",
							true ? IProgressMonitor.UNKNOWN : TOTAL_TIME);
					
//					getPmMake()
//					makeTaskModel();
//					makeApplication();
					//makeTemplateComponent();
				}
				else if(runType==7){
					CompAssemManager.getInstance().monitor.beginTask("Re",
							true ? IProgressMonitor.UNKNOWN : TOTAL_TIME);
					makeCompEdit(null);
				}
				else if(runType==8){
					CompAssemManager.getInstance().monitor.beginTask("Re",
							true ? IProgressMonitor.UNKNOWN : TOTAL_TIME);
					RootCmpEdtTreeModel rl = null;
					String str = "D:\\etri\\runtime-EclipseApplication(1)";

						File dirFile = new File(str);
						rl = rcem;
						boolean isRoot = true;
						recurseCmpEdtRef(dirFile, rl,isRoot);
				}

				//   for (int total = 0; total < TOTAL_TIME && !monitor.isCanceled(); total += INCREMENT) {
				//     Thread.sleep(INCREMENT);
				//     monitor.worked(INCREMENT);
				//     if (total == TOTAL_TIME / 2) monitor.subTask("Doing second half");
				//   }
				CompAssemManager.getInstance().monitor.done();
//				if (CompAssemManager.getInstance().monitor.isCanceled()){
//					throw new InterruptedException("The long running operation was cancelled");
//				}
			}
		});
	}

	public boolean isBegin() {
		return isBegin;
	}

	public void setBegin(boolean isBegin) {
		this.isBegin = isBegin;
	}

	public void getRun(){

	}

	public RootLibTreeModel getRtm() {
		return rtm;
	}

	public void setRtm(RootLibTreeModel rtm) {
		this.rtm = rtm;
	}
	
	public RootCmpEdtTreeModel getRcem() {
		return rcem;
	}
	public void setRcem(RootCmpEdtTreeModel rcem) {
		this.rcem = rcem;
		System.out.println(rcem);
	}
	
	//20110808서동민
	public RootCmpFnshTreeModel getRcefm() {
		return rcefm;
	}
	public void setRcefm(RootCmpFnshTreeModel rcefm) {
		this.rcefm = rcefm;
	}
	
	public AtomicComponentModel getAtmc() {
		return atmc;
	}
	//
	public void setAtmc(AtomicComponentModel atmc) {
		this.atmc = atmc;
	}

	public int getRunType() {
		return runType;
	}

	public void setRunType(int runType) {
		this.runType = runType;
	}

	public UMLTreeParentModel getUmMake() {
		return umMake;
	}

	public void setUmMake(UMLTreeParentModel umMake) {
		this.umMake = umMake;
	}

	public UMLTreeParentModel getPmMake() {//khg 2010.05.26 import Application
		return pmMake;
	}

	public void setPmMake(UMLTreeParentModel pmMake) {//khg 2010.05.26 import Application
		this.pmMake = pmMake;
	}

	public String getDpath() {//khg 2010.05.26 import Application
		return dpath;
	}

	public void setDpath(String dpath) {//khg 2010.05.26 import Application
		this.dpath = dpath;
	}

	public File getTargetMake() {
		return targetMake;
	}

	public void setTargetMake(File targetMake) {
		this.targetMake = targetMake;
	}

	public boolean isImport() {
		return isImport;
	}

	public void setImport(boolean isImport) {
		this.isImport = isImport;
	}

	public static void deleteFilesAndDirs(String path) {
		deleteFiles(path);
		deleteDirs(path);
	}
	//ijs TarManager -2 
	public static void deleteFilesAndDirs2(String path) {
		deleteFiles(path);
		deleteDirs2(path);
	}

	public static void deleteFiles(String path) {
		File file = new File(path);
		File[] files = file.listFiles();

		if (files == null) {
			return;
		}

		if (files.length != 0) {
			for (int i =  0; i < files.length; i++) {
				if (files[i].isFile()) {
					files[i].delete();
					System.out.println(files[i].getName() + " is Delete!");
				} else {
					deleteFiles(files[i].getPath());
					System.out.println("Call the Recursive 'deleteFiles' Method..");
				}
			}
		}
	}

	public static void deleteDirs(String path) {

		File dir = new File(path);
		File[] dirs = dir.listFiles();
		//        if("c:\\comp_temp".equals(path)){
		//        	return;
		//        }
		if (dirs == null) {
			return;
		}

		if (dirs.length != 0) {
			for (int i = 0; i < dirs.length; i++) {
				if (dirs[i].listFiles()!=null && dirs[i].listFiles().length == 0) {
					if(!(strWorkDir + "comp_temp").equals(dirs[i].getPath()))
						dirs[i].delete();
					System.out.println(dirs[i].getName() + " is Delete!");
				} else {
					deleteDirs(dirs[i].getPath());
					System.out.println("Call the Recursive 'deletedirs' Method..");
				}
			}
		}
		//		if("c:\\comp_temp")
		if(!(strWorkDir + "comp_temp").equals(path))
			dir.delete();
		System.out.println(dir.getName() + " is Delete!");
	}
	
	//ijs TarManager -2 
	public static void deleteDirs2(String path) {

		File dir = new File(path);
		File[] dirs = dir.listFiles();
		//        if("c:\\comp_temp".equals(path)){
		//        	return;
		//        }
		if (dirs == null) {
			return;
		}

		if (dirs.length != 0) {
			for (int i = 0; i < dirs.length; i++) {
				if (dirs[i].listFiles()!=null && dirs[i].listFiles().length == 0) {
//					if(!"c:\\comp_temp".equals(dirs[i].getPath()))
						dirs[i].delete();
					System.out.println(dirs[i].getName() + " is Delete!");
				} else {
					deleteDirs(dirs[i].getPath());
					System.out.println("Call the Recursive 'deletedirs' Method..");
				}
			}
		}
		//		if("c:\\comp_temp")
//		if(!"c:\\comp_temp".equals(path))
			dir.delete();
		System.out.println(dir.getName() + " is Delete!");
	}

	public void saveInstanceFile(File src , File desc,HashMap map){
		Document doc;

		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setIgnoringElementContentWhitespace(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			doc = builder.parse(src);
			Element root = doc.getDocumentElement();
			NodeList nl =root.getChildNodes();
			boolean isMakeXml = false;

			for(int i=0;i<nl.getLength();i++){
				Node element = (Node) nl.item(i);
				System.out.println(element.getNodeName()+element.getTextContent());
				if("name".equals(element.getNodeName())){
					String name = (String)map.get("name");
					if(name!=null && !"".equals(name)){
						String xmlName = element.getTextContent();
						if(xmlName.trim().equals(name)){
							//							isMakeXml = false;
							//							break;
						}
						else{ 
							element.setTextContent(name);
							isMakeXml = true;
						}
					}

				}
				else if("id".equals(element.getNodeName())){
					String id = (String)map.get("id");
					if(id!=null && !"".equals(id)){
						String xmlName = element.getTextContent();
						if(xmlName.trim().equals(id)){
							//							isMakeXml = false;
							//							break;
						}
						else{ 
							element.setTextContent(id);
							isMakeXml = true;
						}
					}

				}
				else if("description".equals(element.getNodeName())){
					String description = (String)map.get("description");
					if(description!=null && !"".equals(description)){
						String xmlName = element.getTextContent();
						if(xmlName.trim().equals(description)){
							//							isMakeXml = false;
							//							break;
						}
						else{ 
							element.setTextContent(description);
							isMakeXml = true;
						}
					}

				}

			}

			if(isMakeXml){
				TransformerFactory tFactory = TransformerFactory.newInstance();
				Transformer transformer = tFactory.newTransformer();
				BufferedWriter out = new BufferedWriter(new FileWriter(desc));
				Properties props = new Properties();
				props.setProperty("indent", "yes");
				transformer.setOutputProperties(props);
				transformer.transform(new DOMSource(doc), new StreamResult(System.out));
				transformer.transform(new DOMSource(doc), new StreamResult(out));
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
	}


	public void copyInstanceFile(File src , File desc,HashMap map){
		Document doc;
		BufferedWriter out = null;
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setIgnoringElementContentWhitespace(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			doc = builder.parse(src);
			Element root = doc.getDocumentElement();
			NodeList nl =root.getChildNodes();
			Node ports = null;
			//			boolean isMakeXml = true;
			boolean isProperties = false; 

			for(int i=0;i<nl.getLength();i++){
				Node element = (Node) nl.item(i);
				System.out.println(element.getNodeName()+element.getTextContent());

				if("name".equals(element.getNodeName())){
					String name = (String)map.get("name");
					if(name!=null && !"".equals(name)){
						String xmlName = element.getTextContent();
						element.setTextContent(name);
					}

				}
				if("ports".equals(element.getNodeName())){
					ports = element;

				}
				else if("id".equals(element.getNodeName())){
					String id = (String)map.get("id");
					if(id!=null && !"".equals(id)){
						String xmlName = element.getTextContent();

						element.setTextContent(id);


					}

				}
				else if("description".equals(element.getNodeName())){
					String description = (String)map.get("description");
					if(description!=null && !"".equals(description)){
						String xmlName = element.getTextContent();

						element.setTextContent(description);

					}

				}
				else if("properties".equals(element.getNodeName())){
					System.out.println("dddddddddddddd");
					java.util.ArrayList items = new java.util.ArrayList();
					isProperties = true;

					for(int i1=0;i1<element.getChildNodes().getLength();i1++){
						Node child = (Node) element.getChildNodes().item(i1);
						System.out.println("ddddddddddddd:"+child.getNodeName());
						items.add(child);
						//						if("property".equals(child.getNodeName())){
						//							element.removeChild(child);
						//						}


						//						if(child.getNextSibling()!=null){
						//							element.removeChild(child.getNextSibling());
						//							
						//						}
						//						if(child.getTextContent().equals("\\n")){
						//							element.removeChild(child);
						//						}

					}

					for(int i1=0;i1<items.size();i1++){
						Node child = (Node) items.get(i1);
						element.removeChild(child);
					}

					//					for(int i1=0;i1<element.getChildNodes().getLength();i1++){
					//						Node child = (Node) element.getChildNodes().item(i1);
					//						System.out.println("ddddddddddddd:"+child.getNodeName());
					////						if("property".equals(child.getNodeName())){
					//							element.removeChild(child);
					////						}
					//						
					//
					////						if(child.getNextSibling()!=null){
					////							element.removeChild(child.getNextSibling());
					////							
					////						}
					////						if(child.getTextContent().equals("\\n")){
					////							element.removeChild(child);
					////						}
					//						
					//					}

					java.util.ArrayList list = (java.util.ArrayList)map.get("properties");
					if(list!=null){

						//						ElementImpl di = new ElementImpl(element,0);

						for(int i2=0;i2<list.size();i2++){
							DetailPropertyTableItem dpt = (DetailPropertyTableItem)list.get(i2);
							Element de =doc.createElement("property");

							de.setAttribute("name", dpt.tapName);
							de.setAttribute("type", dpt.sTagType);
							de.setTextContent(dpt.desc);
							//						de.setNodeValue(dpt.desc);
							element.appendChild(de);
							//						Node child = new Node();
							//						

						}
					}


				}
			}
			java.util.ArrayList list = (java.util.ArrayList)map.get("properties");
			if(!isProperties && list!=null && list.size()>0){
				Element dep =doc.createElement("properties");
				for(int i2=0;i2<list.size();i2++){
					DetailPropertyTableItem dpt = (DetailPropertyTableItem)list.get(i2);
					Element de =doc.createElement("property");

					de.setAttribute("name", dpt.tapName);
					de.setAttribute("type", dpt.sTagType);
					de.setTextContent(dpt.desc);
					//					de.setNodeValue(dpt.desc);
					dep.appendChild(de);
					//					Node child = new Node();
					//					

				}
				if(ports!=null)
					root.insertBefore(dep, ports);
				else
					root.appendChild(dep);

			}

			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer transformer = tFactory.newTransformer();
			out = new BufferedWriter(new FileWriter(desc));
			Properties props = new Properties();
			props.setProperty("indent", "yes");
			transformer.setOutputProperties(props);
			transformer.transform(new DOMSource(doc), new StreamResult(System.out));
			transformer.transform(new DOMSource(doc), new StreamResult(out));

		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		finally{
			try{
				if(out!=null){
					out.close();

				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public void makeTaskModel(File dir,UMLTreeParentModel treeP){
		String contents[] = dir.list();
		StrcuturedPackageTreeLibModel treeObject = null;
		java.util.ArrayList list = new java.util.ArrayList();
		for(int i = 0; i < contents.length; i++){

			System.out.println(contents[i]);
			// 현재 디렉토리와 파일 정보를 가지고 File 객체를 생성합니다.
			File child = new File(dir, contents[i]);

			if(child.isFile()){
			
				int index = contents[i].lastIndexOf(".xml");
				//					int indexd = contents[i].lastIndexOf(".dll");
				if(child.getName().length()==index+4
						&& child.getName().lastIndexOf("_view.xml")==-1){

					treeObject = this.addModelComponentModel(child);
					 if(treeObject==null){
						 list.add(child);
					 }
					//						uparent = cm.getUMLTreeModel().getParent();
					//						this.chkcmp.put(child.getPath()+File.separator+child.getName(), "chk");
					if(monitor!=null){
						monitor.subTask(child.getPath());
						monitor.worked(INCREMENT);
					}
					
				}
			}
		}
		
		if(treeObject!=null){
//			TreeSelection iSelection = (TreeSelection)viewer.getSelection();
//			UMLTreeParentModel treeObject = (UMLTreeParentModel)iSelection.getFirstElement();
//			UMLModel um = (UMLModel)treeObject.getRefModel();
//			UMLTreeParentModel treeP = getRcet(); 
			boolean isOverlapping = ProjectManager.getInstance().isOverlapping((UMLTreeParentModel)treeP,  treeObject.getName());
			String name =treeObject.getName();
			if(isOverlapping) {
				for(int i=0;i<1000;i++){
					String tempname = name +i ;
					isOverlapping = ProjectManager.getInstance().isOverlapping((UMLTreeParentModel)treeP,  tempname);
					if(!isOverlapping){
						name = tempname;
						break;
					}
				}
				
			}
			UMLModel um = (UMLModel)treeObject.getRefModel();
			
			ProjectManager.getInstance().addUMLModel(name, treeP, um, 1100,5);
//			AtomicComponentModel am
			if(um instanceof AtomicComponentModel){
				AtomicComponentModel am = (AtomicComponentModel)um;
			
			
			for(int i=0;i<list.size();i++){
				File s = (File)list.get(i);
				File t = new File(am.getCmpFolder()+File.separator+"profile"+File.separator+s.getName());
				this.copyFile(s, t);
				
			}
			}

//		}
//		catch(Exception e){
//			e.printStackTrace();
//		}

		ProjectManager.getInstance().getModelBrowser().refresh(ProjectManager.getInstance().getModelBrowser().getUMLTreeModelSelected());
		}
				//					else if(child.getName().length()==indexd+4){
				//						String key = child.getName().substring(0,indexd);
				//						this.getDllFile().put(key, child);
				//					}
				
		
	}

	public static void main(String [] args) {
		Document doc;
		try{
			// DOM Document를 생성하기 위하여 팩토리를 생성한다
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setIgnoringElementContentWhitespace(true);
			// 팩토리로부터 Document파서를 얻어내도록 한다.
			DocumentBuilder builder = factory.newDocumentBuilder();

			// Document DOM파서로 하여금 입력받은 파일을 파싱하도록 요청한다.
			doc = builder.parse(new File("C:\\lib3\\HelloMaker.xml"));
			//               doc.set
			//파싱된 후의 루트요소를 얻어내보도록 하며, 그 타입은 Element타입이다.
			Element root = doc.getDocumentElement();
			//               NodeList nl = root.getElementsByTagName("name");
			NodeList nl =root.getChildNodes();
			for(int i=0;i<nl.getLength();i++){
				Node element = (Node) nl.item(i);
				System.out.println(element.getNodeName()+element.getTextContent());
				if("name".equals(element.getNodeName())){
					//            		   element.setTextContent("rrrr");
				}
			}




			//               root.
			//주석을 한번 추가하여 달아본다.
			//               Comment comment = doc.createComment("Training test");
			// 만들어진 주석을 루트원소의 자식으로 추가한다.
			//               root.appendChild(comment);

			// 루트원소의 각종 정보를 출력해보도록 한다
			System.out.println("Tag name : " + root.getTagName());
			System.out.println("Node type : " + root.getNodeType());
			System.out.println("Node Value :"+root.getNodeValue());
			System.out.println("First Child : " +root.getFirstChild());
			System.out.println("Last Child : " +root.getLastChild());
			// 현재 메모리에 올라와 있는 DOM Document를 화면에 출력해본다.
			// 다른 예를 파일 출력도 가능하다.
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer transformer = tFactory.newTransformer();
			File f = new File("c:\\test.xml");
			BufferedWriter out = new BufferedWriter(new FileWriter("c:\\test.xml"));
			Properties props = new Properties();
			//               props.setProperty("version", "1.0");
			//               props.setProperty("encoding", "euc-kr");
			props.setProperty("indent", "yes");
			//               props.setProperty("standalone", "no");
			transformer.setOutputProperties(props);
			transformer.transform(new DOMSource(doc), new StreamResult(System.out));
			transformer.transform(new DOMSource(doc), new StreamResult(out));
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	
	public void setModel(String xmlPath){
		DocComponentModel doc = new DocComponentModel();
		doc.load(xmlPath);

		
		
		
		for(int i=0;i<this.getDiagramList().size();i++){
			N3EditorDiagramModel nd = (N3EditorDiagramModel)this.getDiagramList().get(i);
//			PortModel test = new EventOutputPortModel();
//			test.setName("test");
//			test.setType("int");
//			cm.createPort(test, (UMLContainerModel)nd);

			if(nd!=null){
				nd.setModels(doc);
			}
		}
	}
	
	
//	File  f = new File(this.getCmpFolder()+File.separator+this.getName()+".h");
//	this.writeFile( sb,f);
	

	//	public Repository connectRepository(String ip,int port,String path){
	//		Repository repo = null;
	//		try{
	//		
	//		PlanetServer planet = new PlanetServerImpl();
	//		planet.start();
	//		
	//		RobotFramework robot = PlanetUtils.createProxy(planet, ip, port, path,
	//														RobotFramework.class);
	//		 repo = robot.getLocalRepository();
	//		}
	//		catch(Exception e){
	//			e.printStackTrace();
	//		}
	//		return repo;
	//		
	//	}
	//	
	//	public java.util.ArrayList listComponentAll(Repository repo){
	//		java.util.ArrayList list = new java.util.ArrayList();
	//		try{
	//		
	////			Collection<ExecutableInfo> infos = repo.findExecutableInfo(ExecutableType.COMPONENT,
	////					null, null, null, null);
	////			for ( ExecutableInfo info: infos ) {
	////				list.add(info);
	////			}
	//			for(int i=0;i<10;i++){
	//				ExecutableInfo info = new ExecutableInfo();
	//				info.type = ExecutableType.COMPONENT;
	//				info.name = "FaceSensor"+i;
	//				info.version = "0.1."+i;
	//				info.description = "다사로봇 IrobiQ용 얼굴센서 컴포넌트"+i;
	//				info.fileType = "dll";
	//				info.manufacturerId = "dasa"+i;
	//				list.add(info);
	//			}
	//			return list;
	//			
	//		}
	//		catch(Exception e){
	//			e.printStackTrace();
	//		}
	//		return list;
	//		
	//		
	//	}
	//	
	//	public java.util.ArrayList mappingComponents(java.util.ArrayList list1){
	//		java.util.ArrayList list = new java.util.ArrayList();
	//		
	//		if(list1!=null){
	//			for(int i1=0;i1<list1.size();i1++){
	//				RepoComponent rc = new 	RepoComponent();
	//				ExecutableInfo  einfo = (ExecutableInfo)list1.get(i1);
	//				rc.setExecutableInfo(einfo);
	//				RepoComponentTreeModel tr = new RepoComponentTreeModel(rc.getName());
	//				tr.setRefModel(rc);
	//			    rc.setTreeModel(tr);
	//				list.add(tr);
	//			}
	//		}
	//		return list1;
	//		
	//	}
	//	public void makeTree(UMLTreeParentModel up,java.util.ArrayList list){
	//		if(list!=null){
	//			for(int i=0;i<list.size();i++){
	//				RepoComponentTreeModel child = (RepoComponentTreeModel)list.get(i);
	//				up.addChild(child);
	//				child.setParent(up);
	//			}
	//			ProjectManager.getInstance().getModelBrowser().refresh(up);
	//			ProjectManager.getInstance().getModelBrowser().expend(up);
	//		}
	//		
	//	}
	//	
	//	
	//	public java.util.ArrayList getRepoComponentsInfo(String name){
	//		java.util.ArrayList list = null;
	//		int i = name.indexOf(":");
	//		int j = name.indexOf("/");
	//		String ip = "";
	//		int port = -1;
	//		String path = "";
	//		if(i>0){
	//			ip = name.substring(0,i);
	//		}
	//		else{
	//			return null;
	//		}
	//		
	//		if(j>0){
	//			String ports = name.substring(i+1,j);
	//			try{
	//			Integer.valueOf(ports);
	//			}
	//			catch(Exception e){
	//				e.printStackTrace();
	//				return null;
	//			}
	//		}
	//		else{
	//			return null;
	//		}
	//		
	//		path = name.substring(j+1);
	////		Repository repo = this.connectRepository(ip, port, path);
	//		Repository repo = null;
	//		list = this.mappingComponents(this.listComponentAll(repo));
	//
	//		
	//	
	//		
	//		
	//		return list;
	//	}



}
