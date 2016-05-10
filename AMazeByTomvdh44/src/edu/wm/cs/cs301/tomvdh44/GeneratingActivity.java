package edu.wm.cs.cs301.tomvdh44;
import android.content.Intent;

import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import edu.wm.cs.cs301.tomvdh44.falstad.Maze;

public class GeneratingActivity extends ActionBarActivity {
	
	public static Maze maze;
	private static Button newbutton;
	ProgressBar progressbar;
	Thread running;
	private Handler handler;
	private Intent intent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		intent = getIntent();
		intent.setClass(GeneratingActivity.this, PlayActivity.class);
		
		int algInt;
		String generator = intent.getStringExtra("generator");
		if(generator.equals("Backtracking")){
			algInt = 0;
		}
		else if(generator.equals("Prims")){
			algInt = 1;
		}
		else{
			algInt = 2;
		}
		
		int progress = intent.getIntExtra("difficulty", 0);
		
		maze = new Maze(algInt);
		
		maze.build(progress);
		
		setContentView(R.layout.activity_generating);
		OnStartMazeListener();
		
		progressbar = (ProgressBar) findViewById(R.id.progressBar1);
		progressbar.setProgress(0);
		handler = new Handler();
		
		running = new Thread (new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (progressbar.getProgress() < maze.mazebuilder.getMaxProgress()) {
					handler.post(new Runnable(){

						@Override
						public void run() {
							// TODO Auto-generated method stub
							progressbar.setProgress(maze.getPercentDone());
						}
						
					});
					try {
						Thread.sleep(30);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				startActivity(intent);
			}
			
		});
		running.start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.generating, menu);
		return true;
	}
	
	public void OnStartMazeListener(){
		newbutton = (Button)findViewById(R.id.StartMazeButton);
		newbutton.setOnClickListener(
				new View.OnClickListener(){
					@Override
					public void onClick(View m){
						startActivity(intent);
					}
				}
					
			);	
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