package com.em.presentation


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.em.common.AlertDialogConfig
import com.em.common.CommonUi
import com.em.common.Container
import com.em.common.Core
import com.em.common.AppLogger
import com.em.common.AppResources
import com.em.presentation.assignable.Assignable
import com.em.presentation.assignable.LiveValueAssignable
import com.em.presentation.assignable.StateFlowAssignable
import com.em.presentation.live.Event
import com.em.presentation.live.LiveEventValue
import com.em.presentation.live.LiveValue
import com.em.presentation.live.MutableLiveValue
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.sample
import kotlinx.coroutines.launch

/**
 * Base class for all view models.
 */
@OptIn(FlowPreview::class)
open class BaseViewModel: ViewModel() {


    /**
     * View model with pre-assigned error handler.
     * Error handler is taken from [Core.errorHandler].
     */

    protected val viewModelScope: CoroutineScope by lazy {
        val errorHandler = CoroutineExceptionHandler { _, exception ->
            Core.errorHandler.handlerError(exception)
        }
        CoroutineScope(SupervisorJob() + Dispatchers.Main + errorHandler)
    }

    /**
     * Access android resources from view-models
     */

    protected val resources: AppResources get() = Core.resources

    /**
     * Launch common actions (toasts and dialogs) from view-models
     */

    protected val commonUi : CommonUi get() = Core.commonUi

    /**
     * Log any event/error from view-models
     */

    protected val  logger: AppLogger get() = Core.logger

    private val debounceFlow = MutableSharedFlow<()-> Unit>(
        replay = 1,
        extraBufferCapacity = 42
    )

    init {
        viewModelScope.launch {
            debounceFlow.sample(Core.debouncePeriodMillis).collect {
                it()
            }
        }
    }

    /**
     * Created a [LiveValue]. You can change the value inside [LiveValue]
     * within your view-model class but you can't do this in other classes.
     */

    protected fun <T> liveValue(): LiveValue<T> {
        return MutableLiveValue()
   }

    /**
     * Create a [LiveValue] with the predefined value.
     * You can change the value inside [LiveValue] within your view-model
     * class but you can't do this in other classes.
     */

    protected fun <T> liveValue(value: T): LiveValue<T> {
        return MutableLiveValue(MutableLiveData(value))
    }

    /**
     * Assign a new value to the [LiveValue] container.
     */

    protected var <T> LiveValue<T>.value: T
        get() = this.requireValue()
        set(value){
            (this as? MutableLiveValue)?.setValue(value)
        }

    /**
     * Create an instance of [LiveValue] which holds [Event]
     * Used to fire one-time side-effect events.
     */

    protected fun <T> liveEvent(): LiveEventValue<T> {
        return MutableLiveValue()
    }

    /**
     * Publish a new event to the [LiveEventValue] created by [liveEvent]
     */
    protected fun <T> LiveEventValue<T>.publish(event: T){
        this.value = Event(event)
    }

    /**
     * Try to load something with [loader] function and reflect the loading
     * status into specified [stateFlow]
     */
    protected fun <T> loadScreenInto(
        stateFlow: MutableStateFlow<Container<T>>,
        errorHandler: ((Exception) -> Unit)? = null,
        loader: suspend () -> T
    ) {
        loadScreenInto(StateFlowAssignable(stateFlow), errorHandler, loader)
    }
    /**
     * Try to load something with [loader] function and reflect the loading
     * status into the specified [liveValue]
     */

    protected fun <T> loadScreenInto(
        liveValue: LiveValue<Container<T>>,
        errorHandler: ((Exception)-> Unit)? = null,
        loader: suspend () -> T
    )
    {
        loadScreenInto(LiveValueAssignable(liveValue), errorHandler, loader)
    }

    private fun <T> loadScreenInto(
        assignable: Assignable<Container<T>>,
        errorHandler: ((Exception) -> Unit)? = null,
        loader: suspend () -> T
    )
    {
        viewModelScope.launch {
            try {
                assignable.setValue(Container.Pending)
                val value = loader()
                assignable.setValue(Container.Success(value))
            }catch (e: Exception){
                if (e is CancellationException) throw e
                errorHandler?.invoke(e)
                assignable.setValue(Container.Error(e))
            }
        }
    }

    /**
     * Convert the [Flow] into [LiveValue]
     */

    protected fun <T> Flow<T>.toLiveValue(initialValue: T? = null): LiveValue<T> {
        val liveValue = if (initialValue != null)
            liveValue<T>(initialValue)
        else{
            liveValue()
        }
        viewModelScope.launch {
            collect{
                liveValue.value = it
            }
        }
        return liveValue
    }

    /**
     * Call an action only once within the [Core.debouncePeriodMillis] period.
     */

    protected fun debounce(block: () -> Unit){
        debounceFlow.tryEmit(block)
    }

    /**
     * Show an error dialog with the provided [message] and one OK button.
     */

    protected fun showErrorDialog(message: String){
        Core.globalScope.launch {
            commonUi.alertDialog(
                AlertDialogConfig(
                title = resources.getString(R.string.core_presentation_general_error_title),
                message = message,
                positiveButton = resources.getString(R.string.core_presentation_general_error_ok),
            )
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}