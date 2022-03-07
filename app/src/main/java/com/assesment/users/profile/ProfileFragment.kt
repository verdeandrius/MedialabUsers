package com.assesment.users.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.assesment.users.R
import com.assesment.users.common.hide
import com.assesment.users.common.isNotEmpty
import com.assesment.users.common.models.User
import com.assesment.users.common.show
import com.assesment.users.databinding.FragmentProfileBinding
import com.assesment.users.profile.viewmodel.ProfileViewModel
import com.google.gson.Gson
import org.koin.androidx.viewmodel.ext.android.viewModel

const val UPDATE_MODE = "UPDATE_MODE"
const val USER_DATA = "USER_DATA"

class ProfileFragment : Fragment() {

    private val profileViewModel: ProfileViewModel by viewModel()
    private lateinit var binding: FragmentProfileBinding
    private val viewMode = MutableLiveData<ViewMode>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        setObservers()
        setViewMode()
        setupCreateUserButton()
        setupEditButton()
        setupBackButton()
        setupCloseButton()
    }

    private fun setViewMode() {
        arguments?.let {
            if (it.containsKey(USER_DATA)) {
                if (it.getBoolean(UPDATE_MODE)) {
                    viewMode.value = ViewMode.EDIT_PROFILE
                } else {
                    viewMode.value = ViewMode.VIEW_PROFILE
                }
            } else {
                viewMode.value = ViewMode.CREATE_PROFILE
            }
        }
    }

    private fun setupEditButton(){
        binding.ivEdit.setOnClickListener {
            viewMode.value = ViewMode.EDIT_PROFILE
        }
    }

    private fun setupCloseButton(){
        binding.ivClose.setOnClickListener {
            viewMode.value = ViewMode.VIEW_PROFILE
        }
    }

    private fun setupBackButton(){
        binding.ivBack.setOnClickListener {
            closeFragment()
        }
    }
    private fun getEnteredUserData(): User {
        val rawUser = arguments?.getString(USER_DATA)
        return Gson().fromJson(rawUser, User::class.java)
    }

    private fun setUserDataToEditText(user: User) {
        binding.etName.setText(user.name)
        binding.etBio.setText(user.bio)
    }

    private fun setUserDataToTextView(user: User) {
        binding.tvName.text = user.name
        binding.tvBio.text = user.bio
    }

    private fun setObservers() {
        profileViewModel.isUserAdded.observe(viewLifecycleOwner, isUserAddedObserver)
        viewMode.observe(viewLifecycleOwner, viewModeObserver)
    }

    private val isUserAddedObserver = Observer<Boolean> {
        when (it) {
            true -> {
                Toast.makeText(requireContext(), getString(R.string.app_name), Toast.LENGTH_SHORT)
                    .show()
                closeFragment()
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

    private fun closeFragment(){
        removeObservers()
        findNavController().popBackStack()
    }

    private fun removeObservers() {
        profileViewModel.isUserAdded.removeObserver(isUserAddedObserver)
        viewMode.removeObserver(viewModeObserver)
    }

    @Suppress("WHEN_ENUM_CAN_BE_NULL_IN_JAVA")
    private val viewModeObserver = Observer<ViewMode> { viewMode ->
        when (viewMode) {
            ViewMode.VIEW_PROFILE -> onViewProfileMode()
            ViewMode.EDIT_PROFILE -> onEditProfileMode()
            ViewMode.CREATE_PROFILE -> onCreateProfileMode()
        }
    }

    private fun onCreateProfileMode() {
        binding.apply {
            tvBio.hide()
            tvName.hide()
            ivEdit.hide()
            ivClose.hide()
            ivSave.hide()
            etName.show()
            etBio.show()
            btCreate.show()
        }
    }

    private fun onViewProfileMode() {
        setUserDataToTextView(getEnteredUserData())
        binding.apply {
            etName.hide()
            etBio.hide()
            ivClose.hide()
            ivSave.hide()
            btCreate.hide()
            ivEdit.show()
            tvBio.show()
            tvName.show()
        }
    }

    private fun onEditProfileMode() {
        setUserDataToEditText(getEnteredUserData())
        binding.apply {
            tvBio.hide()
            tvName.hide()
            btCreate.hide()
            ivEdit.hide()
            ivClose.show()
            ivSave.show()
            etName.show()
            etBio.show()
        }
    }
}

private enum class ViewMode {
    VIEW_PROFILE,
    EDIT_PROFILE,
    CREATE_PROFILE
}