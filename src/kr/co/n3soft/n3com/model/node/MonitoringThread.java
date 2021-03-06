package kr.co.n3soft.n3com.model.node;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;

import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.net.HeaderReceiveData;
import kr.co.n3soft.n3com.net.HeaderSendData;
import kr.co.n3soft.n3com.project.browser.RootCmpComposingTreeModel;
import kr.co.n3soft.n3com.project.browser.UMLTreeModel;
import kr.co.n3soft.n3com.project.dialog.DetailPropertyTableItem;
import kr.co.n3soft.n3com.project.dialog.OpenMonitoringDialog;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.PlatformUI;

public class MonitoringThread implements Runnable {
	NodeItemModel nitem = null;
	public String appName = "";
	boolean isstart = true;
	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	Iterator it = null;
	boolean initData = false; 
	HeaderSendData hs =null;

	Socket			m_socket = null;
	ServerSocket	m_sockServer = null;
	public InputStream m_is;
	public OutputStream m_os;
	BufferedOutputStream bos = null; 
	BufferedInputStream bis = null; 
	boolean isTest = false;
	public static boolean isConnectm = true;
	//	boolean initData = false;
	StringBuffer logVar = new StringBuffer();
	boolean isHeader = true;
	int index = 0;
	public UMLModel um = null;

	String header = "ver=1.0;target=symbol;cmd=get;success=ok;payloadSize=147";
	String[] value={"{app=HelloWorld;var=hello.message;valueformat=str;value=\"A[11]hello world0;\";}{app=HelloWorld;var=hello.count;valueformat=str;value=\"I1231;\";}"
			,"{app=HelloWorld;var=hello.message;valueformat=str;value=\"A[8]CHello Me;\";}{app=HelloWorld;var=hello.count;valueformat=str;value=\"iX3075;\";}"
			,"{app=HelloWorld;var=hello.message;valueformat=str;value=\"A[11]Chello world2;\";}{app=HelloWorld;var=hello.count;valueformat=str;value=\"iX3076;\";}"
			,"{app=HelloWorld;var=hello.message;valueformat=str;value=\"A[11]Chello world3;\";}{app=HelloWorld;var=hello.count;valueformat=str;value=\"I5;\";}"
			,"{app=HelloWorld;var=hello.message;valueformat=str;value=\"A[11]Chello world4;\";}{app=HelloWorld;var=hello.count;valueformat=str;value=\"I3;\";}"
			,"{app=HelloWorld;var=hello.message;valueformat=str;value=\"A[11]Chello world5;\";}{app=HelloWorld;var=hello.count;valueformat=str;value=\"I7;\";}"
			,"{app=HelloWorld;var=hello.message;valueformat=str;value=\"hello world6;\";}{app=HelloWorld;var=hello.count;valueformat=str;value=\"I9;\";}"
			,"{app=HelloWorld;var=hello.message;valueformat=str;value=\"A[11]Chello world7;\";}{app=HelloWorld;var=hello.count;valueformat=str;value=\"I0;\";}"
			,"{app=HelloWorld;var=hello.message;valueformat=str;value=\"hello world8;\";}{app=HelloWorld;var=hello.count;valueformat=str;value=\"I1;\";}"
			,"{app=HelloWorld;var=hello.message;valueformat=str;value=\"A[11]Chello world9;\";}{app=HelloWorld;var=hello.count;valueformat=str;value=\"I5;\";}"
	};
	//	boolean isHeader = true;
	public NodeItemModel getNitem() {
		return nitem;
	}

	public void setNitem(NodeItemModel nitem) {
		this.nitem = nitem;
	}

	public void run() {
//		for (index = 0; index <= 100; index++) {
//		if(!initData){
			
			 
//		}
	while(true){
	try {
	Thread.sleep(500);
//	System.out.println("isConnect===>"+isConnectm);
	if(!isConnectm){
//		PlatformUI.getWorkbench().getDisplay().disposeExec(runnable);
		return ;
	}
	} catch (InterruptedException ex) {
	ex.printStackTrace();
	}
	
	PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
	public void run() {
		try{
//			while(isstart){
 System.out.println("isConnectm=====>"+isConnectm);
 
 if(!isConnectm){
//		PlatformUI.getWorkbench().getDisplay().disposeExec(runnable);
		return ;
	}

//	while(isstart){
	try{
     System.out.println("initData=====>"+initData);
     System.out.println("initData=====>"+initData);
     System.out.println("initData=====>"+initData);
     System.out.println("initData=====>"+initData);
     
    
     
		if(!initData){
			hs = new HeaderSendData();
			hs.cmdType = 10;
			hs.app_name = appName;

			java.util.ArrayList checks = new  java.util.ArrayList();
			java.util.ArrayList checkss = nitem.getTempCmp();
			for(int i=0;i<nitem.getTempCmp().size();i++){
				OpenMonitoringDialog od = (OpenMonitoringDialog)nitem.getTempCmp().get(i);
				TableItem [] datas = od.tableViewer.getTable().getItems();
				for(int i1=0;i1<datas.length;i1++){
					TableItem  ta = datas[i1];
					DetailPropertyTableItem dp =(DetailPropertyTableItem)ta.getData();
					dp.cmpName = od.um.getName();
					
					if(dp.isCheck){
						checks.add(dp);
					}
					System.out.println("data");
				}
				

			}
			it = checks.iterator();
		}
		
		while(it.hasNext() && !initData){
			DetailPropertyTableItem dp =(DetailPropertyTableItem)it.next();
			hs.var.add(dp);

		}
//		if(!initData)
//			initData = true;

		boolean isSuccess = false;
		//				while(!isConnect){
		isSuccess = connectNetwork();
		if(isSuccess){

			isSuccess = cmd(hs);
			if(isSuccess){
				String header = read_line(m_is);
				System.out.println("header=============>"+header);
				if(header==null){
//					 isConnectm = false;
//						initData = false;
//						isstart = false;
//						closeNetwork();
					header.toString();
				
				}
				if(header!=null){
					HeaderReceiveData hr = new HeaderReceiveData();
					hr.var = hs.var;
					hr.setHeaderReceiveData(header);
					System.out.println("ret=============>"+hr.ret);
					if("0".equals(hr.payloadSize)){
						isConnectm = false;
						initData = false;
						closeNetwork();
						MessageBox dialog = new MessageBox(ProjectManager.getInstance().window.getShell(),SWT.ERROR);
						dialog.setText("Error");
						dialog.setMessage("payloadSize==0");
						dialog.open();
					}
					else if("ok".equals(hr.success)){
						String var = read_line(m_is);
						hr.setVar(var);
					for(int i1=0;i1<hr.var.size();i1++){
						DetailPropertyTableItem dp =(DetailPropertyTableItem)hr.var.get(i1);
//						tableViewer.update(dp, null);
						for(int i=0;i<nitem.getTempCmp().size();i++){
							OpenMonitoringDialog od = (OpenMonitoringDialog)nitem.getTempCmp().get(i);
							od.tableViewer.update(dp, null);
							
						}
					}
					
					for(int i=0;i<nitem.getTempCmp().size();i++){
						OpenMonitoringDialog od = (OpenMonitoringDialog)nitem.getTempCmp().get(i);
						od.viewLog(hr.var);
						
					}
					

						//								for(int i1=0;i1<hr.var.size();i1++){
						//									DetailPropertyTableItem dp =(DetailPropertyTableItem)hr.var.get(i1);
						//									if(viewLog){
						//										StructuredSelection iSelection = (StructuredSelection)tableViewer.getSelection();
						//										if(iSelection.size()==1){
						//											DetailPropertyTableItem selectModel =(DetailPropertyTableItem)iSelection.getFirstElement();
						//											if(selectModel == dp){
						//												System.out.println("log======>"+dp.desc);
						////												styleField.append(dp.desc+"\n");
						//												if(oldselectModel!=null){
						//													if(oldselectModel!=selectModel){
						//														styleField.setText("");
						//													}
						//												}
						//												styleField.append(dp.desc.trim()+"\n");
						//												oldselectModel = selectModel;
						//											}
						//										}
						//										
						//									}
						//									tableViewer.update(dp, null);
						//								}

						closeNetwork();
						//								DetailPropertyTableItem sti = new DetailPropertyTableItem();
						//								sti.sTagType = "int";
						//								sti.tapName = "dddd";
						//								tableViewer.add(sti);

						//								hr.var
					}
					else{
						isConnectm = false;
						initData = false;
						closeNetwork();
						MessageBox dialog = new MessageBox(ProjectManager.getInstance().window.getShell(),SWT.ERROR);
						dialog.setText("Error");
						dialog.setMessage(hr.ret);
						dialog.open();

					}


				}
			}
			Thread.sleep(1000);
			 if(nitem.getTempCmp().size()==0){
         	 isConnectm = false;
					initData = false;
//					isstart = false;
					closeNetwork();
					PlatformUI.getWorkbench().getDisplay().disposeExec(this);
//					 PlatformUI.getWorkbench().getDisplay().getSyncThread().destroy();
					
         	return;
          }
		}
		else{
			isConnectm = false;
			initData = false;
//			isstart = false;
			closeNetwork();
			PlatformUI.getWorkbench().getDisplay().disposeExec(this);
			return;
//			e.printStackTrace();
//			 PlatformUI.getWorkbench().getDisplay().getSyncThread().destroy();
		}

	}
	catch(Exception e){
		isConnectm = false;
		initData = false;
//		isstart = false;
		closeNetwork();
		e.printStackTrace();
		PlatformUI.getWorkbench().getDisplay().disposeExec(this);
		return;
//		 PlatformUI.getWorkbench().getDisplay().getSyncThread().destroy();
	}
//	}

 
 
// if(nitem.getTempCmp().size()==0){
//	 isConnectm = false;
//		initData = false;
//		isstart = false;
//		closeNetwork();
//		 PlatformUI.getWorkbench().getDisplay().getSyncThread().destroy();
//		
////	return;
// }
//            if(!isConnectm){
//            	break;
//            }

//				PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
//					public void run() {
////						while(isstart){
//						try{
//                            System.out.println("initData=====>"+initData);
//                            System.out.println("initData=====>"+initData);
//                            System.out.println("initData=====>"+initData);
//                            System.out.println("initData=====>"+initData);
//                            
//                           
//                            
//							if(!initData){
//								hs = new HeaderSendData();
//								hs.cmdType = 10;
//								hs.app_name = appName;
//
//								java.util.ArrayList checks = new  java.util.ArrayList();
//								java.util.ArrayList checkss = nitem.getTempCmp();
//								for(int i=0;i<nitem.getTempCmp().size();i++){
//									OpenMonitoringDialog od = (OpenMonitoringDialog)nitem.getTempCmp().get(i);
//									TableItem [] datas = od.tableViewer.getTable().getItems();
//									for(int i1=0;i1<datas.length;i1++){
//										TableItem  ta = datas[i1];
//										DetailPropertyTableItem dp =(DetailPropertyTableItem)ta.getData();
//										dp.cmpName = od.um.getName();
//										
//										if(dp.isCheck){
//											checks.add(dp);
//										}
//										System.out.println("data");
//									}
//									
//
//								}
//								it = checks.iterator();
//							}
//							
//							while(it.hasNext() && !initData){
//								DetailPropertyTableItem dp =(DetailPropertyTableItem)it.next();
//								hs.var.add(dp);
//
//							}
////							if(!initData)
////								initData = true;
//
//							boolean isSuccess = false;
//							//				while(!isConnect){
//							isSuccess = connectNetwork();
//							if(isSuccess){
//
//								isSuccess = cmd(hs);
//								if(isSuccess){
//									String header = read_line(m_is);
//									System.out.println("header=============>"+header);
//									if(header==null){
////										 isConnectm = false;
////											initData = false;
////											isstart = false;
////											closeNetwork();
//										header.toString();
//									
//									}
//									if(header!=null){
//										HeaderReceiveData hr = new HeaderReceiveData();
//										hr.var = hs.var;
//										hr.setHeaderReceiveData(header);
//										System.out.println("ret=============>"+hr.ret);
//										if("0".equals(hr.payloadSize)){
//											isConnectm = false;
//											initData = false;
//											closeNetwork();
//											MessageBox dialog = new MessageBox(ProjectManager.getInstance().window.getShell(),SWT.ERROR);
//											dialog.setText("Error");
//											dialog.setMessage("payloadSize==0");
//											dialog.open();
//										}
//										else if("ok".equals(hr.success)){
//											String var = read_line(m_is);
//											hr.setVar(var);
//										for(int i1=0;i1<hr.var.size();i1++){
//											DetailPropertyTableItem dp =(DetailPropertyTableItem)hr.var.get(i1);
////											tableViewer.update(dp, null);
//											for(int i=0;i<nitem.getTempCmp().size();i++){
//												OpenMonitoringDialog od = (OpenMonitoringDialog)nitem.getTempCmp().get(i);
//												od.tableViewer.update(dp, null);
//												
//											}
//										}
//										
//										for(int i=0;i<nitem.getTempCmp().size();i++){
//											OpenMonitoringDialog od = (OpenMonitoringDialog)nitem.getTempCmp().get(i);
//											od.viewLog(hr.var);
//											
//										}
//										
//
//											//								for(int i1=0;i1<hr.var.size();i1++){
//											//									DetailPropertyTableItem dp =(DetailPropertyTableItem)hr.var.get(i1);
//											//									if(viewLog){
//											//										StructuredSelection iSelection = (StructuredSelection)tableViewer.getSelection();
//											//										if(iSelection.size()==1){
//											//											DetailPropertyTableItem selectModel =(DetailPropertyTableItem)iSelection.getFirstElement();
//											//											if(selectModel == dp){
//											//												System.out.println("log======>"+dp.desc);
//											////												styleField.append(dp.desc+"\n");
//											//												if(oldselectModel!=null){
//											//													if(oldselectModel!=selectModel){
//											//														styleField.setText("");
//											//													}
//											//												}
//											//												styleField.append(dp.desc.trim()+"\n");
//											//												oldselectModel = selectModel;
//											//											}
//											//										}
//											//										
//											//									}
//											//									tableViewer.update(dp, null);
//											//								}
//
//											closeNetwork();
//											//								DetailPropertyTableItem sti = new DetailPropertyTableItem();
//											//								sti.sTagType = "int";
//											//								sti.tapName = "dddd";
//											//								tableViewer.add(sti);
//
//											//								hr.var
//										}
//										else{
//											isConnectm = false;
//											initData = false;
//											closeNetwork();
//											MessageBox dialog = new MessageBox(ProjectManager.getInstance().window.getShell(),SWT.ERROR);
//											dialog.setText("Error");
//											dialog.setMessage(hr.ret);
//											dialog.open();
//
//										}
//
//
//									}
//								}
//								Thread.sleep(3000);
//								 if(nitem.getTempCmp().size()==0){
//					            	 isConnectm = false;
//										initData = false;
//										isstart = false;
//										closeNetwork();
//										 PlatformUI.getWorkbench().getDisplay().getSyncThread().destroy();
//										
////					            	return;
//					             }
//							}
//							else{
//								isConnectm = false;
//								initData = false;
//								isstart = false;
//								closeNetwork();
////								e.printStackTrace();
//								 PlatformUI.getWorkbench().getDisplay().getSyncThread().destroy();
//							}
//
//						}
//						catch(Exception e){
//							isConnectm = false;
//							initData = false;
//							isstart = false;
//							closeNetwork();
//							e.printStackTrace();
//							 PlatformUI.getWorkbench().getDisplay().getSyncThread().destroy();
//						}
////						}
//					}
////					else{
////						isConnectm = false;
////						initData = false;
////						isstart = false;
////						closeNetwork();
//////						e.printStackTrace();
////						 PlatformUI.getWorkbench().getDisplay().getSyncThread().destroy();
////					}
////
//				});
//			}
		}
		catch(Exception e){
			isConnectm = false;
			initData = false;
			closeNetwork();
			e.printStackTrace();
		}


	}

	});
	}
	

	}

	public boolean connectNetwork()
	{
		if(!this.isTest ){
			try
			{
				//				isWin =false;
				if(nitem!=null && isstart ){
					String ip = nitem.getIP();
					String port = nitem.getPort1();
					InetAddress addr = InetAddress.getByName(ip); 
					System.out.println("addr = " + addr);
					if(m_socket==null){
						m_socket = new Socket(ip, Integer.valueOf(port));	
//						m_socket.setSoTimeout(1000);

						if( m_socket != null )
						{	
							m_os = m_socket.getOutputStream();
							m_is = m_socket.getInputStream();

							return true;
						}
					}
					else{
						if(!m_socket.isConnected()){

							closeNetwork();
							m_socket = null;
							m_socket = new Socket(ip, Integer.valueOf(port));			

							if( m_socket != null )
							{	
								m_os = m_socket.getOutputStream();
								m_is = m_socket.getInputStream();

								return true;
							}
						}
					}
				}
			}
			catch(Exception e)
			{
				//			ProjectManager.getInstance().getConsole().appendMessage2("서버 연결 실패 IP="+ip+" Port="+port);
				//			this.addMsg("서버 연결 실패 IP="+ip+" Port="+port);
				//			this.errMsg = "서버 연결 실패 IP="+ip+" Port="+port;
				//			MessageBox dialog = new MessageBox(ProjectManager.getInstance().window.getShell(),SWT.ERROR);
				////			dialog.setText("Message");
				//			dialog.setMessage("서버 연결 실패 IP="+ip+" Port="+port); //KDI 080908 0002

				//			dialog.open();
				e.printStackTrace();
				return false;
			}

			if( m_socket == null )
				return false;
		}

		return true;
	}
	public void closeNetwork(){
		if(!this.isTest){
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
				m_socket = null;

			}
			catch(Exception e){
				e.printStackTrace();
			}
		}

	}

	public boolean cmd(HeaderSendData hsd){
		try{

			System.out.println("send data=========================>"+hsd.getHeader());
			if("0".equals(hsd.payloadSize))
				return false;
			if(!this.isTest && !"0".equals(hsd.payloadSize)){
				m_os.write(hsd.getHeader().getBytes(),0,hsd.getHeader().getBytes().length);

				m_os.flush();
				System.out.println("send data2 =========================>"+hsd.getHeader());
			}


		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;

	}

	public  final String read_line(InputStream in) throws IOException {

		if(!isTest){
			ByteArrayOutputStream bout = new ByteArrayOutputStream();

			boolean eof = false;
			while( true ) {
				try{
				int b = in.read();
				if (b == -1) { eof = true; break;}
				if ( b != '\r' && b != '\n' ) bout.write((byte)b);
				if (b == '\n') break;
				if (b == '}') break;
				if(nitem.getTempCmp().size()==0){
	            	 isConnectm = false;
						initData = false;
						isstart = false;
						closeNetwork();
						
	            	return null;
	             }
				}
				catch(Exception e){
					e.printStackTrace();
					 isConnectm = false;
						initData = false;
						isstart = false;
						closeNetwork();
						
	            	return null;
				}
			}
			bout.flush();
			//		ByteBuffer buffer2 = ByteBuffer.wrap(bout.toByteArray());


			if ( eof && bout.size() == 0 ) return null; 
			//Or return ""; ? what's fit for you?
			return bout.toString();
		}
		else{
			StringBuffer sb = new StringBuffer();
			if(this.isHeader){
				sb.append(this.header);
				isHeader = false;
			}
			else{
				if(index == this.value.length){
					index = 0;
				}
				sb.append(this.value[index]);
				index++;
				isHeader = true;
			}
			return sb.toString();
		}
	}

	public void setApp(UMLTreeModel ut){
		//		if(ut==null){
		//			 ut = um.getUMLTreeModel();
		//			
		//		}

		if(ut.getParent() instanceof RootCmpComposingTreeModel){
			this.appName = ut.getName();
			return;
		}
		else{
			this.setApp(ut.getParent());
		}

	}

}
