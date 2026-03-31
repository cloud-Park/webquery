package com.hdmf.webquery.helloworld

import com.hdmf.webquery.helloworld.dto.HelloWorldRequest
import com.hdmf.webquery.helloworld.dto.HelloWorldResponse
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.data.repository.findByIdOrNull

class HelloWorldServiceTest :
    DescribeSpec({

        val repository = mockk<HelloWorldRepository>()
        val service = HelloWorldService(repository)

        describe("getAll") {
            it("전체 목록을 반환한다") {
                every { repository.findAll() } returns
                    listOf(
                        HelloWorld(id = 1L, message = "Hello"),
                        HelloWorld(id = 2L, message = "World"),
                    )

                val result = service.findAll()

                result.size shouldBe 2
                result[0] shouldBe HelloWorldResponse(id = 1L, message = "Hello")
                result[1] shouldBe HelloWorldResponse(id = 2L, message = "World")
            }
        }

        describe("getById") {
            it("존재하는 id면 응답을 반환한다") {
                every { repository.findByIdOrNull(1L) } returns HelloWorld(id = 1L, message = "Hello")

                val result = service.findById(1L)

                result shouldBe HelloWorldResponse(id = 1L, message = "Hello")
            }

            it("존재하지 않는 id면 예외를 던진다") {
                every { repository.findByIdOrNull(99L) } returns null

                shouldThrow<NoSuchElementException> {
                    service.findById(99L)
                }
            }
        }

        describe("create") {
            it("저장 후 응답을 반환한다") {
                val request = HelloWorldRequest(message = "Hello")
                every { repository.save(any()) } returns HelloWorld(id = 1L, message = "Hello")

                val result = service.createHelloWorld(request)

                result shouldBe HelloWorldResponse(id = 1L, message = "Hello")
                verify(exactly = 1) { repository.save(any()) }
            }
        }

        describe("update") {
            it("존재하는 id면 메시지를 수정하고 반환한다") {
                val entity = HelloWorld(id = 1L, message = "Old")
                every { repository.findByIdOrNull(1L) } returns entity

                val result = service.updateHelloWorld(1L, HelloWorldRequest(message = "New"))

                result shouldBe HelloWorldResponse(id = 1L, message = "New")
                entity.message shouldBe "New"
            }

            it("존재하지 않는 id면 예외를 던진다") {
                every { repository.findByIdOrNull(99L) } returns null

                shouldThrow<NoSuchElementException> {
                    service.updateHelloWorld(99L, HelloWorldRequest(message = "New"))
                }
            }
        }

        describe("delete") {
            it("존재하는 id면 삭제한다") {
                every { repository.existsById(1L) } returns true
                every { repository.deleteById(1L) } returns Unit

                service.deleteHelloWorld(1L)

                verify(exactly = 1) { repository.deleteById(1L) }
            }

            it("존재하지 않는 id면 예외를 던진다") {
                every { repository.existsById(99L) } returns false

                shouldThrow<NoSuchElementException> {
                    service.deleteHelloWorld(99L)
                }
            }
        }
    })
