package com.backend.ServiceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.Entity.User;
import com.backend.Repository.UserRepo;
import com.backend.Service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userrepo;

	@Override
	public User saveUser(User user) {
		return userrepo.save(user);
	}

	@Override
	public Optional<User> findByEmail(String email) {
		return userrepo.findByEmail(email);
	}

	@Override
	public boolean isActiveUser(String email) {
		Optional<User> userOpt = userrepo.findByEmail(email);
		return userOpt.isPresent() && userOpt.get().getIsActive();
	}

	@Override
	public boolean authenticate(String email, String password) {
		Optional<User> userOpt = userrepo.findByEmail(email);
		return userOpt.isPresent() && userOpt.get().getPassword().equals(password) && userOpt.get().getIsActive();
	}

	@Override
	public List<User> getallusers() {
		return userrepo.findAll();
	}

	@Override
	public Optional<User> findById(Long id) {
		return userrepo.findById(id);
	}

	@Override
	public Optional<User> updateUser(Long id, User user) {
		return userrepo.findById(id).map(existingUser -> {
			existingUser.setPrefix(user.getPrefix());
			existingUser.setFirstname(user.getFirstname());
			existingUser.setLastname(user.getLastname());
			existingUser.setEmail(user.getEmail());
			existingUser.setIsActive(user.getIsActive());
			existingUser.setUsername(user.getUsername());
			existingUser.setPassword(user.getPassword());
			existingUser.setAllowLogin(user.getAllowLogin());
			existingUser.setLocation(user.getLocation());

			existingUser.setEnableServiceStaffPin(user.isEnableServiceStaffPin());
			existingUser.setStaffPin(user.getStaffPin());

			existingUser.setSalesCommissionPercentage(user.getSalesCommissionPercentage());
			existingUser.setCommisionPercent(user.getCommisionPercent());
			existingUser.setAllowContacts(user.getAllowContacts());
			existingUser.setSelectedContacts(user.getSelectedContacts());

			existingUser.setLanguage(user.getLanguage());
			existingUser.setDateOfBirth(user.getDateOfBirth());
			existingUser.setGender(user.getGender());
			existingUser.setMaritalStatus(user.getMaritalStatus());
			existingUser.setBloodGroup(user.getBloodGroup());
			existingUser.setMobileNumber(user.getMobileNumber());
			existingUser.setAlternateContactNumber(user.getAlternateContactNumber());
			existingUser.setFamilyContactNumber(user.getFamilyContactNumber());
			existingUser.setFacebookLink(user.getFacebookLink());
			existingUser.setTwitterLink(user.getTwitterLink());
			existingUser.setSocialMedia1(user.getSocialMedia1());
			existingUser.setSocialMedia2(user.getSocialMedia2());
			existingUser.setCustomField1(user.getCustomField1());
			existingUser.setCustomField2(user.getCustomField2());
			existingUser.setCustomField3(user.getCustomField3());
			existingUser.setCustomField4(user.getCustomField4());
			existingUser.setGuardianName(user.getGuardianName());
			existingUser.setIdProofName(user.getIdProofName());
			existingUser.setIdProofNumber(user.getIdProofNumber());
			existingUser.setPermanentAddress(user.getPermanentAddress());
			existingUser.setCurrentAddress(user.getCurrentAddress());

			existingUser.setDepartmentId(user.getDepartmentId());
			existingUser.setDesignationId(user.getDesignationId());
			existingUser.setPrimaryWorkLocation(user.getPrimaryWorkLocation());
			existingUser.setBasicSalary(user.getBasicSalary());
			existingUser.setSalaryIn(user.getSalaryIn());
			existingUser.setPayComponentId(user.getPayComponentId());

			existingUser.setAccountHolderName(user.getAccountHolderName());
			existingUser.setAccountNumber(user.getAccountNumber());
			existingUser.setBankName(user.getBankName());
			existingUser.setIfsc(user.getIfsc());
			existingUser.setBranch(user.getBranch());
			existingUser.setTaxPayerId(user.getTaxPayerId());
			existingUser.setRoles(user.getRoles());

			return userrepo.save(existingUser);
		});
	}

	@Override
	public boolean deleteUser(Long id) {
		if (userrepo.existsById(id)) {
			userrepo.deleteById(id);
			return true;
		}
		return false;
	}

	@Override
	public User getUserWithRolesAndPermissions(String email) {
		List<User> users = userrepo.findByEmailItsPermissions(email);
		if (users.size() == 1) {
			return users.get(0);
		} else if (users.isEmpty()) {
			return null; // or throw an appropriate exception
		} else {
			throw new IllegalStateException("Multiple users found with username: " + email);
		}
	}

	@Override
	public Optional<String> getUserName(String email) {
		return userrepo.getUsernameByEmail(email);
	}
}
