package com.homechange.api.repository;

import com.homechange.api.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Nemanja on 5/21/17.
 *
 * Repository for Message entity
 */
public interface MessageRepository extends JpaRepository<Message, Long> {
}
