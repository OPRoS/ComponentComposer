/*
 *  OPRoS Component Engine (OCE)
 *  Copyright (c) 2008 ETRI. http://www.etri.re.kr.
 *  Distributed under the OPRoS License, Version 1.0.
 *
 *  @Created : 2009. 2. 19
 *  @Author  : sby (sby@etri.re.kr)
 *
 *  @File    : Event.h
 *
 *
 */

#ifndef EVENT_H_
#define EVENT_H_

#include <string>
#include <boost/any.hpp>
#include <boost/serialization/base_object.hpp>
#include <boost/serialization/access.hpp>
#include <ReturnType.h>
class EventImpl;

class Event {
protected:
	EventImpl *m_impl;

public:
	Event();
	virtual ~Event();

	virtual std::string &getId();
	virtual void setId(const std::string &strid);
	virtual std::string &getComponentId();
	virtual void setComponentId(const std::string &strid);
	virtual std::string &getPortId();
	virtual void setPortId(const std::string &strid);
	virtual boost::any &getContent();
	virtual ReturnType setContent(boost::any &con);
	virtual bool checkContentType(boost::any &con)=0;
	virtual Event *clone()=0;
	Event &operator=(Event &rhs);

//	friend std::ostream &operator<<(std::ostream &os, Event &ev) {
//		return os << "[eventid:" << ev.getId() << ",componentid:"
//				<< ev.getComponentId() << ",portid:" << ev.getPortId() << "]";
//	}
};


#endif /* EVENT_H_ */
