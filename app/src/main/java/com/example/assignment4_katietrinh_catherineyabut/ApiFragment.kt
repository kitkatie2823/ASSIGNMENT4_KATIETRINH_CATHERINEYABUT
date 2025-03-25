package com.example.assignment4_katietrinh_catherineyabut

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.assignment4_katietrinh_catherineyabut.model.Todo
import com.example.assignment4_katietrinh_catherineyabut.api.ApiService
import com.google.firebase.firestore.FirebaseFirestore
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ApiFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ApiFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_api, container, false)
        val btnApiCall = view.findViewById<Button>(R.id.btn_api_call)

        btnApiCall.setOnClickListener {
            fetchDataFromApi()
        }
        return view
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_api, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ApiFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ApiFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun fetchDataFromApi() {
        // Retrofit/Volley code to fetch todos
        // Save each Todo to Firestore
        val db = FirebaseFirestore.getInstance()
        val todosRef = db.collection("todos")

        // Example Retrofit call
        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(com.example.assignment4_katietrinh_catherineyabut.api.ApiService::class.java)
        service.getTodos().enqueue(object : Callback<List<Todo>> {
            override fun onResponse(call: Call<List<Todo>>, response: Response<List<Todo>>) {
                response.body()?.forEach { todo ->
                    todosRef.document(todo.id.toString()).set(todo)
                }
                Toast.makeText(requireContext(), "Data saved to Firebase!", Toast.LENGTH_SHORT).show()
            }
            override fun onFailure(call: Call<List<Todo>>, t: Throwable) {
                Toast.makeText(requireContext(), "API call failed!", Toast.LENGTH_SHORT).show()
            }
        })
    }
}