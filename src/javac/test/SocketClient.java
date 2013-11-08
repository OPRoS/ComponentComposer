package javac.test;

import java.io.*; 
import java.net.*; 
import java.util.*;

public class SocketClient 
{
	Socket m_sock = null;
	
	public boolean open(String ip, int port) {
		try {
			m_sock = new Socket(ip, port);
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public void close() {
		try {
			if (m_sock != null) m_sock.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void process() throws Exception {
		appFiles(null);

		String[] list = enterCommand();
		String cmd = list[0];
		
		while (cmd.equals("exit") == false) {
			if (cmd.equals("download") == true) {			
				fileDownload(list);
			}
			else if (cmd.equals("upload") == true) {
				fileUpload(list);
			} 
			else if (cmd.equals("app.files") == true) {
				appFiles(list);
			}
	
			list = enterCommand();
			cmd = list[0];
		}
	}

	public String readLine() throws IOException {
		String str = new String();
		
		System.out.print("str=");

		byte[] buf = new byte[1];;
		m_sock.getInputStream().read(buf, 0, 1);
		char ch = (char) buf[0];
		while (ch != '\n') {
			System.out.print(ch);
			str += ch;
			m_sock.getInputStream().read(buf, 0, 1);
			ch = (char) buf[0];
		}
		System.out.println("");
		return str;
	}

	public int findParam(String line, int idx, String ret) throws IOException {
		int bidx;
		
		System.out.println("[INFO] findParam : idx=" + idx);
		
		// skip blanks
		while (idx < line.length() && 
				(line.charAt(idx) == ' ' || line.charAt(idx) == '\t' ||
				 line.charAt(idx) == '\r' || line.charAt(idx) == '\n')) ++idx;
		if (idx >= line.length()) return -1;
		
		bidx = idx;
		
		// find blanks or end of string
		while (idx < line.length() && line.charAt(idx) != ' ' &&  line.charAt(idx) != '\t' && 
				line.charAt(idx) != '\r' && line.charAt(idx) != '\n') ++idx;
		ret = line.substring(bidx, idx);
		
		return idx;
	}

	public String[] enterCommand() throws IOException {
		System.out.print("cmd>> ");
		
		String line = new String();
		while(true) {
			char ch = (char) System.in.read();
			if (ch != '\n') line += ch;
			else break;
		}
			
		System.out.println("[INFO] line.len=" + line.length() + ",line=[" + line + "]");

		Vector v = new Vector();
		String ret = new String();
		int idx = 0;
		idx = findParam(line, idx, ret);
		while (idx  != -1) {
			v.addElement(ret);
			ret = new String();
			idx = findParam(line, idx, ret);		
		}
		
		String[] list = new String[v.size()];
		for (int i = 0; i < v.size(); ++i) {
			list[i] = (String) v.elementAt(i);
			System.out.println("[INFO] " + (i+1) + ". param.len=" + line.length() + ",param=[" + line + "]");
		}
		
		return list;
	}

	public Properties parseHeader(String header) throws IOException {
		Properties props = new Properties();
		StringTokenizer st = new StringTokenizer(header,";");
		
		while(st.hasMoreTokens()) {
			String tk = st.nextToken();
			StringTokenizer mt = new StringTokenizer(tk, "=");
			
			String n = mt.nextToken();
			String v;
			if (mt.hasMoreTokens()) v = mt.nextToken();
			else v = "";
			
			props.setProperty(n, v);
		}
			
		System.out.println("[props] ---------");
		Enumeration ek = props.keys();
		while (ek.hasMoreElements()) {
			String n = (String) ek.nextElement();
			String v = (String) props.getProperty(n);
			System.out.println(n + "=" + v);
		}
		System.out.println("-----------------");
		return props;
	}

	public void compStatus(String[] args) throws Exception {
		System.out.println("\n\n[INFO] compStatus : enter -----------------------------");

		String compName = args[1];
		String str = new String("ver=1.0;target=monitor;cmd=comp.status;comp.name=");
		str += compName + ";payloadSize=0\n";
		byte[] data = str.getBytes();
		m_sock.getOutputStream().write(data, 0, data.length);

		String ret = readLine();
		System.out.println("[compStatus] " + ret);
	}

	public void appFiles(String[] args) throws Exception {
		System.out.println("\n\n[INFO] compStatus : enter -----------------------------");

		String str = new String("ver=1.0;target=monitor;cmd=app.files;payloadSize=0\n");
		byte[] data = str.getBytes();
		m_sock.getOutputStream().write(data, 0, data.length);

		String ret = readLine();
		System.out.println("[compStatus] " + ret);
	}

	public void fileUploadTwoWay(String[] args) throws Exception {
		System.out.println("\n\n[INFO] fileUpload : enter -----------------------------");

		String fn = args[1];
		String str = new String("ver=1.0;target=deploy;cmd=file.upload;file.name=");
		str += fn + ";payloadSize=0\n";
		byte[] data = str.getBytes();
		m_sock.getOutputStream().write(data, 0, data.length);

		System.out.println("[INFO] fileUpload : request header write done....");

		File f = new File(args[2]);
		byte[] buf = new byte[1024];
		FileOutputStream fos = new FileOutputStream(f);
		while (true) {
			String ret = readLine();
			System.out.println("[fileUpload] " + ret);
	
			Properties props = parseHeader(ret);
			if (props.containsKey("payloadSize") == false) {
				System.out.println("[ERROR] No payloadSize");
				return;
			}

			int fSize = Integer.parseInt(props.getProperty("payloadSize"));
			if (fSize <=0) break;
			
			int rSize;	
			int rLen = 0;
			System.out.println("Current Size = " + fSize);
			while (fSize > 0) {
				rSize = fSize < 1024 ? fSize : 1024;
				
				// System.out.println("\nTrying to read=" + rSize);
				
				rSize = m_sock.getInputStream().read(buf, 0, rSize);
				fos.write(buf, 0, rSize);
				
				rLen += rSize;
				fSize -= rSize;
				
				System.out.println("readSize=" + rSize + ", TotalReadSize = " + rLen + ", RemainingSize = " + fSize);
			}
			String okStr = new String("ok\n");
			data = okStr.getBytes();
			m_sock.getOutputStream().write(data, 0, data.length);
		}
		fos.close();
		
		System.out.println("[INFO] fileUpload : exit -----------------------------");
	}

	public void fileUpload(String[] args) throws Exception {
		System.out.println("\n\n[INFO] fileUpload : enter -----------------------------");

		String fn = args[1];
		String str = new String("ver=1.0;target=deploy;cmd=file.upload;file.name=");
		str += fn + ";payloadSize=0\n";
		byte[] data = str.getBytes();
		m_sock.getOutputStream().write(data, 0, data.length);

		System.out.println("[INFO] fileUpload : request header write done....");

		File f = new File(args[2]);
		byte[] buf = new byte[1024];
		FileOutputStream fos = new FileOutputStream(f);

		String ret = readLine();
		System.out.println("[fileUpload] " + ret);
	
		Properties props = parseHeader(ret);
		if (props.containsKey("payloadSize") == false) {
			System.out.println("[ERROR] No payloadSize");
			return;
		}
		int fSize = Integer.parseInt(props.getProperty("payloadSize"));

		System.out.println("Current FileSize = " + fSize);
			
		int rSize;	
		int rLen = 0;
		while (fSize > 0) {
			rSize = fSize < 1024 ? fSize : 1024; 
//			System.out.println("\nTrying to read=" + rSize);
			rSize = m_sock.getInputStream().read(buf, 0, rSize);
			fos.write(buf, 0, rSize);
			
			rLen += rSize;
			fSize -= rSize;
			
//			System.out.println("readSize=" + rSize + ", TotalReadSize = " + rLen + ", RemainingSize = " + fSize);	
		}
		fos.close();
		
		System.out.println("[INFO] fileUpload : exit -----------------------------");
	}

	public void fileDownload(String[] args) throws Exception {
		System.out.println("\n\n[INFO] fileDownload : enter -----------------------------");

		String fn = args[2];
		File f = new File("c:\\lib3\\HelloMaker.dll");
		
		if (f.exists() == false) {
			System.out.println("[ERROR] not exists : file=" + fn);
			return;
		}
		long fsize = f.length();

		System.out.println("file.name=" + fn + ", file.size=" + fsize);
		
		String str = new String("ver=1.0;target=deploy;cmd=file.download;file.name=");
		str += args[1] + ";payloadSize=" + fsize + "\n";
		
		byte[] data = str.getBytes();
		m_sock.getOutputStream().write(data, 0, data.length);

		System.out.println("[INFO] fileDownload : request header write done : file.size=" + fsize);

		FileInputStream fis = new FileInputStream(f);
		byte[] buf = new byte[1024];
		while (true) {
			int rSize = fis.read(buf);
			if (rSize <= 0) break;
			
			m_sock.getOutputStream().write(buf, 0, rSize);
		}
		fis.close();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(m_sock.getInputStream()));
		String ret = br.readLine();
		System.out.println("[fileDownload] " + ret);
		System.out.println("[INFO] fileDownload : exit -----------------------------");
	}

	public void testDownload() throws Exception
	{
    	String[] list;
    	list = new String[3];
    	list[0] = "download";
    	list[1] = "h.xml";
    	list[2] = "hello.xml";
    	fileDownload(list);
    	
    	list = new String[3];
    	list[0] = "download";
    	list[1] = "hello/hm.xml";
    	list[2] = "HelloMaker.xml";
    	fileDownload(list);
    	
    	list = new String[3];
    	list[0] = "download";
    	list[1] = "hello/hm.dll";
    	list[2] = "HelloMakerComp.dll";
    	fileDownload(list);
	}

	public void testUpload() throws Exception
	{
    	String[] list;

    	list = new String[3];
    	list[0] = "upload";
    	list[1] = "hello.xml";
    	list[2] = "h.xml";
    	//fileUploadTwoWay(list);
    	fileUpload(list);

    	list = new String[3];
    	list[0] = "upload";
    	list[1] = "hello/HelloMaker.xml";
    	list[2] = "hm.xml";
//    	fileUploadTwoWay(list);
    	fileUpload(list);

    	list = new String[3];
    	list[0] = "upload";
    	list[1] = "hello/HelloMakerComp.dll";
    	list[2] = "hm.dll";
//    	fileUploadTwoWay(list);
    	fileUpload(list);
	}

	public void testMonitoring() throws Exception
	{
    	String[] list;

    	appFiles(null);

    	list = new String[2];
    	list[0] = "compStatus";
    	list[1] = "HelloMaker";
    	compStatus(list);
	}
	
	public static void main(String[] args)  
    { 
		SocketClient sc = new SocketClient();

        try{
        	String ip = new String("127.0.0.1");
        	int port = 30001;
        	if (args.length > 0) {
        		ip = args[0];
           		return;
        	}
        	if (args.length > 1) {
        		port = Integer.parseInt(args[1]);
        	}
        	      	
        	if (!sc.open(ip, port)) {
           		System.out.println("[ERROR] Socket open error : ip=" + ip + ", port=" + port);
           		return;
        	}
       		System.out.println("[INFO] Socket connected : ip=" + ip + ", port=" + port);

//       		sc.testMonitoring();
        	sc.testDownload();
//        	sc.testUpload();      	
       } 
        catch(Exception e){ 
    		System.out.println("[ERROR] Socket processing error");
            e.printStackTrace(); 
        } 
        
        sc.close();
    } 
}
