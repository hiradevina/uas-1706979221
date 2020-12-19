package id.ac.ui.cs.mobileprogramming.hira.youngsil.utils

sealed class State<T> {
    class Loading<T> : State<T>()
    data class Success<T>(val data: T) : State<T>()
    data class Failed<T>(val throwable: Throwable) : State<T>()
    class Initialize<T> : State<T>()

    companion object {
        fun <T> loading() = Loading<T>()
        fun <T> success(data: T) = Success(data)
        fun <T> failed(throwable: Throwable) = Failed<T>(throwable)
        fun <T> init() = Initialize<T>()
    }
}
