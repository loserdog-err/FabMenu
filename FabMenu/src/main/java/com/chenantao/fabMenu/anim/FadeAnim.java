package com.chenantao.fabMenu.anim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.ViewGroup;

import com.chenantao.fabMenu.FabMenuAnim;

/**
 * Created by ChenAt on 2016/8/30.
 * desc
 */
public class FadeAnim implements FabMenuAnim {
	public static final long DURATION = 300;

	@Override
	public Animator provideOpenAnimator(ViewGroup menuItem, View icon, View title, int index) {
		return ObjectAnimator.ofFloat(menuItem, "alpha", 0f, 1f);
	}

	@Override
	public Animator provideCloseAnimator(final ViewGroup menuItem, View icon, View title, int index) {
		Animator animator = ObjectAnimator.ofFloat(menuItem, "alpha", 1f, 0f);
		animator.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				menuItem.setVisibility(View.GONE);
			}
		});
		return animator;
	}

	@Override
	public long provideAnimDuration() {
		return 300;
	}
//	@Override
//	public void openAnim(ViewGroup menuItem, View icon, View title) {
//		menuItem.setAlpha(0f);
//		menuItem.animate().alpha(1f).setDuration(300).setListener(null).start();
//	}
//
//	@Override
//	public void closeAnim(final ViewGroup menuItem, View icon, View title) {
//		menuItem.animate().alpha(0f).setDuration(300).setListener(new AnimatorListenerAdapter() {
//			@Override
//			public void onAnimationEnd(Animator animation) {
//				menuItem.setVisibility(View.GONE);
//			}
//		}).start();
//	}
}
