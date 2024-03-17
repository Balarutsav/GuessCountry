package com.guesscountry.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.guesscountry.R
import com.guesscountry.ui.components.CustomTopAppBar
import com.guesscountry.ui.components.RoundedButton

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onClickGuessTheCountry: () -> Unit,
    onClickGuessHints: () -> Unit,
    onClickGuessTheFlag: () -> Unit,
    onClickAdvanceLevel: () -> Unit,

    ) {


    Scaffold() { padding ->
        Column(        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
        ) {
            RoundedButton(onClick = onClickGuessTheCountry, text = "Guess the Country")
            RoundedButton(onClick = onClickGuessHints, text = "Guess-Hints")
            RoundedButton(onClick = onClickGuessTheFlag, text = "Guess the Flag")
            RoundedButton(onClick = onClickAdvanceLevel, text = "Advanced Level")
        }
    }

}