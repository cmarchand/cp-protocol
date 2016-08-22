/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package top.marchand.xml.protocols;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author cmarchand
 */
public class ProtocolInstallerTestStartManually {
    
    @Test
    public void testMain() {
        String[] args = {"top.marchand.xml.protocols.MainEntryPoint", "1", "2", "3"};
        try {
            MainEntryPoint.main(args);
        } catch(Throwable ex) {
            ex.printStackTrace(System.err);
            fail("Problem while starting ProtocolInstaller with sub-application");
        }
    }
    
}
