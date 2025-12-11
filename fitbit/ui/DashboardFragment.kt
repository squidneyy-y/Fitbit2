package com.example.fitbit.ui

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.fitbit.R
import com.example.fitbit.data.AppDatabase
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class DashboardFragment : Fragment() {

    private lateinit var chart: LineChart
    private lateinit var tvAvgCalories: TextView
    private lateinit var tvAvgWater: TextView
    private lateinit var database: AppDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)
        chart = view.findViewById(R.id.chart)
        tvAvgCalories = view.findViewById(R.id.tvAvgCalories)
        tvAvgWater = view.findViewById(R.id.tvAvgWater)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        database = AppDatabase.getInstance(requireContext().applicationContext)

        viewLifecycleOwner.lifecycleScope.launch {
            database.dailyEntryDao().getAll().collectLatest { entries ->
                if (entries.isNotEmpty()) {
                    val chartEntries = entries.mapIndexed { index, entry ->
                        Entry(index.toFloat(), entry.calories.toFloat())
                    }

                    val dataSet = LineDataSet(chartEntries, "Calories Over Time")
                    dataSet.color = Color.BLUE
                    dataSet.valueTextColor = Color.BLACK

                    val lineData = LineData(dataSet)
                    chart.data = lineData
                    chart.invalidate() // Refresh the chart
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            database.dailyEntryDao().getAverageCalories().collectLatest { avgCalories ->
                tvAvgCalories.text = "Avg Calories: ${avgCalories?.roundToInt() ?: 0}"
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            database.dailyEntryDao().getAverageWaterIntake().collectLatest { avgWater ->
                tvAvgWater.text = "Avg Water: ${avgWater?.roundToInt() ?: 0} cups"
            }
        }
    }
}