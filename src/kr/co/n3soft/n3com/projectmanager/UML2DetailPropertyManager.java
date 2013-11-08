package kr.co.n3soft.n3com.projectmanager;

import kr.co.n3soft.n3com.lang.N3Messages;
import kr.co.n3soft.n3com.model.comm.FinalPackageModel;
import kr.co.n3soft.n3com.model.comm.LineModel;
import kr.co.n3soft.n3com.model.comm.UMLElementModel;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.comm.descriptor.AttrPropertyDescriptor;
import kr.co.n3soft.n3com.model.comm.descriptor.ConfigureTimeLinePropertyDescriptor;
import kr.co.n3soft.n3com.model.comm.descriptor.DescriptionPropertyDescriptor;
import kr.co.n3soft.n3com.model.comm.descriptor.DetailPropertyDescriptor;
import kr.co.n3soft.n3com.model.comm.descriptor.FontPropertyDescriptor;
import kr.co.n3soft.n3com.model.comm.descriptor.OperPropertyDescriptor;
import kr.co.n3soft.n3com.model.usecase.CollaborationModel;
import kr.co.n3soft.n3com.model.usecase.FinalActorModel;
import kr.co.n3soft.n3com.model.usecase.FinalBoundryModel;
import kr.co.n3soft.n3com.model.usecase.UseCaseModel;

import org.eclipse.gef.examples.logicdesigner.figures.LogicColorConstants;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.ui.views.properties.ColorPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

public class UML2DetailPropertyManager {
	protected IPropertyDescriptor[] descriptors = null;

	  public static String ID_ANGLE = "angle"; //$NON-NLS-1$
	    public static String ID_SIZE = "size"; //$NON-NLS-1$
	    public static String ID_LOCATION = "location"; //$NON-NLS-1$
	    public static String ID_NAME = "name"; //$NON-NLS-1$
	    public static String ID_COLOR = "backgroundcolor"; //$NON-NLS-1$
	    public static String ID_DESCRIPTION = "description"; //$NON-NLS-1$
	    public static String ID_FONT = "font"; //$NON-NLS-1$
	    //	uMLDataModel = new UMLDataModel();
	    protected String stereotype = "";
	    protected String name = "";
	    protected String description = "";
	    protected Color backGroundColor = LogicColorConstants.defaultFillColor;
	    protected Font font = new Font(null, new FontData());
	    public static String ID_ATTRIBUTE = "attribute";
	    public static String ID_OPERATION = "operation";
	    public static String ID_STEREOTYPE = "stereotype";
	    public static String ID_CONFIGURE_TIMELINE = "configureTimeline";
	    
	    public static String ID_DETAIL = "detailProperty";
	    
	    public static String ID_CHANGE_DRAG = "changeDrag";
	    public static String ID_CHANGE_PROPERTY = "changeProperty";
	    
	    public static String ID_CHANGE_GROUP = "changeGroup";
	    public static String ID_CONFIGURE_NUM = "configureNum";
	    private java.util.ArrayList updateListener = new java.util.ArrayList();
	    //	static{
	    public static PropertyDescriptor nameProp = new TextPropertyDescriptor(ID_NAME, N3Messages.POPUP_NAME);//2008040302 PKY S 
	    public static PropertyDescriptor streoProp = new TextPropertyDescriptor(ID_STEREOTYPE, N3Messages.POPUP_STEREOTYPE);//2008040302 PKY S 
	    public static ColorPropertyDescriptor colorPropertyDescriptor = new ColorPropertyDescriptor(ID_COLOR,  N3Messages.POPUP_BACKGROUND_COLOR);//2008040302 PKY S 
	    public static DescriptionPropertyDescriptor descriptionPropertyDescriptor =
	        new DescriptionPropertyDescriptor(ID_DESCRIPTION, (N3Messages.POPUP_DESCRIPTION));//2008040302 PKY S ;
	    public static FontPropertyDescriptor fontPropertyDescriptor = new FontPropertyDescriptor(ID_FONT, "폰트");
	    public static PropertyDescriptor attrProp = new AttrPropertyDescriptor(ID_ATTRIBUTE, N3Messages.POPUP_ATTRIBUTES);//2008040302 PKY S 
	    public static PropertyDescriptor configureTimeLineProp = new ConfigureTimeLinePropertyDescriptor(ID_CONFIGURE_TIMELINE, "ConfigureTimeLine");
	    public static PropertyDescriptor DetailPropertyDescriptorProp = new DetailPropertyDescriptor(ID_DETAIL, "Detail");
	    public static PropertyDescriptor operProp = new OperPropertyDescriptor(ID_OPERATION, N3Messages.POPUP_OPERATION);//2008040302 PKY S );
	    public static PropertyDescriptor colorProp = colorPropertyDescriptor;
	private static UML2DetailPropertyManager instance;
	 public static UML2DetailPropertyManager getInstance() {
	        if (instance == null) {
	            instance = new UML2DetailPropertyManager();
	            return instance;
	        }
	        else {
	            return instance;
	        }
	    }
	 
	 public IPropertyDescriptor[] getCreateProperty(UMLElementModel em){
		 //공통
		 //설명
		 //이름
		 //폰트/view
		 //색깔/view
		 //사이즈/view
		 //스테레오타입
		 //제약사항
		 //파일첨부,웹첨부
		 //Multiplicity
		 //속성
		 //오퍼레이션
		 //유즈케이스,액티비티,클래스,패키지,컬레보레이션
		 	//ADD 다이어그램
		 
		 //가시성
		 //태그 value
		 
		 if(em instanceof UMLModel){
			 UMLModel um = (UMLModel)em;
			 if(em instanceof UseCaseModel){
				 //시나리오
				 //extension point
			 }
			 else if(em instanceof FinalActorModel){

			 }
			 else if(em instanceof FinalBoundryModel){

			 }
			 else if(em instanceof CollaborationModel){

			 }
			 else if(em instanceof FinalPackageModel){

			 }
			 //FinalActorModel
			 
		 }
		 else if(em instanceof LineModel){
			 //공통
			 
		 }
		 return descriptors;
		 
	 }

}
