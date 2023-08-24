package com.kkpfg.fundtrading

import okhttp3.HttpUrl
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest


class MyMockServer {
    companion object{
        fun createMockServer(): HttpUrl {
            val mockServer = MockWebServer()
            mockServer.dispatcher =  MockDispatcher()
            mockServer.start()
            return  mockServer.url("/")
        }
    }
}

class MockDispatcher: Dispatcher(){

    override fun dispatch(request: RecordedRequest): MockResponse {
        return when (request.path) {
            "/login" -> loginOk
            else -> notFound
        }
    }

    private val notFound = MockResponse().setResponseCode(404)

    private val loginOk = MockResponse()
        .setResponseCode(200)
        .setBody("""{ "accessToken":"xxxx", "refreshToken":"yyyy" }""")

}