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
package org.cloud.yclient.ui.article.fragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.cloud.yclient.R;
import org.cloud.yclient.base.BaseFragment;
import org.cloud.yclient.base.contract.NovelListContract;
import org.cloud.yclient.model.bean.NovelInfoBean;
import org.cloud.yclient.ui.article.presenter.ArticleListPresenter;
import org.cloud.yclient.ui.novel.activity.NovelChapterActivity;
import org.cloud.yclient.ui.novel.adapter.NovelListAdapter;
import org.cloud.yclient.utils.NetUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author d05660ddw
 * @version 1.0 2017/6/20
 */

public class ArticleListFragment extends BaseFragment<ArticleListPresenter> implements NovelListContract.View, OnRefreshListener, OnLoadMoreListener {

    private static final String CATEGORY_NUM = "category_id";

    private int mCategoryId;

    private NovelListAdapter mAdapter;
    private List<NovelInfoBean> mList = new ArrayList<>();

    @BindView(R.id.swipe_target)
    RecyclerView rvRepositories;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;

    public static ArticleListFragment newInstance(int id) {
        ArticleListFragment fragment = new ArticleListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(CATEGORY_NUM, id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void addNewData(List<NovelInfoBean> list) {
        int insertStartPosition = mAdapter.getListOldSize();
        mAdapter.setList(list);
        mAdapter.notifyItemRangeInserted(insertStartPosition, list.size());
    }

    @Override
    public void setNewData(List<NovelInfoBean> list) {
        mAdapter.setNewList(list);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void overRefresh() {
        if (swipeToLoadLayout == null) {
            return;
        }
        if (swipeToLoadLayout.isRefreshing()) {
            swipeToLoadLayout.setRefreshing(false);
        }
        if (swipeToLoadLayout.isLoadingMore()) {
            swipeToLoadLayout.setLoadingMore(false);
        }
    }

    @Override
    public void setRefreshEnabled(boolean flag) {
        swipeToLoadLayout.setRefreshEnabled(flag);
    }

    @Override
    public void setLoadMoreEnabled(boolean flag) {
        swipeToLoadLayout.setLoadMoreEnabled(flag);
    }

    private void initMovieListAdapter() {
        overRefresh();
        mAdapter = new NovelListAdapter(mContext, mList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        rvRepositories.setLayoutManager(linearLayoutManager);
        rvRepositories.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext).build());
        rvRepositories.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new NovelListAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                NovelChapterActivity.startWithRepository(Long.parseLong(mList.get(position).getId()), getActivity());
                //MovieDetailsActivity.startWithRepository(Long.parseLong(mList.get(position).getId()), getActivity());
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_novel_list;
    }

    @Override
    protected void initAdapter() {
        initMovieListAdapter();
    }

    @Override
    protected void initEventAndData() {
        if (getArguments() != null) {
            mCategoryId = getArguments().getInt(CATEGORY_NUM);
        }
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        swipeToLoadLayout.setRefreshEnabled(true);
        swipeToLoadLayout.setLoadMoreEnabled(true);

        //加载数据
        swipeToLoadLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeToLoadLayout.setRefreshing(true);
            }
        }, 100);
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    public void onLoadMore() {
        mPresenter.getMoreDatas(mCategoryId);
    }

    @Override
    public void onRefresh() {
        //获取数据
        if (NetUtils.hasNetWorkConection(mContext)) {
            mPresenter.getNewDatas(mCategoryId);
        } else {
            showErrorMsg(getString(R.string.gank_net_fail));
            this.overRefresh();
        }
    }

    @Override
    public void showErrorMsg(String msg) {

    }
}
