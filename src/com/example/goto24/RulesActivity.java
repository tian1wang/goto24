package com.example.goto24;

import com.example.o.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.app.Activity;
import android.view.Menu;

public class RulesActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rules);
	}

	public void p(View view) {

		Intent intent = new Intent(this, MainActivity.class);
		this.startActivity(intent);
		this.finish();

	}

}
