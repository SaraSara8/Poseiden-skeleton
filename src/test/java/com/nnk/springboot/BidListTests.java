package com.nnk.springboot;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)  // JUnit 5: Extension pour les tests Spring
@SpringBootTest
public class BidListTests {

	@Autowired
	private BidListRepository bidListRepository;

	@Test
	public void bidListTest() {

		// Initialisation d'un objet BidList pour les tests

		BidList bid = new BidList();
		bid.setAccount("Account Test");
		bid.setType("Type Test");    ;
		bid.setBidQuantity(10d);

		// Save
		bid = bidListRepository.save(bid);

		assertNotNull(bid.getId());
		assertEquals(bid.getBidQuantity(), 10d, 10d);

		// Update
		bid.setBidQuantity(20d);
		bid = bidListRepository.save(bid);
		assertEquals(bid.getBidQuantity(), 20d, 20d);

		// Find
		List<BidList> listResult = bidListRepository.findAll();
		assertTrue(listResult.size() > 0);

		// Delete
		Integer id = bid.getId();
		bidListRepository.delete(bid);
		Optional<BidList> bidList = bidListRepository.findById(id);
		assertFalse(bidList.isPresent());
	}
}