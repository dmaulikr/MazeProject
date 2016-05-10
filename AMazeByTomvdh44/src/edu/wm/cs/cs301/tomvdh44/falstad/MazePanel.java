package edu.wm.cs.cs301.tomvdh44.falstad;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import edu.wm.cs.cs301.tomvdh44.R;

/**
 * Handles maze graphics.
 */
public class MazePanel extends View  {
    // TODO: please check http://developer.android.com/guide/topics/graphics/2d-graphics.html
    // on how to implement your own View class
    //
	private Canvas maze_canvas;
	private Bitmap bit_map;
	protected Paint col;
	
	
    /**
     * Constructor with one context parameter.
     * @param context
     */
   public MazePanel(Context context) {
	   super(context);
	   init();
    }


	private void init() {
		bit_map = Bitmap.createBitmap(400, 400, Config.RGB_565);
		   if (bit_map == null) {
			   Log.v("MazePanel", "no bitmap being created");
		   }
		   maze_canvas = new Canvas(bit_map) ;
		   col = new Paint() ;
		   // may be set color for col or style 
		   
	}
    


	public MazePanel(Context context, AttributeSet app) {
    	super(context, app);
    	init() ;
    } 
    
    
    @Override
	public void onDraw(Canvas c) {
	// TODO: draw bitmap
    super.onDraw(c);
    //c.drawColor(Color.WHITE);
    //  c.drawCircle(1, 5, 3, 7);
    c.drawBitmap(bit_map, 0, 0, col);
    
    }
    
    /**
     * Measures the view and its content to determine the measured width and the measured height.
     * @param width
     * @param height
     */
    
    @Override
    public void onMeasure(int width, int height) {
	// as described for superclass method
    	//TODO: 
    	super.onMeasure(width, height);
    }
  
    /**
     * Updates maze graphics.
     */
    public void update() {
	//TODO: update maze graphics
    	Log.v("mazepanel", "calling invalidate") ;
    	invalidate() ;
    }
    
    /**
     * Sets paint object color attribute to given color.
     * @param color
     */
    public void setColor(int color) {
	// TODO: set the current color
    	col.setARGB(255, color, color, color);
    }
    
    /**
     * Takes in color integer values [0-255], returns corresponding color-int value. 
     * @param color values
     */
   //public static int getColorEncoding(int red, int green, int blue) {
	// TODO: provide rgb color encoding 
	  
    //}
    
    /**
     * Returns the RGB value representing the current color. 
     * @return integer RGB value
     */
   public int getColor() {
	   return col.getColor();
   } 
    
    /**
     * Takes in rectangle params, fills rectangle in canvas based on these. 
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public void fillRect(int x, int y, int width, int height) {
	// draw a filled rectangle on the canvas, requires decision on its color
    	Rect rectangle = new Rect(x, y, width + x, height + y);
		maze_canvas.drawRect(rectangle, col);
    	
    }
    
    /**
     * Takes in polygon params, fills polygon in canvas based on these. 
     * Paint is always that for corn.
     * @param xPoints
     * @param yPoints
     * @param nPoints
     */
    public void fillPolygon(int[] xps, int[] yps, int nPoints){
	// translate the points into a path
	// draw a path on the canvase
    	Path path = new Path();
		path.reset(); 
		
		path.moveTo(xps[0], yps[0]);
		
		for (int x = 1; x < xps.length; x++) {
			path.lineTo(xps[x], yps[x]);
		}
		
		path.lineTo(xps[0], yps[0]); 
		
		maze_canvas.drawPath(path, col);
	}

    
    /**
     * Takes in line params, draws line in canvas based on these. 
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     */
    public void drawLine(int x1, int y1, int x2, int y2) {
	// TODO: draw a line on the canvas 
    	maze_canvas.drawLine(x1, y1, x2, y2, col);
    }
    
    /**
     * Takes in oval params, fills oval in canvas based on these. 
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public void fillOval(int x, int y, int width, int height) {
	// TODO: draw an oval on the canvas
    RectF rectf = new RectF(x, y, x + width, y + height);
	maze_canvas.drawOval(rectf, col);
    }
}