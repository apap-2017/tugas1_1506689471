package com.example.service;

import com.example.model.KeluargaModel;

public interface KeluargaService {
	KeluargaModel selectKeluarga(String nkk);
	KeluargaModel getKeluarga(int id);
	void addKeluarga(KeluargaModel keluarga);
	void updateKeluarga(KeluargaModel keluarga);
	
}
