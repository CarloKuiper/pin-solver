package main.greeting;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class ResponseController {

    private static final String TEMPLATE = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping("/greeting")
    public Response greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new Response(counter.incrementAndGet(), String.format(TEMPLATE, name));
    }
}
