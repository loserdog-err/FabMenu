package com.chenantao.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.chenantao.fabMenu.FabMenu;
import com.chenantao.fabMenu.anim.ZhihuAnim;

public class MainActivity extends AppCompatActivity {

	FabMenu mFabMenu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mFabMenu = (FabMenu) findViewById(R.id.fabMenu);
		mFabMenu.setMenuItemAnim(new ZhihuAnim());

		mFabMenu.setOnMenuItemClickListener(new FabMenu.OnMenuClickListener() {
			@Override
			public void onMenuItemClick(View view, String title) {
				Toast.makeText(MainActivity.this, title + ",click", Toast.LENGTH_SHORT).show();
			}
		});

	}
}
