/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.des.test;

import com.des.generate.GenerateFile;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Surachai
 */
public class test {

    public static void main(String[] args) {
        GenerateFile g = new GenerateFile();
        String input = "line 0 :<basiclib>\n"
                + "line 1 :    SET src = resource\\\\tranfer\\\\javalib\\\\basiclib\n"
                + "line 2 :    SET des = <%projectpath%>\\\\web\\\\WEB-INF\\\\lib\n"
                + "line 3 :    ADDFOLDER(src,des);\n"
                + "line 4 :</basiclib>";
        //System.out.println(g.scantag("<%", "%>", input));
        Map<String,Object> parameter = new HashMap<String,Object>();
        parameter.put("projectpath", "path");
        parameter.put("projectname", "name");
        parameter.put("packagename", "package");
        parameter.put("url", "url");
        
        g.ReadCommandTag("C:\\Users\\Surachai\\Documents\\NetBeansProjects\\JSpringGenerate\\resource\\command.xml","basiclib",null);
    }
}
