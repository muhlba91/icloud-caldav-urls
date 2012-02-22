package com.niftyside.icloud.calendars.handlers;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.niftyside.icloud.calendars.exceptions.XMLException;

/**
 * Reads a XML string and parses it into {@link CalendarInfo}s.
 * 
 * @author Daniel Muehlbachler
 * @copyright 2012 Daniel Muehlbachler
 * 
 * @see {@link http://icloud.niftyside.com}
 * 
 * @version 1.0
 * 
 */
public class XMLReader {
	/* * * * * Variables * * * * */

	/** All nodes. */
	private List<Node> nodes;
	/** Already analyzed? */
	private boolean analyzed;
	/** All responses. */
	private List<CalendarInfo> responses;

	/* * * * * Constructor * * * * */

	/**
	 * Initializes a new XML Reader.
	 * 
	 * @since 1.0
	 */
	public XMLReader() {
		nodes = new ArrayList<Node>();
		responses = new ArrayList<CalendarInfo>();
		analyzed = false;
	}

	/**
	 * Initializes a new XML reader.
	 * 
	 * @param xml
	 *            the XML to use
	 * @throws XMLException
	 *             if an error occurs
	 * 
	 * @since 1.0
	 */
	public XMLReader(String xml) throws XMLException {
		nodes = new ArrayList<Node>();
		responses = new ArrayList<CalendarInfo>();
		analyzed = false;

		parse(xml);
	}

	/* * * * * Methods * * * * */

	/**
	 * Parses a new XML string.
	 * 
	 * @param xml
	 *            the XML to parse
	 * @throws XMLException
	 *             if an error occurs
	 * 
	 * @since 1.0
	 */
	public void setXML(String xml) throws XMLException {
		analyzed = false;
		responses = new ArrayList<CalendarInfo>();
		nodes = new ArrayList<Node>();

		parse(xml);
	}

	/**
	 * Gets the principal ID.
	 * 
	 * @return the principal ID
	 * 
	 * @since 1.0
	 */
	public String getPrincipal() {
		Node node;
		NodeList list;

		// find propstat
		node = nodes.get(0);
		list = node.getChildNodes();
		for (int i = 0; i < list.getLength(); i++)
			if (list.item(i).getNodeName().equalsIgnoreCase("propstat"))
				node = list.item(i);

		// find prop
		list = node.getChildNodes();
		for (int i = 0; i < list.getLength(); i++)
			if (list.item(i).getNodeName().equalsIgnoreCase("prop"))
				node = list.item(i);

		// find current-user-principal
		list = node.getChildNodes();
		for (int i = 0; i < list.getLength(); i++)
			if (list.item(i).getNodeName()
					.equalsIgnoreCase("current-user-principal"))
				node = list.item(i);

		// get href
		list = node.getChildNodes();
		for (int i = 0; i < list.getLength(); i++)
			if (list.item(i).getNodeName().equalsIgnoreCase("href"))
				node = list.item(i);
		String href = node.getTextContent();

		// parse href
		String[] temp = href.split("/");
		return temp[1];
	}

	/**
	 * Gets all calendars.
	 * 
	 * @param server
	 *            the server
	 * @return all calendar responses
	 * 
	 * @since 1.0
	 */
	public List<CalendarInfo> getResponses(String server) {
		if (!analyzed) {
			analyzeCalendars(server);
			analyzed = true;
		}

		return responses;
	}

	/* * * * * Private methods * * * * */

	/**
	 * Parses the given XML string.
	 * 
	 * @param xml
	 *            the xml to parse
	 * @throws @throws XMLException if an error occurs
	 * 
	 * @since 1.0
	 */
	private void parse(String xml) throws XMLException {
		// get parser factories and parse XML
		Document doc = null;
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			InputSource input = new InputSource(new StringReader(xml));
			doc = builder.parse(input);
		} catch (Exception e) {
			throw new XMLException("Error: can't create XML builder.");
		}

		// get the root element
		Element root = doc.getDocumentElement();

		// get responses
		NodeList list = root.getElementsByTagName("response");

		// iterate through nodes and save them
		for (int i = 0; i < list.getLength(); i++)
			nodes.add(list.item(i));
	}

	/**
	 * Analyzes all calendars and saves their information.
	 * 
	 * @param server
	 *            the server
	 * 
	 * @since 1.0
	 */
	private void analyzeCalendars(String server) {
		NodeList list;
		CalendarInfo r;

		for (Node node : nodes) {
			r = new CalendarInfo();
			r.setServer(server);

			// parse node
			list = node.getChildNodes();
			for (int i = 0; i < list.getLength(); i++) {
				if (list.item(i).getNodeName().equalsIgnoreCase("href"))
					r.setHref(list.item(i).getTextContent());
				if (list.item(i).getNodeName().equalsIgnoreCase("propstat"))
					node = list.item(i);
			}

			// we have found our propstat, so parse again
			list = node.getChildNodes();
			for (int i = 0; i < list.getLength(); i++)
				if (list.item(i).getNodeName().equalsIgnoreCase("prop"))
					node = list.item(i);

			// search displayname
			list = node.getChildNodes();
			for (int i = 0; i < list.getLength(); i++)
				if (list.item(i).getNodeName().equalsIgnoreCase("displayname"))
					node = list.item(i);
			r.setName(node.getTextContent());

			// add response
			if (!r.getHref().equalsIgnoreCase("/"))
				responses.add(r);
		}
	}
}
