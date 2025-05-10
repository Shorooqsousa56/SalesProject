package com.example.SalesProject.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.SalesProject.entity.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByEmail(String email);

    List<Users> findByroleIn(List<String> Role);

    Optional<Users> findById(long id);

    Optional<Users> deleteById(long id);

    @Modifying
    @Query("UPDATE Users user SET user.name = :name, user.phone = :phone, user.address = :address WHERE user.id = :id")
    int updateUserDetails(@Param("id") Long id,
            @Param("name") String name,
            @Param("phone") String phone,
            @Param("address") String address);

}
