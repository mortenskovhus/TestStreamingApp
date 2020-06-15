package com.forestmouse.samples.streaming.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.forestmouse.samples.streaming.R
import com.forestmouse.samples.streaming.extensions.setVisibleOrGone
import com.forestmouse.samples.streaming.viewmodel.MainViewModel
import com.squareup.picasso.Picasso
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_details.*
import javax.inject.Inject

class DetailsFragment : DaggerFragment() {

    @Inject
    lateinit var mainViewModel: MainViewModel

    private var position: Int = -1
    private var title: String? = null
    private var imageUrl: String? = null
    private var body: String? = null

    companion object {
        private const val ARGS_POSITION = "args_position"
        private const val ARGS_TITLE_TEXT = "args_title_text"
        private const val ARGS_IMAGE_URL = "args_image_url"
        private const val ARGS_BODY_TEXT = "args_body_text"

        fun newInstance(
                position: Int,
                titleText: String?,
                imageUrl: String?,
                bodyText: String?
        ) =
                DetailsFragment().apply {
                    arguments = Bundle().apply {
                        putInt(ARGS_POSITION, position)
                        putString(ARGS_TITLE_TEXT, titleText)
                        putString(ARGS_IMAGE_URL, imageUrl)
                        putString(ARGS_BODY_TEXT, bodyText)
                    }
                }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        position = arguments?.getInt(ARGS_POSITION, -1) ?: return
        if (position < 0) return

        imageUrl = arguments?.getString(ARGS_IMAGE_URL, "") ?: return
        Picasso.get().load(imageUrl).into(postImage)

        title = arguments?.getString(ARGS_TITLE_TEXT, "") ?: return
        titleText.text = title ?: return

        body = arguments?.getString(ARGS_BODY_TEXT, "") ?: return
        bodyText.text = body ?: return

        showComments()
        showCommentLoading()
        showCommentTimeout()
    }

    private fun showComments() {
        mainViewModel.selectedPostIdLiveData.observe(
                viewLifecycleOwner,
                Observer { selectedPostPosition ->
                    selectedPostPosition?.let {
                        mainViewModel.getComments(it)

                        mainViewModel.commentsLiveData.observe(
                                viewLifecycleOwner,
                                Observer { comments ->
                                    comments?.let {
                                        if (!comments.isNullOrEmpty()) {
                                            comments.sortByDescending { it.id }
                                            detailsRecyclerView.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
                                            detailsRecyclerView.adapter = DetailsAdapter(comments.filter { !it.name.isNullOrEmpty() && !it.body.isNullOrEmpty() }.toMutableList())
                                        }
                                    }
                                })
                    }
                })
    }

    private fun showCommentLoading() {
        mainViewModel.isLoadingComments.observe(viewLifecycleOwner, Observer { isLoading ->
            commentsProgressbar.setVisibleOrGone(isLoading)
        })
        detailsRecyclerView.adapter?.notifyDataSetChanged()
    }

    private fun showCommentTimeout() {
        mainViewModel.isTimeoutComments.observe(viewLifecycleOwner, Observer { isTimeout ->
            if (isTimeout) {
                val dialogBuilder = AlertDialog.Builder(requireContext())
                dialogBuilder.setMessage(R.string.alert_msg)
                        .setCancelable(false)
                        .setPositiveButton(R.string.alert_retry) { dialogInterface, _ ->
                            dialogInterface?.dismiss()
                            commentsProgressbar.setVisibleOrGone(true)
                            showComments()
                        }
                        .setNegativeButton(R.string.alert_cancel) { dialogInterface, _ ->
                            dialogInterface?.dismiss()
                        }

                val alert = dialogBuilder.create()
                alert.setTitle(R.string.alert_title)
                alert.show()
            }
        })
    }
}
