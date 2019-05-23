package com.ivanalvarado.template.repository

import com.ivanalvarado.template.database.dao.ExampleDao
import com.ivanalvarado.template.network.services.ExampleApiService
import javax.inject.Inject

class ExampleRepository @Inject constructor(
    private val exampleDao: ExampleDao,
    private val exampleApiService: ExampleApiService
) {

}