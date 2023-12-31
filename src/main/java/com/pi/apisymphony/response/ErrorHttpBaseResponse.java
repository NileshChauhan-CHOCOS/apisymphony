package com.pi.apisymphony.response;

import java.time.LocalDateTime;

public record ErrorHttpBaseResponse(int code, Object error, LocalDateTime localDateTime) {
}
