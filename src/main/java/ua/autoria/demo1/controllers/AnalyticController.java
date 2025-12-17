package ua.autoria.demo1.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ua.autoria.demo1.models.AnalyticsResponse;
import ua.autoria.demo1.services.AnalyticService;

@RestController
@AllArgsConstructor
public class AnalyticController {
    private AnalyticService analyticService;

    @GetMapping("/offers/get-analytic/{userId}/{postId}")
    public ResponseEntity<AnalyticsResponse> getAnalytic(@PathVariable Long userId, @PathVariable Long postId) {
        try {
            return new ResponseEntity<>(analyticService.getAnalytics(userId, postId), HttpStatus.OK);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
