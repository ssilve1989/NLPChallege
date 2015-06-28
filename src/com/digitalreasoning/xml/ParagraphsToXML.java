package com.digitalreasoning.xml;

import com.digitalreasoning.structure.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.util.Collection;

/**
 * Created by steve on 6/27/15.
 * Transforms objects that implement XMLTag
 * into an XML document
 */
public class ParagraphsToXML {
    private static final String NAMED_ENTITY = "namedEntity";
    private final String outputFile;
    private final Collection<Paragraph> paragraphs;
    private final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    private DocumentBuilder dBuilder;
    private Document doc;

    public ParagraphsToXML(String outputFile, Collection<Paragraph> paragraphs){
        this.outputFile = outputFile;
        this.paragraphs = paragraphs;
        try {
            dBuilder = dbf.newDocumentBuilder();
            doc = dBuilder.newDocument();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void writeFile() throws TransformerException {
        if(doc == null) return;
        final Element rootElement = doc.createElement("File");
        doc.appendChild(rootElement);
        this.paragraphs.forEach(paragraph -> paragraphToXml(rootElement, paragraph));
        createOutput(doc);
    }

    private void paragraphToXml(Element root, Paragraph p){
        Element paragraphElement = doc.createElement(p.getXMLTag());
        root.appendChild(paragraphElement);
        p.getSentences().forEach(sentence -> sentenceToXMl(paragraphElement, sentence));
    }
    private void sentenceToXMl(Element parent, Sentence s){
        Element sentenceElement = doc.createElement(s.getXMLTag());
        parent.appendChild(sentenceElement);
        s.getSentencePieces().forEach(piece -> sentencePieceToXML(sentenceElement, piece));
    }

    private void sentencePieceToXML(Element parent, SentencePiece piece){
        Element sentencePieceElement = doc.createElement(piece.getXMLTag());
        if(piece instanceof Word){
            Word word = (Word) piece;
            if(word.isNamedEntity()) sentencePieceElement.setAttribute(NAMED_ENTITY, "true");
            sentencePieceElement.appendChild(doc.createTextNode(word.getWord()));
        }else if(piece instanceof Punctuation){
            Punctuation punctuation = (Punctuation) piece;
            sentencePieceElement.appendChild(doc.createTextNode(punctuation.getPunctuation()));
        }
        parent.appendChild(sentencePieceElement);
    }

    private void createOutput(Document document) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(this.outputFile);
        transformer.transform(source, result);
        StreamResult resultSysOut = new StreamResult(System.out);
        transformer.transform(source, resultSysOut);
    }
}
