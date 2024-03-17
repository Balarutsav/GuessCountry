package com.guesscountry.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.guesscountry.presentation.HomeScreen

@Composable
fun MainGraph(
    navController: NavHostController
) {

    NavHost(navController = navController, startDestination = Screen.Home.route) {

        composable(Screen.Home.route) {
            HomeScreen(onClickGuessHints = {

            }, onClickGuessTheCountry = {

            }, onClickAdvanceLevel = {

            }, onClickGuessTheFlag = {

            })
        }

    }
}