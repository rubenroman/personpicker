package com.rubsnick.personpicker;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

public class MainActivity extends Activity {
ArrayList<String>lstStraws= new ArrayList<String>();
	List<EditText>lstEditText=new ArrayList<EditText>();
	
	String SelectedPersonName;
	int selectedPersonIndex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        
    }
    //This Removes the Field from the Layout.
    public void RemovePlayer(View view)
    {
    	
    	LinearLayout llPlayers=(LinearLayout) findViewById(R.id.llPlayers);
    	if(lstEditText.size()!=0)
    	{
    	llPlayers.removeView(lstEditText.get(lstEditText.size()-1));
    	lstEditText.remove(lstEditText.size()-1);
    	}
    }
    //This adds an Edit Text so user can input names
    public void AddPlayer(View view)
    {
    	
    	if(lstEditText.size()<15)
    	{
    	LinearLayout llPlayers=(LinearLayout) findViewById(R.id.llPlayers);
    	EditText Player= new EditText(this);
    	
    	Player.setTextSize(20);
    	llPlayers.addView(Player);
    	lstEditText.add(Player);
    	
    	
    	Player.requestFocus();
    	InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    	imm.showSoftInput(Player, InputMethodManager.SHOW_IMPLICIT);
    	}
    	
    }
  //Makes sure that there are no Empty Text Strings.
    public boolean AreStringsEmpty()
    {
    	for(int i=0;lstStraws.size()>i;i++)
    	{
    		if(lstStraws.get(i).isEmpty()||lstStraws.get(i)==" "||lstStraws.get(i)=="  ")
    		{
    			
    			return true;
    		}
    		
    	}
    	
    	return false;
    }
    
 //This Method verifies that all Fields are Valid and once information is valid it starts PlayActivity
    public void StartClick(View view)
    {
    	
    	AddTextStringList();
    	if(lstStraws.size()==1)
    	{
    		
    		AlertDialog.Builder builder = new AlertDialog.Builder(this);

    		
    		builder.setMessage("You need more than One Player to play!");
    		builder.setTitle("Alert!");
builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
    public void onClick(DialogInterface dialog, int id) {
        // Do Nothing
    }
});
    		
    		AlertDialog dialog = builder.create();
    		
    		dialog.show();
    	}
    	else if(lstStraws.size()!=0)
    	{
    		
    		if(AreStringsEmpty())
    		{
    			
        		AlertDialog.Builder builder = new AlertDialog.Builder(this);

        		
        		builder.setMessage("All Players Must Have a Name");
        		builder.setTitle("Error");
    builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int id) {
            // Do Nothing
        }
    });
        		
        		AlertDialog dialog = builder.create();
        		
        		dialog.show();
    
    		}
    		else
    		{
    			
    	    	
    	    	
    	    	 Intent intent= new Intent(this,PlayActivity.class);
    	   
    	    	 intent.putExtra("lstPlayers", lstStraws);
    	    	 startActivity(intent);
    			
    		}
    	}
    	else
    	{
    		
    		
    		AlertDialog.Builder builder = new AlertDialog.Builder(this);

    		
    		builder.setMessage("Please Add Players");
    		builder.setTitle("Alert!");
builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
    public void onClick(DialogInterface dialog, int id) {
        // Do Nothing
    }
});
    		
    		AlertDialog dialog = builder.create();
    		
    		dialog.show();
    	}
    }
    //This grabs The Text Views values and add it to our Array of Texts.
    public void AddTextStringList()
    {
    	lstStraws.clear();
    for(int i=0;lstEditText.size()>i;i++)
    {
    	lstStraws.add(lstEditText.get(i).getText().toString());
    }
    	
    }
  
    
   

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
