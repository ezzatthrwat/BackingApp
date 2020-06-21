package com.forudacity.backingapp.view.steps.stepsDetails;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.forudacity.backingapp.R;
import com.forudacity.backingapp.databinding.FragmentStepDetailsBinding;
import com.forudacity.backingapp.model.Ingredient;
import com.forudacity.backingapp.model.StepsAndIngredient;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class StepDetailsFragment extends Fragment implements View.OnClickListener{

    private FragmentStepDetailsBinding mFragmentStepDetailsBinding ;
    private StepDetailsViewModel stepDetailsViewModel;

    private final String STEP_INDEX = "index";
    private final String ISTAPLAYOUT = "isTapLayout";
    private final String VIDEO_POSITION = "videoPosition";
    private final String VIDEO_WINDOW = "videoWindowIndex";
    private final String VIDEO_STATE = "videoState";
    private int StepIndex ;
    private int stepListSize ;
    private long currentVideoPosition ;
    private int currentWindowIndex ;
    private boolean currentPlayState = true;
    private boolean isTapLayout ;

    private SimpleExoPlayer mExoPlayer;
    private String userAgent;
    private MediaSource mediaSource ;

    public StepDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mFragmentStepDetailsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_step_details , container , false);

        if(savedInstanceState != null) {
            StepIndex = savedInstanceState.getInt(STEP_INDEX);
            isTapLayout = savedInstanceState.getBoolean(ISTAPLAYOUT);
            currentVideoPosition = savedInstanceState.getLong(VIDEO_POSITION);
            currentWindowIndex = savedInstanceState.getInt(VIDEO_WINDOW);
            currentPlayState = savedInstanceState.getBoolean(VIDEO_STATE);
        }

        if (isTapLayout){
            mFragmentStepDetailsBinding.nextStepButton.setVisibility(View.GONE);
            mFragmentStepDetailsBinding.previousButton.setVisibility(View.GONE);
        }else{
            mFragmentStepDetailsBinding.nextStepButton.setOnClickListener(this);
            mFragmentStepDetailsBinding.previousButton.setOnClickListener(this);
        }


        return mFragmentStepDetailsBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        stepListSize = StepsAndIngredient.getInstance().getStepsSize();
        stepDetailsViewModel = ViewModelProviders.of(this).get(StepDetailsViewModel.class);
        stepDetailsViewModel.getSteps(StepIndex).observe(requireActivity(), step -> {
            String uriString;
            if (step.getVideoURL().equals("")){
                uriString = step.getThumbnailURL();
            } else{
                uriString = step.getVideoURL();
            }

            mediaSource = buildMediaSource(Uri.parse(uriString));
            mExoPlayer.setPlayWhenReady(currentPlayState);
            mExoPlayer.seekTo(currentWindowIndex, currentVideoPosition);
            mExoPlayer.prepare(mediaSource, false, false);

            if (StepIndex == 0){
                List<Ingredient> ingredientList =  StepsAndIngredient.getInstance().getIngredients();
                for (Ingredient ingredients :ingredientList ) {
                    mFragmentStepDetailsBinding.StepDesTextView.append( ingredients.getQuantity()+ " " + ingredients.getMeasure() +" " +ingredients.getIngredient() + "\n");
                }
            } else {
                mFragmentStepDetailsBinding.StepDesTextView.setText(step.getDescription());
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.nextStepButton :
                if (StepIndex < stepListSize-1) {
                    StepIndex++;
                    stepDetailsViewModel.notifyStepByPosition(StepIndex);
                    currentPlayState = true ;
                    currentWindowIndex = 0 ;
                    currentVideoPosition = 0;
                }
                break;
            case R.id.previousButton:
                if (StepIndex > 0){
                    StepIndex--;
                    stepDetailsViewModel.notifyStepByPosition(StepIndex);
                    currentPlayState = true ;
                    currentWindowIndex = 0 ;
                    currentVideoPosition = 0;
                }
                break;
        }
    }

    public void setStepIndex(int CurrentIndex){
        this.StepIndex = CurrentIndex;
    }
    public void isTapLayout(boolean isTapLayout){
        this.isTapLayout = isTapLayout;
    }
    private void initializePlayer(){
        if (mExoPlayer == null) {
            DefaultTrackSelector trackSelector = new DefaultTrackSelector();
            trackSelector.setParameters(trackSelector.buildUponParameters().setMaxVideoSizeSd());
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(requireContext(), trackSelector);
            mFragmentStepDetailsBinding.mpPlayerView.setPlayer(mExoPlayer);
            userAgent = Util.getUserAgent(requireContext(), getString(R.string.app_name));
            if (mediaSource != null) {
                mExoPlayer.setPlayWhenReady(currentPlayState);
                mExoPlayer.seekTo(currentWindowIndex, currentVideoPosition);
                mExoPlayer.prepare(mediaSource, false, false);
            }
        }

    }

    private MediaSource buildMediaSource(Uri mediaUri){
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(requireActivity(), userAgent);
        return  new ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(mediaUri);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle currentState) {
        currentState.putInt(STEP_INDEX, StepIndex);
        currentState.putBoolean(ISTAPLAYOUT, isTapLayout);
        currentState.putLong(VIDEO_POSITION, currentVideoPosition);
        currentState.putInt(VIDEO_WINDOW, currentWindowIndex);
        currentState.putBoolean(VIDEO_STATE, currentPlayState);

    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT >= 24) {
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT < 24 || mExoPlayer == null)) {
            initializePlayer();
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT < 24) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT >= 24) {
            releasePlayer();
        }
    }


    private void releasePlayer() {
        if (mExoPlayer != null) {
            currentPlayState = mExoPlayer.getPlayWhenReady();
            currentVideoPosition = mExoPlayer.getCurrentPosition();
            currentWindowIndex = mExoPlayer.getCurrentWindowIndex();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

  /*  @Override
    public void onBackPressed() {

        if(mExoPlayer != null){
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null ;
        }

    }*/
}
