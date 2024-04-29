package learning.jpa.SpringDataJpaConcepts.lock.optimistic;

import learning.jpa.SpringDataJpaConcepts.entities.*;
import learning.jpa.SpringDataJpaConcepts.repositories.*;
import learning.jpa.SpringDataJpaConcepts.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.transaction.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.math.*;
import java.util.concurrent.*;

@RestController
@RequestMapping(value = "/test")
public class OptimisticLockTestController {

    @Autowired
    private UserService userService;


    @PostMapping(value = "/optimisticlock")
    public void testOptimisticLocking(@RequestParam(value = "increment") Double increment) throws InterruptedException, ExecutionException {

        /**
         * By using version field and @Version annotation in User entity, we have implemented Optimistic locking.
         * If two are more threads have read the same data then only one thread will be able to update the record, and
         * after record is updated, version will be incremented, thus since other threads have read the old data with plder version
         * they won't be able to update the recods.
         *
         * Here in the example, Running two threads to increment the salary by given percentage, but only one thread
         * will be able to update the salary, two increments will not happen.
         * One thread has to fail in update.
         */
        ExecutorService service = Executors.newFixedThreadPool(2);
        Future<User> userFuture =  service.submit(() -> userService.updateUser(increment));
        Future<User> userFuture2 = service.submit(() -> userService.updateUser(increment));

        while (!userFuture.isDone() && !userFuture2.isDone()){
            System.out.println("Tasks are still not complete");
            TimeUnit.SECONDS.sleep(1);
        }
        System.out.println(userFuture.get());
        System.out.println(userFuture2.get());
        service.shutdown();
    }
}
