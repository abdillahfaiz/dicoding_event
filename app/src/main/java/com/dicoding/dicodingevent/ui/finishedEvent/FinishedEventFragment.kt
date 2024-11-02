package com.dicoding.dicodingevent.ui.finishedEvent

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.dicodingevent.ui.detailEvent.DetailEventActivity
import com.dicoding.dicodingevent.data.remote.response.ListEventsItem
import com.dicoding.dicodingevent.databinding.FragmentFinishedEventBinding

class FinishedEventFragment : Fragment() {

    private var _binding: FragmentFinishedEventBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val finishedEventViewModel by viewModels<FinishedEventViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFinishedEventBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvEvent.layoutManager =  layoutManager

        finishedEventViewModel.listEvents.observe(viewLifecycleOwner) {listEvent ->
            setListEvent(listEvent)
        }

        finishedEventViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

    }

    private fun setListEvent(consumer: List<ListEventsItem>) {
        val adapter = FinishedEventAdapter()
        adapter.submitList(consumer)
        binding.rvEvent.adapter = adapter
        adapter.setOnItemClickCallback(object : FinishedEventAdapter.OnItemClickCallback{
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
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.INVISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}