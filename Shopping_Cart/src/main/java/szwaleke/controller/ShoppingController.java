package szwaleke.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import szwaleke.beans.ShoppingItem;
import szwaleke.database.DatabaseAccess;

@Controller
public class ShoppingController {
	
	@Autowired
	private DatabaseAccess da;
	
	@GetMapping("/")
	public String view(Model model) {
		model.addAttribute("shoppinglist", da.getShoppingList());
		return "view";
	}
	
	@GetMapping("/add")
	public String addShoppingItem(Model model) {
		model.addAttribute("item", new ShoppingItem());
		return "add";
	}
	
	@PostMapping("/add")
	public String doAddShoppingItem(Model model, @ModelAttribute ShoppingItem item) {
		da.addShoppingItem(item);
		model.addAttribute("item", new ShoppingItem());
		return "redirect:/";
	}
	
	@GetMapping("/edit/{id}")
	public String editShoppingItem(@PathVariable int id, Model model) {
		model.addAttribute("item", da.selectItem(id));
		return "edit";
	}
	
	@PostMapping("/edit")
	public String makeEdit(@ModelAttribute ShoppingItem item) {
		da.editShoppingItem(item);
		return "redirect:/";
	}
	
	@GetMapping("/delete/{id}")
	public String deleteShoppingItem(@PathVariable int id) {
		da.deleteShoppingItem(id);
		return "redirect:/";
	}
}
