package com.yusys.agile.utils;

import com.google.common.collect.Lists;
import com.thoughtworks.xstream.XStream;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 *  
 * @description xml工具类
 */
public class XmlUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(XmlUtils.class);

    private static final String CHARSET = "UTF-8";

    /**
     * 将xml转换成对象
     * @param xml xml文本
     * @param expression XPath Expressions
     * @param clazz 转换成的类
     * @param <T>
     * @return
     */
    public static <T> T transformXmlToObject(String xml, String expression, Class clazz) {
        if (StringUtils.isBlank(xml) || StringUtils.isBlank(expression) || null == clazz) {
            return null;
        }
        Document document = null;
        T object = null;
        try {
            document = createDocumentFromXml(xml);
            String xPathXml = transformNodeToString(getSpecifyNodeByXPath(document, expression));
            XStream xStream = XStreamUtils.getXStream(clazz);
            object = (T) xStream.fromXML(xPathXml);
        } catch (Exception e) {
            LOGGER.error("transformXmlToObject occur exception:{}", e.getMessage());
        }
        return object;
    }

    /**
     * 根据xml创建文档对象
     * @param xml
     * @return
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    public static Document createDocumentFromXml(String xml) throws ParserConfigurationException, SAXException, IOException {
        InputSource inputSource = new InputSource(new StringReader(xml));
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = builder.parse(inputSource);
        return document;
    }

    /**
     * 将节点转换成字符串
     * @param node
     * @return
     */
    public static String transformNodeToString(Node node) {
        String xml = "";
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, CHARSET);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            transformer.transform(new DOMSource(node), new StreamResult(bos));
            xml = bos.toString(CHARSET);
        } catch (Exception e) {
            LOGGER.error("transformNodeToString occur exception:{}", e.getMessage());
        }
        return xml;
    }

    /**
     * 查询单节点对象
     * @param document
     * @param expression
     * @return
     */
    public static Node getSpecifyNodeByXPath(Node document, String expression) {
        Node node = null;
        try {
            XPath xPath = XPathFactory.newInstance().newXPath();
            XPathExpression xPathExpression = xPath.compile(expression);
            node = (Node) xPathExpression.evaluate(document, XPathConstants.NODE);
        } catch (XPathExpressionException e) {
            LOGGER.error("getSpecifyNodeByXPath occur exception:{}", e.getMessage());
        }
        return node;
    }

    /**
     * 查询多节点对象
     * @param document
     * @param expression
     * @param nsContext
     * @return
     */
    public static NodeList getSpecifyNodesByXPath(Node document, String expression, NamespaceContext nsContext) {
        NodeList nodeList = null;
        try {
            XPath xPath = XPathFactory.newInstance().newXPath();
            if (null != nsContext) {
                xPath.setNamespaceContext(nsContext);
            }
            XPathExpression xPathExpression = xPath.compile(expression);
            nodeList = (NodeList) xPathExpression.evaluate(document, XPathConstants.NODESET);
        } catch (XPathExpressionException e) {
            LOGGER.error("getSpecifyNodesByXPath occur exception:{}", e.getMessage());
        }
        return nodeList;
    }

    /**
     * 查询节点值
     * @param document
     * @param expression
     * @return
     */
    public static String getNodeValueByXPath(Node document, String expression) {
        String value = null;
        try {
            XPath xPath = XPathFactory.newInstance().newXPath();
            XPathExpression xPathExpression = xPath.compile(expression);
            value = (String) xPathExpression.evaluate(document, XPathConstants.STRING);
        } catch (XPathExpressionException e) {
            LOGGER.error("getNodeValueByXPath occur exception:{}", e.getMessage());
        }
        return value;
    }

    /**
     * 将xml转换成对象集合
     * @param xml
     * @param expression
     * @param clazz
     * @param alias
     * @param <T>
     * @return
     */
    public static <T> List<T> transformXmlToObjects(String xml, String expression, Class<T> clazz, String alias) {
        if (StringUtils.isBlank(xml) || StringUtils.isBlank(expression) || null == clazz) {
            return Collections.emptyList();
        }
        Document document = null;
        T object = null;
        List<T> objects = Collections.emptyList();
        try {
            document = createDocumentFromXml(xml);
            NodeList nodeList = getSpecifyNodesByXPath(document, expression, null);
            Node node = null;
            int nodeLen = 0;
            String xPathXml = null;
            if (Objects.nonNull(nodeList)) {
                XStream xStream = XStreamUtils.getXStream(clazz);
                if (StringUtils.isNotBlank(alias)) {
                    xStream.alias(alias, clazz);
                }
                objects = Lists.newArrayList();
                nodeLen = nodeList.getLength();
                for (int i = 0; i < nodeLen; i++) {
                    node = nodeList.item(i);
                    xPathXml = transformNodeToString(node);
                    object = (T) xStream.fromXML(xPathXml);
                    objects.add(object);
                }
            }
        } catch (Exception e) {
            LOGGER.error("getListFromXml occur exception:{}", e.getMessage());
        }
        return objects;
    }
}