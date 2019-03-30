package com.henan.yijiajia.p_base.bean;

import java.util.List;


/**
 * Created by 叶满林 on 2019/3/21.
 */

public class MainTypeBean {
    public String id;
    public String name;
    public List<TypeBean> type;

    public static class TypeBean{
        public String id;
        public String name;

        @Override
        public String toString() {
            return name;
        }
    }
}
