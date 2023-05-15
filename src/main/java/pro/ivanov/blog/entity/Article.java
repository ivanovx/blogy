package pro.ivanov.blog.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "artilces")
public class Article {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @PastOrPresent
    @Column(nullable = false)
    private LocalDateTime createdOn = LocalDateTime.now();

    @PastOrPresent
    @Column(nullable = false)
    private LocalDateTime updatedOn = LocalDateTime.now();

    @ManyToOne
    private User author;
}