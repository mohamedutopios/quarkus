package org.agoncal.quarkus.starting;


import javax.ws.rs.FormParam;

public class Book {

/*  public int id;
  public String title;
  public String author;
  public int yearOfPublication;
  public String genre;*/


  // Pour Bookresource3 et la partie @BeanParam
    @FormParam("id")
    public int id;

    @FormParam("title")
    private String title;

    @FormParam("author")
    private String author;

    @FormParam("publishedDate")
    private int yearOfPublication;

    @FormParam("price")
    private String genre;

    // Constructeur par défaut nécessaire pour RESTEasy
    public Book() {
    }



  // Constructor

  public Book(int id, String title, String author, int yearOfPublication, String genre) {
    this.id = id;
    this.title = title;
    this.author = author;
    this.yearOfPublication = yearOfPublication;
    this.genre = genre;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public int getYearOfPublication() {
    return yearOfPublication;
  }

  public void setYearOfPublication(int yearOfPublication) {
    this.yearOfPublication = yearOfPublication;
  }

  public String getGenre() {
    return genre;
  }

  public void setGenre(String genre) {
    this.genre = genre;
  }
}
