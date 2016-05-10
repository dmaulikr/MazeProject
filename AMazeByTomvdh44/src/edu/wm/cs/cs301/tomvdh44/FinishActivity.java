package edu.wm.cs.cs301.tomvdh44;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class FinishActivity extends Activity {
	
	Intent intent;
	boolean finished;
	int stepCount;
	int energy;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_finish);
		
		intent = getIntent();
		intent.setClass(FinishActivity.this, AMazeActivity.class);
		
		finished = intent.getBooleanExtra("finished", true);
		stepCount = intent.getIntExtra("steps", 0);
		energy = intent.getIntExtra("energy", 2500);
		
		setSteps();
		setEnergy();
		setFinishText();
		
	}

	public void restart(View v){
		startActivity(intent);
	}
	
	public void setFinishText(){
		if(!finished){
			TextView finalText = (TextView) findViewById(R.id.textView1);
			finalText.setText(R.string.lost);
		}
	}
	
	public void setEnergy(){
		TextView energyText = (TextView) findViewById(R.id.energy);
		energyText.setText("Energy Spent: " + energy + "/2500");
	}
	
	public void setSteps(){
		TextView stepText = (TextView) findViewById(R.id.steps);
		stepText.setText("Steps Taken: " + stepCount);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.finish, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
