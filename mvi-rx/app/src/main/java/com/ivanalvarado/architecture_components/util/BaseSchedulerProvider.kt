package com.ivanalvarado.architecture_components.util

import io.reactivex.Scheduler

/**
 * Allow providing different types of [Scheduler]s.
 */
interface BaseSchedulerProvider {
    fun computation(): Scheduler
    fun io(): Scheduler
    fun ui(): Scheduler
}