package com.example.demo.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.LinkedList;
import java.util.List;

/**
 * The class implement the interface CustomUserRepo
 */
@Repository
public class CustomUserRepoImpl implements CustomUserRepo {
    @Autowired
    private EntityManager entityManager;
}