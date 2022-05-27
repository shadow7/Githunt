package com.githunt

import androidx.compose.animation.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.NavDestination.Companion.hierarchy
import com.github.ui.owner.details.OwnerDetailsFeature
import com.github.ui.owner.details.OwnerDetailsViewModel
import com.github.ui.search.SearchFeature
import com.github.ui.search.SearchViewModel
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable

internal sealed class Root(val route: String) {
    object Main : Root("Main")
}

internal sealed class Screen(val screenRoute: String) {
    fun createRoute(root: Root) = "${root.route}/$screenRoute"

    object Search : Screen("search")
    object OrgDetails : Screen("org/details/{orgName}") {
        const val argName: String = "orgName"

        fun createRoute(root: Root, orgName: String): String {
            return "${root.route}/org/details/$orgName"
        }

        fun orgName(backStackEntry: NavBackStackEntry): String {
            return backStackEntry.arguments?.getString(argName) ?: ""
        }
    }
}

@ExperimentalAnimationApi
@Composable
internal fun AppNavigation(
    navController: NavHostController,
    searchViewModel: SearchViewModel,
    detailsViewModel: OwnerDetailsViewModel,
    modifier: Modifier = Modifier,
) {
    AnimatedNavHost(
        navController = navController,
        startDestination = Root.Main.route,
        enterTransition = { defaultEnterTransition(initialState, targetState) },
        exitTransition = { defaultExitTransition(initialState, targetState) },
        popEnterTransition = { defaultPopEnterTransition() },
        popExitTransition = { defaultPopExitTransition() },
        modifier = modifier,
    ) {
        addSearchScreen(navController, searchViewModel, Root.Main)
        addOrgDetailsScreen(navController, detailsViewModel, Root.Main)
    }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addSearchScreen(
    navController: NavController,
    searchViewModel: SearchViewModel,
    root: Root,
) {
    composable(root.route) {
        SearchFeature(
            viewModel = searchViewModel,
            openOrgDetails = { name ->
                navController.navigate(
                    Screen.OrgDetails.createRoute(
                        root,
                        name
                    )
                )
            }
        )
    }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addOrgDetailsScreen(
    navController: NavController,
    viewModel: OwnerDetailsViewModel,
    root: Root,
) {
    composable(
        route = Screen.OrgDetails.createRoute(root),
        arguments = listOf(
            navArgument(Screen.OrgDetails.argName) { type = NavType.StringType }
        ),
    ) { backStackEntry ->
        OwnerDetailsFeature(
            viewModel = viewModel,
            organizationName = Screen.OrgDetails.orgName(backStackEntry)
        )
    }
}

@ExperimentalAnimationApi
private fun AnimatedContentScope<*>.defaultEnterTransition(
    initial: NavBackStackEntry,
    target: NavBackStackEntry,
): EnterTransition {
    val initialNavGraph = initial.destination.hostNavGraph
    val targetNavGraph = target.destination.hostNavGraph
    // If we're crossing nav graphs (bottom navigation graphs), we crossfade
    if (initialNavGraph.id != targetNavGraph.id) {
        return fadeIn()
    }
    // Otherwise we're in the same nav graph, we can imply a direction
    return fadeIn() + slideIntoContainer(AnimatedContentScope.SlideDirection.Start)
}

@ExperimentalAnimationApi
private fun AnimatedContentScope<*>.defaultExitTransition(
    initial: NavBackStackEntry,
    target: NavBackStackEntry,
): ExitTransition {
    val initialNavGraph = initial.destination.hostNavGraph
    val targetNavGraph = target.destination.hostNavGraph
    // If we're crossing nav graphs (bottom navigation graphs), we crossfade
    if (initialNavGraph.id != targetNavGraph.id) {
        return fadeOut()
    }
    // Otherwise we're in the same nav graph, we can imply a direction
    return fadeOut() + slideOutOfContainer(AnimatedContentScope.SlideDirection.Start)
}

private val NavDestination.hostNavGraph: NavGraph
    get() = hierarchy.first { it is NavGraph } as NavGraph

@ExperimentalAnimationApi
private fun AnimatedContentScope<*>.defaultPopEnterTransition(): EnterTransition {
    return fadeIn() + slideIntoContainer(AnimatedContentScope.SlideDirection.End)
}

@ExperimentalAnimationApi
private fun AnimatedContentScope<*>.defaultPopExitTransition(): ExitTransition {
    return fadeOut() + slideOutOfContainer(AnimatedContentScope.SlideDirection.End)
}