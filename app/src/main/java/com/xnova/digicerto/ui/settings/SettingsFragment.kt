package com.xnova.digicerto.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.xnova.digicerto.R
import com.xnova.digicerto.databinding.FragmentSettingsBinding
import com.xnova.digicerto.services.adapters.SettingsAdapter
import com.xnova.digicerto.services.enums.AlertDialogType
import com.xnova.digicerto.services.enums.OperationType
import com.xnova.digicerto.services.factories.AlertDialogFactory
import com.xnova.digicerto.services.listeners.LoginListener
import com.xnova.digicerto.ui.login.LoginFragment
import com.xnova.digicerto.ui.main.MainActivity

class SettingsFragment : Fragment() {

    private val mLoginFragment = LoginFragment()
    private val mSettingsAdapter = SettingsAdapter()

    private lateinit var mViewModel: SettingsViewModel
    private var mBinding: FragmentSettingsBinding? = null

    private fun binding() = mBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mViewModel = ViewModelProvider(this).get(SettingsViewModel::class.java)
        mBinding = FragmentSettingsBinding.inflate(inflater, container, false)
        val root = binding().root

        observers()
        listeners()
        setRecylerViews()
        login()
        necessaryChooseTypeOperation()

        return root
    }

    override fun onResume() {
        super.onResume()
        setComponents()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }

    private fun observers() {
        settingsListPairObserver()
    }

    private fun settingsObserver() {
        //mViewModel.
    }

    private fun settingsListPairObserver() {
        mViewModel.settingsListPair.observe(requireActivity(), {
            mSettingsAdapter.updateSettings(it)
        })
    }

    private fun listeners() {
        loginListener()
    }

    private fun loginListener() {
        mLoginFragment.onAttach(object : LoginListener {
            override fun authenticate(authenticated: Boolean) {
                if (!authenticated) {
                    homePageChange()
                }
            }
        })
    }

    private fun setRecylerViews() {
        setSettingsRecylerView()
    }

    private fun setSettingsRecylerView() {
        val recycler = binding().recyclerSettings
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.adapter = mSettingsAdapter
    }

    private fun login() {
        if (!mViewModel.loginAvailable()) {
            showAlert(R.string.text_access_denied, R.string.msg_first_register)
            homePageChange()
            return
        }

        mLoginFragment.show(parentFragmentManager, LoginFragment.TAG)
    }

    private fun necessaryChooseTypeOperation() {
        if (mViewModel.necessaryChooseTypeOperation()) {
            val builder = AlertDialog.Builder(requireContext())
            builder.setMessage(R.string.msg_necessary_choosen_operation_type)
                .setCancelable(false)
                .setTitle(R.string.text_necessary_action)
                .setNegativeButton(R.string.text_FTP) { dialog, _ ->
                    mViewModel.setOperationType(OperationType.FTP)
                    dialog.dismiss()
                    setComponents()
                }
                .setPositiveButton(R.string.text_web_service) { dialog, _ ->
                    mViewModel.setOperationType(OperationType.WebService)
                    dialog.dismiss()
                    setComponents()
                }

            return builder.create().show()
        } else {
            setComponents()
        }
    }

    private fun setComponents() {
        mViewModel.loadSettings()
    }

    private fun showAlert(titleId: Int, messageId: Int) {
        AlertDialogFactory(requireContext())
            .getInstance(AlertDialogType.Info, titleId, messageId)
            .show()
    }

    private fun homePageChange() {
        (activity as MainActivity).changeFragment(R.id.nav_home)
    }
}