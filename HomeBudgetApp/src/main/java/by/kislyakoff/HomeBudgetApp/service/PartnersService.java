package by.kislyakoff.HomeBudgetApp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import by.kislyakoff.HomeBudgetApp.model.Partner;
import by.kislyakoff.HomeBudgetApp.repository.PartnersRepository;

@Service
@Transactional(readOnly = true)
public class PartnersService {
	
	public static int DEFAULT_PARTNERS_PER_PAGE = 10;
	
	public static enum Order {
        DEFAULT,
        NAME_ASC,
        NAME_DESC
    }
	
	private final PartnersRepository partnersRepository;

	@Autowired
	public PartnersService(PartnersRepository partnersRepository) {
		this.partnersRepository = partnersRepository;
	}
	
	public List<Partner> partnersList() {
		return partnersRepository.findAllByOrderByNameAsc();
	}
	
	public Page<Partner> partnersListPaginated(String seek, int page, int partnersPerPage, PartnersService.Order order) {
		
		Sort sort;
		
		switch (order) {
		
		case NAME_ASC:
			sort = Sort.by("name").ascending();
			break;
		case NAME_DESC:
			sort = Sort.by("name").descending();
			break;
		case DEFAULT:
			default:
				sort = Sort.by("id").ascending();
		}
		
		return seek == null ? 
				partnersRepository.findAll(PageRequest.of(page, partnersPerPage, sort)) :
					partnersRepository.findByNameContainingIgnoreCase(seek, PageRequest.of(page, partnersPerPage, sort));
	}
	
	public Optional<Partner> findByName(String name) {
		return partnersRepository.findByName(name);
	}
	
	public Partner findById(Integer id) {
		return partnersRepository.findById(id).orElse(null);
	}

	@Transactional
	public void create(Partner partner) {
		partnersRepository.save(partner);
	}

	@Transactional
	public void update(Integer id, Partner updatedPartner) {
		updatedPartner.setId(id);
		partnersRepository.save(updatedPartner);
	}
	
	@Transactional
	public void delete(Integer id) {
		partnersRepository.deleteById(id);
	}
	

}
