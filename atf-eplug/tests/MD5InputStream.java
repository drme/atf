/*
 * MD5InputStream, a subclass of FilterInputStream implementing MD5
 * functionality on a stream.
 *
 * Authori: Santeri Paavolainen, Helsinki Finland 1996, 2003
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
 * MD5InputStream is a subclass of FilterInputStream adding MD5
 * hashing of the read input.
 *
 * @version	$Revision: 1.2 $
 * @author	Santeri Paavolainen <santtu@iki.fi>
 */

public class MD5InputStream extends FilterInputStream {
  /**
   * MD5 context
   */
  private MD5	md5;

  /**
   * Creates a MD5InputStream
   * @param in	The input stream
   */
  public MD5InputStream (InputStream in) {
    super(in);

    md5 = new MD5();
  }

  /**
   * Read a byte of data.
   * @see java.io.FilterInputStream
   */
  public int read() throws IOException {
    int c = in.read();

    if (c == -1)
	return -1;

    if ((c & ~0xff) != 0) {
      System.out.println("MD5InputStream.read() got character with (c & ~0xff) != 0)!");
    } else {
      md5.Update(c);
    }

    return c;
  }

  /**
   * Reads into an array of bytes.
   *
   * @see java.lang.FilterInputStream
   */
  public int read (byte bytes[], int offset, int length) throws IOException {
    int	r;

    if ((r = in.read(bytes, offset, length)) == -1)
      return r;

    md5.Update(bytes, offset, r);

    return r;
  }

  /**
   * Returns array of bytes representing hash of the stream as
   * finalized for the current state.
   * @see MD5.Final()
   */

  public byte [] hash () {
    return md5.Final();
  }
}
