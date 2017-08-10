package com.springrest.services;

import com.springrest.mappers.RefugeeMapper;
import com.springrest.model.Refugee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Created by tanerali on 24/07/2017.
 */
@Service
public class RefugeeService {

    @Autowired
    RefugeeMapper refugeeMapper;


    //get all users using com
    public ArrayList<Refugee> getAllRefugeeData (){
        return refugeeMapper.getAllRefugeeData();
    }

    //get refugee in given year
    public Refugee getRefugeesInGivenYear (int year, String country) {
        return refugeeMapper.getRefugeesInGivenYear(year, country);
    }


    //add new refugee
    public Refugee addNew(Refugee refugee) {
        refugeeMapper.insertRefugee(refugee);
        return refugeeMapper.getByData(refugee.getYear(), refugee.getCountry(), refugee.getOrigin(),
                refugee.getRefugees());
    }

    //update user by its id
    public int updateById(Refugee refugee) {
        return refugeeMapper.updateRefugee(refugee);
    }

    //delete
    public int deleteById(int id) {

        return refugeeMapper.deleteRefugee(id);
//        return refugeeMapper.getByID(id);
    }
}
