package com.guesscountry.navigation


sealed class Screen(val route: String) {
    object Home : Screen("home")
    object GuessTheCountry : Screen("guessTheCountry")
    object GuessHints : Screen("GuessHints")
    object GuessFlags : Screen("GuessFlags")
    object AdvanceLevel : Screen("AdvanceLevel")

}