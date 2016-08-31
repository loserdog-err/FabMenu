# FabMenu
fab 按钮打开 menu 的一个封装组件

##1.效果展示

##2.添加依赖
```compile 'com.chenantao:FabMenu:1.0.0'```

##3.在布局中引入控件
```
 <com.chenantao.fabMenu.FabMenu
        android:id="@+id/fabMenu"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:paddingBottom="10dp"
        android:paddingRight="10dp"
        android:paddingTop="10dp"
        app:mGravity="left_top"
        app:mMenu="@menu/menu"
        app:mMenuItemAnim="zhihu"/>
```
##自定义属性说明
1)mGravity:控件的位置，有四个可选值：左上(left_top),右上(right_top),左下(left_bottom),右下(right_bottom)
2)mMenu:菜单项，跟 activity 设置 menu 一样，直接在 menu 文件夹中设置。
```
<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:android="http://schemas.android.com/apk/res/android">
    <item
        android:id="@+id/menu_ask"
        android:icon="@mipmap/menu1"
        android:title="提问"/>
    <item
        android:id="@+id/menu_answer"
        android:icon="@mipmap/menu2"
        android:title="回答"/>
    <item
        android:id="@+id/menu_write"
        android:icon="@mipmap/menu3"
        android:title="写文章"/>
</menu>
```
3)mMenuItemAnim:菜单打开以及关闭的时候菜单项的动画，libiary 预置了三个动画 zhihu scheduleShow fade,效果如上面所示，你也可以自己实现动画效果，按照如下格式.
```
public interface FabMenuAnim {

	Animator provideOpenAnimator(ViewGroup menuItem, View icon, View title,int index);

	Animator provideCloseAnimator(final ViewGroup menuItem, View icon, View title,int index);

	/**
	 * 避免 item 跟 menu 的动画看起来不协调，提供一个方法为 menu 设置动画时间
	 * @return
	 */
	long provideAnimDuration();
}
```
实现 FabMenuAnim 接口，并设置进控件即可。详情可见 libiary 动画的实现
```
		mFabMenu.setMenuItemAnim(new ZhihuAnim());
```
