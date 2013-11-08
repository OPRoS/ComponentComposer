/*
 *  OPRoS Component Engine (OCE)
 *  Copyright (c) 2008 ETRI. http://www.etri.re.kr.
 *  Distributed under the OPRoS License, Version 1.0.
 *
 *  @Created : 2009. 2. 19
 *  @Author  : sby (sby@etri.re.kr)
 *
 *  @File    : ArchiveFactory.h
 *
 *
 */

#ifndef ARCHIVEFACTORY_H_
#define ARCHIVEFACTORY_H_

#include <sstream>
#include <boost/archive/polymorphic_oarchive.hpp>
#include <boost/archive/polymorphic_iarchive.hpp>

/**
 * ArchiveFactory is the abstract base class for all boost archive instance creation factory
 *
 */
class ArchiveFactory {
public:
	/**
	 *  Create boost output archive instance related to output stream
	 *
	 *  @param str		stream reference used by boost output archive.
	 *  				After serialization process, the engine gets string from str and
	 *  				send a message including the string to target host
	 */
	virtual boost::archive::polymorphic_oarchive *getOutputArchive(std::ostream &str) =0;

	/**
	 *  Create boost input archive instance related to input stream
	 *
	 *	@param str		stream reference used by boost input archive.
	 *					There are serialized data in the stream from other hosts or components.
	 */
	virtual boost::archive::polymorphic_iarchive *getInputArchive(std::istream &str) =0;

	/**
	 * Release output archive instance
	 *
	 *	@param arc		polymorphic_oarchive instance pointer.
	 *					the pointer had to be created from getOutputArchive() function.
	 */
	virtual void release(boost::archive::polymorphic_oarchive *arc)=0;

	/**
	 * Release input archive instance
	 *
	 *	@param arc		polymorphic_iarchive instance pointer.
	 *					the pointer had to be created from getInputArchive() function.
	 */
	virtual void release(boost::archive::polymorphic_iarchive *arc)=0;

	ArchiveFactory();
	virtual ~ArchiveFactory();
};
#endif /* ARCHIVEFACTORY_H_ */
