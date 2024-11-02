package com.dicoding.dicodingevent.ui.UpcomingEvent

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.dicodingevent.ui.detailEvent.DetailEventActivity
import com.dicoding.dicodingevent.data.remote.response.ListEventsItem
import com.dicoding.dicodingevent.databinding.FragmentUpcomingEventBinding

class UpcomingEventFragment : Fragment() {

    private var _binding: FragmentUpcomingEventBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val upcomingEventViewmodel by viewModels<UpcomingViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(UpcomingViewModel::class.java)

        _binding = FragmentUpcomingEventBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvEvent.layoutManager =  layoutManager

        upcomingEventViewmodel.listEvents.observe(viewLifecycleOwner) {listEvent ->
            setListEvent(listEvent)
        }

        upcomingEventViewmodel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

    }

    private fun setListEvent(consumer: List<ListEventsItem>) {
        val adapter = UpcomingEventAdapter()
        adapter.submitList(consumer)
        binding.rvEvent.adapter = adapter
        adapter.setOnItemClickCallback(object : UpcomingEventAdapter.OnItemClickCallback{
            override fun onItemClicked(data: ListEventsItem) {
                Log.d("ID", data.id.toString())
                val intent = Intent(activity, DetailEventActivity::class.java)
                intent.putExtra(DetailEventActivity.ID, data.id.toString())
                startActivity(intent)
            }

        })
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar2.visibility = View.VISIBLE
        } else {
            binding.progressBar2.visibility = View.INVISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}