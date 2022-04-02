package com.chocolateloverraj.androidapiwebview.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.chocolateloverraj.androidapiwebview.ApisRecyclerViewAdapter
import com.chocolateloverraj.androidapiwebview.databinding.FragmentApisBinding

class ApisFragment : Fragment() {
    private var binding: FragmentApisBinding? = null
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentApisBinding.inflate(inflater, container, false)
        val root: View = binding!!.root
        val recyclerView = binding!!.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = ApisRecyclerViewAdapter(requireContext())
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}