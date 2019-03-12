package com.henan.yijiajia.p_network;

/**
 * Created by 叶满林 on 2019/3/2.
 */

public class NetBasebean {
    public String type;
    public String msg;
    public Object data;

    @Override
    public String toString() {
        return "NetBasebean{" +
                "type='" + type + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
