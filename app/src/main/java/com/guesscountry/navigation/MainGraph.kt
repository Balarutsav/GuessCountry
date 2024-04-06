package com.guesscountry.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.guesscountry.presentation.guessTheFlagScreen.GuessTheFlagScreen
import com.guesscountry.presentation.guessTheCountry.GuessTheCountryScreen
import com.guesscountry.presentation.guessTheHint.GuessTheHint
import com.guesscountry.presentation.home.HomeScreen

@Composable
fun MainGraph(
    navController: NavHostController, applicationContext: Context
) {

    NavHost(navController = navController, startDestination = Screen.Home.route) {

        composable(Screen.Home.route) {
            HomeScreen(onClickGuessHints = {
                navController.navigate(Screen.GuessHints.route)

            }, onClickGuessTheCountry = {
                navController.navigate(Screen.GuessTheCountry.route) {}

            }, onClickAdvanceLevel = {

            }, onClickGuessTheFlag = {
                navController.navigate(Screen.GuessFlags.route)

            })
        }

        composable(Screen.GuessHints.route) {
            GuessTheHint {
                navController.navigate(Screen.Home.route) {
                    popUpTo(0)

                }
            }
        }

        composable(Screen.GuessTheCountry.route) {

            GuessTheCountryScreen(onClickBack = {
                navController.navigate(Screen.Home.route) {
                    popUpTo(0)

                }

            })
        }

        composable(Screen.GuessFlags.route) {

            GuessTheFlagScreen(context = applicationContext, onClickBack = {
                navController.navigate(Screen.Home.route) {
                    popUpTo(0)

                }

            })
        }
    }

}
