#if !defined(_OPROSTYPES_H)
#define _OPROSTYPES_H

typedef enum {
	OPROS_MT_PROVIDED, OPROS_MT_REQUIRED, OPROS_MT_REMOTE,
} MethodType;

typedef enum {
	OPROS_SERVICE, OPROS_EVENT, OPROS_DATA , OPROS_REMOTE_DATA
} PortType;

typedef enum {
	OPROS_FIFO, OPROS_LIFO, OPROS_UFIFO, OPROS_LAST
} DataPortPolicy;

typedef enum {
	OPROS_CS_CREATED, OPROS_CS_READY, OPROS_CS_ACTIVE, OPROS_CS_INACTIVE, OPROS_CS_ERROR, OPROS_CS_DESTROYED
} LifecycleState;

typedef enum {
	OPROS_ES_CREATED, OPROS_ES_ACTIVE, OPROS_ES_INACTIVE, OPROS_ES_ERROR, OPROS_ES_DESTROYED, OPROS_ES_PREPARE_TO_DESTROYED
} ExecutorState;

typedef enum {
	OPROS_DATA_PORT_INPUT, OPROS_DATA_PORT_OUTPUT
} DataPortRole;

typedef enum {
	OPROS_EVENT_PORT_INPUT, OPROS_EVENT_PORT_OUTPUT
} EventPortRole;


#define DEFAULT_QUEUE_LIMIT		15
// Message Headers..
//
#define MESSAGE_HEADER_NVP  	"OPRosHeader"
#define MESSAGE_OP_MAGIC_NVP	"OPMagic"
#define METHODCALL_NVP 			"MethodCall"
#define METHODRESULT_NVP 		"MethodResult"
#define PORTDATA_NVP	 		"PortData"
#define DATAPORT_NVP			"DataPort"
#define EVENTPORT_NVP			"EventPort"
#define EVENTDATA_NVP			"EventData"

#define OPROS_HEADER_MAGIC		"OPROS"
#define METHOD_REQUEST_MAGIC	"MQ"
#define METHOD_RESPONE_MAGIC	"MR"
#define REMOTE_DATA_MAGIC		"RD"
#define REMOTE_EVENT_MAGIC		"ED"

#ifdef _MSC_VER
#define SAFE_CASTING(a,b) (dynamic_cast<a>(b))
#define ANY_CASTING(a,b) (boost::any_cast<a>(b))
#else
#define SAFE_CASTING(a,b) (static_cast<a>(b))
#define ANY_CASTING(a,b) (boost::unsafe_any_cast<a>(b))
#endif

#endif  // _OPROSTYPES_H
