package org.omsf.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MenuController {

    @GetMapping("/")
    public String showHome() {
        return "index";
    }

    @GetMapping("/store")
    public String showStorePage() {
        return "store";
    }

    @GetMapping("/search")
    public String showSearchPage() {
        return "search";
    }
}
