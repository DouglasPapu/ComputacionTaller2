package co.edu.icesi.fi.tics.tssc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.icesi.fi.tics.tssc.model.TsscAdmin;
import co.edu.icesi.fi.tics.tssc.repositories.IAdminRepository;

@Service
public class AdminServiceImpl implements AdminService{
	
	
	private IAdminRepository adminRepository;
	
	@Autowired
	public AdminServiceImpl(IAdminRepository adminRepository) {
		this.adminRepository = adminRepository;
	}

	@Override
	public TsscAdmin save(TsscAdmin nuevo) {
		return adminRepository.save(nuevo);
	}

	@Override
	public TsscAdmin edit(TsscAdmin editado) {
		return adminRepository.save(editado);
	}

	@Override
	public void delete(TsscAdmin delete) {
		adminRepository.delete(delete);
	}
	
	

}
