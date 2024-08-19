package com.kbz.mobiz.core.deligation

import com.kbz.mobiz.domain.data.vos.MovieVo
import com.kbz.mobiz.domain.data.vos.SearchVo


interface OnMovieClick {
    fun onClickMovie(vo : MovieVo)
}
interface SearchClick {
    fun onClickMovie(vo : SearchVo)
}