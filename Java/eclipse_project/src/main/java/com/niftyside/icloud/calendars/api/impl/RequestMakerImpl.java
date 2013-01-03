package com.niftyside.icloud.calendars.api.impl;

import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.niftyside.icloud.calendars.api.exception.RequestException;
import com.niftyside.icloud.calendars.api.model.RequestMaker;

/**
 * Implementation of {@link RequestMaker}.
 * 
 * @author Daniel Muehlbachler
 * @copyright 2011-2013 Daniel Muehlbachler
 * 
 * @see http://icloud.niftyside.com
 * 
 * @version 1.1
 * 
 */
public class RequestMakerImpl implements RequestMaker {
	/* * * * * Variables * * * * */

	/** The request builder. */
	private final DocumentBuilder builder;

	/* * * * * Constructor * * * * */

	/**
	 * Initializes a new request maker.
	 * 
	 * @throws RequestException
	 *             if the initialization of the builder failed
	 * 
	 * @since 1.1
	 */
	public RequestMakerImpl() throws RequestException {
		final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			builder = factory.newDocumentBuilder();
		} catch (final ParserConfigurationException e) {
			throw new RequestException("Error: can't create request builder.", e);
		}
	}

	/* * * * * Methods * * * * */

	@Override
	public String makeRequest(final String requestType) throws RequestException {
		final Document doc = builder.newDocument();

		createRequest(doc, requestType);

		return writeToString(doc);
	}

	/* * * * * Private methods * * * * */

	/**
	 * Writes the request in the memory to a string.
	 * 
	 * @param doc
	 *            the document to write
	 * @return the string representation of the XML document
	 * @throws RequestException
	 *             if transformation of XML request to String failed
	 * 
	 * @since 1.1
	 */
	private String writeToString(final Document doc) throws RequestException {
		final DOMSource source = new DOMSource(doc);

		final StringWriter writer = new StringWriter();
		final StreamResult result = new StreamResult(writer);

		try {
			final TransformerFactory transformerFactory = TransformerFactory.newInstance();
			final Transformer transformer = transformerFactory.newTransformer();

			transformer.transform(source, result);
		} catch (final TransformerException e) {
			throw new RequestException("Error: can't transform to valid XML.", e);
		}

		return writer.toString();
	}

	/**
	 * Creates a new request.
	 * 
	 * @param doc
	 *            the document
	 * @param requestType
	 *            the request type
	 * 
	 * @since 1.1
	 */
	private void createRequest(final Document doc, final String requestType) {
		final Element root = doc.createElement("A:propfind");
		root.setAttribute("xmlns:A", "DAV:");

		final Element child = doc.createElement("A:prop");
		final Element request = doc.createElement("A:" + requestType);

		child.appendChild(request);
		root.appendChild(child);

		doc.appendChild(root);
	}

}
