package com.cn.theodore;

import java.util.ArrayList;

import com.cn.theodore.util.DragGridAdapter;
import com.cn.theodore.util.DragGridView;



import android.app.Activity;
import android.os.Bundle;
import android.view.animation.TranslateAnimation;

public class AniGridViewActivity extends Activity {
	/** GridView. */
	private DragGridView gridView;
	TranslateAnimation left, right;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		com.cn.theodore.util.Configure.init(this);
		gridView = (DragGridView) findViewById(R.id.gridview);
		ArrayList<String> l = new ArrayList<String>();
		for (int i = 0; i < 8; i++) {
			l.add("" + i);
		}
		DragGridAdapter adapter = new DragGridAdapter(AniGridViewActivity.this, l);
		gridView.setAdapter(adapter);
	}
}