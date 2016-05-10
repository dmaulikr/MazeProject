package edu.wm.cs.cs301.tomvdh44;
import android.content.Intent;
import android.opengl.Visibility;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import android.os.Bundle;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.support.v7.internal.widget.AdapterViewCompat.OnItemSelectedListener;
import android.util.Log;
import android.view.MenuItem;


public class AMazeActivity extends ActionBarActivity {
	
	public Spinner algSpinner;
	public Spinner fileSpinner;
	public SeekBar seekBar;
	public RadioGroup navRadio;
	public RadioButton manualRadio;
	public RadioButton wallfollowerRadio;
	public RadioButton wizardRadio;
	private String generator;
	private String navigator;
	public static int progress;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.v("AMazeActivity", "onCreate");
		
		setContentView(R.layout.activity_amaze);
		
		navigator = "Manual";
		generator = "Prims";
		
		//Set up Seek Bar
		seekBar = (SeekBar)findViewById(R.id.seekBar1);
		TextView difficulty_Level = (TextView) findViewById(R.id.difficulty_text);
		difficulty_Level.setText("0");
		seekBarTrack();
		
		//Second Radio Group
		manualRadio = (RadioButton)findViewById(R.id.Man_Radio);
		wallfollowerRadio = (RadioButton)findViewById(R.id.Wall_Radio);
		wizardRadio = (RadioButton)findViewById(R.id.Wiz_Radio);
		
		manualRadio = (RadioButton)findViewById(R.id.Man_Radio);
		wallfollowerRadio = (RadioButton)findViewById(R.id.Wall_Radio);
		wizardRadio = (RadioButton)findViewById(R.id.Wiz_Radio);
		
		// Set up Spinners
		algSpinner = (Spinner)findViewById(R.id.AlgSpinner);
		fileSpinner = (Spinner)findViewById(R.id.FileSpinner);
		startSetup();
		spinnerSetup();
		
	}
	
	public void startSetup(){
		Button StartButton = (Button)findViewById(R.id.StartButton);
		StartButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.v("Amazeactivity", "startbutton clicked " + v.getClass().toString()) ;
				Toast.makeText(AMazeActivity.this, "Start Button Clicked", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(AMazeActivity.this, GeneratingActivity.class);
				 // Pass seek bar to intent
				intent.putExtra("difficulty", progress);
				intent.putExtra("navigator", navigator);
				intent.putExtra("generator", generator);
				AMazeActivity.this.startActivity(intent);
				
			}
		});
	}
	
	
	public void displayAlgs(View v)
	{
		RadioGroup radio_alg = (RadioGroup) findViewById(R.id.navRadios);
		if(v.getId()==R.id.File_Radio){
			Toast.makeText(AMazeActivity.this, "File Button Selected", Toast.LENGTH_SHORT).show();
			fileSpinner.setVisibility(View.VISIBLE);
			radio_alg.setVisibility(View.GONE);
		}
		else{
			Toast.makeText(AMazeActivity.this, "Algorithm Button Selected", Toast.LENGTH_SHORT).show();
			radio_alg.setVisibility(View.VISIBLE);
			fileSpinner.setVisibility(View.GONE);
		}
	}
	
	public void updateText(){
		TextView difficulty = (TextView) findViewById(R.id.difficulty_text);
		if(progress < 10){
			difficulty.setText("" + progress);
			
			Log.v("AMazeActivity", "Text Updated. Progress = " + progress);
			
		}
		else if(progress==10){
			difficulty.setText("A");
		}
		else if(progress==11){
			difficulty.setText("B");
		}
		else if(progress==12){
			difficulty.setText("C");
		}
		else if(progress==13){
			difficulty.setText("D");
		}
		else if(progress==14){
			difficulty.setText("E");
		}
		else{
			difficulty.setText("F");
		}
	}
	
	public void navSelect(View v){
			
		if(v.getId()==R.id.Man_Radio){
			Toast.makeText(AMazeActivity.this, "Manual Button Selected", Toast.LENGTH_SHORT).show();
			navigator = "Manual";
		}
		else if(v.getId()==R.id.Wall_Radio){
			Toast.makeText(AMazeActivity.this, "WallFollower Button Selected", Toast.LENGTH_SHORT).show();
			navigator = "Wallfollower";
		}
		else{
			Toast.makeText(AMazeActivity.this, "Wizard Button Selected", Toast.LENGTH_SHORT).show();
			navigator = "Wizard";
			}
		
	}	
	
	public void spinnerSetup(){
		ArrayAdapter<CharSequence> algAdapter = ArrayAdapter.createFromResource(this,R.array.RandomMazes, android.R.layout.simple_spinner_item);
		algAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		algSpinner.setAdapter(algAdapter);
		algSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				generator = (String) parent.getItemAtPosition(position);
				Toast.makeText(getBaseContext(), generator + " Option Selected", Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			
				
			}
		});
	}
	
	public void seekBarTrack(){
		Log.v("AMazeActivity", "seekBarTrack");
		seekBar.setOnSeekBarChangeListener(
			new SeekBar.OnSeekBarChangeListener() {
			
				@Override
				public void onStopTrackingTouch(SeekBar seekBar) {
					// TODO Auto-generated method stub
					updateText();
					char progress_char = '0';
					if(progress<10)
					{
						progress_char = (char) ('0' + progress);
					}
					else{
						progress_char = (char) (55 + progress);
					}
					Toast.makeText(AMazeActivity.this, "Difficulty Level: " + (progress_char), Toast.LENGTH_SHORT).show();
				}
				
				@Override
				public void onStartTrackingTouch(SeekBar seekBar) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
					// TODO Auto-generated method stub
					Log.v("AMazeActivity", "Progress Changed");
					AMazeActivity.progress=progress;
					updateText();
					
				}
			});
				
		}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.amaze, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		/*int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		*/
		return super.onOptionsItemSelected(item);
	}
	
	
	}	

