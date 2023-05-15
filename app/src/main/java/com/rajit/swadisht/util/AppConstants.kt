package com.rajit.swadisht.util

import com.rajit.swadisht.R
import com.rajit.swadisht.data.model.OnboardingItem

// Contains all the AppConstants that are to be used inside the app
object AppConstants {

    /*
    *   Static List of `OnboardingITem`
    *   OnboardingItem is a data class under data -> model
    */
    val onboardingItems: List<OnboardingItem> = listOf(
        OnboardingItem(
            R.drawable.onboard1,
            "We're here for you",
            "Your hunger is our priority. Tasty and delicious dishes always at your disposal."
        ),

        OnboardingItem(
            R.drawable.onboard2,
            "Get your favourite dishes",
            "Get your favourite dishes anywhere, anytime at affordable rates. No more Cravings. Enjoy."
        ),

        OnboardingItem(
            R.drawable.onboard3,
            "IceCreams and Deserts",
            "Ice Creams and Deserts are your love. And we know that. Choose from a large variety of Deserts."
        ),

        OnboardingItem(
            R.drawable.onboard4,
            "Full Meals",
            "We have all types of meals to suit your mood and hunger level."
        )
    )

    /*
    *   Shared Preferences Key for storing FirstTimeUser Boolean
    *   This is used to determine whether to show Onboarding Screen or not
    */
    const val isFirstTimeUser: String = "IS_FIRST_TIME_USER"

}