package com.forudacity.backingapp.view.steps.stepMain;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.forudacity.backingapp.AppRepository;
import com.forudacity.backingapp.model.Step;

import java.util.List;

 public class StepsListViewModel extends AndroidViewModel {

    private LiveData<List<Step>> stepListLiveData ;

     public StepsListViewModel(@NonNull Application application) {
        super(application);
         AppRepository appRepository = AppRepository.getInstance();
         stepListLiveData = appRepository.getSteps() ;
    }

    LiveData<List<Step>> getStepListLiveData() {
        return stepListLiveData;
    }
}
