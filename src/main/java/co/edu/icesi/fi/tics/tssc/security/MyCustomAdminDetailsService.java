package co.edu.icesi.fi.tics.tssc.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import co.edu.icesi.fi.tics.tssc.model.TsscAdmin;
import co.edu.icesi.fi.tics.tssc.repositories.IAdminRepository;

@Service
public class MyCustomAdminDetailsService implements UserDetailsService {

	private IAdminRepository adminRepository;
	
	@Autowired
	public MyCustomAdminDetailsService(IAdminRepository adminRepository) {		
		this.adminRepository = adminRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		TsscAdmin admin = adminRepository.findByUser(username);
		if (admin != null) {
			User.UserBuilder builder = User.withUsername(username).password(admin.getPassword())
					.roles(admin.getSuperAdmin());
			return builder.build();
		} else {
			throw new UsernameNotFoundException("User not found.");
		}
	}

}
