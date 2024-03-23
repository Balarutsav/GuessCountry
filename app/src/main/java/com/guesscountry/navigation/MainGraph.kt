package com.guesscountry.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.guesscountry.presentation.GuessTheFlagScreen.GuessTheFlagScreen
import com.guesscountry.presentation.guessTheCountry.GuessTheCountryScreen
import com.guesscountry.presentation.home.HomeScreen

@Composable
fun MainGraph(
    navController: NavHostController
) {

    NavHost(navController = navController, startDestination = Screen.Home.route) {

        composable(Screen.Home.route) {
            HomeScreen(onClickGuessHints = {

            }, onClickGuessTheCountry = {
                navController.navigate(Screen.GuessTheCountry.route) {}

            }, onClickAdvanceLevel = {

            }, onClickGuessTheFlag = {
                navController.navigate(Screen.GuessFlags.route)

            })
        }
        composable(Screen.GuessTheCountry.route){

            GuessTheCountryScreen(onClickBack =  {
                navController.navigate(Screen.Home.route) {
                    popUpTo(0)

                }

            })
        }

        composable(Screen.GuessFlags.route){

            GuessTheFlagScreen(onClickBack =  {
                navController.navigate(Screen.Home.route) {
                    popUpTo(0)

                }

            })
        }
    }

}
