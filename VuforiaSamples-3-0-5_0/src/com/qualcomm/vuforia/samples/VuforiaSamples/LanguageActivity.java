package com.qualcomm.vuforia.samples.VuforiaSamples;

import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.TextView;

import com.qualcomm.vuforia.samples.VuforiaSamples.app.CloudRecognition.CloudReco;

public class LanguageActivity extends Activity {

	private String translated;
	private String word;
	private String language;
	private TextToSpeech ttobj;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_language);

		word = getIntent().getStringExtra("word");
		word = fixWord(word);
		language = getIntent().getStringExtra("language");

		TextView english = (TextView) findViewById(R.id.english);
		english.setText(word);
		TextView anotherLanguageLabel = (TextView) findViewById(R.id.languageLabel);
		System.out.println("here1: " + calcLanguage(language));
		anotherLanguageLabel.setText(calcLanguage(language));

		new TranslateTask().execute("");

		ttobj = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
			@Override
			public void onInit(int status) {
				if (status != TextToSpeech.ERROR) {
					ttobj.setLanguage(fixLanguage(language));
				}
			}
		});
	}
	
	public void onSoundClick(View view) {
		ttobj.speak(fixWord(word), TextToSpeech.QUEUE_FLUSH, null);
	}

	private Locale fixLanguage(String input) {
		System.out.println("here1 language: "+ input);
		if (input.equals("en")) {
			return Locale.ENGLISH;
		} else if (input.equals("ru")) {
			return Locale.ENGLISH;
		} else if (input.equals("uk")) {
			return Locale.ENGLISH;
		} else if (input.equals("de")) {
			return Locale.GERMAN;
		} else if (input.equals("fr")) {
			return Locale.FRENCH;
		} else if (input.equals("es")) {
			return Locale.ENGLISH;
		} else if (input.equals("it")) {
			return Locale.ITALIAN;
		} else if (input.equals("tr")) {
			return Locale.ENGLISH;
		}
		
		return Locale.ENGLISH;
	}

	private String fixWord(String input) {
		input = input.replaceAll("0","");
		input = input.replaceAll("1","");
		input = input.replaceAll("2","");
		input = input.replaceAll("3","");
		input = input.replaceAll("4","");
		input = input.replaceAll("5","");
		return input;
	}

	public void onScanClick(View view) {
		Intent intent = new Intent(this, CloudReco.class);
		intent.putExtra("language", language);
		startActivity(intent);
	}

	public void onMenuClick(View view) {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}

	private String calcLanguage(String input) {
		if (input.equals("en")) {
			return "In English";
		} else if (input.equals("ru")) {
			return "In Russian";
		} else if (input.equals("uk")) {
			return "In Ukranian";
		} else if (input.equals("de")) {
			return "In German";
		} else if (input.equals("fr")) {
			return "In French";
		} else if (input.equals("es")) {
			return "In Spanish";
		} else if (input.equals("it")) {
			return "In Italian";
		} else if (input.equals("tr")) {
			return "In Turkish";
		}
		return "";
	}

	private class TranslateTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			translated = new TranslateAPI().translate(word, "en", language);
			return translated;
		}

		@Override
		protected void onPostExecute(String result) {

			TextView anotherLanguage = (TextView) findViewById(R.id.language);
			anotherLanguage.setText(translated);
		}

		@Override
		protected void onPreExecute() {
		}

		@Override
		protected void onProgressUpdate(Void... values) {
		}
	}
}
