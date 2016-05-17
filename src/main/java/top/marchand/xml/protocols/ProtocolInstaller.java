/**
 * This Source Code Form is subject to the terms of 
 * the Mozilla Public License, v. 2.0. If a copy of 
 * the MPL was not distributed with this file, You 
 * can obtain one at https://mozilla.org/MPL/2.0/.
 */
package top.marchand.xml.protocols;

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
    
}
