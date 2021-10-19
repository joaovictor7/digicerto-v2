package com.xnova.digicerto.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.xnova.digicerto.R
import com.xnova.digicerto.databinding.FragmentSettingsBinding
import com.xnova.digicerto.models.MenuSettings
import com.xnova.digicerto.ui.adapters.MenuSettingsAdapter
import com.xnova.digicerto.services.constants.SettingsConstants
import com.xnova.digicerto.services.enums.settings.OperationType
import com.xnova.digicerto.services.factories.AlertFactory
import com.xnova.digicerto.services.listeners.LoginListener
import com.xnova.digicerto.services.listeners.MenuSettingsListener
import com.xnova.digicerto.ui.BaseFragment
import com.xnova.digicerto.ui.login.LoginFragment
import com.xnova.digicerto.ui.main.MainActivity
import com.xnova.digicerto.ui.settings.application.SettingsApplicationActivity
import com.xnova.digicerto.ui.settings.collect.SettingsCollectActivity
import com.xnova.digicerto.ui.settings.file.SettingsFileActivity
import com.xnova.digicerto.ui.settings.ftp.SettingsFTPActivity
import com.xnova.digicerto.ui.settings.printer.SettingsPrinterActivity
import com.xnova.digicerto.ui.settings.travel.SettingsTravelActivity
import com.xnova.digicerto.ui.settings.ws.SettingsWSActivity

class SettingsFragment : BaseFragment() {

    private val mLoginFragment = LoginFragment()
    private val mMenuSettingsAdapter = MenuSettingsAdapter()
    private var mBinding: FragmentSettingsBinding? = null
    private lateinit var mViewModel: SettingsViewModel

    private fun binding() = mBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentSettingsBinding.inflate(inflater, container, false)
        mViewModel = ViewModelProvider(this).get(SettingsViewModel::class.java)
        viewRoot = binding().root

        listeners()
        observers()
        adapters()
        login()

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        mViewModel.refreshEntities()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }

    private fun observers() {
        mViewModel.refreshScreen.observe(requireActivity(), {
            setComponents()
        })
    }

    private fun listeners() {
        mLoginFragment.setListener(object : LoginListener {
            override fun authenticate(authenticated: Boolean) {
                if (!authenticated) {
                    homePageChange()
                    return
                }

                if (mViewModel.settings.necessaryChooseTypeOperation) {
                    showChooseTypeOperationAlert()
                }
            }
        })

        mMenuSettingsAdapter.setListener(object : MenuSettingsListener {
            override fun onClick(menu: MenuSettings) {
                when (menu.id) {
                    SettingsConstants.MENU.TRAVEL_ID -> openActivity(SettingsTravelActivity::class.java)
                    SettingsConstants.MENU.APPLICATION_ID -> openActivity(
                        SettingsApplicationActivity::class.java
                    )
                    SettingsConstants.MENU.COLLECT_ID -> openActivity(SettingsCollectActivity::class.java)
                    SettingsConstants.MENU.FTP_ID -> openActivity(SettingsFTPActivity::class.java)
                    SettingsConstants.MENU.WS_ID -> openActivity(SettingsWSActivity::class.java)
                    SettingsConstants.MENU.FILE_ID -> openActivity(SettingsFileActivity::class.java)
                    SettingsConstants.MENU.PRINTER_ID -> openActivity(SettingsPrinterActivity::class.java)
                }
            }
        })
    }

    private fun adapters() {
        val recycler = binding().recyclerSettings
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.adapter = mMenuSettingsAdapter
    }

    private fun setComponents() {
        mMenuSettingsAdapter.setMenuSettings(mViewModel.getMenuSettings())
    }

    private fun login() {
        mLoginFragment.show(childFragmentManager, LoginFragment.TAG)
    }

    private fun showChooseTypeOperationAlert() {
        if (mViewModel.settings.necessaryChooseTypeOperation) {
            AlertFactory(requireContext())
                .setCancelable(false)
                .setTitle(R.string.text_necessary_action)
                .setMessage(R.string.msg_necessary_choosen_operation_type)
                .setPositiveButton(R.string.text_web_service) { dialog, _ ->
                    dialog.dismiss()
                    mViewModel.setOperationType(OperationType.WebService)
                    mViewModel.save()
                }
                .setNegativeButton(R.string.text_FTP) { dialog, _ ->
                    dialog.dismiss()
                    mViewModel.setOperationType(OperationType.FTP)
                    mViewModel.save()
                }
                .show()
        }
    }

    private fun homePageChange() {
        (activity as MainActivity).changeFragment(R.id.nav_home)
    }
}