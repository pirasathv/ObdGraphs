package org.obd.graphs.ui.metrics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.obd.graphs.R
import org.obd.graphs.bl.datalogger.MetricsAggregator
import org.obd.graphs.ui.common.ToggleToolbarDoubleClickListener
import org.obd.metrics.api.model.ObdMetric

class MetricsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_metrics, container, false)
        val data: MutableList<ObdMetric> = arrayListOf()
        val adapter = MetricsViewAdapter(root.context, data)

        MetricsAggregator.metrics.observe(viewLifecycleOwner) {
            it?.let {
                val indexOf = data.indexOf(it)
                if (indexOf == -1) {
                    data.add(it)
                    adapter.notifyItemInserted(data.indexOf(it))
                } else {
                    data[indexOf] = it
                    adapter.notifyItemChanged(indexOf, it)
                }
            }
        }

        val recyclerView: RecyclerView = root.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = GridLayoutManager(root.context, 1)
        recyclerView.adapter = adapter
        recyclerView.addOnItemTouchListener(
            ToggleToolbarDoubleClickListener(
                requireContext()
            )
        )
        return root
    }
}