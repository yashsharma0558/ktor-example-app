package com.dev.ktor_example_app.api

import com.dev.ktor_example_app.model.Employee
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @GET("/get-employee/{id}")
    fun getEmployeeForID(@Path("id") employeeRequests: String): Call<Employee>

    @POST("/create-update-employee")
    fun createEmployeeOrUpdateEmployeeForID(@Body employee: Employee): Call<Void>

    @DELETE("/delete-employee/{id}")
    fun deleteEmployeeForID(@Path("id") deleteEmployeeRequests: String): Call<Void>
}