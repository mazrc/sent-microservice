package tech.objex.ms;

import tech.objex.ms.dto.SentenceDto;
import tech.objex.ms.dto.SentimentDto;
import io.prometheus.client.Counter;
import io.prometheus.client.Gauge;
import io.prometheus.client.Histogram;
import io.prometheus.client.Summary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


@CrossOrigin(origins = "*")
@RestController
public class SentimentController {

    private static double rand(double min, double max) {
        return min + (Math.random() * (max - min));
    }

    Logger LOGGER = LoggerFactory.getLogger(SentimentController.class);

    // prometheus metrics registration
    Counter counter = Counter.build().namespace("demo").name("demoCounter").help("this is demo counter").register();
    Gauge gauge = Gauge.build().namespace("demo").name("demoGauge").help("this is demo gauge").register();
    Histogram histogram = Histogram.build().namespace("demo").name("demoHistogram").help("this is demo histogram").register();
    Summary summary = Summary.build().namespace("demo").name("demoSummary").help("this is demo summary").register();

    @Value("${sa.logic.api.url}")
    private String saLogicApiUrl;

    @PostMapping("/sentiment")
    public SentimentDto sentimentAnalysis(@RequestBody SentenceDto sentenceDto) {
        RestTemplate restTemplate = new RestTemplate();

        LOGGER.info("/sentiment requested for: " + sentenceDto.getSentence() );
        // Log a simple message
        LOGGER.debug("debug level log");
        LOGGER.info("info level log");
        LOGGER.error("error level log");

        // prometheus metrics
        counter.inc();
        gauge.set(rand(-5, 10));
        summary.observe(rand(0, 5));

        System.out.println("this is system out.");

        Histogram.Timer requestTimer = histogram.startTimer(); // measures request latency
        try {
            return restTemplate.postForEntity(saLogicApiUrl + "/analyse/sentiment",
                    sentenceDto, SentimentDto.class)
                    .getBody();
        } finally {
            // Stop the histogram timer
            requestTimer.observeDuration();
        }
    }

    @GetMapping("/testHealth")
    public void testHealth() {
        System.out.println("<< pingged >> !!");
    }

}


