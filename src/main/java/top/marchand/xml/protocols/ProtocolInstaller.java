/**
 * This Source Code Form is subject to the terms of 
 * the Mozilla Public License, v. 2.0. If a copy of 
 * the MPL was not distributed with this file, You 
 * can obtain one at https://mozilla.org/MPL/2.0/.
 */
package top.marchand.xml.protocols;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * A helper class to install CP protocol.
 * @author cmarchand
 */
public class ProtocolInstaller {
    
    /**
     * Register a new package for protocol handlers
     */
    public static void registerAdditionalProtocols() {
        final String packageName = ProtocolInstaller.class.getPackage().getName();
        final String protocolPathProp = "java.protocol.handler.pkgs";
        String uriHandlers = System.getProperty(protocolPathProp, "");
        if (!uriHandlers.contains(packageName)) {
            if (uriHandlers.length() != 0) {
                uriHandlers += "|";
            }
            uriHandlers += packageName;
            System.setProperty(protocolPathProp, uriHandlers);
        }
    }
    
    /**
     * Entry point.
     * This can be used to start another program, installing protocols before.
     * {@code java -cp ...top.marchand.xml.protocols.ProtocolInstaller ClassToStart param1 param2 ...}
     * This install protocols, and then starts {@code ClassToStart} as if ClassToStart was started normally.
     * 
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        registerAdditionalProtocols();
        if(args.length>0) {
            String className = args[0];
            String[] argv = Arrays.copyOfRange(args, 1, args.length);
            try {
                Class clazz = Class.forName(className);
                Method m = clazz.getMethod("main", String[].class);
                m.invoke(null, (Object)argv);
            } catch(ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                ex.printStackTrace(System.err);
                System.exit(1);
            }
        }
    }
    
}
