package com.blkxltng.sunset;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;

/**
 * Created by firej on 12/12/2017.
 */

public class SunsetFragment extends Fragment {

    private View mSceneView;
    private View mSunView;
    private View mSunReflectView;
    private View mSkyView;
    private View mSeaView;

    private int mBlueSkyColor;
    private int mSunsetSkyColor;
    private int mNightSkyColor;

    private boolean isSunset = false;

    public static SunsetFragment newInstance() {
        return new SunsetFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sunset, container, false);
        mSceneView = view;
        mSunView = view.findViewById(R.id.sun);
        mSunReflectView = view.findViewById(R.id.sun_reflect);
        mSkyView = view.findViewById(R.id.sky);
        mSeaView = view.findViewById(R.id.sea);

        Resources resources = getResources();
        mBlueSkyColor = resources.getColor(R.color.blue_sky);
        mSunsetSkyColor = resources.getColor(R.color.sunset_sky);
        mNightSkyColor = resources.getColor(R.color.night_sky);

        mSceneView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isSunset == false) {
                    startAnimation();
                    isSunset = true;
                } else {
                    reverseAnimation();
                    isSunset = false;
                }
            }
        });

        return view;
    }

    private void startAnimation() {
        float sunYStart = mSunView.getTop();
        float sunYEnd = mSkyView.getHeight();

        float sunReflectYStart = mSunReflectView.getTop();
        float sunReflectYEnd = 0.0f - mSunReflectView.getBottom();

        ObjectAnimator heightAnimator = ObjectAnimator
                .ofFloat(mSunView, "y", sunYStart, sunYEnd)
                .setDuration(3000);
        heightAnimator.setInterpolator(new AccelerateInterpolator());

        ObjectAnimator heightAnimatorReflect = ObjectAnimator
                .ofFloat(mSunReflectView, "y", sunReflectYStart, sunReflectYEnd)
                .setDuration(3000);
        heightAnimatorReflect.setInterpolator(new AccelerateInterpolator());

        ObjectAnimator sunsetSkyAnimator = ObjectAnimator
                .ofInt(mSkyView, "backgroundColor", mBlueSkyColor, mSunsetSkyColor)
                .setDuration(3000);
        sunsetSkyAnimator.setEvaluator(new ArgbEvaluator());

        ObjectAnimator nightSkyAnimator = ObjectAnimator
                .ofInt(mSkyView, "backgroundColor", mSunsetSkyColor, mNightSkyColor)
                .setDuration(1500);
        nightSkyAnimator.setEvaluator(new ArgbEvaluator());

        ObjectAnimator heatAnimator = ObjectAnimator.ofPropertyValuesHolder(mSunView,
                PropertyValuesHolder.ofFloat("scaleX", 1.1f)
                , PropertyValuesHolder.ofFloat("scaleY", 1.1f))
                .setDuration(1000);
        heatAnimator.setRepeatCount(ObjectAnimator.INFINITE);
        heatAnimator.setInterpolator(new AccelerateDecelerateInterpolator());

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet
                .play(heightAnimator)
                .with(heightAnimatorReflect)
                .with(sunsetSkyAnimator)
                .with(heatAnimator)
                .before(nightSkyAnimator);
        animatorSet.start();
    }

    private void reverseAnimation() {
        float sunYStart = mSkyView.getHeight();
        float sunYEnd = mSunView.getTop();

        float sunReflectYStart = 0.0f - mSunReflectView.getBottom();
        float sunReflectYEnd = mSunReflectView.getTop();

        ObjectAnimator heightAnimator = ObjectAnimator
                .ofFloat(mSunView, "y", sunYStart, sunYEnd)
                .setDuration(3000);
        heightAnimator.setInterpolator(new AccelerateInterpolator());

        ObjectAnimator heightAnimatorReflect = ObjectAnimator
                .ofFloat(mSunReflectView, "y", sunReflectYStart, sunReflectYEnd)
                .setDuration(3000);
        heightAnimatorReflect.setInterpolator(new AccelerateInterpolator());

        ObjectAnimator sunsetSkyAnimator = ObjectAnimator
                .ofInt(mSkyView, "backgroundColor", mSunsetSkyColor, mBlueSkyColor)
                .setDuration(3000);
        sunsetSkyAnimator.setEvaluator(new ArgbEvaluator());

        ObjectAnimator nightSkyAnimator = ObjectAnimator
                .ofInt(mSkyView, "backgroundColor", mNightSkyColor, mSunsetSkyColor)
                .setDuration(1500);
        nightSkyAnimator.setEvaluator(new ArgbEvaluator());

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet
                .play(heightAnimator)
                .with(heightAnimatorReflect)
                .with(sunsetSkyAnimator)
                .after(nightSkyAnimator);
        animatorSet.start();
    }
}
