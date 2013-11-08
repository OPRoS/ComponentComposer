package kr.co.n3soft.n3com.net;

import java.io.File;
import java.util.StringTokenizer;

import kr.co.n3soft.n3com.project.dialog.DetailPropertyTableItem;


public class HeaderSendData {
	public String ver = "1.0";
	public String target = "";
	public String cmd = "";
	public String file_name = "";
	public String payloadSize = "";
	public String file_path = "";
	public File payload = null;
	public String app_name = "";
	public String comp_name = "";
	public int i=0;
	
	public int cmdType = 0;
	
	public java.util.ArrayList var = new java.util.ArrayList();
	
	public String getHeader(){
		if(cmdType==0){
			return this.getStringFileDownLoadHeader();
		}
		else if(cmdType==1){
			return this.getStringAppFilesHeader();
		}
		else if(cmdType==2){
			this.cmd= "app.comp_list";
			this.target = "monitor";
			this.payloadSize = "0";
			return this.getStringAppCmdHeader();
		}
		else if(cmdType==3){
			this.cmd= "app.run";
			this.target = "monitor";
			this.payloadSize = "0";
			return this.getStringAppCmdHeader();
		}
		else if(cmdType==4){
			this.cmd = "app.stop";
			this.target = "monitor";
			this.payloadSize = "0";
			return this.getStringAppCmdHeader();
		}
		else if(cmdType==5){
			this.cmd = "app.info";
			this.target = "monitor";
			this.payloadSize = "0";
			return this.getStringAppCmdHeader();
		}
		else if(cmdType==6){
			this.cmd = "comp.info";
			this.target = "monitor";
			this.payloadSize = "0";
			return this.getStringCompCmdHeader();
		}
		else if(cmdType==7){
			this.cmd = "comp.list";
			this.target = "monitor";
			this.payloadSize = "0";
			return this.getStringCompCmdHeader();
		}
		else if(cmdType==8){
			this.cmd = "comp.props";
			this.target = "monitor";
			this.payloadSize = "0";
			return this.getStringCompCmdHeader();
		}
		else if(cmdType==9){
			this.cmd = "app.run_list";
			this.target = "monitor";
			this.payloadSize = "0";
			return this.getStringRunListCmdHeader();
		}
		else if(cmdType==10){
			this.cmd = "get";
			this.target = "symbol";
			this.payloadSize = "0";
			return this.getStringGetCmdHeader();
		}
		else if(cmdType==11){
			return this.getFileCheckHeader();
		}
		
		else if(cmdType==12){
			return this.getFileDeleteHeader();
		}
		
		return null;
	}
	
//	public String getStringAppRunHeader(){
//		String ret = this.getVerKeyMapping()+";"+
//		this.getTargetKeyMapping()+";"+
//		this.getCmdKeyMapping()+";"+
//		this.getAppNameKeyMapping()+";"+
//		this.getPayloadSizeKeyMapping()+NetManager.ln;
//		
//		return ret;
//	}
//	
//	public String getStringAppRunHeader2(){
//		String ret = this.getVerKeyMapping()+";"+
//		this.getTargetKeyMapping()+";"+
//		this.getCmdKeyMapping()+";"+
//		this.getAppNameKeyMapping()+";"+
//		this.getPayloadSizeKeyMapping();
//		
//		return ret;
//	}
	public String getStringGetCmdHeader(){
		String total = "";
//		String ret = this.getVerKeyMapping()+";"+
//		this.getTargetKeyMapping()+";"+
//		this.getCmdKeyMapping()+";"+
////		this.getAppNameKeyMapping()+";"+
//		this.getPayloadSizeKeyMapping()+NetManager.ln;
		StringBuffer payloadformat = new StringBuffer("");
		
		if(this.var!=null){
			
			for(int i=0;i<var.size();i++){
				payloadformat.append("{");
//				payloadformat.append(this.getAppNameKeyMapping()+";");
				DetailPropertyTableItem dt = (DetailPropertyTableItem)this.var.get(i);
//				dt.cmpName = this.comp_name;
				payloadformat.append(dt.getVar(app_name));
//				if(i!=var.size()-1){
					payloadformat.append(";");
//				}
				payloadformat.append("}");
			}
			
//			payloadformat.toString().get
//			System.out.println("ret==========>"+ret);
			int ps = payloadformat.toString().length();
			this.payloadSize = String.valueOf(ps);
			String ret = this.getVerKeyMapping()+";"+
			this.getTargetKeyMapping()+";"+
			this.getCmdKeyMapping()+";"+
//			this.getAppNameKeyMapping()+";"+
			this.getPayloadSizeKeyMapping()+";"+NetManager.ln;
			
			
			total = ret+payloadformat.toString();
		}
//		System.out.println("payload==========>"+total);
//		String message = "{app=MessagePrinter;var=MessagePrinter.test;valueformat=str;value=A[8]CHello Me"+i+";;}";
//////		
//		i++;
//		total = "ver1.0;target=symbol;cmd=set;payloadSize="+message.length()+";"+NetManager.ln+message;
//		System.out.println("payload2==========>"+total);
		
		return total;
	}
	
	public String getStringRunListCmdHeader(){
		String ret = this.getVerKeyMapping()+";"+
		this.getTargetKeyMapping()+";"+
		this.getCmdKeyMapping()+";"+
//		this.getAppNameKeyMapping()+";"+
		this.getPayloadSizeKeyMapping()+NetManager.ln;
		
		return ret;
	}
	
	public String getStringRunListCmdHeader2(){
		String ret = this.getVerKeyMapping()+";"+
		this.getTargetKeyMapping()+";"+
		this.getCmdKeyMapping()+";"+
		this.getAppNameKeyMapping()+";"+
		this.getPayloadSizeKeyMapping();
		
		return ret;
	}
	
	public String getStringCompCmdHeader(){
		String ret = this.getVerKeyMapping()+";"+
		this.getTargetKeyMapping()+";"+
		this.getCmdKeyMapping()+";"+
		this.getCompNameKeyMapping()+";"+
		this.getPayloadSizeKeyMapping()+NetManager.ln;
		
		return ret;
	}
	
	public String getStringCompECmdHeader(){
		this.app_name = this.comp_name;
		String ret = this.getVerKeyMapping()+";"+
		this.getTargetKeyMapping()+";"+
		this.getCmdKeyMapping()+";"+
		this.getAppNameKeyMapping()+";"+
		this.getPayloadSizeKeyMapping()+NetManager.ln;
		
		return ret;
	}
	
	
	public String getStringCompCmdHeader2(){
		String ret = this.getVerKeyMapping()+";"+
		this.getTargetKeyMapping()+";"+
		this.getCmdKeyMapping()+";"+
		this.getCompNameKeyMapping()+";"+
		this.getPayloadSizeKeyMapping();
		
		return ret;
	}
	
	public String getStringAppCmdHeader(){
		String ret = this.getVerKeyMapping()+";"+
		this.getTargetKeyMapping()+";"+
		this.getCmdKeyMapping()+";"+
		this.getAppNameKeyMapping()+";"+
		this.getPayloadSizeKeyMapping()+NetManager.ln;
		
		return ret;
	}
	
	public String getStringAppCmdHeader2(){
		String ret = this.getVerKeyMapping()+";"+
		this.getTargetKeyMapping()+";"+
		this.getCmdKeyMapping()+";"+
		this.getAppNameKeyMapping()+";"+
		this.getPayloadSizeKeyMapping();
		
		return ret;
	}
	
	
	public String getStringAppCompListHeader(){
		String ret = this.getVerKeyMapping()+";"+
		this.getTargetKeyMapping()+";"+
		this.getCmdKeyMapping()+";"+
		this.getPayloadSizeKeyMapping()+NetManager.ln;
		
		return ret;
	}
	
	public String getStringAppCompListHeader2(){
		String ret = this.getVerKeyMapping()+";"+
		this.getTargetKeyMapping()+";"+
		this.getCmdKeyMapping()+";"+
		this.getPayloadSizeKeyMapping();
		
		return ret;
	}
	
	
	public String getStringAppFilesHeader(){
		String ret = this.getVerKeyMapping()+";"+
		this.getTargetKeyMapping()+";"+
		this.getCmdKeyMapping()+";"+
		this.getPayloadSizeKeyMapping()+NetManager.ln;
		
		return ret;
	}
	
	public String getStringAppFilesHeader2(){
		String ret = this.getVerKeyMapping()+";"+
		this.getTargetKeyMapping()+";"+
		this.getCmdKeyMapping()+";"+
		this.getPayloadSizeKeyMapping();
		
		return ret;
	}
	
	
	public String getStringFileDownLoadHeader(){
		String ret = this.getVerKeyMapping()+";"+
		this.getTargetKeyMapping()+";"+
		this.getCmdKeyMapping()+";"+
		this.getFile_nameKeyMapping()+";"+
		this.getPayloadSizeKeyMapping()+NetManager.ln;
		
		return ret;
	}
	
	//111107 SDM  S - 중복 어플리케이션 체크 및 삭제 메시지
	public String getFileCheckHeader(){	//중복체크
		String ret = this.getVerKeyMapping()+";"+
		this.getTargetKeyMapping()+";"+
		this.getCmdKeyMapping()+";"+
		this.getAppNameKeyMapping()+";"+
		this.getPayloadSizeKeyMapping()+NetManager.ln;
		
		return ret;
	}
	
	public String getFileDeleteHeader(){	//삭제
		String ret = this.getVerKeyMapping()+";"+
		this.getTargetKeyMapping()+";"+
		this.getCmdKeyMapping()+";"+
		this.getAppNameKeyMapping()+";"+
		this.getPayloadSizeKeyMapping()+NetManager.ln;
		
		return ret;
	}
	//111107 SDM  E - 중복 어플리케이션 체크 및 삭제 메시지
	
	public String getStringFileDownLoadHeader2(){
		String ret = this.getVerKeyMapping()+";"+
		this.getTargetKeyMapping()+";"+
		this.getCmdKeyMapping()+";"+
		this.getFile_nameKeyMapping()+";"+
		this.getPayloadSizeKeyMapping();
		
		return ret;
	}
	
	public String getStringFileUploadHeader(){
		String ret = this.getVerKeyMapping()+";"+
		this.getTargetKeyMapping()+";"+
		this.getCmdKeyMapping()+";"+
		this.getFile_nameKeyMapping()+";"+
		this.getPayloadSizeKeyMapping()+NetManager.ln;
		
		return ret;
	}
	
	public String getStringFileUploadHeader2(){
		String ret = this.getVerKeyMapping()+";"+
		this.getTargetKeyMapping()+";"+
		this.getCmdKeyMapping()+";"+
		this.getFile_nameKeyMapping()+";"+
		this.getPayloadSizeKeyMapping();
		
		return ret;
	}
	
	public byte[] getByteFileDownLoadHeader(){
		
		
		return getStringFileDownLoadHeader().getBytes();
	}
	
	public byte[] getByteFileUploadHeader(){
		
		
		return getStringFileUploadHeader().getBytes();
	}
	
	public String getVerKeyMapping(){
		String ret = NetManager.ver_key + "="+this.ver;
		return ret;
	}
	public String getTargetKeyMapping(){
		String ret = NetManager.target_key + "="+this.target;
		return ret;
	}
	public String getCmdKeyMapping(){
		String ret = NetManager.cmd_key + "="+this.cmd;
		return ret;
	}
	public String getFile_nameKeyMapping(){
//		if(this.file_name)
		String ret = NetManager.file_name_key + "="+this.file_name;
		return ret;
	}
	public String getPayloadSizeKeyMapping(){
		String ret = NetManager.payloadSize_key + "="+this.payloadSize;
		return ret;
	}
	
	public String getAppNameKeyMapping(){
		String ret = NetManager.app_name_key + "="+this.app_name;
		return ret;
	}
	
	public String getCompNameKeyMapping(){
		String ret = NetManager.comp_name_key + "="+this.comp_name;
		return ret;
	}
	
	public void setHeaderReceiveData(String str){
		StringTokenizer stk = new StringTokenizer(str,";");
		while(stk.hasMoreTokens()){
			String token = stk.nextToken();
			if(token!=null && token.indexOf("=")!=-1){
				String[] arr = token.split("=");
			
				if(NetManager.ver_key.equals(arr[0])){
					this.ver = arr[1];
				}
				if(NetManager.target_key.equals(arr[0])){
					this.target = arr[1];
				}
				if(NetManager.cmd_key.equals(arr[0])){
					this.cmd = arr[1];
				}
				if(NetManager.file_name_key.equals(arr[0])){
					this.file_name = arr[1];
				}
			
				if(NetManager.payloadSize_key.equals(arr[0])){
					this.payloadSize = arr[1];
				}
				
			}
			
		}
		
	}
	

}
