package likelionjpa.jpashop;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LombokController {

    @GetMapping("/lombok")
    public String lombok(Model model){
        model.addAttribute("data","helloWorld");
        return "lombok";
    }
}
