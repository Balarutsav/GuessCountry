package com.guesscountry.presentation.GuessTheFlagScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.guesscountry.R
import com.guesscountry.ui.components.CustomTopAppBar
import kotlinx.coroutines.delay
import java.util.Locale


@Composable
fun GuessTheFlagScreen(
    viewModel: GuessTheFlagViewModel = hiltViewModel(),
    onClickBack: () -> Unit
) {


    var showMessage by rememberSaveable { mutableStateOf(false) }
    val countryList = viewModel.countryList.value
    val currentCountry = viewModel.getSavedRandomCountry()

    var buttonText by rememberSaveable { mutableStateOf("Submit") }

    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(R.string.txt_guess_the_flag),
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onClickBack) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = stringResource(R.string.description_back_arrow)
                        )
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(5.dp).fillMaxSize(),

            horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
        ) {
            // Text box to select country flag from country name
            Text(
                text = "Select country flag for ${viewModel.getSavedRandomCountry()?.name}",
                modifier = Modifier.padding(8.dp)
            )

            // Display 3 flags horizontally
            LazyRow(
                modifier = Modifier.padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(countryList) { country ->
                    CountryImage(
                        country.code.lowercase(Locale.getDefault()),
                        selected = viewModel.selectedCountryName == country.name,
                        onClick = {
                            if (buttonText.equals("Submit")) {
                                viewModel.selectedCountryName = country.name
                            }

                        }
                    )
                }
            }

            // Submit button
            Button(
                onClick = {
                    if (buttonText.equals("Submit")) {
                        if (viewModel.selectedCountryName.isNotBlank()) {
                            showMessage = true
                            buttonText = "Next"
                        }else{
                            showMessage = true

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
                    containerColor = if (viewModel.selectedCountryName == currentCountry?.name) {
                        Color(0xFF4CAF50) // Green color for correct answer
                    }else if (viewModel.selectedCountryName.isEmpty()){
                        MaterialTheme.colorScheme.primary
                    }  else {
                        Color(0xFFF44336) // Red color for wrong answer
                    }
                ) {
                    val styleText = if (viewModel.selectedCountryName.isEmpty()){
                        "Please select country flag."
                    }else if (viewModel.selectedCountryName == currentCountry?.name) {
                        "CORRECT!"
                    } else {
                        "WRONG!"
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
    code: String, modifier: Modifier = Modifier, selected: Boolean, onClick: () -> Unit
) {
    val imageUrl = "file:///android_asset/countryFlags/$code.png"
    val painter = rememberAsyncImagePainter(
        ImageRequest.Builder(LocalContext.current).data(data = imageUrl)
            .apply(block = fun ImageRequest.Builder.() {

            }).build()
    )

    Card(
        modifier = Modifier
            .size(100.dp)
            .clickable(onClick = onClick)
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp), // Adjust the corner radius as needed
        border = BorderStroke(
            2.dp,
            if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )
        }
    }
}