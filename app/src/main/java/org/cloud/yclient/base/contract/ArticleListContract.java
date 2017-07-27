package org.cloud.yclient.base.contract;

import org.cloud.yclient.base.BasePresenter;
import org.cloud.yclient.base.BaseView;
import org.cloud.yclient.model.bean.NovelInfoBean;

import java.util.List;

/**
 * @author d05660ddw
 * @version 1.0 2017/7/8
 */

public interface ArticleListContract {

    interface View extends BaseView {

        void addNewData(List<NovelInfoBean> mList);

        void setNewData(List<NovelInfoBean> mList);

        void overRefresh();

        void setRefreshEnabled(boolean flag);

        void setLoadMoreEnabled(boolean flag);

    }

    interface Presenter extends BasePresenter<View> {

        void getNewDatas(int start);

        void getMoreDatas(int start);

    }
}
