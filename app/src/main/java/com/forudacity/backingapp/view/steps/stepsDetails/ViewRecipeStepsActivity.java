package com.forudacity.backingapp.view.steps.stepsDetails;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import com.forudacity.backingapp.R;

public class ViewRecipeStepsActivity extends AppCompatActivity {

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         DataBindingUtil.setContentView(this, R.layout.activity_view_recipe_steps);

        intent = getIntent();
        initToolbar();
        if (savedInstanceState == null){
            starFragment();
        }
    }
    private void initToolbar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar !=null) actionBar.setDisplayHomeAsUpEnabled(true);
        if (intent.hasExtra(getString(R.string.RECIPES_NAME))){
            setTitle(intent.getStringExtra(getString(R.string.RECIPES_NAME)));
        }
    }

    private void starFragment() {

        FragmentManager fragmentManager = getSupportFragmentManager();

        int StepIndex = 0;
        if (intent.hasExtra(getString(R.string.CURRENT_STEP_INDEX))) {
            StepIndex = intent.getIntExtra(getString(R.string.CURRENT_STEP_INDEX), 0);
        }

        StepDetailsFragment stepDetailsFragment = new StepDetailsFragment();
        stepDetailsFragment.setStepIndex(StepIndex);
        fragmentManager.beginTransaction()
                .add(R.id.RecipeStepViewContainer, stepDetailsFragment)
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return true;
    }

}

