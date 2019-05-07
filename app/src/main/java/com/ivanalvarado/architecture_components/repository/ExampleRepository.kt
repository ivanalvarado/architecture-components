package com.ivanalvarado.architecture_components.repository

import com.ivanalvarado.architecture_components.database.dao.ExampleDao
import com.ivanalvarado.architecture_components.network.services.ExampleApiService
import javax.inject.Inject

class ExampleRepository @Inject constructor(
    private val exampleDao: ExampleDao,
    private val exampleApiService: ExampleApiService
) {

}