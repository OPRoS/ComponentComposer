package kr.co.n3soft.n3com.net;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.Iterator;

import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.component.ComponentLibModel;
import kr.co.n3soft.n3com.model.component.ComponentModel;
import kr.co.n3soft.n3com.model.component.RepoComponent;
import kr.co.n3soft.n3com.model.diagram.N3EditorDiagramModel;
import kr.co.n3soft.n3com.model.node.NodeItemModel;
import kr.co.n3soft.n3com.project.browser.RepoComponentTreeModel;
import kr.co.n3soft.n3com.project.browser.SendFile;
import kr.co.n3soft.n3com.project.browser.UMLTreeModel;
import kr.co.n3soft.n3com.project.browser.UMLTreeParentModel;
import kr.co.n3soft.n3com.projectmanager.CompAssemManager;
import kr.co.n3soft.n3com.projectmanager.OPRoSManifest;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;
import kr.co.n3soft.n3com.projectmanager.TarManager;
import opros.ExecutableExistsException;
import opros.ExecutableInfo;
import opros.ExecutableType;
import opros.Repository;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.PlatformUI;

import planet.PlanetServer;
import camus.CamusServer;
import camus.scm.GlobalServiceDirectory;
import etri.camus.CamusUtils;
import etri.planet.PlanetServerImpl;
import etri.util.io.IOUtils;




public class NetManager implements IRunnableWithProgress{

	private static NetManager instance;

	private static boolean isTest = false;
	
	//ijs TarManager -3 
	public  boolean isJar = false;
	//ijs monitoring->exports
	public OPRoSManifest oPRoSManifest = null;

	private static final int TOTAL_TIME = 10000;
	//* 요청 패킷 (클라이언트 -> 서버)
	public static String app_key = "app";
	public static String var_key = "var";
	public static String ver_key = "ver";
	public static String target_key = "target";
	public static String cmd_key = "cmd";
	public static String file_name_key = "file.name";
	public static String payloadSize_key = "payloadSize";
	public static String app_name_key = "app.name";
	public static String comp_name_key = "comp.name";
	//* 반환 패킷 (서버 -> 클라이언트)
	public static String success_key = "success";
	public static String ret_key = "ret";

	public static String  ok = "ok";
	public static String fail = "ok";

	public static boolean isReply = false;
	public static boolean isExit = false;
	public static String fileName = "";

	public static boolean isTotalSuccess = true;

	public static int replyCount = 0;

	public static boolean role_send = true;
	private static final int INCREMENT = 500;
	
	public String errMsg = "";
	
	public String retMsg = "";	//111108 SDM - 전송결과
	public boolean isChk = true;	//111108 SDM - isChk 전역변수로..
	private int runLoop = 0;//111108 SDM S - 실행루프.. (임시)
	private String appName = "";	//111223 SDM S - 삭제 어플리케이션명
	
	public void setAppName(String appName){
		this.appName = appName;
	}
	

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	Repository repo = null;

	public java.util.ArrayList msgs = new java.util.ArrayList();

	IProgressMonitor monitor = null;
	String text;

	public String getText() {
		return text;
	}

	public void addMsg(Object msg){
		msgs.add(msg);

	}
	
	public void viewErrMsg(){
		if(this.errMsg!=null && !this.errMsg.equals("")){
			MessageBox dialog = new MessageBox(ProjectManager.getInstance().window.getShell(),SWT.ERROR);
			dialog.setText("Error");
			dialog.setMessage(this.errMsg); //KDI 080908 0002
			
			dialog.open();
		}
		this.errMsg = "";
	}

	public void printMsg(){

		for(int i=0;i<this.msgs.size();i++){
			Object msg = this.msgs.get(i);

			if(i==0){
				if(msg instanceof String)
					ProjectManager.getInstance().getConsole().addMessage((String)msg);
				else if(msg instanceof String[]){
					String[] msgs = (String[])msg;
					for(int i1=0;i1<msgs.length;i1++){
						String t1 = msgs[i1];

						ProjectManager.getInstance().getConsole().appendMessage2(t1);
					}
				}

			}
			else{
				if(msg instanceof String)
					ProjectManager.getInstance().getConsole().appendMessage2((String)msg);
				else if(msg instanceof String[]){
					String[] msgs = (String[])msg;
					for(int i1=0;i1<msgs.length;i1++){
						String t1 = msgs[i1];
						ProjectManager.getInstance().getConsole().appendMessage2(t1);
					}
				}
			}
		}
		this.msgs.clear();
		this.viewErrMsg();
		
	}

	public void printMsg2(){

		for(int i=0;i<this.msgs.size();i++){
			String msg = (String)this.msgs.get(i);
			if(i==0){
				ProjectManager.getInstance().getConsole().addMessage(msg);
			}
			else{
				boolean isN = false;
				String[] ms = msg.split(",");
				if(ms!=null){
					String[] md = ms[0].split(":");
					if(md!=null && md.length==2){
						isN = true;
					}
				}
				if(isN){
					for(int i1=0;i<ms.length;i1++){
						String msg2 = ms[i1];
						ProjectManager.getInstance().getConsole().appendMessage2(msg2);
					}
				}
				else 
					ProjectManager.getInstance().getConsole().appendMessage2(msg);
			}
		}
		this.msgs.clear();
		this.viewErrMsg();
	
	}

	public void setText(String text) {
		this.text = text;
	}

	NodeItemModel nodeItemModel = null;
	public NodeItemModel getNodeItemModel() {
		return nodeItemModel;
	}

	public void setNodeItemModel(NodeItemModel nodeItemModel) {
		this.nodeItemModel = nodeItemModel;
	}
	//111108 SDM S - 실행루프.. (임시)
	public void setRunLoop(int runLoop) {
		this.runLoop = runLoop;
	}
	//111108 SDM E - 실행루프.. (임시)

	public N3EditorDiagramModel appDiagram = null;

	public N3EditorDiagramModel getAppDiagram() {
		return appDiagram;
	}

	public void setAppDiagram(N3EditorDiagramModel appDiagram) {
		this.appDiagram = appDiagram;
	}

	public IProgressMonitor getMonitor() {
		return monitor;
	}

	public void setMonitor(IProgressMonitor monitor) {
		this.monitor = monitor;
	}

	public  String ip = "";
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		if(ip==null || ip.trim().equals("")){
//			this.ip= "127.0.0.1";
		}
		else
			this.ip = ip;
	}

	public String port = "";

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		if(port==null || port.trim().equals("")){
//			this.port= "7308";
		}
		else
			this.port = port;
	}

	public static String ln = "\n";

	public static int type = 0;//배포

	ServerSocket	m_sockServer = null;
	Socket			m_socket = null;

	FileOutputStream m_fos;
	File file = null;

	public File getDirectory() {
		return file;
	}

	public void setDirectory(File file) {
		this.file = file;
	}

	public InputStream m_is;
	public OutputStream m_os;
	BufferedOutputStream bos = null; 
	BufferedInputStream bis = null; 


	public static NetManager getInstance() {
		if (instance == null) {
			instance = new NetManager();

			return instance;
		}
		else {
			return instance;
		}
	}

	//헤더 커맨드

	public void executeCommand(int type){
		try{
			//배포 -file.download
			if(type==0){
				java.util.ArrayList hrds = new java.util.ArrayList();
				if(this.file!=null && this.file.isDirectory()){
					CompAssemManager.getInstance().initCompFiles();
					CompAssemManager.getInstance().getComponentFile(file);
					java.util.ArrayList list = CompAssemManager.getInstance().getCompFiles();
					if(list !=null &&list.size()>0){

						for(int i=0;i<list.size();i++){
							SendFile sf = (SendFile)list.get(i);
							if(sf.value!=null && sf.value.exists()){
								HeaderSendData hrd = new HeaderSendData();
								hrd.target = "deploy";
								hrd.cmd = "file.download";
								hrd.payloadSize = String.valueOf(sf.value.length());
								hrd.file_name = sf.key;
								hrd.payload = sf.value;
								hrds.add(hrd);
							}

						}

						//						this.connectNetwork();
						this.runFileDownLoad(hrds);
						//						FileDownLoadThread fd = new FileDownLoadThread(hrds);
						//						ProjectManager.getInstance().window.getShell().getDisplay().asyncExec(fd);






					}
				}


			}//배포 -file.upload
			//			현재 실행되고 있는 로봇 응용의 리스트반환
			else if(type==1){
				//				HeaderSendData hrd = new HeaderSendData();
				//				hrd.target = "monitor";
				//				hrd.cmd = "app.list";
				//				hrd.payloadSize = String.valueOf("0");
				//				boolean isTotalSuccess = this.cmd(hrd);
				//				
				//				String str = SocketUtil.read_line(m_is);
				//				if(str!=null && !str.trim().equals("")){
				//					System.out.println("응답"+str);
				//					HeaderReceiveData hrs = new HeaderReceiveData();
				//					hrs.setHeaderReceiveData(str);
				//					if(hrs!=null){
				//						if(NetManager.ok.equals(hrs.success)){
				//							String[] rets = hrs.getRet();
				//							if(rets!=null){
				//								for(int i1=0;i1<rets.length;i1++){
				//									AppTreeModel app = new AppTreeModel(rets[i1]);
				//								}
				//							}
				//						}
				//					}
				//				}
			}
			//111107 SDM  S - 중복 어플리케이션 체크 및 삭제 메시지
			else if(type==2){	//어플리케이션 중복 체크
				String strAppName = this.appDiagram.getName().replace(" diagram", "");
				
				if(strAppName != null && !strAppName.equals("")){
					HeaderSendData hsd = new HeaderSendData();
					hsd.target = "monitor";
					hsd.cmd = "app.exist";
					hsd.app_name = strAppName;
					hsd.payloadSize = "0";
					hsd.cmdType = 11;
					
					retMsg = this.runFileCheck(hsd);
				}
			}
			
			else if(type==3){	//어플리케이션삭제
				String strAppName = this.appDiagram.getName().replace(" diagram", "");
				
				if(strAppName != null && !strAppName.equals("")){
					HeaderSendData hsd = new HeaderSendData();
					hsd.target = "monitor";
					hsd.cmd = "app.delete";
					hsd.app_name = strAppName;
					hsd.payloadSize = "0";
					hsd.cmdType = 12;
					
					retMsg = this.runFileDelete(hsd);
				}	
			}
			//111107 SDM E - 중복 어플리케이션 체크 및 삭제 메시지
			else if(type==4){	//로봇-노드 어플리케이션 목록 삭제 메시지
				HeaderSendData hsd = new HeaderSendData();
				hsd.target = "monitor";
				hsd.cmd = "app.delete";
				hsd.app_name = this.appName;
				hsd.payloadSize = "0";
				hsd.cmdType = 12;
				
				retMsg = this.runFileDelete(hsd);
			}
		}
		catch(Exception e1){
			e1.printStackTrace();

		}

	}

	// 서버소켓 생성과 클라이언트와 연결
	public boolean connectNetwork()
	{
		try
		{
			InetAddress addr = InetAddress.getByName(this.ip); 
			System.out.println("addr = " + addr);
			m_socket = new Socket(this.ip, Integer.valueOf(this.port.trim()));
			if( m_socket != null )
			{	
				m_os = m_socket.getOutputStream();
				m_is = m_socket.getInputStream();
				//				ProjectManager.getInstance().getConsole().appendMessage2("서버 연결  ip["+ip+"] port["+port+"]");
				this.addMsg("서버 연결  ip["+ip+"] port["+port+"]");
				if(monitor!=null){
					monitor.subTask("서버 연결  ip["+ip+"] port["+port+"]");
					monitor.worked(INCREMENT);
				}
				isExit  = false;
				//				m_socket.setSoTimeout(120);     
				return true;
			}
		}
		catch(Exception e)
		{
			//			ProjectManager.getInstance().getConsole().appendMessage2("서버 연결 실패 IP="+ip+" Port="+port);
			this.addMsg("서버 연결 실패 IP="+ip+" Port="+port);
			this.errMsg = "서버 연결 실패 IP="+ip+" Port="+port;
//			MessageBox dialog = new MessageBox(ProjectManager.getInstance().window.getShell(),SWT.ERROR);
////			dialog.setText("Message");
//			dialog.setMessage("서버 연결 실패 IP="+ip+" Port="+port); //KDI 080908 0002
			
//			dialog.open();
			e.printStackTrace();
		}

		if( m_socket == null )
			return false;

		return true;
	}
	public void closeNetwork(){
		try{
			if(m_os!=null)
				m_os.close();
			if(m_is!=null)
				m_is.close();
			if(bis!=null)
				bis.close();
			if(bos!=null)
				bos.close();
			if(m_socket!=null)
				m_socket.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}

	}


	//	class FileDownLoadReplyThread extends Thread{
	//		public FileDownLoadReplyThread()
	//		{			
	//			start();
	//			System.out.println("응답시작");
	//		}
	//		public void run()
	//		{
	//			// 확인 수신
	//			try{
	//				while(true){
	//					System.out.println("응답을 기다림===>");
	//					String str = SocketUtil.read_line(m_is);
	//					if(str!=null && !str.trim().equals("")){
	//						HeaderReceiveData hrs = new HeaderReceiveData();
	//
	//						hrs.setHeaderReceiveData(str);
	//						
	////						if(isReply){
	//							if(hrs!=null){
	//								//							hrs.setFileName(hsd.file_name);
	//								if(NetManager.ok.equals(hrs.success)){
	//
	//									ProjectManager.getInstance().getConsole().addMessage(fileName+" : "+hrs.ret);
	//									isReply = true;
	////									role_send = true;
	//									Thread.sleep(100);
	//									
	//								}
	//								else{
	//									ProjectManager.getInstance().getConsole().addMessage(fileName+" : "+hrs.ret);
	//									isExit = false;
	//									break;
	//								}
	//							}
	//							else{
	//								ProjectManager.getInstance().getConsole().addMessage(fileName+" : "+"error");
	//								isExit = false;
	//								break;
	//							}
	//							replyCount++;
	//							
	//						}
	//
	////					}
	//					if(isExit)
	//						break;
	//				}
	//			}
	//			catch(Exception e){
	//				e.printStackTrace();
	//			}
	//		}
	//
	//	}

	//111107 SDM  S - 중복 어플리케이션 체크 및 삭제
	public String runFileCheck(HeaderSendData hsd){
		String tempMsg = "";
		
		if(hsd != null){
			boolean isTotalSuccess = sendMag(hsd);
			
			if(isTotalSuccess){
				try {
					String str = SocketUtil.read_line(m_is);
//					str = "ver=1.0;target=monitor;cmd=app.exist;success=ok;ret=no;payloadSize=0\n";	//임시
					
					if(str!=null && !str.trim().equals("")){
						System.out.println("응답"+str);
						HeaderReceiveData hrs = new HeaderReceiveData();

						hrs.setHeaderReceiveData(str);
						
						if(hrs!=null){
							if(NetManager.ok.equals(hrs.success)){

								//								ProjectManager.getInstance().getConsole().appendMessage2("서버 응답: "+fileName+" size["+hrs.payloadSize+"] : "+hrs.ret);
								this.addMsg("어플리케이션 중복 체크 : "+hrs.appName+" ->> ["+hrs.ret+"]");
								if(monitor!=null){
									monitor.subTask("어플리케이션 중복 체크 : "+hrs.appName+" ->> ["+hrs.ret+"]");
									monitor.worked(INCREMENT);
								}
								return hrs.ret;
							}
							else{
								//								ProjectManager.getInstance().getConsole().appendMessage2("서버 응답: "+fileName+" : "+hrs.ret);
								this.addMsg("어플리케이션 중복 체크 실패");
//								this.errMsg = "어플리케이션 중복 체크 실패2";
								if(monitor!=null){
									monitor.subTask("어플리케이션 체크 실패");
									monitor.worked(INCREMENT);
								}
								isExit = true;
								isTotalSuccess = false;

							}
						}
						else{
							//							ProjectManager.getInstance().getConsole().appendMessage2("서버 응답: "+fileName+" : "+"error");
							this.addMsg("어플리케이션 중복 체크 실패3");
							this.errMsg = "어플리케이션 중복 체크 실패4";
							isTotalSuccess = false;
							isExit = true;

						}
						tempMsg = hrs.ret;
					}
					return tempMsg;	
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return tempMsg;
				}
			}
		}
		return tempMsg;
	}
	
	public String runFileDelete(HeaderSendData hsd){
		String tempMsg = "";
		
		if(hsd != null){
			boolean isTotalSuccess = sendMag(hsd);
			
			if(isTotalSuccess){
				try {
					String str = SocketUtil.read_line(m_is);
//					str = "ver=1.0;target=monitor;cmd=app.delete;success=ok;ret=ok;payloadSize=0\n";	//임시
					
					if(str!=null && !str.trim().equals("")){
						System.out.println("응답"+str);
						HeaderReceiveData hrs = new HeaderReceiveData();

						hrs.setHeaderReceiveData(str);
						
						if(hrs!=null){
							if(NetManager.ok.equals(hrs.success)){

								//								ProjectManager.getInstance().getConsole().appendMessage2("서버 응답: "+fileName+" size["+hrs.payloadSize+"] : "+hrs.ret);
								this.addMsg("어플리케이션 삭제 : "+hrs.appName+" ->> ["+hrs.ret+"]");
								if(monitor!=null){
									monitor.subTask("어플리케이션 삭제 : "+hrs.appName+" ->> ["+hrs.ret+"]");
									monitor.worked(INCREMENT);
								}
								return hrs.ret;
							}
							else{
								//								ProjectManager.getInstance().getConsole().appendMessage2("서버 응답: "+fileName+" : "+hrs.ret);
								this.addMsg("어플리케이션 삭제 : "+hrs.appName+" ->> 실패 ["+hrs.ret+"]");
								this.errMsg = "어플리케이션 삭제 : "+hrs.appName+" ->> 실패 ["+hrs.ret+"]";
								if(monitor!=null){
									monitor.subTask("어플리케이션 삭제 : "+hrs.appName+" ->> 실패 ["+hrs.ret+"]");
									monitor.worked(INCREMENT);
								}
								isExit = true;
								isTotalSuccess = false;

							}
						}
						else{
							//							ProjectManager.getInstance().getConsole().appendMessage2("서버 응답: "+fileName+" : "+"error");
							this.addMsg("어플리케이션 삭제 : "+hrs.appName+" ->> 실패 ["+hrs.ret+"]");
							this.errMsg = "어플리케이션 삭제 : "+hrs.appName+" ->> 실패 ["+hrs.ret+"]";
							isTotalSuccess = false;
							isExit = true;
						}
						tempMsg = hrs.ret;
					}
					return tempMsg;	
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return tempMsg;
				}
			}
		}
		return tempMsg;
	}
	//111107 SDM  E - 중복 어플리케이션 체크 및 삭제

	public void runFileDownLoad(java.util.ArrayList hsds){
		try{
//			this.errMsg = "";
			if(hsds!=null){
				isTotalSuccess = true;

				for(int i=0;i<hsds.size();i++){
					if(isExit)
						break;
					HeaderSendData hsd	 = (HeaderSendData)hsds.get(i);
					fileName = hsd.file_name;
					boolean isTotalSuccess = cmd(hsd);

					if(isTotalSuccess){
						//						ProjectManager.getInstance().getConsole().appendMessage2("헤더 전송: "+hsd.getStringFileDownLoadHeader2());

						isTotalSuccess = cmdfiledownload_file(hsd);
						if(!isTotalSuccess){
							isExit = true;
							break;
						}

						this.addMsg("파일 전송: "+hsd.file_name+ " size["+hsd.payloadSize+"]");
						if(monitor!=null){
							monitor.subTask("파일 전송: "+hsd.file_name+ " size["+hsd.payloadSize+"]");
							monitor.worked(INCREMENT);
						}
						//						ProjectManager.getInstance().getConsole().appendMessage2("파일 전송: "+hsd.file_name+ " size["+hsd.payloadSize+"]");


					}
					else{

						break;
					}
					System.out.println("응답을 기다림");
					String str = SocketUtil.read_line(m_is);

					if(str!=null && !str.trim().equals("")){
						System.out.println("응답"+str);
						HeaderReceiveData hrs = new HeaderReceiveData();

						hrs.setHeaderReceiveData(str);


						if(hrs!=null){
							if(NetManager.ok.equals(hrs.success)){

								//								ProjectManager.getInstance().getConsole().appendMessage2("서버 응답: "+fileName+" size["+hrs.payloadSize+"] : "+hrs.ret);
								this.addMsg("서버 응답: "+fileName+" size["+hrs.payloadSize+"] : "+hrs.ret);
								if(monitor!=null){
									monitor.subTask("서버 응답: "+fileName+" size["+hrs.payloadSize+"] : "+hrs.ret);
									monitor.worked(INCREMENT);
								}



							}
							else{
								//								ProjectManager.getInstance().getConsole().appendMessage2("서버 응답: "+fileName+" : "+hrs.ret);
								this.addMsg("서버 응답: "+fileName+" : "+hrs.ret);
								this.errMsg = fileName+" : "+hrs.ret;
								if(monitor!=null){
									monitor.subTask("서버 응답: "+fileName+" : "+hrs.ret);
									monitor.worked(INCREMENT);
								}
								isExit = true;
								isTotalSuccess = false;

								break;
							}
						}
						else{
							//							ProjectManager.getInstance().getConsole().appendMessage2("서버 응답: "+fileName+" : "+"error");
							this.addMsg("서버 응답: "+fileName+" : "+"error");
							this.errMsg = fileName+" : error";
							isTotalSuccess = false;
							isExit = true;

							break;
						}
						replyCount++;

					}
				}



			}


		}
		catch(Exception e){
			e.printStackTrace();
			isTotalSuccess = false;
			//			ProjectManager.getInstance().getConsole().appendMessage2("파일 전송 실패");\
			this.addMsg("파일 전송 실패");
		}
		//		finally
		//		{
		//			try
		//			{
		//				m_os.close();
		//				m_is.close();
		//				bis.close();
		//				bos.close();
		//				m_socket.close();
		//			}
		//			catch(IOException e) {}
		//		}
		if(isTotalSuccess){
			//			ProjectManager.getInstance().getConsole().appendMessage2("파일 전송 완료");
			if(errMsg.equals(""))
			this.addMsg("파일 전송 완료");
		}
		isExit = true;
	}

	class FileDownLoadThread implements  Runnable {
		java.util.ArrayList hsds = null;
		//		java.util.ArrayList hrs = null;
		public java.util.ArrayList getHsds() {
			return hsds;
		}
		public void setHsds(java.util.ArrayList hsds) {
			this.hsds = hsds;
		}
		public FileDownLoadThread(java.util.ArrayList pHsds)
		{			
			hsds = pHsds;

		}
		public void run()
		{
			try{
				if(hsds!=null){
					isTotalSuccess = true;

					for(int i=0;i<hsds.size();i++){
						if(isExit)
							break;
						HeaderSendData hsd	 = (HeaderSendData)hsds.get(i);
						fileName = hsd.file_name;
						boolean isTotalSuccess = cmd(hsd);

						if(isTotalSuccess){
							//							ProjectManager.getInstance().getConsole().appendMessage2("헤더 전송: "+hsd.getStringFileDownLoadHeader2());

							isTotalSuccess = cmdfiledownload_file(hsd);
							if(!isTotalSuccess){
								isExit = true;
								break;
							}
							ProjectManager.getInstance().getConsole().appendMessage2("파일 전송: "+hsd.file_name+ " size["+hsd.payloadSize+"]");

						}
						else{

							break;
						}
						System.out.println("응답을 기다림");
						String str = SocketUtil.read_line(m_is);

						if(str!=null && !str.trim().equals("")){
							System.out.println("응답"+str);
							HeaderReceiveData hrs = new HeaderReceiveData();

							hrs.setHeaderReceiveData(str);


							if(hrs!=null){
								if(NetManager.ok.equals(hrs.success)){

									ProjectManager.getInstance().getConsole().appendMessage2("서버 응답: "+fileName+" size["+hrs.payloadSize+"] : "+hrs.ret);


								}
								else{
									ProjectManager.getInstance().getConsole().appendMessage2("서버 응답: "+fileName+" : "+hrs.ret);
									isExit = true;
									isTotalSuccess = false;
									break;
								}
							}
							else{
								ProjectManager.getInstance().getConsole().appendMessage2("서버 응답: "+fileName+" : "+"error");
								isTotalSuccess = false;
								isExit = true;
								break;
							}
							replyCount++;

						}
					}



				}


			}
			catch(Exception e){
				e.printStackTrace();
				isTotalSuccess = false;
				ProjectManager.getInstance().getConsole().appendMessage2("파일 전송 실패");
			}
			finally
			{
				try
				{
					m_os.close();
					m_is.close();
					bis.close();
					bos.close();
					m_socket.close();
				}
				catch(IOException e) {}
			}
			if(isTotalSuccess)
				ProjectManager.getInstance().getConsole().appendMessage2("파일 전송 완료");
			isExit = true;
		}

	}

	public boolean cmdfileupload(){
		return true;
	}



	public boolean cmd(HeaderSendData hsd){
		try{

			System.out.println(hsd.getHeader());
			m_os.write(hsd.getHeader().getBytes(),0,hsd.getHeader().getBytes().length);

			m_os.flush();


		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;

	}
	
	//111107 SDM S - 메시지 전달
	public boolean sendMag(HeaderSendData hsd){
		try{

			System.out.println(hsd.getHeader());
			m_os.write(hsd.getHeader().getBytes(),0,hsd.getHeader().getBytes().length);
			m_os.flush();
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;

	}
	//111107 SDM E - 메시지 전달


	static PlanetServer s_planet; 
	public Repository connectRepository(String ip,int port) {
		try {
			s_planet = new PlanetServerImpl();
			s_planet.start();
			//			ProjectManager.getInstance().getConsole().appendMessage2("1-1");

			CamusServer camus = CamusUtils.createCamusServerProxy(s_planet, ip, port);
			GlobalServiceDirectory gsd = camus.getGlobalServiceDirectory();
			//			ProjectManager.getInstance().getConsole().appendMessage2("2");
			//			 this.printMsg();
			return CamusUtils.getFirstServiceIGE(gsd, Repository.class);
		}
		catch (Exception e) {
			ProjectManager.getInstance().writeLog("error", e);
			e.printStackTrace();
			//			ProjectManager.getInstance().getConsole().appendMessage2(e.toString());
			//            this.printMsg();

			return null;
		}


	}
	
	public Repository connectRepository2(String name) {
		try {
			s_planet = new PlanetServerImpl();
			s_planet.start();
			//			ProjectManager.getInstance().getConsole().appendMessage2("1-1");
			java.util.ArrayList list = null;
			int i = name.indexOf(":");

			String ip = "";
			int port = -1;

			if(i>0){
				ip = name.substring(0,i);
				try{
					InetAddress addr = InetAddress.getByName(ip);
				}
				catch(Exception e){
					MessageDialog.openError(ProjectManager.getInstance().window.getShell(), "Error", "IP 의 값이 잘못되었습니다.");
					e.printStackTrace();
				}
			}
			else{
				MessageDialog.openError(ProjectManager.getInstance().window.getShell(), "Error", "Port 를 입력하여 주세요");

				return null;
			}


			String ports = name.substring(i+1);
			try{
				port = Integer.valueOf(ports);
			}
			catch(Exception e){
				MessageDialog.openError(ProjectManager.getInstance().window.getShell(), "Error", "Port 의 값이 잘못되었습니다.");
				e.printStackTrace();
				return null;
			}
			CamusServer camus = CamusUtils.createCamusServerProxy(s_planet, ip, port);
			GlobalServiceDirectory gsd = camus.getGlobalServiceDirectory();
			//			ProjectManager.getInstance().getConsole().appendMessage2("2");
			//			 this.printMsg();
			return CamusUtils.getFirstServiceIGE(gsd, Repository.class);
		}
		catch (Exception e) {
			ProjectManager.getInstance().writeLog("error", e);
			e.printStackTrace();
			//			ProjectManager.getInstance().getConsole().appendMessage2(e.toString());
			//            this.printMsg();

			return null;
		}


	}
	
	public void deleteRepoComp(UMLTreeParentModel parent,UMLTreeModel ut ){
		try{
			RepoComponent info = null;
			repo = this.connectRepository2(parent.getName());
			if(ut.getRefModel() instanceof RepoComponent){
				 info = (RepoComponent)ut.getRefModel();
			}
			else{
				return;
			}
			MessageBox dialog = new MessageBox(ProjectManager.getInstance().window.getShell(),SWT.ICON_QUESTION|SWT.YES|SWT.NO);
			dialog.setText("Message");
			dialog.setMessage(info.getName() +" 컴포넌트를 삭제하시겠습니까?");
			int k=dialog.open();
			switch(k) {
			case IDialogConstants.FINISH_ID:
				break;
			case SWT.YES:
				
				
				try{
					repo.removeExecutable(info.getId());
					java.util.ArrayList childs = new java.util.ArrayList(); 
					for(int i=0;i<parent.getChildren().length;i++){
						Object obj = parent.getChildren()[i];
						childs.add(obj);
					}
					for(int i=0;i<childs.size();i++){
						Object obj = childs.get(i);
						parent.removeChild((UMLTreeModel)obj);
					}
					java.util.ArrayList list= NetManager.getInstance().getRepoComponentsInfo(parent.getName());
					NetManager.getInstance().makeTree(parent, list);
				}
				catch(Exception e1){
					e1.printStackTrace();
					ProjectManager.getInstance().writeLog("error", e1);
				}
				ProjectManager.getInstance().getModelBrowser().refresh(parent);
				ProjectManager.getInstance().getModelBrowser().expend(parent);
				
			case SWT.NO:
				return;
			case SWT.CANCEL:
				break;
			}
			
		
			
		}
		catch(Exception e){
			e.printStackTrace();
			ProjectManager.getInstance().writeLog("error", e);
		}
		
	}

	public void uploadRepo( UMLTreeParentModel parent,UMLTreeModel ut ){
		ComponentLibModel info = null;
//		repo = null;
		if(!this.isTest)
		repo = this.connectRepository2(parent.getName());
		if(ut.getRefModel() instanceof ComponentLibModel){
			 info = (ComponentLibModel)ut.getRefModel();
		}
		else{
			return;
		}
		
		try{
			//			grepo.getExecutableInfo(info.getId()))	
			
			byte[] profileBytes = IOUtils.toBytes(info.getFile());
			byte[] exeFileBytes = IOUtils.toBytes(info.getDllFile());
			try{
                if(!this.isTest)
				repo.addExecutable(profileBytes, exeFileBytes);
				try{

					java.util.ArrayList childs = new java.util.ArrayList(); 
					for(int i=0;i<parent.getChildren().length;i++){
						Object obj = parent.getChildren()[i];
						childs.add(obj);
					}
					for(int i=0;i<childs.size();i++){
						Object obj = childs.get(i);
						parent.removeChild((UMLTreeModel)obj);
					}
					java.util.ArrayList list= NetManager.getInstance().getRepoComponentsInfo(parent.getName());
					NetManager.getInstance().makeTree(parent, list);
				}
				catch(Exception e1){
					e1.printStackTrace();
					ProjectManager.getInstance().writeLog("error", e1);
					
				}
				ProjectManager.getInstance().getModelBrowser().refresh(parent);
				ProjectManager.getInstance().getModelBrowser().expend(parent);
			}
			catch(ExecutableExistsException e){
				
//				ExecutableExistsException
//				MessageBox dialog = new MessageBox(ProjectManager.getInstance().window.getShell(),SWT.ICON_QUESTION|SWT.YES|SWT.NO);
//				dialog.setText("Message");
//				dialog.setMessage("저장소에 동일한 컴포넌트가 등록되어있습니다. 기존 컴포넌트를 삭제하고 등록하시겠습니까?");
//				int k=dialog.open();
//				switch(k) {
//				case IDialogConstants.FINISH_ID:
//					break;
//				case SWT.YES:
//					repo.removeExecutable(info.getId());
//					repo.addExecutable(profileBytes, exeFileBytes);
//					try{
//
//						java.util.ArrayList childs = new java.util.ArrayList(); 
//						for(int i=0;i<parent.getChildren().length;i++){
//							Object obj = parent.getChildren()[i];
//							childs.add(obj);
//						}
//						for(int i=0;i<childs.size();i++){
//							Object obj = childs.get(i);
//							parent.removeChild((UMLTreeModel)obj);
//						}
//						java.util.ArrayList list= NetManager.getInstance().getRepoComponentsInfo(parent.getName());
//						NetManager.getInstance().makeTree(parent, list);
//					}
//					catch(Exception e1){
//						e1.printStackTrace();
//						ProjectManager.getInstance().writeLog("error", e1);
//					}
//					ProjectManager.getInstance().getModelBrowser().refresh(parent);
//					ProjectManager.getInstance().getModelBrowser().expend(parent);
//					
//				case SWT.NO:
//					return;
//				case SWT.CANCEL:
//					break;
//				}
//				
			}

		}
		catch(Exception ex){
			ex.printStackTrace();
			ProjectManager.getInstance().writeLog("error", ex);
		}



	}

	public void downloadRepo(ExecutableInfo info){
		try{
			byte[] profileBytes = null;
			byte[] exeFileBytes = null;

			if(!this.isTest){
				//				Collection<ExecutableInfo> infos = repo.findExecutableInfo(ExecutableType.COMPONENT,
				//						"TouchSensor", "dll", null, "yujin");
				//				info = infos.iterator().next();
				ExecutableInfo info2 = repo.getExecutableInfo(info.id);

				exeFileBytes = repo.getExecutable(info.id);
				profileBytes = repo.getProfile(info.id);
			}
			else{
				File profile = new File("c:\\lib4\\HelloMaker.xml");
				File exefile = new File("c:\\lib4\\HelloMaker.dll");
				profileBytes = IOUtils.toBytes(profile);
				exeFileBytes = IOUtils.toBytes(exefile);


			}
			if(profileBytes!=null){
				String path = ProjectManager.getInstance().getSourceFolder();	//110905 SDM - 로컬리파지토리 경로 받기
				File desc = new File(path+File.separator+info.name+".xml");
				this.copyFile(profileBytes, desc);
			}
			if(exeFileBytes!=null){
				String path = ProjectManager.getInstance().getSourceFolder();
				File desc = new File(path+File.separator+info.name+"."+info.libraryType);
				this.copyFile(exeFileBytes, desc);
			}

		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	private void copyFile(byte[] bytes , File desFile){
		OutputStream out = null;
		try
		{
			//            InputStream in = new InputStream();
			out = new FileOutputStream(desFile);
			if(bytes!=null) 
				out.write(bytes);

			out.close();

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally{
			try{
				out.close();
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}


	public java.util.ArrayList listComponentAll(Repository repo){
		java.util.ArrayList list = new java.util.ArrayList();
		//		ProjectManager.getInstance().getConsole().appendMessage2("3---3");
		try{
			//			Repository repo1 = new Repository();
			if(!this.isTest){
				try {
					System.out.println("----------------->");
					//					ProjectManager.getInstance().getConsole().appendMessage2("3");

					Collection<ExecutableInfo> infos = repo.findExecutableInfoAll(ExecutableType.COMPONENT,
							null, null, null, null);
					for ( ExecutableInfo info: infos ) {
						list.add(info);
					}
				} catch (Exception e) {
					ProjectManager.getInstance().writeLog("error", e);
					//					ProjectManager.getInstance().getConsole().appendMessage2(e.toString());
					//					this.printMsg();
					e.printStackTrace();
				}
			}
			else{
				for(int i=0;i<10;i++){
					ExecutableInfo info = new ExecutableInfo();
					info.type = ExecutableType.COMPONENT;
					info.name = "FaceSensor"+i;
					info.version = "0.1."+i;
					//					info.description = "다사로봇 IrobiQ용 얼굴센서 컴포넌트"+i;
					info.libraryType = "dll";
					info.manufacturer = "dasa"+i;
					list.add(info);
				}
			}


		}
		catch(Exception e){
			e.printStackTrace();
			ProjectManager.getInstance().writeLog("error", e);

		}
		return list;


	}



	public java.util.ArrayList mappingComponents(java.util.ArrayList list1){
		java.util.ArrayList list = new java.util.ArrayList();

		if(list1!=null){
			for(int i1=0;i1<list1.size();i1++){
				RepoComponent rc = new 	RepoComponent();
				ExecutableInfo  einfo = (ExecutableInfo)list1.get(i1);
				rc.setExecutableInfo(einfo);
				RepoComponentTreeModel tr = new RepoComponentTreeModel(rc.getName());
				tr.setRefModel(rc);
				rc.setTreeModel(tr);
				list.add(tr);
			}
		}

		return list;

	}
	public void makeTree(UMLTreeParentModel up,java.util.ArrayList list){
		if(list!=null){
			for(int i=0;i<list.size();i++){
				RepoComponentTreeModel child = (RepoComponentTreeModel)list.get(i);
				up.addChild(child);
				child.setParent(up);
			}
			ProjectManager.getInstance().getModelBrowser().refresh(up);
			ProjectManager.getInstance().getModelBrowser().expend(up);
		}

	}



	
	public java.util.ArrayList getRepoComponentsInfo(String name){
		java.util.ArrayList list = null;
		int i = name.indexOf(":");

		String ip = "";
		int port = -1;

		if(i>0){
			ip = name.substring(0,i);
			try{
				InetAddress addr = InetAddress.getByName(ip);
			}
			catch(Exception e){
				MessageDialog.openError(ProjectManager.getInstance().window.getShell(), "Error", "IP 의 값이 잘못되었습니다.");
				e.printStackTrace();
			}
		}
		else{
			MessageDialog.openError(ProjectManager.getInstance().window.getShell(), "Error", "Port 를 입력하여 주세요");

			return null;
		}


		String ports = name.substring(i+1);
		try{
			port = Integer.valueOf(ports);
		}
		catch(Exception e){
			MessageDialog.openError(ProjectManager.getInstance().window.getShell(), "Error", "Port 의 값이 잘못되었습니다.");
			e.printStackTrace();
			return null;
		}


		if(!this.isTest){
			if(repo==null)
			repo = this.connectRepository(ip, port);
		}
		//		ProjectManager.getInstance().getConsole().appendMessage2("3-1");
		//		this.printMsg();


		try{
			//	ProjectManager.getInstance().getConsole().appendMessage2("repo==>"+repo);
			//	this.printMsg();
			//	Collection<ExecutableInfo> infos = repo.findExecutableInfoAll(ExecutableType.COMPONENT,
			//			null, null, null, null);	
			list = this.mappingComponents(this.listComponentAll(repo));
		}
		catch(Exception e){
			ProjectManager.getInstance().writeLog("error", e);
			//	ProjectManager.getInstance().getConsole().appendMessage2(e.toString());
			//	this.printMsg();
			e.printStackTrace();
		}




		return list;
	}





	public boolean cmdfiledownload_file(HeaderSendData hsd)
	{
		
		HeaderReceiveData hrd = null;
		FileInputStream fis = null;
		boolean isResult = true;

		try
		{
			fis = new FileInputStream(hsd.payload);
			bos = new BufferedOutputStream(m_os); 
			bis = new BufferedInputStream(fis); 
			//			byte[] snd_buf = new byte[4079];
			ByteBuffer buffer = ByteBuffer.allocate(4079); //220바이트 공간 설정
			System.out.println("order==>"+buffer.order());


			long nFileSize = 0;			
			int nIndex = 1;
			int nRead;
			long nRemaint;
			nFileSize = Long.valueOf(hsd.payloadSize);
			int nEnd = (int)nFileSize/4079;
			nRemaint = nFileSize%4079;
			if( nRemaint != 0 )
				nEnd++;
			else
				nRemaint = 4079;



			for(int i=0; ; )
			{


				if(nIndex<=nEnd){
					if( nEnd == nIndex )
					{
						i = bis.read(buffer.array(), 0, (int)nRemaint);

					}
					else if( nEnd != nIndex )
					{
						if( nEnd != 0)
							i = bis.read(buffer.array(), 0, 4079);
						else
							i = 0;
					}
					if( i == -1 )
						break;

					if( nEnd == nIndex )
					{
						bos.write(buffer.array(),0, (int)nRemaint);
						bos.flush();
						break;

					}
					else if(nEnd!=0)
					{
						bos.write(buffer.array());

					}
					bos.flush();
				}

				//				// 확인 수신
				//				String str = SocketUtil.read_line(m_is);
				//				if(str!=null && !str.trim().equals("")){
				//					hrd = new HeaderReceiveData();
				//					hrd.setHeaderReceiveData(str);
				//					break;
				//				}

				if(nEnd == 0)
					break;

				nIndex++;
			}

			fis.close();
			bis.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
			isResult = false;


		}
		finally{
			try{
				fis.close();
				bis.close();
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		return isResult;




	}
	//ijs TarManager -3 
	//ijs TarManager -2 
	public void run(IProgressMonitor monitor) throws InvocationTargetException,
	InterruptedException {
		
			this.monitor = monitor;
			this.monitor.beginTask("Deploy "+this.text,
					true ? IProgressMonitor.UNKNOWN : TOTAL_TIME);
			 
			PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
				public void run(){
					try{
						isChk = true;	//111108 SDM - isChk 전역변수로..
						if(appDiagram!=null){
				java.util.HashMap hm = new java.util.HashMap();
				java.util.ArrayList temp = null;
				N3EditorDiagramModel nd = appDiagram;


				UMLTreeParentModel up = nd.getUMLTreeModel().getParent();
				String str = CompAssemManager.getInstance().getNetFoler();
				File target = new File(str);
				if("All".equals(getText())){
					for(int i=0;i<appDiagram.getChildren().size();i++){

						Object obj = appDiagram.getChildren().get(i);
						if(obj instanceof ComponentModel){
							ComponentModel cm = (ComponentModel)obj;
							if(cm.getNodeItemModel()==null){
								isChk = false;
								//								ProjectManager.getInstance().getConsole().appendMessage2(cm.getName()+": node is not exist");
								addMsg(cm.getName()+": node is not exist");
								//							return;
							}
							else{
								String id = cm.getNodeItemModel().getUMLDataModel().getId();
								Object obj1 = hm.get(id);
								if(obj1==null){
									temp = new java.util.ArrayList();
									temp.add(cm);
									hm.put(cm.getNodeItemModel().getUMLDataModel().getId(), temp);
								}
								else{
									temp = (java.util.ArrayList)obj1;
									temp.add(cm);


								}

							}

						}
					}
					if(!isChk){
						//						ProjectManager.getInstance().getConsole().appendMessage2("deploy fail");
						addMsg("deploy fail");
						return;
					}


					Iterator it = hm.values().iterator();

					while(it.hasNext()){
						File dirFile = null;
						temp = (java.util.ArrayList )it.next();
						if(target.exists()){
							CompAssemManager.getInstance().deleteFilesAndDirs(target.getPath());
							dirFile = new File(target.getPath()+File.separator+up.getName());
							
							//ijs monitoring->exports
							if(NetManager.getInstance().isJar){
								oPRoSManifest = new OPRoSManifest();
								oPRoSManifest.Archive_name =up.getName()+".tar";
								oPRoSManifest.Archive_description = up.getName();
//								oPRoSManifest.ar
							}
							boolean ismkTarget =  dirFile.mkdirs();
							if(ismkTarget){
								if(NetManager.getInstance().isJar){
									CompAssemManager.getInstance().writeExportMain(up, dirFile.getPath());
								}
								else
								CompAssemManager.getInstance().writeExportMain(up, target.getPath());
								//								
							}

						}
						else{

							boolean ismkTarget = target.mkdir();
							dirFile = new File(target.getPath()+File.separator+up.getName());
							if(ismkTarget){
								ismkTarget =  dirFile.mkdir();
								if(ismkTarget){
									if(NetManager.getInstance().isJar){
										CompAssemManager.getInstance().writeExportMain(up, dirFile.getPath());
									}
									else
									CompAssemManager.getInstance().writeExportMain(up, target.getPath());
									//										this.makeCopyComposite(ut, dirFile);
								}
							}

						}

						for(int j=0;j<temp.size();j++){
							ComponentModel cm = 	(ComponentModel)temp.get(j);
							UMLModel addModel = cm;
							//ijs monitoring->exports
							if(NetManager.getInstance().isJar){
//								oPRoSManifest = new OPRoSManifest();
								if(!oPRoSManifest.Archive_Elements.contains(cm.getName()))
								oPRoSManifest.Archive_Elements.add(cm.getName());
							}
							Thread.sleep(1000);
							CompAssemManager.getInstance().makeNetSubCopy(cm, dirFile);
							if(j==0){



								NetManager.getInstance().setDirectory(new File(str));
								NetManager.getInstance().setIp(cm.getNodeItemModel().getIP());
								NetManager.getInstance().setPort(cm.getNodeItemModel().getPort1());

							}
							//							ProjectManager.getInstance().getConsole().addMessage(addModel.getName()+" Component 전송시작");

						}
						if(NetManager.getInstance().isJar){
							//ijs monitoring->exports
							oPRoSManifest.f = new File(dirFile+File.separator+"OPRoS.mf");
							oPRoSManifest.writeOPRoSManifest();
							TarManager.getInstance().makeTar(dirFile, target);
							CompAssemManager.getInstance().deleteFilesAndDirs(dirFile.getPath());
						}
						boolean isTrue = connectNetwork();
						if(isTrue){
							if(runLoop == 0){
								NetManager.getInstance().executeCommand(2);
							
								if(retMsg != null && !retMsg.equals("")){
									if(retMsg.equals("no")){
										NetManager.getInstance().executeCommand(0);
									}
									else if(retMsg.equals("yes")){
										MessageBox dialog = new MessageBox(ProjectManager.getInstance().window.getShell(),SWT.YES|SWT.NO|SWT.ICON_QUESTION);
										dialog.setText("Message");
										dialog.setMessage("Application of an equal name was Deploy. Would you wear by new Application?");
										int result = dialog.open();
										if(result == SWT.YES){
											NetManager.getInstance().executeCommand(3);
											if(retMsg.equals("ok"))
												NetManager.getInstance().executeCommand(0);
										}
									}
								}
							}
							else if(runLoop == 1){
								NetManager.getInstance().executeCommand(2);
								
								if(retMsg != null && !retMsg.equals("")){
									if(retMsg.equals("yes")){
										NetManager.getInstance().executeCommand(3);
//										MessageBox dialog = new MessageBox(ProjectManager.getInstance().window.getShell(),SWT.YES|SWT.NO|SWT.CANCEL|SWT.ICON_QUESTION);
//										dialog.setText("Message");
//										dialog.setMessage("실행엔진에 해당 어플리케이션과 동일한 어플리케이션이 존재 합니다. 함께 삭제 하시겠습니까?");
//										int result = dialog.open();
//										if(result == SWT.YES){
//											NetManager.getInstance().executeCommand(3);
//										}
//										else if(result == SWT.CANCEL)
//											retMsg = "cancel";
									}
								}
							}
							runLoop = 0;
						}
						closeNetwork();
					}

				}
				//111223 SDM S - 로봇-노드에서 불러온 어플리케이션 리스트 삭제
//				else if("|@|".equals(getText())){
//					boolean isTrue = connectNetwork();
//					if(isTrue){
//						executeCommand(3);
//					}
//					closeNetwork();
//				}
				//111223 SDM E - 로봇-노드에서 불러온 어플리케이션 리스트 삭제
				else{
					File dirFile = null;
					if(target.exists()){
						CompAssemManager.getInstance().deleteFilesAndDirs(target.getPath());
						dirFile = new File(target.getPath()+File.separator+up.getName());
						
						//ijs monitoring->exports
						if(NetManager.getInstance().isJar){
							oPRoSManifest = new OPRoSManifest();
							oPRoSManifest.Archive_name =up.getName()+".tar";
							oPRoSManifest.Archive_description = up.getName();
//							oPRoSManifest.ar
						}

						boolean ismkTarget =  dirFile.mkdir();
						if(ismkTarget){
							if(NetManager.getInstance().isJar){
								CompAssemManager.getInstance().writeExportMain(up, dirFile.getPath());
							}
							else
							CompAssemManager.getInstance().writeExportMain(up, target.getPath());
							//							
						}

					}
					else{

						boolean ismkTarget = target.mkdir();
						dirFile = new File(target.getPath()+File.separator+up.getName());
						if(ismkTarget){
							ismkTarget =  dirFile.mkdir();
							if(ismkTarget){
								if(NetManager.getInstance().isJar){
									CompAssemManager.getInstance().writeExportMain(up, dirFile.getPath());
								}
								else
								CompAssemManager.getInstance().writeExportMain(up, target.getPath());
								//									this.makeCopyComposite(ut, dirFile);
							}
						}

					}
					temp = new java.util.ArrayList();
					for(int i=0;i<appDiagram.getChildren().size();i++){

						Object obj = appDiagram.getChildren().get(i);
						if(obj instanceof ComponentModel){
							ComponentModel cm = (ComponentModel)obj;
							if(cm.getNodeItemModel()==null){
								isChk = false;
								addMsg(cm.getName()+": node is not exist");
								//							return;
							}
							else{
								if(nodeItemModel == cm.getNodeItemModel())
									temp.add(cm);
							}

						}
					}
					if(!isChk){
						//						ProjectManager.getInstance().getConsole().appendMessage2("deploy fail");
						addMsg("deploy fail");
						return;
					}
					for(int j=0;j<temp.size();j++){
						ComponentModel cm = 	(ComponentModel)temp.get(j);
						UMLModel addModel = cm;
						//ijs monitoring->exports
						if(NetManager.getInstance().isJar){
//							oPRoSManifest = new OPRoSManifest();
							if(!oPRoSManifest.Archive_Elements.contains(cm.getName()))
							oPRoSManifest.Archive_Elements.add(cm.getName());
						}
						CompAssemManager.getInstance().makeNetSubCopy(cm, dirFile);
						if(j==0){
							NetManager.getInstance().setDirectory(new File(str));
							NetManager.getInstance().setIp(cm.getNodeItemModel().getIP());
							NetManager.getInstance().setPort(cm.getNodeItemModel().getPort1());
						}
					}
					if(NetManager.getInstance().isJar){
						//ijs monitoring->exports
						oPRoSManifest.f = new File(dirFile+File.separator+"OPRoS.mf");
						oPRoSManifest.writeOPRoSManifest();
						TarManager.getInstance().makeTar(dirFile, target);
						CompAssemManager.getInstance().deleteFilesAndDirs(dirFile.getPath());
					}
					boolean isTrue = connectNetwork();
					
					//111108 SDM S
					if(isTrue){
						NetManager.getInstance().executeCommand(2);
						
						if(retMsg != null && !retMsg.equals("")){
							if(retMsg.equals("no")){
								NetManager.getInstance().executeCommand(0);
							}
							else if(retMsg.equals("yes")){
								MessageBox dialog = new MessageBox(ProjectManager.getInstance().window.getShell(),SWT.YES|SWT.NO|SWT.ICON_QUESTION);
								dialog.setText("Message");
								dialog.setMessage("Application of an equal name was Deploy. Would you wear by new Application?");
								int result = dialog.open();
								if(result == SWT.YES){
									NetManager.getInstance().executeCommand(3);
									if(retMsg.equals("ok"))
										NetManager.getInstance().executeCommand(0);
								}
							}
						}
					}
					closeNetwork();


				}
				
				hm.clear();

			}

		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally
		{

			closeNetwork();

		}
			}
		});



		//		this.monitor = monitor;
		//		if(this.runType==1){
		//			this.monitor.beginTask("Loading library",
		//					true ? IProgressMonitor.UNKNOWN : TOTAL_TIME);
		//			this.makeLib(null);
		//		}
		//		else if(this.runType==2){
		//			this.monitor.beginTask("Export Application",
		//					true ? IProgressMonitor.UNKNOWN : TOTAL_TIME);
		//			this.makeCopyMain(null, null);
		//		}
		//		else if(this.runType==3){
		//			this.monitor.beginTask("Export Composite",
		//					true ? IProgressMonitor.UNKNOWN : TOTAL_TIME);
		//			this.makeCopyComposite(null, null);
		//		}
		//
		//		//   for (int total = 0; total < TOTAL_TIME && !monitor.isCanceled(); total += INCREMENT) {
		//		//     Thread.sleep(INCREMENT);
		//		//     monitor.worked(INCREMENT);
		//		//     if (total == TOTAL_TIME / 2) monitor.subTask("Doing second half");
		//		//   }
		//		monitor.done();
		//		if (monitor.isCanceled())
		//			throw new InterruptedException("The long running operation was cancelled");
	}

	public static void main(String[] args) {
		//		NetManager.getInstance().setDirectory(new File(CompAssemManager.getInstance().getNetFoler()));
		//		NetManager.getInstance().setIp("127.0.0.1");
		//		NetManager.getInstance().setPort("7308");
		//		NetManager.getInstance().executeCommand(0);
		try{
			InetAddress addr = InetAddress.getByName("127.0.0.1"); 
			System.out.println("addr = " + addr);
		}
		catch(Exception e){
			e.printStackTrace();
		}


	}
	
	


	//	public boolean isCheckIp(String ip){
	//		InetAddress.
	//	}










}
