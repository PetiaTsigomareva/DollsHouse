package com.petia.dollhouse.service;

import java.util.List;

public interface BaceService {

	<T> String add(T model);

	<T> T edit(T model);

	<T> List<T> findAll();

	<T> T findByID(String id);

	<T> T delete(T model);

}
