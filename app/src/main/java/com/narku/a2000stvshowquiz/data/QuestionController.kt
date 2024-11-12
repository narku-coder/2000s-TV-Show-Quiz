package com.narku.a2000stvshowquiz.data

import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class QuestionController : Callback<List<Question>> {

    private val BASE_URL = "http://localhost:3000/"
    var questions = ArrayList<Question>(40)
    val api: QuestionApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(QuestionApiService::class.java)
    }

    fun start() {
        val gson = GsonBuilder()
            .setLenient()
            .create()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        val questionApi = retrofit.create(QuestionApiService::class.java)

        val questions = questionApi.getQuestions()
        questions.enqueue(this)
    }

    override fun onResponse(p0: Call<List<Question>>, p1: Response<List<Question>>) {
        if (p1.isSuccessful) {
            val questionList = p1.body()
            questionList?.forEach { question ->
                questions.add(question)
            }
        } else {
            println(p1.errorBody())
        }
    }

    override fun onFailure(p0: Call<List<Question>>, p1: Throwable) {
        p1.printStackTrace()
    }
}