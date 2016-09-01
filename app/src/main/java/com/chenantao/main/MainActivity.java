package com.chenantao.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.chenantao.fabMenu.FabMenu;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by chenantao on 2016/7/7 15:47.
 * update:
 * desc:
 */
public class MainActivity extends AppCompatActivity {

	private static final String TAG = "MainActivity";
	private ListView mLv;
	private FabMenu mFabMenu;
//	private ImageView mIv;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mLv = (ListView) findViewById(R.id.lvTest);
		mFabMenu = (FabMenu) findViewById(R.id.fabMenu);
		List<String> datas = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			datas.add("hehe:" + i);
		}
		mLv.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, datas));
	}

}


