package com.example.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import com.example.service.KeluargaService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class KeluargaModel {
	private String id;
	private String nomor_kk;
	private String alamat;
	private String rt;
	private String rw;
	private String id_kelurahan;
	private int is_tidak_berlaku;
	private String nama_kota;
	private String nama_kelurahan;
	private String nama_kecamatan;
	private String kode_kecamatan;
	List<PendudukModel> penduduks;
	
	public void generateNkk(KeluargaService keluargaDAO) {
		StringTokenizer tanggalLahirTokenizer = new StringTokenizer(new SimpleDateFormat("yy-MM-dd").format(new Date()), "-");
		String tahunLahir = tanggalLahirTokenizer.nextToken().substring(2);
		String bulanLahir = tanggalLahirTokenizer.nextToken();
		String tanggalLahir = tanggalLahirTokenizer.nextToken();
		
		long nkk = Long.parseLong(this.getKode_kecamatan().substring(0,6) + tanggalLahir + bulanLahir + tahunLahir + "0001");
		
		while(true) {
			KeluargaModel checker = keluargaDAO.selectKeluarga(""+nkk);
			if(checker != null) {
				nkk++;
			}else {
				break;
			}
		}
		this.setNomor_kk(""+nkk);
	}
}
