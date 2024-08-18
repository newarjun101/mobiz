package com.kbz.mobiz.core.arguments

import com.kbz.mobiz.domain.vos.MovieVo

object DetailArgument {

    var movieVo :  MovieVo? = null

    fun onClickDetail(vo :MovieVo) {
        movieVo = vo
    }
}