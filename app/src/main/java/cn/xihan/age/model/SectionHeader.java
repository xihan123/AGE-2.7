package cn.xihan.age.model;

import com.qmuiteam.qmui.widget.section.QMUISection;

/**
 * @项目名 : AGE动漫
 * @作者 : MissYang
 * @创建时间 : 2021/2/21 14:19
 * @介绍 :
 */
public class SectionHeader implements QMUISection.Model<SectionHeader> {
    private final String text;

    public SectionHeader(String text){
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public SectionHeader cloneForDiff() {
        return new SectionHeader(getText());
    }

    @Override
    public boolean isSameItem(SectionHeader other) {
        return text.equals(other.text);
    }

    @Override
    public boolean isSameContent(SectionHeader other) {
        return true;
    }
}

