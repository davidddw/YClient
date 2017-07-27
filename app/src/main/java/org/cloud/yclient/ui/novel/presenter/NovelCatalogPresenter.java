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

import android.util.Log;

import org.cloud.yclient.base.RxPresenter;
import org.cloud.yclient.base.contract.NovelCatalogContract;
import org.cloud.yclient.model.DataManager;
import org.cloud.yclient.model.bean.NovelChapterBean;
import org.cloud.yclient.model.http.exception.ApiException;
import org.cloud.yclient.model.http.responese.MyAPIResponse;
import org.cloud.yclient.utils.CommonSubscriber;
import org.cloud.yclient.utils.RxUtil;
import org.reactivestreams.Publisher;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * @author d05660ddw
 * @version 1.0 2017/7/8
 */

public class NovelCatalogPresenter extends RxPresenter<NovelCatalogContract.View> implements NovelCatalogContract.Presenter {

    private DataManager mDataManager;
    private int pageSize = 60;
    private int pageIndex = 1;
    private int totalNumber;

    @Inject
    public NovelCatalogPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void getNovelChapter(long id, long page) {
        addSubscribe(mDataManager.getNovelChapter(id, page, pageSize)
                .compose(RxUtil.<MyAPIResponse<List<NovelChapterBean>>>rxSchedulerHelper())
                .compose(new FlowableTransformer<MyAPIResponse<List<NovelChapterBean>>, List<NovelChapterBean>>() {
                    @Override
                    public Publisher<List<NovelChapterBean>> apply(@NonNull Flowable<MyAPIResponse<List<NovelChapterBean>>> response) {
                        return response.flatMap(new Function<MyAPIResponse<List<NovelChapterBean>>, Flowable<List<NovelChapterBean>>>() {
                            @Override
                            public Flowable<List<NovelChapterBean>> apply(@NonNull MyAPIResponse<List<NovelChapterBean>> listMyAPIResponse) throws
                                    Exception {
                                if (listMyAPIResponse.getTotal() > 0) {
                                    totalNumber = listMyAPIResponse.getTotal();
                                    return RxUtil.createData(listMyAPIResponse.getItems());
                                } else {
                                    totalNumber = 0;
                                    return Flowable.error(new ApiException("服务器返回error"));
                                }
                            }
                        });
                    }
                })
                .subscribeWith(new CommonSubscriber<List<NovelChapterBean>>(mView) {
                    @Override
                    public void onNext(List<NovelChapterBean> novelInfoBean) {
                        if (mView != null) {
                            mView.showContent(novelInfoBean, totalNumber);
                            mView.overRefresh();
                        }
                    }
                })
        );
    }
}
