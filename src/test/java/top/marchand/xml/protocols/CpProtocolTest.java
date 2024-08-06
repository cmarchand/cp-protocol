/**
 * This Source Code Form is subject to the terms of 
 * the Mozilla Public License, v. 2.0. If a copy of 
 * the MPL was not distributed with this file, You 
 * can obtain one at https://mozilla.org/MPL/2.0/.
 */
package top.marchand.xml.protocols;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.URL;
import static org.junit.Assert.*;

import com.sun.jdi.InvalidModuleException;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author cmarchand
 */
public class CpProtocolTest {

    @BeforeClass
    public static void intialize() {
        ProtocolInstaller.registerAdditionalProtocols();
    }
    
    @Test
    public void createUrl() throws Exception {
        new URL("cp:/top/marchand/xml/protocols/file.test");
        assertTrue(true);
    }
    
    @Test
    public void loadFile() throws Exception {
        InputStream is = new URL("cp:/top/marchand/xml/protocols/file.test").openConnection().getInputStream();
        assertNotNull("InputStream is null !",is);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String s = br.readLine();
        assertEquals("The read line is not XXXXX", "XXXXX", s);
        is.close();
    }

    @Test
    public void notExistingFile() throws Exception {
        try {
            InputStream is = new URL("cp:/top/marchand/xml/protocols/not-existing.test").openConnection().getInputStream();
            fail("For not existing files, an IOException should be thrown!");
        } catch (IOException e){
            // A thrown IOException is the expected behavior
        }
    }
}
