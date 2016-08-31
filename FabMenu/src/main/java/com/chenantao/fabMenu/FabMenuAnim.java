package com.chenantao.fabMenu;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by ChenAt on 2016/8/30.
 * desc
 */
public interface FabMenuAnim {

	void openAnim(ViewGroup menuItem, View icon, View title);

	void closeAnim(ViewGroup menuItem, View icon, View title);
}
