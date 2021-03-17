package cn.xihan.age.base;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.qmuiteam.qmui.arch.QMUIFragment;
import com.qmuiteam.qmui.arch.SwipeBackLayout;
import com.qmuiteam.qmui.skin.QMUISkinMaker;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;

import cn.xihan.age.MyApp;
import cn.xihan.age.utils.DatabaseUtil;
import cn.xihan.age.utils.Utils;

/**
 * @项目名 : AGE动漫
 * @作者 : MissYang
 * @创建时间 : 2021/3/10 11:06
 * @介绍 :
 */
public abstract class BaseFragment extends QMUIFragment {

    private static final String TAG = "BaseFragment";

    private int mBindId = -1;

    public BaseFragment() {
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(MyApp.openSkinMake){
            openSkinMaker();
        }
        Utils.creatFile();
        DatabaseUtil.CREATE_TABLES();
    }

    public void openSkinMaker(){
        if(mBindId < 0){
            mBindId = QMUISkinMaker.getInstance().bind(this);
        }
    }

    public void closeSkinMaker(){
        QMUISkinMaker.getInstance().unBind(mBindId);
        mBindId = -1;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        closeSkinMaker();
    }

    @Override
    protected int backViewInitOffset(Context context, int dragDirection, int moveEdge) {
        if (moveEdge == SwipeBackLayout.EDGE_TOP || moveEdge == SwipeBackLayout.EDGE_BOTTOM) {
            return 0;
        }
        return QMUIDisplayHelper.dp2px(context, 100);
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.i(TAG, getClass().getSimpleName() + " onResume");

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, getClass().getSimpleName() + " onStart");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, getClass().getSimpleName() + " onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, getClass().getSimpleName() + " onStop");
    }




}


