package com.vungn.mytlumdc.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.vungn.mytlumdc.R
import com.vungn.mytlumdc.databinding.FragmentMainBinding
import com.vungn.mytlumdc.ui.home.adapter.ViewpagerAdapter
import com.vungn.mytlumdc.ui.home.views.FeatureFragment
import com.vungn.mytlumdc.ui.home.views.HomeFragment
import com.vungn.mytlumdc.ui.home.views.NotificationFragment
import com.vungn.mytlumdc.ui.home.views.SettingFragment
import com.vungn.mytlumdc.ui.login.LoginFragment
import com.vungn.mytlumdc.ui.user.UserViewModel
import com.vungn.mytlumdc.ui.user.impl.UserViewModelImpl
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewpagerAdapter: ViewpagerAdapter
    private val userViewModel: UserViewModel by activityViewModels<UserViewModelImpl>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navController = findNavController()

        val currentBackStackEntry = navController.currentBackStackEntry!!
        val savedStateHandle = currentBackStackEntry.savedStateHandle
        savedStateHandle.getLiveData<Boolean>(LoginFragment.LOGIN_SUCCESSFUL)
            .observe(currentBackStackEntry) { success ->
                if (!success) {
                    requireActivity().finish()
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val homeFragment = HomeFragment()
        val featureFragment = FeatureFragment()
        val notificationFragment = NotificationFragment()
        val settingFragment = SettingFragment()
        val navController = findNavController()
        userViewModel.user.observe(viewLifecycleOwner) { user ->
            if (user != null) {
                Toast.makeText(this.context, "Login success", Toast.LENGTH_SHORT).show()
            } else {
                navController.navigate(R.id.loginFragment)
            }
        }
        loadFragment(homeFragment)
        viewpagerAdapter = ViewpagerAdapter(this)
        binding.navigationBar.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeFragment -> {
                    loadFragment(homeFragment)
                    true
                }

                R.id.featureFragment -> {
                    loadFragment(featureFragment)
                    true
                }

                R.id.notificationFragment -> {
                    loadFragment(notificationFragment)
                    true
                }

                R.id.settingFragment -> {
                    loadFragment(settingFragment)
                    true
                }

                else -> {
                    false
                }
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction: FragmentTransaction =
            requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(binding.fragment.id, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}