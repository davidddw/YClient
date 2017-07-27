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
package org.cloud.yclient.model.http;

import org.cloud.yclient.model.bean.MovieDetailsBean;
import org.cloud.yclient.model.bean.MovieInfoBean;
import org.cloud.yclient.model.bean.NovelChapterBean;
import org.cloud.yclient.model.bean.NovelInfoBean;
import org.cloud.yclient.model.http.api.DoubanApis;
import org.cloud.yclient.model.http.api.MyApis;
import org.cloud.yclient.model.http.responese.DoubanResponse;
import org.cloud.yclient.model.http.responese.MyAPIResponse;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * @author d05660ddw
 * @version 1.0 2017/7/7
 */

public class RetrofitHelper implements HttpHelper {

    private DoubanApis mDoubanApis;
    private MyApis mMyApis;

    @Inject
    public RetrofitHelper(DoubanApis doubanApis, MyApis myApis) {
        mDoubanApis = doubanApis;
        mMyApis = myApis;
    }

    @Override
    public Flowable<DoubanResponse<List<MovieInfoBean>>> getComingSoonMovie(int start, int count) {
        return mDoubanApis.getComingSoonMovie(start, count);
    }

    @Override
    public Flowable<MyAPIResponse<List<NovelInfoBean>>> getNovelListInfo(int page, int size) {
        return mMyApis.getNovelListInfo(page, size);
    }

    @Override
    public Flowable<NovelInfoBean> getNovelInfo(long novelId) {
        return mMyApis.getNovelInfo(novelId);
    }

    @Override
    public Flowable<MyAPIResponse<List<NovelChapterBean>>> getNovelChapter(long novelId, long page, int size) {
        return mMyApis.getNovelChapter(novelId, page, size);
    }

    @Override
    public Flowable<DoubanResponse<List<MovieInfoBean>>> getInTheatersMovie(String city, int start, int count) {
        return mDoubanApis.getInTheatersMovie(city, start, count);
    }

    @Override
    public Flowable<DoubanResponse<List<MovieInfoBean>>> getTop250Movie(int start, int count) {
        return mDoubanApis.getTopMovie(start, count);
    }

    @Override
    public Flowable<MovieDetailsBean> getMovieDetails(long customId) {
        return mDoubanApis.getMovieDetails(customId);
    }
}