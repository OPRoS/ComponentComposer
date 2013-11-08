package uuu;

import java.awt.GraphicsEnvironment;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import org.eclipse.compare.internal.CompareEditor;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import org.opros.mainpreference.preferences.PreferenceInitializer;
import org.opros.mainpreference.preferences.PreferenceConstants;
import org.opros.mainpreference.preferences.MainPreferencePage;
import org.opros.mainpreference.Activator;

import org.eclipse.ui.views.contentoutline.ContentOutline;
import org.eclipse.ui.views.contentoutline.ContentOutlinePage;


import kr.co.n3soft.n3com.projectmanager.TarManager;
import org.eclipse.jdt.core.*;
import org.eclipse.ui.IWorkbench;

import org.eclipse.swt.SWT;	//ÀÓ½Ã

public class test {
	public int i = 0;

	public String name;
	public Integer scope;
	public String initValue;
	public Integer type;
	public String id;
	public String desc="";
	public boolean isState = false;
	java.util.ArrayList params = new java.util.ArrayList();
	static DirectoryFieldEditor p_lib;

	/**
	 * @param args
	 */


	public static void main(String args[]) {
		
		String tab = "";
		String source = "	  		  		dddd";
		
		int nFirst = -1;
		int nLast = 0;
		
		while(nFirst == nLast-1){
			nFirst = nLast;
			
			nLast = source.indexOf(" ", nFirst);
			if(nFirst != nLast){
				nLast = source.indexOf("\t", nFirst);
			}
			
			if(nFirst == nLast){
				nLast = nLast + 1;
				tab += source.substring(nFirst, nLast);
			}
		}
		
		
		
		
		
		String sss = "d1dddd";
		int n = sss.indexOf("1");
		String vvccc = sss.substring(0, n);
		
		String strSysRoot = System.getenv("SystemRoot");
		CompareEditor dfgds;
		int nStyle=0;
		int tempSize = -13;
		
		String str = "1|" + "±¼¸²Ã¼" + "|" + 10 + "|" +nStyle + "|" + "WINDOWS" + "|" + "1" + "|" + tempSize + "|" + "0|0|0|" + 400 + "|" + "0|0|0|-127|3|2|1|49|" + "±¼¸²Ã¼";
		
		FontData fd = new FontData(str);

//		IWorkbenchPreferencePage aa;
		
		
		
		GraphicsEnvironment a = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Properties P = System.getProperties();
		String ccc = P.get("sun.desktop").toString();
		String str1[] = a.getAvailableFontFamilyNames();
		System.out.println(str1);
		
//		ContentOutlinePage a = new ContentOutlinePage();
		List l = new ArrayList();
		l.add("1.fffff");
		l.add("2.fdsgggg");
		l.add("3.dsdgsg");
		
		String d = l.toString();
		d = d.replace("[", "");
		d = d.replace("]", "");
		String[] ddd = d.split(", ");
		
		System.out.println(d);
		
		StringTokenizer sb = new StringTokenizer("sdsdasdasdasdagbmxcnlkncxlv");
		String dd = sb.nextToken();
		
		System.out.println(dd.equals("sd"));
		
		StringBuffer ssss = new StringBuffer();
		ssss.append("1111");
		ssss.append("2222");
		
		System.out.println(ssss);
//		String f = new File("D:\\etri\\etri_n (5)\\uuu\\src\\uuu\\test.java");
//		IType a = JavaCore.create(f);
		
	  String aa = "hello.MessagePrinter.int_value";
	  String[] tt = aa.split(".");
	  
	  
	  System.out.println("tt===>"+tt.length);
	  
	  
//		String osName = System.getProperty("os.name");
//		try{
//		TarManager.getInstance().makeTar(new File("C:\\ºÎ¾ûÀÌ\\MessagePrinter"), new File("C:\\ºÎ¾ûÀÌ"));
//		
//		osName = Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.LOCAL_REPOSITORY_PATH);
//	//	Activator.getDefault().getWorkbench().getProgressService().
//		}
//		catch(Exception e){
//			e.printStackTrace();
//		}
//		
//		System.out.println("???? : " + osName);
//
//		//		 String dd = "dd:atomic";
//		//		 String[] gg = dd.split(":");
//		//		 System.out.println("ddd");
//		//		 String h = "0493e0";
//		//		 int n = Integer.parseInt(h, 16);
//		//		 long n1 = Long.parseLong("11e1a300", 16);
//		//		 System.out.println("h======>"+n1);
//		//		 char t = Character.forDigit(58, 16);
//		//		 System.out.println("h111======>"+(char)58);
//		//		 String vVal = "dddd;";
//		//		 String[] vars = vVal.split(";");
//		//		 System.out.println("vars======>"+vars);
//		//		 String value = "I10;";
//		////		 java.math.BigDecimal bd = new java.math.BigDecimal("11e1a300",16);
//		////		 java.math.BigDecimal.
//		////		 retValue = value.substring(1);
//		//		 
//		//		 int index = value.lastIndexOf(";");
//		//			System.out.println("index1===>"+index);
//		//			System.out.println("index2===>"+value.length());
//		////		 String.
//		//			HeaderReceiveData hr = new HeaderReceiveData();
//		//			 value = hr.getType(value);
//		//			System.out.println("value===>"+value);
//		//			String tt ="T{Hello}i5;i7;i8;T{Hello1}i5;i7;i8;T{Hello}i5;i7;i8;;";
//		//			StringTokenizer stk = new StringTokenizer(tt,"T{");
//		//			while(stk.hasMoreElements()){
//		//				System.out.println("value===>"+stk.nextToken());
//		//			}
//		//			String hv = "hello\\{gggggg\\}";
//		//			hv = hv.replace("\\", "");
//		//			System.out.println("hv===>"+hv);
//		//			String str = "E09304";
//		//			
//		//			int dd11 = getBigEndian(str.getBytes());
//		//			
//		//			System.out.println("dd11===>"+dd11);
//		////			java.lang.
//		//			String hex = "bfacb0a3";
//		//			int i = 300000;
//		//			String ddss = Integer.toHexString(i);
//		////			Float.toHexString(arg0)
//		////			ddss = Double.parseDouble(hex);
//		//			System.out.println("ddss===>"+ddss);
//		//			float d = 0.44f;
//		//		Double.
//		//		double value=Double.parseDouble("123456789.123456789");
//		String ss = Integer.toHexString(300000);
//		String toConvert = "419D6F34547E6B75";
//		//		Double d= new Double((double)Integer.parseInt(toConvert, 16)); 
//		Double dblDouble = Double.longBitsToDouble(Long.parseLong(toConvert,
//				16));
//		
//		char d = java.lang.Character.forDigit(58, 16);
////		java.lang.Character.
//        int d1 = 88;
//        int ssa = Integer.parseInt("59", 16);
//
//		int p = Integer.parseInt("7530", 16);
//		
//		System.out.println("p===============>"+p);
//		String pp = hexaConvert("0493e0");
//		int dd = 0x58;
//		//		double newValue = swap(value);
//
//		//		System.out.println("big endian value = 0x" + Double.toHexString(value)
//		//						   + ", little endian value = 0x" + Double.toHexString(newValue));
//
//		//			Ingetger.
//
//		//			java.math.BigInteger
//
//		//			byte[] hex_byte = BigInteger(hex, 16).toByteArray();
//		String dds = "\\n{app=HelloWorld;var=hello.message;var=hello.count}";
//
//		
//		System.out.println("str:" + dds.getBytes().length);
	}

	private static Composite getFieldEditorParent() {
		// TODO Auto-generated method stub
		return null;
	}

	public static  String hexaConvert(String value){
		StringBuffer sb = new StringBuffer();
		if(value!=null){
			char[] arr = value.toCharArray();
			if(arr.length>2){
				int length = arr.length;
				int tlength = length/2;
				int mod = length % 2;
				if(mod==0){
				for(int i=1;i<=tlength;i++){
					char a = arr[length-2];
					char b = arr[length-1];
					System.out.println("a===>"+a);
					System.out.println("b===>"+b);
					sb.append(a);
					sb.append(b);
					length = length -2;

				}
				return sb.toString();
				}
			}
			else{
				return value;
			}
		}
		return "";

	}

	public static int getBigEndian(byte[] v){
		int[] arr = new int[4];
		for(int i=0;i<4;i++){
			arr[i] = (int)(v[3-i] & 0xFF);
		}
		return ((arr[0]  << 24) + (arr[1]  << 16) + (arr[2]  << 8) + (arr[3]  << 0));
	}

	static short swap(short x) {
		return (short)((x << 8) |
				((x >> 8) & 0xff));
	}

	static char swap(char x) {
		return (char)((x << 8) |
				((x >> 8) & 0xff));
	}

	static int swap(int x) {
		return (int)((swap((short)x) << 16) |
				(swap((short)(x >> 16)) & 0xffff));
	}

	static long swap(long x) {
		return (long)(((long)swap((int)(x)) << 32) |
				((long)swap((int)(x >> 32)) & 0xffffffffL));
	}

	static float swap(float x) {
		return Float.intBitsToFloat(swap(Float.floatToRawIntBits(x)));
	}

	static double swap(double x) {
		return Double.longBitsToDouble(swap(Double.doubleToRawLongBits(x)));
	}









}






