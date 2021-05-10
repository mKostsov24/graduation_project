package org.example.repository;

import org.example.model.Users;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface UserRepository extends CrudRepository<Users, Integer> {
    Users findByEmail(String email);

    @Query("SELECT u FROM Users u where u.email = :email")
    Optional<Users> findByEmailOption(@Param("email") String email);

    @Modifying
    @Query("update Users u set u.code = :code WHERE u.id = :id")
    void setCodeValue(@Param("code") String code, @Param("id") int id);

    Users getByCode(String code);

    @Modifying
    @Query("update Users u set u.password = :password WHERE u.id = :id")
    void setNewPassword(@Param("password") String password, @Param("id") int id);

    @Modifying
    @Query("update Users u set u.name = :name WHERE u.id = :id")
    void setNewName(@Param("name") String name, @Param("id") int id);

    @Modifying
    @Query("update Users u set u.email = :email WHERE u.id = :id")
    void setNewEmail(@Param("email") String email, @Param("id") int id);

    @Modifying
    @Query("update Users u set u.photo = null WHERE u.id = :id")
    void deletePhoto(@Param("id") int id);

    @Modifying
    @Query("update Users u set u.photo = :photo WHERE u.id = :id")
    void setNewPhoto(@Param("photo") String photo, @Param("id") int id);

    @Modifying
    @Query("update Users u set u.code = null WHERE u.id = :id")
    void setCodeNull(@Param("id") int id);

}
