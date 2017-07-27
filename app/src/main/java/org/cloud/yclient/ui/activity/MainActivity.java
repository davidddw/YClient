/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2017 d05660@163.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.cloud.yclient.ui.activity;

import android.graphics.Color;
import android.support.annotation.IdRes;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.Toast;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import org.cloud.yclient.R;
import org.cloud.yclient.base.SimpleActivity;
import org.cloud.yclient.ui.fragment.AlbumFragment;
import org.cloud.yclient.ui.article.fragment.ArticleMainFragment;
import org.cloud.yclient.ui.fragment.MainFragment;
import org.cloud.yclient.ui.novel.fragment.NovelListFragment;

import butterknife.BindView;

public class MainActivity extends SimpleActivity {

    private static final String TAG = "MainActivity";

    @BindView(R.id.main_bottombar)
    BottomBar mBottombar;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;

    private MainFragment mMainFragment;
    private ArticleMainFragment mCategoryFragment;
    private AlbumFragment mAlbumFragment;
    private NovelListFragment mNovelFragment;

    private long exitTime;

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initEventAndData() {
        initToolbar();
        initBottombar();//初始化fragment
//        String str = mContext.getString(R.string.app_name);
//        setToolBar(mToolbar, str);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void initToolbar() {
        mToolbar.setTitle(R.string.app_name);
        mToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mToolbar);
        if (mToolbarTitle != null) {
            mToolbarTitle.setText(R.string.app_name);
            if(getSupportActionBar()!=null) {
                getSupportActionBar().setDisplayShowTitleEnabled(false);
            }
        }
        mToolbar.setNavigationIcon(R.drawable.img_slide_menu);
    }

    private void initBottombar() {
        mBottombar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId) {
                    case R.id.tab_main:
                        if (null == mMainFragment) {
                            mMainFragment = MainFragment.newInstance();
                        }
                        changeFragment(R.id.frame_layout, mMainFragment);
                        break;
                    case R.id.tab_category:
                        if (null == mCategoryFragment) {
                            mCategoryFragment = ArticleMainFragment.newInstance();
                        }
                        changeFragment(R.id.frame_layout, mCategoryFragment);
                        break;
                    case R.id.tab_album:
                        if (null == mAlbumFragment) {
                            mAlbumFragment = AlbumFragment.newInstance();
                        }
                        changeFragment(R.id.frame_layout, mAlbumFragment);
                        break;
                    case R.id.tab_novel:
                        if (null == mNovelFragment) {
                            mNovelFragment = NovelListFragment.newInstance();
                        }
                        changeFragment(R.id.frame_layout, mNovelFragment);
                        break;
                }
            }
        });
    }

}
