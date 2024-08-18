package com.kbz.mobiz.core.deligation

import com.kbz.mobiz.domain.vos.MovieVo
import com.kbz.mobiz.domain.vos.SearchVo


interface OnMovieClick {
    fun onClickMovie(vo : MovieVo)
}
interface SearchClick {
    fun onClickMovie(vo : SearchVo)
}