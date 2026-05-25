package com.spring.repository;


import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.spring.model.BookBean;

@Repository
public class BookRepository {
	 @Autowired
	    private JdbcTemplate jdbcTemplate;

	    // =========================
	    // Save Book
	    // =========================
	    public int saveBook(BookBean book){
	        // database value 그대로 book_status insert
	        String sql = "INSERT INTO book(book_name,description,release_year,price,book_status,stock) VALUES (?,?,?,?,?,?)";

	        KeyHolder keyHolder = new GeneratedKeyHolder();

	        jdbcTemplate.update(connection -> {
	            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	            ps.setString(1, book.getBookName());
	            ps.setString(2, book.getDescription());
	            ps.setInt(3, book.getReleaseYear());
	            ps.setDouble(4, book.getPrice());
	            ps.setString(5, book.getBookStatus()); // database ရဲ့ value 그대로
	            ps.setInt(6, book.getStock());
	            return ps;
	        }, keyHolder);

	        return keyHolder.getKey().intValue();
	    }

	    // =========================
	    // Save Book Author
	    // =========================
	    public void saveBookAuthor(int bookId, int authorId){
	        String sql = "INSERT INTO book_author(book_id,author_id) VALUES (?,?)";
	        jdbcTemplate.update(sql, bookId, authorId);
	    }

	    // =========================
	    // Save Book Category
	    // =========================
	    public void saveBookCategory(int bookId, int categoryId){
	        String sql = "INSERT INTO book_category(book_id,category_id) VALUES (?,?)";
	        jdbcTemplate.update(sql, bookId, categoryId);
	    }

	    // =========================
	    // Dropdown Data
	    // =========================
	    public List<Map<String,Object>> getAllAuthors(){
	        return jdbcTemplate.queryForList("SELECT * FROM author");
	    }

	    public List<Map<String,Object>> getAllCategories(){
	        return jdbcTemplate.queryForList("SELECT * FROM category");
	    }

	    public String getAuthorName(int id){
	        return jdbcTemplate.queryForObject("SELECT author_name FROM author WHERE id=?", String.class, id);
	    }

	    public String getCategoryName(int id){
	        return jdbcTemplate.queryForObject("SELECT cat_name FROM category WHERE id=?", String.class, id);
	    }
}
