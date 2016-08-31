package com.chenantao.fabMenu;

import android.animation.Animator;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by ChenAt on 2016/8/30.
 * desc
 */
public interface FabMenuAnim {

	Animator provideOpenAnimator(ViewGroup menuItem, View icon, View title,int index);

	Animator provideCloseAnimator(final ViewGroup menuItem, View icon, View title,int index);

	/**
	 * 避免 item 跟 menu 的动画看起来不协调，提供一个方法为 menu 设置动画时间
	 * @return
	 */
	long provideAnimDuration();
}
