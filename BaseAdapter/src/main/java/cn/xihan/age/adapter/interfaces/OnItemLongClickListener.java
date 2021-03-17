package cn.xihan.age.adapter.interfaces;

import cn.xihan.age.adapter.ViewHolder;

/**
 * Author: SheHuan
 * Time: 2019/6/13 16:48
 */
public interface OnItemLongClickListener<T> {
    void onItemLongClick(ViewHolder viewHolder, T data, int position);
}
