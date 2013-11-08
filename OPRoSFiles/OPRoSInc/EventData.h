/*
 *  OPRoS Component Engine (OCE)
 *  Copyright (c) 2008 ETRI. http://www.etri.re.kr.
 *  Distributed under the OPRoS License, Version 1.0.
 *
 *  @Created : 2009. 2. 23
 *  @Author  : sby (sby@etri.re.kr)
 *
 *  @File    : EventData.h
 *
 *
 */

#ifndef EVENTDATA_H_
#define EVENTDATA_H_

#include <Event.h>
#include <boost/serialization/string.hpp>
#include <boost/serialization/base_object.hpp>
#include <boost/serialization/split_member.hpp>
#include <cstring>

template<typename dataType>
class EventData: public Event {
	friend class boost::serialization::access;


public:
	EventData() {


	}

	virtual ~EventData() {
	}

	virtual bool checkContentType(boost::any &data) {
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

	virtual dataType *getContentData() {
		dataType *somdata;

		somdata = ANY_CASTING(dataType, &getContent());

		return somdata;
	}
	virtual ReturnType setContentData(const dataType &data) {
		boost::any somdata = data;
		return setContent(somdata);
	}

	EventData<dataType> &operator=(const EventData<dataType> &rhs) {
		setId(rhs.getId());
		setComponentId(rhs.getComponentId());
		setPortId(rhs.getPortId());

		dataType *org = rhs.getContentData();

		if (org != NULL) {
			setContentData(*org);
		}

		return *this;
	}

	virtual Event *clone() {
		EventData<dataType> *ev = new EventData<dataType> ();

		ev->setId(getId());
		ev->setComponentId(getComponentId());
		ev->setPortId(getPortId());

		dataType *org = getContentData();
		if (org != NULL) {
			ev->setContentData(*org);
		}

		return ev;
	}

protected:

	friend std::ostream &operator<<(std::ostream &os,
			const EventData<dataType> &ev) {
				EventData<dataType> &nv = (EventData<dataType> &)ev;
		return os << "[eventid:" << nv.getId() << ",componentid:"
				<< nv.getComponentId() << ",portid:" << nv.getPortId() << "]";
	}

};

namespace boost {
namespace serialization {


template<class Archive, class T>
inline void save_this(Archive &ar, EventData<T> &t, const unsigned int file_version)
{
	std::string &eventid = t.getId();
	ar & BOOST_SERIALIZATION_NVP(eventid);
	std::string &componentid = t.getComponentId();
	ar & BOOST_SERIALIZATION_NVP(componentid);
	std::string &portid = t.getPortId();
	ar & BOOST_SERIALIZATION_NVP(portid);

	bool IsContent = false;
	T *content_ptr = t.getContentData();
	if (content_ptr != NULL) {
		IsContent = true;
		T &content = *content_ptr;
		ar & BOOST_SERIALIZATION_NVP(IsContent);
		ar & BOOST_SERIALIZATION_NVP(content); // for NVP naming
	} else {
		ar & BOOST_SERIALIZATION_NVP(IsContent);
	}
}


template<class Archive, class T>
inline void save(Archive &ar, const EventData<T> &t,
		const unsigned int file_version) {

	 save_this(ar,(EventData<T> &)t,file_version);
}

template<class Archive, class T>
inline void load(Archive & ar, EventData<T> &t, const unsigned int file_version) {

	std::string eventid;
	ar & BOOST_SERIALIZATION_NVP(eventid);
	std::string componentid;
	ar & BOOST_SERIALIZATION_NVP(componentid);
	std::string portid;
	ar & BOOST_SERIALIZATION_NVP(portid);

	t.setId(eventid);
	t.setComponentId(componentid);
	t.setPortId(portid);

	bool IsContent;
	ar & BOOST_SERIALIZATION_NVP(IsContent);

	if (IsContent) {
		T content;
		ar & BOOST_SERIALIZATION_NVP(content);
		t.setContentData(content);
	}
}

template<class Archive, class T>
inline void serialize(Archive &ar, EventData<T> &t,
		const unsigned int file_version) {


	boost::serialization::split_free(ar, t, file_version);
}

} // namespace serialization
} // namespace boost

#endif /* EVENTDATA_H_ */
