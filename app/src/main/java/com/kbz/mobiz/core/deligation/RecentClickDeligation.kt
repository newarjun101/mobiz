package com.kbz.mobiz.core.deligation

import com.kbz.mobiz.domain.vos.RecentVo

interface RecentClickDeligation {

    fun onClickRecent(vo : RecentVo)
    fun onDeleteRecent(vo: RecentVo)
}