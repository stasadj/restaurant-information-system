package com.restaurant.backend.support;

import java.time.LocalDateTime;
import java.util.Comparator;

import com.restaurant.backend.domain.Item;

public class ItemCurrentValueComparator implements Comparator<Item> {
	private int criteria = 1;

	public ItemCurrentValueComparator(int criteria) {
		this.criteria = criteria;
	}

	@Override
	public int compare(Item o1, Item o2) {
		return criteria * o1.getItemValueAt(LocalDateTime.now()).getSellingPrice()
				.compareTo(o2.getItemValueAt(LocalDateTime.now()).getSellingPrice());
	}

}
