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
import com.assesment.users.common.*
import com.assesment.users.common.models.User
import com.assesment.users.databinding.FragmentProfileBinding
import com.assesment.users.profile.viewmodel.ProfileViewModel
import com.facebook.drawee.view.SimpleDraweeView
import com.google.gson.Gson
import org.koin.androidx.viewmodel.ext.android.viewModel

const val UPDATE_MODE = "UPDATE_MODE"
const val USER_DATA = "USER_DATA"

class ProfileFragment : Fragment() {

    private val profileViewModel: ProfileViewModel by viewModel()
    private lateinit var binding: FragmentProfileBinding
    private val viewMode = MutableLiveData<ViewMode>()
    private val avatarSelected = MutableLiveData<SimpleDraweeView>()
    private var avatarValue: String = AVATAR_1_URL

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
        setupSaveButton()
        setupAvatarImages()
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

    private fun setupAvatarImages() {
        binding.avatar1.apply {
            setImageURI(AVATAR_1_URL)
            setOnClickListener {
                avatarSelected.value = this
                avatarValue = AVATAR_1_URL
            }
            setSelected(requireContext())
        }

        binding.avatar2.apply {
            setImageURI(AVATAR_2_URL)
            setOnClickListener {
                avatarSelected.value = this
                avatarValue = AVATAR_2_URL
            }
        }

        binding.avatar3.apply {
            setImageURI(AVATAR_3_URL)
            setOnClickListener {
                avatarSelected.value = this
                avatarValue = AVATAR_3_URL
            }
        }

        binding.avatar4.apply {
            setImageURI(AVATAR_4_URL)
            setOnClickListener {
                avatarSelected.value = this
                avatarValue = AVATAR_4_URL
            }
        }

        binding.avatar5.apply {
            setImageURI(AVATAR_5_URL)
            setOnClickListener {
                avatarSelected.value = this
                avatarValue = AVATAR_5_URL
            }
        }

        binding.avatar6.apply {
            setImageURI(AVATAR_6_URL)
            setOnClickListener {
                avatarSelected.value = this
                avatarValue = AVATAR_6_URL
            }
        }

    }

    private fun setupSaveButton() {
        binding.ivSave.setOnClickListener {
            if (validateInputFields()) {
                profileViewModel.updateUser(getUserDataFromInputFields(getEnteredUserData().id))
            }
        }
    }

    private fun setupEditButton() {
        binding.ivEdit.setOnClickListener {
            viewMode.value = ViewMode.EDIT_PROFILE
        }
    }

    private fun setupCloseButton() {
        binding.ivClose.setOnClickListener {
            viewMode.value = ViewMode.VIEW_PROFILE
        }
    }

    private fun setupBackButton() {
        binding.ivBack.setOnClickListener {
            closeFragment()
        }
    }

    private fun getEnteredUserData(): User {
        val rawUser = arguments?.getString(USER_DATA)
        return Gson().fromJson(rawUser, User::class.java)
    }

    private fun setUserDataToEditFields(user: User) {
        binding.apply {
            etName.setText(user.name)
            etBio.setText(user.bio)
            avatarValue = user.avatarUrl
        }
    }

    private fun setUserDataToViewFields(user: User) {
        binding.apply {
            tvName.text = user.name
            tvBio.text = user.bio
            avatarSelected.setImageURI(user.avatarUrl)
        }
    }

    private fun setObservers() {
        profileViewModel.isUserAdded.observe(viewLifecycleOwner, isUserAddedObserver)
        profileViewModel.isUserUpdated.observe(viewLifecycleOwner, isUserUpdatedObserver)
        avatarSelected.observe(viewLifecycleOwner, avatarSelectedObserver)
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

    private val isUserUpdatedObserver = Observer<User> {
        viewMode.value = ViewMode.VIEW_PROFILE
        setUserDataToViewFields(it)
    }

    private fun setupCreateUserButton() {
        binding.btCreate.setOnClickListener {
            if (validateInputFields()) {
                profileViewModel.addUser(getUserDataFromInputFields())
            }
        }
    }

    private fun validateInputFields(): Boolean {
        return binding.etName.isNotEmpty(getString(R.string.app_name))
                && binding.etBio.isNotEmpty(getString(R.string.app_name))
    }

    private fun getUserDataFromInputFields(id: String? = null): User {
        return id?.let {
            User(
                name = binding.etName.text.toString(),
                bio = binding.etBio.text.toString(),
                avatarUrl = avatarValue,
                id = it
            )
        } ?: User(
            name = binding.etName.text.toString(),
            bio = binding.etBio.text.toString(),
            avatarUrl = avatarValue
        )
    }

    private fun closeFragment() {
        removeObservers()
        findNavController().popBackStack()
    }

    private fun removeObservers() {
        profileViewModel.isUserAdded.removeObserver(isUserAddedObserver)
        profileViewModel.isUserUpdated.removeObserver(isUserUpdatedObserver)
        viewMode.removeObserver(viewModeObserver)
        avatarSelected.removeObserver(avatarSelectedObserver)
    }

    private val avatarSelectedObserver = Observer<SimpleDraweeView> {
        binding.apply {
            avatar1.setUnselected()
            avatar2.setUnselected()
            avatar3.setUnselected()
            avatar4.setUnselected()
            avatar5.setUnselected()
            avatar6.setUnselected()
        }
        it.setSelected(requireContext())
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
            avatarSelected.hide()
            avatarGroup.show()
            etName.show()
            etBio.show()
            btCreate.show()
        }
    }

    private fun onViewProfileMode() {
        setUserDataToViewFields(getEnteredUserData())
        binding.apply {
            etName.hide()
            etBio.hide()
            ivClose.hide()
            ivSave.hide()
            btCreate.hide()
            avatarGroup.hide()
            avatarSelected.show()
            ivEdit.show()
            tvBio.show()
            tvName.show()
        }
    }

    private fun onEditProfileMode() {
        setUserDataToEditFields(getEnteredUserData())
        binding.apply {
            tvBio.hide()
            tvName.hide()
            btCreate.hide()
            ivEdit.hide()
            avatarSelected.hide()
            avatarGroup.show()
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