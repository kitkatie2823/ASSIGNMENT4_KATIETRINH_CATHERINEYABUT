package com.example.assignment4_katietrinh_catherineyabut

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment4_katietrinh_catherineyabut.adapter.TodoAdapter
import com.example.assignment4_katietrinh_catherineyabut.model.Todo
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DisplayFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DisplayFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var todoAdapter: TodoAdapter

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
        val view = inflater.inflate(R.layout.fragment_display, container, false)
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Initialize adapter with empty list and click listener
        todoAdapter = TodoAdapter(emptyList()) { todo ->
            val intent = Intent(requireContext(), TodoDetailActivity::class.java)
            intent.putExtra("todo_id", todo.id.toString()) // Pass todo ID to detail activity
            startActivity(intent)
        }
        recyclerView.adapter = todoAdapter

        // Fetch data when DISPLAY button is clicked
        view.findViewById<Button>(R.id.btn_display).setOnClickListener {
            fetchDataFromFirebase()
        }

        // FAB to open AddTodoActivity
        view.findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            startActivity(Intent(requireContext(), AddTodoActivity::class.java))
        }

        return view

        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_display, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DisplayFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DisplayFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun fetchDataFromFirebase() {
        FirebaseFirestore.getInstance().collection("todos")
            .get()
            .addOnSuccessListener { result ->
                val todos = result.toObjects(Todo::class.java)
                todoAdapter.updateData(todos)
            }
            .addOnFailureListener { e ->
                Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}