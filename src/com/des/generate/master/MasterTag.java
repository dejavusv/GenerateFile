/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.des.generate.master;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Surachai
 */
public class MasterTag {
    private String name;
    private Map<String,Object> parameter;
    private List<String> command;
    private boolean existsFile;
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Map<String, Object> getParameter() {
        return parameter;
    }

    public void setParameter(Map<String, Object> paramiter) {
        this.parameter = paramiter;
    }

    public List<String> getCommand() {
        return command;
    }

    public void setCommand(List<String> command) {
        this.command = command;
    }
    
    public void AddCommand(String command) {
        this.command.add(command);
    }
    
    @Override
    public String toString(){
        
        return "name :"+this.name +" paramiter :"+this.parameter +"command :"+this.command;
        
    }

    public boolean isExistsFile() {
        return existsFile;
    }

    public void setExistsFile(boolean existsFile) {
        this.existsFile = existsFile;
    }

}
