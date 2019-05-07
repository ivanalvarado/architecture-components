package com.ivanalvarado.template.network

import com.ivanalvarado.template.database.entity.ExampleEntity

data class ExampleApiResponse(
    val page: Long,
    val results: List<ExampleEntity>,
    val total_results: Long,
    val total_pages: Long
)