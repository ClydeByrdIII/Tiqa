package com.qualcomm.vuforia.samples.VuforiaSamples;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.qualcomm.vuforia.samples.VuforiaSamples.app.CloudRecognition.CloudReco;

public class MainActivity extends Activity {
	private Spinner spinner;
	private String languages[] = { "en", "ru", "uk", "de", "fr", "es", "it", "tr" };
	private String choice;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		addListenerOnButton();
		addListenerOnSpinnerItemSelection();
		
		choice = "en";
		
		spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
				choice = languages[pos];
			}

			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
	}

	public void addListenerOnSpinnerItemSelection() {
		spinner = (Spinner) findViewById(R.id.spinner1);
		spinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());
	}

	public void addListenerOnButton() {
		spinner = (Spinner) findViewById(R.id.spinner1);
	}

	public void on_start_click(View view) {
		Intent intent = new Intent(this, CloudReco.class);
		intent.putExtra("language", choice);
		startActivity(intent);
	}

}
