package mybatis.services;

import mybatis.mappers.RefugeeMapper;
import mybatis.model.Refugee;
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

    //get all users using mybatis
    public ArrayList<Refugee> getAllRefugeeData (){
        return refugeeMapper.getAllRefugeeData();
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
