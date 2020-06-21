package com.forudacity.backingapp.view.steps.stepMain;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.forudacity.backingapp.R;
import com.forudacity.backingapp.databinding.FragmentStepsListBinding;
import com.forudacity.backingapp.model.Step;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class StepsListFragment extends Fragment implements StepsAdapter.CardClickListener {


    public interface OnStepClickListener {
        void onStepSelected(int currentIndex);
    }
    private OnStepClickListener mCallback ;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mCallback = (OnStepClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnStepClickListener");
        }
    }

    private FragmentStepsListBinding mFragmentStepsListBinding ;

    private StepsListViewModel stepsListViewModel ;

    public StepsListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mFragmentStepsListBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_steps_list , container , false);
        initViewModel();
        initStepsRecyclerView();

        return mFragmentStepsListBinding.getRoot() ;
    }


    @Override
    public void onResume() {
        super.onResume();
        getStepsData();
    }

    private void initViewModel() {
        stepsListViewModel = ViewModelProviders.of(this).get(StepsListViewModel.class);
    }

    private void initStepsRecyclerView() {
        mFragmentStepsListBinding.stepsRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireActivity());
        mFragmentStepsListBinding.stepsRecyclerView.setLayoutManager(linearLayoutManager);

    }

    private void getStepsData() {
        stepsListViewModel.getStepListLiveData().observe(this, new Observer<List<Step>>() {
            @Override
            public void onChanged(List<Step> steps) {
                if (steps !=null && !steps.isEmpty()) {
                    StepsAdapter stepsAdapter = new StepsAdapter(steps, requireActivity(), StepsListFragment.this);
                    mFragmentStepsListBinding.stepsRecyclerView.setAdapter(stepsAdapter);
                }
            }
        });
    }

    @Override
    public void OnCardClickListener(int currentIndex) { mCallback.onStepSelected(currentIndex); }
}
