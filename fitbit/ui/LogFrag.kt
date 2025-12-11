package com.example.fitbit.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitbit.AddEntryActivity
import com.example.fitbit.DailyEntryAdapter
import com.example.fitbit.R
import com.example.fitbit.data.AppDatabase
import com.example.fitbit.data.DailyEntry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class LogFrag : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var entryAdapter: DailyEntryAdapter
    private lateinit var database: AppDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_log, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        database = AppDatabase.getInstance(requireContext().applicationContext)

        recyclerView = view.findViewById(R.id.recyclerView)
        val btnAddFood = view.findViewById<Button>(R.id.btnAddFood)

        entryAdapter = DailyEntryAdapter(emptyList()) { entry ->
            deleteEntry(entry)
        }
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = entryAdapter

        lifecycleScope.launch {
            database.dailyEntryDao().getAll().collect { entries ->
                entryAdapter.updateData(entries)
            }
        }

        btnAddFood.setOnClickListener {
            val intent = Intent(requireContext(), AddEntryActivity::class.java)
            startActivity(intent)
        }
    }

    private fun deleteEntry(entry: DailyEntry) {
        lifecycleScope.launch(Dispatchers.IO) {
            database.dailyEntryDao().delete(entry)
        }
    }
}