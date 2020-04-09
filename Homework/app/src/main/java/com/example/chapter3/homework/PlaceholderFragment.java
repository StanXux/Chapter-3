package com.example.chapter3.homework;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;

public class PlaceholderFragment extends Fragment implements MyAdapter.ListItemClickListener{

    private static final String TAG = "StanXux";
    private LottieAnimationView lottie_animation_view;
    private RecyclerView mrecyclerView;
    private AnimatorSet animatorSet;

    private ArrayList<Item> items = Item.getItems();
    private MyAdapter myAdapter;
    private static int num_friends = 7;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO ex3-3: 修改 fragment_placeholder，添加 loading 控件和列表视图控件
        View view = inflater.inflate(R.layout.fragment_placeholder, container, false);

        lottie_animation_view = view.findViewById(R.id.lottie_animation_view);
        mrecyclerView = view.findViewById(R.id.rv_list);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mrecyclerView.setLayoutManager(layoutManager);
        mrecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        mrecyclerView.setHasFixedSize(true);

        myAdapter = new MyAdapter(num_friends,this);
        mrecyclerView.setAdapter(myAdapter);

        mrecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            // 最后一个完全可见项的位置
            private int lastCompletelyVisibleItemPosition;

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                assert layoutManager != null;
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (visibleItemCount > 0 && lastCompletelyVisibleItemPosition >= totalItemCount - 1) {
                        Toast.makeText(getActivity(), "已滑动到底部!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if (layoutManager instanceof LinearLayoutManager) {
                    lastCompletelyVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastCompletelyVisibleItemPosition();
                }
            }
        });

        mrecyclerView.setAlpha(0f);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getView().postDelayed(new Runnable() {
            @Override
            public void run() {
                // 这里会在 5s 后执行
                // TODO ex3-4：实现动画，将 lottie 控件淡出，列表数据淡入
                if (animatorSet != null) {
                    animatorSet.cancel();
                }

                ObjectAnimator animator1 = ObjectAnimator.ofFloat(lottie_animation_view,
                        "alpha", 1.0f, 0f);
                animator1.setDuration(1000);


                ObjectAnimator animator2 = ObjectAnimator.ofFloat(mrecyclerView,
                        "alpha", 0f, 1.0f);
                animator2.setDuration(1000);

                animatorSet = new AnimatorSet();
                animatorSet.play(animator2).after(animator1);
                animatorSet.start();

            }
        }, 5000);
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {

    }
}
