package com.ivanalvarado.architecture_components

import com.ivanalvarado.architecture_components.di.component.DaggerAppComponent
import com.ivanalvarado.architecture_components.ui.user_list.UserListModelStore
import com.ivanalvarado.architecture_components.ui.user_list.UserListState
import com.ivanalvarado.architecture_components.util.ReplaceMainThreadSchedulerRule
import dagger.android.AndroidInjection
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnit
import javax.inject.Inject

class UserListModelStoreTest : BaseTest() {

    // Swap out AndroidSchedulers.mainThread() for trampoline scheduler.
    @get:Rule
    val schedulerRule = ReplaceMainThreadSchedulerRule()

    private val userListModelStore = UserListModelStore()

    @Before
    fun setUp() {
    }

    @Test
    fun startingState() {
        val testObserver = TestObserver<UserListState>()

        userListModelStore.modelState().subscribe(testObserver)
    }
}