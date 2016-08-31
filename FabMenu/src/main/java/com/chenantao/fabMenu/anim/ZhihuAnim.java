package com.chenantao.fabMenu.anim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.ViewGroup;

import com.chenantao.fabMenu.FabMenuAnim;

/**
 * Created by ChenAt on 2016/8/30.
 * desc
 */
public class ZhihuAnim implements FabMenuAnim {

	private static final String TAG = "ZhihuAnim";
//	@Override
//	public void openAnim(ViewGroup menuItem, View icon, View title) {
//		menuItem.setScaleX(0f);
//		menuItem.setScaleY(0f);
//		menuItem.animate().setInterpolator(new OvershootInterpolator())
//			.scaleX(1.0f).scaleY(1.0f).setDuration(300).setListener(null).start();
//	}
//
//	@Override
//	public void closeAnim(final ViewGroup menuItem, View icon, View title) {
//		menuItem.setPivotY(icon.getY() + icon.getMeasuredHeight() / 2);
//		menuItem.animate().scaleX(0).scaleY(0).setDuration(300)
//			.setInterpolator(new AccelerateDecelerateInterpolator())
//			.setListener(new AnimatorListenerAdapter() {
//				@Override
//				public void onAnimationEnd(Animator animation) {
//					menuItem.setVisibility(View.GONE);
//				}
//			}).start();
//	}

	public static final long DURATION = 300;

	public Animator provideOpenAnimator(ViewGroup menuItem, View icon, View title, int index) {
		AnimatorSet set = new AnimatorSet();
		set.playTogether(
			ObjectAnimator.ofFloat(menuItem, "scaleX", 0f, 1f),
			ObjectAnimator.ofFloat(menuItem, "scaleY", 0f, 1f)
		);
		return set.setDuration(400);
	}

	public Animator provideCloseAnimator(final ViewGroup menuItem, View icon, View title, int index) {
		menuItem.setPivotX(icon.getX() + icon.getMeasuredWidth() / 2);
		menuItem.setPivotY(icon.getY() + icon.getMeasuredHeight() / 2);
		AnimatorSet set = new AnimatorSet();
		set.playTogether(
			ObjectAnimator.ofFloat(menuItem, "scaleX", 1f, 0f),
			ObjectAnimator.ofFloat(menuItem, "scaleY", 1f, 0f)
		);
		set.setDuration(DURATION).addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				menuItem.setVisibility(View.GONE);
			}
		});
		return set;
	}

	@Override
	public long provideAnimDuration() {
		return DURATION;
	}
}
