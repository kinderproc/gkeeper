package com.algon.gkeeper.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Platform {
  MAC("Macintosh"),
  WINDOWS("Windows");

  private final String title;
}
