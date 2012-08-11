package org.google.sdu.douban.book;

import java.util.ArrayList;

import org.google.sdu.douban.movie.Movie;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

public class BookHandler extends DefaultHandler{
	private String kEntryElementName = "entry";
	private String kSummaryElementName="summary";
	private String kTitleElementName = "title";
	private String kLinkElementName = "link";
	private String kAttrElementName = "attribute";
	private String kAuthorAttrName = "author";
	private String kCastAttrName = "cast";
	private String kRatingElementName = "rating";
	private ArrayList<Book> bookList;
	private Book book;
	private String TAG = "BookHandler";
	private String attr = "";
	private String title = "";
	private String summary="";
	private String cast = "";
	private String author= "";

	public ArrayList<Book> getBookList() {
		return bookList;
	}

	@Override
	public void endDocument() throws SAXException {
		// TODO Auto-generated method stub
		super.endDocument();
		Log.d(TAG, "end-document");
	}

	@Override
	public void startDocument() throws SAXException {
		// TODO Auto-generated method stub
		super.startDocument();
	bookList = new ArrayList<Book>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// TODO Auto-generated method stub
		super.startElement(uri, localName, qName, attributes);
		if (localName.equalsIgnoreCase(kTitleElementName)) {
			attr = kTitleElementName;
		} else if (localName.equalsIgnoreCase(kEntryElementName)) {
			book= new Book();
		} else if (localName.equalsIgnoreCase(kLinkElementName)) {
			String rel = attributes.getValue("rel");
			String url = attributes.getValue("href");
			if (rel.equalsIgnoreCase("self")) {
				book.setSigleurl(url);
			} else if (rel.equalsIgnoreCase("image")) {
				book.setImageurl(url);
			}
		} else if (localName.equalsIgnoreCase(kRatingElementName)) {
			String rat = attributes.getValue("average");
			float ratf = Float.parseFloat(rat);
			book.setRating(ratf);
		} else if (localName.equalsIgnoreCase(kAttrElementName)) {
			attr = attributes.getValue("name");
		}else if(localName.equalsIgnoreCase(kSummaryElementName)){
		   attr=kSummaryElementName;
		}
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		// TODO Auto-generated method stub
		super.characters(ch, start, length);
		String value = new String(ch, start, length);
		//value = value.trim();
		if (attr.equalsIgnoreCase(kTitleElementName)) {
			title = value;
		} else if (attr.equalsIgnoreCase(kAuthorAttrName)) {
			author += value + " ";
		} else if (attr.equalsIgnoreCase(kCastAttrName)) {
			cast += value + " ";
		} else if (attr.equalsIgnoreCase("country")) {
			book.setArea(value);
		} else if (attr.equalsIgnoreCase("aka")) {
			title += "(" + value + ")";
		} else if (attr.equalsIgnoreCase("area")) {
			book.setArea(value);
		} else if (attr.equalsIgnoreCase("price")) {
			book.setPrice(value);
		}else if(attr.equalsIgnoreCase("isbn13")){
			book.setIsbn(value);
			
		}else if(attr.equalsIgnoreCase("publisher")){
			book.setPublisher(value);
		}else if(attr.equalsIgnoreCase(kSummaryElementName)){
			summary=value;
			
		}else if(attr.equalsIgnoreCase("author_intro")){
			book.setAuthor_intro(value);
		}
		attr = "";
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// TODO Auto-generated method stub
		super.endElement(uri, localName, qName);
		if (localName.equalsIgnoreCase(kEntryElementName)) {
			book.setCast(cast);
			book.setAuthor(author);
			book.setTitle(title);
			book.setSummary(summary);
			bookList.add(book);
			Log.d(TAG, "title:" + title);
			cast = "";
			author= "";
			title = "";
			summary="";
		}
	}
}
