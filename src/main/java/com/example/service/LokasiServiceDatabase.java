package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.LokasiMapper;
import com.example.model.KecamatanModel;
import com.example.model.KelurahanModel;
import com.example.model.KotaModel;


@Service
public class LokasiServiceDatabase implements LokasiService {
	@Autowired
	private LokasiMapper lokasiMapper;

	@Override
	public List<KelurahanModel> selectAllKelurahan() {
		return lokasiMapper.selectAllKelurahan();
	}

	@Override
	public List<KecamatanModel> selectAllKecamatan() {
		return lokasiMapper.selectAllKecamatan();
	}

	@Override
	public List<KotaModel> selectAllKota() {
		return lokasiMapper.selectAllKota();
	}
	
	@Override
	public List<KecamatanModel> selectAllKecamatanByKota(String id_kota) {
		return lokasiMapper.selectAllKecamatanByKota(id_kota);
	}
	
	@Override
	public KotaModel selectKotaById(String id_kota) {
		return lokasiMapper.selectKotaById(id_kota);
	}
	
	@Override
	public KecamatanModel selectKecamatanById(String id_kecamatan) {
		return lokasiMapper.selectKecamatanById(id_kecamatan);
	}
	
	@Override
	public KelurahanModel selectKelurahanById(String id_kelurahan) {
		return lokasiMapper.selectKelurahanById(id_kelurahan);
	}
	
	@Override
	public List<KelurahanModel> selectAllKelurahanByKecamatan(String id_kelurahan) {
		return lokasiMapper.selectAllKelurahanByKecamatan(id_kelurahan);
	}
}
