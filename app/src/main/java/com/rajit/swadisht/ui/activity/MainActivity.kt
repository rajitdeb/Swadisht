package com.rajit.swadisht.ui.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ContentInfoCompat.Flags
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.rajit.swadisht.R
import com.rajit.swadisht.adapter.OnboardingAdapter
import com.rajit.swadisht.databinding.ActivityMainBinding
import com.rajit.swadisht.util.AppConstants

// First Entry Point of the App
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mAdapter: OnboardingAdapter by lazy { OnboardingAdapter(AppConstants.onboardingItems) }
    private var isFirstTimeUser: Boolean = true
    private var dots = mutableListOf<TextView>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*
        *   installing the Splash Screen
        *   Used from SplashScreen API
        */
        installSplashScreen()

        // using ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Check for preferences if the app is used for the First Time
        val sharedPref = getSharedPreferences(AppConstants.isFirstTimeUser, Context.MODE_PRIVATE)
        isFirstTimeUser = sharedPref.getBoolean(AppConstants.isFirstTimeUser, true)

        /*
        *   If it is not the First time the app is used
        *   Don't show Onboarding Activity
        */
        if (!isFirstTimeUser)
            moveToNextActivity()

        // Onboarding ViewPager Setup
        binding.onboardingViewPager.adapter = mAdapter

        // setting up page indicator in onCreate() with default values
        setupDotsIndicator()

        // Setting Previous, Next and Finish button based on Page Position
        binding.onboardingViewPager.registerOnPageChangeCallback(object : OnPageChangeCallback() {

            override fun onPageSelected(position: Int) {

                setupDotsIndicator(position)

                when (position) {
                    0 -> {
                        setPrevEnabled(false)
                        setNextEnabled(true)
                        setFinishVisibility(false)
                    }

                    AppConstants.onboardingItems.size - 1 -> {
                        setPrevEnabled(true)
                        setNextEnabled(false)
                        setFinishVisibility(true)
                    }

                    else -> {
                        setPrevEnabled(true)
                        setNextEnabled(true)
                        setFinishVisibility(false)
                    }
                }
            }
        })


        /*
        *   Handling Previous Button Click Event
        *   Going back to previous page on button click
        */
        binding.prevBtn.setOnClickListener {

            binding.apply {
                onboardingViewPager.setCurrentItem(
                    onboardingViewPager.currentItem - 1,
                    true
                )
            }
        }

        /*
        *   Handling Next Button Click Event
        *   Going to next page on button click
        */
        binding.nextBtn.setOnClickListener {

            binding.apply {
                onboardingViewPager.setCurrentItem(
                    onboardingViewPager.currentItem + 1,
                    true
                )
            }
        }

        /*
        *   Handling Finish Button Click Event
        *   Setting isFirstTimeUser to False and storing it in SharedPreferences
        *   Then, Moving to next activity
        */
        binding.finishBtn.setOnClickListener {

            with(sharedPref.edit()) {
                putBoolean(AppConstants.isFirstTimeUser, false)
                apply()
            }

            moveToNextActivity()

        }

    }

    /*
    *   Creating Custom Page Indicator for Onboarding Screens
    *   @param `currentPosition` used to update the dots indicator to show the current screen
    *   @param `totalSlideSize` used to determine the total number of dots to create
    *
    *   DEFAULT VALUE:
    *   @param `currentPosition` has a default value of 0, i.e., first screen
    *   @param `totalSlideSize` takes a default value from AppConstants file under utils
    */
    private fun setupDotsIndicator(
        currentPosition: Int = 0,
        totalSlideSize: Int = AppConstants.onboardingItems.size
    ) {

        binding.dotsLayout.removeAllViews()

        for (i in 0 until totalSlideSize) {
            dots.add(TextView(this))
            dots[i].text = Html.fromHtml("&#8226;")
            dots[i].textSize = 35F
            dots[i].setTextColor(resources.getColor(android.R.color.darker_gray))

            binding.dotsLayout.addView(dots[i])
        }

        if (dots.size > 0)
            dots[currentPosition].setTextColor(resources.getColor(R.color.purple_700))

    }

    /*
    *   Enabling / Disabling Previous Button based on:
    *   @param `enable` condition
    */
    fun setPrevEnabled(enable: Boolean) {
        binding.prevBtn.isEnabled = enable
    }

    /*
    *   Enabling / Disabling Next Button based on:
    *   @param `enable` condition
    */
    fun setNextEnabled(enable: Boolean) {
        binding.nextBtn.isEnabled = enable
    }

    /*
    *   Setting Visibility of Finish Button based on:
    *   @param `visible` condition
    */
    fun setFinishVisibility(visible: Boolean) {
        if (visible)
            binding.finishBtn.visibility = View.VISIBLE
        else
            binding.finishBtn.visibility = View.GONE
    }

    /*
    *   Function to move to next activity
    *   And remove the MainActivity, which is the OnboardingActivity from Navigation Stack
    */
    private fun moveToNextActivity() {
        startActivity(Intent(this, HomeActivity::class.java))
        finishAffinity()
    }
}