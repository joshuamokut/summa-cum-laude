/**
 * 
 */
package taxicompare.app.utils;

import java.net.URL;
import java.net.HttpURLConnection;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author joshuaMokut
 *
 */
public class GoogeDirectionMatrix {
	
	private static String url;
	private String prefixUrl=
			"https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&key=your_api_key&language=en";
	private String origin="&origins=";
	private String destination="&destinations=";
	
	
	private String processString(String s) {
		String longitude;
		String latitude;
		
		int curIndex=0;
		
		while(s.charAt(curIndex)!=' ') {
			curIndex++;
		}
		
		latitude=s.substring(0, curIndex);
		longitude=s.substring(curIndex+1, s.length());
		return longitude+','+latitude;
	}
	
	public GoogeDirectionMatrix(String origin, String destination){
		this.origin =this.origin+processString(origin);
		this.destination=this.destination+processString(destination);
		System.out.println(this.origin);
		buildUrl();
	}
	
	public JSONObject sendRequest() throws Exception {
		URL obj= new URL(url);
		HttpURLConnection con=(HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response= new StringBuffer();
		while((inputLine=in.readLine())!=null) {
			response.append(inputLine);
		}
		in.close();
		JSONObject myresponse=new JSONObject(response.toString());
		
		
		return myresponse;
	}

	private void buildUrl() {
		url=prefixUrl+origin+destination;
	}
}
