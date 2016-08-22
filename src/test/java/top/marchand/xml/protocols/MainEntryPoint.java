/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package top.marchand.xml.protocols;

import java.io.InputStream;
import java.net.URL;

/**
 *
 * @author cmarchand
 */
public class MainEntryPoint {
    
    public static void main(String[] args) throws Exception {
        StringBuilder sb = new StringBuilder();
        for(String s:args) {
            sb.append(s).append(" ");
        }
        System.out.println("MainEntryPoint "+sb.toString());
        InputStream is = new URL("cp:/top/marchand/xml/protocols/file.test").openConnection().getInputStream();
        if(is==null) throw new IllegalStateException("cp: protocol not installed");
    }
}
