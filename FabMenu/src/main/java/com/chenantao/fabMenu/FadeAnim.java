package com.chenantao.fabMenu;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by ChenAt on 2016/8/30.
 * desc
 */
public class FadeAnim implements FabMenuAnim {
	@Override
	public void openAnim(ViewGroup menuItem, View icon, View title) {
		menuItem.setAlpha(0f);
		menuItem.animate().alpha(1f).setDuration(300).setListener(null).start();
	}

	@Override
	public void closeAnim(final ViewGroup menuItem, View icon, View title) {
		menuItem.animate().alpha(0f).setDuration(300).setListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				menuItem.setVisibility(View.GONE);
			}
		}).start();
	}
}
