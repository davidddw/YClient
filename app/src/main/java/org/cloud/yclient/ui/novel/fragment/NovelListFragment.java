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
package org.cloud.yclient.ui.novel.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;

import org.cloud.yclient.R;
import org.cloud.yclient.base.BaseFragment;
import org.cloud.yclient.base.contract.NovelListContract;
import org.cloud.yclient.model.bean.NovelInfoBean;
import org.cloud.yclient.ui.novel.activity.NovelChapterActivity;
import org.cloud.yclient.ui.novel.adapter.NovelListAdapter;
import org.cloud.yclient.ui.novel.presenter.NovelListPresenter;
import org.cloud.yclient.utils.NetUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author d05660ddw
 * @version 1.0 2017/6/20
 */

public class NovelListFragment extends BaseFragment<NovelListPresenter> implements NovelListContract.View, OnRefreshListener, OnLoadMoreListener {

    private NovelListAdapter mAdapter;
    private List<NovelInfoBean> mList = new ArrayList<>();

    @BindView(R.id.swipe_target)
    RecyclerView mRvListNovels;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout mSwipeToLoadLayout;

    public static NovelListFragment newInstance() {
        return new NovelListFragment();
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
        if (mSwipeToLoadLayout == null) {
            return;
        }
        if (mSwipeToLoadLayout.isRefreshing()) {
            mSwipeToLoadLayout.setRefreshing(false);
        }
        if (mSwipeToLoadLayout.isLoadingMore()) {
            mSwipeToLoadLayout.setLoadingMore(false);
        }
    }

    @Override
    public void setRefreshEnabled(boolean flag) {
        mSwipeToLoadLayout.setRefreshEnabled(flag);
    }

    @Override
    public void setLoadMoreEnabled(boolean flag) {
        mSwipeToLoadLayout.setLoadMoreEnabled(flag);
    }

    private void initMovieListAdapter() {
        overRefresh();
        mAdapter = new NovelListAdapter(mContext, mList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        mRvListNovels.setLayoutManager(linearLayoutManager);
        mRvListNovels.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new NovelListAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                NovelChapterActivity.startWithRepository(Long.parseLong(mList.get(position).getId()), getActivity());
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
        mSwipeToLoadLayout.setOnRefreshListener(this);
        mSwipeToLoadLayout.setOnLoadMoreListener(this);
        mSwipeToLoadLayout.setRefreshEnabled(true);
        mSwipeToLoadLayout.setLoadMoreEnabled(true);

        //加载数据
        mSwipeToLoadLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeToLoadLayout.setRefreshing(true);
            }
        }, 100);
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    public void onLoadMore() {
        mPresenter.getMoreDatas();
    }

    @Override
    public void onRefresh() {
        //获取数据
        if (NetUtils.hasNetWorkConection(mContext)) {
            mPresenter.getNewDatas();
        } else {
            showErrorMsg(getString(R.string.gank_net_fail));
            this.overRefresh();
        }
    }

    @Override
    public void showErrorMsg(String msg) {

    }
}
