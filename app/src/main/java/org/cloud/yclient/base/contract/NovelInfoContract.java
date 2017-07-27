package org.cloud.yclient.base.contract;

import org.cloud.yclient.base.BasePresenter;
import org.cloud.yclient.base.BaseView;
import org.cloud.yclient.model.bean.NovelInfoBean;

/**
 * @author d05660ddw
 * @version 1.0 2017/7/8
 */

public interface NovelInfoContract {

    interface View extends BaseView {

        void showContent(NovelInfoBean novelInfoBean);

        void overRefresh();
    }

    interface Presenter extends BasePresenter<View> {

        void getNovelInfo(long id);
    }
}
