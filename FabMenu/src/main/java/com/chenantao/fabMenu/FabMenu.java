package com.chenantao.fabMenu;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.v7.view.SupportMenuInflater;
import android.support.v7.view.menu.MenuBuilder;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chenantao.fabMenu.anim.FadeAnim;
import com.chenantao.fabMenu.anim.ScheduleShowAnim;
import com.chenantao.fabMenu.anim.ZhihuAnim;


/**
 * Created by ChenAt on 2016/8/23.
 * desc
 */
public class FabMenu extends ViewGroup {

	private static final String TAG = "FloatingMenu";

	public static final int GRAVITY_LEFT_TOP = 0;
	public static final int GRAVITY_RIGHT_TOP = 1;
	public static final int GRAVITY_LEFT_BOTTOM = 2;
	public static final int GRAVITY_RIGHT_BOTTOM = 3;

	public static final int ANIM_FADE = 0;//淡入淡出动画
	public static final int ANIM_ZHIHU = 1;//仿知乎动画
	public static final int ANIM_SCHEDULE_SHOW = 2;//顺序出现

	private static final int BORDER_ICON_WIDTH = 8;//dp
	private static final int BORDER_FAB_WIDTH = 6;//dp

	private static final int MENU_LINE_SPACE = 10;//DP

	private static final String TAG_ICON = "icon";
	private static final String TAG_TITLE = "title";

	private static final int DURATION_ANIM = 400;

	//自定义属性
	private int mMenuResourceId;
	private int mGravity;

	private Menu mMenu;
	private CircleBgImageView mFab;

	private boolean mIsMenuOpen = false;

	private int mMenuIconWidth;

	private Rect mTouchRect;//这个 View 因为需要一层遮罩层，所以是强制占满全屏，mTouchRect 是可见的大小

	private OnMenuClickListener mListener;
//	private FabMenuAnim mAnim;

	private FabMenuAnim mMenuAnimator;
	private Animator mCurrentAnimator;

	public FabMenu(Context context) {
		this(context, null);
	}

	public FabMenu(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public FabMenu(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		//获取自定义属性
		TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.FabMenu);
		mMenuResourceId = ta.getResourceId(R.styleable.FabMenu_mMenu, -1);
		mGravity = ta.getInt(R.styleable.FabMenu_mGravity, GRAVITY_LEFT_TOP);
		initAnim(ta.getInt(R.styleable.FabMenu_mMenuItemAnim, -1));
		ta.recycle();
		mMenu = new MenuBuilder(context);
		new SupportMenuInflater(context).inflate(mMenuResourceId, mMenu);
		//添加子view
		//初始化 fab
		generateFab();
		//根据 menu 生成子view
		generateMenuItemToView(mMenu);
		mTouchRect = new Rect();
	}

	private void initAnim(int anim) {
		switch (anim) {
			case ANIM_FADE:
				mMenuAnimator = new FadeAnim();
				break;
			case ANIM_ZHIHU:
				mMenuAnimator = new ZhihuAnim();
				break;
			case ANIM_SCHEDULE_SHOW:
				mMenuAnimator = new ScheduleShowAnim();
				break;
			default:
				mMenuAnimator = new FadeAnim();
		}
	}

	private void generateFab() {
		mFab = new CircleBgImageView(getContext());
		mFab.setImageResource(R.mipmap.ic_add, ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
		mFab.setBorderWidth(Utils.dp2px(BORDER_FAB_WIDTH));
		mFab.setBackgroundColor(Color.parseColor("#2EACFF"));
		addView(mFab);
		mFab.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				toggleMenu(true);
				if (mListener != null) {
					mListener.onFabClick(v);
				}
			}
		});
	}

	//将 menu 转换成view
	private void generateMenuItemToView(Menu menu) {
		for (int i = 0; i < menu.size(); i++) {
			final MenuItem item = menu.getItem(i);
			LinearLayout llMenuWrap = new LinearLayout(getContext());
			int wrapPadding = Utils.dp2px(MENU_LINE_SPACE);
			llMenuWrap.setPadding(0, wrapPadding, 0, wrapPadding);
			llMenuWrap.setGravity(Gravity.CENTER);
			llMenuWrap.setOrientation(LinearLayout.HORIZONTAL);
			//标题 view
			TextView tvTitle = new TextView(getContext());
			tvTitle.setPadding(Utils.dp2px(10), Utils.dp2px(5), Utils.dp2px(10), Utils.dp2px(5));
			tvTitle.setText(item.getTitle());
			tvTitle.setBackground(getResources().getDrawable(R.drawable.title_bg));
			tvTitle.setTag(TAG_TITLE);
			//icon
			CircleBgImageView ivIcon = new CircleBgImageView(getContext());
			ivIcon.setBorderWidth(BORDER_ICON_WIDTH);
			ivIcon.setImageDrawable(item.getIcon());
			ivIcon.setTag(TAG_ICON);
			mMenuIconWidth = Utils.dp2px(BORDER_ICON_WIDTH) * 2 + item.getIcon().getIntrinsicWidth();
			//
			if (mGravity == GRAVITY_LEFT_BOTTOM || mGravity == GRAVITY_LEFT_TOP) {
				llMenuWrap.addView(ivIcon);
				llMenuWrap.addView(tvTitle);
				((LinearLayout.LayoutParams) tvTitle.getLayoutParams()).leftMargin = Utils.dp2px(10);
			} else {
				llMenuWrap.addView(tvTitle);
				llMenuWrap.addView(ivIcon);
				((LinearLayout.LayoutParams) tvTitle.getLayoutParams()).rightMargin = Utils.dp2px(10);
			}
			llMenuWrap.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (mListener != null) {
						mListener.onMenuItemClick(v, item.getTitle().toString());
					}
				}
			});
			llMenuWrap.setVisibility(GONE);
			addView(llMenuWrap);
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int childCount = getChildCount();
		for (int i = 0; i < childCount; i++) {
			measureChildWithMargins(getChildAt(i), widthMeasureSpec, 0, heightMeasureSpec, 0);
		}
		setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec));
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int childCount = getChildCount();
		int left = getPaddingLeft() + l;
		int top = getPaddingTop() + t;
		int right = r - getPaddingRight();
		int bottom = b - getPaddingBottom();
		for (int i = 0; i < childCount; i++) {
			View child = getChildAt(i);
			switch (mGravity) {
				case GRAVITY_LEFT_TOP:
					int maxRight = 0;
					if (i == 0) {
						child.layout(left, top, left + child.getMeasuredWidth(), top + child.getMeasuredHeight());
					} else {
						int menuLeft = (int) (mFab.getX() + (mFab.getMeasuredWidth() - mMenuIconWidth) / 2);
						int menuRight = menuLeft + child.getMeasuredWidth();
						maxRight = Math.max(maxRight, menuRight);
						child.layout(menuLeft, top, menuRight, top + child.getMeasuredHeight());
					}
					top += child.getMeasuredHeight();
					//计算实际可操作的区域大小
					mTouchRect.set(left, (int) mFab.getY(), maxRight, top);
					break;
				case GRAVITY_RIGHT_TOP:
					int minLeft = 9999;
					if (i == 0) {
						child.layout(right - child.getMeasuredWidth(), top, right, top + child.getMeasuredHeight());
					} else {
						int menuRight = (int) (mFab.getX() + (mFab.getMeasuredWidth() + mMenuIconWidth) / 2);
						int menuLeft = menuRight - child.getMeasuredWidth();
						minLeft = Math.min(menuLeft, minLeft);
						child.layout(menuLeft, top, menuRight, top + child.getMeasuredHeight());
					}
					top += child.getMeasuredHeight();
					mTouchRect.set(minLeft, (int) mFab.getY(), right, top);
					break;
				case GRAVITY_LEFT_BOTTOM:
					maxRight = 0;
					if (i == 0) {
						child.layout(left, bottom - child.getMeasuredHeight(), left + child.getMeasuredWidth(), bottom);
					} else {
						int menuLeft = (int) (mFab.getX() + (mFab.getMeasuredWidth() - mMenuIconWidth) / 2);
						int menuRight = menuLeft + child.getMeasuredWidth();
						maxRight = Math.max(maxRight, menuRight);
						child.layout(menuLeft, bottom - child.getMeasuredHeight(), menuLeft + child.getMeasuredWidth(), bottom);
					}
					bottom -= child.getMeasuredHeight();
					//计算实际可操作的区域大小
					mTouchRect.set(left, bottom, maxRight, b - getPaddingBottom());
					break;
				case GRAVITY_RIGHT_BOTTOM:
					minLeft = 9999;
					if (i == 0) {
						child.layout(right - child.getMeasuredWidth(), bottom - child.getMeasuredHeight(), right, bottom);
					} else {
						int menuRight = (int) (mFab.getX() + (mFab.getMeasuredWidth() + mMenuIconWidth) / 2);
						int menuLeft = menuRight - child.getMeasuredWidth();
						minLeft = Math.min(menuLeft, minLeft);
						child.layout(menuRight - child.getMeasuredWidth(), bottom - child.getMeasuredHeight(), menuRight, bottom);
					}
					bottom -= child.getMeasuredHeight();
					mTouchRect.set(minLeft, bottom, right, b - getPaddingBottom());
					break;
			}
		}
	}

	private void toggleMenu(boolean withAnim) {
		if (mFab == null) {
			return;
		}
		if (mCurrentAnimator != null && mCurrentAnimator.isStarted()) {
			mCurrentAnimator.cancel();
		}
		if (withAnim) {
			mIsMenuOpen = !mIsMenuOpen;
			//menu 将要打开
			float degree = mIsMenuOpen ? -45f : 0f;
			AnimatorSet set = new AnimatorSet();
			mCurrentAnimator = set;
			Animator fabAnimator = ObjectAnimator.ofFloat(mFab, "rotation", degree);
			AnimatorSet.Builder animBuilder = set.play(fabAnimator);
			for (int i = 0; i < getChildCount(); i++) {
				if (i != 0) {
					final ViewGroup view = (ViewGroup) getChildAt(i);
					if (!mIsMenuOpen) {//should close
						animBuilder.with(mMenuAnimator.provideCloseAnimator(view, view.findViewWithTag(TAG_ICON), view.findViewWithTag(TAG_TITLE), i - 1));
					} else {//should open
						setBackgroundColor(0x750a0a0a);
						view.setVisibility(VISIBLE);
						animBuilder.with(mMenuAnimator.provideOpenAnimator(view, view.findViewWithTag(TAG_ICON), view.findViewWithTag(TAG_TITLE), i - 1));
					}
				}
			}
			set.addListener(new AnimatorListenerAdapter() {
				@Override
				public void onAnimationEnd(Animator animation) {
					if (!mIsMenuOpen) {
						setBackgroundColor(Color.TRANSPARENT);
					}
				}
			});
			set.setDuration(mMenuAnimator.provideAnimDuration());
			set.start();
		}
	}

	public void setMenuItemAnim(FabMenuAnim anim) {
		if (anim != null) {
			this.mMenuAnimator = anim;
		}
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		Log.d(TAG, "onInterceptTouchEvent:" + mTouchRect);
		if (ev.getAction() == MotionEvent.ACTION_DOWN) {
			//当前菜单打开，且触摸区域没在菜单范围内，则关闭菜单
			if (!mTouchRect.contains((int) ev.getX(), (int) ev.getY()) && mIsMenuOpen) {
				toggleMenu(true);
			}
		}
		return super.onInterceptTouchEvent(ev);
	}

	public void setOnMenuItemClickListener(OnMenuClickListener listener) {
		mListener = listener;
	}

	@Override
	protected LayoutParams generateDefaultLayoutParams() {
		return new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	}

	public abstract static class OnMenuClickListener {
		public abstract void onMenuItemClick(View view, String title);

		public void onFabClick(View view) {
		}
	}
}
