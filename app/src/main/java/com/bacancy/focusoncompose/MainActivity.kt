package com.bacancy.focusoncompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bacancy.focusoncompose.ui.theme.FocusOnComposeTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FocusOnComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = { Text("Focus on Compose") },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = Color.Blue,
                                titleContentColor = Color.White
                            )
                        )
                    },
                    floatingActionButton = {
                        FloatingActionButton(onClick = { /* Action */ }) {
                            Icon(Icons.Default.Add, contentDescription = "Add")
                        }
                    },
                    content = { innerPadding ->
                        AppNavigation(
                            modifier = Modifier.padding(innerPadding)
                        )
                    })
            }
        }
    }
}

@Composable
fun AppNavigation(modifier: Modifier = Modifier,counterViewModel: CounterViewModel = viewModel()) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") { Counter(modifier, navController, counterViewModel) }
        composable("details/{currentCount}") { backStackEntry ->
            DetailsScreen(currentCounter = backStackEntry.arguments?.getString("currentCount")?.toInt() ?: 0, modifier, navController, counterViewModel)
        }
    }
}

@Composable
fun Counter(modifier: Modifier = Modifier, navController: NavController, counterViewModel: CounterViewModel) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Count is ${counterViewModel.count}", fontSize = 50.sp, modifier = Modifier.padding(20.dp))
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
        ) {
            Button(
                modifier = modifier
                    .padding(horizontal = 5.dp),
                onClick = { counterViewModel.numbersList.add(counterViewModel.count++) }, colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Blue,
                    contentColor = Color.White // This changes the text color
                )
            ) {
                Text(text = "Increment", fontSize = 30.sp)
            }
            Button(
                modifier = modifier
                    .padding(horizontal = 5.dp),
                onClick = { navController.navigate("details/${counterViewModel.count}") },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Blue,
                    contentColor = Color.White
                )
            ) {
                Text(text = "Details", fontSize = 30.sp)
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            items(items = counterViewModel.numbersList) { index ->
                Text(
                    text = "Item #${counterViewModel.numbersList[index]}",
                    color = Color.Black,
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable {
                            println("Clicked on item ${counterViewModel.numbersList[index]}")
                        }
                )
                if (index < counterViewModel.numbersList.size - 1) {
                    HorizontalDivider(
                        color = Color.Gray,
                        thickness = 1.dp,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun DetailsScreen(currentCounter: Int, modifier: Modifier = Modifier, navController: NavController, counterViewModel: CounterViewModel) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("You are in Details Screen with ${counterViewModel.count}")
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp),
            onClick = { navController.popBackStack() }, colors = ButtonDefaults.buttonColors(
                containerColor = Color.Blue,
                contentColor = Color.White // This changes the text color
            )
        ) {
            Text(text = "Back to Home", fontSize = 30.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CounterPreview() {
    FocusOnComposeTheme {
        AppNavigation()
    }
}