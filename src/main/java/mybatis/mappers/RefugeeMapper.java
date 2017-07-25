package mybatis.mappers;

import mybatis.model.Refugee;
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


    @Select(GET_ALL_REFUGEE_DATA)
    public ArrayList<Refugee> getAllRefugeeData();

    @Select(GET_BY_DATA)
    public Refugee getByData (int year, String country, String origin, double refugees);

    @Insert(INSERT_REFUGEE)
    public int insertRefugee (Refugee refugee);

    @Update(UPDATE_REFUGEE)
    public int updateRefugee (Refugee refugee);

    @Delete(DELETE_REFUGEE)
    public int deleteRefugee (int id);
}
