/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.des.generate;


public class Properties {
    private String TAG_DATA_XML = "<!--<add data>-->";
    private String TAG_DATA_JAVA = "//generate code";
    private String FILE_PATH;
    private String OUTPUT_PATH;
    private String TEMPLATE_PATH;
    private String TRANFER_PATH;
    private String TAG_LOOP = "<Generate:loop>";
    private String TAG_LOOP_END = "</Generate:loop>";

    public String getFILE_PATH() {
        return FILE_PATH;
    }

    public void setFILE_PATH(String FILE_PATH) {
        this.FILE_PATH = FILE_PATH;
    }

    public String getOUTPUT_PATH() {
        return OUTPUT_PATH;
    }

    public void setOUTPUT_PATH(String OUTPUT_PATH) {
        this.OUTPUT_PATH = OUTPUT_PATH;
    }

    public String getTEMPLATE_PATH() {
        return TEMPLATE_PATH;
    }

    public void setTEMPLATE_PATH(String TEMPLATE_PATH) {
        this.TEMPLATE_PATH = TEMPLATE_PATH;
    }

    public String getTRANFER_PATH() {
        return TRANFER_PATH;
    }

    public void setTRANFER_PATH(String TRANFER_PATH) {
        this.TRANFER_PATH = TRANFER_PATH;
    }

    public String getTAG_DATA_XML() {
        return TAG_DATA_XML;
    }

    public String getTAG_DATA_JAVA() {
        return TAG_DATA_JAVA;
    }

    public String getTAG_LOOP() {
        return TAG_LOOP;
    }

    public String getTAG_LOOP_END() {
        return TAG_LOOP_END;
    }

    
}
