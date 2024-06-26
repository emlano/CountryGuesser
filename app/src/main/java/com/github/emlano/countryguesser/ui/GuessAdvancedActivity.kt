package com.github.emlano.countryguesser.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.emlano.countryguesser.R
import com.github.emlano.countryguesser.Result
import com.github.emlano.countryguesser.getFlagIdFromCountryCode
import com.github.emlano.countryguesser.readJsonAsset
import com.github.emlano.countryguesser.ui.theme.CountryGuesserTheme

class GuessAdvancedActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CountryGuesserTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GuessAdvanced(switch = intent.getBooleanExtra("switch", false))
                }
            }
        }
    }
}

@Composable
fun GuessAdvanced(switch: Boolean, modifier: Modifier = Modifier) {
    val state = rememberScrollState()

    val countries = readJsonAsset(LocalContext.current)
    var countryList by rememberSaveable { mutableStateOf(List(3) { _ -> countries.keys.random() }) }
    var countryNameList by rememberSaveable {
        mutableStateOf(List(3) {
            countries.getValue(
                countryList[it]
            )
        })
    }

    var firstAnswer by rememberSaveable { mutableStateOf("") }
    var secondAnswer by rememberSaveable { mutableStateOf("") }
    var thirdAnswer by rememberSaveable { mutableStateOf("") }

    var isFirstAnswerCorrect by rememberSaveable { mutableStateOf(Result.Ongoing) }
    var isSecondAnswerCorrect by rememberSaveable { mutableStateOf(Result.Ongoing) }
    var isThirdAnswerCorrect by rememberSaveable { mutableStateOf(Result.Ongoing) }

    var chances by rememberSaveable { mutableIntStateOf(3) }
    var finalResult by rememberSaveable { mutableStateOf(Result.Ongoing) }

    var gamesWon by rememberSaveable { mutableIntStateOf(0) }

    Column(
        modifier = modifier.verticalScroll(state),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(25.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.Top,
        ) {
            Box(
                modifier = modifier
                    .border(
                        width = 3.dp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(10.dp)
                    )
            ) {
                Text(
                    modifier = modifier.padding(10.dp),
                    text = stringResource(id = R.string.score)
                        .replace("\$points", gamesWon.toString()),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        }
        if (switch) {
            CountDownTimer(
                modifier = modifier,
                // Pass game state into the timer to stop it or pause it
                result = finalResult,
                // To repeat the timer on multiple attempts
                restart = chances > 0,
                // OnEnd executes a lambda after count down ends
                onEnd = {
                    isFirstAnswerCorrect = checkAnswer(firstAnswer, countryNameList[0])
                    isSecondAnswerCorrect = checkAnswer(secondAnswer, countryNameList[1])
                    isThirdAnswerCorrect = checkAnswer(thirdAnswer, countryNameList[2])

                    val resultCalculation = checkFinalResult(
                        isFirstAnswerCorrect,
                        isSecondAnswerCorrect,
                        isThirdAnswerCorrect
                    )

                    if (chances == 0 || resultCalculation == Result.Correct) {
                        finalResult = resultCalculation
                        gamesWon += getWinCount(
                            isFirstAnswerCorrect,
                            isSecondAnswerCorrect,
                            isThirdAnswerCorrect
                        )
                    } else chances--
                }
            )
        }
        HeaderText(text = R.string.advanced_mode)
        Box(
            modifier = modifier
        ) {
            LazyRow(
                modifier = modifier,
                contentPadding = PaddingValues(25.dp),
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                item {
                    FlagNameDisplay(
                        modifier = modifier,
                        value = firstAnswer,
                        enabled = isFirstAnswerCorrect != Result.Correct && finalResult == Result.Ongoing,
                        isError = isFirstAnswerCorrect == Result.Wrong,
                        flag = getFlagIdFromCountryCode(countryList[0]),
                        onChange = { value ->
                            firstAnswer = value
                            isFirstAnswerCorrect = Result.Ongoing
                        },
                        answer = if (
                            finalResult == Result.Wrong &&
                            isFirstAnswerCorrect == Result.Wrong
                        ) {
                            countryNameList[0]
                        } else {
                            ""
                        }
                    )
                }

                item {
                    FlagNameDisplay(
                        modifier = modifier,
                        value = secondAnswer,
                        enabled = isSecondAnswerCorrect != Result.Correct && finalResult == Result.Ongoing,
                        isError = isSecondAnswerCorrect == Result.Wrong,
                        flag = getFlagIdFromCountryCode(countryList[1]),
                        onChange = { value ->
                            secondAnswer = value
                            isSecondAnswerCorrect = Result.Ongoing
                        },
                        answer = if (
                            finalResult == Result.Wrong &&
                            isSecondAnswerCorrect == Result.Wrong
                        ) {
                            countryNameList[1]
                        } else {
                            ""
                        }
                    )
                }

                item {
                    FlagNameDisplay(
                        modifier = modifier,
                        value = thirdAnswer,
                        enabled = isThirdAnswerCorrect != Result.Correct && finalResult == Result.Ongoing,
                        isError = isThirdAnswerCorrect == Result.Wrong,
                        flag = getFlagIdFromCountryCode(countryList[2]),
                        onChange = { value ->
                            thirdAnswer = value
                            isThirdAnswerCorrect = Result.Ongoing
                        },
                        answer = if (
                            finalResult == Result.Wrong &&
                            isThirdAnswerCorrect == Result.Wrong
                        ) {
                            countryNameList[2]
                        } else {
                            ""
                        }
                    )
                }
            }
        }
        ResultText(result = finalResult)
        SubmitNextButton(
            result = finalResult,
            onClickSubmit = {
                isFirstAnswerCorrect = checkAnswer(firstAnswer, countryNameList[0])
                isSecondAnswerCorrect = checkAnswer(secondAnswer, countryNameList[1])
                isThirdAnswerCorrect = checkAnswer(thirdAnswer, countryNameList[2])

                val resultCalculation = checkFinalResult(
                    isFirstAnswerCorrect,
                    isSecondAnswerCorrect,
                    isThirdAnswerCorrect
                )

                if (chances == 1 || resultCalculation == Result.Correct) {
                    finalResult = resultCalculation
                    gamesWon += getWinCount(
                        isFirstAnswerCorrect,
                        isSecondAnswerCorrect,
                        isThirdAnswerCorrect
                    )
                } else chances--
            },
            onClickNext = {
                countryList = List(3) { _ -> countries.keys.random() }
                countryNameList = List(3) { countries.getValue(countryList[it]) }
                firstAnswer = ""
                secondAnswer = ""
                thirdAnswer = ""

                isFirstAnswerCorrect = Result.Ongoing
                isSecondAnswerCorrect = Result.Ongoing
                isThirdAnswerCorrect = Result.Ongoing

                chances = 3
                finalResult = Result.Ongoing
            })
    }
}

@Composable
fun FlagNameDisplay(
    value: String,
    flag: Int,
    enabled: Boolean,
    isError: Boolean,
    modifier: Modifier = Modifier,
    answer: String = "",
    onChange: (String) -> Unit
) {
    // Change colors depending on the answer given
    val disabledTextFieldColor = when (isError) {
        true -> MaterialTheme.colorScheme.error
        false -> MaterialTheme.colorScheme.onError
    }

    Column(
        modifier = modifier
            .width(350.dp)
            .height(425.dp)
            .border(
                width = 3.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(15.dp)
            )
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FlagHero(resource = flag)
        TextField(
            modifier = modifier.padding(bottom = 40.dp),
            value = value,
            label = { Text(text = stringResource(id = R.string.enter_country_name)) },
            enabled = enabled,
            isError = isError,
            colors = TextFieldDefaults.colors(
                disabledTextColor = disabledTextFieldColor,
                disabledLabelColor = disabledTextFieldColor,
                disabledIndicatorColor = disabledTextFieldColor,
                errorTextColor = MaterialTheme.colorScheme.error,
            ),
            onValueChange = onChange
        )

        if (answer.isNotEmpty()) {
            Text(
                text = stringResource(id = R.string.answer_was).replace("\$answer", answer),
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onSecondary,
                modifier = modifier.padding(bottom = 25.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview4() {
    CountryGuesserTheme {
        GuessAdvanced(switch = false)
    }
}

fun checkAnswer(answer: String, actual: String): Result {
    return if (answer.trim().equals(actual, ignoreCase = true)) Result.Correct else Result.Wrong
}

fun checkFinalResult(vararg args: Result): Result {
    return if (args.any { it != Result.Correct }) Result.Wrong else Result.Correct
}

fun getWinCount(vararg args: Result): Int {
    return args.count { it == Result.Correct }
}