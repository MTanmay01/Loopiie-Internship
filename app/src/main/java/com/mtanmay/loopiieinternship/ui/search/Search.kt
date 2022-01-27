package com.mtanmay.loopiieinternship.ui.search

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.mtanmay.loopiieinternship.R
import com.mtanmay.loopiieinternship.api.Photo
import com.mtanmay.loopiieinternship.databinding.FragmentSearchBinding
import com.mtanmay.loopiieinternship.shared.Adapter
import com.mtanmay.loopiieinternship.shared.LoadstateAdapter
import com.mtanmay.loopiieinternship.shared.ViewModel
import com.mtanmay.loopiieinternship.utils.NetworkUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@AndroidEntryPoint
class Search : Fragment(R.layout.fragment_search), Adapter.OnItemClickListener {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var viewModel: ViewModel
    private val searchAdapter = Adapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(ViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)

        binding.apply {

            searchImages.layoutManager = GridLayoutManager(requireContext(), 2)
            searchImages.setHasFixedSize(true)
            searchImages.adapter = searchAdapter.withLoadStateHeaderAndFooter(
                header = LoadstateAdapter { searchAdapter.retry() },
                footer = LoadstateAdapter { searchAdapter.retry() }
            )

        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadResults()
        searchAdapter.setOnItemClickListener(this)

    }

    fun loadResults() {

        var searchJob: Job? = null

        binding.apply {

            searchEditText.setOnKeyListener(View.OnKeyListener { v, _, event ->
                if (event.action == KeyEvent.ACTION_DOWN || event.action == KeyEvent.KEYCODE_ENTER) {

                    if(!NetworkUtil.isConnected(requireContext()))
                        retrySnackbar()

                    val query = searchEditText.text.toString()
                    if (!TextUtils.isEmpty(query))
                        viewModel.getSearchPhotos(query).observe(viewLifecycleOwner) {
                            searchJob?.cancel()
                            searchJob = lifecycleScope.launch {
                                searchAdapter.submitData(viewLifecycleOwner.lifecycle, it)
                            }
                        }

                    val manager =
                        requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    manager.hideSoftInputFromWindow(v.windowToken, 0)
                }
                return@OnKeyListener false
            })

        }
    }

    fun retrySnackbar() {
        Snackbar.make(binding.root, "No internet connection", Snackbar.LENGTH_SHORT)
            .setAction("RETRY") {
                if (NetworkUtil.isConnected(requireContext()))
                    loadResults()
                else
                    retrySnackbar()
            }
            .show()
    }

    override fun onItemClick(photo: Photo) {
        val action = SearchDirections.actionSearchToImageScreen(photo)
        findNavController().navigate(action)
    }
}