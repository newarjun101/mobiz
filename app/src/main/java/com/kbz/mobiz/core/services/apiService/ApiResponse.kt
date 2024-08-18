package com.kbz.mobiz.core.services.apiService


sealed  class ApiResponse<T>
    (val data : T? = null,
     val message : String? = null) {
    class Success<T> ( data : T?,message: String? = "Success") : ApiResponse<T>(data = data,message = message)
    class Failure<T> (message : String) : ApiResponse<T>(message=message)
    class Loading<T>()  : ApiResponse<T>()
}