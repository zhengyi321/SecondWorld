package com.et.secondworld.message.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.et.secondworld.BaseFragment;
import com.et.secondworld.R;
import com.et.secondworld.bean.GetImportantNoticeBean;
import com.et.secondworld.bean.GetImportantNoticeDetailBean;
import com.et.secondworld.message.adapter.MessageImportantNoticeRVAdapter;
import com.et.secondworld.network.NoticeNetWork;
import com.et.secondworld.widget.db.XCCacheManager.XCCacheManager;
import com.et.secondworld.widget.db.XCCacheSaveName.XCCacheSaveName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observer;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/4/7
 **/
public class MessageImportantNoticeFragment extends BaseFragment {

    @BindView(R.id.rv_message_important_notice)
    RecyclerView rvMessageImportantNotice;
    @BindView(R.id.tv_message_important_notice)
    TextView tvMessageImportantNotice;
    private MessageImportantNoticeRVAdapter rvAdapter;
    private int page = 1;
    private int limit = 5;
    View view;
    @Override
    public View setView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_message_important_notice,container,false);
        return view;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this,view);
//        startActivity(new Intent(getActivity(), ChatActivity.class).putExtra(EaseConstant.EXTRA_USER_ID, "aa"));
        initRecycleView();
        initData();
    }


    private void initRecycleView(){
        rvAdapter = new MessageImportantNoticeRVAdapter();
//        ArrayList<String> dataList = new ArrayList<>();
//        for(int i = 0;i < 4;i++){
//            dataList.add("");
//        }
        rvMessageImportantNotice.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvMessageImportantNotice.setAdapter(rvAdapter);
//        rvAdapter.replaceAll(dataList);
    }

    private void initData(){
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(getContext());
        String city = xcCacheManager.readCache(xcCacheSaveName.forumCity);
        NoticeNetWork noticeNetWork = new NoticeNetWork();
//        Log.d("city111",city);
        if(city == null){
            city = "";
        }
        Map<String,Object> map = new HashMap<>();
        map.put("place",city);
        noticeNetWork.getImportantNoticeFromNet(map, new Observer<GetImportantNoticeBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(GetImportantNoticeBean getImportantNoticeBean) {
                if(getImportantNoticeBean.getIssuccess().equals("1")) {

                    List<GetImportantNoticeBean.ListBean> listBeans = getImportantNoticeBean.getList();
                    ArrayList<Object> list = new ArrayList<>();
                    ArrayList<GetImportantNoticeBean.ListBean> listBeanList = new ArrayList<>();
                    GetImportantNoticeBean.ListBean tempItem = null;
                    for(int i=0;i<listBeans.size();i++){
//                        Log.d("zzz112",listBeans.get(i).getTime()+"");

                       if(tempItem == null){
                             tempItem = listBeans.get(i);
                            listBeanList.add(listBeans.get(i));
//                           tempItem = listBeanList.get(0);
//                            listBeans.remove(i);

//                            continue;

                        }
                         if(tempItem.getTime().trim().equals(listBeans.get(i).getTime().trim())){
                            listBeanList.add(listBeans.get(i));
//                            listBeans.remove(i);

//                            continue;
                        }else {
                            Log.d("zzz111112",listBeanList.get(0).getTime()+"");

                             list.add(listBeanList.clone());
                             listBeanList.clear();
//                            break;
//                             listBeanList = new ArrayList<>();
//                            continue;

                            tempItem = listBeans.get(i);
                            listBeanList.add(listBeans.get(i));
//                            listBeans.remove(i);

//                            continue;
                        }
                    }
                    list.add(listBeanList.clone());
                   /* list.add(listBeanList.clone());
                    listBeanList.add(new GetImportantNoticeBean.ListBean());
                    listBeanList.add(new GetImportantNoticeBean.ListBean());
                    list.add(listBeanList.clone());
                    listBeanList.clear();
                    listBeanList.add(new GetImportantNoticeBean.ListBean());
                    listBeanList.add(new GetImportantNoticeBean.ListBean());
                    listBeanList.add(new GetImportantNoticeBean.ListBean());
                    list.add(listBeanList.clone());*/


                    Log.d("zzz112",list+"");
                    if(list.size() == 0){
                        tvMessageImportantNotice.setVisibility(View.VISIBLE);
                    }else {
                        tvMessageImportantNotice.setVisibility(View.GONE);
                    }
                    rvAdapter.replaceAll(list);
                }
            }
        });
    }
    @Override
    public void onResume(){
        super.onResume();
//        initData();
    }
}
