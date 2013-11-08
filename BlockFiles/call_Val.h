#ifdef THIS_IS_CALL
extern
#endif
double * inVal;

#ifdef THIS_IS_CALL
extern
#endif
double * outVal;

#ifdef THIS_IS_CALL
extern
#endif
int outValSize;

#ifdef THIS_IS_CALL
	extern
#ifdef __cplusplus
	"C"
#endif
	void save_inVal(double * in);
#else
#ifdef __cplusplus
	extern "C"
#endif
	void save_inVal(double * in)
	{
		inVal= in;
	}
#endif

#ifdef THIS_IS_CALL
	extern
#ifdef __cplusplus
	"C"
#endif
	void save_outVal(double * in, int size);
#else
#ifdef __cplusplus
	extern "C"
#endif
	void save_outVal(double * in, int size)
	{
		outVal= (double *) malloc(sizeof(double) * size);
		memcpy(outVal, in, sizeof(double) * size);
		outValSize= size;
	}
#endif

#ifdef THIS_IS_CALL
	extern
#ifdef __cplusplus
	"C"
#endif
	double * get_inVal(void);
#else
#ifdef __cplusplus
	extern "C"
#endif
	double * get_inVal(void)
	{
		return inVal;
	}
#endif

#ifdef THIS_IS_CALL
	extern
#ifdef __cplusplus
	"C"
#endif
	double * get_outVal(void);
#else
#ifdef __cplusplus
	extern "C"
#endif
	double * get_outVal(void)
	{
		return outVal;
	}
#endif

#ifdef THIS_IS_CALL
	extern
#ifdef __cplusplus
	"C"
#endif
	int get_outValSize(void);
#else
#ifdef __cplusplus
	extern "C"
#endif
	int get_outValSize(void)
	{
		return outValSize;
	}
#endif
