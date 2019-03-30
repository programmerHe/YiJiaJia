package com.henan.yijiajia.p_location.bean;

import me.yokeyword.indexablerv.IndexableEntity;

/**
 * Created by YoKey on 16/10/8.
 */
public class AlternativeCityEntity implements IndexableEntity {
    public String nick;
    public String avatar;
    public String mobile;

    public AlternativeCityEntity(String nick, String mobile) {
        this.nick = nick;
        this.mobile = mobile;
    }

    @Override
    public String getFieldIndexBy() {
        return nick;
    }

    @Override
    public void setFieldIndexBy(String indexField) {
        this.nick = indexField;
    }

    @Override
    public void setFieldPinyinIndexBy(String pinyin) {
    }
}
