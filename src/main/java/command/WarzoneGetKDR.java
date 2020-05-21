package command;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
class CodObject {

	String status;
	Data data;

	public String getStatus() {
		return status;
	}

	public Data getData() {
		return data;
	}

}

@JsonIgnoreProperties(ignoreUnknown = true)
class Data {
	LifeTime lifetime;

	public LifeTime getLifetime() {
		return lifetime;
	}

	public void setLifetime(LifeTime lifetime) {
		this.lifetime = lifetime;
	}

}

@JsonIgnoreProperties(ignoreUnknown = true)
class LifeTime {
	Mode mode;

	public Mode getMode() {
		return mode;
	}

	public void setMode(Mode mode) {
		this.mode = mode;
	}

}

@JsonIgnoreProperties(ignoreUnknown = true)
class Mode {
	Br br;

	public Br getBr() {
		return br;
	}

	public void setBr(Br br) {
		this.br = br;
	}

}

@JsonIgnoreProperties(ignoreUnknown = true)
class Br {
	Properties properties;

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

}

@JsonIgnoreProperties(ignoreUnknown = true)
class Properties {
	double kdRatio;

	public double getKdRatio() {
		return kdRatio;
	}

	public void setKdRatio(double kdRatio) {
		this.kdRatio = kdRatio;
	}

}

@JsonIgnoreProperties(ignoreUnknown = true)
public class WarzoneGetKDR {

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static double requestActivisionKDR(String nickname, String tag) throws IOException {
	    
	    
	    OkHttpClient client1 = new OkHttpClient().newBuilder()
		    .build();
		  Request request1 = new Request.Builder()
		    .url("https://profile.callofduty.com/cod/login")
		    .method("GET", null)
		    .build();
		  Response response1 = client1.newCall(request1).execute();
		  
	    List<String> tre = response1.headers("Set-Cookie");
	    String hmm = tre.get(0);
	    String[] array = hmm.split(";");
	    System.out.println("XSRF TOKEN : "+array[0]);
	    String xrfstoken = array[0];
	    
	    OkHttpClient client3 = new OkHttpClient().newBuilder()
		    .build();
		  MediaType mediaType = MediaType.parse("text/plain");
		  RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
		    .addFormDataPart("username", "{{wear96@gmail.com}}")
		    .addFormDataPart("password", "{{Swifty8wear}}")
		    .addFormDataPart("remember_me", "true")
		    .addFormDataPart("_csrf", "{{"+xrfstoken+"}}")
		    .build();
		  Request request3 = new Request.Builder()
		    .url("https://profile.callofduty.com/do_login?new_SiteId=cod")
		    .method("POST", body)
		    .addHeader("Cookie", "XSRF-TOKEN={{"+xrfstoken+"}}")
		    .build();
		  Response response3 = client3.newCall(request3).execute();
		  System.out.println("/////////");
		  System.out.println("/////////");
		  System.out.println(response3.isSuccessful());
		  System.out.println("/////////");
		  System.out.println(response3);
		  System.out.println("////////////////////////////////////////////");
		  System.out.println(response3.challenges());
	    
//	    OkHttpClient client = new OkHttpClient().newBuilder()
//		    .build();
//		  Request request2 = new Request.Builder()
//		    .url("https://my.callofduty.com/api/papi-client/stats/cod/v1/title/mw/platform/uno/gamer/akusanas%234678954/profile/type/wz")
//		    .method("GET", null)
//		    .addHeader("Cookie", "utkn={{UTKN}}; rtkn={{RTKN}};")
//		    .build();
//		  Response response2 = client.newCall(request2).execute();
	    
	    
	    

		URL urlForGetRequest = new URL(
				"https://my.callofduty.com/api/papi-client/stats/cod/v1/title/mw/platform/uno/gamer/" + nickname + "%23"
						+ tag + "/profile/type/wz");

		String readLine = null;// wLuk0sis-YT

		HttpURLConnection conection = (HttpURLConnection) urlForGetRequest.openConnection();

		conection.setRequestMethod("GET");

		int responseCode = conection.getResponseCode();

		if (responseCode == HttpURLConnection.HTTP_OK) {

			BufferedReader in = new BufferedReader(

					new InputStreamReader(conection.getInputStream()));

			StringBuffer response = new StringBuffer();

			while ((readLine = in.readLine()) != null) {

				response.append(readLine);
			}
			in.close();

			ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
					false);
			CodObject codObject = objectMapper.readValue(response.toString(), CodObject.class);

			if (codObject.status.matches("error")) {
				//System.out.println(codObject.toString());
				System.out.println("ERROR ////// blogas activision tagas");
				return 0.0;
			}
			if (codObject.status.matches("success")) {
				System.out.println(
						"SUCCSESS: Grazino zaidejo K/D:Ratio : " + codObject.data.lifetime.mode.br.properties.kdRatio);
				return codObject.data.lifetime.mode.br.properties.kdRatio;
			}

		} else {

			System.out.println("GET NOT WORKED");

		}
		return 0.0;

	}

}