package QA.Project.Controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class GUIController {
    private final RestTemplate restTemplate;

    public GUIController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/my-page")
    public String showMyPage() {
        return "index"; // Replace "my-page" with the actual name of your HTML file
    }
    @GetMapping("/Convert")
    public ModelAndView Convert(@RequestParam("source") String sourceCurrency,
                                @RequestParam("target") String targetCurrency,
                                @RequestParam(name = "amount", required = false) Double amount){
        String url = "http://localhost:8080/api/currency/convert?amount=" + amount + "&source=" + sourceCurrency + "&target=" + targetCurrency;

        Double response = restTemplate.getForObject(url,Double.class);
        Double rate = response/amount;
        ModelAndView modelAndView= new ModelAndView("index");
        modelAndView.addObject("rate",rate);
        modelAndView.addObject("apiResult",response);

        return modelAndView;
    }
}
