package com.chenantao.fabMenu;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;


/**
 * Created by ChenAt on 2016/8/23.
 */
public class CircleBgImageView extends ImageView {

	private static int DEFAULT_BORDER_WIDTH = 10;//dp
	private int mBorderWidth;
	private int mImgRadius;
	private int mRadius;

	private Paint mPaint;

	private int mMenuResourceId;

	public CircleBgImageView(Context context) {
		this(context, null);
	}

	public CircleBgImageView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CircleBgImageView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		//获取自定义属性
		TypedArray ta = context.obtainStyledAttributes(attrs,
			R.styleable.FabMenu);
		mBorderWidth = ta.getDimensionPixelSize(R.styleable.FabMenu_mBorderWidth,
			Utils.dp2px(DEFAULT_BORDER_WIDTH));
		ta.recycle();
		//初始化画笔
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setColor(Color.WHITE);
		//
		setScaleType(ScaleType.CENTER);
		//将图标着色为黑色
		if (getDrawable() != null) {
			Utils.tintDrawable(getDrawable(), ColorStateList.valueOf(Color.BLACK));
		}
	}


	public void setBorderWidth(int dp) {
		mBorderWidth = Utils.dp2px(dp);
		requestLayout();
		invalidate();
	}

	public void setBackgroundColor(int color) {
		mPaint.setColor(color);
		requestLayout();
		invalidate();
	}

	@Override
	public void setImageResource(int resId) {
		this.setImageResource(resId, ColorStateList.valueOf(Color.BLACK));
	}

	public void setImageResource(int resId, ColorStateList tintColor) {
		super.setImageResource(resId);
		Utils.tintDrawable(getDrawable(), tintColor);
	}

	@Override
	public void setImageDrawable(Drawable drawable) {
		super.setImageDrawable(drawable);
		Utils.tintDrawable(getDrawable(), ColorStateList.valueOf(Color.BLACK));
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		float max = Math.max(getMeasuredWidth(), getMeasuredHeight());
		mRadius = (int) ((max + mBorderWidth * 2) / 2);
		setMeasuredDimension(mRadius * 2, mRadius * 2);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawCircle(mRadius, mRadius, mRadius, mPaint);
		super.onDraw(canvas);
	}
}
