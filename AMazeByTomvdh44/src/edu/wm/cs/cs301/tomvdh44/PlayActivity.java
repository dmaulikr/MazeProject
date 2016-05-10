package edu.wm.cs.cs301.tomvdh44;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;
import edu.wm.cs.cs301.tomvdh44.falstad.MapDrawer;
import edu.wm.cs.cs301.tomvdh44.falstad.MazePanel;

public class PlayActivity extends ActionBarActivity implements OnCheckedChangeListener {
	
	int stepCount = 0;
	int MOVEDEC = 5;
	int TURNDEC = 3;
	int REVERSEDEC = 6;
	Switch mapSwitch;
	Switch wallSwitch;
	Switch routeSwitch;
	Button leftButton;
	Button topButton;
	Button rightButton;
	Button backButton;
	MazePanel mazePanel;
	ProgressBar energyLevel;
	Intent intent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play);
		
		leftButton = (Button) findViewById(R.id.leftButton);
		rightButton = (Button) findViewById(R.id.rightButton);
		topButton = (Button) findViewById(R.id.topButton);
		backButton = (Button) findViewById(R.id.backButton);
		
		routeSwitch = (Switch) findViewById(R.id.switch1);
		mapSwitch = (Switch) findViewById(R.id.switch2);
		wallSwitch = (Switch) findViewById(R.id.switch3);
		
		energyLevel = (ProgressBar) findViewById(R.id.progressBar1);
		
		mazePanel = (MazePanel) findViewById(R.id.view1);
		GeneratingActivity.maze.setMazePanel(mazePanel);
		
		intent = getIntent();
		
		intent.setClass(PlayActivity.this, FinishActivity.class);
	
		GeneratingActivity.maze.init();
	
		GeneratingActivity.maze.mazebuilder.drawNewMaze();
		
		
		
		setUpSwitches();
	}
	
	public void setUpSwitches(){
		setUpMap();
		setUpRoute();
		setUpWall();
	}
	
	public void checkEnergy(){
		if(energyLevel.getProgress() <= 0){
			passIntents(false);
			startActivity(intent);
		}
	}
	
	public void passIntents(boolean finished){
		intent.putExtra("steps", stepCount);
		intent.putExtra("energy", 2500 - energyLevel.getProgress());
		intent.putExtra("finished", finished);
	}
	
	public boolean checkFinish(){
		int x = GeneratingActivity.maze.getX();
		int y = GeneratingActivity.maze.getY();
		int distance = GeneratingActivity.maze.mazedists.getDistance(x, y);
		int dx = GeneratingActivity.maze.getDirX();
		int dy = GeneratingActivity.maze.getDirY();
		int width = GeneratingActivity.maze.getWidth();
		int height = GeneratingActivity.maze.getHeight();
		
		int newy = y + dy;
		int newx = x + dx;
		
	
	
		if(distance == 1
				&& (newx >= width || newx < 0 || newy >= height || newy < 0)){
			Toast.makeText(PlayActivity.this, "Finished" , Toast.LENGTH_LONG).show();
			++stepCount;
			passIntents(true);
			startActivity(intent);
		}
		MapDrawer direction = (MapDrawer) GeneratingActivity.maze.views.get(1);
		
		return false;
	}
	
	public void topButtonClick(View v) {
		if(!checkFinish()){
			GeneratingActivity.maze.walk(1);
			Toast.makeText(PlayActivity.this, "Top Button Clicked", Toast.LENGTH_SHORT).show();
			energyLevel.setProgress(energyLevel.getProgress() - MOVEDEC);
			++stepCount;
			checkEnergy();
		}
	}

	public void backButtonClick(View v) {
		GeneratingActivity.maze.rotate(2);
		Toast.makeText(PlayActivity.this, "Back Button Clicked", Toast.LENGTH_SHORT).show();
		energyLevel.setProgress(energyLevel.getProgress() - REVERSEDEC);
		checkEnergy();
	}

	public void rightButtonClick(View v) {
		GeneratingActivity.maze.rotate(-1);
		Toast.makeText(PlayActivity.this, "Right Button Clicked", Toast.LENGTH_SHORT).show();
		energyLevel.setProgress(energyLevel.getProgress() - TURNDEC);
		checkEnergy();
	}

	public void leftButtonClick(View v) {
		GeneratingActivity.maze.rotate(1);
		Toast.makeText(PlayActivity.this, "Left Button Clicked", Toast.LENGTH_SHORT).show();
		energyLevel.setProgress(energyLevel.getProgress() - TURNDEC);
		checkEnergy();
	}

	
	
	public void setUpMap(){
		mapSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked == true){
					Toast.makeText(PlayActivity.this, "Map Enabled", Toast.LENGTH_SHORT).show();
				}
				else{
					Toast.makeText(PlayActivity.this, "Map Disabled", Toast.LENGTH_SHORT).show();
				}
				GeneratingActivity.maze.setMapMode(isChecked);
				
				GeneratingActivity.maze.notifyViewerRedraw();
			}
		});
	}
	
	public void setUpRoute(){
		routeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked == true){
					Toast.makeText(PlayActivity.this, "Route Enabled", Toast.LENGTH_SHORT).show();
				}
				else{
					Toast.makeText(PlayActivity.this, "Route Disabled", Toast.LENGTH_SHORT).show();
				}
				GeneratingActivity.maze.setSolutionMode(isChecked);
				GeneratingActivity.maze.notifyViewerRedraw();
			}
		});
	}
	
	public void setUpWall(){
		wallSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked == true){
					Toast.makeText(PlayActivity.this, "Wall Enabled", Toast.LENGTH_SHORT).show();
				}
				else{
					Toast.makeText(PlayActivity.this, "Wall Disabled", Toast.LENGTH_SHORT).show();
				}
				GeneratingActivity.maze.setWallMode(isChecked);
				GeneratingActivity.maze.notifyViewerRedraw();
			}
		});
	}
	
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            // do something when check is selected
        } else {
            //do something when unchecked
        }
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.play, menu);
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
