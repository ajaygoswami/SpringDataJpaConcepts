package learning.jpa.SpringDataJpaConcepts.repositories;

import jakarta.persistence.*;
import learning.jpa.SpringDataJpaConcepts.entities.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    /*@Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
     if we use this, it will increment the version for the other
     thread too which failed to update the transaction because of thread 1 update*/

    /*@Lock(LockModeType.OPTIMISTIC)
     If entity already contains version field, we even dont need to use this annotation, since underlying jpa implementation
     already manages this. If one thread succeeded to update the record, second thread will fail to update the record since version is already incremented
     and second thread is holding old version
    */

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    /*this acquire an exclusive lock on the record, it means if thread 1 read the record it put lock on the row and
    second thread wont be able to read the record until 1st thread releases the lock.
    there will not be any data inconsistency in the update.
    if second thread fail to get the lock on the record , it will throw org.springframework.dao.PessimisticLockingFailureException
    Caused by: org.hibernate.PessimisticLockException:*/

    /*
     @Lock(LockModeType.PESSIMISTIC_READ)
    FROM JPA doc
    * It is permissible for an implementation to use LockModeType.PESSIMISTIC_WRITE where
    * LockModeType.PESSIMISTIC_READ was requested, but not vice versa."

    So although we are using READ, it works like a WRITE
    * */
    User findByEmail(String email);
}
