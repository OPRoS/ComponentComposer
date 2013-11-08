/*
 *  OPRoS Component Engine (OCE)
 *  Copyright (c) 2008 ETRI. http://www.etri.re.kr.
 *  Distributed under the OPRoS License, Version 1.0.
 *
 *  @Created : 2009. 2. 20
 *  @Author  : sby (sby@etri.re.kr)
 *
 *  @File    : OutputDataPortInterface.h
 *
 *
 */

#ifndef OUTPUTDATAPORTINTERFACE_H_
#define OUTPUTDATAPORTINTERFACE_H_

#include <boost/any.hpp>
#include <boost/archive/polymorphic_iarchive.hpp>
#include <DataPort.h>
#include <ReturnType.h>
#include <ArchiveFactory.h>
#include <IDataPortLink.h>
#include <Connector.h>

class OutputDataPortImpl;

class OutputDataPortInterface: public DataPort {
protected:
	friend class OutputDataPortImpl;
	OutputDataPortImpl *m_impl;
	ArchiveFactory *m_arc;

	virtual ReturnType push_data(boost::any &data);

public:
	OutputDataPortInterface();
	OutputDataPortInterface(const std::string &name);

	virtual ReturnType push(boost::any & data)=0;
	virtual ReturnType pushData(unsigned char *data, int size)=0;
	virtual ReturnType marshal(boost::any &data, std::string &outdata) = 0;
	virtual boost::any *pop();
	virtual boost::any *peek();


	virtual bool checkType(boost::any & data) = 0;
	virtual bool checkType(DataPort *dst) = 0;



	virtual DataPortRole getRole();
	virtual void setArchiveFactory(ArchiveFactory *darc);
	virtual void addConnection(IDataPortLink * connection);
	virtual void removeConnection(IDataPortLink *connection);
	virtual void clearConnection();
	virtual ~OutputDataPortInterface();
};

#endif /* OUTPUTDATAPORTINTERFACE_H_ */
