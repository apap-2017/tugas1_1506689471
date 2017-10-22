package com.example.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.model.KeluargaModel;
import com.example.model.PendudukModel;

@Mapper
public interface KeluargaMapper {
//	@Select("SELECT * FROM keluarga JOIN kelurahan JOIN kecamatan JOIN kota"
//			+ " ON keluarga.id_kelurahan = kelurahan.id "
//			+ " AND kelurahan.id_kecamatan = kecamatan.id AND kecamatan.id_kota = kota.id"
//			+ " WHERE nomor_kk = #{nkk}")
//    KeluargaModel selectKeluarga (@Param("nkk") String nkk);
	
	@Select("SELECT * FROM penduduk JOIN keluarga "
			+ " ON penduduk.id_keluarga = keluarga.id WHERE nomor_kk = #{nkk}")
	List<PendudukModel> selectPenduduks(@Param("nkk") String nkk);

	@Select("SELECT * FROM keluarga JOIN kelurahan JOIN kecamatan JOIN kota"
			+ " ON keluarga.id_kelurahan = kelurahan.id AND kelurahan.id_kecamatan = kecamatan.id "
			+ " AND kecamatan.id_kota = kota.id"
			+ " WHERE nomor_kk = #{nkk}")
    @Results(value = {
    		@Result(property="nomor_kk", column="nomor_kk"),
    		@Result(property="alamat", column="alamat"),
    		@Result(property="rt", column="rt"),
    		@Result(property="rw", column="rw"),
    		@Result(property="nama_kelurahan", column="nama_kelurahan"),
    		@Result(property="nama_kecamatan", column="nama_kecamatan"),
    		@Result(property="kode_kecamatan", column="kode_kecamatan"),
    		@Result(property="nama_kota", column="nama_kota"),
    		@Result(property="penduduks", column="nomor_kk",
    		javaType = List.class,
    		many=@Many(select="selectPenduduks"))
    })
    KeluargaModel selectKeluarga(@Param("nkk") String nkk);
	
	@Select("SELECT * FROM keluarga JOIN kelurahan JOIN kecamatan JOIN kota"
			+ " ON keluarga.id_kelurahan = kelurahan.id AND kelurahan.id_kecamatan = kecamatan.id "
			+ " AND kecamatan.id_kota = kota.id"
			+ " WHERE keluarga.id = #{id}")
    @Results(value = {
    		@Result(property="nomor_kk", column="nomor_kk"),
    		@Result(property="alamat", column="alamat"),
    		@Result(property="rt", column="rt"),
    		@Result(property="rw", column="rw"),
    		@Result(property="nama_kelurahan", column="nama_kelurahan"),
    		@Result(property="nama_kecamatan", column="nama_kecamatan"),
    		@Result(property="kode_kecamatan", column="kode_kecamatan"),
    		@Result(property="nama_kota", column="nama_kota"),
    		@Result(property="penduduks", column="nomor_kk",
    		javaType = List.class,
    		many=@Many(select="selectPenduduks"))
    })
    KeluargaModel getKeluarga(@Param("id") int id); 
	
	@Insert("INSERT INTO keluarga (alamat, nomor_kk, rt, rw, id_kelurahan, is_tidak_berlaku)"
    		+ " VALUES ('${alamat}', '${nomor_kk}', '${rt}', '${rw}', '${id_kelurahan}', '${is_tidak_berlaku}')")
    void addKeluarga (KeluargaModel keluarga);
	
	@Update("UPDATE keluarga SET alamat = #{alamat}, nomor_kk = #{nomor_kk}, rt = #{rt}, rw = #{rw}, "
			+ "id_kelurahan = #{id_kelurahan}, is_tidak_berlaku = #{is_tidak_berlaku} "
			+ "WHERE id = #{id}")
    void updateKeluarga (KeluargaModel keluarga);
	
	@Update("UPDATE keluarga SET is_tidak_berlaku = 1 WHERE id = #{id}")
	void updateStatusKematian(@Param("id") int id);
}
