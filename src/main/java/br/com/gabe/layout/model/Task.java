package br.com.gabe.layout.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Task {

  private Long id;
  private String name;
  @DateTimeFormat(pattern = "dd/MM/yyyy")
  private LocalDate date;
}
