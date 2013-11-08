/*
 *  OPRoS Component Engine (OCE)
 *  Copyright (c) 2008 ETRI. http://www.etri.re.kr.
 *  Distributed under the OPRoS License, Version 1.0.
 *
 *  @Created : 2009. 2. 19
 *  @Author  : sby (sby@etri.re.kr)
 *
 *  @File    : InputDataPort.h
 *
 *
 */

#ifndef INPUTDATAPORT_H_
#define INPUTDATAPORT_H_
#include <boost/any.hpp>
#include <boost/type_traits.hpp>
#include <boost/archive/polymorphic_iarchive.hpp>
#include <DataPort.h>
#include <ReturnType.h>
#include <InputDataPortInterface.h>
#include <ArchiveFactory.h>
#include <sstream>

#include <cstring>

//
//
//  Version 2. InputDataPort can contain Pointer data
// 			  auto deletion is supported.
//
//

template<typename dataType>
class InputDataPort: public InputDataPortInterface {

public:
	InputDataPort() {
	}

	InputDataPort( DataPortPolicy data_policy,unsigned int queue_limit) :
		InputDataPortInterface( data_policy, queue_limit) {	}

	InputDataPort(const std::string &name) :
		InputDataPortInterface(name) {
	}

	InputDataPort(const std::string &name,
			DataPortPolicy data_policy, unsigned int queue_limit) :
		InputDataPortInterface(name,  data_policy, queue_limit) {
	}

	virtual ~InputDataPort() {
	}


	dataType *getContentPointer(boost::any &data) {
		return boost::unsafe_any_cast<dataType>(&data);
	}

	dataType getContent(boost::any &data) {
		dataType *pd = ANY_CASTING(dataType, &data);
		return *pd;
	}

	virtual ReturnType push(boost::any &data) {
	/*	dataType *k;

		k = ANY_CASTING(dataType, &data);

		boost::any somedata = *k; */

		return push_data(data);
	}

	virtual ReturnType push(dataType &data) { // data copy�� �ʿ���.
		boost::any ndata = data;

		return push(ndata);
	}

	virtual ReturnType pushData(unsigned char *data, int size) {
		if (m_arc == NULL) {
			return OPROS_UNSUPPORTED_METHOD;
		}
		std::string d_str((const char *) data, size);
		std::stringstream s_str(d_str);

		boost::archive::polymorphic_iarchive *arc = m_arc->getInputArchive(
				s_str);

		ReturnType rtV = push(*arc);

		m_arc->release(arc);

		return rtV;
	}

	virtual ReturnType push(boost::archive::polymorphic_iarchive &in) {
		boost::any mdata;

		dataType portData;

		in >> boost::serialization::make_nvp(PORTDATA_NVP, portData);

		mdata = portData;

		return push_data(mdata);
	}

	virtual bool checkType(DataPort *dst)
	{
		dataType sdata;

		boost::any tdata = sdata;

		return dst->checkType(tdata);
	}



	virtual bool checkType(boost::any & data) {
		if (data.empty())
			return false;
#ifndef __linux__
		if (data.type() == typeid(dataType))
			return true;
#else
		if ( ::strcmp(data.type().name(), typeid(dataType).name() )==0)
		return true;
#endif
		return false;
	}

};

#endif /* INPUTDATAPORT_H_ */

