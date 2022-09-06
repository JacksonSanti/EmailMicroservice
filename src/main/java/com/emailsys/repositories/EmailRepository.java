package com.emailsys.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emailsys.models.EmailModel;

@Repository
public interface EmailRepository extends JpaRepository<EmailModel, Integer> {

}
