package com.ybase.dorm.xmlsql;

import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.ybase.dorm.bas.DormUtil;
import com.ybase.dorm.vo.Page;

public class SqlXmlFactory {
	private static final Logger log = Logger.getLogger(SqlXmlFactory.class);
	private static DocumentBuilderFactory docBuilderFac = null;
	private static DocumentBuilder docBuilder = null;
	private static Document xmlSql = null;
	private static Element root = null;
	// private static final String XML_NODE_ROOT = "sqlxml";
	private static final String XML_NODE_SQL = "sql";
	private static final String XML_NODE_OP = "op";
	private static final String XML_NODE_AS = "as";
	private static final String XML_NODE_WHERE = "where";
	private static final String XML_NODE_AND = "and";
	private static final String XML_NODE_OR = "or";

	static {
		try {
			docBuilderFac = DocumentBuilderFactory.newInstance();
			docBuilder = docBuilderFac.newDocumentBuilder();
			xmlSql = docBuilder.parse(SqlXmlFactory.class.getResourceAsStream("sql.xml"));
			root = xmlSql.getDocumentElement();
			log.info("SQL XML 框架加载成功!");
			// TODO 检查sqlxml 合法性
		} catch (Exception e) {
			log.info("SQL XML 框架加载失败!");
			log.error(e.getMessage(), e);
		}
	}

	public static Object[] getXmlSqlById(String id) throws Exception {
		if (DormUtil.isNullOrEmpty(id)) {
			throw new Exception("Sql Xml id 为空");
		}

		NodeList sqls = root.getElementsByTagName(XML_NODE_SQL);
		for (int i = 0; i < sqls.getLength(); i++) {
			Node node = sqls.item(i);
			NamedNodeMap attrs = node.getAttributes();
			if (attrs != null && attrs.getLength() > 0) {
				for (int j = 0; j < attrs.getLength(); j++) {
					Attr attr = (Attr) attrs.item(j);
					if ("id".equals(attr.getName()) && id.equals(attr.getValue())) {
						NodeList childs = node.getChildNodes();
						return dealSqlSyntax(childs);
					}
				}
			} else {
				throw new Exception("Sql 标签不合法");
			}
		}
		throw new Exception("Sql Xml 中找不指定id的服务");
	}

	public static Object[] getXmlSqlById(String id, Page page) throws Exception {
		Object[] rst = getXmlSqlById(id);
		if (rst.length > 0) {
			if (page != null) {
				rst[0] = DormUtil.appendPage((String) rst[0], page);
			}
			return rst;
		} else {
			throw new Exception("获取sql xml 失败");
		}
	}

	private static Object[] dealSqlSyntax(NodeList childs) throws Exception {
		StringBuffer sql = new StringBuffer();
		Vector<String> columns = new Vector<String>();
		for (int i = 0; i < childs.getLength(); i++) {
			Node node = childs.item(i);
			String nodeValue = node.getNodeValue() != null ? DormUtil.trim(node.getNodeValue()) : "";
			int nodeType = node.getNodeType();
			String nodeName = node.getNodeName().trim();
			if (Node.TEXT_NODE == nodeType) {
				if (!"".equals(nodeValue)) {
					sql.append(" ").append(nodeValue.trim());
				}
			} else {
				if (XML_NODE_OP.equals(nodeName)) {
					if (DormUtil.isNullOrEmpty(node.getTextContent())) {
						throw new Exception("SQL op 类型错误");
					}
					sql.append(" ").append(node.getTextContent());
				} else if (XML_NODE_AS.equals(nodeName)) {
					if (DormUtil.isNullOrEmpty(node.getTextContent())) {
						throw new Exception("SQL as 类型错误");
					}

					columns.add(DormUtil.trim(node.getTextContent()));
					sql.append(" ").append(XML_NODE_AS).append(" ").append(node.getTextContent());
					if (node.hasAttributes()) {
						sql.append(" ");
					} else {
						sql.append(",");
					}
				} else if (XML_NODE_WHERE.equals(nodeName)) {
					sql.append(" ").append(XML_NODE_WHERE);
					apendSqlCon(sql, node);
				} else {
					throw new Exception("SQL XML非法标签");
				}
			}
		}

		log.info("SQL XML 框架sql 语句:" + sql.toString());
		log.info("SQL XML 框架sql 列:" + columns.toArray().toString());
		return new Object[] { sql.toString(), columns };
	}

	private static void apendSqlCon(StringBuffer sql, Node parent) throws Exception {
		NodeList nodes = parent.getChildNodes();
		int count = 0;
		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);
			String nodeName = node.getNodeName().trim();
			if (XML_NODE_AND.equals(nodeName)) {
				if (DormUtil.isNullOrEmpty(node.getTextContent())) {
					throw new Exception("SQL and 类型错误");
				}

				if (count != 0) {
					sql.append(" ").append(XML_NODE_AND);
				}
				sql.append(" ").append(node.getTextContent());
				count++;
			} else if (XML_NODE_OR.equals(nodeName)) {
				if (DormUtil.isNullOrEmpty(node.getTextContent())) {
					throw new Exception("SQL or 类型错误");
				}

				if (count != 0) {
					sql.append(" ").append(XML_NODE_OR);
				}
				sql.append(" ").append(node.getTextContent());
				count++;
			}
		}
	}

	public static void main(String args[]) throws Exception {
		Page page = new Page();
		page.setCurrent(1);
		page.setTotalRecord(22);
		Object[] rst = SqlXmlFactory.getXmlSqlById("selectBlogPage", page);
		System.out.println(rst[0] + " --> " + rst[1]);
	}

}
