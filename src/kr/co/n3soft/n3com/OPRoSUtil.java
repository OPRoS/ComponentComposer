package kr.co.n3soft.n3com;

//import java.io.File;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.StringTokenizer;

import kr.co.n3soft.n3com.model.comm.PortModel;
import kr.co.n3soft.n3com.model.component.AtomicComponentModel;
import kr.co.n3soft.n3com.model.component.OPRoSDataTypeElementModel;
import kr.co.n3soft.n3com.model.component.OPRoSServiceTypeElementModel;
import kr.co.n3soft.n3com.project.browser.RootCmpEdtTreeModel;
import kr.co.n3soft.n3com.project.browser.UMLTreeParentModel;

import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Preferences;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;

import uuu.Activator;


/**
 * 이 클래스는 OPRoS CE에서 사용되는 독립적으로 자주 사용되는
 * 메소드를 모아서 유틸리티로 제공하는 클래스이다.
 * 이 클래스는 다음과 같은 내용을 제공한다.
 * <ul>
 * <li>메시지 박스 출력
 * <li>라벨/버튼/텍스트박스/콤보/그룹/컴포짓/리스트 UI 요소 만들기
 * <li>컴파일러(OPRoS프로젝트 타입)에 따른 MakeFile/VS2008 프로젝트파일 만들기
 * <li>OPRoS Component Library 와 Include 파일의 디렉토리 위치 찾아오기
 * </ul>
 *  
 * @author jwkim 
 * @author jwkim@ed.co.kr
 * @author sevensky@mju.ac.kr
 * @since OPRoS_CE 1.0.0
 */
public class OPRoSUtil {
	/**
	 * OPRoS_CE에 포함된 OPRoS Component Library 와 Include 파일의 디렉토리를 저장한 문자열 
	 */
	private static String OPRoSFilesPath=null;
	public static final String[] dataTypes = {
		OPRoSStrings.getString("DataType0"),
		OPRoSStrings.getString("DataType1"),
		OPRoSStrings.getString("DataType2"),
		OPRoSStrings.getString("DataType3"),
		OPRoSStrings.getString("DataType4"),
		OPRoSStrings.getString("DataType5"),
		OPRoSStrings.getString("DataType6"),
		OPRoSStrings.getString("DataType7"),
		OPRoSStrings.getString("DataType8"),
		OPRoSStrings.getString("DataType9"),
		OPRoSStrings.getString("DataType10"),
		OPRoSStrings.getString("DataType11"),
		OPRoSStrings.getString("DataType12"),
		OPRoSStrings.getString("DataType13"),
		OPRoSStrings.getString("DataType14"),
		OPRoSStrings.getString("DataType15"),
		OPRoSStrings.getString("DataType16"),
		OPRoSStrings.getString("DataType17"),
		OPRoSStrings.getString("DataType18"),
		OPRoSStrings.getString("DataType19")
		};
	public static final String[] dataNotBoostTypes = {
		OPRoSStrings.getString("DataType0"),
		OPRoSStrings.getString("DataType1"),
		OPRoSStrings.getString("DataType2"),
		OPRoSStrings.getString("DataType3"),
		OPRoSStrings.getString("DataType4"),
		OPRoSStrings.getString("DataType5"),
		OPRoSStrings.getString("DataType6")};
	
	/**
	 * static 메소드
	 * 전달된 메시지를 출력하는 메시지 박스를 호출한다.
	 * @param msg		출력될 메시지
	 * @param style		메시지 박스 스타일(SWT Style 상수)
	 * @see	org.eclipse.swt.widgets.MessageBox
	 * @see org.eclipse.swt.widgets.Display
	 * @see org.eclipse.swt.widgets.Display#getDefault()
	 * @see org.eclipse.swt.widgets.Display#getActiveShell()
	 * @see org.eclipse.swt.SWT
	 */
	public static int openMessageBox(String msg, int style){
		MessageBox msgDlg = new MessageBox(Display.getDefault().getActiveShell(),style);
		msgDlg.setMessage( msg);
		return msgDlg.open();
	}
	
	/**
	 * static 메소드
	 * 전달된 형태의 SWT Widget 리스트를 생성한다.
	 * @param container		리스트가 포함될 상위 컨테이너
	 * @param style			리스트의 스타일(SWT Style Constants)
	 * @param hSpan			가로방향의 할당영역 개수
	 * @param hIndent		가로방향의 띄어쓰기 크기 
	 * @param vSpan			세로방향의 할당영역 개수
	 * @param vIndent		세로방향의 띄어쓰기 크기
	 * @param width			넓이
	 * @param height		높이
	 * @param gridStyle		리스트가 가지는 GridData의 gridStyle
	 * @return				생성된 SWT Widget 리스트
	 * @see org.eclipse.swt.widgets.List
	 * @see org.eclipse.swt.widgets.Composite
	 * @see org.eclipse.swt.Layout.GridData
	 * @see org.eclipse.swt.SWT
	 */
	public static List createList(Composite container, int style, int hSpan, int hIndent, int vSpan, int vIndent, int width, int height, int gridStyle){
		List list = new List(container, style);
		GridData gd= new GridData(gridStyle);
		gd.horizontalSpan = hSpan;
		gd.horizontalIndent = hIndent;
		gd.verticalSpan = vSpan;
		gd.verticalIndent = vIndent;
		gd.widthHint=width;
		gd.heightHint=height;
		list.setLayoutData(gd);
		return list;
	}
	
	/**
	 * static 메소드
	 * 전달된 형태의 SWT Widget 버튼을 생성한다.
	 * @param container		버튼이 포함될 상위 컨테이너
	 * @param style			버튼의 스타일(SWT Style Constants)
	 * @param text			버튼에 표시되는 이름
	 * @param hSpan			가로방향의 할당영역 개수
	 * @param hIndent		가로방향의 띄어쓰기 크기 
	 * @param vSpan			세로방향의 할당영역 개수
	 * @param vIndent		세로방향의 띄어쓰기 크기
	 * @param width			넓이
	 * @param height		높이
	 * @param gridStyle		버튼이 가지는 GridData의 gridStyle
	 * @return				생성된 SWT Widget 버튼
	 * @see org.eclipse.swt.widgets.Button
	 * @see org.eclipse.swt.widgets.Composite
	 * @see org.eclipse.swt.Layout.GridData
	 * @see org.eclipse.swt.SWT
	 */
	public static Button createButton(Composite container, int style, String text,int hSpan, int hIndent, int vSpan, int vIndent,int width, int height, int gridStyle) {
		Button button = new Button(container, style);
		GridData gd = new GridData(gridStyle);
		gd.horizontalSpan = hSpan;
		gd.horizontalIndent = hIndent;
		gd.verticalIndent = vIndent;
		gd.verticalSpan = vSpan;
		gd.widthHint=width;
		gd.heightHint = height;
		button.setLayoutData(gd);
		button.setText(text);
		return button;		
	}
	
	/**
	 * static 메소드
	 * 전달된 형태의 SWT Widget 라벨을 생성한다.
	 * @param container		라벨이 포함될 상위 컨테이너
	 * @param text			라벨에 표시되는 이름
	 * @param style			라벨의 스타일(SWT Style Constants)
	 * @param hSpan			가로방향의 할당영역 개수
	 * @param hIndent		가로방향의 띄어쓰기 크기 
	 * @param vSpan			세로방향의 할당영역 개수
	 * @param vIndent		세로방향의 띄어쓰기 크기
	 * @param gridStyle		라벨이 가지는 GridData의 gridStyle
	 * @return				생성된 SWT Widget 라벨
	 * @see org.eclipse.swt.widgets.Label
	 * @see org.eclipse.swt.widgets.Composite
	 * @see org.eclipse.swt.Layout.GridData
	 * @see org.eclipse.swt.SWT
	 */
	public static Label createLabel(Composite container, String text,int style, int hSpan, int hIndent, int vSpan, int vIndent, int gridStyle) {
		Label label = new Label(container, style);
		label.setText(text);
		GridData gd = new GridData(gridStyle);
		gd.horizontalSpan = hSpan;
		gd.horizontalIndent = hIndent;
		gd.verticalIndent = vIndent;
		gd.verticalSpan = vSpan;
		label.setLayoutData(gd);
		return label;
	}
	
	/**
	 * static 메소드
	 * 전달된 형태의 SWT Widget 텍스트박스를 생성한다.
	 * @param container		텍스트박스가 포함될 상위 컨테이너
	 * @param style			텍스트박스의 스타일(SWT Style Constants)
	 * @param hSpan			가로방향의 할당영역 개수
	 * @param hIndent		가로방향의 띄어쓰기 크기
	 * @param vSpan			세로방향의 할당영역 개수
	 * @param vIndent		세로방향의 띄어쓰기 크기
	 * @param width			넓이
	 * @param height		높이
	 * @param gridStyle		텍스트박스가 가지는 GridData의 gridStyle
	 * @return				생성된 SWT Widget 텍스트박스 
	 * @see org.eclipse.swt.widgets.Text
	 * @see org.eclipse.swt.widgets.Composite
	 * @see org.eclipse.swt.Layout.GridData
	 * @see org.eclipse.swt.SWT
	 */
	public static Text createText(Composite container, int style, int hSpan, int hIndent, int vSpan, int vIndent, int width,int height, int gridStyle) {
		Text text = new Text(container, style);
		GridData gd = new GridData(gridStyle);
		gd.horizontalSpan = hSpan;
		gd.horizontalIndent = hIndent;
		gd.verticalIndent = vIndent;
		gd.verticalSpan = vSpan;
		gd.widthHint=width;
		if(height>0)
			gd.heightHint=height;
		text.setLayoutData(gd);
		return text;
	}
	
	/**
	 * static 메소드
	 * 전달된 형태의 SWT Widget 콤보박스를 생성한다.
	 * @param container		콤보박스가 포함될 상위 컨테이너
	 * @param style			콤보박스의 스타일(SWT Style Constants)
	 * @param items			콤보박스에 표시되는 아이템의 리스트
	 * @param initIndex		초기표시 아이템 리스트의 인덱스
	 * @param hSpan			가로방향의 할당영역 개수
	 * @param hIndent		가로방향의 띄어쓰기 크기
	 * @param vSpan			세로방향의 할당영역 개수
	 * @param vIndent		세로방향의 띄어쓰기 크기
	 * @param width			넓이
	 * @param height		높이
	 * @param gridStyle		콤보박스가 가지는 GridData의 gridStyle
	 * @return				생성된 SWT Widget 콤보박스
	 * @see org.eclipse.swt.widgets.Combo
	 * @see org.eclipse.swt.widgets.Composite
	 * @see org.eclipse.swt.Layout.GridData
	 * @see org.eclipse.swt.SWT
	 */
	public static Combo createCombo(Composite container, int style, String[] items, int initIndex, int hSpan, int hIndent, int vSpan, int vIndent, int width, int height, int gridStyle){
		Combo combo = new Combo(container, style);
		GridData gd= new GridData(gridStyle);
		gd.horizontalSpan = hSpan;
		gd.horizontalIndent = hIndent;
		gd.verticalSpan = vSpan;
		gd.verticalIndent = vIndent;
		if(width>0)
			gd.widthHint=width;
		if(height>0)
			gd.heightHint=height;
		combo.setLayoutData(gd);
		if(items!=null)
			combo.setItems(items);
		if(initIndex!=-1){
			combo.setText(items[initIndex]);
		}
		return combo;
	}
	
	/**
	 * static 메소드
	 * 전달된 형태의 SWT Widget 그룹를 생성한다.
	 * @param parent		그룹이 포함될 상위 컨테이너
	 * @param style			그룹의 스타일(SWT Style Constants)
	 * @param text			그룹 이름
	 * @param column		그룹이 가지는 GridLayout의 열의 개수
	 * @param gridStyle		그룹이 가지는 GridData의 gridStyle
	 * @return				생성된 SWT Widget 그룹
	 * @see org.eclipse.swt.widgets.Group
	 * @see org.eclipse.swt.widgets.Composite
	 * @see org.eclipse.swt.Layout.GridData
	 * @see org.eclipse.swt.SWT
	 */
	public static Group createGroup(Composite parent, int style, String text, int column, int gridStyle){
		Group group = new Group(parent, style);
		group.setFont(parent.getFont());
		group.setText(text); 
		GridLayout layout = new GridLayout();
		layout.numColumns = column;
		group.setLayout(layout);
		group.setLayoutData(new GridData(gridStyle));
		return group;
	}
	
	/**
	 * static 메소드
	 * 전달된 형태의 SWT Widget 컴포짓을 생성한다.
	 * @param parent		컴포짓이 포함될 상위 컨테이너
	 * @param style			컴포짓의 스타일(SWT Style Constants)
	 * @param column		컴포짓이 가지는 GridLayout의 열의 개수
	 * @param gridStyle		컴포짓이 가지는 GridData의 gridStyle
	 * @return				생성된 SWT Widget 컴포짓
	 * @see org.eclipse.swt.widgets.Composite
	 * @see org.eclipse.swt.Layout.GridData
	 * @see org.eclipse.swt.SWT
	 */
	public static Composite createComposite(Composite parent, int style, int column, int gridStyle){
		Composite composite = new Composite(parent, style);
		composite.setFont(parent.getFont());
        GridLayout layout = new GridLayout();
        layout.numColumns = column;
        composite.setLayout(layout);
        composite.setLayoutData(new GridData(gridStyle));//수평
        return composite;
	}
	
	/**
	 * static 메소드
	 * compile을 위해 makefile을 생성한다.
	 * Project 설정에 따라 MinGW이면 MinGW버전 Makefile을 생성하고
	 * MSVC면 MSVC 버전 Makefile을 생성한다.
	 * 
	 * @see kr.co.ed.opros.ce.core.OPRoSProjectInfo
	 * @see kr.co.ed.opros.ce.OPRoSActivator
	 * @see kr.co.ed.opros.ce.wizards.WizardMessages
	 * @see org.eclipse.core.runtime.Preferences
	 */
//	public static void createMakeFile(String libTypeName,OPRoSProjectInfo prjInfo){
//		OPRoSProjectInfo info;
//		String libType;
//		if(prjInfo!=null)
//			info = prjInfo;
//		else
//			return;
//		if(libTypeName==null || libTypeName.isEmpty())
//			libType="dll";
//		else
//			libType=libTypeName;
//		if(info!=null){
//			Iterator<String> compIt = info.getComponents();
//			while(compIt.hasNext()){
//				String compName = compIt.next();
//				File dir = new File(info.getLocation()+"/"+info.getPrjName()+"/"+compName+"/Debug");
//				if(!dir.exists())
//					dir.mkdir();
//				dir = new File(info.getLocation()+"/"+info.getPrjName()+"/"+compName+"/Release");
//				if(!dir.exists())
//					dir.mkdir();
//			}
//			FileOutputStream outStream=null;
//			try {
//				outStream = new FileOutputStream(info.getLocation()+"/"+info.getPrjName()+"/Makefile");
//			} catch (FileNotFoundException e) {
//				e.printStackTrace();
//			}
//			if(outStream!=null){
//				Preferences pref = OPRoSActivator.getDefault().getPluginPreferences();
//				String strBoostLibPath = pref.getString(PreferenceConstants.BOOST_LIB_PATH);
//				String strBoostIncPath = pref.getString(PreferenceConstants.BOOST_INC_PATH);
//				String strOPRoSLibPath = pref.getString(PreferenceConstants.OPROS_LIB_PATH);
//				String strOPRoSIncPath = pref.getString(PreferenceConstants.OPROS_INC_PATH);
//				String strOtherIncPath = pref.getString(PreferenceConstants.OTHER_INC_PATH);
//				String strVSOption= pref.getString(PreferenceConstants.VS_COMPILE_OPTION);
//				String strVSUnicode= pref.getString(PreferenceConstants.VS_UNICODE_OPTION);
//				String strMDOption="/MD";
//				
//				if(strBoostLibPath.isEmpty())
//					strBoostLibPath = "./boost/lib # change this with your boost library path\n";
//				if(strBoostIncPath.isEmpty())
//					strBoostIncPath = "./boost/include/boost-1_35 # change this with your boost include path\n";
//				if(strOPRoSLibPath.isEmpty())
//					strOPRoSLibPath = getOPRoSFilesPath()+"OPRoSFiles/OPRoSLib";
//				if(strOPRoSIncPath.isEmpty())
//					strOPRoSIncPath = getOPRoSFilesPath()+"OPRoSFiles/OPRoSInc";
//				if(strVSOption.isEmpty()){
//					strVSOption = "RELEASE";
//					strMDOption = "/MD";
//				}else if(strVSOption.compareTo("RELEASE")==0){
//					strMDOption="/MD";
//				}else{
//					strMDOption = "/MDd";
//				}
//				if(strVSUnicode.isEmpty())
//					strVSUnicode = "UNICODE";
//				Iterator<String> it = info.getComponents();
//				String szContents="";
//				if(info.getImplLanguage().equals(WizardMessages.getString("NewPjtWizardPage.SelectLanguageGroup.Combo0"))){
//					//szContents+="OUTDIR=.\\Release\nINTDIR=.\\Release\nOutDir=.\\Release\n";
//					szContents+="\nBOOST_INC="+strBoostIncPath;
//					szContents+="\nBOOST_LIB="+strBoostLibPath;
//					szContents+="\nOPROS_INC="+strOPRoSIncPath;
//					szContents+="\nOPROS_LIB="+strOPRoSLibPath;
//					szContents+="\n\nSO_TYPE= "+libType+" #shared object extensions\n";// PROJECT=\n";
//					
//					szContents+="INC = -I$(BOOST_INC)";
//					szContents+=" -I$(OPROS_INC)";
//					if(!strOtherIncPath.isEmpty()){
//						StringTokenizer token=new StringTokenizer(strOtherIncPath,";");
//						while(token.hasMoreTokens()){
//							szContents+=" -I"+token.nextToken();
//						}
//					}
//					szContents+="\n\n";
//					szContents+="DEBUGCXXFLAGS=-g3 -Wall -c -fmessage-length=0 -mthreads -DMAKE_COMPONENT_DLL $(INC)\n";
//					szContents+="RELEASECXXFLAGS=-O2 -Wall -c -fmessage-length=0 -mthreads -DMAKE_COMPONENT_DLL $(INC)\n\n";
//					szContents+="LFLAGS= -L$(BOOST_LIB)";
//					szContents+=" -L$(OPROS_LIB)\n\n";
//					szContents+="LIBS= -lOPRoSCDL -llibboost_serialization-mgw34-mt\n\n";
//					
//	
//					int cnt=0;
//					String compName="";
//					while(it.hasNext()){
//						cnt++;
//						compName=it.next();
//						szContents+="\nSOURCE"+cnt+"="+compName+"/"+compName+".cpp";
//						szContents+="\nDEBUGOBJS"+cnt+"="+compName+"/Debug/"+compName+".o";
//						szContents+="\nRELEASEOBJS"+cnt+"="+compName+"/Release/"+compName+".o";
//						szContents+="\nDEBUGTARGET"+cnt+"="+compName+"/Debug/"+compName+".$(SO_TYPE)";
//						szContents+="\nRELEASETARGET"+cnt+"="+compName+"/Release/"+compName+".$(SO_TYPE)";
//						
//						szContents+="\n$(DEBUGOBJS"+cnt+"):";
//						szContents+="\n	$(CXX) $(DEBUGCXXFLAGS) $(SOURCE"+cnt+") -o $(DEBUGOBJS"+cnt+")\n";
//						szContents+="\n$(DEBUGTARGET"+cnt+"): $(DEBUGOBJS"+cnt+")";
//						szContents+="\n	$(CXX) $(LFLAGS) -shared -WI,-soname,$@ -o $@ $(DEBUGOBJS"+cnt+") $(LIBS)\n";
//						szContents+="\n$(RELEASEOBJS"+cnt+"):";
//						szContents+="\n	$(CXX) $(RELEASECXXFLAGS) $(SOURCE"+cnt+") -o $(RELEASEOBJS"+cnt+")\n";
//						szContents+="\n$(RELEASETARGET"+cnt+"): $(RELEASEOBJS"+cnt+")";
//						szContents+="\n	$(CXX) $(LFLAGS) -shared -WI,-soname,$@ -o $@ $(RELEASEOBJS"+cnt+") $(LIBS)";
//					}
//					szContents+="\n\nall: ";
//					for(int i=1;i<=cnt;i++){
//						szContents+=" $(DEBUGTARGET"+i+") $(RELEASETARGET"+i+")";
//					}
//					szContents+="\n\ndefault:";
//					for(int i=1;i<=cnt;i++){
//						szContents+=" $(DEBUGTARGET"+i+")";
//					}
//					szContents+="\n\nclean:\n	rm -f";
//					for(int j=1;j<=cnt;j++){
//						szContents+=" $(DEBUGOBJS"+j+") $(DEBUGTARGET"+j+") $(RELEASEOBJS"+j+") $(RELEASETARGET"+j+")";
//					}
//				}
//				else if(info.getImplLanguage().equals(WizardMessages.getString("NewPrjWizardPage.SelectLanguageGroup.Combo1"))){
//					szContents+="\nBOOST_INC="+strBoostIncPath;
//					szContents+="\nBOOST_LIB="+strBoostLibPath;
//					szContents+="\nOPROS_INC="+strOPRoSIncPath;
//					szContents+="\nOPROS_LIB="+strOPRoSLibPath+"\n\n";
//	
//					int cnt=0;
//					int i=1;
//					String compName="";
//					while(it.hasNext()){
//						cnt++;
//						compName=it.next();
//						szContents+="PROJECT"+cnt+"="+compName+"\n";
//						if(strVSOption.compareTo("RELEASE")==0){
//							szContents+="OUTDIR"+cnt+"=.\\$(PROJECT"+cnt+")\\Release\n";
//							szContents+="INTDIR"+cnt+"=.\\$(PROJECT"+cnt+")\\Release\n";
//							szContents+="OutDir"+cnt+"=.\\$(PROJECT"+cnt+")\\Release\n";
//						}else{
//							szContents+="OUTDIR"+cnt+"=.\\$(PROJECT"+cnt+")\\Debug\n";
//							szContents+="INTDIR"+cnt+"=.\\$(PROJECT"+cnt+")\\Debug\n";
//							szContents+="OutDir"+cnt+"=.\\$(PROJECT"+cnt+")\\Debug\n";
//						}
//						szContents+="TARGET"+cnt+"=$(PROJECT"+cnt+")."+libType+"\n";
//						szContents+="OBJ"+cnt+"=$(PROJECT"+cnt+").obj\n\n";
//					}
//					szContents+="ALL:";
//					for(i=1;i<=cnt;i++){
//						szContents+="	$(OUTDIR"+i+")\\$(TARGET"+i+")";
//					}
//					szContents+="\n\nCLEAN:\n";
//					for(i=1;i<=cnt;i++){
//						szContents+="	-@erase \"$(OUTDIR"+i+")\\$(TARGET"+i+")\"\n";
//						szContents+="	-@erase \"$(OUTDIR"+i+")\\$(OBJ"+i+")\"\n";
//						szContents+="	-@erase \"$(OUTDIR"+i+")\\$(PROJECT"+i+").lib\"\n";
//					}
//					for(i=1;i<=cnt;i++){
//						szContents+="\n$(OUTDIR"+i+") :\n	if not exist \"$(OUTDIR"+i+")/$(NULL)\" mkdir \"$(OUTDIR"+i+")\"\n";
//					}
//	
//					szContents+="\nINC = /I \"$(BOOST_INC)\"";
//					szContents+=" /I \"$(OPROS_INC)\"";
//					if(!strOtherIncPath.isEmpty()){
//						StringTokenizer token=new StringTokenizer(strOtherIncPath,";");
//						while(token.hasMoreTokens()){
//							szContents+=" -I"+token.nextToken();
//						}
//					}
//					szContents+="\n\n";
//					szContents+="CPP=cl.exe\n\n";
//					for(i=1;i<=cnt;i++){
//						szContents+="CPP_PROJ=/O2 /Oi $(INC) /GL /D \"WIN32\" /D \"_WINDOWS\" /D \"N"+strVSOption+"\" /D \"MAKE_COMPONENT_DLL\" /D \"_USRDLL\" /D \"_WINDLL\" /D \"_AFXDLL\" ";
//						szContents+="/D \"_"+strVSUnicode+"\" \\\n";
//						szContents+="/D \""+strVSUnicode+"\" /FD /EHsc "+strMDOption+" /Gy /Fo\"$(INTDIR"+i+")\\\\\" /Fd\"$(INTDIR"+i+")\\\\\" /W0 /nologo /c /Zi /TP /errorReport:prompt\n\n";
//						szContents+="{$(PROJECT"+i+")}.cpp{$(INTDIR"+i+")}.obj::\n";
//						szContents+="	$(CPP) $(CPP_PROJ) $<\n\n";
//					}
//					
//					szContents+="LINK32=link.exe\n";
//					for(i=1;i<=cnt;i++){
//						szContents+="LINK32_FLAGS"+i+"=/OUT:\"$(OUTDIR"+i+")\\$(TARGET"+i+")\" /INCREMENTAL:NO /NOLOGO /libpath:\"$(BOOST_LIB)\" /libpath:\"$(OPROS_LIB)\" \\\n";
//						szContents+="/DLL /MANIFEST /"+strVSOption+" /SUBSYSTEM:WINDOWS /OPT:REF /OPT:ICF /LTCG /DYNAMICBASE /NXCOMPAT /MACHINE:X86 /ERRORREPORT:PROMPT OPRoSCDL.lib\n\n\n";
//					}
//					
//	
//					for(i=1;i<=cnt;i++){
//						szContents+="LINK32_OBJS"+i+"= \\\n";
//						szContents+="	\"$(INTDIR"+i+")/$(OBJ"+i+")\"\n\n\n";
//					}
//					for(i=1;i<=cnt;i++){
//						szContents+="$(OUTDIR"+i+")/$(TARGET"+i+") : $(OUTDIR"+i+") $(DEF_FILE) $(LINK32_OBJS"+i+")\n";
//						szContents+="	$(LINK32) $(LINK32_FLAGS"+i+") $(LINK32_OBJS"+i+")\n";
//					}
//					createVS2008prjFile(info);
//				}
//				try {
//					outStream.write(szContents.getBytes());
//					outStream.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//	}
	
	/**
	 * static 메소드
	 * MSVC 프로젝트일 경우 Visual Studio 2008용 프로젝트 파일을 생성한다.
	 * 	 * 
	 * @see kr.co.ed.opros.ce.core.OPRoSProjectInfo
	 * @see java.io.FileOutputStream
	 * @see kr.co.ed.opros.ce.OPRoSActivator
	 * @see org.eclipse.core.runtime.Preferences
	 */
//	public static void createVS2008prjFile(OPRoSProjectInfo prj){
//		OPRoSProjectInfo info = prj;
//		FileOutputStream prjStream=null;
//		Preferences pref = OPRoSActivator.getDefault().getPluginPreferences();
//		String strBoostLibPath = pref.getString(PreferenceConstants.BOOST_LIB_PATH);
//		String strBoostIncPath = pref.getString(PreferenceConstants.BOOST_INC_PATH);
//		String strOPRoSLibPath = pref.getString(PreferenceConstants.OPROS_LIB_PATH);
//		String strOPRoSIncPath = pref.getString(PreferenceConstants.OPROS_INC_PATH);
//		String strVSOption= pref.getString(PreferenceConstants.VS_COMPILE_OPTION);
//		if(strBoostLibPath.isEmpty())
//			strBoostLibPath = "./boost/lib # change this with your boost library path\n";
//		if(strBoostIncPath.isEmpty())
//			strBoostIncPath = "./boost/include/boost-1_35 # change this with your boost include path\n";
//		if(strOPRoSLibPath.isEmpty())
//			strOPRoSLibPath = getOPRoSFilesPath()+"OPRoSFiles/OPRoSLib";
//		if(strOPRoSIncPath.isEmpty())
//			strOPRoSIncPath = getOPRoSFilesPath()+"OPRoSFiles/OPRoSInc";
//		if(strVSOption.isEmpty())
//			strVSOption = "RELEASE";
//		
//		Iterator<String> it = info.getComponents();
//		String szPrjContents="";
//		String compName="";
//		while(it.hasNext()){
//			compName=it.next();
//			try {
//				prjStream = new FileOutputStream(info.getLocation()+"/"+info.getPrjName()+"/"+compName+"/"+compName+".vcproj");
//			} catch (FileNotFoundException e) {
//				e.printStackTrace();
//			}
//			if(prjStream!=null){
//				szPrjContents="<?xml version=\"1.0\" encoding=\"euc-kr\"?>";
//				szPrjContents="<VisualStudioProject\n";
//				szPrjContents+="ProjectType=\"Visual C++\"\n";
//				szPrjContents+="Version=\"9.00\"\n";
//				szPrjContents+="Name=\""+compName+"\"\n";
//				szPrjContents+="RootNamespace=\""+compName+"\"\n";
//				szPrjContents+="Keyword=\"Win32Proj\"\n";
//				szPrjContents+="TargetFrameworkVersion=\"0\"\n";
//				szPrjContents+=">\n";
//				szPrjContents+="<Platforms>\n";
//				szPrjContents+="<Platform\n";
//				szPrjContents+="Name=\"Win32\"\n";
//				szPrjContents+="/>\n";
//				szPrjContents+="</Platforms>\n";
//				szPrjContents+="<ToolFiles/>\n";
//				szPrjContents+="<Configurations>\n";
//				szPrjContents+="<Configuration\n";
//				szPrjContents+="Name=\"Debug|Win32\"\n";
//				szPrjContents+="OutputDirectory=\"$(SolutionDir)$(ConfigurationName)\"\n";
//				szPrjContents+="IntermediateDirectory=\"$(ConfigurationName)\"\n";
//				szPrjContents+="ConfigurationType=\"1\"\n";
//				szPrjContents+="CharacterSet=\"1\"\n";
//				szPrjContents+=">\n";
//				szPrjContents+="<Tool\n";
//				szPrjContents+="Name=\"VCPreBuildEventTool\"\n";
//				szPrjContents+="/>\n";
//				szPrjContents+="<Tool\n";
//				szPrjContents+="Name=\"VCCustomBuildTool\"\n";
//				szPrjContents+="/>\n";
//				szPrjContents+="<Tool\n";
//				szPrjContents+="Name=\"VCXMLDataGeneratorTool\"\n";
//				szPrjContents+="/>\n";
//				szPrjContents+="<Tool\n";
//				szPrjContents+="Name=\"VCWebServiceProxyGeneratorTool\"\n";
//				szPrjContents+="/>\n";
//				szPrjContents+="<Tool\n";
//				szPrjContents+="Name=\"VCMIDLTool\"\n";
//				szPrjContents+="/>\n";
//				szPrjContents+="<Tool\n";
//				szPrjContents+="Name=\"VCCLCompilerTool\"\n";
//				szPrjContents+="Optimization=\"0\"\n";
//				szPrjContents+="AdditionalIncludeDirectories=\""+strOPRoSIncPath+";&quot;"+strBoostIncPath+"&quot;\"\n";
//				szPrjContents+="PreprocessorDefinitions=\"WIN32;_DEBUG;_CONSOLE;MAKE_COMPONENT_DLL;\"\n";
//				szPrjContents+="MinimalRebuild=\"true\"\n";
//				szPrjContents+="BasicRuntimeChecks=\"3\"\n";
//				szPrjContents+="RuntimeLibrary=\"3\"\n";
//				szPrjContents+="UsePrecompiledHeader=\"0\"\n";
//				szPrjContents+="WarningLevel=\"3\"\n";
//				szPrjContents+="DebugInformationFormat=\"4\"\n";
//				szPrjContents+="DisableSpecificWarnings=\"4290;4819;4996\"\n";
//				szPrjContents+="/>\n";
//				szPrjContents+="<Tool\n";
//				szPrjContents+="Name=\"VCManagedResourceCompilerTool\"\n";
//				szPrjContents+="/>\n";
//				szPrjContents+="<Tool\n";
//				szPrjContents+="Name=\"VCResourceCompilerTool\"\n";
//				szPrjContents+="/>\n";
//				szPrjContents+="<Tool\n";
//				szPrjContents+="Name=\"VCPreLinkEventTool\"\n";
//				szPrjContents+="/>\n";
//				szPrjContents+="<Tool\n";
//				szPrjContents+="Name=\"VCLinkerTool\"\n";
//				szPrjContents+="AdditionalDependencies=\"OPRoSCDL.lib\"\n";
//				szPrjContents+="LinkIncremental=\"2\"\n";
//				szPrjContents+="AdditionalLibraryDirectories=\""+strBoostLibPath+";"+strOPRoSLibPath+"\"\n";
//				szPrjContents+="GenerateDebugInformation=\"true\"\n";
//				szPrjContents+="SubSystem=\"1\"\n";
//				szPrjContents+="TargetMachine=\"1\"\n";
//				szPrjContents+="/>\n";				
//				szPrjContents+="<Tool\n";
//				szPrjContents+="Name=\"VCALinkTool\"\n";
//				szPrjContents+="/>\n";
//				szPrjContents+="<Tool\n";
//				szPrjContents+="Name=\"VCManifestTool\"\n";
//				szPrjContents+="/>\n";
//				szPrjContents+="<Tool\n";
//				szPrjContents+="Name=\"VCXDCMakeTool\"\n";
//				szPrjContents+="/>\n";
//				szPrjContents+="<Tool\n";
//				szPrjContents+="Name=\"VCBscMakeTool\"\n";
//				szPrjContents+="/>\n";
//				szPrjContents+="<Tool\n";
//				szPrjContents+="Name=\"VCFxCopTool\"\n";
//				szPrjContents+="/>\n";
//				szPrjContents+="<Tool\n";
//				szPrjContents+="Name=\"VCAppVerifierTool\"\n";
//				szPrjContents+="/>\n";
//				szPrjContents+="<Tool\n";
//				szPrjContents+="Name=\"VCPostBuildEventTool\"\n";
//				szPrjContents+="/>\n";
//				szPrjContents+="</Configuration>\n";
//				szPrjContents+="<Configuration\n";
////				if(strVSOption.compareTo("RELEASE")==0){
//					szPrjContents+="Name=\"Release|Win32\"\n";
////				}else{
////					szPrjContents+="Name=\"Debug|Win32\"\n";
////				}
//				szPrjContents+="OutputDirectory=\"$(SolutionDir)$(ConfigurationName)\"\n";
//				szPrjContents+="IntermediateDirectory=\"$(ConfigurationName)\"\n";
//				szPrjContents+="ConfigurationType=\"1\"\n";
//				szPrjContents+="CharacterSet=\"1\"\n";
//				szPrjContents+="WholeProgramOptimization=\"1\"\n";
//				szPrjContents+=">\n";
//				szPrjContents+="<Tool\n";
//				szPrjContents+="Name=\"VCPreBuildEventTool\"\n";
//				szPrjContents+="/>\n";
//				szPrjContents+="<Tool\n";
//				szPrjContents+="Name=\"VCCustomBuildTool\"\n";
//				szPrjContents+="/>\n";
//				szPrjContents+="<Tool\n";
//				szPrjContents+="Name=\"VCXMLDataGeneratorTool\"\n";
//				szPrjContents+="/>\n";
//				szPrjContents+="<Tool\n";
//				szPrjContents+="Name=\"VCWebServiceProxyGeneratorTool\"\n";
//				szPrjContents+="/>\n";
//				szPrjContents+="<Tool\n";
//				szPrjContents+="Name=\"VCMIDLTool\"\n";
//				szPrjContents+="/>\n";
//				szPrjContents+="<Tool\n";
//				szPrjContents+="Name=\"VCCLCompilerTool\"\n";
//				szPrjContents+="Optimization=\"2\"\n";
//				szPrjContents+="EnableIntrinsicFunctions=\"true\"\n";
//				szPrjContents+="AdditionalIncludeDirectories=\""+strOPRoSIncPath+";"+strBoostIncPath+";\"\n";
//				szPrjContents+="PreprocessorDefinitions=\"WIN32;NDEBUG;_CONSOLE;MAKE_COMPONENT_DLL;\"\n";
//				szPrjContents+="RuntimeLibrary=\"2\"\n";
//				szPrjContents+="EnableFunctionLevelLinking=\"true\"\n";
//				szPrjContents+="UsePrecompiledHeader=\"0\"\n";
//				szPrjContents+="WarningLevel=\"3\"\n";
//				szPrjContents+="DebugInformationFormat=\"3\"\n";
//				szPrjContents+="DisableSpecificWarnings=\"4290;4819;4996\"\n";
//				szPrjContents+="/>\n";
//				szPrjContents+="<Tool\n";
//				szPrjContents+="Name=\"VCManagedResourceCompilerTool\"\n";
//				szPrjContents+="/>\n";
//				szPrjContents+="<Tool\n";
//				szPrjContents+="Name=\"VCResourceCompilerTool\"\n";
//				szPrjContents+="/>\n";
//				szPrjContents+="<Tool\n";
//				szPrjContents+="Name=\"VCPreLinkEventTool\"\n";
//				szPrjContents+="/>\n";
//				szPrjContents+="<Tool\n";
//				szPrjContents+="Name=\"VCLinkerTool\"\n";
//				szPrjContents+="AdditionalDependencies=\"OPRoSCDL.lib\"\n";
//				szPrjContents+="LinkIncremental=\"1\"\n";
//				szPrjContents+="AdditionalLibraryDirectories=\""+strBoostLibPath+";"+strOPRoSLibPath+"\"\n";
//				szPrjContents+="GenerateDebugInformation=\"true\"\n";
//				szPrjContents+="SubSystem=\"1\"\n";
//				szPrjContents+="OptimizeReferences=\"2\"\n";
//				szPrjContents+="EnableCOMDATFolding=\"2\"\n";
//				szPrjContents+="TargetMachine=\"1\"\n";
//				szPrjContents+="/>\n";				
//				szPrjContents+="<Tool\n";
//				szPrjContents+="Name=\"VCALinkTool\"\n";
//				szPrjContents+="/>\n";
//				szPrjContents+="<Tool\n";
//				szPrjContents+="Name=\"VCManifestTool\"\n";
//				szPrjContents+="/>\n";
//				szPrjContents+="<Tool\n";
//				szPrjContents+="Name=\"VCXDCMakeTool\"\n";
//				szPrjContents+="/>\n";
//				szPrjContents+="<Tool\n";
//				szPrjContents+="Name=\"VCBscMakeTool\"\n";
//				szPrjContents+="/>\n";
//				szPrjContents+="<Tool\n";
//				szPrjContents+="Name=\"VCFxCopTool\"\n";
//				szPrjContents+="/>\n";
//				szPrjContents+="<Tool\n";
//				szPrjContents+="Name=\"VCAppVerifierTool\"\n";
//				szPrjContents+="/>\n";
//				szPrjContents+="<Tool\n";
//				szPrjContents+="Name=\"VCPostBuildEventTool\"\n";
//				szPrjContents+="/>\n";
//				szPrjContents+="</Configuration>\n";
//				szPrjContents+="</Configurations>\n";
//				szPrjContents+="<References>\n";
//				szPrjContents+="</References>\n";
//				szPrjContents+="<Files>\n";
//				szPrjContents+="<Filter\n";
//				szPrjContents+="Name=\"source\"\n";
//				szPrjContents+="Filter=\"cpp;c;cc;cxx;def;odl;idl;hpj;bat;asm;asmx\"\n";
//				szPrjContents+=">\n";
//				szPrjContents+="<File\n";
//				szPrjContents+="RelativePath=\".\\"+compName+".cpp\"\n";
//				szPrjContents+=">\n";
//				szPrjContents+="</File>";
//				szPrjContents+="</Filter>\n";
//				szPrjContents+="<Filter\n";
//				szPrjContents+="Name=\"header\"\n";
//				szPrjContents+="Filter=\"h;hpp;hxx;hm;inl;inc;xsd\"\n";
//				szPrjContents+=">\n";
//				szPrjContents+="<File\n";
//				szPrjContents+="RelativePath=\".\\"+compName+".h\"\n";
//				szPrjContents+=">\n";
//				szPrjContents+="</File>";
//				szPrjContents+="</Filter>\n";
//				szPrjContents+="<Filter\n";
//				szPrjContents+="Name=\"resource\"\n";
//				szPrjContents+="Filter=\"rc;ico;cur;bmp;dlg;rc2;rct;bin;rgs;gif;jpg;jpeg;jpe;resx;tiff;tif;png;wav\"\n";
//				szPrjContents+=">\n";
//				szPrjContents+="</Filter>\n";
//				szPrjContents+="</Files>\n";
//				szPrjContents+="<Globals>\n";
//				szPrjContents+="</Globals>\n";
//				szPrjContents+="</VisualStudioProject>\n";
//				try {
//					prjStream.write(szPrjContents.getBytes());
//					prjStream.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//	}
	
	/**
	 * static 메소드
	 * Runtime에서의 OPRoSFiles 폴더의 위치를 가져온다.
	 * 
	 * @return OPRoSFiles의 경로
	 * 
	 * @see kr.co.ed.opros.ce.OPRoSActivator
	 * @See org.eclipse.core.runtime.Platform
	 */

	public static String getOPRoSFilesPath(){
		if(OPRoSFilesPath==null||OPRoSFilesPath.isEmpty()){
			URL url = Activator.getDefault().getBundle().getEntry("/");
			try {
				OPRoSFilesPath = Platform.asLocalURL(url).getPath();
//				String OPRoSFilesPath1 = OPRoSFilesPath;
//				System.out.println("OPRoSFilesPath1===>"+OPRoSFilesPath1);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			if(OPRoSFilesPath.startsWith("/"))
				OPRoSFilesPath = OPRoSFilesPath.substring(1);
//			String OPRoSFilesPath1 = OPRoSFilesPath;
//			System.out.println("OPRoSFilesPath1===>"+OPRoSFilesPath1);
		}
		return OPRoSFilesPath;
	}
	
	/**
	 * static 메소드
	 * 프로젝트의 구현언어(MinGW,MSVC,JAVA) 설정값을 가져온다.
	 * 
	 * @param IProject 클랙스의 인스턴스, 프로젝트 정보
	 * @return MinGW C++의 경우 0을 리턴, MSVC C++의 경우 1을 리턴
	 *  
	 * @see java.io.FileInputStream
	 * @see org.eclipse.core.resources.IFile
	 * @see org.jdom.input.SAXBuilder
	 * @see org.jdom.Document
	 * @see org.jdom.Element
	 */
//	public static int getProjectLanguageSetting(IProject prj,OPRoSProjectInfo info){
//		OPRoSProjectInfo prjInfo=info;
//		int ret=-1;
//        if(prj!=null){
//	        IFile projectProfile = prj.getFile(prj.getName()+"Prj.xml");
//			FileInputStream input=null;
//			try {
//				input = (FileInputStream) projectProfile.getContents();
//			} catch (CoreException e) {
//				e.printStackTrace();
//			}
//			SAXBuilder builder = new SAXBuilder();
//			Document doc = null;
//			try {
//				doc = builder.build( input );
//			} catch (JDOMException e) {
//				e.printStackTrace();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			Element root;
//			root = doc.getRootElement();
//			if(prjInfo!=null){
//				prjInfo.clear();
//				prjInfo.loadProfile(root);
//				if(prjInfo.getImplLanguage().equalsIgnoreCase("MSVC C++"))
//					ret= 1;
//				else if(prjInfo.getImplLanguage().equalsIgnoreCase("MinGW C++"))
//					ret= 0;
//			}
//			if(input!=null){
//				try {
//					input.close();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//        }
//		return ret;
//	}
//	public static Image createImage(String name){
//		InputStream stream = OPRoSActivator.class.getResourceAsStream(name);
//		Image image = new Image(null,stream);
//		try{
//			stream.close();
//		}catch(IOException e){
//			e.printStackTrace();
//		}
//		return image;
//	}
	public static boolean isDuplicatePortName(String portName,boolean bNotSelf,AtomicComponentModel comModel){
		AtomicComponentModel compModel = comModel;
		boolean bCheck=false;
		if(compModel!=null){
			
			
			if(compModel.getPorts().size()>0){
				Iterator it = compModel.getPorts().iterator();
				Object model;
				while(it.hasNext()){
					model= it.next();
					if(model instanceof PortModel){
						if(portName.compareTo(((PortModel)model).getName())==0){
							if(bNotSelf&&bCheck==false){
								bCheck=true;
							}else{
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}
	public static boolean isDuplicatePropertyName(String propertyName,boolean bNotSelf,AtomicComponentModel comModel){
		AtomicComponentModel compModel = comModel;
		boolean bCheck=false;
		if(compModel!=null){
			if(compModel.getPorts().size()>0){
				Iterator it = compModel.getPorts().iterator();
				Object model;
				while(it.hasNext()){
				model=it.next();
				if(model instanceof PortModel){
					if(propertyName.compareTo(((PortModel)model).getName())==0){
						if(bNotSelf&&bCheck==false){
							bCheck=true;
						}else{
							return true;
						}
					}
				}
			}
		}
		}
		return false;
	}
	public static boolean isDuplicateDataTypeName(String dataTypeName,boolean bNotSelf,AtomicComponentModel comModel){
		AtomicComponentModel compModel = comModel;
		boolean bCheck=false;
		if(compModel!=null && compModel.getDataTypesModel()!=null){
			Iterator it = compModel.getDataTypesModel().iterator();
			Object model;
			while(it.hasNext()){
				model=  it.next();
				if(model instanceof OPRoSDataTypeElementModel){
					if(dataTypeName.compareTo(((OPRoSDataTypeElementModel)model).getDataTypeFileName())==0){
						if(bNotSelf&&bCheck==false){
							bCheck=true;
						}else{
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	public static boolean isDuplicateServiceTypeName(String serviceTypeName,boolean bNotSelf,AtomicComponentModel comModel){
		AtomicComponentModel compModel = comModel;
		boolean bCheck=false;
		if(compModel!=null){
			Iterator it = compModel.getServiceTypesModel().iterator();
			Object model;
			while(it.hasNext()){
				model=it.next();
				if(model instanceof OPRoSServiceTypeElementModel){
					if(serviceTypeName.compareTo(((OPRoSServiceTypeElementModel)model).getServiceTypeFileName())==0){
						if(bNotSelf&&bCheck==false){
							bCheck=true;
						}else{
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	public static void createVS2008prjFile(OPRoSProjectInfo prj){
		OPRoSProjectInfo info = prj;
		FileOutputStream prjStream=null;
//		Preferences pref = N3Plugin.getDefault().getPluginPreferences();
//		String strBoostLibPath ="";
//		String strBoostIncPath = "";
//		String strOPRoSLibPath = "";
//		String strOPRoSIncPath = "";
//		String strVSOption= "";
		Preferences pref = Activator.getDefault().getPluginPreferences();
//		String strBoostLibPath = pref.getString(PreferenceConstants.BOOST_LIB_PATH);
//		String strBoostIncPath = pref.getString(PreferenceConstants.BOOST_INC_PATH);
		String strOPRoSLibPath = pref.getString(PreferenceConstants.OPROS_LIB_PATH);
//		String strOPRoSIncPath = pref.getString(PreferenceConstants.OPROS_INC_PATH);
		String strVSOption= pref.getString(PreferenceConstants.VS_COMPILE_OPTION);
		String strBoostLibPath = strOPRoSLibPath+File.separator+"lib";
		String strBoostIncPath = strOPRoSLibPath+File.separator+"include"+File.separator+"boost";
		String strOPRoSIncPath = strOPRoSLibPath+File.separator+"include";
		
//		if(strBoostLibPath.isEmpty())
//			strBoostLibPath = "./boost/lib # change this with your boost library path\n";
//		if(strBoostIncPath.isEmpty())
//			strBoostIncPath = "./boost/include/boost-1_35 # change this with your boost include path\n";
//		if(strOPRoSLibPath.isEmpty())
//			strOPRoSLibPath = getOPRoSFilesPath()+"OPRoSFiles/OPRoSLib";
//		if(strOPRoSIncPath.isEmpty())
//			strOPRoSIncPath = getOPRoSFilesPath()+"OPRoSFiles/OPRoSInc";
		if(strVSOption.isEmpty())
			strVSOption = "RELEASE";
		
		Iterator<String> it = info.getComponents();
		String szPrjContents="";
		String compName="";
		while(it.hasNext()){
			compName=it.next();
			try {
				prjStream = new FileOutputStream(info.getLocation()+"/"+info.getPrjName()+"/"+compName+"/"+compName+".vcproj");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			if(prjStream!=null){
				szPrjContents="<?xml version=\"1.0\" encoding=\"euc-kr\"?>";
				szPrjContents="<VisualStudioProject\n";
				szPrjContents+="ProjectType=\"Visual C++\"\n";
				szPrjContents+="Version=\"9.00\"\n";
				szPrjContents+="Name=\""+compName+"\"\n";
				szPrjContents+="RootNamespace=\""+compName+"\"\n";
				szPrjContents+="Keyword=\"Win32Proj\"\n";
				szPrjContents+="TargetFrameworkVersion=\"0\"\n";
				szPrjContents+=">\n";
				szPrjContents+="<Platforms>\n";
				szPrjContents+="<Platform\n";
				szPrjContents+="Name=\"Win32\"\n";
				szPrjContents+="/>\n";
				szPrjContents+="</Platforms>\n";
				szPrjContents+="<ToolFiles/>\n";
				szPrjContents+="<Configurations>\n";
				szPrjContents+="<Configuration\n";
				szPrjContents+="Name=\"Debug|Win32\"\n";
				szPrjContents+="OutputDirectory=\"$(SolutionDir)$(ConfigurationName)\"\n";
				szPrjContents+="IntermediateDirectory=\"$(ConfigurationName)\"\n";
				szPrjContents+="ConfigurationType=\"1\"\n";
				szPrjContents+="CharacterSet=\"1\"\n";
				szPrjContents+=">\n";
				szPrjContents+="<Tool\n";
				szPrjContents+="Name=\"VCPreBuildEventTool\"\n";
				szPrjContents+="/>\n";
				szPrjContents+="<Tool\n";
				szPrjContents+="Name=\"VCCustomBuildTool\"\n";
				szPrjContents+="/>\n";
				szPrjContents+="<Tool\n";
				szPrjContents+="Name=\"VCXMLDataGeneratorTool\"\n";
				szPrjContents+="/>\n";
				szPrjContents+="<Tool\n";
				szPrjContents+="Name=\"VCWebServiceProxyGeneratorTool\"\n";
				szPrjContents+="/>\n";
				szPrjContents+="<Tool\n";
				szPrjContents+="Name=\"VCMIDLTool\"\n";
				szPrjContents+="/>\n";
				szPrjContents+="<Tool\n";
				szPrjContents+="Name=\"VCCLCompilerTool\"\n";
				szPrjContents+="Optimization=\"0\"\n";
				szPrjContents+="AdditionalIncludeDirectories=\""+strOPRoSIncPath+";&quot;"+strBoostIncPath+"&quot;\"\n";
				szPrjContents+="PreprocessorDefinitions=\"WIN32;_DEBUG;_CONSOLE;MAKE_COMPONENT_DLL;\"\n";
				szPrjContents+="MinimalRebuild=\"true\"\n";
				szPrjContents+="BasicRuntimeChecks=\"3\"\n";
				szPrjContents+="RuntimeLibrary=\"3\"\n";
				szPrjContents+="UsePrecompiledHeader=\"0\"\n";
				szPrjContents+="WarningLevel=\"3\"\n";
				szPrjContents+="DebugInformationFormat=\"4\"\n";
				szPrjContents+="DisableSpecificWarnings=\"4290;4819;4996\"\n";
				szPrjContents+="/>\n";
				szPrjContents+="<Tool\n";
				szPrjContents+="Name=\"VCManagedResourceCompilerTool\"\n";
				szPrjContents+="/>\n";
				szPrjContents+="<Tool\n";
				szPrjContents+="Name=\"VCResourceCompilerTool\"\n";
				szPrjContents+="/>\n";
				szPrjContents+="<Tool\n";
				szPrjContents+="Name=\"VCPreLinkEventTool\"\n";
				szPrjContents+="/>\n";
				szPrjContents+="<Tool\n";
				szPrjContents+="Name=\"VCLinkerTool\"\n";
				szPrjContents+="AdditionalDependencies=\"OPRoSCDL.lib\"\n";
				szPrjContents+="LinkIncremental=\"2\"\n";
//				szPrjContents+="AdditionalLibraryDirectories=\""+strBoostLibPath+";"+strOPRoSLibPath+"\"\n";
				szPrjContents+="AdditionalLibraryDirectories=\""+strBoostLibPath+"\"\n";
				szPrjContents+="GenerateDebugInformation=\"true\"\n";
				szPrjContents+="SubSystem=\"1\"\n";
				szPrjContents+="TargetMachine=\"1\"\n";
				szPrjContents+="/>\n";				
				szPrjContents+="<Tool\n";
				szPrjContents+="Name=\"VCALinkTool\"\n";
				szPrjContents+="/>\n";
				szPrjContents+="<Tool\n";
				szPrjContents+="Name=\"VCManifestTool\"\n";
				szPrjContents+="/>\n";
				szPrjContents+="<Tool\n";
				szPrjContents+="Name=\"VCXDCMakeTool\"\n";
				szPrjContents+="/>\n";
				szPrjContents+="<Tool\n";
				szPrjContents+="Name=\"VCBscMakeTool\"\n";
				szPrjContents+="/>\n";
				szPrjContents+="<Tool\n";
				szPrjContents+="Name=\"VCFxCopTool\"\n";
				szPrjContents+="/>\n";
				szPrjContents+="<Tool\n";
				szPrjContents+="Name=\"VCAppVerifierTool\"\n";
				szPrjContents+="/>\n";
				szPrjContents+="<Tool\n";
				szPrjContents+="Name=\"VCPostBuildEventTool\"\n";
				szPrjContents+="/>\n";
				szPrjContents+="</Configuration>\n";
				szPrjContents+="<Configuration\n";
				if(strVSOption.compareTo("RELEASE")==0){
					szPrjContents+="Name=\"Release|Win32\"\n";
				}else{
					szPrjContents+="Name=\"Debug|Win32\"\n";
				}
				szPrjContents+="OutputDirectory=\"$(SolutionDir)$(ConfigurationName)\"\n";
				szPrjContents+="IntermediateDirectory=\"$(ConfigurationName)\"\n";
				szPrjContents+="ConfigurationType=\"1\"\n";
				szPrjContents+="CharacterSet=\"1\"\n";
				szPrjContents+="WholeProgramOptimization=\"1\"\n";
				szPrjContents+=">\n";
				szPrjContents+="<Tool\n";
				szPrjContents+="Name=\"VCPreBuildEventTool\"\n";
				szPrjContents+="/>\n";
				szPrjContents+="<Tool\n";
				szPrjContents+="Name=\"VCCustomBuildTool\"\n";
				szPrjContents+="/>\n";
				szPrjContents+="<Tool\n";
				szPrjContents+="Name=\"VCXMLDataGeneratorTool\"\n";
				szPrjContents+="/>\n";
				szPrjContents+="<Tool\n";
				szPrjContents+="Name=\"VCWebServiceProxyGeneratorTool\"\n";
				szPrjContents+="/>\n";
				szPrjContents+="<Tool\n";
				szPrjContents+="Name=\"VCMIDLTool\"\n";
				szPrjContents+="/>\n";
				szPrjContents+="<Tool\n";
				szPrjContents+="Name=\"VCCLCompilerTool\"\n";
				szPrjContents+="Optimization=\"2\"\n";
				szPrjContents+="EnableIntrinsicFunctions=\"true\"\n";
				szPrjContents+="AdditionalIncludeDirectories=\""+strOPRoSIncPath+";"+strBoostIncPath+";\"\n";
				szPrjContents+="PreprocessorDefinitions=\"WIN32;NDEBUG;_CONSOLE;MAKE_COMPONENT_DLL;\"\n";
				szPrjContents+="RuntimeLibrary=\"2\"\n";
				szPrjContents+="EnableFunctionLevelLinking=\"true\"\n";
				szPrjContents+="UsePrecompiledHeader=\"0\"\n";
				szPrjContents+="WarningLevel=\"3\"\n";
				szPrjContents+="DebugInformationFormat=\"3\"\n";
				szPrjContents+="DisableSpecificWarnings=\"4290;4819;4996\"\n";
				szPrjContents+="/>\n";
				szPrjContents+="<Tool\n";
				szPrjContents+="Name=\"VCManagedResourceCompilerTool\"\n";
				szPrjContents+="/>\n";
				szPrjContents+="<Tool\n";
				szPrjContents+="Name=\"VCResourceCompilerTool\"\n";
				szPrjContents+="/>\n";
				szPrjContents+="<Tool\n";
				szPrjContents+="Name=\"VCPreLinkEventTool\"\n";
				szPrjContents+="/>\n";
				szPrjContents+="<Tool\n";
				szPrjContents+="Name=\"VCLinkerTool\"\n";
				szPrjContents+="AdditionalDependencies=\"OPRoSCDL.lib\"\n";
				szPrjContents+="LinkIncremental=\"1\"\n";
				szPrjContents+="AdditionalLibraryDirectories=\""+strBoostLibPath+";"+strOPRoSLibPath+"\"\n";
				szPrjContents+="GenerateDebugInformation=\"true\"\n";
				szPrjContents+="SubSystem=\"1\"\n";
				szPrjContents+="OptimizeReferences=\"2\"\n";
				szPrjContents+="EnableCOMDATFolding=\"2\"\n";
				szPrjContents+="TargetMachine=\"1\"\n";
				szPrjContents+="/>\n";				
				szPrjContents+="<Tool\n";
				szPrjContents+="Name=\"VCALinkTool\"\n";
				szPrjContents+="/>\n";
				szPrjContents+="<Tool\n";
				szPrjContents+="Name=\"VCManifestTool\"\n";
				szPrjContents+="/>\n";
				szPrjContents+="<Tool\n";
				szPrjContents+="Name=\"VCXDCMakeTool\"\n";
				szPrjContents+="/>\n";
				szPrjContents+="<Tool\n";
				szPrjContents+="Name=\"VCBscMakeTool\"\n";
				szPrjContents+="/>\n";
				szPrjContents+="<Tool\n";
				szPrjContents+="Name=\"VCFxCopTool\"\n";
				szPrjContents+="/>\n";
				szPrjContents+="<Tool\n";
				szPrjContents+="Name=\"VCAppVerifierTool\"\n";
				szPrjContents+="/>\n";
				szPrjContents+="<Tool\n";
				szPrjContents+="Name=\"VCPostBuildEventTool\"\n";
				szPrjContents+="/>\n";
				szPrjContents+="</Configuration>\n";
				szPrjContents+="</Configurations>\n";
				szPrjContents+="<References>\n";
				szPrjContents+="</References>\n";
				szPrjContents+="<Files>\n";
				szPrjContents+="<Filter\n";
				szPrjContents+="Name=\"source\"\n";
				szPrjContents+="Filter=\"cpp;c;cc;cxx;def;odl;idl;hpj;bat;asm;asmx\"\n";
				szPrjContents+=">\n";
				szPrjContents+="<File\n";
				szPrjContents+="RelativePath=\".\\"+compName+".cpp\"\n";
				szPrjContents+=">\n";
				szPrjContents+="</File>";
				szPrjContents+="</Filter>\n";
				szPrjContents+="<Filter\n";
				szPrjContents+="Name=\"header\"\n";
				szPrjContents+="Filter=\"h;hpp;hxx;hm;inl;inc;xsd\"\n";
				szPrjContents+=">\n";
				szPrjContents+="<File\n";
				szPrjContents+="RelativePath=\".\\"+compName+".h\"\n";
				szPrjContents+=">\n";
				szPrjContents+="</File>";
				szPrjContents+="</Filter>\n";
				szPrjContents+="<Filter\n";
				szPrjContents+="Name=\"resource\"\n";
				szPrjContents+="Filter=\"rc;ico;cur;bmp;dlg;rc2;rct;bin;rgs;gif;jpg;jpeg;jpe;resx;tiff;tif;png;wav\"\n";
				szPrjContents+=">\n";
				szPrjContents+="</Filter>\n";
				szPrjContents+="</Files>\n";
				szPrjContents+="<Globals>\n";
				szPrjContents+="</Globals>\n";
				szPrjContents+="</VisualStudioProject>\n";
				try {
					prjStream.write(szPrjContents.getBytes());
					prjStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	public static void createMakeFile(String libTypeName,OPRoSProjectInfo prjInfo,AtomicComponentModel atom){
		boolean isBlock = false;
		OPRoSProjectInfo info;
		String libType;
		UMLTreeParentModel up = atom.getUMLTreeModel().getParent();
		if(up instanceof RootCmpEdtTreeModel){
			info = atom.getOPRoSProjectInfo();
		}
		else{
			info = up.getOPRoSProjectInfo();
		}
		if(atom.isBlockComponent()){
			isBlock = true;
		}
		else{
			String path  = atom.getCmpFolder();
			path = path +File.separator + atom.getName()+".block";
			File f = new File(path);
			if(f.exists()){
				isBlock = true;
			}
			
			
		}
		
		
//		if(prjInfo!=null)
//			info = prjInfo;
//		else
//			return;
		if(atom==null)
			return;
//			info = prjInfo;
//		else
			
//		info = up.getOPRoSProjectInfo();
		libType = atom.getlibTypeProp();
		info.setImplLanguage(atom.getimplementLangString());
//		atom.getcompilerString();
		if(libTypeName==null || libTypeName.isEmpty())
			libType="dll";
		else
			libType=libTypeName;
		if(info!=null){
			Iterator<String> compIt = info.getComponents();
			while(compIt.hasNext()){
				String compName = compIt.next();
				File dir = new File(info.getLocation()+"/"+info.getPrjName()+"/"+compName+"/Debug");
				if(!dir.exists())
					dir.mkdir();
				dir = new File(info.getLocation()+"/"+info.getPrjName()+"/"+compName+"/Release");
				if(!dir.exists())
					dir.mkdir();
			}
			FileOutputStream outStream=null;
			try {
				outStream = new FileOutputStream(info.getLocation()+"/"+info.getPrjName()+"/Makefile");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			if(outStream!=null){
				Preferences pref = Activator.getDefault().getPluginPreferences();
//				String strBoostLibPath = pref.getString(PreferenceConstants.BOOST_LIB_PATH);
//				String strBoostIncPath = pref.getString(PreferenceConstants.BOOST_INC_PATH);
//				String strOPRoSLibPath = pref.getString(PreferenceConstants.OPROS_LIB_PATH);
//				String strOPRoSIncPath = pref.getString(PreferenceConstants.OPROS_INC_PATH);
				String strOtherIncPath = pref.getString(PreferenceConstants.OTHER_INC_PATH);
//				String strVSOption= pref.getString(PreferenceConstants.VS_COMPILE_OPTION);
				
				
				
//				String strBoostLibPath = pref.getString(PreferenceConstants.BOOST_LIB_PATH);
//				String strBoostIncPath = pref.getString(PreferenceConstants.BOOST_INC_PATH);
				String strOPRoSLibPath = pref.getString(PreferenceConstants.OPROS_LIB_PATH);
//				String strOPRoSIncPath = pref.getString(PreferenceConstants.OPROS_INC_PATH);
				String strVSOption= pref.getString(PreferenceConstants.VS_COMPILE_OPTION);
				String strBoostLibPath = strOPRoSLibPath+File.separator+"lib";
				String strBoostIncPath = strOPRoSLibPath+File.separator+"include"+File.separator+"boost";
				String strOPRoSIncPath = strOPRoSLibPath+File.separator+"include";
				
				
//				String strVSUnicode= pref.getString(PreferenceConstants.VS_UNICODE_OPTION);
//				String strBoostLibPath ="";
//				String strBoostIncPath = "";
//				String strOPRoSLibPath = "";
//				String strOPRoSIncPath = "";
//				String strOtherIncPath = "";
//				String strVSOption= "";
				String strVSUnicode= "UNICODE";
				String strMDOption="/MD";
				
//				if(strBoostLibPath.isEmpty())
//					strBoostLibPath = "./boost/lib # change this with your boost library path\n";
//				if(strBoostIncPath.isEmpty())
//					strBoostIncPath = "./boost/include/boost-1_35 # change this with your boost include path\n";
//				if(strOPRoSLibPath.isEmpty())
//					strOPRoSLibPath = getOPRoSFilesPath()+"OPRoSFiles/OPRoSLib";
//				if(strOPRoSIncPath.isEmpty())
//					strOPRoSIncPath = getOPRoSFilesPath()+"OPRoSFiles/OPRoSInc";
//				if(strVSOption.isEmpty()){
//					strVSOption = "RELEASE";
//					strMDOption = "/MD";
//				}else if(strVSOption.compareTo("RELEASE")==0){
//					strMDOption="/MD";
//				}else{
//					strMDOption = "/MDd";
//				}
//				if(strVSUnicode.isEmpty())
//					strVSUnicode = "UNICODE";
				
			
				Iterator<String> it = info.getComponents();
				String szContents="";
				
				if(isBlock){
					
//					szContents+="\nBOOST_INC="+strBoostIncPath;
//					szContents+="\nBOOST_LIB="+strBoostLibPath;
//					szContents+="\nOPROS_INC="+strOPRoSIncPath;
//					szContents+="\nOPROS_LIB="+strOPRoSLibPath+"\n\n";
					szContents+="\nBOOST_INC="+strBoostIncPath;
					szContents+="\nBOOST_LIB="+strBoostLibPath;
					szContents+="\nOPROS_INC="+strOPRoSIncPath;
					szContents+="\nOPROS_LIB="+strBoostLibPath+"\n\n";
	
					int cnt=0;
					int i=1;
					String compName="";
					while(it.hasNext()){
						cnt++;
						compName=it.next();
						szContents+="PROJECT"+cnt+"="+compName+"\n";
						if(strVSOption.compareTo("RELEASE")==0){
							szContents+="OUTDIR"+cnt+"=.\\$(PROJECT"+cnt+")\\Release\n";
							szContents+="INTDIR"+cnt+"=.\\$(PROJECT"+cnt+")\\Release\n";
							szContents+="OutDir"+cnt+"=.\\$(PROJECT"+cnt+")\\Release\n";
						}else{
							szContents+="OUTDIR"+cnt+"=.\\$(PROJECT"+cnt+")\\Debug\n";
							szContents+="INTDIR"+cnt+"=.\\$(PROJECT"+cnt+")\\Debug\n";
							szContents+="OutDir"+cnt+"=.\\$(PROJECT"+cnt+")\\Debug\n";
						}
						szContents+="TARGET"+cnt+"=$(PROJECT"+cnt+")."+libType+"\n";
						szContents+="OBJ"+cnt+"=$(PROJECT"+cnt+").obj\n\n";
						szContents+="OBJ2"+cnt+"=$(PROJECT"+cnt+")_bl.obj\n\n";
						szContents+="SourceDir=.\\$(PROJECT"+cnt+")\n\n";
					}
					szContents+="ALL:";
					for(i=1;i<=cnt;i++){
						szContents+="	$(OUTDIR"+i+")\\$(TARGET"+i+")";
					}
					szContents+="\n\nCLEAN:\n";
					for(i=1;i<=cnt;i++){
						szContents+="	-@erase \"$(OUTDIR"+i+")\\$(TARGET"+i+")\"\n";
						szContents+="	-@erase \"$(OUTDIR"+i+")\\$(OBJ"+i+")\"\n";
						szContents+="	-@erase \"$(OUTDIR"+i+")\\$(PROJECT"+i+").lib\"\n";
					}
					for(i=1;i<=cnt;i++){
						szContents+="\n$(OUTDIR"+i+") :\n	if not exist \"$(OUTDIR"+i+")/$(NULL)\" mkdir \"$(OUTDIR"+i+")\"\n";
					}
	
					szContents+="\nINC = /I \"$(BOOST_INC)\"";
					szContents+=" /I \"$(OPROS_INC)\" -IC:\\MinGW\\bin";
					if(!strOtherIncPath.isEmpty()){
						StringTokenizer token=new StringTokenizer(strOtherIncPath,";");
						while(token.hasMoreTokens()){
							szContents+=" -I"+token.nextToken();
						}
					}
					szContents+="\n\n";
					szContents+="CPP=cl.exe\n\n";
					for(i=1;i<=cnt;i++){
						szContents+="CPP_PROJ=/O2 /Oi $(INC) /GL /D \"WIN32\" /D \"_WINDOWS\" /D \"N"+strVSOption+"\" /D \"MAKE_COMPONENT_DLL\" /D \"_USRDLL\" /D \"_WINDLL\" /D \"_AFXDLL\" ";
						szContents+="/D \"_"+strVSUnicode+"\" \\\n";
						szContents+="/D \""+strVSUnicode+"\" /FD /EHsc "+strMDOption+" /Gy /Fo\"$(INTDIR"+i+")\\\\\" /Fd\"$(INTDIR"+i+")\\\\\" /W0 /nologo /c /Zi /TP /errorReport:prompt\n\n";
						szContents+="{$(PROJECT"+i+")}.cpp{$(INTDIR"+i+")}.obj::\n";
						szContents+="	$(CPP) $(CPP_PROJ) $<\n\n";
					}
					
					szContents+="LINK32=link.exe\n";
					for(i=1;i<=cnt;i++){
						szContents+="LINK32_FLAGS"+i+"=/OUT:\"$(OUTDIR"+i+")\\$(TARGET"+i+")\" /INCREMENTAL:NO /NOLOGO /libpath:\"$(BOOST_LIB)\" /libpath:\"$(OPROS_LIB)\" \\\n";
						szContents+="/DLL /MANIFEST /"+strVSOption+" /SUBSYSTEM:WINDOWS /OPT:REF /OPT:ICF /LTCG /DYNAMICBASE /NXCOMPAT /MACHINE:X86 /ERRORREPORT:PROMPT OPRoSCDL.lib\n\n\n";
					}
					
	
					for(i=1;i<=cnt;i++){
						szContents+="LINK32_OBJS"+i+"= \\\n";
						szContents+="	\"$(INTDIR"+i+")/$(OBJ"+i+")\"\n\n\n";
					}
					for(i=1;i<=cnt;i++){
						szContents+="$(OUTDIR"+i+")/$(TARGET"+i+") : $(OUTDIR"+i+") $(DEF_FILE) $(LINK32_OBJS"+i+")\n";
						szContents+="	$(CPP) $(CPP_PROJ) $(SourceDir)\\$(PROJECT"+cnt+").cpp  $(SourceDir)\\$(PROJECT"+cnt+")_bl.c \\"+"\n";
						szContents+="	$(SourceDir)\\data_log.c $(SourceDir)\\ode_rk4.c"+"\n";
						szContents+="	$(LINK32) $(LINK32_FLAGS"+i+")$(INTDIR"+i+")/ode_rk4.obj $(INTDIR"+i+")/data_log.obj"+"\n";
					}
					szContents+="$(INTDIR"+i+")/$(OBJ2) $(LINK32_OBJS1)\"\n\n\n";
					if(info.getImplLanguage().equals("MSVC C++"))
					createVS2008prjFile(info);
					
				}
				else if(info.getImplLanguage().equals("MinGW C++")){
					//szContents+="OUTDIR=.\\Release\nINTDIR=.\\Release\nOutDir=.\\Release\n";
					szContents+="\nBOOST_INC="+strBoostIncPath;
					szContents+="\nBOOST_LIB="+strBoostLibPath;
					szContents+="\nOPROS_INC="+strOPRoSIncPath;
					szContents+="\nOPROS_LIB="+strOPRoSLibPath;
					szContents+="\n\nSO_TYPE= "+libType+" #shared object extensions\n";// PROJECT=\n";
					
					szContents+="INC = -I$(BOOST_INC)";
					szContents+=" -I$(OPROS_INC)";
					if(!strOtherIncPath.isEmpty()){
						StringTokenizer token=new StringTokenizer(strOtherIncPath,";");
						while(token.hasMoreTokens()){
							szContents+=" -I"+token.nextToken();
						}
					}
					szContents+="\n\n";
					szContents+="DEBUGCXXFLAGS=-g3 -Wall -c -fmessage-length=0 -mthreads -DMAKE_COMPONENT_DLL $(INC)\n";
					szContents+="RELEASECXXFLAGS=-O2 -Wall -c -fmessage-length=0 -mthreads -DMAKE_COMPONENT_DLL $(INC)\n\n";
					szContents+="LFLAGS= -L$(BOOST_LIB)";
					szContents+=" -L$(OPROS_LIB)\n\n";
					szContents+="LIBS= -lOPRoSCDL -llibboost_serialization-mgw34-mt\n\n";
					
	
					int cnt=0;
					String compName="";
					while(it.hasNext()){
						cnt++;
						compName=it.next();
						szContents+="\nSOURCE"+cnt+"="+compName+"/"+compName+".cpp";
						szContents+="\nDEBUGOBJS"+cnt+"="+compName+"/Debug/"+compName+".o";
						szContents+="\nRELEASEOBJS"+cnt+"="+compName+"/Release/"+compName+".o";
						szContents+="\nDEBUGTARGET"+cnt+"="+compName+"/Debug/"+compName+".$(SO_TYPE)";
						szContents+="\nRELEASETARGET"+cnt+"="+compName+"/Release/"+compName+".$(SO_TYPE)";
						
						szContents+="\n$(DEBUGOBJS"+cnt+"):";
						szContents+="\n	$(CXX) $(DEBUGCXXFLAGS) $(SOURCE"+cnt+") -o $(DEBUGOBJS"+cnt+")\n";
						szContents+="\n$(DEBUGTARGET"+cnt+"): $(DEBUGOBJS"+cnt+")";
						szContents+="\n	$(CXX) $(LFLAGS) -shared -WI,-soname,$@ -o $@ $(DEBUGOBJS"+cnt+") $(LIBS)\n";
						szContents+="\n$(RELEASEOBJS"+cnt+"):";
						szContents+="\n	$(CXX) $(RELEASECXXFLAGS) $(SOURCE"+cnt+") -o $(RELEASEOBJS"+cnt+")\n";
						szContents+="\n$(RELEASETARGET"+cnt+"): $(RELEASEOBJS"+cnt+")";
						szContents+="\n	$(CXX) $(LFLAGS) -shared -WI,-soname,$@ -o $@ $(RELEASEOBJS"+cnt+") $(LIBS)";
					}
					szContents+="\n\nall: ";
					for(int i=1;i<=cnt;i++){
						szContents+=" $(DEBUGTARGET"+i+") $(RELEASETARGET"+i+")";
					}
					szContents+="\n\ndefault:";
					for(int i=1;i<=cnt;i++){
						szContents+=" $(DEBUGTARGET"+i+")";
					}
					szContents+="\n\nclean:\n	rm -f";
					for(int j=1;j<=cnt;j++){
						szContents+=" $(DEBUGOBJS"+j+") $(DEBUGTARGET"+j+") $(RELEASEOBJS"+j+") $(RELEASETARGET"+j+")";
					}
				}
				else if(info.getImplLanguage().equals("MSVC C++")){
					szContents+="\nBOOST_INC="+strBoostIncPath;
					szContents+="\nBOOST_LIB="+strBoostLibPath;
					szContents+="\nOPROS_INC="+strOPRoSIncPath;
					szContents+="\nOPROS_LIB="+strOPRoSLibPath+"\n\n";
	
					int cnt=0;
					int i=1;
					String compName="";
					while(it.hasNext()){
						cnt++;
						compName=it.next();
						szContents+="PROJECT"+cnt+"="+compName+"\n";
						if(strVSOption.compareTo("RELEASE")==0){
							szContents+="OUTDIR"+cnt+"=.\\$(PROJECT"+cnt+")\\Release\n";
							szContents+="INTDIR"+cnt+"=.\\$(PROJECT"+cnt+")\\Release\n";
							szContents+="OutDir"+cnt+"=.\\$(PROJECT"+cnt+")\\Release\n";
						}else{
							szContents+="OUTDIR"+cnt+"=.\\$(PROJECT"+cnt+")\\Debug\n";
							szContents+="INTDIR"+cnt+"=.\\$(PROJECT"+cnt+")\\Debug\n";
							szContents+="OutDir"+cnt+"=.\\$(PROJECT"+cnt+")\\Debug\n";
						}
						szContents+="TARGET"+cnt+"=$(PROJECT"+cnt+")."+libType+"\n";
						szContents+="OBJ"+cnt+"=$(PROJECT"+cnt+").obj\n\n";
					}
					szContents+="ALL:";
					for(i=1;i<=cnt;i++){
						szContents+="	$(OUTDIR"+i+")\\$(TARGET"+i+")";
					}
					szContents+="\n\nCLEAN:\n";
					for(i=1;i<=cnt;i++){
						szContents+="	-@erase \"$(OUTDIR"+i+")\\$(TARGET"+i+")\"\n";
						szContents+="	-@erase \"$(OUTDIR"+i+")\\$(OBJ"+i+")\"\n";
						szContents+="	-@erase \"$(OUTDIR"+i+")\\$(PROJECT"+i+").lib\"\n";
					}
					for(i=1;i<=cnt;i++){
						szContents+="\n$(OUTDIR"+i+") :\n	if not exist \"$(OUTDIR"+i+")/$(NULL)\" mkdir \"$(OUTDIR"+i+")\"\n";
					}
	
					szContents+="\nINC = /I \"$(BOOST_INC)\"";
					szContents+=" /I \"$(OPROS_INC)\"";
					if(!strOtherIncPath.isEmpty()){
						StringTokenizer token=new StringTokenizer(strOtherIncPath,";");
						while(token.hasMoreTokens()){
							szContents+=" -I"+token.nextToken();
						}
					}
					szContents+="\n\n";
					szContents+="CPP=cl.exe\n\n";
					for(i=1;i<=cnt;i++){
						szContents+="CPP_PROJ=/O2 /Oi $(INC) /GL /D \"WIN32\" /D \"_WINDOWS\" /D \"N"+strVSOption+"\" /D \"MAKE_COMPONENT_DLL\" /D \"_USRDLL\" /D \"_WINDLL\" /D \"_AFXDLL\" ";
						szContents+="/D \"_"+strVSUnicode+"\" \\\n";
						szContents+="/D \""+strVSUnicode+"\" /FD /EHsc "+strMDOption+" /Gy /Fo\"$(INTDIR"+i+")\\\\\" /Fd\"$(INTDIR"+i+")\\\\\" /W0 /nologo /c /Zi /TP /errorReport:prompt\n\n";
						szContents+="{$(PROJECT"+i+")}.cpp{$(INTDIR"+i+")}.obj::\n";
						szContents+="	$(CPP) $(CPP_PROJ) $<\n\n";
					}
					
					szContents+="LINK32=link.exe\n";
					for(i=1;i<=cnt;i++){
						szContents+="LINK32_FLAGS"+i+"=/OUT:\"$(OUTDIR"+i+")\\$(TARGET"+i+")\" /INCREMENTAL:NO /NOLOGO /libpath:\"$(BOOST_LIB)\" /libpath:\"$(OPROS_LIB)\" \\\n";
						szContents+="/DLL /MANIFEST /"+strVSOption+" /SUBSYSTEM:WINDOWS /OPT:REF /OPT:ICF /LTCG /DYNAMICBASE /NXCOMPAT /MACHINE:X86 /ERRORREPORT:PROMPT OPRoSCDL.lib\n\n\n";
					}
					
	
					for(i=1;i<=cnt;i++){
						szContents+="LINK32_OBJS"+i+"= \\\n";
						szContents+="	\"$(INTDIR"+i+")/$(OBJ"+i+")\"\n\n\n";
					}
					for(i=1;i<=cnt;i++){
						szContents+="$(OUTDIR"+i+")/$(TARGET"+i+") : $(OUTDIR"+i+") $(DEF_FILE) $(LINK32_OBJS"+i+")\n";
						szContents+="	$(LINK32) $(LINK32_FLAGS"+i+") $(LINK32_OBJS"+i+")\n";
					}
					createVS2008prjFile(info);
				}
				try {
					outStream.write(szContents.getBytes());
					outStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
