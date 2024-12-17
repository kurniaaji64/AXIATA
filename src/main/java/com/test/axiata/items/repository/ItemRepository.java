package com.test.axiata.items.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.test.axiata.items.entity.Item;
import com.test.axiata.users.entity.Users;

public interface ItemRepository extends JpaRepository<Item,Integer>{

    @Query("Select i from Item i Where i.name =:name")
    Optional<Item> findByName(String name);

    @Query("SELECT i FROM Item i WHERE i.id IN :itemId")
    List<Item> findItemsById(@Param("itemId") Integer[] id);
    
}
