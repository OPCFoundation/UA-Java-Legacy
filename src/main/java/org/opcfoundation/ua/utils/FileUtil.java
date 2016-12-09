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

package org.opcfoundation.ua.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.URL;

import org.opcfoundation.ua.utils.bytebuffer.ByteQueue;


/**
 * <p>FileUtil class.</p>
 *
 */
public class FileUtil {

    /**
     * Creates and writes a binary file
     *
     * @param file file
     * @param data data
     * @throws java.io.IOException on i/o problems
     */
    public static void writeFile(File file, byte[] data)
    throws IOException
    {        
        file.createNewFile();
        file.setWritable(true);
        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        try {
            raf.setLength(data.length);
            raf.seek(0);
            raf.write(data);            
        } finally {
            raf.close();
        }
    }

    /**
     * Reads entire binary file
     *
     * @param file file
     * @return contents of binary file
     * @throws java.io.IOException on i/o problems
     */
    public static byte[] readFile(File file)
    throws IOException
    {
        FileInputStream fis = new FileInputStream(file);
        try {
            long size = file.length();
            if (size>Integer.MAX_VALUE) 
                throw new IOException("File too big");
            int len = (int) size;
            byte data [] = new byte[len];
            int pos = 0;
            
            while (pos<size) {
                int read = fis.read(data, pos, len-pos);
                pos += read;
            }
            return data;
        } finally {
            fis.close();
        }
    }
    
    /**
     * Reads entire binary file to a byte array
     *
     * @param url a {@link java.net.URL} object.
     * @return contents of binary file
     * @throws java.io.IOException on i/o problems
     */
    public static byte[] readFile(URL url)
    throws IOException
    {    	
    	ByteQueue q = new ByteQueue();
        InputStream is = url.openStream();
        try {
        	byte[] buf = new byte[1024];
        	for (;;) {
        		int bytesRead = is.read(buf);
        		if (bytesRead==-1) break;
        		q.put(buf, 0, bytesRead);
        	}  
            byte[] result = new byte[ (int) q.getBytesWritten() ];
            q.get(result);
            return result;
        } finally {
            is.close();
        }
    }    
    
    /**
     * Reads entire binary file to a byte array.
     * Note the stream is not closed.
     *
     * @param is input stream
     * @return contents of the stream
     * @throws java.io.IOException on i/o problems
     */
    public static byte[] readStream(InputStream is)
    throws IOException
    {    	
    	ByteQueue q = new ByteQueue();
        byte[] buf = new byte[1024];
        for (;;) {
        	int bytesRead = is.read(buf); 
        	if (bytesRead==-1) break;
        	q.put(buf, 0, bytesRead);
        }  
        byte[] result = new byte[ (int) q.getBytesWritten() ];
        q.get(result);
        return result;
    }        

    
}
