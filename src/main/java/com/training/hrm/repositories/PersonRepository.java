package com.training.hrm.repositories;

import com.training.hrm.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository <Person, Long> {
    Person findPersonByCitizenIdentity_CitizenIdentityID(String citizenIdentityID);
    Person findPersonByPassPort_PassPortID(String passPortID);
    Person findPersonByPhoneNumber(String phoneNumber);
    Person findPersonByEmail(String email);
    Person findPersonByPersonID(Long personID);
}
