package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Photos;

@Repository
public interface PhotoRepository  extends JpaRepository<Photos, Integer>{

}
