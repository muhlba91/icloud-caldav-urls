package com.niftyside.icloud.calendars.api.impl

import com.niftyside.icloud.calendars.api.exception.RequestException
import com.niftyside.icloud.calendars.api.model.RequestMaker
import org.w3c.dom.Document

import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.parsers.ParserConfigurationException
import javax.xml.transform.TransformerException
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

/**
 * Basic implementation of the request maker.
 *
 * Project: iCloud calendars
 * User: daniel
 * Date: 23.09.13
 * Time: 17:12
 *
 * @author Daniel Muehlbachler
 * @copyright 2011-2013 Daniel Muehlbachler
 *
 * @version 2.0.0
 */
class BasicRequestMaker implements RequestMaker {
	/* * * * * Variables * * * * */

	private final def builder

	/* * * * * Constructor * * * * */

	/**
	 * Initializes a new request maker.
	 *
	 * @throws RequestException
	 *             if the initialization of the builder failed
	 *
	 * @since 2.0.0
	 */
	BasicRequestMaker() throws RequestException {
		def factory = DocumentBuilderFactory.newInstance()
		try {
			builder = factory.newDocumentBuilder()
		} catch(ParserConfigurationException e) {
			throw new RequestException("Can't create request builder!", e)
		}
	}

	/* * * * * Methods * * * * */

	@Override
	def makePropfindRequest(String requestType) throws RequestException {
		def doc = builder.newDocument()

		createRequest(doc, requestType)

		return writeToString(doc)
	}

	/* * * * * Private methods * * * * */

	/**
	 * Creates a new request.
	 *
	 * @param doc
	 *            the document
	 * @param requestType
	 *            the request type
	 *
	 * @since 2.0.0
	 */
	private def createRequest(Document doc, String requestType) {
		def root = doc.createElement("A:propfind")
		root.setAttribute("xmlns:A", "DAV:")

		def child = doc.createElement("A:prop")
		def request = doc.createElement("A:" + requestType)

		child.appendChild(request)
		root.appendChild(child)

		doc.appendChild(root)
	}

	/**
	 * Writes the request in the memory to a string.
	 *
	 * @param doc
	 *            the document to write
	 * @return the string representation of the XML document
	 * @throws RequestException
	 *             if transformation of XML request to String failed
	 *
	 * @since 2.0.0
	 */
	private def writeToString(Document doc) throws RequestException {
		def source = new DOMSource(doc)

		def writer = new StringWriter()
		def result = new StreamResult(writer)

		try {
			def transformerFactory = TransformerFactory.newInstance()
			def transformer = transformerFactory.newTransformer()

			transformer.transform(source, result)
		} catch(TransformerException e) {
			throw new RequestException("Can't transform to valid XML!", e)
		}

		writer.toString()
	}
}
