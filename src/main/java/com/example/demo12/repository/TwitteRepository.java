package com.example.demo12.repository;

import com.example.demo12.model.Twitte;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TwitteRepository  extends JpaRepository<Twitte, Long> {
}
