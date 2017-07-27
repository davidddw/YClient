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
package org.cloud.yclient.ui.novel.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.wang.avi.AVLoadingIndicatorView;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.cloud.yclient.R;
import org.cloud.yclient.base.BaseActivity;
import org.cloud.yclient.base.contract.NovelInfoContract;
import org.cloud.yclient.constants.Constants;
import org.cloud.yclient.model.bean.NovelChapterBean;
import org.cloud.yclient.model.bean.NovelInfoBean;
import org.cloud.yclient.ui.novel.adapter.NovelChapterListAdapter;
import org.cloud.yclient.ui.novel.presenter.NovelInfoPresenter;
import org.cloud.yclient.utils.GlideHelper;
import org.cloud.yclient.utils.NetUtils;
import org.cloud.yclient.utils.StatusBarUtil;
import org.cloud.yclient.utils.SystemUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.BlurTransformation;

public class NovelChapterActivity extends BaseActivity<NovelInfoPresenter> implements NovelInfoContract.View, NestedScrollView.OnScrollChangeListener {

    private static final String ARG_REPOSITORY = "arg_repository";

    @BindView(R.id.progressbar)
    AVLoadingIndicatorView mPbProgress;
    @BindView(R.id.flNovelChapter)
    FrameLayout mFlNovelChapter;

    @BindView(R.id.ivNovelBgImage)
    ImageView mIvNovelBgImage;

    @BindView(R.id.nsvScroller)
    NestedScrollView mNsvScroller;

    @BindView(R.id.ivNovelImageName)
    ImageView mIvNovelImageName;
    @BindView(R.id.tvNovelName)
    TextView mTvNovelName;
    @BindView(R.id.tvNovelAuthor)
    TextView mTvNovelAuthor;
    @BindView(R.id.tvNovelType)
    TextView mTvNovelType;
    @BindView(R.id.tvNovelSummary)
    TextView mTvNovelSummary;

    @BindView(R.id.tvChapterMore)
    TextView mTvChapterMore;
    @BindView(R.id.rvLatestChapter)
    RecyclerView mRvLatestChapter;
    @BindView(R.id.tbNovelChapterToolbar)
    Toolbar mTbNovelChapterToolbar;
    @BindView(R.id.bblLayout)
    ButtonBarLayout mBblLayout;
    @BindView(R.id.tvToolbarTitle)
    TextView mTvToolbarTitle;

    NovelChapterListAdapter latestChapterListAdapter;
    LinearLayoutManager mLinearLayoutManager;


    //private List<NovelChapterBean> titles = new ArrayList<>();

    private int lastScrollY = 0;
    private int h = SystemUtil.dp2px(120);

    public static void startWithRepository(long subjectId, Activity startingActivity) {
        Intent intent = new Intent(startingActivity, NovelChapterActivity.class);
        intent.putExtra(ARG_REPOSITORY, subjectId);
        startingActivity.startActivity(intent);
    }

    /**
     * 加载titlebar背景
     */
    private void setImgHeaderBg(String imgUrl) {
        if (!TextUtils.isEmpty(imgUrl)) {
            // 高斯模糊背景  参数：12,5
            Glide.with(this).load(imgUrl)
                    .error(R.mipmap.stackblur_default)
                    .bitmapTransform(new BlurTransformation(this, 12, 5)).into(mIvNovelBgImage);
        }
    }

    @Override
    public void showContent(NovelInfoBean data) {
        mTvToolbarTitle.setText(data.getName());
        String imagePath = Constants.HOME_URL + data.getCover();
        setImgHeaderBg(imagePath);
        GlideHelper.loadMovieTopImg(mIvNovelImageName, imagePath);
        mTvNovelName.setText(data.getName());
        mTvNovelAuthor.setText(data.getAuthor());
        String tmpStr = "连载中 | " + data.getType();
        mTvNovelType.setText(tmpStr);
        mTvNovelSummary.setText(data.getDescription());
        initContentAdapter(data);
    }

    @Override
    public void overRefresh() {
        mPbProgress.setVisibility(View.GONE);
        mFlNovelChapter.setVisibility(View.VISIBLE);
    }

    @Override
    public void showErrorMsg(String msg) {

    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_novel_chapter;
    }

    @Override
    protected void initEventAndData() {
        mFlNovelChapter.setVisibility(View.GONE);
        mPbProgress.setVisibility(View.VISIBLE);
        long subjectId = getIntent().getLongExtra(ARG_REPOSITORY, 0);
        mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mPresenter.getNovelInfo(subjectId);
        StatusBarUtil.immersive(this);
        StatusBarUtil.setPaddingSmart(this, mTbNovelChapterToolbar);
        initView();
    }

    @OnClick(R.id.tvChapterMore)
    void tv_chapter_more() {
        long subjectId = getIntent().getLongExtra(ARG_REPOSITORY, 0);
        NovelCatalogActivity.startWithRepository(subjectId, NovelChapterActivity.this);
    }

    private void initView() {
        setSupportActionBar(mTbNovelChapterToolbar);
        mTbNovelChapterToolbar.setTitle("");
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //去除默认Title显示
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.icon_back);
        }
        mTbNovelChapterToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        mNsvScroller.setOnScrollChangeListener(this);
        mTbNovelChapterToolbar.setBackgroundColor(0);
    }

    private void initContentAdapter(final NovelInfoBean data) {
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRvLatestChapter.setLayoutManager(linearLayoutManager);
        mRvLatestChapter.setItemAnimator(new DefaultItemAnimator());
        mRvLatestChapter.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).build());

        if (latestChapterListAdapter == null) {
            //设置适配器
            final List<NovelChapterBean> mList = data.getLatestChapter();
            latestChapterListAdapter = new NovelChapterListAdapter(this, mList);
            mRvLatestChapter.setAdapter(latestChapterListAdapter);

            latestChapterListAdapter.setOnItemClickListener(new NovelChapterListAdapter.OnItemClickListener() {
                @Override
                public void onItemClickListener(View view, int position) {
                    if (NetUtils.hasNetWorkConection(NovelChapterActivity.this)) {
                        NovelDetailsActivity.startWithRepository(mList.get(position).getWid(), NovelChapterActivity.this);
                    } else {
                        Toast.makeText(NovelChapterActivity.this, getString(R.string.gank_net_fail), Toast.LENGTH_LONG).show();
                    }
                }
            });
            //使用NestedScrollView嵌套RecyclerView的时候RecyclerView上滑动没有滚动的效果，解决办法:
            mRvLatestChapter.setNestedScrollingEnabled(false);
        } else {
            latestChapterListAdapter.setDatas(data.getLatestChapter());
        }
    }

    /**
     * 滚动显示toolbar
     * @param v
     * @param scrollX
     * @param scrollY
     * @param oldScrollX
     * @param oldScrollY
     */
    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        int mOffset = 0;
        int mScrollY;
        int color = ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary) & 0x00ffffff;
        if (lastScrollY < h) {
            scrollY = Math.min(h, scrollY);
            mScrollY = scrollY > h ? h : scrollY;
            mBblLayout.setAlpha(1f * mScrollY / h);
            mTbNovelChapterToolbar.setBackgroundColor(((255 * mScrollY / h) << 24) | color);
            mIvNovelBgImage.setTranslationY(mOffset - mScrollY);
        }
        lastScrollY = scrollY;
    }

    @Override
    protected void onDestroy() {
        latestChapterListAdapter = null;
        super.onDestroy();
    }

}
