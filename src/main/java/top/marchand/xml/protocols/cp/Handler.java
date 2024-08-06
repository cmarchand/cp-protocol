/**
 * This Source Code Form is subject to the terms of 
 * the Mozilla Public License, v. 2.0. If a copy of 
 * the MPL was not distributed with this file, You 
 * can obtain one at https://mozilla.org/MPL/2.0/.
 */
package top.marchand.xml.protocols.cp;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

/**
 * A URLStreamHandler for {@code cp} protocol.
 * <p>{@code cp:} protocol is a protocol where resources are in classpath,
 * and <i>scheme-specific-part</i> (see {@link java.net.URI}) is the path
 * expected by {@link java.lang.Class#getResourceAsStream(java.lang.String) }</p>
 * 
 * @author Christophe Marchand &lt;christophe@marchand.top&gt;
 */
public class Handler extends URLStreamHandler {
    private static final ThreadLocal<ClassLoader> CP_THREAD_LOCAL = new ThreadLocal<>();
    
    /**
     * Set the ClassLoader to load resources from, for the current thread.
     * Behing the scene, a {@code ThreadLocal&lt;ClassLoader&gt;} is used.
     * If no ClassLoader is specified, {@code Thread.currrentThread().getContextClassLoader()} is used.
     * @param cl The class loader to use for the current thread
     */
    public static void setClassLoader(final ClassLoader cl) {
        CP_THREAD_LOCAL.set(cl);
    }

    @Override
    protected URLConnection openConnection(URL u) throws IOException {
        if(!"cp".equals(u.getProtocol())) throw new IllegalArgumentException(u.getProtocol()+" is not supported by "+Handler.class.getName());
        return new CpUrlConnection(u);
    }
    
    public class CpUrlConnection extends URLConnection {
        private final String path;
        
        private CpUrlConnection(URL url) throws MalformedURLException {
            super(url);
            // suppress the cp:
            String sTmp = url.toExternalForm().substring(3);
            if(!sTmp.startsWith("/")) throw new MalformedURLException("cp: URL must be absolute. i.e. start with 'cp:/'");
            path = sTmp.substring(1);
        }

        @Override
        public void connect() throws IOException {
            // nothing to do
        }

        @Override
        public InputStream getInputStream() throws IOException {
            ClassLoader cl = Handler.CP_THREAD_LOCAL.get();
            if(cl==null) cl = Thread.currentThread().getContextClassLoader();
            InputStream inputStream = cl.getResourceAsStream(path);
            if(inputStream==null)
                throw new IOException("Could not find resource " + path);
            return cl.getResourceAsStream(path);
        }
        
    }
    
}
