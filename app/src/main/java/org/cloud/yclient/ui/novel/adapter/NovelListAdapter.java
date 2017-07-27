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
package org.cloud.yclient.ui.novel.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.flyco.roundview.RoundTextView;

import org.cloud.yclient.R;
import org.cloud.yclient.app.App;
import org.cloud.yclient.constants.Constants;
import org.cloud.yclient.model.bean.NovelInfoBean;
import org.cloud.yclient.utils.GlideHelper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author d05660ddw
 * @version 1.0 2017/7/8
 */

public class NovelListAdapter extends RecyclerView.Adapter<NovelListAdapter.ViewHolder> {

    private Context mContext;
    private List<NovelInfoBean> mList;
    private LayoutInflater inflater;
    private OnItemClickListener mOnItemClickListener;

    public NovelListAdapter(Context mContext, List<NovelInfoBean> mList) {
        inflater = LayoutInflater.from(mContext);
        this.mList = mList;
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
    }

    public void setList(List<NovelInfoBean> list) {
        mList.addAll(list);
    }

    public void setNewList(List<NovelInfoBean> list) {
        mList.clear();
        mList.addAll(list);
    }

    public int getListOldSize() {
        return mList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.novel_item_list, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.bindData(mList.get(position), position + 1);
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClickListener(holder.itemView, holder.getAdapterPosition());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivNovelImage)
        ImageView mIvNovelImage;
        @BindView(R.id.tvNovelName)
        TextView mTvNovelName;
        @BindView(R.id.tvNovelSummary)
        TextView mTvNovelSummary;
        @BindView(R.id.tvNovelAuthor)
        TextView mTvNovelAuthor;
        @BindView(R.id.rtvNovelType)
        RoundTextView mRtvNovelType;
        @BindView(R.id.tvNovelLatest)
        TextView mTvNovelLatest;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bindData(final NovelInfoBean subject, int position) {
            String imagePath = Constants.HOME_URL + subject.getCover();
            GlideHelper.showImage(App.getInstance(), imagePath, mIvNovelImage);
            mTvNovelName.setText(subject.getName());
            String directoryName = subject.getAuthor();
            mTvNovelAuthor.setText(directoryName);
            String movieCast = subject.getDescription();
            mTvNovelSummary.setText(movieCast);
            mRtvNovelType.setText(subject.getType());
            mTvNovelLatest.setText("asdfasdf");
        }
    }

    public interface OnItemClickListener {
        void onItemClickListener(View view, int position);
    }
}
