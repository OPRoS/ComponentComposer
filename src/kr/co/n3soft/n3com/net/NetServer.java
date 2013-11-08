package kr.co.n3soft.n3com.net;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import kr.co.n3soft.n3com.net.FileChat.acceptClient;
import kr.co.n3soft.n3com.net.FileChat.rcvCommand;

public class NetServer {
	ServerSocket	m_sockServer = null;
	Socket			m_socket = null;
	FileOutputStream m_fos;
	
	InputStream m_is;
	OutputStream m_os;
	
	String saveFolder = "c:\\temp";
	
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
			
		
		System.out.println("���������� �����Ǿ����ϴ�.");
		return true;
	}
	
	public void start(){
		createServerSocket();
		acceptClient ac = new acceptClient();
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
			if( m_sockServer!=null && m_socket == null )
			{
				try
				{
					m_socket = m_sockServer.accept();
					if( m_socket != null )
					{
						m_os = m_socket.getOutputStream();
						m_is = m_socket.getInputStream();
						
						BufferedReader br = new BufferedReader( new InputStreamReader(m_is) );
						System.out.println("����Ǿ����ϴ�.");
						String tmp = null;
						while(true){
							tmp = br.readLine();
							System.out.println("tmp=======>"+tmp);
							
						}
					
					}
				}
				catch(IOException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	public static void main(String[] args) {
		NetServer ns = new NetServer();
		ns.start();
		
	}

}
