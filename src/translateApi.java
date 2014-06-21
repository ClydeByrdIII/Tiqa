package src;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;



public class translateApi {
	
	public static void writeStream(OutputStream out, String[] values) {
		StringBuilder formEncodedData = new StringBuilder("");
		try {
			for(int i = 0; i < values.length ; i+=2) {
				formEncodedData.append(values[i] + "=" + values[i+1] + "&");
			}
			formEncodedData.deleteCharAt(formEncodedData.length() - 1);
			System.out.println(formEncodedData.toString());
			out.write(formEncodedData.toString().getBytes());
			out.close();
		} catch(Exception error) {
			error.printStackTrace();
		} 
		
		
	}
	
	public static void readStream(InputStream in) {
		byte[] response = new byte[1024];
		String text = "";
		try {
			while(in.read(response) != -1) {
				text += new String(response);
			}
			in.close();
			System.out.println(text);
		} catch(Exception error) {
			error.printStackTrace();
		}
	}

	public static void translate(String srcText, String srcLang, String destLang) {
		URL url;
		HttpURLConnection urlConnection = null;
		try {
			url = new URL("https://translate.yandex.net/api/v1.5/tr.json/translate");
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setDoOutput(true);
			urlConnection.setChunkedStreamingMode(0);
			String array[] = {"key", "trnsl.1.1.20140621T062348Z.a7da5b9951adccec.328c9f2044693e7e2886bd82d197fc29bac29864", "lang", srcLang + "-" + destLang, "text", srcText};
			urlConnection.addRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
		    writeStream(out, array);
			InputStream in = new BufferedInputStream(urlConnection.getInputStream());
			readStream(in);
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
