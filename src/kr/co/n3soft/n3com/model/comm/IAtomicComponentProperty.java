package kr.co.n3soft.n3com.model.comm;

public interface IAtomicComponentProperty {
	public static final String PROPERTY_COMP_NAME = "Component Name";
	public static final String PROPERTY_COMP_DESCRIPTION = "Component Description";
	public static final String PROPERTY_COMP_VERSION = "Component Version";
	public static final String PROPERTY_COMP_ID = "Component ID";
	public static final String PROPERTY_COMPANY_NAME = "Company Name";
	public static final String PROPERTY_COMPANY_PHONE = "Company Phone";
	public static final String PROPERTY_COMPANY_ADDRESS = "Company Address";
	public static final String PROPERTY_COMPANY_HOMEPAGE = "Company Homepage";
	public static final String PROPERTY_LICENSE_POLICY = "License Policy";
	
	public static final String PROPERTY_CPU_NAME = "CPU Name";
	
	public static final String PROPERTY_DOMAIN = "Execution Environment Domain";
	public static final String PROPERTY_ROBOT_TYPE = "Execution Environment Robot Type";
	
	public static final String PROPERTY_LIBRARY_NAME = "Library Name";
	public static final String PROPERTY_LIBRARY_TYPE = "Library Type";
	public static final String PROPERTY_IMPL_LANGUAGE = "Implemention Language";
	public static final String PROPERTY_COMPILER = "Compiler";
	
	public static final String PROPERTY_OS_NAME = "OS Name";
	public static final String PROPERTY_OS_VERSION = "OS Version";
	
	public static final String PROPERTY_EXE_TYPE = "Execution Type";
	public static final String PROPERTY_EXE_PERIOD = "Excution Period";
	public static final String PROPERTY_EXE_PRIORITY = "Excution Priority";
	public static final String PROPERTY_EXE_INSTANCE_TYPE = "Execution Instance Type";
	public static final String PROPERTY_EXE_LIFECYCLE_TYPE = "Execution Lifecycle Type";
	
	public static final String[] PROPERTY_LICENSE_POLICY_ARRAY = new String[] { "OPRoS", "GPL","LGPL","FREE","OTHERS"};

	public static final String[] PROPERTY_EXE_INSTANCE_TYPE_ARRAY = new String[] { "singleton", "multiple"};

	public static final String[] PROPERTY_EXE_LIFECYCLE_TYPE_ARRAY = new String[] { "start", "init"};

	public static final String[] PROPERTY_EXE_TYPE_ARRAY = new String[] { "periodic", "non-periodic","passive"};

	public static final String[] PROPERTY_COMPILER_ARRAY = new String[] { "g++", "MSVC","JDK"};

	public static final String[] PROPERTY_IMPL_LANGUAGE_ARRAY = new String[] { "MinGW C++", "MSVC C++"};
	

}
