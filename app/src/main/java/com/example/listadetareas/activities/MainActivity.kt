package com.example.listadetareas.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.listadetareas.R
import com.example.listadetareas.adapters.CategoryAdapter
import com.example.listadetareas.data.Category
import com.example.listadetareas.data.CategoryDAO
import com.example.listadetareas.databinding.ActivityMainBinding
import com.example.listadetareas.databinding.DialogCreateCategoryBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var  adapter: CategoryAdapter
    var categoryList: List<Category> = emptyList()
    lateinit var  categoryDAO: CategoryDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        categoryDAO = CategoryDAO(this)

        categoryList = categoryDAO.findAll()

        adapter = CategoryAdapter(categoryList) {
            // He pulsado categoria
        }
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager (this)
        binding.addCategoryButton.setOnClickListener {
            showCategoryDialog()
        }
    }

    fun  showCategoryDialog() {
        val dialogBinding = DialogCreateCategoryBinding.inflate(layoutInflater)

        MaterialAlertDialogBuilder(this)
        //AlertDialog.Builder(this)
            .setTitle("Create category")
            .setView(dialogBinding.root)
            .setPositiveButton(android.R.string.ok, { dialog, which ->
                val title = dialogBinding.titleEditText.text.toString()
                val category = Category(-1, title)
                categoryDAO.insert(category)
                loadData()



            })
            .setNegativeButton(android.R.string.cancel, null)
            .setIcon(R.drawable.ic_add)
            .show()

    }
    fun loadData() {
        categoryList = categoryDAO.findAll()
        adapter.updateItems(categoryList)
    }
}