package com.trungtamjava.model;

import lombok.Data;

@Data
public class TokenDTO {
	private String accessToken;
	private Long expirationTime;
}
