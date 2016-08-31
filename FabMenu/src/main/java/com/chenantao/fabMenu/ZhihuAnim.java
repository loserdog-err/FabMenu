package com.chenantao.fabMenu;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.OvershootInterpolator;

/**
 * Created by ChenAt on 2016/8/30.
 * desc
 */
public class ZhihuAnim implements FabMenuAnim {

	@Override
	public void openAnim(ViewGroup menuItem, View icon, View title) {
		menuItem.setScaleX(0f);
		menuItem.setScaleY(0f);
		menuItem.animate().setInterpolator(new OvershootInterpolator())
			.scaleX(1.0f).scaleY(1.0f).setDuration(300).setListener(null).start();
	}

	@Override
	public void closeAnim(final ViewGroup menuItem, View icon, View title) {
		menuItem.setPivotX(icon.getX() + icon.getMeasuredWidth() / 2);
		menuItem.setPivotY(icon.getY() + icon.getMeasuredHeight() / 2);
		menuItem.animate().scaleX(0).scaleY(0).setDuration(300)
			.setInterpolator(new AccelerateDecelerateInterpolator())
			.setListener(new AnimatorListenerAdapter() {
				@Override
				public void onAnimationEnd(Animator animation) {
					menuItem.setVisibility(View.GONE);
				}
			}).start();
	}
}
