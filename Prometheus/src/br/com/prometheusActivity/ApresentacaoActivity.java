package br.com.prometheusActivity;

import android.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;


public class ApresentacaoActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_apresentacao);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.apresentacao, menu);
		return true;
	}

}
