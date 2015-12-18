/* Copyright (c) 1996-2015, OPC Foundation. All rights reserved.
   The source code in this file is covered under a dual-license scenario:
     - RCL: for OPC Foundation members in good-standing
     - GPL V2: everybody else
   RCL license terms accompanied with this source code. See http://opcfoundation.org/License/RCL/1.00/
   GNU General Public License as published by the Free Software Foundation;
   version 2 of the License are accompanied with this source code. See http://opcfoundation.org/License/GPLv2
   This source code is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
*/

package org.opcfoundation.ua.transport.tcp.impl;

/**
 * TCP Communication headers
 * 
 */
public class TcpMessageType {

	/** A final chunk for a message. */
    public static final int FINAL = 0x46000000;

    /** An intermediate chunk for a message. */
    public static final int CONTINUE = 0x43000000;
    
    /** A final chunk for a message which indicates that the message has been aborted by the sender. */ 
    public static final int ABORT = 0x41000000;

    /** A mask used to select the message type portion of the message id. */
    public static final int MESSAGE_TYPE_MASK = 0x00FFFFFF;

    /** A mask used to select the chunk type portion of the message id. */
    public static final int CHUNK_TYPE_MASK = 0xFF000000;
                   
    /** A chunk for a generic message. */
    public static final int MESSAGE = 0x0047534D;

    /** A chunk for an OpenSecureChannel message. */
    public static final int OPEN = 0x004E504F;

    /** A chunk for a CloseSecureChannel message. */
    public static final int CLOSE = 0x004F4C43; 

    
    /** A hello message. */
    public static final int HELLO = 0x004C4548;

    /** An acknowledge message. */
    public static final int ACKNOWLEDGE = 0x004B4341;

    /** An error message. */
    public static final int ERROR = 0x00525245;
    
	public static final int MSGC = MESSAGE | CONTINUE;  
	public static final int MSGF = MESSAGE | FINAL;  
	public static final int MSGA = MESSAGE | ABORT;  
	public static final int OPNC = OPEN | CONTINUE;  
	public static final int OPNF = OPEN | FINAL;  
	public static final int OPNA = OPEN | ABORT;

	public static final int HELF = HELLO | FINAL;  
	public static final int ACKF = ACKNOWLEDGE | FINAL;  
	public static final int ERRF = ERROR | FINAL;  
	
	public static boolean isFinal(int msgType)
	{
		return (msgType & CHUNK_TYPE_MASK) == FINAL;
	}

	public static boolean continues(int msgType)
	{
		return (msgType & CHUNK_TYPE_MASK) == CONTINUE;
	}
	
	public static boolean isAbort(int msgType)
	{
		return (msgType & CHUNK_TYPE_MASK) == ABORT;
	}
	
	
}
