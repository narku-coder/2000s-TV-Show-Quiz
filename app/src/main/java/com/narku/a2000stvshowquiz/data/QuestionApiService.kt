package com.narku.a2000stvshowquiz.data

import retrofit2.Call
import retrofit2.http.GET

interface QuestionApiService {
    @GET("questions")
    fun getQuestions(): Call<List<Question>>
}