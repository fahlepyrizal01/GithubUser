package com.fahlepyrizal01.githubusers.presenter.fragment

import android.content.Context
import android.content.res.Configuration.UI_MODE_NIGHT_MASK
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.fahlepyrizal01.githubusers.databinding.FragmentSplashBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import android.R.color as androidColorResource

class SplashFragment : Fragment() {

    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        context?.isDarkTheme().let {
            @Suppress("DEPRECATION")
            binding.tvName.setTextColor(resources.getColor(
                if (it == true) androidColorResource.white else androidColorResource.black
            ))
        }

        lifecycleScope.launch {
            delay(THIRD_SECOND)
            super.getView()?.findNavController()?.navigate(
                SplashFragmentDirections.actionSplashFragmentToSearchFragment()
            )
        }

    }

    private fun Context.isDarkTheme(): Boolean {
        return resources.configuration.uiMode and UI_MODE_NIGHT_MASK == UI_MODE_NIGHT_YES
    }

    companion object {
        const val THIRD_SECOND = 3000L
    }

}