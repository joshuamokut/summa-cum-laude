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
public class YandexGeocoding {
	
	private String url;
	private String address;
	private String prefixUrl=
			"https://geocode-maps.yandex.ru/1.x/?apikey=your_api_key&"
			+ "format=json&rspn=1&ll=49.108795,55.796289&spn=5.0,5.0&lang=en-US&geocode=";
	
	public YandexGeocoding(String s){
		this.address = s.replace(' ', '+');
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
		url=prefixUrl+address;
	}
}
