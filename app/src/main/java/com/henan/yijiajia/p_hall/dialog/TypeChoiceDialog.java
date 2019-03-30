package com.henan.yijiajia.p_hall.dialog;

/**
 * Created by 叶满林 on 2019/3/21.
 */

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.henan.yijiajia.R;
import com.henan.yijiajia.p_base.bean.MainTypeBean;
import com.henan.yijiajia.p_base.util.AssetsUtils;
import com.henan.yijiajia.p_base.util.JsonUtils;

import java.util.ArrayList;
import java.util.List;



public class TypeChoiceDialog extends Dialog {

    private Context context;

    private MainTypeBean.TypeBean originalType;//原始的type类型
    private int originalMainTypeNum,originalTypeNum;//原始type一级菜单与二级菜单的位置

    private List<MainTypeBean> mMainTypeBeanList;
    private List<MainTypeBean.TypeBean> mOriginalTypeList;
    private ListView mMainTypeListView;
    private ListView mTypeListView;
    private Button mConfirmButton;
    private String[] mMainTypeStrings;
    private String[] mOriginalTypeStrings;
    public TypeChoiceDialog(@NonNull Context context , MainTypeBean.TypeBean originalType) {
        super(context);
        this.context = context;
        this.originalType = originalType;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        addListener();
    }

    private void addListener() {
        mConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mMainTypeListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
//                OnScrollListener.SCROLL_STATE_IDLE：滚动停止时的状态
//                OnScrollListener.SCROLL_STATE_STOUCH_SCROLL：触摸正在滚动，手指还没离开界面时的状态
//                OnScrollListener.SCROLL_STATE_FLING：用户在用力滑动后，ListView由于惯性将继续滑动时的状态
                if (scrollState == SCROLL_STATE_IDLE){
                    int position = mMainTypeListView.getFirstVisiblePosition() ;
                    mOriginalTypeList = mMainTypeBeanList.get(position).type;
                    ArrayAdapter adapter = new ArrayAdapter<String>(context, R.layout.choice_type_item_layout, typebeanToStrings(mOriginalTypeList));
                    mTypeListView.setAdapter(adapter);
                }
            }
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    private void initView() {
        //加载dialog布局
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.choice_type_layout, null);
        setContentView(view);

        mMainTypeListView = view.findViewById(R.id.lv_main_type);
        mTypeListView = view.findViewById(R.id.lv_type);
        mConfirmButton = view.findViewById(R.id.bt_confirm);
    }

    private void initData() {
        //获取分类数据
        String json=AssetsUtils.getOriginalFundData(context,"type.json");
        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
        JsonArray mainTypeJsons = jsonObject.getAsJsonArray("main_type");
        //加强for循环遍历得到数组
        mMainTypeBeanList = new ArrayList<MainTypeBean>();
        for (JsonElement mainTypeJSon : mainTypeJsons) {
            //使用GSON，直接转成Bean对象
            MainTypeBean mainTypeBean = JsonUtils.jsonElementToObject(mainTypeJSon,MainTypeBean.class);
            mMainTypeBeanList.add(mainTypeBean);
        }

        //判断传进来的字符串是否为空
        if (originalType!=null){
            //for遍历原来选的分类所在的主分类位置0-n
            for (int i = 1 ; i <=mMainTypeBeanList.size() ; i++){
                if (mMainTypeBeanList.get(i).type.contains(originalType)){
                    mOriginalTypeList = mMainTypeBeanList.get(i).type;
                    originalMainTypeNum = i;
                    break;
                }
            }
            //同理找原来子分类的位置
            for (int i = 1; i <= mOriginalTypeList.size() ; i++){
                if (originalType .equals(mOriginalTypeList.get(i))){
                    originalTypeNum = i;
                    break;
                }
            }
        }else {
            //传进来的字符串为空，则设置默认1大类中1小类
            originalMainTypeNum=0;
            mOriginalTypeList=mMainTypeBeanList.get(0).type;
            originalTypeNum=0;
        }

        //给主分类listview设置适配器，并设置滑动到默认位置(遍历成数组)
        mMainTypeStrings = new String[mMainTypeBeanList.size()+2];
        mMainTypeStrings[0]="-主分类-";
        mMainTypeStrings[mMainTypeBeanList.size()+1]=" ";
        for(int i=1;i<=mMainTypeBeanList.size();i++){
            mMainTypeStrings[i]=mMainTypeBeanList.get(i-1).name;
        }
        mMainTypeListView.setAdapter(new ArrayAdapter<String>(context,R.layout.choice_type_item_layout,mMainTypeStrings));
        mMainTypeListView.setSelection(originalMainTypeNum);

        //给子分类listview设置适配器，并设置滑动到默认位置
        mTypeListView.setAdapter(new ArrayAdapter<String>(context,R.layout.choice_type_item_layout,typebeanToStrings(mOriginalTypeList)));
        mTypeListView.setSelection(originalTypeNum);
    }

    private  String[] typebeanToStrings(List<MainTypeBean.TypeBean> typeBeanList){
        String [] typeStrings = new String[typeBeanList.size()+2];
        typeStrings[0]=" ";
        typeStrings[typeBeanList.size()+1]=" ";
        for(int i=1;i<=typeBeanList.size();i++){
            typeStrings[i]=typeBeanList.get(i-1).name;
        }
        return typeStrings;
    }

}