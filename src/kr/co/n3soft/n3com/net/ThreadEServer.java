package kr.co.n3soft.n3com.net;

import java.io.*;
import java.net.*;
import java.lang.String;

class ThreadEServer
{
	public static void main(String[] args) throws IOException
	{
		ServerSocket server = new ServerSocket(7308,2); // 2381 Port

		System.out.println("Server Start!");

		while (true)
		{
			Socket socket = server.accept();
			new Connection(socket);
		}
	}

	static class Connection extends Thread
	{
		private Socket socket;
		private InputStream fromClient;
		private OutputStream toClient;

		public Connection(Socket socket) throws IOException
		{
			System.out.println("connecting  " + socket);
			this.socket = socket;
			fromClient = socket.getInputStream();
			toClient   = socket.getOutputStream();
			start();
		}

		public void run()
		{
			InputStream fromClient =null;
			OutputStream toClient = null;
//			FileOutputStream m_fos = new FileOutputStream(m_strDownDir+fileName);
			FileOutputStream m_fos = null;
			try
			{
				 fromClient = socket.getInputStream();
				 toClient = socket.getOutputStream();
//				FileOutputStream m_fos = new FileOutputStream(m_strDownDir+fileName);
				 m_fos = null;

				byte[] buf = new byte[4079];
				int count;

				String outdata = null;
				while(true){
					String header = SocketUtil.read_line(fromClient);
					System.out.println("header====>"+header);
					if(header==null){
						break;
					}
					HeaderSendData hsd = new HeaderSendData();
					hsd.setHeaderReceiveData(header);
					long totalRead = 0;
//					C:\serverDown
					
					 m_fos = new FileOutputStream("C:\\serverDown\\"+hsd.file_name);
					while (true)
					{
	//					toClient.write(buf, 0, count);
						int nRead = fromClient.read(buf);
						// 파일로 저장하자!
						m_fos.write(buf,0, nRead);
						outdata = new String(buf, 0, nRead);
						totalRead = totalRead + nRead;
						System.out.println(outdata);
						System.out.println("totalRead"+totalRead);
						if(totalRead==Long.valueOf(hsd.payloadSize).longValue()){
							m_fos.close();
							break;
						}
					}
					HeaderReceiveData hrd = new HeaderReceiveData();
					hrd.ver = "1.0";
					hrd.target = "monitor";
					hrd.cmd = "app.files";
					hrd.success = "ok";
					hrd.ret = "ok";
					hrd.payloadSize = hsd.payloadSize;
					toClient.write(hrd.getByteFileDownLoadHeader());
					

				}
				
//				System.out.println("연결 종료  " + socket);
			}
			catch(IOException e)
			{
				System.out.println("연결 중단  " + socket);
				e.printStackTrace();
			}
			finally
			{
				try
				{
					
					fromClient.close();
					toClient.close();
//					FileOutputStream m_fos = new FileOutputStream(m_strDownDir+fileName);
					m_fos.close();
					socket.close();
				}
				catch(IOException e) {}
			}
		}
	}
}
