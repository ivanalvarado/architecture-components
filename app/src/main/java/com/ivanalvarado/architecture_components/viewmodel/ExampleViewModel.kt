package com.ivanalvarado.architecture_components.viewmodel

import android.arch.lifecycle.ViewModel
import com.ivanalvarado.architecture_components.repository.ExampleRepository
import javax.inject.Inject

class ExampleViewModel @Inject constructor(private val exampleRepository: ExampleRepository) : ViewModel() {

    init {

    }


}