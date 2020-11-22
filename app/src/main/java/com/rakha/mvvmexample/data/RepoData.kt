package com.rakha.mvvmexample.data

import com.google.gson.annotations.SerializedName

/**
 *   Created By rakha
 *   2020-01-13
 */
class RepoData(
    val name: String?,
    val description: String?,
    val language: String?,

    @field:SerializedName("html_url")
    val htmlUrl: String?
)