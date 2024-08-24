package com.dev.ktor_example_app.api

import com.dev.ktor_example_app.model.Employee
import com.dev.ktor_example_app.model.DeleteEmployeeRequests
import com.dev.ktor_example_app.model.EmployeeRequests
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("/get-employee/{id}")
    fun getEmployeeForID(@Body employeeRequests: EmployeeRequests): Call<Employee>

    @POST("/create-update-employee")
    fun createEmployeeOrUpdateEmployeeForID(@Body employee: Employee): Call<Void>

    @POST("/delete-employee/{id}")
    fun deleteEmployeeForID(@Body deleteEmployeeRequests: DeleteEmployeeRequests): Call<Void>
}