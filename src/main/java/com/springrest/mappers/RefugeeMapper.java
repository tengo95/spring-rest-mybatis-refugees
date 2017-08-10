package com.springrest.mappers;

import com.springrest.model.Refugee;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;

/**
 * Created by tanerali on 24/07/2017.
 */

@Mapper
public interface RefugeeMapper {

    String GET_ALL_REFUGEE_DATA = "Select * From `immigrants`.refugees_all";
    String INSERT_REFUGEE = "INSERT INTO `immigrants`.refugees_all " +
            "(year, country, origin, refugees) " +
            "VALUES (#{year}, #{country}, #{origin}, #{refugees})";
    String UPDATE_REFUGEE = "UPDATE `immigrants`.refugees_all SET country = #{country} " +
            "WHERE id = #{id}";
    String DELETE_REFUGEE = "DELETE FROM `immigrants`.refugees_all WHERE id = #{id}";
    String GET_BY_DATA = "SELECT * FROM `immigrants`.refugees_all where year = #{year} " +
            "AND country = #{country} AND origin = #{origin} AND refugees = #{refugees}";
    String GET_REFUGEES_IN_GIVE_YEAR = "SELECT total_population FROM `immigrants`.refugees_all " +
            "WHERE year = #{year} AND country = #{country}";


    @Select(GET_ALL_REFUGEE_DATA)
    public ArrayList<Refugee> getAllRefugeeData();

    @Select(GET_BY_DATA)
    public Refugee getByData (int year, String country, String origin, double refugees);

    @Select(GET_REFUGEES_IN_GIVE_YEAR)
    public Refugee getRefugeesInGivenYear (int year, String country);

    @Insert(INSERT_REFUGEE)
    public int insertRefugee (Refugee refugee);

    @Update(UPDATE_REFUGEE)
    public int updateRefugee (Refugee refugee);

    @Delete(DELETE_REFUGEE)
    public int deleteRefugee (int id);
}
