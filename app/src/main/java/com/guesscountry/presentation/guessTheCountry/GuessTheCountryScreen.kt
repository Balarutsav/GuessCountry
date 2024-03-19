package com.guesscountry.presentation.guessTheCountry

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.guesscountry.R
import com.guesscountry.ui.components.CustomTopAppBar
import kotlinx.coroutines.delay
import java.util.Locale


@Composable
fun GuessTheCountryScreen(
    viewModel: GuessTheCountryViewModel = hiltViewModel(), onClickBack: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.getCountryList()
    }

    var showMessage by rememberSaveable { mutableStateOf(false) }
    val countryList = viewModel.countryList.value
    val randomCountry = viewModel.getSavedRandomCountry()

    var buttonText by rememberSaveable { mutableStateOf("Submit") }

    Scaffold(topBar = {
        CustomTopAppBar(title = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.txt_guess_the_country),
            )
        }, navigationIcon = {
            IconButton(onClick = onClickBack) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = stringResource(R.string.description_back_arrow)
                )
            }
        })
    }) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(5.dp),

            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            CountryImage(
                (randomCountry?.code ?: "").lowercase(Locale.getDefault()),
                modifier = Modifier.size(105.dp) // Set the size as per your requirement
            )
            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(countryList) { country ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = country.name == viewModel.selectedCountryName, onClick = {
                                viewModel.selectedCountryName = country.name
                            }, modifier = Modifier.padding(end = 8.dp)
                        )


                        Box(modifier = Modifier
                            .weight(1f)
                            .clickable {
                                viewModel.selectedCountryName = country.name
                            }) {
                            Text(

                                text = country.name, modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                }
            }

            Button(
                onClick = {
                    if (buttonText.equals("Submit")) {
                        if (viewModel.selectedCountryName.isNotBlank()) {
                            showMessage = true
                            buttonText = "Next"
                        }
                    } else {
                        buttonText = "Submit"
                        viewModel.reset()

                    }
                }, modifier = Modifier.padding(16.dp)
            ) {
                Text(text = buttonText)
            }

            // Show message if needed
            if (showMessage) {
                Snackbar(
                    modifier = Modifier.padding(16.dp),
                    containerColor = if (viewModel.selectedCountryName == randomCountry?.name) {
                        Color(0xFF4CAF50) // Green color for correct answer
                    } else {
                        Color(0xFFF44336) // Red color for wrong answer
                    }
                ) {
                    val styleText = if (viewModel.selectedCountryName == randomCountry?.name) {
                        buildAnnotatedString {
                            append("CORRECT!")
                        }
                    } else {
                        buildAnnotatedString {
                            append("WRONG! The correct country is ")
                            pushStyle(SpanStyle(color = Color.Blue))
                            append(randomCountry?.name ?: "")
                            pop()
                        }

                    }

                    Text(text = styleText, color = Color.White)
                    LaunchedEffect(Unit) {
                        delay(1000) // Delay for 1 second (1000 milliseconds)
                        showMessage = false // Dismiss the Snackbar
                    }
                }
            }
        }
    }


}

@Composable
fun CountryImage(
    code: String, modifier: Modifier = Modifier
) {
    val imageUrl = "file:///android_asset/countryFlags/$code.png"
    val painter = rememberAsyncImagePainter(
        ImageRequest.Builder(LocalContext.current).data(data = imageUrl)
            .build()
    )

    Image(
        painter = painter, contentDescription = null, modifier = modifier
    )
}