package com.example.spring.fortunes.dao;

import java.util.List;

import com.example.spring.fortunes.models.Fortune;

public interface FortuneDAO {
	public List<Fortune> findAll();
	public Fortune findOne();	
	public void save(List<Fortune> list);
}
