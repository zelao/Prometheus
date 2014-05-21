package br.com.prometheusActivity;

import android.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;


public class TeatroActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activ);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.teatro, menu);
		return true;
	}

}
