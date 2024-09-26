package com.xyz.models;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentInformation {
	
	 @Column(name = "card_holder_name")
	 private String cardHolderName;
	 
	 @Column(name = "card_number")
	 private String cardNumber;
	 
	 @Column(name = "expiration_date")
	 private String expirationDate;
	 
	 
	 private String cvv;
	

}
