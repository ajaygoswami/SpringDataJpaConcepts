package learning.jpa.SpringDataJpaConcepts.lock.pessimistic;

import learning.jpa.SpringDataJpaConcepts.entities.*;
import learning.jpa.SpringDataJpaConcepts.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.*;

@RestController
@RequestMapping(value = "/test")
public class PessimisticLockTestController {

    @Autowired
    private UserService userService;


    @PostMapping(value = "/pessimisticlock")
    public void testOptimisticLocking(@RequestParam(value = "increment") Double increment) throws InterruptedException, ExecutionException {

        ExecutorService service = Executors.newFixedThreadPool(4);
        Future<User> userFuture =  service.submit(() -> userService.updateUser(increment));
        Future<User> userFuture2 = service.submit(() -> userService.updateUser(increment));

        Future<User> userFuture3 =  service.submit(() -> userService.updateUser(increment));
        Future<User> userFuture4 = service.submit(() -> userService.updateUser(increment));

        while (!userFuture.isDone() && !userFuture2.isDone()&& !userFuture3.isDone()&& !userFuture4.isDone()){
            System.out.println("Tasks are still not complete");
            TimeUnit.SECONDS.sleep(1);
        }
        System.out.println(userFuture.get());
        System.out.println(userFuture2.get());
        System.out.println(userFuture3.get());
        System.out.println(userFuture4.get());
        service.shutdown();
    }
}
