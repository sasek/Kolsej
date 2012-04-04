package edu.sasq.prevajalniki.android;

import org.htmlcleaner.TagNode;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ProjektPrevajalnikiActivity extends ListActivity {
    /** Called when the activity is first created. */
	public String xpath;
	public Context cnt;
	public Cleaner novi;
	public String[] link;
	public ProgressDialog bar;
	public ArrayAdapter<String> adapter;
	String[] naslov;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        novi= new Cleaner();
        cnt= getApplicationContext();
        naslov= new String[1];
        naslov[0]="";
        xpath="http://www.kolosej.si/spored/"+MainMenu.kraj+"/";
        bar=ProgressDialog.show(this, "Wait","Downloading data", true);
        Thread nit = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				TagNode y = novi.xmlCleaner(xpath);
				Message mesage = new Message();
				mesage.obj=y;
				prvi.sendMessage(mesage);
				
			}
		});
		nit.start();
		
	}
    Handler prvi= new Handler(){
    	@Override
    	public void handleMessage(Message m){
    		TagNode y = (TagNode) m.obj;
    		TagNode[] ime;
            TagNode[] zanr;
    		ime=Cleaner.findInfo(y, "//span[@class='title-si']//a");
    		zanr=Cleaner.findInfo(y, "//span[@class='genre']");
    		naslov=new String[ime.length];
    		link= new String[ime.length];
    		for(int i=0; i<ime.length; i++){
    			String x = ime[i].getText().toString();
    			x=x.replace("&#39;", "'");
    			naslov[i]=x+"\n("+zanr[i].getText().toString()+")";
    			x=x.replace('è', 'c');
    			x=x.replace('ž', 'z');
    			x=x.replace('š', 's');
    			link[i]="http://www.kolosej.si"+(ime[i].getAttributeByName("href"));
    			adapter = new ArrayAdapter<String>(cnt,
    					android.R.layout.simple_list_item_1, naslov);
    			setListAdapter(adapter);
    			bar.dismiss();
    		}
    	}
    };

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		long item =getListAdapter().getItemId(position);
		Podrobno.link=link[(int)item];
		Podrobno.naslov=naslov[(int)item];
		Intent i = new Intent();
  	  i.setClass(this, Podrobno.class);
  	  startActivity(i);
	}/*
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
	public TagNode xmlCleaner(String fileName) {
		CleanerProperties props = new CleanerProperties();
		props.setTranslateSpecialEntities(true);
		props.setTransResCharsToNCR(true);
		props.setOmitComments(true);
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
		}*/
  
}