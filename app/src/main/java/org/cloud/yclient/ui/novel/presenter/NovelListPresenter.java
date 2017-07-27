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
package org.cloud.yclient.ui.novel.presenter;

import org.cloud.yclient.base.RxPresenter;
import org.cloud.yclient.base.contract.NovelListContract;
import org.cloud.yclient.model.DataManager;
import org.cloud.yclient.model.bean.NovelInfoBean;
import org.cloud.yclient.model.http.responese.MyAPIResponse;
import org.cloud.yclient.utils.CommonSubscriber;
import org.cloud.yclient.utils.RxUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

/**
 * @author d05660ddw
 * @version 1.0 2017/7/8
 */

public class NovelListPresenter extends RxPresenter<NovelListContract.View> implements NovelListContract.Presenter {

    private DataManager mDataManager;
    private int pageSize = 10;
    private int pageIndex = 1;
    private int maxPage;
    private List<NovelInfoBean> mDatas = new ArrayList<>();

    @Inject
    public NovelListPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    private void getDatas(int pageIndex) {
        apiSubscribe(mDataManager.getNovelListInfo(pageIndex, pageSize), pageIndex == 1);
    }

    private void apiSubscribe(Flowable<MyAPIResponse<List<NovelInfoBean>>> flowable, final boolean reset) {
        addSubscribe(flowable
                .compose(RxUtil.<MyAPIResponse<List<NovelInfoBean>>>rxSchedulerHelper())
                .filter(new Predicate<MyAPIResponse<List<NovelInfoBean>>>() {
                    @Override
                    public boolean test(@NonNull MyAPIResponse<List<NovelInfoBean>> listMyAPIResponse) throws Exception {
                        maxPage = (int) Math.ceil((float)listMyAPIResponse.getTotal() / listMyAPIResponse.getSize());
                        return maxPage >= listMyAPIResponse.getPage();
                    }
                })
                .compose(RxUtil.<List<NovelInfoBean>>handleMyAPIResult())
                .subscribeWith(new CommonSubscriber<List<NovelInfoBean>>(mView) {
                    @Override
                    public void onNext(List<NovelInfoBean> novelInfoBean) {
                        mDatas = novelInfoBean;
                        if (mView != null) {
                            if (reset) {
                                mView.setNewData(novelInfoBean);
                            } else {
                                mView.addNewData(novelInfoBean);
                            }
                            mView.overRefresh();
                        }

                    }
                })
        );
    }


    @Override
    public void getNewDatas() {
        int pageIndex = 1;
        getDatas(pageIndex);
        if (mView != null) {
            mView.setRefreshEnabled(true);
            mView.setLoadMoreEnabled(true);
        }
    }

    @Override
    public void getMoreDatas() {
        if (maxPage > pageIndex) {
            pageIndex++;
            getDatas(pageIndex);
        } else {
            if (mView != null) {
                mView.showErrorMsg("没有更多数据了");
                mView.overRefresh();
                mView.setLoadMoreEnabled(false);
            }
        }
    }
}
