package com.dev.ktor_example_app

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dev.ktor_example_app.api.RetrofitClient
import com.dev.ktor_example_app.model.Employee
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var nameTextView: TextView
    private lateinit var surnameTextView: TextView
    private lateinit var yearTextView: TextView
    private lateinit var idTextView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //TextView Initialization
        nameTextView = findViewById(R.id.tv1)
        surnameTextView = findViewById(R.id.tv2)
        yearTextView = findViewById(R.id.tv3)
        idTextView = findViewById(R.id.tv4)

        //Button Declaration
        val getEmployeeButton = findViewById<Button>(R.id.btn1)
        val createEmployeeButton = findViewById<Button>(R.id.btn2)
        val deleteEmployeeButton = findViewById<Button>(R.id.btn3)

        //EditText Declaration
        val idEditText = findViewById<EditText>(R.id.et1)

        getEmployeeButton.setOnClickListener {
            getEmployeeDetails(idEditText.text.toString())
        }
        createEmployeeButton.setOnClickListener {
            val employee = Employee("John", "Doe", "2020", "")
            createOrUpdateEmployee(employee)
        }

        deleteEmployeeButton.setOnClickListener {

            deleteEmployee(idEditText.text.toString())
        }


    }

    private fun deleteEmployee(id: String) {

//        val employee = DeleteEmployeeRequests(id)

        val apiService = RetrofitClient.instance
        apiService.deleteEmployeeForID(id).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@MainActivity, "Employee deleted successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@MainActivity, "Failed to delete employee", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun createOrUpdateEmployee(employee: Employee) {
        val apiService = RetrofitClient.instance
        apiService.createEmployeeOrUpdateEmployeeForID(employee).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@MainActivity, "Employee saved successfully", Toast.LENGTH_SHORT).show()
                } else {

                    Toast.makeText(this@MainActivity, "Failed to save employee", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                Log.e("MAINACTIVITY","Error: ${t.message}")
            }
        })
    }

    private fun getEmployeeDetails(id: String) {

//        val employeeRequests = EmployeeRequests(id)
        val apiService = RetrofitClient.instance
        apiService.getEmployeeForID(id).enqueue(object : Callback<Employee> {
            override fun onResponse(call: Call<Employee>, response: Response<Employee>) {
                if (response.isSuccessful) {
                    val employee = response.body()
                    Toast.makeText(
                        this@MainActivity,
                        "Employee: ${employee?.name} ${employee?.surname}",
                        Toast.LENGTH_SHORT
                    ).show()
                    nameTextView.text = employee?.name
                    surnameTextView.text = employee?.surname
                    yearTextView.text = employee?.year
                    idTextView.text = employee?.id
                } else {
                    Toast.makeText(this@MainActivity, "Failed to get employee", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(call: Call<Employee>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}