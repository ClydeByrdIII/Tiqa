package src;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import json.JSONArray;
import json.JSONObject;



public class translateApi {
	
	public static void writeStream(OutputStream out, String data) {
		try {
			out.write(data.getBytes());
			out.close();
		} catch(IOException error) {
			error.printStackTrace();
		} 
		
	}
	
	public static String readStream(InputStream in) {
		byte[] response = new byte[1024];
		String text = "";
		try {
			while(in.read(response) != -1) {
				text += new String(response);
			}
			in.close();
		} catch(Exception error) {
			error.printStackTrace();
		}
		text = parseJsonString(text);
		return text;
	}
	
	//This gets the translation from the json string
	public static String parseJsonString(String st){
		String ret = "";
		JSONObject json = null;
		json = new JSONObject(st);
		JSONArray arr = (JSONArray) json.get("text");
		ret = (String) arr.get(0);
		return ret;
	}

	public static String translate(String srcText, String srcLang, String destLang) {
		URL url;
		HttpURLConnection urlConnection = null;
		String translationEngine = "https://translate.yandex.net/api/v1.5/tr.json/translate";
		String response = null;
		try {
			// create a connection to the translation engine
			url = new URL(translationEngine);
			urlConnection = (HttpURLConnection) url.openConnection();
			// implicitly enable POST request
			urlConnection.setDoOutput(true);
			urlConnection.setConnectTimeout(30000);
			// format data
			String requestParameters[] = 
				{	// api key
					"key", "trnsl.1.1.20140621T062348Z.a7da5b9951adccec.328c9f2044693e7e2886bd82d197fc29bac29864",
					// translating to, from
					"lang", srcLang + "-" + destLang,
					// text to translate
					"text", srcText
				};
			
			// encoded in x-www-form-urlencoded ( key=value&key2=value2... )
			StringBuilder data = new StringBuilder();
			for(int i = 0; i < requestParameters.length ; i+=2) {
				data.append(requestParameters[i] + "=" + requestParameters[i+1] + "&");
			}
			
			// delete the last &
			data.setLength(data.length() - 1);
			urlConnection.setFixedLengthStreamingMode(data.length());
			
			// set the format of the data
			urlConnection.addRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			
			// Make request
			OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
		    
		    writeStream(out, data.toString());
		   
		    if(urlConnection.getResponseCode() != 200) {
		    	// Error
		    	response = "Error HTTP Status Code:" 
		    	+ urlConnection.getResponseCode()
		    	+ " Message: " + urlConnection.getResponseMessage();
		    	
			} else {
			    // Get Response
				InputStream in = new BufferedInputStream(urlConnection.getInputStream());
				response = readStream(in);
		    }
		} catch(Exception error) {
			error.printStackTrace();
		} finally {
			urlConnection.disconnect();
		}
		
		return response;
	}

	public static void main(String args[]) {
		System.out.println(translate("Hello I am John","en","es"));
	}
}
