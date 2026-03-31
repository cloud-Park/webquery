package com.hdmf.webquery.helloworld

import com.hdmf.webquery.helloworld.dto.HelloWorldRequest
import com.hdmf.webquery.helloworld.dto.HelloWorldResponse
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

class HelloWorldControllerTest :
    DescribeSpec({

        val service = mockk<HelloWorldService>()
        val controller = HelloWorldController(service)

        describe("getAll") {
            it("서비스의 전체 목록을 반환한다") {
                val expected =
                    listOf(
                        HelloWorldResponse(id = 1L, message = "Hello"),
                        HelloWorldResponse(id = 2L, message = "World"),
                    )
                every { service.findAll() } returns expected

                val result = controller.findAll()

                result shouldBe expected
            }
        }

        describe("getById") {
            it("서비스에서 단건을 조회해 반환한다") {
                val expected = HelloWorldResponse(id = 1L, message = "Hello")
                every { service.findById(1L) } returns expected

                val result = controller.findById(1L)

                result shouldBe expected
            }
        }

        describe("create") {
            it("서비스에 생성을 위임하고 결과를 반환한다") {
                val request = HelloWorldRequest(message = "Hello")
                val expected = HelloWorldResponse(id = 1L, message = "Hello")
                every { service.createHelloWorld(request) } returns expected

                val result = controller.createHelloWorld(request)

                result shouldBe expected
                verify(exactly = 1) { service.createHelloWorld(request) }
            }
        }

        describe("update") {
            it("서비스에 수정을 위임하고 결과를 반환한다") {
                val request = HelloWorldRequest(message = "Updated")
                val expected = HelloWorldResponse(id = 1L, message = "Updated")
                every { service.updateHelloWorld(1L, request) } returns expected

                val result = controller.updateHelloWorld(1L, request)

                result shouldBe expected
                verify(exactly = 1) { service.updateHelloWorld(1L, request) }
            }
        }

        describe("delete") {
            it("서비스에 삭제를 위임한다") {
                every { service.deleteHelloWorld(1L) } returns Unit

                controller.deleteHelloWorld(1L)

                verify(exactly = 1) { service.deleteHelloWorld(1L) }
            }
        }
    })
