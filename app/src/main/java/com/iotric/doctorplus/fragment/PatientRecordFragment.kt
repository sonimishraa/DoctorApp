package com.iotric.doctorplus.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.iotric.doctorplus.R
import com.iotric.doctorplus.databinding.PatientRecordFragmentBinding
import com.iotric.doctorplus.viewmodel.PatientRecordViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PatientRecordFragment : BaseFragment() {

    val viewModel: PatientRecordViewModel by viewModels()
    val args: PatientRecordFragmentArgs by navArgs()
    private lateinit var binding: PatientRecordFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        binding = PatientRecordFragmentBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setArgs()
    }

    private fun initView() {
        binding.toolbarTitle.text = getString(R.string.patient_record)
        binding.toolbar.setNavigationOnClickListener { view ->
            findNavController().popBackStack()
        }
        binding.toolbar.inflateMenu(R.menu.main)
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.add_new_report -> {

                }
                R.id.update_report -> {

                }
            }


            true
        }
        /*  val pagerAdapter = PatientRecordPagerAdapter(childFragmentManager)
          val viewPager = binding.viewPager
          viewPager.adapter = pagerAdapter
          binding.tablayout.setupWithViewPager(viewPager)*/
    }
/*
override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
    inflater.inflate(R.menu.report_option_menu, menu)

}*/

/*  override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
      inflater.inflate(R.menu.report_option_menu, menu)

  }
*/
/*override fun onOptionsItemSelected(item: MenuItem): Boolean {
    super.onOptionsItemSelected(item)
    when (item.itemId) {
        R.id.add_new_report -> {

        }
        R.id.update_report -> {

        }
    }
    return true
}*/

/*  private fun optionMenuOpen() {
      val popup = PopupMenu(requireContext(), view)
      popup.menuInflater.inflate(R.menu.report_option_menu, popup.menu)
      popup.setOnMenuItemClickListener {
          when (it.itemId) {
              R.id.add_new_report -> {

              }
              R.id.update_report -> {

              }
          }
          true
      }
      popup.show()

  }*/

    private fun setArgs() {
        val argsItem = args.result
        val visitItem = argsItem.visit?.firstOrNull()
        binding.tvName.text = argsItem.pname
        binding.tvContact.text = argsItem.pphone
        binding.tvEmail.text = argsItem.pemail
        binding.tvLastVisit.text = visitItem?.nextvisitdate + " " + visitItem?.nextvisittime ?: ""
    }

}

