package com.kkpfg.fundtrading

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kkpfg.fundtrading.viewmodels.MainViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*
import org.junit.Before


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class MainInstrumentedTest {

    private lateinit var appContext: Context;

    @Before
    fun setup(){
        appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.kkpfg.fundtrading", appContext.packageName)

        TokenManager.initTokenManager(appContext)
    }

    @After
    fun finish(){

    }

    @Test
    fun test_MainViewModel_Login() {
        runBlocking {
            val mockServer = MyMockServer.createMockServer()
            val viewModel = MainViewModel(mockServer)
            viewModel.doLogin("user", "pass")

            delay(300)

            assertEquals(true, viewModel.isLoginSuccess.value)
        }
    }


}