package com.rajit.swadisht.data.model

import androidx.annotation.DrawableRes

// Data Class for Onboarding Screens
data class OnboardingItem(
    @DrawableRes val onboardingImage: Int,
    val onboardingTitle: String,
    val onboardingDescription: String
)
