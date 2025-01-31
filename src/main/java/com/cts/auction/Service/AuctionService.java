package com.cts.auction.Service;


import java.util.List;

import com.cts.auction.DisplayDTO.AuctionDisplayDTO;
import com.cts.auction.Entity.AuctionEntity;

public interface AuctionService {
	
	
	String placeBid(int id, int pid, AuctionEntity auction); 
	
	void scheduleAuctionEnd(int id); 
	

	List<AuctionDisplayDTO> getAllAuctions();

	AuctionDisplayDTO getauctionbyId(int id);

	String deleteAuction(int id);

	String deleteAll();

	List<AuctionDisplayDTO> getAuctionByUser(int id);

}
