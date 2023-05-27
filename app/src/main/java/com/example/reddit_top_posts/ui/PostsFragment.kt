package com.example.reddit_top_posts.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.reddit_top_posts.adapters.PostsAdapter
import com.example.reddit_top_posts.modelandview.PostsViewModel
import com.example.reddittopposts.databinding.FragmentPostsBinding
//import dagger.hilt.android.AndroidEntryPoint
//import javax.inject.Inject

//@AndroidEntryPoint
class PostsFragment : Fragment() {
    private lateinit var binding: FragmentPostsBinding
    private val postsViewModel = PostsViewModel()
//    @Inject
    private var postsAdapter = PostsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPostsBinding.inflate(layoutInflater, container, false);
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

            lifecycleScope.launchWhenCreated {
                postsViewModel.postsList.collect {
                    postsAdapter.submitData(it)
                }
            }

            postsAdapter.setOnItemClickListener {
//                val direction = PostsFragmentDirections.(it.id)
//                findNavController().navigate(direction)
            }

            lifecycleScope.launchWhenCreated {
                postsAdapter.loadStateFlow.collect {
                    val state = it.refresh
                }
            }

            posts.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = postsAdapter
            }

//            posts.adapter = postsAdapter.withLoadStateFooter(
//                LoadMoreAdapter {
//                    posts.retry()
//                }
//            )

        }
    }
}