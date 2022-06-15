package services;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import models.Item;

public class ItemDBservices {

	public void createItemTable(Connection connection) {

		try (Statement statment = connection.createStatement()) {
			String query = "CREATE TABLE ITEM(\r\n" + "itemId int not null identity(1000,1),\r\n"
					+ "name nvarchar(40),\r\n" + "unitPrice money,\r\n" + "purchaseDate date,\r\n" + "quantity int,\r\n"
					+ "primary key(itemId)\r\n" + ")";
			statment.execute(query); // TODO how to check if the table was created
			System.out.println("Success creating the table.");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void seedDB3Items(Connection connection) {

		try (Statement statment = connection.createStatement()) {

			String query = "insert into item (name, unitPrice, purchaseDate, quantity) values('mouse', 120, '2021-1-1', 1);\r\n"
					+ "insert into item (name, unitPrice, purchaseDate, quantity) values('keyboard', 99.99, '2021-5-7', 3);\r\n"
					+ "insert into item (name, unitPrice, purchaseDate, quantity) values('screen', 1399, '2020-2-26', 2);";

			statment.executeUpdate(query);
			System.out.println("Success -  added 3 items to DB ! ");

		} catch (SQLException e) {
			System.out.println("Something went wrong!");
			e.printStackTrace();
		}

	}

	public Item getItem(Connection connection, String id) {

		Item item = null;
		String query = "select * from Item where itemId = " + id;

		try (Statement statment = connection.createStatement(); ResultSet resultSet = statment.executeQuery(query);) {

			if (resultSet.next()) {

				item = new Item();
				item.setItemId(resultSet.getInt(1));
				item.setName(resultSet.getString(2));
				item.setUniTPrice(resultSet.getDouble(3));
				item.setDate(resultSet.getDate(4).toLocalDate());
				item.setQuantity(resultSet.getInt(5));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return item;
	}

	public void addItem(Connection connection, Item item) {

		try (Statement statment = connection.createStatement()) {

			String query = "INSERT into Item (name, unitPrice, purchaseDate, quantity)" + "values('%s', %f, '%s', %d);"
					.formatted(item.getName(), item.getUniTPrice(), item.getDate().toString(), item.getQuantity());
			int rowsAffected = statment.executeUpdate(query);
			System.out.println("Success ! " + rowsAffected + " rows affected");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Item updateItem(Connection connection, Item item) {

		Item itm = getItem(connection, "%d".formatted(item.getItemId()));
		if (itm == null) {
			System.err.println("The Item does not exist in the DB!");
			return null;
		}

		if (!item.equals(itm)) {
			try (Statement statment = connection.createStatement()) {
				String query = "UPDATE Item "
						+ "set name='%S', unitPrice= %f, purchaseDate= '%S', quantity= %d".formatted(item.getName(),
								item.getUniTPrice(), item.getDate().toString(), item.getQuantity())
						+ "\n where itemid = %d".formatted(item.getItemId());

				int rowsAffected = statment.executeUpdate(query);

				System.out.println("Success ! " + (rowsAffected) + " rows affected");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return item;
	}

	public Item deleteItem(Connection connection, Item item) {
		Item itm = getItem(connection, "%d".formatted(item.getItemId()));

		if (itm == null)
			System.err.println("The Item does not exist in the DB!");

		try (Statement statment = connection.createStatement()) {

			String query = "Delete from Item where itemId = %d".formatted(item.getItemId());
			int rowsAffected = statment.executeUpdate(query);
			System.out.println("Success ! " + rowsAffected + " rows affected");

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return itm;
	}

	public List<Item> addListOfItems(Connection connection, List<Item> items) {

		setConnectionCommitFalse(connection);

		String query = "INSERT into Item (name, unitPrice, purchaseDate, quantity)" + "values(?, ?, ?, ?);";

		try (PreparedStatement preparedStatment = connection.prepareStatement(query)) {
			for (Item item : items) {

				preparedStatment.setString(1, item.getName());
				preparedStatment.setDouble(2, item.getUniTPrice());
				preparedStatment.setDate(3, Date.valueOf(item.getDate()));
				preparedStatment.setInt(4, item.getQuantity());

				preparedStatment.addBatch();

			}

			int[] rowsAffected = preparedStatment.executeBatch();

			for (int i : rowsAffected) {
				System.out.println("Success ! " + i + " rows affected");
				if (i == 0)
					throw new SQLException("something went wrong!");
			}

		} catch (SQLException e) {
			System.err.println("Rolling back" + e.getMessage());
			rollBackCon(connection);
		}

		setConnectionCommitTrue(connection);

		return getLastItems(connection, items.size());
	}

	public List<Item> getLastItems(Connection connection, int lastItemsNum) {

		Item item = null;
		String query = "select * from Item";
		List<Item> lastItems = new ArrayList<>();

		try (Statement statment = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY); ResultSet resultSet = statment.executeQuery(query);) {

			System.out.println(resultSet);

			resultSet.afterLast();
			int index = 1;
			while (resultSet.previous() && lastItemsNum >= index) {

				item = new Item();
				item.setItemId(resultSet.getInt(1));
				item.setName(resultSet.getString(2));
				item.setUniTPrice(resultSet.getDouble(3));
				item.setDate(resultSet.getDate(4).toLocalDate());
				item.setQuantity(resultSet.getInt(5));

				lastItems.add(item);
				index++;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lastItems;
	}

	public List<Item> updateListOfItems(Connection connection, List<Item> items) {

		setConnectionCommitFalse(connection);

		List<String> idsUpdated = new ArrayList<>();
		String query = "UPDATE Item " + "set name=? , unitPrice= ?, purchaseDate= ?, quantity= ? \n where itemid = ?";

		try (PreparedStatement preparedStatment = connection.prepareStatement(query)) {

			for (Item item : items) {
				if (item != null) {
					preparedStatment.setString(1, item.getName());
					preparedStatment.setDouble(2, item.getUniTPrice());
					preparedStatment.setDate(3, Date.valueOf(item.getDate()));
					preparedStatment.setInt(4, item.getQuantity());
					preparedStatment.setInt(5, item.getItemId());

					preparedStatment.addBatch();
					idsUpdated.add("%s".formatted(item.getItemId()));
				}else {
					addFakeItemToStatment(preparedStatment);
				}
			}

			int[] rowsAffected = preparedStatment.executeBatch();

			for (int i : rowsAffected) {
				if (i == 0)
					throw new SQLException("something went wrong!");
				System.out.println("Success ! " + i + " rows affected");
			}

		} catch (SQLException e) {
			System.err.println("Rolling back" + e.getMessage());
			rollBackCon(connection);
			idsUpdated.removeAll(idsUpdated);
		}

		setConnectionCommitTrue(connection);

		return getListItemsByIds(connection, idsUpdated);
	}

	public List<Item> getListItemsByIds(Connection connection, List<String> ids) {

		List<Item> items = new ArrayList<Item>();
		try (Statement statement = connection.createStatement()) {

			for (String id : ids) {
				if (checkItemExist(connection, id))
					items.add(getItem(connection, id));
				else
					items.add(null); // only to see the error msg & rollback
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return items;
	}

	private boolean checkItemExist(Connection connection, String id) {
		return getItem(connection, id) != null;
	}

	private void setConnectionCommitFalse(Connection connection) {
		try {
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void setConnectionCommitTrue(Connection connection) {
		try {
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void rollBackCon(Connection connection) {
		try {
			connection.rollback();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void addFakeItemToStatment(PreparedStatement preparedStatment) {
		try {
			preparedStatment.setString(1, "1040");
			preparedStatment.setDouble(2, 102f);
			preparedStatment.setDate(3, Date.valueOf(LocalDate.now()));
			preparedStatment.setInt(4, 4);
			preparedStatment.setInt(5, 4);
			preparedStatment.addBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
