package com.ivan.cinematicketbooking.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.ivan.cinematicketbooking.R
import com.ivan.cinematicketbooking.di.App
import com.ivan.cinematicketbooking.navigation.AppNavigatorImpl
import com.ivan.cinematicketbooking.navigation.NavigationCommand
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var navigator: AppNavigatorImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        lifecycleScope.launch {
            navigator.navigationCommands.collect { command ->
                when (command) {
                    is NavigationCommand.To -> navController.navigate(command.destinationId)
                    is NavigationCommand.Back -> navController.popBackStack()
                    else -> Unit
                }
            }
        }
    }
}