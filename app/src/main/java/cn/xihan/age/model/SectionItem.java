package cn.xihan.age.model;

import com.qmuiteam.qmui.widget.section.QMUISection;

/**
 * @项目名 : AGE动漫
 * @作者 : MissYang
 * @创建时间 : 2021/2/21 14:18
 * @介绍 :
 */
public class SectionItem implements QMUISection.Model<SectionItem> {
    private final String text;

    public SectionItem(String text){
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public SectionItem cloneForDiff() {
        return new SectionItem(getText());
    }

    @Override
    public boolean isSameItem(SectionItem other) {
        return text.equals(other.text);
    }

    @Override
    public boolean isSameContent(SectionItem other) {
        return true;
    }
}

