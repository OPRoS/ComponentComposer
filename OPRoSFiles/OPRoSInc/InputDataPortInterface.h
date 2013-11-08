/*
 *  OPRoS Component Engine (OCE)
 *  Copyright (c) 2008 ETRI. http://www.etri.re.kr.
 *  Distributed under the OPRoS License, Version 1.0.
 *
 *  @Created : 2009. 2. 19
 *  @Author  : sby (sby@etri.re.kr)
 *
 *  @File    : InputDataPortInterface.h
 *
 *
 */

#ifndef INPUTDATAPORTINTERFACE_H_
#define INPUTDATAPORTINTERFACE_H_

#include <boost/any.hpp>
#include <boost/archive/polymorphic_iarchive.hpp>
#include <DataPort.h>
#include <ReturnType.h>
#include <ArchiveFactory.h>
class InputDataPortImpl;

class InputDataPortInterface : public DataPort{
protected:
	InputDataPortImpl *m_impl;
	ArchiveFactory *m_arc;

protected:
	virtual ReturnType push_data(boost::any & data);

public:
	InputDataPortInterface();
	InputDataPortInterface(DataPortPolicy data_policy, unsigned int queue_limit);
	InputDataPortInterface(const std::string &name);
	InputDataPortInterface(const std::string &name, DataPortPolicy data_policy, unsigned int queue_limit);


	virtual DataPortRole getRole();

	virtual boost::any *peek();
	virtual boost::any *pop();
	virtual int getSize();	
	virtual void setArchiveFactory(ArchiveFactory *darc);
	virtual void reset();

	virtual ReturnType push(boost::any & data) = 0;
	virtual ReturnType pushData(unsigned char *data, int size) = 0;
	virtual ReturnType push(boost::archive::polymorphic_iarchive &in)=0;

	virtual bool checkType(boost::any & data)=0;
	virtual bool checkType(DataPort *dst) = 0;

	virtual ~InputDataPortInterface();
};

#endif /* INPUTDATAPORTINTERFACE_H_ */
