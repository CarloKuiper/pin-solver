package main.pin;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

import static main.AbstractGrid.DEFAULT_BOARD_ENCODING;

@RestController
public class SolverController {

    private final AtomicLong counter = new AtomicLong();
    private static final Logger logger = Logger.getLogger(SolverController.class.getName());

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping("/pin/solver")
    public PinSolver pinSolver(
            @RequestParam(value = "pinGrid", defaultValue = DEFAULT_BOARD_ENCODING) String pinGrid,
            @RequestParam(value = "pieces", required = false) String[] pieces
    ) {
        PinSolver pinSolver = new PinSolver(counter.incrementAndGet(), pinGrid, pieces);
        if (pinSolver.solve()) logger.info("Great success, I like.");
        else logger.info("NormalSolver.java solve() Not successful: off to kill another puppy out of sheer frustration.");
        return pinSolver;
    }
}
