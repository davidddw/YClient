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
package org.cloud.yclient.model;

import org.cloud.yclient.model.bean.MovieDetailsBean;
import org.cloud.yclient.model.bean.MovieInfoBean;
import org.cloud.yclient.model.bean.NovelChapterBean;
import org.cloud.yclient.model.bean.NovelInfoBean;
import org.cloud.yclient.model.db.DBHelper;
import org.cloud.yclient.model.http.HttpHelper;
import org.cloud.yclient.model.http.responese.DoubanResponse;
import org.cloud.yclient.model.http.responese.MyAPIResponse;
import org.cloud.yclient.model.prefs.PreferencesHelper;

import java.util.List;

import io.reactivex.Flowable;

/**
 * @author d05660ddw
 * @version 1.0 2017/7/7
 */

public class DataManager implements HttpHelper, DBHelper, PreferencesHelper {

    HttpHelper mHttpHelper;
    DBHelper mDbHelper;
    PreferencesHelper mPreferencesHelper;

    public DataManager(HttpHelper httpHelper, DBHelper dbHelper, PreferencesHelper preferencesHelper) {
        mHttpHelper = httpHelper;
        mDbHelper = dbHelper;
        mPreferencesHelper = preferencesHelper;
    }

    @Override
    public void insertNewsId(int id) {

    }

    @Override
    public boolean queryNewsId(int id) {
        return false;
    }

    @Override
    public Flowable<DoubanResponse<List<MovieInfoBean>>> getComingSoonMovie(int start, int count) {
        return mHttpHelper.getComingSoonMovie(start, count);
    }

    @Override
    public Flowable<MyAPIResponse<List<NovelInfoBean>>> getNovelListInfo(int page, int size) {
        return mHttpHelper.getNovelListInfo(page, size);
    }

    @Override
    public Flowable<NovelInfoBean> getNovelInfo(long novelId) {
        return mHttpHelper.getNovelInfo(novelId);
    }

    @Override
    public Flowable<MyAPIResponse<List<NovelChapterBean>>> getNovelChapter(long novelId, long page, int size) {
        return mHttpHelper.getNovelChapter(novelId, page, size);
    }

    @Override
    public Flowable<DoubanResponse<List<MovieInfoBean>>> getInTheatersMovie(String city, int start, int count) {
        return mHttpHelper.getInTheatersMovie(city, start, count);
    }

    @Override
    public Flowable<DoubanResponse<List<MovieInfoBean>>> getTop250Movie(int start, int count) {
        return mHttpHelper.getTop250Movie(start, count);
    }

    @Override
    public Flowable<MovieDetailsBean> getMovieDetails(long customId) {
        return mHttpHelper.getMovieDetails(customId);
    }

}
