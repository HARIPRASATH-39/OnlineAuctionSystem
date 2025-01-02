package com.cts.auction.Entity;



import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name="product")
@NoArgsConstructor
@Builder
@AllArgsConstructor

public class ProductEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
    @JoinColumn(name="userid")
	private UserEntity user;
	
	private String productName;
	
	private Double price;
	
	private Double highest_bid;
	
	private String status;
	
	
	@OneToOne(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true) 
	private AuctionEntity auctions;
	
	@PrePersist public void prePersist() 
	{ if (highest_bid == null) {
		highest_bid = 0.0; 
	 }
	if(status==null)
	{
		status="unsold";
	}
	}
	
	
	

}
