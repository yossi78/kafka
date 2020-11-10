package com.demo.kafka.utils;

import org.json.JSONObject;
import org.json.XML;





public class XmlUtil {


    public static String convertToXML(JSONObject json , String root) {
        String xml = "<?xml version=\"1.0\" encoding=\"ISO-8859-15\"?>\n<"+root+">" + XML.toString(json) + "</"+root+">";
        return xml;
    }


    public static JSONObject convertToJSONObject(String xml) {
        JSONObject jsonObject = XML.toJSONObject(xml);
        jsonObject = (JSONObject) jsonObject.get(jsonObject.keys().next());
        return jsonObject;
    }



    public static String convertToPretty(JSONObject jsonObject){
        int PRETTY_PRINT_INDENT_FACTOR = 4;
        String jsonPrettyPrintString = jsonObject.toString(PRETTY_PRINT_INDENT_FACTOR);
        return jsonPrettyPrintString;
    }

}
