package com.ivanalvarado.architecture_components.util

import org.junit.Rule
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import org.mockito.quality.Strictness

open class MockitoTest {

    val mockitoStrictness: Strictness get() = Strictness.LENIENT
    @Rule
    fun mockitoRule(): MockitoRule = MockitoJUnit.rule().strictness(mockitoStrictness)
}