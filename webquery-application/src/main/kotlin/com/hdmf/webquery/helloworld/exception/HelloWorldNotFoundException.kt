package com.hdmf.webquery.helloworld.exception

import com.hdmf.webquery.exception.BusinessException

class HelloWorldNotFoundException(
    id: Long,
) : BusinessException("HelloWorld를 찾을 수 없습니다: $id")
