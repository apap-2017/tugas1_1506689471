package com.example.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.model.PendudukModel;

@Mapper
public interface PendudukMapper {
	
	@Select("SELECT * FROM penduduk join keluarga join kelurahan join kecamatan join kota"
			+ " ON penduduk.id_keluarga = keluarga.id AND keluarga.id_kelurahan = kelurahan.id "
			+ " AND kelurahan.id_kecamatan = kecamatan.id AND kecamatan.id_kota = kota.id"
			+ " WHERE nik = #{nik}")
    PendudukModel selectPenduduk (@Param("nik") String nik);
	
	@Insert("INSERT INTO penduduk (nik, nama, tempat_lahir, tanggal_lahir, jenis_kelamin, is_wni,"
			+ " id_keluarga, agama, pekerjaan, status_perkawinan, status_dalam_keluarga, "
			+ " golongan_darah, is_wafat)"
    		+ " VALUES ('${nik}', '${nama}', '${tempat_lahir}', '${tanggal_lahir}', ${jenis_kelamin},"
    		+ " ${is_wni}, '${id_keluarga}', '${agama}', '${pekerjaan}', '${status_perkawinan}', "
    		+ " '${status_dalam_keluarga}', '${golongan_darah}',"
    		+ " '${is_wafat}')")
    void addPenduduk (PendudukModel penduduk);
	
	@Update("UPDATE penduduk SET nik = #{nik}, nama = #{nama}, tempat_lahir = #{tempat_lahir}, tanggal_lahir = #{tanggal_lahir}, "
			+ "jenis_kelamin = #{jenis_kelamin}, is_wni = #{is_wni}, golongan_darah = #{golongan_darah}, is_wafat = #{is_wafat} "
			+ " WHERE id = #{id}")
    void updatePenduduk (PendudukModel penduduk);
	
	@Update("UPDATE penduduk SET is_wafat = 1 WHERE nik = #{nik}")
	void updateStatusKematian(@Param("nik") String nik);

	@Select("select * from keluarga, penduduk where penduduk.id_keluarga = keluarga.id and #{id_keluarga} = penduduk.id_keluarga")
    @Results(value = {
        	@Result(property="nik", column="nik"),
        	@Result(property="nama", column="nama"),
        	@Result(property="tempat_lahir", column="tempat_lahir"),
        	@Result(property="tanggal_lahir", column="tanggal_lahir"),
        	@Result(property="jenis_kelamin", column="jenis_kelamin"),
        	@Result(property="is_wni", column="is_wni"),
        	@Result(property="id_keluarga", column="id_keluarga"),
        	@Result(property="agama", column="agama"),
        	@Result(property="pekerjaan", column="pekerjaan"),
        	@Result(property="status_perkawinan", column="status_perkawinan"),
        	@Result(property="status_dalam_keluarga", column="status_dalam_keluarga"),
        	@Result(property="golongan_darah", column="golongan_darah"),
        	@Result(property="is_wafat", column="is_wafat")
        })
    List<PendudukModel> selectAnggotaKeluarga(int id_keluarga);
	
	@Select("select * from penduduk, keluarga where penduduk.id_keluarga = keluarga.id and keluarga.id_kelurahan = #{id_kelurahan}")
	List<PendudukModel> selectPendudukByKelurahan(@Param("id_kelurahan") String id_kelurahan);
	
	 @Select("select distinct nama, nik, tanggal_lahir, (YEAR(CURDATE())-YEAR(tanggal_lahir)) as umur from penduduk join "
				+ "keluarga on penduduk.id_keluarga = keluarga.id where keluarga.id_kelurahan = #{id_kelurahan} order by umur asc limit 1")
		PendudukModel pendudukTermuda(@Param("id_kelurahan") String id_kelurahan); 
	 
	 @Select("select distinct nama, nik, tanggal_lahir, (YEAR(CURDATE())-YEAR(tanggal_lahir)) as umur from penduduk join "
				+ "keluarga on penduduk.id_keluarga = keluarga.id where keluarga.id_kelurahan = #{id_kelurahan} order by umur desc limit 1")
		PendudukModel pendudukTertua(@Param("id_kelurahan") String id_kelurahan);
}
