package com.niftyside.icloud.calendars.api.impl;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.niftyside.icloud.calendars.api.exception.CalendarException;
import com.niftyside.icloud.calendars.api.exception.XMLException;
import com.niftyside.icloud.calendars.api.model.Calendar;
import com.niftyside.icloud.calendars.api.model.XMLReader;

/**
 * Implementation of {@link XMLReader}.
 * 
 * @author Daniel Muehlbachler
 * @copyright 2012 Daniel Muehlbachler
 * 
 * @see http://icloud.niftyside.com
 * 
 * @version 1.1
 * 
 */
public class XMLReaderImpl implements XMLReader {
	/* * * * * Variables * * * * */

	/** All found response nodes. */
	private List<Node> nodes;

	/* * * * * Methods * * * * */

	@Override
	public void setXML(final String xml) throws XMLException {
		if (xml == null)
			throw new XMLException("Error: no XML string given.", null);

		nodes = new ArrayList<>();

		parseNodes(xml);
	}

	@Override
	public String getPrincipal() {
		final String[] nodeNames = { "propstat", "prop",
				"current-user-principal", "href" };
		Node node;
		NodeList list;

		node = nodes.get(0);
		for (final String searchNodeName : nodeNames) {
			list = node.getChildNodes();
			for (int i = 0; i < list.getLength(); i++)
				if (list.item(i).getNodeName().equalsIgnoreCase(searchNodeName))
					node = list.item(i);
		}

		final String[] href = node.getTextContent().split("/");

		return href[1];
	}

	@Override
	public List<Calendar> getCalendars(final String server) {
		final List<Calendar> calendars = new ArrayList<>();

		Calendar calendar;
		for (final Node node : nodes) {
			calendar = parseCalendar(node, server);
			if (calendar != null)
				calendars.add(calendar);
		}

		return calendars;
	}

	/* * * * * Private methods * * * * */

	/**
	 * Analyzes a node and extracts the needed calendar properties
	 * 
	 * @param node
	 *            the node to analyze
	 * @param server
	 *            the server
	 * 
	 * @since 1.1
	 */
	private Calendar parseCalendar(Node node, final String server) {
		Calendar calendar = null;
		final String[] nodeNames = { "propstat", "prop", "displayname" };
		NodeList list;
		Node tempNode;

		String name, href = "/";

		for (final String searchNodeName : nodeNames) {
			list = node.getChildNodes();
			for (int i = 0; i < list.getLength(); i++) {
				tempNode = list.item(i);
				if (tempNode.getNodeName().equalsIgnoreCase("href"))
					href = tempNode.getTextContent();
				if (tempNode.getNodeName().equalsIgnoreCase(searchNodeName))
					node = tempNode;
			}
		}
		name = node.getTextContent();

		try {
			if (!href.equalsIgnoreCase("/"))
				calendar = new CalendarImpl(name, href, server);
		} catch (final CalendarException e) {
			Logger.getLogger(Calendar.class.getName()).log(Level.SEVERE,
					e.getMessage(), e);
		}

		return calendar;
	}

	/**
	 * Parses the given XML string.
	 * 
	 * @param xml
	 *            the xml to parse
	 * @throws @throws XMLException if an error occurs
	 * 
	 * @since 1.1
	 */
	private void parseNodes(final String xml) throws XMLException {
		Document doc = null;
		try {
			final DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			final DocumentBuilder builder = factory.newDocumentBuilder();
			doc = builder.parse(new InputSource(new StringReader(xml)));
		} catch (SAXException | IOException | ParserConfigurationException e) {
			throw new XMLException("Error: can't create XML builder.", e);
		}

		final Element root = doc.getDocumentElement();
		final NodeList list = root.getElementsByTagName("response");

		for (int i = 0; i < list.getLength(); i++)
			nodes.add(list.item(i));
	}
}
