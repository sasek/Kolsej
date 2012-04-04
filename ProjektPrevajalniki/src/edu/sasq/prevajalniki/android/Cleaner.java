package edu.sasq.prevajalniki.android;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

public class Cleaner {
	
	public static TagNode[] findInfo(TagNode node, String XPathExpression) {
		TagNode[] description_node = null;
		Object[] eden;
		try {
			
		eden =node.evaluateXPath(XPathExpression);
		description_node=new TagNode[eden.length];
		for(int i=0;i<eden.length;i++)
		{
			description_node[i]=(TagNode)eden[i];
		}
		} catch (XPatherException e) {
		e.printStackTrace();
		}
		return description_node;
		}

	public TagNode xmlCleaner2(String fileName) {
		CleanerProperties props = new CleanerProperties();
		props.setTranslateSpecialEntities(true);
		props.setTransResCharsToNCR(true);
		props.setOmitComments(true);
		TagNode tagNode;
		try {
		tagNode = new HtmlCleaner(props).clean(fileName);
		return tagNode;
		} catch (Exception e) {
		e.printStackTrace();
		}
		return null;
		}
	public TagNode xmlCleaner(String fileName) {
		CleanerProperties props = new CleanerProperties();
		props.setTranslateSpecialEntities(true);
		props.setRecognizeUnicodeChars(true);
		props.setAdvancedXmlEscape(true);
		props.setUseEmptyElementTags(true);
		props.setUseCdataForScriptAndStyle(true);
		TagNode tagNode;
		try {
		tagNode = new HtmlCleaner(props).clean(new URL(fileName));
		return tagNode;
		} catch (MalformedURLException e) {
		e.printStackTrace();
		} catch (IOException e) {
		e.printStackTrace();
		}
		return null;
		}

}
