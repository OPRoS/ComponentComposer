/*
 *  OPRoS Component Engine (OCE)
 *  Copyright (c) 2008 ETRI. http://www.etri.re.kr.
 *  Distributed under the OPRoS License, Version 1.0.
 *
 *  @Created : 2009. 2. 18
 *  @Author  : sby (sby@etri.re.kr)
 *
 *  @File    : Method.h
 *
 *
 */

#ifndef METHOD_H_
#define METHOD_H_

#include <Port.h>
#include <ReturnType.h>
#include <ArchiveFactory.h>

class Method {
protected:
	std::string m_name;
	
	ArchiveFactory *m_arc;
public:
	Method();
	Method(const std::string &name);
	virtual ~Method();
	virtual std::string &getName();	
	virtual void setArchiveFactory(ArchiveFactory *darc);
	virtual ReturnType call(std::string &invalue, std::string &outvalue);
	virtual ReturnType call(boost::archive::polymorphic_iarchive &in, boost::archive::polymorphic_oarchive &out) = 0;
};

#endif /* METHOD_H_ */
