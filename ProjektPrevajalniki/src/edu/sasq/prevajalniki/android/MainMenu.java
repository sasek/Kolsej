package edu.sasq.prevajalniki.android;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

public class MainMenu extends Activity{
	/** Called when the activity is first created. */
	public static String kraj;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmenu);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        kraj="";
    }
    public void Ljclick(View v)
    {
    	kraj="ljubljana";
    	Intent i = new Intent();
    	  i.setClass(this, ProjektPrevajalnikiActivity.class);
    	  startActivity(i);
    }
    public void Mbclick(View v)
    {
    	kraj="maribor";
    	Intent i = new Intent();
  	  i.setClass(this, ProjektPrevajalnikiActivity.class);
  	  startActivity(i);
    }
    public void Krclick(View v)
    {
    	kraj="kranj";
    	Intent i = new Intent();
  	  i.setClass(this, ProjektPrevajalnikiActivity.class);
  	  startActivity(i);
    }
    public void Koclick(View v)
    {
    	kraj="koper";
    	Intent i = new Intent();
  	  i.setClass(this, ProjektPrevajalnikiActivity.class);
  	  startActivity(i);
    }
}
