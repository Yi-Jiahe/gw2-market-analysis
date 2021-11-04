package com.jiahe.app.controllers;

import com.jiahe.app.models.Observation;
import com.jiahe.app.services.ObservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ObservationController {
    @Autowired
    private ObservationService observationService;

    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }

    @GetMapping("/index")
    public String serveIndex(
            @RequestParam(value = "item_id", defaultValue = "24") int itemId,
            Model model) {

        List<Observation> observations = observationService.findByItemId(itemId);

        model.addAttribute("observations", observations);

        return "index";
    }
}
