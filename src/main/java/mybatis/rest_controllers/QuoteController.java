package mybatis.rest_controllers;

import mybatis.model.Sample.Quote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Created by ryandesmond on 7/25/17.
 */
@RestController
public class QuoteController {

    @Autowired
    RestTemplate restTemplate;

    private static final Logger log = LoggerFactory.getLogger(QuoteController.class);

    @RequestMapping("/quote")
    public Quote quote(@RequestParam(value="name", defaultValue="World") String name) {
        Quote quote = restTemplate.getForObject(
                "http://gturnquist-quoters.cfapps.io/api/random", Quote.class);
        log.info(quote.toString());
        return quote;
    }

}