package edu.sasq.prevajalniki.android;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Podrobno extends Activity  {
	public static String link;
	public ProgressDialog bar;
	public Cleaner novi;
	public Cleaner drugi;
	public TextView txtNaslov;
	public TextView txtNapredavanju;
	public TextView txtDolzina;
	public TextView txtScenarij;
	public TextView txtRezija;
	public TextView txtIgrajo;
	public TextView txtOpis;
	public TextView txtImdbOcena;
	public ImageView slika;
	public static String naslov;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.podrobnosti);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		txtNaslov = (TextView) findViewById(R.id.txtNaslov);
		txtNapredavanju = (TextView) findViewById(R.id.txtNapredvajanju);
		txtDolzina = (TextView) findViewById(R.id.txtDolzina);
		txtScenarij = (TextView) findViewById(R.id.txtScenarij);
		txtRezija = (TextView) findViewById(R.id.txtRezija);
		txtIgrajo = (TextView) findViewById(R.id.txtIgrajo);
		txtOpis = (TextView) findViewById(R.id.txtOpis);
		slika = (ImageView) findViewById(R.id.imageView1);
		slika.setVisibility(View.INVISIBLE);
		naslov = naslov.substring(0, naslov.indexOf("\n"));
		txtImdbOcena = (TextView) findViewById(R.id.ocena);
		bar=ProgressDialog.show(this, "Wait","Downloading data", true);
        Thread nit = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				novi = new Cleaner();
				String[] strDatum;
				TagNode y = novi.xmlCleaner(link);
				TagNode[] dolzina;
				TagNode[] datum;
				TagNode[] scenarij;
				TagNode[] igralci;
				TagNode[] rezija;
				TagNode[] opis;
				TagNode[] slikica;
				TagNode[] link;
				dolzina = Cleaner.findInfo(y, "//span[@class='duration']");
				datum = Cleaner.findInfo(y, "//span[@class='date-single']");
				scenarij = Cleaner.findInfo(y, "//span[@class='screenplay']");
				igralci = Cleaner.findInfo(y, "//span[@class='actors']");
				rezija = Cleaner.findInfo(y, "//span[@class='director']");
				opis = Cleaner.findInfo(y, "//div[@class='summary']");
				slikica = Cleaner.findInfo(y, "//div[@class='movie-image']//img");
				link = Cleaner.findInfo(y, "//span[@class='links']//a");
				String[] StrDolzina = new String[dolzina.length];
				String[] Strscenarij = new String[scenarij.length];
				String[] Strigralci = new String[igralci.length];
				String[] Strrezija = new String[rezija.length];
				String[] Strslikica = new String[slikica.length];
				String[] Stropis = new String[opis.length];
				String[] linkImdb = new String[link.length];
				strDatum = new String[datum.length];
				try {
					for (int i = 0; i < dolzina.length; i++) {
						StrDolzina[i] = dolzina[i].getText().toString();
					}
					for (int i = 0; i < opis.length; i++) {
						Stropis[i] = opis[i].getText().toString();
					}
					for (int i = 0; i < slikica.length; i++) {
						Strslikica[i] = "http://www.kolosej.si"
								+ slikica[i].getAttributeByName("src").toString();
					}
					for (int i = 0; i < dolzina.length; i++) {
						StrDolzina[i] = dolzina[i].getText().toString();
					}
					for (int i = 0; i < datum.length; i++)
						strDatum[i] = datum[i].getText().toString();
					for (int i = 0; i < scenarij.length; i++)
						Strscenarij[i] = scenarij[i].getText().toString();
					for (int i = 0; i < igralci.length; i++)
						Strigralci[i] = igralci[i].getText().toString();
					for (int i = 0; i < rezija.length; i++)
						Strrezija[i] = rezija[i].getText().toString();
					for (int i = 0; i < linkImdb.length; i++)
						linkImdb[i] = link[i].getAttributeByName("href").toString();
				} catch (Exception b) {
					b.printStackTrace();
				}

				Cleaner drugi = new Cleaner();
				TagNode x = drugi.xmlCleaner2(data(linkImdb[1]));
				TagNode[] imdbOcena;
				imdbOcena = Cleaner.findInfo(x, "//span[@itemprop='ratingValue']");
				String imdbString[] = new String[imdbOcena.length];
				for (int i = 0; i < imdbOcena.length; i++) {
					imdbString[i] = imdbOcena[i].getText().toString();
				}
				String vmes="";
				if (dolzina.length != 0) {
					vmes = StrDolzina[StrDolzina.length - 1].replaceAll(
							"\n", "");
					vmes = vmes.substring(9);
				}
				Message mesage = new Message();
				Object [] posli={imdbString[imdbString.length-1]+"/10",Podrobno.naslov,strDatum[strDatum.length - 1],vmes,Strigralci[Strigralci.length - 1].replaceAll(
								"\n", "").substring(9),Strrezija[Strrezija.length - 1].replaceAll("\n",
								"").substring(9),Strscenarij[Strscenarij.length - 1].replaceAll(
										"\n", "").substring(10),Stropis[Stropis.length - 1],getBitmapFromURL(Strslikica[Strslikica.length - 1])};
				mesage.obj=posli;
				prvi.sendMessage(mesage);
				
			}
		});
		nit.start();
		
	}
	 Handler prvi= new Handler(){
	    	@Override
	    	public void handleMessage(Message m){
	    		Object[] data=(Object[]) m.obj;
	    		try {
	    			slika.setVisibility(View.VISIBLE);
	    			txtImdbOcena.setText((CharSequence) data[0]);
	    			txtNaslov.setText((CharSequence) data[1]);
	    			txtNapredavanju.setText((CharSequence) data[2]);
	    			
	    				txtDolzina.setText((CharSequence) data[3]);
	    			txtIgrajo.setText((CharSequence) data[4]);
	    			txtRezija.setText((CharSequence) data[5]);
	    			txtScenarij.setText((CharSequence) data[6]);
	    			txtOpis.setText((CharSequence) data[7]);
	    			slika.setImageBitmap((Bitmap) data[8]);
	    			bar.dismiss();
	    		} catch (Exception k) {
	    			k.printStackTrace();
	    		}
	    		
	    	}
	 };

	public static Bitmap getBitmapFromURL(String src) {
		try {
			URL url = new URL(src);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoInput(true);
			connection.connect();
			InputStream input = connection.getInputStream();
			Bitmap myBitmap = BitmapFactory.decodeStream(input);
			return myBitmap;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String data(String link) {
		try {
			URL url = new URL(link);
			HttpURLConnection urlConnection = (HttpURLConnection) url
					.openConnection();
			urlConnection.setRequestProperty("User-Agent", "Mozilla/4.0");

			InputStream is = urlConnection.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);

			int numCharsRead;
			char[] charArray = new char[1024];
			StringBuffer sb = new StringBuffer();
			while ((numCharsRead = isr.read(charArray)) > 0) {
				sb.append(charArray, 0, numCharsRead);
			}
			//System.out.println(sb.toString());
			return sb.toString();
		} catch (Exception n) {
			n.printStackTrace();
			return null;
		}
		
	}
}
