package learning.jpa.SpringDataJpaConcepts.service;

import learning.jpa.SpringDataJpaConcepts.entities.*;
import learning.jpa.SpringDataJpaConcepts.repositories.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.math.*;
import java.util.concurrent.*;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public User updateUser(Double salaryIncrement){
        User user = userRepository.findByEmail("ajay@gmail.com");

        try {
            //putting it into sleep for 2 seconds so that other thread should also try to read the same record
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Double incrementPercentage = (100+salaryIncrement)/100;
        user.setCharges(user.getCharges().multiply(BigDecimal.valueOf(incrementPercentage)));
       return userRepository.saveAndFlush(user);
    }
}
