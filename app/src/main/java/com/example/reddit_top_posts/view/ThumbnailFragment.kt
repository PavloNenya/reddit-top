package com.example.reddit_top_posts.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.load
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.reddit_top_posts.view.listeners.ZoomListener
import com.example.reddit_top_posts.viewmodel.ThumbnailViewModel
import com.example.reddit_top_posts.viewmodel.ViewModelFactory
import com.example.reddittopposts.R
import com.example.reddittopposts.databinding.FragmentThumbnailImageBinding

class ThumbnailFragment : Fragment() {
    private lateinit var binding: FragmentThumbnailImageBinding
    private lateinit var thumbnailUrl: String
    private lateinit var scaleGestureDetector: ScaleGestureDetector
    private lateinit var zoomListener: ZoomListener
    private val args: ThumbnailFragmentArgs by navArgs()
    private val viewModel: ThumbnailViewModel by viewModels { ViewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentThumbnailImageBinding.inflate(layoutInflater, container, false)
        val width = activity?.windowManager?.defaultDisplay?.width!!
        val height = activity?.windowManager?.defaultDisplay?.height!!

        zoomListener = ZoomListener(
            binding.thumbnailPicture,
            height,
            width,
            activity?.resources?.configuration?.orientation
        )
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        thumbnailUrl = args.thumbnailUrl

    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            thumbnailPicture.load(thumbnailUrl) {
                placeholder(R.drawable.image_placeholder)
                error(R.drawable.error_picture)
                listener(
                    onSuccess = { _: ImageRequest, _: SuccessResult ->
                        progressBar.visibility = View.GONE
                        scaleGestureDetector =
                            ScaleGestureDetector(binding.root.context, zoomListener)
                        root.setOnTouchListener { view: View, event: MotionEvent ->
                            scaleGestureDetector.onTouchEvent(event)
                            zoomListener.onTouch(view, event)
                            true
                        }
                        downloadButton.setOnClickListener {
                            viewModel.saveImage(thumbnailUrl, thumbnailPicture)
                        }
                    },
                    onError = { _, _ ->
                        progressBar.visibility = View.GONE
                    }
                )
            }


        }
    }
}