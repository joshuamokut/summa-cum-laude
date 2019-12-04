/**
 * 
 */
package taxicompare.app;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import org.json.JSONObject;

import taxicompare.app.WindowEventHandler;
import taxicompare.app.utils.GoogeDirectionMatrix;
import taxicompare.app.utils.Splashing;
import taxicompare.app.utils.YandexGeocoding;


/**
 * @author joshuaMokut
 *
 */
public class Main extends JFrame {

	private WindowEventHandler windowEventHandler;
	private static JTextField departureAddress;
	private static JTextField destinationAddress;
	private static JButton compare;
	private static JLabel departureLabel;
	private static JLabel destinationLabel;
	private static JLabel timeLabel;
	private static JLabel distanceLabel;
	private static JLabel estimatedTime;
	private static JLabel estimatedDistance;
	private static JButton resetButton;
	
	public static GridLayout grid;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param args
	 */
	
	
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				//Initializes and processing splashScreen: the screen that appear when the program is still loading
				Splashing.splashInit();
				Splashing.Pause(1000);
				
				Splashing.splashText("Creating Application...");
				Splashing.splashProgress(33);
				Splashing.Pause(1000);
				 
				Splashing.splashText("Loading Packages...");
				Splashing.splashProgress(66);
				Splashing.Pause(1000);
				
				Splashing.splashText("Almost Done...");
				Splashing.splashProgress(99);
				Splashing.Pause(1000);
				
				
				Main app=new Main();
				app.setVisible(true);
			}
		});	
	}

	public Main() throws HeadlessException {

		super();
		this.setTitle("DistanceCompare");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		windowEventHandler=new WindowEventHandler();
		windowEventHandler.setApp(this);
		this.addWindowListener(windowEventHandler);
		
		
		//create a grid with 2 columns and 3 rows
		grid=new GridLayout(5, 2, 2, 2);
		
		//set layout to be a grid		
		this.setLayout(grid);
		
		//initialize variable for frame components;
		departureLabel= new JLabel();
		destinationLabel= new JLabel();
		departureAddress = new JTextField();
		destinationAddress=new JTextField();
		timeLabel=new JLabel();
		distanceLabel=new JLabel();
		estimatedTime=new JLabel();
		estimatedDistance = new JLabel();
		compare=new JButton("Compute time and distance");
		resetButton=new JButton("RESET");
		
		//set label texts;
		departureLabel.setText("From where :");
		destinationLabel.setText("Where to :");
		timeLabel.setText("Estimated Travel Time : ");
		distanceLabel.setText("Estimated Travel Distance : ");
		

		//add components to the frame
		this.getContentPane().add(departureLabel);
		this.getContentPane().add(departureAddress);
		this.getContentPane().add(destinationLabel);
		this.getContentPane().add(destinationAddress);
		this.getContentPane().add(resetButton);
		this.getContentPane().add(compare);
		this.getContentPane().add(timeLabel);
		this.getContentPane().add(estimatedTime);
		this.getContentPane().add(distanceLabel);
		this.getContentPane().add(estimatedDistance);

		
		compare.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//TODO validate input;

				YandexGeocoding departureRequest=new YandexGeocoding(departureAddress.getText());
				YandexGeocoding destinationRequest=new YandexGeocoding(destinationAddress.getText());
				
				try{
					JSONObject departure = departureRequest.sendRequest();
					JSONObject destination = destinationRequest.sendRequest();					
					
					try {
						String departureCordinate=departure.getJSONObject("response").getJSONObject("GeoObjectCollection").
								getJSONArray("featureMember").getJSONObject(0).getJSONObject("GeoObject")
								.getJSONObject("Point").getString("pos");
						
						
						
						
						String destinationCordinate= 
								destination.getJSONObject("response").getJSONObject("GeoObjectCollection").
								getJSONArray("featureMember").getJSONObject(0).getJSONObject("GeoObject")
								.getJSONObject("Point").getString("pos");
						
						
						GoogeDirectionMatrix queryObject=new GoogeDirectionMatrix(departureCordinate, destinationCordinate);
						
						JSONObject googleQueryResult=queryObject.sendRequest();
						System.out.println(googleQueryResult.getString("status"));
						if (googleQueryResult.getString("status").equals("OK")) {//there was a little challenge here 
							//because I was using the equal to sign instead of the equals 
							//function
							estimatedTime.setText(googleQueryResult.getJSONArray("rows").getJSONObject(0).getJSONArray("elements")
									.getJSONObject(0).getJSONObject("duration").getString("text"));
							
							estimatedDistance.setText(googleQueryResult.getJSONArray("rows").getJSONObject(0).getJSONArray("elements")
									.getJSONObject(0).getJSONObject("distance").getString("text"));
						}
					}catch(Exception e) {
						estimatedTime.setText("INVALID REQUEST");
						estimatedDistance.setText("INVALID REQUEST");
						
					}
					
				}catch(Exception e)
				{
					estimatedTime.setText("INVALID REQUEST");
					estimatedDistance.setText("INVALID REQUEST");
					
				}
				
			}
		});
		
		
		resetButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				estimatedTime.setText("");
				estimatedDistance.setText("");
				departureAddress.setText("");
				destinationAddress.setText("");
			}
			
		});
		
		setBounds(0, 0, 600, 600);
		
		
	}

	
}
