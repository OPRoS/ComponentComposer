package kr.co.n3soft.n3com.net;


/**
 * @(#) SocketUtil.java
 * Copyright 1999-2002 by  Java Service Network Community, KOREA.
 * All rights reserved.  http://www.javaservice.net
 * 
 * NOTICE !      You can copy or redistribute this code freely, 
 * but you should not remove the information about the copyright notice 
 * and the author.
 * 
 * @author  WonYoung Lee, javaservice@hanmail.net
 */
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
public class SocketUtil {

	public static final int INTPUTSTREAM_READ_RETRY_COUNT = 10;

	public SocketUtil() {}


	/** 
	 * The <code>read_data</code> method of <code>SocketUtil</code> reads the
	 * specified length of bytes from the given input stream.
	 *
	 * @param      in   an inputstream
	 * @param      len  the number of bytes read.
	 * @return     The specified number of bytes read until the end of
	 *             the stream is reached.
	 * @exception  IOException  if an I/O error or unexpected EOF occurs
	 */
	public static final byte[] read_data(InputStream in, int len) throws IOException {
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
	
		int bcount = 0;
		byte[] buf = new byte[2048];
		int read_retry_count = 0;
		while( bcount < len ) {
			int n = in.read(buf,0, len-bcount < 2048 ? len-bcount : 2048 );
			if ( n > 0 ) { bcount += n; bout.write(buf,0,n); }
			// What would like to do if you've got an unexpected EOF before
			// reading all data ?
			//else if (n == -1) break;
			else if ( n == -1 ) throw 
			new IOException("inputstream has returned an unexpected EOF");
			else  { // n == 0
				if (++read_retry_count >= INTPUTSTREAM_READ_RETRY_COUNT)
					throw new IOException("inputstream-read-retry-count( " +
							INTPUTSTREAM_READ_RETRY_COUNT + ") exceed !");
			}
		}
		bout.flush();
		return bout.toByteArray();
	}

	/** 
	 * The <code>read_data</code> method of <code>SocketUtil</code> reads all
	 * the bytes from the given inputstream until the given input stream 
	 * has not returned an EOF(end-of-stream) indicator.
	 *
	 * @param      in   an inputstream
	 * @return     all bytes read if the end of the stream is reached.
	 * @exception  IOException  if an I/O error occurs
	 */
	public static final byte[] read_data(InputStream in) throws IOException {
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		int bcount = 0;
		byte[] buf = new byte[2048];
		int read_retry_count = 0;
		while( true ) {
			int n = in.read(buf);
			if ( n > 0 ) { bcount += n; bout.write(buf,0,n); }
			else if (n == -1) break;
			else  { // n == 0
				if (++read_retry_count >= INTPUTSTREAM_READ_RETRY_COUNT)
					throw new IOException("inputstream-read-retry-count( " +
							INTPUTSTREAM_READ_RETRY_COUNT + ") exceed !");
			}
		}
		bout.flush();
		return bout.toByteArray();
	}

	/**
	 * Read a line of text.  A line is considered to be terminated by a line
	 * feed ('\n') or a carriage return followed immediately by a linefeed.
	 *
	 * @return     A String containing the contents of the line, not including
	 *             any line-termination characters, or null if the end of the
	 *             stream has been reached
	 *
	 * @exception  IOException  If an I/O error occurs
	 */
	public static final String read_line(InputStream in) throws IOException {
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		
		boolean eof = false;
		while( true ) {
			int b = in.read();
			if (b == -1) { eof = true; break;}
			if ( b != '\r' && b != '\n' ) bout.write((byte)b);
			if (b == '\n') break;
		}
		bout.flush();
//		ByteBuffer buffer2 = ByteBuffer.wrap(bout.toByteArray());
		
		
		if ( eof && bout.size() == 0 ) return null; 
		//Or return ""; ? what's fit for you?
		return bout.toString();
	} 
	
	public static final String read_line2(InputStream in) throws IOException {
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		
		boolean eof = false;
		while( true ) {
			int b = in.read();
			if (b == -1) { eof = true; break;}
			if ( b != '\r' && b != '\n' ) bout.write((byte)b);
			if (b == '\n') break;
		}
		bout.flush();
//		ByteBuffer buffer2 = ByteBuffer.allocate(bout.size());
//		String str = new String(buffer2.array());
		
		if ( eof && bout.size() == 0 ) return null; 
		//Or return ""; ? what's fit for you?
		return bout.toString();
	} 
}

