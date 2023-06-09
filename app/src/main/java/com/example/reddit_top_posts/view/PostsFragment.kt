package com.example.reddit_top_posts.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.reddit_top_posts.view.adapters.LoadPostsAdapter
import com.example.reddit_top_posts.view.adapters.PostsAdapter
import com.example.reddit_top_posts.viewmodel.PostsViewModel
import com.example.reddittopposts.databinding.FragmentPostsBinding
import kotlinx.coroutines.launch


class PostsFragment : Fragment() {

    private lateinit var binding: FragmentPostsBinding
    private var postsAdapter = PostsAdapter()

    private val postsViewModel: PostsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPostsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            postsAdapter.setOnItemClickListener {
                findNavController().navigate(
                    PostsFragmentDirections.actionPostsFragmentToThumbnailImageFragment(it.url)
                )
            }

            postsViewModel.postsLiveData.observe(viewLifecycleOwner) {
                lifecycleScope.launch {
                    repeatOnLifecycle(Lifecycle.State.CREATED) {
                        postsViewModel.postsLiveData.value?.collect {
                            postsAdapter.submitData(it)
                        }
                    }
                }
            }



            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.CREATED) {
                    postsAdapter.loadStateFlow.collect {
                        progressBar.isVisible = it.refresh is LoadState.Loading
                    }
                }
            }

            posts.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = postsAdapter

                adapter = postsAdapter.withLoadStateFooter(
                    LoadPostsAdapter {
                        postsAdapter.retry()
                    }
                )
            }

        }
    }
}