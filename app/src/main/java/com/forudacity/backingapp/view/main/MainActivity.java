package com.forudacity.backingapp.view.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.forudacity.backingapp.R;
import com.forudacity.backingapp.databinding.ActivityMainBinding;
import com.forudacity.backingapp.model.Recipes;
import com.forudacity.backingapp.model.StepsAndIngredient;
import com.forudacity.backingapp.view.steps.StepsActivity;
import com.forudacity.backingapp.widget.IngredientWidgetProvider;

import java.util.List;

public class MainActivity extends AppCompatActivity implements RecipesCardsAdapter.CardClickListener {

    ActivityMainBinding activityMainBinding ;

    private MainViewModel mainViewModel ;

    boolean isTabLayout ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this , R.layout.activity_main);
        setTitle(getString(R.string.BackingTime));

        initRecipesRecyclerView();
        initViewModel();
        getRecipesCards();
    }

    private void initRecipesRecyclerView() {

        if (activityMainBinding.BackingCardsGridRecyclerView != null) {
            activityMainBinding.BackingCardsGridRecyclerView.setHasFixedSize(true);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this , 3);
            activityMainBinding.BackingCardsGridRecyclerView.setLayoutManager(gridLayoutManager);
            isTabLayout = true ;
        } else if (activityMainBinding.BackingCardsRecyclerView != null){
            activityMainBinding.BackingCardsRecyclerView.setHasFixedSize(true);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            activityMainBinding.BackingCardsRecyclerView.setLayoutManager(linearLayoutManager);
            isTabLayout = false ;
        }
    }

    private void initViewModel() {
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
    }

    private void getRecipesCards() {
        activityMainBinding.RecipesProgressBar.setVisibility(View.VISIBLE);

        mainViewModel.getRecipesListLiveData().observe(this, new Observer<List<Recipes>>() {
            @Override
            public void onChanged(List<Recipes> recipes) {

                if (recipes != null && !recipes.isEmpty()){
                    RecipesCardsAdapter recipesCardsAdapter = new RecipesCardsAdapter(recipes , MainActivity.this , MainActivity.this);
                    if (isTabLayout){
                        if (activityMainBinding.BackingCardsGridRecyclerView != null) {
                            activityMainBinding.BackingCardsGridRecyclerView.setAdapter(recipesCardsAdapter);
                        }
                    } else {
                        if (activityMainBinding.BackingCardsRecyclerView != null) {
                            activityMainBinding.BackingCardsRecyclerView.setAdapter(recipesCardsAdapter);
                        }
                    }
                    activityMainBinding.RecipesProgressBar.setVisibility(View.GONE);
                }else{
                    activityMainBinding.RecipesProgressBar.setVisibility(View.GONE);
                    activityMainBinding.EmptyListTextView.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void OnCardClickListener(Recipes recipes) {

        StepsAndIngredient stepsAndIngredient =  StepsAndIngredient.getInstance();
        stepsAndIngredient.setIngredients(recipes.getIngredients());
        stepsAndIngredient.setSteps(recipes.getSteps());

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, IngredientWidgetProvider.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.IngredientListView);
        IngredientWidgetProvider.updatePlantWidgets(this,appWidgetManager, appWidgetIds);

        Intent intent = new Intent(this , StepsActivity.class);
        intent.putExtra(getString(R.string.RECIPES_NAME) ,recipes.getName());
        startActivity(intent);
    }
}
