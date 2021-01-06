package com.fahlepyrizal01.githubusers.presenter.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.fahlepyrizal01.core.ui.ViewModelFactory
import com.fahlepyrizal01.core.utils.Status
import com.fahlepyrizal01.githubusers.MyApplication
import com.fahlepyrizal01.githubusers.R
import com.fahlepyrizal01.githubusers.databinding.FragmentSearchBinding
import com.fahlepyrizal01.githubusers.presenter.adapter.UserRecyclerViewAdapter
import com.fahlepyrizal01.githubusers.presenter.viewmodel.SearchViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelFactory

    private val viewModel: SearchViewModel by viewModels {
        factory
    }

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val recyclerViewAdapter by lazy {
        UserRecyclerViewAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    @FlowPreview
    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        binding.apply {

            svSearch.isIconifiedByDefault = false
            svSearch.requestFocus()

            viewModel.search.observe(viewLifecycleOwner, {
                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.searchUser(it)
                    setObserveUsers()
                }
            })

            svSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    viewLifecycleOwner.lifecycleScope.launch {
                        viewModel.queryChannel.send(query.orEmpty())
                    }

                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    viewLifecycleOwner.lifecycleScope.launch {
                        viewModel.queryChannel.send(newText.orEmpty())
                    }

                    return false
                }

            })

            cvSearch.setOnClickListener { svSearch.requestFocus() }

        }

    }

    private fun setObserveUsers() = with(binding) {
        viewModel.searchUserPagedList?.observe(viewLifecycleOwner) { user ->
            recyclerViewAdapter.submitList(user)
        }

        viewModel.searchUserNetworkState?.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.FIRST_LOADING -> {
                    grpEmptyOrError.visibility = GONE
                    pbSearch.visibility = VISIBLE
                }
                Status.LOADING -> {
                    if (recyclerViewAdapter.currentList?.size ?: DEFAULT_LIST_SIZE > MINIMUM_LIST_SIZE) {
                        pbLoadMore.visibility = VISIBLE
                    }
                }
                Status.SUCCESS -> {
                    pbSearch.visibility = GONE
                    pbLoadMore.visibility = GONE
                }
                Status.FIRST_EMPTY_DATA -> {
                    setState(false)
                    pbSearch.visibility = GONE
                }
                Status.EMPTY_DATA -> {
                    pbLoadMore.visibility = GONE
                    if (recyclerViewAdapter.currentList?.size ?: DEFAULT_LIST_SIZE > MINIMUM_LIST_SIZE) {
                        Toast.makeText(
                            context,
                            resources.getString(R.string.load_more_empty_message),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
                Status.FIRST_FAILED -> {
                    setState(true)
                    pbSearch.visibility = GONE
                }
                else -> {
                    pbLoadMore.visibility = GONE
                    Toast.makeText(
                        context,
                        resources.getString(R.string.error_message),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as MyApplication).appComponent.inject(this)
    }

    override fun onDestroyView() {
        binding.rvUser.apply {
            addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
                override fun onViewAttachedToWindow(v: View?) {}

                override fun onViewDetachedFromWindow(v: View?) {
                    adapter = null
                }
            })
        }

        _binding = null

        super.onDestroyView()
    }

    private fun setupRecyclerView() = with(binding.rvUser) {
        layoutManager = LinearLayoutManager(context)
        adapter = recyclerViewAdapter
    }

    private fun setState(isError: Boolean) = with(binding) {
        grpEmptyOrError.visibility = VISIBLE
        ivEmptyOrError.setImageResource(if (isError) R.drawable.ic_error else R.drawable.ic_empty_user)
        tvEmptyOrError.text = resources.getString(
            if (isError) R.string.error_message else R.string.search_not_found
        )
    }

    companion object {
        const val DEFAULT_LIST_SIZE = 0
        const val MINIMUM_LIST_SIZE = 10
    }

}