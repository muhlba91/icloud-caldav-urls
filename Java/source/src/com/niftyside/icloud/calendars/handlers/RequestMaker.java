package com.niftyside.icloud.calendars.handlers;

import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.niftyside.icloud.calendars.exceptions.RequestException;

/**
 * Makes a special XML PROPFIND request and parses it to a string.
 * 
 * @author Daniel Muehlbachler
 * @copyright 2012 Daniel Muehlbachler
 * 
 * @see {@link http://icloud.niftyside.com}
 * 
 * @version 1.0
 * 
 */
public class RequestMaker {
	/* * * * * Variables * * * * */

	/** The builder. */
	private DocumentBuilder builder;

	/* * * * * Constructor * * * * */

	/**
	 * Initializes a new request maker.
	 * 
	 * @throws RequestException
	 *             if the initialization of the builder failed
	 * 
	 * @since 1.0
	 */
	public RequestMaker() throws RequestException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			builder = factory.newDocumentBuilder();
		} catch (Exception e) {
			throw new RequestException("Error: can't create request builder.");
		}
	}

	/* * * * * Methods * * * * */

	/**
	 * Makes a new request.
	 * 
	 * @param requestType
	 *            the requested type
	 * @return the built request string
	 * @throws RequestException
	 *             if transformation of XML request to String failed
	 * 
	 * @since 1.0
	 */
	public String make(String requestType) throws RequestException {
		// Here instead of parsing an existing document we want to
		// create a new one.
		Document doc = builder.newDocument();

		// create request
		// root element
		Element root = doc.createElement("A:propfind");
		root.setAttribute("xmlns:A", "DAV:");
		// child element
		Element child = doc.createElement("A:prop");
		// request element
		Element req = doc.createElement("A:" + requestType);
		// append
		child.appendChild(req);
		root.appendChild(child);
		doc.appendChild(root);

		// The XML document we created above is still in memory
		// so we have to output it to a real file.
		// In order to do it we first have to create
		// an instance of DOMSource
		DOMSource source = new DOMSource(doc);

		// StringWriter is a wrapper to write to a string.
		StringWriter writer = new StringWriter();
		StreamResult result = new StreamResult(writer);

		// Once again we are using a factory of some sort,
		// this time for getting a Transformer instance,
		// which we use to output the XML
		try {
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();

			// The actual output to a file goes here
			transformer.transform(source, result);
		} catch (Exception e) {
			throw new RequestException("Error: can't transform to valid XML.");
		}

		return writer.toString();
	}
}
