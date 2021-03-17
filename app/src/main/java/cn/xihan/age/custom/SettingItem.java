package cn.xihan.age.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.xihan.age.R;
import cn.xihan.age.utils.LayoutUtil;


/**
 * @项目名 : AGE动漫
 * @作者 : MissYang
 * @创建时间 : 2021/2/21 20:08
 * @介绍 :
 */
public class SettingItem extends LinearLayout {
    private ImageView mIvLeftIcon,mIvRightIcon;
    private TextView mTvLeft,mTvRight;

    private boolean leftTextIsShow,rightTextIsShow;

    public SettingItem(Context context) {
        super(context);
        initView();
    }

    public SettingItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SettingItem);
        boolean leftIconIsShow = ta.getBoolean(R.styleable.SettingItem_leftIconIsShow, true);// 默认图标显示
        int leftIconResId = ta.getResourceId(R.styleable.SettingItem_leftIconResId, 0);
        float leftIconWidth = ta.getLayoutDimension(R.styleable.SettingItem_leftIconWidth, 0);
        float leftIconHeight = ta.getLayoutDimension(R.styleable.SettingItem_leftIconHeight, 0);
        float leftIconMarginLeft = ta.getLayoutDimension(R.styleable.SettingItem_leftIconMarginLeft, 0);

        leftTextIsShow = ta.getBoolean(R.styleable.SettingItem_leftTextIsShow,true);// 默认显示文字

        String leftText = ta.getString(R.styleable.SettingItem_leftText);
        int leftTextColor = ta.getColor(R.styleable.SettingItem_leftTextColor, 0);
        float leftTextSize = ta.getDimensionPixelSize(R.styleable.SettingItem_leftTextSize, 0);
        float leftTextMarginLeft = ta.getDimensionPixelSize(R.styleable.SettingItem_leftTextMarginLeft, 0);

        boolean rightIconIsShow = ta.getBoolean(R.styleable.SettingItem_rightIconIsShow, true);// 默认图标显示
        int rightIconResId = ta.getResourceId(R.styleable.SettingItem_rightIconResId, 0);
        float rightIconWidth = ta.getLayoutDimension(R.styleable.SettingItem_rightIconWidth, 0);
        float rightIconHeight = ta.getLayoutDimension(R.styleable.SettingItem_rightIconHeight, 0);
        // 左侧图片的左边距,右侧图标的右边距
        float rightIconMarginRight = ta.getLayoutDimension(R.styleable.SettingItem_rightIconMarginRight, 0);

        rightTextIsShow = ta.getBoolean(R.styleable.SettingItem_rightTextIsShow,true);// 默认显示文字
        String rightText = ta.getString(R.styleable.SettingItem_rightText);
        int rightTextColor = ta.getColor(R.styleable.SettingItem_rightTextColor, 0);
        float rightTextSize = ta.getDimensionPixelSize(R.styleable.SettingItem_rightTextSize, 0);
        // 左侧文字的左边距,右侧文字的右边距
        float rightTextMarginRight = ta.getDimensionPixelSize(R.styleable.SettingItem_rightTextMarginRight, 0);

        ta.recycle();

        initView();

        if (leftIconIsShow) {
            mIvLeftIcon.setImageResource(leftIconResId);
            LayoutParams leftIconParams = new LayoutParams((int) leftIconWidth, (int) leftIconHeight);
            leftIconParams.gravity = Gravity.CENTER_VERTICAL;
            leftIconParams.setMargins((int) leftIconMarginLeft,0, 0,0);
            addView(mIvLeftIcon, leftIconParams);
        }

        LayoutParams textContentParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        textContentParams.weight = 1;
        textContentParams.gravity = Gravity.CENTER_VERTICAL;
        LinearLayout textContent = new LinearLayout(getContext());
        if (leftTextIsShow) {
            mTvLeft.setText(leftText);
            mTvLeft.setTextColor(leftTextColor);
            mTvLeft.setTextSize(LayoutUtil.getSpByPixel(getContext(), (int) leftTextSize));
            LayoutParams leftTextParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            leftTextParams.gravity = Gravity.LEFT | Gravity.CENTER_VERTICAL;
            leftTextParams.weight = 1;
            leftTextParams.setMargins((int) leftTextMarginLeft, 0, 0, 0);
            mTvLeft.setGravity(Gravity.LEFT);
            textContent.addView(mTvLeft, leftTextParams);
        }
        if (rightTextIsShow) {
            mTvRight.setText(rightText);
            mTvRight.setTextColor(rightTextColor);
            mTvRight.setTextSize(LayoutUtil.getSpByPixel(getContext(), (int) rightTextSize));
            LayoutParams rightTextParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            rightTextParams.gravity = Gravity.RIGHT | Gravity.CENTER_VERTICAL;
            rightTextParams.weight = 1;
            rightTextParams.setMargins(0, 0, (int) rightTextMarginRight, 0);
            mTvRight.setGravity(Gravity.RIGHT);
            textContent.addView(mTvRight, rightTextParams);
        }
        addView(textContent,textContentParams);

        if (rightIconIsShow) {
            mIvRightIcon.setImageResource(rightIconResId);
            LayoutParams rightIconParams = new LayoutParams((int) rightIconWidth, (int) rightIconHeight);
            rightIconParams.gravity = Gravity.CENTER_VERTICAL;
            rightIconParams.setMargins(0,0, (int) rightIconMarginRight,0);
            addView(mIvRightIcon, rightIconParams);
        }
    }

    private void initView() {
        setOrientation(HORIZONTAL);
        mIvLeftIcon = new ImageView(getContext());
        mTvLeft = new TextView(getContext());
        mTvRight = new TextView(getContext());
        mIvRightIcon = new ImageView(getContext());
    }

    public void setLeftIcon(int leftIconResId){
        if (leftTextIsShow){
            mIvLeftIcon.setImageResource(leftIconResId);
        }
    }
    public void setLeftText(String leftText){
        if (leftTextIsShow){
            mTvLeft.setText(leftText);
        }
    }
    public void setRightText(String rightText){
        if (rightTextIsShow){
            mTvRight.setText(rightText);
        }
    }
    public void setRightIcon(int rightIconResId){
        if (rightTextIsShow){
            mIvRightIcon.setImageResource(rightIconResId);
        }
    }
}


