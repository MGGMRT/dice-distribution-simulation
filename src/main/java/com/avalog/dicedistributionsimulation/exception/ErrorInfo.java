package com.avalog.dicedistributionsimulation.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Builder
@Component
@NoArgsConstructor
@AllArgsConstructor
public class ErrorInfo {
  private String code;
  private String description;
  private String field;

  public ErrorInfo( String description, String field) {
    this.description = description;
    this.field = field;
  }

}
