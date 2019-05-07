package com.ivanalvarado.architecture_components.network

import com.ivanalvarado.architecture_components.database.entity.ExampleEntity

data class ExampleApiResponse(
    val page: Long,
    val results: List<ExampleEntity>,
    val total_results: Long,
    val total_pages: Long
)