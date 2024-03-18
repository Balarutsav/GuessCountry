package com.guesscountry.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.guesscountry.R
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
            RoundedButton(onClick = onClickGuessTheCountry, text = stringResource(R.string.txt_guess_the_country))
            RoundedButton(onClick = onClickGuessHints, text = stringResource(R.string.txt_guess_hints))
            RoundedButton(onClick = onClickGuessTheFlag, text = stringResource(R.string.txt_guess_the_flag))
            RoundedButton(onClick = onClickAdvanceLevel, text = stringResource(R.string.txt_advanced_level))
        }
    }

}