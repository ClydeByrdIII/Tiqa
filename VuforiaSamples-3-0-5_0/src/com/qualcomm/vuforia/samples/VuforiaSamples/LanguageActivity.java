package com.qualcomm.vuforia.samples.VuforiaSamples;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.qualcomm.vuforia.samples.VuforiaSamples.app.CloudRecognition.CloudReco;

public class LanguageActivity extends Activity {

	private String translated;
	private String word;
	private String language;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_language);

		word = getIntent().getStringExtra("word");
		language = getIntent().getStringExtra("language");
		word = fixWord(word);
		
		TextView english = (TextView) findViewById(R.id.english);
		english.setText(word);
		TextView anotherLanguageLabel = (TextView) findViewById(R.id.languageLabel);
		System.out.println("here1: " + calcLanguage(language));
		anotherLanguageLabel.setText(calcLanguage(language));
		
		new TranslateTask().execute("");
	}

	private String fixWord(String input) {
		if (input.equals("board2")) {
			return "board";
		}
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
