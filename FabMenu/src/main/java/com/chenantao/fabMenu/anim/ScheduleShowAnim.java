package com.chenantao.fabMenu.anim;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;

import com.chenantao.fabMenu.FabMenuAnim;

/**
 * Created by ChenAt on 2016/8/31.
 * desc
 */
public class ScheduleShowAnim implements FabMenuAnim {

	@Override
	public Animator provideOpenAnimator(ViewGroup menuItem, View icon, View title, int index) {
		menuItem.setScaleX(0f);
		menuItem.setScaleY(0f);
		menuItem.setPivotX(icon.getX() + icon.getMeasuredWidth() / 2);
		menuItem.setPivotY(icon.getY() + icon.getMeasuredHeight() / 2);
		AnimatorSet set = new AnimatorSet();
		set.playTogether(
			ObjectAnimator.ofFloat(menuItem, "scaleX", 0f, 1f),
			ObjectAnimator.ofFloat(menuItem, "scaleY", 0f, 1f)
		);
		set.setInterpolator(new OvershootInterpolator());
		set.setStartDelay(index * 50);
		set.setDuration(200);
		return set;
	}

	@Override
	public Animator provideCloseAnimator(ViewGroup menuItem, View icon, View title, int index) {
		AnimatorSet set = new AnimatorSet();
		set.playTogether(
			ObjectAnimator.ofFloat(menuItem, "scaleX", 1f, 0f),
			ObjectAnimator.ofFloat(menuItem, "scaleY", 1f, 0f)
		);
		set.setStartDelay(200 - index * 50);
		set.setDuration(200);
		return set;
	}

	@Override
	public long provideAnimDuration() {
		return 200;
	}
}
