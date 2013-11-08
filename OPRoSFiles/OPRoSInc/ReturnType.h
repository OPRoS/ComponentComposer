/*
 *  OPRoS Component Engine (OCE)
 *  Copyright (c) 2008 ETRI. http://www.etri.re.kr.
 *  Distributed under the OPRoS License, Version 1.0.
 *
 *  @Created : 2009. 2. 19
 *  @Author  : sby (sby@etri.re.kr)
 *
 *  @File    : ReturnType.h
 *
 *
 */

#ifndef RETURNTYPE_H_
#define RETURNTYPE_H_

enum ReturnType {
	OPROS_SUCCESS=200,
	OPROS_SUCCESS_SYNC=202,
	OPROS_CALLER_ERROR=400,
	OPROS_BAD_INPUT_PARAMETER=401,
	OPROS_INPUT_OUT_OF_RANGE=402,
	OPROS_INPUT_NULL=403,
	OPROS_UNAUTHORIZED=404,
	OPROS_PRECONDITION_NOT_MET=405,
	OPROS_CALLEE_ERROR=500,
	OPROS_UNSUPPORTED_METHOD=501,
	OPROS_INTERNAL_FAULT=502,
	OPROS_OUT_OF_RESOURCES=408,
	OPROS_VERSION_MISMATCH=505
};

#endif /* RETURNTYPE_H_ */
