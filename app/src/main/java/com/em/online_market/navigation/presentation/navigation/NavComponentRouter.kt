package com.em.online_market.navigation.presentation.navigation

import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.em.online_market.navigation.DestinationsProvider
import com.em.online_market.navigation.presentation.TabsFragment
import com.em.presentation.ARG_SCREEN
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import java.io.Serializable
import java.util.regex.Pattern


/**
 * Stack and tabs navigation based on Navigation Component library
 */
class NavComponentRouter @AssistedInject constructor(
    @Assisted @IdRes private val fragmentContainerId: Int,
    private val destinationProvider: DestinationsProvider,
    private val navigationModelHolder: NavigationModeHolder,
    private val activity: FragmentActivity,
) {

    private var currentStartDestination = 0
    private var navController: NavController? = null
    private var fragmentDialog: Int = 0

    private val destinationListeners = mutableSetOf<()->Unit>()

    private val fragmentListener = object : FragmentManager.FragmentLifecycleCallbacks(){
        override fun onFragmentViewCreated(fm: FragmentManager, f: Fragment, v: View, savedInstanceState: Bundle?
        ) {
            super.onFragmentViewCreated(fm, f, v, savedInstanceState)
            if (f is TabsFragment || f is NavHostFragment) return
            val currentNavController = f.findNavController()
            onNavControllerActivated(currentNavController)
            destinationListeners.forEach{it()}
        }

        override fun onFragmentStarted(fm: FragmentManager, f: Fragment) {
            super.onFragmentStarted(fm, f)
            if(f is DialogFragment) fragmentDialog++
        }

        override fun onFragmentStopped(fm: FragmentManager, f: Fragment) {
            super.onFragmentStopped(fm, f)
            if (f is DialogFragment) fragmentDialog--
        }
    }

    fun onCreate(){
        activity.supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentListener, true)
    }

    fun onDestroy(){
        activity.supportFragmentManager.unregisterFragmentLifecycleCallbacks(fragmentListener)
        navController = null
        destinationListeners.clear()
    }

    fun onNavigateUp(): Boolean{
        pop()
        return true
    }


    fun pop(){
        activity.onBackPressedDispatcher.onBackPressed()
    }


    fun onSavedInstantState(bundle: Bundle){
        bundle.putInt(KEY_START_DESTINATION, currentStartDestination)
        bundle.putSerializable(KEY_NAV_MODE, navigationModelHolder.navigateMode)
    }


    fun onRestoreInstanceState(bundle: Bundle){
        currentStartDestination = bundle.getInt(KEY_START_DESTINATION,0)
        navigationModelHolder.navigateMode = bundle.getSerializable(KEY_NAV_MODE) as? NavigationMode
            ?: throw IllegalStateException("No state to be restored")
        restoreRoot()
    }
    private fun restoreRoot(){
        val graph = getRootNavController().navInflater.inflate(destinationProvider.provideNavigationGraphId())
        graph.setStartDestination(destinationProvider.provideStartDestinationId())
        getRootNavController().graph = graph
    }

    private fun getRootNavController(): NavController {
        val fragmentManager = activity.supportFragmentManager
        val navHost = fragmentManager.findFragmentById(fragmentContainerId) as NavHostFragment
        return navHost.navController
    }


    fun switchToStack(@IdRes initialDestinationId: Int){
        navigationModelHolder.navigateMode = NavigationMode.Stack
        switchRoot(initialDestinationId)
        currentStartDestination = initialDestinationId
    }

    private fun switchRoot(@IdRes rootDestinationId: Int){
        if(currentStartDestination == 0){
            restoreRoot()
        }else{
            getRootNavController().navigate(
                resId = rootDestinationId,
                args = null,
                navOptions{
                    popUpTo(currentStartDestination){
                        inclusive = true
                    }
                }
            )
        }
    }

    fun switchToTabs(rootDestinationId: List<NavTab>, startTabDestinationId: Int?){
        navigationModelHolder.navigateMode = NavigationMode.Tabs(
            ArrayList(rootDestinationId),
            startTabDestinationId
        )
        switchRoot(destinationProvider.provideTabsDestinationId())
        currentStartDestination = destinationProvider.provideTabsDestinationId()
    }

    fun launch(@IdRes destinationId: Int, args: Serializable? = null){
        if(args == null){
            getRootNavController().navigate(resId = destinationId)
        }else{
            getRootNavController().navigate(
                resId = destinationId,
                args = Bundle().apply {
                    putSerializable(ARG_SCREEN, args)
                }
            )
        }
    }

    internal fun addDestinationListener(listener: () -> Unit){
        destinationListeners.add(listener)
    }

    internal fun hasDestination(id: Int): Boolean{
        return getRootNavController().popBackStack(id, true)
    }

    internal fun isDialog(): Boolean{
        return fragmentDialog > 0
    }



    private val destinationListener = NavController.OnDestinationChangedListener { controller, destination, arguments ->
        val appCompactActivity = activity as? AppCompatActivity ?: return@OnDestinationChangedListener
        val title = prepareTitle(destination.label, arguments)
        if (title.isNotBlank()){
            appCompactActivity.supportActionBar?.title = title
        }
        appCompactActivity.supportActionBar?.setDisplayHomeAsUpEnabled(!isStartDestination(destination))

    }

    private fun isStartDestination(destination: NavDestination?): Boolean{
        if(destination == null) return false
        val graph = destination.parent ?: return false
        val startTabsDestinations = destinationProvider.provideMainTabs().map { it.destinationId }
        val startDestinations = startTabsDestinations + graph.startDestinationId
        return startDestinations.contains(destination.id)
    }

    private fun prepareTitle(label: CharSequence?, arguments: Bundle?): String{

        //code for this method has been copied from Google sources :)
        if (label == null) return ""
        val title = StringBuffer()
        val fillInPattern = Pattern.compile("\\{(.+?)\\}")
        val matcher = fillInPattern.matcher(label)
        while (matcher.find()) {
            val argName = matcher.group(1)
            if (arguments != null && arguments.containsKey(argName)) {
                matcher.appendReplacement(title, "")
                title.append(arguments[argName].toString())
            } else {
                throw IllegalArgumentException(
                    "Could not find $argName in $arguments to fill label $label"
                )
            }
        }
        matcher.appendTail(title)
        return title.toString()
    }


    private fun onNavControllerActivated(navController: NavController){
        if(this.navController == navController) return
        this.navController?.removeOnDestinationChangedListener(destinationListener)
        navController.addOnDestinationChangedListener(destinationListener)
        this.navController = navController
    }

    @AssistedFactory
    interface Factory{
        fun create(
            @IdRes fragmentContainerId: Int
        ): NavComponentRouter
    }

    private companion object{
        const val KEY_START_DESTINATION = "startDestination"
        const val KEY_NAV_MODE = "navMode"
    }


}