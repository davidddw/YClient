package org.cloud.yclient.base.contract;

import org.cloud.yclient.base.BasePresenter;
import org.cloud.yclient.base.BaseView;
import org.cloud.yclient.model.bean.MovieDetailsBean;

/**
 * @author d05660ddw
 * @version 1.0 2017/6/20
 */

public interface DetailsContract {

    interface View extends BaseView {

        void showContent(MovieDetailsBean movieDetailsBean);

        void overRefresh();
    }

    interface Presenter extends BasePresenter<View> {

        void getMovieDetails(long id);

    }
}
