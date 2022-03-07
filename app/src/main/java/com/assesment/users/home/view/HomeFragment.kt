package com.assesment.users.home.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.assesment.users.R
import com.assesment.users.common.models.User
import com.assesment.users.common.toggleVisibility
import com.assesment.users.databinding.FragmentHomeBinding
import com.assesment.users.databinding.ItemUserListBinding
import com.assesment.users.home.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val homeViewModel: HomeViewModel by viewModel()
    private val rvAdapter = UserRecyclerViewAdapter()
    private var userSelected : User? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun onStart() {
        super.onStart()
        initData()
    }

    private fun initData() {
        homeViewModel.getUsers()
    }

    private fun initView() {
        setupRvAdapter()
        setupUserRecyclerView()
        setupAddButton()
        setupCloseButton()
        setObservers()
    }

    private fun setupUserRecyclerView() {
        binding.rvUsers.adapter = rvAdapter
    }

    private fun setObservers(){
        homeViewModel.users.observe(viewLifecycleOwner, usersObserver)
    }

    private fun setupAddButton() {
        binding.ivAdd.setOnClickListener {
            findNavController().navigate(
                R.id.profileFragment, bundleOf(
                    "UPDATE_MODE" to false
                )
            )
        }
    }

    private fun setupCloseButton(){
        binding.ivAdd.toggleVisibility()
        binding.ivEdit.toggleVisibility()
        binding.ivRemove.toggleVisibility()
        binding.ivClose.toggleVisibility()
    }

    private fun setupRvAdapter(){
        rvAdapter.onUserSelectedListener = object : OnUserSelectedListener {
            override fun onUserSelected(user: User, itemBinding: ItemUserListBinding) {
                userSelected = user
                onUserItemLongClickPressed(itemBinding)
            }
        }
    }

    private fun onUserItemLongClickPressed(itemBinding: ItemUserListBinding){
        binding.ivAdd.toggleVisibility()
        binding.ivEdit.toggleVisibility()
        binding.ivRemove.toggleVisibility()
        binding.ivClose.toggleVisibility()
        //TODO Unselect background
    }

    private val usersObserver = Observer<List<User>> {
        rvAdapter.submitList(it)
    }


}