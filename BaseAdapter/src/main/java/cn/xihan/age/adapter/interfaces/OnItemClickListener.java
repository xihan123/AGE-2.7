package cn.xihan.age.adapter.interfaces;

import cn.xihan.age.adapter.ViewHolder;
/**
 * Author: Othershe
 * Time: 2016/8/29 10:48
 */
public interface OnItemClickListener<T> {
    void onItemClick(ViewHolder viewHolder, T data, int position);
}
