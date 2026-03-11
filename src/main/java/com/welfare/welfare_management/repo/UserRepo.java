package com.welfare.welfare_management.repo;

import com.welfare.welfare_management.model.Role;
import com.welfare.welfare_management.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepo  extends JpaRepository<User,String> {


    @Modifying
    @Transactional
    @Query("UPDATE User u SET " +
            "u.password = COALESCE(:password, u.password), " +
            "u.email = COALESCE(:email, u.email), " +
            "u.name = COALESCE(:name, u.name), " +
            "u.nic = COALESCE(:nic, u.nic), " +
            "u.phone = COALESCE(:phone, u.phone), " +
            "u.role = COALESCE(:role, u.role), " +
            "u.active = COALESCE(:active, u.active) " +
            "WHERE u.id = :id")

    int updateUserById(
            @Param("id") String id,
            @Param("password") String password,
            @Param("email") String email,
            @Param("name") String name,
            @Param("nic") String nic,
            @Param("phone") String phone,
            @Param("role") Role role,
            @Param("active") boolean active
    );

    @Query(value = "SELECT * FROM User WHERE id=?1", nativeQuery = true)
    User getUserById(String userId);

    @Query(value = "SELECT * FROM User WHERE email=?1", nativeQuery = true)
    User findByEmail(String email);

    @Query(value = "SELECT * FROM User WHERE nic=?1", nativeQuery = true)
    User findByNic(String nic);

    @Query(value = "SELECT * FROM User WHERE phone=?1", nativeQuery = true)
    User findByPhone(String nic);


    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.active = :status WHERE u.id = :userId")
    int updateUserStatus(@Param("userId") String userId,
                         @Param("status") boolean status);


    boolean existsByEmailAndIdNot(String email, String id);

    boolean existsByNicAndIdNot(String nic, String id);

    boolean existsByPhoneAndIdNot(String phone, String id);
}
