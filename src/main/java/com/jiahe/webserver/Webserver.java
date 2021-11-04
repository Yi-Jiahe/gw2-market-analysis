package com.jiahe.webserver;

import com.jiahe.models.Observation;
import com.jiahe.services.ObservationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@Controller
public class Webserver {
    @Autowired
    private ObservationsService observationsService;

    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }

    @GetMapping("/index")
    public String serveIndex(
            @RequestParam(value = "item_id", defaultValue = "24") int itemId,
            Model model) {
//        List<Observation> observations = observationsService.findByItemId(itemId);
        List<Observation> observations = new ArrayList<Observation>();
        for (int i=0; i<10; i++) {
            observations.add(new Observation(i,i*3,i*4,i*20,i*21, Date.from(Instant.now())));
        }
        model.addAttribute("observations", observations);
        return "index";
    }
}
