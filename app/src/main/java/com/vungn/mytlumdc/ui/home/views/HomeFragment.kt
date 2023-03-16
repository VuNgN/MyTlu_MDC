package com.vungn.mytlumdc.ui.home.views

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.ColorUtils
import androidx.fragment.app.Fragment
import com.google.android.material.color.MaterialColors
import com.vungn.mytlumdc.databinding.FragmentHomeBinding
import com.vungn.mytlumdc.util.isDarkThemeOn
import kotlin.properties.Delegates

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var isDarkMode by Delegates.notNull<Boolean>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isDarkMode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            resources.configuration.isNightModeActive
        } else {
            requireContext().isDarkThemeOn()
        }
        if (!isDarkMode) {
            view.setBackgroundColor(
                ColorUtils.setAlphaComponent(
                    MaterialColors.getColor(
                        view,
                        com.google.android.material.R.attr.colorPrimary,
                    ),
                    15
                )
            )
        }
    }
}