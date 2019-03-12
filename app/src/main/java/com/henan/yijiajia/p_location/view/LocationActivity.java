package com.henan.yijiajia.p_location.view;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.henan.yijiajia.R;
import com.henan.yijiajia.p_base.BaseActivity;
import com.henan.yijiajia.p_location.adapter.ContactAdapter;
import com.henan.yijiajia.p_location.adapter.HotCityGridViewAdapter;
import com.henan.yijiajia.p_location.bean.UserEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.yokeyword.indexablerv.IndexableAdapter;
import me.yokeyword.indexablerv.IndexableHeaderAdapter;
import me.yokeyword.indexablerv.IndexableLayout;

public class LocationActivity extends BaseActivity {
    //IndexableLayout 的适配器
    private ContactAdapter mAdapter;
    //自定义头部adapter
    private BannerHeaderAdapter mBannerHeaderAdapter;
    //热门城市的数组
    private String[] mHotCity = {"上海", "深圳", "广州", "北京", "重庆", "福州", "大连", "上海", "苏州", "杭州", "成都", "武汉"};

    //热门城市的适配器
    private HotCityGridViewAdapter hotCityGridViewAdapter;
    // 热门城市的集合
    private ArrayList<String> list;
    //返回按钮
    private ImageView mBackImageView;

    private Intent intent;
    private IndexableLayout mIndexableLayout;

    @Override
    protected int initLayout() {
        return R.layout.activity_location;
    }

    @Override
    protected void initView() {
        intent = getIntent();
        mBackImageView = (ImageView) findViewById(R.id.pic_contact_back);
        mIndexableLayout = (IndexableLayout) findViewById(R.id.indexableLayout);

    }

    @Override
    protected void initData() {
        mIndexableLayout.setLayoutManager(new LinearLayoutManager(this));
        initAdapter();
    }

    public void initAdapter() {
        mAdapter = new ContactAdapter(this);
        mIndexableLayout.setAdapter(mAdapter);
        mIndexableLayout.setOverlayStyle_Center();
        mAdapter.setDatas(initDatas());
        /*indexableLayout.setOverlayStyle_MaterialDesign(Color.RED);设置提示框为仿联系人气泡样式*/
        // 全字母排序,排序规则设置为：每个字母都会进行比较排序(速度较慢)
        mIndexableLayout.setCompareMode(IndexableLayout.MODE_FAST);
        /*indexableLayout.addHeaderAdapter(new SimpleHeaderAdapter<>(mAdapter, "☆",null, null));
         构造函数里3个参数,分别对应 (IndexBar的字母索引, IndexTitle, 数据源), 不想显示哪个就传null, 数据源传null时,代表add一个普通的View
         mMenuHeaderAdapter = new MenuHeaderAdapter("↑", null, initMenuDatas());
         indexableLayout.addHeaderAdapter(mMenuHeaderAdapter);*/

        // 这里BannerView只有一个Item, 添加一个长度为1的任意List作为第三个参数
        List<String> bannerList = new ArrayList<>();
        bannerList.add("");
        mBannerHeaderAdapter = new BannerHeaderAdapter("↑", null, bannerList);
        mIndexableLayout.addHeaderAdapter(mBannerHeaderAdapter);
    }

    protected void addListener() {
        mBackImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mAdapter.setOnItemContentClickListener(new IndexableAdapter.OnItemContentClickListener<UserEntity>() {
            @Override
            public void onItemClick(View v, int originalPosition, int currentPosition, UserEntity entity) {
                if (originalPosition >= 0) {
                    intent.putExtra("city", entity.getNick());
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                }
            }
        });
    }

    ;

    /**
     * 自定义的Banner Header
     */
    class BannerHeaderAdapter extends IndexableHeaderAdapter {
        private static final int TYPE = 1;

        //这里传的参数上面注释有
        public BannerHeaderAdapter(String index, String indexTitle, List datas) {
            super(index, indexTitle, datas);
        }

        @Override
        public int getItemViewType() {
            return TYPE;
        }

        @Override
        public RecyclerView.ViewHolder onCreateContentViewHolder(ViewGroup parent) {
            View view = LayoutInflater.from(LocationActivity.this).inflate(R.layout.location_banner_header, parent, false);
            VH holder = new VH(view);
            return holder;
        }

        @Override
        public void onBindContentViewHolder(RecyclerView.ViewHolder holder, Object entity) {
            // 数据源为null时, 该方法不用实现
            final VH vh = (VH) holder;
            list = new ArrayList<>();
            for (int i = 0; i < mHotCity.length; i++) {
                list.add(mHotCity[i]);
            }
            hotCityGridViewAdapter = new HotCityGridViewAdapter(LocationActivity.this, list);
            // 绑定adpter
            vh.head_home_change_city_gridview.setAdapter(hotCityGridViewAdapter);
            //热门城市的item点击事件
            vh.head_home_change_city_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    intent.putExtra("city", list.get(position));
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });
            //设置定位城市的点击事件
            vh.item_header_city_dw.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intent.putExtra("city", vh.item_header_city_dw.getText().toString());
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });

        }

        private class VH extends RecyclerView.ViewHolder {
            GridView head_home_change_city_gridview;
            TextView item_header_city_dw;

            public VH(View itemView) {
                super(itemView);
                head_home_change_city_gridview = (QGridView) itemView.findViewById(R.id.item_header_city_gridview);
                item_header_city_dw = (TextView) itemView.findViewById(R.id.item_header_city_dw);
            }
        }
    }

    private List<UserEntity> initDatas() {
        List<UserEntity> list = new ArrayList<>();
        // 初始化数据，R.array.provinces是城市资源，下面有贴出资源文件代码
        List<String> contactStrings = Arrays.asList(getResources().getStringArray(R.array.provinces));
        List<String> mobileStrings = Arrays.asList(getResources().getStringArray(R.array.provinces));
        for (int i = 0; i < contactStrings.size(); i++) {
            UserEntity contactEntity = new UserEntity(contactStrings.get(i), mobileStrings.get(i));
            list.add(contactEntity);
        }
        return list;
    }
}
