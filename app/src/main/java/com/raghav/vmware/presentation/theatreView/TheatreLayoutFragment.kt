package com.raghav.vmware.presentation.theatreView

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.setMargins
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.raghav.vmware.R
import com.raghav.vmware.databinding.FragmentTheatreViewBinding
import com.raghav.vmware.domain.model.Result
import com.raghav.vmware.domain.model.Seat
import com.raghav.vmware.domain.model.TheatreInfo
import com.raghav.vmware.domain.utils.toPrecision
import com.raghav.vmware.presentation.utils.SnackBarHelper.Companion.showSnackbarAction
import com.raghav.vmware.presentation.utils.dpToPx
import com.raghav.vmware.presentation.utils.formatResource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TheatreLayoutFragment : Fragment() {

    private val vm: TheatreLayoutViewModel by viewModels()
    private var binding: FragmentTheatreViewBinding? = null
    private val selectedViewList = ArrayList<View>()

    companion object {
        const val UNOCCUPIED = 1
        const val OCCUPIED = 2
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_theatre_view, container, false)
        binding = FragmentTheatreViewBinding.bind(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toggleProgressView(true)
        vm.loadTheatreInfo().observe(viewLifecycleOwner) {
            if (it is Result.Success) {
                buildLayout(it.data!!)
            } else {
                showSnackbarAction(view, R.string.retry, R.string.failed_to_load) {
                    vm.loadTheatreInfo()
                }
            }
            toggleProgressView(false)
        }
        vm.grossPrice.observe(viewLifecycleOwner) {
            binding?.totalTicketPrice?.text = String.formatResource(requireContext(), R.string.price_format, it.toPrecision(2).toString())
        }
        binding?.buyBtn?.setOnClickListener {
            val options = NavOptions.Builder()
                .setPopUpTo(R.id.theatreLayout, true)
                .build()
            findNavController().navigate(R.id.salesuccesspage, Bundle(), options)
        }
    }

    private fun buildLayout(info: TheatreInfo) {
        binding?.seatLayout?.removeAllViews()
        var overallRowPos = 0
        info.sections.forEach { sectionStructure ->
            val sectionLayout = LinearLayout(context)
            sectionLayout.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            sectionLayout.orientation = LinearLayout.VERTICAL
            (sectionLayout.layoutParams as LinearLayout.LayoutParams).setMargins(requireContext().dpToPx(16))
            binding?.seatLayout?.addView(sectionLayout)
            sectionStructure.rows.forEachIndexed { rowPos, rowStructure->
                val rowLayout = LinearLayout(context)
                rowLayout.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                rowLayout.orientation = LinearLayout.HORIZONTAL
                sectionLayout.addView(rowLayout)
                buildRow(sectionStructure.name, rowPos, rowStructure, rowLayout, overallRowPos)
                addLabelRow(rowLayout, overallRowPos)
                overallRowPos++
            }
            val sectionDivider = layoutInflater.inflate(R.layout.section_divider_view, binding?.seatLayout, false)
            val divider = sectionDivider.findViewById<TextView>(R.id.dividerText)
            divider.text = String.formatResource(requireContext(), R.string.section_info,
                sectionStructure.name, sectionStructure.price.toPrecision(2).toString())
            binding?.seatLayout?.addView(sectionDivider)
        }
        val imageView = layoutInflater.inflate(R.layout.screen_this_side_view, binding?.seatLayout, false)
        binding?.seatLayout?.addView(imageView)
    }

    private fun buildRow(sectionName: String, rowPos: Int, row: List<Int>, rowLayout: LinearLayout, overallRowPos: Int) {
        row.forEachIndexed { colPos, seatStatus ->
            if (colPos == 0) {
                addLabelRow(rowLayout, overallRowPos)
            }
            val seatView = layoutInflater.inflate(R.layout.seat_icon_view, rowLayout, false)
            val numberText : TextView = seatView.findViewById(R.id.seatNumber)
            numberText.text = (colPos).toString()
            seatView.tag = Seat(rowPos, colPos, sectionName)
            when (seatStatus) {
                UNOCCUPIED -> {
                    seatView.isEnabled = true
                    seatView.setOnClickListener {
                        updateSelection(it)
                    }
                }
                OCCUPIED -> {
                    seatView.background = ResourcesCompat.getDrawable(requireContext().resources, R.drawable.dark_solid_background, null)
                }
                else -> {
                    seatView.visibility = View.INVISIBLE
                }
            }
            rowLayout.addView(seatView)
        }
    }

    private fun addLabelRow(rowLayout: LinearLayout, overallRowPos: Int) {
        val rowNoView = layoutInflater.inflate(R.layout.seat_icon_view, rowLayout, false)
        val numberText : TextView = rowNoView.findViewById(R.id.seatNumber)
        numberText.text = vm.getRowName(overallRowPos)
        rowLayout.addView(rowNoView)
    }

    private fun updateSelection(selectedView: View) {
        if (selectedViewList.contains(selectedView)) {
            toggleBuyButton(false)
            selectedViewList.remove(selectedView)
            selectedView.isSelected = false
        } else {
            if(selectedViewList.size == 2) {
                toggleBuyButton(false)
                selectedViewList.forEach {
                    it.isSelected = false
                }
                selectedViewList.clear()
            }
            selectedView.isSelected = true
            selectedViewList.add(selectedView)
            vm.updateTotalPrice(selectedViewList.map { it.tag as Seat })
        }

        if (selectedViewList.size == 2) {
            toggleBuyButton(true)
            vm.updateTotalPrice(selectedViewList.map { it.tag as Seat })
        }
    }

    private fun toggleBuyButton(enable: Boolean) {
        binding?.buyBtn?.isEnabled = enable
        binding?.buyBtn?.isClickable = enable
    }

    private fun toggleProgressView(isLoading: Boolean) {
        if (isLoading) {
            binding?.progressView?.visibility = View.VISIBLE
        } else {
            binding?.progressView?.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        selectedViewList.clear()
        binding = null
    }
}