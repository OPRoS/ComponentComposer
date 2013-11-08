package kr.co.n3soft.n3com.net;

import java.io.*;
import java.util.*;
import java.net.*;
import java.awt.*;
import java.text.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

class FileChat extends JFrame implements ActionListener
{	
	JMenuBar	m_menuBar;
	JMenu		m_menuFile;
	JMenuItem	m_itemFileOpen;
	JMenuItem	m_itemChat;
	JMenuItem	m_itemExit;
	JMenu		m_menuSetting;
	JMenuItem	m_itemSetting;
	JMenuItem	m_itemMyInfo;
	JMenu		m_menuConnect;
	JMenuItem	m_itemConnect;
	JMenuItem	m_itemServer;
	
	Font		m_font;
	
	JToolBar	m_toolbar;
	JButton		m_toolbarFile;
	JButton		m_toolbarChat;
	JButton		m_toolbarCancel;
	
	JLabel		�������ϼ�, �������ϼ���;
	JLabel		���ѻ����;
	JLabel		�������ϸ�, �������ϸ�;
	JLabel		���������;
	
	JProgressBar	��ü�����;
	JProgressBar	���������;
	
	File[]		m_lstSelectedFiles;	
	
	String		m_strFileCount="";	
	String[]	m_lstFileName;
	String[]	m_lstFileSize;
	String[]	m_lstFileDate;	
	String		m_strIP = "127.0.0.1";
	String		m_strDownDir = "down/";
	String		m_strLogDir = "log/";
	
	static final String	m_cmdRcvFile = "������ �����ض�!";
	static final String	m_cmdSndFile = "������ �����ض�!";
	static final String	m_cndCompleteSnd = "�������ۿϷ�";
	static final String	m_cndCompleteRcv = "�������ۿϷ�";
	static final String	m_cmdQuit = "quit";
	
	ServerSocket	m_sockServer = null;
	Socket			m_socket = null;
	
	FileOutputStream m_fos;
		
	InputStream m_is;
	OutputStream m_os;
	
	boolean m_bServer = false;
	boolean m_bConnected = false;
	boolean m_bWorking = false;
	boolean m_bLogging = true;
	boolean m_bCancel = false;
	boolean m_bSndFile = false;
	boolean m_bQuit = false;
	
	double m_dTotalValue = 0;
	double m_dCurValue = 0;
	int m_nTotalSize = 0;
	
	// ������
	public FileChat()
	{
		super("onestone FileChat");
		
		// ���� ������
		makeLogDownFolder();
		
		Container c  = getContentPane();
		
		// �޴�����
		setJMenuBar( makeMenu() );
		
		// ���� ����		
		c.add( makeToolBar() );
		
		// ��Ʈ ����
		m_font = new Font("Dialog", Font.PLAIN, 12);
		
		// ���׸� �ǳ� ����
		Image image = Toolkit.getDefaultToolkit().getImage("image/43.gif");
		Dimension window = new Dimension(325,170);
		ImagePanel back = new ImagePanel(image, window);
		back.setLayout( null );
		back.setBounds(0,0, window.width, window.height);
		
		// �����ϼ� ����
		�������ϼ� = new JLabel("���ϼ�:");
		�������ϼ�.setBounds( 5, 40, 45, 25 );
		back.add( �������ϼ�);
		
		�������ϼ��� = new JLabel();
		�������ϼ���.setBounds( 55, 40, 50, 25 );
		�������ϼ�.setFont(m_font);
		�������ϼ���.setFont(m_font);
		back.add( �������ϼ��� );
		
		// ���ѻ����
		���ѻ���� = new JLabel("KB", SwingConstants.RIGHT);		
		���ѻ����.setBounds( 205, 40, 100, 25 );
		���ѻ����.setFont(m_font);
		back.add( ���ѻ���� );
		
		// ��ü�����  ����
		��ü����� = new JProgressBar(0, 100);
		��ü�����.setBounds( 5 , 70, 300, 5 );
		��ü�����.setValue(0);		
		back.add( ��ü����� );
		
		// �������ϸ� ����
		�������ϸ� = new JLabel("���ϸ�:");
		�������ϸ�.setBounds( 5, 90, 40, 25 );
		�������ϸ�.setFont(m_font);
		back.add( �������ϸ� );
		�������ϸ� = new JLabel();
		�������ϸ�.setBounds( 50, 90, 150, 25 );
		�������ϸ�.setFont(m_font);
		back.add( �������ϸ� );
		
		// ���������� ����
		��������� = new JLabel("KB", SwingConstants.RIGHT);		
		���������.setBounds( 205, 90, 100, 25 );
		���������.setFont(m_font);		
		back.add( ��������� );
		
		// ���������
		��������� = new JProgressBar(0, 100);
		���������.setBounds( 5 , 120, 300, 5 );		
		���������.setValue(0);		
		back.add( ��������� );
		
		c.add(back, BorderLayout.CENTER );
		
		// ������ ���� ����
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				if( m_socket != null )
				{
					// ������ ����
					m_bQuit = true;
					sndCommand(2);
				}
				else
					System.exit(0);
			}
		});
		
		// ��ҹ�ư�� ���Ϻ������ư�� ���´�.
		m_toolbarCancel.setEnabled(false);
		m_toolbarFile.setEnabled(false);
		m_itemFileOpen.setEnabled(false);
	}
	
	//  [log]������ [down]������ ������
	public void makeLogDownFolder()
	{	
		File file = new File("log");
		file.mkdir();
		file = new File("down");
		file.mkdir();
	}
	
	//  �޴��� �����.
	public JMenuBar makeMenu()
	{
		m_menuBar = new JMenuBar();
		m_menuFile = new JMenu("File");

		m_itemFileOpen = new JMenuItem("UploadFile Select");
		m_itemChat = new JMenuItem("Chat");
		m_itemExit = new JMenuItem("Exit");
		
		m_menuFile.add( m_itemFileOpen );
		m_menuFile.add( m_itemChat );
		m_menuFile.addSeparator();
		m_menuFile.add( m_itemExit );
		
		m_menuSetting = new JMenu("Setting");
		m_itemSetting = new JMenuItem("Options");
		m_itemMyInfo  = new JMenuItem("MyInfo");
		m_menuSetting.add( m_itemSetting );
		m_menuSetting.add( m_itemMyInfo );
		
		m_menuConnect = new JMenu("Connect");
		m_itemConnect = new JMenuItem("������ �����ϱ�");
		m_itemServer = new JMenuItem("Server ����");
		m_itemConnect.setFont(m_font);
		m_itemServer.setFont(m_font);
		m_menuConnect.add( m_itemConnect );
		m_menuConnect.add( m_itemServer );
		
		m_menuBar.add( m_menuFile );
		m_menuBar.add( m_menuSetting );
		m_menuBar.add( m_menuConnect );
		
		m_itemFileOpen.addActionListener(this);
		m_itemExit.addActionListener(this);
		m_itemSetting.addActionListener(this);
		m_itemMyInfo.addActionListener(this);
		m_itemConnect.addActionListener(this);
		m_itemServer.addActionListener(this);
		
		return m_menuBar;
	}
	
	// ���ٸ� ������
	public JToolBar makeToolBar()
	{
		JPanel pan = new JPanel();
		pan.setLayout(null);
		m_toolbar = new JToolBar();
		m_toolbarFile = new JButton("���Ͼ��ε�");
		m_toolbarFile.setBorder( BorderFactory.createRaisedBevelBorder() );		
		m_toolbarFile.setFont(m_font);
		m_toolbarFile.setBounds(0,0,65,24);
		m_toolbarFile.setOpaque(true);
		m_toolbarFile.setBackground(Color.blue);
		m_toolbarFile.setForeground(Color.yellow);
		pan.add( m_toolbarFile );
		
		m_toolbarChat = new JButton("ä��");
		m_toolbarChat.setBorder( BorderFactory.createRaisedBevelBorder() );
		m_toolbarChat.setFont(m_font);
		m_toolbarChat.setBounds(67,0,30,24);
		m_toolbarChat.setOpaque(true);
		m_toolbarChat.setBackground(Color.blue);
		m_toolbarChat.setForeground(Color.yellow);
		pan.add( m_toolbarChat);
		
		m_toolbarCancel = new JButton("���");
		m_toolbarCancel.setBorder( BorderFactory.createRaisedBevelBorder() );
		m_toolbarCancel.setFont(m_font);
		m_toolbarCancel.setBounds(99,0,30,24);
		m_toolbarCancel.setOpaque(true);
		m_toolbarCancel.setBackground(Color.blue);
		m_toolbarFile.setForeground(Color.yellow);
		pan.add( m_toolbarCancel );
		pan.setOpaque(true);
		pan.setBackground(Color.cyan);
		m_toolbar.add( pan );
		
		m_toolbarFile.addActionListener(this);
		m_toolbarCancel.addActionListener(this);
		
		m_toolbar.setBounds( 5, 5, 150, 26);
		m_toolbar.setOpaque(true);
		m_toolbar.setBackground( Color.cyan );
		
		return m_toolbar;
	}
	
	// �󺧻Ӹ� �ƴ϶� ���������� �ʱ�ȭ�Ѵ�.
	public void initFileChat()
	{
		�������ϼ���.setText( "" );
		���ѻ����.setText( "KB" );
		�������ϸ�.setText( "" );
		���������.setText( "KB" );
		���������.setValue(0);
		��ü�����.setValue(0);
	}
	
	// �̺�Ʈ �ڵ鸵 �Լ�
	public void actionPerformed(ActionEvent e)
	{
		Object ob = e.getSource();
		
		// ���Ͼ��ε带 ������
		if( ob == m_itemFileOpen || ob == m_toolbarFile )
		{			
			// ������ ������ ������ ������ ���������Ͽ� ���۽���!			
			if( getUploadFileCount() > 0 )
			{
				// ���濡�� ���Ϲ������ ��ɳ���
				sndCommand(0);
			}
		}
		// IP���� �޴��� ������
		else if( ob == m_itemSetting )
		{
			OptDialog od = new OptDialog(this, "�ɼ� ����", true);
			od.pack();
			od.setLocation(300,300);
			od.setResizable(false);
			od.setVisible(true);
//System.out.println("(MAIN) : [" + m_strIP + "]");
		}
		// �� ������ ������
		else if( ob == m_itemMyInfo )
		{
			Programmer onestone = new Programmer(this, "������", true);
			onestone.pack();
			onestone.setLocation(400,300);
			onestone.setResizable(false);
			onestone.setVisible(true);
		}
		// ������ �����ؼ� ���Һд��� ��ٸ���.
		else if( ob == m_itemConnect )
		{
			if( m_socket == null )
			{
				connectNetwork();
				rcvCommand rp = new rcvCommand();
			}
		}
		// ���� �����ϰ� ��������带 ����.
		else if( ob == m_itemServer )
		{			
			if( m_sockServer == null)
			{
				if( createServerSocket() )
				{
					// �������� �޴� ����
					m_itemServer.setEnabled(false);
					m_itemConnect.setEnabled(false);
					setTitle("FileChat �����Դϴ�.");
					acceptClient ac = new acceptClient();
				}
			}
		}
		// ��ҹ�ư ��������
		else if( ob == m_toolbarCancel )
		{
			m_bCancel = true;
			System.out.println("��ҹ�ư�� �������ϴ�.");
		}
		// ���Ḧ ��������
		else if( ob == m_itemExit )
		{
			// �������� ����
			if( m_socket != null )
			{
				m_bQuit = true;
				sndCommand(2);
			}
			else
			{
				System.exit(0);
			}
		}
	}
	
	// ���ε��� ������ ������!
	public int getUploadFileCount()
	{
		JFileChooser fc = new JFileChooser(".");
		fc.setMultiSelectionEnabled(true);
		int ret = fc.showOpenDialog(this);
		int nFileCount = 0;
		
		if( ret == JFileChooser.APPROVE_OPTION)
		{
		
			m_lstSelectedFiles = fc.getSelectedFiles();
			nFileCount = m_lstSelectedFiles.length;
			m_strFileCount = Integer.toString( nFileCount );
			
//System.out.println(":"+m_strFileCount);			
			�������ϼ���.setText( "1/"+m_strFileCount );
//JOptionPane.showMessageDialog(this, m_lstSelectedFiles[0], "���ε� ���ϼ���",JOptionPane.YES_NO_OPTION);
		}
		
		return nFileCount;
	}
	
	
	// ���� ������ ������ ���̴�. type 0: ������������   1: �������ϼ���    2: Quit����
	public void sndCommand(int type)
	{	
		try
		{
			String msg;
			if(type == 0)
			{
				// "�ʴ� ������ �����ض�!";
				msg = m_cmdRcvFile + "\n";
				m_os.write(msg.getBytes());
				m_os.flush();
				m_bSndFile = true;
				System.out.println("���� ����� ���Ƚ��ϴ�! ���� ������");
			}
			else if(type == 1)
			{
				// "�ʴ� ������ �����ض�!";
				msg = m_cmdSndFile + "\n";
				m_os.write(msg.getBytes());
				m_os.flush();
				m_bSndFile = false;
				System.out.println("���� ����� ���Ƚ��ϴ�! ���� ������");
			}
			else
			{
				msg = m_cmdQuit + "\n";
				m_os.write(msg.getBytes());
				m_os.flush();
				System.out.println("�������� ���Ƚ��ϴ�! ���� �����Ұ��̴�");
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	// ���������� �����Ѵ�.
	public boolean createServerSocket()
	{
		try
		{
			m_sockServer = new ServerSocket(7308,2);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		if( m_sockServer == null )
			return false;
			
		m_bServer = true;
		System.out.println("���������� �����Ǿ����ϴ�.");
		return true;
	}
	
	// �������� ������ Ŭ���̾�Ʈ�� ����
	public boolean connectNetwork()
	{
		try
		{
			m_socket = new Socket(m_strIP, 7308);			
			
			if( m_socket != null )
			{	
				m_os = m_socket.getOutputStream();
				m_is = m_socket.getInputStream();
				m_bConnected = true;
				System.out.println("[" + m_strIP + "]�� ����Ǿ����ϴ�.");
				m_itemServer.setEnabled(false);
				m_itemConnect.setEnabled(false);
				m_toolbarFile.setEnabled(true);
				setTitle("FileChat Ŭ���̾�Ʈ�Դϴ�.");
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		if( m_socket == null )
			return false;
		
		return true;
	}
	
	// ���� ������ ����
	public void sndFileInfo()
	{
		try
		{
			int nTotalFileSize = 0;			
			String str;			
			String start = "������������\n";
			m_os.write( start.getBytes() );
			m_os.flush();
			
			m_lstFileName = new String[m_lstSelectedFiles.length];
			m_lstFileSize = new String[m_lstSelectedFiles.length];
			m_lstFileDate = new String[m_lstSelectedFiles.length];
			
			for(int i=0; i< m_lstSelectedFiles.length; i++)
			{
				// �������� ���� �����ϱ�
				m_lstFileName[i] = m_lstSelectedFiles[i].getName();
				m_lstFileSize[i] = (new Long(m_lstSelectedFiles[i].length())).toString();
				m_lstFileDate[i] = (new Date(m_lstSelectedFiles[i].lastModified())).toString();
				
				// �����ϻ�����
				m_nTotalSize = nTotalFileSize += Integer.parseInt( m_lstFileSize[i] );
				
				// �������� ����� �����ϱ�
				str = m_lstFileName[i] + ";" + m_lstFileSize[i] + ";" + m_lstFileDate[i] + ";\n";
				m_os.write( str.getBytes() );
				m_os.flush();
			}
			
			// �������ϼ� and ���ѻ�����
			String tmp = (new DecimalFormat("####")).format(m_lstFileName.length );
			�������ϼ���.setText( "1/"+tmp );
			tmp = (new DecimalFormat("#,###,###,###,### KB")).format(nTotalFileSize );			
			���ѻ����.setText( tmp );
			
			String end = "����������\n";
			m_os.write( end.getBytes() );
			m_os.flush();
			System.out.println("������ ������ ���������� ���½��ϴ�.");
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	// ���� ������ �����ؼ� �����Ѵ�.
	public void rcvFileInfo()
	{
		try
		{
			BufferedReader br = new BufferedReader( new InputStreamReader(m_is) );
			
			int nRead;
			int nIndex = 0;
			String str= "";
			String tmp;
			String[] lstFiles;
			
			System.out.println("���������� ��ٸ��� �ֽ��ϴ�...");
			
			while(true)
			{
				tmp = br.readLine();
				
//System.out.println("rcv data : [" + tmp + "]");
				
				// �����
				if( tmp.equals("quit") )
					break;
				else if(tmp.equals("������������"))
				{
				}
				else if(tmp.equals("����������"))
				{
					System.out.println("���������� �޾ҽ��ϴ�.");
					System.out.println(str);
					break;
				}
				else
				{
					str += tmp + "\n";
					nIndex++;
				}
			}
		
			int nFileCount = 0;
			int nSize = 0;
			StringTokenizer st = new StringTokenizer(str, "\n");
		
			lstFiles = new String[nIndex];
			m_lstFileName = new String[nIndex];
			m_lstFileSize = new String[nIndex];
			m_lstFileDate = new String[nIndex];
		
//System.out.print("���?:");
//System.out.println(nIndex);

			while( st.hasMoreTokens() )
			{
				lstFiles[nSize] = st.nextToken();
//System.out.println("lstFiles[" + nSize + "] : [" + lstFiles[nSize] + "]");
				nSize++;
			}
			
			String letsgo = "���۽����ض�!\n";
			m_os.write( letsgo.getBytes());
			m_os.flush();			
			
			// �� ���� ǥ��
			StringTokenizer stt;
			String fileName, fileSize, fileDate;
			int nTotalFileSize = 0;
			for(int i=0; i<lstFiles.length; i++)			
			{
//System.out.println("lstFiles[" + i + "] : [" + lstFiles[i] + "]");
				stt = new StringTokenizer(lstFiles[i], ";");
				
				while( stt.hasMoreTokens() )
				{
					m_lstFileName[i] = fileName = stt.nextToken();
					m_lstFileSize[i] = fileSize = stt.nextToken();
					m_lstFileDate[i] = fileDate = stt.nextToken();
//System.out.println("fileName : [" + fileName + "]");
//System.out.println("fileSize : [" + fileSize + "]");
//System.out.println("fileDate : [" + fileDate + "]");
	
					m_nTotalSize = nTotalFileSize += Integer.parseInt( fileSize );					
				}
			}
			// �������ϼ� and ���ѻ�����
			tmp = (new DecimalFormat("####")).format(lstFiles.length );
			�������ϼ���.setText( "1/"+tmp );
			tmp = (new DecimalFormat("##,###,###,###,### KB")).format(nTotalFileSize);
			���ѻ����.setText( tmp );
				
			download dd = new download();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	// ���Һд� ��ٸ�
	public void setRole()
	{
		try
		{
			BufferedReader br = new BufferedReader( new InputStreamReader(m_is) );
			
			int nRead;
			String str= "";
			String tmp;
			
			boolean bRcvFileInfo = false;
			boolean bQuit = false;
			
			int nIndex = 0;
			
			while(true)
			{
				System.out.println("��ɴ����...");
				tmp = br.readLine();
				
//System.out.println("rcv data : [" + tmp + "]");
				
				// �������϶�
				if( tmp.equals(m_cmdRcvFile) )
				{
					// ���� ���ϼ����ϴ� �������� �˾����ϱ� �������������� �Ѵ�.					
					System.out.println("���� ���� ����� �޾ҽ��ϴ�!");
					m_itemFileOpen.setEnabled(false);
					m_toolbarFile.setEnabled(false);
					m_itemFileOpen.setEnabled(false);
					m_bSndFile = false;					
					rcvFileInfoThread rf = new rcvFileInfoThread();
					sndCommand(1);
					break;
				}
				// �������϶�
				else if( tmp.equals(m_cmdSndFile) )
				{
					System.out.println("���� ���� ����� �޾ҽ��ϴ�.");
					m_itemFileOpen.setEnabled(false);
					m_toolbarFile.setEnabled(false);
					m_itemFileOpen.setEnabled(false);
					m_bSndFile = true;
					sndFileInfo();
					m_bWorking = true;
					initiateTransfer it = new initiateTransfer();
					break;
				}
				// �����
				else if( tmp.equals("quit") )
				{
					System.out.println("���� ����� �޾ҽ��ϴ�.");
					sndCommand(2);
					bQuit = true;
					break;
				}
			}
			if( bQuit )
			{
				m_itemServer.setEnabled(true);
				m_itemConnect.setEnabled(true);
				if( m_socket != null )
				{
					m_is.close();
					m_os.close();
					m_socket.close();
				}				
				if( m_bServer )
				{
					m_sockServer.close();
					m_sockServer = null;
					m_bServer = false;
				}				
				m_socket = null;
				
				// ���� �����Ѵٸ�
				if( m_bQuit )
				{
					System.out.println("�����մϴ�. �����մϴ�.");
					System.exit(0);
				}
				else
				{
					System.out.println("������ �����Ǿ����ϴ�.");
					m_toolbarFile.setEnabled(false);
					m_itemFileOpen.setEnabled(false);
				}
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	// ���� ������
	class acceptClient extends Thread
	{
		public acceptClient()
		{
			start();
		}
		public void run()
		{
			// �����϶� ÷���� �������������
			if( m_bServer && m_socket == null )
			{
				try
				{
					m_socket = m_sockServer.accept();
					if( m_socket != null )
					{
						m_os = m_socket.getOutputStream();
						m_is = m_socket.getInputStream();
						m_bConnected = true;
						rcvCommand rp = new rcvCommand();
						System.out.println("����Ǿ����ϴ�.");
						m_itemServer.setEnabled(false);
						m_itemConnect.setEnabled(false);
						m_toolbarFile.setEnabled(true);
					}
				}
				catch(IOException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	// �����ʱ�ȭ ������
	class initiateTransfer extends Thread
	{
		public initiateTransfer()
		{
			start();
		}
		public void run()
		{
			// ���������� �����ϰ�, "���Ͻ����ض�!"�� �ö����� ��ٸ���.
			try
			{
				BufferedReader br = new BufferedReader( new InputStreamReader(m_is) );
				String msg;
				
				System.out.println("�������� ���۸���� ��ٸ��� �ֽ��ϴ�.");
				msg = br.readLine();
				
				System.out.println("���ŵ� �޽��� : [" + msg + "]");
				
				if( msg.equals("���۽����ض�!"))
				{
					upload up = new upload();
				}
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	// ������������ ������ ����
	class rcvFileInfoThread extends Thread		
	{
		public rcvFileInfoThread()
		{
			start();
		}
		public void run()
		{
			rcvFileInfo();
		}
	}
	
	// ���� �д� ��ٸ�
	class rcvCommand extends Thread
	{
		public rcvCommand()
		{
			start();
		}		

		public void run()
		{
			// Ȱ��ȭ ��Ų��.
			initFileChat();
			m_itemFileOpen.setEnabled(true);
			m_toolbarFile.setEnabled(true);
			m_bCancel = false;
			setRole();
		}
	}
	
	// upload Thread
	class upload extends Thread
	{
		public upload()
		{			
			
			start();
		}
		public void run()
		{
			FileOutputStream logServer = null;
			int nFileSizeTmp;
			String tmp;
	
			// �α׼����� �Ǿ������� ������ �����Ѵ�.
			if( m_bLogging )
			{
				Date date = new Date();
				try
				{
					String logFileName = date.toString() + ".log";
					logFileName = logFileName.replace(' ', '_');
					logFileName = logFileName.replace(':', '_');									
					logServer = new FileOutputStream( m_strLogDir + logFileName );
				}
				catch(IOException e)
				{
					e.printStackTrace();
				}
			}
			
			m_dTotalValue = 0;
			��ü�����.setValue(0);
			m_toolbarCancel.setEnabled(true);
			
			for(int i=0; i<m_lstSelectedFiles.length; i++)
			{
				�������ϸ�.setText(m_lstFileName[i]);
				
				nFileSizeTmp = Integer.parseInt( m_lstFileSize[i] );
				tmp = (new DecimalFormat("###,###,###,### KB")).format( nFileSizeTmp );				
				���������.setText( tmp );
				�������ϼ���.setText( i+1 + "/" + m_lstSelectedFiles.length );
				
				m_dCurValue = 0;
				���������.setValue(0);
				
				int nRet = sndFile( i, m_lstSelectedFiles[i].getPath() );
				
				if( nRet == 0 )
				{
					// output log�� �����.
					try
					{
						if( m_bLogging )
						{	
							String str;							
							Date dt = new Date(System.currentTimeMillis());
							
							str = "[" + i + "] " + dt.toString() + "\r\n";
							str += "-------------------------------------------\r\n";
							str += "�� �� �� : [" + m_lstFileName[i] + "]\r\n";
							str += "����ũ�� : [" + m_lstFileSize[i] + "]\r\n";
							str += "���ϳ�¥ : [" + m_lstFileDate[i] + "]\r\n";
							str += "-------------------------------------------\r\n\r\n";
							
							logServer.write(str.getBytes());
						}
					}
					catch(IOException e)
					{
						e.printStackTrace();
					}
				}
				// ��ҹ�ư ������
				else if( nRet == 2)
				{
					// ���ʱ�ȭ
					initFileChat();
					break;
				}
				else
					break;
			}
			m_bWorking = false;
			m_toolbarCancel.setEnabled(false);
			
			if( m_bLogging )
			{
				try
				{
					logServer.close();
				}
				catch(IOException e)
				{
					e.printStackTrace();
				}
			}
			
			// �ٽ� ���Һд��� ��ٸ���.
			rcvCommand rc = new rcvCommand();
		}
	}
	
	class download extends Thread
	{
		public download()
		{			
			start();
		}
		public void run()
		{
			m_dTotalValue = 0;
			
			int nRet;
			int nFileSizeTmp;
			String tmp;
			
			FileOutputStream logClient = null;
			
			if( m_bLogging )
			{
				Date date = new Date();
				try
				{
					String logFileName = date.toString() + ".log";
					logFileName = logFileName.replace(' ', '_');
					logFileName = logFileName.replace(':', '_');
					logClient = new FileOutputStream( m_strLogDir + logFileName );
				}
				catch(IOException e)
				{
					e.printStackTrace();
				}
			}
			
			��ü�����.setValue(0);
			m_bWorking = true;
			m_toolbarCancel.setEnabled(true);
			
			for(int i=0; i<m_lstFileName.length; i++)
			{
				�������ϸ�.setText(m_lstFileName[i]);
				nFileSizeTmp = Integer.parseInt( m_lstFileSize[i] );
				tmp = (new DecimalFormat("###,###,###,### KB")).format( nFileSizeTmp );
				���������.setText( tmp );
				�������ϼ���.setText( i+1 + "/" + m_lstFileName.length );
				
				m_dCurValue = 0;
				���������.setValue(0);
				
				nRet = rcvFile(i, m_lstFileName[i], m_lstFileSize[i], m_lstFileDate[i]);
				
				if(nRet == 0)
				{
					// output log�� �����.
					try
					{
						if( m_bLogging )
						{	
							String str;						
							Date dt = new Date();						
							str = "------------------------------------------------------------------\r\n";
							str += "[" + i + "] ���ſϷ� : [" + dt.toString() + "]\r\n";
							str += "------------------------------------------------------------------\r\n";
							str += "�� �� �� : [" + m_lstFileName[i] + "]\r\n";						
							str += "����ũ�� : [" + m_lstFileSize[i] + "]\r\n";						
							str += "���ϳ�¥ : [" + m_lstFileDate[i] + "]\r\n";						
							str += "------------------------------------------------------------------\r\n\r\n";						
							System.out.println(str);
							logClient.write(str.getBytes());						
						}
					}
					catch(IOException e)
					{
						e.printStackTrace();
					}
				}
				// ��ҹ�ư ������
				else if( nRet == 2)
				{
					// �󺧰� �ʱ�ȭ
					initFileChat();
					break;
				}
				else
					break;
			}
			try
			{
				logClient.close();
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
			m_bWorking = false;
			m_toolbarCancel.setEnabled(false);
			
			// �ٽ� ���Һд��� ��ٸ���.
			rcvCommand rp = new rcvCommand();
		}
	}
	
	// ���� �����ϴ� �Լ� ��ȯ 0:���� 1:���� 2:���
	public int sndFile(int j, String fileName)
	{
		boolean bFail = false;
		boolean bCancel = false;
		
		try
		{
			FileInputStream fis = new FileInputStream(fileName);
			
			String ����, ����, ����ȣ;	// 5�ڸ�, 6�ڸ�, 6�ڸ�
			byte[] snd_buf = new byte[4079];
			byte[] recv_buf = new byte[14];
			
			
			long nFileSize = 0;			
			int nIndex = 1;
			int nRead;
			long nRemaint;
			double dCurBarPos = 0, dTotalBarPos = 0;
			// ������ ����� ���ؼ� ����ȣ�� �˾Ƴ���.
			nFileSize = (new File(fileName)).length();
			int nEnd = (int)nFileSize/4079;
			nRemaint = nFileSize%4079;
			if( nRemaint != 0 )
				nEnd++;
			else
				nRemaint = 4079;
			
			����ȣ = (new DecimalFormat("000000")).format(nEnd);
			
			for(int i=0; ; )
			{
				// ��ҹ�ư ������....
				if( m_bCancel )
				{					
					���� = "00000";
					���� = "000000";
					����ȣ = "000000\n";
					m_os.write( ����.getBytes() );
					m_os.write( ����.getBytes() );
					m_os.write( ����ȣ.getBytes());					
					m_os.flush();
					bCancel = true;
					break;
				}			
				
				if( nEnd == nIndex )
				{
					i = fis.read(snd_buf, 0, (int)nRemaint);
//System.out.println("������:[" + (int)nRemaint +"]");
//System.out.println( i );
				}
				else if( nEnd != nIndex )
				{
					if( nEnd != 0)
						i = fis.read(snd_buf, 0, 4079);
					else
						i = 0;
				}
				if( i == -1 )
					break;
				
				���� = (new DecimalFormat("00000")).format( 12+i );
				���� = (new DecimalFormat("000000")).format( nIndex );

//System.out.println("���� : ["+ ���� + "]");
//System.out.println("���� : ["+ ���� + "]");
//System.out.println("����ȣ : ["+ ����ȣ + "]");
				
				// ������ ����
				m_os.write( ����.getBytes());
				m_os.write( ����.getBytes());
				m_os.write( ����ȣ.getBytes());
				
				if( nEnd == nIndex )
				{
					m_os.write(snd_buf,0, (int)nRemaint);
					m_dCurValue = m_dCurValue + nRemaint;
					m_dTotalValue += nRemaint;
				}
				else if( nEnd == 0)
				{
					m_dCurValue = 0;					
				}
				else
				{
					m_os.write(snd_buf);
					m_dCurValue = m_dCurValue + 4079;
					m_dTotalValue += 4079;
				}
				m_os.flush();
//System.out.println("snd_buf ���� :[" + snd_buf.length + "]");

				// ����ٸ� ǥ������
				dCurBarPos = m_dCurValue/Double.parseDouble(m_lstFileSize[j]) * 100;
				dTotalBarPos = m_dTotalValue/(double)m_nTotalSize*100;
					
				���������.setValue( (int)dCurBarPos );
				��ü�����.setValue( (int)dTotalBarPos );
				
				// Ȯ�� ����
				nRead = m_is.read(recv_buf, 0, 5);
				
				String strCancel = new String(recv_buf, 0, nRead);
				// ���� ��� �Ǿ�����
				if( strCancel.equals("00000") )					
				{
					nRead = m_is.read(recv_buf, 0, 6);
					
					strCancel = new String(recv_buf, 0, nRead );
					
					if( strCancel.equals("000000") )
					{					
						System.out.println("Ŭ���̾�Ʈ�� ���ϼ����� ����Ͽ����ϴ�!");
						bCancel = true;
						break;
					}
				}
				int nRem = Integer.parseInt( new String(recv_buf, 0, nRead) );
				nRead = m_is.read(recv_buf, 0, 6);
				int nRecvIndex = Integer.parseInt( new String(recv_buf, 0, nRead) );				
				
				if( nRecvIndex != nIndex)
				{
					String str = fileName + "���� ������ [" + nIndex + "]�� �Ǿ�� �ϴµ�\n"
						+ "�߸��� ����[" + nRecvIndex + "]�� ���ŵǾ����ϴ�!";
					JOptionPane.showMessageDialog(this, str, "���Ͼ��ε� ����!!!!",JOptionPane.YES_NO_OPTION);
					bFail = true;
					break;
				}
				nRead = m_is.read(recv_buf, 0, 2);
				String strOK = new String(recv_buf,0,2);
				if( strOK.equals("OK") == false)
				{
					JOptionPane.showMessageDialog(this, "OK�� ���Ź��� ���߽��ϴ�!", "���Ͼ��ε� ����!!!!",JOptionPane.YES_NO_OPTION);
					bFail = true;
					break;
				}
				else
				{
//System.out.println(nIndex + " : OK ����");
				}
				
				if(nEnd == 0)
					break;
				
				nIndex++;
			}
			fis.close();			
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		if( bFail )
			return 1;
		if( bCancel )
			return 2;			
			
		return 0;
	}
	
	// ���� ���� �Լ� ��ȯ�� 0 : ���� 1:���� 2:���
	public int rcvFile(int i, String fileName, String fileSize, String fileDate)
	{
		boolean bCancel = false;
		try
		{
			m_fos = new FileOutputStream(m_strDownDir+fileName);
			
			byte[] recv_buf = new byte[4096];
			byte[] tmp_packet = new byte[4096];
			int nRead;
			int nHdr;
			int nRcvIndex = 0;
			int nEnd;
			int nPktSize, nLen, nSize;
			double dCurBarPos = 0;
			double dTotalBarPos = 0;	
			String ����;
			String ����, ����ȣ, strOK = "OK";
			
			while(true)
			{	
				nRead = m_is.read(recv_buf,0 , 5);
				���� = new String(recv_buf, 0, nRead);
				
				// ��� ����� ������
				if( ����.equals("00000") )
				{
					System.out.println("��� ���Ե�.");
					nRead = m_is.read(recv_buf, 0, 6);
					���� = new String(recv_buf, 0, nRead);
					nRead = m_is.read(recv_buf, 0, 6);
					����ȣ = new String(recv_buf, 0, nRead);
					if( ����.equals("000000") && ����ȣ.equals("000000"))
					{
						System.out.println("������ ���������� ����Ͽ����ϴ�!");
						bCancel = true;
						break;
					}
				}
//System.out.println("���� : ["+ ���� + "]");
				nHdr = Integer.parseInt( ���� );
				
				nRead = m_is.read(recv_buf, 0, 6);
				���� = new String(recv_buf, 0, nRead);
//System.out.println("���� : ["+ ���� + "]");

				nRead = m_is.read(recv_buf, 0, 6);
				����ȣ = new String(recv_buf, 0, nRead);
//System.out.println("����ȣ : ["+ ����ȣ + "]");

				nRcvIndex = Integer.parseInt( ���� );
				nEnd = Integer.parseInt(����ȣ);
				
				nPktSize = nHdr - 12;
//System.out.println("nPktSize : [" + nPktSize + "]");

				nLen = 0;
				nRead = 0;
				nSize = 1000;
				if( nPktSize > 0 )
				while(nLen < nPktSize)
				{				
					nRead = m_is.read(tmp_packet,0,nSize);
					// ���Ϸ� ��������!
					m_fos.write(tmp_packet,0, nRead);
					
					// ����ٸ� ǥ������
					m_dCurValue = m_dCurValue + nRead;
					m_dTotalValue += nRead;
					
					dCurBarPos = m_dCurValue/Double.parseDouble(m_lstFileSize[i]) * 100;
					dTotalBarPos = m_dTotalValue/(double)m_nTotalSize*100;
					
					���������.setValue( (int)dCurBarPos );
					��ü�����.setValue( (int)dTotalBarPos );
					
					nLen += nRead;
					
					nSize = nPktSize - nRead;
					if( nSize >= tmp_packet.length )
						nSize = tmp_packet.length;
					
					if( nLen == nPktSize)
						break;
				}
				
				// ��ҹ�ư ������....
				if( m_bCancel )
				{									
					���� = "00000";
					���� = "000000";
					����ȣ = "000000\n";
					m_os.write( ����.getBytes() );
					m_os.write( ����.getBytes() );
					m_os.write( ����ȣ.getBytes());
					m_os.flush();
					bCancel = true;
					break;
				}
				
				// Ȯ���� ��������
				���� = (new DecimalFormat("00000")).format( 8 );
				���� = (new DecimalFormat("000000")).format( nRcvIndex );
				
				m_os.write( ����.getBytes() );
				m_os.write( ����.getBytes() );
				m_os.write( strOK.getBytes() );
				m_os.flush();
//System.out.println("OK ����");
				
				if( nRcvIndex == nEnd || nEnd == 0)
				{
//System.out.println(m_lstFileName[i] + "���ſϷ�");
					break;
				}
			}
			m_fos.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		// ��ҵǾ�������
		if( bCancel )
			return 2;
		return 0;
	}
	
	public static void main(String argv[])
	{
		JFrame f = new FileChat();
		f.setSize(325,210);
		f.setVisible(true);
		f.setResizable(false);
	}
}