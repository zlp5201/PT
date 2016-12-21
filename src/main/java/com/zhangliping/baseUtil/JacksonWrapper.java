/**
 * 
 */
package com.zhangliping.baseUtil;

/**
 * @author zhangliping
 *
 */

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser.Feature;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;

public class JacksonWrapper {
    private static final ThreadLocal<ObjectMapper> INCLUDE_NULL_MAPPER = new ThreadLocal<ObjectMapper>();
    private static final ThreadLocal<ObjectMapper> NOT_INCLUDE_NULL_MAPPER = new ThreadLocal<ObjectMapper>();

    private static ObjectMapper getMapper(boolean serializeNull) {
        ThreadLocal<ObjectMapper> tl = serializeNull ? INCLUDE_NULL_MAPPER : NOT_INCLUDE_NULL_MAPPER;
        if (null == tl.get()) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.disable(
                    new DeserializationConfig.Feature[] { DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES });

            mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
            mapper.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true) ;  
            if (!serializeNull) {
                mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
                mapper.disable(new SerializationConfig.Feature[] { SerializationConfig.Feature.WRITE_NULL_MAP_VALUES });
            }
            tl.set(mapper);
        }
        return (ObjectMapper) tl.get();
    }

    public static String toJsonString(Object obj) throws JsonGenerationException, JsonMappingException, IOException {
        return toJsonString(obj, true);
    }

    public static String toJsonString(Object obj, DateFormat dateFormat)
            throws JsonGenerationException, JsonMappingException, IOException {
        return toJsonString(obj, true, dateFormat);
    }

    public static byte[] toJsonAsBytes(Object obj) throws JsonGenerationException, JsonMappingException, IOException {
        return toJsonAsBytes(obj, true);
    }

    public static void toJsonToFile(File file, Object obj)
            throws JsonGenerationException, JsonMappingException, IOException {
        toJsonToFile(file, obj, true);
    }

    public static void toJsonToOutputStream(OutputStream out, Object obj)
            throws JsonGenerationException, JsonMappingException, IOException {
        toJsonToOutputStream(out, obj, true);
    }

    public static void toJsonToWriter(Writer writer, Object obj)
            throws JsonGenerationException, JsonMappingException, IOException {
        toJsonToWriter(writer, obj, true);
    }

    public static <T> T toObject(String json, Class<T> clazz)
            throws JsonParseException, JsonMappingException, IOException {
        return (T) toObject(json, clazz, true);
    }

    public static <T> T toObject(byte[] src, Class<T> clazz)
            throws JsonParseException, JsonMappingException, IOException {
        return (T) toObject(src, clazz, true);
    }

    public static <T> T toObject(File file, Class<T> clazz)
            throws JsonParseException, JsonMappingException, IOException {
        return (T) toObject(file, clazz, true);
    }

    public static <T> T toObject(InputStream input, Class<T> clazz)
            throws JsonParseException, JsonMappingException, IOException {
        return (T) toObject(input, clazz, true);
    }

    public static <T> T toObject(Reader reader, Class<T> clazz)
            throws JsonParseException, JsonMappingException, IOException {
        return (T) toObject(reader, clazz, true);
    }

    public static <T> T toObject(URL url, Class<T> clazz) throws JsonParseException, JsonMappingException, IOException {
        return (T) toObject(url, clazz, true);
    }

    public static String toJsonString(Object obj, boolean serializeNull)
            throws JsonGenerationException, JsonMappingException, IOException {
        return getMapper(serializeNull).writeValueAsString(obj);
    }
    
    public static String toJsonString(Object obj, boolean serializeNull, DateFormat dateFormat)
            throws JsonGenerationException, JsonMappingException, IOException {
    	SerializationConfig oldSerializationConfig = getMapper(serializeNull).getSerializationConfig();
    	getMapper(serializeNull).setSerializationConfig(oldSerializationConfig.withDateFormat(dateFormat));
        String result = getMapper(serializeNull).writeValueAsString(obj);
        getMapper(serializeNull).setSerializationConfig(oldSerializationConfig);
        
        return result;
    }

    public static byte[] toJsonAsBytes(Object obj, boolean serializeNull)
            throws JsonGenerationException, JsonMappingException, IOException {
        return getMapper(serializeNull).writeValueAsBytes(obj);
    }

    public static void toJsonToFile(File file, Object obj, boolean serializeNull)
            throws JsonGenerationException, JsonMappingException, IOException {
        getMapper(serializeNull).writeValue(file, obj);
    }

    public static void toJsonToOutputStream(OutputStream out, Object obj, boolean serializeNull)
            throws JsonGenerationException, JsonMappingException, IOException {
        getMapper(serializeNull).writeValue(out, obj);
    }

    public static void toJsonToWriter(Writer writer, Object obj, boolean serializeNull)
            throws JsonGenerationException, JsonMappingException, IOException {
        getMapper(serializeNull).writeValue(writer, obj);
    }

    public static <T> T toObject(String json, Class<T> clazz, boolean serializeNull)
            throws JsonParseException, JsonMappingException, IOException {
        return (T) getMapper(serializeNull).readValue(json, clazz);
    }

    public static <T> T toObject(byte[] src, Class<T> clazz, boolean serializeNull)
            throws JsonParseException, JsonMappingException, IOException {
        return (T) getMapper(serializeNull).readValue(src, clazz);
    }

    public static <T> T toObject(File file, Class<T> clazz, boolean serializeNull)
            throws JsonParseException, JsonMappingException, IOException {
        return (T) getMapper(serializeNull).readValue(file, clazz);
    }

    public static <T> T toObject(InputStream input, Class<T> clazz, boolean serializeNull)
            throws JsonParseException, JsonMappingException, IOException {
        return (T) getMapper(serializeNull).readValue(input, clazz);
    }

    public static <T> T toObject(Reader reader, Class<T> clazz, boolean serializeNull)
            throws JsonParseException, JsonMappingException, IOException {
        return (T) getMapper(serializeNull).readValue(reader, clazz);
    }

    public static <T> T toObject(URL url, Class<T> clazz, boolean serializeNull)
            throws JsonParseException, JsonMappingException, IOException {
        return (T) getMapper(serializeNull).readValue(url, clazz);
    }
}
