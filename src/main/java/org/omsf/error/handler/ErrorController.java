package org.omsf.error.handler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * packageName    : org.omsf.error.handler
 * fileName       : ErrorController
 * author         : Yeong-Huns
 * date           : 2024-06-30
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-06-30        Yeong-Huns       최초 생성
 */
@Controller
public class ErrorController {
    @GetMapping("/errorTestPage")
    public String showErrorTestPage() {
        return "error/errorTestPage";
    }
    
    @GetMapping("/error/403")
    public String show403ErrorPage() {
        return "error/errorPage";
    }
}
