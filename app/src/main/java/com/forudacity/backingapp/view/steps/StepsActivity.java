package com.forudacity.backingapp.view.steps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.forudacity.backingapp.R;
import com.forudacity.backingapp.view.steps.stepMain.StepsListFragment;
import com.forudacity.backingapp.view.steps.stepsDetails.StepDetailsFragment;
import com.forudacity.backingapp.view.steps.stepsDetails.ViewRecipeStepsActivity;

public class StepsActivity extends AppCompatActivity implements StepsListFragment.OnStepClickListener {

    private boolean isTabLayout;
    private FragmentManager fragmentManager ;
    private String TitleName ;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_steps);

        isTabLayout = findViewById(R.id.Divider) != null;
        initToolbar();
        if (savedInstanceState == null){
            startFragment();
        }
    }
    private void initToolbar() {
        intent = getIntent();
        if (intent.hasExtra(getString(R.string.RECIPES_NAME))){
            TitleName = intent.getStringExtra(getString(R.string.RECIPES_NAME)) ;
            setTitle(TitleName);
        }

        ActionBar actionBar = getSupportActionBar();
        if (actionBar !=null) actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void startFragment() {
        fragmentManager = getSupportFragmentManager() ;
        if (isTabLayout){
            StepDetailsFragment stepDetailsFragment = new StepDetailsFragment();
            stepDetailsFragment.isTapLayout(isTabLayout);
            stepDetailsFragment.setStepIndex(0);
            fragmentManager.beginTransaction()
                    .add(R.id.fragmentContainer, stepDetailsFragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return true;
    }

    @Override
    public void onStepSelected(int currentIndex) {
        if (isTabLayout){
            StepDetailsFragment stepDetailsFragment = new StepDetailsFragment();
            stepDetailsFragment.setStepIndex(currentIndex);
            stepDetailsFragment.isTapLayout(isTabLayout);
            fragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, stepDetailsFragment)
                    .commit();
        }else {
            Intent intent = new Intent(this, ViewRecipeStepsActivity.class);
            intent.putExtra(getString(R.string.CURRENT_STEP_INDEX) ,currentIndex);
            intent.putExtra(getString(R.string.RECIPES_NAME), TitleName);
            startActivity(intent);
        }
    }

}
