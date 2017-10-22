package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.KeluargaMapper;
import com.example.dao.PendudukMapper;
import com.example.model.PendudukModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service

public class PendudukServiceDatabase implements PendudukService {
	
	 @Autowired
	 private KeluargaMapper keluargaMapper;
	
	 @Autowired
	 private PendudukMapper pendudukMapper;

	 @Override
	 public PendudukModel selectPenduduk (String nik)
	 {
		 log.info("select penduduk with nik");
		 return pendudukMapper.selectPenduduk(nik);
	 }
	 
	 @Override
	 public void addPenduduk (PendudukModel penduduk)
	 {
		 log.info("add penduduk");
		 pendudukMapper.addPenduduk(penduduk);
	 }
	 
	 @Override
	 public void updatePenduduk (PendudukModel penduduk)
	 {
		 log.info("update penduduk");
		 pendudukMapper.updatePenduduk(penduduk);
	 }
	 
	@Override
	 public void updateStatusKematian (PendudukModel penduduk)
	 {
		 log.info("update status kematian penduduk");
		 int id_keluarga = penduduk.getId_keluarga();
		 List<PendudukModel> anggota = pendudukMapper.selectAnggotaKeluarga(id_keluarga);
		 
		 int wafat = 0;
		 for (int i = 0; i < anggota.size(); i++) {
			 if(anggota.get(i).getIs_wafat() == 1) {
				 wafat++;
			 }
		 }
		 
		 if(wafat == anggota.size()) {
			 keluargaMapper.updateStatusKematian(id_keluarga);
		 }
		 pendudukMapper.updateStatusKematian(penduduk.getNik());
	 }
	 
	@Override
	 public List<PendudukModel> selectPendudukByKelurahan (String id_kelurahan)
	 {
		 log.info("select penduduk with id kecamatan");
		 return pendudukMapper.selectPendudukByKelurahan(id_kelurahan);
	 }

	@Override
	public PendudukModel pendudukTermuda(String id_kelurahan) {
		return pendudukMapper.pendudukTermuda(id_kelurahan);
	}

	@Override
	public PendudukModel pendudukTertua(String id_kelurahan) {
		return pendudukMapper.pendudukTertua(id_kelurahan);
	}
}
