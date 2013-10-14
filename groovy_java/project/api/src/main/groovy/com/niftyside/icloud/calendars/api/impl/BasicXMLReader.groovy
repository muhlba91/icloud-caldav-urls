package com.niftyside.icloud.calendars.api.impl

import com.niftyside.icloud.calendars.api.exception.CalendarException
import com.niftyside.icloud.calendars.api.exception.XMLException
import com.niftyside.icloud.calendars.api.model.XMLReader
import org.xml.sax.InputSource
import org.xml.sax.SAXException

import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.parsers.ParserConfigurationException
import java.util.logging.Level
import java.util.logging.Logger

/**
 * Basic XML reader.
 *
 * Project: iCloud calendars
 * User: daniel
 * Date: 23.09.13
 * Time: 16:42
 *
 * @author Daniel Muehlbachler
 * @copyright 2011-2013 Daniel Muehlbachler
 *
 * @version 2.0.0
 */
class BasicXMLReader implements XMLReader {
	/* * * * * Variables * * * * */

	private def nodes

	/* * * * * Methods * * * * */

	@Override
	def setXML(String xml) throws XMLException {
		if(xml == null || xml.isEmpty()) {
			throw new XMLException("No XML string given!", null)
		}

		nodes = new ArrayList<org.w3c.dom.Node>()

		parseNodes(xml)
	}

	@Override
	def getPrincipal() {
		def nodeNames = ["propstat", "prop", "current-user-principal", "href"]
		def node
		def list

		node = nodes.get(0)
		nodeNames.each { searchNodeName ->
			list = node.getChildNodes()
			for(int i = 0; i < list.getLength(); i ++) {
				if(list.item(i).getNodeName().equalsIgnoreCase(searchNodeName)) {
					node = list.item(i)
				}
			}
		}

		def principal = node.getTextContent().split("/")

		principal.length > 1 ? principal[1] : "N/A"
	}

	@Override
	def getCalendars(String server) {
		def calendars = new ArrayList<com.niftyside.icloud.calendars.api.model.Calendar>()

		def calendar
		nodes.each { node ->
			calendar = parseCalendar(node, server)
			if(calendar != null && ! calendar.getName().isEmpty()) {
				calendars.add(calendar)
			}
		}

		calendars
	}

	/* * * * * Private methods * * * * */

	/**
	 * Parses the given XML string.
	 *
	 * @param xml
	 *            the xml to parse
	 * @throws @throws XMLException if an error occurs
	 *
	 * @since 2.0.0
	 */
	private def parseNodes(String xml) throws XMLException {
		def doc
		try {
			def factory = DocumentBuilderFactory.newInstance()
			def builder = factory.newDocumentBuilder()
			doc = builder.parse(new InputSource(new StringReader(xml)))
		} catch(SAXException | IOException | ParserConfigurationException e) {
			throw new XMLException("Can't create XML builder!", e)
		}

		def root = doc.getDocumentElement()
		def list = root.getElementsByTagName("response")


		for(int i = 0; i < list.getLength(); i ++) {
			nodes.add(list.item(i))
		}
	}

	/**
	 * Analyzes a node and extracts the needed calendar properties
	 *
	 * @param node
	 *            the node to analyze
	 * @param server
	 *            the server
	 *
	 * @since 2.0.0
	 */
	private def parseCalendar(org.w3c.dom.Node node, String server) {
		def calendar = null
		def nodeNames = ["propstat", "prop", "displayname"]
		def analyzationNode = node
		def list
		def tempNode

		def name
		def href = "/"

		nodeNames.each { searchNodeName ->
			list = analyzationNode.getChildNodes()
			for(int i = 0; i < list.getLength(); i ++) {
				tempNode = list.item(i)
				if(tempNode.getNodeName().equalsIgnoreCase("href")) {
					href = tempNode.getTextContent()
				}
				if(tempNode.getNodeName().equalsIgnoreCase(searchNodeName)) {
					analyzationNode = tempNode
				}
			}
		}
		name = analyzationNode.getTextContent()

		try {
			if(! href.equalsIgnoreCase("/")) {
				calendar = new DefaultCalendar(name, href, server)
			}
		} catch(CalendarException e) {
			Logger.getLogger(Calendar.class.getName()).log(Level.SEVERE, e.getMessage(), e)
		}

		calendar
	}
}
