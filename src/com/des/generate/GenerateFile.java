/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.des.generate;

import com.des.generate.master.MasterTag;
import java.io.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.simple.JSONObject;

/**
 *
 * @author Surachai
 */
public class GenerateFile {

    private static final String TAG_DATA_XML = "<!--<add data>-->";
    private static final String TAG_DECLARE_JAVA = "//generate code";
    private static final String TAG_DECLARE_VARIABLE = "//generate variable";
    private static final String TAG_LOOP = "<Generate:loop>";
    private static final String TAG_LOOP_END = "</Generate:loop>";
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";
    private static final String FILE_NOT_FOUND = "file not found";

    public static void main(String[] args) throws IOException {
        GenerateFile g = new GenerateFile();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("packagename", "com.to.spring");
        map.put("projectname", "testGenerate");
        map.put("modelname", "entity");

        // g.MappingTemplateFile("C:/Users/Surachai/Documents/NetBeansProjects/JSpringGenerate/resource/file/java/datalayer/entity/entity.java", map, "C:/Users/Surachai/Documents/NetBeansProjects/JSpringGenerate/resource/output/entity.java");
        List<String> typeList = new LinkedList<String>();
        // typeList.add("CUSTOMER");
        // typeList.add("PROJECT_ID");
        map.put("fieldname", typeList);
        map.put("returntype", "LIST<model>");
        map.put("functionname", "getProject");
        map.put("modelname", "Project");
        map.put("titlename", "project");
        map.put("tagname", "SELECT_BY_ID");

        // g.addDataToFile( "C:/Users/Surachai/Documents/NetBeansProjects/JSpringGenerate/resource/output/entity.java", "C:/Users/Surachai/Documents/NetBeansProjects/JSpringGenerate/resource/template/model.template", map, TAG_DECLARE_VARIABLE);
        //g.addDataToFile( "C:/Users/Surachai/Documents/NetBeansProjects/JSpringGenerate/resource/output/entity.java", "C:/Users/Surachai/Documents/NetBeansProjects/JSpringGenerate/resource/template/JDBCFunction_select.template", map, TAG_DECLARE_JAVA);
    }

    public String MappingTemplateFile(String templateFilename, Map<String, Object> map, String outputPath) throws IOException {
        String result = "";
        List<String> input = readFile(templateFilename);
        if (input.size() == 1) {
            if (IsFail(input.get(0))) {
                return input.get(0);
            }
        }
        input = Mapdata(input, map);
        if (input.size() == 1) {
            if (IsFail(input.get(0))) {
                return input.get(0);
            }
        }
        return writefile(input, setPackagenameTopath(outputPath));
    }

    public String setPackagenameTopath(String outputPath) {
        if (outputPath.indexOf(".") != -1) {
            String temp = outputPath;
            int count = 0;
            while (temp.indexOf(".") != -1) {
                temp = temp.replaceFirst("\\.", "/");
                count++;
            }
            if (count > 1) {
                for (int i = 0; i < count - 1; i++) {
                    outputPath = outputPath.replaceFirst("\\.", "/");
                }
            }
        }
        return outputPath;
    }

    public String addDataToFile(String Filename, String template, Map<String, Object> map, String type) throws IOException {

        List<String> input = readFile(setPackagenameTopath(Filename));
        if (input.size() == 1) {
            if (IsFail(input.get(0))) {
                return input.get(0);
            }
        }
        List<String> TemplateData = readFile(template);
        if (TemplateData.size() == 1) {
            if (IsFail(TemplateData.get(0))) {
                return TemplateData.get(0);
            }
        }
        TemplateData = Mapdata(TemplateData, map);
        if (TemplateData.size() == 1) {
            if (IsFail(TemplateData.get(0))) {
                return TemplateData.get(0);
            }
        }
        // if (CompareFileandTemplate(input, TemplateData)) {
        //     return FAIL + " :This has in file already ,confirm to add it?";
        // }
        int indexAddData = 0;
        String searchTag = type;

        for (int i = 0; i < input.size(); i++) {
            if (input.get(i).indexOf(searchTag) != -1) {
                indexAddData = i;
            }
        }
        if (indexAddData != 0) {
            for (int j = TemplateData.size() - 1; j >= 0; j--) {
                input.add(indexAddData, TemplateData.get(j));
            }
        }
        return writefile(input, setPackagenameTopath(Filename));
    }

    public boolean CompareFileandTemplate(List<String> inputFile, List<String> TemplateData) {
        int countOFMatch = 0;
        for (int i = 0; i < inputFile.size(); i++) {
            countOFMatch = 0;
            for (int j = 0; j < TemplateData.size(); j++) {
                if (i + j >= inputFile.size()) {
                    return false;
                }
                if (inputFile.get(i + j).equalsIgnoreCase(TemplateData.get(j))) {
                    countOFMatch += 1;
                    if (countOFMatch == TemplateData.size()) {
                        return true;
                    }
                } else {
                    j += TemplateData.size();
                }
            }
        }
        return false;
    }

    public List<String> readFile(String filename) {
        List<String> data = new LinkedList<>();
        try {
            FileInputStream fstream = new FileInputStream(filename);
            DataInputStream instream = new DataInputStream(fstream);
            BufferedReader bf = new BufferedReader(new InputStreamReader(instream));
            String line;
            while ((line = bf.readLine()) != null) {
                data.add(line);
            }
            instream.close();
        } catch (Exception e) {
            data = new LinkedList<>();
            data.add(FAIL + "because :" + e.toString());
            return data;
        }
        return data;
    }

    public boolean IsFail(String input) {
        if ((input != null) && (input.length() >= 4)) {
            if (input.substring(0, 4).equalsIgnoreCase(FAIL)) {
                return true;
            }
        }
        return false;
    }

    public String readFileToString(String filename) {
        String data = "";
        try {
            FileInputStream fstream = new FileInputStream(filename);
            DataInputStream instream = new DataInputStream(fstream);
            BufferedReader bf = new BufferedReader(new InputStreamReader(instream));
            String line;
            while ((line = bf.readLine()) != null) {
                data += line;
            }
            instream.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return data;
    }

    public List<String> Mapdata(List<String> dataFile, Map<String, Object> map) {
        List<String> keySet = getKeyFromMap(map);
        String LoopList = "";
        int IsLoop = 0;
        int indexAddData = 0;
        int indexRemoveData = 0;
        String function = null;
        String parameter = null;
        //System.out.println(dataFile);
        try {

            for (int i = 0; i < dataFile.size(); i++) {
                if (dataFile.get(i).indexOf(TAG_LOOP) != -1) {
                    List<String> command = readFunction(dataFile.get(i));
                    if (command != null) {
                        function = command.get(0);
                        parameter = command.get(1);
                    }
                    indexAddData = i;
                    i++;
                    IsLoop = 1;
                }
                if (dataFile.get(i).indexOf(TAG_LOOP_END) != -1) {
                    IsLoop = 0;
                    indexRemoveData = i;
                    i++;
                }
                if (IsLoop == 1) {
                    LoopList += dataFile.get(i) + "\n";
                } else {
                    if (!LoopList.equalsIgnoreCase("")) {
                        List<String> GenerateList = MapListdata(LoopList, map, keySet, function, parameter);
                        for (int l = GenerateList.size() - 1; l >= 0; l--) {
                            dataFile.add(i, GenerateList.get(l));
                        }
                        LoopList = "";
                    }
                    for (int j = 0; j < keySet.size(); j++) {
                        if (dataFile.get(i).indexOf("<%" + keySet.get(j) + "%>") != -1) {
                            dataFile.set(i, dataFile.get(i).replaceAll("<%" + keySet.get(j) + "%>", map.get(keySet.get(j)).toString()));
                        }
                    }
                }
            }
            //remove tag
            for (int i = indexAddData; i <= indexRemoveData; i++) {
                dataFile.remove(indexAddData);
            }

            // remove empty list
            List<Integer> emptyList = new LinkedList<Integer>();
            for (int i = 0; i < dataFile.size(); i++) {
                if (dataFile.get(i).equalsIgnoreCase("")) {
                    emptyList.add(i);
                }

            }
            for (int i = emptyList.size() - 1; i >= 0; i--) {
                dataFile.remove((int) emptyList.get(i));
            }

        } catch (Exception ex) {
            dataFile = new LinkedList<String>();
            dataFile.add(FAIL + " because " + ex.toString());
            return dataFile;
        }

        return dataFile;
    }

    public List<String> MapListdata(String dataFile, Map<String, Object> map, List<String> keySet, String function, String parameter) {
        List<String> result = new LinkedList<String>();
        int MaxLOOP = 0;
        List<String> SetParameter = new LinkedList<String>();
        String temp = "";
        for (int i = 0; i < keySet.size(); i++) {
            if (IsList(map.get(keySet.get(i)))) {
                SetParameter = (List<String>) map.get(keySet.get(i));
                for (int j = 0; j < SetParameter.size(); j++) {
                    if (MaxLOOP < SetParameter.size()) {
                        temp += dataFile;
                    }
                    MaxLOOP += 1;
                    //get count to replace data in template
                    int countAtt = CountOfAttibute(dataFile, keySet.get(i));
                    for (int k = 0; k < countAtt; k++) {
                        //replace data from count
                        temp = temp.replaceFirst("<%" + keySet.get(i) + "%>", SetParameter.get(j));
                    }
                }
            }
        }

        String[] splitData = temp.split("\n");
        for (int i = 0; i < splitData.length; i++) {
            result.add(splitData[i]);
        }
        //check function in Tag generate Loop
        for (int i = 0; i < result.size(); i++) {
            if (function != null) {
                if (function.equalsIgnoreCase("SUB_LAST")) {
                    if ((i == result.size() - 1) && (!result.get(i).equalsIgnoreCase(""))) {
                        if (parameter.equalsIgnoreCase(result.get(i).substring(result.get(i).length() - parameter.length()))) {
                            result.set(i, result.get(i).substring(0, result.get(i).length() - parameter.length()));
                        }
                    }
                }
            }
            System.out.println("result : " + result.get(i));
        }

        return result;
    }

    public List<String> readFunction(String input) {
        List<String> result = null;
        if (input.trim().split(TAG_LOOP).length > 1) {
            String Func_parameter = input.trim().split(TAG_LOOP)[1];
            if (Func_parameter.indexOf("SUB_LAST") != -1) {
                result = new LinkedList<String>();
                result.add("SUB_LAST");
                result.add(Func_parameter.substring(input.trim().split(TAG_LOOP)[1].indexOf("(") + 1, input.trim().split(TAG_LOOP)[1].indexOf(")")));
            }
        }
        return result;
    }

    public int CountOfAttibute(String data, String TagCompare) {
        int count = 0;
        while (data.indexOf("<%" + TagCompare + "%>") != -1) {
            //System.out.println(data.indexOf("<%attibutename%>"));
            data = data.replaceFirst("<%" + TagCompare + "%>", "");
            count++;
        }
        //System.out.println(count +","+TagCompare);
        return count;
    }

    public boolean IsList(Object obj) {
        if (obj instanceof List) {
            return true;
        } else {
            return false;
        }
    }

    public List<String> getKeyFromMap(Map<String, Object> map) {
        Set<String> key = map.keySet();
        Iterator<String> ListKey = key.iterator();
        List<String> keySet = new LinkedList<String>();
        while (ListKey.hasNext()) {
            String Tempkey = ListKey.next();
            keySet.add(Tempkey);
        }
        return keySet;
    }

    public List<String> getKeyFromJSONArray(Set<String> key) {
        Iterator<String> ListKey = key.iterator();
        List<String> keySet = new LinkedList<String>();
        while (ListKey.hasNext()) {
            String Tempkey = ListKey.next();
            keySet.add(Tempkey);
        }
        return keySet;
    }

    public String writefile(List<String> input, String outputpath) {
        String result = "";
        try {
            FileWriter fw = new FileWriter(outputpath);

            BufferedWriter out = new BufferedWriter(fw);
            for (int i = 0; i < input.size(); i++) {
                out.write(input.get(i));
                out.newLine();
            }
            out.close();
            result = SUCCESS;
        } catch (Exception ex) {
            result = FAIL + "because " + ex.toString();
        }

        return result;
    }

    public File[] getListFileandFolder(String pathfile) {
        File folder = new File(pathfile);
        File[] listOfFiles = folder.listFiles();
        return listOfFiles;
    }

    public List<String> getListFilename(String pathfile) {
        File[] listOfFiles = getListFileandFolder(pathfile);
        List<String> ListFile = new LinkedList<String>();
        /*
        if (listOfFiles != null) {
            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()) {
                    ListFile.add(listOfFiles[i].getName());
                }
            }
        }*/
        return ListFile;
    }

    public List<String> getListFoldername(String pathfile) {
        File[] listOfFiles = getListFileandFolder(pathfile);
        List<String> ListFolder = new LinkedList<String>();
        if (listOfFiles != null) {
            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isDirectory()) {
                    ListFolder.add(listOfFiles[i].getName());
                }
            }
        }
        return ListFolder;
    }

    public void writefile(String input, String outputpath) throws IOException {
        FileWriter fw = new FileWriter(outputpath);
        BufferedWriter out = new BufferedWriter(fw);
        out.write(input);
        out.newLine();
        out.close();
    }

    public String CopyFile(String SourcePathFile, String DesPathFile) {
        InputStream inStream = null;
        OutputStream outStream = null;
        String result = "";
        try {
            File SourceFile = new File(SourcePathFile);
            if (!SourceFile.exists()) {
                return FAIL + " because " + "file not exists";
            }
            File DestinationFile = new File(DesPathFile);
            inStream = new FileInputStream(SourceFile);
            outStream = new FileOutputStream(DestinationFile); // for override file content

            byte[] buffer = new byte[1024];
            int length;
            while ((length = inStream.read(buffer)) > 0) {
                outStream.write(buffer, 0, length);
            }
            if (inStream != null) {
                inStream.close();
            }
            if (outStream != null) {
                outStream.close();
            }
            result = SUCCESS;
        } catch (IOException e) {
            e.printStackTrace();
            result = FAIL + "because : " + e.toString();
        }
        return result;
    }

    public String copyFolder(File src, File dest) {
        String result = "";
        try {
            if (src.isDirectory()) {

                //if directory not exists, create it
                if (!dest.exists()) {
                    dest.mkdir();
                }

                //list all the directory contents
                String files[] = src.list();

                for (String file : files) {
                    //construct the src and dest file structure
                    File srcFile = new File(src, file);
                    File destFile = new File(dest, file);
                    //recursive copy
                    copyFolder(srcFile, destFile);
                }
                result = SUCCESS;
            } else {
                //if file, then copy it
                //Use bytes stream to support all file types
                InputStream in = new FileInputStream(src);
                OutputStream out = new FileOutputStream(dest);

                byte[] buffer = new byte[1024];

                int length;
                //copy the file content in bytes 
                while ((length = in.read(buffer)) > 0) {
                    out.write(buffer, 0, length);
                }
                in.close();
                out.close();
                result = SUCCESS;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            result = FAIL + " because " + ex.toString();
        }

        return result;
    }

    public String createFolder(String folderName) {
        folderName = folderName.replaceAll("\\.", "/");
        String result = "";
        try {
            if (folderName.indexOf("/") != -1) {
                String sequence[] = folderName.split("/");
                String path = sequence[0];
                for (int i = 1; i < sequence.length; i++) {
                    path += "/" + sequence[i];
                    File folder = new File(path);
                    if (!folder.exists()) {
                        folder.mkdir();
                    }
                }
            } else {
                File folder = new File(folderName);
                if (!folder.exists()) {
                    folder.mkdir();
                }
            }
            result = SUCCESS;
        } catch (Exception ex) {
            result = FAIL + "because : " + ex.toString();
        }
        return result;
    }

    public MasterTag ReadCommandTag(String pathfile, String tagname, Map<String, Object> map) {
        List<String> data = readFile(pathfile);
        MasterTag tag = new MasterTag();
        List<String> Listcommand = new LinkedList<String>();
        Map<String, Object> parameter = new HashMap<String, Object>();
        for (int i = 0; i < data.size(); i++) {
            //System.out.println("line "+i+" :"+data.get(i));
            if (data.get(i).length() > 0) {
                if (scantag("<", ">", data.get(i)).equalsIgnoreCase(tagname)) {
                    //get name
                    tag.setName(scantag("<", ">", data.get(i)));
                    System.out.println("name : " + tag.getName());
                    for (int j = i + 1; j < data.size(); j++) {
                        //get command and variable
                        if (data.get(j).trim().split(" ")[0].equalsIgnoreCase("SET")) {
                            if (!scantag("<%", "%>", data.get(j)).equalsIgnoreCase("")) {
                                while (!scantag("<%", "%>", data.get(j)).equalsIgnoreCase("")) {
                                    String parametername = scantag("<%", "%>", data.get(j));
                                    data.set(j, data.get(j).replaceAll("<%" + parametername + "%>", map.get(parametername).toString()));
                                }
                            }
                            //set parameter map from commmand.xml
                            if (data.get(j).trim().split(" ")[1].equalsIgnoreCase("map")) {
                                parameter.put("map", SetMapParameter(data.get(j).trim().substring(data.get(j).trim().indexOf("=") + 1).trim(), map));
                            } else {// set other parameter from command.xml
                                parameter.put(data.get(j).trim().split(" ")[1], data.get(j).trim().substring(data.get(j).trim().indexOf("=") + 1).trim());
                            }
                            tag.setParameter(parameter);
                        } else if (data.get(j).trim().split(" ")[0].equalsIgnoreCase("ADDFOLDER")) {
                            Listcommand.add("ADDFOLDER");
                            tag.setCommand(Listcommand);
                        } else if (data.get(j).trim().split(" ")[0].equalsIgnoreCase("MAPPING")) {
                            Listcommand.add("MAPPING");
                            tag.setCommand(Listcommand);
                        } else if (data.get(j).trim().split(" ")[0].equalsIgnoreCase("ADDFILE")) {
                            Listcommand.add("ADDFILE");
                            tag.setCommand(Listcommand);
                        } else if (data.get(j).trim().split(" ")[0].equalsIgnoreCase("CREATEFOLDER")) {
                            Listcommand.add("CREATEFOLDER");
                            tag.setCommand(Listcommand);
                        } else if (data.get(j).trim().split(" ")[0].equalsIgnoreCase("ADDDATA")) {
                            Listcommand.add("ADDDATA");
                            tag.setCommand(Listcommand);
                        }
                        if (scantag("</", ">", data.get(j)).equalsIgnoreCase(tagname)) {
                            i = j++;
                            break;
                        }
                    }
                }
            }
        }
        if (parameter.get("des") != null) {
            File CheckFile = new File(setPackagenameTopath(parameter.get("des").toString()));
            tag.setExistsFile(CheckFile.exists());
        }

        return tag;
    }

    public String RunListCommand(String CommmandPath, List<String> Listcommand, Map<String, Object> map, boolean FlagOverWrite) throws IOException {
        String result = "";
        for (int i = 0; i < Listcommand.size(); i++) {
            //check Exiting file
            MasterTag tag = ReadCommandTag(CommmandPath, Listcommand.get(i), map);

            System.out.println("tag : " + tag.getCommand().get(0));

            if (!tag.getCommand().get(0).equalsIgnoreCase("ADDDATA")) {
                if (tag.isExistsFile() && !FlagOverWrite) {
                    System.out.println("file exists!!!!");
                    return FAIL + " file (" + tag.getParameter().get("des") + ") is exists.please confirm to replace?";
                } else {
                    result = RunCommandTag(tag);
                }
            } else {
                result = RunCommandTag(tag);

            }

        }
        return result;
    }

    public String RunCommandTag(MasterTag tag) throws IOException {
        String result = "";
        GenerateFile g = new GenerateFile();
        Map<String, Object> parameter = tag.getParameter();
        if (tag.getCommand() != null) {
            List<String> command = tag.getCommand();
            for (int i = 0; i < tag.getCommand().size(); i++) {
                if (command.get(i).equalsIgnoreCase("ADDFOLDER")) {
                    result = g.copyFolder(new File(parameter.get("src").toString()), new File(parameter.get("des").toString()));
                } else if (command.get(i).equalsIgnoreCase("ADDFILE")) {
                    result = g.CopyFile(parameter.get("src").toString(), parameter.get("des").toString());
                } else if (command.get(i).equalsIgnoreCase("CREATEFOLDER")) {
                    result = g.createFolder(parameter.get("des").toString());
                } else if (command.get(i).equalsIgnoreCase("MAPPING")) {
                    result = g.MappingTemplateFile(parameter.get("src").toString(), (Map<String, Object>) parameter.get("map"), parameter.get("des").toString());
                } else if (command.get(i).equalsIgnoreCase("ADDDATA")) {
                    result = g.addDataToFile(parameter.get("des").toString(), parameter.get("src").toString(), (Map<String, Object>) parameter.get("map"), parameter.get("tag").toString());
                }

            }
        }
        return result;
    }

    private Map<String, Object> SetMapParameter(String Listparameter, Map<String, Object> map) {
        Map<String, Object> result = new HashMap<String, Object>();
        if (Listparameter.indexOf(",") != -1) {
            String[] pathParameter = Listparameter.split(",");
            for (int i = 0; i < pathParameter.length; i++) {
                result.put(pathParameter[i], map.get(pathParameter[i]));
            }
        } else {
            result.put(Listparameter, map.get(Listparameter));
        }
        return result;
    }

    private String scantagValue(String tagname, String attibutename, String data) {
        int indexStartTag = data.indexOf(tagname);
        //System.out.println(data.indexOf(tagname));
        String TagHeader = data.substring(indexStartTag, data.indexOf("/>"));
        TagHeader = TagHeader.replaceAll(tagname, "").trim();
        //System.out.println(TagHeader);
        if (TagHeader.length() > 0) {
            String valuePath[] = TagHeader.split(" ");
            for (int i = 0; i < valuePath.length; i++) {
                //System.out.println(valuePath[i]);
                if (valuePath[i].indexOf(attibutename) != -1) {
                    System.out.println(valuePath[i].replaceFirst(attibutename, "").replaceFirst("=", "").replaceAll("'", ""));
                    return valuePath[i].replaceFirst(attibutename, "").replaceFirst("=", "").replaceAll("'", "");
                }

            }
        }
        return "";
    }

    private String scantag(String tagstart, String tagend, String data) {
        if (data.indexOf(tagstart) != -1) {
            return data.trim().substring(data.trim().indexOf(tagstart) + tagstart.length(), data.trim().indexOf(tagend));
        }
        return "";
    }

    private MasterTag getTagDetail(String name) {
        MasterTag tag = new MasterTag();

        return tag;
    }

}
