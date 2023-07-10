package com.example.proyectoappmoviles.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.proyectoappmoviles.models.Sede
import com.example.proyectoappmoviles.screens.barbers.BarberScreenViewModel
import com.example.proyectoappmoviles.screens.barbers.ui.BarberScreen
import com.example.proyectoappmoviles.screens.history.HistoryScreenViewModel
import com.example.proyectoappmoviles.screens.history.ui.HistoryScreen
import com.example.proyectoappmoviles.screens.location.LocationScreenViewModel
import com.example.proyectoappmoviles.screens.location.ui.LocationScreen
import com.example.proyectoappmoviles.screens.login.LoginScreenViewModel
import com.example.proyectoappmoviles.screens.login.ui.LoginScreen
import com.example.proyectoappmoviles.screens.menu.MenuScreenViewModel
import com.example.proyectoappmoviles.screens.menu.ui.MenuScreen
import com.example.proyectoappmoviles.screens.register.RegisterScreenViewModel
import com.example.proyectoappmoviles.screens.register.ui.RegisterScreen
import com.example.proyectoappmoviles.screens.resumedate.ResumeDateViewModel
import com.example.proyectoappmoviles.screens.resumedate.ui.ResumeDateScreen
import com.example.proyectoappmoviles.screens.schedule.ScheduleScreenViewModel
import com.example.proyectoappmoviles.screens.schedule.ui.ScheduleScreen
import com.example.proyectoappmoviles.screens.services.ServicesScreenViewModel
import com.example.proyectoappmoviles.screens.services.ui.ServicesScreen
import com.example.proyectoappmoviles.screens.splashscreen.BSSplashScreen

@Composable
fun AppNavigation(){
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = ProjectScreens.BSSplashScreen.name
    ){
        composable(ProjectScreens.BSSplashScreen.name){
            BSSplashScreen(navController = navController)
        }
        composable(ProjectScreens.LoginScreen.name){
            LoginScreen(navController = navController, LoginScreenViewModel())
        }
        composable(ProjectScreens.RegisterScreen.name){
            RegisterScreen(navController = navController, RegisterScreenViewModel())
        }
        composable(ProjectScreens.LocationScreen.name){
            LocationScreen(navController = navController, LocationScreenViewModel())
        }
        composable(route =ProjectScreens.MenuScreen.name){
            MenuScreen(navController = navController, MenuScreenViewModel())
        }
        composable(ProjectScreens.BarberScreen.name){
            BarberScreen(navController = navController, BarberScreenViewModel())
        }
        composable(ProjectScreens.ServicesScreen.name){
            ServicesScreen(navController = navController, ServicesScreenViewModel())
        }
        composable(ProjectScreens.ScheduleScreen.name){
            ScheduleScreen(navController = navController, viewModel = ScheduleScreenViewModel())
        }
        composable(ProjectScreens.ResumeDateScreen.name){
            ResumeDateScreen(navController = navController, viewModel = ResumeDateViewModel())
        }
        composable(ProjectScreens.HistoryScreen.name){
            HistoryScreen(navController = navController, viewModel = HistoryScreenViewModel())
        }
    }
}