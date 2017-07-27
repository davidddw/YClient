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
import org.cloud.yclient.base.contract.NovelInfoContract;
import org.cloud.yclient.model.DataManager;
import org.cloud.yclient.model.bean.NovelInfoBean;
import org.cloud.yclient.utils.CommonSubscriber;
import org.cloud.yclient.utils.RxUtil;

import javax.inject.Inject;

/**
 * @author d05660ddw
 * @version 1.0 2017/7/8
 */

public class NovelInfoPresenter extends RxPresenter<NovelInfoContract.View> implements NovelInfoContract.Presenter {

    private DataManager mDataManager;

    @Inject
    public NovelInfoPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void getNovelInfo(long id) {
        addSubscribe(mDataManager.getNovelInfo(id)
                .compose(RxUtil.<NovelInfoBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<NovelInfoBean>(mView) {
                    @Override
                    public void onNext(NovelInfoBean novelInfoBean) {
                        if (mView != null) {
                            mView.showContent(novelInfoBean);
                            mView.overRefresh();
                        }
                    }
                })
        );
    }

}
