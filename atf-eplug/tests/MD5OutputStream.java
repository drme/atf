/*
 * MD5OutputStream, a subclass of FilterOutputStream implementing MD5
 * functionality on a stream.
 *
 * Author: Santeri Paavolainen, Helsinki Finland 1996, 2003
 * (c) Santeri Paavolainen, Helsinki Finland 1996, 2003
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Library General Public
 * License as published by the Free Software Foundation; either
 * version 2 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Library General Public License for more details.
 *
 * You should have received a copy of the GNU Library General Public
 * License along with this library; if not, write to the Free
 * Software Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 *
 * See http://www.iki.fi/santtu/programs/md5/ for more information on this
 * and the MD5 class.
 *
 */

//package fi.iki.santtu.md5;
//import fi.iki.santtu.md5.MD5;
import java.io.*;

/**
 * MD5OutputStream is a subclass of FilterOutputStream adding MD5
 * hashing of the read output.
 *
 * @version	$Revision: 1.2 $
 * @author	Santeri Paavolainen <santtu@iki.fi>
 */

public class MD5OutputStream extends FilterOutputStream {
    /**
     * MD5 context
     */
    private MD5	md5;

    /**
     * Creates MD5OutputStream
     * @param out	The output stream
     */

    public MD5OutputStream (OutputStream out) {
	super(out);

	md5 = new MD5();
    }

    /**
     * Writes a byte.
     *
     * @see java.lang.FilterOutputStream
     */

    public void write (int b) throws IOException {
	out.write(b);
	md5.Update((byte) b);
    }

    /**
     * Writes a sub array of bytes.
     *
     * @see java.lang.FilterOutputStream
     */

    public void write (byte b[], int off, int len) throws IOException {
	out.write(b, off, len);
	md5.Update(b, off, len);
    }

    /**
     * Returns array of bytes representing hash of the stream as finalized
     * for the current state.
     * @see MD5.Final()
     */

    public byte[] hash () {
	return md5.Final();
    }
}
