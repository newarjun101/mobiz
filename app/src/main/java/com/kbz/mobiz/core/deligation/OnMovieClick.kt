package com.kbz.mobiz.core.deligation

import com.kbz.mobiz.domain.vos.MovieVo


interface OnMovieClick {
    fun onClickMovie(vo : MovieVo)
}