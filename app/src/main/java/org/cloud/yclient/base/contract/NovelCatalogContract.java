package org.cloud.yclient.base.contract;

import org.cloud.yclient.base.BasePresenter;
import org.cloud.yclient.base.BaseView;
import org.cloud.yclient.model.bean.NovelChapterBean;
import org.cloud.yclient.model.bean.NovelInfoBean;

import java.util.List;

/**
 * @author d05660ddw
 * @version 1.0 2017/7/8
 */

public interface NovelCatalogContract {

    interface View extends BaseView {

        void showContent(List<NovelChapterBean> novelChapterBean, int count);

        void setRefreshing();

        void overRefresh();
    }

    interface Presenter extends BasePresenter<View> {

        void getNovelChapter(long id, long page);

    }
}
