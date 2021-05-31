package wolox.training.models;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Strings.isNullOrEmpty;

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
    @ApiModelProperty(value = "The genre of the book", example = "Action")
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
        checkArgument(!isNullOrEmpty(genre), ValidationError.NULL_VALUE.getMsg());
        this.genre = genre;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        checkArgument(!isNullOrEmpty(author), ValidationError.NULL_VALUE.getMsg());
        this.author = author;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        checkArgument(!isNullOrEmpty(image), ValidationError.NULL_VALUE.getMsg());
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        checkArgument(!isNullOrEmpty(title), ValidationError.NULL_VALUE.getMsg());
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        checkArgument(!isNullOrEmpty(subtitle), ValidationError.NULL_VALUE.getMsg());
        this.subtitle = subtitle;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        checkArgument(!isNullOrEmpty(publisher), ValidationError.NULL_VALUE.getMsg());
        this.publisher = publisher;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        checkArgument(!isNullOrEmpty(year), ValidationError.NULL_VALUE.getMsg());
        this.year = year;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        checkArgument(pages == 0 , ValidationError.ZERO_VALUE.getMsg());
        this.pages = pages;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        checkArgument(!isNullOrEmpty(isbn), ValidationError.NULL_VALUE.getMsg());
        this.isbn = isbn;
    }
}
