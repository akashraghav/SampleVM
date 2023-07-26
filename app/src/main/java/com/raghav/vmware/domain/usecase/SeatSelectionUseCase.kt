package com.raghav.vmware.domain.usecase

import com.raghav.vmware.domain.model.Result
import com.raghav.vmware.domain.model.Seat
import com.raghav.vmware.domain.model.TheatreInfo
import com.raghav.vmware.domain.repository.TheatreInfoRepository
import com.raghav.vmware.domain.utils.LogUtil
import com.raghav.vmware.domain.utils.retryOnFailure
import com.raghav.vmware.domain.utils.size
import java.math.BigDecimal
import javax.inject.Inject

class SeatSelectionUseCase @Inject constructor(
    private val theatreInfoRepository: TheatreInfoRepository,
) {

    suspend fun getTheatreInfo(): Result<TheatreInfo> = retryOnFailure(3) {
        theatreInfoRepository.getTheatreInfo()
    }

    fun computeTotalPrice(seats: List<Seat>, theatreInfo: TheatreInfo): BigDecimal {
        var total = BigDecimal(0.0)
        seats.forEach { seat ->
            theatreInfo.sections.firstOrNull { layout ->
                seat.section == layout.name
            }?.let {
                total = total.add(it.price)
            }
        }
        return total
    }

}