package com.mtanmay.loopiieinternship.ui.feed

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import com.mtanmay.loopiieinternship.R
import com.mtanmay.loopiieinternship.api.Photo
import com.mtanmay.loopiieinternship.databinding.FragmentFeedBinding
import com.mtanmay.loopiieinternship.shared.Adapter
import com.mtanmay.loopiieinternship.shared.LoadstateAdapter
import com.mtanmay.loopiieinternship.shared.ViewModel
import com.mtanmay.loopiieinternship.utils.NetworkUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Feed : Fragment(R.layout.fragment_feed), Adapter.OnItemClickListener,
    SwipeRefreshLayout.OnRefreshListener {

    private lateinit var viewModel: ViewModel
    private lateinit var binding: FragmentFeedBinding
    private lateinit var refreshLayout: SwipeRefreshLayout

    private val feedAdapter = Adapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(ViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentFeedBinding.inflate(inflater, container, false)

        binding.apply {

            feedImages.apply {
                layoutManager = GridLayoutManager(requireContext(), 2)
                setHasFixedSize(true)
                adapter = feedAdapter.withLoadStateHeaderAndFooter(
                    header = LoadstateAdapter { feedAdapter.retry() },
                    footer = LoadstateAdapter { feedAdapter.retry() }
                )
            }

            swipeLayout.setOnRefreshListener(this@Feed)

        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (NetworkUtil.isConnected(requireContext())) {
            if (viewModel.loadAgain) {
                loadFeed()
                viewModel.loadAgain = false
            }

            feedAdapter.setOnItemClickListener(this)
        } else
            retrySnackbar()

    }

    private fun retrySnackbar() {
        Snackbar.make(binding.root, "No internet connection", Snackbar.LENGTH_SHORT)
            .setAction("RETRY") {
                if (NetworkUtil.isConnected(requireContext()))
                    loadFeed()
                else
                    retrySnackbar()
            }
            .show()
    }

    private fun loadFeed() {

        viewModel.getRecentPhotos().observe(viewLifecycleOwner) {
            feedAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
    }

    override fun onItemClick(photo: Photo) {
        val action = FeedDirections.actionFeedToImageScreen(photo)
        findNavController().navigate(action)
    }

    override fun onRefresh() {
        viewModel.loadAgain = true
        loadFeed()
        binding.swipeLayout.isRefreshing = false
    }

}