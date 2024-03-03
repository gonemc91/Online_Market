package com.em.online_market.navigation.presentation

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.em.common_impl.ActivityRequired
import com.em.online_market.R
import com.em.online_market.databinding.ActivityMainBinding
import com.em.online_market.navigation.DestinationsProvider
import com.em.online_market.navigation.presentation.navigation.NavComponentRouter
import com.em.online_market.navigation.presentation.navigation.RouterHolder
import com.em.theme.R.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), RouterHolder {
    @Inject
    lateinit var navComponentRouterFactory: NavComponentRouter.Factory

    @Inject
    lateinit var destinationsProvider: DestinationsProvider

    @Inject
    lateinit var activityRequiredSet: Set<@JvmSuppressWildcards ActivityRequired>

    private val viewModel by viewModels<MainViewModel>()

    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val navComponentRouter by lazy(LazyThreadSafetyMode.NONE) {
        navComponentRouterFactory.create(R.id.fragmentContainer)
    }

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        if (savedInstanceState != null) {
            navComponentRouter.onRestoreInstanceState(savedInstanceState)
        }

        navComponentRouter.onCreate()
        if (savedInstanceState == null) {
            navComponentRouter.switchToStack(destinationsProvider.provideStartDestinationId())
        }
        activityRequiredSet.forEach {
            it.onCreated(this)
        }

        setSupportActionBar(binding.toolbar)

        with(binding){
            setupListeners()
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        return (navComponentRouter.onNavigateUp()) || super.onSupportNavigateUp()
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        navComponentRouter.onSavedInstantState(outState)
    }

    override fun onStart() {
        super.onStart()
        activityRequiredSet.forEach { it.onStarted() }
    }

    override fun onStop() {
        super.onStop()
        activityRequiredSet.forEach { it.onStopped() }
    }

    override fun onDestroy() {
        super.onDestroy()
        navComponentRouter.onDestroy()
        activityRequiredSet.forEach { it.onDestroyed() }
    }

    override fun requireRouter(): NavComponentRouter {
        return navComponentRouter
    }


    private fun ActivityMainBinding.setupListeners() {

    }
}