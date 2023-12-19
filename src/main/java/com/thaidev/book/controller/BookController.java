package com.thaidev.book.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.thaidev.book.entity.Book;
import com.thaidev.book.entity.MyBookList;
import com.thaidev.book.service.BookService;
import com.thaidev.book.service.MyBookListService;

@Controller
public class BookController {

	@Autowired
	private BookService bookService;
	
	@Autowired
	private MyBookListService myBookListService;
	
	@GetMapping("/")
	public String home() {
		return "home";
	}
	
	@GetMapping("/book-register")
	public String bookRegister() {
		return "bookRegister";
	}
	
	@GetMapping("/available-books")
	public ModelAndView getAllBook() {
		 List<Book> list = bookService.getAllBook();
//		 ModelAndView mav = new ModelAndView();
//		 mav.setViewName("bookList");
//		 mav.addObject("book", list);
		 return new ModelAndView("bookList", "book", list);
	}
	
	@PostMapping("/save")
	public String addBook(@ModelAttribute Book book) {
		bookService.save(book);
		return "redirect:/available-books";
	}
	
	@GetMapping("/my-books")
	public String getMyBook(Model model) {
		List<MyBookList> list = myBookListService.getAllMyBooks();
		model.addAttribute("book", list);
		return "myBooks";
	}
	
	@RequestMapping("/mylist/{id}")
	public String getMyList(@PathVariable("id") int id) {
		Book book = bookService.getBookById(id);
		MyBookList bookList = new MyBookList(book.getId(), book.getName(), book.getAuthor(), book.getPrice());
		myBookListService.saveMyBooks(bookList);
		return "redirect:/my-books";
	}
	
	@RequestMapping("editBook/{id}")
	public String editBook(@PathVariable("id") int id, Model model) {
		Book book = bookService.getBookById(id);
		model.addAttribute("book", book);
		return "bookEdit";
	}
	
	@RequestMapping("/deleteBook/{id}")
	public String deleteBook(@PathVariable("id") int id) {
		bookService.deleteById(id);
		return "redirect:/available-books";
	}
	
}
