package com.guesscountry.presentation.guessTheHint

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.guesscountry.R
import com.guesscountry.ui.components.CustomTopAppBar
import java.util.Locale


@Composable
fun GuessTheHint(
    guessTheHintViewModel: GuessTheHintViewModel = hiltViewModel(), onClickBack: () -> Unit
) {
    val TAG = "Guess The Hint"

    val randomCountry = guessTheHintViewModel.getSavedRandomCountry()


    val guessedCountryName = remember { mutableStateListOf<String>() }


    guessTheHintViewModel.getSavedRandomCountry()?.name?.toList()?.map { " " }?.let { nameList ->
        guessedCountryName.clear()
        guessedCountryName.addAll(nameList)
    }
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
                    var focusRequester by remember { mutableStateOf(FocusRequester()) }

                    TextField(value = guessedCountryName.get(index),
                        onValueChange = {
                            guessedCountryName[index] = it

                        },
                        modifier = Modifier
                            .padding(10.dp)
                            .size(50.dp),
                        textStyle = TextStyle(Color.Black),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Next
                        ))
                }
            }

        }

    }
}

@Preview(showBackground = true)
@Composable
fun GuessTheHintPreview() {
    GuessTheHint(onClickBack = {})
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
