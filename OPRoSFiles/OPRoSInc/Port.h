/*
 *  OPRoS Component Engine (OCE)
 *  Copyright (c) 2008 ETRI. http://www.etri.re.kr.
 *  Distributed under the OPRoS License, Version 1.0.
 *
 *  @Created : 2009. 2. 18
 *  @Author  : sby (sby@etri.re.kr)
 *
 *  @File    : Port.h
 *
 *
 */

#ifndef PORT_H_
#define PORT_H_

#include <OPRoSTypes.h>
#include <string>
#include <boost/serialization/array.hpp>
#include <boost/serialization/complex.hpp>
#include <boost/serialization/hash_map.hpp>
#include <boost/serialization/hash_set.hpp>
#include <boost/serialization/deque.hpp>
#include <boost/serialization/vector.hpp>
#include <boost/serialization/list.hpp>
#include <boost/serialization/slist.hpp>
#include <boost/serialization/map.hpp>
#include <boost/serialization/set.hpp>
#include <boost/serialization/scoped_ptr.hpp>
#include <boost/serialization/shared_ptr.hpp>
#include <boost/serialization/string.hpp>
#include <boost/serialization/valarray.hpp>
#include <boost/serialization/weak_ptr.hpp>
#include <boost/serialization/string.hpp>
class Port {
protected:
	std::string m_name;
	PortType m_type;

public:
	Port();
	Port(const std::string &name, const PortType &type);
	virtual std::string &getName();
	virtual PortType getType();
	virtual ~Port();
	virtual void reset()=0;
};

#endif /* PORT_H_ */
