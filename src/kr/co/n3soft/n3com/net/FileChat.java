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
	
	JLabel		라벨총파일수, 라벨총파일수값;
	JLabel		라벨총사이즈값;
	JLabel		라벨현파일명, 라벨현파일명값;
	JLabel		라벨현사이즈값;
	
	JProgressBar	전체진행바;
	JProgressBar	현재진행바;
	
	File[]		m_lstSelectedFiles;	
	
	String		m_strFileCount="";	
	String[]	m_lstFileName;
	String[]	m_lstFileSize;
	String[]	m_lstFileDate;	
	String		m_strIP = "127.0.0.1";
	String		m_strDownDir = "down/";
	String		m_strLogDir = "log/";
	
	static final String	m_cmdRcvFile = "파일을 수신해라!";
	static final String	m_cmdSndFile = "파일을 전송해라!";
	static final String	m_cndCompleteSnd = "파일전송완료";
	static final String	m_cndCompleteRcv = "파일전송완료";
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
	
	// 생성자
	public FileChat()
	{
		super("onestone FileChat");
		
		// 폴더 만들자
		makeLogDownFolder();
		
		Container c  = getContentPane();
		
		// 메뉴생성
		setJMenuBar( makeMenu() );
		
		// 툴바 생성		
		c.add( makeToolBar() );
		
		// 폰트 설정
		m_font = new Font("Dialog", Font.PLAIN, 12);
		
		// 배경그림 판넬 설정
		Image image = Toolkit.getDefaultToolkit().getImage("image/43.gif");
		Dimension window = new Dimension(325,170);
		ImagePanel back = new ImagePanel(image, window);
		back.setLayout( null );
		back.setBounds(0,0, window.width, window.height);
		
		// 라벨파일수 설정
		라벨총파일수 = new JLabel("파일수:");
		라벨총파일수.setBounds( 5, 40, 45, 25 );
		back.add( 라벨총파일수);
		
		라벨총파일수값 = new JLabel();
		라벨총파일수값.setBounds( 55, 40, 50, 25 );
		라벨총파일수.setFont(m_font);
		라벨총파일수값.setFont(m_font);
		back.add( 라벨총파일수값 );
		
		// 라벨총사이즈값
		라벨총사이즈값 = new JLabel("KB", SwingConstants.RIGHT);		
		라벨총사이즈값.setBounds( 205, 40, 100, 25 );
		라벨총사이즈값.setFont(m_font);
		back.add( 라벨총사이즈값 );
		
		// 전체진행바  설정
		전체진행바 = new JProgressBar(0, 100);
		전체진행바.setBounds( 5 , 70, 300, 5 );
		전체진행바.setValue(0);		
		back.add( 전체진행바 );
		
		// 라벨현파일명 설정
		라벨현파일명 = new JLabel("파일명:");
		라벨현파일명.setBounds( 5, 90, 40, 25 );
		라벨현파일명.setFont(m_font);
		back.add( 라벨현파일명 );
		라벨현파일명값 = new JLabel();
		라벨현파일명값.setBounds( 50, 90, 150, 25 );
		라벨현파일명값.setFont(m_font);
		back.add( 라벨현파일명값 );
		
		// 라벨현사이즈 설정
		라벨현사이즈값 = new JLabel("KB", SwingConstants.RIGHT);		
		라벨현사이즈값.setBounds( 205, 90, 100, 25 );
		라벨현사이즈값.setFont(m_font);		
		back.add( 라벨현사이즈값 );
		
		// 현재진행바
		현재진행바 = new JProgressBar(0, 100);
		현재진행바.setBounds( 5 , 120, 300, 5 );		
		현재진행바.setValue(0);		
		back.add( 현재진행바 );
		
		c.add(back, BorderLayout.CENTER );
		
		// 윈도우 종료 설정
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				if( m_socket != null )
				{
					// 종료명령 보냄
					m_bQuit = true;
					sndCommand(2);
				}
				else
					System.exit(0);
			}
		});
		
		// 취소버튼과 파일보내기버튼을 막는다.
		m_toolbarCancel.setEnabled(false);
		m_toolbarFile.setEnabled(false);
		m_itemFileOpen.setEnabled(false);
	}
	
	//  [log]폴더와 [down]폴더를 만들자
	public void makeLogDownFolder()
	{	
		File file = new File("log");
		file.mkdir();
		file = new File("down");
		file.mkdir();
	}
	
	//  메뉴를 만든다.
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
		m_itemConnect = new JMenuItem("서버에 연결하기");
		m_itemServer = new JMenuItem("Server 가동");
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
	
	// 툴바를 만들자
	public JToolBar makeToolBar()
	{
		JPanel pan = new JPanel();
		pan.setLayout(null);
		m_toolbar = new JToolBar();
		m_toolbarFile = new JButton("파일업로드");
		m_toolbarFile.setBorder( BorderFactory.createRaisedBevelBorder() );		
		m_toolbarFile.setFont(m_font);
		m_toolbarFile.setBounds(0,0,65,24);
		m_toolbarFile.setOpaque(true);
		m_toolbarFile.setBackground(Color.blue);
		m_toolbarFile.setForeground(Color.yellow);
		pan.add( m_toolbarFile );
		
		m_toolbarChat = new JButton("채팅");
		m_toolbarChat.setBorder( BorderFactory.createRaisedBevelBorder() );
		m_toolbarChat.setFont(m_font);
		m_toolbarChat.setBounds(67,0,30,24);
		m_toolbarChat.setOpaque(true);
		m_toolbarChat.setBackground(Color.blue);
		m_toolbarChat.setForeground(Color.yellow);
		pan.add( m_toolbarChat);
		
		m_toolbarCancel = new JButton("취소");
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
	
	// 라벨뿐만 아니라 여러가지를 초기화한다.
	public void initFileChat()
	{
		라벨총파일수값.setText( "" );
		라벨총사이즈값.setText( "KB" );
		라벨현파일명값.setText( "" );
		라벨현사이즈값.setText( "KB" );
		현재진행바.setValue(0);
		전체진행바.setValue(0);
	}
	
	// 이벤트 핸들링 함수
	public void actionPerformed(ActionEvent e)
	{
		Object ob = e.getSource();
		
		// 파일업로드를 할적에
		if( ob == m_itemFileOpen || ob == m_toolbarFile )
		{			
			// 선택한 파일이 있으면 소켓을 생성연결하여 전송시작!			
			if( getUploadFileCount() > 0 )
			{
				// 상대방에게 파일받으라고 명령내림
				sndCommand(0);
			}
		}
		// IP설정 메뉴를 누를때
		else if( ob == m_itemSetting )
		{
			OptDialog od = new OptDialog(this, "옵션 설정", true);
			od.pack();
			od.setLocation(300,300);
			od.setResizable(false);
			od.setVisible(true);
//System.out.println("(MAIN) : [" + m_strIP + "]");
		}
		// 내 정보를 누를때
		else if( ob == m_itemMyInfo )
		{
			Programmer onestone = new Programmer(this, "만든이", true);
			onestone.pack();
			onestone.setLocation(400,300);
			onestone.setResizable(false);
			onestone.setVisible(true);
		}
		// 서버에 연결해서 역할분담을 기다린다.
		else if( ob == m_itemConnect )
		{
			if( m_socket == null )
			{
				connectNetwork();
				rcvCommand rp = new rcvCommand();
			}
		}
		// 서버 생성하고 허락쓰레드를 띄운다.
		else if( ob == m_itemServer )
		{			
			if( m_sockServer == null)
			{
				if( createServerSocket() )
				{
					// 서버가동 메뉴 막음
					m_itemServer.setEnabled(false);
					m_itemConnect.setEnabled(false);
					setTitle("FileChat 서버입니다.");
					acceptClient ac = new acceptClient();
				}
			}
		}
		// 취소버튼 누를적에
		else if( ob == m_toolbarCancel )
		{
			m_bCancel = true;
			System.out.println("취소버튼을 눌렀습니다.");
		}
		// 종료를 누를적에
		else if( ob == m_itemExit )
		{
			// 종료명령을 보냄
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
	
	// 업로드할 파일을 구하자!
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
			라벨총파일수값.setText( "1/"+m_strFileCount );
//JOptionPane.showMessageDialog(this, m_lstSelectedFiles[0], "업로드 파일선택",JOptionPane.YES_NO_OPTION);
		}
		
		return nFileCount;
	}
	
	
	// 나는 파일을 보내는 놈이다. type 0: 나는파일전송   1: 나는파일수신    2: Quit전송
	public void sndCommand(int type)
	{	
		try
		{
			String msg;
			if(type == 0)
			{
				// "너는 파일을 수신해라!";
				msg = m_cmdRcvFile + "\n";
				m_os.write(msg.getBytes());
				m_os.flush();
				m_bSndFile = true;
				System.out.println("수신 명령을 내렸습니다! 나는 전송자");
			}
			else if(type == 1)
			{
				// "너는 파일을 전송해라!";
				msg = m_cmdSndFile + "\n";
				m_os.write(msg.getBytes());
				m_os.flush();
				m_bSndFile = false;
				System.out.println("전송 명령을 내렸습니다! 나는 수신자");
			}
			else
			{
				msg = m_cmdQuit + "\n";
				m_os.write(msg.getBytes());
				m_os.flush();
				System.out.println("종료명령을 내렸습니다! 나는 종료할것이다");
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	// 서버소켓을 생성한다.
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
		System.out.println("서버소켓이 생성되었습니다.");
		return true;
	}
	
	// 서버소켓 생성과 클라이언트와 연결
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
				System.out.println("[" + m_strIP + "]와 연결되었습니다.");
				m_itemServer.setEnabled(false);
				m_itemConnect.setEnabled(false);
				m_toolbarFile.setEnabled(true);
				setTitle("FileChat 클라이언트입니다.");
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
	
	// 파일 정보를 전송
	public void sndFileInfo()
	{
		try
		{
			int nTotalFileSize = 0;			
			String str;			
			String start = "파일정보시작\n";
			m_os.write( start.getBytes() );
			m_os.flush();
			
			m_lstFileName = new String[m_lstSelectedFiles.length];
			m_lstFileSize = new String[m_lstSelectedFiles.length];
			m_lstFileDate = new String[m_lstSelectedFiles.length];
			
			for(int i=0; i< m_lstSelectedFiles.length; i++)
			{
				// 파일정보 변수 설정하기
				m_lstFileName[i] = m_lstSelectedFiles[i].getName();
				m_lstFileSize[i] = (new Long(m_lstSelectedFiles[i].length())).toString();
				m_lstFileDate[i] = (new Date(m_lstSelectedFiles[i].lastModified())).toString();
				
				// 총파일사이즈
				m_nTotalSize = nTotalFileSize += Integer.parseInt( m_lstFileSize[i] );
				
				// 파일정보 만들고 전송하기
				str = m_lstFileName[i] + ";" + m_lstFileSize[i] + ";" + m_lstFileDate[i] + ";\n";
				m_os.write( str.getBytes() );
				m_os.flush();
			}
			
			// 라벨총파일수 and 라벨총사이즈
			String tmp = (new DecimalFormat("####")).format(m_lstFileName.length );
			라벨총파일수값.setText( "1/"+tmp );
			tmp = (new DecimalFormat("#,###,###,###,### KB")).format(nTotalFileSize );			
			라벨총사이즈값.setText( tmp );
			
			String end = "파일정보끝\n";
			m_os.write( end.getBytes() );
			m_os.flush();
			System.out.println("앞으로 전송할 파일정보를 보냈습니다.");
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	// 파일 정보를 수신해서 설정한다.
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
			
			System.out.println("파일정보를 기다리고 있습니다...");
			
			while(true)
			{
				tmp = br.readLine();
				
//System.out.println("rcv data : [" + tmp + "]");
				
				// 종료시
				if( tmp.equals("quit") )
					break;
				else if(tmp.equals("파일정보시작"))
				{
				}
				else if(tmp.equals("파일정보끝"))
				{
					System.out.println("파일정보를 받았습니다.");
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
		
//System.out.print("몇개지?:");
//System.out.println(nIndex);

			while( st.hasMoreTokens() )
			{
				lstFiles[nSize] = st.nextToken();
//System.out.println("lstFiles[" + nSize + "] : [" + lstFiles[nSize] + "]");
				nSize++;
			}
			
			String letsgo = "전송시작해라!\n";
			m_os.write( letsgo.getBytes());
			m_os.flush();			
			
			// 라벨 정보 표시
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
			// 라벨총파일수 and 라벨총사이즈
			tmp = (new DecimalFormat("####")).format(lstFiles.length );
			라벨총파일수값.setText( "1/"+tmp );
			tmp = (new DecimalFormat("##,###,###,###,### KB")).format(nTotalFileSize);
			라벨총사이즈값.setText( tmp );
				
			download dd = new download();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	// 역할분담 기다림
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
				System.out.println("명령대기중...");
				tmp = br.readLine();
				
//System.out.println("rcv data : [" + tmp + "]");
				
				// 수신자일때
				if( tmp.equals(m_cmdRcvFile) )
				{
					// 나는 파일수신하는 역할인지 알았으니깐 파일정보수신을 한다.					
					System.out.println("파일 수신 명령을 받았습니다!");
					m_itemFileOpen.setEnabled(false);
					m_toolbarFile.setEnabled(false);
					m_itemFileOpen.setEnabled(false);
					m_bSndFile = false;					
					rcvFileInfoThread rf = new rcvFileInfoThread();
					sndCommand(1);
					break;
				}
				// 전송자일때
				else if( tmp.equals(m_cmdSndFile) )
				{
					System.out.println("파일 전송 명령을 받았습니다.");
					m_itemFileOpen.setEnabled(false);
					m_toolbarFile.setEnabled(false);
					m_itemFileOpen.setEnabled(false);
					m_bSndFile = true;
					sndFileInfo();
					m_bWorking = true;
					initiateTransfer it = new initiateTransfer();
					break;
				}
				// 종료시
				else if( tmp.equals("quit") )
				{
					System.out.println("종료 명령을 받았습니다.");
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
				
				// 내가 종료한다면
				if( m_bQuit )
				{
					System.out.println("종료합니다. 감사합니다.");
					System.exit(0);
				}
				else
				{
					System.out.println("연결이 해제되었습니다.");
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
	
	// 수락 쓰레드
	class acceptClient extends Thread
	{
		public acceptClient()
		{
			start();
		}
		public void run()
		{
			// 서버일때 첨으로 연결수락할적에
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
						System.out.println("연결되었습니다.");
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
	
	// 전송초기화 쓰레드
	class initiateTransfer extends Thread
	{
		public initiateTransfer()
		{
			start();
		}
		public void run()
		{
			// 파일정보를 전송하고, "파일시작해라!"가 올때까지 기다린다.
			try
			{
				BufferedReader br = new BufferedReader( new InputStreamReader(m_is) );
				String msg;
				
				System.out.println("파일전송 시작명령을 기다리고 있습니다.");
				msg = br.readLine();
				
				System.out.println("수신된 메시지 : [" + msg + "]");
				
				if( msg.equals("전송시작해라!"))
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
	
	// 파일정보수신 쓰레드 생성
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
	
	// 역할 분담 기다림
	class rcvCommand extends Thread
	{
		public rcvCommand()
		{
			start();
		}		

		public void run()
		{
			// 활설화 시킨다.
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
	
			// 로그설정이 되어있으면 파일을 생성한다.
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
			전체진행바.setValue(0);
			m_toolbarCancel.setEnabled(true);
			
			for(int i=0; i<m_lstSelectedFiles.length; i++)
			{
				라벨현파일명값.setText(m_lstFileName[i]);
				
				nFileSizeTmp = Integer.parseInt( m_lstFileSize[i] );
				tmp = (new DecimalFormat("###,###,###,### KB")).format( nFileSizeTmp );				
				라벨현사이즈값.setText( tmp );
				라벨총파일수값.setText( i+1 + "/" + m_lstSelectedFiles.length );
				
				m_dCurValue = 0;
				현재진행바.setValue(0);
				
				int nRet = sndFile( i, m_lstSelectedFiles[i].getPath() );
				
				if( nRet == 0 )
				{
					// output log를 남긴다.
					try
					{
						if( m_bLogging )
						{	
							String str;							
							Date dt = new Date(System.currentTimeMillis());
							
							str = "[" + i + "] " + dt.toString() + "\r\n";
							str += "-------------------------------------------\r\n";
							str += "파 일 명 : [" + m_lstFileName[i] + "]\r\n";
							str += "파일크기 : [" + m_lstFileSize[i] + "]\r\n";
							str += "파일날짜 : [" + m_lstFileDate[i] + "]\r\n";
							str += "-------------------------------------------\r\n\r\n";
							
							logServer.write(str.getBytes());
						}
					}
					catch(IOException e)
					{
						e.printStackTrace();
					}
				}
				// 취소버튼 눈를때
				else if( nRet == 2)
				{
					// 라벨초기화
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
			
			// 다시 역할분담을 기다린다.
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
			
			전체진행바.setValue(0);
			m_bWorking = true;
			m_toolbarCancel.setEnabled(true);
			
			for(int i=0; i<m_lstFileName.length; i++)
			{
				라벨현파일명값.setText(m_lstFileName[i]);
				nFileSizeTmp = Integer.parseInt( m_lstFileSize[i] );
				tmp = (new DecimalFormat("###,###,###,### KB")).format( nFileSizeTmp );
				라벨현사이즈값.setText( tmp );
				라벨총파일수값.setText( i+1 + "/" + m_lstFileName.length );
				
				m_dCurValue = 0;
				현재진행바.setValue(0);
				
				nRet = rcvFile(i, m_lstFileName[i], m_lstFileSize[i], m_lstFileDate[i]);
				
				if(nRet == 0)
				{
					// output log를 남긴다.
					try
					{
						if( m_bLogging )
						{	
							String str;						
							Date dt = new Date();						
							str = "------------------------------------------------------------------\r\n";
							str += "[" + i + "] 수신완료 : [" + dt.toString() + "]\r\n";
							str += "------------------------------------------------------------------\r\n";
							str += "파 일 명 : [" + m_lstFileName[i] + "]\r\n";						
							str += "파일크기 : [" + m_lstFileSize[i] + "]\r\n";						
							str += "파일날짜 : [" + m_lstFileDate[i] + "]\r\n";						
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
				// 취소버튼 누를때
				else if( nRet == 2)
				{
					// 라벨값 초기화
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
			
			// 다시 역할분담을 기다린다.
			rcvCommand rp = new rcvCommand();
		}
	}
	
	// 파일 전송하는 함수 반환 0:성공 1:실패 2:취소
	public int sndFile(int j, String fileName)
	{
		boolean bFail = false;
		boolean bCancel = false;
		
		try
		{
			FileInputStream fis = new FileInputStream(fileName);
			
			String 전문, 순번, 끝번호;	// 5자리, 6자리, 6자리
			byte[] snd_buf = new byte[4079];
			byte[] recv_buf = new byte[14];
			
			
			long nFileSize = 0;			
			int nIndex = 1;
			int nRead;
			long nRemaint;
			double dCurBarPos = 0, dTotalBarPos = 0;
			// 파일의 사이즈를 구해서 끝번호를 알아낸다.
			nFileSize = (new File(fileName)).length();
			int nEnd = (int)nFileSize/4079;
			nRemaint = nFileSize%4079;
			if( nRemaint != 0 )
				nEnd++;
			else
				nRemaint = 4079;
			
			끝번호 = (new DecimalFormat("000000")).format(nEnd);
			
			for(int i=0; ; )
			{
				// 취소버튼 누르면....
				if( m_bCancel )
				{					
					전문 = "00000";
					순번 = "000000";
					끝번호 = "000000\n";
					m_os.write( 전문.getBytes() );
					m_os.write( 순번.getBytes() );
					m_os.write( 끝번호.getBytes());					
					m_os.flush();
					bCancel = true;
					break;
				}			
				
				if( nEnd == nIndex )
				{
					i = fis.read(snd_buf, 0, (int)nRemaint);
//System.out.println("나머지:[" + (int)nRemaint +"]");
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
				
				전문 = (new DecimalFormat("00000")).format( 12+i );
				순번 = (new DecimalFormat("000000")).format( nIndex );

//System.out.println("전문 : ["+ 전문 + "]");
//System.out.println("순번 : ["+ 순번 + "]");
//System.out.println("끝번호 : ["+ 끝번호 + "]");
				
				// 프레임 전송
				m_os.write( 전문.getBytes());
				m_os.write( 순번.getBytes());
				m_os.write( 끝번호.getBytes());
				
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
//System.out.println("snd_buf 길이 :[" + snd_buf.length + "]");

				// 진행바를 표시하자
				dCurBarPos = m_dCurValue/Double.parseDouble(m_lstFileSize[j]) * 100;
				dTotalBarPos = m_dTotalValue/(double)m_nTotalSize*100;
					
				현재진행바.setValue( (int)dCurBarPos );
				전체진행바.setValue( (int)dTotalBarPos );
				
				// 확인 수신
				nRead = m_is.read(recv_buf, 0, 5);
				
				String strCancel = new String(recv_buf, 0, nRead);
				// 수신 취소 되었을때
				if( strCancel.equals("00000") )					
				{
					nRead = m_is.read(recv_buf, 0, 6);
					
					strCancel = new String(recv_buf, 0, nRead );
					
					if( strCancel.equals("000000") )
					{					
						System.out.println("클라이언트가 파일수신을 취소하였습니다!");
						bCancel = true;
						break;
					}
				}
				int nRem = Integer.parseInt( new String(recv_buf, 0, nRead) );
				nRead = m_is.read(recv_buf, 0, 6);
				int nRecvIndex = Integer.parseInt( new String(recv_buf, 0, nRead) );				
				
				if( nRecvIndex != nIndex)
				{
					String str = fileName + "에서 순번이 [" + nIndex + "]가 되어야 하는데\n"
						+ "잘못된 순번[" + nRecvIndex + "]이 수신되었습니다!";
					JOptionPane.showMessageDialog(this, str, "파일업로드 에러!!!!",JOptionPane.YES_NO_OPTION);
					bFail = true;
					break;
				}
				nRead = m_is.read(recv_buf, 0, 2);
				String strOK = new String(recv_buf,0,2);
				if( strOK.equals("OK") == false)
				{
					JOptionPane.showMessageDialog(this, "OK를 수신받지 못했습니다!", "파일업로드 에러!!!!",JOptionPane.YES_NO_OPTION);
					bFail = true;
					break;
				}
				else
				{
//System.out.println(nIndex + " : OK 수신");
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
	
	// 수신 파일 함수 반환값 0 : 성공 1:실패 2:취소
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
			String 전문;
			String 순번, 끝번호, strOK = "OK";
			
			while(true)
			{	
				nRead = m_is.read(recv_buf,0 , 5);
				전문 = new String(recv_buf, 0, nRead);
				
				// 취소 명령을 받으면
				if( 전문.equals("00000") )
				{
					System.out.println("취소 들어왔따.");
					nRead = m_is.read(recv_buf, 0, 6);
					순번 = new String(recv_buf, 0, nRead);
					nRead = m_is.read(recv_buf, 0, 6);
					끝번호 = new String(recv_buf, 0, nRead);
					if( 순번.equals("000000") && 끝번호.equals("000000"))
					{
						System.out.println("서버가 파일전송을 취소하였습니다!");
						bCancel = true;
						break;
					}
				}
//System.out.println("전문 : ["+ 전문 + "]");
				nHdr = Integer.parseInt( 전문 );
				
				nRead = m_is.read(recv_buf, 0, 6);
				순번 = new String(recv_buf, 0, nRead);
//System.out.println("순번 : ["+ 순번 + "]");

				nRead = m_is.read(recv_buf, 0, 6);
				끝번호 = new String(recv_buf, 0, nRead);
//System.out.println("끝번호 : ["+ 끝번호 + "]");

				nRcvIndex = Integer.parseInt( 순번 );
				nEnd = Integer.parseInt(끝번호);
				
				nPktSize = nHdr - 12;
//System.out.println("nPktSize : [" + nPktSize + "]");

				nLen = 0;
				nRead = 0;
				nSize = 1000;
				if( nPktSize > 0 )
				while(nLen < nPktSize)
				{				
					nRead = m_is.read(tmp_packet,0,nSize);
					// 파일로 저장하자!
					m_fos.write(tmp_packet,0, nRead);
					
					// 진행바를 표시하자
					m_dCurValue = m_dCurValue + nRead;
					m_dTotalValue += nRead;
					
					dCurBarPos = m_dCurValue/Double.parseDouble(m_lstFileSize[i]) * 100;
					dTotalBarPos = m_dTotalValue/(double)m_nTotalSize*100;
					
					현재진행바.setValue( (int)dCurBarPos );
					전체진행바.setValue( (int)dTotalBarPos );
					
					nLen += nRead;
					
					nSize = nPktSize - nRead;
					if( nSize >= tmp_packet.length )
						nSize = tmp_packet.length;
					
					if( nLen == nPktSize)
						break;
				}
				
				// 취소버튼 누르면....
				if( m_bCancel )
				{									
					전문 = "00000";
					순번 = "000000";
					끝번호 = "000000\n";
					m_os.write( 전문.getBytes() );
					m_os.write( 순번.getBytes() );
					m_os.write( 끝번호.getBytes());
					m_os.flush();
					bCancel = true;
					break;
				}
				
				// 확인을 전송하자
				전문 = (new DecimalFormat("00000")).format( 8 );
				순번 = (new DecimalFormat("000000")).format( nRcvIndex );
				
				m_os.write( 전문.getBytes() );
				m_os.write( 순번.getBytes() );
				m_os.write( strOK.getBytes() );
				m_os.flush();
//System.out.println("OK 전송");
				
				if( nRcvIndex == nEnd || nEnd == 0)
				{
//System.out.println(m_lstFileName[i] + "수신완료");
					break;
				}
			}
			m_fos.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		// 취소되었을적에
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