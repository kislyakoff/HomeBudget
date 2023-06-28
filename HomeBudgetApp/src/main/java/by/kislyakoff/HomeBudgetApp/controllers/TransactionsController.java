package by.kislyakoff.HomeBudgetApp.controllers;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import by.kislyakoff.HomeBudgetApp.dto.TransactionDTO;
import by.kislyakoff.HomeBudgetApp.dto.projections.TransactionProjection;
import by.kislyakoff.HomeBudgetApp.dto.projections.TransactionProjection2;
import by.kislyakoff.HomeBudgetApp.dto.projections.TransactionView;
import by.kislyakoff.HomeBudgetApp.model.Person;
import by.kislyakoff.HomeBudgetApp.model.Transaction;
import by.kislyakoff.HomeBudgetApp.service.TransactionsService;
import by.kislyakoff.HomeBudgetApp.util.helpers.PaginationHelper;

@Controller
@RequestMapping("/homebudget/transactions")
public class TransactionsController {
	
	private final TransactionsService transactionsService;
	private final ModelMapper modelMapper;
	

	@Autowired
	public TransactionsController(TransactionsService transactionsService, ModelMapper modelMapper) {
		this.transactionsService = transactionsService;
		this.modelMapper = modelMapper;
	}
	
	//TODO implement pagination and search
	@GetMapping
	public String transactionsList(@AuthenticationPrincipal(expression = "person") Person person,
									Model model) {
		
		List<TransactionView> list = transactionsService.getList(person.getId());
		model.addAttribute("transactions", list);
		
		
		return "layouts/transactions";
	}
	
	@GetMapping({"/paginated", "/paginated/{page}"})
	public String transactionsListPaginated(@AuthenticationPrincipal(expression = "id") Integer ownerId,
									@PathVariable Optional<Integer> page,
									@RequestParam(name = "seek", required = false) String seek,
									Model model) {
		Integer _page = 0;
		
		if (page.isPresent()) {
			_page = page.get() - 1;
		}
		Page<TransactionProjection2> list = transactionsService.getTransactionsListPaginated(ownerId, seek, _page, 
									TransactionsService.DEFAULT_TRANSACTIONS_PER_PAGE);
		model.addAttribute("transactions", list.getContent());
		model.addAttribute("page", _page + 1);
		model.addAttribute("pagination", PaginationHelper.generate(_page + 1, list.getTotalPages()));
		model.addAttribute("seek", seek);
		
		
		return "layouts/transactions";
	}
	
	// TODO just for test - pending to delete
	@GetMapping("/test/list")
	@ResponseBody
	public List<TransactionView> list(@AuthenticationPrincipal(expression = "person") Person person) {
		
		return transactionsService.getList(person.getId());
	}
	
// TODO just for test - pending to delete
	@GetMapping("/test/list2")
	@ResponseBody
	public List<TransactionView> list2(@AuthenticationPrincipal(expression = "person") Person person) {
		
		return transactionsService.getList2(person.getId());
	}
		
// TODO just for test - pending to delete
	@GetMapping("/test/list3")
	@ResponseBody
	public List<TransactionProjection2> list3(@AuthenticationPrincipal(expression = "person") Person person) {
		
		return transactionsService.getList3(person.getId());
		
	}
	
	@PostMapping("/create")
	@ResponseBody
	public ResponseEntity<HttpStatus> create(@RequestBody TransactionDTO transactionDTO,
											 @AuthenticationPrincipal(expression = "id") Integer ownerId) {
		
		return transactionsService.create(mapToTransaction(transactionDTO), ownerId) ? 
						ResponseEntity.ok(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	private Transaction mapToTransaction(TransactionDTO dto) {
		return modelMapper.map(dto, Transaction.class);
	}
	
	//TODO implement update logic
	@PatchMapping("edit/{id}")
	@ResponseBody
	public ResponseEntity<HttpStatus> edit(@RequestBody TransactionDTO transactionDTO, @PathVariable Long id,
											@AuthenticationPrincipal(expression = "id") Integer ownerId) {
		
		return transactionsService.update(id, mapToTransaction(transactionDTO), ownerId) ?
					ResponseEntity.ok(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@DeleteMapping("/erase/{id}")
	@ResponseBody
	public boolean delete(@PathVariable Long id, 
								@AuthenticationPrincipal(expression = "id") Integer ownerId) {
		
		return transactionsService.delete(id, ownerId);
	}

}
