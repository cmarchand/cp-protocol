/**
 * This Source Code Form is subject to the terms of 
 * the Mozilla Public License, v. 2.0. If a copy of 
 * the MPL was not distributed with this file, You 
 * can obtain one at https://mozilla.org/MPL/2.0/.
 */
package top.marchand.xml.protocols.cp;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

/**
 * A URLStreamHandler for <tt>cp</tt> protocol.
 * <p><tt>cp:</tt> protocol is a protocol where resources are in classpath,
 * and <i>scheme-specific-part</i> (see {@link java.net.URI}) is the path
 * expected by {@link java.lang.Class#getResourceAsStream(java.lang.String) }</p>
 * 
 * @author Christophe Marchand &lt;christophe@marchand.top&gt;
 */
public class Handler extends URLStreamHandler {

    @Override
    protected URLConnection openConnection(URL u) throws IOException {
        if(!"cp".equals(u.getProtocol())) throw new IllegalArgumentException(u.getProtocol()+" is not supported by "+Handler.class.getName());
        return new CpUrlConnection(u);
    }
    
    public class CpUrlConnection extends URLConnection {
        private final String path;
        
        private CpUrlConnection(URL url) {
            super(url);
            path = url.toExternalForm().substring(3);
        }

        @Override
        public void connect() throws IOException {
            // nothing to do
        }

        @Override
        public InputStream getInputStream() throws IOException {
            return this.getClass().getResourceAsStream(path);
        }
        
    }
    
}
