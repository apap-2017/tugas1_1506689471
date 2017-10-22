package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.model.KeluargaModel;
import com.example.service.KeluargaService;
import com.example.service.LokasiService;

@Controller
public class KeluargaController {
	
	@Autowired
	KeluargaService keluargaDAO;
	
	@Autowired
	LokasiService lokasiDAO;
	
	@RequestMapping("/keluarga")
    public String viewKeluarga (Model model,
            @RequestParam(value = "nkk") String nkk)
    {
       KeluargaModel keluarga = keluargaDAO.selectKeluarga(nkk);
        if (keluarga != null) {
            model.addAttribute ("keluarga", keluarga);
            return "viewKeluarga";
        } else {
            model.addAttribute ("nkk", nkk);
            return "not-found-keluarga";
        }
    }
	
	@RequestMapping("/keluarga/tambah")
    public String tambahKeluarga(Model model)
    {
		model.addAttribute("keluarga", new KeluargaModel());
		model.addAttribute("allKelurahan", lokasiDAO.selectAllKelurahan());
		return "form-tambah-keluarga";
    }
	
	@RequestMapping(value = "/keluarga/tambah", method = RequestMethod.POST)
    public String tambahKeluarga(Model model, @ModelAttribute KeluargaModel keluarga)
    {
		keluarga.generateNkk(keluargaDAO);
		keluargaDAO.addKeluarga(keluarga);
		model.addAttribute("nkk", keluarga.getNomor_kk());
        return "sukses-tambah-keluarga";
    }
	
	@RequestMapping("/keluarga/ubah/{nkk}")
    public String ubahPenduduk(Model model, 
            @PathVariable(value = "nkk") String nkk)
    {
		KeluargaModel keluarga = keluargaDAO.selectKeluarga(nkk);
		if (keluarga != null) {
			model.addAttribute("allKelurahan", lokasiDAO.selectAllKelurahan());
			model.addAttribute("keluarga", keluarga);
	        return "form-update-keluarga";
		} else {
			return "not-found-keluarga";
		}
		
    }
	

	@RequestMapping(value = "/keluarga/ubah/{nkk}", method = RequestMethod.POST)
    public String ubahKeluarga(Model model, @PathVariable(value = "nkk") String nkk,
    		@ModelAttribute KeluargaModel keluarga)
    {
		KeluargaModel keluargaLama = keluargaDAO.selectKeluarga(nkk);
		keluarga.setNomor_kk(keluargaLama.getNomor_kk());
		keluarga.setId(keluargaLama.getId());
		keluarga.setIs_tidak_berlaku(keluargaLama.getIs_tidak_berlaku());

		if(!keluargaLama.getId_kelurahan().equals(keluarga.getId_kelurahan())) {
			keluarga.generateNkk(keluargaDAO);
		}
		keluargaDAO.updateKeluarga(keluarga);
		
		model.addAttribute("nkk", keluarga.getNomor_kk());
        return "sukses-tambah-keluarga";
    }
}
