package com.assesment.users.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.assesment.users.R
import com.assesment.users.common.isNotEmpty
import com.assesment.users.common.models.User
import com.assesment.users.databinding.FragmentProfileBinding
import com.assesment.users.profile.viewmodel.ProfileViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

const val UPDATE_MODE = "UPDATE_MODE"

class ProfileFragment : Fragment() {

    private val profileViewModel: ProfileViewModel by viewModel()
    private lateinit var binding: FragmentProfileBinding

    private var updateMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            updateMode = it.getBoolean(UPDATE_MODE)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
        initView()
        initData()
    }

    private fun initView() {
        setupCreateUserButton()
    }

    private fun initData() {

    }

    private fun setObservers() {
        profileViewModel.isUserAdded.observe(viewLifecycleOwner, isUserAddedObserver)
    }

    private val isUserAddedObserver = Observer<Boolean> {
        when (it) {
            true -> {
                Toast.makeText(requireContext(), getString(R.string.app_name), Toast.LENGTH_SHORT)
                    .show()
                findNavController().navigateUp()
            }
            false -> Toast.makeText(
                requireContext(),
                getString(R.string.app_name),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun setupCreateUserButton() {
        binding.btCreate.setOnClickListener {
            if (
                binding.etName.isNotEmpty(getString(R.string.app_name))
                && binding.etBio.isNotEmpty(getString(R.string.app_name))
            ) {
                profileViewModel.addUser(
                    User(
                        name = binding.etName.text.toString(),
                        bio = binding.etBio.text.toString(),
                        avatarId = 1
                    )
                )
            }
        }
    }
}