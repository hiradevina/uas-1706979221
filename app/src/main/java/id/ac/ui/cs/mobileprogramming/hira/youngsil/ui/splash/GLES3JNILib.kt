package id.ac.ui.cs.mobileprogramming.hira.youngsil.ui.splash

// Wrapper for native library

object GLES3JNILib {
    external fun init()
    external fun resize(width: Int, height: Int)
    external fun step()

    init {
        System.loadLibrary("gles3jni")
    }
}