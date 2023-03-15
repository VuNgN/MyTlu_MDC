package com.vungn.mytlumdc.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.vungn.mytlumdc.databinding.FragmentLoginBinding
import com.vungn.mytlumdc.ui.user.UserViewModel
import com.vungn.mytlumdc.ui.user.impl.UserViewModelImpl
import com.vungn.mytlumdc.util.hideSoftKeyboard
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {
    companion object {
        const val LOGIN_SUCCESSFUL: String = "LOGIN_SUCCESSFUL"
    }

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val userViewModel: UserViewModel by activityViewModels<UserViewModelImpl>()
    private lateinit var savedStateHandle: SavedStateHandle

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        savedStateHandle = findNavController().previousBackStackEntry!!.savedStateHandle
        savedStateHandle[LOGIN_SUCCESSFUL] = false

        setupUI(view)
        handleEvents()
    }

    private fun setupUI(view: View) {
        // Set up touch listener for non-text box views to hide keyboard.
        if (view !is EditText) {
            view.setOnClickListener {
                requireActivity().hideSoftKeyboard()
            }
        }

        //If a layout container, iterate over children and seed recursion.
        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                val innerView = view.getChildAt(i)
                setupUI(innerView)
            }
        }
    }

    private fun handleEvents() {
        binding.apply {
            passwordEdittext.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    val username = usernameEdittext.text.toString()
                    val password = passwordEdittext.text.toString()
                    login(username, password)
                    true
                } else {
                    false
                }
            }
            loginButton.setOnClickListener {
                val username = usernameEdittext.text.toString()
                val password = passwordEdittext.text.toString()
                login(username, password)
            }
        }
    }

    private fun login(username: String, password: String) {
        lifecycleScope.launch {
            userViewModel.login(username, password).collect { result ->
                if (result.success) {
                    savedStateHandle[LOGIN_SUCCESSFUL] = true
                    findNavController().popBackStack()
                } else {
                    showErrorMessage()
                }
            }
        }
    }

    private fun showErrorMessage() {
        Toast.makeText(this.context, "Login false", Toast.LENGTH_SHORT).show()
    }
}