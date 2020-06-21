package com.forudacity.backingapp.view.steps.stepMain;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;


import com.forudacity.backingapp.R;
import com.forudacity.backingapp.databinding.StepsListItemBinding;
import com.forudacity.backingapp.model.Step;

import java.util.List;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepsViewHolder> {


    public interface CardClickListener{
        void OnCardClickListener(int currentIndex);
    }
    private CardClickListener mCardClickListener ;

    private List<Step> steps;
    private Context context;

    StepsAdapter(List<Step> steps, Context context , CardClickListener mCardClickListener) {
        this.steps = steps;
        this.context = context;
        this.mCardClickListener = mCardClickListener ;
    }

    @NonNull
    @Override
    public StepsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        StepsListItemBinding mStepsListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(context) ,
                R.layout.steps_list_item , parent , false);

        return new StepsViewHolder(mStepsListItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull StepsViewHolder holder, int position) {
        holder.stepsListItemBinding.setStep(steps.get(position));
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

     class StepsViewHolder extends RecyclerView.ViewHolder {

       private StepsListItemBinding stepsListItemBinding ;

        StepsViewHolder(@NonNull StepsListItemBinding stepsListItemBinding) {
            super(stepsListItemBinding.getRoot());
            this.stepsListItemBinding = stepsListItemBinding ;

            stepsListItemBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCardClickListener.OnCardClickListener(getAdapterPosition());
                }
            });
        }
    }
}
