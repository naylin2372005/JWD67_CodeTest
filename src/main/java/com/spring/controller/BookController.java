package com.spring.controller;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.spring.model.BookBean;
import com.spring.repository.BookRepository;

@Controller
public class BookController {
	 @Autowired
	    private BookRepository repo;

	    @GetMapping("/book/form")
	    public String bookForm(Model model){
	        model.addAttribute("book", new BookBean());
	        model.addAttribute("authors", repo.getAllAuthors());
	        model.addAttribute("categories", repo.getAllCategories());

	        // Year select box: 1900 ~ current year
	        int currentYear = Year.now().getValue();
	        List<Integer> years = new ArrayList<>();
	        for(int y=currentYear; y>=1900; y--){
	            years.add(y);
	        }
	        model.addAttribute("years", years);

	        return "book_form";
	    }

	    @PostMapping("/book/save")
	    public String saveBook(@ModelAttribute BookBean book, Model model){

	        int currentYear = Year.now().getValue();

	        // =========================
	        // Validation
	        // =========================
	        if(book.getBookName() == null || book.getBookName().trim().isEmpty() ||
	           book.getDescription() == null || book.getDescription().trim().isEmpty()){
	            return errorReturn(model, "Book Name and Description are required!");
	        }

	        if(book.getPrice() <= 0){
	            return errorReturn(model, "Price must be greater than 0");
	        }

	        if(book.getStock() <= 0){
	            return errorReturn(model, "Stock must be greater than 0");
	        }

	        if(book.getAuthorId() == 0){
	            return errorReturn(model, "Author must be selected");
	        }

	        if(book.getCategoryId() == 0){
	            return errorReturn(model, "Category must be selected");
	        }

	        if(book.getReleaseYear() <= 0 || book.getReleaseYear() > currentYear){
	            return errorReturn(model, "Release Year is invalid!");
	        }

	        // =========================
	        // Save
	        // =========================
	        if(book.getBookStatus() == null || book.getBookStatus().trim().isEmpty()){
	            book.setBookStatus("available"); // database default
	        }

	        int bookId = repo.saveBook(book);
	        repo.saveBookAuthor(bookId, book.getAuthorId());
	        repo.saveBookCategory(bookId, book.getCategoryId());

	        book.setAuthorName(repo.getAuthorName(book.getAuthorId()));
	        book.setCatName(repo.getCategoryName(book.getCategoryId()));

	        model.addAttribute("book", book);

//	        return "book_info";
	        return "book_info";
	    }

	    private String errorReturn(Model model, String message){
	        model.addAttribute("error", message);
	        model.addAttribute("authors", repo.getAllAuthors());
	        model.addAttribute("categories", repo.getAllCategories());

	        int currentYear = Year.now().getValue();
	        List<Integer> years = new ArrayList<>();
	        for(int y=currentYear; y>=1900; y--){
	            years.add(y);
	        }
	        model.addAttribute("years", years);

	        model.addAttribute("book", new BookBean());
	        return "book_form";
	    }
}
