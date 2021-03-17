package cn.xihan.age.ui.fragments.home;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.qmuiteam.qmui.arch.QMUIFragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.xihan.age.Config;
import cn.xihan.age.R;
import cn.xihan.age.base.BaseRecyclerAdapter;
import cn.xihan.age.base.RecyclerViewHolder;
import cn.xihan.age.bean.MuluBean;
import cn.xihan.age.custom.MyTabLayout;
import cn.xihan.age.ui.fragments.AniInfosFragment;
import cn.xihan.age.utils.MySingleton;

/**
 * @项目名 : AGE动漫
 * @作者 : MissYang
 * @创建时间 : 2021/3/2 0:59
 * @介绍 :
 */
public class MlFragment extends QMUIFragment {
    FrameLayout layout;
    @BindView(R.id.tab_layout1)
    MyTabLayout  tabLayout1;
    @BindView(R.id.tab_layout2)
    MyTabLayout  tabLayout2;
    @BindView(R.id.tab_layout3)
    MyTabLayout  tabLayout3;
    @BindView(R.id.tab_layout4)
    MyTabLayout  tabLayout4;
    @BindView(R.id.tab_layout5)
    MyTabLayout  tabLayout5;
    @BindView(R.id.tab_layout6)
    MyTabLayout  tabLayout6;
    @BindView(R.id.tab_layout7)
    MyTabLayout  tabLayout7;
    @BindView(R.id.tab_layout8)
    MyTabLayout  tabLayout8;
    @BindView(R.id.tab_layout9)
    MyTabLayout  tabLayout9;
    @BindView(R.id.et_mulu)
    EditText     etMulu;
    @BindView(R.id.bt_shaixuan)
    Button       btShaixuan;
    @BindView(R.id.tv_zongshu)
    TextView     tvZongshu;
    @BindView(R.id.listview)
    RecyclerView listview;

    RequestQueue queue;
    Context          ctx;

    AniInfosFragment aniInfosFragment;

    String zzdq,zzbb,zzszm,zznf,zzjd,zzzt,zzlx,zzzy,zzpx,URL,GuolvGJC,Fjzongshu;

    Bundle bundle;

    final List<MuluBean.AniPreLDTO> MuluDataBean = new ArrayList<>();

    BaseRecyclerAdapter<MuluBean.AniPreLDTO> mMuluAdapter;

    @Override
    protected View onCreateView() {
        layout = (FrameLayout) LayoutInflater.from(getActivity()).inflate(R.layout.fragment_ml, null);
        ButterKnife.bind(this, layout);
        ctx = requireContext();
        queue = MySingleton.getInstance(ctx).getRequestQueue();
        initView();
        return layout;
    }

    /**
     * 初始化
     */
    private void initView() {
        bundle =new Bundle();

        tabLayout1.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() ==0){
                    bundle.putString("dq","all");
                    //Toast.makeText(getContext(),"选中全部",Toast.LENGTH_SHORT).show();
                }else if(tab.getPosition() ==1){
                    bundle.putString("dq","日本");
                    //Toast.makeText(getContext(),"选中日本",Toast.LENGTH_SHORT).show();
                }
                else if(tab.getPosition() ==2){
                    bundle.putString("dq","中国");
                    //Toast.makeText(getContext(),"选中中国",Toast.LENGTH_SHORT).show();
                }
                else if(tab.getPosition() ==3){
                    bundle.putString("dq","欧美");
                    //Toast.makeText(getContext(),"选中欧美",Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        Objects.requireNonNull(tabLayout1.getTabAt(0)).select();
        tabLayout2.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() ==0){
                    bundle.putString("bb","all");
                    //Toast.makeText(getContext(),"选中全部",Toast.LENGTH_SHORT).show();
                }else if(tab.getPosition() ==1){
                    bundle.putString("bb","TV");
                    //Toast.makeText(getContext(),"选中TV",Toast.LENGTH_SHORT).show();
                }
                else if(tab.getPosition() ==2){
                    bundle.putString("bb","剧场版");
                    //Toast.makeText(getContext(),"选中剧场版",Toast.LENGTH_SHORT).show();
                }
                else if(tab.getPosition() ==3){
                    bundle.putString("bb","OVA");
                    //Toast.makeText(getContext(),"选中OVA",Toast.LENGTH_SHORT).show();

                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        tabLayout3.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() ==0){
                    bundle.putString("szm","all");
                    //Toast.makeText(getContext(),"选中全部",Toast.LENGTH_SHORT).show();
                }else if(tab.getPosition() ==1){
                    bundle.putString("szm","A");
                    //Toast.makeText(getContext(),"选中A",Toast.LENGTH_SHORT).show();
                }
                else if(tab.getPosition() ==2){
                    bundle.putString("szm","B");
                    //Toast.makeText(getContext(),"选中B",Toast.LENGTH_SHORT).show();
                }
                else if(tab.getPosition() ==3){
                    bundle.putString("szm","C");
                    //Toast.makeText(getContext(),"选中C",Toast.LENGTH_SHORT).show();

                }
                else if(tab.getPosition() ==4){
                    bundle.putString("szm","D");
                    //Toast.makeText(getContext(),"选中D",Toast.LENGTH_SHORT).show();

                }
                else if(tab.getPosition() ==5){
                    bundle.putString("szm","E");
                    //Toast.makeText(getContext(),"选中E",Toast.LENGTH_SHORT).show();

                }
                else if(tab.getPosition() ==6){
                    bundle.putString("szm","F");
                    //Toast.makeText(getContext(),"选中F",Toast.LENGTH_SHORT).show();

                }
                else if(tab.getPosition() ==7){
                    bundle.putString("szm","G");
                    //Toast.makeText(getContext(),"选中G",Toast.LENGTH_SHORT).show();

                }
                else if(tab.getPosition() ==8){
                    bundle.putString("szm","H");
                    //Toast.makeText(getContext(),"选中H",Toast.LENGTH_SHORT).show();

                }else if(tab.getPosition() ==9){
                    bundle.putString("szm","I");
                    //Toast.makeText(getContext(),"选中I",Toast.LENGTH_SHORT).show();

                }else if(tab.getPosition() ==10){
                    bundle.putString("szm","J");
                    //Toast.makeText(getContext(),"选中J",Toast.LENGTH_SHORT).show();

                }else if(tab.getPosition() ==11){
                    bundle.putString("szm","K");
                    //Toast.makeText(getContext(),"选中K",Toast.LENGTH_SHORT).show();

                }else if(tab.getPosition() ==12){
                    bundle.putString("szm","L");
                    //Toast.makeText(getContext(),"选中L",Toast.LENGTH_SHORT).show();

                }else if(tab.getPosition() ==13){
                    bundle.putString("szm","M");
                    //Toast.makeText(getContext(),"选中M",Toast.LENGTH_SHORT).show();

                }else if(tab.getPosition() ==14){
                    bundle.putString("szm","N");
                    //Toast.makeText(getContext(),"选中N",Toast.LENGTH_SHORT).show();

                }else if(tab.getPosition() ==15){
                    bundle.putString("szm","O");
                    //Toast.makeText(getContext(),"选中O",Toast.LENGTH_SHORT).show();

                }else if(tab.getPosition() ==16){
                    bundle.putString("szm","P");
                    //Toast.makeText(getContext(),"选中P",Toast.LENGTH_SHORT).show();

                }else if(tab.getPosition() ==17){
                    bundle.putString("szm","Q");
                    //Toast.makeText(getContext(),"选中Q",Toast.LENGTH_SHORT).show();

                }else if(tab.getPosition() ==18){
                    bundle.putString("szm","R");
                    //Toast.makeText(getContext(),"选中R",Toast.LENGTH_SHORT).show();

                }else if(tab.getPosition() ==19){
                    bundle.putString("szm","S");
                    //Toast.makeText(getContext(),"选中S",Toast.LENGTH_SHORT).show();

                }else if(tab.getPosition() ==20){
                    bundle.putString("szm","T");
                    //Toast.makeText(getContext(),"选中T",Toast.LENGTH_SHORT).show();

                }else if(tab.getPosition() ==21){
                    bundle.putString("szm","U");
                    //Toast.makeText(getContext(),"选中U",Toast.LENGTH_SHORT).show();

                }else if(tab.getPosition() ==22){
                    bundle.putString("szm","V");
                    //Toast.makeText(getContext(),"选中V",Toast.LENGTH_SHORT).show();

                }else if(tab.getPosition() ==23){
                    bundle.putString("szm","W");
                    //Toast.makeText(getContext(),"选中W",Toast.LENGTH_SHORT).show();

                }else if(tab.getPosition() ==24){
                    bundle.putString("szm","X");
                    //Toast.makeText(getContext(),"选中X",Toast.LENGTH_SHORT).show();

                }else if(tab.getPosition() ==25){
                    bundle.putString("szm","Y");
                    //Toast.makeText(getContext(),"选中Y",Toast.LENGTH_SHORT).show();

                }else if(tab.getPosition() ==26){
                    bundle.putString("szm","Z");
                    //Toast.makeText(getContext(),"选中Z",Toast.LENGTH_SHORT).show();

                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        tabLayout4.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() ==0){
                    bundle.putString("nf","all");
                    //Toast.makeText(getContext(),"选中全部",Toast.LENGTH_SHORT).show();
                }else if(tab.getPosition() ==1){
                    bundle.putString("nf","2021");
                    //Toast.makeText(getContext(),"选中2021",Toast.LENGTH_SHORT).show();
                }else if(tab.getPosition() ==2){
                    bundle.putString("nf","2020");
                    //Toast.makeText(getContext(),"2020",Toast.LENGTH_SHORT).show();
                }else if(tab.getPosition() ==3){
                    bundle.putString("nf","2019");
                    //Toast.makeText(getContext(),"2019",Toast.LENGTH_SHORT).show();
                }else if(tab.getPosition() ==4){
                    bundle.putString("nf","2018");
                    //Toast.makeText(getContext(),"2018",Toast.LENGTH_SHORT).show();
                }else if(tab.getPosition() ==5){
                    bundle.putString("nf","2017");
                    //Toast.makeText(getContext(),"2017",Toast.LENGTH_SHORT).show();
                }else if(tab.getPosition() ==6){
                    bundle.putString("nf","2016");
                    //Toast.makeText(getContext(),"2016",Toast.LENGTH_SHORT).show();
                }else if(tab.getPosition() ==7){
                    bundle.putString("nf","2015");
                    //Toast.makeText(getContext(),"2015",Toast.LENGTH_SHORT).show();
                }else if(tab.getPosition() ==8){
                    bundle.putString("nf","2014");
                    //Toast.makeText(getContext(),"2014",Toast.LENGTH_SHORT).show();
                }else if(tab.getPosition() ==9){
                    bundle.putString("nf","2013");
                    //Toast.makeText(getContext(),"2013",Toast.LENGTH_SHORT).show();
                }else if(tab.getPosition() ==10){
                    bundle.putString("nf","2012");
                    //Toast.makeText(getContext(),"2012",Toast.LENGTH_SHORT).show();
                }else if(tab.getPosition() ==11){
                    bundle.putString("nf","2011");
                    //Toast.makeText(getContext(),"2011",Toast.LENGTH_SHORT).show();
                }else if(tab.getPosition() ==12){
                    bundle.putString("nf","2010");
                    //Toast.makeText(getContext(),"2010",Toast.LENGTH_SHORT).show();
                }else if(tab.getPosition() ==13){
                    bundle.putString("nf","2009");
                    //Toast.makeText(getContext(),"2009",Toast.LENGTH_SHORT).show();
                }else if(tab.getPosition() ==14){
                    bundle.putString("nf","2008");
                    //Toast.makeText(getContext(),"2008",Toast.LENGTH_SHORT).show();
                }else if(tab.getPosition() ==15){
                    bundle.putString("nf","2007");
                    //Toast.makeText(getContext(),"2007",Toast.LENGTH_SHORT).show();
                }else if(tab.getPosition() ==16){
                    bundle.putString("nf","2006");
                    //Toast.makeText(getContext(),"2006",Toast.LENGTH_SHORT).show();
                }else if(tab.getPosition() ==17){
                    bundle.putString("nf","2005");
                    //Toast.makeText(getContext(),"2005",Toast.LENGTH_SHORT).show();
                }else if(tab.getPosition() ==18){
                    bundle.putString("nf","2004");
                    //Toast.makeText(getContext(),"2004",Toast.LENGTH_SHORT).show();
                }else if(tab.getPosition() ==19){
                    bundle.putString("nf","2003");
                    //Toast.makeText(getContext(),"2003",Toast.LENGTH_SHORT).show();
                }else if(tab.getPosition() ==20){
                    bundle.putString("nf","2002");
                    //Toast.makeText(getContext(),"2002",Toast.LENGTH_SHORT).show();
                }else if(tab.getPosition() ==21){
                    bundle.putString("nf","2001");
                    //Toast.makeText(getContext(),"2001",Toast.LENGTH_SHORT).show();
                }else if(tab.getPosition() ==22){
                    bundle.putString("nf","2000");
                    //Toast.makeText(getContext(),"2000",Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        tabLayout5.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() ==0){
                    bundle.putString("jd","all");
                    //Toast.makeText(getContext(),"选中全部",Toast.LENGTH_SHORT).show();
                }else if(tab.getPosition() ==1){
                    bundle.putString("jd","1");
                    //Toast.makeText(getContext(),"选中1月",Toast.LENGTH_SHORT).show();
                }
                else if(tab.getPosition() ==2){
                    bundle.putString("jd","4");
                    //Toast.makeText(getContext(),"选中4月",Toast.LENGTH_SHORT).show();
                }
                else if(tab.getPosition() ==3){
                    bundle.putString("jd","7");
                    //Toast.makeText(getContext(),"选中7月",Toast.LENGTH_SHORT).show();

                }else if(tab.getPosition() ==4){
                    bundle.putString("jd","10");
                    //Toast.makeText(getContext(),"选中10月",Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        tabLayout6.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() ==0){
                    bundle.putString("zt","all");
                    //Toast.makeText(getContext(),"选中全部",Toast.LENGTH_SHORT).show();
                }else if(tab.getPosition() ==1){
                    bundle.putString("zt","连载");
                    //Toast.makeText(getContext(),"选中连载",Toast.LENGTH_SHORT).show();
                }
                else if(tab.getPosition() ==2){
                    bundle.putString("zt","完结");
                    //Toast.makeText(getContext(),"选中完结",Toast.LENGTH_SHORT).show();
                }
                else if(tab.getPosition() ==3){
                    bundle.putString("zt","未播放");
                    //Toast.makeText(getContext(),"选中未播放",Toast.LENGTH_SHORT).show();

                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        tabLayout7.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() ==0){
                    bundle.putString("lx","all");
                    //Toast.makeText(getContext(),"选中全部",Toast.LENGTH_SHORT).show();
                }else if(tab.getPosition() ==1){
                    bundle.putString("lx","搞笑");
                    //Toast.makeText(getContext(),"选中搞笑",Toast.LENGTH_SHORT).show();
                }
                else if(tab.getPosition() ==2){
                    bundle.putString("lx","运动");
                    //Toast.makeText(getContext(),"选中运动",Toast.LENGTH_SHORT).show();
                }
                else if(tab.getPosition() ==3){
                    bundle.putString("lx","励志");
                    //Toast.makeText(getContext(),"选中励志",Toast.LENGTH_SHORT).show();

                }
                else if(tab.getPosition() ==4){
                    bundle.putString("lx","热血");
                    //Toast.makeText(getContext(),"选中热血",Toast.LENGTH_SHORT).show();

                }else if(tab.getPosition() ==5){
                    bundle.putString("lx","战斗");
                    //Toast.makeText(getContext(),"选中战斗",Toast.LENGTH_SHORT).show();

                }else if(tab.getPosition() ==6){
                    bundle.putString("lx","竞技");
                    //Toast.makeText(getContext(),"选中竞技",Toast.LENGTH_SHORT).show();

                }else if(tab.getPosition() ==7){
                    bundle.putString("lx","校园");
                    //Toast.makeText(getContext(),"选中校园",Toast.LENGTH_SHORT).show();

                }else if(tab.getPosition() ==8){
                    bundle.putString("lx","青春");
                    //Toast.makeText(getContext(),"选中青春",Toast.LENGTH_SHORT).show();

                }else if(tab.getPosition() ==9){
                    bundle.putString("lx","爱情");
                    //Toast.makeText(getContext(),"选中爱情",Toast.LENGTH_SHORT).show();

                }else if(tab.getPosition() ==10){
                    bundle.putString("lx","冒险");
                    //Toast.makeText(getContext(),"选中冒险",Toast.LENGTH_SHORT).show();

                }else if(tab.getPosition() ==11){
                    bundle.putString("lx","后宫");
                    //Toast.makeText(getContext(),"选中后宫",Toast.LENGTH_SHORT).show();

                }else if(tab.getPosition() ==12){
                    bundle.putString("lx","百合");
                    //Toast.makeText(getContext(),"选中百合",Toast.LENGTH_SHORT).show();

                }else if(tab.getPosition() ==13){
                    bundle.putString("lx","萝莉");
                    //Toast.makeText(getContext(),"选中萝莉",Toast.LENGTH_SHORT).show();

                }else if(tab.getPosition() ==14){
                    bundle.putString("lx","魔法");
                    //Toast.makeText(getContext(),"选中魔法",Toast.LENGTH_SHORT).show();

                }else if(tab.getPosition() ==15){
                    bundle.putString("lx","悬疑");
                    //Toast.makeText(getContext(),"悬疑",Toast.LENGTH_SHORT).show();

                }else if(tab.getPosition() ==16){
                    bundle.putString("lx","推理");
                    //Toast.makeText(getContext(),"推理",Toast.LENGTH_SHORT).show();

                }else if(tab.getPosition() ==17){
                    bundle.putString("lx","奇幻");
                    //Toast.makeText(getContext(),"奇幻",Toast.LENGTH_SHORT).show();

                }else if(tab.getPosition() ==18){
                    bundle.putString("lx","科幻");
                    //Toast.makeText(getContext(),"科幻",Toast.LENGTH_SHORT).show();

                }else if(tab.getPosition() ==19){
                    bundle.putString("lx","游戏");
                    //Toast.makeText(getContext(),"游戏",Toast.LENGTH_SHORT).show();

                }else if(tab.getPosition() ==20){
                    bundle.putString("lx","神魔");
                    //Toast.makeText(getContext(),"神魔",Toast.LENGTH_SHORT).show();

                }else if(tab.getPosition() ==21){
                    bundle.putString("lx","恐怖");
                    //Toast.makeText(getContext(),"恐怖",Toast.LENGTH_SHORT).show();

                }else if(tab.getPosition() ==22){
                    bundle.putString("lx","血腥");
                    //Toast.makeText(getContext(),"血腥",Toast.LENGTH_SHORT).show();

                }else if(tab.getPosition() ==23){
                    bundle.putString("lx","机战");
                    //Toast.makeText(getContext(),"机战",Toast.LENGTH_SHORT).show();

                }else if(tab.getPosition() ==24){
                    bundle.putString("lx","战争");
                    //Toast.makeText(getContext(),"战争",Toast.LENGTH_SHORT).show();

                }else if(tab.getPosition() ==25){
                    bundle.putString("lx","犯罪");
                    //Toast.makeText(getContext(),"犯罪",Toast.LENGTH_SHORT).show();

                }else if(tab.getPosition() ==26){
                    bundle.putString("lx","历史");
                    //Toast.makeText(getContext(),"历史",Toast.LENGTH_SHORT).show();

                }else if(tab.getPosition() ==27){
                    bundle.putString("lx","社会");
                    //Toast.makeText(getContext(),"社会",Toast.LENGTH_SHORT).show();

                }else if(tab.getPosition() ==28){
                    bundle.putString("lx","职场");
                    //Toast.makeText(getContext(),"职场",Toast.LENGTH_SHORT).show();

                }else if(tab.getPosition() ==29){
                    bundle.putString("lx","剧情");
                    //Toast.makeText(getContext(),"剧情",Toast.LENGTH_SHORT).show();

                }else if(tab.getPosition() ==30){
                    bundle.putString("lx","伪娘");
                    //Toast.makeText(getContext(),"伪娘",Toast.LENGTH_SHORT).show();

                }else if(tab.getPosition() ==31){
                    bundle.putString("lx","耽美");
                    //Toast.makeText(getContext(),"耽美",Toast.LENGTH_SHORT).show();

                }else if(tab.getPosition() ==32){
                    bundle.putString("lx","童年");
                    //Toast.makeText(getContext(),"童年",Toast.LENGTH_SHORT).show();

                }else if(tab.getPosition() ==33){
                    bundle.putString("lx","教育");
                    //Toast.makeText(getContext(),"教育",Toast.LENGTH_SHORT).show();

                }else if(tab.getPosition() ==34){
                    bundle.putString("lx","亲子");
                    //Toast.makeText(getContext(),"亲子",Toast.LENGTH_SHORT).show();

                }else if(tab.getPosition() ==35){
                    bundle.putString("lx","真人");
                    //Toast.makeText(getContext(),"真人",Toast.LENGTH_SHORT).show();

                }else if(tab.getPosition() ==36){
                    bundle.putString("lx","歌舞");
                    //Toast.makeText(getContext(),"歌舞",Toast.LENGTH_SHORT).show();

                }else if(tab.getPosition() ==37){
                    bundle.putString("lx","肉番");
                    //Toast.makeText(getContext(),"肉番",Toast.LENGTH_SHORT).show();

                }else if(tab.getPosition() ==38){
                    bundle.putString("lx","美少女");
                    //Toast.makeText(getContext(),"美少女",Toast.LENGTH_SHORT).show();

                }else if(tab.getPosition() ==39){
                    bundle.putString("lx","轻小说");
                    //Toast.makeText(getContext(),"轻小说",Toast.LENGTH_SHORT).show();

                }else if(tab.getPosition() ==40){
                    bundle.putString("lx","吸血鬼");
                    //Toast.makeText(getContext(),"吸血鬼",Toast.LENGTH_SHORT).show();

                }else if(tab.getPosition() ==41){
                    bundle.putString("lx","女性向");
                    //Toast.makeText(getContext(),"女性向",Toast.LENGTH_SHORT).show();

                }else if(tab.getPosition() ==42){
                    bundle.putString("lx","泡面番");
                    //Toast.makeText(getContext(),"泡面番",Toast.LENGTH_SHORT).show();

                }else if(tab.getPosition() ==43){
                    bundle.putString("lx","欢乐向");
                    //Toast.makeText(getContext(),"欢乐向",Toast.LENGTH_SHORT).show();

                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        tabLayout8.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() ==0){
                    bundle.putString("zy","all");
                    //Toast.makeText(getContext(),"选中全部",Toast.LENGTH_SHORT).show();
                }else if(tab.getPosition() ==1){
                    bundle.putString("zy","BDRIP");
                    //Toast.makeText(getContext(),"选中BDRIP",Toast.LENGTH_SHORT).show();
                }
                else if(tab.getPosition() ==2){
                    bundle.putString("zy","AGERIP");
                    //Toast.makeText(getContext(),"选中AGERIP",Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        tabLayout9.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() ==0){
                    bundle.putString("px","更新时间");
                    //Toast.makeText(getContext(),"选中更新时间",Toast.LENGTH_SHORT).show();
                }else if(tab.getPosition() ==1){
                    bundle.putString("px","名称");
                    //Toast.makeText(getContext(),"选中名称",Toast.LENGTH_SHORT).show();
                }
                else if(tab.getPosition() ==2){
                    bundle.putString("px","点击量");
                    //Toast.makeText(getContext(),"选中点击量",Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @OnClick(R.id.bt_shaixuan)
    public void onClick() {
        if (tabLayout1.getSelectedTabPosition() == 0){ zzdq = "all"; }else {zzdq = bundle.getString("dq");}
        if (tabLayout2.getSelectedTabPosition() == 0){ zzbb = "all"; }else {zzbb = bundle.getString("bb");}
        if (tabLayout3.getSelectedTabPosition() == 0){ zzszm = "all"; }else {zzszm = bundle.getString("szm");}
        if (tabLayout4.getSelectedTabPosition() == 0){ zznf = "all"; }else {zznf = bundle.getString("nf");}
        if (tabLayout5.getSelectedTabPosition() == 0){ zzjd = "all"; }else {zzjd = bundle.getString("jd");}
        if (tabLayout6.getSelectedTabPosition() == 0){ zzzt = "all"; }else {zzzt = bundle.getString("zt");}
        if (tabLayout7.getSelectedTabPosition() == 0){ zzlx = "all"; }else {zzlx = bundle.getString("lx");}
        if (tabLayout8.getSelectedTabPosition() == 0){ zzzy = "all"; }else {zzzy = bundle.getString("zy");}
        if (tabLayout9.getSelectedTabPosition() == 0){ zzpx = "更新时间"; }else {zzpx = bundle.getString("px");}
        String dq = "&region=" +zzdq;
        String bb = "?genre=" +zzbb;
        String szm = "&letter=" +zzszm;
        String nf = "&year=" +zznf;
        String jd = "&season=" +zzjd;
        String zt = "&status=" +zzzt;
        String lx = "&label=" +zzlx;
        String zy = "&resource=" +zzzy;
        String px = "&order=" +zzpx;
        if (!TextUtils.isEmpty(etMulu.getText().toString())) {
            URL= Config.FlMuluApi + bb + lx +szm +px +dq + zy +jd +zt   +nf+ "&page=1&size=" + etMulu.getText().toString();
        }else {
            URL= Config.FlMuluApi + bb + lx +szm +px +dq + zy +jd +zt  +nf+ "&page=1&size=15";
        }
        getMuluList(URL);

    }

    /**
     * 得到目录结果
     * @param url
     */
    private void getMuluList(String url) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, jsonObject -> {
            //Log.d("xihan","目录结果:" + jsonObject);
            if (!TextUtils.isEmpty(String.valueOf(jsonObject))){
                try {
                    JSONArray jsonArray = jsonObject.optJSONArray("AniPreL");
                    if (Objects.requireNonNull(jsonArray).length() > 0 ){
                        for (int i = 0; jsonArray.length() > i; i++){
                            JSONObject MuluJg = jsonArray.getJSONObject(i);
                            MuluBean.AniPreLDTO muluBean = new MuluBean.AniPreLDTO();
                            muluBean.setR动画名称(MuluJg.optString("R动画名称"));
                            muluBean.setR首播时间(MuluJg.optString("R首播时间"));
                            muluBean.setR制作公司(MuluJg.optString("R制作公司"));
                            muluBean.setR简介(MuluJg.optString("R简介"));
                            muluBean.setR播放状态(MuluJg.optString("R播放状态"));
                            muluBean.setR原版名称(MuluJg.optString("R原版名称"));
                            muluBean.setR封面图小(MuluJg.optString("R封面图小"));
                            muluBean.setAid(MuluJg.optString("AID"));
                            //Log.d("xihan", "动画名称:" + MuluJg.optString("R动画名称"));
                            MuluDataBean.add(muluBean);
                        }
                    }
                    GuolvGJC = jsonObject.optString("PageCtrl");
                    Fjzongshu= jsonObject.optString("AllCnt");
                    //Log.d("xihan","总数:" + Fjzongshu + "过滤关键词：" +GuolvGJC);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
            requireActivity().runOnUiThread(this::setDate);
        }, null);
        queue.add(jsonObjectRequest);
    }

    /**
     * 设置数据
     */
    private void setDate() {
        tvZongshu.setText("共" + Fjzongshu + "部");
        listview.setLayoutManager(new LinearLayoutManager(ctx) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });
        mMuluAdapter = new BaseRecyclerAdapter<MuluBean.AniPreLDTO>(ctx,MuluDataBean) {
            @Override
            public int getItemLayoutId() {
                return R.layout.item_serach;
            }

            @Override
            public void bindData(RecyclerViewHolder holder, MuluBean.AniPreLDTO item) {
                TextView name = holder.itemView.findViewById(R.id.name);
                TextView createdAt =holder.itemView.findViewById(R.id.createdAt);
                TextView updatedAt =holder.itemView.findViewById(R.id.updatedAt);
                TextView description=holder.itemView.findViewById(R.id.description);
                TextView state= holder.itemView.findViewById(R.id.state);
                TextView region=holder.itemView.findViewById(R.id.region);
                ImageView imageView=holder.itemView.findViewById(R.id.cover);
                name.setText( "番剧名称:"+item.getR动画名称());
                createdAt.setText("首播时间:"+item.getR首播时间());
                updatedAt.setText("制作公司:"+item.getR制作公司());
                description.setText("介绍:"+item.getR简介());
                state.setText("状态:"+item.getR播放状态());
                region.setText("原版名称:"+item.getR原版名称());
                //Log.v("xihan cover","http:"+bangumiData.getCover());
                String fmt =item.getR封面图小().replace("http:", "").replace("https:", "");
                //Log.d("xihan","封面图url:" + fmt);
                Glide.with(requireContext()).load( "https:" +fmt).into(imageView);

            }
        };
        listview.setAdapter(mMuluAdapter);
        if (MuluDataBean.size() >15){
            RelativeLayout.LayoutParams lp =
                    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, MuluDataBean.size() * 91);
            listview.setLayoutParams(lp);
        }
        mMuluAdapter.setOnItemClickListener((itemView, pos) -> {
            String url = Config.AniInfoApi+MuluDataBean.get(pos).getAid();
            //Toast.makeText(getContext(), url, Toast.LENGTH_LONG).show();
            String aid =MuluDataBean.get(pos).getAid();
            Bundle bundle = new Bundle();
            bundle.putString("url",url);
            bundle.putString("aid",aid);
            if (aniInfosFragment == null){
                aniInfosFragment = new AniInfosFragment();
            }
            aniInfosFragment.setArguments(bundle);
            startFragment(aniInfosFragment);
        });
    }
}
