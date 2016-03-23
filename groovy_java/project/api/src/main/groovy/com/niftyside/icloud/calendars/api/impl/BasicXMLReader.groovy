package com.niftyside.icloud.calendars.api.impl

import com.niftyside.icloud.calendars.api.exception.CalendarException
import com.niftyside.icloud.calendars.api.exception.XMLException
import com.niftyside.icloud.calendars.api.model.XMLReader
import org.w3c.dom.Node
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
 * @copyright 2011-2016 Daniel Muehlbachler
 *
 * @version 2.1.0
 */
class BasicXMLReader implements XMLReader {
	/* * * * * Variables * * * * */

	private nodes

	/* * * * * Methods * * * * */

	@Override
	def setXML(String xml) throws XMLException {
		if(xml == null || xml.empty) {
			throw new XMLException("No XML string given!", null)
		}

		nodes = []

		parseNodes(xml)
	}

	@Override
	def getPrincipal() {
		def nodeNames = ["propstat", "prop", "current-user-principal", "href"]

		def principal = iterateThroughNodes(nodeNames)?.textContent?.split("/") ?: []
		principal.length > 1 ? principal[1] : "N/A"
	}

	@Override
	def getCardDavUrl() {
		def nodeNames = ["propstat", "prop", "addressbook-home-set", "href"]

		def url = iterateThroughNodes(nodeNames)?.textContent ?: ""
		url?.empty ? "N/A" : url
	}

	@Override
	def getCalendars(String server) {
		def calendars = []

		def calendar
		nodes.each { node ->
			calendar = parseCalendar(node, server)
			if(calendar != null && !calendar.name.empty) {
				calendars.add(calendar)
			}
		}

		calendars
	}

	/* * * * * Private methods * * * * */

	/**
	 * Iterates through the nodes to find a specific one.
	 *
	 * @param nodeNames
	 * 				the node names to iterate through
	 * @return the last node
	 *
	 * @since 2.1.0
	 */
	private Node iterateThroughNodes(ArrayList<String> nodeNames) {
		def node
		def list
		node = nodes.get(0)
		nodeNames.each { searchNodeName ->
			list = node.childNodes
			for(int i = 0; i < list.length; i++) {
				if(list.item(i).nodeName.equalsIgnoreCase(searchNodeName)) {
					node = list.item(i)
				}
			}
		}
		node
	}

	/**
	 * Parses the given XML string.
	 *
	 * @param xml
	 *            the xml to parse
	 * @throws @throws XMLException if an error occurs
	 *
	 * @since 2.0.0
	 */
	private parseNodes(String xml) throws XMLException {
		def doc
		try {
			def factory = DocumentBuilderFactory.newInstance()
			def builder = factory.newDocumentBuilder()
			doc = builder.parse(new InputSource(new StringReader(xml)))
		} catch(SAXException | IOException | ParserConfigurationException e) {
			throw new XMLException("Can't create XML builder!", e)
		}

		def root = doc.documentElement
		def list = root.getElementsByTagName("response")


		for(int i = 0; i < list.length; i++) {
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
	private parseCalendar(Node node, String server) {
		def calendar = null
		def nodeNames = ["propstat", "prop", "displayname"]
		def analyzationNode = node
		def list
		def tempNode

		def name
		def href = "/"

		nodeNames.each { searchNodeName ->
			list = analyzationNode.childNodes
			for(int i = 0; i < list.length; i++) {
				tempNode = list.item(i)
				if(tempNode.nodeName.equalsIgnoreCase("href")) {
					href = tempNode.textContent
				}
				if(tempNode.nodeName.equalsIgnoreCase(searchNodeName)) {
					analyzationNode = tempNode
				}
			}
		}
		name = analyzationNode.textContent
		if(name.empty || name.trim().equalsIgnoreCase(href)) {
			name = "N/A"
		}

		try {
			if(!href.equalsIgnoreCase("/")) {
				calendar = new DefaultCalendar(name, href, server)
			}
		} catch(CalendarException e) {
			Logger.getLogger(Calendar.class.name).log(Level.SEVERE, e.message, e)
		}

		calendar
	}
}
