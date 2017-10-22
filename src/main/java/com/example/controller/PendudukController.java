package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.model.KecamatanModel;
import com.example.model.KelurahanModel;
import com.example.model.KotaModel;
import com.example.model.PendudukModel;
import com.example.service.KeluargaService;
import com.example.service.LokasiService;
import com.example.service.PendudukService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class PendudukController {
	
	@Autowired
	PendudukService pendudukDAO;
	
	@Autowired
	KeluargaService keluargaDAO;
	
	@Autowired
	LokasiService lokasiDAO;
	
	@RequestMapping("/")
    public String index()
    {
        return "index";
    }
	
	@RequestMapping("/penduduk")
    public String viewPenduduk (Model model,
            @RequestParam(value = "nik") String nik)
    {
       PendudukModel penduduk = pendudukDAO.selectPenduduk(nik);
        if (penduduk != null) {
        	if(penduduk.getIs_wni() == 1) {
        		penduduk.setKewarganegaraan("WNI");
        	} else {
        		penduduk.setKewarganegaraan("WNA");
        	}
        	
        	if (penduduk.getIs_wafat() == 0) {
        		penduduk.setWafat("Hidup");
        	}else {
        		penduduk.setWafat("Wafat");
        	}
            model.addAttribute ("penduduk", penduduk);
            return "viewPenduduk";
        } else {
            model.addAttribute ("nik", nik);
            return "not-found-penduduk";
        }
    }
	
	@RequestMapping("/penduduk/tambah")
    public String tambahPenduduk(Model model)
    {
		PendudukModel penduduk = new PendudukModel();
		model.addAttribute("penduduk", penduduk);
        return "form-tambah-penduduk";
    }
	

	@RequestMapping(value = "/penduduk/tambah", method = RequestMethod.POST)
    public String tambahPenduduk(Model model, @ModelAttribute PendudukModel penduduk)
    {
		penduduk.generateNik(keluargaDAO, pendudukDAO);
		pendudukDAO.addPenduduk(penduduk);
		model.addAttribute("nik", penduduk.getNik());
        return "sukses-tambah-penduduk";
    }
	
	@RequestMapping("/penduduk/ubah/{nik}")
    public String ubahPenduduk(Model model, 
            @PathVariable(value = "nik") String nik)
    {
		PendudukModel penduduk = pendudukDAO.selectPenduduk(nik);
		if (penduduk != null) {
			model.addAttribute("penduduk", penduduk);
	        return "form-update-penduduk";
		} else {
			return "not-found-penduduk";
		}
    }
	

	@RequestMapping(value = "/penduduk/ubah/{nik}", method = RequestMethod.POST)
    public String ubahPenduduk(Model model, @PathVariable(value = "nik") String nik,
    		@ModelAttribute PendudukModel penduduk)
    {
		PendudukModel pendudukLama = pendudukDAO.selectPenduduk(nik);

		penduduk.setId(pendudukLama.getId());
		if(!pendudukLama.getTanggal_lahir().equals(penduduk.getTanggal_lahir())) {
			penduduk.generateNik(keluargaDAO, pendudukDAO);
			log.info("Input {}", penduduk.getNik());
			penduduk.setNik(penduduk.getNik());
		}
		pendudukDAO.updatePenduduk(penduduk);
		model.addAttribute("nik", pendudukLama.getNik());
        return "sukses-tambah-penduduk";
    }
	
	@RequestMapping(value = "/penduduk/mati", method = RequestMethod.POST)
    public String statusKematian(Model model, @RequestParam(value = "nik") String nik)
    {
		PendudukModel penduduk = pendudukDAO.selectPenduduk(nik);
		pendudukDAO.updateStatusKematian(penduduk);

		model.addAttribute("nik", nik);
        return "sukses-update-kematian";
    }
	
	@RequestMapping("/penduduk/cari")
    public String cariPenduduk(Model model, @RequestParam(value = "id_kota", required = false) String id_kota,
    		@RequestParam(value = "id_kecamatan", required = false) String id_kecamatan,
    		@RequestParam(value = "id_kelurahan", required = false) String id_kelurahan)
    {
		
		if(id_kelurahan != null) {
			KotaModel kota = lokasiDAO.selectKotaById(id_kota);
			KecamatanModel kecamatan = lokasiDAO.selectKecamatanById(id_kecamatan);
			KelurahanModel kelurahan = lokasiDAO.selectKelurahanById(id_kelurahan);
			List<PendudukModel> listPenduduk = pendudukDAO.selectPendudukByKelurahan(id_kelurahan);
			
			model.addAttribute("kota", kota);
			model.addAttribute("kecamatan", kecamatan);
			model.addAttribute("kelurahan", kelurahan);
			model.addAttribute("listPenduduk", listPenduduk);
			model.addAttribute("termuda", pendudukDAO.pendudukTermuda(id_kelurahan));
			model.addAttribute("tertua", pendudukDAO.pendudukTertua(id_kelurahan));
			return "cari-penduduk-result";
			
		}
		if(id_kecamatan != null) {
			KotaModel kota = lokasiDAO.selectKotaById(id_kota);
			KecamatanModel kecamatan = lokasiDAO.selectKecamatanById(id_kecamatan);
			List<KelurahanModel> listKelurahan = lokasiDAO.selectAllKelurahanByKecamatan(id_kecamatan);
			
			model.addAttribute("kota", kota);
			model.addAttribute("kecamatan", kecamatan);
			model.addAttribute("listKelurahan", listKelurahan);
			return "cari-penduduk-kelurahan";
		}
		if(id_kota != null) {
			KotaModel kota = lokasiDAO.selectKotaById(id_kota);
			List<KecamatanModel> listKecamatan = lokasiDAO.selectAllKecamatanByKota(id_kota);
			model.addAttribute("kota", kota);
			model.addAttribute("listKecamatan", listKecamatan);
			return "cari-penduduk-kecamatan";
		}
		List<KotaModel> listKota = lokasiDAO.selectAllKota();
		model.addAttribute("listKota", listKota);
		model.addAttribute("termuda", pendudukDAO.pendudukTermuda(id_kelurahan));
		model.addAttribute("tertua", pendudukDAO.pendudukTertua(id_kelurahan));
        return "cari-penduduk-kota";
        
    }
	
	
}
