package com.apprecipe.abngit

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.apprecipe.abngit.ui.details.RepoDetailsScreen
import com.apprecipe.abngit.ui.list.ReposListScreen

@Composable
fun MyAppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = "list"
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable("list") {
            ReposListScreen(
                onListItemClick = { repo -> navController.navigate("details/${repo.id}") },
            )
        }
        composable(
            "details/{id}",
            arguments = listOf(navArgument("id") { type = NavType.LongType })
        ) { backStackEntry ->
            backStackEntry.arguments?.getLong("id")?.let { RepoDetailsScreen(repoId = it) }
        }
    }
}
