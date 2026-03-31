package com.hdmf.webquery.helloworld

import org.springframework.data.jpa.repository.JpaRepository

interface HelloWorldRepository : JpaRepository<HelloWorld, Long>
