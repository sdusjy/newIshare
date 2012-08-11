package org.google.sdu.douban.movie;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

/**
 * @author fddi
 * 
 */
public class MovieHandler extends DefaultHandler {
	private String kEntryElementName = "entry";
	private String kTitleElementName = "title";
	private String kLinkElementName = "link";
	private String kAttrElementName = "attribute";
	private String kDirectorAttrName = "director";
	private String kCastAttrName = "cast";
	private String kRatingElementName = "rating";
	private ArrayList<Movie> movieList;
	private Movie movie;
	private String TAG = "MovieHandler";
	private String attr = "";
	private String title = "";
	private String cast = "";
	private String director = "";

	public ArrayList<Movie> getMovieList() {
		return movieList;
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
		movieList = new ArrayList<Movie>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// TODO Auto-generated method stub
		super.startElement(uri, localName, qName, attributes);
		if (localName.equalsIgnoreCase(kTitleElementName)) {
			attr = kTitleElementName;
		} else if (localName.equalsIgnoreCase(kEntryElementName)) {
			movie = new Movie();
		} else if (localName.equalsIgnoreCase(kLinkElementName)) {
			String rel = attributes.getValue("rel");
			String url = attributes.getValue("href");
			if (rel.equalsIgnoreCase("self")) {
				movie.setSingleUrl(url);
			} else if (rel.equalsIgnoreCase("image")) {
				movie.setImgUrl(url);
			}
		} else if (localName.equalsIgnoreCase(kRatingElementName)) {
			String rat = attributes.getValue("average");
			float ratf = Float.parseFloat(rat);
			movie.setRating(ratf);
		} else if (localName.equalsIgnoreCase(kAttrElementName)) {
			attr = attributes.getValue("name");
		}
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		// TODO Auto-generated method stub
		super.characters(ch, start, length);
		String value = new String(ch, start, length);
		value = value.trim();
		if (attr.equalsIgnoreCase(kTitleElementName)) {
			title = value;
		} else if (attr.equalsIgnoreCase(kDirectorAttrName)) {
			director += value + " ";
		} else if (attr.equalsIgnoreCase(kCastAttrName)) {
			cast += value + " ";
		} else if (attr.equalsIgnoreCase("country")) {
			movie.setArea(value);
		} else if (attr.equalsIgnoreCase("aka")) {
			title += "(" + value + ")";
		} else if (attr.equalsIgnoreCase("area")) {
			movie.setArea(value);
		} else if (attr.equalsIgnoreCase("pubdate")) {
			movie.setReDate(value);
		}
		attr = "";
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// TODO Auto-generated method stub
		super.endElement(uri, localName, qName);
		if (localName.equalsIgnoreCase(kEntryElementName)) {
			movie.setCast(cast);
			movie.setDirector(director);
			movie.setTitle(title);
			movieList.add(movie);
			Log.d(TAG, "title:" + title);
			cast = "";
			director = "";
			title = "";
		}
	}
}
