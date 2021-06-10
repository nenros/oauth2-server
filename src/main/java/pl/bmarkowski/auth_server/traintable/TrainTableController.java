package pl.bmarkowski.auth_server.traintable;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/train")
public class TrainTableController {

    @GetMapping
    public String trainTable(){
        return "train-table";
    }

}
