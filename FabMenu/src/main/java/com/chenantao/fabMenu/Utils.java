package com.chenantao.fabMenu;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.WindowManager;

/**
 * Created by ChenAt on 2016/8/23.
 */
public class Utils {

	public static int dp2px(float dp) {
		return (int) TypedValue.applyDimension(
			TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());
	}

	public static void tintDrawable(Drawable drawable, ColorStateList color) {
		final Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
		DrawableCompat.setTintList(wrappedDrawable, color);
	}

	/**
	 * 获得屏幕高度
	 *
	 * @param context
	 * @return
	 */
	public static int getScreenWidth(Context context)
	{
		WindowManager wm = (WindowManager) context
			.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics.widthPixels;
	}

	/**
	 * 获得屏幕宽度
	 *
	 * @param context
	 * @return
	 */
	public static int getScreenHeight(Context context)
	{
		WindowManager wm = (WindowManager) context
			.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics.heightPixels;
	}
}
