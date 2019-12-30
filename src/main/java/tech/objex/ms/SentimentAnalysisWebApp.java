package tech.objex.ms;

import io.prometheus.client.spring.boot.EnablePrometheusEndpoint;
import io.prometheus.client.spring.boot.EnableSpringBootMetricsCollector;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

// Add a Prometheus metrics enpoint to the route `/prometheus`. `/metrics` is already taken by Actuator.
@EnablePrometheusEndpoint

// Pull all metrics from Actuator and expose them as Prometheus metrics. Need to disable security feature in properties file.
@EnableSpringBootMetricsCollector

public class SentimentAnalysisWebApp {

	public static void main(String[] args) {
		SpringApplication.run(SentimentAnalysisWebApp.class, args);
	}
}
