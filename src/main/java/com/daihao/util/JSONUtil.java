package com.daihao.util;

import java.util.TimeZone;

import com.daihao.model.Depart;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONUtil
{
    public static String getString(Object object)
    {
        try
        {
            ObjectMapper mapper = new ObjectMapper();
            mapper.setTimeZone(TimeZone.getDefault());
            String json = mapper.writeValueAsString(object);
            return json;
        }
        catch (JsonProcessingException e)
        {
            e.printStackTrace();
        }
        return "Nothing";
    }

    public static String getPrettyString(Object object)
    {
        try
        {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
            return json;
        }
        catch (JsonProcessingException e)
        {
            e.printStackTrace();
        }
        return "Nothing";
    }

    public static Object getObject(String json, Object object)
    {
        try
        {
            ObjectMapper mapper = new ObjectMapper();
            return  object = mapper.readValue(json, object.getClass());
        }
        catch (JsonProcessingException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(getString(new Depart("RoomA", "Phòng A")));
    }
}
