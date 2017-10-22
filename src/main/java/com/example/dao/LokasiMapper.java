package com.example.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.example.model.KecamatanModel;
import com.example.model.KelurahanModel;
import com.example.model.KotaModel;

@Mapper
public interface LokasiMapper {
	@Select("SELECT * FROM kelurahan JOIN kecamatan JOIN kota"
			+ " ON kelurahan.id_kecamatan = kecamatan.id "
			+ " AND kecamatan.id_kota = kota.id"
			)
    @Results(value = {
    		@Result(property="id", column="kelurahan.id")
    })
    List<KelurahanModel> selectAllKelurahan();
	

	@Select("SELECT * FROM kecamatan")
    List<KecamatanModel> selectAllKecamatan();
	
	@Select("SELECT * FROM kota")
    List<KotaModel> selectAllKota();
	
	@Select("SELECT * FROM kecamatan WHERE kecamatan.id_kota = #{id_kota}")
	List<KecamatanModel> selectAllKecamatanByKota(@Param("id_kota") String id_kota);
	
	@Select("SELECT * FROM kota WHERE kota.id = #{id_kota}")
	KotaModel selectKotaById(@Param("id_kota")String id_kota);
	
	@Select("SELECT * FROM kecamatan WHERE kecamatan.id = #{id_kecamatan}")
	KecamatanModel selectKecamatanById(@Param("id_kecamatan")String id_kecamatan);
	
	@Select("SELECT * FROM kelurahan WHERE kelurahan.id = #{id_kelurahan}")
	KelurahanModel selectKelurahanById(@Param("id_kelurahan")String id_kelurahan);
	
	@Select("SELECT * FROM kelurahan WHERE kelurahan.id_kecamatan = #{id_kecamatan}")
	List<KelurahanModel> selectAllKelurahanByKecamatan(@Param("id_kecamatan") String id_kecamatan);
}
