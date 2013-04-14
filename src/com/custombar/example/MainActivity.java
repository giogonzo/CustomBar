package com.custombar.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.custombar.custombar.R;

public class MainActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Random rand = new Random();
		List<CustomInterface> objects = new ArrayList<CustomInterface>();

		for (int i = 0; i < 300; i++) {
			objects.add(new CustomObject(rand.nextInt(200), rand.nextInt(100)));
		}

		ListView peopleList = (ListView) findViewById(R.id.list);
		peopleList.setAdapter(new ListAdapter(this, R.layout.row1, objects));
	}
}
