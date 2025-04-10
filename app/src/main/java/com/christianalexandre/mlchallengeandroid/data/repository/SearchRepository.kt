package com.christianalexandre.mlchallengeandroid.data.repository

interface SearchRepository {
    suspend fun search(query: String)
}