package com.yusys.agile.utils;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description XStream工具类
 */
public class XStreamUtils {

    private static final Map<String, XStream> XStreamMap = new ConcurrentHashMap<String, XStream>();

    /**
     * 获取xstream对象
     *
     * @param clazz
     * @return
     */
    public static XStream getXStream(Class clazz) {
        XStream xStream = null;
        if (null == clazz.getAnnotation(XStreamAlias.class)) {
            return null;
        }

        xStream = XStreamMap.get(clazz.getName());
        if (null == xStream) {
            synchronized (XStreamUtils.class) {
                xStream = XStreamMap.get(clazz.getName());
                if (null == xStream) {
                    xStream = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));
                    xStream.processAnnotations(clazz);
                    XStreamMap.put(clazz.getName(), xStream);
                }
            }
        }
        return xStream;
    }
}