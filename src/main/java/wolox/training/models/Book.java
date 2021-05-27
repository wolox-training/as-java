package wolox.training.models;

import static com.google.common.base.Preconditions.checkNotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import wolox.training.enums.ValidationError;

/**
 * Model to the table Book.
 */
@Entity
@ApiModel(description = "Model to the Book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @ApiModelProperty(value = "The identifier of the book", example = "321")
    private long id;

    @NotNull
    @ApiModelProperty(value = "The gener of the book", example = "Action")
    private String genre;

    @NotNull
    private String author;

    @NotNull
    private String image;

    @NotNull
    private String title;

    @NotNull
    private String subtitle;

    @NotNull
    private String publisher;

    @NotNull
    private String year;

    @NotNull
    private int pages;

    @NotNull
    private String isbn;

    public Book() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = checkNotNull(id, ValidationError.NULL_VALUE.getMsg());
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = checkNotNull(genre, ValidationError.NULL_VALUE.getMsg());
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = checkNotNull(author, ValidationError.NULL_VALUE.getMsg());
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = checkNotNull(image, ValidationError.NULL_VALUE.getMsg());
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = checkNotNull(title, ValidationError.NULL_VALUE.getMsg());
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = checkNotNull(subtitle, ValidationError.NULL_VALUE.getMsg());
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = checkNotNull(publisher, ValidationError.NULL_VALUE.getMsg());
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = checkNotNull(year, ValidationError.NULL_VALUE.getMsg());
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = checkNotNull(pages, ValidationError.NULL_VALUE.getMsg());
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = checkNotNull(isbn, ValidationError.NULL_VALUE.getMsg());
    }
}
