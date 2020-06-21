package com.forudacity.backingapp;

import android.os.AsyncTask;

import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.forudacity.backingapp.model.Recipes;
import com.forudacity.backingapp.model.Step;
import com.forudacity.backingapp.model.StepsAndIngredient;
import com.forudacity.backingapp.utils.EspressoIdlingResource;
import com.forudacity.backingapp.utils.JsonUtils;
import com.forudacity.backingapp.utils.NetWorkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class AppRepository {

    private static AppRepository ourInstance;
    private MutableLiveData<List<Recipes>> recipesMutableLiveData = new MutableLiveData<>();

    @VisibleForTesting
    private void IncrementIdling(){EspressoIdlingResource.increment();}
    @VisibleForTesting
    private void DecrementIdling(){EspressoIdlingResource.decrement();}

    private AppRepository(){
    }

    public static AppRepository getInstance() {
        if (ourInstance == null) {
            ourInstance = new AppRepository();
        }
        return ourInstance;
    }

    public LiveData<List<Recipes>> getRecipesCards(){
        new FetchRecipesData().execute(NetWorkUtils.myCallingUrl());
        return recipesMutableLiveData ;
    }

    private class FetchRecipesData extends AsyncTask<URL,Void, List<Recipes>>{

        @Override
        protected void onPreExecute() {
            IncrementIdling();
        }

        @Override
        protected List<Recipes> doInBackground(URL... urls) {


            if (urls.length == 0){
                return  null;
            }
            URL Url = urls[0];
            String recipesJSON = null;
            try {
                recipesJSON = NetWorkUtils.getResponseFromHttpUrl(Url);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (recipesJSON != null) {
                return JsonUtils.parseRecipesJsonToPojo(recipesJSON);
            }else{
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Recipes> recipes) {

            if (recipes != null){
                recipesMutableLiveData.setValue(recipes);
            }

            DecrementIdling();
        }
    }

    /*
        ----------------------------------- Steps ---------------------------------------
    */
    private MutableLiveData<List<Step>> stepMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Step> oneStepMutableLiveData = new MutableLiveData<>();
    private StepsAndIngredient stepsAndIngredient =  StepsAndIngredient.getInstance();

    public MutableLiveData<List<Step>> getSteps(){
        stepMutableLiveData.setValue(stepsAndIngredient.getSteps());
        return stepMutableLiveData;
    }

    public MutableLiveData<Step> getStepObject(int index){
        oneStepMutableLiveData.setValue(stepsAndIngredient.getSteps().get(index));
        return  oneStepMutableLiveData;
    }

}
