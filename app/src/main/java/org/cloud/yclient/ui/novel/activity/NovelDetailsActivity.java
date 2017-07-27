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
import android.os.Bundle;

import org.cloud.yclient.R;
import org.cloud.yclient.base.BaseActivity;
import org.cloud.yclient.base.contract.NovelInfoContract;
import org.cloud.yclient.model.bean.NovelChapterBean;
import org.cloud.yclient.model.bean.NovelInfoBean;
import org.cloud.yclient.ui.novel.presenter.NovelInfoPresenter;

import java.util.List;

public class NovelDetailsActivity extends BaseActivity<NovelInfoPresenter> implements NovelInfoContract.View {

    private static final String ARG_REPOSITORY = "arg_repository";

    @Override
    protected int getLayout() {
        return R.layout.activity_novel_details;
    }

    public static void startWithRepository(String location, Activity startingActivity) {
        Intent intent = new Intent(startingActivity, NovelDetailsActivity.class);
        intent.putExtra(ARG_REPOSITORY, location);
        startingActivity.startActivity(intent);
    }

    @Override
    protected void initEventAndData() {
        String subjectId = getIntent().getStringExtra(ARG_REPOSITORY);
//        mPresenter.getNovelInfo(subjectId);
//        mPresenter.getNovelChapter(subjectId);
//        StatusBarUtil.immersive(this);
//        StatusBarUtil.setPaddingSmart(this, toolbarDoubanDetail);
//        initView();
    }

    @Override
    public void showContent(NovelInfoBean novelInfoBean) {

    }

    @Override
    public void overRefresh() {

    }

    @Override
    public void showErrorMsg(String msg) {

    }

    @Override
    protected void initInject() {

    }
}