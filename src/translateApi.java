package src;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import json.JSONObject;



public class translateApi {
	
	public static void writeStream(OutputStream out, String data) {
		try {
			out.write(data.getBytes());
			out.close();
		} catch(Exception error) {
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
		ret = (String) json.get(st);
		return ret;
	}

	public static void translate(String srcText, String srcLang, String destLang) {
		URL url;
		HttpURLConnection urlConnection = null;
		try {
			// create a connection to the translation engine
			url = new URL("https://translate.yandex.net/api/v1.5/tr.json/translate");
			urlConnection = (HttpURLConnection) url.openConnection();
			// implicitly enable POST request
			urlConnection.setDoOutput(true);
			// format data
			String requestParameters[] = 
				{	// api key
					"key", "trnsl.1.1.20140621T062348Z.a7da5b9951adccec.328c9f2044693e7e2886bd82d197fc29bac29864",
					// translating to, from
					"lang", srcLang + "-" + destLang,
					// text to translate
					"text", srcText
				};
			
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
		    
		    // Get Response
			InputStream in = new BufferedInputStream(urlConnection.getInputStream());
			String response = readStream(in);
			System.out.println(response);
		} catch(Exception error) {
			error.printStackTrace();
		} finally {
			urlConnection.disconnect();
		}
	}

	public static void main(String args[]) {
		translate("Hello","en","es");
	}
}
