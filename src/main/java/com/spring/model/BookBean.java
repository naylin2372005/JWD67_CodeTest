package com.spring.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class BookBean {
	
	  private int id;
	    private String bookName;
	    private String description;
	    private Integer releaseYear;
	    private double price;
	    private int stock;
	    private String bookStatus;

	    private int authorId;
	    private int categoryId;

	    private String authorName;
	    private String catName;

	   
	}

