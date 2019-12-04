/**
 * 
 */
package taxicompare.app.utils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.SplashScreen;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;

/**
 * @author joshuaMokut
 *
 */
public class Splashing {
	
	private static SplashScreen mySplash;
	private static java.awt.geom.Rectangle2D.Double splashTextArea;
	private static Double  splashProgressArea;
	private static Graphics2D splashGraphics;
	private static Font font;
	
	public static void splashInit() {
		mySplash=SplashScreen.getSplashScreen();//get the default splashscreen
		if (mySplash!=null) {
			Dimension dim=mySplash.getSize();
			int width=dim.width;
			int height= dim.height;
			
			//stake some area out for our information
			splashTextArea=new Rectangle2D.Double(width*0.15, height*0.65, width*0.75, 14 );
			splashProgressArea=new Rectangle2D.Double(width*0.02, height*0.85, width*0.97, 7);
			
			splashGraphics=mySplash.createGraphics();
			font=new Font("Dialog", Font.PLAIN, 12);
			splashGraphics.setFont(font);
			
			splashText("Starting");
			splashProgress(0);
		}
		else {
			System.out.println("wtf");
		}
	}
	
	public static void splashText (String str) {
		if (mySplash!=null && mySplash.isVisible()) {
			splashGraphics.setPaint(Color.RED);
			splashGraphics.fill(splashTextArea);
			
			
			FontMetrics metrics=splashGraphics.getFontMetrics(font);
			int hgt=metrics.getHeight()-metrics.getDescent();
			int adv=metrics.stringWidth(str); 
			
			splashGraphics.setPaint(Color.WHITE);
			if (splashTextArea.getMaxX()>adv) {
				splashGraphics.drawString(str,  (int)((splashTextArea.getMaxX()-adv)/2), 
						(int)(splashTextArea.getY()+hgt-metrics.getDescent()));
			}
			else {
				splashGraphics.drawString(str,  (int)(splashTextArea.width+1), 
						(int)(splashTextArea.getY()+hgt-metrics.getDescent()));
			}
			
			mySplash.update();
		}
	}
	
	public static void splashProgress(int pct) {
		if (mySplash!=null && mySplash.isVisible()) {
			splashGraphics.setPaint(Color.WHITE);
			splashGraphics.fill(splashProgressArea);
			
			splashGraphics.setPaint(Color.BLUE);
			splashGraphics.draw(splashProgressArea);
			
			int x=(int)splashProgressArea.getMinX();
			int y=(int)splashProgressArea.getMinY();
			int wid=(int)splashProgressArea.getWidth();
			int hgt=(int)splashProgressArea.getHeight();
			
			int doneWidth =Math.round((pct*wid)/100f);
			doneWidth=Math.max(0,  Math.min(doneWidth, wid-1));
			
			splashGraphics.setPaint(Color.BLUE);
			splashGraphics.fillRect(x,  y+1, doneWidth, hgt-1);
			
			mySplash.update();
		}
		else {
			System.out.println("coudln't find shit");
		}
	}
	
	public static void Pause(int delay) {
		try {
			Thread.sleep(delay);
		} catch(InterruptedException ex)
		{
			
		}
		
	}	
}
