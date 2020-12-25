package id.ac.ui.cs.mobileprogramming.hira.youngsil.ui.splash

import android.content.Context
import android.opengl.GLSurfaceView
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class SplashView(context: Context?) : GLSurfaceView(context) {
    private class Renderer : GLSurfaceView.Renderer {
        override fun onDrawFrame(gl: GL10) {
            GLES3JNILib.step()
        }

        override fun onSurfaceChanged(gl: GL10, width: Int, height: Int) {
            GLES3JNILib.resize(width, height)
        }

        override fun onSurfaceCreated(gl: GL10, config: EGLConfig) {
            GLES3JNILib.init()
        }
    }

    companion object {
        private const val TAG = "GLES3JNI"
        private const val DEBUG = true
    }

    init {
        // Pick an EGLConfig with RGB8 color, 16-bit depth, no stencil,
        // supporting OpenGL ES 2.0 or later backwards-compatible versions.
        setEGLContextClientVersion(3)
        setEGLConfigChooser(8, 8, 8, 0, 16, 0)
        setRenderer(Renderer())
    }
}