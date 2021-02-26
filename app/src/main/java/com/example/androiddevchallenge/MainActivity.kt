/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.androiddevchallenge.model.Dog
import com.example.androiddevchallenge.model.ScreenState.DetailState
import com.example.androiddevchallenge.model.ScreenState.ListState
import com.example.androiddevchallenge.model.ScreenStateViewModel
import com.example.androiddevchallenge.ui.screen.DetailScreen
import com.example.androiddevchallenge.ui.screen.ListScreen
import com.example.androiddevchallenge.ui.theme.MyTheme

class MainActivity : AppCompatActivity() {

    private val screenStateViewModel by viewModels<ScreenStateViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
                MyApp(screenStateViewModel)
            }
        }
    }

    override fun onBackPressed() {
        if (!screenStateViewModel.onBack()) {
            super.onBackPressed()
        }
    }
}

// Start building your app here!
@Composable
fun MyApp(screenStateViewModel: ScreenStateViewModel) {
    val dogList = listOf(
        Dog(name = "dog1", description = "dog1 is blar blar blar"),
        Dog(name = "dog2", description = "dog2 is blar blar blar"),
        Dog(name = "dog3", description = "dog3 is blar blar blar"),
        Dog(name = "dog4", description = "dog4 is blar blar blar"),
    )

    val onClick: (Dog) -> Unit = { dog ->
        screenStateViewModel.navigateTo(DetailState(dog))
    }

    val screenState = screenStateViewModel.currentScreenState
    Surface(color = MaterialTheme.colors.background) {
        when (screenState) {
            ListState -> ListScreen(dogList, onClick)
            is DetailState -> DetailScreen(screenState)
        }
    }
}

@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
    MyTheme {
        MyApp(ScreenStateViewModel())
    }
}

@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun DarkPreview() {
    MyTheme(darkTheme = true) {
        MyApp(ScreenStateViewModel())
    }
}
