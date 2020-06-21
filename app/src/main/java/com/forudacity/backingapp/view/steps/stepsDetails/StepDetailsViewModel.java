package com.forudacity.backingapp.view.steps.stepsDetails;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.forudacity.backingapp.AppRepository;
import com.forudacity.backingapp.model.Step;

public class StepDetailsViewModel extends AndroidViewModel {

    private LiveData<Step> step ;
    private AppRepository appRepository;
    public StepDetailsViewModel(@NonNull Application application) {
        super(application);
         appRepository = AppRepository.getInstance();

    }

    LiveData<Step> getSteps(int Index){
        step = appRepository.getStepObject(Index);
        return step;
    }

    void notifyStepByPosition(int Index){
        step = appRepository.getStepObject(Index);
    }
}
