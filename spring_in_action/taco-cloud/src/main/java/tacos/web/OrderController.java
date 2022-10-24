package tacos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import tacos.Order;
import tacos.User;
import tacos.data.OrderRepository;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("order")
//@ConfigurationProperties(prefix = "taco.orders")
public class OrderController {

//	private int pageSize = 20;

//	public void setPageSize(int pageSize) {
//		this.pageSize = pageSize;
//	}

	private OrderProps props;

	private OrderRepository orderRepo;

	public OrderController(OrderRepository orderRepo) {
		this.orderRepo = orderRepo;
	}

	@GetMapping("/current")
	public String orderForm(@AuthenticationPrincipal User user, @ModelAttribute Order order) {
		if(order.getDeliveryName() == null) {
			order.setDeliveryName(user.getFullname());
		}
		if(order.getDeliveryStreet() == null) {
			order.setDeliveryStreet(user.getStreet());
		}
		if(order.getDeliveryCity() == null) {
			order.setDeliveryCity(user.getCity());
		}
		if(order.getDeliveryZip() == null) {
			order.setDeliveryState(user.getState());
		}
		if(order.getDeliveryZip() == null) {
			order.setDeliveryZip(user.getZip());
		}

		return "orderForm";
	}

	@PostMapping
	public String processOrder(@Valid Order order, Errors errors, SessionStatus sessionStatus, @AuthenticationPrincipal User user) {
		if(errors.hasErrors()) {
			return "orderForm";
		}
//		log.info("Order submitted " + order);\

		order.setUser(user);

		orderRepo.save(order);
		sessionStatus.setComplete();

		return "redirect:/";
	}

	@GetMapping
	public String ordersForUser(@AuthenticationPrincipal User user, Model model) {
		Pageable pageable = PageRequest.of(0, props.getPageSize());
		model.addAttribute("orders", orderRepo.findByUserOrderByPlacedAtDesc(user, pageable ));
		return "orderList";
	}
}
