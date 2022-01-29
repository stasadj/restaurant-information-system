package com.restaurant.backend.service;

import com.restaurant.backend.domain.Staff;
import com.restaurant.backend.domain.StaffPaymentItem;
import com.restaurant.backend.dto.UserDTO;
import com.restaurant.backend.dto.requests.ChangePinDTO;
import com.restaurant.backend.dto.requests.SetSalaryDTO;
import com.restaurant.backend.exception.BadRequestException;
import com.restaurant.backend.exception.NotFoundException;
import com.restaurant.backend.repository.StaffPaymentItemRepository;
import com.restaurant.backend.repository.StaffRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class StaffService {
	private final StaffRepository staffRepository;
	private final StaffPaymentItemRepository staffPaymentItemRepository;

	public List<Staff> getAll() {
		return staffRepository.findAll();
	}

	public Staff findOne(Long id) throws NotFoundException {
		return staffRepository.findById(id).orElseThrow(
				() -> new NotFoundException(String.format("No staff member with id %d has been found", id)));
	}

	public Staff create(Staff staff) throws BadRequestException {
		staff.setId(null);
		staff.setDeleted(false);
		Optional<Staff> maybeStaff = staffRepository.findByPin(staff.getPin());
		if (maybeStaff.isPresent())
			throw new BadRequestException(String.format("Staff member with pin %d already exists.", staff.getPin()));
		return staffRepository.save(staff);
	}

	public Staff update(UserDTO userDTO) throws NotFoundException {
		Staff staff = findOne(userDTO.getId());
		staff.setFirstName(userDTO.getFirstName());
		staff.setLastName(userDTO.getLastName());
		staff.setEmail(userDTO.getEmail());
		staff.setPhoneNumber(userDTO.getPhoneNumber());
		return staffRepository.save(staff);
	}

	public void delete(Long id) throws NotFoundException {
		Staff staff = findOne(id);
		staffRepository.delete(staff);
	}

	public Staff changePin(ChangePinDTO changePinDTO) throws NotFoundException, BadRequestException {
		Optional<Staff> maybeStaff = staffRepository.findByPin(changePinDTO.getCurrentPin());
		Optional<Staff> maybeOtherStaff = staffRepository.findByPin(changePinDTO.getNewPin());
		if (maybeStaff.isEmpty())
			throw new NotFoundException("Current pin does not exist.");
		if (maybeOtherStaff.isPresent())
			throw new BadRequestException("New pin is already in use.");
		Staff staff = maybeStaff.get();
		staff.setPin(changePinDTO.getNewPin());
		staffRepository.save(staff);
		return staff;
	}

	public Staff changeSalary(SetSalaryDTO dto) throws NotFoundException {
		Staff staff = findOne(dto.getStaffId());
		staff.setMonthlyWage(dto.getMonthlyWage());
		return staffRepository.save(staff);
	}

	public List<StaffPaymentItem> getStaffPaymentItems(Long userId) throws NotFoundException {
		return staffPaymentItemRepository.findAllByUser_IdOrderByDateTimeAsc(userId);
	}
}
