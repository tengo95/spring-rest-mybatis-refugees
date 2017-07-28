package mybatis.controllers;

import mybatis.model.Sample.Refugee;
import mybatis.services.RefugeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * Created by tanerali on 24/07/2017.
 */

@RestController
@RequestMapping("/refugees")
public class RefugeesController {

    @Autowired
    RefugeeService refugeeService;


    //RequestMapping maps URLs to methods

    //Get
    @RequestMapping("/all")
    public ArrayList<Refugee> getAllRefugeeData() {
        return refugeeService.getAllRefugeeData();
    }

    //get total refugees in a country in a given year
    @RequestMapping(method = RequestMethod.GET, value = "/")
    public Refugee getRefugeesInAGivenYear(@RequestParam(value = "year")int year,
                                           @RequestParam(value = "country")String country) {
        return refugeeService.getRefugeesInGivenYear(year, country);
    }

    //Create
    @RequestMapping(method = RequestMethod.POST, value = "/")
    public Refugee addNew(@RequestBody Refugee refugee) {
        return refugeeService.addNew(refugee);
    }

    //Update
    @RequestMapping(method = RequestMethod.PATCH, value = "/")
    public int updateById(@RequestBody Refugee refugee) {
        return refugeeService.updateById(refugee);
    }

    //Delete
    @RequestMapping(method= RequestMethod.DELETE, value="/")
    public int deleteById(@RequestParam(value="id")int id){
        return refugeeService.deleteById(id);
    }


    @RequestMapping(value = "/custom", method = RequestMethod.GET)
    public Refugee getCustom(@RequestParam(value="year")int year,
                                        @RequestParam(value="country")String country) {
        return null;
    }
}
