package com.kbz.mobiz.core.deligation

import com.kbz.mobiz.domain.data.vos.RecentVo

interface RecentClickDeligation {

    fun onClickRecent(vo : RecentVo)
    fun onDeleteRecent(vo: RecentVo)
}