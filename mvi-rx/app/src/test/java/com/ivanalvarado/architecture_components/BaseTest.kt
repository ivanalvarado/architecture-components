package com.ivanalvarado.architecture_components

import com.ivanalvarado.architecture_components.util.ReplaceMainThreadSchedulerRule
import org.junit.Rule
import org.mockito.junit.MockitoJUnit

open class BaseTest {

//    // Swap out AndroidSchedulers.mainThread() for trampoline scheduler.
//    @get:Rule
//    val schedulerRule = ReplaceMainThreadSchedulerRule()

    // Injects any @Mock reference properties in this test class.
    @get:Rule
    val mockitoRule = MockitoJUnit.rule()
}