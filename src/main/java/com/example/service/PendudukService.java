package com.example.service;

import java.util.List;

import com.example.model.PendudukModel;

public interface PendudukService {
	PendudukModel selectPenduduk(String nik);
	void addPenduduk(PendudukModel penduduk);
	void updatePenduduk(PendudukModel penduduk);
	void updateStatusKematian(PendudukModel penduduk);
	List<PendudukModel> selectPendudukByKelurahan(String id_kelurahan);
	PendudukModel pendudukTermuda(String id_kelurahan);
	PendudukModel pendudukTertua(String id_kelurahan);
}
