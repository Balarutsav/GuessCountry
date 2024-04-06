package com.guesscountry.presentation.home

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.guesscountry.R
import com.guesscountry.ui.components.RoundedButton
import com.guesscountry.utils.PreferenceManager
import com.guesscountry.utils.SharedPreferencesKey

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onClickGuessTheCountry: () -> Unit,
    onClickGuessHints: () -> Unit,
    onClickGuessTheFlag: () -> Unit,
    onClickAdvanceLevel: () -> Unit,

    ) {
    val context = LocalContext.current
    val preferenceManager = remember { PreferenceManager(context) }

    var isCountDownEnable by rememberSaveable {
        mutableStateOf(
            preferenceManager.getBoolean(
                SharedPreferencesKey.isCountDownTimerEnable,
                false
            )
        )
    }


    Scaffold { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text("CountDown Timer :")
                Spacer(modifier = Modifier.width(5.dp))
                Switch(
                    checked = isCountDownEnable, onCheckedChange = {
                        isCountDownEnable = it
                        preferenceManager.setBoolean(
                            SharedPreferencesKey.isCountDownTimerEnable,
                            it
                        )
                    }, modifier = Modifier.padding(start = 5.dp)
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                RoundedButton(
                    onClick = onClickGuessTheCountry,
                    text = stringResource(R.string.txt_guess_the_country)
                )
                RoundedButton(
                    onClick = onClickGuessTheFlag,
                    text = stringResource(R.string.txt_guess_the_flag)
                )

            }
        }
    }

}