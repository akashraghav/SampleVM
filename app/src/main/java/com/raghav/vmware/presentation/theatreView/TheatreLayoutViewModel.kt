package com.raghav.vmware.presentation.theatreView

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raghav.vmware.domain.dispatcher.AppDispatcher
import com.raghav.vmware.domain.model.Result
import com.raghav.vmware.domain.model.Seat
import com.raghav.vmware.domain.model.TheatreInfo
import com.raghav.vmware.domain.usecase.SeatSelectionUseCase
import com.raghav.vmware.domain.utils.LogUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.math.BigDecimal
import javax.inject.Inject


@HiltViewModel
class TheatreLayoutViewModel @Inject constructor(
    private val seatSelectionUseCase: SeatSelectionUseCase,
    private val dispatcher: AppDispatcher
) : ViewModel() {

    private var selectedSeats: List<Seat>? = null
    private var totalPrice: BigDecimal = BigDecimal(0.0)

    private val _grossPrice = MutableLiveData(totalPrice)
    val grossPrice: LiveData<BigDecimal> = _grossPrice

    private val _theatreInfo = MutableLiveData<Result<TheatreInfo>>()

    fun loadTheatreInfo(): LiveData<Result<TheatreInfo>> {
        viewModelScope.launch(dispatcher.io()) {
            val result = seatSelectionUseCase.getTheatreInfo()
            LogUtil.logD("result : ${result.data}")
            _theatreInfo.postValue(result)
        }
        return _theatreInfo
    }

    fun updateTotalPrice(seatList: List<Seat>) {
        selectedSeats = seatList
        totalPrice = seatSelectionUseCase.computeTotalPrice(seatList, _theatreInfo.value!!.data!!)
        _grossPrice.postValue(totalPrice)
    }

    fun getRowName(pos: Int): String {
        return ('A'+ pos).toString()
    }

}