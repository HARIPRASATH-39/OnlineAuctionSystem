package com.cts.auction.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)

public class AuctionNotFoundException extends RuntimeException{

	public AuctionNotFoundException(String message) {
		super(message);
	}
}
