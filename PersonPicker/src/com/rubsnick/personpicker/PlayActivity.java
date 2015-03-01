package com.rubsnick.personpicker;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PlayActivity extends Activity {
	ArrayList<String> lstStraws ;
	ArrayList<TextView> lstRemaining= new ArrayList<TextView>();
	ArrayList<TextView> lstSaved= new ArrayList<TextView>();
	MediaPlayer mpFail ;
	MediaPlayer mpSafe;
	ImageView backgroundImage;
	RotateAnimation rotateAnimation1 ;
	String SelectedPersonName;
	int selectedPersonIndex;
	
	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	   
        setContentView(R.layout.personpicker);
        
         lstStraws = (ArrayList<String>) getIntent().getSerializableExtra("lstPlayers");
       // We Assign the Sound Effects that will play when a Player is Saved or is doomed
        mpFail= MediaPlayer.create(this, R.raw.fail); 
        mpSafe= MediaPlayer.create(this, R.raw.safe);
	    
         AddNamesToRemaining();
        
         backgroundImage=(ImageView) findViewById(R.id.imageView1);
         rotateAnimation1  = new RotateAnimation(0, 720,
     	        Animation.RELATIVE_TO_SELF, 0.5f,
     	        Animation.RELATIVE_TO_SELF, 0.5f);
         rotateAnimation1.setInterpolator(new LinearInterpolator());
 		
 		rotateAnimation1.setDuration(1500);
 		rotateAnimation1.setRepeatCount(0);
 		
        
	}
	
	//We add the Names to the Remaining Linear Layout so the user Knows who is left.
	   public void AddNamesToRemaining()
	    {
		   LinearLayout llRemaining=(LinearLayout) findViewById(R.id.llRemaining);
	    	for(int i=0;lstStraws.size()>i;i++)
	    	{
	    	TextView PlayerRemaining= new TextView(this);
	    	PlayerRemaining.setTextSize(20);
	    	PlayerRemaining.setText(lstStraws.get(i));
	    	llRemaining.addView(PlayerRemaining);
	    	lstRemaining.add(PlayerRemaining);
	    	}
	    	
	    }
	   
//This Method Picks a random valid index, and is our Heart of the Application.
	  public int PickPeople(int Size)
	    {
	    	
	    	return Math.round((float)Math.random()*(Size)) ;
	    }
//This is the Method that listens to when the PlayButton is clicked, and Does all of our important Logic
	 public void ONclick(View view)
	    {
		 backgroundImage.startAnimation(rotateAnimation1);
		 LinearLayout llRemaining=(LinearLayout) findViewById(R.id.llRemaining);
		 LinearLayout llSaved=(LinearLayout) findViewById(R.id.llSaved);
	    	
		
 		AlertDialog.Builder builder = new AlertDialog.Builder(this);
 		
 		builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
 public void onClick(DialogInterface dialog, int id) {
     // Do Nothing
 }
});
//If Size is 0 it will Finish current Activity, if not it proceeds with logic.
	    if(lstStraws.size()!=0)
	    {

	    	int size=lstStraws.size()-1;
	    	   selectedPersonIndex=   PickPeople(size);
	    	   SelectedPersonName=lstStraws.get(selectedPersonIndex);
	    	   
	    	   //If only 2 remain in the list It will change The color of Play Button and pick the loser.
	    	   if(lstStraws.size()==2)
	    	   {
	    		   Button playButton=(Button) findViewById(R.id.btnPlay);
	    		   playButton.setBackgroundColor(Color.parseColor("#1975D1"));
	    		   playButton.setText("Go Back");
	    		   mpFail.start();
	    		   builder.setMessage(SelectedPersonName+" is Doomed");
	    	 		builder.setTitle("Loser");
	    	 		builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
	    	 			 public void onClick(DialogInterface dialog, int id) {
	    	 				
	    	 				lstStraws.remove(selectedPersonIndex);
	    	 			    
	    	 			 }
	    	 			});
	    	 		AlertDialog dialog = builder.create();
	    	 		dialog.show();
	    	 		TextView SavedPlayer= new TextView(this);
	    	 		SavedPlayer.setTextSize(20);
	    	 		// We check to see what the final index is so we don't reach with Out of Range Exception.
	    	 		if(selectedPersonIndex==1)
	    	 		{
	    	 	   SavedPlayer.setText(lstStraws.get(0));
	    	 	   
	    	 	  lstSaved.add(SavedPlayer);
  	       		llSaved.addView(SavedPlayer);
  	       	llRemaining.removeView(lstRemaining.get(0));
	    	 		}
	    	 		else
	    	 		{
	    	 		   SavedPlayer.setText(lstStraws.get(1));
	 	    	 	  lstSaved.add(SavedPlayer);
	   	       		llSaved.addView(SavedPlayer);
	   	       	llRemaining.removeView(lstRemaining.get(1));
	    	 		}
	    	   }
	    	   //If the Index is Larger then then We do Logic that says user is safe and move names from one linear layout to another.
	    	   else if (lstStraws.size()>1)
	    	   {
	    		    
	    	    	mpSafe.start();
	    	    	
	    	    	
	    		   builder.setMessage(SelectedPersonName+" is safe");
	    	 		builder.setTitle("Saved");
	    		   
	    	 		  AlertDialog dialog = builder.create();
	    	       		TextView SavedPlayer= new TextView(this);
	    	       		SavedPlayer.setText(lstStraws.get(selectedPersonIndex));
	    	       		SavedPlayer.setTextSize(20);
	    	       		lstSaved.add(SavedPlayer);
	    	       		llSaved.addView(SavedPlayer);
	    		    	lstStraws.remove(selectedPersonIndex);
	    		    	llRemaining.removeView(lstRemaining.remove(selectedPersonIndex));
	    		    	dialog.show();
	    	   }
	    	   else
	   	    {
	   	    	finish();
	   	    	
	   	    }
	    }  
	    		  
	    }
}
