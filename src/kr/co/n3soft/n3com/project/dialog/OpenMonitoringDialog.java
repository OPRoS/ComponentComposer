package kr.co.n3soft.n3com.project.dialog;

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

import kr.co.n3soft.n3com.edit.UMLEditPart;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.component.ComponentModel;
import kr.co.n3soft.n3com.model.node.MonitoringThread;
import kr.co.n3soft.n3com.model.node.NodeItemModel;
import kr.co.n3soft.n3com.net.HeaderReceiveData;
import kr.co.n3soft.n3com.net.HeaderSendData;
import kr.co.n3soft.n3com.project.browser.RootCmpComposingTreeModel;
import kr.co.n3soft.n3com.project.browser.UMLTreeModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.PlatformUI;

public class OpenMonitoringDialog  extends TitleAreaDialog  {
	
	public static boolean isWin = true;
	public boolean isApprun = false;
	Group type;
	public TableViewer tableViewer;
	Group description;
	MonitoringThread mt = null;
	NodeItemModel nitem = null;
	private StyledText styleField;
	Thread t = null;
	public StyledText getStyleField() {
		return styleField;
	}

	public void setStyleField(StyledText styleField) {
		this.styleField = styleField;
	}

	public UMLModel um = null;
	public java.util.ArrayList monitorVariables = new java.util.ArrayList();
	Button buttonAdd = null;
	Button buttonRemove = null;
	Group tagButtonGroup;
	public NodeItemModel nodeItemModel = null;
	Socket			m_socket = null;
	ServerSocket	m_sockServer = null;
	public InputStream m_is;
	public OutputStream m_os;
	BufferedOutputStream bos = null; 
	BufferedInputStream bis = null; 
//	boolean isTest = true;
	boolean isConnectm = false;
	boolean initData = false;
	StringBuffer logVar = new StringBuffer();
	boolean isHeader = true;
	int index = 0;
	boolean viewLog = false;
//	public static boolean isTestD = true;
	public static int im = 1;
	DetailPropertyTableItem oldselectModel;
	private static final int CANCEL_ID = 1;
	String appName = "";
	OpenMonitoringDialog od = this;
	
	String header = "ver=1.0;target=symbol;cmd=get;success=ok;payloadSize=147";
//	"T[MyClass]ZT;I11;F11.11;d111.111;T[UserClass]ZF;I22;F22.22;d222.222;;;"
//	String[] value={"{app=HelloWorld;var=hello.count;valueformat=str;value=\"I1231;\";}"
//			,"{app=HelloWorld;var=hello.message;valueformat=str;value=\"A[8]CHello Me;\";}{app=HelloWorld;var=hello.count;valueformat=str;value=\"iX3075;\";}"
//			,"{app=HelloWorld;var=hello.message;valueformat=str;value=\"A[11]Chello world2;\";}{app=HelloWorld;var=hello.count;valueformat=str;value=\"iX3076;\";}"
//			,"{app=HelloWorld;var=hello.message;valueformat=str;value=\"A[11]Chello world3;\";}{app=HelloWorld;var=hello.count;valueformat=str;value=\"I5;\";}"
//			,"{app=HelloWorld;var=hello.message;valueformat=str;value=\"A[11]Chello world4;\";}{app=HelloWorld;var=hello.count;valueformat=str;value=\"I3;\";}"
//			,"{app=HelloWorld;var=hello.message;valueformat=str;value=\"A[11]Chello world5;\";}{app=HelloWorld;var=hello.count;valueformat=str;value=\"I7;\";}"
//			,"{app=HelloWorld;var=hello.message;valueformat=str;value=\"hello world6;\";}{app=HelloWorld;var=hello.count;valueformat=str;value=\"I9;\";}"
//			,"{app=HelloWorld;var=hello.message;valueformat=str;value=\"A[11]Chello world7;\";}{app=HelloWorld;var=hello.count;valueformat=str;value=\"I0;\";}"
//			,"{app=HelloWorld;var=hello.message;valueformat=str;value=\"hello world8;\";}{app=HelloWorld;var=hello.count;valueformat=str;value=\"I1;\";}"
//			,"{app=HelloWorld;var=hello.message;valueformat=str;value=\"A[11]Chello world9;\";}{app=HelloWorld;var=hello.count;valueformat=str;value=\"I5;\";}"
//	};
	String[] value={"{var=Application.hello.count;valueformat=str;value=\"I1231;\";}{var=Application.hello.message;valueformat=str;value=\"A[8]CHello Me;\";}{app=HelloWorld;var=hello.myclass;valueformat=str;value=\"T[std::string]L22;A[22]Cmessage from component1;;\";}"
			,"{var=Application.hello.message;valueformat=str;value=\"A[8]CHello Me1;\";}{var=Application.hello.myclass;valueformat=str;value=\"T[std::string]L23;A[22]Cmessage from component2;;\";}{var=Application.hello.count;valueformat=str;value=\"I1232;\";}"

	};
    String dd = "\"dd\"";
//    public OpenMonitoringDialog() {
//    	super(null);
//    	isApprun = true;
//    	ProjectManager.getInstance().setThread(new Thread(new MonitoringThread()));
////    	new Thread(new MonitoringThread());
//    	
//    }
	public OpenMonitoringDialog(Shell parent) {
		super(parent);

		this.setShellStyle(SWT.MODELESS | SWT.RESIZE | SWT.DIALOG_TRIM);
		this.setHelpAvailable(false);
//		this.setTitle("Monitoring");
//		this.set

		//	    this.set
		UMLTreeModel  uMLTreeModel= ProjectManager.getInstance().getModelBrowser().getUMLTreeModelSelected();
		System.out.println("ProjectManager.getInstance().getSelectPropertyUMLElementModel()==========>"+ProjectManager.getInstance().getSelectPropertyUMLElementModel());
		if (ProjectManager.getInstance().getSelectPropertyUMLElementModel() != null) {
			Object obj = ProjectManager.getInstance().getSelectPropertyUMLElementModel();
			if(obj instanceof UMLModel){
				UMLModel umModel = (UMLModel)obj;
				//	    				UMLModel umModel = (UMLModel)classEditPart.getModel();
				um = (UMLModel)umModel;
				Object obj1 = um.getMonitorVariables();
				if(obj1 !=null){
					monitorVariables = (java.util.ArrayList)um.getMonitorVariables().clone();
				}
			}
		}
		else{
			java.util.List list = ProjectManager.getInstance().getSelectNodes();
			if(list!=null && list.size()>0){
				if (list != null && list.size() == 1) {
					Object obj = list.get(0);
					if (obj instanceof UMLEditPart) {
						UMLEditPart u = (UMLEditPart)obj;
						um = (UMLModel)u.getModel();

						monitorVariables = (java.util.ArrayList)um.getMonitorVariables().clone();

					}
				}

			}
		}

	}

	protected Control createDialogArea(Composite parent) {
		Composite comp = (Composite)super.createDialogArea(parent);


		SashForm sf2 = new SashForm(parent, SWT.VERTICAL); 
		GridLayout layout2 = (GridLayout)comp.getLayout();
		layout2.numColumns = 1;
		FillLayout flowLayout2 = new FillLayout();
		sf2.setLayout(flowLayout2);
		GridData data6 = new GridData(GridData.FILL_BOTH);
		data6.heightHint = 400;
		data6.widthHint = 350;
		sf2.setLayoutData(data6);
		//20080717 KDI s 주석처리
		//		SashForm sf = new SashForm(sf2, SWT.HORIZONTAL); 
		GridLayout layout = (GridLayout)comp.getLayout();

		FillLayout flowLayout = new FillLayout();
		//		sf.setLayout(flowLayout);
		//		GridData data5 = new GridData(GridData.FILL_BOTH);
		//		data5.heightHint = 400;
		//		data5.widthHint = 600;
		//		sf.setLayoutData(data5);
		//20080717 KDI e 주석처리
		GridData data1 = new GridData(GridData.FILL_BOTH);
		//		data1.heightHint = 180;
		//		data1.widthHint = 280;
		type = new Group(sf2, SWT.SHADOW_ETCHED_IN); //20080717 KDI s 
		type.setLayout(layout);
		type.setText("Variable");//2008040302 PKY S 
		type.setLayoutData(data1);
		final Table table = new Table(type, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION|SWT.MULTI);

		table.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e)
			{
				//20080721 KDI s
//				if(initData){
					viewLog = true;
//				}
				System.out.println("");
				//				if(parameterCellEditor != null){
				//				parameterCellEditor.setOperationItem((ParameterCellEditor)e.item.getData());
				//				}
				//20080721 KDI e
			}

			public void widgetDefaultSelected(SelectionEvent e)
			{
				System.out.println("");
			}

		});
		tableViewer = buildAndLayoutTable(table);
		this.attachLabelProviderTag(tableViewer);
		if(monitorVariables.toArray().length>0){
			for(int i=0;i<monitorVariables.size();i++){
				DetailPropertyTableItem sti = (DetailPropertyTableItem)monitorVariables.get(i);
				tableViewer.add(sti);
				//				tableViewer.
			}
		}
		data1 = new GridData(GridData.FILL_HORIZONTAL);
		//		data1.horizontalSpan = 2;
		data1.heightHint = 200;
		data1.widthHint = 200;
		table.setLayoutData(data1);
		//		sf2.set
		GridData data2 = new GridData(GridData.FILL_BOTH);

		//		data2.widthHint = 300;
		description = new Group(sf2, SWT.SHADOW_ETCHED_IN); ////20080717 KDI s
		description.setText("Variable Log");//2008040302 PKY S ;
		description.setLayoutData(data2);
		description.setLayout(flowLayout); ////20080717 KDI s
		styleField = new StyledText(description, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
		styleField.setEditable(false);
		styleField.pack();
		sf2.setWeights(new int[] { 70, 30 });
		description.pack();
		GridLayout gridLayoutButton = new GridLayout(2,true);
		GridData tagButtonGroupData = new GridData(GridData.FILL_BOTH);
		tagButtonGroupData.heightHint = 100;
		tagButtonGroupData.widthHint = 200;
		this.tagButtonGroup = new Group(parent, SWT.SHADOW_ETCHED_IN);
		this.tagButtonGroup.setLayout(gridLayoutButton);
		this.tagButtonGroup.setLayoutData(tagButtonGroupData);
		buttonAdd = new Button(tagButtonGroup,SWT.PUSH);
//		buttonAdd.setText("start");
		buttonAdd.setImage(ProjectManager.getInstance().getImage(10202));//2008040302 PKY S 
		this.buttonRemove= new Button(tagButtonGroup,SWT.PUSH);
//		buttonRemove.setText("stop");
		buttonRemove.setImage(ProjectManager.getInstance().getImage(10203));//2008040302 PKY S 
		buttonRemove.addSelectionListener(new SelectionListener() {

			
			public void widgetSelected(SelectionEvent e)
			{
//				isConnectm = false;
//				initData = false;
				nodeItemModel.delTempCmp(od);
				if(nodeItemModel.getTempCmp().size()==0){
					isConnectm = false;
					initData = false;
				}
//				closeNetwork();
			}
			public void widgetDefaultSelected(SelectionEvent e)
			{
			}
		});
		
//		buttonAdd.setImage(ProjectManager.getInstance().getImage(203));//2008040302 PKY S 
		buttonAdd.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e)
			{
				//					TableColumn tc = tableViewer.getTable().getColumn(0);
//				DetailPropertyTableItem sti = new DetailPropertyTableItem();
//				sti.sTagType = "int";
//				sti.tapName = "dddd";
//				tableViewer.add(sti);
				if(nodeItemModel==null){

					MessageBox dialog = new MessageBox(ProjectManager.getInstance().window.getShell(),SWT.ERROR);
					dialog.setText("Error");
					dialog.setMessage("Node setting error");
					dialog.open();
					return;
				}
				else{
					String ip = nodeItemModel.getIP();
					String port = nodeItemModel.getPort1();
					if("".equals(ip) || ip==null){
						MessageBox dialog = new MessageBox(ProjectManager.getInstance().window.getShell(),SWT.ERROR);
						dialog.setText("Error");
						dialog.setMessage("Node setting error");
						dialog.open();
						return;
					}
					if("".equals(port) || port==null){
						MessageBox dialog = new MessageBox(ProjectManager.getInstance().window.getShell(),SWT.ERROR);
						dialog.setText("Error");
						dialog.setMessage("Node setting error");
						dialog.open();
						return;
					}
				}
				nodeItemModel.addTempCmp(od);
				isConnectm = true;
				initData = false;
//				System.out.println("dddd");
				setApp(null);
//				nodeItemModel.setAppName(appName);
//				nodeItemModel.start();
				
//				PlatformUI.getWorkbench().getDisplay().asyncExec(nodeItemModel.getRunable());
				
//				MonitoringThread mt = new MonitoringThread();
//				mt.setNitem(nodeItemModel);
//				mt.appName = appName;
////				mt.setApp(ut)
//				Thread t = new Thread(mt);
//				t.start();
//				
			
				
				new Thread(new MonitoringThread()).start();

//				int selectIndex=tableViewer.getTable().getSelectionIndex();//PKY 08071601 S 다이얼로그 UI작업
//				PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
//					public void run(){
//						try{
//						int i = 1;
//						StructuredSelection iSelection = (StructuredSelection)tableViewer.getSelection();
//						Iterator it = iSelection.iterator();
//						HeaderSendData hs = new HeaderSendData();
//						hs.app_name = "app";
//						hs.comp_name = "hello";
//						hs.cmdType = 10;
//						while(it.hasNext()){
//							DetailPropertyTableItem dp =(DetailPropertyTableItem)it.next();
//							hs.var.add(dp);
//							//										for(int i1=0;i1<1000;i1++){
//							//											dp.setValue(dp.sTagType, dp.tapName,String.valueOf(i1) , "ddd");
//							//											tableViewer.update(dp, null);
//							////											try{
//							//////											Thread.sleep(100);
//							////											}
//							////											catch(Exception e){
//							////												e.printStackTrace();
//							////											}
//							//										}
//							//										tableViewer.editElement(dp, 2);
//
//							//										}
//						}
//						boolean isSuccess = false;
////						while(!isConnect){
//						isSuccess = connectNetwork();
//
//						if(isSuccess){
//
//							isSuccess = cmd(hs);
//							if(isSuccess){
//								String header = read_line(m_is);
//								if(header!=null){
//									HeaderReceiveData hr = new HeaderReceiveData();
//									hr.var = hs.var;
//									hr.setHeaderReceiveData(header);
//									
//									
//									if("ok".equals(hr.success)){
//										String var = read_line(m_is);
//										hr.setVar(var);
//										
//										for(int i1=0;i1<hr.var.size();i1++){
//											DetailPropertyTableItem dp =(DetailPropertyTableItem)hr.var.get(i1);
//											tableViewer.update(dp, null);
//										}
////										DetailPropertyTableItem sti = new DetailPropertyTableItem();
////										sti.sTagType = "int";
////										sti.tapName = "dddd";
////										tableViewer.add(sti);
//										
////										hr.var
//									}
//									
//									
//								}
//							}
////							Thread.sleep(10000);
//						}
////						}
//						}
//						catch(Exception e){
//							e.printStackTrace();
//						}
//
//
//
//					}
//				});




				//							}
				//						}
				//					}

				//					tableViewer.refresh();
				//					Object obj = iSelection.getFirstElement();
				////					tableViewer.getTable().get
				//					if (obj != null) {
				//						PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
				//							public void run(){
				//								
				//								
				//							}
				//						});
				//						mViewer.remove(obj);
				//						monitorVariables.remove(obj);
				//						//PKY 08071601 S 다이얼로그 UI작업
				//						if(mViewer.getTable().getItemCount()>=selectIndex+1){
				//							mViewer.getTable().select(selectIndex);
				//						}else{
				//							mViewer.getTable().select(mViewer.getTable().getItemCount()-1);
				//						}
				//PKY 08071601 E 다이얼로그 UI작업
				//					}
			}

			public void widgetDefaultSelected(SelectionEvent e)
			{
			}

		});


		//		  buttonAdd.setl
		//		  this.setButtonLayoutData(buttonAdd);

		//		this.tagButtonGroup = new Group(tagComposite, SWT.SHADOW_ETCHED_IN);
		//		this.tagButtonGroup.setLayout(gridLayoutButton);
		//		this.tagButtonGroup.setLayoutData(tagButtonGroupData);

		return comp;
	}

	private TableViewer buildAndLayoutTable(final Table table) {
		TableViewer tableViewer = new TableViewer(table);
		this.createColumns(tableViewer);
//		TableLayout layout = new TableLayout();
//		layout.addColumnData(new ColumnWeightData(50, 50, true));
//		layout.addColumnData(new ColumnWeightData(50, 50, true));
//		layout.addColumnData(new ColumnWeightData(50, 50, true));
//		layout.addColumnData(new ColumnWeightData(10, 10, true));
//
//		table.setLayout(layout);
//		TableColumn checkColumn = new TableColumn(table, SWT.CENTER);
//		checkColumn.setText("check");
//		TableColumn accessColumn = new TableColumn(table, SWT.CENTER);
//		accessColumn.setText("name");
//		TableColumn steroColumn = new TableColumn(table, SWT.CENTER);
//		steroColumn.setText("type");
//		TableColumn nameColumn = new TableColumn(table, SWT.CENTER);
//		nameColumn.setText("value");
		//		TableColumn typeColumn = new TableColumn(table, SWT.CENTER);
		//		typeColumn.setText("");


		table.setHeaderVisible(true);
		return tableViewer;
	}
	
	private void createColumns(TableViewer viewer) {

		String[] titles = { "check", "name", "type", "value" };
		int[] bounds = { 100, 100, 100, 100 };

		for (int i = 0; i < titles.length; i++) {
			TableViewerColumn column = new TableViewerColumn(viewer, SWT.CENTER);
			column.getColumn().setText(titles[i]);
			column.getColumn().setWidth(bounds[i]);
			column.getColumn().setResizable(true);
			column.getColumn().setMoveable(true);
			if(i==0)
			column.setEditingSupport(new CheckEditingSupport(viewer, i,this));
		}
		Table table = viewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
	}
	
	private void attachLabelProviderTag(TableViewer viewer) {
		viewer.setLabelProvider(
				new ITableLabelProvider() {
					
					private  final Image CHECKED =  ProjectManager.getInstance().getImage(10204);
					
					public Image getColumnImage(Object element, int columnIndex) {
						if(element instanceof DetailPropertyTableItem){
							DetailPropertyTableItem si = (DetailPropertyTableItem)element;
							if (columnIndex == 0) {
								if (((DetailPropertyTableItem) element).isCheck) {
									return CHECKED;
								} else {
									return null;
								}
							}

						}

						return null;

					}
					public String getColumnText(Object element, int columnIndex) {
						switch (columnIndex) {
						case 0:
//							String value = String.valueOf(((DetailPropertyTableItem)element).isCheck);
							boolean ischk = ((DetailPropertyTableItem)element).isCheck;
//							if(ischk){
//								viewer.
//							}
							String value = "";
							return value;
						case 1:
							 value = ((DetailPropertyTableItem)element).tapName;
							return value;
						case 2:
							value = ((DetailPropertyTableItem)element).sTagType;
							return value;
						case 3:
							value = ((DetailPropertyTableItem)element).desc;
							return value;

						default:
							return "Invalid column: " + columnIndex;
						}
					}
					public void addListener(ILabelProviderListener listener) {
					}
					public void dispose() {
					}
					public boolean isLabelProperty(Object element, String property) {
						return false;
					}
					public void removeListener(ILabelProviderListener lpl) {
					}
				});
	}

	public boolean connectNetwork()
	{
		if(!ProjectManager.mtest ){
			try
			{
				isWin =false;
				if(nodeItemModel!=null  ){
					String ip = nodeItemModel.getIP();
					String port = nodeItemModel.getPort1();
					InetAddress addr = InetAddress.getByName(ip); 
					System.out.println("addr = " + addr);
					if(m_socket==null){
					m_socket = new Socket(ip, Integer.valueOf(port));			

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
		if(!ProjectManager.mtest){
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
	protected void createButtonsForButtonBar(Composite parent) {
		// create OK and Cancel buttons by default
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	public boolean cmd(HeaderSendData hsd){
		try{

			System.out.println("send data=========================>"+hsd.getHeader());
			
			if("0".equals(hsd.payloadSize))
				return false;
			if(!ProjectManager.mtest ){
				m_os.write(hsd.getHeader().getBytes(),0,hsd.getHeader().getBytes().length);

				m_os.flush();
			}
			System.out.println("send data2=========================>"+hsd.getHeader());


		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;

	}
	
	public  final String read_message(InputStream in) throws IOException {
		if(!ProjectManager.mtest){
			ByteArrayOutputStream bout = new ByteArrayOutputStream();

			boolean eof = false;
			while( true ) {
				int b = in.read();
				if (b == -1) { eof = true; break;}
				 bout.write((byte)b);
				 System.out.println("bout===>"+bout.toString());
				
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

	public  final String read_line(InputStream in,String payloadSize) throws IOException {

		if(!ProjectManager.mtest){
			ByteArrayOutputStream bout = new ByteArrayOutputStream();

			boolean eof = false;
			while( true ) {
				int b = in.read();
				System.out.println("b------>"+b);
				if (b == -1) { eof = true; break;}
//				if ( b != '\r' && b != '\n' )
				bout.write((byte)b);
//				if (b == '\n') break;
//				if (b == '}') break;
				
				if(bout.size() == Integer.parseInt(payloadSize)){
					System.out.println("size=====>"+bout.size());
					break;
				}
				
				if(!isConnectm)
					break;
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
	
	
	
	public  final String read_Header(InputStream in) throws IOException {

		if(!ProjectManager.mtest){
			ByteArrayOutputStream bout = new ByteArrayOutputStream();

			boolean eof = false;
			while( true ) {
				int b = in.read();
				System.out.println("b------>"+b);
				if (b == -1) { eof = true; break;}
				if ( b != '\r' && b != '\n' ) bout.write((byte)b);
				if (b == '\n') break;
//				if (b == '}') break;
//				if(bout.toByteArray().length == Integer.parseInt(payloadSize))
//					break;
				
				if(!isConnectm)
					break;
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
	
	class MonitoringThread implements Runnable {
		private int index = 0;
		Iterator it = null;
		HeaderSendData hs = new HeaderSendData();
		public void run() {
//		for (index = 0; index <= 100; index++) {
//			if(!initData){
				
				 
//			}
		while(true){
		try {
		Thread.sleep(500);
//		System.out.println("isConnect===>"+isConnectm);
		if(!isConnectm){
//			PlatformUI.getWorkbench().getDisplay().disposeExec(runnable);
			return ;
		}
		} catch (InterruptedException ex) {
		ex.printStackTrace();
		}
		
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
		public void run() {
			try{
//				while(isstart){
	 System.out.println("isConnectm=====>"+isConnectm);
	 
	 if(!isConnectm){
//			PlatformUI.getWorkbench().getDisplay().disposeExec(runnable);
			return ;
		}

//		while(isstart){
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
//				if(ProjectManager.getInstance().getThread()!=null){
				
				java.util.ArrayList checkss = nodeItemModel.getTempCmp();
				for(int i=0;i<nodeItemModel.getTempCmp().size();i++){
					OpenMonitoringDialog od = (OpenMonitoringDialog)nodeItemModel.getTempCmp().get(i);
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
//				}
//				else{
//					java.util.ArrayList checkss = nodeItemModel.getTempCmp();
//					for(int i=0;i<nodeItemModel.getTempCmp().size();i++){
//						UMLModel od = (UMLModel)nodeItemModel.getTempCmp().get(i);
//						
//						for(int i1=0;i1<od.getMonitorVariables().size();i1++){
//							DetailPropertyTableItem dp =(DetailPropertyTableItem)od.getMonitorVariables().get(i1);
//							dp.cmpName = od.getName();
//							if(dp.isCheck){
//								checks.add(dp);
//							}
//						}
////						TableItem [] datas = od.tableViewer.getTable().getItems();
////						for(int i1=0;i1<datas.length;i1++){
////							TableItem  ta = datas[i1];
////							DetailPropertyTableItem dp =(DetailPropertyTableItem)ta.getData();
////							dp.cmpName = od.um.getName();
////							
////							if(dp.isCheck){
////								checks.add(dp);
////							}
////							System.out.println("data");
////						}
//						
//
//					}
//				}
				it = checks.iterator();
			}
			
			while(it.hasNext() && !initData){
				DetailPropertyTableItem dp =(DetailPropertyTableItem)it.next();
				hs.var.add(dp);

			}
//			if(!initData)
//				initData = true;

			boolean isSuccess = false;
			//				while(!isConnect){
			isSuccess = connectNetwork();
			if(isSuccess){

				isSuccess = cmd(hs);
				if(isSuccess){
					String header = read_Header(m_is);
					System.out.println("header=============>"+header);
					if(header==null){
//						 isConnectm = false;
//							initData = false;
//							isstart = false;
//							closeNetwork();
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
							String var = read_line(m_is,hr.payloadSize);
							hr.setVar(var);
						for(int i1=0;i1<hr.var.size();i1++){
							DetailPropertyTableItem dp =(DetailPropertyTableItem)hr.var.get(i1);
//							tableViewer.update(dp, null);
//							if(ProjectManager.getInstance().getThread()!=null){
//								for(int i=0;i<nodeItemModel.getTempCmp().size();i++){
//									UMLModel od = (UMLModel)nodeItemModel.getTempCmp().get(i);
//									od.setName(od.getName());
//									
//								}
//							}
//							else{
								for(int i=0;i<nodeItemModel.getTempCmp().size();i++){
									OpenMonitoringDialog od = (OpenMonitoringDialog)nodeItemModel.getTempCmp().get(i);
									od.tableViewer.update(dp, null);
									
								}
//							}
						}
						
						for(int i=0;i<nodeItemModel.getTempCmp().size();i++){
							OpenMonitoringDialog od = (OpenMonitoringDialog)nodeItemModel.getTempCmp().get(i);
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
				Thread.sleep(500);
				 if(nodeItemModel.getTempCmp().size()==0){
	         	 isConnectm = false;
						initData = false;
//						isstart = false;
						closeNetwork();
						PlatformUI.getWorkbench().getDisplay().disposeExec(this);
//						 PlatformUI.getWorkbench().getDisplay().getSyncThread().destroy();
						
	         	return;
	          }
			}
			else{
				isConnectm = false;
				initData = false;
//				isstart = false;
				closeNetwork();
				PlatformUI.getWorkbench().getDisplay().disposeExec(this);
				return;
//				e.printStackTrace();
//				 PlatformUI.getWorkbench().getDisplay().getSyncThread().destroy();
			}

		}
		catch(Exception e){
			isConnectm = false;
			initData = false;
//			isstart = false;
			closeNetwork();
			e.printStackTrace();
			PlatformUI.getWorkbench().getDisplay().disposeExec(this);
			return;
//			 PlatformUI.getWorkbench().getDisplay().getSyncThread().destroy();
		}
//		}

	 
	 
	// if(nitem.getTempCmp().size()==0){
//		 isConnectm = false;
//			initData = false;
//			isstart = false;
//			closeNetwork();
//			 PlatformUI.getWorkbench().getDisplay().getSyncThread().destroy();
//			
////		return;
	// }
//	            if(!isConnectm){
//	            	break;
//	            }

//					PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
//						public void run() {
////							while(isstart){
//							try{
//	                            System.out.println("initData=====>"+initData);
//	                            System.out.println("initData=====>"+initData);
//	                            System.out.println("initData=====>"+initData);
//	                            System.out.println("initData=====>"+initData);
//	                            
//	                           
//	                            
//								if(!initData){
//									hs = new HeaderSendData();
//									hs.cmdType = 10;
//									hs.app_name = appName;
	//
//									java.util.ArrayList checks = new  java.util.ArrayList();
//									java.util.ArrayList checkss = nitem.getTempCmp();
//									for(int i=0;i<nitem.getTempCmp().size();i++){
//										OpenMonitoringDialog od = (OpenMonitoringDialog)nitem.getTempCmp().get(i);
//										TableItem [] datas = od.tableViewer.getTable().getItems();
//										for(int i1=0;i1<datas.length;i1++){
//											TableItem  ta = datas[i1];
//											DetailPropertyTableItem dp =(DetailPropertyTableItem)ta.getData();
//											dp.cmpName = od.um.getName();
//											
//											if(dp.isCheck){
//												checks.add(dp);
//											}
//											System.out.println("data");
//										}
//										
	//
//									}
//									it = checks.iterator();
//								}
//								
//								while(it.hasNext() && !initData){
//									DetailPropertyTableItem dp =(DetailPropertyTableItem)it.next();
//									hs.var.add(dp);
	//
//								}
////								if(!initData)
////									initData = true;
	//
//								boolean isSuccess = false;
//								//				while(!isConnect){
//								isSuccess = connectNetwork();
//								if(isSuccess){
	//
//									isSuccess = cmd(hs);
//									if(isSuccess){
//										String header = read_line(m_is);
//										System.out.println("header=============>"+header);
//										if(header==null){
////											 isConnectm = false;
////												initData = false;
////												isstart = false;
////												closeNetwork();
//											header.toString();
//										
//										}
//										if(header!=null){
//											HeaderReceiveData hr = new HeaderReceiveData();
//											hr.var = hs.var;
//											hr.setHeaderReceiveData(header);
//											System.out.println("ret=============>"+hr.ret);
//											if("0".equals(hr.payloadSize)){
//												isConnectm = false;
//												initData = false;
//												closeNetwork();
//												MessageBox dialog = new MessageBox(ProjectManager.getInstance().window.getShell(),SWT.ERROR);
//												dialog.setText("Error");
//												dialog.setMessage("payloadSize==0");
//												dialog.open();
//											}
//											else if("ok".equals(hr.success)){
//												String var = read_line(m_is);
//												hr.setVar(var);
//											for(int i1=0;i1<hr.var.size();i1++){
//												DetailPropertyTableItem dp =(DetailPropertyTableItem)hr.var.get(i1);
////												tableViewer.update(dp, null);
//												for(int i=0;i<nitem.getTempCmp().size();i++){
//													OpenMonitoringDialog od = (OpenMonitoringDialog)nitem.getTempCmp().get(i);
//													od.tableViewer.update(dp, null);
//													
//												}
//											}
//											
//											for(int i=0;i<nitem.getTempCmp().size();i++){
//												OpenMonitoringDialog od = (OpenMonitoringDialog)nitem.getTempCmp().get(i);
//												od.viewLog(hr.var);
//												
//											}
//											
	//
//												//								for(int i1=0;i1<hr.var.size();i1++){
//												//									DetailPropertyTableItem dp =(DetailPropertyTableItem)hr.var.get(i1);
//												//									if(viewLog){
//												//										StructuredSelection iSelection = (StructuredSelection)tableViewer.getSelection();
//												//										if(iSelection.size()==1){
//												//											DetailPropertyTableItem selectModel =(DetailPropertyTableItem)iSelection.getFirstElement();
//												//											if(selectModel == dp){
//												//												System.out.println("log======>"+dp.desc);
//												////												styleField.append(dp.desc+"\n");
//												//												if(oldselectModel!=null){
//												//													if(oldselectModel!=selectModel){
//												//														styleField.setText("");
//												//													}
//												//												}
//												//												styleField.append(dp.desc.trim()+"\n");
//												//												oldselectModel = selectModel;
//												//											}
//												//										}
//												//										
//												//									}
//												//									tableViewer.update(dp, null);
//												//								}
	//
//												closeNetwork();
//												//								DetailPropertyTableItem sti = new DetailPropertyTableItem();
//												//								sti.sTagType = "int";
//												//								sti.tapName = "dddd";
//												//								tableViewer.add(sti);
	//
//												//								hr.var
//											}
//											else{
//												isConnectm = false;
//												initData = false;
//												closeNetwork();
//												MessageBox dialog = new MessageBox(ProjectManager.getInstance().window.getShell(),SWT.ERROR);
//												dialog.setText("Error");
//												dialog.setMessage(hr.ret);
//												dialog.open();
	//
//											}
	//
	//
//										}
//									}
//									Thread.sleep(3000);
//									 if(nitem.getTempCmp().size()==0){
//						            	 isConnectm = false;
//											initData = false;
//											isstart = false;
//											closeNetwork();
//											 PlatformUI.getWorkbench().getDisplay().getSyncThread().destroy();
//											
////						            	return;
//						             }
//								}
//								else{
//									isConnectm = false;
//									initData = false;
//									isstart = false;
//									closeNetwork();
////									e.printStackTrace();
//									 PlatformUI.getWorkbench().getDisplay().getSyncThread().destroy();
//								}
	//
//							}
//							catch(Exception e){
//								isConnectm = false;
//								initData = false;
//								isstart = false;
//								closeNetwork();
//								e.printStackTrace();
//								 PlatformUI.getWorkbench().getDisplay().getSyncThread().destroy();
//							}
////							}
//						}
////						else{
////							isConnectm = false;
////							initData = false;
////							isstart = false;
////							closeNetwork();
//////							e.printStackTrace();
////							 PlatformUI.getWorkbench().getDisplay().getSyncThread().destroy();
////						}
	////
//					});
//				}
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
//		}
	}
	
	public void viewLog(java.util.ArrayList data){
		if(this.viewLog){
			for(int i1=0;i1<data.size();i1++){
				DetailPropertyTableItem dp =(DetailPropertyTableItem)data.get(i1);
			StructuredSelection iSelection = (StructuredSelection)tableViewer.getSelection();
			if(iSelection.size()==1){
				DetailPropertyTableItem selectModel =(DetailPropertyTableItem)iSelection.getFirstElement();
				if(selectModel == dp){
					System.out.println("log======>"+dp.desc);
//					styleField.append(dp.desc+"\n");
					if(oldselectModel!=null){
						if(oldselectModel!=selectModel){
							styleField.setText("");
						}
					}
					styleField.append(dp.desc.trim()+"\n");
					oldselectModel = selectModel;
				}
			}
			
			}
		}
		
	}
	
	protected void configureShell(Shell newShell) {
		if(um!=null){
			newShell.setText(um.getName() +" Variable Monitoring Dialog");
			if(um instanceof ComponentModel){
				ComponentModel cm = (ComponentModel)um;
				this.nodeItemModel = cm.getNodeItemModel();
				
				setApp(null);
			}
		}
		else
		newShell.setText("Variable Monitoring Dialog");
		super.configureShell(newShell);
	}
	
	protected void buttonPressed(int buttonId)
	{
		if (buttonId == CANCEL_ID) {
			isConnectm = false;
			initData = false;
			closeNetwork();
		}
		else if(buttonId == IDialogConstants.OK_ID) {
			isConnectm = false;
			initData = false;
			closeNetwork();
		}
		super.buttonPressed(buttonId);
	}
	
	public boolean close() {
	
		isConnectm = false;
		initData = false;
		if(nodeItemModel!=null)
		nodeItemModel.delTempCmp(od);
//		nodeItemModel.getTempCmp().
	
//		closeNetwork();
		return super.close();
		
	}
	
	public void setApp(UMLTreeModel ut){
		if(ut==null){
			 ut = um.getUMLTreeModel();
			
		}
		
		if(ut.getParent() instanceof RootCmpComposingTreeModel){
			this.appName = ut.getName();
			return;
		}
		else{
			this.setApp(ut.getParent());
		}
		
	}


}
