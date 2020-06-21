package com.forudacity.backingapp.view.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.forudacity.backingapp.AppRepository;
import com.forudacity.backingapp.model.Recipes;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private LiveData<List<Recipes>> recipesListLiveData ;

    public MainViewModel(@NonNull Application application) {
        super(application);

        AppRepository appRepository = AppRepository.getInstance();
        recipesListLiveData = appRepository.getRecipesCards();
    }

    LiveData<List<Recipes>> getRecipesListLiveData(){
        return recipesListLiveData ;
    }
}
