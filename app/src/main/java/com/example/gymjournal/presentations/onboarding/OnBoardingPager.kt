package com.example.gymjournal.presentations.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.gymjournal.R
import com.example.gymjournal.core.constant.Routes
import kotlinx.coroutines.launch
@Composable
fun OnboardingPager(
    navController: NavController,
    viewModel: OnboardingViewModel = hiltViewModel()
) {
    val pages = listOf<@Composable () -> Unit>(
        { OnboardingPageOneContent() },
        { OnboardingPageTwoContent() },
        { OnboardingPageThreeContent() }
    )

    val pagerState = rememberPagerState(pageCount = { pages.size })
    val coroutineScope = rememberCoroutineScope()

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Pager
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) { page -> pages[page]() }

            Spacer(modifier = Modifier.height(16.dp))

            // Dots indicator
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(pages.size) { index ->
                    val color = if (pagerState.currentPage == index)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)

                    Box(
                        modifier = Modifier
                            .padding(4.dp)
                            .size(10.dp)
                            .background(color, shape = MaterialTheme.shapes.small)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Button
            Button(
                onClick = {
                    if (pagerState.currentPage < pages.lastIndex) {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    } else {
                        // ✅ Save onboarding completed
                        viewModel.saveOnboardingState(true)

                        // ✅ Navigate to AUTH, removing onboarding from backstack
                        navController.navigate(Routes.AUTH) {
                            popUpTo(Routes.ONBOARDING) {
                                inclusive = true
                            }
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    if (pagerState.currentPage == pages.lastIndex) "Get Started"
                    else "Next"
                )
            }
        }
    }
}

@Composable
fun OnboardingPageOneContent() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(id = R.drawable.gymjournal),
            contentDescription = "Logo",
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 32.dp)
        )
        Text("Welcome to Gym Journal", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(12.dp))
        Text("Track your workouts and stay motivated with personalized routines.")
    }
}

@Composable
fun OnboardingPageTwoContent() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Set Your Goals", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(12.dp))
        Text("Create custom fitness goals and track your progress.")
    }
}

@Composable
fun OnboardingPageThreeContent() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Stay Consistent", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(12.dp))
        Text("stay on track with your plans.")
    }
}
