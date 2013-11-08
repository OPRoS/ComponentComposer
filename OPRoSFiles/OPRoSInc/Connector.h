/*
 *  OPRoS Component Engine (OCE)
 *  Copyright (c) 2008 ETRI. http://www.etri.re.kr.
 *  Distributed under the OPRoS License, Version 1.0.
 *
 *  @Created : 2009. 2. 27
 *  @Author  : sby (sby@etri.re.kr)
 *
 *  @File    : Connector.h
 *
 *
 */

#ifndef CONNECTOR_H_
#define CONNECTOR_H_

#include <ServicePortConnector.h>
#include <DataPortConnector.h>
#include <EventPortConnector.h>
#include <ReturnType.h>
#include <string>


class Connector : public ServicePortConnector, public DataPortConnector, public EventPortConnector{
	std::string m_name;
public:
	Connector(){};
	virtual ~Connector(){};

	virtual ReturnType sendData(unsigned char *data, int len) = 0;
	virtual ReturnType recvData(unsigned char *data, int len) = 0;

	virtual ReturnType requestService(std::string &name, std::string &invalue, std::string &outvalue) = 0;
	virtual ReturnType responseService(std::string &name, std::string &invalue, std::string &outvalue) = 0;

	virtual ReturnType sendEvent(unsigned char *data, int len) = 0;
	virtual ReturnType recvEvent(unsigned char *data, int len) = 0;
};

#endif /* CONNECTOR_H_ */
