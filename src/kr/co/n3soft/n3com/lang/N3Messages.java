package kr.co.n3soft.n3com.lang;

import org.eclipse.osgi.util.NLS;

public class N3Messages {

	static boolean isEng = true;

	/**
	 * POPUP
	 */
	public static String POPUP_EDIT;

	public static String POPUP_COPY;

	public static String POPUP_DELETE;

	public static String POPUP_PASTE;

	public static String POPUP_FIND;

	public static String POPUP_ADD;

	public static String POPUP_ADD_ATTRIBUTE;

	public static String POPUP_ADD_OPERATION;

	public static String POPUP_ADD_MESSAGE;

	public static String POPUP_ADD_PORT;

	public static String POPUP_ADD_ACTIVITY_PARAMETER;

	public static String POPUP_ADD_EXPANSION_NODE;

	public static String POPUP_ADD_OBJECT_NODE;

	public static String POPUP_ADD_EXIT_POINT;

	public static String POPUP_ADD_ENTRY_POINT;

	public static String POPUP_ADD_CONTAINER;

	public static String POPUP_ADD_DIAGRAM;

	public static String POPUP_SELECT_TYPE;

	public static String POPUP_EDIT_MESSAGE;

	public static String POPUP_SET_DEEP_HISTORY;

	public static String POPUP_SET_INTERFACE_MODE;

	public static String POPUP_SET_RULER_MODE;

	public static String POPUP_OPEN_DIAGRAM;

	public static String POPUP_ADD_ACTIVITY_PARTITION;

	public static String POPUP_ADD_ACTION_PIN;

	public static String POPUP_ADD_STATE_REGION;

	public static String POPUP_ADD_SEQ_MESSAGE;

	public static String POPUP_NEW_OPERATION;

	public static String POPUP_AGGREGATE;

	public static String POPUP_NONE;

	public static String POPUP_COMPOSITE;

	public static String POPUP_NAVIGABLE;

	public static String POPUP_ROLE;

	public static String POPUP_FIND_IN_DIAGRAMS;

	public static String POPUP_REMOVE;

	public static String POPUP_DESCRIPTION;

	public static String POPUP_NAME;

	public static String POPUP_PACKAGE;

	public static String POPUP_SCOPE;

	public static String POPUP_STEREOTYPE;

	public static String POPUP_PROPERTIES;

	public static String POPUP_ATTRIBUTES;

	public static String POPUP_OPERATION;

	public static String POPUP_SETELEMENTCLASSIFIER;
	
	public static String POPUP_TYPE;
	
	public static String POPUP_RETURNTYPE;

	public static String POPUP_RETURN;
	
	public static String POPUP_PACKAGE_ADD;
	
	public static String POPUP_DIAGRAME_ADD;
	
	public static String POPUP_MODEL_ADD;
	
	public static String POPUP_MODEL_DELETE;
	
	public static String POPUP_RENAME;
	
	public static String POPUP_PRINT;
	
	public static String POPUP_BACKGROUND_COLOR;
	
	public static String POPUP_DEFAULT_INITIAL_VALUE;
	
	public static String POPUP_LOAD;
	
	public static String POPUP_SAVE;
		//2008042901 PKY S 

	public static String POPUP_FILE;
	
	public static String POPUP_VIEW;
	
	public static String POPUP_HELP;
	
	public static String POPUP_DIRECT_LINE; //PKY 08051401 S 라인 꺽인것 바로 직선으로 만들기
	
	public static String POPUP_MODEL_INCLUSION;//PKY 08052101 S 컨테이너에서 그룹으로 변경
	
	public static String POPUP_MODEL_INCLUSION_CENCEL;//PKY 08052101 S 컨테이너에서 그룹으로 변경
	
	public static String POPUP_JAVA;
	
	public static String POPUP_GENERATE_CODE;
	
	//2008042901 PKY S
	public static String DIALOG_STRUCTURAL;
	
	public static String DIALOG_BEHAVIORAL;
	
	public static String DIALOG_OBJECT;
	
	public static String DIALOG_INTERACTION_OVERVIEW;
	
	public static String DIALOG_PACKAGE;
	
	public static String POPUP_Z_ORDER_BACK;
	
	public static String POPUP_Z_ORDER_FORWARD;
	
	public static String POPUP_Z_ODRDER;//PKY 08060201 S
	
	public static String POPUP_SAVE_AS;//PKY 08062601 S 단축키 정의

	public static String  POPUP_CLEAR;//PKY 08062601 S 콘솔 클리어할수있도록 수정
	
	public static String POPUP_EXCEL_SAVE; //PKY 08070101 S 팝업 메뉴 이미지 삽입
	
	public static String POPUP_EXCEL_LOAD; //PKY 08070101 S 팝업 메뉴 이미지 삽입
	
	public static String POPUP_REQUIRED_EXCEL; //V1.02 WJH E 080904 요구사항 추적표 추가

	public static String POPUP_SAVE_IMAGE; //20080718 KDI s
	
	//PKY 08072201 S오퍼레이션 어트리뷰트 아이콘 삽입
	public static String POPUP_CONFIGURE_TIMELINE;

	public static String POPUP_INSTANCE_CLASSIFIER;

	public static String POPUP_REQID;
	
	public static String POPUP_TRACING_IN_REQUIREMENT;
	
	public static String POPUP_NEW_PROJECT; //20080730 KDI s
	
	public static String POPUP_RECENT_OPEN_PROJECT;//PKY 08080501 S NewPorject에 불러온 프로젝트 리스트도 보여주도록 하여 사용자가 편의성 개선
	
	public static String POPUP_ROSE_LOAD;//PKY 08090201 S Rose Load를 Rose 2003 Load로 변경
	
	public static String POPUP_REMOVE_ROLE;//PKY 08090804 S 
	
	/**
	 * MODEL
	 */
	public static String MODEL_ACTOR;

	public static String MODEL_USECASE;

	
	
	/**
	 * Dialog
	 */
	public static String DIALOG_SETELEMENTCLASSIFIER;
	
	public static String DIALOG_MODELTYPE;
	
	public static String DIALOG_DIAGRAMTYPE;
	
	public static String DIALOG_TEXT_TO_FIND;
	
	public static String DIALOG_CASE_SENSITIVE;
	
	public static String DIALOG_EXACT_MATCH;
	
	public static String DIALOG_IN_DESCRIPTION;
	
	public static String DIALOG_IN_MODEL;
	
	public static String DIALOG_IN_DIAGARAM;
	
	public static String DIALOG_PARAMETERS;
	
	public static String DIALOG_MODELBROWSER_DROP_MISS; //PKY 08062601 S 모델 브라우져 이동 시 패키지 자체가 사라지는 문제 발생 문제 수정
	
	public static String DIALOG_CONSTRAINT;//PKY 08070701 S

	
	//2008043001 PKY S
	
	public static String DIALOG_MULTIPLICITY;
	
	public static String DIALOG_IS_ACTIVE;
	
	public static String DIALOG_IMAGE;
	
	public static String DIALOG_PRECONDITION;
	
	public static String DIALOG_POSTCONDITION;
	
	public static String DIALOG_IS_PARAMETER_NAME;
	
	public static String DIALOG_IS_SINGLEEXECUTION;
	
	public static String DIALOG_INSTANCE_CLASSIFIER;
	
	public static String DIALOG_DERIVED;//PKY 08060201
	
	public static String DIALOG_CONST; //PKY 08060201 S
	
	public static String DIALOG_EXTENSIONPOINT;//PKY 08060201 S
	
	public static String DIALOG_TAG;//PKY 08060201 S
	
	public static String DIALOG_VALUE;//PKY 08060201 S
	
	public static String DIALOG_SPECIFICATION;//PKY 08060201 S
	
	public static String DIALOG_OPTION;//PKY 08060201 S
	
	public static String OPEN_N3PROPERTY;
	
	public static String DIALOG_SET_OBJECT_STATE; //PKY 08061101 S
	
	//PKY 08061801 S
	public static String DIALOG_CONTROL_FLOW_TYPE;
	
	public static String DIALOG_SIGNATURE;
	
	public static String DIALOG_CONDITION;
	
	public static String DIALOG_SEQUENCE_EXPRESSION;
	
	public static String DIALOG_RETURN;
	
	public static String DIALOG_LIFECYCLE; 
	
	public static String DIALOG_MESSAGE; 
	
	public static String DIALOG_STATE;
	
	public static String DIALOG_TIME;
	
	public static String DIALOG_EVENT;
	
	public static String DIALOG_DURATION_CONSTRAINT;
	
	public static String DIALOG_TIME_CONSTRAINT;
	
	public static String DIALOG_TRANSITION_POINTS;
	
	public static String DIALOG_ADD_STATE;
	
	public static String DIALOG_ADD_TRANSITIONPOINT;
	
	public static String DIALOG_DELETE_TRANSITIONPOINT;
	
	public static String DIALOG_DELETE_STATE;
	
	public static String DIALOG_STATES;
	
	public static String DIALOG_SCOPE_NULL_MESSAGE; //PKY 08070901 S Combox에 text 입력시 에러발생문제 수정
	
	//PKY 08071601 S 다이얼로그 UI작업
	public static String DIALOG_DELETE_MESSAGE;

	public static String DIALOG_NEW_PROJECT_MESSAGE;
	
	public static String DIALOG_COLOR_DEFAULT_MESSAGE;
	
	public static String DIALOG_OPEN_PROJECT_MESSAGE;//PKY 08072401 S 프로젝트 불러올 시에 타 도구와 같은 형식 메시지를 제공하도록 수정
	
	public static String DIALOG_OPEN_PROJECT_NOT_FILE_EXCEPTION;//PKY 08072401 S Open시 프로젝트 파일이 존재하지 않을경우 에러 발생문제 수정
	
	public static String DIALOG_OPEN_PROJECT_LIST_DELETE;//PKY 08072401 S Open시 프로젝트 파일이 존재하지 않을경우 에러 발생문제 수정

	public static String DIALOG_OPEN;//PKY 08080501 S Image Dialog 이미지 리스트 및 불러오기 편하도록 개선
	
	public static String DIALOG_IMG_PREVIEW;//PKY 08080501 S Image Dialog 이미지 리스트 및 불러오기 편하도록 개선
	
	public static String DIALOG_FILE_URL;//PKY 08080501 S Image Dialog 이미지 리스트 및 불러오기 편하도록 개선
	
	public static String DIALOG_FILE_LIST;//PKY 08080501 S Image Dialog 이미지 리스트 및 불러오기 편하도록 개선

	public static String DIALOG_FILE_NOT_LOADING;//PKY 08080501 S Image Dialog 이미지 리스트 및 불러오기 편하도록 개선
	
	public static String DIALOG_TEAM_FOLDER;//PKY 08082201 S 팀 프로젝트 경로를 프로젝트에서 변경가능하도록
	
	public static String DIALOG_ADD_TEAM_FOLDER_WARNING;//PKY 08082201 S 팀 프로젝트 경로를 프로젝트에서 변경가능하도록
	
	public static String DIALOG_OPEN_TEAM_NOT_FILE_EXCEPTION;//PKY 08082201 S 팀 프로젝트 경로를 프로젝트에서 변경가능하도록

	//PKY 08071601 E 다이얼로그 UI작업
	
	//20080718 KDI s
	public static String DIALOG_SOURCEROLE;
	
	public static String DIALOG_TARGETROLE;
	
	public static String DIALOG_ROLE;
	
	public static String DIALOG_ROLENOTE;
	
	public static String DIALOG_CONSTRAINTS;
	
	public static String DIALOG_QUALIFIERS;
	
	public static String DIALOG_DERIVED_UNION;
	
	public static String DIALOG_OWNED;
	
	public static String DIALOG_ORDERED;
	
	public static String DIALOG_ALLOW_DUPLICATES;
	
	public static String DIALOG_AGGREGATION;
	
	public static String DIALOG_NAVIGABILITY;
	//20080718 KDI e
	//PKY 08072201 S오퍼레이션 어트리뷰트 아이콘 삽입
	public static String DIALOG_NEW_ACTION;
	
	public static String DIALOG_NEW_STCUTUREACTIVITY;
	
	public static String DIALOG_MESSAGE_PROPERTIEST;
	//PKY 08072201 E오퍼레이션 어트리뷰트 아이콘 삽입

	public static String DIALOG_EXTENDED;
	
	//20080723 KDI s
	public static String DIALOG_TAB_CONSTRAINTS;
	
	public static String DIALOG_GUARD;
	
	public static String DIALOG_WEIGHT;
	
	public static String DIALOG_EFFECT;
	
	public static String DIALOG_TAB_SCENARIO;
	
	public static String DIALOG_TYPE;
	
	public static String DIALOG_NAME;
	
	public static String DIALOG_INTERACTION_OPRANDS;
	
	public static String DIALOG_PATTERN;
	
	public static String DIALOG_CONFIGURE;
	
	public static String DIALOG_SYNCH;
	
	public static String DIALOG_KIND;	
	//20080723 KDI e
	
	public static String DIALOG_SELECT_BEHAVIOR; //20080725 KDI s
	
	public static String DIALOG_PROJECT_NAME; //20080730 KDI s
	
	public static String DIALOG_IS_REFERENCE;//PKY 08080501 S Part에 프로퍼티 속성이 잘못들어간것
	
	public static String DIALOG_ADD_REQ_ID;//PKY 08080501 S RequirementID를 다이얼로그 리스트로 보여주도록 
	
	public static String DIALOG_REQ_ID_LIST;//PKY 08080501 S RequirementID를 다이얼로그 리스트로 보여주도록
	
	public static String DIALOG_STANDARD_COLORS;//PKY 08080501 S RequirementID를 다이얼로그 리스트로 보여주도록
	
	public static String DIALOG_STANDARD_COLORS_ELEMENT_FILL;//PKY 08080501 S RequirementID를 다이얼로그 리스트로 보여주도록
	
	//PKY 08081101 S RequirementID 여러 개 넣을 수 있도록 개선
	public static String DIALOG_ADD_REQUIREMENT_ID_ERROR;

	public static String DIALOG_DELETE_ACTOR_IMAGE;
	//PKY 08081101 E RequirementID 여러 개 넣을 수 있도록 개선
	
	public static String DIALOG_TIME_MAX;//PKY 08081101 S Timing 최대 해당 모델 Max 이상 시작점을 입력할 경우 라인이 에러
	
	public static String DIALOG_SHOW_ATTR;//PKY 08081801 S 오퍼레이션 어트리뷰트 숨길수 있는 기능

	public static String DIALOG_SHOW_OPER;//PKY 08081801 S 오퍼레이션 어트리뷰트 숨길수 있는 기능
	
	public static String DIALOG_SAVE_MESSAGE;		// V1.02 WJH E 080822 추가

	public static String DIALOG_SAME_NAME_CONFLICT_MESSAGE; //KDI 080908 0002
	
	/**
	 * PALETTE
	 */

	public static String PALETTE_TITLE_USECASE;

	public static String PALETTE_TITLE_CLASS;

	public static String PALETTE_TITLE_COMPOSITE;

	public static String PALETTE_TITLE_COMMUNICATION;

	public static String PALETTE_TITLE_INTERACTION;

	public static String PALETTE_TITLE_TIMING;

	public static String PALETTE_TITLE_ACTIVITY;

	public static String PALETTE_TITLE_COMPONENT;

	public static String PALETTE_TITLE_DEPLOYMENT;

	public static String PALETTE_TITLE_STATE;

	public static String PALETTE_TITLE_NOTE;

	public static String PALETTE_ACTOR;

	public static String PALETTE_BOUNDARY;

	public static String PALETTE_COLLABORATION;

	public static String PALETTE_PACKAGE;

	public static String PALETTE_ASSOCIATE;

	public static String PALETTE_EXTEND;

	public static String PALETTE_INCLUDE;

	public static String PALETTE_GENERALIZATION;

	public static String PALETTE_CLASS;

	public static String PALETTE_INTERFACE;

	public static String PALETTE_ENUMERATION;

	public static String PALETTE_DEPENDENCY;

	public static String PALETTE_IMPORT;

	public static String PALETTE_MERGE;

	public static String PALETTE_USECASE;

	public static String PALETTE_REALIZE;

	public static String PALETTE_EXCEPTION;

	public static String PALETTE_ACTIVITY;

	public static String PALETTE_ACTION;

	public static String PALETTE_SEND;

	public static String PALETTE_RECEIVE;

	public static String PALETTE_INITIAL;

	public static String PALETTE_FINAL;

	public static String PALETTE_FLOWFINAL;

	public static String PALETTE_SYNCH;

	public static String PALETTE_DECISION;

	public static String PALETTE_OBJECT;

	public static String PALETTE_CENTRALBUFFERNODE;

	public static String PALETTE_DATASTORE;

	public static String PALETTE_SWIMLANE;

	public static String PALETTE_FORK_JOIN;

	public static String PALETTE_STCUTUREACTIVITY;

	public static String PALETTE_CONTROLFLOW;

	public static String PALETTE_OBJECTFLOW;

	public static String PALETTE_PART;

	public static String PALETTE_REQUIREDINTERFACE;

	public static String PALETTE_PROVIDEDINTERFACE;

	public static String PALETTE_CONNECTOR;

	public static String PALETTE_DELEGATE;

	public static String PALETTE_REPRESENTS;

	public static String PALETTE_OCCURRENCE;

	public static String PALETTE_ROLEBINDING;

	public static String PALETTE_LIFELINE;

	public static String PALETTE_FRAGMENT;

	public static String PALETTE_ENDPOINT;

	public static String PALETTE_MESSAGE;

	public static String PALETTE_SELFMESSAGE;

	public static String PALETTE_COMPONENT;

	public static String PALETTE_ARTIFACT;

	public static String PALETTE_STATE;

	public static String PALETTE_SUBMACHINESTATE;

	public static String PALETTE_HISTORY;

	public static String PALETTE_TERMINATE;

	public static String PALETTE_JUNCTION;

	public static String PALETTE_ENTRYPOINT;

	public static String PALETTE_EXIT;

	public static String PALETTE_CHOICE;

	public static String PALETTE_TRANSITION;

	public static String PALETTE_NODE;

	public static String PALETTE_DEVICE;

	public static String PALETTE_EXECUTIONENVIRONMENT;

	public static String PALETTE_DEPLOYMENTSPECIFICATION;

	public static String PALETTE_MANIFESTION;

	public static String PALETTE_COMMUNICATIONPATH;

	public static String PALETTE_DEPLOYMENT;

	public static String PALETTE_STATELIFELINE;

	public static String PALETTE_GROUP;

	public static String PALETTE_NOTE;

	public static String PALETTE_NOTELINK;
	
	public static String PALETTE_REQUIRMENT;
	
	/**
	 * Pattern
	 */
	public static String PATTERN_EJB;
	
	public static String PATTERN_ENTITY_EJB;
	
	public static String PATTERN_GOF;
	
	public static String PATTERN_ABSTRACT_FACTORY;
	
	public static String PATTERN_ADAPTER;
	
	public static String PATTERN_BRIDGE;
	
	public static String PATTERN_BUILDER;
	
	public static String PATTERN_CHAIN_OF_REPONSIBILITY;
	
	public static String MENU_APPLY_PATTERN;
	
	public static String PATTERN_COMMAND;
	
	public static String PATTERN_COMPOSITE;
	
	public static String PATTERN_DECORATOR;
	
	public static String PATTERN_FACADE;
	
	public static String PATTERN_FACTORY_METHOD;
	
	public static String PATTERN_FLYWEIGHT;
	
	public static String PATTERN_INTERPRETER;
	
	public static String PATTERN_ITERATOR;
	
	public static String PATTERN_MEDIATOR;
	
	public static String PATTERN_MEMENTO;
	
	public static String PATTERN_OBSERVER;
	
	public static String PATTERN_PROTOTYPE;
	
	public static String PATTERN_PROXY;
	
	public static String PATTERN_SINGLETON;
	
	public static String PATTERN_STATE;
	
	public static String PATTERN_STRATEGY;
	
	public static String PATTERN_TEMPLATE_METHOD;
	
	public static String PATTERN_VISITOR;
	
	public static String PATTERN_MESSAGEDRIVEN_EJB;
	
	public static String PATTERN_SESSION_EJB;
	
	
	/**
	 * Report
	 */
	public static String REPORT_GENERATE; //20080711 KDI s
	
	public static String REPORT_PREVIEW; //20080616 KDI s
	
	public static String REPORT_USE_TEMPLATE; //20080618 KDI s
	
	public static String REPORT_SCRIPT_NAME; //20080618 KDI s
	
	public static String REPORT_FILE_DATE; //20080618 KDI s
	
	public static String REPORT_GENERATOR; //20080711 KDI s
	
	public static String REPORT_SCRIPT_WIZARD; //20080711 KDI s
	
	public static String REPORT_GENERATE_MENU; //20080711 KDI s
	
//	public static String DIALOG_REPORT_SCRIPT_PATH; //20080618 KDI s
	
	//20080903 KDI s
	public static String REPORT_METHODOLOGICAL_TYPE;
	
	public static String REPORT_ADDME;
	
	public static String REPORT_MARMI;
	
	public static String REPORT_PRODUCT_TYPE;
	
	public static String REPORT_SELECT_USE_TEMPLATE;
	
	public static String REPORT_WARNING;
	
	public static String REPORT_OK;
	
	public static String REPORT_TABLE_SET;
	
	public static String REPORT_TABLE_CELL_COUNT_SET;
	
	public static String REPORT_TABLE_CELL_ROW;
	
	public static String REPORT_TABLE_CELL_COLUMN;
	
	public static String REPORT_TABLE_PROPERTY_SET;
	
	public static String REPORT_MODELBROWSER_SET;
	
	public static String REPORT_WORD_DOC_SET;
	
	public static String REPORT_SELECTALL;
	
	public static String REPORT_TABLE_NAME;
	
	public static String REPORT_TABLE_WORD_TYPE;
	
	public static String REPORT_TABLE_WORD_OPTION;
	
	public static String REPORT_TABLE_SEARCH_MODEL;
	
	public static String REPORT_TABLE_MODEL_PROPERTY;
	
	public static String REPORT_TABLE_HEADER_FOOTER_SET;
	
	public static String REPORT_TABLE_HEADER;
	
	public static String REPORT_TABLE_FOOTER;
	
	public static String REPORT_MESSAGES_SELECTED_TEMPLATE_FILE;
	

	
	
	
	public static String REPORT_TYPE_BUSSINESS_DEFINE;	
	public static String REPORT_TYPE_ADDME_BUSSINESS_DEFINE;
	public static String REPORT_TYPE_ADDME_USECASE_SPECIFICATION;
	public static String REPORT_TYPE_ADDME_CLASS_SPECIFICATION;
	public static String REPORT_TYPE_ADDME_COMPONENT_ARCHITECTURE_DEFINE;
	public static String REPORT_TYPE_ADDME_INTERFACE_INTERACTION_SPECIFICATION; 
	public static String REPORT_TYPE_ADDME_INTERFACE_SPECIFICATION;
	public static String REPORT_TYPE_ADDME_COMPONENT_SPECIFICATION;
	public static String REPORT_TYPE_ADDME_COMPONENT_DESIGN;

	public static String REPORT_TYPE_MARMI_OBJECTMODEL_TECHNIQUE;
	public static String REPORT_TYPE_MARMI_BUSINESS_PROCESS_ACTIVITYMODEL_TECHINIQUE;
	public static String REPORT_TYPE_MARMI_USECASEMODEL_TECHINIQUE;
	public static String REPORT_TYPE_MARMI_COMPONENT_SPECIFICATION;
	public static String REPORT_TYPE_MARMI_COMPONENT_DESIGN;
	
	
	
	public static String REPORT_WORD_TABLE_ADDME_ACTIVITY_PACKAGE_NAME;
	public static String REPORT_WORD_TABLE_ADDME_ACTIVITY_DIAGRAM_NAME;
	public static String REPORT_WORD_TABLE_ADDME_ACTIVITY_IMAGES;
	public static String REPORT_WORD_TABLE_ADDME_ACTIVITY_ACTIVITYLIST;
	public static String REPORT_WORD_TABLE_ADDME_ACTIVITY_ACTIVITYTABLE;

	public static String REPORT_WORD_TABLE_ADDME_USECASE_PACKAGE_NAME;
	public static String REPORT_WORD_TABLE_ADDME_USECASE_SYSTEM_OUTLINE;
	public static String REPORT_WORD_TABLE_ADDME_USECASE_ACTOR_USECASE_DEFINE;
	public static String REPORT_WORD_TABLE_ADDME_USECASE_ACTOR_LIST;
	public static String REPORT_WORD_TABLE_ADDME_USECASE_ACTOR_TABLE;
	public static String REPORT_WORD_TABLE_ADDME_USECASE_USECASE_DEFINE ;
	public static String REPORT_WORD_TABLE_ADDME_USECASE_USECASE_TABLE;
	public static String REPORT_WORD_TABLE_ADDME_USECASE_USECASE_DIAGRAM ;
	public static String REPORT_WORD_TABLE_ADDME_USECASE_DIAGRAM_NAME;
	public static String REPORT_WORD_TABLE_ADDME_USECASE_IMAGES;
	public static String REPORT_WORD_TABLE_ADDME_USECASE_USECASE_DESCRIPTION;
	public static String REPORT_WORD_TABLE_ADDME_USECASE_USECASE_NAME;
	public static String REPORT_WORD_TABLE_ADDME_USECASE_INITIATING_ACTOR_NAME;
	public static String REPORT_WORD_TABLE_ADDME_USECASE_USECASE_OUTLINE;
	public static String REPORT_WORD_TABLE_ADDME_USECASE_USECASE_EVENT_FLOW;
	public static String REPORT_WORD_TABLE_ADDME_USECASE_USECASE_BASIC_FLOW;
	public static String REPORT_WORD_TABLE_ADDME_USECASE_USECASE_ALTERNATIVE_FLOW;
	public static String REPORT_WORD_TABLE_ADDME_USECASE_USECASE_EXCEPTION_FLOW;
	public static String REPORT_WORD_TABLE_ADDME_USECASE_USECASE_PRE_POST_CONDITION;
	public static String REPORT_WORD_TABLE_ADDME_USECASE_USECASE_VARIABLE ;
	public static String REPORT_WORD_TABLE_ADDME_USECASE_USECASE_ADDITIONAL_FACT;
	public static String REPORT_WORD_TABLE_ADDME_USECASE_USECASE_SCENARIO;
	public static String REPORT_WORD_TABLE_ADDME_USECASE_CONNECTION_INFORMATION;

	public static String REPORT_WORD_TABLE_ADDME_CLASS_PACKAGE_NAME;
	public static String REPORT_WORD_TABLE_ADDME_CLASS_CLASS_DIAGRAM;
	public static String REPORT_WORD_TABLE_ADDME_CLASS_DIAGRAM_NAME;
	public static String REPORT_WORD_TABLE_ADDME_CLASS_IMAGE;
	public static String REPORT_WORD_TABLE_ADDME_CLASS_CLASS_DEFINE;
	public static String REPORT_WORD_TABLE_ADDME_CLASS_CLASS_NAME;
	public static String REPORT_WORD_TABLE_ADDME_CLASS_CLASS_OUTLINE;
	public static String REPORT_WORD_TABLE_ADDME_CLASS_CLASS_ATTRIBUTE;
	public static String REPORT_WORD_TABLE_ADDME_CLASS_CLASS_ATTRIBUTE_TABLE ;
	public static String REPORT_WORD_TABLE_ADDME_CLASS_CLASS_OPERATION;
	public static String REPORT_WORD_TABLE_ADDME_CLASS_CLASS_OPERATION_TABLE;

	public static String REPORT_WORD_TABLE_ADDME_COMPONENT_ARCHITECTURE_PACKAGE_NAME;
	public static String REPORT_WORD_TABLE_ADDME_COMPONENT_ARCHITECTURE_COMPONENT_ARCHITECTURE_DIAGRAM;
	public static String REPORT_WORD_TABLE_ADDME_COMPONENT_ARCHITECTURE_IMAGE;
	public static String REPORT_WORD_TABLE_ADDME_COMPONENT_ARCHITECTURE_COMPONENT_DESCRIPTION;
	public static String REPORT_WORD_TABLE_ADDME_COMPONENT_ARCHITECTURE_COMPONENT_IDENTIFICATION;
	public static String REPORT_WORD_TABLE_ADDME_COMPONENT_ARCHITECTURE_ARCHITECTURE_PATTERN;

	public static String REPORT_WORD_TABLE_ADDME_INTERFACE_INTERACTION_SPECIFICATION_PACKAGE_NAME;
	public static String REPORT_WORD_TABLE_ADDME_INTERFACE_INTERACTION_SPECIFICATION_SEQUENCE_DIAGRAM_NAME;
	public static String REPORT_WORD_TABLE_ADDME_INTERFACE_INTERACTION_SPECIFICATION_IMAGE;

	public static String REPORT_WORD_TABLE_ADDME_INTERFACE_SPECIFICATION_PACKAGE_NAME;
	public static String REPORT_WORD_TABLE_ADDME_INTERFACE_SPECIFICATION_INTERFACE_DEFINE;
	public static String REPORT_WORD_TABLE_ADDME_INTERFACE_SPECIFICATION_INNER_CLASS_DIAGRAM_NAME;
	public static String REPORT_WORD_TABLE_ADDME_INTERFACE_SPECIFICATION_INTERFACE_NAME;
	public static String REPORT_WORD_TABLE_ADDME_INTERFACE_SPECIFICATION_INTERFACE_TABLE;
	public static String REPORT_WORD_TABLE_ADDME_INTERFACE_SPECIFICATION_INTERFACE_INFORMATION_MODEL;
	public static String REPORT_WORD_TABLE_ADDME_INTERFACE_SPECIFICATION_IMAGE;

	public static String REPORT_WORD_TABLE_ADDME_COMPONENT_SPECIFICATION_COMPONENT_SPECIFICATION;
	public static String REPORT_WORD_TABLE_ADDME_COMPONENT_SPECIFICATION_PACKAGE_NAME;
	public static String REPORT_WORD_TABLE_ADDME_COMPONENT_SPECIFICATION_COMPONENT_NAME;
	public static String REPORT_WORD_TABLE_ADDME_COMPONENT_SPECIFICATION_COMPONENT_TABLE;

	public static String REPORT_WORD_TABLE_ADDME_COMPONENT_DESIGN_COMPONENT_DESIGN;
	public static String REPORT_WORD_TABLE_ADDME_COMPONENT_DESIGN_PACKAGE_NAME;
	public static String REPORT_WORD_TABLE_ADDME_COMPONENT_DESIGN_COMPONENT_NAME ;
	public static String REPORT_WORD_TABLE_ADDME_COMPONENT_DESIGN_INNER_CLASS_DIAGRAM;
	public static String REPORT_WORD_TABLE_ADDME_COMPONENT_DESIGN_INNER_CLASS_DIAGRAM_NAME;
	public static String REPORT_WORD_TABLE_ADDME_COMPONENT_DESIGN_IMAGE;
	public static String REPORT_WORD_TABLE_ADDME_COMPONENT_DESIGN_INNER_CLASS;
	public static String REPORT_WORD_TABLE_ADDME_COMPONENT_DESIGN_INNER_CLASS_NAME;
	public static String REPORT_WORD_TABLE_ADDME_COMPONENT_DESIGN_INNER_CLASS_OUTLINE;
	public static String REPORT_WORD_TABLE_ADDME_COMPONENT_DESIGN_INNER_CLASS_ATTRIBUTE;
	public static String REPORT_WORD_TABLE_ADDME_COMPONENT_DESIGN_INNER_CLASS_ATTRIBUTE_TABLE;
	public static String REPORT_WORD_TABLE_ADDME_COMPONENT_DESIGN_INNER_CLASS_OPERATION;
	public static String REPORT_WORD_TABLE_ADDME_COMPONENT_DESIGN_INNER_CLASS_OPERATION_TABLE;
	public static String REPORT_WORD_TABLE_ADDME_COMPONENT_DESIGN_ALGORITHM;
	public static String REPORT_WORD_TABLE_ADDME_COMPONENT_DESIGN_INNER_SEQUENCE_DAIGRAM;
	public static String REPORT_WORD_TABLE_ADDME_COMPONENT_DESIGN_INNER_SEQUENCE_DAIGRAM_NAME;

	public static String REPORT_WORD_TABLE_MARMI_OBJECTMODEL_TECHNIQUE_PACKAGE_NAME;
	public static String REPORT_WORD_TABLE_MARMI_OBJECTMODEL_TECHNIQUE_CLASS_DIAGRAM;
	public static String REPORT_WORD_TABLE_MARMI_OBJECTMODEL_TECHNIQUE_CLASS_DIAGRAM_NAME;
	public static String REPORT_WORD_TABLE_MARMI_OBJECTMODEL_TECHNIQUE_IMAGE;
	public static String REPORT_WORD_TABLE_MARMI_OBJECTMODEL_TECHNIQUE_CLASS_DEFINE;
	public static String REPORT_WORD_TABLE_MARMI_OBJECTMODEL_TECHNIQUE_CLASS_NAME;
	public static String REPORT_WORD_TABLE_MARMI_OBJECTMODEL_TECHNIQUE_CLASS_OUTLINE;
	public static String REPORT_WORD_TABLE_MARMI_OBJECTMODEL_TECHNIQUE_CLASS_ATTRIBUTE;
	public static String REPORT_WORD_TABLE_MARMI_OBJECTMODEL_TECHNIQUE_CLASS_ATTRIBUTE_TABLE;
	public static String REPORT_WORD_TABLE_MARMI_OBJECTMODEL_TECHNIQUE_CLASS_OPERATION;
	public static String REPORT_WORD_TABLE_MARMI_OBJECTMODEL_TECHNIQUE_CLASS_OPERATION_TABLE;
	public static String REPORT_WORD_TABLE_MARMI_OBJECTMODEL_TECHNIQUE_CLASS_ALGORITHM;
	public static String REPORT_WORD_TABLE_MARMI_OBJECTMODEL_TECHNIQUE_CLASS_CONNECTION_DIAGRAM;

	public static String REPORT_WORD_TABLE_MARMI_BUSINESS_PROCESS_ACTIVITYMODEL_TECHINIQUE_PACKAGE_NAME;
	public static String REPORT_WORD_TABLE_MARMI_BUSINESS_PROCESS_ACTIVITYMODEL_TECHINIQUE_ACTIVITY_DIAGRAM_NAME;
	public static String REPORT_WORD_TABLE_MARMI_BUSINESS_PROCESS_ACTIVITYMODEL_TECHINIQUE_ACTIVITY_DIAGRAM_OUTLINE;
	public static String REPORT_WORD_TABLE_MARMI_BUSINESS_PROCESS_ACTIVITYMODEL_TECHINIQUE_PROCESS_ACTIVITY_MODEL;
	public static String REPORT_WORD_TABLE_MARMI_BUSINESS_PROCESS_ACTIVITYMODEL_TECHINIQUE_SWIM_LANE;
	public static String REPORT_WORD_TABLE_MARMI_BUSINESS_PROCESS_ACTIVITYMODEL_TECHINIQUE_SWIM_LANE_TABLE;
	public static String REPORT_WORD_TABLE_MARMI_BUSINESS_PROCESS_ACTIVITYMODEL_TECHINIQUE_PROCESS_ACTIVITY_DAIGRAM;
	public static String REPORT_WORD_TABLE_MARMI_BUSINESS_PROCESS_ACTIVITYMODEL_TECHINIQUE_IMAGE;

	public static String REPORT_WORD_TABLE_MARMI_USECASEMODEL_TECHINIQUE_PACKAGE_NAME;
	public static String REPORT_WORD_TABLE_MARMI_USECASEMODEL_TECHINIQUE_SYSTEM_OUTLINE;
	public static String REPORT_WORD_TABLE_MARMI_USECASEMODEL_TECHINIQUE_ACTOR_LIST;
	public static String REPORT_WORD_TABLE_MARMI_USECASEMODEL_TECHINIQUE_ACTOR_TABLE;
	public static String REPORT_WORD_TABLE_MARMI_USECASEMODEL_TECHINIQUE_USECASE_DIAGRAM;
	public static String REPORT_WORD_TABLE_MARMI_USECASEMODEL_TECHINIQUE_USECASE_DIAGRAM_NAME ;
	public static String REPORT_WORD_TABLE_MARMI_USECASEMODEL_TECHINIQUE_IMAGE;
	public static String REPORT_WORD_TABLE_MARMI_USECASEMODEL_TECHINIQUE_USECASE_DESCRIPTION;
	public static String REPORT_WORD_TABLE_MARMI_USECASEMODEL_TECHINIQUE_USECASE_NAME ;
	public static String REPORT_WORD_TABLE_MARMI_USECASEMODEL_TECHINIQUE_USECASE_OUTLINE;
	public static String REPORT_WORD_TABLE_MARMI_USECASEMODEL_TECHINIQUE_USECASE_EVENT_FOLW;
	public static String REPORT_WORD_TABLE_MARMI_USECASEMODEL_TECHINIQUE_USECASE_BASIC_FLOW;
	public static String REPORT_WORD_TABLE_MARMI_USECASEMODEL_TECHINIQUE_USECASE_ALTERNATIVE_FLOW ;
	public static String REPORT_WORD_TABLE_MARMI_USECASEMODEL_TECHINIQUE_USECASE_EXCEPTION_FLOW;
	public static String REPORT_WORD_TABLE_MARMI_USECASEMODEL_TECHINIQUE_USECASE_PRECONDITION;
	public static String REPORT_WORD_TABLE_MARMI_USECASEMODEL_TECHINIQUE_USECASE_POSTCONDITION;
	public static String REPORT_WORD_TABLE_MARMI_USECASEMODEL_TECHINIQUE_USECASE_VARIABLE;

	public static String REPORT_WORD_TABLE_MARMI_COMPONENT_SPECIFICATION_PACKAGE_NAME;
	public static String REPORT_WORD_TABLE_MARMI_COMPONENT_SPECIFICATION_COMPONENT_DIAGRAM;
	public static String REPORT_WORD_TABLE_MARMI_COMPONENT_SPECIFICATION_IMAGE;
	public static String REPORT_WORD_TABLE_MARMI_COMPONENT_SPECIFICATION_SYSTEM_COMPONENT;
	public static String REPORT_WORD_TABLE_MARMI_COMPONENT_SPECIFICATION_COMPONENT_NAME;
	public static String REPORT_WORD_TABLE_MARMI_COMPONENT_SPECIFICATION_COMPONENT_DGM;
	public static String REPORT_WORD_TABLE_MARMI_COMPONENT_SPECIFICATION_COMPONENT_SPECIFICATION;
	public static String REPORT_WORD_TABLE_MARMI_COMPONENT_SPECIFICATION_COMPONENT_TABLE;
	public static String REPORT_WORD_TABLE_MARMI_COMPONENT_SPECIFICATION_COMPONENT_RESTRICTED_CONDITION;
	public static String REPORT_WORD_TABLE_MARMI_COMPONENT_SPECIFICATION_COMPONENT_INTERFACE_BETWEEN_RESTRICTED_CONDITION;
	public static String REPORT_WORD_TABLE_MARMI_COMPONENT_SPECIFICATION_COMPONENT_INTERFACE_SPECIFICATION;
	public static String REPORT_WORD_TABLE_MARMI_COMPONENT_SPECIFICATION_COMPONENT_SEQUENCE_DIAGRAM;

	public static String REPORT_WORD_TABLE_MARMI_COMPONENT_DESIGN_PACKAGE_NAME;
	public static String REPORT_WORD_TABLE_MARMI_COMPONENT_DESIGN_COMPONENT_NAME;
	public static String REPORT_WORD_TABLE_MARMI_COMPONENT_DESIGN_COMPONENT_OUTLINE;
	public static String REPORT_WORD_TABLE_MARMI_COMPONENT_DESIGN_COMPONENT_INNER_CLASS;
	public static String REPORT_WORD_TABLE_MARMI_COMPONENT_DESIGN_COMPONENT_INNER_CLASS_DIAGRAM_NAME;
	public static String REPORT_WORD_TABLE_MARMI_COMPONENT_DESIGN_IMAGE;
	public static String REPORT_WORD_TABLE_MARMI_COMPONENT_DESIGN_COMPONENT_INNER_SEQUENCE_DIAGRAM;
	public static String REPORT_WORD_TABLE_MARMI_COMPONENT_DESIGN_COMPONENT_INNER_SEQUENCE_DIAGRAM_NAME;
	
	
	public static String REPORT_TYPE_ADDME_BUSSINESS_DEFINE_DESCRIPTION;
	public static String REPORT_TYPE_ADDME_USECASE_SPECIFICATION_DESCRIPTION;
	public static String REPORT_TYPE_ADDME_CLASS_SPECIFICATION_DESCRIPTION;
	public static String REPORT_TYPE_ADDME_COMPONENT_ARCHITECTURE_DEFINE_DESCRIPTION;
	public static String REPORT_TYPE_ADDME_INTERFACE_INTERACTION_SPECIFICATION_DESCRIPTION;
	public static String REPORT_TYPE_ADDME_INTERFACE_SPECIFICATION_DESCRIPTION;
	public static String REPORT_TYPE_ADDME_COMPONENT_SPECIFICATION_DESCRIPTION;
	public static String REPORT_TYPE_ADDME_COMPONENT_DESIGN_DESCRIPTION;

	public static String REPORT_TYPE_MARMI_OBJECTMODEL_TECHNIQUE_DESCRIPTION;
	public static String REPORT_TYPE_MARMI_BUSINESS_PROCESS_ACTIVITYMODEL_TECHINIQUE_DESCRIPTION;
	public static String REPORT_TYPE_MARMI_USECASEMODEL_TECHINIQUE_DESCRIPTION;
	public static String REPORT_TYPE_MARMI_COMPONENT_SPECIFICATION_DESCRIPTION;
	public static String REPORT_TYPE_MARMI_COMPONENT_DESIGN_DESCRIPTION;
	//20080903 KDI e
	

    public static String REPORT_DIALOG_HEADER_FOOTER;  //KDI 080908 0002

	/**
	 * MODEL
	 */
	public static String  MODEL_EVENT;
	
	static {
		if (isEng) {
			NLS.initializeMessages(
					"kr.co.n3soft.n3com.lang.N3Com_en", N3Messages.class); //$NON-NLS-1$
		} else
			NLS.initializeMessages(
					"kr.co.n3soft.n3com.lang.N3Com_ko_KR", N3Messages.class); //$NON-NLS-1$
	}

}
