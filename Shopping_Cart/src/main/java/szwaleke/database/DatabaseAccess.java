package szwaleke.database;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import szwaleke.beans.ShoppingItem;

@Repository
public class DatabaseAccess {
	
	@Autowired
	protected NamedParameterJdbcTemplate jdbc;
	
	//List of all items within the shopping cart
	public ArrayList<ShoppingItem> getShoppingList() {
		String query="SELECT id, name, description, price, link FROM my_purchases";
		ArrayList<ShoppingItem> shoppingList = 
				(ArrayList<ShoppingItem>)jdbc.query(query,
				new BeanPropertyRowMapper<ShoppingItem>(ShoppingItem.class));
		return shoppingList;
	}
	
	//select item from shopping cart
	public ShoppingItem selectItem(int id) {
		ShoppingItem item = null;
		String query = "SELECT * FROM my_purchases WHERE id=:id";
		MapSqlParameterSource source = new MapSqlParameterSource();
		source.addValue("id", id);
		ArrayList<ShoppingItem> shoppingList = 
				(ArrayList<ShoppingItem>)jdbc.query(query, source,
						new BeanPropertyRowMapper<ShoppingItem>(ShoppingItem.class));
		item = shoppingList.get(0);
		return item;
	}
	
	//add new item to shopping cart
	public void addShoppingItem(ShoppingItem item) {
		String query = "INSERT INTO my_purchases (name, description, price, link) "
				+ "VALUES (:name, :description, :price, :link)";
		MapSqlParameterSource source = new MapSqlParameterSource();
		source.addValue("name", item.getName());
		source.addValue("description", item.getDescription());
		source.addValue("price", item.getPrice());
		source.addValue("link", item.getLink());
		jdbc.update(query, source);
	}
	
	//delete item from shopping cart
	public void deleteShoppingItem(int id) {
		String query = "DELETE FROM my_purchases WHERE id = :id";
		MapSqlParameterSource source = new MapSqlParameterSource();
		source.addValue("id", id);
		jdbc.update(query, source);
	}
	
	//modify item inside shopping cart
	public void editShoppingItem(ShoppingItem item) {
		String query = "UPDATE my_purchases SET name=:name, description=:description, " + 
				"price=:price, link=:link WHERE id=:id";
		MapSqlParameterSource source = new MapSqlParameterSource();
			source.addValue("id", item.getId());
			source.addValue("name", item.getName());
			source.addValue("description", item.getDescription());
			source.addValue("price", item.getPrice());
			source.addValue("link", item.getLink());
			jdbc.update(query, source);
	}

}