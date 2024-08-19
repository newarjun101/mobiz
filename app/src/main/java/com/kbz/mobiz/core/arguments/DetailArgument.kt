package com.kbz.mobiz.core.arguments

import com.kbz.mobiz.domain.data.vos.MovieVo

object DetailArgument {

    var movieVo :  MovieVo? = null

    fun onClickDetail(vo : MovieVo) {
        movieVo = vo
    }
}