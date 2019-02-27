package com.rpc.util;


import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;

import com.rpc.exception.DataInconsistentException;
import com.rpc.exception.UnkownException;

public final class JsonUtil {

	static final ObjectMapper ObjMpr = new ObjectMapper();

	public static <T> T toObject(String json, Class<T> type) {
		if (StringUtils.isBlank(json)) {
			return null;
		}
		try {
			return ObjMpr.readValue(json, type);
		} catch (Exception e) {
			throw new DataInconsistentException(e);
		}
	}

	public static String toJson(Object obj) {
		try {
			return ObjMpr.writeValueAsString(obj);
		} catch (Exception e) {
			throw new DataInconsistentException(e);
		}
	}

	@SuppressWarnings({ "deprecation", "rawtypes" })
	public static  Collection toCollectionObject(String content,
			Class<? extends Collection> collectionType, Class beanType) {
		if (StringUtils.isEmpty(content) || collectionType == null
				|| beanType == null) {
			return null;
		}

		try {
			return ObjMpr.readValue(content,
					TypeFactory.collectionType(collectionType, beanType));
		} catch (JsonParseException e) {
			throw new RuntimeException("cannot parse:" + content, e);
		} catch (JsonMappingException e) {
			throw new RuntimeException("cannot parse:" + content, e);
		} catch (IOException e) {
			throw new RuntimeException("cannot parse:" + content, e);
		}

	}
	
	
	public static void writeValue(OutputStream out, Object value){
		try {
			ObjMpr.writeValue(out, value);
		} catch (Exception e) {
			new UnkownException("序列化返回失�?");
		}
	}
}
