package org.cloud.yclient.ui.novel.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.flyco.roundview.RoundTextView;
import com.wang.avi.AVLoadingIndicatorView;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.cloud.yclient.R;
import org.cloud.yclient.base.BaseActivity;
import org.cloud.yclient.base.contract.NovelCatalogContract;
import org.cloud.yclient.model.bean.NovelChapterBean;
import org.cloud.yclient.ui.novel.adapter.NovelChapterListAdapter;
import org.cloud.yclient.ui.novel.presenter.NovelCatalogPresenter;
import org.cloud.yclient.utils.NetUtils;
import org.cloud.yclient.utils.StatusBarUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class NovelCatalogActivity extends BaseActivity<NovelCatalogPresenter> implements NovelCatalogContract.View {

    private static final String ARG_REPOSITORY = "arg_repository";

    @BindView(R.id.progressbar)
    AVLoadingIndicatorView mPbProgress;
    @BindView(R.id.llCatalog)
    LinearLayout mLlCatalog;

    @BindView(R.id.tvSideMenuTitle)
    TextView mTvSideMenuTitle;
    @BindView(R.id.rtvSideMenuOrder)
    RoundTextView mRtvSideMenuOrder;
    @BindView(R.id.rvSideMenuContent)
    RecyclerView mRvSideMenuContent;
    @BindView(R.id.sSpinnerButton)
    Spinner mSSpinnerButton;

    @BindView(R.id.tbNovelChapterToolbar)
    Toolbar mTbNovelChapterToolbar;
    @BindView(R.id.bblLayout)
    ButtonBarLayout mBblLayout;
    @BindView(R.id.tvToolbarTitle)
    TextView mTvToolbarTitle;

    boolean flag = true;
    long subjectId;

    LinearLayoutManager mLinearLayoutManager;
    NovelChapterListAdapter titleChapterListAdapter;
    ArrayAdapter<String> pageAdapter;

    public static void startWithRepository(long subjectId, Activity startingActivity) {
        Intent intent = new Intent(startingActivity, NovelCatalogActivity.class);
        intent.putExtra(ARG_REPOSITORY, subjectId);
        startingActivity.startActivity(intent);
    }

    //显示数据
    @Override
    public void showContent(List<NovelChapterBean> data, int count) {
        String tmpStr = "共" + count + "章";
        mTvSideMenuTitle.setText(tmpStr);
        initCatalogAdapter(data, count);
    }

    @Override
    public void setRefreshing() {
        mPbProgress.setVisibility(View.VISIBLE);
        mLlCatalog.setVisibility(View.GONE);
    }

    @Override
    public void overRefresh() {
        mPbProgress.setVisibility(View.GONE);
        mLlCatalog.setVisibility(View.VISIBLE);
    }


    @OnClick(R.id.rtvSideMenuOrder)
    void iv_menu_action() {
        if (flag) {
            mLinearLayoutManager.setReverseLayout(true);
            mLinearLayoutManager.setStackFromEnd(true);
            flag = false;
            mRtvSideMenuOrder.setText("正序");
        } else {
            mLinearLayoutManager.setReverseLayout(false);
            mLinearLayoutManager.setStackFromEnd(false);
            flag = true;
            mRtvSideMenuOrder.setText("倒序");
        }

    }

    private String[] generateData(int count) {
        int maxPage = (int) Math.ceil((float) count / 60);
        String[] mItems = new String[maxPage];
        for (int i = 0; i < maxPage; i++) {
            int start = i * 60 + 1;
            int end = (count < (i + 1) * 60) ? count : (i + 1) * 60;
            mItems[i] = start + "-" + end + "章";
        }
        return mItems;
    }

    private void initCatalogAdapter(final List<NovelChapterBean> mList, int count) {
        //menu recyclerView
        mRvSideMenuContent.setLayoutManager(mLinearLayoutManager);
        mRvSideMenuContent.setItemAnimator(new DefaultItemAnimator());
        mRvSideMenuContent.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).build());

        if (titleChapterListAdapter == null) {
            titleChapterListAdapter = new NovelChapterListAdapter(this, mList);
            mRvSideMenuContent.setAdapter(titleChapterListAdapter);
            titleChapterListAdapter.setOnItemClickListener(new NovelChapterListAdapter.OnItemClickListener() {
                @Override
                public void onItemClickListener(View view, int position) {
                    //mDlDrawerLayout.closeDrawers();
                    if (NetUtils.hasNetWorkConection(NovelCatalogActivity.this)) {
                        NovelDetailsActivity.startWithRepository(mList.get(position).getWid(), NovelCatalogActivity.this);
                    } else {
                        Toast.makeText(NovelCatalogActivity.this, getString(R.string.gank_net_fail), Toast.LENGTH_LONG).show();
                    }
                }
            });
        } else {
            titleChapterListAdapter.setDatas(mList);
        }
        if (pageAdapter == null) {
            pageAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, generateData(count));
            pageAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
            // 将ArrayAdapter添加Spinner对象中
            mSSpinnerButton.setAdapter(pageAdapter);
        }
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
        return R.layout.activity_novel_catalog;
    }

    @Override
    protected void initEventAndData() {
        setRefreshing();
        mTvToolbarTitle.setText("目录");
        subjectId = getIntent().getLongExtra(ARG_REPOSITORY, 0);
        //获取数据
        mPresenter.getNovelChapter(subjectId, 1);
        mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        StatusBarUtil.immersive(this);
        StatusBarUtil.setPaddingSmart(this, mTbNovelChapterToolbar);
        initView();
    }

    @Override
    protected void onDestroy() {
        titleChapterListAdapter = null;
        super.onDestroy();
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

        //Spinner在初始化时会自动调用一次OnItemSelectedListener事件, 如果用户选择的也是第一项，那么OnItemSelectedListener事件不会被触发
        mSSpinnerButton.setSelection(0, true);
        mSSpinnerButton.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mPresenter.getNovelChapter(subjectId, position + 1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
