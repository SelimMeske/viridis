package com.viridis

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.google.android.gms.auth.api.identity.Identity
import com.viridis.ui.BottomBarItem
import com.viridis.ui.auth.GoogleAuthUiClient
import com.viridis.ui.eco_tracker.EcoTrackerScreen
import com.viridis.ui.home.HomeScreen
import com.viridis.ui.news.NewsDetailScreen
import com.viridis.ui.news.NewsScreen
import com.viridis.ui.news.NewsViewModel
import com.viridis.ui.pollution.PollutionScreen
import com.viridis.ui.pollution.PollutionViewModel
import com.viridis.ui.profile.ProfileScreen
import com.viridis.ui.auth.SignInScreen
import com.viridis.ui.auth.SignInViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Locale

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val memberId = googleAuthUiClient.getSignedInUser()?.userId

            val bottomBarItems = listOf(
                BottomBarItem(R.string.home, R.drawable.baseline_home_24, "home"),
                BottomBarItem(R.string.news, R.drawable.baseline_newspaper_24, "news"),
                BottomBarItem(R.string.tracker, R.drawable.baseline_checklist_24, "ecoTracker/$memberId"),
                BottomBarItem(R.string.profile, R.drawable.baseline_person_24, "profile")
            )

            val bottomBarDestinations =
                listOf("home", "newsScreen", "ecoTracker", "footprint", "profile")
            var showBottomBar by rememberSaveable { mutableStateOf(false) }

            showBottomBar = when (navBackStackEntry?.destination?.route) {
                "sign_in" -> false
                else -> true
            }

            var canNavigateBack by remember { mutableStateOf(false) }

            DisposableEffect(navController) {
                val callback = NavController.OnDestinationChangedListener { navC, destination, _ ->
                    canNavigateBack = !(bottomBarDestinations.contains(destination.route))
                }
                navController.addOnDestinationChangedListener(callback)
                onDispose {
                    navController.removeOnDestinationChangedListener(callback)
                }
            }

            Scaffold(
                topBar = {
                    if (showBottomBar) {
                        TopAppBar(title = { Text("Viridis") },
                            navigationIcon = {
                                if (canNavigateBack) {
                                    IconButton({ navController.popBackStack() }) {
                                        Icon(
                                            imageVector = Icons.Default.ArrowBack,
                                            contentDescription = null
                                        )
                                    }
                                }
                            })
                    }
                },

                bottomBar = {
                    if (showBottomBar) {
                        BottomNavigationBar(
                            items = bottomBarItems,
                            navController = navController
                        )
                    }
                }) { innerPadding ->
                Box(
                    Modifier
                        .fillMaxSize()
                        .background(Color.White)
                        .padding(innerPadding)
                ) {
                    val newsViewModel = viewModel<NewsViewModel>()
                    val newsState = newsViewModel.newsState.collectAsStateWithLifecycle()

                    val pollutionViewModel = viewModel<PollutionViewModel>()
                    val stateOfPoll =
                        pollutionViewModel.pollutionState.collectAsStateWithLifecycle()

                    val userData = googleAuthUiClient.getSignedInUser()
                    val profileImage = userData?.profilePictureUrl
                        ?: "https://static.vecteezy.com/system/resources/thumbnails/020/185/984/small_2x/placeholder-icon-design-free-vector.jpg"
                    // Fetch latest news
                    LaunchedEffect(Unit) {
                        newsViewModel.fetchNews()
                    }

                    NavHost(navController = navController, startDestination = "sign_in") {
                        composable("sign_in") {
                            val viewModel = viewModel<SignInViewModel>()
                            val state by viewModel.state.collectAsStateWithLifecycle()
                            var isLoginIntentLoading by remember { mutableStateOf(false) }

                            if (googleAuthUiClient.getSignedInUser()?.userId != null) {
                                navController.navigate("home")
                            }

                            val launcher = rememberLauncherForActivityResult(
                                contract = ActivityResultContracts.StartIntentSenderForResult(),
                                onResult = { result ->
                                    isLoginIntentLoading = false
                                    if (result.resultCode == RESULT_OK) {
                                        lifecycleScope.launch {
                                            val signInResult =
                                                googleAuthUiClient.getSignInResultFromIntent(
                                                    intent = result.data ?: return@launch
                                                )
                                            viewModel.onSignInResult(signInResult)
                                        }
                                    }
                                }
                            )

                            LaunchedEffect(key1 = state.isSignInSuccessful) {
                                if (state.isSignInSuccessful) {
                                    navController.navigate("home")
                                    viewModel.resetState()
                                }
                            }

                            SignInScreen(state = state, isLoginIntentLoading) {
                                isLoginIntentLoading = true
                                lifecycleScope.launch {
                                    val signInIntentSender = googleAuthUiClient.signIn()
                                    launcher.launch(
                                        IntentSenderRequest.Builder(
                                            signInIntentSender ?: return@launch
                                        ).build()
                                    )
                                }
                            }
                        }

                        composable("profile") {
                            ProfileScreen(userData ?: return@composable) {
                                lifecycleScope.launch {
                                    googleAuthUiClient.signOut()
                                    navController.navigate("sign_in")
                                }
                            }
                        }

                        composable("home") {
                            HomeScreen(
                                userData?.username ?: "Anonymus",
                                profileImage,
                                newsState.value,
                                navController
                            )
                        }

                        composable("pollutionScreen") {
                            PollutionScreen(pollutionData = stateOfPoll.value, viewModel = pollutionViewModel) {
                                pollutionViewModel.fetchPollutionData(it)
                            }
                        }

                        navigation(startDestination = "newsScreen", route = "news") {
                            composable("newsScreen") {
                                NewsScreen(news = newsState.value, navController = navController)
                            }

                            composable("newsDetails/{index}", arguments = listOf(
                                navArgument("index") {
                                    type = NavType.IntType
                                    defaultValue = 0
                                }
                            )) { navBackStackEntry ->
                                val index = navBackStackEntry.arguments?.getInt("index")
                                NewsDetailScreen(index = index ?: 0)
                            }
                        }

                        composable("ecoTracker/{memberId}", arguments = listOf(
                            navArgument("memberId") { type = NavType.StringType }
                        )) {
                            EcoTrackerScreen()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(
    items: List<BottomBarItem>,
    modifier: Modifier = Modifier,
    initialSelectedItem: Int = 0,
    navController: NavHostController
) {
    var selectedItem by remember {
        mutableIntStateOf(initialSelectedItem)
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier
            .background(Color.White)
            .height(60.dp)
            .fillMaxWidth()
    ) {
        items.forEachIndexed { index, item ->
            BottomBarSingleItem(
                item.icon,
                item.title,
                item.route,
                navController = navController,
                onItemClick = {
                    selectedItem = index
                }
            )
        }
    }
}

@Composable
fun BottomBarSingleItem(
    icon: Int,
    title: Int,
    route: String,
    onItemClick: () -> Unit,
    navController: NavHostController
) {
    var currentlySelect by remember {
        mutableStateOf(false)
    }

    DisposableEffect(navController) {
        val listener =
            NavController.OnDestinationChangedListener { _, destination, _ ->
                currentlySelect = destination.parent?.route == route || destination.route == route
            }
        navController.addOnDestinationChangedListener(listener)
        onDispose {
            navController.removeOnDestinationChangedListener(listener)
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .clickable {
                onItemClick()
                navController.navigate(route) {
                    popUpTo(navController.graph.findStartDestination().id)
                    launchSingleTop = true
                }
            }
            .padding(start = 5.dp, end = 5.dp)
    ) {
        Icon(
            painter = painterResource(id = icon),
            tint = if (currentlySelect) Color.Blue else Color.DarkGray,
            contentDescription = ""
        )
        Text(stringResource(id = title))
    }
}