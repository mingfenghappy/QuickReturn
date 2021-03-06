package com.etiennelawlor.quickreturn.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.etiennelawlor.quickreturn.R;
import com.etiennelawlor.quickreturn.adapters.CountriesGridLayoutAdapter;
import com.etiennelawlor.quickreturn.adapters.CountriesLinearLayoutAdapter;
import com.etiennelawlor.quickreturn.itemdecorations.DividerItemDecoration;
import com.etiennelawlor.quickreturn.itemdecorations.GridSpacesItemDecoration;
import com.etiennelawlor.quickreturn.library.enums.QuickReturnAnimationType;
import com.etiennelawlor.quickreturn.library.enums.QuickReturnViewType;
import com.etiennelawlor.quickreturn.library.listeners.QuickReturnRecyclerViewOnScrollListener;
import com.etiennelawlor.quickreturn.library.listeners.SpeedyQuickReturnRecyclerViewOnScrollListener;
import com.etiennelawlor.quickreturn.library.utils.QuickReturnUtils;

import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by etiennelawlor on 6/23/14.
 */
public class QuickReturnHeaderRecyclerViewFragment extends Fragment {

    // region Member Variables
    private String[] mValues;
    private QuickReturnAnimationType mQuickReturnAnimationType;
    private String mLayoutManagerType;
    private QuickReturnRecyclerViewOnScrollListener mScrollListener;
    private SpeedyQuickReturnRecyclerViewOnScrollListener mScrollListener2;

    @Bind(R.id.rv)
    RecyclerView mRecyclerView;
    @Bind(R.id.quick_return_tv)
    TextView mQuickReturnTextView;
    // endregion

    // region Constructors
    public static QuickReturnHeaderRecyclerViewFragment newInstance(Bundle extras) {
        QuickReturnHeaderRecyclerViewFragment fragment = new QuickReturnHeaderRecyclerViewFragment();
        fragment.setRetainInstance(true);
        fragment.setArguments(extras);
        return fragment;
    }
    
    public static QuickReturnHeaderRecyclerViewFragment newInstance() {
        QuickReturnHeaderRecyclerViewFragment fragment = new QuickReturnHeaderRecyclerViewFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public QuickReturnHeaderRecyclerViewFragment() {
    }
    // endregion

    // region Lifecycle Methods
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null) {
            mQuickReturnAnimationType = QuickReturnAnimationType.valueOf(getArguments().getString("quick_return_animation_type"));
            mLayoutManagerType = getArguments().getString("layout_manager");
        }
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recyclerview_quick_return_header, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mValues = getResources().getStringArray(R.array.countries);

        if(mLayoutManagerType.equals("linear")){
            CountriesLinearLayoutAdapter countriesLinearLayoutAdapter = new CountriesLinearLayoutAdapter(getActivity(), Arrays.asList(mValues));

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
            mRecyclerView.setLayoutManager(layoutManager);
            mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), null));

            mRecyclerView.setAdapter(countriesLinearLayoutAdapter);
        } else if(mLayoutManagerType.equals("grid")) {
            CountriesGridLayoutAdapter countriesGridLayoutAdapter = new CountriesGridLayoutAdapter(getActivity(), Arrays.asList(mValues));

            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
            mRecyclerView.setLayoutManager(layoutManager);
            mRecyclerView.addItemDecoration(new GridSpacesItemDecoration(QuickReturnUtils.dp2px(getActivity(), 8)));

            mRecyclerView.setAdapter(countriesGridLayoutAdapter);
        }
        int headerHeight = getActivity().getResources().getDimensionPixelSize(R.dimen.header_height2);

        switch (mQuickReturnAnimationType){
            case TRANSLATION_SIMPLE:
                if(mLayoutManagerType.equals("grid")){
                    mScrollListener = new QuickReturnRecyclerViewOnScrollListener.Builder(QuickReturnViewType.HEADER)
                                    .header(mQuickReturnTextView)
                                    .minHeaderTranslation(-headerHeight)
                                    .columnCount(2)
                                    .build();
                    
                } else {
                    mScrollListener = new QuickReturnRecyclerViewOnScrollListener.Builder(QuickReturnViewType.HEADER)
                            .header(mQuickReturnTextView)
                            .minHeaderTranslation(-headerHeight)
                            .build();
                }
                mRecyclerView.addOnScrollListener(mScrollListener);
                break;
            case TRANSLATION_SNAP:
                if(mLayoutManagerType.equals("grid")){
                    mScrollListener = new QuickReturnRecyclerViewOnScrollListener.Builder(QuickReturnViewType.HEADER)
                            .header(mQuickReturnTextView)
                            .minHeaderTranslation(-headerHeight)
                            .columnCount(2)
                            .isSnappable(true)
                            .build();
                } else {
                    mScrollListener = new QuickReturnRecyclerViewOnScrollListener.Builder(QuickReturnViewType.HEADER)
                            .header(mQuickReturnTextView)
                            .minHeaderTranslation(-headerHeight)
                            .isSnappable(true)
                            .build();
                }
                mRecyclerView.addOnScrollListener(mScrollListener);
                break;
            case TRANSLATION_ANTICIPATE_OVERSHOOT:
                if(mLayoutManagerType.equals("grid")){
                    mScrollListener2 = new SpeedyQuickReturnRecyclerViewOnScrollListener.Builder(getActivity(), QuickReturnViewType.HEADER)
                            .header(mQuickReturnTextView)
                            .columnCount(2)
                            .build();
                } else {
                    mScrollListener2 = new SpeedyQuickReturnRecyclerViewOnScrollListener.Builder(getActivity(), QuickReturnViewType.HEADER)
                            .header(mQuickReturnTextView)
                            .build();
                }
                mRecyclerView.addOnScrollListener(mScrollListener2);
                break;
            default:
                if(mLayoutManagerType.equals("grid")){
                    mScrollListener = new QuickReturnRecyclerViewOnScrollListener.Builder(QuickReturnViewType.HEADER)
                            .header(mQuickReturnTextView)
                            .minHeaderTranslation(-headerHeight)
                            .columnCount(2)
                            .build();
                } else {
                    mScrollListener = new QuickReturnRecyclerViewOnScrollListener.Builder(QuickReturnViewType.HEADER)
                            .header(mQuickReturnTextView)
                            .minHeaderTranslation(-headerHeight)
                            .build();
                }
                mRecyclerView.addOnScrollListener(mScrollListener);
                break;
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        removeListeners();
        ButterKnife.unbind(this);
    }

    // endregion

    // region Helper Methods
    private void removeListeners(){
        if(mQuickReturnAnimationType == QuickReturnAnimationType.TRANSLATION_ANTICIPATE_OVERSHOOT)
            mRecyclerView.removeOnScrollListener(mScrollListener2);
        else
            mRecyclerView.removeOnScrollListener(mScrollListener);
    }
    // endregion
}
