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
package org.cloud.yclient.model.http.api;

import org.cloud.yclient.model.bean.NovelChapterBean;
import org.cloud.yclient.model.bean.NovelInfoBean;
import org.cloud.yclient.model.http.responese.MyAPIResponse;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author d05660ddw
 * @version 1.0 2017/7/7
 */

public interface MyApis {

    String HOST = "https://www.d05660.top/api/v1/";

    @GET("novel")
    Flowable<MyAPIResponse<List<NovelInfoBean>>> getNovelListInfo(@Query("page") int page, @Query("size") int size);

    @GET("novel/{id}")
    Flowable<NovelInfoBean> getNovelInfo(@Path("id") long novelId);

    @GET("novel/{id}/chapter")
    Flowable<MyAPIResponse<List<NovelChapterBean>>> getNovelChapter(@Path("id") long novelId, @Query("page") long page, @Query("size") int size);
}
