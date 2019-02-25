package com.henan.yijiajia.p_hall.bean;

import java.util.List;

/**
 * Created by 叶满林 on 2019/2/23.
 */

public class ClassifyMenuBean {
    public int code;
    public List<MenuBean> data;

    public static class MenuBean {
        public String moduleTitle;//小模块标题
        public List<SubmenuBean> dataList;

        public static class SubmenuBean {
            public String id;
            public String title;
        }
    }


}
