package com.guesscountry.presentation.guessTheHint

import android.widget.EditText
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.guesscountry.R
import com.guesscountry.ui.components.CustomTopAppBar
import java.util.Locale


@Composable
fun GuessTheHint(
    guessTheHintViewModel: GuessTheHintViewModel = hiltViewModel(), onClickBack: () -> Unit
) {
    val randomCountry = guessTheHintViewModel.getSavedRandomCountry()

    Scaffold(topBar = {
        CustomTopAppBar(title = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.txt_guess_hints),
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
                .padding(5.dp)
                .fillMaxWidth(),

            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            CountryImage(
                (randomCountry?.code ?: "").lowercase(Locale.getDefault()),
                modifier = Modifier.size(200.dp)
            )

            LazyRow {
                itemsIndexed(randomCountry?.name?.toList() ?: emptyList()) { index, countryName ->

                    TextField( guessTheHintViewModel.guessedCountryName.value.get(index),
                        modifier = Modifier
                            .padding(10.dp)
                            .size(30.dp),
                        onValueChange = {

                            guessTheHintViewModel.     guessedCountryName.value.set(index, it)

                        })
                }
            }

        }

    }
}

@Preview(showBackground = true)
@Composable
fun GuessTheHintPreview() {
    GuessTheHint( // Provide a mock view model here if needed
        onClickBack = {})
}

@Composable
fun CountryImage(
    code: String, modifier: Modifier = Modifier
) {
    val imageUrl = "file:///android_asset/countryFlags/$code.png"
    val painter = rememberAsyncImagePainter(
        ImageRequest.Builder(LocalContext.current).data(data = imageUrl).build()
    )

    Image(
        painter = painter, contentDescription = null, modifier = modifier
    )
}
